<?php

use App\Models\Admin;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\Hash;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();
    $this->admin = Admin::factory()->create();
    $this->user = User::factory()->create();
    $this->user->assignRole('client');
});

/**
 * 断言重定向到 admin.users.index（允许带查询参数如 ?selected=、?expanded=）。
 */
function assertRedirectsToUsersIndex(\Illuminate\Testing\TestResponse $response): void
{
    $response->assertRedirect();
    $location = $response->headers->get('Location');
    expect($location)->toContain('/admin/users');
}

describe('GET /admin/users', function () {
    it('returns 200 and Admin/Users/Index with users and roles when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index'));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Admin/Users/Index', false)
                    ->has('users')
                    ->has('roles')
                    ->has('filters')
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.users.index'));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('GET /admin/users?selected={user} (inline edit)', function () {
    it('returns 200 and Admin/Users/Index with selectedUser data when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index', ['selected' => $this->user->id]));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Admin/Users/Index', false)
                    ->has('selectedUser')
                    ->has('selectedUser.direct_permissions')
                    ->where('selectedUser.id', $this->user->id)
                    ->where('selectedUser.username', $this->user->username)
            );
    });

    it('returns selectedUser with parent_permissions and parent_subscription_expires_at when selected is sub-account', function () {
        $parent = User::factory()->create(['subscription_expires_at' => now()->addDays(30)]);
        $parent->assignRole('client');
        $parent->givePermissionTo('devices.view');
        $sub = User::factory()->create(['parent_id' => $parent->id]);
        $sub->syncRoles($parent->getRoleNames()->values()->all());
        $sub->givePermissionTo('devices.view');

        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index', ['selected' => $sub->id]));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Admin/Users/Index', false)
                    ->where('selectedUser.id', $sub->id)
                    ->where('selectedUser.is_sub_account', true)
                    ->has('selectedUser.parent_permissions')
                    ->has('selectedUser.parent_subscription_expires_at')
                    ->where('selectedUser.parent_username', $parent->username)
            );
        $props = $response->original->getData()['page']['props'];
        expect($props['selectedUser']['parent_permissions'])->not->toContain('teams.manage');
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.users.index', ['selected' => $this->user->id]));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('GET /admin/users?search= & ?expanded=', function () {
    it('filters users by search (username/email of main or sub)', function () {
        $main = User::factory()->create(['username' => 'mainunique', 'email' => 'mainunique@example.com']);
        $main->assignRole('client');
        $sub = User::factory()->create(['parent_id' => $main->id, 'username' => 'subunique', 'email' => 'subunique@example.com']);

        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index', ['search' => 'mainunique']));

        $response->assertStatus(200);
        $users = $response->original->getData()['page']['props']['users']['data'];
        expect(collect($users)->pluck('username')->toArray())->toContain('mainunique');

        $response2 = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index', ['search' => 'subunique']));

        $response2->assertStatus(200);
        $users2 = $response2->original->getData()['page']['props']['users']['data'];
        expect(collect($users2)->first()['children'] ?? [])->not->toBeEmpty();
    });

    it('returns filters.expanded as array of ids when expanded query present', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index', ['expanded' => $this->user->id.',99']));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->has('filters.expanded')
                    ->where('filters.expanded', [$this->user->id, 99])
            );
    });
});

describe('GET /admin/users tree all_permissions (assignable to sub-account)', function () {
    it('does not include teams.manage in all_permissions for main account', function () {
        $this->user->givePermissionTo('teams.manage');

        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index'));

        $users = $response->original->getData()['page']['props']['users']['data'];
        $first = collect($users)->firstWhere('id', $this->user->id);
        expect($first)->not->toBeNull();
        expect($first['all_permissions'])->not->toContain('teams.manage');
    });
});

