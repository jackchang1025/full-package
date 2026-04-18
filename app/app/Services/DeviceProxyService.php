<?php

declare(strict_types=1);

namespace App\Services;

use App\Models\Device;
use Illuminate\Http\Client\ConnectionException;
use Illuminate\Http\Client\Response;
use Illuminate\Support\Facades\Http;

/**
 * 通过 frpc 隧道向设备 HTTP API 发请求。
 *
 * 设备的 HTTP Server (port 7910) 经 frpc → frps 暴露在:
 *   http://{frps_host}:{device.frpc_base_port}
 *
 * 典型用途:
 *   - 查询设备实时状态 (/version, /info, /containerState)
 *   - 执行截图、全局操作等命令 (/screenshot/0, /global/action)
 *   - WebSocket 不可用时的备用控制通道
 */
class DeviceProxyService
{
    private string $frpsHost;
    private int $defaultTimeout;

    public function __construct()
    {
        // Laravel 容器内用 Docker 服务名 'frps' 访问隧道端口
        $this->frpsHost = config('frpc.proxy_host', 'frps');
        $this->defaultTimeout = 10;
    }

    /**
     * 判断设备是否已有 frpc 隧道（有端口分配）。
     */
    public function hasTunnel(Device $device): bool
    {
        return $device->frpc_base_port !== null;
    }

    /**
     * 获取设备 HTTP API 的隧道基础 URL。
     * 例: http://frps:20000
     */
    public function getDeviceBaseUrl(Device $device): ?string
    {
        if (! $this->hasTunnel($device)) {
            return null;
        }

        $port = (int) $device->frpc_base_port;
        $start = (int) config('frpc.port_range_start', 20000);
        $end = (int) config('frpc.port_range_end', 30000);

        if ($port < $start || $port > $end) {
            \Illuminate\Support\Facades\Log::channel('security')->warning(
                'device.frpc_base_port out of range',
                ['device_id' => $device->uuid, 'port' => $port, 'range' => "$start-$end"],
            );

            return null;
        }

        return "http://{$this->frpsHost}:{$port}";
    }

    /**
     * GET 请求设备 API。
     *
     * @param  array<string, mixed>  $query  查询参数
     */
    public function get(Device $device, string $path, array $query = [], ?int $timeout = null): DeviceApiResponse
    {
        $baseUrl = $this->getDeviceBaseUrl($device);
        if ($baseUrl === null) {
            return $this->hasTunnel($device)
                ? DeviceApiResponse::portOutOfRange((int) $device->frpc_base_port)
                : DeviceApiResponse::noTunnel();
        }

        try {
            $response = Http::timeout($timeout ?? $this->defaultTimeout)
                ->get($baseUrl . $path, $query);

            return DeviceApiResponse::fromHttp($response);
        } catch (ConnectionException $e) {
            return DeviceApiResponse::connectionFailed($e->getMessage());
        }
    }

    /**
     * POST 请求设备 API。
     *
     * @param  array<string, mixed>  $body  请求体
     */
    public function post(Device $device, string $path, array $body = [], ?int $timeout = null): DeviceApiResponse
    {
        $baseUrl = $this->getDeviceBaseUrl($device);
        if ($baseUrl === null) {
            return $this->hasTunnel($device)
                ? DeviceApiResponse::portOutOfRange((int) $device->frpc_base_port)
                : DeviceApiResponse::noTunnel();
        }

        try {
            $response = Http::timeout($timeout ?? $this->defaultTimeout)
                ->post($baseUrl . $path, $body);

            return DeviceApiResponse::fromHttp($response);
        } catch (ConnectionException $e) {
            return DeviceApiResponse::connectionFailed($e->getMessage());
        }
    }

    /**
     * 泛型 HTTP 请求，支持 GET 和 POST。
     *
     * @param  'GET'|'POST'  $method
     */
    public function request(
        Device $device,
        string $method,
        string $path,
        array $query = [],
        array $body = [],
        ?int $timeout = null,
    ): DeviceApiResponse {
        $method = strtoupper($method);
        if ($method !== 'GET' && $method !== 'POST') {
            throw new \InvalidArgumentException("Unsupported HTTP method: {$method}");
        }

        return $method === 'GET'
            ? $this->get($device, $path, $query, $timeout)
            : $this->post($device, $path, $body, $timeout);
    }

    // ─── 常用快捷方法 ────────────────────────────────────────────

    /** 检查设备本地 HTTP Server 是否在线 */
    public function ping(Device $device): bool
    {
        return $this->get($device, '/version', timeout: 3)->ok();
    }

    /** 获取设备基本信息 */
    public function getDeviceInfo(Device $device): DeviceApiResponse
    {
        return $this->get($device, '/info');
    }

    /** 获取无障碍服务状态 */
    public function getContainerState(Device $device): DeviceApiResponse
    {
        return $this->get($device, '/containerState');
    }

    /** 截屏 (0=竖屏) */
    public function screenshot(Device $device, int $rotation = 0): DeviceApiResponse
    {
        return $this->get($device, "/screenshot/{$rotation}", timeout: 15);
    }

    /** 执行全局操作 (back/home/recents/lock) */
    public function globalAction(Device $device, string $action): DeviceApiResponse
    {
        return $this->post($device, '/global/action', ['action' => $action]);
    }

    /** 查询已安装应用列表 */
    public function getInstalledApps(Device $device): DeviceApiResponse
    {
        return $this->get($device, '/getInstalledPackages', timeout: 20);
    }
}
