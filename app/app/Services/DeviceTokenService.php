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

    // --- Legacy: email-based token (backward compat) ---

    public function generateToken(string $email, int $buildId): string
    {
        $timestamp = time();
        $hmac = hash_hmac('sha256', "{$email}|{$buildId}|{$timestamp}", $this->secret);

        return "{$email}||{$hmac}.{$buildId}.{$timestamp}";
    }

    public function validateToken(string $rawUserEmail): array
    {
        // Try new owner_token format first: userId.hmac.timestamp
        if (! str_contains($rawUserEmail, '||') && str_contains($rawUserEmail, '.')) {
            return $this->validateOwnerToken($rawUserEmail);
        }

        if (! str_contains($rawUserEmail, '||')) {
            return ['email' => $rawUserEmail, 'build_id' => null, 'authenticated' => false, 'user_id' => null];
        }

        [$email, $tokenPart] = explode('||', $rawUserEmail, 2);

        $segments = explode('.', $tokenPart, 3);
        if (count($segments) !== 3) {
            return ['email' => $email, 'build_id' => null, 'authenticated' => false, 'user_id' => null];
        }

        [$hmac, $buildId, $timestamp] = $segments;

        $expected = hash_hmac('sha256', "{$email}|{$buildId}|{$timestamp}", $this->secret);

        if (! hash_equals($expected, $hmac)) {
            return ['email' => $email, 'build_id' => (int) $buildId, 'authenticated' => false, 'user_id' => null];
        }

        return ['email' => $email, 'build_id' => (int) $buildId, 'authenticated' => true, 'user_id' => null];
    }

    // --- New: userId-based token (no email exposure) ---

    /**
     * Generate owner token from user ID. Written into APK config during build.
     * Format: {userId}.{hmac}.{timestamp}
     */
    public function generateOwnerToken(int $userId): string
    {
        $timestamp = time();
        $hmac = hash_hmac('sha256', "{$userId}|{$timestamp}", $this->secret);

        return "{$userId}.{$hmac}.{$timestamp}";
    }

    /**
     * Validate owner token. Returns user_id on success.
     * Format: {userId}.{hmac}.{timestamp}
     */
    public function validateOwnerToken(string $token): array
    {
        $segments = explode('.', $token, 3);
        if (count($segments) !== 3) {
            return ['email' => null, 'build_id' => null, 'authenticated' => false, 'user_id' => null];
        }

        [$userId, $hmac, $timestamp] = $segments;

        $expected = hash_hmac('sha256', "{$userId}|{$timestamp}", $this->secret);

        if (! hash_equals($expected, $hmac)) {
            return ['email' => null, 'build_id' => null, 'authenticated' => false, 'user_id' => (int) $userId];
        }

        return ['email' => null, 'build_id' => null, 'authenticated' => true, 'user_id' => (int) $userId];
    }
}
