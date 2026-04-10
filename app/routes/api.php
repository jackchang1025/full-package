<?php

declare(strict_types=1);

use App\Http\Controllers\Api\AgentController;
use App\Http\Controllers\Api\DeviceApiController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
| Android 设备端调用的 API 接口。无需 web session 认证。
*/

Route::post('/agent/query.json', [AgentController::class, 'query']);

Route::prefix('device')->middleware('auth.device')->group(function (): void {
    Route::post('/register.json', [DeviceApiController::class, 'register']);
    Route::post('/updateDeviceInfo.json', [DeviceApiController::class, 'updateInfo']);
});
