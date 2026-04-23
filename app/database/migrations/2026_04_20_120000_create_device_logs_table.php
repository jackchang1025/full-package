<?php

declare(strict_types=1);

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_logs', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->constrained()->cascadeOnDelete();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->string('log_type', 10)->index();
            $table->text('content');
            $table->timestamp('device_timestamp')->nullable()->index();
            $table->string('device_uid', 64)->index();
            $table->timestamps();

            $table->index(['device_id', 'log_type', 'device_timestamp']);
            $table->index(['user_id', 'log_type', 'device_timestamp']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_logs');
    }
};
