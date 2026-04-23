<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->unsignedTinyInteger('sdk_version')->nullable()->after('android_version');
            $table->string('app_name', 100)->nullable()->after('package_name');
            $table->string('app_version', 50)->nullable()->after('app_name');
            $table->boolean('is_charging')->default(false)->after('battery_level');
            $table->unsignedSmallInteger('screen_width')->nullable()->after('network_type');
            $table->unsignedSmallInteger('screen_height')->nullable()->after('screen_width');
            $table->boolean('has_sim')->default(false)->after('screen_height');
            $table->string('phone_number2', 50)->nullable()->after('phone_number');
        });
    }

    public function down(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->dropColumn([
                'sdk_version', 'app_name', 'app_version', 'is_charging',
                'screen_width', 'screen_height', 'has_sim', 'phone_number2',
            ]);
        });
    }
};
