# AGENTS.md - 代码库指南

> 本文档为 AI 编程代理提供项目结构、代码风格和开发规范指南。

## 项目概述

飞鹰管理系统 V2 - 基于 Laravel 12 的设备管理平台，已从原生 PHP 迁移至现代化技术栈。

### 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| **后端框架** | Laravel | 12.x |
| **前端框架** | Vue 3 + Inertia.js | 3.5 / 2.x |
| **UI 组件库** | Naive UI | 2.43 |
| **数据库** | MySQL | 8.4 |
| **缓存** | Redis | Alpine |
| **WebSocket** | PHP + Swoole | 6.x |
| **开发环境** | Laravel Sail (Docker) | - |
| **构建工具** | Vite | 7.x |
| **CSS** | Tailwind CSS | 4.x |

## 项目结构

```
full-package/
├── app/                      # Laravel 12 应用 (主项目)
├── android/                  # Android 客户端项目 (Vendor APK 复刻)
├── legacy/                   # 旧项目归档 (仅参考)
├── shared/                   # 共享资源 (APK 构建模板)
├── docs/                     # 📚 文档 (按领域分类)
│   ├── platform/             # V2 Web 平台 (Laravel + Vue)
│   ├── apk-template/         # Replica APK 模板 (Smali 构建)
│   ├── apk-research/         # APK 客户端可行性研究
│   ├── vendor-reverse/       # Vendor APK 逆向分析
│   ├── vendor-replication/   # Vendor APK Java 复刻 (8模块)
│   ├── legacy/               # V1 旧系统文档
│   └── _archive/             # 非文档文件归档
├── AGENTS.md                 # 本文件
└── CLAUDE.md                 # Claude Code 指南
```

## 快速启动

```bash
cd app
./dev-start.sh
./vendor/bin/sail npm run dev
```

### 访问地址

| 服务 | 地址 |
|------|------|
| 应用 | http://localhost:8000 |
| WebSocket | ws://localhost:8081 |
| MySQL | localhost:3307 |
| Redis | localhost:6380 |
| Vite HMR | localhost:5173 |

## 开发检查清单

### 后端修改
- [ ] 遵循 Laravel 最佳实践
- [ ] 使用 Eloquent ORM (避免原生 SQL)
- [ ] 添加适当的验证规则

### 前端修改
- [ ] 使用 TypeScript 类型定义
- [ ] 使用 Naive UI 组件
- [ ] 遵循 Vue 3 Composition API

### 提交前
- [ ] `./vendor/bin/sail artisan route:clear`
- [ ] `./vendor/bin/sail npm run build` (生产环境)

> ⚠️ **开发环境注意**: 开发环境使用 `npm run dev` 启动 Vite 开发服务器，不要执行 `npm run build`。构建后的文件会启用缓存，导致热重载失效。

### WebSocket 功能测试

```bash
cd app
./vendor/bin/sail pest tests/Feature/WebSocket/
```

- **测试专用服务器**：PHPUnit Extension 自动启动使用随机端口的 WebSocket 服务器，测试结束后自动关闭
- **端口隔离**：每次运行分配随机可用端口，避免与开发环境或容器内服务冲突
- **数据隔离**：使用手动清理（非事务），因 WebSocket 为独立进程无法看到测试事务数据

---

## 📚 文档索引

### 一、V2 Web 平台文档 (`docs/platform/`)

核心开发文档，覆盖 Laravel 后端、Vue 前端、部署运维。

