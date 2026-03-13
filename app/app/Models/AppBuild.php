<?php

namespace App\Models;

use App\Services\ApkBuilder\ApkBuildConfig;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class AppBuild extends Model
{
    protected $fillable = [
        'user_id',
        'template_id',
        'package_name',
        'name',
        'version',
        'websocket_url',
        'client_name',
        'icon_path',
        'background_path',
        'file_path',
        'is_custom',
        'build_config',
        'build_stats',
        'device_token',
        'started_at',
        'completed_at',
    ];

    protected function casts(): array
    {
        return [
            'is_custom' => 'boolean',
            'build_config' => 'array',
            'build_stats' => 'array',
            'started_at' => 'datetime',
            'completed_at' => 'datetime',
        ];
    }

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

    public function template(): BelongsTo
    {
        return $this->belongsTo(AppTemplate::class, 'template_id');
    }

    public function toBuildConfig(): ApkBuildConfig
    {
        $config = $this->build_config ?? [];

        return ApkBuildConfig::fromArray(array_merge($config, [
            'app_id' => $this->package_name,
            'user_id' => (string) $this->user_id,
            'app_name' => $this->name,
            'app_version' => $this->version ?? '1.0',
            'websocket_url' => $this->websocket_url ?? '',
            'client_name' => $this->client_name ?? '',
            'icon_path' => $this->icon_path ?? '',
            'background_path' => $this->background_path ?? 'black',
        ]));
    }

    public function getDownloadUrlAttribute(): ?string
    {
        if (empty($this->file_path)) {
            return null;
        }

        // file_path 格式: storage/app/public/apk/...
        // Web 访问路径: /storage/apk/...
        if (str_starts_with($this->file_path, 'storage/app/public/')) {
            $webPath = '/storage/' . substr($this->file_path, 19);
            return url($webPath);
        }

        return url($this->file_path);
    }

    /**
     * 获取分享页面 URL
     */
    public function getShareUrlAttribute(): string
    {
        return route('builds.download', $this->id);
    }

    public function getBuildDurationAttribute(): ?int
    {
        if (! $this->started_at) {
            return null;
        }

        $endTime = $this->completed_at ?? now();

        return $this->started_at->diffInSeconds($endTime);
    }

    /**
     * 获取图标的完整 URL
     * 数据库存储相对路径（如 /storage/icons/1/abc.png），转换为绝对 URL
     */
    public function getIconUrlAttribute(): ?string
    {
        if (empty($this->icon_path)) {
            return null;
        }

        // 新格式：相对路径（以 / 开头）
        if (str_starts_with($this->icon_path, '/')) {
            return url($this->icon_path);
        }

        // 兼容旧格式：仅文件名
        return url("/storage/icons/{$this->user_id}/{$this->icon_path}");
    }

    /**
     * 获取背景图的完整 URL
     * 数据库存储相对路径（如 /storage/backgrounds/1/abc.png），转换为绝对 URL
     */
    public function getBackgroundUrlAttribute(): ?string
    {
        if (empty($this->background_path) || $this->background_path === 'black') {
            return null;
        }

        // 新格式：相对路径（以 / 开头）
        if (str_starts_with($this->background_path, '/')) {
            return url($this->background_path);
        }

        // 兼容旧格式：仅文件名
        return url("/storage/backgrounds/{$this->user_id}/{$this->background_path}");
    }
}
