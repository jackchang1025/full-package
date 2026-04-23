# Setup Phase 3 MIUI Gap Fix Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Fill 5 vendor-parity gaps in the ADB pairing automation — MIUI security center dialog, AdbInputApplyActivity detection, "security setting in progress" loop, Xiaomi SDK>=35 su check, and SDK<=30 checkbox pre-check wiring.

**Architecture:** All changes are additions to existing files. No new files created. Constants go in `SetupConstants.kt`, dialog automation in `DialogHandler.kt`, event routing in `SystemOptimizeManager.kt`, and navigator tweaks in `DevOptionsNavigator.kt`/`PairFlowOrchestrator.kt`.

**Tech Stack:** Kotlin, Android AccessibilityService API, JADX reference `C0360a2.java`

**JADX Reference Docs:**
- `docs/ADB/小米MIUI开发者选项自动化审计.md` — MIUI-specific pairing flow details
- `docs/ADB/厂商自动化ADB配对机制审计.md` — Cross-vendor pairing mechanism audit

---

### Task 1: Add missing MIUI constants to SetupConstants.kt

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt`

**JADX ref:** `dh0.f55807f7` (security setting in progress), `dh0.f55788d8` (next step button), `dh0.f55798e8` (allow dev settings)

- [ ] **Step 1: Add MIUI security constants**

Add these 3 new constant lists at the end of `SetupConstants` object (before the closing `}`), after `PAIR_FAIL_DIALOG_TEXTS`:

```kotlin
    /** MIUI 安全中心包名 — vendor XOR encrypted: KFYcdEAxGScZIi5aBChELBUtUj8/XAM= */
    const val MIUI_SECURITY_CENTER_PKG = "com.miui.securitycenter"

    /** MIUI ADB 输入 Activity — vendor n0 (L6053) */
    const val MIUI_ADB_INPUT_ACTIVITY = "com.miui.permcenter.install.AdbInputApplyActivity"

    /** "安全设置正在打开"文本 — vendor dh0.f55807f7 */
    val SECURITY_SETTING_OPENING_TEXTS: List<String> = listOf(
        "安全设置正在打开", "安全設置正在打開",
        "Security settings opening", "Security settings are opening",
        "セキュリティ設定を開いています", "보안 설정 열는 중",
        "Mở cài đặt bảo mật", "กำลังเปิดการตั้งค่าความปลอดภัย",
        "Membuka pengaturan keamanan", "Открытие настроек безопасности"
    )

    /** "下一步"按钮文本 — vendor dh0.f55788d8 */
    val NEXT_STEP_TEXTS: List<String> = listOf(
        "下一步", "下一個", "繼續",
        "Next", "Next step", "Continue",
        "次へ", "다음", "Tiếp theo", "ถัดไป",
        "Lanjut", "Berikutnya", "Seterusnya", "Susunod",
        "अगला", "পরবর্তী", "اگلا",
        "التالي", "הבא", "بعدی", "İleri", "Sonraki",
        "Suivant", "Siguiente", "Próximo", "Avanti",
        "Weiter", "Volgende", "Nästa", "Neste", "Næste",
        "Seuraava", "Далее", "Далі",
        "Dalej", "Další", "Ďalej",
        "Következő", "Următorul",
        "Επόμενο", "Следващ", "Ifuatayo"
    )

    /** "允许开发设置"弹窗文本 — vendor dh0.f55798e8 */
    val ALLOW_DEV_SETTINGS_TEXTS: List<String> = listOf(
        "允许开发设置", "允許開發設定", "允许开发者选项",
        "Allow development settings", "Allow developer settings",
        "開発設定を許可", "개발 설정 허용",
        "Cho phép cài đặt phát triển", "อนุญาตการตั้งค่านักพัฒนา",
        "Izinkan setelan developer", "Разрешить настройки разработчика"
    )
```

- [ ] **Step 2: Verify compile**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: `BUILD SUCCESSFUL`

---

### Task 2: Implement MIUI security center dialog handling in DialogHandler.kt

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/DialogHandler.kt:307-389`

**JADX ref:** `C0360a2.java B3 (m211994b3, L637-699)` — MIUI security dialog button dispatch

- [ ] **Step 1: Add handleMiuiSecurityDialog method**

Add this method after `handleDisablePermissionMonitor()` (after line 306) and before `findConfirmButtonRecursive()`:

