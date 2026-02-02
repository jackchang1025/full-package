<?php

declare(strict_types=1);

namespace App\Services;

use Illuminate\Support\Facades\Log;
use MaxMind\Db\Reader;

/**
 * IP 归属地查询服务
 * 参考旧项目 legacy/src/api/Ping.php 的 getCountry 实现
 * 使用 MaxMind GeoLite2-City 数据库
 */
class GeoIpService
{
    private ?Reader $reader = null;

    public function __construct()
    {
        $path = config('geoip.database_path');

        if ($path && file_exists($path)) {
            try {
                $this->reader = new Reader($path);
            } catch (\Throwable $e) {
                Log::channel('websocket')->warning('GeoIP: Failed to load database', [
                    'path' => $path,
                    'error' => $e->getMessage(),
                ]);
            }
        } else {
            Log::channel('websocket')->warning('GeoIP: Database file not found', ['path' => $path]);
        }
    }

    /**
     * 获取 IP 归属地
     * 返回格式: "中国 上海" 或 "China" 或 "局域网"
     * 兼容：无数据库或任何异常时返回 null，不抛出
     */
    public function getLocation(string $ip): ?string
    {
        try {
            if (empty($ip) || $this->isPrivateIp($ip)) {
                return '局域网';
            }

            if ($this->reader === null) {
                return null;
            }

            $record = $this->reader->get($ip);

            if ($record === null) {
                return null;
            }

            $parts = [];

            // 国家 - 优先中文
            $country = $record['country']['names']['zh-CN'] ?? $record['country']['names']['en'] ?? null;
            if ($country) {
                $parts[] = $country;
            }

            // 省份/地区 - GeoLite2-City 有 subdivisions
            $subdivision = isset($record['subdivisions'][0])
                ? ($record['subdivisions'][0]['names']['zh-CN'] ?? $record['subdivisions'][0]['names']['en'] ?? null)
                : null;
            if ($subdivision && $subdivision !== $country) {
                $parts[] = $subdivision;
            }

            return empty($parts) ? null : implode(' ', $parts);
        } catch (\Throwable $e) {
            Log::channel('websocket')->debug('GeoIP lookup failed', ['ip' => $ip, 'error' => $e->getMessage()]);

            return null;
        }
    }

    /**
     * 获取国家名称（兼容旧项目 getCountry）
     */
    public function getCountry(string $ip): ?string
    {
        if (empty($ip) || $this->isPrivateIp($ip)) {
            return '局域网';
        }

        if ($this->reader === null) {
            return null;
        }

        try {
            $record = $this->reader->get($ip);

            if ($record === null || !isset($record['country']['names']['en'])) {
                return null;
            }

            return $record['country']['names']['en'];
        } catch (\Throwable $e) {
            Log::channel('websocket')->debug('GeoIP country lookup failed', ['ip' => $ip, 'error' => $e->getMessage()]);

            return null;
        }
    }

    private function isPrivateIp(string $ip): bool
    {
        return filter_var(
            $ip,
            FILTER_VALIDATE_IP,
            FILTER_FLAG_NO_PRIV_RANGE | FILTER_FLAG_NO_RES_RANGE
        ) === false;
    }

    public function __destruct()
    {
        if ($this->reader !== null) {
            try {
                $this->reader->close();
            } catch (\Throwable) {
                // ignore
            }
        }
    }
}
