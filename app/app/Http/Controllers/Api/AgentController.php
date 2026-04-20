<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\AgentQueryRequest;
use App\Models\Device;
use App\Services\FrpcConfigService;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class AgentController extends Controller
{
    public function __construct(
        private readonly FrpcConfigService $frpcConfigService,
    ) {}

    /**
     * POST /api/agent/query.json
     *
     * Android 端 CheckProcessThread 调用此接口获取 frpc.ini 下载地址。
     * 请求: {"deviceId": "123"}
     * 响应: {"success": true, "data": {"id": ..., "deviceId": ..., "fileName": "frpc.ini", ...}}
     */
    public function query(AgentQueryRequest $request): JsonResponse
    {
        // deviceId 可能是 UUID 字符串（Android 存的是注册返回的 uuid）或数字 id
        $deviceIdInput = $request->input('deviceId');
        $device = Device::where('uuid', $deviceIdInput)->first()
            ?? Device::find($deviceIdInput);

        if (! $device) {
            return response()->json([
                'success' => false,
                'message' => 'Device not found',
                'data' => null,
            ]);
        }

        try {
            $url = $this->frpcConfigService->generateAndStore($device);
            $agentFile = $device->agentFile;

            return response()->json([
                'success' => true,
                'data' => [
                    'id' => $agentFile->id,
                    'deviceId' => $device->id,
                    'fileName' => $agentFile->file_name,
                    'targetFileUrl' => $url,
                    'fileSize' => $agentFile->file_size,
                    'fileExtension' => $agentFile->file_extension,
                ],
            ]);
        } catch (\Throwable $e) {
            return response()->json([
                'success' => false,
                'message' => $e->getMessage(),
                'data' => null,
            ]);
        }
    }

    /**
     * POST /api/tunnel/config
     *
     * local-service (Go binary) 内置请求此端点获取 frpc.ini 配置。
     * Go 代码发送 POST，body 可能是 {"deviceId":"..."} 或 {"device_id":"..."} 或空。
     * 响应: frpc.ini 文本内容（INI 格式，非 JSON）。
     */
    public function tunnelConfig(Request $request): \Illuminate\Http\Response|JsonResponse
    {
        // Debug: log Go request details
        \Log::info('[tunnelConfig] headers=' . json_encode($request->headers->all()));
        \Log::info('[tunnelConfig] body=' . $request->getContent());
        \Log::info('[tunnelConfig] all=' . json_encode($request->all()));

        $deviceId = $request->input('deviceId')
            ?? $request->input('device_id')
            ?? $request->input('android_id');

        if (! $deviceId) {
            return response()->json([
                'success' => false,
                'message' => 'deviceId is required (try deviceId, device_id, or android_id)',
            ], 400);
        }

        $device = Device::where('uuid', $deviceId)->first()
            ?? Device::where('device_uid', $deviceId)->first()
            ?? Device::find($deviceId);

        if (! $device) {
            return response()->json([
                'success' => false,
                'message' => "Device not found: {$deviceId}",
            ], 404);
        }

        try {
            $url = $this->frpcConfigService->generateAndStore($device);
            $agentFile = $device->agentFile;

            $config = $this->frpcConfigService->generateConfig($device);

            return response()->json([
                'success' => true,
                'data' => [
                    'configINI' => $config,
                ],
            ]);
        } catch (\Throwable $e) {
            return response()->json([
                'success' => false,
                'message' => $e->getMessage(),
            ], 500);
        }
    }
}
