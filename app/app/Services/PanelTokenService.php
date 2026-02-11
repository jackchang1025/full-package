<?php

declare(strict_types=1);

namespace App\Services;

final class PanelTokenService
{
    private string $secret;

    private int $ttl;

    public function __construct()
    {
        $this->secret = config('websocket.panel_auth.secret', '');
        $this->ttl = (int) config('websocket.panel_auth.ttl', 300);
    }

    public function generateToken(int $userId, string $guard, ?int $timestamp = null): string
    {
        $timestamp ??= time();
        $hmac = hash_hmac('sha256', "{$userId}|{$guard}|{$timestamp}", $this->secret);

        return "{$hmac}.{$userId}.{$guard}.{$timestamp}";
    }

    public function validateToken(string $token): array
    {
        $segments = explode('.', $token, 4);

        if (count($segments) !== 4) {
            return ['user_id' => null, 'guard' => null, 'authenticated' => false];
        }

        [$hmac, $userId, $guard, $timestamp] = $segments;

        if (! in_array($guard, ['web', 'admin'], true)) {
            return ['user_id' => (int) $userId, 'guard' => $guard, 'authenticated' => false];
        }

        $expected = hash_hmac('sha256', "{$userId}|{$guard}|{$timestamp}", $this->secret);

        if (! hash_equals($expected, $hmac)) {
            return ['user_id' => (int) $userId, 'guard' => $guard, 'authenticated' => false];
        }

        if ((time() - (int) $timestamp) > $this->ttl) {
            return ['user_id' => (int) $userId, 'guard' => $guard, 'authenticated' => false];
        }

        return ['user_id' => (int) $userId, 'guard' => $guard, 'authenticated' => true];
    }
}
