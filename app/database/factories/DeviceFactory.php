<?php

namespace Database\Factories;

use App\Models\Device;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class DeviceFactory extends Factory
{
    protected $model = Device::class;

    public function definition(): array
    {
        return [
            'uuid' => fake()->uuid(),
            'user_id' => User::factory(),
            'name' => fake()->words(2, true) . ' Phone',
            'country' => fake()->country(),
            'ip_address' => fake()->ipv4(),
            'android_version' => fake()->randomElement(['12', '13', '14', '15']),
            'model' => fake()->randomElement(['Pixel 8', 'Samsung S24', 'OnePlus 12', 'Xiaomi 14']),
            'phone_number' => fake()->phoneNumber(),
            'battery_level' => fake()->numberBetween(10, 100),
            'network_type' => fake()->randomElement(['wifi', '4g', '5g']),
            'installed_at' => fake()->dateTimeBetween('-1 year', 'now'),
            'last_seen_at' => fake()->dateTimeBetween('-1 week', 'now'),
            'is_online' => fake()->boolean(30),
            'is_removed' => false,
            'is_active' => true,
            'has_accessibility' => fake()->boolean(70),
            'settings' => [],
            'permissions' => [],
        ];
    }

    public function online(): static
    {
        return $this->state(fn (array $attributes) => [
            'is_online' => true,
            'last_seen_at' => now(),
        ]);
    }

    public function offline(): static
    {
        return $this->state(fn (array $attributes) => [
            'is_online' => false,
        ]);
    }

    public function removed(): static
    {
        return $this->state(fn (array $attributes) => [
            'is_removed' => true,
        ]);
    }
}
