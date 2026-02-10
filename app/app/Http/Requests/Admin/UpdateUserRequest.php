<?php

namespace App\Http\Requests\Admin;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;
use Illuminate\Validation\Rules\Password;

class UpdateUserRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        $user = $this->route('user');

        return [
            'username' => ['required', 'string', 'max:50', Rule::unique('users', 'username')->ignore($user->id)],
            'email' => ['required', 'string', 'email', 'max:255', Rule::unique('users', 'email')->ignore($user->id)],
            'password' => ['nullable', 'string', Password::default(), 'confirmed'],
            'subscription_expires_at' => ['nullable', 'date'],
            'roles' => ['array'],
            'roles.*' => ['string', 'exists:roles,name'],
            'direct_permissions' => ['array'],
            'direct_permissions.*' => ['string', 'exists:permissions,name'],
            'max_sub_accounts' => ['integer', 'min:0'],
        ];
    }
}
