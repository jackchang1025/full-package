<?php

namespace App\Providers;

use App\Models\Setting;
use App\Services\ApkBuilder\ApkBuilder;
use App\Services\ApkBuilder\Contracts\FileSystemInterface;
use App\Services\ApkBuilder\Contracts\ProcessRunnerInterface;
use App\Services\ApkBuilder\LaravelFileSystem;
use App\Services\ApkBuilder\LaravelProcessRunner;
use App\Services\GradleApkBuilder\GradleApkBuilder;
use Illuminate\Support\Facades\Config;
use Illuminate\Support\Facades\URL;
use Illuminate\Support\ServiceProvider;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Setting keys that override config values.
     */
    private const SETTING_CONFIG_MAP = [
        'app_name' => 'app.name',
        'app_logo' => 'app.logo',
        'admin_entry_path' => 'site.admin_entry_path',
        'user_entry_path' => 'site.user_entry_path',
    ];

    public function register(): void
    {
        $this->app->bind(FileSystemInterface::class, LaravelFileSystem::class);
        $this->app->bind(ProcessRunnerInterface::class, LaravelProcessRunner::class);
        $this->app->singleton(ApkBuilder::class);
        // GradleApkBuilder 持有 per-build 可变状态 (workDir/stepStats)，不能共享实例
        $this->app->bind(GradleApkBuilder::class);
    }

    public function boot(): void
    {
        // 当 APP_URL 为 https 时，强制所有生成的 URL 使用 HTTPS
        if (str_starts_with(config('app.url'), 'https://')) {
            URL::forceScheme('https');
        }

        $this->applySettingsFromDatabase();
    }

    /**
     * Override config with values from settings table when present.
     */
    protected function applySettingsFromDatabase(): void
    {
        try {
            $settings = Setting::getMany(array_keys(self::SETTING_CONFIG_MAP));

            foreach (self::SETTING_CONFIG_MAP as $settingKey => $configKey) {
                if ($settings[$settingKey] !== null) {
                    Config::set($configKey, $settings[$settingKey]);
                }
            }

            // Sync app_logo to app.favicon so browser tab icon updates
            if ($settings['app_logo'] !== null) {
                Config::set('app.favicon', $settings['app_logo']);
            }

            $this->applyFortifySettings($settings['user_entry_path']);
        } catch (\Exception $e) {
            // 表不存在时跳过（如迁移期间）
        }
    }

    /**
     * Apply Fortify-specific settings based on user entry path.
     */
    protected function applyFortifySettings(?string $userPath): void
    {
        if ($userPath === null) {
            return;
        }

        $trimmed = trim($userPath, '/');
        Config::set('fortify.prefix', $trimmed);
        Config::set('fortify.home', $trimmed !== '' ? "/{$trimmed}/home" : '/home');
    }
}
