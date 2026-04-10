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
            return $this->unauthorized();
        }

        $result = $this->tokenService->validateToken($bearer);

        if (! $result['authenticated']) {
            return $this->unauthorized();
        }

        $request->merge([
            '_device_auth_email' => $result['email'],
            '_device_auth_build_id' => $result['build_id'],
        ]);

        return $next($request);
    }

    private function unauthorized(): Response
    {
        return response()->json([
            'success' => false,
            'code' => 401,
            'msg' => 'Unauthorized',
            'data' => null,
        ], 401);
    }
}
