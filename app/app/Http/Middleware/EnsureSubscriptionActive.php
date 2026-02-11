<?php

namespace App\Http\Middleware;

use App\Models\User;
use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Symfony\Component\HttpFoundation\Response;

class EnsureSubscriptionActive
{
    public function handle(Request $request, Closure $next): Response
    {
        $user = $request->user();

        if (! $user instanceof User) {
            return $next($request);
        }

        if ($user->hasActiveSubscription()) {
            return $next($request);
        }

        // 订阅已过期：Inertia/AJAX 返回 403 供前端弹框并主动退出；普通请求直接登出并重定向
        if ($request->header('X-Inertia') || $request->expectsJson()) {
            return response()->json([
                'message' => 'subscription_expired',
                'title' => __('subscription.expired.title'),
                'content' => __('subscription.expired.content'),
                'positive_text' => __('subscription.expired.positive_text'),
            ], 403);
        }

        Auth::guard('web')->logout();
        $request->session()->regenerateToken();
        $request->session()->flash('subscription_expired', true);

        return redirect()->guest(route('login'));
    }
}
