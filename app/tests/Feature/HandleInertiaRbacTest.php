<?php

use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Collection;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();
});

describe('Inertia shared auth data', function () {
    it('auth.user contains roles and permissions as arrays and does not contain old role field', function () {
        $user = User::factory()->create(['subscription_expires_at' => now()->addDays(30)]);
        $user->assignRole('client');

        $response = $this->actingAs($user)->get(route('dashboard'));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->has('auth')
                    ->where('auth.user.id', $user->id)
                    ->where('auth.user.username', $user->username)
                    ->has('auth.user.roles')
                    ->has('auth.user.permissions')
                    ->where('auth.user.roles', function ($v) {
                        $arr = $v instanceof Collection ? $v->all() : (array) $v;
                        return is_array($arr) && in_array('client', $arr, true);
                    })
                    ->where('auth.user.permissions', function ($v) {
                        return $v instanceof Collection || is_array($v);
                    })
                    ->where('auth.user', function ($authUser) {
                        $arr = $authUser instanceof Collection ? $authUser->toArray() : (array) $authUser;
                        expect($arr)->not->toHaveKey('role');
                        return true;
                    })
            );
    });
});
