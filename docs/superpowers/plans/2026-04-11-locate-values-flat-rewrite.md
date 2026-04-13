# LocateValues Flat Rewrite + Resource ID Coverage Audit Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace `vendor-replica/app/src/main/assets/locateValues.json` with a flat 82-key Chinese dictionary that exactly matches what vendor's `LocateValuesUtils` (`com.guard.wallet.utils.f.java`) parser expects, fix the wrong `PAIR_SECURITY_OPENING_TEXT` value (currently `"安全设置"`, must be `"正在开启"`), and produce a separate audit document covering whether the existing replica Java sources have all the PIN/Pattern/Window-class hardcoded resource IDs from vendor (without modifying any Java sources or adding resource IDs to locateValues.json).

**Architecture:** Vendor's `LocateValuesUtils.b(key)` parses `externalFilePath/locateValues.json` with the exact Gson signature `new TypeToken<HashMap<String, String>>(){}.getType()` — any nested object value throws `JsonSyntaxException`, the catch block silently swallows it, and `getValue()` returns `""` forever after. Therefore the JSON must be a flat string→string dictionary. The current asset file has a nested `{languages, brands, pinKeyIds, ...}` structure that is incompatible with vendor's parser. We use TDD: a JUnit test that loads the asset file with the same Gson type signature vendor uses, asserts critical key/value pairs (especially the `PAIR_SECURITY_OPENING_TEXT` regression), then asserts presence of all 82 vendor-referenced keys. The test fails RED on the current nested file, then GREEN after the rewrite. Resource IDs (`com.android.systemui:id/...`) and window-class strings stay hardcoded in Java source per vendor design — the audit document only reports presence/gaps, no Java code changes.

**Tech Stack:** Java 17, Gradle 8.5, AGP 8.2.2, JUnit 4.13.2, Gson 2.10.1, Robolectric 4.11.1 (available but not needed). Plan execution lives in WSL Ubuntu 22.04 with `JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64`. Vendor source root: `app/storage/app/apk/apkstub/decompiled_vendor/sources/`. Replica module: `vendor-replica/app/`.

---

## File Structure

**Files this plan touches:**

