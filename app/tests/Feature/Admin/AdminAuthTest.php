<?php

use App\Models\Admin;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

beforeEach(function () {
    $this->admin = Admin::factory()->withPassword('Admin123!')->create([
        'email' => 'admin@test.com',
        'name' => 'Test Admin',
    ]);
});

describe('GET /admin/login (login page)', function () {
    it('returns 200 and Admin/Login Inertia when guest', function () {
        $response = $this->get(route('admin.login'));

        $response->assertStatus(200)
            ->assertInertia(fn($page) => $page->component('Admin/Login', false));
    });

    it('login page has shared Inertia props (appName, auth)', function () {
        $response = $this->get(route('admin.login'));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Login', false)
                    ->has('appName')
                    ->has('auth')
                    ->where('auth.admin', null)
            );
    });

    it('redirects to admin.dashboard when admin already logged in', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.login'));

        $response->assertRedirect(route('admin.dashboard'));
    });
});

describe('POST /admin/login', function () {
    it('redirects to admin.dashboard with valid credentials', function () {
        $response = $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);

        $response->assertRedirect(route('admin.dashboard'));
        $this->assertAuthenticatedAs($this->admin, 'admin');
    });

    it('redirects to intended URL when guest had visited protected page', function () {
        $this->get(route('admin.users.index'));
        $response = $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'Admin123!',
        ]);

        $response->assertRedirect(route('admin.users.index'));
        $this->assertAuthenticatedAs($this->admin, 'admin');
    });

    it('returns back with errors for invalid password', function () {
        $response = $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'wrong-password',
        ]);

        $response->assertSessionHasErrors('email');
        $response->assertRedirect();
        $this->assertGuest('admin');
    });

    it('returns back with errors for non-existent email', function () {
        $response = $this->post(route('admin.login'), [
            'email' => 'nonexistent@test.com',
            'password' => 'Admin123!',
        ]);

        $response->assertSessionHasErrors('email');
        $this->assertGuest('admin');
    });

    it('returns 422 or back with errors when email is empty', function () {
        $response = $this->post(route('admin.login'), [
            'email' => '',
            'password' => 'Admin123!',
        ]);

        $response->assertSessionHasErrors('email');
        $this->assertGuest('admin');
    });

    it('returns 422 or back with errors when password is empty', function () {
        $response = $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => '',
        ]);

        $response->assertSessionHasErrors('password');
        $this->assertGuest('admin');
    });

    it('returns back with errors for invalid email format', function () {
        $response = $this->post(route('admin.login'), [
            'email' => 'not-an-email',
            'password' => 'Admin123!',
        ]);

        $response->assertSessionHasErrors('email');
        $this->assertGuest('admin');
    });

    it('keeps email in old input on validation error', function () {
        $response = $this->post(route('admin.login'), [
            'email' => 'admin@test.com',
            'password' => 'wrong',
        ]);

        $response->assertSessionHasErrors('email');
        $response->assertSessionHas('_old_input.email', 'admin@test.com');
    });
});

describe('POST /admin/logout', function () {
    it('redirects to admin.login and logs out admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.logout'));

        $response->assertRedirect(route('admin.login'));
        $this->assertGuest('admin');
    });
});

describe('Admin middleware', function () {
    it('redirects guest to admin.login for GET /admin/dashboard', function () {
        $response = $this->get(route('admin.dashboard'));

        $response->assertRedirect(route('admin.login'));
    });

    it('redirects web user to admin.login for GET /admin/dashboard', function () {
        $user = User::factory()->create();
        $response = $this->actingAs($user)->get(route('admin.dashboard'));

        $response->assertRedirect(route('admin.login'));
    });

    it('returns 200 and Admin/Dashboard when admin logged in', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.dashboard'));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Dashboard', false)
                    ->has('stats')
            );
    });

    it('redirects guest to admin.login for GET /admin/users', function () {
        $response = $this->get(route('admin.users.index'));
        $response->assertRedirect(route('admin.login'));
    });

    it('redirects guest to admin.login for GET /admin/builds', function () {
        $response = $this->get(route('admin.builds.index'));
        $response->assertRedirect(route('admin.login'));
    });

    it('redirects guest to admin.login for GET /admin/devices', function () {
        $response = $this->get(route('admin.devices.index'));
        $response->assertRedirect(route('admin.login'));
    });
});
