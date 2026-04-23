# Module Subagent & Knowledge Cache System — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 为 update-replica 项目中每个代码模块创建专属 `.claude/agents/` agent 定义文件和对应的知识缓存文件，使任何会话都能通过 `Agent(subagent_type="xxx-agent")` 快速恢复模块上下文，避免重复读 JADX 源码。

**Architecture:** 两层结构 — 冷层 `docs/cache/CACHE_*.md`（永久磁盘文件，跨会话）+ 热层 `.claude/agents/*.md`（agent 定义文件，指示 agent 启动时读缓存）。每个 agent 专注一个模块目录，首次启动读缓存（~100行 vs ~5000行 JADX），完成后更新缓存状态。

**Tech Stack:** Claude Code Agent System (`.claude/agents/` 自定义 agent) + Markdown 知识缓存

---

## 模块划分与 Agent 映射

| # | Agent 名 | 负责目录 | JADX LOC | 文件数 | 缓存文件 |
|---|----------|---------|----------|--------|---------|
| 1 | `svc-agent` | `service/` root + `service/account/` | 13,771 | 21 | CACHE_MyAccessibilityService.md + CACHE_ServiceRoot.md |
| 2 | `cipher-agent` | `service/modules/cipher/` | 7,402 | 16 | CACHE_CipherCaptureManager.md + CACHE_CipherModule.md |
| 3 | `setup-agent` | `service/modules/setup/` | 7,346 | 4+14 inner | CACHE_SystemOptimizeManager.md |
| 4 | `modules-agent` | `service/modules/` root + `overlay/` + `screen/` | 15,435 | 25 | CACHE_MainOrchestrator.md + CACHE_NetworkManager.md + CACHE_RemoteConfigManager.md |
| 5 | `cmd-agent` | `service/modules/command/` | 5,239 | 10 | CACHE_CommandModule.md |
| 6 | `yw5xud-agent` | `service/modules/yw5xud/` | 50,596 | 11 | CACHE_Yw5xudEngines.md |
| 7 | `infra-agent` | `manager/` + `network/` + `util/` + `security/` + `keepalive/` | 7,822 | 13 | CACHE_InfraLayer.md |
| 8 | `ui-agent` | `activity/` + `receiver/` + `inject/` + `p029ui/` + `view/` + root | 3,700 | 23 | CACHE_UILayer.md |

---

## Task 1: 创建 8 个 Agent 定义文件

**Files:**
- Create: `.claude/agents/svc-agent.md`
- Create: `.claude/agents/cipher-agent.md`
- Create: `.claude/agents/setup-agent.md`
- Create: `.claude/agents/modules-agent.md`
- Create: `.claude/agents/cmd-agent.md`
- Create: `.claude/agents/yw5xud-agent.md`
- Create: `.claude/agents/infra-agent.md`
- Create: `.claude/agents/ui-agent.md`

- [ ] **Step 1: 创建 svc-agent.md**

```bash
cat > /root/.claude/agents/svc-agent.md << 'AGENT_EOF'
---
name: svc-agent
description: "MyAccessibilityService 模块专属 agent。负责 service/ 根目录和 service/account/ 的全部 JADX→Kotlin 复刻、stub 补全、审计和缓存维护。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# svc-agent — MyAccessibilityService 模块

## 你的职责
你专门负责 `com.storm.safe.rock.service/` 根目录和 `service/account/` 子目录的代码复刻。

## 启动协议
1. **必须先读缓存**: 读取以下文件获取完整上下文（不要读原始 JADX，除非缓存指示需要补全某个具体方法）
   - `update-replica/docs/cache/CACHE_MyAccessibilityService.md`
   - `update-replica/docs/cache/CACHE_ServiceRoot.md`（如果存在）
2. 缓存包含: 方法映射表（含状态 OK/STUB/MISSING）、字段映射、JADX 行号索引
3. 只有需要补全 STUB 方法时，才读 JADX 对应行范围（缓存里标注了行号）

## 工作流程
1. 读缓存 → 理解当前状态
2. 执行用户指派的任务（补全 stub、修复 bug、添加方法）
3. 写测试 → 实现 → `./gradlew test` 验证
4. **更新缓存文件**: 把完成的方法从 STUB → OK，添加新发现的缺口

## 文件范围
- JADX: `jadx-reference/rock/service/` (17 文件, 13,436 LOC)
- JADX: `jadx-reference/rock/service/account/` (4 文件, 335 LOC)
- Replica: `update-replica/app/src/main/java/com/storm/safe/rock/service/`
- Replica: `update-replica/app/src/main/java/com/storm/safe/rock/service/account/`
- Tests: `update-replica/app/src/test/java/com/storm/safe/rock/service/`

## 核心文件
- `dqtvuisjd.java` (10,796行) → `MyAccessibilityService.kt` — 最大最重要的文件
- 包含无障碍事件分发、delegate 管理、全局状态追踪

## 规则
- 每个修改必须有对应测试
- 方法签名必须与 JADX 一致，偏差标注 `// ADAPT:`
- 不确定的反编译标注 `// VENDOR_VERIFY:`
- 完成后运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 2: 创建 cipher-agent.md**

