<?php

declare(strict_types=1);

namespace App\Services;

use App\Models\Device;
use Illuminate\Support\Facades\Storage;

class FrpcConfigService
{
    /**
     * 为设备分配 frpc 端口（3 个连续端口）。
     * 如果设备已有端口分配，直接返回。
     */
    public function allocatePort(Device $device): int
    {
        if ($device->frpc_base_port) {
            return $device->frpc_base_port;
        }

        $rangeStart = (int) config('frpc.port_range_start', 20000);
        $rangeEnd = (int) config('frpc.port_range_end', 30000);

        // 查询已分配的端口，找到第一个空闲的 3-port 段
        $occupiedPorts = Device::whereNotNull('frpc_base_port')
            ->pluck('frpc_base_port')
            ->sort()
            ->values()
            ->toArray();

        $candidate = $rangeStart;
        foreach ($occupiedPorts as $occupied) {
            if ($candidate + 2 < $occupied) {
                break;
            }
            $candidate = $occupied + 3;
        }

        if ($candidate + 2 > $rangeEnd) {
            throw new \RuntimeException("No available frpc ports in range {$rangeStart}-{$rangeEnd}");
        }

        $device->update([
            'frpc_base_port' => $candidate,
        ]);

        return $candidate;
    }

    /**
     * 为设备生成 frpc.ini 配置内容。
     * 使用 INI 格式（兼容旧版 frpc，非 TOML）。
     */
    public function generateConfig(Device $device): string
    {
        $basePort = $device->frpc_base_port;
        if (! $basePort) {
            $basePort = $this->allocatePort($device);
        }

        $serverAddr = config('frpc.server_addr');
        $serverPort = config('frpc.server_port');
        $authToken = config('frpc.auth_token');
        $deviceId = $device->id;
        $wsPort = $basePort + 1;
        $debugPort = $basePort + 2;

        return <<<INI
[common]
server_addr = {$serverAddr}
server_port = {$serverPort}
token = {$authToken}
admin_addr = 127.0.0.1
admin_port = 7400
log_level = warn

[http-api-{$deviceId}]
type = tcp
local_ip = 127.0.0.1
local_port = 7910
remote_port = {$basePort}

[websocket-{$deviceId}]
type = tcp
local_ip = 127.0.0.1
local_port = 7900
remote_port = {$wsPort}

[wifi-debug-port]
type = tcp
local_ip = 127.0.0.1
local_port = 5555
remote_port = {$debugPort}
INI;
    }

    /**
     * 生成 frpc.ini 配置并保存为文件，返回可下载的 URL。
     */
    public function generateAndStore(Device $device): string
    {
        $content = $this->generateConfig($device);
        $fileName = "frpc_{$device->uuid}.ini";
        $storagePath = "agent-files/{$fileName}";

        Storage::disk('public')->put($storagePath, $content);

        $device->update([
            'frpc_config_generated_at' => now(),
        ]);

        // 更新或创建 agent file 记录
        $device->agentFile()->updateOrCreate(
            ['file_name' => 'frpc.ini'],
            [
                'target_file_url' => Storage::disk('public')->url($storagePath),
                'file_size' => strlen($content),
                'file_extension' => 'ini',
            ]
        );

        return $device->agentFile->target_file_url;
    }
}
