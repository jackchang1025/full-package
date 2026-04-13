# HyperOS 3 应用详情页内联自启动 Toggle 优化

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** HyperOS 3 在应用详情页内联了"自启动" Switch 控件，引擎可以直接在此页面 toggle，无需跳转到自启动管理页面再滚动查找。

**Architecture:** 在 XiaomiDelegateTask case 1（应用详情页处理）中，`navigateAndSetPowerStrategy()` 之后、`advanceStateMachine()` 之前，新增一步：搜索应用详情页的内联"自启动" Switch，找到就直接 toggle 并标记 `s.set(true)`，跳过后续的自启动管理页跳转。如果找不到（MIUI 14 等旧版本没有内联 Switch），fallthrough 到原有的 `advanceStateMachine()` 路径。

**Tech Stack:** Java 8+, JUnit 4, Android Accessibility API

**真机数据（UI dump 确认）：**
```
应用详情页 (ApplicationsDetailsActivity) 中：
  class=android.widget.Switch  content-desc="自启动"  checkable=true  checked=false  clickable=true
    └── text="自启动"  class=android.widget.TextView (子节点)
```

---

## File Map

| 文件 | 操作 | 职责 |
|------|------|------|
| `vendor-replica/.../engine/XiaomiEngine.java` | 新增方法 | `tryToggleInlineAutoStart()` — 在应用详情页搜索并 toggle 内联自启动 Switch |
| `vendor-replica/.../delegate/task/XiaomiDelegateTask.java` | 修改 case 1 | 在 `navigateAndSetPowerStrategy()` 后调用 `tryToggleInlineAutoStart()` |
| `vendor-replica/.../engine/UiDumpMatchTest.java` | 新增测试 | 验证 app-detail dump 中自启动 Switch 的存在性和结构 |

---

### Task 1: UI dump 离线测试 — 验证应用详情页内联自启动 Switch

**Files:**
- Modify: `vendor-replica/app/src/test/java/com/guard/wallet/engine/UiDumpMatchTest.java`
- Fixture: `vendor-replica/app/src/test/resources/ui-dumps/xiaomi-13-hyperos3/app-detail-with-autostart.xml`（已存在）

- [ ] **Step 1: 添加 3 个测试方法到 UiDumpMatchTest.java**

在 `UiDumpMatchTest.java` 的 `// ═══════ locateValues.json 完整性检查 ═══════` 之前添加：

```java
    // ═══════ 应用详情页内联自启动 Switch (HyperOS 3) ═══════

    @Test
    public void appDetailWithAutostart_hasInlineAutoStartSwitch() {
        String xml = loadDump("app-detail-with-autostart.xml");
        // HyperOS 3 在应用详情页内联了 content-desc="自启动" 的 Switch
        assertTrue("应用详情页应有内联的自启动 Switch (content-desc='自启动')",
                xml.contains("content-desc=\"自启动\"")
                && xml.contains("class=\"android.widget.Switch\""));
    }

    @Test
    public void appDetailWithAutostart_autoStartSwitchIsCheckable() {
        String xml = loadDump("app-detail-with-autostart.xml");
        // Switch 节点必须是 checkable + clickable
        int idx = xml.indexOf("content-desc=\"自启动\"");
        assertTrue("自启动 Switch 应存在", idx > 0);
        int nodeStart = xml.lastIndexOf("<node", idx);
        int nodeEnd = xml.indexOf("/>", idx);
        if (nodeEnd == -1) nodeEnd = xml.indexOf("</node>", idx);
        String node = xml.substring(nodeStart, Math.min(nodeEnd + 2, xml.length()));
        assertTrue("自启动 Switch 应可勾选", node.contains("checkable=\"true\""));
        assertTrue("自启动 Switch 应可点击", node.contains("clickable=\"true\""));
    }

    @Test
    public void appDetailWithAutostart_autoStartDefaultUnchecked() {
        String xml = loadDump("app-detail-with-autostart.xml");
        int idx = xml.indexOf("content-desc=\"自启动\"");
        int nodeStart = xml.lastIndexOf("<node", idx);
        int nodeEnd = xml.indexOf("/>", idx);
        if (nodeEnd == -1) nodeEnd = xml.indexOf("</node>", idx);
        String node = xml.substring(nodeStart, Math.min(nodeEnd + 2, xml.length()));
        assertTrue("自启动 Switch 默认应未勾选", node.contains("checked=\"false\""));
    }
```