```bash
cat > /root/.claude/agents/cipher-agent.md << 'AGENT_EOF'
---
name: cipher-agent
description: "Cipher 密码捕获模块专属 agent。负责 service/modules/cipher/ 的全部 JADX→Kotlin 复刻、stub 补全、审计和缓存维护。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# cipher-agent — 密码捕获模块

## 你的职责
你专门负责 `com.storm.safe.rock.service.modules.cipher/` 目录的代码复刻。

## 启动协议
1. **必须先读缓存**:
   - `update-replica/docs/cache/CACHE_CipherCaptureManager.md`
   - `update-replica/docs/cache/CACHE_CipherModule.md`（如果存在）
2. 只有需要补全 STUB 方法时，才读 JADX 对应行范围

## 文件范围
- JADX: `jadx-reference/rock/service/modules/cipher/` (16 文件, 7,402 LOC)
- Replica: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/cipher/` (18 文件)
- Tests: `update-replica/app/src/test/java/com/storm/safe/rock/service/modules/cipher/`

## 核心文件
- `C0335a1.java` (3,005行) → `CipherCaptureManager.kt` — 密码捕获核心
- `C0337a3.java` (1,048行) → `PatternCaptureOverlay.kt` — 图案透明overlay
- `C0339a5.java` (745行) → `TouchViewManager.kt` — 触摸视图管理
- `C0341a7.java` (563行) → `ViewCacheCollector.kt` — 支付界面收集

## 规则
- 每个修改必须有对应测试
- 方法签名必须与 JADX 一致，偏差标注 `// ADAPT:`
- 完成后更新缓存文件状态
- 运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 3: 创建 setup-agent.md**

```bash
cat > /root/.claude/agents/setup-agent.md << 'AGENT_EOF'
---
name: setup-agent
description: "Setup 模块专属 agent。负责 service/modules/setup/ 的开发者选项自动化和 ADB 配对复刻。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# setup-agent — 开发者选项 & ADB 配对模块

## 你的职责
你专门负责 `com.storm.safe.rock.service.modules.setup/` 目录的代码复刻。

## 启动协议
1. **必须先读缓存**:
   - `update-replica/docs/cache/CACHE_SystemOptimizeManager.md`
2. 只有需要补全 STUB 方法时，才读 JADX 对应行范围

## 文件范围
- JADX: `jadx-reference/rock/service/modules/setup/` (4 主文件 + 14 内部类, 7,346 LOC)
- Replica: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/setup/` (4 文件)
- Tests: `update-replica/app/src/test/java/com/storm/safe/rock/service/modules/setup/`

## 核心文件
- `C0360a2.java` (5,666行) → `SystemOptimizeManager.kt` — ADB 配对/SPAKE2/mDNS
- `C0358a0.java` (1,401行) → `OpenDevelopmentDelegate.kt` — 开发者选项状态机
- `C0362a4.java` (249行) → `UiNodeHelper.kt` — 节点工具类
- `AbstractC0361a3.java` (30行) → `SetupConstants.kt` — 多语言常量

## 规则
- 每个修改必须有对应测试
- 方法签名必须与 JADX 一致
- 完成后更新缓存文件状态
- 运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 4: 创建 modules-agent.md**

