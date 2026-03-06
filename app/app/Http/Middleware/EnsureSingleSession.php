<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Symfony\Component\HttpFoundation\Response;

/**
 * 单点登录中间件：确保同一账号只能在一个设备上登录。
 *
 * 登录时将随机 token 写入模型的 session_token 字段和当前 session。
 * 每次请求比对两者，不一致说明该账号已在其他设备登录，返回 409。
 */
class EnsureSingleSession
{
    public function handle(Request $request, Closure $next): Response
    {
        foreach (['web', 'admin'] as $guard) {
            $user = Auth::guard($guard)->user();

            if (! $user) {
                continue;
            }

            $sessionKey = "single_session_token_{$guard}";
            $sessionToken = $request->session()->get($sessionKey);
            
            // 刷新模型以获取最新的 session_token
            $user->refresh();
            $dbToken = $user->session_token;

            if ($sessionToken === null && $dbToken === null) {
                continue;
            }

            if ($sessionToken !== null && $dbToken === $sessionToken) {
                continue;
            }

            Auth::guard($guard)->logout();
            $request->session()->flush();
            $request->session()->regenerateToken();
            cookie()->queue(cookie()->forget(Auth::guard($guard)->getRecallerName()));

            if ($request->header('X-Inertia') || $request->expectsJson()) {
                return response()->json([
                    'message' => 'session_kicked',
                    'guard' => $guard,
                ], 409);
            }

            $loginRoute = $guard === 'admin' ? 'admin.login' : 'login';

            return redirect()->route($loginRoute)
                ->with('error', '您的账号已在其他设备登录，当前会话已失效。');
        }

        return $next($request);
    }
}