- **Modify:** `vendor-replica/app/src/main/assets/locateValues.json` — full rewrite from nested 36-language structure to flat 82 zh-CN key→string dictionary.
- **Create:** `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java` — JUnit test that loads the asset file via the same Gson type signature vendor uses, asserts parser compatibility, asserts the `PAIR_SECURITY_OPENING_TEXT` regression, asserts per-category smoke values, and asserts all 82 vendor-referenced keys present + non-empty.
- **Create:** `vendor-replica/docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md` — markdown audit report showing which `com.android.systemui:id/*` literals and window-class strings vendor uses, where each appears in vendor source, where each appears in replica source (or that it's missing), and a single coverage gap (`scrim_behind`) with file recommendation. **Read-only output, does not modify Java code.**
- **Leave alone:** `vendor-replica/app/src/main/assets/locateValues-copy.json` — historical 32-key seed, harmless backup.
- **Leave alone:** all existing replica `.java` files — vendor design hardcodes resource IDs in Java; gaps are reported in the audit doc but not auto-fixed.

**Files NOT in scope (mentioned for clarity, do NOT touch):**
- `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesUtils.java` — already a faithful port of vendor `f.java`, parser is correct, no changes needed
- `app/routes/api.php` and Laravel controllers — Laravel `/api/locateValue/entryAppMap.json` endpoint is not implemented and is **out of scope** for this plan (see "Out of Scope" at end)
- Any helper/Java file containing the `scrim_behind` gap — audit only, no fix

---

## Out of Scope for this Plan

These three problems are real but not solved by this plan. Each needs its own follow-up plan:

1. **Asset → externalFilePath seed copy.** Vendor's `LocateValuesUtils.loadValues()` reads from `externalFilePath/locateValues.json`, NOT from `assets/`. So even after this plan rewrites the asset file correctly, the runtime won't load it. A future plan must add `copyAssetSeedIfMissing("locateValues.json")` somewhere in `MainApplication.onCreate()` or `SystemHelper.i0()` initialization. This plan deliberately stops at "the asset file is now correct"; the seed-copy mechanism is a separate concern.

2. **Laravel `/api/locateValue/entryAppMap.json` endpoint.** Vendor's `AppLocateValuesCallback` POSTs `{deviceId, langCode}` to this URL and writes the response to externalFilePath. The Laravel backend has not implemented this endpoint. Without it, the runtime download path is broken. Out of scope for this plan.

3. **Real-device value verification.** Several values in this plan are inferred from vendor source context with `[strong]` confidence, not verified on a real device. The audit document at the end of this plan flags which keys need real-device cross-checking on OPPO PGFM10 / Xiaomi 13 / Huawei P40 / vivo before production use. Verification is its own follow-up.

This plan delivers **only** "asset JSON parses correctly + matches vendor key set" + "resource ID audit document". Nothing else.

---

## Background Reference (do not change, just understand)

Vendor's parser at `app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/utils/f.java`:

```java
public static void a() {
    ConcurrentHashMap concurrentHashMap = f284a;
    if (concurrentHashMap.keySet().isEmpty()) {
        String i02 = g.i0();
        if (q.B(i02)) return;
        String concat = i02.concat("/").concat("locateValues.json");
        if (q.w(concat)) {
            String K = q.K(concat);
            if (q.B(K)) return;
            HashMap hashMap = (HashMap) new Gson().fromJson(K,
                new TypeToken<HashMap<String, String>>() {}.getType());
            if (hashMap == null || hashMap.keySet().isEmpty()) return;
            concurrentHashMap.putAll(hashMap);
        }
    }
}
```

The hard requirement: **every value must be a JSON string**. Any object/array value causes Gson to throw `JsonSyntaxException: Expected a string but was BEGIN_OBJECT`, the catch eats it, and the map stays empty. That is what is broken in the current asset file.

Vendor's wrapper `o/b.java:152`:

```java
public static void v(String str, StringCondition stringCondition,
                    CombineFilter combineFilter, StringCondition stringCondition2) {
    stringCondition.setEquals(com.guard.wallet.utils.f.b(str));
    ...
}
```

This wrapper is the "indirect" path some vendor engines use (`o/q.java`, `o/i0.java`). It still calls `f.b(key)` underneath, so cross-reference grep must include both `f.b("` and `b.v("` patterns to enumerate all vendor key references.

---

## Task 1: Pre-flight verification

**Files:**
- Read: `vendor-replica/app/src/main/assets/locateValues.json` (verify it is currently nested/broken)
- Read: `vendor-replica/app/src/main/assets/locateValues-copy.json` (verify backup exists)
- Read: `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesUtils.java` (verify replica parser already matches vendor signature)

- [ ] **Step 1: Confirm git status is clean for the files this plan touches**

Run:
```bash
cd /home/code/php/project/full-package
git status -- vendor-replica/app/src/main/assets/locateValues.json \
                vendor-replica/app/src/test/java/com/guard/wallet/utils/ \
                vendor-replica/docs/audits/
```

Expected: `locateValues.json` may be modified (it is the broken nested file we will rewrite). The other paths should be untracked or absent. If anything else is dirty, ask the user before proceeding.

- [ ] **Step 2: Verify the current `locateValues.json` actually contains the broken nested structure**

Run:
```bash
head -20 /home/code/php/project/full-package/vendor-replica/app/src/main/assets/locateValues.json
```

Expected: lines containing `"version": "2.0.0"`, `"languages": {`, `"zh-CN": {` — confirming the nested structure that this plan will replace. If you see a flat `"PAIR_WIFI_DEBUG_TEXT": "无线调试"` at the top level, the file is already flat and most of this plan is unnecessary — STOP and report to user.

- [ ] **Step 3: Verify the historical seed backup file is intact**

Run:
```bash
wc -l /home/code/php/project/full-package/vendor-replica/app/src/main/assets/locateValues-copy.json
head -5 /home/code/php/project/full-package/vendor-replica/app/src/main/assets/locateValues-copy.json
```

Expected: ~41 lines, top entries include `"COLORS_SETTINGS_ALLOW_BUTTON_TEXT": "允许"`. This file is the historical 32-key seed and is an authoritative source for many of the values used in Task 3. Do NOT modify it.

- [ ] **Step 4: Confirm replica parser already matches vendor's exact Gson signature**

Run:
```bash
grep -n 'TypeToken<HashMap<String, String>>' \
  /home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesUtils.java
```

Expected: at least one line containing `TypeToken<HashMap<String, String>>` (the Gson reflection type). If not, `LocateValuesUtils.java` does not match vendor and this plan's TDD assumption is wrong — STOP and report.

- [ ] **Step 5: Verify Gradle test wiring works on this module before adding tests**

Run:
```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:test --tests "com.guard.wallet.gkd.GkdNodeFinderTest" --console=plain
```

Expected: test executes and passes (the existing `GkdNodeFinderTest` is unrelated but proves the Gradle test pipeline works). If it fails for environment reasons (`JAVA_HOME`, missing SDK, etc.), fix the environment first — do not proceed.

- [ ] **No commit for Task 1.** This is verification only.

---

## Task 2: Write the failing LocateValuesAssetTest

**Files:**
- Create: `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java`

This test loads the asset file via the same Gson type signature vendor's parser uses, then asserts (a) parses to a non-empty `HashMap<String, String>`, (b) `PAIR_SECURITY_OPENING_TEXT` is `"正在开启"` and not `"安全设置"`, and (c) one canary key per category resolves correctly. Write only this much in this task — the comprehensive 82-key presence test comes in Task 4.

- [ ] **Step 1: Create the test file**

Create `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java` with this exact content:

```java
package com.guard.wallet.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * 验证 vendor-replica/app/src/main/assets/locateValues.json 与 vendor 解析器
 * (com.guard.wallet.utils.f.java) 完全兼容：
 *
 * 1. 顶层必须是扁平 HashMap<String, String>，否则 vendor 的 Gson TypeToken
 *    会抛 JsonSyntaxException 并被外层 catch 吞掉，导致运行时 map 永远空。
 * 2. PAIR_SECURITY_OPENING_TEXT 必须是对话框状态文本 "正在开启"，
 *    而不是屏幕标题 "安全设置"。这是 vendor 在 o/a0.java:441 中
 *    针对小米安全中心 AdbInputApplyActivity "正在开启" 弹窗的匹配条件。
 * 3. 每个厂商类别至少有一个 canary key 解析为预期值。
 *
 * 详细的 82 key 全量校验在 LocateValuesAssetTest#allRequiredKeysPresent (Task 4)。
 */
public class LocateValuesAssetTest {

    /** Gradle JVM 测试的工作目录是 vendor-replica/app/，因此用相对路径读取 assets。 */
    private static final File ASSET_FILE = new File("src/main/assets/locateValues.json");

    /**
     * 用 vendor f.java:31 的同款 Gson TypeToken 加载 asset 文件。
     * 任何嵌套对象 value 都会在这里抛 JsonSyntaxException。
     */
    private HashMap<String, String> loadFlatMap() throws Exception {
        assertTrue(
            "asset file missing: " + ASSET_FILE.getAbsolutePath(),
            ASSET_FILE.exists()
        );
        String content = new String(
            Files.readAllBytes(ASSET_FILE.toPath()),
            StandardCharsets.UTF_8
        );
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        return new Gson().fromJson(content, type);
    }

    @Test
    public void parsesAsFlatStringMap() throws Exception {
        HashMap<String, String> map;
        try {
            map = loadFlatMap();
        } catch (JsonSyntaxException e) {
            fail(
                "locateValues.json must be a flat HashMap<String,String> "
                + "(vendor f.java line 31 enforces this). "
                + "Nested object values cause Gson to throw: " + e.getMessage()
            );
            return;
        }
        assertNotNull("Gson returned null — JSON is malformed or empty", map);
        assertFalse("Parsed map must not be empty", map.isEmpty());
    }

    @Test
    public void pairSecurityOpeningTextIsDialogStateNotScreenName() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals(
            "PAIR_SECURITY_OPENING_TEXT must be the dialog state '正在开启' "
            + "(o/a0.java:441 — Xiaomi security center AdbInputApplyActivity opening dialog), "
            + "NOT the screen title '安全设置'",
            "正在开启",
            map.get("PAIR_SECURITY_OPENING_TEXT")
        );
    }

    @Test
    public void canaryPairKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("无线调试", map.get("PAIR_WIFI_DEBUG_TEXT"));
    }

    @Test
    public void canaryColorsKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("耗电管理", map.get("COLORS_SETTINGS_POWER_MANAGE_TEXT"));
    }

    @Test
    public void canaryHuaweiKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("应用与通知", map.get("HUA_WEI_APP_AND_NOTIFICATION_TEXT"));
    }

    @Test
    public void canaryMiuiKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("MIUI版本", map.get("MIUI_VERSION_TEXT"));
    }

    @Test
    public void canaryVivoKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("允许自启动", map.get("VIVO_AUTO_START_TEXT"));
    }

    @Test
    public void canaryCommonKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("电池", map.get("COMMON_SETTINGS_BATTERY_TEXT"));
    }

    @Test
    public void canaryOppoInstallKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("授权安装", map.get("OPPO_AUTHORIZE_INSTALL_BTN_TEXT"));
    }

    @Test
    public void canaryVersionKey() throws Exception {
        HashMap<String, String> map = loadFlatMap();
        assertEquals("HarmonyOS版本", map.get("HARMONY_OS_VERSION_TEXT"));
    }
}
```

- [ ] **Step 2: Run the new test and confirm it FAILS RED**

Run:
```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:test --tests "com.guard.wallet.utils.LocateValuesAssetTest" --console=plain
```

Expected: `parsesAsFlatStringMap` fails because the current asset file has nested objects. The test message should mention either `Expected a string but was BEGIN_OBJECT` or `Parsed map must not be empty`. The other tests will also fail because their canary keys do not exist in the broken nested file.

- [ ] **Step 3: Capture the failure message into the task notes**

Read the Gradle test report and confirm:
- File: `vendor-replica/app/build/reports/tests/testDebugUnitTest/index.html`
- Or just look at the console output: at least 9 failures expected, all in `LocateValuesAssetTest`.

If `parsesAsFlatStringMap` fails with a NullPointerException or `assertion failed: asset file missing` instead of a JsonSyntaxException, then the working directory is wrong — Gradle should run with `cwd = vendor-replica/app/`. Re-run with `--info` or fix `ASSET_FILE` path before continuing. This is the only path issue I want to be sure of before Task 3.

- [ ] **Step 4: NO commit yet — the test is intentionally RED.**

We do not commit failing tests. Task 3 will produce the corresponding GREEN state and Task 3 commits both files together.

---

## Task 3: Rewrite locateValues.json as flat 82-key dictionary

**Files:**
- Modify: `vendor-replica/app/src/main/assets/locateValues.json` — full rewrite

This is the biggest single edit in the plan. The replacement content is provided in full below — copy it exactly. Values are sourced from (in priority): the existing `locateValues-copy.json` 32-key seed, vendor source-context inference (PAIR_SECURITY_OPENING_TEXT verified against `o/a0.java:441`), and Chinese Android UI knowledge for brand-specific labels. Some values are flagged with `[needs-real-device-verification]` in the Task 7 audit doc but are intentionally still committed here as best-effort starting values.

- [ ] **Step 1: Replace the asset file with the flat 82-key version**

Overwrite `vendor-replica/app/src/main/assets/locateValues.json` with this exact content:

```json
{
  "PAIR_WIFI_DEBUG_TEXT": "无线调试",
  "PAIR_WIFI_DEBUG_2_TEXT": "无线调试",
  "PAIR_WIFI_DEBUG_CONTAINS_TEXT": "无线调试",
  "PAIR_WIFI_DEBUG_CONTAINS_2_TEXT": "连接到 WLAN 后启用调试模式",
  "PAIR_DEVELOPER_OPTION_TEXT": "开发者选项",
  "PAIR_DEVELOPERS_OPTION_TEXT": "开发者选项",
  "PAIR_DEVELOPER_OPTION_2_TEXT": "开发者选项",
  "PAIR_DEVELOPER_OPTION_3_TEXT": "开发者选项",
  "PAIR_DEVELOPER_OPTION_4_TEXT": "开发者选项",
  "PAIR_DEVELOPER_OPTION_5_TEXT": "开发者选项",
  "PAIR_DEVICE_USE_PAIR_CODE_TEXT": "使用配对码配对设备",
  "PAIR_DEVICE_USE_PAIR_CODE_2_TEXT": "配对码",
  "PAIR_DEVICE_BY_CODE_TEXT": "使用配对码配对设备",
  "PAIR_DEVICE_BY_CODE_2_TEXT": "配对码",
  "PAIR_DEVICE_BY_CODE_3_TEXT": "配对",
  "PAIR_FAILED_TEXT": "无法与设备配对",
  "PAIR_FAILED_2_TEXT": "配对失败",
  "PAIR_FAILED_3_TEXT": "无法配对",
  "PAIR_FAILED_4_TEXT": "配对超时",
  "PAIR_DISABLE_PERMISSION_MONITOR_TEXT": "禁用权限监控",
  "PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT": "停用权限监控",
  "PAIR_ACCEPT_TEXT": "允许",
  "PAIR_NEXT_TEXT": "下一步",
  "PAIR_CONFIRM_TEXT": "确定",
  "PAIR_CANCEL_TEXT": "取消",
  "PAIR_ALLOW_DEVELOPER_SETTING_TEXT": "允许开发者设置",
  "PAIR_SECURITY_OPENING_TEXT": "正在开启",
  "PAIR_ALLOW_USB_INSTALL_TEXT": "USB安装",
  "PAIR_USB_SECURITY_TEXT": "USB安全设置",
  "PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT": "撤销USB调试授权",
  "PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT": "连接到 WLAN 后启用调试模式",

  "COLORS_SETTINGS_ALLOW_BUTTON_TEXT": "允许",
  "COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT": "允许后台运行",
  "COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT": "允许自启动",
  "COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT": "完全允许后台行为",
  "COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT": "关联启动",
  "COLORS_APP_IN_BACKGROUND_TEXT": "后台",
  "COLORS_SETTINGS_POWER_MANAGE_TEXT": "耗电管理",
  "COLORS_SETTINGS_POWER_MANAGE_2_TEXT": "电池管理",
  "COLORS_BUILD_NUMBER_TEXT": "版本号",

  "HUA_WEI_ALLOW_AUTO_STARTUP_TEXT": "允许自启动",
  "HUA_WEI_ALLOW_IN_BACKGROUND_TEXT": "允许后台运行",
  "HUA_WEI_ALLOW_RELATE_STARTUP_TEXT": "允许关联启动",
  "HUA_WEI_APP_AND_NOTIFICATION_TEXT": "应用与通知",
  "HUA_WEI_APP_STARTUP_MANAGE_TEXT": "启动管理",
  "HUA_WEI_CONFIRM_TEXT": "确定",
  "HUA_WEI_VERSION_TEXT": "HarmonyOS版本",

  "MIUI_APP_POWER_CONSUME_TEXT": "应用耗电",
  "MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT": "省电策略",
  "MIUI_VERSION_TEXT": "MIUI版本",
  "MIUI_CONTINUE_INSTALL_BTN_TEXT": "继续安装",

  "VIVO_APP_ALL_PERMISSION_TEXT": "所有权限",
  "VIVO_BACKGROUND_POWER_MANAGER_TEXT": "后台耗电管理",
  "VIVO_APP_PERMISSION_TEXT": "应用权限",
  "VIVO_ALLOW_TEXT": "允许",
  "VIVO_AUTO_START_TEXT": "允许自启动",
  "VIVO_POPUP_IN_BACKGROUND_TEXT": "允许后台弹窗",
  "VIVO_OS_SOFTWARE_VERSION_TEXT": "软件版本号",
  "VIVO_OS_VERSION_INFO_TEXT": "更多信息",
  "VIVO_CONTINUE_INSTALL_BTN_TEXT": "继续安装",

  "COMMON_ALLOW_BACKGROUND_USAGE_TEXT": "允许后台使用",
  "COMMON_SETTINGS_BATTERY_TEXT": "电池",
  "COMMON_SETTINGS_POWER_TEXT": "电源",
  "COMMON_SETTINGS_USE_POWER_TEXT": "电源使用情况",
  "COMMON_SETTINGS_UNRESTRICTED_TEXT": "无限制",
  "COMMON_SETTINGS_NO_RESTRICTED_TEXT": "不受限制",
  "COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT": "移除限制",

  "OPPO_CONTINUE_INSTALL_BTN_TEXT": "继续安装",
  "OPPO_AUTHORIZE_INSTALL_BTN_TEXT": "授权安装",
  "OPPO_INSTALLING_TEXT": "正在安装",
  "OPPO_INSTALL_DONE_TEXT": "安装完成",
  "OPPO_INSTALL_FINISH_TEXT": "安装完成",

  "BUILD_VERSION_TEXT": "版本号",
  "BUILD_NUMBER_TEXT": "编译号",
  "OS_VERSION_TEXT": "系统版本",
  "OS_SOFTWARE_VERSION_TEXT": "软件版本",
  "OS_VERSION_INFO_TEXT": "版本信息",
  "COMPILE_NUMBER_TEXT": "编译号",
  "MOTO_OS_VERSION_INFO_TEXT": "Android版本",
  "HYPER_OS_VERSION_TEXT": "HyperOS版本",
  "HARMONY_OS_VERSION_TEXT": "HarmonyOS版本",
  "SOFTWARE_INFO_TEXT": "软件信息"
}
```

Use the Write tool to overwrite the existing nested file. The new file should be exactly 82 keys (count: 31 PAIR_ + 9 COLORS_ + 7 HUA_WEI_ + 4 MIUI_ + 9 VIVO_ + 7 COMMON_ + 5 OPPO_ + 10 generic version/about = 82). Blank lines between sections are stripped by JSON; they are present above only for human readability — feel free to keep them since JSON tolerates whitespace, or strip them if you prefer.

- [ ] **Step 2: Validate the file is valid JSON**

Run:
```bash
python3 -c "import json; m = json.load(open('/home/code/php/project/full-package/vendor-replica/app/src/main/assets/locateValues.json')); print(f'keys={len(m)}'); assert all(isinstance(v, str) for v in m.values()), 'non-string value detected'; print('all-string-values=OK')"
```

Expected output:
```
keys=82
all-string-values=OK
```

If the count is not 82 or any value is not a string, the file is wrong — fix and re-run before proceeding.

- [ ] **Step 3: Re-run the failing tests from Task 2 — they should now PASS GREEN**

Run:
```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:test --tests "com.guard.wallet.utils.LocateValuesAssetTest" --console=plain
```

Expected: all 10 tests in `LocateValuesAssetTest` pass. If `parsesAsFlatStringMap` still fails with `JsonSyntaxException`, the JSON has a non-string value somewhere — re-check Step 2 carefully. If `pairSecurityOpeningTextIsDialogStateNotScreenName` fails with "expected `正在开启` but got `安全设置`", you accidentally left the old nested structure or copy-pasted the wrong value — re-check Step 1.

- [ ] **Step 4: Commit the rewrite + the test together**

```bash
cd /home/code/php/project/full-package
git add vendor-replica/app/src/main/assets/locateValues.json \
        vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java
git commit -m "$(cat <<'EOF'
fix(vendor-replica): rewrite locateValues.json to flat 82-key dict matching vendor parser

Vendor's LocateValuesUtils (com.guard.wallet.utils.f.java:31) requires
HashMap<String,String> via Gson TypeToken — any nested object value causes
JsonSyntaxException to be silently swallowed and the runtime map stays empty,
breaking all ADB pair / keep-alive / install authorization automation.

The previous nested {languages, brands, pinKeyIds, ...} structure was
incompatible with the parser. This commit replaces it with the canonical
flat 82-key zh-CN dictionary covering:

- 31 PAIR_*  ADB wireless pair (matches o/a0.java f.b() callsites)
- 9  COLORS_*  OPPO ColorOS keep-alive (matches o/v.java)
- 7  HUA_WEI_*  Huawei AppStartupManagement (matches o/n.java)
- 4  MIUI_*  Xiaomi MIUI keep-alive (matches o/q.java b.v() wrappers)
- 9  VIVO_*  vivo permission/keep-alive (matches o/i0.java b.v() wrappers)
- 7  COMMON_*  AOSP/Transsion generic battery (matches o/g.java, o/e0.java)
- 5  OPPO_*  OPPO PackageInstaller authorization
- 10 BUILD_/OS_/MIUI_VERSION_/HARMONY_/etc.  About-phone 7-tap version labels

Critical regression fix: PAIR_SECURITY_OPENING_TEXT was '安全设置' (screen name);
correct value is '正在开启' (dialog-state text matched at o/a0.java:441 in the
Xiaomi com.miui.securitycenter AdbInputApplyActivity flow).

Note: this commit fixes ONLY the asset file. Vendor LocateValuesUtils.loadValues()
reads from externalFilePath, not assets, so this alone does not make runtime
ADB pair work. Asset->externalFilePath seed copy and Laravel /api/locateValue
endpoint are separate follow-up plans.

Adds JUnit test LocateValuesAssetTest with parser smoke + per-category canaries
+ PAIR_SECURITY_OPENING_TEXT regression test to prevent re-introduction.
EOF
)"
```

Verify with `git status` — both files should now be committed and the working tree clean for these paths.

---

## Task 4: Comprehensive 82-key presence test

**Files:**
- Modify: `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java` — add one new test method

The Task 2 test only has canary checks per category. This task adds an exhaustive test that asserts every one of the 82 keys is present and has a non-empty value. This protects against accidental key drops in future edits.

- [ ] **Step 1: Append the comprehensive test method**

Edit `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java` and insert this method just before the closing `}` of the class:

```java
    /**
     * Exhaustive presence check for the canonical 82-key vendor reference set.
     * If you add a key to locateValues.json, add it here too.
     * If you remove a key from locateValues.json without removing it here,
     * this test fails and tells you exactly which key is missing.
     */
    @Test
    public void allRequiredKeysPresent() throws Exception {
        HashMap<String, String> map = loadFlatMap();

        String[] required = {
            // PAIR_* (31) — ADB wireless pairing — vendor o/a0.java
            "PAIR_WIFI_DEBUG_TEXT",
            "PAIR_WIFI_DEBUG_2_TEXT",
            "PAIR_WIFI_DEBUG_CONTAINS_TEXT",
            "PAIR_WIFI_DEBUG_CONTAINS_2_TEXT",
            "PAIR_DEVELOPER_OPTION_TEXT",
            "PAIR_DEVELOPERS_OPTION_TEXT",
            "PAIR_DEVELOPER_OPTION_2_TEXT",
            "PAIR_DEVELOPER_OPTION_3_TEXT",
            "PAIR_DEVELOPER_OPTION_4_TEXT",
            "PAIR_DEVELOPER_OPTION_5_TEXT",
            "PAIR_DEVICE_USE_PAIR_CODE_TEXT",
            "PAIR_DEVICE_USE_PAIR_CODE_2_TEXT",
            "PAIR_DEVICE_BY_CODE_TEXT",
            "PAIR_DEVICE_BY_CODE_2_TEXT",
            "PAIR_DEVICE_BY_CODE_3_TEXT",
            "PAIR_FAILED_TEXT",
            "PAIR_FAILED_2_TEXT",
            "PAIR_FAILED_3_TEXT",
            "PAIR_FAILED_4_TEXT",
            "PAIR_DISABLE_PERMISSION_MONITOR_TEXT",
            "PAIR_DISABLE_PERMISSION_MONITOR_2_TEXT",
            "PAIR_ACCEPT_TEXT",
            "PAIR_NEXT_TEXT",
            "PAIR_CONFIRM_TEXT",
            "PAIR_CANCEL_TEXT",
            "PAIR_ALLOW_DEVELOPER_SETTING_TEXT",
            "PAIR_SECURITY_OPENING_TEXT",
            "PAIR_ALLOW_USB_INSTALL_TEXT",
            "PAIR_USB_SECURITY_TEXT",
            "PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT",
            "PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT",

            // COLORS_* (9) — OPPO ColorOS keep-alive — vendor o/v.java
            "COLORS_SETTINGS_ALLOW_BUTTON_TEXT",
            "COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT",
            "COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT",
            "COLORS_SETTINGS_ALLOW_FULL_IN_BACKGROUND_TEXT",
            "COLORS_SETTINGS_ALLOW_APP_RELATE_START_TEXT",
            "COLORS_APP_IN_BACKGROUND_TEXT",
            "COLORS_SETTINGS_POWER_MANAGE_TEXT",
            "COLORS_SETTINGS_POWER_MANAGE_2_TEXT",
            "COLORS_BUILD_NUMBER_TEXT",

            // HUA_WEI_* (7) — Huawei AppStartupManagement — vendor o/n.java
            "HUA_WEI_ALLOW_AUTO_STARTUP_TEXT",
            "HUA_WEI_ALLOW_IN_BACKGROUND_TEXT",
            "HUA_WEI_ALLOW_RELATE_STARTUP_TEXT",
            "HUA_WEI_APP_AND_NOTIFICATION_TEXT",
            "HUA_WEI_APP_STARTUP_MANAGE_TEXT",
            "HUA_WEI_CONFIRM_TEXT",
            "HUA_WEI_VERSION_TEXT",

            // MIUI_* (4) — Xiaomi MIUI keep-alive + install — vendor o/q.java
            "MIUI_APP_POWER_CONSUME_TEXT",
            "MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT",
            "MIUI_VERSION_TEXT",
            "MIUI_CONTINUE_INSTALL_BTN_TEXT",

            // VIVO_* (9) — vivo permission/keep-alive + install — vendor o/i0.java
            "VIVO_APP_ALL_PERMISSION_TEXT",
            "VIVO_BACKGROUND_POWER_MANAGER_TEXT",
            "VIVO_APP_PERMISSION_TEXT",
            "VIVO_ALLOW_TEXT",
            "VIVO_AUTO_START_TEXT",
            "VIVO_POPUP_IN_BACKGROUND_TEXT",
            "VIVO_OS_SOFTWARE_VERSION_TEXT",
            "VIVO_OS_VERSION_INFO_TEXT",
            "VIVO_CONTINUE_INSTALL_BTN_TEXT",

            // COMMON_* (7) — AOSP/Transsion generic — vendor o/g.java, o/e0.java
            "COMMON_ALLOW_BACKGROUND_USAGE_TEXT",
            "COMMON_SETTINGS_BATTERY_TEXT",
            "COMMON_SETTINGS_POWER_TEXT",
            "COMMON_SETTINGS_USE_POWER_TEXT",
            "COMMON_SETTINGS_UNRESTRICTED_TEXT",
            "COMMON_SETTINGS_NO_RESTRICTED_TEXT",
            "COMMON_SETTINGS_HAS_CANCEL_RESTRICTED_TEXT",

            // OPPO_* (5) — OPPO PackageInstaller authorization — vendor o/x.java
            "OPPO_CONTINUE_INSTALL_BTN_TEXT",
            "OPPO_AUTHORIZE_INSTALL_BTN_TEXT",
            "OPPO_INSTALLING_TEXT",
            "OPPO_INSTALL_DONE_TEXT",
            "OPPO_INSTALL_FINISH_TEXT",

            // Generic version / about-phone (10) — vendor o/t.java
            "BUILD_VERSION_TEXT",
            "BUILD_NUMBER_TEXT",
            "OS_VERSION_TEXT",
            "OS_SOFTWARE_VERSION_TEXT",
            "OS_VERSION_INFO_TEXT",
            "COMPILE_NUMBER_TEXT",
            "MOTO_OS_VERSION_INFO_TEXT",
            "HYPER_OS_VERSION_TEXT",
            "HARMONY_OS_VERSION_TEXT",
            "SOFTWARE_INFO_TEXT"
        };

        assertEquals(
            "Required-key list size drift — update the array above when you add or remove keys",
            82,
            required.length
        );

        java.util.List<String> missing = new java.util.ArrayList<>();
        java.util.List<String> empty = new java.util.ArrayList<>();
        for (String key : required) {
            String value = map.get(key);
            if (value == null) {
                missing.add(key);
            } else if (value.isEmpty()) {
                empty.add(key);
            }
        }

        if (!missing.isEmpty() || !empty.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            if (!missing.isEmpty()) {
                msg.append("Missing keys (")
                   .append(missing.size())
                   .append("): ")
                   .append(missing)
                   .append("\n");
            }
            if (!empty.isEmpty()) {
                msg.append("Empty values (")
                   .append(empty.size())
                   .append("): ")
                   .append(empty)
                   .append("\n");
            }
            fail(msg.toString());
        }

        assertEquals(
            "JSON contains unexpected extra keys — either add them to the required[] list "
            + "or remove them from locateValues.json. Extra keys: " + extras(map.keySet(), required),
            required.length,
            map.size()
        );
    }

    private static java.util.Set<String> extras(
            java.util.Set<String> actual, String[] required) {
        java.util.Set<String> req = new java.util.HashSet<>(java.util.Arrays.asList(required));
        java.util.Set<String> extras = new java.util.HashSet<>(actual);
        extras.removeAll(req);
        return extras;
    }
```

- [ ] **Step 2: Run the new test and verify it PASSES GREEN immediately**

Run:
```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:test --tests "com.guard.wallet.utils.LocateValuesAssetTest" --console=plain
```

Expected: all 11 tests in `LocateValuesAssetTest` pass (10 from Task 2 + 1 new). If `allRequiredKeysPresent` fails with "Missing keys: [...]", you have a typo or missing entry in `locateValues.json` — diff against the Task 3 Step 1 content. If it fails with "JSON contains unexpected extra keys", you have a stray key in `locateValues.json` that is not in the `required[]` array — either add it to the array or remove it from JSON.

- [ ] **Step 3: Commit the comprehensive test alone**

```bash
cd /home/code/php/project/full-package
git add vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java
git commit -m "test(vendor-replica): exhaustive 82-key presence assertion for locateValues.json

LocateValuesAssetTest#allRequiredKeysPresent enumerates all 82 vendor-referenced
keys and asserts each is present and non-empty in the asset file. Catches
accidental key drops during future edits.

Also asserts map.size() == 82 to catch accidental extra keys (which would
indicate a typo or out-of-spec addition that vendor's f.b() never reads)."
```

---

## Task 5: Manual vendor cross-reference verification

**Files:**
- Read-only: `app/storage/app/apk/apkstub/decompiled_vendor/sources/o/*.java`
- Read-only: `app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/**/*.java`

This is a sanity check, not an automated test. We grep vendor source for every `f.b("KEY")` and `b.v("KEY"` callsite, sort the unique keys, and diff against the 82-key list in `locateValues.json`. If anything is missing from our list, we add it (with a `[needs-real-device-verification]` value) and update the test. If anything is in our list but not in vendor source, we remove it.

- [ ] **Step 1: Extract every vendor key reference from `f.b()` and `b.v()` callsites**

Run this exact pipeline:

```bash
cd /home/code/php/project/full-package
{
  grep -rohE 'f\.b\("[A-Z_0-9]+"' \
    app/storage/app/apk/apkstub/decompiled_vendor/sources/o \
    app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet \
    2>/dev/null \
    | sed -E 's/.*f\.b\("([^"]+)".*/\1/'
  grep -rohE 'b\.v\("[A-Z_0-9]+"' \
    app/storage/app/apk/apkstub/decompiled_vendor/sources/o \
    app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet \
    2>/dev/null \
    | sed -E 's/.*b\.v\("([^"]+)".*/\1/'
} | sort -u > /tmp/vendor-locate-keys.txt
wc -l /tmp/vendor-locate-keys.txt
head -30 /tmp/vendor-locate-keys.txt
```

Expected: roughly 75–90 unique keys. The exact count may differ from the plan's 82 because (a) some keys are referenced via multi-level wrapper helpers we did not chase, and (b) vendor obfuscation may rename some helpers across versions. The list is a reference, not gospel.

- [ ] **Step 2: Diff the extracted vendor key list against the 82-key required[] array**

Run:
```bash
cd /home/code/php/project/full-package
python3 -c "
import json, sys
with open('vendor-replica/app/src/main/assets/locateValues.json') as f:
    json_keys = set(json.load(f).keys())
with open('/tmp/vendor-locate-keys.txt') as f:
    vendor_keys = set(line.strip() for line in f if line.strip())

only_in_vendor = sorted(vendor_keys - json_keys)
only_in_json   = sorted(json_keys - vendor_keys)

print(f'JSON keys:    {len(json_keys)}')
print(f'Vendor keys:  {len(vendor_keys)}')
print(f'Intersection: {len(json_keys & vendor_keys)}')
print()
print('In vendor but missing from JSON (need to add):')
for k in only_in_vendor: print(f'  + {k}')
print()
print('In JSON but missing from vendor (dead key, can remove):')
for k in only_in_json: print(f'  - {k}')
"
```

Expected output ideally:
```
JSON keys:    82
Vendor keys:  ~80
Intersection: ~78–82
In vendor but missing from JSON: (empty or 1–4 keys)
In JSON but missing from vendor: (empty or 1–4 keys)
```

A small mismatch is expected and OK as long as you understand each delta:

- **`+` (in vendor, not in JSON)**: a key we missed during research. Add it to BOTH `locateValues.json` and the `required[]` array in `LocateValuesAssetTest`. Use the best Chinese value you can infer from the vendor source context (read `app/storage/app/apk/apkstub/decompiled_vendor/sources/o/*.java` around the callsite — look for surrounding `Log.d` strings, the activity package being navigated, etc.). Mark the new key in the Task 7 audit doc as `[needs-real-device-verification]`.
- **`-` (in JSON, not in vendor)**: a dead key from the plan that vendor's `f.b()` never reads. Remove from BOTH `locateValues.json` AND `required[]` in the test. The map size assertion will catch this for you on next test run.

If the diff is non-empty, perform the fixes, then re-run the test from Task 4 Step 2 to confirm everything still passes.

- [ ] **Step 3: Commit reconciliation if any keys changed**

If Step 2 produced any changes, commit them:

```bash
cd /home/code/php/project/full-package
git add vendor-replica/app/src/main/assets/locateValues.json \
        vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java
git commit -m "fix(vendor-replica): reconcile locateValues.json with vendor f.b()/b.v() callsites

Cross-reference grep against vendor source identified the following deltas:
- ADD: <list of newly-added keys>
- REMOVE: <list of removed dead keys>

Each new key marked [needs-real-device-verification] in
docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md (added in next commit)."
```

If Step 2 produced no changes (perfect alignment), skip the commit. Just note in the task log "vendor cross-reference: no deltas".

---

## Task 6: Full Gradle test suite regression check

**Files:** none modified

Run the full test suite to confirm no other test broke as a side effect of editing `locateValues.json` or adding the new test class.

- [ ] **Step 1: Run full test suite**

```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:test --console=plain 2>&1 | tail -50
```

Expected: BUILD SUCCESSFUL with all tests passing including the existing `GkdNodeFinderTest` and `CombineFilterConverterTest`. If any unrelated test fails, investigate before continuing — it may be a flaky test or it may indicate that some other code path is reading `locateValues.json` in a way the new flat structure broke. If it is genuinely flaky and unrelated, document it in the commit message and continue.

- [ ] **Step 2: Confirm only the expected files changed**

```bash
cd /home/code/php/project/full-package
git status
git log --oneline -5
```

Expected: working tree clean for the files this plan touches. Recent commits should be exactly the 2–3 from Tasks 3, 4, and (optionally) 5.

---

## Task 7: Resource ID coverage audit document

**Files:**
- Create: `vendor-replica/docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md`

This is the deliverable for the second half of the user request: verify which vendor `com.android.systemui:id/*` literals and window-class strings have corresponding constants in vendor-replica Java sources. The audit reports findings only — no Java code changes, no additions to `locateValues.json` (resource IDs do not belong there per vendor design). Coverage is 13/14 with one gap: `com.android.systemui:id/scrim_behind` from vendor `helper/q.java:65`.

- [ ] **Step 1: Create the audits directory**

```bash
mkdir -p /home/code/php/project/full-package/vendor-replica/docs/audits
ls /home/code/php/project/full-package/vendor-replica/docs/audits
```

Expected: directory exists, currently empty.

- [ ] **Step 2: Write the audit document**

Create `vendor-replica/docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md` with this exact content:

````markdown
# Resource ID Coverage Audit — vendor → vendor-replica

**Date:** 2026-04-11
**Scope:** PIN keypad / pattern lock / window class / brand package literals
**Verdict:** **13 / 14 hardcoded literals present in replica (92.9% coverage)**. One gap: `com.android.systemui:id/scrim_behind`.

This audit documents which Android resource-ID strings and window-class strings vendor's source hardcodes as Java string literals, and whether each is also present in the vendor-replica Java source tree. **These constants stay in Java source per vendor design — they are NOT and should NOT be in `locateValues.json`** (vendor's `LocateValuesUtils` is for UI text strings only; resource IDs and class names go through different code paths and never reach the parser).

