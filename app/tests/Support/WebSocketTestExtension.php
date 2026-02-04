<?php

declare(strict_types=1);

namespace Tests\Support;

use PHPUnit\Runner\Extension\Extension;
use PHPUnit\Runner\Extension\Facade;
use PHPUnit\Runner\Extension\ParameterCollection;
use PHPUnit\TextUI\Configuration\Configuration;

/**
 * PHPUnit Extension - 管理 WebSocket 测试服务器生命周期
 *
 * 在 WebSocket 测试套件开始前自动启动服务器，
 * 测试完成后自动关闭服务器。
 *
 * 在 phpunit.xml 中注册:
 * <extensions>
 *     <bootstrap class="Tests\Support\WebSocketTestExtension"/>
 * </extensions>
 */
final class WebSocketTestExtension implements Extension
{
    public function bootstrap(
        Configuration $configuration,
        Facade $facade,
        ParameterCollection $parameters
    ): void {
        $facade->registerSubscribers(
            new WebSocketTestStartedSubscriber(),
            new WebSocketTestFinishedSubscriber()
        );
    }
}
