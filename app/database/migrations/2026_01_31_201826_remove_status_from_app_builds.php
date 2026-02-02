<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->dropColumn(['status', 'error_message']);
        });
    }

    public function down(): void
    {
        Schema::table('app_builds', function (Blueprint $table) {
            $table->string('status')->default('pending')->after('file_path');
            $table->text('error_message')->nullable()->after('build_stats');
        });
    }
};
