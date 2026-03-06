<?php

test('prepareBuildConfig includes login_title field', function () {
    $controller = new \App\Http\Controllers\AppBuildController();
    $reflection = new \ReflectionClass($controller);
    $method = $reflection->getMethod('prepareBuildConfig');
    $method->setAccessible(true);

    $validated = [
        'name' => 'Test App',
        'login_title' => '自定义标题',
        'login_dis' => '左按钮',
        'login_btn' => '右按钮',
    ];

    $buildConfig = $method->invoke($controller, $validated);

    expect($buildConfig)
        ->toHaveKey('login_title')
        ->and($buildConfig['login_title'])->toBe('自定义标题')
        ->and($buildConfig['login_dis'])->toBe('左按钮')
        ->and($buildConfig['login_btn'])->toBe('右按钮');
});

test('prepareBuildConfig uses default when login_title is empty', function () {
    $controller = new \App\Http\Controllers\AppBuildController();
    $reflection = new \ReflectionClass($controller);
    $method = $reflection->getMethod('prepareBuildConfig');
    $method->setAccessible(true);

    $validated = [
        'name' => 'Test App',
        'login_title' => '',
    ];

    $buildConfig = $method->invoke($controller, $validated);

    // 空字符串应该使用默认值
    expect($buildConfig['login_title'])->toBe('欢迎使用');
});

test('prepareBuildConfig uses default when login_title is not provided', function () {
    $controller = new \App\Http\Controllers\AppBuildController();
    $reflection = new \ReflectionClass($controller);
    $method = $reflection->getMethod('prepareBuildConfig');
    $method->setAccessible(true);

    $validated = [
        'name' => 'Test App',
    ];

    $buildConfig = $method->invoke($controller, $validated);

    // 未提供时应该使用默认值
    expect($buildConfig['login_title'])->toBe('欢迎使用');
});
