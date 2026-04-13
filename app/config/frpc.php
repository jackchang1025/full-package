<?php

declare(strict_types=1);

return [
    /*
    |--------------------------------------------------------------------------
    | frps 服务器配置
    |--------------------------------------------------------------------------
    | server_addr: 写入 frpc.ini 的地址（设备连接 frps 用，必须是设备可达的 IP）
    | proxy_host:  Laravel 容器内访问 frps 隧道用（Docker 服务名或 IP）
    */
    'server_addr' => env('FRPS_SERVER_ADDR', '127.0.0.1'),
    'proxy_host' => env('FRPS_PROXY_HOST', 'frps'),
    'server_port' => (int) env('FRPS_SERVER_PORT', 7000),
    'auth_token' => env('FRPS_AUTH_TOKEN', ''),

    /*
    |--------------------------------------------------------------------------
    | 端口分配范围
    |--------------------------------------------------------------------------
    | 每台设备分配 3 个连续端口:
    |   port+0 → HTTP API (local 7910)
    |   port+1 → WebSocket (local 7900)
    |   port+2 → WiFi Debug (local dynamic)
    */
    'port_range_start' => (int) env('FRPC_PORT_RANGE_START', 20000),
    'port_range_end' => (int) env('FRPC_PORT_RANGE_END', 30000),
];