- [ ] **Step 2: 运行测试确认 PASS**

```bash
cd vendor-replica && JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 ./gradlew testDebugUnitTest --tests "com.guard.wallet.engine.UiDumpMatchTest" --console=plain 2>&1 | tail -10
```

预期: 15 个测试全部 PASS（12 原有 + 3 新增）。

- [ ] **Step 3: 提交**

```bash
git add vendor-replica/app/src/test/java/com/guard/wallet/engine/UiDumpMatchTest.java
git commit -m "test(vendor-replica): add UI dump tests for HyperOS 3 inline autostart Switch

3 new tests verify the app-detail page has an inline autostart Switch
(content-desc='自启动', checkable, clickable, default unchecked).
This Switch exists on HyperOS 3 but not on MIUI 14."
```

---

### Task 2: XiaomiEngine 新增 `tryToggleInlineAutoStart()` 方法

**Files:**
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/engine/XiaomiEngine.java`

- [ ] **Step 1: 在 `toggleAutoStart()` 方法之前添加新方法**

在 XiaomiEngine.java 中，`toggleAutoStart()` 方法之前（约第 353 行），插入：

```java
    /**
     * ADAPT: HyperOS 3 在应用详情页 (ApplicationsDetailsActivity) 内联了"自启动" Switch。
     * 无需跳转到 AutoStartManagementActivity 再滚动查找。
     *
     * 搜索 content-desc="自启动" 的 Switch 节点，若找到且未勾选则点击 toggle。
     *
     * @return true 若找到并成功 toggle（或已经是勾选状态）
     */
    public final boolean tryToggleInlineAutoStart() {
        try {
            if (this.k() == null) return false;

            // 构建 filter: class=Switch + content-desc 包含 "自启动"
            CombineFilter filter = new CombineFilter();
            filter.setStringConditions(new LinkedList<>());
            StringCondition classCond = new StringCondition();
            classCond.setProperty("className");
            classCond.setEquals("android.widget.Switch");
            filter.getStringConditions().add(classCond);
            StringCondition descCond = new StringCondition();
            descCond.setProperty("desc");
            descCond.setContains("自启动");
            filter.getStringConditions().add(descCond);

            UiObject switchNode = this.k().findOneByCombine(filter);
            if (switchNode == null) {
                Log.d("o.q", "应用详情页未找到内联自启动 Switch (非 HyperOS 3?)");
                return false;
            }

            Log.d("o.q", "HyperOS 3 应用详情页内联自启动 Switch 已找到");
            boolean checked = switchNode.checked();
            if (checked) {
                Log.d("o.q", "内联自启动 Switch 已勾选，无需操作");
                return true;
            }

            // 点击 toggle
            if (switchNode.click()) {
                Log.d("o.q", "已点击内联自启动 Switch");
                // 等待 UI 刷新后验证
                com.guard.wallet.utils.SystemHelper.T0(3);
                switchNode.refresh();
                checked = switchNode.checked();
                if (checked) {
                    Log.d("o.q", "内联自启动 Switch 已成功勾选");
                    return true;
                }
            }

            // fallback: 尝试手势点击 Switch 右侧
            int tapX = switchNode.boundsInScreen().right - 50;
            int tapY = (int) switchNode.centerInScreen().getY();
            if (com.guard.wallet.utils.SystemHelper.s(tapX, tapY)) {
                Log.d("o.q", "已手势点击内联自启动 Switch");
                com.guard.wallet.utils.SystemHelper.T0(3);
                switchNode.refresh();
                checked = switchNode.checked();
                if (checked) {
                    Log.d("o.q", "内联自启动 Switch 手势勾选成功");
                    return true;
                }
            }

            Log.e("o.q", "内联自启动 Switch 点击后仍未勾选");
            return false;
        } catch (Exception ex) {
            AppUtils.s("o.q", ex);
            return false;
        }
    }
