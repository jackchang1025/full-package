<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Models\Device;
use App\Services\DeviceProxyService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class DeviceCommandController
{
    public function __construct(
        private readonly DeviceProxyService $proxy,
    ) {}

    public function adbStatus(Device $device): JsonResponse
    {
        if (! $this->proxy->hasTunnel($device)) {
            return response()->json([
                'success' => false,
                'error' => '设备未配置 frpc 隧道',
            ], 422);
        }

        $response = $this->proxy->get($device, '/adbStatus');

        return response()->json($response->toArray(), $response->ok() ? 200 : 502);
    }

    public function permissions(Device $device): JsonResponse
    {
        if (! $this->proxy->hasTunnel($device)) {
            return response()->json([
                'success' => false,
                'error' => '设备未配置 frpc 隧道',
            ], 422);
        }

        $response = $this->proxy->get($device, '/permissions');

        return response()->json($response->toArray(), $response->ok() ? 200 : 502);
    }

    public function sendAdbCommand(Request $request, Device $device): JsonResponse
    {
        $validated = $request->validate([
            'command' => 'required|string|in:START_PAIRING,AUTO_WIRELESS_PAIRING,DIRECT_PAIR,FULL_DEPLOY,DEPLOY_LOCAL_SERVICE,OPEN_WIFI_DEBUG_SETTINGS,OPEN_ABOUT_PHONE',
            'params' => 'nullable|array',
        ]);

        if (! $this->proxy->hasTunnel($device)) {
            return response()->json([
                'success' => false,
                'error' => '设备未配置 frpc 隧道',
            ], 422);
        }

        $response = $this->proxy->post($device, '/dispatch', [
            'command' => $validated['command'],
            'params' => $validated['params'] ?? new \stdClass(),
        ], timeout: 15);

        return response()->json($response->toArray(), $response->ok() ? 200 : 502);
    }
}
