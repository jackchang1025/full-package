<?php

use App\Models\Admin;
use App\Models\User;
use App\Services\PanelTokenService;
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

    config(['websocket.panel_auth.secret' => 'test-secret']);
    config(['websocket.panel_auth.ttl' => 300]);
});

// ---------- 用户端 /ws-token ----------

describe('GET /ws-token (user route)', function () {
    it('returns web guard token when user is logged in', function () {
        $response = $this->actingAs($this->user)
            ->getJson(route('ws.token'));

        $response->assertOk()->assertJsonStructure(['token']);

        $token = $response->json('token');
        $parts = explode('.', $token);
        expect($parts)->toHaveCount(4)
            ->and($parts[1])->toBe((string) $this->user->id)
            ->and($parts[2])->toBe('web');
    });

    it('returns web guard token even when admin is also logged in (session overlap)', function () {
        // 模拟同一浏览器同时登录 admin 和 user 的场景
        session()->put('login_admin_'.sha1('Illuminate\Auth\SessionGuard'), $this->admin->id);

        $response = $this->actingAs($this->user)
            ->withSession(['login_admin_'.sha1('Illuminate\Auth\SessionGuard') => $this->admin->id])
            ->getJson(route('ws.token'));

        $response->assertOk()->assertJsonStructure(['token']);

        $parts = explode('.', $response->json('token'));
        expect($parts[1])->toBe((string) $this->user->id)
            ->and($parts[2])->toBe('web');
    });

    it('returns 401 when guest accesses user ws-token route', function () {
        $response = $this->getJson(route('ws.token'));

        // auth middleware 会拦截，可能返回 401 或重定向
        expect($response->status())->toBeIn([401, 302]);
    });

    it('generated token can be validated by PanelTokenService', function () {
        $response = $this->actingAs($this->user)
            ->getJson(route('ws.token'));

        $response->assertOk();

        $service = new PanelTokenService;
        $result = $service->validateToken($response->json('token'));

        expect($result['authenticated'])->toBeTrue()
            ->and($result['user_id'])->toBe($this->user->id)
            ->and($result['guard'])->toBe('web');
    });

    it('returns token with correct user id for different users', function () {
        $anotherUser = User::factory()->create([
            'email' => 'another@test.com',
            'username' => 'another',
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $anotherUser->assignRole('client');

        $response = $this->actingAs($anotherUser)
            ->getJson(route('ws.token'));

        $response->assertOk();
        $parts = explode('.', $response->json('token'));
        expect($parts[1])->toBe((string) $anotherUser->id)
            ->and($parts[2])->toBe('web');
    });
});

// ---------- 管理端 /admin/ws-token ----------

describe('GET /admin/ws-token (admin route)', function () {
    it('returns admin guard token when admin is logged in', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->getJson(route('admin.ws.token'));

        $response->assertOk()->assertJsonStructure(['token']);

        $parts = explode('.', $response->json('token'));
        expect($parts[1])->toBe((string) $this->admin->id)
            ->and($parts[2])->toBe('admin');
    });

    it('returns admin guard token even when user is also logged in (session overlap)', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->withSession(['login_web_'.sha1('Illuminate\Auth\SessionGuard') => $this->user->id])
            ->getJson(route('admin.ws.token'));

        $response->assertOk()->assertJsonStructure(['token']);

        $parts = explode('.', $response->json('token'));
        expect($parts[1])->toBe((string) $this->admin->id)
            ->and($parts[2])->toBe('admin');
    });

    it('returns 401 or redirect when guest accesses admin ws-token route', function () {
        $response = $this->getJson(route('admin.ws.token'));

        expect($response->status())->toBeIn([401, 302]);
    });

    it('returns 401 or redirect when web user accesses admin ws-token route', function () {
        $response = $this->actingAs($this->user)
            ->getJson(route('admin.ws.token'));

        expect($response->status())->toBeIn([401, 302]);
    });

    it('generated admin token can be validated by PanelTokenService', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->getJson(route('admin.ws.token'));

        $response->assertOk();

        $service = new PanelTokenService;
        $result = $service->validateToken($response->json('token'));

        expect($result['authenticated'])->toBeTrue()
            ->and($result['user_id'])->toBe($this->admin->id)
            ->and($result['guard'])->toBe('admin');
    });
});

// ---------- Guard 隔离：核心安全场景 ----------

describe('Guard isolation (the bug scenario)', function () {
    it('user route never returns admin guard token regardless of admin session', function () {
        // 这是修复前的 bug 场景：同时登录 admin + user，用户端 ws-token 错误返回 admin token
        $response = $this->actingAs($this->user)
            ->withSession(['login_admin_'.sha1('Illuminate\Auth\SessionGuard') => $this->admin->id])
            ->getJson(route('ws.token'));

        $response->assertOk();

        $parts = explode('.', $response->json('token'));
        // guard 必须是 web，不能是 admin
        expect($parts[2])->toBe('web')
            ->and($parts[1])->toBe((string) $this->user->id);
    });

    it('admin route never returns web guard token regardless of user session', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->withSession(['login_web_'.sha1('Illuminate\Auth\SessionGuard') => $this->user->id])
            ->getJson(route('admin.ws.token'));

        $response->assertOk();

        $parts = explode('.', $response->json('token'));
        // guard 必须是 admin，不能是 web
        expect($parts[2])->toBe('admin')
            ->and($parts[1])->toBe((string) $this->admin->id);
    });

    it('user token userId matches the authenticated user, not the admin', function () {
        $response = $this->actingAs($this->user)
            ->withSession(['login_admin_'.sha1('Illuminate\Auth\SessionGuard') => $this->admin->id])
            ->getJson(route('ws.token'));

        $service = new PanelTokenService;
        $result = $service->validateToken($response->json('token'));

        expect($result['user_id'])->toBe($this->user->id)
            ->and($result['user_id'])->not->toBe($this->admin->id);
    });
});

// ---------- 子账号场景 ----------

describe('Sub-account token generation', function () {
    it('returns token with sub-account user id (not parent id)', function () {
        $parent = User::factory()->create([
            'email' => 'parent@test.com',
            'username' => 'parent',
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $parent->assignRole('client');

        $subAccount = User::factory()->create([
            'email' => 'sub@test.com',
            'username' => 'subuser',
            'parent_id' => $parent->id,
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $subAccount->assignRole('client');

        $response = $this->actingAs($subAccount)
            ->getJson(route('ws.token'));

        $response->assertOk();

        $parts = explode('.', $response->json('token'));
        // token 中的 userId 是子账号自身的 id
        expect($parts[1])->toBe((string) $subAccount->id)
            ->and($parts[2])->toBe('web');
    });
});

// ---------- Token 响应格式 ----------

describe('Token response format', function () {
    it('user route response contains only token key', function () {
        $response = $this->actingAs($this->user)
            ->getJson(route('ws.token'));

        $response->assertOk()
            ->assertJsonStructure(['token'])
            ->assertJsonMissingPath('error');
    });

    it('admin route response contains only token key', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->getJson(route('admin.ws.token'));

        $response->assertOk()
            ->assertJsonStructure(['token'])
            ->assertJsonMissingPath('error');
    });

    it('each request generates a different token (timestamp changes)', function () {
        $response1 = $this->actingAs($this->user)->getJson(route('ws.token'));
        sleep(1);
        $response2 = $this->actingAs($this->user)->getJson(route('ws.token'));

        expect($response1->json('token'))->not->toBe($response2->json('token'));
    });
});
