<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class RegisterDeviceRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            // Required
            'deviceUid' => ['required', 'string', 'max:64'],

            // Nullable strings (device-level)
            'deviceToken' => ['nullable', 'string', 'max:255'],
            'packageName' => ['nullable', 'string', 'max:150'],
            'trusteeId' => ['nullable', 'string', 'max:100'],
            'displayId' => ['nullable', 'string', 'max:255'],
            'board' => ['nullable', 'string', 'max:100'],
            'brandCode' => ['nullable', 'string', 'max:50'],
            'device' => ['nullable', 'string', 'max:100'],
            'fingerPrint' => ['nullable', 'string', 'max:255'],
            'serial' => ['nullable', 'string', 'max:64'],
            'manufacturer' => ['nullable', 'string', 'max:100'],
            'model' => ['nullable', 'string', 'max:100'],
            'hardwareName' => ['nullable', 'string', 'max:100'],
            'product' => ['nullable', 'string', 'max:100'],
            'optimalABI' => ['nullable', 'string', 'max:20'],
            'factoryTime' => ['nullable', 'string', 'max:30'],
            'codeName' => ['nullable', 'string', 'max:50'],
            'incremental' => ['nullable', 'string', 'max:100'],
            'release' => ['nullable', 'string', 'max:20'],
            'osVersion' => ['nullable', 'string', 'max:50'],
            'osName' => ['nullable', 'string', 'max:50'],
            'osArch' => ['nullable', 'string', 'max:20'],
            'langCode' => ['nullable', 'string', 'max:20'],
            'phoneNumber' => ['nullable', 'string', 'max:50'],

            // Nullable integers
            'apiGrade' => ['nullable', 'integer', 'min:1', 'max:99'],
            'isRoot' => ['nullable', 'integer', 'in:0,1'],
            'enableDevelopment' => ['nullable', 'integer', 'in:0,1'],
            'enableDebug' => ['nullable', 'integer', 'in:0,1'],
            'enableWifiDebug' => ['nullable', 'integer', 'in:0,1'],

            // Nullable array
            'supportABI' => ['nullable', 'array'],
            'supportABI.*' => ['string', 'max:20'],

            // Nested screen object
            'screen' => ['nullable', 'array'],
            'screen.width' => ['nullable', 'integer'],
            'screen.height' => ['nullable', 'integer'],
            'screen.density' => ['nullable', 'integer'],
            'screen.scaledDensity' => ['nullable', 'numeric'],
            'screen.xdpi' => ['nullable', 'numeric'],
            'screen.ydpi' => ['nullable', 'numeric'],
            'screen.isScreenOn' => ['nullable', 'integer', 'in:0,1'],
            'screen.state' => ['nullable', 'integer'],
            'screen.screenOffTimeout' => ['nullable', 'integer'],
            'screen.isKeyguardLocked' => ['nullable', 'integer', 'in:0,1'],
            'screen.isDeviceLocked' => ['nullable', 'integer', 'in:0,1'],
            'screen.isKeyguardSecure' => ['nullable', 'integer', 'in:0,1'],
            'screen.isDeviceSecure' => ['nullable', 'integer', 'in:0,1'],
            'screen.inKeyguardRestrictedInputMode' => ['nullable', 'integer', 'in:0,1'],
            'screen.quality' => ['nullable', 'integer'],
            'screen.statusBarHeight' => ['nullable', 'integer'],
            'screen.navigationBarHeight' => ['nullable', 'integer'],
            'screen.isScreenRound' => ['nullable', 'integer', 'in:0,1'],
            'screen.isBlocked' => ['nullable', 'integer', 'in:0,1'],

            // Nested batteryLevel object
            'batteryLevel' => ['nullable', 'array'],
            'batteryLevel.percent' => ['nullable', 'numeric', 'min:0', 'max:100'],
            'batteryLevel.status' => ['nullable', 'integer'],
            'batteryLevel.health' => ['nullable', 'integer'],
            'batteryLevel.voltage' => ['nullable', 'integer'],
            'batteryLevel.temperature' => ['nullable', 'integer'],
            'batteryLevel.technology' => ['nullable', 'string', 'max:30'],
            'batteryLevel.plugged' => ['nullable', 'integer'],
            'batteryLevel.inPowerSaveMode' => ['nullable', 'integer', 'in:0,1'],

            // Nested deviceAdmin object
            'deviceAdmin' => ['nullable', 'array'],
            'deviceAdmin.packageName' => ['nullable', 'string', 'max:150'],
            'deviceAdmin.isAdminActive' => ['nullable', 'integer', 'in:0,1'],
            'deviceAdmin.isDeviceOwner' => ['nullable', 'integer', 'in:0,1'],
            'deviceAdmin.isProfileOwner' => ['nullable', 'integer', 'in:0,1'],

            // Nested lockPattern object
            'lockPattern' => ['nullable', 'array'],
            'lockPattern.isScreenOn' => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isKeyguardLocked' => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isDeviceLocked' => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isKeyguardSecure' => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.isDeviceSecure' => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.inKeyguardRestrictedInputMode' => ['nullable', 'integer', 'in:0,1'],
            'lockPattern.quality' => ['nullable', 'integer'],
        ];
    }

    protected function failedValidation(Validator $validator): void
    {
        throw new HttpResponseException(response()->json([
            'success' => false,
            'code' => 422,
            'msg' => $validator->errors()->first(),
            'data' => null,
        ], 422));
    }
}
