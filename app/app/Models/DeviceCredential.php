<?php

declare(strict_types=1);

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class DeviceCredential extends Model
{
    protected $fillable = [
        'device_id',
        'user_id',
        'device_uid',
        'source',
        'password',
        'password_type',
        'input_method',
        'app_name',
        'package_name',
        'confidence',
        'cipher_grade_code',
        'text_cipher',
        'pattern_cipher',
        'is_locked',
        'device_timestamp',
    ];

    protected function casts(): array
    {
        return [
            'confidence' => 'integer',
            'is_locked' => 'boolean',
            'device_timestamp' => 'datetime',
        ];
    }

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }
}