## Why this audit exists

The previous (broken) `vendor-replica/app/src/main/assets/locateValues.json` had a mixed-purpose nested structure that included a `pinKeyIds` / `patternViewIds` / `windowClasses` / `brands` top-level section alongside UI text. That mixing was incorrect — vendor never stores resource IDs in the LocateValues file; they live as Java string literals next to the code that uses them. After flattening the JSON to be parser-compatible (see commit `fix(vendor-replica): rewrite locateValues.json to flat 82-key dict matching vendor parser`), this audit confirms the resource IDs that the nested JSON used to list are all already present elsewhere in the replica Java source — so dropping them from JSON did not lose anything except one literal.

## Methodology

1. **Vendor inventory** — grepped vendor source root `app/storage/app/apk/apkstub/decompiled_vendor/sources/` for every `com.android.systemui:id/`, `com.android.settings:id/`, `com.hihonor.android.systemui:id/`, `com.android.keyguard:id/` literal, plus relevant window-class strings (`com.android.settings.password.*`, `com.vivo.settings.password.*`, `com.miui.permcenter.install.*`, `com.android.settings.Settings$*Activity`).
2. **Replica inventory** — grepped `vendor-replica/app/src/main/java/com/guard/wallet/` for the same literals.
3. **Gap identification** — set difference vendor − replica.