describe('PUT /admin/users/{user}', function () {
    it('updates user subscription and roles and redirects to admin.users.index when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'username' => $this->user->username,
                'email' => $this->user->email,
                'subscription_expires_at' => '2026-12-31',
                'roles' => ['client'],
            ]);

        assertRedirectsToUsersIndex($response);
        $response->assertSessionHas('success');

        $this->user->refresh();
        expect($this->user->subscription_expires_at?->format('Y-m-d'))->toBe('2026-12-31');
        expect($this->user->hasRole('client'))->toBeTrue();
    });

    it('updates user direct_permissions and redirects to admin.users.index when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'username' => $this->user->username,
                'email' => $this->user->email,
                'roles' => ['client'],
                'direct_permissions' => ['builds.delete'],
            ]);

        assertRedirectsToUsersIndex($response);
        $this->user->refresh();
        expect($this->user->getDirectPermissions()->pluck('name')->all())->toContain('builds.delete');
        expect($this->user->can('builds.delete'))->toBeTrue();
    });

    it('updates user username, email and password when admin', function () {
        $newUsername = 'newusername';
        $newEmail = 'newemail@example.com';
        $newPassword = 'NewSecurePassword1!';

        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'username' => $newUsername,
                'email' => $newEmail,
                'password' => $newPassword,
                'password_confirmation' => $newPassword,
                'roles' => $this->user->getRoleNames()->values()->all(),
            ]);

        assertRedirectsToUsersIndex($response);
        $response->assertSessionHas('success');

        $this->user->refresh();
        expect($this->user->username)->toBe($newUsername);
        expect($this->user->email)->toBe($newEmail);
        expect(Hash::check($newPassword, $this->user->password))->toBeTrue();
    });

    it('does not change password when password left empty', function () {
        $originalPasswordHash = $this->user->password;
        $newUsername = 'updatedname';

        $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'username' => $newUsername,
                'email' => $this->user->email,
                'roles' => $this->user->getRoleNames()->values()->all(),
            ]);

        $this->user->refresh();
        expect($this->user->username)->toBe($newUsername);
        expect($this->user->password)->toBe($originalPasswordHash);
    });

    it('returns validation redirect when username is duplicate', function () {
        $otherUser = User::factory()->create(['username' => 'otheruser']);

        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'username' => 'otheruser',
                'email' => $this->user->email,
                'roles' => $this->user->getRoleNames()->values()->all(),
            ]);

        $response->assertSessionHasErrors(['username']);
        $this->user->refresh();
        expect($this->user->username)->not->toBe('otheruser');
    });

    it('returns validation redirect when email is duplicate', function () {
        $otherUser = User::factory()->create(['email' => 'other@example.com']);

        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'username' => $this->user->username,
                'email' => 'other@example.com',
                'roles' => $this->user->getRoleNames()->values()->all(),
            ]);

        $response->assertSessionHasErrors(['email']);
        $this->user->refresh();
        expect($this->user->email)->not->toBe('other@example.com');
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->put(route('admin.users.update', $this->user), [
            'username' => $this->user->username,
            'email' => $this->user->email,
            'roles' => ['client'],
        ]);
        $response->assertRedirect(route('admin.login'));
    });

    it('sub-account: does not change subscription_expires_at or roles, only direct_permissions within parent', function () {
        $parent = User::factory()->create(['subscription_expires_at' => now()->addDays(10)]);
        $parent->assignRole('client');
        $parent->givePermissionTo(['devices.view', 'devices.edit']);
        $sub = User::factory()->create(['parent_id' => $parent->id]);
        $sub->syncRoles($parent->getRoleNames()->values()->all());
        $sub->givePermissionTo('devices.view');

        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $sub), [
                'username' => $sub->username,
                'email' => $sub->email,
                'subscription_expires_at' => '2099-12-31',
                'roles' => ['client'],
                'direct_permissions' => ['devices.edit'],
            ]);

        assertRedirectsToUsersIndex($response);
        $sub->refresh();
        expect($sub->subscription_expires_at->format('Y-m-d'))->toBe($parent->subscription_expires_at->format('Y-m-d'));
        expect($sub->getDirectPermissions()->pluck('name')->all())->toEqual(['devices.edit']);
    });

    it('sub-account: cannot be assigned teams.manage even when sent in request', function () {
        $parent = User::factory()->create();
        $parent->assignRole('client');
        $parent->givePermissionTo(['teams.manage', 'devices.view']);
        $sub = User::factory()->create(['parent_id' => $parent->id]);
        $sub->syncRoles($parent->getRoleNames()->values()->all());

        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $sub), [
                'username' => $sub->username,
                'email' => $sub->email,
                'direct_permissions' => ['teams.manage', 'devices.view'],
            ]);

        assertRedirectsToUsersIndex($response);
        $sub->refresh();
        expect($sub->getDirectPermissions()->pluck('name')->all())->not->toContain('teams.manage');
        expect($sub->getDirectPermissions()->pluck('name')->all())->toContain('devices.view');
    });
});

