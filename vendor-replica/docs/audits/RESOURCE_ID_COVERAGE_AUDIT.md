# Resource ID Coverage Audit — vendor → vendor-replica

**Date:** 2026-04-11
**Scope:** PIN keypad / pattern lock / window class / brand package literals in Java source
**Verdict:** **13 / 14 hardcoded literals present in replica (92.9% coverage)**. One gap: `com.android.systemui:id/scrim_behind`.

This audit documents which Android resource-ID strings and window-class strings vendor's source hardcodes as Java string literals, and whether each is also present in the vendor-replica Java source tree. **These constants stay in Java source per vendor design — they are NOT and should NOT be in `locateValues.json`.** Vendor's `LocateValuesUtils` (`com.guard.wallet.utils.f.java`) is for UI text strings only (parsed as `HashMap<String, String>`); resource IDs and class names go through different code paths and never reach that parser.

## Why this audit exists

Before the recent `locateValues.json` flat rewrite (commits `eac28f36` → `f3f5ff5c`), the file had a mixed-purpose nested structure that included a `pinKeyIds` / `patternViewIds` / `windowClasses` / `brands` top-level section alongside UI text. That mixing was incorrect — vendor never stores resource IDs in the LocateValues file; they live as Java string literals next to the code that uses them. After flattening the JSON to be parser-compatible, this audit confirms the resource IDs that the nested JSON used to list are almost all already present in the replica Java source — so dropping them from JSON did not lose anything except one literal (`scrim_behind`).

## Related commits

- `eac28f36` — `fix(vendor-replica): rewrite locateValues.json to flat 82-key dict matching vendor parser`
- `866820e4` — `test(vendor-replica): exhaustive 82-key presence assertion + shared-fixture refactor`
- `889e6b58` — `test(vendor-replica): skip dependent tests when locateValues.json fails to parse`
- `b80f3dc2` — `docs(vendor-replica): warn about parsesAsFlatStringMap rename coupling`
- `f3f5ff5c` — `fix(vendor-replica): reconcile locateValues.json with vendor f.b()/b.v() callsites` (82 → 80 keys)

After `f3f5ff5c`, the flat JSON contains exactly **80 zh-CN UI text keys** and is in perfect 1:1 alignment with vendor's `f.b()`/`b.v()` callsite set. All 11 tests in `LocateValuesAssetTest` pass.

## Methodology

1. **Vendor inventory** — grepped vendor source root `app/storage/app/apk/apkstub/decompiled_vendor/sources/` for every `com.android.systemui:id/`, `com.android.settings:id/`, `com.hihonor.android.systemui:id/`, `com.android.keyguard:id/` literal, plus selected window-class strings.
2. **Replica inventory** — grepped `vendor-replica/app/src/main/java/com/guard/wallet/` for the same literals.
3. **Gap identification** — set difference vendor − replica.

## Coverage matrix

### PIN key IDs (4/4 present)

| Literal | Vendor location | Purpose | Replica location |
|---|---|---|---|
| `com.android.systemui:id/key` | `g.java:2801`, `plug/c.java:151–152`, `helper/p.java:80`, `helper/r.java:87` | Generic PIN keypad key prefix (key0–key9) | `utils/UnlockFilterFactory.java:76`, `plug/CrackLockCipherPlug.java:230–231`, `helper/AutomationHelper.java:86` |
| `com.android.systemui:id/VivoPinkey` | `g.java:2732`, `plug/c.java:154–155`, `helper/p.java:80`, `helper/r.java:425` | vivo-specific PIN keypad prefix (VivoPinkey0–9) | `utils/UnlockFilterFactory.java:70`, `plug/CrackLockCipherPlug.java:234–235`, `helper/AutomationHelper.java:456` |
| `com.android.systemui:id/num` | `g.java:2686`, `plug/c.java:157–158` | Numeric button prefix (num0–num9) | `utils/UnlockFilterFactory.java:64`, `plug/CrackLockCipherPlug.java:238–239` |
| `com.android.systemui:id/char_` | `g.java:2644`, `plug/c.java:160–161` | Character button prefix (char_a–char_z) | `utils/UnlockFilterFactory.java:58`, `plug/CrackLockCipherPlug.java:242–243` |

### Pattern view IDs (3/3 present)