```kotlin
    /**
     * Handle MIUI security center dialog — click "Next"/"Allow" buttons.
     * vendor: B3 (m211994b3, L637-699)
     *
     * MIUI pops security dialogs when enabling USB debugging/wireless debugging.
     * This handler clicks through "Next step" then "Allow" buttons,
     * with a loop detecting "security setting in progress" text (dh0.f55807f7).
     *
     * @return true if a security dialog was detected and handled
     */
    fun handleMiuiSecurityDialog(): Boolean {
        val root = service.rootInActiveWindow ?: return false
        try {
            // Step 1: Try "下一步" (Next) button — vendor L651-660
            val nextBtn = findConfirmButtonRecursive(root, SetupConstants.NEXT_STEP_TEXTS)
            if (nextBtn != null && nextBtn.isClickable) {
                nextBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.i(TAG, "MIUI: 已点击'下一步'按钮")
                SystemClock.sleep(1500L)
                return true
            }

            // Step 2: Try "允许" (Allow) button — vendor L662-698
            val allowBtn = findConfirmButtonRecursive(root, SetupConstants.ALLOW_BUTTON_TEXTS)
            if (allowBtn != null && allowBtn.isClickable) {
                allowBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.i(TAG, "MIUI: 已点击'允许'按钮")

                // Step 3: Loop detecting "安全设置正在打开" — vendor L673-694
                // Max 20 iterations × 1500ms = 30s timeout
                for (i in 0 until 20) {
                    SystemClock.sleep(1500L)
                    val checkRoot = service.rootInActiveWindow ?: break
                    val progressNode = SystemOptimizeManager.findNodeByTexts(
                        checkRoot, SetupConstants.SECURITY_SETTING_OPENING_TEXTS
                    )
                    if (progressNode != null) {
                        Log.d(TAG, "MIUI: 检测到'安全设置正在打开'文本 (iter=$i)")
                        continue
                    }
                    Log.d(TAG, "MIUI: '安全设置正在打开'文本已消失 (iter=$i)")
                    break
                }
                return true
            }

            // Step 3: Try generic dialog accept texts
            val acceptBtn = findConfirmButtonRecursive(root, SetupConstants.DIALOG_ACCEPT_TEXTS)
            if (acceptBtn != null && acceptBtn.isClickable) {
                acceptBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.i(TAG, "MIUI: 已点击确认按钮 (generic)")
                SystemClock.sleep(1000L)
                return true
            }

            return false
        } catch (e: Exception) {
            Log.e(TAG, "handleMiuiSecurityDialog 异常", e)
            return false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    /**
     * Check if current window is MIUI security center.
     * vendor: n0 (L6053) — com.miui.securitycenter package detection
     */
    fun isInMiuiSecurityCenter(packageName: String?): Boolean {
        return packageName == SetupConstants.MIUI_SECURITY_CENTER_PKG
    }

    /**
     * Check if current window is MIUI ADB input activity.
     * vendor: n0 (L6053) — AdbInputApplyActivity detection
     */
    fun isInMiuiAdbInputWindow(packageName: String?, className: String?): Boolean {
        if (packageName != SetupConstants.MIUI_SECURITY_CENTER_PKG) return false
        return className?.contains("AdbInputApplyActivity") == true ||
            className?.contains("permcenter") == true
    }
```

- [ ] **Step 2: Verify compile**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: `BUILD SUCCESSFUL`

---

### Task 3: Wire MIUI security dialog into event routing

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt:400-443`

**JADX ref:** `C0360a2.java i4 (L3800-3850)` — event routing with MIUI securitycenter branch

- [ ] **Step 1: Add MIUI security center routing in mainAccessibilityEventHandler**

In `mainAccessibilityEventHandler()`, add MIUI security center detection **before** the pair fail dialog check (before line 436). Insert after the dev options `return` block:

```kotlin
            // ━━━ MIUI security center dialog (Phase 3) ━━━
            // vendor: i4 L4455-4499 — Xiaomi securitycenter branch
            if (dialogHandler.isInMiuiSecurityCenter(pkg) ||
                dialogHandler.isInMiuiAdbInputWindow(pkg, event.className?.toString())) {
                if (!processedActions.contains("pairInSecurityCenter")) {
                    processedActions.add("pairInSecurityCenter")
                    scheduleTask("B3") {
                        val handled = dialogHandler.handleMiuiSecurityDialog()
                        processedActions.remove("pairInSecurityCenter")
                        if (handled) {
                            Log.d(TAG, "MIUI security dialog handled, continuing pair flow")
                        }
                    }
                }
                return
            }
```

- [ ] **Step 2: Verify compile**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: `BUILD SUCCESSFUL`

---

### Task 4: Wire Xiaomi SDK<=30 pre-check in PairFlowOrchestrator

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/PairFlowOrchestrator.kt`

