<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->string('version', 20)->default('1.0')->after('name');
            $table->string('user_host')->nullable()->after('version');
            $table->string('client_name')->nullable()->after('user_host');
            $table->string('background_path')->nullable()->after('icon_path');
            $table->boolean('use_wss')->default(false)->after('is_custom');
            $table->json('build_config')->nullable()->after('use_wss');
            $table->json('build_stats')->nullable()->after('build_config');
            $table->timestamp('started_at')->nullable()->after('error_message');
            $table->timestamp('completed_at')->nullable()->after('started_at');
        });
    }

    public function down(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->dropColumn([
                'version',
                'user_host',
                'client_name',
                'background_path',
                'use_wss',
                'build_config',
                'build_stats',
                'started_at',
                'completed_at',
            ]);
        });
    }
};