```

- [ ] **Step 2: 编译确认**

```bash
cd vendor-replica && JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 ./gradlew compileDebugJavaWithJavac --console=plain 2>&1 | tail -5
```

预期: BUILD SUCCESSFUL

- [ ] **Step 3: 提交**

```bash
git add vendor-replica/app/src/main/java/com/guard/wallet/engine/XiaomiEngine.java
git commit -m "feat(vendor-replica): XiaomiEngine.tryToggleInlineAutoStart() for HyperOS 3

HyperOS 3 inlines an autostart Switch (content-desc='自启动') directly
on the app detail page. This method searches for it and toggles it,
avoiding the unreliable jump to AutoStartManagementActivity + scroll."
```

---

### Task 3: XiaomiDelegateTask case 1 接入内联 toggle

**Files:**
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/delegate/task/XiaomiDelegateTask.java`

- [ ] **Step 1: 修改 case 1 在 `navigateAndSetPowerStrategy()` 之后尝试内联 toggle**

当前 case 1 的逻辑是：
```java
case 1:
    while (!engine.isInAppDetailWindow() && counter++ < 20) { sleep(1); }
    engine.navigateAndSetPowerStrategy();
    engine.advanceStateMachine();
```

修改为：
```java
            case 1:
                /* Poll loop: wait for app detail window, set power strategy, then autostart */
                engine.getClass();
                try {
                    AtomicInteger counter = new AtomicInteger(0);
                    while (!engine.isInAppDetailWindow() && counter.incrementAndGet() < 20) {
                        com.guard.wallet.utils.SystemHelper.T0(1);
                    }
                    engine.navigateAndSetPowerStrategy();

                    // ADAPT: HyperOS 3 在应用详情页内联了"自启动" Switch。
                    // 尝试直接 toggle，成功则标记 s=true 并跳过 advanceStateMachine()
                    // 的 AutoStartManagementActivity 跳转（那条路径在 HyperOS 3 上滚动搜索不稳定）。
                    if (engine.tryToggleInlineAutoStart()) {
                        engine.s.set(true);
                        Log.d("o.q", "HyperOS 3 内联自启动已完成，跳过自启动管理页");
                    }

                    engine.advanceStateMachine();
                } catch (Exception ex) {
                    AppUtils.s("o.q", ex);
                }
                return;
```

注意：`advanceStateMachine()` 仍然被调用。如果 `s.get() == true`（内联 toggle 成功），`advanceStateMachine()` 内部会跳过 `启动MIUI自启动管理` 分支，直接走 `savePowerControlState() → Z()` 结束引擎。如果内联 toggle 失败（MIUI 14 没有内联 Switch），`s.get() == false`，`advanceStateMachine()` 走原有路径跳转自启动管理页。**两条路径兼容。**

- [ ] **Step 2: 编译确认**

```bash
cd vendor-replica && JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 ./gradlew compileDebugJavaWithJavac --console=plain 2>&1 | tail -5
```

- [ ] **Step 3: 运行全部测试确认无回归**

```bash
cd vendor-replica && JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 ./gradlew test --console=plain 2>&1 | tail -5
```

预期: 84 个测试全部 PASS (81 + 3 新增)。

- [ ] **Step 4: 提交**

```bash
git add vendor-replica/app/src/main/java/com/guard/wallet/delegate/task/XiaomiDelegateTask.java
git commit -m "feat(vendor-replica): wire tryToggleInlineAutoStart() into XiaomiDelegateTask case 1

In case 1 (app detail page), after navigateAndSetPowerStrategy(),
try the inline autostart Switch before advanceStateMachine().
If successful, s=true skips the unreliable AutoStartManagementActivity
scroll path. Falls through to original path on MIUI 14 (no inline Switch)."
```

