# HyperOS 3 ListenWindow 匹配修复 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 修复 HyperOS 3 (Android 16) 上 `AccessibilityEvent.getClassName()` 返回泛化值 `"android.view.View"` 导致所有 7 个保活引擎的 ListenWindow 匹配全面失效的问题。

**Architecture:** 在 `ListenWindow.equals()` 中增加 Android 16 泛化 className 的降级匹配逻辑（仅比 packageName），并在所有使用独立 `if` + 同 packageName 窗口检测的引擎 `u()` 方法中增加 `else if` 互斥防护。

**Tech Stack:** Java 8+, JUnit 4.13.2, Android Accessibility API

**Root Cause（来自审计报告）：**

```
HyperOS 3 (Android 16) 的 AccessibilityEvent.getClassName() 不再返回真实 Activity 类名，
而是返回 "android.view.View"。

调用链:
  MyAccessibilityService.G():619-623 → eventClass = "android.view.View"
    → v2.set("android.view.View")
    → AccessibilityDelegate.c():329 → ListenWindow.equals():128 → false
    → XiaomiEngine.u():597 → f0() → q():849-850 → ListenWindow.equals() → false
    → h0() → q() → false
    → 没有后续 task 排队 → 100s 定时器 Z() 清理退出
```

---

## File Map

| 文件 | 操作 | 职责 |
|------|------|------|
| `vendor-replica/.../req/ListenWindow.java` | 修改 :126-129 | `equals()` 增加泛化 className 降级匹配 |
| `vendor-replica/.../engine/XiaomiEngine.java` | 修改 :612 | `u()` 增加 `f0()` / `h0()` 互斥 |
| `vendor-replica/.../engine/HuaweiEngine.java` | 修改 :475,484 | `u()` 增加 `j0()` / `i0()` / `k0()` 互斥 |
| `vendor-replica/.../engine/AospKeepAliveEngine.java` | 修改 :425 | `u()` 增加 `i0()` / `h0()` 互斥 |
| `vendor-replica/.../engine/TranssionEngine.java` | 修改 :425,434 | `u()` 增加 `k0()` / `j0()` / `l0()` 互斥 |
| `vendor-replica/.../req/ListenWindowTest.java` | 新建 | ListenWindow.equals() 的单元测试 |

**不需要互斥修改的引擎（已审计确认安全）：**
- `OppoEngine` — `k0()` 用 `com.android.settings`, `l0()` 用 `com.oplus.battery`, `m0()` 用 `com.coloros.oppoguardelf`，packageName 不同，降级后不冲突
- `VivoEngine` — 使用 state 状态机 + 条件守卫 (`Objects.equals(stateRef.get(), "prepareInXxx") && this.xxx()`)，天然互斥

**不修改的文件：**
- `AccessibilityDelegate.java` — `c()` 和 `q()` 都委托给 `ListenWindow.equals()`，修复 equals 即可
- `MyAccessibilityService.java` — `G()` 方法从 event 取 className 是正确行为，问题在下游匹配

---

### Task 1: ListenWindow.equals() 泛化 className 降级匹配

**Files:**
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/req/ListenWindow.java:126-129`
- Test: `vendor-replica/app/src/test/java/com/guard/wallet/req/ListenWindowTest.java`

**设计决策：**

`ListenWindow.equals()` 当前逻辑在第 119 行有一个分支："一方 className 为空 → 仅比 packageName"。
泛化 className（`android.view.View`）在语义上等价于"className 未知"，所以在 SoftInputWindow 特判之后、
正常路径之前，插入同样的 packageName-only 降级逻辑。

经过审计确认，**只有 `android.view.View`** 是需要处理的泛化值：
- `android.widget.FrameLayout` 被 OppoEngine、TranssionEngine、XiaomiEngine 的 `m0()` 用作精确匹配目标，**不能降级**
- `MyAccessibilityService.G():630-631` 已有 `systemui + android.view.View` 的过滤（直接 ignore），所以 systemui 的泛化 View 不会到达引擎

**hashCode 不一致风险：** `hashCode()` 仍然是 `Objects.hash(packageName, className)`，但 equals 降级后两个 `equals=true` 的对象可能 hashCode 不同。这违反 Java 契约，但当前代码仅用 `ConcurrentLinkedQueue.contains()` 和手动 iterator（不依赖 hashCode），所以**运行时安全**。不修改 hashCode 是有意为之——若改为仅 hash packageName 会降低 HashSet 场景的分布质量，且当前无此使用场景。在测试中用注释标记此设计决策。

- [ ] **Step 1: 编写 ListenWindowTest.java 测试文件**

创建 `vendor-replica/app/src/test/java/com/guard/wallet/req/ListenWindowTest.java`:

```java
package com.guard.wallet.req;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * ListenWindow.equals() 单元测试。
 *
 * 覆盖场景:
 *   1-2.  正常匹配 / 不匹配 (pkg+cls)
 *   3-7.  HyperOS 3 泛化 className 降级 (5 tests)
 *   8.    FrameLayout 不是泛化值
 *   9-11. 原有行为回归 (空 className / 空 packageName / SoftInputWindow)
 *  12-14. equals 基本契约 (自反性 / null / 类型)
 *  15.    对称性: a.equals(b) == b.equals(a) 对泛化 className
 */
