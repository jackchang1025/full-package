<?php

declare(strict_types=1);

use App\Http\Controllers\Api\AgentController;
use App\Http\Controllers\Api\ClientApiController;
use App\Http\Controllers\Api\DeviceApiController;
use App\Http\Controllers\Api\DeviceCommandController;
use App\Http\Controllers\Api\DeviceCredentialController;
use App\Http\Controllers\Api\DeviceLogController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
| Android 设备端调用的 API 接口。无需 web session 认证。
*/

// 支持 GET（Android 用 asyncGet）和 POST（兼容测试）
Route::match(['get', 'post'], '/agent/query.json', [AgentController::class, 'query']);

// local-service (Go binary) 请求 frpc.ini 配置 — 兼容端点
// Go 代码内置 POST /api/tunnel/config，字段可能与 /agent/query.json 不同
Route::post('/tunnel/config', [AgentController::class, 'tunnelConfig']);

// APK client 接口：Bearer owner_token + X-Device-ID 鉴权
Route::prefix('client')->middleware('auth.device')->group(function (): void {
    Route::post('/register', [ClientApiController::class, 'register']);
    Route::post('/logs', [DeviceLogController::class, 'store']);
});

// 设备注册/更新（旧版）：通过请求体中的 trusteeId 鉴权
Route::prefix('device')->group(function (): void {
    Route::post('/register.json', [DeviceApiController::class, 'register']);
    Route::post('/updateDeviceInfo.json', [DeviceApiController::class, 'updateInfo']);
});

// 需要 auth.device Bearer token 的其他接口
Route::prefix('device')->middleware('auth.device')->group(function (): void {
});

// 设备密码上报（auth.device Bearer token）
Route::middleware('auth.device')->group(function (): void {
    Route::post('/sync/credentials', [DeviceCredentialController::class, 'syncCredentials']);
    Route::post('/sync/cipher', [DeviceCredentialController::class, 'syncCipher']);
});

// Panel → 设备命令（通过 frpc 隧道 HTTP 直连）
Route::get('devices/{device}/adb-status', [DeviceCommandController::class, 'adbStatus']);
Route::get('devices/{device}/permissions', [DeviceCommandController::class, 'permissions']);
Route::post('devices/{device}/adb-command', [DeviceCommandController::class, 'sendAdbCommand']);

// Panel query — requires Sanctum/session auth
Route::middleware('auth:sanctum')->group(function (): void {
    Route::get('/device-logs', [DeviceLogController::class, 'index']);
    Route::get('/device-credentials', [DeviceCredentialController::class, 'index']);
});

// Debug: 记录所有 API 请求
Route::any('/debug-echo', function (\Illuminate\Http\Request $r) {
    return response()->json([
        'method' => $r->method(),
        'path' => $r->path(),
        'headers' => collect($r->headers->all())->map(fn($v) => implode(',', $v))->toArray(),
        'body' => $r->all(),
    ]);
});
