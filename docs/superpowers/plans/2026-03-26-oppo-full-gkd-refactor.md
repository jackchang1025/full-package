# OPPO 自动化保活和授权完全重构为 GKD Selector

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 将 OppoEngine 和 OppoPermissionEngine 的所有 CombineFilter 节点匹配逻辑完全替换为 GKD Selector，提升代码可维护性。

**Architecture:**
- 基于已集成的 GKD Selector 和 GkdSelectorHelper
- 将所有硬编码的节点匹配逻辑转为 GKD 选择器字符串
- 保持现有 Engine 架构和状态机不变
- 仅替换节点查找层

**Tech Stack:**
- GKD Selector (已集成)
- GkdSelectorHelper (已实现)
- Java 17 + Kotlin 2.1.0

**Prerequisites:**
- GKD Selector 已集成并编译成功
- GkdSelectorHelper 已实现
- OppoPermissionEngine 已部分重构（2/5 处）

---

## 文件结构

### 修改文件
- `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java` - 保活引擎
- `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java` - 权限引擎

---

## Task 1: 审计 OppoEngine 的 CombineFilter 使用

**Files:**
- Read: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java`

- [ ] **Step 1: 搜索所有 CombineFilter 使用**

```bash
grep -n "CombineFilter\|StringCondition\|findNode" android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java | head -20
```

- [ ] **Step 2: 创建审计报告**

记录所有需要替换的位置，包括：
- 行号
- 用途（Switch 查找、TextView 查找等）
- 当前 Filter 逻辑
- 对应的 GKD 选择器

- [ ] **Step 3: Commit 审计报告**

```bash
git add docs/superpowers/plans/2026-03-26-oppo-engine-audit.md
git commit -m "docs: audit OppoEngine CombineFilter usage"
```

---

## Task 2: 重构 OppoEngine 使用 GKD Selector

**Files:**
- Modify: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java`

- [ ] **Step 1: 添加导入**

```java
import com.vendor.rat.auto.util.GkdSelectorHelper;
```

- [ ] **Step 2: 替换所有 CombineFilter**

逐个替换，每次替换后验证编译。

- [ ] **Step 3: 验证编译**

```bash
cd android && ./gradlew :app:assembleDebug
```

- [ ] **Step 4: Commit**

```bash
git add android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java
git commit -m "refactor: replace all CombineFilter with GKD Selector in OppoEngine"
```

---

## Task 3: 完成 OppoPermissionEngine 剩余重构

**Files:**
- Modify: `android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java`

- [ ] **Step 1: 查看审计报告**

参考 `/home/code/php/project/full-package/docs/superpowers/plans/2026-03-26-oppo-permission-engine-audit.md`

剩余 3 处需要替换。

- [ ] **Step 2: 替换剩余 CombineFilter**

逐个替换并验证。

- [ ] **Step 3: 验证编译**

```bash
./gradlew :app:assembleDebug
```

- [ ] **Step 4: Commit**

```bash
git add android/app/src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java
git commit -m "refactor: complete GKD Selector migration in OppoPermissionEngine"
```

---

## Task 4: 端到端测试

- [ ] **Step 1: 编译 APK**

```bash
cd android && ./gradlew assembleDebug
```

- [ ] **Step 2: 安装到测试设备（可选）**

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

- [ ] **Step 3: 查看日志**

```bash
adb logcat | grep "OppoEngine\|OppoPermissionEngine\|GkdSelector"
```

---

## 验收标准

- [ ] OppoEngine 所有 CombineFilter 已替换
- [ ] OppoPermissionEngine 所有 CombineFilter 已替换
- [ ] APK 编译成功
- [ ] 代码已提交

---

## 预估时间

- Task 1: 30 分钟
- Task 2: 2 小时
- Task 3: 1 小时
- Task 4: 30 分钟

**总计：4 小时**

