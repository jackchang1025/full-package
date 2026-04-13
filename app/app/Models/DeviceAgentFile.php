<?php

declare(strict_types=1);

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class DeviceAgentFile extends Model
{
    protected $fillable = [
        'device_id',
        'file_name',
        'target_file_url',
        'file_size',
        'file_extension',
    ];

    public function device(): BelongsTo
    {
        return $this->belongsTo(Device::class);
    }
}