public class ListenWindowTest {

    // ═══════ 正常匹配 ═══════

    @Test
    public void exactMatch_samePackageAndClass() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    public void noMatch_samePackageDifferentClass() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
        assertFalse(a.equals(b));
    }

    // ═══════ HyperOS 3 泛化 className 降级 ═══════

    @Test
    public void hyperOs3_actualIsGenericView_fallbackToPackageOnly() {
        // 模拟真机场景: currentWindow className = "android.view.View"
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow actual = new ListenWindow("com.miui.securitycenter",
                "android.view.View");
        assertTrue("HyperOS 3 泛化 className 应降级为仅比 packageName",
                expected.equals(actual));
    }

    @Test
    public void hyperOs3_expectedIsGenericView_fallbackToPackageOnly() {
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "android.view.View");
        ListenWindow actual = new ListenWindow("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
        assertTrue(expected.equals(actual));
    }

    @Test
    public void hyperOs3_differentPackage_noFalsePositive() {
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow actual = new ListenWindow("com.android.settings",
                "android.view.View");
        assertFalse("不同 packageName 即使 className 泛化也不应匹配",
                expected.equals(actual));
    }

    @Test
    public void hyperOs3_bothGenericView_samePackage() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter", "android.view.View");
        ListenWindow b = new ListenWindow("com.miui.securitycenter", "android.view.View");
        assertTrue(a.equals(b));
    }

    @Test
    public void hyperOs3_bothGenericView_differentPackage() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter", "android.view.View");
        ListenWindow b = new ListenWindow("com.android.settings", "android.view.View");
        assertFalse(a.equals(b));
    }

    // ═══════ FrameLayout 不是泛化值 ═══════

    @Test
    public void frameLayout_isNotGeneric_requiresExactMatch() {
        // FrameLayout 被 OppoEngine/TranssionEngine/XiaomiEngine.m0() 用作精确匹配
        ListenWindow expected = new ListenWindow("com.miui.securitycenter",
                "android.widget.FrameLayout");
        ListenWindow actual = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertFalse("FrameLayout 不是泛化值，不应降级匹配",
                expected.equals(actual));
    }

    // ═══════ 原有行为回归 ═══════

    @Test
    public void emptyClassName_fallbackToPackageOnly() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter", null);
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertTrue("空 className 应降级为仅比 packageName", a.equals(b));
    }

    @Test
    public void emptyPackageName_fallbackToClassOnly() {
        ListenWindow a = new ListenWindow(null,
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        assertTrue("空 packageName 应降级为仅比 className", a.equals(b));
    }

    @Test
    public void softInputWindow_fallbackToPackageOnly() {
        ListenWindow a = new ListenWindow("com.example.ime",
                "android.inputmethodservice.SoftInputWindow");
        ListenWindow b = new ListenWindow("com.example.ime",
                "com.example.ime.SomeActivity");
        assertTrue("SoftInputWindow 应降级为仅比 packageName", a.equals(b));
    }

    // ═══════ equals 基本契约 ═══════

    @Test
    public void reflexive() {
        ListenWindow a = new ListenWindow("pkg", "cls");
        assertTrue(a.equals(a));
    }

    @Test
    public void nullSafe() {
        ListenWindow a = new ListenWindow("pkg", "cls");
        assertFalse(a.equals(null));
    }

    @Test
    public void differentType() {
        ListenWindow a = new ListenWindow("pkg", "cls");
        assertFalse(a.equals("not a ListenWindow"));
    }

    // ═══════ 对称性 ═══════

    @Test
    public void symmetric_genericViewFallback() {
        ListenWindow a = new ListenWindow("com.miui.securitycenter",
                "com.miui.appmanager.ApplicationsDetailsActivity");
        ListenWindow b = new ListenWindow("com.miui.securitycenter",
                "android.view.View");
        assertEquals("equals 必须对称: a.equals(b) == b.equals(a)",
                a.equals(b), b.equals(a));
    }

    // 注: hashCode 不一致是 known trade-off (见 plan 设计决策)。
    // 当前仅用 ConcurrentLinkedQueue.contains() 和手动 iterator，不依赖 hashCode。
    // 若将来引入 HashSet<ListenWindow> 使用场景，需同步修改 hashCode。
}
```

- [ ] **Step 2: 运行测试，确认 HyperOS 3 降级测试 FAIL，原有行为测试 PASS**

```bash
cd vendor-replica && ./gradlew test --tests "com.guard.wallet.req.ListenWindowTest" --console=plain 2>&1 | tail -30
```

预期: `hyperOs3_actualIsGenericView_fallbackToPackageOnly` 等 4 个测试 FAIL（期望 true 得到 false），`symmetric_genericViewFallback` FAIL。其余 10 个 PASS。

- [ ] **Step 3: 修改 ListenWindow.equals() 增加泛化降级**

修改 `vendor-replica/app/src/main/java/com/guard/wallet/req/ListenWindow.java`。

在第 126 行（SoftInputWindow 特判 `return` 之后）、第 128 行（`// Normal` 之前），插入 4 行新代码。修改后的 :122-133 应为：

