<?php

declare(strict_types=1);

namespace App\Http\Middleware;

use App\Services\DeviceTokenService;
use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class AuthenticateDevice
{
    public function __construct(
        private readonly DeviceTokenService $tokenService,
    ) {}

    public function handle(Request $request, Closure $next): Response
    {
        $bearer = $request->bearerToken();

        if ($bearer === null) {
            return $this->unauthorized('Missing Authorization header');
        }

        $result = $this->tokenService->validateToken($bearer);

        if (! $result['authenticated']) {
            return $this->unauthorized('Invalid owner_token');
        }

        $deviceId = $request->header('X-Device-ID', '');

        $request->merge([
            '_device_auth_email' => $result['email'],
            '_device_auth_build_id' => $result['build_id'],
            '_device_auth_user_id' => $result['user_id'],
            '_device_id' => $deviceId,
        ]);

        return $next($request);
    }

    private function unauthorized(string $msg = 'Unauthorized'): Response
    {
        return response()->json([
            'success' => false,
            'code' => 401,
            'msg' => $msg,
            'data' => null,
        ], 401);
    }
}
