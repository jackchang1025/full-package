<?php

use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();
});

// ── User 模型辅助方法 ──────────────────────────────

describe('User model sub-account helpers', function () {
    it('isSubAccount returns true when parent_id is set', function () {
        $parent = User::factory()->create();
        $child = User::factory()->create(['parent_id' => $parent->id]);

        expect($parent->isSubAccount())->toBeFalse();
        expect($child->isSubAccount())->toBeTrue();
    });

    it('getResourceOwnerId returns parent_id for sub-account', function () {
        $parent = User::factory()->create();
        $child = User::factory()->create(['parent_id' => $parent->id]);

        expect($parent->getResourceOwnerId())->toBe($parent->id);
        expect($child->getResourceOwnerId())->toBe($parent->id);
    });

    it('hasActiveSubscription inherits from parent for sub-accounts', function () {
        $parent = User::factory()->create([
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $child = User::factory()->create([
            'parent_id' => $parent->id,
            'subscription_expires_at' => null,
        ]);

        expect($child->hasActiveSubscription())->toBeTrue();

        // 父账号过期后子账号也不可用
        $parent->update(['subscription_expires_at' => now()->subDay()]);
        $parent->refresh();
        $child->refresh();

        expect($parent->hasActiveSubscription())->toBeFalse();
        expect($child->hasActiveSubscription())->toBeFalse();
    });

    it('canCreateSubAccount checks permission and quota', function () {
        $user = User::factory()->create(['max_sub_accounts' => 2]);
        $user->assignRole('client');
        $user->givePermissionTo('teams.manage');

        expect($user->canCreateSubAccount())->toBeTrue();

        // 创建两个子账号达到上限
        User::factory()->create(['parent_id' => $user->id]);
        User::factory()->create(['parent_id' => $user->id]);

        expect($user->canCreateSubAccount())->toBeFalse();
    });

    it('sub-accounts cannot create sub-accounts', function () {
        $parent = User::factory()->create(['max_sub_accounts' => 5]);
        $child = User::factory()->create(['parent_id' => $parent->id, 'max_sub_accounts' => 5]);
        $child->givePermissionTo('teams.manage');

        expect($child->canCreateSubAccount())->toBeFalse();
    });
});

// ── 子账号 CRUD ─────────────────────────────────

describe('SubAccount CRUD', function () {
    it('parent user can access sub-accounts index', function () {
        $user = User::factory()->create(['max_sub_accounts' => 5, 'subscription_expires_at' => now()->addDays(30)]);
        $user->assignRole('client');
        $user->givePermissionTo('teams.manage');

        $response = $this->actingAs($user)->get(route('sub-accounts.index'));

        $response->assertStatus(200);
    });

    it('user without teams.manage cannot access sub-accounts', function () {
        $user = User::factory()->create(['subscription_expires_at' => now()->addDays(30)]);
        $user->assignRole('client');

        $response = $this->actingAs($user)->get(route('sub-accounts.index'));

        $response->assertStatus(403);
    });

    it('parent user can create a sub-account', function () {
        $user = User::factory()->create([
            'max_sub_accounts' => 5,
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $user->assignRole('client');
        $user->givePermissionTo('teams.manage');

        $response = $this->actingAs($user)->post(route('sub-accounts.store'), [
            'username' => 'subuser1',
            'email' => 'sub1@example.com',
            'password' => 'password123',
            'password_confirmation' => 'password123',
            'permissions' => ['devices.view'],
        ]);

        $response->assertRedirect(route('sub-accounts.index'));

        $sub = User::where('email', 'sub1@example.com')->first();
        expect($sub)->not->toBeNull();
        expect($sub->parent_id)->toBe($user->id);
        expect($sub->isSubAccount())->toBeTrue();
        expect($sub->hasDirectPermission('devices.view'))->toBeTrue();
    });

    it('cannot create sub-account exceeding quota', function () {
        $user = User::factory()->create(['max_sub_accounts' => 1, 'subscription_expires_at' => now()->addDays(30)]);
        $user->assignRole('client');
        $user->givePermissionTo('teams.manage');
        User::factory()->create(['parent_id' => $user->id]);

        $response = $this->actingAs($user)->post(route('sub-accounts.store'), [
            'username' => 'subuser2',
            'email' => 'sub2@example.com',
            'password' => 'password123',
            'password_confirmation' => 'password123',
        ]);

        $response->assertStatus(403);
    });

    it('cannot assign permissions parent does not have', function () {
        $user = User::factory()->create(['max_sub_accounts' => 5, 'subscription_expires_at' => now()->addDays(30)]);
        // 不分配 client 角色，只给 devices.view 权限
        $user->givePermissionTo(['devices.view', 'teams.manage']);

        $response = $this->actingAs($user)->post(route('sub-accounts.store'), [
            'username' => 'subuser3',
            'email' => 'sub3@example.com',
            'password' => 'password123',
            'password_confirmation' => 'password123',
            'permissions' => ['builds.create'], // 父用户没有这个权限
        ]);

        $response->assertSessionHasErrors('permissions.0');
    });

    it('parent user can update sub-account permissions', function () {
        $user = User::factory()->create(['max_sub_accounts' => 5, 'subscription_expires_at' => now()->addDays(30)]);
        $user->assignRole('client');
        $user->givePermissionTo('teams.manage');

        $sub = User::factory()->create(['parent_id' => $user->id]);
        $sub->givePermissionTo('devices.view');

        $response = $this->actingAs($user)->put(route('sub-accounts.update', $sub), [
            'username' => $sub->username,
            'email' => $sub->email,
            'permissions' => ['devices.view', 'builds.view'],
        ]);

        $response->assertRedirect(route('sub-accounts.index'));
        $sub->refresh();
        expect($sub->hasDirectPermission('builds.view'))->toBeTrue();
    });

    it('parent user can delete sub-account', function () {
        $user = User::factory()->create(['max_sub_accounts' => 5, 'subscription_expires_at' => now()->addDays(30)]);
        $user->assignRole('client');
        $user->givePermissionTo('teams.manage');

        $sub = User::factory()->create(['parent_id' => $user->id]);

        $response = $this->actingAs($user)->delete(route('sub-accounts.destroy', $sub));

        $response->assertRedirect(route('sub-accounts.index'));
        // forceDelete: 物理删除，连 withTrashed 也查不到
        expect(User::withTrashed()->find($sub->id))->toBeNull();
    });

    it('cannot manage sub-accounts of another user', function () {
        $user1 = User::factory()->create(['max_sub_accounts' => 5, 'subscription_expires_at' => now()->addDays(30)]);
        $user1->assignRole('client');
        $user1->givePermissionTo('teams.manage');

        $user2 = User::factory()->create(['max_sub_accounts' => 5, 'subscription_expires_at' => now()->addDays(30)]);
        $user2->assignRole('client');
        $user2->givePermissionTo('teams.manage');

        $sub = User::factory()->create(['parent_id' => $user2->id]);

        $response = $this->actingAs($user1)->put(route('sub-accounts.update', $sub), [
            'username' => 'hacked',
            'email' => 'hacked@example.com',
        ]);

        $response->assertStatus(403);
    });

    it('sub-account cannot access sub-account management', function () {
        $parent = User::factory()->create(['max_sub_accounts' => 5, 'subscription_expires_at' => now()->addDays(30)]);
        $parent->assignRole('client');
        $parent->givePermissionTo('teams.manage');

        $sub = User::factory()->create(['parent_id' => $parent->id, 'subscription_expires_at' => now()->addDays(30)]);
        $sub->givePermissionTo('teams.manage');

        $response = $this->actingAs($sub)->get(route('sub-accounts.index'));

        $response->assertStatus(403);
    });
});

// ── 资源作用域 ──────────────────────────────────

describe('Sub-account resource scoping', function () {
    it('sub-account sees parent devices on dashboard', function () {
        $parent = User::factory()->create([
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $parent->assignRole('client');

        $sub = User::factory()->create([
            'parent_id' => $parent->id,
            'subscription_expires_at' => now()->addDays(30),
        ]);
        $sub->givePermissionTo('devices.view');

        // 创建属于父用户的设备
        \App\Models\Device::factory()->create(['user_id' => $parent->id]);

        $response = $this->actingAs($sub)->get(route('dashboard'));

        $response->assertStatus(200);
        $response->assertInertia(fn($page) => $page->where('stats.totalDevices', 1));
    });
});
