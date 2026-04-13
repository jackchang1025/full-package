<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\Device\RegisterDeviceRequest;
use App\Http\Requests\Device\UpdateDeviceInfoRequest;
use App\Models\Device;
use App\Models\DeviceDetail;
use App\Models\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Str;

class DeviceApiController extends Controller
{
    public function register(RegisterDeviceRequest $request): JsonResponse
    {
        $validated = $request->validated();

        // 通过 trusteeId 找到所属用户（trusteeId 在 APK config.json 中配置）
        $trusteeId = $validated['trusteeId'] ?? null;
        $user = null;

        if ($trusteeId) {
            $user = User::where('trustee_id', $trusteeId)->first();
        }

        // 回退：通过 Bearer token 认证（如果有）
        if (! $user) {
            $email = $request->input('_device_auth_email');
            if ($email) {
                $user = User::where('email', $email)->first();
            }
        }

        // 回退：使用第一个用户（仅开发环境，生产应配置 trusteeId）
        if (! $user) {
            $user = User::orderBy('id')->first();
        }

        if (! $user) {
            return $this->error('No user found for this device. Set trusteeId in config.json.', 404);
        }

        $ownerId = $user->getResourceOwnerId();

        $device = Device::where('device_uid', $validated['deviceUid'])
            ->where('user_id', $ownerId)
            ->first();

        $isNew = $device === null;

        $deviceData = [
            'device_uid' => $validated['deviceUid'],
            'user_id' => $ownerId,
            'name' => $validated['model'] ?? $validated['brandCode'] ?? 'Unknown',
            'brand' => $validated['brandCode'] ?? null,
            'manufacturer' => $validated['manufacturer'] ?? null,
            'model' => $validated['model'] ?? null,
            'fingerprint' => $validated['fingerPrint'] ?? null,
            'serial' => $validated['serial'] ?? null,
            'package_name' => $validated['packageName'] ?? null,
            'trustee_id' => $validated['trusteeId'] ?? null,
            'android_version' => isset($validated['apiGrade']) ? (string) $validated['apiGrade'] : ($validated['release'] ?? null),
            'phone_number' => $validated['phoneNumber'] ?? null,
            'lang_code' => $validated['langCode'] ?? null,
            'is_root' => (bool) ($validated['isRoot'] ?? false),
            'enable_development' => (bool) ($validated['enableDevelopment'] ?? false),
            'enable_debug' => (bool) ($validated['enableDebug'] ?? false),
            'enable_wifi_debug' => (bool) ($validated['enableWifiDebug'] ?? false),
            'battery_level' => isset($validated['batteryLevel']['percent'])
                ? (int) $validated['batteryLevel']['percent']
                : null,
            'last_seen_at' => now(),
            'is_online' => true,
        ];

        if ($isNew) {
            $deviceData['uuid'] = Str::uuid()->toString();
            $deviceData['installed_at'] = now();
            $device = Device::create($deviceData);
        } else {
            $device->update($deviceData);
        }

        $detailData = $this->buildDetailData($validated);
        DeviceDetail::updateOrCreate(
            ['device_id' => $device->id],
            $detailData,
        );

        return $this->success($device->uuid);
    }

    public function updateInfo(UpdateDeviceInfoRequest $request): JsonResponse
    {
        $validated = $request->validated();

        $trusteeId = $validated['trusteeId'] ?? null;
        $user = null;
        if ($trusteeId) {
            $user = User::where('trustee_id', $trusteeId)->first();
        }
        if (! $user) {
            $email = $request->input('_device_auth_email');
            if ($email) {
                $user = User::where('email', $email)->first();
            }
        }
        if (! $user) {
            $user = User::orderBy('id')->first();
        }
        if (! $user) {
            return $this->error('User not found', 404);
        }

        $ownerId = $user->getResourceOwnerId();

        $device = Device::where('device_uid', $validated['deviceUid'])
            ->where('user_id', $ownerId)
            ->first();

        if (! $device) {
            return $this->error('Device not found', 404);
        }

        $updateData = array_filter([
            'brand' => $validated['brandCode'] ?? null,
            'android_version' => isset($validated['apiGrade']) ? (string) $validated['apiGrade'] : null,
            'lang_code' => $validated['langCode'] ?? null,
            'phone_number' => $validated['phoneNumber'] ?? null,
        ], fn ($value) => $value !== null);

        $updateData['last_seen_at'] = now();
        $updateData['is_online'] = true;

        $device->update($updateData);

        return $this->success($device->uuid);
    }

