<?php

namespace App\Exceptions;

use Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException;

/**
 * 子账号业务规则异常（配额超限、嵌套禁止、归属错误等）。
 */
class SubAccountException extends AccessDeniedHttpException
{
    public static function nested(): static
    {
        return new static('子账号无法管理子账号');
    }

    public static function quotaExceeded(): static
    {
        return new static('已达到子账号数量上限');
    }

    public static function notOwned(): static
    {
        return new static('该子账号不属于当前用户');
    }
}
