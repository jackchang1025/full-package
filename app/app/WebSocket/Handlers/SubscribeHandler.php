<?php

declare(strict_types=1);

namespace App\WebSocket\Handlers;

use App\Services\PanelTokenService;
use App\WebSocket\ConnectionManager;
use App\WebSocket\WebSocketLog;

final class SubscribeHandler
{
    private ConnectionManager $connectionManager;

    public function __construct(ConnectionManager $connectionManager)
    {
        $this->connectionManager = $connectionManager;
    }

    public function handle(int $fd, array $data): void
    {
        $subc = $data['subc'] ?? 'subscribe';

        if ($subc === 'checkphone') {
            (new CheckPhoneHandler($this->connectionManager))->handle($fd, $data);

            return;
        }

        $token = $data['token'] ?? '';

        if (empty($token)) {
            WebSocketLog::getLogger()->warning("Subscribe: missing token, fd={$fd}");
            $this->connectionManager->send($fd, [
                'type' => 'subscribe',
                'success' => false,
                'error' => 'Token is required',
            ]);

            return;
        }

        $tokenService = new PanelTokenService;
        $result = $tokenService->validateToken($token);

        if (! $result['authenticated']) {
            WebSocketLog::getLogger()->warning("Subscribe: invalid token, fd={$fd}");
            $this->connectionManager->send($fd, [
                'type' => 'subscribe',
                'success' => false,
                'error' => 'Invalid or expired token',
            ]);

            return;
        }

        $userId = $result['user_id'];
        $guard = $result['guard'];

        \Illuminate\Support\Facades\DB::reconnect();

        $isAdmin = $guard === 'admin';

        if ($isAdmin) {
            $user = \App\Models\Admin::find($userId);
        } else {
            $user = \App\Models\User::find($userId);
        }

        if ($user === null) {
            WebSocketLog::getLogger()->warning("Subscribe: user not found, fd={$fd}, userId={$userId}, guard={$guard}");
            $this->connectionManager->send($fd, [
                'type' => 'subscribe',
                'success' => false,
                'error' => 'User not found',
            ]);

            return;
        }

        // Resolve resource owner for sub-accounts
        $ownerEmail = $user->email;
        $ownerId = $isAdmin ? null : $userId;

        if (! $isAdmin) {
            $owner = $user->getResourceOwner();
            $ownerEmail = $owner->email;
            $ownerId = $owner->id;
        }

        WebSocketLog::getLogger()->info("Subscribe: fd={$fd}, userId={$userId}, guard={$guard}, isAdmin=".($isAdmin ? 'true' : 'false'));

        // Register panel user with userId for device authorization
        $this->connectionManager->registerPanelUser($fd, $ownerEmail, $isAdmin, $ownerId);

        // Get device list and stats
        $queryUserId = $isAdmin ? null : $ownerId;
        $devices = $this->connectionManager->getDeviceListForUser($queryUserId);
        $stats = $this->connectionManager->getDeviceStats($queryUserId);

        WebSocketLog::getLogger()->info("Subscribe: returning {$stats['total']} devices for fd={$fd}");

        $this->connectionManager->send($fd, [
            'type' => 'subscribe',
            'success' => true,
            'isAdmin' => $isAdmin,
            'devices' => $devices,
            'stats' => $stats,
        ]);
    }
}
