<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\LoginRequest;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Inertia\Inertia;

class AuthController extends Controller
{
    public function showLoginForm()
    {
        if (Auth::guard('admin')->check()) {
            return redirect()->route('admin.dashboard');
        }

        return Inertia::render('Admin/Login');
    }

    public function login(LoginRequest $request)
    {
        $validated = $request->validated();

        if (Auth::guard('admin')->attempt($validated, $request->boolean('remember'))) {
            $request->session()->regenerate();

            // 单点登录：生成新 token，旧设备的 session 将在下次请求时失效
            $token = bin2hex(random_bytes(32));
            /** @var \App\Models\Admin $admin */
            $admin = Auth::guard('admin')->user();
            $admin->update(['session_token' => $token]);
            $request->session()->put('single_session_token_admin', $token);

            return redirect()->intended(route('admin.dashboard'))
                ->with('success', '登录成功');
        }

        return back()->withErrors([
            'email' => __('auth.failed'),
        ])->onlyInput('email');
    }

    public function logout(Request $request)
    {
        Auth::guard('admin')->logout();
        $request->session()->regenerateToken();

        return redirect()->route('admin.login');
    }
}
