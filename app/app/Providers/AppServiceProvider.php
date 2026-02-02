<?php

namespace App\Providers;

use App\Services\ApkBuilder\ApkBuilder;
use Illuminate\Support\ServiceProvider;

class AppServiceProvider extends ServiceProvider
{
    public function register(): void
    {
        $this->app->singleton(ApkBuilder::class);
    }

    public function boot(): void
    {
        //
    }
}
