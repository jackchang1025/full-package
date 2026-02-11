<?php

declare(strict_types=1);

use App\Services\PanelTokenService;

beforeEach(function () {
    config(['websocket.panel_auth.secret' => 'test-panel-secret-key']);
    config(['websocket.panel_auth.ttl' => 300]);
});

describe('PanelTokenService', function () {
    it('生成的 token 格式正确：hmac.userId.guard.timestamp', function () {
        $service = new PanelTokenService;
        $token = $service->generateToken(42, 'web');

        $parts = explode('.', $token);
        expect($parts)->toHaveCount(4)
            ->and($parts[0])->toHaveLength(64) // SHA-256 hex
            ->and($parts[1])->toBe('42')
            ->and($parts[2])->toBe('web')
            ->and((int) $parts[3])->toBeGreaterThan(0);
    });

    it('有效 token 验证通过', function () {
        $service = new PanelTokenService;
        $token = $service->generateToken(1, 'web');
        $result = $service->validateToken($token);

        expect($result['authenticated'])->toBeTrue()
            ->and($result['user_id'])->toBe(1)
            ->and($result['guard'])->toBe('web');
    });

    it('admin guard 的 token 验证通过', function () {
        $service = new PanelTokenService;
        $token = $service->generateToken(5, 'admin');
        $result = $service->validateToken($token);

        expect($result['authenticated'])->toBeTrue()
            ->and($result['user_id'])->toBe(5)
            ->and($result['guard'])->toBe('admin');
    });

    it('篡改 HMAC 的 token 验证失败', function () {
        $service = new PanelTokenService;
        $token = $service->generateToken(1, 'web');

        // Tamper with the HMAC
        $parts = explode('.', $token);
        $parts[0] = str_repeat('a', 64);
        $tampered = implode('.', $parts);

        $result = $service->validateToken($tampered);
        expect($result['authenticated'])->toBeFalse();
    });

    it('过期 token 验证失败', function () {
        $service = new PanelTokenService;
        // Generate token with timestamp 10 minutes ago
        $token = $service->generateToken(1, 'web', time() - 600);

        $result = $service->validateToken($token);
        expect($result['authenticated'])->toBeFalse()
            ->and($result['user_id'])->toBe(1)
            ->and($result['guard'])->toBe('web');
    });

    it('非法 guard 的 token 验证失败', function () {
        $service = new PanelTokenService;
        $timestamp = time();
        $hmac = hash_hmac('sha256', "1|invalid|{$timestamp}", 'test-panel-secret-key');
        $token = "{$hmac}.1.invalid.{$timestamp}";

        $result = $service->validateToken($token);
        expect($result['authenticated'])->toBeFalse();
    });

    it('空 token 验证失败', function () {
        $service = new PanelTokenService;
        $result = $service->validateToken('');

        expect($result['authenticated'])->toBeFalse()
            ->and($result['user_id'])->toBeNull()
            ->and($result['guard'])->toBeNull();
    });

    it('格式不完整的 token 验证失败', function () {
        $service = new PanelTokenService;

        expect($service->validateToken('abc.123')['authenticated'])->toBeFalse();
        expect($service->validateToken('abc')['authenticated'])->toBeFalse();
        expect($service->validateToken('a.b.c.d.e')['authenticated'])->toBeFalse();
    });

    it('不同密钥生成的 token 验证失败', function () {
        config(['websocket.panel_auth.secret' => 'secret-a']);
        $serviceA = new PanelTokenService;
        $token = $serviceA->generateToken(1, 'web');

        config(['websocket.panel_auth.secret' => 'secret-b']);
        $serviceB = new PanelTokenService;
        $result = $serviceB->validateToken($token);

        expect($result['authenticated'])->toBeFalse();
    });

    it('自定义 timestamp 参数生效', function () {
        $service = new PanelTokenService;
        $fixedTime = 1700000000;
        $token = $service->generateToken(1, 'web', $fixedTime);

        $parts = explode('.', $token);
        expect((int) $parts[3])->toBe($fixedTime);
    });
});
