<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\Services\DeviceTokenService;
use App\WebSocket\WebSocketLog;

class DeviceAuthService
{
    private readonly DeviceTokenService $tokenService;

    public function __construct()
    {
        $this->tokenService = new DeviceTokenService;
    }

    public function authenticate(string $token, string $sessionId): ?DeviceAuthResult
    {
        if ($token === '' || $sessionId === '') {
            return null;
        }

        $result = $this->tokenService->validateOwnerToken($token);

        if (! $result['authenticated']) {
            WebSocketLog::getLogger()->warning("Device auth failed: invalid token, sessionId={$sessionId}");

            return null;
        }

        return new DeviceAuthResult(
            userId: $result['user_id'],
            sessionId: $sessionId,
        );
    }
}
