<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class AppTemplate extends Model
{
    protected $fillable = [
        'package_name',
        'name',
        'size',
        'version',
        'main_activity',
        'icon_path',
        'folder_path',
        'is_active',
    ];

    protected function casts(): array
    {
        return [
            'is_active' => 'boolean',
        ];
    }

    public function builds(): HasMany
    {
        return $this->hasMany(AppBuild::class, 'template_id');
    }
}
