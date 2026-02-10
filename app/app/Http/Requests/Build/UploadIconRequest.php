<?php

namespace App\Http\Requests\Build;

use Illuminate\Foundation\Http\FormRequest;

class UploadIconRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'icon' => 'required|image|mimes:png,jpg,jpeg|max:2048',
        ];
    }
}