```java
            // SoftInputWindow special case: compare packageName only
            if ("android.inputmethodservice.SoftInputWindow".equals(this.className)
                    || "android.inputmethodservice.SoftInputWindow".equals(other.className)) {
                return Objects.equals(this.packageName, other.packageName);
            }
            // ADAPT: HyperOS 3 (Android 16) AccessibilityEvent.getClassName()
            // returns "android.view.View" instead of the real Activity class.
            // Fall back to packageName-only when either side is this generic value.
            // FrameLayout excluded — used as exact match target by multiple engines.
            if ("android.view.View".equals(this.className)
                    || "android.view.View".equals(other.className)) {
                return Objects.equals(this.packageName, other.packageName);
            }
            // Normal: both must match
            return Objects.equals(this.packageName, other.packageName)
                    && Objects.equals(this.className, other.className);
```

- [ ] **Step 4: 运行全部 ListenWindowTest，确认全部 PASS**

```bash
cd vendor-replica && ./gradlew test --tests "com.guard.wallet.req.ListenWindowTest" --console=plain 2>&1 | tail -30
```

预期: 15 个测试全部 PASS。

- [ ] **Step 5: 运行全部现有测试，确认无回归**

```bash
cd vendor-replica && ./gradlew test --console=plain 2>&1 | tail -20
```

预期: 54 + 15 = 69 个测试全部 PASS（GkdNodeFinder 11 + CombineFilterConverter 20 + LocateValuesAsset 11 + LocateValuesSeeder 12 + ListenWindow 15）。

- [ ] **Step 6: 提交**

```bash
git add vendor-replica/app/src/test/java/com/guard/wallet/req/ListenWindowTest.java \
        vendor-replica/app/src/main/java/com/guard/wallet/req/ListenWindow.java
git commit -m "fix(vendor-replica): ListenWindow.equals() fallback for HyperOS 3 generic className

HyperOS 3 (Android 16) returns 'android.view.View' instead of real
Activity class names in AccessibilityEvent.getClassName(). This caused
all 7 keep-alive engines to fail window matching via ListenWindow.equals().

When either side has className='android.view.View', fall back to
packageName-only comparison. FrameLayout intentionally excluded as
multiple engines use it as an exact match target.

15 new unit tests covering: HyperOS 3 fallback (5), FrameLayout
exclusion (1), regression (3), equals contract (3), symmetry (1),
baseline (2)."
```

