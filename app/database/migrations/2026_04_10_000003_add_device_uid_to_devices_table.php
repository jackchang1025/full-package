<?php

declare(strict_types=1);

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->string('device_uid', 64)->nullable()->index()->after('uuid');
            $table->string('brand', 50)->nullable()->after('model');
            $table->string('manufacturer', 100)->nullable()->after('brand');
            $table->string('fingerprint', 255)->nullable()->after('manufacturer');
            $table->string('serial', 64)->nullable()->after('fingerprint');
            $table->string('package_name', 150)->nullable()->after('serial');
            $table->boolean('is_root')->default(false)->after('has_accessibility');
            $table->boolean('enable_development')->default(false)->after('is_root');
            $table->boolean('enable_debug')->default(false)->after('enable_development');
            $table->boolean('enable_wifi_debug')->default(false)->after('enable_debug');
            $table->string('lang_code', 20)->nullable()->after('network_type');
            $table->string('trustee_id', 100)->nullable()->after('lang_code');
        });
    }

    public function down(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->dropIndex(['device_uid']);
            $table->dropColumn([
                'device_uid',
                'brand',
                'manufacturer',
                'fingerprint',
                'serial',
                'package_name',
                'is_root',
                'enable_development',
                'enable_debug',
                'enable_wifi_debug',
                'lang_code',
                'trustee_id',
            ]);
        });
    }
};
