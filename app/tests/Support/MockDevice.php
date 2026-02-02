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
        $this->deviceInfo = [
            'phone_name' => $options['phone_name'] ?? 'Mock Device',
            'model' => $options['model'] ?? 'Mock Pixel 8',
            'android_version' => $options['android_version'] ?? '14',
            'battery_charge' => $options['battery_charge'] ?? '85',
            'accessibility' => $options['accessibility'] ?? '1',
            'country' => $options['country'] ?? 'China',
            'user_email' => $options['user_email'] ?? 'test@example.com',
            'install_date' => $options['install_date'] ?? '2026-01-01',
        ];
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
