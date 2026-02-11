<?php

declare(strict_types=1);

namespace Tests\Support;

use Swoole\Coroutine\Http\Client;

class WebSocketTestClient
{
    private ?Client $client = null;

    private string $host;

    private int $port;

    private bool $connected = false;

    private array $receivedMessages = [];

    private array $messageHandlers = [];

    public function __construct(string $host = 'localhost', int $port = 8081)
    {
        $this->host = $host;
        $this->port = $port;
    }

    public function connect(float $timeout = 5.0): bool
    {
        $this->client = new Client($this->host, $this->port);
        $this->client->set(['timeout' => $timeout]);

        $upgraded = $this->client->upgrade('/');

        if ($upgraded) {
            $this->connected = true;
        }

        return $this->connected;
    }

    public function send(array $data): bool
    {
        if (! $this->connected || ! $this->client) {
            return false;
        }

        return $this->client->push(json_encode($data));
    }

    public function receive(float $timeout = 5.0): ?array
    {
        if (! $this->connected || ! $this->client) {
            return null;
        }

        $frame = $this->client->recv($timeout);

        if ($frame === false || $frame === '') {
            return null;
        }

        if (is_object($frame) && property_exists($frame, 'data')) {
            $data = json_decode($frame->data, true);
        } else {
            $data = json_decode((string) $frame, true);
        }

        if ($data) {
            $this->receivedMessages[] = $data;
        }

        return $data;
    }

    public function waitForMessage(string $type, float $timeout = 5.0): ?array
    {
        $startTime = microtime(true);

        while ((microtime(true) - $startTime) < $timeout) {
            $message = $this->receive(0.1);

            if ($message && isset($message['type']) && $message['type'] === $type) {
                return $message;
            }
        }

        return null;
    }

    public function waitForPush(string $type, ?string $deviceId = null, float $timeout = 5.0): ?array
    {
        // First check already received messages
        foreach ($this->receivedMessages as $message) {
            if (isset($message['type']) && $message['type'] === $type) {
                if ($deviceId === null || (isset($message['pid']) && $message['pid'] === $deviceId)) {
                    return $message;
                }
            }
        }

        // Then wait for new messages
        $startTime = microtime(true);

        while ((microtime(true) - $startTime) < $timeout) {
            $message = $this->receive(0.1);

            if ($message && isset($message['type']) && $message['type'] === $type) {
                if ($deviceId === null || (isset($message['pid']) && $message['pid'] === $deviceId)) {
                    return $message;
                }
            }
        }

        return null;
    }

    public function disconnect(): void
    {
        if ($this->client) {
            $this->client->close();
            $this->client = null;
        }
        $this->connected = false;
    }

    public function isConnected(): bool
    {
        return $this->connected;
    }

    public function getReceivedMessages(): array
    {
        return $this->receivedMessages;
    }

    public function clearMessages(): void
    {
        $this->receivedMessages = [];
    }

    /**
     * Send test reset command to clear server state.
     * Only works in local/testing environment.
     */
    public function sendTestReset(): bool
    {
        if (! $this->send(['subc' => '__test_reset'])) {
            return false;
        }

        $response = $this->waitForMessage('test_reset', 3.0);

        return $response !== null && ($response['success'] ?? false);
    }
}
