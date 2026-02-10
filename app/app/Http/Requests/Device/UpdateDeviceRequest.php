<?php

namespace App\Http\Requests\Device;

use Illuminate\Foundation\Http\FormRequest;

/**
 * 设备备注更新请求（用户端 + 管理端共享）。
 */
class UpdateDeviceRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'remark' => ['nullable', 'string', 'max:200'],
        ];
    }
}
