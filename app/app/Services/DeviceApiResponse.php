<?php

declare(strict_types=1);

namespace App\Services;

use Illuminate\Http\Client\Response;

/**
 * DeviceProxyService 的响应封装。
 */
final class DeviceApiResponse
{
    private function __construct(
        public readonly bool $success,
        public readonly int $status,
        public readonly ?array $data,
        public readonly ?string $error,
    ) {}

    public static function fromHttp(Response $response): self
    {
        $json = null;
        try {
            $json = $response->json();
        } catch (\Throwable) {
        }

        return new self(
            success: $response->successful(),
            status: $response->status(),
            data: is_array($json) ? $json : null,
            error: $response->successful() ? null : $response->body(),
        );
    }

    public static function noTunnel(): self
    {
        return new self(false, 0, null, 'Device has no frpc tunnel assigned');
    }

    public static function connectionFailed(string $reason): self
    {
        return new self(false, 0, null, "Connection failed: {$reason}");
    }

    public function ok(): bool
    {
        return $this->success;
    }

    /** 取 data 中指定键，未找到返回 null */
    public function get(string $key): mixed
    {
        return $this->data[$key] ?? null;
    }

    public function toArray(): array
    {
        return [
            'success' => $this->success,
            'status' => $this->status,
            'data' => $this->data,
            'error' => $this->error,
        ];
    }
}