**JADX ref:** `C0360a2.java P() (L571-582)` — xiaomiNeedsPreCheck logic

- [ ] **Step 1: Read current pairInDevOption to find the Xiaomi pre-check location**

Read `PairFlowOrchestrator.kt` lines 126-240 to find the exact insertion point for the Xiaomi SDK<=30 pre-check. The vendor code does this BEFORE searching for the wireless debug node:

```kotlin
// After handleVivoDevOptionsSwitch and before findWirelessDebugNode
val brand = android.os.Build.BRAND.lowercase(java.util.Locale.ROOT)
val isXiaomi = brand == "xiaomi" || brand == "redmi" || brand == "poco" || brand == "blackshark"
```

Find the `xiaomiNeedsPreCheck` variable (should be around line 219). Verify it calls `handleVivoDevOptionsSwitch` — if it does, it's already wired. If the code only references it in a comment but doesn't actually execute the pre-check when in the "wireless debug node found" path, we need to add the call.

- [ ] **Step 2: Ensure the pre-check is active**

The pre-check should execute `devOptionsNav.handleVivoDevOptionsSwitch(clickableNode)` when `xiaomiNeedsPreCheck` is true. This toggles the wireless debugging checkbox on MIUI 12 (SDK 30). Verify the code path is:

```kotlin
if (xiaomiNeedsPreCheck) {
    Log.d(TAG, "G() 小米 SDK<=30 预勾选无线调试")
    manager.devOptionsNav.handleVivoDevOptionsSwitch(clickableNode)
}
```

If this is already present (the audit showed it at PairFlowOrchestrator:219-223), confirm it's called correctly and not dead code.

- [ ] **Step 3: Verify compile**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: `BUILD SUCCESSFUL`

---

### Task 5: Add Xiaomi SDK>=35 special handling in DevOptionsNavigator

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/DevOptionsNavigator.kt`

**JADX ref:** `C0360a2.java L3958-3961` — Xiaomi SDK>=35 su binary check

- [ ] **Step 1: Add isXiaomiSdk35Plus method**

Add after `clearProcessedDevOpts()`:

```kotlin
    /**
     * Xiaomi SDK>=35 (Android 15+) special handling.
     * vendor: C0360a2.java L3958-3961
     *
     * On HyperOS 2.0+, check if su binary exists.
     * This affects wireless debugging availability detection.
     */
    fun isXiaomiNeedsSpecialHandling(): Boolean {
        val brand = android.os.Build.BRAND.lowercase(java.util.Locale.ROOT)
        val isXiaomi = brand == "xiaomi" || brand == "redmi" || brand == "poco" || brand == "blackshark"
        if (!isXiaomi || android.os.Build.VERSION.SDK_INT < 35) return false

        return try {
            val suPaths = listOf("/system/bin/su", "/system/xbin/su", "/sbin/su")
            val hasSu = suPaths.any { java.io.File(it).exists() }
            Log.d(TAG, "Xiaomi SDK>=35: su check = $hasSu")
            hasSu
        } catch (e: Exception) {
            Log.w(TAG, "Xiaomi SDK>=35: su check failed: ${e.message}")
            false
        }
    }
```

- [ ] **Step 2: Verify compile**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: `BUILD SUCCESSFUL`

---

### Task 6: Final compilation and verification

**Files:**
- All modified files from Tasks 1-5

- [ ] **Step 1: Full compile check**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -10`
Expected: `BUILD SUCCESSFUL` with 0 errors

- [ ] **Step 2: Verify line counts are reasonable**

Run:
```bash
wc -l app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt \
     app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/DialogHandler.kt \
     app/src/main/java/com/storm/safe/rock/service/modules/setup/flow/DevOptionsNavigator.kt \
     app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt
```
Expected:
- SetupConstants: ~730 (was 677 + ~55 new lines)
- DialogHandler: ~460 (was 389 + ~70 new lines)
- DevOptionsNavigator: ~330 (was 309 + ~20 new lines)
- SystemOptimizeManager: ~475 (was 462 + ~12 new lines)

- [ ] **Step 3: Verify MIUI constants are accessible**

Run: `grep -c "MIUI_SECURITY_CENTER_PKG\|SECURITY_SETTING_OPENING\|NEXT_STEP_TEXTS\|MIUI_ADB_INPUT" app/src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt`
Expected: `4`

- [ ] **Step 4: Verify event routing wired**

Run: `grep -c "pairInSecurityCenter\|isInMiuiSecurityCenter\|handleMiuiSecurityDialog" app/src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt`
Expected: `3` (routing code references all three)
