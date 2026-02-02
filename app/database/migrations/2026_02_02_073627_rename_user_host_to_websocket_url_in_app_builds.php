<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->renameColumn('user_host', 'websocket_url');
            $table->dropColumn('use_wss');
        });
    }

    public function down(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->renameColumn('websocket_url', 'user_host');
            $table->boolean('use_wss')->default(false)->after('is_custom');
        });
    }
};
