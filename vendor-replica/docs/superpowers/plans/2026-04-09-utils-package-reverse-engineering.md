# Utils Package Reverse Engineering Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Rename 11 obfuscated single-letter Java files in `com.guard.wallet.utils/` to semantic class names with readable field/method names, achieving production-ready code quality.

**Architecture:** Files remain in the same package (`com.guard.wallet.utils`). Each task renames one class + its fields/methods + updates all external callers. Tasks are ordered from lowest reference count to highest, building confidence before tackling the 800+ line files. Parallel agent dispatch is safe for non-overlapping tasks (Tasks 1-3 can run in parallel).

**Tech Stack:** Java 8, Android SDK 34, Gradle 8.5

**Vendor Reference:** `/home/code/php/project/full-package/androidReverseEngineering/src/com/guard/wallet/utils/`

---

## File Structure

All files stay in `app/src/main/java/com/guard/wallet/utils/` — only class names change:

| Old file | New file | Lines | Ext refs |
|----------|----------|-------|----------|
| `c.java` | DELETE (empty abstract class) | 4 | ~0 real |
| `a.java` | `DialogCancelListener.java` | 10 | ~5 |
| `i.java` | `SnowflakeIdGenerator.java` | 56 | ~10 |
| `k.java` | `WindowUtils.java` | 70 | ~15 |
| `j.java` | `HttpCallbackUtils.java` | 33 | ~10 |
| `f.java` | `LocateValuesUtils.java` | 44 | ~20 |
| `b.java` | `GuideDialogUtils.java` | 106 | ~30 |
| `d.java` | `ConfigManager.java` | 272 | ~40 |
| `e.java` | `DeviceUtils.java` | 442 | ~80 |
| `h.java` | `SharedPrefsManager.java` | 749 | ~200 |
| `g.java` | `SystemHelper.java` | 810 | ~300 |

---

### Task 1: Delete c.java + Rename a.java, i.java, k.java (smallest files, parallel-safe)

**Files:**
- Delete: `app/src/main/java/com/guard/wallet/utils/c.java`
- Rename: `a.java` → `DialogCancelListener.java`
- Rename: `i.java` → `SnowflakeIdGenerator.java`
- Rename: `k.java` → `WindowUtils.java` (already has this name in doc header — verify)

**Agent prompt context:**
- `c.java` is an empty `public abstract class c {}` — search for `utils.c` references; if none compile against it, delete. If some exist, replace with inline usage.
- `a.java` (10L): `implements DialogInterface.OnCancelListener`, used by `b.java` only. Rename class + update b.java reference.
- `i.java` (56L): Snowflake ID generator. Rename class. Fields: `a→dataCenterId`, `b→sequence`, `c→lastTimestamp`. Method: `a()→nextId()`.
- `k.java` (70L): Window utils. Rename class. Methods: `a()→isMainThread()`, `b(View)→setSkipScreenshot(View)`, `c(int)→setScreenBrightness(int)`.

