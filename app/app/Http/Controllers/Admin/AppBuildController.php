<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\AppBuild;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Inertia\Response;

class AppBuildController extends Controller
{
    public function index(Request $request): Response
    {
        $query = AppBuild::query()->with(['user', 'template']);

        if ($request->filled('search')) {
            $search = $request->input('search');
            $query->where(function ($q) use ($search) {
                $q->where('name', 'like', "%{$search}%")
                    ->orWhere('package_name', 'like', "%{$search}%")
                    ->orWhereHas('user', fn($q) => $q->where('email', 'like', "%{$search}%"));
            });
        }

        $builds = $query->orderByDesc('created_at')->paginate(20)->through(function (AppBuild $build) {
            $build->append(['download_url', 'icon_url', 'share_url']);
            return [
                'id' => $build->id,
                'name' => $build->name,
                'package_name' => $build->package_name,
                'version' => $build->version,
                'is_custom' => $build->is_custom,
                'created_at' => $build->created_at?->toISOString(),
                'template' => $build->template ? ['id' => $build->template->id, 'name' => $build->template->name, 'package_name' => $build->template->package_name ?? ''] : null,
                'user' => $build->user ? ['id' => $build->user->id, 'username' => $build->user->username, 'email' => $build->user->email] : null,
                'download_url' => $build->download_url,
                'icon_url' => $build->icon_url,
                'share_url' => $build->share_url,
            ];
        });

        return Inertia::render('Admin/Builds/Index', [
            'builds' => $builds,
            'filters' => ['search' => $request->input('search', '')],
        ]);
    }

    public function show(AppBuild $build): Response
    {
        $build->load(['user', 'template']);
        $build->append(['download_url', 'build_duration', 'icon_url', 'background_url']);

        return Inertia::render('Admin/Builds/Show', [
            'build' => $build,
            'backUrl' => route('admin.builds.index'),
        ]);
    }

    public function destroy(Request $request, AppBuild $build)
    {
        $build->delete();

        if ($request->header('X-Inertia')) {
            return back();
        }

        return redirect()->route('admin.builds.index')->with('success', 'APK 构建已删除');
    }
}
