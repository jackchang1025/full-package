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
        $this->authorize('devices.view');
        $user = $request->user();

        $devices = Device::where('is_removed', false)
            ->where('user_id', $user->id)
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

        $statsQuery = Device::where('is_removed', false)->where('user_id', $user->id);
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
            'canControl' => $user->can('devices.control'),
        ]);
    }

    public function show(Request $request, Device $device): Response
    {
        $this->authorize('devices.view');
        abort_if($device->user_id !== $request->user()->id, 403);
        $user = $request->user();

        return Inertia::render('Devices/Show', [
            'device' => $device,
            'usercheck' => md5($user->email . config('app.key')),
        ]);
    }

    public function control(Request $request, Device $device): Response
    {
        $this->authorize('devices.control');
        abort_if($device->user_id !== $request->user()->id, 403);
        $user = $request->user();

        return Inertia::render('Devices/Control', [
            'device' => $device,
            'usercheck' => md5($user->email . config('app.key')),
            'backUrl' => route('devices.index'),
        ]);
    }

    public function update(Request $request, Device $device)
    {
        $this->authorize('devices.view');
        abort_if($device->user_id !== $request->user()->id, 403);

        $validated = $request->validate([
            'remark' => ['nullable', 'string', 'max:200'],
        ]);

        $device->update($validated);

        if ($request->header('X-Inertia')) {
            return back();
        }

        return redirect()->route('devices.index')
            ->with('success', '备注已更新');
    }

    public function destroy(Request $request, Device $device)
    {
        $this->authorize('devices.delete');
        abort_if($device->user_id !== $request->user()->id, 403);

        $device->update(['is_removed' => true]);

        return redirect()->route('devices.index')
            ->with('success', '设备已移除');
    }
}