| 文档 | 用途 | 推荐场景 |
|------|------|---------|
| [FRONTEND.md](./docs/platform/FRONTEND.md) | **前端架构** - Vue 3 + Inertia.js、页面组件、Composables | ⭐ 前端开发首选 |
| [API.md](./docs/platform/API.md) | API 接口文档 | 开发 API 功能 |
| [DEVELOPMENT.md](./docs/platform/DEVELOPMENT.md) | 开发环境详解 | 环境配置、常用命令 |
| [DEPLOYMENT.md](./docs/platform/DEPLOYMENT.md) | **部署文档** - Docker/Compose、宝塔面板、Nginx、SSL | 生产部署 |
| [RUNBOOK.md](./docs/platform/RUNBOOK.md) | 运维手册 | 日常运维、故障排查 |
| [CONTRIB.md](./docs/platform/CONTRIB.md) | 贡献指南 | 新开发者入门 |
| [DOCKER_SUPERVISOR.md](./docs/platform/DOCKER_SUPERVISOR.md) | Docker Supervisor 配置 | 容器后台服务管理 |
| [SINGLE_SIGN_ON.md](./docs/platform/SINGLE_SIGN_ON.md) | **单点登录** - session_token、中间件 | ⭐ 会话管理 |
| [ADMIN_USER_MANAGEMENT.md](./docs/platform/ADMIN_USER_MANAGEMENT.md) | 总后台用户管理与子账号 | Admin Users 页面 |
| [CONTROL_PANEL_SCREEN_OPERATIONS.md](./docs/platform/CONTROL_PANEL_SCREEN_OPERATIONS.md) | **控制面板屏幕操作** - 投屏、坐标换算、触摸转发 | 设备控制页开发 |
| [DEVICE_STATUS_FIELDS.md](./docs/platform/DEVICE_STATUS_FIELDS.md) | 设备状态字段参考 | 前端字段映射 |
| [DEVICE_CONTROL_MIGRATION.md](./docs/platform/DEVICE_CONTROL_MIGRATION.md) | 设备控制迁移记录 | 迁移参考 |
| [GALLERY_THUMBNAIL_FIX.md](./docs/platform/GALLERY_THUMBNAIL_FIX.md) | 相册缩略图修复 | Bug 修复参考 |
| LOGIN_TITLE_*.md (3个) | 登录标题功能 | 登录页定制 |

#### WebSocket 专题 (`docs/platform/websocket/`)

| 文档 | 用途 | 推荐场景 |
|------|------|---------|
| [CLIENT.md](./docs/platform/websocket/CLIENT.md) | **WebSocket 系统架构** - 三端架构、数据流、消息协议 | ⭐ WebSocket 开发首选 |
| [SERVER_PHP.md](./docs/platform/websocket/SERVER_PHP.md) | **PHP WebSocket 服务器** - Swoole 实现、Handler 详解 | 维护 WebSocket 服务 |
| [SERVER_NODEJS.md](./docs/platform/websocket/SERVER_NODEJS.md) | **Node.js 原始实现** - 消息协议、命令列表 | 理解原始协议 |
| [PROTOCOL.md](./docs/platform/websocket/PROTOCOL.md) | WebSocket 协议定义 | 协议参考 |
| [TESTING.md](./docs/platform/websocket/TESTING.md) | WebSocket 测试套件 | 测试架构 |
| [COMPATIBILITY_REPORT.md](./docs/platform/websocket/COMPATIBILITY_REPORT.md) | 兼容性报告 | 协议对比 |
| [FRONTEND_CLIENT_ANALYSIS.md](./docs/platform/websocket/FRONTEND_CLIENT_ANALYSIS.md) | 前端 WebSocket 客户端分析 (旧版逆向) | 理解旧前端 |

---

### 二、Replica APK 模板文档 (`docs/apk-template/`)

当前使用的 APK 客户端模板（Smali 占位符注入方式构建）。

| 文档 | 用途 | 推荐场景 |
|------|------|---------|
| [BUILDER.md](./docs/apk-template/BUILDER.md) | **APK 构建服务** - Laravel 版、配置参数 | ⭐ APK 构建开发 |
| [BUILDER_AUTO_WAKE_SCREEN.md](./docs/apk-template/BUILDER_AUTO_WAKE_SCREEN.md) | 自动唤醒屏幕功能 | 构建参数扩展 |
| [GUIDE_ACTIVITY.md](./docs/apk-template/GUIDE_ACTIVITY.md) | APK 引导页流程 | 启动流程理解 |
| [KEEP_ALIVE_MECHANISM.md](./docs/apk-template/KEEP_ALIVE_MECHANISM.md) | **保活机制** - 多层防护策略 | ⭐ 调试服务问题 |
| [KEEPALIVE_REPLICATION_DESIGN.md](./docs/apk-template/KEEPALIVE_REPLICATION_DESIGN.md) | 保活复刻设计 | 保活方案设计 |
| TEMPLATE_*_FEASIBILITY.md (2个) | 模板重构/逆向可行性 | 架构决策 |

#### 华为适配专题 (`docs/apk-template/huawei/`)

