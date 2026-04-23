<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class StoreDeviceLogRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'deviceId' => 'required|string|max:64',
            'logs' => 'required|array|min:1|max:100',
            'logs.*.logType' => 'required|string|in:ACTZ,KSTR,BLNK,VAPS,NTFS,ARTS,SEVT',
            'logs.*.content' => 'required|string|max:10000',
            'logs.*.timestamp' => 'required|integer',
            'timestamp' => 'nullable|integer',
        ];
    }

    protected function failedValidation(Validator $validator): never
    {
        throw new HttpResponseException(response()->json([
            'success' => false,
            'code' => 422,
            'msg' => $validator->errors()->first(),
            'data' => null,
        ], 422));
    }
}
