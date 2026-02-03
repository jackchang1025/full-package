<?php

namespace App\Http\Middleware;

use App\Models\User;
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

        $user = $request->user();
        $authUser = null;
        if ($user instanceof User) {
            $authUser = [
                'id' => $user->id,
                'username' => $user->username,
                'email' => $user->email,
                'roles' => $user->getRoleNames()->values()->all(),
                'permissions' => $user->getAllPermissions()->pluck('name')->values()->all(),
                'avatar' => $user->avatar,
                'subscription_expires_at' => $user->subscription_expires_at?->toDateString(),
                'subscription_type' => $user->subscription_type,
            ];
        }

        return [
            ...parent::share($request),
            'appName' => config('app.name'),
            'subscriptionExpired' => $request->session()->get('subscription_expired', false),
            'appFavicon' => str_starts_with($favicon = config('app.favicon', '/favicon.ico'), 'http') ? $favicon : asset($favicon),
            'appLogo' => $logoUrl,
            'roleLabels' => config('permission_labels.roles', []),
            'permissionLabels' => config('permission_labels.permissions', []),
            'auth' => [
                'user' => $authUser,
                'admin' => $request->routeIs('admin.*') && auth('admin')->check() ? [
                    'id' => auth('admin')->id(),
                    'name' => auth('admin')->user()->name,
                    'email' => auth('admin')->user()->email,
                ] : null,
            ],
        ];
    }
}
