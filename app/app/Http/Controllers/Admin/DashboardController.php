<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\AppBuild;
use App\Models\Device;
use App\Models\User;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Inertia\Response;

class DashboardController extends Controller
{
    public function index(Request $request): Response
    {
        $stats = [
            'totalUsers' => User::count(),
            'totalDevices' => Device::where('is_removed', false)->count(),
            'totalBuilds' => AppBuild::count(),
            'todayInstalled' => Device::where('is_removed', false)
                ->whereDate('installed_at', today())
                ->count(),
            'monthInstalled' => Device::where('is_removed', false)
                ->whereMonth('installed_at', now()->month)
                ->whereYear('installed_at', now()->year)
                ->count(),
        ];

        return Inertia::render('Admin/Dashboard', [
            'stats' => $stats,
        ]);
    }
}
