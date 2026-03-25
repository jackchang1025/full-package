<?php

namespace App\Http\Requests\Build;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

/**
 * APK 构建请求验证（store + stream 共享）。
 */
class BuildRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    /**
     * 在验证之前预处理参数：
     * URL 查询参数中的布尔值是字符串 "true"/"false"，
     * 需要转换为实际布尔值才能通过 boolean 验证规则。
     */
    protected function prepareForValidation(): void
    {
        $booleanFields = ['is_custom'];

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
            'template_id' => 'nullable|exists:app_templates,id',
            'name' => 'required|string|max:32',
            // 留空时后端自动生成，仅在有值时校验格式
            'package_name' => [
                'nullable',
                'string',
                'max:255',
                Rule::when($this->filled('package_name'), 'regex:/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)*$/'),
            ],
            'version' => [
                'nullable',
                'string',
                'max:20',
                Rule::when($this->filled('version'), 'regex:/^\d+(\.\d+){0,2}$/'),
            ],
            'is_custom' => 'boolean',

            'client_name' => 'nullable|string|max:100',
            'debug' => 'nullable|integer|in:0,1',
            'alertTitle' => 'nullable|string|max:200',
            'alertMsg' => 'nullable|string|max:1000',
            'okText' => 'nullable|string|max:50',
            'mainUrl' => 'nullable|url',

            'icon_path' => 'nullable|string|max:255',
            'background_path' => 'nullable|string|max:255',
        ];
    }
}
