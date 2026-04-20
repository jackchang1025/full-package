<?php

namespace App\Http\Controllers;

use App\Exceptions\ResourceAccessDeniedException;
use App\Http\Requests\Device\DeviceProxyRequest;
use App\Http\Requests\Device\UpdateDeviceRequest;
use App\Models\Device;
use App\Models\Setting;
use App\Services\DeviceProxyService;
use App\Services\FrpcConfigService;
use App\Services\PanelTokenService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\RateLimiter;
use Inertia\Inertia;
use Inertia\Response;

/**
 * 设备管理控制器。
 *
 * 权限检查由路由中间件 permission:devices.* 统一完成，
 * 控制器内只处理资源归属校验。
 */
class DeviceController extends Controller
{
    public function index(Request $request): Response
    {
        $user = $request->user();
        $ownerId = $user->getResourceOwnerId();
        $showOfflineDevices = Setting::getBool('show_offline_devices') ?? true;

        $query = Device::where('is_removed', false)->where('user_id', $ownerId);

        if (! $showOfflineDevices) {
            $query->where('is_online', true);
        }

        $devices = $query->orderByDesc('last_seen_at')
            ->paginate(50)
            ->through(fn (Device $device) => [
                'id' => $device->id,
                'uuid' => $device->uuid,
                'name' => $device->name ?? '',
                'remark' => $device->remark ?? '',
                'model' => $device->model ?? '',
                'android_version' => $device->android_version ?? '',
                'country' => $device->country ?? '',
                'ip_address' => $device->ip_address ?? '',
                'ip_location' => $device->ip_location ?? '',
                'battery_level' => $device->battery_level,
                'is_online' => $device->is_online,
                'has_accessibility' => $device->has_accessibility,
                'last_seen_at' => $device->last_seen_at?->toISOString(),
                'installed_at' => $device->installed_at?->toISOString(),
            ]);

        $statsQuery = Device::where('is_removed', false)->where('user_id', $ownerId);
        $total = (clone $statsQuery)->count();
        $online = (clone $statsQuery)->where('is_online', true)->count();

        $stats = [
            'total' => $total,
            'online' => $online,
            'offline' => $total - $online,
        ];

        return Inertia::render('Devices/Index', [
            'devices' => $devices,
            'stats' => $stats,
            'canEdit' => $user->can('devices.edit'),
            'canControl' => $user->can('devices.control'),
            'showOfflineDevices' => $showOfflineDevices,
        ]);
    }

    public function show(Request $request, Device $device): Response
    {
        $user = $request->user();
        $this->ensureDeviceOwnership($device, $user);

        return Inertia::render('Devices/Show', [
            'device' => $device,
            'wsToken' => (new PanelTokenService)->generateToken($user->id, 'web'),
        ]);
    }

    public function control(Request $request, Device $device): Response
    {
        $user = $request->user();
        $this->ensureDeviceOwnership($device, $user);

        return Inertia::render('Devices/Control', [
            'device' => $device,
            'wsToken' => (new PanelTokenService)->generateToken($user->id, 'web'),
            'backUrl' => route('devices.index'),
        ]);
    }

    public function update(UpdateDeviceRequest $request, Device $device)
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $device->update($request->validated());

        if ($request->header('X-Inertia')) {
            return back();
        }

