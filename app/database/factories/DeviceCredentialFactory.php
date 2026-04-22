<?php

declare(strict_types=1);

namespace Database\Factories;

use App\Models\Device;
use App\Models\DeviceCredential;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class DeviceCredentialFactory extends Factory
{
    protected $model = DeviceCredential::class;

    public function definition(): array
    {
        return [
            'device_id' => Device::factory(),
            'user_id' => User::factory(),
            'device_uid' => $this->faker->uuid(),
            'source' => $this->faker->randomElement(['credentials', 'cipher', 'websocket']),
            'password' => $this->faker->numerify('######'),
            'password_type' => $this->faker->randomElement(['pin', 'pattern', 'password']),
            'input_method' => 'system_auth_capture',
            'confidence' => $this->faker->numberBetween(80, 100),
            'is_locked' => true,
            'device_timestamp' => now(),
        ];
    }
}