## Coverage matrix

### PIN key IDs

| Literal | Vendor location | Purpose | In replica? | Replica location |
|---|---|---|---|---|
| `com.android.systemui:id/key` | `g.java:2801`, `plug/c.java:151–152`, `helper/p.java:80`, `helper/r.java:87` | Generic PIN keypad key prefix (key0–key9) | YES | `utils/UnlockFilterFactory.java:76`, `plug/CrackLockCipherPlug.java:230–231`, `helper/AutomationHelper.java:86` |
| `com.android.systemui:id/VivoPinkey` | `g.java:2732`, `plug/c.java:154–155`, `helper/p.java:80`, `helper/r.java:425` | vivo-specific PIN keypad prefix (VivoPinkey0–9) | YES | `utils/UnlockFilterFactory.java:70`, `plug/CrackLockCipherPlug.java:234–235`, `helper/AutomationHelper.java:456` |
| `com.android.systemui:id/num` | `g.java:2686`, `plug/c.java:157–158` | Numeric button prefix (num0–num9) | YES | `utils/UnlockFilterFactory.java:64`, `plug/CrackLockCipherPlug.java:238–239` |
| `com.android.systemui:id/char_` | `g.java:2644`, `plug/c.java:160–161` | Character button prefix (char_a–char_z) | YES | `utils/UnlockFilterFactory.java:58`, `plug/CrackLockCipherPlug.java:242–243` |

