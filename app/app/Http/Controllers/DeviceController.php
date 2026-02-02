<?php

namespace App\Http\Controllers;

use App\Models\Device;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Inertia\Response;

class DeviceController extends Controller
{
    public function index(Request $request): Response
    {
        $user = $request->user();
        $isAdmin = $user->isAdmin();

        // 构建查询
        $query = Device::where('is_removed', false);
        if (!$isAdmin) {
            $query->where('user_id', $user->id);
        }

        // 分页获取设备列表
        $devices = $query
            ->orderByDesc('last_seen_at')
            ->paginate(50)
            ->through(fn(Device $device) => [
                'id' => $device->id,
                'uuid' => $device->uuid,
                'name' => $device->name ?? '',
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

        // 统计数据
        $statsQuery = Device::where('is_removed', false);
        if (!$isAdmin) {
            $statsQuery->where('user_id', $user->id);
        }

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
        ]);
    }

    public function show(Request $request, Device $device): Response
    {
        $user = $request->user();
        abort_if($device->user_id !== $user->id && !$user->isAdmin(), 403);

        return Inertia::render('Devices/Show', [
            'device' => $device,
            'usercheck' => md5($user->email . config('app.key')),
        ]);
    }

    public function control(Request $request, Device $device): Response
    {
        $user = $request->user();
        abort_if($device->user_id !== $user->id && !$user->isAdmin(), 403);

        return Inertia::render('Devices/Control', [
            'device' => $device,
            'usercheck' => md5($user->email . config('app.key')),
        ]);
    }

    public function destroy(Request $request, Device $device)
    {
        abort_if($device->user_id !== $request->user()->id, 403);

        $device->update(['is_removed' => true]);

        return redirect()->route('devices.index')
            ->with('success', '设备已移除');
    }
}
