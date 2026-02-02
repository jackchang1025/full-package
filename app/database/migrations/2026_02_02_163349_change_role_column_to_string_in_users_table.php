<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        // MySQL 的 enum 列需要用原生 SQL 修改
        DB::statement("ALTER TABLE users MODIFY COLUMN role VARCHAR(50) NOT NULL DEFAULT 'client'");
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        // 回滚时恢复为 enum 类型
        DB::statement("ALTER TABLE users MODIFY COLUMN role ENUM('admin', 'client') NOT NULL DEFAULT 'client'");
    }
};