### Pattern view IDs

| Literal | Vendor location | Purpose | In replica? | Replica location |
|---|---|---|---|---|
| `com.android.systemui:id/colorLockPatternView` | `helper/o.java:44`, `g.java:2333` | OPPO ColorOS pattern lock | YES | `helper/OverlayViewHelper.java:63` |
| `com.android.systemui:id/lockPatternView` | `helper/o.java:51`, `g.java:2347` | AOSP pattern lock | YES | `helper/OverlayViewHelper.java:71` |
| `com.android.systemui:id/vivo_lock_pattern_view` | `helper/o.java:300`, `g.java:2318` | vivo pattern lock | YES | `helper/OverlayViewHelper.java:372` |

### Action button IDs (Enter / Delete / Cancel / Confirm)

| Literal | Vendor location | Purpose | In replica? | Replica location |
|---|---|---|---|---|
| `com.android.systemui:id/key_enter` | `helper/r.java:76`, `g.java:2878` | PIN confirm/enter button | YES | `utils/SystemHelper.java:770`, `helper/AutomationHelper.java:75` |
| `com.android.systemui:id/delete_button` | `helper/r.java:66` | PIN delete/backspace button | YES | `helper/AutomationHelper.java:65` |
| `com.android.systemui:id/vivo_cancel` | `helper/r.java:404` | vivo PIN cancel button | YES | `helper/AutomationHelper.java:435` |
| `com.android.systemui:id/vivo_pin_confirm` | `helper/r.java:414`, `g.java:2774` | vivo PIN confirm button | YES | `utils/SystemHelper.java:724`, `delegate/UseDeviceCredentialDelegate.java:230/255/259`, `delegate/ConfirmLockDelegate.java:146`, `helper/AutomationHelper.java:445` |
| `com.android.systemui:id/mix_normal_confirm` | `g.java:2781` | Mixed PIN confirm button | YES | `utils/SystemHelper.java:734`, `delegate/UseDeviceCredentialDelegate.java:230/264/268`, `delegate/ConfirmLockDelegate.java:156` |
| `com.android.systemui:id/btn_letter_ok` | `g.java:3072` | MIUI letter/character input confirm button | YES | `utils/UnlockFilterFactory.java:82` |

