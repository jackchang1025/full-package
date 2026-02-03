<?php

namespace Database\Factories;

use App\Models\Admin;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Facades\Hash;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Admin>
 */
class AdminFactory extends Factory
{
    protected static ?string $password = null;

    public function definition(): array
    {
        $email = fake()->unique()->safeEmail();

        return [
            'name' => fake()->name(),
            'email' => $email,
            'email_encrypted' => base64_encode($email),
            'password' => static::$password ??= Hash::make('password'),
        ];
    }

    public function withPassword(string $password): static
    {
        return $this->state(fn() => [
            'password' => Hash::make($password),
        ]);
    }
}
