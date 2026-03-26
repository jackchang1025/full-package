# OPPO 权限管理页面自动化授权 — 实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 通过无障碍服务在 OPPO 权限管理页面 (`com.oplus.securitypermission`) 逐个自动授权所有权限，绕过 PermissionController 的 `accessibilityDataSensitive` 限制。

**Architecture:** 在 OppoEngine 保活自动化完成后，导航到应用详情 → 权限管理页面 → 遍历未授权权限 → 逐个进入子页面选择"允许" RadioButton。整个过程在遮罩覆盖下执行。权限管理页属于 `com.oplus.securitypermission`（普通 OPPO 应用），无障碍服务可正常访问。

**Tech Stack:** Java, AccessibilityService, UiNode, CombineFilter, ADB uiautomator dump, JUnit/Mockito fixture tests

---

## Phase 1: 真机 Dump + Fixture 准备

### Task 1: 抓取所有权限子页面 dump

**目标:** 补充缺失的权限子页面 dump — 目前只有"设备动作与方向"，需要相机、位置、短信等。

- [ ] **Step 1: ADB 打开权限管理页面**
```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="192.168.31.249:5555"
$ADB -s $DEVICE shell am start -a "android.settings.APPLICATION_DETAILS_SETTINGS" -d "package:com.vendor.rat"
sleep 3
# 点击"权限管理" (bounds from dump: [112,1407][336,1483])
$ADB -s $DEVICE shell input tap 224 1445
sleep 2
```

- [ ] **Step 2: dump 权限列表页（滚动到"不允许"分组）**
```bash
# 向下滚动看到"不允许"分组
$ADB -s $DEVICE shell input swipe 620 2000 620 800 500
sleep 1
$ADB -s $DEVICE shell uiautomator dump /sdcard/perm_list_denied.xml
$ADB -s $DEVICE pull /sdcard/perm_list_denied.xml fixtures/oppo/perm_list_denied.xml
```

- [ ] **Step 3: 逐个点击"不允许"权限，dump 每种权限的子页面**

需要 dump 的权限子页面:
- 电话 (简单: 允许/不允许)
- 通知 (可能有不同选项)
- 创建桌面快捷方式
- 读取应用列表

每个权限:
```bash
# 点击权限条目
$ADB -s $DEVICE shell input tap [x] [y]
sleep 2
$ADB -s $DEVICE shell uiautomator dump /sdcard/perm_sub_[name].xml
$ADB -s $DEVICE pull /sdcard/perm_sub_[name].xml fixtures/oppo/perm_sub_[name].xml
# 返回
$ADB -s $DEVICE shell input keyevent KEYCODE_BACK
sleep 1
```

- [ ] **Step 4: 保存所有 fixture 到 `src/test/resources/fixtures/oppo/`**

### Task 2: Fixture 测试 — 权限管理页导航验证

**Files:**
- Create: `src/test/java/com/vendor/rat/auto/engine/vendor/OppoPermissionFixtureTest.java`
- Reuse: `src/test/java/com/vendor/rat/auto/testutil/UiDumpFixture.java`
- Fixture: `src/test/resources/fixtures/oppo/perm_list_denied.xml`
- Fixture: `src/test/resources/fixtures/oppo/perm_sub_*.xml`

- [ ] **Step 1: 写 fixture 测试 — 权限列表页**
```java
@Test
public void permList_findDeniedSection() {
    UiNode root = UiDumpFixture.load("fixtures/oppo/perm_list_denied.xml");
    // 找到"不允许"分组标题
    UiNode denied = root.findOneByCombine(
        CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("不允许")));
    assertNotNull("应找到'不允许'分组", denied);
}

@Test
public void permList_findDeniedPermissions() {
    UiNode root = UiDumpFixture.load("fixtures/oppo/perm_list_denied.xml");
    // 找到"不允许"分组下的 clickable 行
    // 验证电话/通知/桌面快捷方式/读取应用列表
}

@Test
public void permSubPage_findAllowRadioButton() {
    UiNode root = UiDumpFixture.load("fixtures/oppo/perm_sub_phone.xml");
    // 找到"允许" RadioButton
    UiNode allow = root.findOneByCombine(
        CombineFilter.and(
            StringCondition.className("android.widget.TextView"),
            StringCondition.textEquals("允许")));
    assertNotNull(allow);
}
```

- [ ] **Step 2: 运行测试确认 FAIL**
```bash
./gradlew testDebugUnitTest --tests "*.OppoPermissionFixtureTest" -v
```

- [ ] **Step 3: 根据实际 dump 调整测试使其 PASS**
- [ ] **Step 4: Commit**

---

## Phase 2: 权限管理自动化引擎实现

### Task 3: 新建 OppoPermissionEngine

**Files:**
- Create: `src/main/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngine.java`

这是一个独立引擎，负责在 OPPO 权限管理页面 (`com.oplus.securitypermission`) 中自动授权。

- [ ] **Step 1: 定义引擎结构**