describe('POST /admin/users (store)', function () {
    it('creates main account with subscription and roles and redirects with selected', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'newmain',
                'email' => 'newmain@example.com',
                'password' => 'Password123!',
                'password_confirmation' => 'Password123!',
                'subscription_expires_at' => '2027-06-01',
                'roles' => ['client'],
            ]);

        assertRedirectsToUsersIndex($response);
        $response->assertSessionHas('success');
        $url = $response->headers->get('Location');
        expect($url)->toContain('selected=');

        $newUser = User::where('username', 'newmain')->first();
        expect($newUser)->not->toBeNull();
        expect($newUser->subscription_expires_at->format('Y-m-d'))->toBe('2027-06-01');
        expect($newUser->parent_id)->toBeNull();
        expect($newUser->hasRole('client'))->toBeTrue();
    });

    it('creates main account with default client role when roles not provided', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'defaultrole',
                'email' => 'defaultrole@example.com',
                'password' => 'Password123!',
                'password_confirmation' => 'Password123!',
            ]);

        assertRedirectsToUsersIndex($response);
        $newUser = User::where('username', 'defaultrole')->first();
        expect($newUser)->not->toBeNull();
        expect($newUser->hasRole('client'))->toBeTrue();
    });

    it('creates sub-account inheriting parent expiry and roles, permissions limited to parent assignable', function () {
        $parent = User::factory()->create(['subscription_expires_at' => now()->addDays(60), 'max_sub_accounts' => 5]);
        $parent->assignRole('client');
        $parent->givePermissionTo(['devices.view', 'devices.edit']);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'newsub',
                'email' => 'newsub@example.com',
                'password' => 'Password123!',
                'password_confirmation' => 'Password123!',
                'parent_id' => $parent->id,
                'direct_permissions' => ['devices.view', 'devices.edit'],
            ]);

        assertRedirectsToUsersIndex($response);
        $newSub = User::where('username', 'newsub')->first();
        expect($newSub)->not->toBeNull();
        expect($newSub->parent_id)->toBe($parent->id);
        expect($newSub->subscription_expires_at->format('Y-m-d'))->toBe($parent->subscription_expires_at->format('Y-m-d'));
        expect($newSub->getRoleNames()->values()->all())->toEqual($parent->getRoleNames()->values()->all());
        expect($newSub->getDirectPermissions()->pluck('name')->all())->toContain('devices.view', 'devices.edit');
    });

    it('sub-account cannot get teams.manage even when sent in direct_permissions', function () {
        $parent = User::factory()->create(['max_sub_accounts' => 5]);
        $parent->assignRole('client');
        $parent->givePermissionTo(['teams.manage', 'devices.view']);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'subnoteams',
                'email' => 'subnoteams@example.com',
                'password' => 'Password123!',
                'password_confirmation' => 'Password123!',
                'parent_id' => $parent->id,
                'direct_permissions' => ['teams.manage', 'devices.view'],
            ]);

        assertRedirectsToUsersIndex($response);
        $newSub = User::where('username', 'subnoteams')->first();
        expect($newSub)->not->toBeNull();
        expect($newSub->getDirectPermissions()->pluck('name')->all())->not->toContain('teams.manage');
        expect($newSub->getDirectPermissions()->pluck('name')->all())->toContain('devices.view');
    });

    it('returns error when parent account sub-account quota is exceeded', function () {
        $parent = User::factory()->create(['max_sub_accounts' => 1]);
        $parent->assignRole('client');
        // 先创建一个子账号占满配额
        User::factory()->create(['parent_id' => $parent->id]);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'overquota',
                'email' => 'overquota@example.com',
                'password' => 'Password123!',
                'password_confirmation' => 'Password123!',
                'parent_id' => $parent->id,
            ]);

        $response->assertSessionHasErrors(['parent_id']);
        expect(User::where('username', 'overquota')->exists())->toBeFalse();
    });

    it('allows creating sub-account when quota not yet reached', function () {
        $parent = User::factory()->create(['max_sub_accounts' => 2]);
        $parent->assignRole('client');
        // 先创建一个子账号，配额还有 1 个空位
        User::factory()->create(['parent_id' => $parent->id]);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'withinquota',
                'email' => 'withinquota@example.com',
                'password' => 'Password123!',
                'password_confirmation' => 'Password123!',
                'parent_id' => $parent->id,
            ]);

        assertRedirectsToUsersIndex($response);
        expect(User::where('username', 'withinquota')->exists())->toBeTrue();
    });

    it('redirect includes expanded with parent_id when creating sub-account', function () {
        $parent = User::factory()->create(['max_sub_accounts' => 5]);
        $parent->assignRole('client');

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store', [], false).'?expanded=999', [
                'username' => 'subexpand',
                'email' => 'subexpand@example.com',
                'password' => 'Password123!',
                'password_confirmation' => 'Password123!',
                'parent_id' => $parent->id,
            ]);

        $url = $response->headers->get('Location');
        expect($url)->toContain('expanded=');
        expect($url)->toContain((string) $parent->id);
    });

    it('returns validation errors when store data invalid', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => $this->user->username,
                'email' => 'invalid-email',
                'password' => 'short',
                'password_confirmation' => 'short',
            ]);

        $response->assertSessionHasErrors(['username', 'email', 'password']);
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->post(route('admin.users.store'), [
            'username' => 'guestuser',
            'email' => 'guest@example.com',
            'password' => 'Password123!',
            'password_confirmation' => 'Password123!',
        ]);
        $response->assertRedirect(route('admin.login'));
    });
});