---

### Task 2: 所有受影响引擎 u() 增加 f0()/h0() 互斥防护

**Files:**
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/engine/XiaomiEngine.java:612`
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/engine/HuaweiEngine.java:475,484`
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/engine/AospKeepAliveEngine.java:425`
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/engine/TranssionEngine.java:425,434`

**设计决策：**

Task 1 的 ListenWindow 修复使得 `q()` 在 `className="android.view.View"` 时降级为仅比 packageName。
这导致 **同一 packageName 下不同 Activity 对应的窗口检测方法可能同时返回 true**，需要 `else if` 互斥。

审计确认需要修改的 4 个引擎及其冲突的 packageName：

| 引擎 | 冲突的检测方法 | 共享 packageName |
|------|--------------|-----------------|
| XiaomiEngine | `f0()` vs `h0()` | `com.miui.securitycenter` |
| HuaweiEngine | `j0()` vs `i0()` vs `k0()` | `com.android.settings` (j0, i0) |
| AospKeepAliveEngine | `i0()` vs `h0()` | `com.android.settings` |
| TranssionEngine | `k0()` vs `j0()` | `com.android.settings` |

**不需要修改的引擎：**
- `OppoEngine` — 4 个检测方法使用 3 个不同 packageName，降级后不冲突
- `VivoEngine` — 使用 state 状态机守卫 (`Objects.equals(stateRef.get(), "prepareInXxx")`)，天然互斥

**修改原则：** 每个引擎的 `u()` 中，对同 packageName 的独立 `if` 改为 `if / else if` 链。不同 packageName 的保持独立 `if`。

- [ ] **Step 1: XiaomiEngine.java — `if` → `else if`**

修改 `vendor-replica/app/src/main/java/com/guard/wallet/engine/XiaomiEngine.java:612`。

将：
```java
            if (this.h0()) {
```
改为：
```java
            // ADAPT: HyperOS 3 packageName-only fallback 后 f0() 和 h0() 可能同时 true
            // (同属 com.miui.securitycenter)。改为 else if 让 f0() 优先。
            // vendor MIUI 14 上两者不可能同时 true，语义等价。
            else if (this.h0()) {
```

- [ ] **Step 2: HuaweiEngine.java — 3 个检测方法的互斥**

修改 `vendor-replica/app/src/main/java/com/guard/wallet/engine/HuaweiEngine.java`。

`j0()` (com.android.settings/HWSettings)、`i0()` (com.android.settings/AppAndNotification)、`k0()` (com.huawei.systemmanager 或 com.hihonor.systemmanager) 中，j0 和 i0 共享 `com.android.settings`。`h0()` 用 AlertDialog (com.huawei.systemmanager/com.hihonor.systemmanager)，与 k0 可能冲突。

将第 475 行 `if (i0())` 改为 `else if (i0())`，第 484 行 `if (k0())` 改为 `else if (k0())`，第 493 行 `if (h0())` 改为 `else if (h0())`：

```java
            if (inHwSettings) {                                     // j0() — 行 466
                // ... 保持不变
            }
            // ADAPT: HyperOS 3 降级后 j0()/i0() 同属 com.android.settings 可能同时 true
            else if (i0()) {                                        // 行 475
                // ... 保持不变
            }
            // ADAPT: k0()/h0() 同属 com.huawei.systemmanager 可能同时 true
            else if (k0()) {                                        // 行 484
                // ... 保持不变
            }
            else if (h0()) {                                        // 行 493
                // ... 保持不变
            }
```

- [ ] **Step 3: AospKeepAliveEngine.java — `if` → `else if`**

修改 `vendor-replica/app/src/main/java/com/guard/wallet/engine/AospKeepAliveEngine.java:425`。

将：
```java
            if (h0()) {
```
改为：
```java
            // ADAPT: HyperOS 3 降级后 i0()/h0() 同属 com.android.settings 可能同时 true
            else if (h0()) {
```

