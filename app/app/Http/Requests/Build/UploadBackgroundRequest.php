<?php

namespace App\Http\Requests\Build;

use Illuminate\Foundation\Http\FormRequest;

class UploadBackgroundRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'background' => 'required|image|mimes:png,jpg,jpeg|max:5120',
            'type' => 'nullable|string|in:blackui,abg',
        ];
    }
}