| 文档 | 用途 |
|------|------|
| SILENT_AUTOMATION.md | 华为静默自动化 |
| STEALTH_AUTOMATION_*.md (4个) | 华为隐蔽自动化方案迭代 |
| POWERGENIE_*.md (3个) | 华为 PowerGenie 电源管理分析 |
| ANDROID_BACKGROUND_KEEPALIVE_RESEARCH.md | Android 后台保活研究 |

---

### 三、APK 客户端研究 (`docs/apk-research/`)

| 文档 | 用途 |
|------|------|
| [NEW_ANDROID_CLIENT_FEASIBILITY.md](./docs/apk-research/NEW_ANDROID_CLIENT_FEASIBILITY.md) | 新 Android 客户端可行性评估 |
| [OPEN_SOURCE_ANDROID_CLIENT_RESEARCH.md](./docs/apk-research/OPEN_SOURCE_ANDROID_CLIENT_RESEARCH.md) | 开源 Android 客户端调研 |

---

### 四、Vendor APK 逆向分析 (`docs/vendor-reverse/`)

成熟 Vendor APK 的完整逆向工程文档（15 篇），覆盖网络架构、权限绕过、厂商适配、保活机制、隐蔽自动化等。

| 文档 | 用途 |
|------|------|
| APK_NETWORK_ARCHITECTURE.md | 网络架构分析 |
| APK_STARTUP_FLOW_ANALYSIS.md | 启动流程分析 |
| APK_PERMISSION_BYPASS_CODE_REVIEW.md | 权限绕过代码审查 |
| APK_VENDOR_ADAPTATION_ANALYSIS.md | 厂商适配分析 |
| APK_VENDOR_CODE_REVIEW.md | Vendor 代码审查 |
| APK_STEALTH_*.md (2个) | 隐蔽机制分析 |
| APK_HUAWEI_*.md (3个) | 华为专项分析 |
| APK_AUTOMATION_TRIGGER_ANALYSIS.md | 自动化触发分析 |
| APK_CODE_LEVEL_ANALYSIS.md | 代码级分析 |
| APK_DEEP_ANALYSIS_encryption_keepalive.md | 加密与保活深度分析 |
| APK_SCREEN_OFF_KEEPALIVE_DEEP_ANALYSIS.md | 息屏保活深度分析 |
| APK_REVERSE_ANALYSIS_stripchat-release.md | 特定版本逆向分析 |

---

### 五、Vendor APK Java 复刻 (`docs/vendor-replication/`)

一比一复刻 Vendor APK 的 Java Android 项目文档（39 篇）。项目代码位于 `android/` 目录。

#### 根目录 — 核心文档

| 文档 | 用途 |
|------|------|
| [README.md](./docs/vendor-replication/README.md) | 模块索引、项目结构、路线图 |
| [REPLICATION_PROTOCOL.md](./docs/vendor-replication/REPLICATION_PROTOCOL.md) | V3 行为驱动复刻协议 (5 阶段) |
| [REPLICATION_MAP.md](./docs/vendor-replication/REPLICATION_MAP.md) | Vendor → Replica 文件映射表 |
| [JAVA_PROJECT_REQUIREMENTS.md](./docs/vendor-replication/JAVA_PROJECT_REQUIREMENTS.md) | 项目需求与设计 |
| [APK_VENDOR_REPLICATION_PLAN.md](./docs/vendor-replication/APK_VENDOR_REPLICATION_PLAN.md) | 功能复刻计划 |

#### 模块设计 (`modules/`)

| 文档 | 说明 |
|------|------|
| MODULE_01~08.md (8个) | 各模块详细设计文档 (网络/权限/厂商/UI自动化/数据/控制/保活/启动) |

#### 行为审计 (`audits/`)

| 文档 | 说明 |
|------|------|
| AUDIT_MODULE_01~09.md (9个) | Vendor 行为审计 (Phase 1 输出) |
| AUDIT_PANEL_COMMANDS.md | Camera/Mic/Block/KB/Q/Keylog/File 审计 |
| VENDOR_NETWORK_ARCHITECTURE_AUDIT.md | 网络架构审计 |

#### 真机验证与测试 (`verification/`)

| 文档 | 说明 |
|------|------|
| DEVICE_VERIFY_*.md (3个) | 真机功能测试用例 |
| TESTING_GUIDE*.md (2个) | 测试指南 |
| ADB_CONNECTION.md | ADB 连接配置 |

