<?php

namespace App\Http\Controllers;

use App\Exceptions\ResourceAccessDeniedException;
use App\Http\Requests\Device\UpdateDeviceRequest;
use App\Models\Device;
use Illuminate\Http\Request;
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

        $devices = Device::where('is_removed', false)
            ->where('user_id', $ownerId)
            ->orderByDesc('last_seen_at')
            ->paginate(50)
            ->through(fn(Device $device) => [
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
        ]);
    }

    public function show(Request $request, Device $device): Response
    {
        $user = $request->user();
        $this->ensureDeviceOwnership($device, $user);

        return Inertia::render('Devices/Show', [
            'device' => $device,
            'usercheck' => md5($user->email . config('app.key')),
        ]);
    }

    public function control(Request $request, Device $device): Response
    {
        $user = $request->user();
        $this->ensureDeviceOwnership($device, $user);

        return Inertia::render('Devices/Control', [
            'device' => $device,
            'usercheck' => md5($user->email . config('app.key')),
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

    /**
     * 确保设备归属于当前用户（含子账号共享资源逻辑）。
     *
     * @throws ResourceAccessDeniedException
     */
    private function ensureDeviceOwnership(Device $device, mixed $user): void
    {
        if ($device->user_id !== $user->getResourceOwnerId()) {
            throw new ResourceAccessDeniedException();
        }
    }
}
