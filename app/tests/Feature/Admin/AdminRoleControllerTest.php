<?php

use App\Models\Admin;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Spatie\Permission\Models\Role;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new \Database\Seeders\RolePermissionSeeder)->run();
    $this->admin = Admin::factory()->create();
});

describe('GET /admin/roles', function () {
    it('returns 200 and Admin/Roles/Index with roles when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.roles.index'));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Roles/Index', false)
                    ->has('roles')
                    ->has('roles.data')
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.roles.index'));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('GET /admin/roles/create', function () {
    it('returns 200 and Admin/Roles/Create with permissions when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.roles.create'));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Roles/Create', false)
                    ->has('permissions')
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.roles.create'));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('POST /admin/roles', function () {
    it('creates role with permissions and redirects to admin.roles.index when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.roles.store'), [
                'name' => 'vip',
                'permissions' => ['builds.view', 'devices.view'],
            ]);

        $response->assertRedirect(route('admin.roles.index'));
        $response->assertSessionHas('success');

        $role = Role::where('name', 'vip')->where('guard_name', 'web')->first();
        expect($role)->not->toBeNull();
        expect($role->getPermissionNames()->all())->toBe(['builds.view', 'devices.view']);
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->post(route('admin.roles.store'), [
            'name' => 'vip',
            'permissions' => [],
        ]);
        $response->assertRedirect(route('admin.login'));
    });
});

describe('GET /admin/roles/{role}/edit', function () {
    it('returns 200 and Admin/Roles/Edit with role and permissions when admin', function () {
        $role = Role::where('name', 'client')->where('guard_name', 'web')->first();

        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.roles.edit', $role));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Roles/Edit', false)
                    ->has('role')
                    ->has('permissions')
                    ->where('role.id', $role->id)
                    ->where('role.name', 'client')
            );
    });

    it('redirects to admin.login when guest', function () {
        $role = Role::where('name', 'client')->where('guard_name', 'web')->first();
        $response = $this->get(route('admin.roles.edit', $role));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('PUT /admin/roles/{role}', function () {
    it('updates role permissions and redirects to admin.roles.index when admin', function () {
        $role = Role::where('name', 'client')->where('guard_name', 'web')->first();

        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.roles.update', $role), [
                'permissions' => ['builds.view'],
            ]);

        $response->assertRedirect(route('admin.roles.index'));
        $response->assertSessionHas('success');

        $role->refresh();
        expect($role->getPermissionNames()->all())->toBe(['builds.view']);
    });

    it('redirects to admin.login when guest', function () {
        $role = Role::where('name', 'client')->where('guard_name', 'web')->first();
        $response = $this->put(route('admin.roles.update', $role), ['permissions' => []]);
        $response->assertRedirect(route('admin.login'));
    });
});

describe('DELETE /admin/roles/{role}', function () {
    it('deletes role when no users have it and redirects to admin.roles.index', function () {
        $role = Role::create(['name' => 'empty_role', 'guard_name' => 'web']);

        $response = $this->actingAs($this->admin, 'admin')
            ->delete(route('admin.roles.destroy', $role));

        $response->assertRedirect(route('admin.roles.index'));
        $response->assertSessionHas('success');
        expect(Role::where('name', 'empty_role')->exists())->toBeFalse();
    });

    it('returns back with error when role has users', function () {
        $role = Role::where('name', 'client')->where('guard_name', 'web')->first();
        $user = \App\Models\User::factory()->create();
        $user->assignRole('client');

        $response = $this->actingAs($this->admin, 'admin')
            ->delete(route('admin.roles.destroy', $role));

        $response->assertRedirect();
        $response->assertSessionHasErrors('role');
        expect(Role::where('name', 'client')->exists())->toBeTrue();
    });

    it('redirects to admin.login when guest', function () {
        $role = Role::where('name', 'client')->where('guard_name', 'web')->first();
        $response = $this->delete(route('admin.roles.destroy', $role));
        $response->assertRedirect(route('admin.login'));
    });
});
