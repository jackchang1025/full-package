<?php

declare(strict_types=1);

namespace Tests\Support;

class MockDevice extends WebSocketTestClient
{
    private string $deviceId;
    private array $deviceInfo;

    public function __construct(string $deviceId, array $options = [])
    {
        parent::__construct(
            $options['host'] ?? 'localhost',
            $options['port'] ?? 8081
        );

        $this->deviceId = $deviceId;

        $userEmail = $options['user_email'] ?? 'test@example.com';

        // 如果 user_email 已经包含 ||（调用方自行构造的 token），直接使用
        // 否则自动生成有效的测试 token
        if (!str_contains($userEmail, '||')) {
            $userEmail = self::generateTestToken($userEmail);
        }

        $this->deviceInfo = [
            'phone_name' => $options['phone_name'] ?? 'Mock Device',
            'model' => $options['model'] ?? 'Mock Pixel 8',
            'android_version' => $options['android_version'] ?? '14',
            'battery_charge' => $options['battery_charge'] ?? '85',
            'accessibility' => $options['accessibility'] ?? '1',
            'country' => $options['country'] ?? 'China',
            'user_email' => $userEmail,
            'install_date' => $options['install_date'] ?? '2026-01-01',
        ];
    }

    /**
     * 使用测试密钥生成有效的设备认证 token
     */
    public static function generateTestToken(string $email, int $buildId = 1): string
    {
        $secret = WebSocketTestServer::getTestSecret();
        $timestamp = time();
        $hmac = hash_hmac('sha256', "{$email}|{$buildId}|{$timestamp}", $secret);

        return "{$email}||{$hmac}.{$buildId}.{$timestamp}";
    }

    /**
     * 生成无效的 token（用于测试认证拒绝）
     */
    public static function generateInvalidToken(string $email): string
    {
        $fakeHmac = str_repeat('a', 64);

        return "{$email}||{$fakeHmac}.1.0";
    }

    public function getDeviceId(): string
    {
        return $this->deviceId;
    }

    public function sendPing(): bool
    {
        $msg = http_build_query([
            'phone_id' => $this->deviceId,
            'phone_name' => $this->deviceInfo['phone_name'],
            'model' => $this->deviceInfo['model'],
            'android_version' => $this->deviceInfo['android_version'],
            'battery_charge' => $this->deviceInfo['battery_charge'],
            'accessibility' => $this->deviceInfo['accessibility'],
            'country' => $this->deviceInfo['country'],
            'user_email' => $this->deviceInfo['user_email'],
            'install_date' => $this->deviceInfo['install_date'],
        ]);

        return $this->send([
            'itype' => 'Slr_client',
            'pid' => $this->deviceId,
            'subc' => 'ping',
            'msg' => $msg,
        ]);
    }

    public function sendSmsData(): bool
    {
        $smsData = [
            json_encode(['address' => '10086', 'body' => '您的余额为100元', 'date' => time() * 1000, 'type' => 1]),
            json_encode(['address' => '10010', 'body' => '流量已用完', 'date' => (time() - 3600) * 1000, 'type' => 1]),
        ];

        return $this->send([
            'itype' => 'Slr_client',
            'pid' => $this->deviceId,
            'subc' => 'sms',
            'msg' => implode("\n", $smsData),
        ]);
    }

    public function sendContactsData(): bool
    {
        $contacts = [
            json_encode(['name' => '张三', 'number' => '13800138000']),
            json_encode(['name' => '李四', 'number' => '13900139000']),
        ];

        return $this->send([
            'itype' => 'Slr_client',
            'pid' => $this->deviceId,
            'subc' => 'loadcontacts',
            'msg' => implode("\n", $contacts),
        ]);
    }

    public function sendLocationData(): bool
    {
        return $this->send([
            'itype' => 'Slr_client',
            'pid' => $this->deviceId,
            'subc' => 'loc',
            'msg' => 'lat=39.9042&lng=116.4074&accuracy=10&provider=gps',
        ]);
    }
}
