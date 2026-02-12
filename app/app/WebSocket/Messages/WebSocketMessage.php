<?php

declare(strict_types=1);

namespace App\WebSocket\Messages;

final readonly class WebSocketMessage
{
    private function __construct(
        private array $data,
    ) {}

    public static function fromArray(array $data): self
    {
        return new self($data);
    }

    public function itype(): ?string
    {
        return $this->data['itype'] ?? null;
    }

    public function subc(): ?string
    {
        return $this->data['subc'] ?? null;
    }

    public function pid(): ?string
    {
        $val = $this->data['pid'] ?? null;

        return $val !== null ? (string) $val : null;
    }

    public function token(): string
    {
        return $this->data['token'] ?? '';
    }

    public function page(): int
    {
        return max(1, (int) ($this->data['page'] ?? 1));
    }

    public function pageSize(): int
    {
        return min(100, max(1, (int) ($this->data['pageSize'] ?? 10)));
    }

    public function filters(): array
    {
        return $this->data['filters'] ?? [];
    }

    public function get(string $key, mixed $default = null): mixed
    {
        return $this->data[$key] ?? $default;
    }

    public function getString(string $key, string $default = ''): string
    {
        return (string) ($this->data[$key] ?? $default);
    }

    public function getInt(string $key, int $default = 0): int
    {
        return (int) ($this->data[$key] ?? $default);
    }

    public function has(string $key): bool
    {
        return array_key_exists($key, $this->data);
    }

    public function toArray(): array
    {
        return $this->data;
    }
}
