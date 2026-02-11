<?php

use App\Models\Admin;
use App\Models\AppBuild;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();
    $this->admin = Admin::factory()->create();
    $this->user = User::factory()->create();
    $this->user->assignRole('client');
    $this->build = AppBuild::create([
        'user_id' => $this->user->id,
        'name' => 'Test Build',
        'package_name' => 'com.test.build.'.uniqid(),
        'version' => '1.0',
    ]);
});

describe('GET /admin/builds', function () {
    it('returns 200 and Admin/Builds/Index with builds when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.builds.index'));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Admin/Builds/Index', false)
                    ->has('builds')
                    ->has('filters')
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.builds.index'));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('GET /admin/builds/{build}', function () {
    it('returns 200 and Admin/Builds/Show with build when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.builds.show', $this->build));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Admin/Builds/Show', false)
                    ->has('build')
                    ->where('build.id', $this->build->id)
                    ->where('build.name', 'Test Build')
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.builds.show', $this->build));
        $response->assertRedirect(route('admin.login'));
    });
});
