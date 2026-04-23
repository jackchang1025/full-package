<?php

declare(strict_types=1);

namespace Database\Factories;

use App\Models\Device;
use App\Models\DeviceLog;
use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;

class DeviceLogFactory extends Factory
{
    protected $model = DeviceLog::class;

    public function definition(): array
    {
        $logTypes = ['ACTZ', 'KSTR', 'BLNK', 'VAPS', 'NTFS', 'ARTS', 'SEVT'];

        return [
            'device_id' => Device::factory(),
            'user_id' => User::factory(),
            'log_type' => $this->faker->randomElement($logTypes),
            'content' => $this->faker->sentence(),
            'device_timestamp' => $this->faker->dateTimeThisMonth(),
            'device_uid' => $this->faker->uuid(),
        ];
    }
}
