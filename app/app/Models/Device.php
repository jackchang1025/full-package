<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Device extends Model
{
    use HasFactory;
    protected $fillable = [
        'uuid',
        'user_id',
        'name',
        'country',
        'ip_address',
        'ip_location',
        'android_version',
        'model',
        'phone_number',
        'battery_level',
        'network_type',
        'installed_at',
        'last_seen_at',
        'is_online',
        'is_removed',
        'is_active',
        'has_accessibility',
        'settings',
        'permissions',
        'session_id',
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
            'settings' => 'array',
            'permissions' => 'array',
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
}
