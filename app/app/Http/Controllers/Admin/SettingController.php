<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\UpdateSettingRequest;
use App\Models\Setting;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Http\UploadedFile;
use Illuminate\Support\Facades\Artisan;
use Illuminate\Support\Facades\Storage;
use Inertia\Inertia;
use Inertia\Response;

class SettingController extends Controller
{
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
                'logo_max_size_label' => self::formatLogoMaxSizeLabel(self::getLogoMaxSizeKb()),
                'user_entry_path' => $settings['user_entry_path'] ?? config('site.user_entry_path', ''),
                'admin_entry_path' => $settings['admin_entry_path'] ?? config('site.admin_entry_path', 'admin'),
                'max_main_accounts' => Setting::getInt('max_main_accounts'),
                'show_offline_devices' => Setting::getBool('show_offline_devices') ?? true,
            ],
        ]);
    }

    /**
     * Update settings. Accepts POST (with optional logo_file) or PUT.
     */
    public function update(UpdateSettingRequest $request): RedirectResponse
    {
        $validated = $request->validated();

        $userPath = $this->normalizePath($validated['user_entry_path'] ?? '');
        $adminPath = $this->normalizePath($validated['admin_entry_path'] ?? 'admin') ?: 'admin';

        if ($error = $this->validatePathsNotConflict($userPath, $adminPath)) {
            return $error;
        }

        // 检测管理后台路径是否改变
        $oldAdminPath = $this->normalizePath(config('site.admin_entry_path', 'admin')) ?: 'admin';
        $adminPathChanged = $adminPath !== $oldAdminPath;

        $this->handleLogoChange($request, $validated);
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
     * Logo max file size in KB from config (default 10 MB).
     */
    private static function getLogoMaxSizeKb(): int
    {
        return (int) config('site.logo_max_size_kb', 10240);
    }

    /**
     * Format logo max size (KB) for display, e.g. 10240 -> "10 MB".
     */
    private static function formatLogoMaxSizeLabel(int $kb): string
    {
        return $kb >= 1024 ? sprintf('%d MB', (int) round($kb / 1024)) : sprintf('%d KB', $kb);
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
     * Handle logo changes: upload new file, or remove existing logo when app_logo is empty.
     */
    private function handleLogoChange(Request $request, array $validated): void
    {
        // If a new file was uploaded, replace the old logo
        if ($request->hasFile('logo_file')) {
            $this->deleteOldLogo();

            /** @var UploadedFile $file */
            $file = $request->file('logo_file');
            $path = $file->store(self::LOGO_DIRECTORY, self::STORAGE_DISK);

            Setting::set('app_logo', self::STORAGE_URL_PREFIX.$path);

            return;
        }

        // If app_logo was explicitly set to empty, remove the existing logo
        if (array_key_exists('app_logo', $validated) && empty($validated['app_logo'])) {
            $this->deleteOldLogo();
            Setting::set('app_logo', null);
        }
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

        if (array_key_exists('max_main_accounts', $validated)) {
            Setting::set('max_main_accounts', $validated['max_main_accounts']);
        }

        if (array_key_exists('show_offline_devices', $validated)) {
            Setting::set('show_offline_devices', $validated['show_offline_devices']);
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
