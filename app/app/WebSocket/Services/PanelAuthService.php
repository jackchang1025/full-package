<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

use App\Models\Admin;
use App\Models\User;
use App\Services\PanelTokenService;
use App\WebSocket\WebSocketLog;

class PanelAuthService
{
    public function __construct(
        private readonly PanelTokenService $tokenService,
        private readonly DatabaseReconnector $databaseReconnector,
    ) {}

    public function authenticate(string $token): ?PanelAuthResult
    {
        if (empty($token)) {
            return null;
        }

        $result = $this->tokenService->validateToken($token);

        if (! $result['authenticated']) {
            return null;
        }

        $userId = $result['user_id'];
        $guard = $result['guard'];
        $isAdmin = $guard === 'admin';

        $this->databaseReconnector->reconnect();

        $user = $isAdmin
            ? Admin::find($userId)
            : User::find($userId);

        if ($user === null) {
            WebSocketLog::getLogger()->warning("Auth: user not found, userId={$userId}, guard={$guard}");

            return null;
        }

        $ownerEmail = $user->email;
        $ownerId = $isAdmin ? null : $userId;

        if (! $isAdmin) {
            $owner = $user->getResourceOwner();
            $ownerEmail = $owner->email;
            $ownerId = $owner->id;
        }

        return new PanelAuthResult(
            userId: $userId,
            guard: $guard,
            isAdmin: $isAdmin,
            ownerEmail: $ownerEmail,
            ownerId: $ownerId,
        );
    }
}
