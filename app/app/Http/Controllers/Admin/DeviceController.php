<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Requests\Device\UpdateDeviceRequest;
use App\Models\Device;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Inertia\Response;

class DeviceController extends Controller
{
    public function index(Request $request): Response
    {
        $query = Device::query()->with('user')->where('is_removed', false);

        if ($request->filled('search')) {
            $search = $request->input('search');
            $query->where(function ($q) use ($search) {
                $q->where('uuid', 'like', "%{$search}%")
                    ->orWhere('name', 'like', "%{$search}%")
                    ->orWhere('remark', 'like', "%{$search}%")
                    ->orWhereHas('user', fn($q) => $q->where('email', 'like', "%{$search}%")->orWhere('username', 'like', "%{$search}%"));
            });
        }

        $devices = $query->orderByDesc('last_seen_at')->paginate(50)->through(fn(Device $device) => [
            'id' => $device->id,
            'uuid' => $device->uuid,
            'name' => $device->name ?? '',
            'remark' => $device->remark ?? '',
            'model' => $device->model ?? '',
            'android_version' => $device->android_version ?? '',
            'country' => $device->country ?? '',
            'ip_address' => $device->ip_address ?? null,
            'ip_location' => $device->ip_location ?? null,
            'network_type' => $device->network_type ?? null,
            'battery_level' => $device->battery_level,
            'is_online' => $device->is_online,
            'has_accessibility' => $device->has_accessibility,
            'last_seen_at' => $device->last_seen_at?->toISOString(),
            'installed_at' => $device->installed_at?->toISOString(),
            'user' => $device->user ? ['id' => $device->user->id, 'username' => $device->user->username, 'email' => $device->user->email] : null,
        ]);

        $statsQuery = Device::where('is_removed', false);
        $total = (clone $statsQuery)->count();
        $online = (clone $statsQuery)->where('is_online', true)->count();
        $stats = [
            'total' => $total,
            'online' => $online,
            'offline' => $total - $online,
        ];

        return Inertia::render('Admin/Devices/Index', [
            'devices' => $devices,
            'stats' => $stats,
            'filters' => ['search' => $request->input('search', '')],
            'canControl' => true,
        ]);
    }

    public function edit(Device $device): Response
    {
        $device->load('user');

        return Inertia::render('Admin/Devices/Edit', [
            'device' => [
                'uuid' => $device->uuid,
                'name' => $device->name ?? '',
                'remark' => $device->remark ?? '',
                'user' => $device->user ? ['id' => $device->user->id, 'username' => $device->user->username, 'email' => $device->user->email] : null,
            ],
        ]);
    }

    public function update(UpdateDeviceRequest $request, Device $device)
    {
        $device->update($request->validated());

        if ($request->header('X-Inertia')) {
            return back();
        }

        return redirect()->route('admin.devices.index')->with('success', '设备已更新');
    }

    public function control(Device $device): Response
    {
        $admin = auth('admin')->user();

        return Inertia::render('Devices/Control', [
            'device' => $device,
            'usercheck' => md5($admin->email . config('app.key')),
            'backUrl' => route('admin.devices.index'),
        ]);
    }

    public function destroy(Device $device)
    {
        $device->update(['is_removed' => true]);

        if (request()->header('X-Inertia')) {
            return back();
        }

        return redirect()->route('admin.devices.index')->with('success', '设备已移除');
    }
}
