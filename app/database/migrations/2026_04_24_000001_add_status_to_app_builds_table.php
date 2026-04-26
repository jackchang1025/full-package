<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->string('status', 20)->default('pending')->after('is_custom');
            $table->text('error_message')->nullable()->after('build_stats');
        });

        // 现有记录都是成功的（失败时旧代码会 delete）
        DB::table('app_builds')
            ->whereNotNull('completed_at')
            ->update(['status' => 'completed']);
    }

    public function down(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->dropColumn(['status', 'error_message']);
        });
    }
};
