<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Device;
use App\Models\User;
use Carbon\Carbon;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class ClientApiController extends Controller
{
    public function register(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'deviceId' => 'required|string|max:64',
            'deviceName' => 'nullable|string|max:255',
            'model' => 'nullable|string|max:100',
            'brand' => 'nullable|string|max:100',
            'manufacturer' => 'nullable|string|max:100',
            'osVersion' => 'nullable|string|max:50',
            'sdkVersion' => 'nullable|integer',
            'appName' => 'nullable|string|max:100',
            'appVersion' => 'nullable|string|max:50',
            'batteryLevel' => 'nullable|integer|min:0|max:100',
            'isCharging' => 'nullable',
            'screenWidth' => 'nullable|integer',
            'screenHeight' => 'nullable|integer',
            'firstInstallTime' => 'nullable|integer',
            'hasSim' => 'nullable',
            'phoneNumber' => 'nullable|string|max:50',
            'phoneNumber2' => 'nullable|string|max:50',
            'networkType' => 'nullable|string|max:50',
            'ownerUsername' => 'nullable|string|max:100',
            'timestamp' => 'nullable|integer',
        ]);

        $userId = $request->input('_device_auth_user_id');
        $user = User::find($userId);
        if (! $user) {
            return $this->error('User not found', 404);
        }

        $ownerId = $user->getResourceOwnerId();
        $deviceId = $request->input('_device_id') ?: $validated['deviceId'];

        $device = Device::where('device_uid', $deviceId)
            ->orWhere('uuid', $deviceId)
            ->first();

        $deviceData = [
            'device_uid' => $deviceId,
            'user_id' => $ownerId,
            'name' => $validated['deviceName'] ?? $validated['model'] ?? 'Unknown',
            'model' => $validated['model'] ?? null,
            'brand' => $validated['brand'] ?? null,
            'manufacturer' => $validated['manufacturer'] ?? null,
            'android_version' => $validated['osVersion'] ?? null,
            'sdk_version' => $validated['sdkVersion'] ?? null,
            'app_name' => $validated['appName'] ?? null,
            'app_version' => $validated['appVersion'] ?? null,
            'battery_level' => $validated['batteryLevel'] ?? null,
            'is_charging' => (bool) ($validated['isCharging'] ?? false),
            'phone_number' => $validated['phoneNumber'] ?? null,
            'phone_number2' => $validated['phoneNumber2'] ?? null,
            'network_type' => $validated['networkType'] ?? null,
            'screen_width' => $validated['screenWidth'] ?? null,
            'screen_height' => $validated['screenHeight'] ?? null,
            'has_sim' => (bool) ($validated['hasSim'] ?? false),
            'is_online' => true,
            'last_seen_at' => now(),
        ];

        if ($device === null) {
            $deviceData['uuid'] = $deviceId;
            $deviceData['installed_at'] = isset($validated['firstInstallTime']) && $validated['firstInstallTime'] > 0
                ? Carbon::createFromTimestampMs($validated['firstInstallTime'])
                : now();
            $device = Device::create($deviceData);
        } else {
            $device->update($deviceData);
        }

        return $this->success(['uuid' => $device->uuid]);
    }

    private function success(mixed $data = null): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => $data,
        ]);
    }

    private function error(string $msg, int $code): JsonResponse
    {
        return response()->json([
            'success' => false,
            'code' => $code,
            'msg' => $msg,
            'data' => null,
        ], $code);
    }
}