        return redirect()->route('devices.index')
            ->with('success', '备注已更新');
    }

    public function destroy(Request $request, Device $device)
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $device->update(['is_removed' => true]);

        return redirect()->route('devices.index')
            ->with('success', '设备已移除');
    }

    public function batchDestroy(Request $request)
    {
        $validated = $request->validate(['uuids' => 'required|array']);
        $ownerId = $request->user()->getResourceOwnerId();

        Device::whereIn('uuid', $validated['uuids'])
            ->where('user_id', $ownerId)
            ->update(['is_removed' => true]);

        return back();
    }

    /**
     * 通过 frpc 隧道 ping 设备，检查 HTTP Server 是否在线。
     * GET /devices/{device}/frpc-ping
     */
    public function frpcPing(Request $request, Device $device): JsonResponse
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $proxy = new DeviceProxyService;

        if (! $proxy->hasTunnel($device)) {
            return response()->json([
                'online' => false,
                'tunnel' => false,
                'message' => 'No frpc tunnel assigned. Device needs to request /api/agent/query.json first.',
            ]);
        }

        $alive = $proxy->ping($device);

        return response()->json([
            'online' => $alive,
            'tunnel' => true,
            'base_url' => $proxy->getDeviceBaseUrl($device),
            'frpc_port_map' => $device->getFrpcPortMap(),
            'message' => $alive ? 'Device HTTP Server is reachable via frpc tunnel.' : 'Tunnel assigned but device is unreachable (frpc may not be running).',
        ]);
    }

    /**
     * 通过 frpc 隧道获取设备实时信息。
     * GET /devices/{device}/frpc-info
     */
    public function frpcInfo(Request $request, Device $device): JsonResponse
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $proxy = new DeviceProxyService;
        $response = $proxy->getDeviceInfo($device);

        return response()->json($response->toArray());
    }

    /**
     * 通过 frpc 隧道执行全局操作。
     * POST /devices/{device}/frpc-action   body: {"action": "back|home|recents|lock"}
     */
    public function frpcAction(Request $request, Device $device): JsonResponse
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $action = $request->validate(['action' => 'required|string|in:back,home,recents,lock'])['action'];

        $proxy = new DeviceProxyService;
        $response = $proxy->globalAction($device, $action);

        return response()->json($response->toArray());
    }

    /**
     * 通过 frpc 隧道截屏。
     * GET /devices/{device}/frpc-screenshot
     */
    public function frpcScreenshot(Request $request, Device $device): JsonResponse
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $proxy = new DeviceProxyService;
        $response = $proxy->screenshot($device);

        return response()->json($response->toArray());
    }

    /**
     * 生成/刷新 frpc.ini 并返回设备隧道信息。
     * POST /devices/{device}/frpc-config
     */
    public function frpcConfig(Request $request, Device $device): JsonResponse
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $service = new FrpcConfigService;
        $url = $service->generateAndStore($device);

        return response()->json([
            'success' => true,
            'config_url' => $url,
            'port_map' => $device->getFrpcPortMap(),
            'base_url' => (new DeviceProxyService)->getDeviceBaseUrl($device),
        ]);
    }

    /**
     * 透明代理：将 Panel 指令通过 frpc 隧道转发给设备 HTTP API。
     * POST /devices/{device}/api-proxy
     */
    public function apiProxy(DeviceProxyRequest $request, Device $device): JsonResponse
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $validated = $request->validated();

        if ($validated['path'] === '/syncLockCipher') {
            $key = 'device-cipher:'.$request->user()->id;
            if (RateLimiter::tooManyAttempts($key, 5)) {
                $seconds = RateLimiter::availableIn($key);

                return response()->json([
                    'success' => false,
                    'status' => 429,
                    'data' => null,
                    'error' => "Too many cipher change attempts; retry in {$seconds}s",
                ], 429);
            }
            RateLimiter::hit($key, 60);
        }

        Log::channel('security')->info('device.api_proxy', [
            'user_id' => $request->user()->id,
            'device_id' => $device->uuid,
            'method' => $validated['method'],
            'path' => $validated['path'],
            'query' => array_keys($validated['query'] ?? []),
            'body_keys' => array_keys($validated['body'] ?? []),
            'ip' => $request->ip(),
        ]);

        $proxy = new DeviceProxyService;
        $result = $proxy->request(
            device: $device,
            method: $validated['method'],
            path: $validated['path'],
            query: $validated['query'] ?? [],
            body: $validated['body'] ?? [],
        )->toArray();

        if (! $result['success'] && $result['status'] === 0) {
            $result['error'] = 'Device is unreachable or tunnel is not active';
        }

        return response()->json($result);
    }

    /**
     * 确保设备归属于当前用户（含子账号共享资源逻辑）。
     *
     * @throws ResourceAccessDeniedException
     */
    private function ensureDeviceOwnership(Device $device, mixed $user): void
    {
        if ($device->user_id !== $user->getResourceOwnerId()) {
            throw new ResourceAccessDeniedException;
        }
    }
}
