<?php

declare(strict_types=1);

namespace App\Http\Controllers;

use App\Services\PanelTokenService;
use Illuminate\Http\JsonResponse;

class WebSocketTokenController extends Controller
{
    public function __invoke(PanelTokenService $tokenService): JsonResponse
    {
        $isAdminRoute = str_starts_with(request()->route()?->getName() ?? '', 'admin.');

        if ($isAdminRoute) {
            $admin = auth('admin')->user();

            return $admin
                ? response()->json(['token' => $tokenService->generateToken($admin->id, 'admin')])
                : response()->json(['error' => 'Unauthenticated'], 401);
        }

        $user = auth('web')->user();

        return $user
            ? response()->json(['token' => $tokenService->generateToken($user->id, 'web')])
            : response()->json(['error' => 'Unauthenticated'], 401);
    }
}
