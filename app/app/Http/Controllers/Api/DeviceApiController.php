<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class DeviceApiController extends Controller
{
    public function register(Request $request): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => null,
        ]);
    }

    public function updateInfo(Request $request): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => null,
        ]);
    }
}
