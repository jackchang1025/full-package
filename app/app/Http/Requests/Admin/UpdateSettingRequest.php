<?php

namespace App\Http\Requests\Admin;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class UpdateSettingRequest extends FormRequest
{
    /** Reserved path prefixes that cannot be used as entry paths. */
    private const RESERVED_PATHS = ['download', 'up', 'api', 'sanctum'];

    /** Regex for valid entry path characters. */
    private const PATH_REGEX = '/^[a-zA-Z0-9_\-\/]+$/';

    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'app_name' => ['nullable', 'string', 'max:255'],
            'app_logo' => ['nullable', 'string', 'max:500'],
            'logo_file' => ['nullable', 'image', 'max:' . self::getLogoMaxSizeKb()],
            'user_entry_path' => [
                'nullable',
                'string',
                'max:100',
                'regex:' . self::PATH_REGEX,
                Rule::notIn(self::RESERVED_PATHS),
            ],
            'admin_entry_path' => [
                'required',
                'string',
                'max:100',
                'regex:' . self::PATH_REGEX,
                Rule::notIn(self::RESERVED_PATHS),
            ],
        ];
    }

    public function messages(): array
    {
        return [
            'logo_file.max' => 'logo file 不能大于 ' . self::getLogoMaxSizeKb() . ' KB。',
            'user_entry_path.regex' => '用户入口路径只能包含字母、数字、下划线、连字符和斜杠。',
            'admin_entry_path.regex' => '总后台入口路径只能包含字母、数字、下划线、连字符和斜杠。',
            'admin_entry_path.required' => '总后台入口路径不能为空。',
        ];
    }

    private static function getLogoMaxSizeKb(): int
    {
        return (int) config('site.logo_max_size_kb', 10240);
    }
}
