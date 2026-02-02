<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;

class User extends Authenticatable
{
    use HasFactory, Notifiable;

    protected $fillable = [
        'username',
        'email',
        'email_encrypted',
        'password',
        'avatar',
        'role',
        'otp_secret',
        'subscription_expires_at',
        'subscription_type',
        'contact',
        'is_hidden',
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
            'password' => 'hashed',
            'is_hidden' => 'boolean',
        ];
    }

    public function devices(): HasMany
    {
        return $this->hasMany(Device::class);
    }

    public function appBuilds(): HasMany
    {
        return $this->hasMany(AppBuild::class);
    }

    public function isAdmin(): bool
    {
        //测试 暂时不使用管理员
        return $this->role === 'admin___';
    }

    public function hasActiveSubscription(): bool
    {
        return $this->subscription_expires_at && $this->subscription_expires_at->isFuture();
    }
}
