<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('app_builds', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->foreignId('template_id')->nullable()->constrained('app_templates')->onDelete('set null');
            $table->string('package_name')->unique();
            $table->string('name', 100);
            $table->string('icon_path')->nullable();
            $table->string('file_path')->nullable();
            $table->enum('status', ['pending', 'building', 'completed', 'failed'])->default('pending');
            $table->boolean('is_custom')->default(false);
            $table->text('error_message')->nullable();
            $table->timestamps();

            $table->index(['user_id', 'status']);
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('app_builds');
    }
};
