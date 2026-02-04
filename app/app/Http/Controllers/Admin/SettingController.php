<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Setting;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Artisan;
use Illuminate\Support\Facades\Storage;
use Illuminate\Validation\Rule;
use Inertia\Inertia;
use Inertia\Response;

class SettingController extends Controller
{
    /** Reserved path prefixes that cannot be used as entry paths. */
    private const RESERVED_PATHS = ['download', 'up', 'api', 'sanctum'];

    /** Regex for valid entry path characters. */
    private const PATH_REGEX = '/^[a-zA-Z0-9_\-\/]+$/';

    /** Storage disk and directory for site assets. */
    private const STORAGE_DISK = 'public';
    private const LOGO_DIRECTORY = 'site';
    private const STORAGE_URL_PREFIX = '/storage/';

    public function index(): Response
    {
        $settings = Setting::getMany(['app_name', 'app_logo', 'user_entry_path', 'admin_entry_path']);

        $appLogo = $settings['app_logo'] ?? config('app.logo', '');

        return Inertia::render('Admin/Settings/Index', [
            'settings' => [
                'app_name' => $settings['app_name'] ?? config('app.name'),
                'app_logo' => $appLogo,
                'app_logo_url' => self::resolveLogoUrl($appLogo),
                'user_entry_path' => $settings['user_entry_path'] ?? config('site.user_entry_path', ''),
                'admin_entry_path' => $settings['admin_entry_path'] ?? config('site.admin_entry_path', 'admin'),
            ],
        ]);
    }

    /**
     * Update settings. Accepts POST (with optional logo_file) or PUT.
     */
    public function update(Request $request): RedirectResponse
    {
        $validated = $this->validateRequest($request);

        $userPath = $this->normalizePath($validated['user_entry_path'] ?? '');
        $adminPath = $this->normalizePath($validated['admin_entry_path'] ?? 'admin') ?: 'admin';

        if ($error = $this->validatePathsNotConflict($userPath, $adminPath)) {
            return $error;
        }

        // 检测管理后台路径是否改变
        $oldAdminPath = $this->normalizePath(config('site.admin_entry_path', 'admin')) ?: 'admin';
        $adminPathChanged = $adminPath !== $oldAdminPath;

        $this->handleLogoUpload($request);
        $this->saveSettings($validated, $userPath, $adminPath);
        $this->clearCaches();

        $successMessage = '设置已保存。配置与路由缓存已清除，必要时重启服务使入口路径生效。';

        // 如果管理后台路径改变，重定向到新路径
        if ($adminPathChanged) {
            return redirect("/{$adminPath}/settings")->with('success', $successMessage);
        }

        return redirect()->back()->with('success', $successMessage);
    }

    /**
     * Resolve logo path to full URL.
     */
    public static function resolveLogoUrl(string $logo): string
    {
        if ($logo === '') {
            return '';
        }

        return str_starts_with($logo, 'http') ? $logo : asset($logo);
    }

    /**
     * Validate the incoming request.
     */
    private function validateRequest(Request $request): array
    {
        return $request->validate([
            'app_name' => ['nullable', 'string', 'max:255'],
            'app_logo' => ['nullable', 'string', 'max:500'],
            'logo_file' => ['nullable', 'image', 'max:2048'],
            'user_entry_path' => [
                'nullable',
                'string',
                'max:100',
                'regex:' . self::PATH_REGEX,
                Rule::notIn(self::RESERVED_PATHS),
            ],
            'admin_entry_path' => [
                'required',
                'string',
                'max:100',
                'regex:' . self::PATH_REGEX,
                Rule::notIn(self::RESERVED_PATHS),
            ],
        ], [
            'user_entry_path.regex' => '用户入口路径只能包含字母、数字、下划线、连字符和斜杠。',
            'admin_entry_path.regex' => '总后台入口路径只能包含字母、数字、下划线、连字符和斜杠。',
            'admin_entry_path.required' => '总后台入口路径不能为空。',
        ]);
    }

    /**
     * Normalize a path by trimming slashes.
     */
    private function normalizePath(?string $path): string
    {
        return trim((string) $path, '/');
    }

    /**
     * Validate that user and admin paths don't conflict.
     */
    private function validatePathsNotConflict(string $userPath, string $adminPath): ?RedirectResponse
    {
        if ($userPath !== '' && $userPath === $adminPath) {
            return back()->withErrors(['user_entry_path' => '用户入口路径与总后台入口路径不能相同。']);
        }

        return null;
    }

    /**
     * Handle logo file upload if present.
     */
    private function handleLogoUpload(Request $request): void
    {
        if (! $request->hasFile('logo_file')) {
            return;
        }

        $this->deleteOldLogo();

        /** @var UploadedFile $file */
        $file = $request->file('logo_file');
        $path = $file->store(self::LOGO_DIRECTORY, self::STORAGE_DISK);

        Setting::set('app_logo', self::STORAGE_URL_PREFIX . $path);
    }

    /**
     * Delete old logo file if it exists in storage.
     */
    private function deleteOldLogo(): void
    {
        $oldLogo = Setting::get('app_logo');

        if (! $oldLogo || ! str_starts_with($oldLogo, self::STORAGE_URL_PREFIX)) {
            return;
        }

        $oldPath = str_replace(self::STORAGE_URL_PREFIX, '', $oldLogo);

        if (Storage::disk(self::STORAGE_DISK)->exists($oldPath)) {
            Storage::disk(self::STORAGE_DISK)->delete($oldPath);
        }
    }

    /**
     * Save validated settings to database.
     */
    private function saveSettings(array $validated, string $userPath, string $adminPath): void
    {
        if (array_key_exists('app_name', $validated)) {
            Setting::set('app_name', $validated['app_name'] ?: null);
        }

        if (array_key_exists('user_entry_path', $validated)) {
            Setting::set('user_entry_path', $userPath ?: null);
        }

        if (array_key_exists('admin_entry_path', $validated)) {
            Setting::set('admin_entry_path', $adminPath);
        }
    }

    /**
     * Clear application caches.
     */
    private function clearCaches(): void
    {
        Artisan::call('config:clear');
        Artisan::call('route:clear');
    }
}
