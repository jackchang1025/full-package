<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\AgentQueryRequest;
use App\Models\Device;
use App\Services\FrpcConfigService;
use Illuminate\Http\JsonResponse;

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
}
