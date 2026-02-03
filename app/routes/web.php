<?php

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

Route::middleware(['auth'])->group(function () {
    Route::get('/home', [DashboardController::class, 'index'])->name('dashboard');
    Route::get('/dashboard', [DashboardController::class, 'index']);

    Route::resource('devices', DeviceController::class)->only(['index', 'show', 'update', 'destroy']);
    Route::get('/devices/{device}/control', [DeviceController::class, 'control'])->name('devices.control');

    Route::get('/builds/stream', [AppBuildController::class, 'stream'])->name('builds.stream');
    Route::resource('builds', AppBuildController::class)->only(['index', 'create', 'store', 'show', 'destroy']);

    Route::prefix('builds/assets')->group(function () {
        Route::get('/icons', [BuildAssetController::class, 'icons'])->name('builds.assets.icons');
        Route::post('/icons', [BuildAssetController::class, 'uploadIcon'])->name('builds.assets.icons.upload');
        Route::delete('/icons', [BuildAssetController::class, 'deleteIcon'])->name('builds.assets.icons.delete');
        Route::get('/backgrounds', [BuildAssetController::class, 'backgrounds'])->name('builds.assets.backgrounds');
        Route::post('/backgrounds', [BuildAssetController::class, 'uploadBackground'])->name('builds.assets.backgrounds.upload');
        Route::delete('/backgrounds', [BuildAssetController::class, 'deleteBackground'])->name('builds.assets.backgrounds.delete');
    });

    Route::get('/settings/profile', fn() => Inertia::render('Settings/Profile'))->name('settings.profile');
});
