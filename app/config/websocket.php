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
        'max_connection' => (int) env('WEBSOCKET_MAX_CONNECTIONS', 10000),
        'daemonize' => env('WEBSOCKET_DAEMONIZE', false),
        // Swoole 原生日志统一放到 websocket 目录，与 Laravel 日志在同一位置
        'log_file' => storage_path('logs/websocket/swoole.log'),
        'log_level' => (int) env('SWOOLE_LOG_LEVEL', 4), // 0=DEBUG, 1=TRACE, 2=INFO, 3=NOTICE, 4=WARNING, 5=ERROR
        'heartbeat_check_interval' => 25, // Check every 25 seconds
        'heartbeat_idle_time' => 75, // Close if no activity for 75 seconds
        'package_max_length' => 10 * 1024 * 1024, // 10MB max message size
        'buffer_output_size' => 32 * 1024 * 1024, // 32MB output buffer
    ],

    // Heartbeat configuration
    'heartbeat' => [
        'timeout' => 75, // Seconds before considering device offline
        'check_interval' => 25, // Seconds between heartbeat checks
        'probe_interval' => 10, // Seconds between probe attempts
        'max_probes' => 3, // Maximum probe attempts before disconnect
    ],

    // Encryption settings (must match legacy system)
    'encryption' => [
        'key' => env('WEBSOCKET_ENCRYPTION_KEY', '@zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR'),
        'iv' => env('WEBSOCKET_ENCRYPTION_IV', 'G8v!h3*Y.P+pFm/;'),
        'method' => 'AES-256-CBC',
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
