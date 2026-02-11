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

describe('Admin logout does not affect user session', function () {
    it('keeps user logged in when admin logs out', function () {
        // 同时登录 user 和 admin
        session()->put('login_web_'.sha1('Illuminate\Auth\SessionGuard'), $this->user->id);
        session()->put('login_admin_'.sha1('Illuminate\Auth\SessionGuard'), $this->admin->id);

        // 以 admin 身份登出
        $this->actingAs($this->admin, 'admin')
            ->post(route('admin.logout'));

        // admin 已登出
        $this->assertGuest('admin');

        // user 仍然保持登录（session 中的 web guard key 未被清除）
        expect(session()->has('login_web_'.sha1('Illuminate\Auth\SessionGuard')))->toBeTrue();
    });

    it('does not invalidate entire session when admin logs out', function () {
        $this->actingAs($this->admin, 'admin');

        // 在 session 中放入自定义数据
        session()->put('custom_data', 'should_survive');

        $this->post(route('admin.logout'));

        // session 中的自定义数据应该保留
        expect(session()->get('custom_data'))->toBe('should_survive');
    });
});

describe('User logout does not affect admin session', function () {
    it('keeps admin logged in when user logs out', function () {
        // 同时登录 user 和 admin
        session()->put('login_web_'.sha1('Illuminate\Auth\SessionGuard'), $this->user->id);
        session()->put('login_admin_'.sha1('Illuminate\Auth\SessionGuard'), $this->admin->id);

        // 以 user 身份登出
        $this->actingAs($this->user)
            ->post(route('logout'));

        // user 已登出
        $this->assertGuest('web');

        // admin 的 session key 仍然存在
        expect(session()->has('login_admin_'.sha1('Illuminate\Auth\SessionGuard')))->toBeTrue();
    });

    it('does not invalidate entire session when user logs out', function () {
        $this->actingAs($this->user);

        session()->put('custom_data', 'should_survive');

        $this->post(route('logout'));

        expect(session()->get('custom_data'))->toBe('should_survive');
    });
});

describe('Subscription expiry does not affect admin session', function () {
    it('keeps admin session when user subscription expires', function () {
        // 创建订阅过期的用户
        $expiredUser = User::factory()->create([
            'email' => 'expired@test.com',
            'username' => 'expireduser',
            'subscription_expires_at' => now()->subDay(),
        ]);
        $expiredUser->assignRole('client');

        // 在 session 中设置 admin 的登录状态
        session()->put('login_admin_'.sha1('Illuminate\Auth\SessionGuard'), $this->admin->id);

        // 以过期用户身份访问受保护页面（非 Inertia 请求，触发重定向登出）
        $response = $this->actingAs($expiredUser)
            ->get(route('dashboard'));

        $response->assertRedirect(route('login'));

        // admin 的 session key 应该保留
        expect(session()->has('login_admin_'.sha1('Illuminate\Auth\SessionGuard')))->toBeTrue();
    });
});

describe('CSRF token is regenerated after logout', function () {
    it('regenerates CSRF token when admin logs out', function () {
        $this->actingAs($this->admin, 'admin');
        $tokenBefore = session()->token();

        $this->post(route('admin.logout'));

        expect(session()->token())->not->toBe($tokenBefore);
    });

    it('regenerates CSRF token when user logs out', function () {
        $this->actingAs($this->user);
        $tokenBefore = session()->token();

        $this->post(route('logout'));

        expect(session()->token())->not->toBe($tokenBefore);
    });
});
