<?php

namespace App\Http\Requests\SubAccount;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;
use Illuminate\Validation\Rules\Password;

class UpdateSubAccountRequest extends FormRequest
{
    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        $subAccount = $this->route('sub_account');
        $parentPermissions = $this->user()->getAllPermissions()->pluck('name');

        return [
            'username' => ['required', 'string', 'max:50', Rule::unique('users', 'username')->ignore($subAccount->id)],
            'email' => ['required', 'string', 'email', 'max:255', Rule::unique('users', 'email')->ignore($subAccount->id)],
            'password' => ['nullable', 'string', Password::default(), 'confirmed'],
            'permissions' => ['array'],
            'permissions.*' => ['string', Rule::in($parentPermissions)],
        ];
    }
}
