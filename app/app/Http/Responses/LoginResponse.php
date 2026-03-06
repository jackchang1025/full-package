<?php

namespace App\Http\Responses;

use App\Models\User;
use Laravel\Fortify\Contracts\LoginResponse as LoginResponseContract;

class LoginResponse implements LoginResponseContract
{
    public function toResponse($request)
    {
        // 单点登录：生成新 token，旧设备的 session 将在下次请求时失效
        $user = $request->user();
        if ($user instanceof User) {
            $token = bin2hex(random_bytes(32));
            $user->update(['session_token' => $token]);
            $request->session()->put('single_session_token_web', $token);
        }

        return redirect()->intended(config('fortify.home'))
            ->with('success', '登录成功');
    }
}
