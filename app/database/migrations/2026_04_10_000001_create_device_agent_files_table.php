<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('device_agent_files', function (Blueprint $table) {
            $table->id();
            $table->foreignId('device_id')->constrained()->onDelete('cascade');
            $table->string('file_name', 100);
            $table->string('target_file_url', 500);
            $table->unsignedBigInteger('file_size')->nullable();
            $table->string('file_extension', 20)->nullable();
            $table->timestamps();

            $table->index('device_id');
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('device_agent_files');
    }
};
