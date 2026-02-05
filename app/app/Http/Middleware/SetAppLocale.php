<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

/**
 * 确保每个 Web 请求使用 config('app.locale')，使登录/校验等文案与 .env APP_LOCALE 一致。
 */
class SetAppLocale
{
    public function handle(Request $request, Closure $next): Response
    {
        app()->setLocale(config('app.locale'));

        return $next($request);
    }
}
