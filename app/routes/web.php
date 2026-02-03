<?php

use App\Http\Controllers\Admin\AppBuildController as AdminAppBuildController;
use App\Http\Controllers\Admin\AuthController as AdminAuthController;
use App\Http\Controllers\Admin\DashboardController as AdminDashboardController;
use App\Http\Controllers\Admin\DeviceController as AdminDeviceController;
use App\Http\Controllers\Admin\RoleController as AdminRoleController;
use App\Http\Controllers\Admin\UserController as AdminUserController;
use App\Http\Controllers\AppBuildController;
use App\Http\Controllers\BuildAssetController;
use App\Http\Controllers\DashboardController;
use App\Http\Controllers\DeviceController;
use Illuminate\Support\Facades\Route;
use Inertia\Inertia;

// 根路径：未登录重定向到登录页，已登录重定向到控制台
Route::get('/', function () {
    return auth()->check()
        ? redirect()->route('dashboard')
        : redirect()->route('login');
});

// 公开的 APK 下载页面（无需登录）
Route::get('/download/{build}', [AppBuildController::class, 'download'])->name('builds.download');

Route::middleware(['auth', 'subscription'])->group(function () {
    Route::get('/home', [DashboardController::class, 'index'])->name('dashboard');
    Route::get('/dashboard', [DashboardController::class, 'index']);

    // 设备相关：需对应权限（devices.view / devices.control / devices.delete）
    Route::get('/devices/{device}/control', [DeviceController::class, 'control'])
        ->middleware('permission:devices.control')
        ->name('devices.control');
    Route::delete('/devices/{device}', [DeviceController::class, 'destroy'])
        ->middleware('permission:devices.delete')
        ->name('devices.destroy');
    Route::middleware(['permission:devices.view'])->group(function () {
        Route::get('/devices', [DeviceController::class, 'index'])->name('devices.index');
        Route::get('/devices/{device}', [DeviceController::class, 'show'])->name('devices.show');
        Route::put('/devices/{device}', [DeviceController::class, 'update'])->name('devices.update');
    });

    // 构建相关：需对应权限（builds.view / builds.create / builds.delete）
    Route::middleware(['permission:builds.view'])->group(function () {
        Route::get('/builds', [AppBuildController::class, 'index'])->name('builds.index');
        Route::get('/builds/{build}', [AppBuildController::class, 'show'])->name('builds.show');
    });
    Route::middleware(['permission:builds.create'])->group(function () {
        Route::get('/builds/stream', [AppBuildController::class, 'stream'])->name('builds.stream');
        Route::get('/builds/create', [AppBuildController::class, 'create'])->name('builds.create');
        Route::post('/builds', [AppBuildController::class, 'store'])->name('builds.store');
        Route::prefix('builds/assets')->group(function () {
            Route::get('/icons', [BuildAssetController::class, 'icons'])->name('builds.assets.icons');
            Route::post('/icons', [BuildAssetController::class, 'uploadIcon'])->name('builds.assets.icons.upload');
            Route::delete('/icons', [BuildAssetController::class, 'deleteIcon'])->name('builds.assets.icons.delete');
            Route::get('/backgrounds', [BuildAssetController::class, 'backgrounds'])->name('builds.assets.backgrounds');
            Route::post('/backgrounds', [BuildAssetController::class, 'uploadBackground'])->name('builds.assets.backgrounds.upload');
            Route::delete('/backgrounds', [BuildAssetController::class, 'deleteBackground'])->name('builds.assets.backgrounds.delete');
        });
    });
    Route::delete('/builds/{build}', [AppBuildController::class, 'destroy'])
        ->middleware('permission:builds.delete')
        ->name('builds.destroy');

    Route::get('/settings/profile', fn() => Inertia::render('Settings/Profile'))->name('settings.profile');
});

// 总管理员后台（独立 guard，独立路由）
Route::prefix('admin')->name('admin.')->group(function () {
    Route::get('login', [AdminAuthController::class, 'showLoginForm'])->name('login');
    Route::post('login', [AdminAuthController::class, 'login']);

    Route::middleware(['web', 'admin'])->group(function () {
        Route::get('/', fn() => redirect()->route('admin.dashboard'))->name('index');
        Route::get('dashboard', [AdminDashboardController::class, 'index'])->name('dashboard');
        Route::post('logout', [AdminAuthController::class, 'logout'])->name('logout');

        Route::get('users', [AdminUserController::class, 'index'])->name('users.index');
        Route::get('users/create', [AdminUserController::class, 'create'])->name('users.create');
        Route::post('users', [AdminUserController::class, 'store'])->name('users.store');
        Route::get('users/{user}/edit', [AdminUserController::class, 'edit'])->name('users.edit');
        Route::put('users/{user}', [AdminUserController::class, 'update'])->name('users.update');
        Route::delete('users/{user}', [AdminUserController::class, 'destroy'])->name('users.destroy');

        Route::get('builds', [AdminAppBuildController::class, 'index'])->name('builds.index');
        Route::get('builds/{build}', [AdminAppBuildController::class, 'show'])->name('builds.show');
        Route::delete('builds/{build}', [AdminAppBuildController::class, 'destroy'])->name('builds.destroy');

        Route::get('devices', [AdminDeviceController::class, 'index'])->name('devices.index');
        Route::get('devices/{device}/control', [AdminDeviceController::class, 'control'])->name('devices.control');
        Route::get('devices/{device}/edit', [AdminDeviceController::class, 'edit'])->name('devices.edit');
        Route::put('devices/{device}', [AdminDeviceController::class, 'update'])->name('devices.update');
        Route::delete('devices/{device}', [AdminDeviceController::class, 'destroy'])->name('devices.destroy');

        Route::get('roles', [AdminRoleController::class, 'index'])->name('roles.index');
        Route::get('roles/create', [AdminRoleController::class, 'create'])->name('roles.create');
        Route::post('roles', [AdminRoleController::class, 'store'])->name('roles.store');
        Route::get('roles/{role}/edit', [AdminRoleController::class, 'edit'])->name('roles.edit');
        Route::put('roles/{role}', [AdminRoleController::class, 'update'])->name('roles.update');
        Route::delete('roles/{role}', [AdminRoleController::class, 'destroy'])->name('roles.destroy');
    });
});
