<?php

use App\Models\Admin;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;

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
                'subscription_expires_at' => '2026-12-31',
                'subscription_type' => '12_month',
                'roles' => ['client'],
            ]);

        $response->assertRedirect(route('admin.users.index'));
        $response->assertSessionHas('success');

        $this->user->refresh();
        expect($this->user->subscription_expires_at?->format('Y-m-d'))->toBe('2026-12-31');
        expect($this->user->subscription_type)->toBe('12_month');
        expect($this->user->hasRole('client'))->toBeTrue();
    });

    it('updates user direct_permissions and redirects to admin.users.index when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.users.update', $this->user), [
                'roles' => ['client'],
                'direct_permissions' => ['builds.delete'],
            ]);

        $response->assertRedirect(route('admin.users.index'));
        $this->user->refresh();
        expect($this->user->getDirectPermissions()->pluck('name')->all())->toContain('builds.delete');
        expect($this->user->can('builds.delete'))->toBeTrue();
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->put(route('admin.users.update', $this->user), [
            'roles' => ['client'],
        ]);
        $response->assertRedirect(route('admin.login'));
    });
});
