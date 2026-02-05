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
├── app/                      # 🆕 Laravel 12 应用 (主项目)
├── legacy/                   # 📦 旧项目归档
├── shared/                   # 🔗 共享资源 (APK 构建模板)
├── docs/                     # 📚 文档
│   ├── legacy/               # 旧系统文档
│   └── migration/            # 迁移文档
└── AGENTS.md                 # 本文件
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

## 📚 扩展文档索引

### 新系统文档 (位于 `docs/migration/`)

| 文档 | 用途 | 推荐阅读场景 |
|------|------|-------------|
| [FRONTEND.md](./docs/migration/FRONTEND.md) | **前端架构文档** - Vue 3 + Inertia.js 技术栈、页面组件、Composables、WebSocket 类型 | ⭐ 前端开发首选 |
| [API.md](./docs/migration/API.md) | API 接口文档 | 开发 API 功能 |
| [DEVELOPMENT.md](./docs/migration/DEVELOPMENT.md) | 开发环境详解 | 环境配置、常用命令 |
| [APK_BUILDER.md](./docs/migration/APK_BUILDER.md) | **APK 构建服务** - Laravel 版构建服务、配置参数、使用示例 | 开发/维护 APK 构建功能 |
| [DOCKER_SUPERVISOR.md](./docs/migration/DOCKER_SUPERVISOR.md) | **Docker Supervisor 配置** - 容器内服务管理、WebSocket 自动启动、添加新服务 | 配置容器后台服务 |
| [CONTROL_PANEL_SCREEN_OPERATIONS.md](./docs/migration/CONTROL_PANEL_SCREEN_OPERATIONS.md) | **控制面板屏幕操作** - 投屏点击/滑动/长按实现、坐标换算、PanelHandler 与 PanelSendHandler 分工 | 开发设备控制页、触摸转发、WebSocket 消息流 |

### 核心系统文档 (位于 `docs/legacy/`)

| 文档 | 用途 | 推荐阅读场景 |
|------|------|-------------|
| [SYSTEM_FEATURES.md](./docs/legacy/SYSTEM_FEATURES.md) | **系统功能详解** - 完整的功能列表、架构设计和商业模式 | 了解系统全貌、功能范围 |
| [DEPLOYMENT.md](./docs/legacy/DEPLOYMENT.md) | **部署文档** - Docker/手动部署指南、SSL配置、安全加固 | 部署上线、环境配置 |
| [QUICK_REFERENCE.md](./docs/legacy/QUICK_REFERENCE.md) | **快速参考** - 参数表格、加密密钥、常见错误速查 | 开发时快速查阅 |
| [DEVICE_CONTROL_PANEL.md](./docs/legacy/DEVICE_CONTROL_PANEL.md) | **设备控制面板** - 旧版 info.php 单页应用、WebSocket 协议、功能模块、UI 组件 | 理解旧版控制面板、迁移参考 |

### WebSocket 文档

| 文档 | 用途 | 推荐阅读场景 |
|------|------|-------------|
| [WEBSOCKET_CLIENT.md](./docs/migration/WEBSOCKET_CLIENT.md) | **WebSocket 系统架构** - 三端架构 (设备/Web/服务器)、数据流、消息协议、前后端实现 | ⭐ WebSocket 开发首选 |
| [WEBSOCKET_SERVER_PHP.md](./docs/migration/WEBSOCKET_SERVER_PHP.md) | **PHP WebSocket 服务器** - Swoole 实现、Handler 详解、配置说明 | 开发/维护 PHP WebSocket 服务 |
| [DEVICE_STATUS_FIELDS.md](./docs/migration/DEVICE_STATUS_FIELDS.md) | **设备状态字段参考** - statusBatch 消息结构、phoneInfo 字段详解、电池/密码格式解析 | 理解设备数据结构、前端字段映射 |
| [WebSocket 功能测试 README](./app/tests/Feature/WebSocket/README.md) | **WebSocket 功能测试** - 测试专用服务器、随机端口、MockPanel/MockDevice、运行命令 | ⭐ 运行/编写 WebSocket 功能测试 |
| [WEBSOCKET_TESTING.md](./docs/migration/WEBSOCKET_TESTING.md) | **WebSocket 测试套件** - Unit Tests (Pest) + E2E Tests (Node.js)、Mock 客户端、测试命令 | 测试架构总览 |
| [WEBSOCKET_SERVER.md](./docs/WEBSOCKET_SERVER.md) | **Node.js WebSocket 服务器** - 原始实现分析、消息协议、命令列表 | 理解原始协议、对比参考 |
| [FRONTEND_WEBSOCKET_CLIENT.md](./docs/FRONTEND_WEBSOCKET_CLIENT.md) | **前端 WebSocket 客户端分析** - 编译后代码逆向、消息处理、触摸事件、状态管理 | 理解旧前端实现 |
| [CONTROL_PANEL_SCREEN_OPERATIONS.md](./docs/migration/CONTROL_PANEL_SCREEN_OPERATIONS.md) | **控制面板屏幕操作** - ScreenViewer 坐标与事件、mov 消息格式、PanelHandler 转发 | 实现/调试投屏点击滑动长按 |

