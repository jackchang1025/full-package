<?php

namespace App\Http\Requests\Admin;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rules\Password;

class StoreUserRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        return [
            'username' => ['required', 'string', 'max:50', 'unique:users,username'],
            'email' => ['required', 'string', 'email', 'max:255', 'unique:users,email'],
            'password' => ['required', 'string', Password::default(), 'confirmed'],
            'subscription_expires_at' => ['nullable', 'date'],
            'roles' => ['array'],
            'roles.*' => ['string', 'exists:roles,name'],
            'parent_id' => ['nullable', 'integer', 'exists:users,id'],
            'direct_permissions' => ['array'],
            'direct_permissions.*' => ['string', 'exists:permissions,name'],
            'max_sub_accounts' => ['integer', 'min:0'],
        ];
    }
}
