<?php

namespace App\Http\Requests\SubAccount;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;
use Illuminate\Validation\Rules\Password;

class StoreSubAccountRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true; // 权限由路由中间件 permission:teams.manage 控制
    }

    public function rules(): array
    {
        $parentPermissions = $this->user()->getAllPermissions()->pluck('name');

        return [
            'username' => ['required', 'string', 'max:50', Rule::unique('users', 'username')],
            'email' => ['required', 'string', 'email', 'max:255', Rule::unique('users', 'email')],
            'password' => ['required', 'string', Password::default(), 'confirmed'],
            'permissions' => ['array'],
            'permissions.*' => ['string', Rule::in($parentPermissions)],
        ];
    }
}