describe('DELETE /admin/users/{user} (destroy)', function () {
    it('deletes user permanently and redirects to admin.users.index when admin', function () {
        $userId = $this->user->id;
        $response = $this->actingAs($this->admin, 'admin')
            ->delete(route('admin.users.destroy', $this->user));

        assertRedirectsToUsersIndex($response);
        $response->assertSessionHas('success');
        // forceDelete: 连 withTrashed 也查不到
        expect(User::withTrashed()->find($userId))->toBeNull();
    });

    it('redirect preserves expanded when provided', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->delete(route('admin.users.destroy', $this->user).'?expanded=1,2');

        $url = $response->headers->get('Location');
        expect($url)->toContain('expanded=');
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->delete(route('admin.users.destroy', $this->user));
        $response->assertRedirect(route('admin.login'));
        expect(User::find($this->user->id))->not->toBeNull();
    });
});

describe('admin user inline edit full flow', function () {
    it('visits index page with selected user, submits form with username email password roles and permissions, and updates user in database', function () {
        $newUsername = 'edited_username';
        $newEmail = 'edited@example.com';
        $newPassword = 'NewSecurePassword1!';
        $newRoles = ['client'];
        $newDirectPermissions = ['builds.delete', 'builds.view'];
        $newSubscriptionExpiresAt = '2026-12-31';

        $indexResponse = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index', ['selected' => $this->user->id]));

        $indexResponse->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Admin/Users/Index', false)
                    ->has('selectedUser')
                    ->has('roles')
                    ->has('permissions')
                    ->where('selectedUser.id', $this->user->id)
            );

        $updateResponse = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'username' => $newUsername,
                'email' => $newEmail,
                'password' => $newPassword,
                'password_confirmation' => $newPassword,
                'subscription_expires_at' => $newSubscriptionExpiresAt,
                'roles' => $newRoles,
                'direct_permissions' => $newDirectPermissions,
            ]);

        assertRedirectsToUsersIndex($updateResponse);
        $updateResponse->assertSessionHas('success');

        $this->user->refresh();
        expect($this->user->username)->toBe($newUsername);
        expect($this->user->email)->toBe($newEmail);
        expect(Hash::check($newPassword, $this->user->password))->toBeTrue();
        expect($this->user->subscription_expires_at?->format('Y-m-d'))->toBe($newSubscriptionExpiresAt);
        expect($this->user->getRoleNames()->values()->all())->toEqual($newRoles);
        $savedPermissions = $this->user->getDirectPermissions()->pluck('name')->values()->all();
        expect($savedPermissions)->toContain('builds.delete')->toContain('builds.view');
        expect($savedPermissions)->toHaveCount(2);
    });
});
