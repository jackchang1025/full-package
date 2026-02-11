<?php

use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

const USER_LOGIN_EMAIL = 'user@test.com';
const USER_LOGIN_PASSWORD = 'password';

beforeEach(function () {
    (new RolePermissionSeeder)->run();
    $this->user = User::factory()->create([
        'email' => USER_LOGIN_EMAIL,
        'username' => 'testuser',
        'subscription_expires_at' => now()->addDays(30),
    ]);
    $this->user->assignRole('client');
});

describe('GET /login (user login page)', function () {
    it('returns 200 and Auth/Login Inertia with appName when guest', function () {
        $response = $this->get(route('login'));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Auth/Login', false)
                    ->has('appName')
            );
    });

    it('redirects to dashboard when user already logged in', function () {
        $response = $this->actingAs($this->user)->get(route('login'));

        $response->assertRedirect(route('dashboard'));
    });
});

describe('POST /login (user login)', function () {
    it('redirects to dashboard with valid credentials', function () {
        $response = $this->post(route('login'), [
            'email' => USER_LOGIN_EMAIL,
            'password' => USER_LOGIN_PASSWORD,
        ]);

        $response->assertRedirect(route('dashboard'));
        $this->assertAuthenticatedAs($this->user);
    });

    it('redirects to intended URL when guest had visited protected page', function () {
        $this->get(route('dashboard'));
        $response = $this->post(route('login'), [
            'email' => USER_LOGIN_EMAIL,
            'password' => USER_LOGIN_PASSWORD,
        ]);

        $response->assertRedirect(route('dashboard'));
        $this->assertAuthenticatedAs($this->user);
    });

    it('accepts remember checkbox and redirects to dashboard', function () {
        $response = $this->post(route('login'), [
            'email' => USER_LOGIN_EMAIL,
            'password' => USER_LOGIN_PASSWORD,
            'remember' => true,
        ]);

        $response->assertRedirect(route('dashboard'));
        $this->assertAuthenticatedAs($this->user);
    });

    it('returns back with errors and keeps email in old input on invalid password', function () {
        $response = $this->post(route('login'), [
            'email' => USER_LOGIN_EMAIL,
            'password' => 'wrong-password',
        ]);

        $response->assertSessionHasErrors('email');
        $response->assertSessionHas('_old_input.email', USER_LOGIN_EMAIL);
        $response->assertRedirect();
        $this->assertGuest();
    });

    it('returns back with errors for invalid credentials or validation', function (string $email, string $password, array $expectedErrors) {
        $response = $this->post(route('login'), [
            'email' => $email,
            'password' => $password,
        ]);

        $response->assertSessionHasErrors($expectedErrors);
        $this->assertGuest();
    })->with([
        'non-existent email' => ['nonexistent@test.com', USER_LOGIN_PASSWORD, ['email']],
        'empty email' => ['', USER_LOGIN_PASSWORD, ['email']],
        'empty password' => [USER_LOGIN_EMAIL, '', ['password']],
        'invalid email format' => ['not-an-email', USER_LOGIN_PASSWORD, ['email']],
    ]);
});

describe('POST /logout (user)', function () {
    it('redirects and logs out user', function () {
        $response = $this->actingAs($this->user)->post(route('logout'));

        $response->assertRedirect();
        $this->assertGuest();
    });
});

describe('User auth middleware', function () {
    it('redirects guest to login for GET dashboard', function () {
        $response = $this->get(route('dashboard'));

        $response->assertRedirect(route('login'));
    });

    it('returns 200 and Dashboard/Index when user with active subscription is logged in', function () {
        $response = $this->actingAs($this->user)->get(route('dashboard'));

        $response->assertStatus(200)
            ->assertInertia(fn ($page) => $page->component('Dashboard/Index', false));
    });

    it('redirects guest to login for protected user routes', function (string $routeName) {
        $response = $this->get(route($routeName));

        $response->assertRedirect(route('login'));
    })->with(['devices.index', 'builds.index']);
});