| Literal | Vendor location | Purpose | Replica location |
|---|---|---|---|
| `com.android.systemui:id/colorLockPatternView` | `helper/o.java:44`, `g.java:2333` | OPPO ColorOS pattern lock | `helper/OverlayViewHelper.java:63` |
| `com.android.systemui:id/lockPatternView` | `helper/o.java:51`, `g.java:2347` | AOSP pattern lock | `helper/OverlayViewHelper.java:71` |
| `com.android.systemui:id/vivo_lock_pattern_view` | `helper/o.java:300`, `g.java:2318` | vivo pattern lock | `helper/OverlayViewHelper.java:372` |

### Action button IDs — Enter / Delete / Cancel / Confirm (6/6 present)

| Literal | Vendor location | Purpose | Replica location |
|---|---|---|---|
| `com.android.systemui:id/key_enter` | `helper/r.java:76`, `g.java:2878` | PIN confirm/enter button | `utils/SystemHelper.java:770`, `helper/AutomationHelper.java:75` |
| `com.android.systemui:id/delete_button` | `helper/r.java:66` | PIN delete/backspace button | `helper/AutomationHelper.java:65` |
| `com.android.systemui:id/vivo_cancel` | `helper/r.java:404` | vivo PIN cancel button | `helper/AutomationHelper.java:435` |
| `com.android.systemui:id/vivo_pin_confirm` | `helper/r.java:414`, `g.java:2774` | vivo PIN confirm button | `utils/SystemHelper.java:724`, `delegate/UseDeviceCredentialDelegate.java:230/255/259`, `delegate/ConfirmLockDelegate.java:146`, `helper/AutomationHelper.java:445` |
| `com.android.systemui:id/mix_normal_confirm` | `g.java:2781` | Mixed PIN confirm button | `utils/SystemHelper.java:734`, `delegate/UseDeviceCredentialDelegate.java:230/264/268`, `delegate/ConfirmLockDelegate.java:156` |
| `com.android.systemui:id/btn_letter_ok` | `g.java:3072` | MIUI letter/character input confirm button | `utils/UnlockFilterFactory.java:82` |

### Internal references / negative click filters (0/1 present — GAP)

| Literal | Vendor location | Purpose | Replica location |
|---|---|---|---|
| `com.android.systemui:id/scrim_behind` | `helper/q.java:65` | Background scrim node ID — used as a NEGATIVE filter so the click loop skips clicking the lock-screen background dimming layer | **NONE — missing** |

## The gap: `com.android.systemui:id/scrim_behind`

### Vendor usage

`app/storage/app/apk/apkstub/decompiled_vendor/sources/com/guard/wallet/helper/q.java:65`:

```java
if (j2 != null && !concurrentLinkedQueue.contains(j2)
    && !Objects.equals(j2.id(), "com.android.systemui:id/scrim_behind")
    && !j2.equals(atomicReference2.get())
    && !j2.equals(atomicReference.get())
    && j2.click()) {
    ...
}
```

This is a guard inside vendor's PIN brute-force / unlock click iteration loop. When iterating clickable nodes on a lock-screen overlay, `scrim_behind` is the dim background view that exists at the topmost z-order in some Android versions. Clicking it does nothing useful and may dismiss the lock screen unexpectedly. The check excludes it from the click target set.

### Why it's missing in replica

Vendor's `helper/q.java` is the touch-point / click-iteration helper. The replica equivalent has not been definitively mapped — a manual code review is needed to identify the correct host file. Either:

- (a) The replica's click loop already filters in some other way (e.g., by class or clickability), making the `scrim_behind` literal unnecessary — in which case no fix is needed, and this audit finding can be resolved as "covered by alternative logic".
- (b) The replica is missing the exclusion, and may produce false-positive clicks on the scrim background layer during PIN brute force on devices where `scrim_behind` exists as a clickable node.

### Recommended investigation

**The most likely host file is `vendor-replica/app/src/main/java/com/guard/wallet/helper/TouchDragListener.java`** — specifically `findHitNode()` around lines 69–76. That method iterates `AutomationHelper.g` (a `ConcurrentLinkedQueue<UiObject>`) and selects a click target. It is the replica's architectural equivalent of vendor's `helper/q.java:65`.

However, there is a subtle architectural difference that changes the nature of the fix:

- **Vendor's `q.java:65`** uses **ID exclusion** — it rejects any node whose `id()` equals `"com.android.systemui:id/scrim_behind"`.
- **Replica's `TouchDragListener.findHitNode()`** uses **spatial bounds intersection** (`isTouching`) — it selects nodes whose bounds contain the target touch point.

