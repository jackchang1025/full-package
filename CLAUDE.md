# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

飞鹰管理系统 V2 - A device management platform migrated from legacy PHP to modern Laravel 12 + Vue 3 stack.

## Repository Structure

```
full-package/
├── app/                      # Main Laravel 12 application
├── android/                  # Android client project (Java, Gradle 8.5)
├── legacy/                   # Legacy PHP system (archived, reference only)
├── docs/                     # Documentation (organized by domain)
│   ├── platform/             # V2 Web platform (Laravel + Vue + WebSocket)
│   ├── apk-template/         # Replica APK template (Smali build system)
│   ├── apk-research/         # APK client feasibility research
│   ├── vendor-reverse/       # Vendor APK reverse engineering (15 docs)
│   ├── vendor-replication/   # Vendor APK Java replication (8 modules, 39 docs)
│   ├── legacy/               # V1 legacy system docs
│   └── _archive/             # Non-doc files archive
```

## Development Commands

### Laravel (Web Application)

All commands run from the `app/` directory using Laravel Sail:

```bash
# Start development environment
./dev-start.sh
./vendor/bin/sail npm run dev

# Services
./vendor/bin/sail up -d          # Start containers
./vendor/bin/sail down           # Stop containers

# Database
./vendor/bin/sail artisan migrate
./vendor/bin/sail artisan migrate:fresh --seed

# Cache clearing
./vendor/bin/sail artisan cache:clear
./vendor/bin/sail artisan config:clear
./vendor/bin/sail artisan route:clear

# Run tests
./vendor/bin/sail pest                              # All tests
./vendor/bin/sail pest tests/Feature/WebSocket/    # WebSocket tests only

# Lint PHP
./vendor/bin/sail pint

# Build Android APK via Gradle source (from Sail container)
./vendor/bin/sail artisan apk:build-gradle --config=scripts/build-gradle.json
# Or with inline parameters
./vendor/bin/sail artisan apk:build-gradle --app-name="系统服务" --websocket-url="ws://host:8081" --user-email="admin@example.com"
```

### Android (Client Application)

All commands run from the `android/` directory:

```bash
# Run unit tests (daily development - no APK/device needed)
./gradlew test

# Run specific test class
./gradlew test --tests "com.vendor.rat.network.HttpClientTest"

# Clean build and test
./gradlew clean test

# Build Debug APK (only when real device testing is needed)
./gradlew assembleDebug
```

**Environment** (WSL Ubuntu 22.04):
- JDK 17: `/usr/lib/jvm/java-17-openjdk-amd64`
- Android SDK: `/opt/android-sdk`
- Gradle 8.5 + AGP 8.2.2

## Tech Stack

### Web Application (app/)

| Layer | Technology |
|-------|------------|
| Backend | Laravel 12, PHP 8.5 |
| Frontend | Vue 3 + Inertia.js + TypeScript |
| UI | Naive UI 2.43 + Tailwind CSS 4 |
| Database | MySQL 8.4, Redis |
| WebSocket | PHP + Swoole |
| Build | Vite 7 |
| Dev Environment | Laravel Sail (Docker) |

### Android Client (android/)

| Layer | Technology |
|-------|------------|
| Language | Java 8+ (source compat) |
| Platform | Android API 21-34 |
| Build | Gradle 8.5 + AGP 8.2.2 |
| Network | OkHttp 4.12.0 + Conscrypt 2.5.2 |
| JSON | Gson 2.10.1 |
| Test | JUnit 4.13.2 + Mockito 5.3.1 + Robolectric 4.11.1 |
| Dev Environment | WSL Ubuntu 22.04 + JDK 17 |

## Access URLs (Development)

| Service | URL |
|---------|-----|
| Application | http://localhost:8000 |
| WebSocket | ws://localhost:8081 |
| Vite HMR | http://localhost:5173 |
| MySQL | localhost:3307 |
| Redis | localhost:6380 |

## Workflow Rules (MANDATORY)

### 审计优先，代码在后
- 复刻或修复任何模块前，必须先完整读取所有相关 vendor 源文件
- 列出每个类的方法、字段、窗口标志、Intent 参数、时序值，形成审计表
- 与当前 replica 实现逐项对比，列出所有差异
- 用户确认审计结果后才能开始写代码

### 全路径修复
- 修复 bug 前，先 grep 所有涉及该功能的代码路径（主路径 + 回退路径 + 二级处理器）
- 一次性修复全部路径，不要只改第一个找到的
- 修复后检查是否有其他入口点会触发相同问题

### 华为设备约束
- 华为电源管理 (Pged-Freezer) 会在 app 进入后台 ~2s 后冻结进程
- Android 10+ 禁止后台 startActivity，必须用 AccessibilityService 作为 Context 启动
- 无障碍服务在 force-stop 后会解绑，需要重新绑定
- 华为启动管理 UI: Switch checked=true 表示"自动管理"（需要关闭）

## Code Conventions

### Backend (Laravel)
- Use Eloquent ORM (avoid raw SQL)
- Add validation rules for all inputs
- Follow Laravel best practices

### Frontend (Vue 3)
- Use Composition API with `<script setup>`
- TypeScript for all components
- Naive UI components for UI elements

## Key Documentation

- **Frontend development**: `docs/platform/FRONTEND.md`
- **WebSocket system**: `docs/platform/websocket/CLIENT.md`
- **WebSocket PHP server**: `docs/platform/websocket/SERVER_PHP.md`
- **WebSocket Node.js original**: `docs/platform/websocket/SERVER_NODEJS.md`
- **Control panel operations**: `docs/platform/CONTROL_PANEL_SCREEN_OPERATIONS.md`
- **APK builder (Laravel)**: `docs/apk-template/BUILDER.md`
- **APK keepalive mechanism**: `docs/apk-template/KEEP_ALIVE_MECHANISM.md`
- **Legacy APK build system**: `docs/legacy/apk-build/APK_BUILD_SYSTEM.md`
- **Legacy system features**: `docs/legacy/system/SYSTEM_FEATURES.md`
- **Vendor APK reverse engineering**: `docs/vendor-reverse/`
- **Android client modules**: `docs/vendor-replication/README.md`
- **Android testing guide**: `docs/vendor-replication/verification/TESTING_GUIDE.md`
- **ADB 连接指南**: `docs/vendor-replication/verification/ADB_CONNECTION.md`
- **真机深度对比协议**: `docs/vendor-replication/comparison/COMPARISON_REPORT.md`
- **真机对比结果**: `docs/vendor-replication/comparison/COMPARISON_RESULT.md`
- **初始化修复计划**: `docs/vendor-replication/planning/FIX_PLAN.md`
- **HuaweiEngine 对齐**: `docs/vendor-replication/planning/HUAWEI_ENGINE_ALIGNMENT.md`

## Android Vendor APK Replication

> Android 复刻协议详见 `.claude/rules/android-replication.md`（仅在操作 android/、docs/vendor-replication/ 等目录时自动加载）

## Important Notes

- Use `npm run dev` for development (not `npm run build`); built files enable caching that breaks HMR
- WebSocket tests use random ports and auto-cleanup; no manual server management needed
- The `legacy/` directory is for reference only; new development goes in `app/`
- The `android/` directory is a standalone Gradle project; do NOT put Android code in `app/` (that's Laravel)
- Android daily development uses `./gradlew test` in WSL; no need to build APK or connect devices for 90% of testing
