<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Spatie\Permission\Traits\HasRoles;

class User extends Authenticatable
{
    use HasFactory, HasRoles, Notifiable, SoftDeletes;

    protected $fillable = [
        'username',
        'email',
        'email_encrypted',
        'password',
        'avatar',
        'otp_secret',
        'subscription_expires_at',
        'subscription_type',
        'contact',
        'is_hidden',
        'parent_id',
        'max_sub_accounts',
    ];

    protected $hidden = [
        'password',
        'remember_token',
        'otp_secret',
    ];

    protected function casts(): array
    {
        return [
            'email_verified_at' => 'datetime',
            'subscription_expires_at' => 'date',
            'deleted_at' => 'datetime',
            'password' => 'hashed',
            'is_hidden' => 'boolean',
        ];
    }

    // ── 资源关系 ──────────────────────────────────────

    public function devices(): HasMany
    {
        return $this->hasMany(Device::class);
    }

    public function appBuilds(): HasMany
    {
        return $this->hasMany(AppBuild::class);
    }

    // ── 子账号（团队）关系 ─────────────────────────────────

    public function parent(): BelongsTo
    {
        return $this->belongsTo(self::class, 'parent_id');
    }

    public function subAccounts(): HasMany
    {
        return $this->hasMany(self::class, 'parent_id');
    }

    // ── 查询作用域（总后台用户列表）─────────────────────

    /** 仅主账号（不含子账号）。 */
    public function scopeParentOnly(Builder $query): Builder
    {
        return $query->whereNull('parent_id');
    }

    /** 按用户名或邮箱搜索（含子账号）。 */
    public function scopeSearch(Builder $query, ?string $search): Builder
    {
        if ($search === null || $search === '') {
            return $query;
        }

        return $query->where(function (Builder $q) use ($search) {
            $q->where('username', 'like', "%{$search}%")
                ->orWhere('email', 'like', "%{$search}%")
                ->orWhereHas('subAccounts', function (Builder $sq) use ($search) {
                    $sq->where('username', 'like', "%{$search}%")
                        ->orWhere('email', 'like', "%{$search}%");
                });
        });
    }

    // ── 子账号辅助方法 ─────────────────────────────────

    /** 子账号不可被赋予的权限（仅主账号可持有，用于管理子账号）。 */
    public const PERMISSION_TEAMS_MANAGE = 'teams.manage';

    /**
     * 获取可分配给子账号的权限名称列表。
     * 排除 teams.manage，子账号不能管理子账号。
     */
    public function getPermissionsAssignableToSubAccounts(): array
    {
        return $this->getAllPermissions()
            ->where('name', '!=', self::PERMISSION_TEAMS_MANAGE)
            ->pluck('name')
            ->values()
            ->all();
    }

    /**
     * 是否为子账号（有父账号）。
     */
    public function isSubAccount(): bool
    {
        return $this->parent_id !== null;
    }

    /**
     * 获取资源归属用户（父账号或自身）。
     *
     * 子账号共享父账号的资源，因此返回父账号实例；
     * 父账号或独立用户返回自身。
     */
    public function getResourceOwner(): self
    {
        if ($this->isSubAccount()) {
            return $this->parent ?? $this;
        }

        return $this;
    }

    /**
     * 获取资源归属用户 ID。
     *
     * 子账号共享父账号的资源，因此返回 parent_id；
     * 父账号或独立用户返回自身 id。
     */
    public function getResourceOwnerId(): int
    {
        return $this->parent_id ?? $this->id;
    }

    /**
     * 是否还可以创建新的子账号。
     */
    public function canCreateSubAccount(): bool
    {
        return ! $this->isSubAccount()
            && $this->can('teams.manage')
            && $this->subAccounts()->count() < $this->max_sub_accounts;
    }

    // ── 订阅 ────────────────────────────────────────

    /**
     * 检查用户是否有有效订阅。
     *
     * 子账号继承父账号的订阅状态：父账号过期则子账号同时不可用。
     */
    public function hasActiveSubscription(): bool
    {
        if ($this->isSubAccount()) {
            return $this->parent?->hasActiveSubscription() ?? false;
        }

        return $this->subscription_expires_at && $this->subscription_expires_at->isFuture();
    }
}
