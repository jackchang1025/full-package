<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class SyncCipherRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'cipherGradeCode' => 'required|string|max:50',
            'textCipher' => 'nullable|string|max:1000',
            'patternCipher' => 'nullable|string|max:255',
            'isLocked' => 'nullable|boolean',
            'captureTime' => 'nullable|integer',
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
