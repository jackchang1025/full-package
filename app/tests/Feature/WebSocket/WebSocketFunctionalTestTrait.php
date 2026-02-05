<?php

declare(strict_types=1);

namespace Tests\Feature\WebSocket;

use App\Models\Admin;
use App\Models\Device;
use App\Models\User;
use Tests\Support\WebSocketTestServer;

/**
 * WebSocket 功能测试 Trait
 *
 * 注意：不使用 RefreshDatabase，因为 WebSocket 服务器是独立进程，
 * 无法看到测试事务中的数据。改用手动数据清理。
 */
trait WebSocketFunctionalTestTrait
{
    protected User $userA;

    protected User $userB;

    protected Admin $admin;

    /** @var array<int> 测试创建的用户 ID */
    private array $createdUserIds = [];

    /** @var array<int> 测试创建的设备 ID */
    private array $createdDeviceIds = [];

    /** @var array<int> 测试创建的管理员 ID */
    private array $createdAdminIds = [];

    /**
     * 设置 WebSocket 功能测试环境
     * 创建测试用户并跟踪以便清理
     */
    protected function setUpWebSocketFunctional(): void
    {
        // 清理之前可能残留的测试数据
        $this->cleanupTestData();

        // 创建测试用户
        $this->userA = User::create([
            'username' => 'ws_test_user_a_' . uniqid(),
            'email' => 'usera_' . uniqid() . '@ws-test.local',
            'password' => bcrypt('password'),
        ]);
        $this->createdUserIds[] = $this->userA->id;

        $this->userB = User::create([
            'username' => 'ws_test_user_b_' . uniqid(),
            'email' => 'userb_' . uniqid() . '@ws-test.local',
            'password' => bcrypt('password'),
        ]);
        $this->createdUserIds[] = $this->userB->id;

        // 创建测试管理员 (admins 表使用 name 字段)
        $this->admin = Admin::create([
            'name' => 'WS Test Admin ' . uniqid(),
            'email' => 'admin_' . uniqid() . '@ws-test.local',
            'password' => bcrypt('password'),
        ]);
        $this->createdAdminIds[] = $this->admin->id;
    }

    /**
     * 清理 WebSocket 功能测试数据
     * 在 tearDown 中调用
     */
    protected function tearDownWebSocketFunctional(): void
    {
        $this->cleanupCreatedData();
    }

    /**
     * 清理之前测试残留的数据
     */
    private function cleanupTestData(): void
    {
        // 删除测试用户创建的设备
        Device::whereHas('user', function ($q) {
            $q->where('email', 'like', '%@ws-test.local');
        })->delete();

        // 删除带有测试标记的设备
        Device::where('uuid', 'like', 'push-%')
            ->orWhere('uuid', 'like', 'isolation-%')
            ->orWhere('uuid', 'like', 'admin-test-%')
            ->orWhere('uuid', 'like', 'control-%')
            ->orWhere('uuid', 'like', 'test-device-%')
            ->orWhere('uuid', 'like', 'stats-%')
            ->delete();

        // 删除测试用户
        User::where('email', 'like', '%@ws-test.local')->delete();

        // 删除测试管理员
        Admin::where('email', 'like', '%@ws-test.local')->delete();
    }

    /**
     * 清理本次测试创建的数据
     */
    private function cleanupCreatedData(): void
    {
        // 删除创建的设备
        if (! empty($this->createdDeviceIds)) {
            Device::whereIn('id', $this->createdDeviceIds)->delete();
            $this->createdDeviceIds = [];
        }

        // 删除创建的用户
        if (! empty($this->createdUserIds)) {
            // 先删除用户关联的设备
            Device::whereIn('user_id', $this->createdUserIds)->delete();
            User::whereIn('id', $this->createdUserIds)->delete();
            $this->createdUserIds = [];
        }

        // 删除创建的管理员
        if (! empty($this->createdAdminIds)) {
            Admin::whereIn('id', $this->createdAdminIds)->delete();
            $this->createdAdminIds = [];
        }
    }

    /**
     * 创建测试设备并跟踪以便清理
     */
    protected function createTestDevice(array $attributes = []): Device
    {
        $device = Device::create(array_merge([
            'uuid' => 'test-device-' . uniqid(),
            'user_id' => $this->userA->id,
            'name' => 'Test Device',
            'is_online' => false,
            'is_removed' => false,
        ], $attributes));

        $this->createdDeviceIds[] = $device->id;

        return $device;
    }

    /**
     * 获取测试服务器端口
     */
    protected function getTestServerPort(): int
    {
        return WebSocketTestServer::getPort()
            ?? (int) config('websocket.port', 8081);
    }

    /**
     * 获取测试服务器主机
     */
    protected function getTestServerHost(): string
    {
        return '127.0.0.1';
    }

    /**
     * 跳过测试如果 WebSocket 服务器不可用
     */
    protected function skipIfWebSocketServerUnavailable(): void
    {
        if (WebSocketTestServer::isStarted()) {
            if (! WebSocketTestServer::isRunning()) {
                $this->markTestSkipped('WebSocket test server process died unexpectedly');
            }

            return;
        }

        $host = $this->getTestServerHost();
        $port = $this->getTestServerPort();

        $fp = @stream_socket_client(
            "tcp://{$host}:{$port}",
            $errno,
            $errstr,
            2,
            STREAM_CLIENT_CONNECT
        );

        if ($fp === false) {
            $this->markTestSkipped(
                "WebSocket server not available at {$host}:{$port}. "
                    . 'Make sure the test server is running or start it manually: php artisan websocket:serve'
            );
        }

        fclose($fp);
    }

    /**
     * 在 Swoole 协程中运行测试逻辑
     */
    protected function runWebSocketInCoroutine(callable $fn): mixed
    {
        if (! extension_loaded('swoole')) {
            $this->markTestSkipped('Swoole extension required');
        }

        $result = null;
        $exception = null;

        \Swoole\Coroutine\run(function () use ($fn, &$result, &$exception) {
            try {
                $result = $fn();
            } catch (\Throwable $e) {
                $exception = $e;
            }
        });

        if ($exception !== null) {
            throw $exception;
        }

        return $result;
    }
}
