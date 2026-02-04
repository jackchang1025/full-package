<?php

namespace App\Providers;

use App\Models\Setting;
use App\Services\ApkBuilder\ApkBuilder;
use Illuminate\Support\Facades\Config;
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
        $this->app->singleton(ApkBuilder::class);
    }

    public function boot(): void
    {
        $this->applySettingsFromDatabase();
    }

    /**
     * Override config with values from settings table when present.
     */
    protected function applySettingsFromDatabase(): void
    {
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