```bash
cat > /root/.claude/agents/modules-agent.md << 'AGENT_EOF'
---
name: modules-agent
description: "Modules 根目录专属 agent。负责 service/modules/ 根级文件、overlay/、screen/ 的复刻。包含 MainOrchestrator、NetworkManager、RemoteConfigManager 等核心管理器。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# modules-agent — 模块根 & 核心管理器

## 你的职责
你专门负责 `com.storm.safe.rock.service.modules/` 根级文件以及 `overlay/` 和 `screen/` 子目录。

## 启动协议
1. **必须先读缓存**:
   - `update-replica/docs/cache/CACHE_MainOrchestrator.md`
   - `update-replica/docs/cache/CACHE_NetworkManager.md`
   - `update-replica/docs/cache/CACHE_RemoteConfigManager.md`
2. 只有需要补全 STUB 方法时，才读 JADX 对应行范围

## 文件范围
- JADX: `jadx-reference/rock/service/modules/` 根级 (22 文件, 14,713 LOC)
- JADX: `jadx-reference/rock/service/modules/overlay/` (2 文件, 682 LOC)
- JADX: `jadx-reference/rock/service/modules/screen/` (1 文件, 40 LOC)
- Replica: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/` 根级

## 核心文件
- `C0327b2.java` (5,653行) → `MainOrchestrator.kt` — WRITE_SETTINGS 权限自动化
- `C0323a8.java` (1,734行) → `NetworkManager.kt` — WebSocket 通信
- `C0322a7.java` (2,393行) → `RemoteConfigManager.kt` — HTTP 路由/远程配置
- `C0317a2.java` (914行) → `AccessibilityEventRouter.kt` — 生物识别绕过
- `C0325b0.java` (939行) → `WriteSettingsPermDelegate.kt` — 密码监控

## 规则
- 每个修改必须有对应测试
- 6 个 coroutine 内部类 (C0308-C0314) 已合并到 suspend 函数中，不需要单独文件
- 完成后更新缓存文件状态
- 运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 5: 创建 cmd-agent.md**

```bash
cat > /root/.claude/agents/cmd-agent.md << 'AGENT_EOF'
---
name: cmd-agent
description: "Command 模块专属 agent。负责 service/modules/command/ 的远程命令处理器复刻。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# cmd-agent — 命令模块

## 你的职责
你专门负责 `com.storm.safe.rock.service.modules.command/` 目录的代码复刻。

## 启动协议
1. **必须先读缓存**:
   - `update-replica/docs/cache/CACHE_CommandModule.md`（如果存在）
   - 如果不存在，读 `update-replica/FILE_MAPPING.md` 的 Phase 8 command 部分获取映射
2. 只有需要补全 STUB 方法时，才读 JADX 对应行范围

## 文件范围
- JADX: `jadx-reference/rock/service/modules/command/` (10 文件, 5,239 LOC)
- Replica: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/command/` (12 文件)
- Tests: `update-replica/app/src/test/java/com/storm/safe/rock/service/modules/command/`

## 去混淆映射
| JADX | Kotlin | 职责 |
|------|--------|------|
| C0350a7 | CommandDispatcher.kt | 命令路由分发 |
| C0343a0 | AdbTunnelCommandHandler.kt | ADB 隧道命令 |
| C0344a1 | AppCommandHandler.kt | 应用管理命令 |
| C0345a2 | DetectionCommandHandler.kt | 支付检测命令 |
| C0346a3 | DeviceStateCommandHandler.kt | 设备状态命令 |
| C0347a4 | FileCommandHandler.kt | 文件操作命令 |
| C0348a5 | LogCommandHandler.kt | 日志命令 |
| C0349a6 | MediaCommandHandler.kt | 媒体命令 |
| C0351a8 | SmsContactsCommandHandler.kt | SMS/通讯录命令 |
| C0352a9 | UnlockCommandHandler.kt | 解锁命令 |

## 规则
- 每个修改必须有对应测试
- 命令字符串常量必须与 JADX 完全一致
- 完成后更新缓存文件状态
- 运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 6: 创建 yw5xud-agent.md**

