<?php

namespace App\Http\Middleware;

use Illuminate\Http\Request;
use Inertia\Middleware;

class HandleInertiaRequests extends Middleware
{
    protected $rootView = 'app';

    public function version(Request $request): ?string
    {
        return parent::version($request);
    }

    public function share(Request $request): array
    {
        $logo = config('app.logo', '');
        $logoUrl = $logo === '' ? '' : (str_starts_with($logo, 'http') ? $logo : asset($logo));

        return [
            ...parent::share($request),
            'appName' => config('app.name'),
            'appFavicon' => str_starts_with($favicon = config('app.favicon', '/favicon.ico'), 'http') ? $favicon : asset($favicon),
            'appLogo' => $logoUrl,
            'auth' => [
                'user' => $request->user() ? [
                    'id' => $request->user()->id,
                    'username' => $request->user()->username,
                    'email' => $request->user()->email,
                    'role' => $request->user()->role,
                    'avatar' => $request->user()->avatar,
                    'subscription_expires_at' => $request->user()->subscription_expires_at?->toDateString(),
                    'subscription_type' => $request->user()->subscription_type,
                ] : null,
            ],
        ];
    }
}
