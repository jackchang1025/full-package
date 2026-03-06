<?php

use App\Services\ApkBuilder\ApkBuildConfig;
use App\Services\ApkBuilder\SmaliProcessor;

test('login_title field is passed to ApkBuildConfig', function () {
    $configData = [
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'login_title' => '测试标题',
    ];

    $config = ApkBuildConfig::fromArray($configData);

    expect($config->loginTitle)->toBe('测试标题');
});

test('login_title field supports both snake_case and camelCase', function () {
    // 测试 snake_case
    $config1 = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'login_title' => 'Snake Case Title',
    ]);

    expect($config1->loginTitle)->toBe('Snake Case Title');

    // 测试 camelCase
    $config2 = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'loginTitle' => 'Camel Case Title',
    ]);

    expect($config2->loginTitle)->toBe('Camel Case Title');
});

test('login_title field uses default value when not provided', function () {
    $config = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
    ]);

    expect($config->loginTitle)->toBe('欢迎使用');
});

test('login_title field is validated in ApkBuildConfig', function () {
    // 测试超长 login_title
    $longTitle = str_repeat('A', 101);
    
    $config = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'login_title' => $longTitle,
    ]);

    $errors = $config->validate();

    expect($errors)
        ->toBeArray()
        ->toContain('loginTitle must not exceed 100 characters');
});

test('login_title field with special characters is escaped for smali', function () {
    $processor = new SmaliProcessor('/tmp/test');
    $reflection = new \ReflectionClass($processor);
    $method = $reflection->getMethod('escapeForSmaliString');
    $method->setAccessible(true);

    // 测试双引号转义
    $result = $method->invoke($processor, '欢迎"使用"');
    expect($result)->toBe('欢迎\\"使用\\"');

    // 测试反斜杠转义
    $result = $method->invoke($processor, '欢迎\\使用');
    expect($result)->toBe('欢迎\\\\使用');

    // 测试换行符转义
    $result = $method->invoke($processor, "欢迎\n使用");
    expect($result)->toBe('欢迎\\n使用');

    // 测试组合
    $result = $method->invoke($processor, "欢迎\"使用\"\n应用");
    expect($result)->toBe('欢迎\\"使用\\"\\n应用');
});

test('login_title field is included in config toArray', function () {
    $config = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'login_title' => '自定义标题',
    ]);

    $array = $config->toArray();

    expect($array)
        ->toHaveKey('login_title')
        ->and($array['login_title'])->toBe('自定义标题');
});

test('login_title field validation passes for valid input', function () {
    $config = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'login_title' => '这是一个有效的标题',
    ]);

    expect($config->isValid())->toBeTrue();
});

test('login_title field handles empty string', function () {
    $config = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'login_title' => '',
    ]);

    // 空字符串应该被接受
    expect($config->loginTitle)->toBe('');
});

test('login_title field handles unicode characters', function () {
    $unicodeTitle = '欢迎使用 🎉 Welcome';
    
    $config = ApkBuildConfig::fromArray([
        'app_id' => 'com.test.app',
        'user_id' => '1',
        'app_name' => 'Test App',
        'app_version' => '1.0',
        'websocket_url' => 'ws://localhost:8081',
        'login_title' => $unicodeTitle,
    ]);

    expect($config->loginTitle)->toBe($unicodeTitle);
});