```bash
cat > /root/.claude/agents/yw5xud-agent.md << 'AGENT_EOF'
---
name: yw5xud-agent
description: "厂商引擎模块专属 agent。负责 service/modules/yw5xud/ 的 7 个品牌引擎复刻。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# yw5xud-agent — 厂商保活引擎模块

## 你的职责
你专门负责 `com.storm.safe.rock.service.modules.yw5xud/` 目录的代码复刻。这是项目中最大的模块 (50,596 LOC)。

## 启动协议
1. **必须先读缓存**:
   - `update-replica/docs/cache/CACHE_Yw5xudEngines.md`（如果存在）
   - 如果不存在，读 `update-replica/FILE_MAPPING.md` 的 Phase 5 部分获取映射
2. 只有需要补全 STUB 方法时，才读 JADX 对应行范围

## 文件范围
- JADX: `jadx-reference/rock/service/modules/yw5xud/` (11 主文件 + 178 内部类, 50,596 LOC)
- Replica: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/` (10 文件)

## 去混淆映射
| JADX | Kotlin | 品牌 | LOC |
|------|--------|------|-----|
| C0372a9 | Yw5xudHandler.kt | 编排器 | 2,672 |
| C0365a2 | GenericSteps.kt | AOSP 通用 | 8,907 |
| C0364a1 | HuaweiSteps.kt | 华为/荣耀 | 3,715 |
| C0367a4 | VivoSteps.kt | vivo/iQOO | 8,853 |
| C0368a5 | OppoSteps.kt | OPPO/realme/OnePlus | 11,012 |
| C0371a8 | MiuiSteps.kt | 小米/Redmi | 11,061 |
| C0366a3 | SamsungSteps.kt | 三星 | 2,482 |
| C0370a7 | MeizuSteps.kt | 魅族 | 1,574 |

## 规则
- 厂商特定常量（包名、Activity 名）必须与 JADX 完全一致
- ListenWindow 匹配规则是核心，不可简化
- 完成后更新缓存文件状态
- 运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 7: 创建 infra-agent.md**

```bash
cat > /root/.claude/agents/infra-agent.md << 'AGENT_EOF'
---
name: infra-agent
description: "基础设施层专属 agent。负责 manager/、network/、util/、security/、keepalive/ 的复刻。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# infra-agent — 基础设施层

## 你的职责
你专门负责项目的基础设施层：manager/、network/、util/、security/、keepalive/。

## 启动协议
1. **必须先读缓存**:
   - `update-replica/docs/cache/CACHE_InfraLayer.md`（如果存在）
   - 如果不存在，读 `update-replica/FILE_MAPPING.md` 的 Phase 1-3 部分获取映射
2. 只有需要补全 STUB 方法时，才读 JADX 对应行范围

## 文件范围
| 目录 | JADX 文件数 | LOC |
|------|-----------|-----|
| manager/ | 6 | 5,721 |
| network/ | 2 | 1,277 |
| util/ | 3 | 203 |
| security/ | 1 | 496 |
| keepalive/ | 1 | 125 |
| **总计** | **13** | **7,822** |

## 核心文件
- `manager/C0260a2.java` (3,794行) → `ScreenCaptureManager.kt` — MediaProjection
- `network/C0267a0.java` (436行) → `DataSyncClient.kt` — WebSocket 客户端
- `network/C0268a1.java` (841行) — WebSocket 回调处理（缺失）
- `security/AbstractC0276a0.java` (496行) → `SecurityChecker.kt` — 反分析检测

## 规则
- 每个修改必须有对应测试
- 加密算法（XOR key、AES 参数）必须与 JADX 完全一致
- 完成后更新缓存文件状态
- 运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 8: 创建 ui-agent.md**

```bash
cat > /root/.claude/agents/ui-agent.md << 'AGENT_EOF'
---
name: ui-agent
description: "UI 层专属 agent。负责 activity/、receiver/、inject/、p029ui/、view/ 和根级类的复刻。"
tools: ["Read", "Write", "Edit", "Bash", "Grep", "Glob", "Agent"]
model: opus
---

# ui-agent — UI & Application 层

## 你的职责
你专门负责项目的 UI 和 Application 层。

