<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class UpdateDeviceInfoRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'deviceId' => ['nullable', 'string', 'max:64'],
            'deviceUid' => ['required', 'string', 'max:64'],
            'brandCode' => ['nullable', 'string', 'max:50'],
            'apiGrade' => ['nullable', 'integer', 'min:1', 'max:99'],
            'langCode' => ['nullable', 'string', 'max:20'],
            'phoneNumber' => ['nullable', 'string', 'max:50'],
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
