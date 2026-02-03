<?php

use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Spatie\Permission\Models\Permission;
use Spatie\Permission\Models\Role;

uses(RefreshDatabase::class);

describe('RolePermissionSeeder', function () {
    it('creates client role and permissions after run', function () {
        (new RolePermissionSeeder)->run();

        expect(Role::where('guard_name', 'web')->count())->toBeGreaterThan(0);
        expect(Role::where('name', 'client')->where('guard_name', 'web')->exists())->toBeTrue();
        expect(Permission::where('guard_name', 'web')->count())->toBe(6);
    });

    it('assigns client role to users without roles', function () {
        $user = User::factory()->create();
        expect($user->getRoleNames()->isEmpty())->toBeTrue();

        (new RolePermissionSeeder)->run();

        $user->refresh();
        expect($user->hasRole('client'))->toBeTrue();
        expect($user->getRoleNames()->isNotEmpty())->toBeTrue();
    });
});

describe('User with Spatie RBAC', function () {
    beforeEach(function () {
        (new RolePermissionSeeder)->run();
    });

    it('has client role and can use permissions after assignRole', function () {
        $user = User::factory()->create();
        $user->assignRole('client');

        expect($user->hasRole('client'))->toBeTrue();
        expect($user->getRoleNames()->contains('client'))->toBeTrue();
        expect($user->can('builds.view'))->toBeTrue();
        expect($user->can('builds.create'))->toBeTrue();
        expect($user->can('devices.view'))->toBeTrue();
    });

    it('getRoleNames returns collection of role names', function () {
        $user = User::factory()->create();
        $user->assignRole('client');

        $names = $user->getRoleNames();
        expect($names->toArray())->toContain('client');
    });
});

describe('Registration assigns client role', function () {
    beforeEach(function () {
        (new RolePermissionSeeder)->run();
    });

    it('new user from registration has client role', function () {
        $response = $this->post(route('register'), [
            'username' => 'newuser',
            'email' => 'newuser@example.com',
            'password' => 'password',
            'password_confirmation' => 'password',
        ]);

        $response->assertRedirect();
        $user = User::where('email', 'newuser@example.com')->first();
        expect($user)->not->toBeNull();
        expect($user->hasRole('client'))->toBeTrue();
    });
});

describe('User-end permission enforcement', function () {
    beforeEach(function () {
        (new RolePermissionSeeder)->run();
    });

    it('returns 403 when user without devices.view accesses devices index', function () {
        $user = User::factory()->create();
        // 不分配任何角色，或分配一个没有 devices.view 的角色（当前 seeder 只有 client 且带全部权限，这里用无角色用户）
        $response = $this->actingAs($user)->get(route('devices.index'));

        $response->assertStatus(403);
    });

    it('returns 200 when user with client role accesses devices index', function () {
        $user = User::factory()->create();
        $user->assignRole('client');

        $response = $this->actingAs($user)->get(route('devices.index'));

        $response->assertStatus(200);
    });

    it('returns 403 when user without builds.view accesses builds index', function () {
        $user = User::factory()->create();

        $response = $this->actingAs($user)->get(route('builds.index'));

        $response->assertStatus(403);
    });

    it('returns 200 when user with client role accesses builds index', function () {
        $user = User::factory()->create();
        $user->assignRole('client');

        $response = $this->actingAs($user)->get(route('builds.index'));

        $response->assertStatus(200);
    });
});
