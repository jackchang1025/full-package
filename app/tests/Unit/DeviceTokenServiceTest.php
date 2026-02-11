<?php

declare(strict_types=1);

namespace Tests\Unit;

use App\Services\DeviceTokenService;
use Tests\TestCase;

class DeviceTokenServiceTest extends TestCase
{
    private DeviceTokenService $service;

    protected function setUp(): void
    {
        parent::setUp();
        config(['websocket.device_auth.secret' => 'test-secret-key-for-unit-tests']);
        $this->service = new DeviceTokenService;
    }

    public function test_generate_token_format(): void
    {
        $token = $this->service->generateToken('user@example.com', 42);

        $this->assertStringContains('user@example.com||', $token);

        // 验证 || 后面的格式：hmac.buildId.timestamp
        $parts = explode('||', $token, 2);
        $this->assertCount(2, $parts);
        $this->assertEquals('user@example.com', $parts[0]);

        $segments = explode('.', $parts[1], 3);
        $this->assertCount(3, $segments);
        $this->assertEquals(64, strlen($segments[0])); // SHA-256 hex = 64 chars
        $this->assertEquals('42', $segments[1]);
        $this->assertIsNumeric($segments[2]);
    }

    public function test_validate_token_success(): void
    {
        $token = $this->service->generateToken('user@example.com', 42);
        $result = $this->service->validateToken($token);

        $this->assertEquals('user@example.com', $result['email']);
        $this->assertEquals(42, $result['build_id']);
        $this->assertTrue($result['authenticated']);
    }

    public function test_validate_token_tampered_email(): void
    {
        $token = $this->service->generateToken('user@example.com', 42);
        // 篡改 email
        $tampered = str_replace('user@example.com', 'evil@example.com', $token);
        $result = $this->service->validateToken($tampered);

        $this->assertEquals('evil@example.com', $result['email']);
        $this->assertFalse($result['authenticated']);
    }

    public function test_validate_token_tampered_hmac(): void
    {
        $token = $this->service->generateToken('user@example.com', 42);
        $parts = explode('||', $token, 2);
        // 篡改 HMAC 的第一个字符
        $segments = explode('.', $parts[1], 3);
        $segments[0] = str_repeat('a', 64);
        $tampered = $parts[0].'||'.implode('.', $segments);

        $result = $this->service->validateToken($tampered);

        $this->assertFalse($result['authenticated']);
    }

    public function test_validate_no_token_rejected(): void
    {
        $result = $this->service->validateToken('user@example.com');

        $this->assertEquals('user@example.com', $result['email']);
        $this->assertNull($result['build_id']);
        $this->assertFalse($result['authenticated']);
    }

    public function test_validate_malformed_token(): void
    {
        // || 后面格式不对（缺少 segment）
        $result = $this->service->validateToken('user@example.com||badtoken');

        $this->assertEquals('user@example.com', $result['email']);
        $this->assertNull($result['build_id']);
        $this->assertFalse($result['authenticated']);
    }

    public function test_validate_empty_string(): void
    {
        $result = $this->service->validateToken('');

        $this->assertEquals('', $result['email']);
        $this->assertNull($result['build_id']);
        $this->assertFalse($result['authenticated']);
    }

    public function test_different_secrets_produce_different_tokens(): void
    {
        $token1 = $this->service->generateToken('user@example.com', 1);

        config(['websocket.device_auth.secret' => 'different-secret']);
        $service2 = new DeviceTokenService;
        $result = $service2->validateToken($token1);

        $this->assertFalse($result['authenticated']);
    }

    private function assertStringContains(string $needle, string $haystack): void
    {
        $this->assertTrue(
            str_contains($haystack, $needle),
            "Failed asserting that '{$haystack}' contains '{$needle}'"
        );
    }
}
