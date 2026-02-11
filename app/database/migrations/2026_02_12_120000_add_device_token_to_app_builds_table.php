<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->text('device_token')->nullable()->after('build_config');
        });
    }

    public function down(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->dropColumn('device_token');
        });
    }
};