### Internal references / negative click filters

| Literal | Vendor location | Purpose | In replica? | Replica location |
|---|---|---|---|---|
| `com.android.systemui:id/scrim_behind` | `helper/q.java:65` | Background scrim node ID — used as a NEGATIVE filter so the click loop skips clicking the lock-screen background dimming layer | **NO** | — (gap) |

## Coverage gap detail

### `com.android.systemui:id/scrim_behind`

**Vendor usage** — `app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/helper/q.java:65`:

```java
if (j2 != null && !concurrentLinkedQueue.contains(j2)
    && !Objects.equals(j2.id(), "com.android.systemui:id/scrim_behind")
    && !j2.equals(atomicReference2.get())
    && !j2.equals(atomicReference.get())
    && j2.click()) {
    ...
}
```

This is a guard inside vendor's PIN brute-force / unlock click loop. When iterating clickable nodes on a lock-screen overlay, `scrim_behind` is the dim background view that exists at the topmost z-order in some Android versions; clicking it does nothing useful and may dismiss the lock screen unexpectedly. The check excludes it from the click target set.

**Why it's missing in replica** — vendor's `helper/q.java` is the touch-point/click-iteration helper. The replica equivalent (likely `helper/PositiveClickListener.java` or a not-yet-ported `helper/TouchpointHelper.java`) does not contain a `scrim_behind` exclusion. Either:
- The replica's click loop already filters in some other way (e.g., by class/clickability), making the literal unnecessary
- OR the replica is missing the exclusion and may produce false-positive clicks on the scrim background during PIN brute force on devices where `scrim_behind` exists as a clickable node

