<?php

namespace App\Http\Controllers;

use App\Models\AppBuild;
use App\Models\Device;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Inertia\Response;

class DashboardController extends Controller
{
    public function index(Request $request): Response
    {
        $user = $request->user();
        $ownerId = $user->getResourceOwnerId();

        $stats = [
            'totalDevices' => Device::where('user_id', $ownerId)->where('is_removed', false)->count(),
            'onlineDevices' => Device::where('user_id', $ownerId)->where('is_online', true)->where('is_removed', false)->count(),
            'totalBuilds' => AppBuild::where('user_id', $ownerId)->count(),
            'todayInstalled' => Device::where('user_id', $ownerId)
                ->where('is_removed', false)
                ->whereDate('installed_at', today())
                ->count(),
            'monthInstalled' => Device::where('user_id', $ownerId)
                ->where('is_removed', false)
                ->whereMonth('installed_at', now()->month)
                ->whereYear('installed_at', now()->year)
                ->count(),
        ];

        return Inertia::render('Dashboard/Index', [
            'stats' => $stats,
        ]);
    }
}
