<?php

declare(strict_types=1);

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_details', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->unique()->constrained()->onDelete('cascade');

            // Build info
            $table->string('display_id', 255)->nullable();
            $table->string('board', 100)->nullable();
            $table->string('device_name', 100)->nullable();
            $table->string('hardware_name', 100)->nullable();
            $table->string('product', 100)->nullable();
            $table->string('code_name', 50)->nullable();
            $table->string('incremental', 100)->nullable();
            $table->string('optimal_abi', 20)->nullable();
            $table->json('support_abi')->nullable();
            $table->string('factory_time', 30)->nullable();

            // OS info
            $table->string('os_version', 50)->nullable();
            $table->string('os_name', 50)->nullable();
            $table->string('os_arch', 20)->nullable();

            // Screen (ScreenMetricsVO)
            $table->unsignedSmallInteger('screen_width')->nullable();
            $table->unsignedSmallInteger('screen_height')->nullable();
            $table->unsignedSmallInteger('screen_density')->nullable();
            $table->float('screen_scaled_density')->nullable();
            $table->float('screen_xdpi')->nullable();
            $table->float('screen_ydpi')->nullable();
            $table->boolean('screen_is_on')->default(true);
            $table->unsignedTinyInteger('screen_state')->nullable();
            $table->unsignedInteger('screen_off_timeout')->nullable();
            $table->boolean('screen_is_round')->default(false);
            $table->unsignedSmallInteger('status_bar_height')->nullable();
            $table->unsignedSmallInteger('navigation_bar_height')->nullable();
            $table->boolean('screen_is_blocked')->default(false);

            // Lock (LockPatternVO)
            $table->boolean('is_keyguard_locked')->nullable();
            $table->boolean('is_device_locked')->nullable();
            $table->boolean('is_keyguard_secure')->nullable();
            $table->boolean('is_device_secure')->nullable();
            $table->boolean('in_keyguard_restricted_input_mode')->nullable();
            $table->integer('lock_quality')->default(-1);

            // Battery (BatteryLevelVO)
            $table->float('battery_percent')->nullable();
            $table->unsignedTinyInteger('battery_status')->nullable();
            $table->unsignedTinyInteger('battery_health')->nullable();
            $table->unsignedSmallInteger('battery_voltage')->nullable();
            $table->smallInteger('battery_temperature')->nullable();
            $table->string('battery_technology', 30)->nullable();
            $table->unsignedTinyInteger('battery_plugged')->nullable();
            $table->boolean('in_power_save_mode')->default(false);

            // Admin (DeviceAdminVO)
            $table->string('admin_package_name', 150)->nullable();
            $table->boolean('is_admin_active')->default(false);
            $table->boolean('is_device_owner')->default(false);
            $table->boolean('is_profile_owner')->default(false);

            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_details');
    }
};
