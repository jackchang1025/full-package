<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->unsignedInteger('frpc_base_port')->nullable()->after('session_id');
            $table->timestamp('frpc_config_generated_at')->nullable()->after('frpc_base_port');
        });
    }

    public function down(): void
    {
        Schema::table('devices', function (Blueprint $table) {
            $table->dropColumn(['frpc_base_port', 'frpc_config_generated_at']);
        });
    }
};