---

### Task 4: 构建 APK + 真机冒烟验证

**Files:**
- 无代码修改

- [ ] **Step 1: 构建 APK**

```bash
cd vendor-replica && JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 ./gradlew :app:assembleDebug --console=plain 2>&1 | tail -3
```

- [ ] **Step 2: 清洁安装**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEVICE=192.168.31.102:39851
PKG=com.guard.wallet

$ADB -s $DEVICE shell "am force-stop $PKG"
$ADB -s $DEVICE uninstall $PKG
$ADB -s $DEVICE shell "rm -rf /sdcard/Android/data/$PKG"
$ADB -s $DEVICE install -r -g app/build/outputs/apk/debug/app-debug.apk
$ADB -s $DEVICE shell "pm revoke $PKG android.permission.WRITE_SECURE_SETTINGS"
$ADB -s $DEVICE shell "am force-stop $PKG"
sleep 3
$ADB -s $DEVICE logcat -c
$ADB -s $DEVICE shell am start -W -n "$PKG/.activity.MainActivity" \
    -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
```

- [ ] **Step 3: 开启无障碍 + 触发引擎**

```bash
# 用户手动开启无障碍服务后：
$ADB -s $DEVICE shell am start -n "$PKG/.activity.MainActivity"
sleep 3
$ADB -s $DEVICE logcat -c
$ADB -s $DEVICE shell "curl -s http://127.0.0.1:7910/testOppoKeepAlive"
sleep 60
PID=$($ADB -s $DEVICE shell "pidof $PKG" | tr -d '\r')
$ADB -s $DEVICE logcat -d --pid="$PID" -v time | grep -E "o\.q"
```

- [ ] **Step 4: 验证日志**

**预期日志序列（HyperOS 3 新路径）：**

```
o.q: 已进入App省电策略窗口
o.q: HyperOS 3 省电策略直达: 已在省电策略窗口
o.q: keepAliveInAppPowerStrategy 窗口匹配
o.q: 已勾选无限制,不采取任何限制措施              ← 省电策略 OK
o.q: com.guard.wallet 启动成功
o.q: 已进入App详情窗口
o.q: 耗电策略查找成功
o.q: 已点击电量消耗、耗电策略栏目
o.q: HyperOS 3 应用详情页内联自启动 Switch 已找到 ← ★ 新路径
o.q: 已点击内联自启动 Switch                       ← ★
o.q: 内联自启动 Switch 已成功勾选                   ← ★
o.q: HyperOS 3 内联自启动已完成，跳过自启动管理页    ← ★
o.q: 已保存主进程保活策略
o.q: 准备结束本地保活自动化引擎
o.q: 已结束本地保活自动化引擎
```

**判定标准：**
- **PASS**: 出现 `内联自启动 Switch 已成功勾选` 且 `已保存主进程保活策略`
- **PARTIAL**: 出现 `应用详情页未找到内联自启动 Switch` 但后续走自启动管理页路径成功
- **FAIL**: 既没找到内联 Switch 也没走通自启动管理页路径

---

## Definition of Done

- [ ] 3 个 UI dump 测试 PASS（内联 Switch 存在性 + 可点击 + 默认未勾选）
- [ ] 84 个总测试 PASS（81 + 3 新增）
- [ ] `tryToggleInlineAutoStart()` 方法存在且编译通过
- [ ] XiaomiDelegateTask case 1 在 `navigateAndSetPowerStrategy()` 后调用 `tryToggleInlineAutoStart()`
- [ ] 真机验证：日志含 `内联自启动 Switch 已成功勾选`
- [ ] 3 个 focused commit
- [ ] 对 MIUI 14 无副作用（找不到内联 Switch 时 fallthrough 到原有路径）