- [ ] **Step 1:** Read all 4 files + grep for external references (`utils\.c\b`, `utils\.a\b`, `utils\.i\b`, `utils\.k\b`)
- [ ] **Step 2:** Delete c.java if no real references (exclude comments/imports that won't compile)
- [ ] **Step 3:** Create DialogCancelListener.java, SnowflakeIdGenerator.java, WindowUtils.java with renamed class/fields/methods + Chinese doc header
- [ ] **Step 4:** Update all external callers (within utils/ and outside)
- [ ] **Step 5:** Delete old a.java, i.java, k.java
- [ ] **Step 6:** Update .pending files
- [ ] **Step 7:** Build verify: `./gradlew assembleDebug 2>&1 | tail -3`

---

### Task 2: Rename j.java + f.java (small utility files, parallel-safe with Task 1)

**Files:**
- Rename: `j.java` → `HttpCallbackUtils.java`
- Rename: `f.java` → `LocateValuesUtils.java`

**Agent prompt context:**
- `j.java` (33L): HTTP callback utility. Contains inner class. Rename class + inner class methods. Field access pattern: `new j()` or `j.something`.
- `f.java` (44L): Brand-specific localization string loader. Fields: `a→locateValuesMap` (ConcurrentHashMap), `b→loaded` (AtomicBoolean). Methods: `a()→loadValues()`, `b(String)→getValue(String)`.

- [ ] **Step 1:** Read both files + vendor references + grep external callers
- [ ] **Step 2:** Create HttpCallbackUtils.java and LocateValuesUtils.java with renames + Chinese doc headers
- [ ] **Step 3:** Update all external callers
- [ ] **Step 4:** Delete old files, update .pending
- [ ] **Step 5:** Build verify

---

### Task 3: Rename b.java → GuideDialogUtils (106 lines, parallel-safe with Tasks 1-2)

**Files:**
- Rename: `b.java` → `GuideDialogUtils.java`

**Agent prompt context:**
- `b.java` (106L): Accessibility guide dialog management.
- Fields: `a→guideDialogRef`, `b→allowRestrictedSettings`, `c→currentActivityRef`, `d→guideImageIndex`, `e→triggerCount`, `f→statusCode1`, `g→statusCode2`
- Methods: `a()→showGuideActivity()`, `b()→dismissGuideDialog()`, `c()→getGuidePageUrl()`, `d(Activity)→registerCurrentActivity(Activity)`, `e()→triggerGuideFlow()`, `f()→showAccessibilityEnableDialog()`
- CRITICAL: `b.java` references `a.java` (DialogCancelListener) — if Task 1 already renamed it, use new name. If running in parallel, use the OLD name and fix in verification.

- [ ] **Step 1:** Read file + vendor reference + grep external callers
- [ ] **Step 2:** Create GuideDialogUtils.java with renames + Chinese doc header
- [ ] **Step 3:** Update all external callers (especially other utils/ files that call `b.e()`, `b.d()`)
- [ ] **Step 4:** Delete old file, update .pending
- [ ] **Step 5:** Build verify

---

### Task 4: Rename d.java → ConfigManager (272 lines)

**Files:**
- Rename: `d.java` → `ConfigManager.java`

**Agent prompt context:**
- `d.java` (272L): BuildConfig loader from assets/config.json.
- Fields: `a-f` are static Integer defaults → `DEFAULT_PROMOTION_MODEL`, `DEFAULT_UNINSTALL`, `DEFAULT_ACTIVE_ADMIN`, `DEFAULT_DEBUG`, `DEFAULT_SCREEN_OFF_DURATION`, `DEFAULT_IDLE_DURATION`
- Methods: `a()→loadBuildConfig()`, `b()→getBlockIconUrl()`, `c()→getDownloadHost()`, `d()→getDownloadName()`, `e()→getGuideUrl()`, `f()→getMainUrl()`, `g()→getPromotionModel()`, `h()→getServerHost()`, `i()→getUpdateMsg()`
- Called by: MainApplication, delegate classes, server handlers

- [ ] **Step 1:** Read file + vendor reference + grep external callers (search `utils\.d\.` and `\bd\.a\b` etc within utils/ package)
- [ ] **Step 2:** Create ConfigManager.java with renames + Chinese doc header
- [ ] **Step 3:** Update all external callers
- [ ] **Step 4:** Delete old file, update .pending
- [ ] **Step 5:** Build verify

---

### Task 5: Rename e.java → DeviceUtils (442 lines)

**Files:**
- Rename: `e.java` → `DeviceUtils.java`

**Agent prompt context:**
- `e.java` (442L): Device info, brand detection, activity management, screen metrics.
- Fields: `a→deviceIdCache` (String), `b→currentDisplayId` (Integer)
- Methods (14): `a()→getBrandJsFileName()`, `b()→getCurrentActivity()`, `c()→getDeviceUniqueId()`, `d(Context)→getLanguageTag(Context)`, `e()→buildScreenMetrics()`, `f(String)→extractLangPrefix(String)`, `g()→isHuaweiOrHonor()`, `h()→isHarmonyOS()`, `i()→isOppoFamily()`, `j()→isScreenOn()`, `k()→isTecnoFamily()`, `l()→isVivoFamily()`, `m()→isXiaomiFamily()`, `n()→getPhoneNumber()`
- HEAVILY referenced (~80 external refs): `e.b()` (getCurrentActivity), `e.g()` (isHuaweiOrHonor), `e.m()` (isXiaomiFamily) etc.
- CRITICAL: `com.guard.wallet.utils.e` is used as a TYPE in many files (field declarations, method params). Must update all type references too.

- [ ] **Step 1:** Read file + vendor reference + comprehensive grep for `utils\.e\.` and `\be\.` within utils callers
- [ ] **Step 2:** Create DeviceUtils.java with all renames + Chinese doc header
- [ ] **Step 3:** Update all external callers (expect ~80 files)
- [ ] **Step 4:** Delete old file, update .pending
- [ ] **Step 5:** Build verify

---

### Task 6: Rename h.java → SharedPrefsManager (749 lines)

**Files:**
- Rename: `h.java` → `SharedPrefsManager.java`

**Agent prompt context:**
- `h.java` (749L): SharedPreferences wrapper + JSON serialization.
- ALL methods are static + synchronized. ~35 public methods.
- Key method renames: `l(key)→getString(key)`, `i(key)→getInt(key)`, `j(key)→getLong(key)`, `w(key)→remove(key)`, `D(value,key)→saveObject(value,key)`, `N(obj)→toJson(obj)`, `M(json)→parseJson(json)`, `e(key)→hasKey(key)`, `s()→isModified()`, `I()→initialize()`
- CRITICAL: ~200 external references. Pattern `h.l("key")` appears in almost every business file.
- Many callers use `h.` prefix (within same package, unqualified) — grep must cover both `utils.h.` and standalone `h.` in utils/ package files.

- [ ] **Step 1:** Read full file + vendor reference + comprehensive grep
- [ ] **Step 2:** Create SharedPrefsManager.java with all renames + Chinese doc header
- [ ] **Step 3:** Update external callers in batches (use 2 sub-agents if needed: one for com.guard.wallet.server/handler/*.java, one for rest)
- [ ] **Step 4:** Delete old file, update .pending
- [ ] **Step 5:** Build verify

---

### Task 7: Rename g.java → SystemHelper (810 lines)

**Files:**
- Rename: `g.java` → `SystemHelper.java`

**Agent prompt context:**
- `g.java` (810L): Global utility facade with 114+ static methods.
- This is a DELEGATION LAYER — most methods call into the 14+ specialized Utils classes already named properly (AccessibilityUtils, GestureUtils, etc.)
- CLASS RENAME ONLY for this task. Do NOT rename the 114 methods — they are called from hundreds of locations. Method rename would be a separate multi-session effort.
- Fields: None (stateless facade).
- Pattern: `g.Z()` → `SystemHelper.Z()`, `g.k1()` → `SystemHelper.k1()` etc.
- CRITICAL: ~300 external references. Highest impact rename.
- CAREFUL: `g.java` in the `utils` package vs `com.guard.wallet.helper.g` — these are DIFFERENT classes. Only rename refs to `com.guard.wallet.utils.g`.

- [ ] **Step 1:** Comprehensive grep for all `utils.g` and `utils\.g\.` references
- [ ] **Step 2:** Create SystemHelper.java (copy content, change class name only, add Chinese doc header)
- [ ] **Step 3:** Update ALL external callers — use 3 parallel sub-agents:
  - Agent A: `com/guard/wallet/service/*.java` + `com/guard/wallet/receiver/*.java`
  - Agent B: `com/guard/wallet/server/**/*.java` + `com/guard/wallet/delegate/**/*.java`
  - Agent C: all remaining files (MainApplication, thread/, http/, engine/, etc.)
- [ ] **Step 4:** Delete old file, update .pending
- [ ] **Step 5:** Build verify

---

## Execution Order & Parallelism

```
Round 1 (parallel): Task 1 + Task 2 + Task 3    ← 6 small files, ~50 refs
Round 2:            Task 4                        ← d.java, ~40 refs
Round 3:            Task 5                        ← e.java, ~80 refs
Round 4:            Task 6                        ← h.java, ~200 refs
Round 5:            Task 7                        ← g.java, ~300 refs (class rename only)
```

Each round ends with `./gradlew assembleDebug` verification.

## Risk Mitigation

- **False positive grep matches**: `utils.a`, `utils.b` etc. match MANY unrelated patterns (local vars, other packages). Agents MUST read each file to confirm the reference is the utils class, not a coincidental single-letter match.
- **Same-package unqualified refs**: Within `com.guard.wallet.utils/`, files reference each other without the package prefix (just `g.Z()` not `com.guard.wallet.utils.g.Z()`). These MUST be found and updated.
- **g.java method rename deferred**: With 114 methods and 300+ call sites, renaming methods in g.java is a separate effort. This plan only renames the CLASS.
- **.pending files**: Each task updates corresponding .pending files for consistency.
