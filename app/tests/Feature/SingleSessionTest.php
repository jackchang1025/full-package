<?php

use App\Models\Admin;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();

    $this->admin = Admin::factory()->withPassword('Admin123!')->create([
        'email' => 'admin@test.com',
        'name' => 'Test Admin',
    ]);

    $this->user = User::factory()->create([
        'email' => 'user@test.com',
        'username' => 'testuser',
        'subscription_expires_at' => now()->addDays(30),
    ]);
    $this->user->assignRole('client');
});

// ── User (web guard) ─────────────────────────────────────

describe('User login generates session_token', function () {
    it('stores session_token in DB and session after login', function () {
        $response = $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);

        $response->assertRedirect();
        $this->assertAuthenticated('web');

        $this->user->refresh();
        expect($this->user->session_token)->not->toBeNull();
        expect($this->user->session_token)->toHaveLength(64);
        expect(session('single_session_token_web'))->toBe($this->user->session_token);
    });

    it('generates a new session_token on each login', function () {
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);
        $this->user->refresh();
        $tokenFirst = $this->user->session_token;

        $this->post(route('logout'));
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);
        $this->user->refresh();
        $tokenSecond = $this->user->session_token;

        expect($tokenFirst)->not->toBe($tokenSecond);
    });
});

describe('User single session enforcement', function () {
    it('returns 409 with session_kicked for Inertia request when session is invalidated', function () {
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);

        $this->user->refresh();
        expect(session('single_session_token_web'))->toBe($this->user->session_token);

        // Simulate another device login by changing DB token directly
        $this->user->update(['session_token' => bin2hex(random_bytes(32))]);

        $response = $this->get(route('dashboard'), [
            'X-Inertia' => 'true',
            'X-Inertia-Version' => '1.0',
        ]);

        $response->assertStatus(409);
        $response->assertJson([
            'message' => 'session_kicked',
            'guard' => 'web',
        ]);
    });

    it('redirects to login with flash error for non-Inertia request when session is invalidated', function () {
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);

        $this->user->update(['session_token' => bin2hex(random_bytes(32))]);

        $response = $this->get(route('dashboard'));

        $response->assertRedirect(route('login'));
        $response->assertSessionHas('error');
    });

    it('does not kick session when token matches', function () {
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);

        $response = $this->get(route('dashboard'));

        $response->assertStatus(200);
    });

    it('logs out user after being kicked', function () {
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);

        $this->user->update(['session_token' => bin2hex(random_bytes(32))]);

        $this->get(route('dashboard'));

        $this->assertGuest('web');
    });
});

describe('User backward compatibility', function () {
    it('allows old sessions when both session and DB have no token', function () {
        $this->actingAs($this->user);

        $response = $this->get(route('dashboard'));

        $response->assertStatus(200);
    });

    it('kicks old session without token when DB already has a token from another device', function () {
        $this->user->update(['session_token' => bin2hex(random_bytes(32))]);

        $this->actingAs($this->user);

        $response = $this->get(route('dashboard'));

        $response->assertRedirect(route('login'));
    });
});

// ── Admin guard ──────────────────────────────────────────

describe('Admin login generates session_token', function () {
    it('stores session_token in DB and session after login', function () {
        $response = $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);

        $response->assertRedirect();
        $this->assertAuthenticated('admin');

        $this->admin->refresh();
        expect($this->admin->session_token)->not->toBeNull();
        expect($this->admin->session_token)->toHaveLength(64);
        expect(session('single_session_token_admin'))->toBe($this->admin->session_token);
    });
});

describe('Admin single session enforcement', function () {
    it('returns 409 with session_kicked for Inertia request when admin session is invalidated', function () {
        $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);

        $this->admin->refresh();
        expect(session('single_session_token_admin'))->toBe($this->admin->session_token);

        $this->admin->update(['session_token' => bin2hex(random_bytes(32))]);

        $response = $this->get(route('admin.dashboard'), [
            'X-Inertia' => 'true',
            'X-Inertia-Version' => '1.0',
        ]);

        $response->assertStatus(409);
        $response->assertJson([
            'message' => 'session_kicked',
            'guard' => 'admin',
        ]);
    });

    it('redirects to admin login with flash error for non-Inertia request when session is invalidated', function () {
        $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);

        $this->admin->update(['session_token' => bin2hex(random_bytes(32))]);

        $response = $this->get(route('admin.dashboard'));

        $response->assertRedirect(route('admin.login'));
        $response->assertSessionHas('error');
    });

    it('does not kick admin session when token matches', function () {
        $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);

        $response = $this->get(route('admin.dashboard'));

        $response->assertStatus(200);
    });

    it('logs out admin after being kicked', function () {
        $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);

        $this->admin->update(['session_token' => bin2hex(random_bytes(32))]);

        $this->get(route('admin.dashboard'));

        $this->assertGuest('admin');
    });
});

// ── Guest requests ───────────────────────────────────────