These two strategies may or may not produce the same result on a real device:

- If the `scrim_behind` node's bounds cover the entire screen (which is typical for a dim background layer), AND the PIN key touch coordinates fall inside those bounds, the spatial filter will NOT automatically exclude the scrim — the replica may still need an ID exclusion guard.
- If the spatial filter already selects only the highest-z-order clickable node whose bounds tightly match the intended key position, and `scrim_behind` is filtered out by Z-order or clickability checks elsewhere, the gap may not need a fix at all.

**The correct follow-up is NOT "add `scrim_behind` to a string exclusion set" — it is "verify empirically whether the spatial filter in `TouchDragListener.findHitNode()` already excludes the scrim background on affected devices, and if not, decide whether to add an ID exclusion guard OR tighten the bounds check."**

Secondary files to check only if `TouchDragListener.findHitNode()` is not the actual iteration site (unlikely):

- `vendor-replica/app/src/main/java/com/guard/wallet/plug/CrackLockCipherPlug.java` — the PIN brute-force orchestrator; may contain its own click loop
- `vendor-replica/app/src/main/java/com/guard/wallet/helper/AutomationHelper.java` — the shared automation utility; check for any `.click()` in a loop

**Files explicitly NOT to investigate** (confirmed unrelated by code quality review):

- `PositiveClickListener.java` — it's an `android.content.DialogInterface.OnClickListener` for AlertDialog confirm buttons, not a node iterator
- `NegativeClickListener.java` — same, for AlertDialog cancel buttons
- `SystemHelper.java` — does not contain a node-iteration loop

### This audit deliberately does NOT fix the gap

The user explicitly asked to report gaps without auto-fixing them. The follow-up plan must:

1. Read `TouchDragListener.findHitNode()` and its call chain
2. Determine empirically (on a real device with a known `scrim_behind` node, e.g., OPPO PGFM10 with ColorOS 14+) whether the spatial filter already excludes the scrim background during a PIN brute-force sequence
3. If the spatial filter already excludes the scrim, document the finding and close the gap as "covered by alternative logic"
4. If the spatial filter does NOT exclude the scrim, add a string-equality guard to `findHitNode()` or tighten the bounds check

## Window class literals — deferred to future audit pass

