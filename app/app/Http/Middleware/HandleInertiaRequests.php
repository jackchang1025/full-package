<?php

namespace App\Http\Middleware;

use App\Http\Controllers\Admin\SettingController;
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
        return [
            ...parent::share($request),
            ...$this->getAppConfig(),
            ...$this->getFlashMessages($request),
            ...$this->getPermissionLabels(),
            'auth' => $this->getAuthData($request),
        ];
    }

    /**
     * Get application configuration data.
     */
    private function getAppConfig(): array
    {
        return [
            'appName' => config('app.name'),
            'appLogo' => SettingController::resolveLogoUrl((string) config('app.logo', '')),
            'appFavicon' => SettingController::resolveLogoUrl((string) config('app.favicon', '/favicon.ico')),
            'userBasePath' => $this->normalizePath(config('site.user_entry_path', '')),
            'adminBasePath' => $this->normalizePath(config('site.admin_entry_path', 'admin')) ?: 'admin',
        ];
    }

    /**
     * Get flash messages from session.
     */
    private function getFlashMessages(Request $request): array
    {
        return [
            'subscriptionExpired' => $request->session()->get('subscription_expired', false),
            'flash' => [
                'success' => $request->session()->get('success'),
                'error' => $request->session()->get('error'),
            ],
        ];
    }

    /**
     * Get permission labels configuration.
     */
    private function getPermissionLabels(): array
    {
        return [
            'roleLabels' => config('permission_labels.roles', []),
            'permissionLabels' => config('permission_labels.permissions', []),
        ];
    }

    /**
     * Get authentication data for both user and admin.
     */
    private function getAuthData(Request $request): array
    {
        return [
            'user' => $this->getUserData($request),
            'admin' => $this->getAdminData($request),
        ];
    }

    /**
     * Get current user data if authenticated.
     */
    private function getUserData(Request $request): ?array
    {
        $user = $request->user();

        if (! $user instanceof User) {
            return null;
        }

        return [
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

    /**
     * Get admin data if on admin routes and authenticated.
     */
    private function getAdminData(Request $request): ?array
    {
        if (! $request->routeIs('admin.*') || ! auth('admin')->check()) {
            return null;
        }

        $admin = auth('admin')->user();

        return [
            'id' => auth('admin')->id(),
            'name' => $admin->name,
            'email' => $admin->email,
        ];
    }

    /**
     * Normalize a path by trimming slashes.
     */
    private function normalizePath(mixed $path): string
    {
        return trim((string) $path, '/');
    }
}