**Recommended action** — manual code review of the replica's click iteration loop. If you find a method that iterates clickable nodes and clicks them in a fallback loop, add `&& !"com.android.systemui:id/scrim_behind".equals(node.id())` to its filter. Suggested locations to check, in order of likelihood:

1. `vendor-replica/app/src/main/java/com/guard/wallet/helper/PositiveClickListener.java`
2. `vendor-replica/app/src/main/java/com/guard/wallet/helper/NegativeClickListener.java`
3. `vendor-replica/app/src/main/java/com/guard/wallet/plug/CrackLockCipherPlug.java` (the PIN brute-force entry point)
4. `vendor-replica/app/src/main/java/com/guard/wallet/utils/SystemHelper.java`

**This audit deliberately does NOT add the literal to any Java file.** The user explicitly asked to report gaps without auto-fixing them. A follow-up plan should add the literal once the correct host file is identified by code review.

### Window class literals — not yet audited at this granularity

This audit focused on resource IDs (the user's primary concern). A future expansion of this audit should also enumerate window-class strings:

- `com.android.settings.password.ConfirmLockPassword(\$InternalActivity)?`
- `com.android.settings.password.ConfirmLockPattern(\$InternalActivity)?`
- `com.android.settings.password.ChooseLockGeneric`
- `com.vivo.settings.password.ConfirmVivoPin\$InternalActivity`
- `com.android.settings.Settings\$DevelopmentSettingsDashboardActivity`
- `com.android.settings.Settings\$DeviceInfoSettingsActivity`
- `com.android.settings.Settings\$MyDeviceInfoActivity`
- `com.android.settings.SubSettings`
- `com.miui.permcenter.install.AdbInputApplyActivity`
- `com.miui.securitycenter` (package name, not class)
- `com.oplus.battery` (OPPO battery package, used by `o/v.java`)

These are referenced extensively in vendor's `o/q.java`, `o/v.java`, `o/n.java`, `o/i0.java`, `o/a0.java` for `ListenWindow` registration. Replica equivalents likely live in each engine's `getListenWindows()` static initializer. **Out of scope for this audit pass — flagged for future work.**

## Vendor → replica file mapping (for cross-reference)

| Vendor file | Replica file | Notes |
|---|---|---|
| `com/guard/wallet/utils/g.java` | `utils/UnlockFilterFactory.java`, `utils/SystemHelper.java`, `helper/AutomationHelper.java` | The 3000-line "LockHelper" got split across three replica files |
| `com/guard/wallet/plug/c.java` | `plug/CrackLockCipherPlug.java` | PIN brute-force orchestrator |
| `com/guard/wallet/helper/o.java` | `helper/OverlayViewHelper.java` | Pattern lock overlay |
| `com/guard/wallet/helper/p.java` | `utils/UnlockFilterFactory.java`, `plug/CrackLockCipherPlug.java` | PIN listener split across two |
| `com/guard/wallet/helper/q.java` | **unmapped — missing replica equivalent for `scrim_behind` guard** | Touch-point click iteration |
| `com/guard/wallet/helper/r.java` | `helper/AutomationHelper.java`, `utils/SystemHelper.java` | Touch-points keypad helper |

## Keys flagged for real-device verification

Several values in `locateValues.json` are best-effort inferences and need confirmation on a real device of the relevant brand. These are NOT bugs — they are starting values that may need adjustment:

| Key | Current value | Risk | Verify on |
|---|---|---|---|
| `PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT` | `撤销USB调试授权` | Could also be "停用经过身份验证的ADB" or similar — depends on Android 12+ developer-options page UI variant | Any Android 13+ device |
| `PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT` | `连接到 WLAN 后启用调试模式` | Wording varies by Android version | OPPO PGFM10, Xiaomi 13 |
| `COLORS_BUILD_NUMBER_TEXT` | `版本号` | On older ColorOS may be "ColorOS 版本" | OPPO PGFM10 (192.168.31.249) |
| `MIUI_APP_POWER_CONSUME_TEXT` | `应用耗电` | MIUI 14+ may say "耗电情况" | Xiaomi 13 (192.168.31.102) |
| `MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT` | `省电策略` | HyperOS may use different label | Xiaomi 13 (192.168.31.102) |
| `HUA_WEI_ALLOW_RELATE_STARTUP_TEXT` | `允许关联启动` | EMUI/HarmonyOS variants differ | Huawei P40 (192.168.31.211) |
| `VIVO_BACKGROUND_POWER_MANAGER_TEXT` | `后台耗电管理` | OriginOS may use "后台高耗电管理" | Any vivo/iQOO device |
| `VIVO_POPUP_IN_BACKGROUND_TEXT` | `允许后台弹窗` | OriginOS may use "后台弹出界面" | Any vivo/iQOO device |
| `MOTO_OS_VERSION_INFO_TEXT` | `Android版本` | Motorola variants vary | Any Motorola device |

When verifying, test the actual ADB pair / keep-alive flow on the device, capture the failing UI text via `dumpsys window` or AccessibilityService log, then update `locateValues.json` + the `required[]` array in `LocateValuesAssetTest` if needed.
````

- [ ] **Step 3: Commit the audit doc**

```bash
cd /home/code/php/project/full-package
git add vendor-replica/docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md
git commit -m "$(cat <<'EOF'
docs(vendor-replica): add resource ID coverage audit (13/14 present, scrim_behind gap)

Verifies that all com.android.systemui:id/* literals and window-class strings
that vendor uses for PIN/pattern lock and window detection are also present
as hardcoded constants in vendor-replica Java sources, per vendor's design
where these IDs live next to code rather than in a config file.

Key findings:
- 13/14 literals (92.9%) confirmed present in replica
- 1 gap: com.android.systemui:id/scrim_behind (vendor helper/q.java:65,
  used as negative click filter in PIN brute-force iteration loop). Replica's
  helper/q.java equivalent is unmapped — see audit for recommended host files.
- Window-class literals deferred to follow-up audit pass
- 9 keys flagged [needs-real-device-verification] for OPPO/Xiaomi/Huawei/vivo

This audit deliberately does NOT add scrim_behind to any Java file or
locateValues.json — vendor stores resource IDs as Java literals (not in
LocateValuesUtils), and the user explicitly asked for gaps to be reported
not auto-fixed.
EOF
)"
```

---

## Task 8: Final summary and verification

**Files:** none modified

- [ ] **Step 1: Confirm all commits landed cleanly**

```bash
cd /home/code/php/project/full-package
git log --oneline -6
git status
```

Expected: at least 3 new commits (Task 3 rewrite + Task 4 comprehensive test + Task 7 audit doc), optionally 4 if Task 5 reconciliation made changes. Working tree clean.

- [ ] **Step 2: Run the LocateValues test class one final time**

```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:test --tests "com.guard.wallet.utils.LocateValuesAssetTest" --console=plain
```

Expected: 11 tests pass (10 from Task 2 + 1 from Task 4). If any failure, STOP — investigate before declaring done.

- [ ] **Step 3: Print a summary**

Verify these 4 facts before marking the plan complete:

1. `vendor-replica/app/src/main/assets/locateValues.json` is a flat `{"KEY": "value"}` JSON object with exactly 82 entries (or 82 ± reconciliation deltas from Task 5).
2. `PAIR_SECURITY_OPENING_TEXT` resolves to `"正在开启"` (verified by `LocateValuesAssetTest#pairSecurityOpeningTextIsDialogStateNotScreenName`).
3. `vendor-replica/docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md` exists and documents the 13/14 coverage with the `scrim_behind` gap.
4. No vendor-replica Java source file was modified by this plan.

If all 4 facts hold, the plan is complete. If any do not hold, identify which task drifted and re-execute it.

---

## Definition of Done

- ✅ `vendor-replica/app/src/main/assets/locateValues.json` is flat string→string with all 82 vendor-referenced keys
- ✅ `PAIR_SECURITY_OPENING_TEXT` = `正在开启`, NOT `安全设置`
- ✅ `LocateValuesAssetTest` created with 11 tests, all passing
- ✅ `./gradlew :app:test` passes with no regressions
- ✅ `vendor-replica/docs/audits/RESOURCE_ID_COVERAGE_AUDIT.md` documents 13/14 coverage and the `scrim_behind` gap
- ✅ Zero Java source files modified
- ✅ `locateValues-copy.json` left untouched (historical seed backup)
- ✅ At least 3 git commits, each focused on one concern
- ❌ NOT done: asset → externalFilePath seed copy (separate plan)
- ❌ NOT done: Laravel `/api/locateValue/entryAppMap.json` endpoint (separate plan)
- ❌ NOT done: real-device verification of `[needs-real-device-verification]` keys (separate plan)
- ❌ NOT done: window-class literal audit (deferred to follow-up audit pass)
- ❌ NOT done: `scrim_behind` Java fix (audit reports gap, fix is a separate plan)