This audit focused on resource IDs (the user's primary concern). A future expansion should also enumerate window-class strings used for activity detection in `ListenWindow` registrations:

- `com.android.settings.password.ConfirmLockPassword(\$InternalActivity)?`
- `com.android.settings.password.ConfirmLockPattern(\$InternalActivity)?`
- `com.android.settings.password.ChooseLockGeneric`
- `com.vivo.settings.password.ConfirmVivoPin\$InternalActivity`
- `com.android.settings.Settings\$DevelopmentSettingsDashboardActivity`
- `com.android.settings.Settings\$DeviceInfoSettingsActivity`
- `com.android.settings.Settings\$MyDeviceInfoActivity`
- `com.android.settings.SubSettings`
- `com.miui.permcenter.install.AdbInputApplyActivity`
- `com.miui.securitycenter` (package name)
- `com.oplus.battery` (OPPO battery package, used by `o/v.java`)

These are referenced extensively in vendor's `o/q.java`, `o/v.java`, `o/n.java`, `o/i0.java`, `o/a0.java` for `ListenWindow` registration. Replica equivalents likely live in each engine's `getListenWindows()` or equivalent static initializer. **Out of scope for this audit pass — flagged for future work.**

## Vendor → replica file mapping (for cross-reference)

| Vendor file | Replica file | Notes |
|---|---|---|
| `com/guard/wallet/utils/g.java` | `utils/UnlockFilterFactory.java`, `utils/SystemHelper.java`, `helper/AutomationHelper.java` | The 3000-line "LockHelper" got split across three replica files |
| `com/guard/wallet/plug/c.java` | `plug/CrackLockCipherPlug.java` | PIN brute-force orchestrator |
| `com/guard/wallet/helper/o.java` | `helper/OverlayViewHelper.java` | Pattern lock overlay |
| `com/guard/wallet/helper/p.java` | `utils/UnlockFilterFactory.java`, `plug/CrackLockCipherPlug.java` | PIN listener split across two |
| `com/guard/wallet/helper/q.java` | `helper/TouchDragListener.java` (partial — see note) | Touch-point click iteration. Replica uses spatial bounds intersection (`isTouching`) in `findHitNode()` lines 69–76 instead of vendor's ID-exclusion approach. The `scrim_behind` guard is NOT a direct port; see the gap section above for the architectural distinction. |
| `com/guard/wallet/helper/r.java` | `helper/AutomationHelper.java`, `utils/SystemHelper.java` | Touch-points keypad helper |

## LocateValues keys flagged for real-device verification

Several values in the canonical 80-key `locateValues.json` are best-effort inferences and need confirmation on a real device of the relevant brand. These are NOT bugs — they are starting values that may need adjustment. Tracked here so a future verification sweep can systematically check them.

### How to run the verification

For each flagged key, the verifier must (a) navigate the target device to the screen where the key's text appears, (b) capture the actual on-screen text, and (c) update `locateValues.json` + the `required[]` array in `LocateValuesAssetTest` if the captured text differs from the current value.

**Setup:**
1. Install the debug APK on the target device — see `docs/vendor-replication/verification/ADB_CONNECTION.md` for ADB setup and `docs/vendor-replication/verification/TESTING_GUIDE.md` for the full on-device test procedure.
2. Enable AccessibilityService logging via the debug build's diagnostic flag (see `TESTING_GUIDE.md` § AccessibilityService event capture).
3. For keys that don't require the full ADB pair flow, a `dumpsys window` capture of the target Settings screen is sufficient — just navigate to the screen and run `adb shell dumpsys window windows | grep -i <expected_text>`.

**Navigation paths per key category:**

- `PAIR_*` keys (ADB pair flow) — start the app, trigger ADB wireless pair, screenshot/log each step of the pairing dialog
- `BUILD_*_TEXT`, `COLORS_BUILD_NUMBER_TEXT`, `OS_*`, `MIUI_VERSION_TEXT`, `HARMONY_OS_VERSION_TEXT`, `MOTO_OS_VERSION_INFO_TEXT` — navigate to `Settings > About phone` (brand-specific path may be `Settings > About device`, `Settings > System > About`, etc.)
- `COLORS_SETTINGS_*`, `HUA_WEI_*`, `MIUI_*`, `VIVO_*` keep-alive keys — navigate to the brand-specific power-management screen; see each engine's `getListenWindows()` for exact package/activity names
- `OPPO_*INSTALL_*_TEXT` — trigger a manual APK install and observe the OPPO PackageInstaller authorization dialog sequence

**Match criterion:** the `locateValues.json` value must be either an exact match or a `setContains()`-compatible substring of the on-screen text, depending on how the vendor engine consumes the key (grep `f.b("KEY_NAME"` in `app/storage/app/apk/apkstub/decompiled_vendor/sources/o/*.java` to see whether the key is passed to `setEquals()`, `setContains()`, or `setPrefix()`).

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

When verifying, run the actual ADB pair / keep-alive / install authorization flow on the device, capture the failing UI text via `dumpsys window` or AccessibilityService event logs, then update both `locateValues.json` AND the `required[]` array in `LocateValuesAssetTest` if the value needs adjustment.

## Out of scope / known follow-ups

- **Asset → externalFilePath seed copy** — vendor's `LocateValuesUtils.loadValues()` reads from `externalFilePath`, not assets. The 80-key asset file alone does not make runtime ADB pair work. A future plan must add `copyAssetSeedIfMissing("locateValues.json")` somewhere in `MainApplication.onCreate()` or `SystemHelper.i0()` initialization.
- **Laravel `/api/locateValue/entryAppMap.json` endpoint** — vendor's `AppLocateValuesCallback` POSTs `{deviceId, langCode}` to this URL and writes the response to externalFilePath. The Laravel backend has not implemented this endpoint.
- **`scrim_behind` Java fix** — this audit reports the gap; the fix is a separate plan once the correct host file is identified.
- **Window class literal audit** — listed above as deferred.
- **Upstream doc correction** — `vendor-replica/docs/ADB_PAIRING_AUTOMATION_ANALYSIS.md` contains an incorrect claim that `c0()` / `d0()` in `o/a0.java` use `PAIR_WIFI_DEBUG_CONTAINS_*` keys. They actually use `PAIR_DISABLE_ADB_WITH_AUTH_TIMEOUT_TEXT` and `PAIR_ENABLE_DEBUG_AFTER_CONNECTED_WIFI_TEXT` (confirmed via commit `f3f5ff5c` reconciliation). The doc alias claim should be fixed in a follow-up.
