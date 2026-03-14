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
});

it('batch destroys own devices', function () {
    $devices = Device::factory()->count(3)->create(['user_id' => $this->userA->id]);
    
    $response = $this->actingAs($this->userA)
        ->delete(route('devices.batch-destroy'), [
            'uuids' => $devices->pluck('uuid')->toArray()
        ]);
    
    $response->assertStatus(302);
    
    foreach ($devices as $device) {
        expect($device->fresh()->is_removed)->toBeTrue();
    }
});

it('cannot batch destroy other user devices', function () {
    $devicesB = Device::factory()->count(2)->create(['user_id' => $this->userB->id]);
    
    $this->actingAs($this->userA)
        ->delete(route('devices.batch-destroy'), [
            'uuids' => $devicesB->pluck('uuid')->toArray()
        ]);
    
    foreach ($devicesB as $device) {
        expect($device->fresh()->is_removed)->toBeFalse();
    }
});

it('only destroys own devices in mixed batch', function () {
    $devicesA = Device::factory()->count(2)->create(['user_id' => $this->userA->id]);
    $devicesB = Device::factory()->count(2)->create(['user_id' => $this->userB->id]);
    
    $allUuids = $devicesA->pluck('uuid')->merge($devicesB->pluck('uuid'))->toArray();
    
    $this->actingAs($this->userA)
        ->delete(route('devices.batch-destroy'), ['uuids' => $allUuids]);
    
    foreach ($devicesA as $device) {
        expect($device->fresh()->is_removed)->toBeTrue();
    }
    
    foreach ($devicesB as $device) {
        expect($device->fresh()->is_removed)->toBeFalse();
    }
});

it('requires uuids parameter', function () {
    $response = $this->actingAs($this->userA)
        ->delete(route('devices.batch-destroy'), []);
    
    $response->assertSessionHasErrors('uuids');
});
