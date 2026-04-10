<?php

declare(strict_types=1);

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class DeviceDetail extends Model
{
    protected $fillable = [
        'device_id',

        // Build info
        'display_id',
        'board',
        'device_name',
        'hardware_name',
        'product',
        'code_name',
        'incremental',
        'optimal_abi',
        'support_abi',
        'factory_time',

        // OS info
        'os_version',
        'os_name',
        'os_arch',

        // Screen (ScreenMetricsVO)
        'screen_width',
        'screen_height',
        'screen_density',
        'screen_scaled_density',
        'screen_xdpi',
        'screen_ydpi',
        'screen_is_on',
        'screen_state',
        'screen_off_timeout',
        'screen_is_round',
        'status_bar_height',
        'navigation_bar_height',
        'screen_is_blocked',

        // Lock (LockPatternVO)
        'is_keyguard_locked',
        'is_device_locked',
        'is_keyguard_secure',
        'is_device_secure',
        'in_keyguard_restricted_input_mode',
        'lock_quality',

        // Battery (BatteryLevelVO)
        'battery_percent',
        'battery_status',
        'battery_health',
        'battery_voltage',
        'battery_temperature',
        'battery_technology',
        'battery_plugged',
        'in_power_save_mode',

        // Admin (DeviceAdminVO)
        'admin_package_name',
        'is_admin_active',
        'is_device_owner',
        'is_profile_owner',
    ];

    protected function casts(): array
    {
        return [
            'support_abi' => 'array',
            'screen_is_on' => 'boolean',
            'screen_is_round' => 'boolean',
            'screen_is_blocked' => 'boolean',
            'is_keyguard_locked' => 'boolean',
            'is_device_locked' => 'boolean',
            'is_keyguard_secure' => 'boolean',
            'is_device_secure' => 'boolean',
            'in_keyguard_restricted_input_mode' => 'boolean',
            'in_power_save_mode' => 'boolean',
            'is_admin_active' => 'boolean',
            'is_device_owner' => 'boolean',
            'is_profile_owner' => 'boolean',
        ];
    }

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }
}