    private function buildDetailData(array $validated): array
    {
        $data = [];

        // Build info (flat fields)
        $flatMappings = [
            'displayId' => 'display_id',
            'board' => 'board',
            'device' => 'device_name',
            'hardwareName' => 'hardware_name',
            'product' => 'product',
            'codeName' => 'code_name',
            'incremental' => 'incremental',
            'optimalABI' => 'optimal_abi',
            'supportABI' => 'support_abi',
            'factoryTime' => 'factory_time',
            'osVersion' => 'os_version',
            'osName' => 'os_name',
            'osArch' => 'os_arch',
        ];

        foreach ($flatMappings as $camel => $snake) {
            if (array_key_exists($camel, $validated)) {
                $data[$snake] = $validated[$camel];
            }
        }

        // Screen nested object
        if (isset($validated['screen']) && is_array($validated['screen'])) {
            $screen = $validated['screen'];
            $screenMappings = [
                'width' => 'screen_width',
                'height' => 'screen_height',
                'density' => 'screen_density',
                'scaledDensity' => 'screen_scaled_density',
                'xdpi' => 'screen_xdpi',
                'ydpi' => 'screen_ydpi',
                'isScreenOn' => 'screen_is_on',
                'state' => 'screen_state',
                'screenOffTimeout' => 'screen_off_timeout',
                'isScreenRound' => 'screen_is_round',
                'statusBarHeight' => 'status_bar_height',
                'navigationBarHeight' => 'navigation_bar_height',
                'isBlocked' => 'screen_is_blocked',
            ];

            foreach ($screenMappings as $camel => $snake) {
                if (array_key_exists($camel, $screen)) {
                    $data[$snake] = $screen[$camel];
                }
            }
        }

        // Battery nested object
        if (isset($validated['batteryLevel']) && is_array($validated['batteryLevel'])) {
            $battery = $validated['batteryLevel'];
            $batteryMappings = [
                'percent' => 'battery_percent',
                'status' => 'battery_status',
                'health' => 'battery_health',
                'voltage' => 'battery_voltage',
                'temperature' => 'battery_temperature',
                'technology' => 'battery_technology',
                'plugged' => 'battery_plugged',
                'inPowerSaveMode' => 'in_power_save_mode',
            ];

            foreach ($batteryMappings as $camel => $snake) {
                if (array_key_exists($camel, $battery)) {
                    $data[$snake] = $battery[$camel];
                }
            }
        }

        // DeviceAdmin nested object
        if (isset($validated['deviceAdmin']) && is_array($validated['deviceAdmin'])) {
            $admin = $validated['deviceAdmin'];
            $adminMappings = [
                'packageName' => 'admin_package_name',
                'isAdminActive' => 'is_admin_active',
                'isDeviceOwner' => 'is_device_owner',
                'isProfileOwner' => 'is_profile_owner',
            ];

            foreach ($adminMappings as $camel => $snake) {
                if (array_key_exists($camel, $admin)) {
                    $data[$snake] = $admin[$camel];
                }
            }
        }

        // LockPattern nested object
        if (isset($validated['lockPattern']) && is_array($validated['lockPattern'])) {
            $lock = $validated['lockPattern'];
            $lockMappings = [
                'isScreenOn' => 'screen_is_on',
                'isKeyguardLocked' => 'is_keyguard_locked',
                'isDeviceLocked' => 'is_device_locked',
                'isKeyguardSecure' => 'is_keyguard_secure',
                'isDeviceSecure' => 'is_device_secure',
                'inKeyguardRestrictedInputMode' => 'in_keyguard_restricted_input_mode',
                'quality' => 'lock_quality',
            ];

            foreach ($lockMappings as $camel => $snake) {
                if (array_key_exists($camel, $lock)) {
                    $data[$snake] = $lock[$camel];
                }
            }
        }

        return $data;
    }

    private function success(mixed $data = null): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => $data,
            'count' => 1,
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
