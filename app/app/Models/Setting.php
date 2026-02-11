<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Cache;
use Illuminate\Support\Facades\Schema;

class Setting extends Model
{
    public $timestamps = true;

    protected $fillable = ['key', 'value'];

    private const CACHE_PREFIX = 'setting.';

    private const CACHE_TTL_MINUTES = 10;

    private const TABLE_EXISTS_CACHE_KEY = 'setting.table_exists';

    /**
     * Get a setting value by key. Returns null if not found.
     */
    public static function get(string $key): ?string
    {
        if (! self::tableExists()) {
            return null;
        }

        $value = Cache::remember(
            self::cacheKey($key),
            now()->addMinutes(self::CACHE_TTL_MINUTES),
            fn () => self::query()->where('key', $key)->value('value')
        );

        return $value !== null ? (string) $value : null;
    }

    /**
     * Set a setting value. Creates or updates the row.
     */
    public static function set(string $key, ?string $value): void
    {
        if (! self::tableExists()) {
            return;
        }

        self::query()->updateOrInsert(
            ['key' => $key],
            ['value' => $value, 'updated_at' => now()]
        );

        Cache::forget(self::cacheKey($key));
    }

    /**
     * Get multiple settings at once (reduces DB queries).
     *
     * @param  array<string>  $keys
     * @return array<string, string|null>
     */
    public static function getMany(array $keys): array
    {
        if (! self::tableExists() || empty($keys)) {
            return array_fill_keys($keys, null);
        }

        $results = array_fill_keys($keys, null);
        $uncachedKeys = [];

        foreach ($keys as $key) {
            $cached = Cache::get(self::cacheKey($key));
            if ($cached !== null) {
                $results[$key] = (string) $cached;
            } else {
                $uncachedKeys[] = $key;
            }
        }

        if (! empty($uncachedKeys)) {
            $dbResults = self::query()
                ->whereIn('key', $uncachedKeys)
                ->pluck('value', 'key')
                ->all();

            foreach ($uncachedKeys as $key) {
                $value = $dbResults[$key] ?? null;
                $results[$key] = $value !== null ? (string) $value : null;
                Cache::put(self::cacheKey($key), $value, now()->addMinutes(self::CACHE_TTL_MINUTES));
            }
        }

        return $results;
    }

    /**
     * Check if settings table exists (cached to avoid repeated Schema calls).
     */
    protected static function tableExists(): bool
    {
        return Cache::remember(
            self::TABLE_EXISTS_CACHE_KEY,
            now()->addMinutes(60),
            fn () => Schema::hasTable('settings')
        );
    }

    /**
     * Generate cache key for a setting.
     */
    protected static function cacheKey(string $key): string
    {
        return self::CACHE_PREFIX.$key;
    }

    /**
     * Clear cached table existence check (call after migrations).
     */
    public static function clearTableExistsCache(): void
    {
        Cache::forget(self::TABLE_EXISTS_CACHE_KEY);
    }
}