#### 对比报告与修复 (`comparison/`)

| 文档 | 说明 |
|------|------|
| COMPARISON_*.md + DEEP_COMPARISON_AUDIT.md | Vendor vs Replica 对比 |
| FIX_PLAN*.md (3个) | 差异修复方案 |
| HUAWEI_ENGINE_ALIGNMENT.md | 华为引擎对齐 |

#### 补充文档 (`supplementary/`)

| 文档 | 说明 |
|------|------|
| ACCESSIBILITY_MODULES.md | 无障碍模块参考 |
| DEVICE_ADMIN_MODULE.md | 设备管理员模块参考 |

---

### 六、V1 旧系统文档 (`docs/legacy/`)

#### 系统文档 (`docs/legacy/system/`)

| 文档 | 用途 |
|------|------|
| [SYSTEM_FEATURES.md](./docs/legacy/system/SYSTEM_FEATURES.md) | **系统功能详解** - 完整功能列表、架构设计 |
| [DEPLOYMENT.md](./docs/legacy/system/DEPLOYMENT.md) | 旧版部署文档 |
| [QUICK_REFERENCE.md](./docs/legacy/system/QUICK_REFERENCE.md) | 参数表格、加密密钥速查 |
| [DEVICE_CONTROL_PANEL.md](./docs/legacy/system/DEVICE_CONTROL_PANEL.md) | 旧版设备控制面板 (info.php) |

#### APK 构建系统 (`docs/legacy/apk-build/`)

| 文档 | 用途 |
|------|------|
| [APK_BUILD_SYSTEM.md](./docs/legacy/apk-build/APK_BUILD_SYSTEM.md) | 构建流程、参数、PHP/VB.NET 交互 |
| [APK_STUB_TEMPLATE.md](./docs/legacy/apk-build/APK_STUB_TEMPLATE.md) | Smali 代码结构、占位符系统 |
| [APK_RUNTIME_FLOW.md](./docs/legacy/apk-build/APK_RUNTIME_FLOW.md) | 启动机制、服务依赖、保活策略 |
| [APKBUILDER_OPTIMIZATION.md](./docs/legacy/apk-build/APKBUILDER_OPTIMIZATION.md) | ApkBuilder.php 优化 |

#### 逆向工程 (`docs/legacy/reverse-engineering/`)

| 文档 | 用途 |
|------|------|
| [REVERSE_ANALYSIS.md](./docs/legacy/reverse-engineering/REVERSE_ANALYSIS.md) | EaodStarter/EaodWorker 逆向分析 |
| [CODE_MAPPING.md](./docs/legacy/reverse-engineering/CODE_MAPPING.md) | 代码映射指南 |
| [README_ANALYSIS.md](./docs/legacy/reverse-engineering/README_ANALYSIS.md) | 逆向文档索引 |
| APKBuildSystemReverseEngineeringDocumentation/ (6个) | 构建系统逆向专题 |

---

## 文档阅读路径推荐

**新手入门**:
```
AGENTS.md → docs/platform/DEVELOPMENT.md → docs/legacy/system/SYSTEM_FEATURES.md
```

**前端开发**:
```
docs/platform/FRONTEND.md → docs/platform/websocket/CLIENT.md
```

**设备控制页 / 屏幕操作**:
```
docs/platform/CONTROL_PANEL_SCREEN_OPERATIONS.md → docs/platform/websocket/CLIENT.md
```

**WebSocket 开发**:
```
docs/platform/websocket/SERVER_NODEJS.md → docs/platform/websocket/SERVER_PHP.md → docs/platform/websocket/CLIENT.md
```

**WebSocket 功能测试**:
```
app/tests/Feature/WebSocket/README.md → ./vendor/bin/sail pest tests/Feature/WebSocket/
```

**APK 构建开发**:
```
docs/apk-template/BUILDER.md → docs/legacy/apk-build/APK_BUILD_SYSTEM.md → docs/legacy/apk-build/APK_STUB_TEMPLATE.md
```

**Vendor APK 复刻**:
```
docs/vendor-replication/README.md → docs/vendor-replication/REPLICATION_PROTOCOL.md → docs/vendor-reverse/
```

**总后台用户管理 / 子账号**:
```
docs/platform/ADMIN_USER_MANAGEMENT.md → app/Http/Controllers/Admin/UserController.php
```