### APK 构建系统文档 (位于 `docs/legacy/`)

| 文档 | 用途 | 推荐阅读场景 |
|------|------|-------------|
| [APK_BUILD_SYSTEM.md](./docs/legacy/APK_BUILD_SYSTEM.md) | **APK 构建系统详解** - 构建流程、参数、PHP/VB.NET 交互 | 理解 APK 构建机制 |
| [APK_STUB_TEMPLATE.md](./docs/legacy/APK_STUB_TEMPLATE.md) | **APK Stub 模板分析** - Smali 代码结构、占位符系统、配置注入 | 修改 APK 模板、调试构建问题 |
| [APK_RUNTIME_FLOW.md](./docs/legacy/APK_RUNTIME_FLOW.md) | **APK 运行流程** - 启动机制、服务依赖、WebSocket 通信、保活策略 | 理解客户端行为、调试设备问题 |
| [APKBUILDER_OPTIMIZATION.md](./docs/legacy/APKBUILDER_OPTIMIZATION.md) | **ApkBuilder.php 优化** - HTTP 回调机制、数据库更新流程 | 修复构建状态不更新问题 |

### 逆向工程文档 (位于 `docs/legacy/`)

| 文档 | 用途 | 推荐阅读场景 |
|------|------|-------------|
| [REVERSE_ANALYSIS.md](./docs/legacy/REVERSE_ANALYSIS.md) | **逆向源码分析** - EaodStarter/EaodWorker 完整分析 | 深入理解构建程序逻辑 |
| [CODE_MAPPING.md](./docs/legacy/CODE_MAPPING.md) | **代码映射指南** - 参数传递、数据结构、PHP 实现指南 | 实现新功能、重构代码 |
| [README_ANALYSIS.md](./docs/legacy/README_ANALYSIS.md) | **逆向文档索引** - 所有逆向分析文档的导航 | 快速定位逆向相关信息 |

### APK 构建系统逆向工程专题 (位于 `docs/legacy/APKBuildSystemReverseEngineeringDocumentation/`)

| 文档 | 用途 | 推荐阅读场景 |
|------|------|-------------|
| [README.md](./docs/legacy/APKBuildSystemReverseEngineeringDocumentation/README.md) | **专题索引** - 逆向分析项目概述和关键发现 | 了解构建失败根因 |
| [01-system-architecture.md](./docs/legacy/APKBuildSystemReverseEngineeringDocumentation/01-system-architecture.md) | **系统架构分析** - 前端→PHP→VB.NET→Java 完整链路 | 理解系统架构 |
| [02-decompile-analysis.md](./docs/legacy/APKBuildSystemReverseEngineeringDocumentation/02-decompile-analysis.md) | **反编译分析** - ILSpy 反编译、核心代码解析 | 修改 EaodWorker 行为 |
| [03-problem-diagnosis.md](./docs/legacy/APKBuildSystemReverseEngineeringDocumentation/03-problem-diagnosis.md) | **问题诊断** - AndroidManifest 膨胀问题分析 | 排查构建失败 |
| [04-fix-solution.md](./docs/legacy/APKBuildSystemReverseEngineeringDocumentation/04-fix-solution.md) | **修复方案** - dnSpy 修改、手动修复、自动脚本 | 修复构建问题 |
| [05-verification.md](./docs/legacy/APKBuildSystemReverseEngineeringDocumentation/05-verification.md) | **验证测试** - 修复后的验证流程 | 确认修复有效 |

---

## 文档阅读路径推荐

**新手入门**:
```
AGENTS.md → docs/migration/DEVELOPMENT.md → docs/legacy/SYSTEM_FEATURES.md
```

**前端开发**:
```
docs/migration/FRONTEND.md → docs/migration/WEBSOCKET_CLIENT.md
```

**设备控制页 / 屏幕操作**:
```
docs/migration/CONTROL_PANEL_SCREEN_OPERATIONS.md → docs/migration/WEBSOCKET_CLIENT.md
```

**WebSocket 开发**:
```
docs/WEBSOCKET_SERVER.md → docs/migration/WEBSOCKET_SERVER_PHP.md → docs/migration/WEBSOCKET_CLIENT.md
```

**WebSocket 功能测试**:
```
app/tests/Feature/WebSocket/README.md → ./vendor/bin/sail pest tests/Feature/WebSocket/
```

**APK 构建开发**:
```
docs/migration/APK_BUILDER.md → docs/legacy/APK_BUILD_SYSTEM.md → APK_STUB_TEMPLATE.md
```

**排查构建问题**:
```
docs/legacy/APKBuildSystemReverseEngineeringDocumentation/README.md → 03-problem-diagnosis.md → 04-fix-solution.md
```

**深度逆向分析**:
```
docs/legacy/REVERSE_ANALYSIS.md → CODE_MAPPING.md → 02-decompile-analysis.md
```
