<?php

declare(strict_types=1);

namespace App\Services;

class DeviceTokenService
{
    private string $secret;

    public function __construct()
    {
        $this->secret = config('websocket.device_auth.secret', '');
    }

    public function generateToken(string $email, int $buildId): string
    {
        $timestamp = time();
        $hmac = hash_hmac('sha256', "{$email}|{$buildId}|{$timestamp}", $this->secret);

        return "{$email}||{$hmac}.{$buildId}.{$timestamp}";
    }

    public function validateToken(string $rawUserEmail): array
    {
        if (! str_contains($rawUserEmail, '||')) {
            return ['email' => $rawUserEmail, 'build_id' => null, 'authenticated' => false];
        }

        [$email, $tokenPart] = explode('||', $rawUserEmail, 2);

        $segments = explode('.', $tokenPart, 3);
        if (count($segments) !== 3) {
            return ['email' => $email, 'build_id' => null, 'authenticated' => false];
        }

        [$hmac, $buildId, $timestamp] = $segments;

        $expected = hash_hmac('sha256', "{$email}|{$buildId}|{$timestamp}", $this->secret);

        if (! hash_equals($expected, $hmac)) {
            return ['email' => $email, 'build_id' => (int) $buildId, 'authenticated' => false];
        }

        return ['email' => $email, 'build_id' => (int) $buildId, 'authenticated' => true];
    }
}
