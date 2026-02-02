<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('devices', function (Blueprint $table) {
            $table->id();
            $table->uuid('uuid')->unique();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->string('name', 100);
            $table->string('country', 100)->nullable();
            $table->string('ip_address', 45)->nullable();
            $table->string('android_version', 20)->nullable();
            $table->string('model', 100)->nullable();
            $table->string('phone_number', 50)->nullable();
            $table->unsignedTinyInteger('battery_level')->nullable();
            $table->string('network_type', 50)->nullable();
            $table->timestamp('installed_at')->nullable();
            $table->timestamp('last_seen_at')->nullable();
            $table->boolean('is_online')->default(false);
            $table->boolean('is_removed')->default(false);
            $table->boolean('is_active')->default(true);
            $table->boolean('has_accessibility')->default(false);
            $table->json('settings')->nullable();
            $table->json('permissions')->nullable();
            $table->string('session_id')->nullable();
            $table->timestamps();

            $table->index(['user_id', 'is_online']);
            $table->index('last_seen_at');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('devices');
    }
};
