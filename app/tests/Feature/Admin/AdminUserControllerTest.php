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

describe('GET /admin/users', function () {
    it('returns 200 and Admin/Users/Index with users and roles when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.index'));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
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

describe('GET /admin/users/{user}/edit', function () {
    it('returns 200 and Admin/Users/Edit with user, roles, permissions and direct_permissions when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.edit', $this->user));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Users/Edit', false)
                    ->has('user')
                    ->has('roles')
                    ->has('permissions')
                    ->has('user.direct_permissions')
                    ->where('user.id', $this->user->id)
                    ->where('user.username', $this->user->username)
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.users.edit', $this->user));
        $response->assertRedirect(route('admin.login'));
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

        $response->assertRedirect(route('admin.users.index'));
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

        $response->assertRedirect(route('admin.users.index'));
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

        $response->assertRedirect(route('admin.users.index'));
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
});

describe('admin user edit page full flow', function () {
    it('visits edit page, submits form with username email password roles and permissions, and updates user in database', function () {
        $newUsername = 'edited_username';
        $newEmail = 'edited@example.com';
        $newPassword = 'NewSecurePassword1!';
        $newRoles = ['client'];
        $newDirectPermissions = ['builds.delete', 'builds.view'];
        $newSubscriptionExpiresAt = '2026-12-31';

        $editResponse = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.users.edit', $this->user));

        $editResponse->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Users/Edit', false)
                    ->has('user')
                    ->has('roles')
                    ->has('permissions')
                    ->where('user.id', $this->user->id)
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

        $updateResponse->assertRedirect(route('admin.users.index'));
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