- [ ] **Step 4: TranssionEngine.java — 2 处 `if` → `else if`**

修改 `vendor-replica/app/src/main/java/com/guard/wallet/engine/TranssionEngine.java`。

`k0()` 和 `j0()` 同属 `com.android.settings`。`l0()` 用 `com.transsion.phonemaster`，与前两者不冲突但为安全起见也链入。

将第 425 行 `if (this.j0())` 改为 `else if (this.j0())`，第 434 行 `if (this.l0())` 改为 `else if (this.l0())`：

```java
            if (inAppDetail) {                                      // k0() — 行 416
                // ... 保持不变
            }
            // ADAPT: HyperOS 3 降级后 k0()/j0() 同属 com.android.settings 可能同时 true
            else if (this.j0()) {                                   // 行 425
                // ... 保持不变
            }
            else if (this.l0()) {                                   // 行 434，com.transsion.phonemaster 不冲突但保持链
                // ... 保持不变
            }
```

- [ ] **Step 5: 运行全部测试确认无回归**

```bash
cd vendor-replica && ./gradlew test --console=plain 2>&1 | tail -20
```

预期: 69 个测试全部 PASS。

- [ ] **Step 6: 提交**

```bash
git add vendor-replica/app/src/main/java/com/guard/wallet/engine/XiaomiEngine.java \
        vendor-replica/app/src/main/java/com/guard/wallet/engine/HuaweiEngine.java \
        vendor-replica/app/src/main/java/com/guard/wallet/engine/AospKeepAliveEngine.java \
        vendor-replica/app/src/main/java/com/guard/wallet/engine/TranssionEngine.java
git commit -m "fix(vendor-replica): add else-if mutual exclusion to 4 engine u() methods

After ListenWindow.equals() HyperOS 3 fallback to packageName-only,
window detection methods sharing the same packageName may simultaneously
return true. Convert independent if-if chains to if-else-if in:

- XiaomiEngine: f0()/h0() — com.miui.securitycenter
- HuaweiEngine: j0()/i0()/k0()/h0() — com.android.settings, com.huawei.systemmanager
- AospKeepAliveEngine: i0()/h0() — com.android.settings
- TranssionEngine: k0()/j0()/l0() — com.android.settings

OppoEngine and VivoEngine not affected (different packageNames / state guard)."
```

---

### Task 3: 构建 APK + 真机冒烟验证

**Files:**
- 无代码修改，使用脚本执行

**前提:** Task 1 和 Task 2 的提交已完成。

- [ ] **Step 1: 构建 debug APK**

```bash
cd vendor-replica && JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 ./gradlew :app:assembleDebug --console=plain 2>&1 | tail -10
```

预期: `BUILD SUCCESSFUL`

- [ ] **Step 2: 清洁安装到小米 13**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
DEVICE=192.168.31.102:39851
PKG=com.guard.wallet

$ADB -s $DEVICE shell "am force-stop $PKG; pm clear $PKG" 2>&1
$ADB -s $DEVICE uninstall $PKG 2>&1
$ADB -s $DEVICE shell "rm -rf /sdcard/Android/data/$PKG" 2>&1
$ADB -s $DEVICE install -r -g app/build/outputs/apk/debug/app-debug.apk
$ADB -s $DEVICE shell "pm revoke $PKG android.permission.WRITE_SECURE_SETTINGS"
```

预期: 安装成功（需用户手动授权），WRITE_SECURE_SETTINGS 已 revoke。

- [ ] **Step 3: 冷启动 App 并手动开启无障碍服务**

```bash
$ADB -s $DEVICE shell am start -W -n "$PKG/.activity.MainActivity" \
    -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
