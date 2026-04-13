# AGENTS.md

> 面向 AI 编程代理的快速执行指南。目标是减少上下文噪音，优先提供仓库边界、强制规则、常用命令和阅读入口。

## 1. 项目是什么

飞鹰管理系统 V2 是一个设备管理平台工作区，包含：

- `app/`：主业务系统，Laravel 12 + Vue 3 + Inertia.js
- `android/`：独立 Android 客户端工程，Vendor APK 的 Java 复刻项目
- `app/storage/app/apk/apkstub/decompiled_vendor` vendor 逆向目录
- `androidReverseEngineering` vendor 一比一复刻项目
- `legacy/`：旧版 PHP 系统归档，仅参考
- `docs/`：按领域整理的开发、逆向、复刻、运维文档

默认假设：

- 新 Web 功能写在 `app/`
- 新 Android 功能写在 `android/`
- `legacy/` 不承载新开发

## 2. 技术栈速记

### Web (`app/`)

- Backend: Laravel 12, PHP 8.5
- Frontend: Vue 3, Inertia.js, TypeScript
- UI: Naive UI 2.43, Tailwind CSS 4
- DB: MySQL 8.4, Redis
- WebSocket: PHP + Swoole
- Build: Vite 7
- Dev: Laravel Sail

### Android (`android/`)

- Language: Java
- Build: Gradle 8.5 + AGP 8.2.2
- Test: JUnit + Mockito + Robolectric
- Env: WSL Ubuntu 22.04, JDK 17, Android SDK `/opt/android-sdk`

## 3. 强制规则

### 3.1 Android / Vendor 任务先审计再改代码

适用目录：`android/`、`docs/vendor-replication/`、`docs/vendor-reverse/`

必须先做：

- 完整读取相关 vendor 源文件或逆向资料
- 列出类、方法、字段、Intent 参数、窗口标志、关键时序值
- 对比当前 replica 实现，明确差异
- 差异清楚后再写代码

禁止：

- 边猜边改
- 只看一个入口就下结论
- 用“行为大致一致”代替对照验证

### 3.2 修 bug 必须全路径排查

- 先搜索主路径、回退路径、二级处理器、所有入口点
- 一次修完整条调用链，不只改第一个命中的文件
- 修复后检查是否有其他入口会触发同类问题

### 3.3 目录边界

- `app/` 是唯一 Web 主业务目录
- `android/` 是独立 Gradle 工程，不要把 Android 代码写进 `app/`
- `legacy/` 仅用于行为对照、接口参考、迁移参考

### 3.4 华为设备约束

- 华为电源管理可能在应用退后台约 2 秒后冻结进程
- Android 10+ 普通后台 `startActivity` 受限，优先考虑 `AccessibilityService`
- 无障碍服务在 `force-stop` 后会解绑
- 华为“启动管理”里 `checked=true` 往往表示“自动管理”

## 4. 常用命令

### 4.1 Web 开发 (`app/`)

```bash
cd app
./dev-start.sh
./vendor/bin/sail npm run dev
./vendor/bin/sail up -d
./vendor/bin/sail down
./vendor/bin/sail artisan migrate
./vendor/bin/sail artisan migrate:fresh --seed
./vendor/bin/sail artisan cache:clear
./vendor/bin/sail artisan config:clear
./vendor/bin/sail artisan route:clear
./vendor/bin/sail pest
./vendor/bin/sail pest tests/Feature/WebSocket/
./vendor/bin/sail pint
```

### 4.2 Android 开发 (`android/`)

```bash
cd android
./gradlew test
./gradlew test --tests "com.vendor.rat.network.HttpClientTest"
./gradlew clean test
./gradlew assembleDebug
```

默认策略：

- Web 日常开发使用 `npm run dev`，不要用 `npm run build`
- Android 日常开发优先 `./gradlew test`
- 只有需要真机验证或 APK 产物时才构建 APK

## 5. 开发约定

### 5.1 Laravel

- 优先 Eloquent，避免原生 SQL
- 所有输入都应有验证规则
- 优先使用 Laravel 常规结构：Controller / Form Request / Service / Action
- 权限路由与控制器行为保持一致

### 5.2 Vue

- 使用 Vue 3 Composition API
- 优先 `<script setup>`
- 使用 TypeScript
- UI 优先使用 Naive UI

### 5.3 Android

- 目标是行为一致，不是“功能近似”
- 优先单元测试，不以 APK 是否成功构建作为主要反馈
- 真机、ADB、深度比对在必要时再进行

## 6. 测试与验证

### WebSocket 测试

```bash
cd app
./vendor/bin/sail pest tests/Feature/WebSocket/
```

说明：

- 测试会自动使用随机端口
- 测试结束自动清理
- 不需要手动启动测试专用 WebSocket 服务

### 提交前最低检查

- `cd app && ./vendor/bin/sail artisan route:clear`
- 如是生产构建验证，再执行 `cd app && ./vendor/bin/sail npm run build`

## 7. 遇到任务先看什么

### Web / 前端

- 前端架构：`docs/platform/FRONTEND.md`
- 开发环境：`docs/platform/DEVELOPMENT.md`
- API：`docs/platform/API.md`

### WebSocket / 设备控制

- WebSocket 总览：`docs/platform/websocket/CLIENT.md`
- PHP 服务端：`docs/platform/websocket/SERVER_PHP.md`
- 原始 Node 协议参考：`docs/platform/websocket/SERVER_NODEJS.md`
- 控制面板屏幕操作：`docs/platform/CONTROL_PANEL_SCREEN_OPERATIONS.md`

### APK 构建

- Laravel 构建服务：`docs/apk-template/BUILDER.md`
- 保活机制：`docs/apk-template/KEEP_ALIVE_MECHANISM.md`
- 旧版构建系统参考：`docs/legacy/apk-build/APK_BUILD_SYSTEM.md`

### Android 复刻

- 模块索引：`docs/vendor-replication/README.md`
- 复刻协议：`docs/vendor-replication/REPLICATION_PROTOCOL.md`
- 修复计划：`docs/vendor-replication/planning/FIX_PLAN.md`
- HuaweiEngine 对齐：`docs/vendor-replication/planning/HUAWEI_ENGINE_ALIGNMENT.md`
- Android 复刻专项规则：`.claude/rules/android-replication.md`
- Vendor 逆向资料目录：`docs/vendor-reverse/`

### 旧系统参考

- 系统功能：`docs/legacy/system/SYSTEM_FEATURES.md`
- 设备控制面板：`docs/legacy/system/DEVICE_CONTROL_PANEL.md`
- 逆向文档索引：`docs/legacy/reverse-engineering/README_ANALYSIS.md`

## 8. 重要提醒

- `legacy/` 是参考资料，不是开发目录
- `android/` 是独立工程，不要混入 Laravel 目录
- 涉及设备控制、WebSocket、屏幕操作时，先读对应文档再实现
- 涉及 Android Vendor 复刻时，先审计再编码
