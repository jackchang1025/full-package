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
└── docker/                   # Docker configuration
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
- **Android testing guide**: `docs/vendor-replication/TESTING_GUIDE.md`
- **ADB 连接指南**: `docs/vendor-replication/ADB_CONNECTION.md`
- **真机深度对比协议**: `docs/vendor-replication/COMPARISON_REPORT.md`
- **真机对比结果**: `docs/vendor-replication/COMPARISON_RESULT.md`
- **初始化修复计划**: `docs/vendor-replication/FIX_PLAN.md`
- **HuaweiEngine 对齐**: `docs/vendor-replication/HUAWEI_ENGINE_ALIGNMENT.md`

## Android Vendor APK Replication (MANDATORY)

When working on ANY Android replication task in the `android/` directory, you MUST read and follow `docs/vendor-replication/REPLICATION_PROTOCOL.md`.

### How to trigger replication

User says any of these → Claude Code auto-executes the full 5-phase workflow:

```
"复刻模块01" / "复刻网络通信"   → MODULE_01: http/ + bridge/ + msg/
"复刻模块02" / "复刻权限绕过"   → MODULE_02: service/ + o/c + o/e
"复刻模块03" / "复刻厂商适配"   → MODULE_03: o/ 厂商引擎 (33 files)
"复刻模块04" / "复刻UI自动化"   → MODULE_04: entity/ + filter/ + condition/
"复刻模块05" / "复刻数据收集"   → MODULE_05: receiver/ + stat/ + helper/r,o,n
"复刻模块06" / "复刻远程控制"   → MODULE_06: server/ + plug/
"复刻模块07" / "复刻保活机制"   → MODULE_07: receiver/ + thread/ + sync/
"复刻模块08" / "复刻启动流程"   → MODULE_08: root + activity/ + helper/ + utils/
"复刻模块09" / "复刻数据模型"   → MODULE_09: req/ + resp/ + entity/ VO
"复刻 CommandHandler"          → single file replication
```

Partial triggers (only run specific phases):

```
"审计模块02" / "审计权限绕过"   → Phase 1 only: 分析 vendor 行为 → 输出 AUDIT doc
"测试模块02" / "测试权限绕过"   → Phase 4-5: build APK → install → device verify
"验证模块02" / "验证权限绕过"   → Phase 5 only: run device test cases
```

### Workflow (auto-executed, no manual prompts needed)

```
Phase 1: 审计 → 读取 vendor 源文件 → 分析执行流程/入口点/可观测行为 → 输出 AUDIT_MODULE_XX.md
Phase 2: 测试设计 → 基于审计结果编写 JVM 单元测试 + 真机功能测试用例 → 输出 DEVICE_VERIFY_MODULE_XX.md
Phase 3: 实现 → 按依赖顺序逐文件编写 replica → 每文件编译检查 + JVM 测试
Phase 4: 构建安装 → assembleDebug → adb install
Phase 5: 真机验证 → 逐项执行测试用例 → 输出验证报告 → FAIL 则修复后重跑
```

### Key files
- `docs/vendor-replication/REPLICATION_PROTOCOL.md` — V3 行为驱动复刻协议 (5 阶段: 审计→测试设计→实现→构建→真机验证)
- `docs/vendor-replication/REPLICATION_MAP.md` — vendor→replica file mapping table (update after every file)
- `docs/vendor-replication/AUDIT_MODULE_XX.md` — vendor 行为审计文档 (Phase 1 输出)
- `docs/vendor-replication/DEVICE_VERIFY_MODULE_XX.md` — 真机功能测试用例 (Phase 2 输出)
- `docs/vendor-replication/MODULE_*.md` — detailed design docs per module

### Replication Rules (Non-negotiable)
- **Read vendor first**: Always read the full vendor source before writing any replica code
- **Extract before implement**: Generate signature checklist (fields + methods + inner classes) before coding
- **No optimization**: Do not merge methods, remove fields, simplify logic, or "improve" vendor code
- **Mark adaptations**: `// ADAPT: reason` for any deviation from vendor
- **Mark unknowns**: `// TODO: VENDOR_VERIFY` for uncertain logic
- **Compile after each file**: `./gradlew compileDebugJavaWithJavac` after writing each file
- **Full test after module**: `./gradlew test` after completing all files in a module
- **Update mapping table**: After completing each file, update `docs/vendor-replication/REPLICATION_MAP.md`

### Vendor Source Locations
- `app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/` — 294 Java files, 46K lines (primary, decompiled with obfuscated names)
- `app/storage/app/apk/apkstub/decompiled_vendor/sources/o/` — 33 Java files, 11K lines (vendor engine classes)
- `docs/vendor-replication/` — module design docs with deobfuscation notes
- `PhantomRAT-V2/Payloads/android_14/` — secondary reference (21 files)

### How to trigger device verification

User says any of these → Claude Code auto-executes build + install + verify:

```
"验证模块02" / "验证权限绕过" → build APK → install → run MODULE_02 checks
"构建安装" / "build and install" → assembleDebug → adb install
"执行深度对比" / "run deep comparison" → Round A (Vendor) → Round B (Replica) → Round C (对比分析)
"执行 Round A" → Vendor APK 独立测试 (安装→日志→场景→快照→卸载)
"执行 Round B" → Replica APK 独立测试 (构建→安装→日志→场景→快照→卸载)
"对齐 TASK-1~5" → HuaweiEngine 事件驱动链路对齐 (见 HUAWEI_ENGINE_ALIGNMENT.md)
```

Verification protocol: `docs/vendor-replication/DEVICE_VERIFY_MODULE02.md`

ADB config: `docs/vendor-replication/ADB_CONNECTION.md`

```
ADB = /mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEVICE = 192.168.31.162:5555
PACKAGE = com.vendor.rat
```

## Important Notes

- Use `npm run dev` for development (not `npm run build`); built files enable caching that breaks HMR
- WebSocket tests use random ports and auto-cleanup; no manual server management needed
- The `legacy/` directory is for reference only; new development goes in `app/`
- The `android/` directory is a standalone Gradle project; do NOT put Android code in `app/` (that's Laravel)
- Android daily development uses `./gradlew test` in WSL; no need to build APK or connect devices for 90% of testing