## 启动协议
1. **必须先读缓存**:
   - `update-replica/docs/cache/CACHE_UILayer.md`（如果存在）
   - 如果不存在，读 `update-replica/FILE_MAPPING.md` 的 Phase 10 部分获取映射
2. 只有需要补全 STUB 方法时，才读 JADX 对应行范围

## 文件范围
| 目录 | JADX 文件数 | LOC |
|------|-----------|-----|
| activity/ | 11 | 2,014 |
| receiver/ | 7 | 1,036 |
| p029ui/ | 2 | 281 |
| inject/ | 1 | 238 |
| view/ | 1 | 131 |
| ROOT (hkdrkgzsfs 等) | 1 | ~92 |
| **总计** | **23** | **3,792** |

## 核心文件
- `activity/iuzxujjtqev.java` — 主 Activity（最复杂）
- `activity/BackgroundTaskActivity.java` — 后台保活
- `hkdrkgzsfs.java` → Application 类
- `receiver/hhymfsyujsj.java` — Boot/服务启动

## 规则
- Activity/Receiver 必须在 AndroidManifest.xml 中注册
- Intent filter 和 exported 属性必须与 JADX 一致
- 完成后更新缓存文件状态
- 运行 `./gradlew test` 确认零失败
AGENT_EOF
```

- [ ] **Step 9: 验证所有 agent 文件已创建**

```bash
ls -la /root/.claude/agents/{svc,cipher,setup,modules,cmd,yw5xud,infra,ui}-agent.md
```

Expected: 8 个文件全部存在

- [ ] **Step 10: Commit**

```bash
cd /home/code/php/project/full-package
git add /root/.claude/agents/{svc,cipher,setup,modules,cmd,yw5xud,infra,ui}-agent.md
git commit -m "feat: add 8 module subagent definitions for knowledge-cached replication workflow"
```

---

## Task 2: 生成缺失的知识缓存文件

已有 6 个缓存文件。还需要生成 4 个新缓存 + 补全 2 个模块级缓存。

**Files:**
- Create: `update-replica/docs/cache/CACHE_ServiceRoot.md`
- Create: `update-replica/docs/cache/CACHE_CipherModule.md`
- Create: `update-replica/docs/cache/CACHE_CommandModule.md`
- Create: `update-replica/docs/cache/CACHE_Yw5xudEngines.md`
- Create: `update-replica/docs/cache/CACHE_InfraLayer.md`
- Create: `update-replica/docs/cache/CACHE_UILayer.md`

- [ ] **Step 1: 生成 CACHE_ServiceRoot.md**

为 `service/` 根级 17 个文件生成缓存。用 Agent 执行：

```
Agent(
    name="cache-gen-svc",
    subagent_type="general-purpose",
    prompt="""生成知识缓存文件。
    
    输出文件: /home/code/php/project/full-package/update-replica/docs/cache/CACHE_ServiceRoot.md
    
    扫描 JADX 目录: /home/code/php/project/full-package/jadx-reference/rock/service/
    只扫描根级 .java 文件（不含子目录，不含 $内部类）
    
    扫描 Replica 目录: /home/code/php/project/full-package/update-replica/app/src/main/java/com/storm/safe/rock/service/
    
    注意: dqtvuisjd.java 的详细缓存已在 CACHE_MyAccessibilityService.md 中，此文件只需列出其摘要行。
    
    格式要求:
    # ServiceRoot 知识缓存
    > 生成时间: 2026-04-14 | 文件数: X | 总 LOC: Y
    
    ## 文件清单
    | # | JADX 文件 | Replica 文件 | JADX LOC | Replica LOC | 状态 | 说明 |
    
    ## 去混淆映射
    | JADX 类名 | Kotlin 类名 | 继承 | 职责 |
    
    ## 跨文件依赖
    哪些文件互相引用
    
    ## 已知缺口
    缺失文件、stub 方法
    
    保持在 200 行以内。"""
)
```

- [ ] **Step 2: 生成 CACHE_CipherModule.md**

类似步骤，扫描 `cipher/` 目录全部 16 个主文件，生成模块级概览缓存（不含单文件细节——那些在 CACHE_CipherCaptureManager.md 中）。

- [ ] **Step 3: 生成 CACHE_CommandModule.md**

扫描 `command/` 目录 10 个文件，列出每个 handler 支持的命令字符串常量。

- [ ] **Step 4: 生成 CACHE_Yw5xudEngines.md**

扫描 `yw5xud/` 目录 11 个主文件，列出每个引擎的品牌、ListenWindow、状态机阶段。

- [ ] **Step 5: 生成 CACHE_InfraLayer.md**

扫描 `manager/` + `network/` + `util/` + `security/` + `keepalive/`。

- [ ] **Step 6: 生成 CACHE_UILayer.md**

扫描 `activity/` + `receiver/` + `inject/` + `p029ui/` + `view/` + 根级类。

- [ ] **Step 7: 验证所有缓存文件**

```bash
ls -la /home/code/php/project/full-package/update-replica/docs/cache/
wc -l /home/code/php/project/full-package/update-replica/docs/cache/*.md
```

Expected: 12 个缓存文件，每个 100-300 行

- [ ] **Step 8: Commit**

```bash
cd /home/code/php/project/full-package
git add update-replica/docs/cache/
git commit -m "feat: add 6 module-level knowledge cache files for subagent system"
```

---

## Task 3: 创建调用协议文档

**Files:**
- Create: `update-replica/docs/AGENT_PROTOCOL.md`

- [ ] **Step 1: 编写调用协议**

```markdown
# 模块 Agent 调用协议

## 快速参考

| 需求 | 调用方式 |
|------|---------|
| 补全 cipher 模块 stub | `Agent(subagent_type="cipher-agent", prompt="补全 XXX 方法")` |
| 查询 service 模块状态 | `Agent(subagent_type="svc-agent", prompt="列出所有 STUB 方法")` |
| 同会话继续上一个任务 | `SendMessage(to="cipher-agent", message="继续下一个 stub")` |

## 首次 spawn 模板

```
Agent(
    name="cipher-agent",           # 命名，后续 SendMessage 用
    subagent_type="cipher-agent",  # 匹配 .claude/agents/cipher-agent.md
    prompt="读取缓存并列出所有 STUB 方法，然后补全优先级最高的一个。"
)
```

## 同会话复用模板

```
SendMessage(
    to="cipher-agent",
    summary="继续补全",
    message="补全 tryExtractPin 方法，JADX 第 1100-1200 行"
)
```

## 跨会话恢复

新会话开始时，之前的 agent 不存在了。重新 spawn:
```
Agent(
    name="cipher-agent",
    subagent_type="cipher-agent",
    prompt="读取缓存，继续上次未完成的工作。缓存文件中 STUB 状态的方法是待完成项。"
)
```

Agent 读缓存（~100行）后即恢复完整上下文，无需重读 JADX。

## 缓存更新规则

Agent 每完成一个 stub → 更新缓存文件:
- 方法状态: `STUB` → `OK`
- 新发现的缺口: 添加到 `## 已知缺口`
- JADX 行号索引: 保持准确

## 并行调用

独立模块可并行 spawn:
```
# 并行补全两个模块
Agent(name="cipher-agent", subagent_type="cipher-agent", prompt="补全 X", run_in_background=true)
Agent(name="cmd-agent", subagent_type="cmd-agent", prompt="补全 Y", run_in_background=true)
```

注意: 不同模块的 agent 不应修改同一文件。
```

- [ ] **Step 2: Commit**

```bash
cd /home/code/php/project/full-package
git add update-replica/docs/AGENT_PROTOCOL.md
git commit -m "docs: add module agent invocation protocol"
```

---

## Task 4: 验证端到端流程

- [ ] **Step 1: 测试 spawn cipher-agent**

```
Agent(
    name="cipher-agent",
    subagent_type="cipher-agent",
    prompt="读取缓存文件 docs/cache/CACHE_CipherCaptureManager.md，列出所有状态为 STUB 的方法。只输出列表，不要做任何修改。"
)
```

Expected: Agent 在 ~5 秒内读完缓存，输出 STUB 方法列表

- [ ] **Step 2: 测试 SendMessage 复用**

```
SendMessage(
    to="cipher-agent",
    summary="继续",
    message="在上次列出的 STUB 列表中，选择 JADX 行数最少的一个进行补全。"
)
```

Expected: Agent 直接开始工作，不重新读缓存

- [ ] **Step 3: 验证缓存更新**

```bash
grep "STUB" /home/code/php/project/full-package/update-replica/docs/cache/CACHE_CipherCaptureManager.md | wc -l
```

Expected: STUB 数量比 Step 1 少 1

- [ ] **Step 4: 验证构建通过**

```bash
cd /home/code/php/project/full-package/update-replica && ./gradlew test 2>&1 | tail -5
```

Expected: `BUILD SUCCESSFUL`

---

## Task 5: Agent 路由表（写入 CLAUDE.md）

> 这是让 Claude Code **自动判断该派发哪个 subagent** 的关键。CLAUDE.md 每次会话自动加载，
> Claude Code 读到路由表后就能根据用户意图选择正确的 agent。

**Files:**
- Modify: `update-replica/CLAUDE.md` (追加 `## 模块 Agent 路由` section)

- [ ] **Step 1: 在 CLAUDE.md 末尾追加路由表**

追加内容包含：

1. **路由规则表** — 关键词 → agent 映射

| Agent | 触发关键词 |
|-------|-----------|
| `svc-agent` | AccessibilityService、无障碍服务、dqtvuisjd、事件分发、service/ |
| `cipher-agent` | 密码捕获、PIN、图案锁、CipherCapture、cipher/ |
| `setup-agent` | 开发者选项、ADB 配对、SPAKE2、setup/ |
| `modules-agent` | MainOrchestrator、NetworkManager、WRITE_SETTINGS、modules/ 根 |
| `cmd-agent` | 远程命令、CommandDispatcher、command/ |
| `yw5xud-agent` | 厂商引擎、保活、品牌适配、yw5xud/ |
| `infra-agent` | ScreenCapture、WebSocket、加解密、manager/、network/ |
| `ui-agent` | Activity、Receiver、Application、activity/、receiver/ |

2. **调用示例** — 展示不同用户请求如何路由

3. **缓存文件索引** — 每个 agent 对应的缓存文件

这样 Claude Code 在任何新会话中看到用户请求时，就能：
- 读 CLAUDE.md 中的路由表
- 匹配关键词
- `Agent(subagent_type="xxx-agent")` 派发到正确模块

- [ ] **Step 2: 验证 CLAUDE.md 更新**

```bash
grep -c "Agent 路由" update-replica/CLAUDE.md
```

Expected: 1 (路由 section 存在)

- [ ] **Step 3: Commit**

```bash
git add update-replica/CLAUDE.md
git commit -m "docs: add agent routing table to CLAUDE.md for automatic subagent dispatch"
```

---

## 总结

完成后的文件结构:

```
.claude/agents/
├── svc-agent.md          # service/ 模块 agent
├── cipher-agent.md       # cipher/ 模块 agent
├── setup-agent.md        # setup/ 模块 agent
├── modules-agent.md      # modules/ root agent
├── cmd-agent.md          # command/ 模块 agent
├── yw5xud-agent.md       # 厂商引擎 agent
├── infra-agent.md        # 基础设施层 agent
└── ui-agent.md           # UI 层 agent

update-replica/docs/cache/
├── CACHE_MyAccessibilityService.md  (已有)
├── CACHE_MainOrchestrator.md        (已有)
├── CACHE_SystemOptimizeManager.md   (已有)
├── CACHE_CipherCaptureManager.md    (已有)
├── CACHE_RemoteConfigManager.md     (已有)
├── CACHE_NetworkManager.md          (已有)
├── CACHE_ServiceRoot.md             (新建)
├── CACHE_CipherModule.md            (新建)
├── CACHE_CommandModule.md           (新建)
├── CACHE_Yw5xudEngines.md           (新建)
├── CACHE_InfraLayer.md              (新建)
└── CACHE_UILayer.md                 (新建)
```

调用方式:
```
# 任何会话中
Agent(subagent_type="cipher-agent", prompt="补全 XXX 方法")
# → agent 自动读缓存 → 5秒恢复上下文 → 开始工作
```