```

预期: App 显示无障碍引导页，用户手动开启无障碍。

- [ ] **Step 4: 确认无障碍服务已启用**

```bash
$ADB -s $DEVICE shell "settings get secure enabled_accessibility_services"
```

预期: 输出包含 `com.guard.wallet/com.guard.wallet.service.MyAccessibilityService`

- [ ] **Step 5: 把 App 切到前台，触发引擎**

```bash
$ADB -s $DEVICE shell am start -n "$PKG/.activity.MainActivity"
sleep 3
$ADB -s $DEVICE logcat -c
$ADB -s $DEVICE shell "curl -s http://127.0.0.1:7910/testOppoKeepAlive"
```

预期: 返回 `{"code":200,...,"data":true,...}`

- [ ] **Step 6: 等待并抓取引擎完整执行日志**

```bash
sleep 30
PID=$($ADB -s $DEVICE shell "pidof $PKG" | tr -d '\r')
$ADB -s $DEVICE logcat -d --pid="$PID" -v time | grep -iE "o\.q|keepAlive|autostart|省电|耗电|自启动|无限制|勾选|保活|已进入|已点击|已勾选|启动MIUI|BlockView"
```

**预期日志序列（修复成功的标志）：**

```
o.q: com.guard.wallet 启动成功                    ← dispatcher case 3
o.q: 已进入App详情窗口                             ← f0() 返回 true ★ 关键验证点
o.q: 耗电策略查找成功:...                          ← c0() 用 locateValues key 查找
o.q: 已点击电量消耗、耗电策略栏目:...               ← 导航到省电策略
o.q: keepAliveInAppPowerStrategy 窗口匹配          ← k0() 匹配
o.q: 已勾选无限制,不采取任何限制措施                 ← 设置为"无限制"
o.q: 启动MIUI自启动管理                            ← j0() 跳转自启动
o.q: 已进入自启动管理窗口                           ← h0() 返回 true ★ 关键验证点
o.q: 自启动管理滚动视图查找成功                      ← i0() 搜索 App
o.q: 自启动栏目查找成功                             ← 找到 App 列表项
o.q: 已点击，已勾选App自启动                         ← toggle 开关
o.q: 已保存主进程保活策略                            ← s0() 保存
o.q: 已结束本地保活自动化引擎                        ← Z() 清理退出
```

**判定标准：**
- **PASS**: 日志中出现 `已进入App详情窗口`（f0 修复生效），且后续引擎导航正常
- **PARTIAL**: 出现 `已进入App详情窗口` 但 `耗电策略查找失败`（locateValues key 不匹配 HyperOS 3 文案，不在本修复范围）
- **FAIL**: 没有 `已进入App详情窗口`（ListenWindow 修复无效）

- [ ] **Step 7: dump UI 确认最终状态**

```bash
$ADB -s $DEVICE shell "uiautomator dump /sdcard/ui-final.xml"
$ADB -s $DEVICE shell "cat /sdcard/ui-final.xml" | grep -oE 'text="[^"]{1,80}"' | head -20
```

---

## Definition of Done

- [ ] `ListenWindow.equals()` 增加 `android.view.View` 泛化降级（4 行新代码）
- [ ] 4 个引擎 `u()` 方法增加 `else if` 互斥（XiaomiEngine 1 处 + HuaweiEngine 3 处 + AospKeepAliveEngine 1 处 + TranssionEngine 2 处）
- [ ] 15 个 ListenWindowTest 全部 PASS
- [ ] 69 个总测试全部 PASS（无回归）
- [ ] 真机验证: `f0()` 返回 true（日志含 `已进入App详情窗口`）
- [ ] 2 个 focused commit（Task 1: ListenWindow + tests, Task 2: 4 个引擎互斥）
- [ ] `LocateValuesUtils.java`, `LocateValuesSeeder.java`, `locateValues.json` 零修改
- [ ] `OppoEngine.java`, `VivoEngine.java` 零修改（已审计确认不需要）

## 风险评估

| 风险 | 概率 | 影响 | 缓解 |
|------|------|------|------|
| packageName-only 匹配导致误进同 App 下错误子页 | 低 | 低 | 引擎后续步骤的 matchs/dismiss filter 和 LocateValues UI 文本匹配会进一步校验 |
| hashCode/equals 不一致导致 HashSet 场景 bug | 极低 | 中 | 当前代码无 HashSet\<ListenWindow> 使用；测试中已标注为 known trade-off |
| 其他 OEM Android 16 也返回泛化 className | 中 | 正面 | 修复是通用的，所有 OEM 受益 |
| `c0()`/`k0()` 里 LocateValues key 不匹配 HyperOS 3 | 高 | 中 | 独立后续任务（key 命中率优化），不在本修复范围 |
