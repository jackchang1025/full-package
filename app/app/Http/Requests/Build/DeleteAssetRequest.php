<?php

namespace App\Http\Requests\Build;

use Illuminate\Foundation\Http\FormRequest;

/**
 * 删除构建素材请求（图标 / 背景图共享）。
 */
class DeleteAssetRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'name' => 'required|string',
        ];
    }
}
