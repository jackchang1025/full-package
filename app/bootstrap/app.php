<?php

use Illuminate\Foundation\Application;
use Illuminate\Foundation\Configuration\Exceptions;
use Illuminate\Foundation\Configuration\Middleware;

return Application::configure(basePath: dirname(__DIR__))
    ->withRouting(
        web: __DIR__.'/../routes/web.php',
        api: __DIR__.'/../routes/api.php',
        commands: __DIR__.'/../routes/console.php',
        channels: __DIR__.'/../routes/channels.php',
        health: '/up',
    )
    ->withMiddleware(function (Middleware $middleware): void {
        // 信任反向代理，正确识别 HTTPS
        $middleware->trustProxies(at: '*');

        $middleware->web(prepend: [
            \App\Http\Middleware\SetAppLocale::class,
        ]);
        $middleware->web(append: [
            \App\Http\Middleware\EnsureSingleSession::class,
            \App\Http\Middleware\HandleInertiaRequests::class,
        ]);

        $middleware->alias([
            'admin' => \App\Http\Middleware\EnsureAdmin::class,
            'subscription' => \App\Http\Middleware\EnsureSubscriptionActive::class,
            'permission' => \Spatie\Permission\Middleware\PermissionMiddleware::class,
            'auth.device' => \App\Http\Middleware\AuthenticateDevice::class,
        ]);

        // 排除 CSRF 验证的路由
        $middleware->validateCsrfTokens(except: [
            'builds/assets/*',
        ]);
    })
    ->withExceptions(function (Exceptions $exceptions): void {
        // 所有 /api/* 路由异常强制返回 JSON（Android 设备端无法处理 HTML 错误页）
        $exceptions->render(function (\Throwable $e, $request) {
            if (str_starts_with($request->path(), 'api/')) {
                $code = match (true) {
                    $e instanceof \Illuminate\Auth\AuthenticationException => 401,
                    method_exists($e, 'getStatusCode') => $e->getStatusCode(),
                    default => 500,
                };
                return response()->json([
                    'success' => false,
                    'code' => $code,
                    'msg' => $e->getMessage() ?: 'Internal Server Error',
                    'data' => null,
                ], $code);
            }
        });

        // Spatie 无权限：Inertia/AJAX 时返回 403 + 统一 JSON，供前端弹框提示
        $exceptions->render(function (\Spatie\Permission\Exceptions\UnauthorizedException $e, $request) {
            if ($request->header('X-Inertia') || $request->expectsJson()) {
                return response()->json([
                    'message' => 'permission_denied',
                    'title' => __('subscription.permission_denied.title'),
                    'content' => __('subscription.permission_denied.content'),
                    'positive_text' => __('subscription.permission_denied.positive_text'),
                ], 403);
            }
        });

        // 资源归属 / 子账号业务异常：Inertia/AJAX 时返回 403 + 具体错误信息
        $exceptions->render(function (\Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException $e, $request) {
            if ($request->header('X-Inertia') || $request->expectsJson()) {
                return response()->json([
                    'message' => 'access_denied',
                    'error' => $e->getMessage() ?: '无权访问该资源',
                ], 403);
            }
        });
    })->create();
