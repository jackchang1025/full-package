<?php

declare(strict_types=1);

return [
    /*
    |--------------------------------------------------------------------------
    | WebSocket Server Configuration
    |--------------------------------------------------------------------------
    |
    | Configuration for the Swoole WebSocket server that handles real-time
    | communication between management panels and Android devices.
    |
    */

    // Server binding
    'host' => env('WEBSOCKET_HOST', '0.0.0.0'),
    'port' => (int) env('WEBSOCKET_PORT', 8081),

    // Swoole server settings
    // 注意：worker_num 设为 1 确保所有连接在同一个 Worker 中处理
    // 这样可以直接使用 push() 发送消息，无需跨 Worker IPC
    // 对于大规模部署，需要实现 Redis Pub/Sub 或 Swoole Task Worker 方案
    'settings' => [
        'worker_num' => (int) env('WEBSOCKET_WORKERS', 1),
        'max_connection' => (int) env('WEBSOCKET_MAX_CONNECTIONS', 1024),
        'daemonize' => env('WEBSOCKET_DAEMONIZE', false),
        // Swoole 原生日志统一放到 websocket 目录，与 Laravel 日志在同一位置
        'log_file' => storage_path('logs/websocket/swoole.log'),
        'log_level' => (int) env('SWOOLE_LOG_LEVEL', 4), // 0=DEBUG, 1=TRACE, 2=INFO, 3=NOTICE, 4=WARNING, 5=ERROR
        'heartbeat_check_interval' => (int) env('WEBSOCKET_HEARTBEAT_CHECK_INTERVAL', 25),
        'heartbeat_idle_time' => (int) env('WEBSOCKET_HEARTBEAT_IDLE_TIME', 75),
        'package_max_length' => 10 * 1024 * 1024, // 10MB max message size
        'buffer_output_size' => 32 * 1024 * 1024, // 32MB output buffer
    ],

    // Heartbeat configuration
    'heartbeat' => [
        'timeout' => (int) env('WEBSOCKET_HEARTBEAT_TIMEOUT', 75),
        'check_interval' => (int) env('WEBSOCKET_HEARTBEAT_CHECK_INTERVAL', 25),
        'probe_interval' => (int) env('WEBSOCKET_HEARTBEAT_PROBE_INTERVAL', 10),
        'max_probes' => (int) env('WEBSOCKET_HEARTBEAT_MAX_PROBES', 3),
    ],

    // Encryption settings (must match legacy system)
    'encryption' => [
        'key' => env('WEBSOCKET_ENCRYPTION_KEY', '@zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR'),
        'iv' => env('WEBSOCKET_ENCRYPTION_IV', 'G8v!h3*Y.P+pFm/;'),
        'method' => 'AES-256-CBC',
    ],

    // Device authentication (HMAC token signed during APK build)
    'device_auth' => [
        'secret' => env('DEVICE_AUTH_SECRET', ''),
    ],

    // Panel authentication (HMAC token for WebSocket subscribe)
    'panel_auth' => [
        'secret' => env('PANEL_AUTH_SECRET', env('DEVICE_AUTH_SECRET', '')),
        'ttl' => (int) env('PANEL_AUTH_TTL', 300),
    ],

    // Admin email (encrypted) - can view all devices
    'admin_email_encrypted' => 'GCt/Suj1maxHZ3aCykJufw==',

    // File upload settings
    'upload' => [
        'chunk_size' => 256 * 1024, // 256KB per chunk
        'temp_path' => storage_path('app/websocket/uploads'),
    ],

    // Logging
    'logging' => [
        'enabled' => env('WEBSOCKET_LOGGING', true),
        'channel' => 'websocket',
        'log_connections' => true,
        'log_messages' => env('WEBSOCKET_LOG_MESSAGES', false), // Verbose, disable in production
        'log_path' => storage_path('logs'),
    ],

    // Redis configuration for device status
    'redis' => [
        'connection' => env('WEBSOCKET_REDIS_CONNECTION', 'default'),
        'prefix' => 'ws:',
        'device_status_ttl' => 86400, // 24 hours
    ],

    // Client type identifiers (must match legacy protocol)
    'client_types' => [
        'device' => 'Slr_client',
        'panel' => 'slr_panel',
        'panel_send' => 'slr_panelsend',
    ],
];
