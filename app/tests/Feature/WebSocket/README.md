# WebSocket 功能测试

本目录包含 WebSocket 服务的端到端功能测试。

## 测试架构

```
tests/
├── Feature/WebSocket/
│   ├── WebSocketFunctionalTestTrait.php  # 测试 Trait (数据清理、协程支持)
│   ├── SubscribeFunctionalTest.php       # 订阅功能测试
│   ├── DevicePushFunctionalTest.php      # 设备推送测试
│   ├── StatsFunctionalTest.php            # stats 统计功能测试
│   ├── UserIsolationFunctionalTest.php   # 用户隔离测试
│   └── PanelDeviceControlFunctionalTest.php # Panel 控制测试
└── Support/
    ├── WebSocketTestServer.php           # 测试服务器管理 (随机端口)
    ├── WebSocketTestExtension.php        # PHPUnit Extension
    ├── WebSocketTestStartedSubscriber.php
    ├── WebSocketTestFinishedSubscriber.php
    ├── WebSocketTestClient.php           # 测试客户端
    ├── MockPanel.php                     # 模拟 Panel
    └── MockDevice.php                    # 模拟设备
```

## 运行测试

### 使用 Sail (推荐)

```bash
# 运行所有 WebSocket 测试
./vendor/bin/sail pest tests/Feature/WebSocket/

# 运行指定测试文件
./vendor/bin/sail pest tests/Feature/WebSocket/SubscribeFunctionalTest.php

# 使用 PHPUnit 测试套件
./vendor/bin/sail test --testsuite=WebSocket
```

### 本地运行

```bash
# 确保 Swoole 扩展已安装
php -m | grep swoole

# 运行测试
./vendor/bin/pest tests/Feature/WebSocket/
```

## 工作原理

1. **自动服务器管理**: PHPUnit Extension 在测试套件开始前自动启动使用**随机端口**的 WebSocket 服务器
2. **端口隔离**: 每次测试运行使用随机可用端口，避免与其他服务或容器冲突
3. **自动清理**: 测试完成后自动关闭服务器进程
4. **数据隔离**: 使用手动数据清理而非事务（因为 WebSocket 是独立进程）

## 测试覆盖

| 测试文件 | 功能 | 测试数 |
|---------|------|-------|
| `SubscribeFunctionalTest` | Panel 订阅、设备列表、统计数据、心跳 | 4 |
| `DevicePushFunctionalTest` | 设备上线/下线推送 | 3 |
| `StatsFunctionalTest` | stats 统计：subscribe/deviceOnline/deviceUpdate/deviceOffline、用户与管理员隔离 | 8 |
| `UserIsolationFunctionalTest` | 用户数据隔离、管理员权限 | 2 |
| `PanelDeviceControlFunctionalTest` | 设备控制、SMS/联系人请求 | 3 |

## 依赖

- Swoole 扩展 (用于协程 WebSocket 客户端)
- Laravel Sail (可选，用于 Docker 环境)

## 日志

测试服务器日志位于: `storage/logs/websocket-test.log`

## 技术说明

### 为什么不使用 RefreshDatabase？

`RefreshDatabase` trait 使用数据库事务来隔离测试数据。但 WebSocket 测试服务器是**独立进程**，无法看到测试事务中的数据。因此使用手动数据清理：

- `setUpWebSocketFunctional()`: 创建测试用户并跟踪 ID
- `tearDownWebSocketFunctional()`: 清理创建的测试数据
- `createTestDevice()`: 创建设备并自动跟踪以便清理

### 随机端口实现

`WebSocketTestServer::findAvailablePort()` 使用 `stream_socket_server("tcp://127.0.0.1:0")` 让系统分配可用端口，确保不会与容器中运行的其他 WebSocket 服务冲突。

## 故障排除

### 测试超时

如果测试超时，可能是服务器启动失败。检查:
- Swoole 扩展是否安装
- 端口是否被占用
- 日志文件 `storage/logs/websocket-test.log` 中的错误信息

### 连接失败

如果测试跳过并显示 "WebSocket server not available":
- 检查测试服务器是否成功启动
- 查看控制台输出中的 `[WebSocket Test]` 日志

### 数据未同步

如果测试因为数据问题失败（如设备找不到用户）:
- 确保测试使用 `setUpWebSocketFunctional()` 和 `tearDownWebSocketFunctional()`
- 检查 `user_email` 是否与测试用户的邮箱匹配