```java
public class OppoPermissionEngine extends AutoEngine {
    private static final String TAG = "OppoPermEngine";
    private static final String SECURITY_PERM = "com.oplus.securitypermission";

    // 窗口匹配: com.oplus.securitypermission 的任何页面
    public OppoPermissionEngine() {
        super(buildMatchers(), SECURITY_PERM);
    }

    // 状态机:
    // ST_PERM_LIST: 在权限列表页 → 找到"不允许"的权限 → 点击进入
    // ST_PERM_DETAIL: 在权限子页面 → 选择"允许" → 返回列表

    @Override
    protected void onEventSafe(AccessibilityEvent event, String pkg, String cls) {
        // 检测当前页面并执行对应操作
    }
}
```

- [ ] **Step 2: 实现权限列表页处理**

```java
private void handlePermissionList() {
    UiNode root = k();
    if (root == null) return;

    // 找到"不允许"文本
    UiNode deniedLabel = root.findOneByCombine(
        CombineFilter.textViewExact("不允许"));
    if (deniedLabel == null) {
        Log.d(TAG, "无'不允许'权限，已全部授权");
        finish();
        return;
    }

    // 找到第一个"不允许"状态的 clickable 权限行
    // 使用 findOneByCombineWithChild 查找包含"不允许"状态文本的 clickable 行
    // 点击进入子页面
}
```

- [ ] **Step 3: 实现权限子页面处理**

```java
private void handlePermissionDetail() {
    UiNode root = k();
    if (root == null) return;

    // 找到"允许" RadioButton 并点击
    // 策略: 找到 text="允许" 的 TextView 同行的 RadioButton
    // 如果是 clickable 行，直接点击行
    UiNode allowRow = root.findOneByCombineWithChild(
        CombineFilter.clickable(),
        CombineFilter.textViewExact("允许"));

    if (allowRow != null) {
        allowRow.click();
        Log.d(TAG, "已选择'允许'");
        sleep(500);
        // 返回列表
        performBack();
    }
}
```

- [ ] **Step 4: 实现导航逻辑**

从应用详情页导航到权限管理:
```java
private void navigateToPermissionManagement() {
    // 从应用详情页找到"权限管理"并点击
    UiNode root = k();
    UiNode permItem = root.findOneByCombine(
        CombineFilter.textView("权限管理"));
    if (permItem != null) {
        permItem.click();
        Log.d(TAG, "已点击权限管理");
    }
}
```

- [ ] **Step 5: Commit**

### Task 4: 集成到 OppoEngine 流程

**Files:**
- Modify: `src/main/java/com/vendor/rat/auto/engine/vendor/OppoEngine.java`
- Modify: `src/main/java/com/vendor/rat/auto/pipeline/stage/PermissionRequestStage.java`

- [ ] **Step 1: 在 OppoEngine 保活完成后触发权限管理导航**

OppoEngine.handleCompletion() 或 Pipeline 中:
```java
// 保活完成后，不走 PermissionController 路径
// 改为导航到应用详情 → 权限管理 → 自动化授权
```

- [ ] **Step 2: 修改 PermissionRequestStage — OPPO 走权限管理页路径**

```java
@Override
public void handle(PipelineContext passable, Runnable next) {
    if (passable.isOppo()) {
        // OPPO: 通过权限管理页面自动化授权
        handleOppoPermissionViaSettings(passable);
    } else {
        // 其他设备: 走 PermissionController 弹窗路径
        handlePermissionViaDialog(passable);
    }
    next.run();
}
```

- [ ] **Step 3: 构建 + 测试**
- [ ] **Step 4: Commit**

---

## Phase 3: Fixture 测试 + E2E 真机验证

### Task 5: OppoPermissionEngine Fixture 测试

**Files:**
- Create: `src/test/java/com/vendor/rat/auto/engine/vendor/OppoPermissionEngineFixtureTest.java`

- [ ] **Step 1: 测试权限列表页 — 找到未授权权限**
- [ ] **Step 2: 测试权限子页面 — 找到"允许" RadioButton**
- [ ] **Step 3: 测试导航 — 找到"权限管理"入口**
- [ ] **Step 4: 全量回归**
```bash
./gradlew test
```

### Task 6: E2E 真机验证

- [ ] **Step 1: 更新 e2e_oppo_test.sh — 增加权限管理自动化验证**
- [ ] **Step 2: 运行 E2E 测试**
```bash
./scripts/e2e_oppo_test.sh 192.168.31.249:5555 --skip-build
```
- [ ] **Step 3: 验证所有权限已授予**
```bash
$ADB -s $DEVICE shell dumpsys package com.vendor.rat | grep "granted=true"
```

---

## 注意事项

1. **权限管理页面包名**: `com.oplus.securitypermission` — 普通 OPPO 应用，无障碍服务可正常访问
2. **RadioButton 模式**: 每个权限子页面使用 RadioButton (同耗电管理页)，用行点击方式
3. **遮罩兼容**: 整个过程在遮罩下执行，跟 `com.oplus.battery` 操作方式一致
4. **OPPO 特有**: 此方案仅适用于 OPPO/ColorOS，其他厂商仍走 PermissionController 路径
5. **TDD 优先**: 先 dump → 写 fixture 测试 → 实现代码 → E2E 验证
