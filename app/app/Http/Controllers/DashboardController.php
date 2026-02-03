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

        $stats = [
            'totalDevices' => Device::where('user_id', $user->id)->where('is_removed', false)->count(),
            'onlineDevices' => Device::where('user_id', $user->id)->where('is_online', true)->where('is_removed', false)->count(),
            'totalBuilds' => AppBuild::where('user_id', $user->id)->count(),
            'completedBuilds' => AppBuild::where('user_id', $user->id)->count(),
        ];

        return Inertia::render('Dashboard/Index', [
            'stats' => $stats,
        ]);
    }
}
