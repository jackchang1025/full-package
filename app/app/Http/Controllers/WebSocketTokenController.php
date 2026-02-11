<?php

declare(strict_types=1);

namespace App\Http\Controllers;

use App\Services\PanelTokenService;
use Illuminate\Http\JsonResponse;

class WebSocketTokenController extends Controller
{
    public function __invoke(PanelTokenService $tokenService): JsonResponse
    {
        if ($admin = auth('admin')->user()) {
            return response()->json([
                'token' => $tokenService->generateToken($admin->id, 'admin'),
            ]);
        }

        if ($user = auth('web')->user()) {
            return response()->json([
                'token' => $tokenService->generateToken($user->id, 'web'),
            ]);
        }

        return response()->json(['error' => 'Unauthenticated'], 401);
    }
}
