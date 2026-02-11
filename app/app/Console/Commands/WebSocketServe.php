<?php

declare(strict_types=1);

namespace App\Console\Commands;

use App\WebSocket\Server;
use Illuminate\Console\Command;

final class WebSocketServe extends Command
{
    protected $signature = 'websocket:serve
                            {--host= : The host to bind to}
                            {--port= : The port to listen on}';

    protected $description = 'Start the WebSocket server for device communication';

    public function handle(): int
    {
        $host = $this->option('host') ?? config('websocket.host', '0.0.0.0');
        $port = $this->option('port') ?? config('websocket.port', 8081);

        config(['websocket.host' => $host]);
        config(['websocket.port' => (int) $port]);

        $this->info("Starting WebSocket server on {$host}:{$port}");
        $this->info('Press Ctrl+C to stop the server');

        $server = new Server;
        $server->start();

        return self::SUCCESS;
    }
}
