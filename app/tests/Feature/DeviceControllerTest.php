<?php

use App\Models\Device;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();
    $this->userA = User::factory()->create(['subscription_expires_at' => now()->addDays(30)]);
    $this->userA->assignRole('client');
    $this->userB = User::factory()->create(['subscription_expires_at' => now()->addDays(30)]);
    $this->userB->assignRole('client');
    $this->deviceOfB = Device::factory()->create([
        'user_id' => $this->userB->id,
        'is_removed' => false,
    ]);
    $this->deviceOfA = Device::factory()->create([
        'user_id' => $this->userA->id,
        'is_removed' => false,
    ]);
});

describe('DeviceController - only own devices', function () {
    it('returns 403 when user A tries to show user B device', function () {
        $response = $this->actingAs($this->userA)
            ->get(route('devices.show', $this->deviceOfB));

        $response->assertStatus(403);
    });

    it('returns 403 when user A tries to update user B device', function () {
        $response = $this->actingAs($this->userA)
            ->put(route('devices.update', $this->deviceOfB), [
                'remark' => 'Hacked',
            ]);

        $response->assertStatus(403);
    });

    it('returns 200 when user A shows own device', function () {
        $response = $this->actingAs($this->userA)
            ->get(route('devices.show', $this->deviceOfA));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Devices/Show', false)
                    ->has('device')
                    ->where('device.uuid', $this->deviceOfA->uuid)
            );
    });

    it('returns redirect when user A updates own device', function () {
        $response = $this->actingAs($this->userA)
            ->put(route('devices.update', $this->deviceOfA), [
                'remark' => 'My remark',
            ]);

        $response->assertRedirect();
        $this->deviceOfA->refresh();
        expect($this->deviceOfA->remark)->toBe('My remark');
    });

    it('device index returns only own devices for user A', function () {
        $response = $this->actingAs($this->userA)
            ->get(route('devices.index'));

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Devices/Index', false)
                    ->has('devices')
                    ->has('devices.data')
                    ->has('stats')
                    ->where('devices.total', 1)
                    ->where('devices.data.0.uuid', $this->deviceOfA->uuid)
            );
    });

    it('returns 403 when user A tries to destroy user B device', function () {
        $response = $this->actingAs($this->userA)
            ->delete(route('devices.destroy', $this->deviceOfB));

        $response->assertStatus(403);
    });
});