describe('Guest requests are not affected by single session middleware', function () {
    it('does not interfere with guest accessing login page', function () {
        $response = $this->get(route('login'));
        $response->assertStatus(200);
    });

    it('does not interfere with guest accessing admin login page', function () {
        $response = $this->get(route('admin.login'));
        $response->assertStatus(200);
    });
});

// ── Concurrent login scenarios ───────────────────────────

describe('Concurrent login scenarios', function () {
    it('second device login invalidates first device session', function () {
        // Device A 登录
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);
        $this->user->refresh();
        $tokenA = $this->user->session_token;
        $sessionA = session()->getId();

        // Device B 登录（模拟新 session）
        $this->post(route('logout'));
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);
        $this->user->refresh();
        $tokenB = $this->user->session_token;

        expect($tokenA)->not->toBe($tokenB);

        // Device A 的 session 已失效（token 不匹配）
        session()->setId($sessionA);
        session()->put('single_session_token_web', $tokenA);
        $this->actingAs($this->user);

        $response = $this->get(route('dashboard'));
        $response->assertRedirect(route('login'));
    });

    it('rapid successive logins generate different tokens', function () {
        $tokens = [];

        for ($i = 0; $i < 3; $i++) {
            $this->post(route('login'), [
                'email' => 'user@test.com',
                'password' => 'password',
            ]);
            $this->user->refresh();
            $tokens[] = $this->user->session_token;
            $this->post(route('logout'));
        }

        expect($tokens[0])->not->toBe($tokens[1]);
        expect($tokens[1])->not->toBe($tokens[2]);
        expect($tokens[0])->not->toBe($tokens[2]);
    });
});

// ── Session expiry scenarios ─────────────────────────────

describe('Session expiry scenarios', function () {
    it('expired session with valid token does not trigger kick', function () {
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);

        // Session 过期（Laravel 自动处理），用户需重新登录
        $this->post(route('logout'));

        $response = $this->get(route('dashboard'));
        $response->assertRedirect(route('login'));

        // 不是被"踢出"，而是正常的未认证重定向
        $response->assertSessionMissing('error');
    });

    it('session token persists after logout', function () {
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);
        $this->user->refresh();
        $tokenBeforeLogout = $this->user->session_token;

        $this->post(route('logout'));

        $this->user->refresh();
        expect($this->user->session_token)->toBe($tokenBeforeLogout);
    });
});

// ── Dual guard edge cases ────────────────────────────────

describe('Dual guard edge cases', function () {
    it('web guard kick flushes session affecting both guards', function () {
        // 同时登录 web 和 admin
        $this->actingAs($this->user, 'web');
        $this->actingAs($this->admin, 'admin');

        session()->put('single_session_token_web', 'valid_web_token');
        session()->put('single_session_token_admin', 'valid_admin_token');

        // 模拟 web token 失效
        $this->user->update(['session_token' => bin2hex(random_bytes(32))]);

        // 访问需要 web guard 的路由
        $response = $this->get(route('dashboard'));

        // web guard 被踢出，session 被 flush，admin token 也丢失
        $response->assertRedirect(route('login'));
        expect(session()->has('single_session_token_admin'))->toBeFalse();
    });

    it('admin guard kick flushes session affecting both guards', function () {
        // 同时登录 web 和 admin
        $this->actingAs($this->user, 'web');
        $this->actingAs($this->admin, 'admin');

        session()->put('single_session_token_web', 'valid_web_token');
        session()->put('single_session_token_admin', 'valid_admin_token');

        // 模拟 admin token 失效
        $this->admin->update(['session_token' => bin2hex(random_bytes(32))]);

        // 访问需要 admin guard 的路由
        $response = $this->get(route('admin.dashboard'));

        // admin guard 被踢出，session 被 flush
        // 但因为 web guard 先被检查（且通过），所以先返回 web 的登录页
        $response->assertRedirect(route('login'));
        expect(session()->has('single_session_token_web'))->toBeFalse();
    });

    it('middleware checks web guard first then admin guard', function () {
        // 只登录 admin，不登录 web
        $this->actingAs($this->admin, 'admin');
        session()->put('single_session_token_admin', 'invalid_admin');
        $this->admin->update(['session_token' => 'valid_admin']);

        // 访问 admin 路由
        $response = $this->get(route('admin.dashboard'));

        // admin guard 被踢出
        $response->assertRedirect(route('admin.login'));
        $this->assertGuest('admin');
    });

    it('web guard passes then admin guard is checked', function () {
        // 登录 web（token 有效）和 admin（token 无效）
        $this->post(route('login'), [
            'email' => 'user@test.com',
            'password' => 'password',
        ]);
        $this->user->refresh();

        $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);
        $this->admin->refresh();

        // 模拟 admin token 失效
        $this->admin->update(['session_token' => bin2hex(random_bytes(32))]);

        // 访问 admin 路由
        $response = $this->get(route('admin.dashboard'));

        // web guard 通过，admin guard 被踢出
        $response->assertRedirect(route('admin.login'));
        $this->assertGuest('admin');
        // session 被 flush，所有 session 数据丢失
        expect(session()->has('single_session_token_web'))->toBeFalse();
    });
});
