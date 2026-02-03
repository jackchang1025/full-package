<?php

use App\Models\Admin;
use App\Models\Device;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();
    $this->admin = Admin::factory()->create();
    $this->user = User::factory()->create();
    $this->user->assignRole('client');
    $this->device = Device::factory()->create([
        'user_id' => $this->user->id,
        'is_removed' => false,
    ]);
});

describe('GET /admin/devices', function () {
    it('returns 200 and Admin/Devices/Index with devices and stats when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.devices.index'));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Devices/Index', false)
                    ->has('devices')
                    ->has('stats')
                    ->has('filters')
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.devices.index'));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('GET /admin/devices/{device}/edit', function () {
    it('returns 200 and Admin/Devices/Edit with device when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.devices.edit', $this->device));

        $response->assertStatus(200)
            ->assertInertia(
                fn($page) => $page
                    ->component('Admin/Devices/Edit', false)
                    ->has('device')
                    ->where('device.uuid', $this->device->uuid)
            );
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->get(route('admin.devices.edit', $this->device));
        $response->assertRedirect(route('admin.login'));
    });
});

describe('PUT /admin/devices/{device}', function () {
    it('updates device remark and redirects to admin.devices.index when admin', function () {
        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.devices.update', $this->device), [
                'remark' => 'Updated remark by admin',
            ]);

        $response->assertRedirect(route('admin.devices.index'));
        $response->assertSessionHas('success');

        $this->device->refresh();
        expect($this->device->remark)->toBe('Updated remark by admin');
    });

    it('redirects to admin.login when guest', function () {
        $response = $this->put(route('admin.devices.update', $this->device), [
            'remark' => 'Test',
        ]);
        $response->assertRedirect(route('admin.login'));
    });
});
