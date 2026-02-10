<?php

namespace App\Exceptions;

use Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException;

/**
 * 资源归属校验失败（用户尝试访问不属于自己的资源）。
 */
class ResourceAccessDeniedException extends AccessDeniedHttpException
{
    public function __construct(string $message = '无权访问该资源')
    {
        parent::__construct($message);
    }
}
