# LocateValues Asset Seed Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a standalone `LocateValuesSeeder` that copies `assets/locateValues.json` to `externalFilesDir/locateValues.json` on first launch or after APK upgrade (SHA-256 hash comparison), wired into `MainApplication.init()` with a single-line invocation. Enables real-device ADB pairing / keep-alive / install authorization automation to work offline without a C2 backend.

**Architecture:** New `LocateValuesSeeder.java` class labeled `// ADAPT: no vendor equivalent`. Core logic in a pure function `seedIfChanged(InputStream, File, VersionStore)` unit-tested via in-memory `VersionStore` double; outer wrapper `seedIfChanged(Context)` extracts Android dependencies. `LocateValuesUtils.java` stays unchanged (preserves vendor `f.java` 1:1 replication). `MainApplication.init()` gains exactly 6 lines (1 invocation + 3 comment lines + 1 log line + 1 blank).

**Tech Stack:** Java 17, Gradle 8.5, AGP 8.2.2, JUnit 4.13.2, `MessageDigest.getInstance("SHA-256")` from `java.security` (no new dependencies). Zero Robolectric, zero Mockito. All I/O in unit tests goes through `@Rule TemporaryFolder`.

**Spec reference:** `docs/superpowers/specs/2026-04-11-locate-values-asset-seed-design.md` (commit `0e4a48c2`). Read it for the architectural reasoning behind the 5 design decisions (hash semantics, SHA-256, scope, placement, test strategy). This plan is the execution counterpart.

**Prior commit chain:** `eac28f36..b04a8cbb` shipped the flat 80-key asset file + `LocateValuesAssetTest` + resource-ID coverage audit. This plan continues on top of `0e4a48c2` (the spec commit).

---

## File Structure

Before touching any code, understand which files get created / modified and what each one owns.

### Files created

| Path | Lines | Responsibility |
|---|---|---|
| `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java` | ~200 | All seed logic. Exposes `public static SeedResult seedIfChanged(Context)` (production entry) and `static SeedResult seedIfChanged(InputStream, File, VersionStore)` (pure-function inner, unit-tested). Contains inner types: `SeedAction` enum, `SeedResult` value class, `VersionStore` interface, `SharedPrefsVersionStore` production impl. |
| `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java` | ~280 | 9 JUnit 4 tests covering all 5 `SeedAction` outcomes + hash determinism + error paths + contract guarantees. Uses `@Rule TemporaryFolder` and a private `InMemoryVersionStore` inner class. Zero Robolectric. |

### Files modified

| Path | Change | Responsibility after change |
|---|---|---|
| `vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java` | +6 lines at line 163 (inside `init()`, after `Log.d("com.guard.wallet 正在启动")`) | Trigger seed invocation as the first action in `init()`, before any `SystemHelper.i0()` usage or `ConfigFileObserver` registration. |

### Files explicitly NOT touched

- `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesUtils.java` — vendor `f.java` 1:1 replica, must not diverge
- `vendor-replica/app/src/main/assets/locateValues.json` — already canonical (80 keys), Seeder reads it unchanged
- `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java` — existing 11 tests, must stay green
- `vendor-replica/app/build.gradle` — no new dependencies
- Any Laravel backend file — Laravel `/api/locateValue/entryAppMap.json` endpoint is separate plan

---

## Out of Scope

All 6 items documented in spec §9 — no exceptions, no "while we're here" additions:

1. `listenWindows.json` seeding
2. Laravel `POST /api/locateValue/entryAppMap.json` endpoint
3. Real-device verification of 9 `[needs-real-device-verification]` keys
4. Manual version-number bumping mechanism (we chose SHA-256 hash)
5. Generic `AssetSeeder` abstraction for multiple files
6. Asset file compression / obfuscation

---

## Task Overview

| # | Task | Commits | Approx. time |
|---|---|---|---|
| 1 | Pre-flight + skeleton class + first TDD cycle (Test 1: firstRun) | 1 | 20 min |
| 2 | Remaining decision-branch tests (Tests 2, 3, 4) + all 5 `SeedAction` branches implemented | 1 | 20 min |
| 3 | Error path + contract tests (Tests 5, 6, 7, 8, 9) + atomic write hardening | 1 | 25 min |
| 4 | Outer `Context` wrapper + `MainApplication.init()` integration | 1 | 10 min |
| 5 | Full module regression verification + done check | 0 | 5 min |

**Total: 5 tasks, 4 commits.** Test count grows 11 → 20 in `com.guard.wallet.utils.*` (11 existing + 9 new), module total 42 → 51.

---

## Task 1: Pre-flight + skeleton class + first TDD cycle

**Goal:** Establish the `LocateValuesSeeder` class with all type definitions, add the first test (`firstRun_emptyStoreAndNoFile_seedsFirstTime`), watch it fail, implement the minimal inner pure function body to make it pass, commit.

**Files:**
- Create: `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java`
- Create: `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java`

- [ ] **Step 1.1: Pre-flight checks**

Run these verification commands and confirm each expected state. If any fails, STOP and report.

```bash
cd /home/code/php/project/full-package
git log --oneline -3
```
Expected: top commit is `0e4a48c2 docs(specs): add LocateValues asset seed design spec`. The two commits before it are `b04a8cbb` and `d8422b21`.

```bash
git status --short -- \
  vendor-replica/app/src/main/java/com/guard/wallet/utils/ \
  vendor-replica/app/src/test/java/com/guard/wallet/utils/ \
  vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java
```
Expected: only `LocateValuesAssetTest.java` may show `M` or `??` from prior work. No other dirty file in scope. If `LocateValuesSeeder.java` already exists, STOP — Plan A is already partially done.

```bash
ls vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesUtils.java
```
Expected: exists. This is the vendor `f.java` replica we must not modify.

```bash
grep -n 'TypeToken<HashMap<String, String>>' vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesUtils.java
```
Expected: exactly 1 match. Confirms parser signature is untouched from prior 7-commit chain.

```bash
sed -n '160,170p' vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java
```
Expected: shows `public void init()` declaration at line 162, `Log.d(TAG, "com.guard.wallet 正在启动")` at line 163, followed by `instance = this;` at line 164, then audio cache setup starting at line 167. **Remember line 163** — this is the insertion point for Task 4.

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesAssetTest" --console=plain 2>&1 | tail -15
```
Expected: `BUILD SUCCESSFUL`, 11 tests pass. This is the baseline we must not regress.

- [ ] **Step 1.2: Create the skeleton `LocateValuesSeeder.java`**

Use the Write tool to create the file with this exact content. This version has all type definitions + method signatures. The inner pure function body throws `UnsupportedOperationException` so the first test will fail RED as expected.

**File:** `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java`

```java
package com.guard.wallet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 种子 {@code locateValues.json} 从 APK assets 到 externalFilesDir,
 * 仅在 asset 内容发生变化 (SHA-256 hash 比对) 或目标文件首次不存在时执行。
 *
 * <p><b>ADAPT: no vendor equivalent.</b> Vendor 的 {@code f.java} 完全依赖 C2
 * 服务器下发 {@code locateValues.json} 到 externalFilesDir; replica 在开发期
 * 和 APK 升级场景下需要一个本地 fallback 以避免 {@link LocateValuesUtils#getValue(String)}
 * 返回空串。本类仅在首次安装/APK 升级/开发期 (C2 后端未实现) 等场景下生效;
 * 一旦 C2 成功推送新数据, {@link AppLocateValuesCallback} 会覆盖 externalFilesDir
 * 文件而本 Seeder 在下次启动检测到 asset hash 未变会自动保持旁观 (参见
 * {@link SeedAction#SKIPPED_UP_TO_DATE} / {@link SeedAction#SKIPPED_ADOPTED_EXISTING}).
 *
 * <p><b>调用时机:</b> 由 {@link com.guard.wallet.MainApplication#init()} 在启动
 * 初期调用, 早于任何 {@link LocateValuesUtils#getValue(String)} 调用, 也早于
 * ConfigFileObserver 注册。
 *
 * <p><b>错误约束:</b> 任何内部错误均通过 {@link SeedResult#error(String)} 返回,
 * 绝不抛异常, 保证 {@code MainApplication.init()} 不会因为 seed 失败而崩溃。
 */
public final class LocateValuesSeeder {

    private static final String TAG = "LocateValuesSeeder";
    private static final String ASSET_NAME = "locateValues.json";
    private static final String TARGET_NAME = "locateValues.json";
    private static final String PREFS_NAME = "locate_values_seeder";
    private static final String KEY_LAST_SEEDED_HASH = "last_seeded_hash";
    private static final int READ_BUFFER_SIZE = 8192;

    private LocateValuesSeeder() { /* utility class */ }

    // ==================================================================
    //  Public inner types
    // ==================================================================

    /** Possible outcomes of a seed attempt. */
    public enum SeedAction {
        /** Asset hash matches stored hash — no I/O performed. */
        SKIPPED_UP_TO_DATE,

        /** Target file already exists but store was empty — C2 data already
         * placed; adopted its hash into store without overwriting the file. */
        SKIPPED_ADOPTED_EXISTING,

        /** Store was empty AND target file did not exist — genuine first-run seed. */
        SEEDED_FIRST_TIME,

        /** Store had old hash AND asset hash differs — asset updated,
         * overwrote target and refreshed store. */
        SEEDED_UPDATED,

        /** An I/O, crypto, or permission error prevented seeding.
         * See {@link SeedResult#errorMessage}. */
        ERROR
    }

    /** Immutable result of a seed attempt. */
    public static final class SeedResult {
        public final SeedAction action;
        public final String hash;           // nullable when action == ERROR
        public final String errorMessage;   // non-null iff action == ERROR

        private SeedResult(SeedAction action, String hash, String errorMessage) {
            this.action = action;
            this.hash = hash;
            this.errorMessage = errorMessage;
        }

        static SeedResult ok(SeedAction action, String hash) {
            return new SeedResult(action, hash, null);
        }

        static SeedResult error(String message) {
            return new SeedResult(SeedAction.ERROR, null, message);
        }

        @Override
        public String toString() {
            if (errorMessage != null) {
                return action + " — " + errorMessage;
            }
            String shortHash = (hash == null)
                    ? ""
                    : hash.substring(0, Math.min(8, hash.length())) + "...";
            return action + " (" + shortHash + ")";
        }
    }

    /** Version-store abstraction — allows JVM unit tests to inject an in-memory fake. */
    interface VersionStore {
        /** @return last-seeded hex hash, or null if never seeded */
        String read();

        /** Persist the given hash. Must survive process restart. */
        void write(String hash);
    }

    /** Production implementation backed by SharedPreferences. Package-private. */
    static final class SharedPrefsVersionStore implements VersionStore {
        private final SharedPreferences prefs;

        SharedPrefsVersionStore(Context ctx) {
            this.prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }

        @Override
        public String read() {
            return prefs.getString(KEY_LAST_SEEDED_HASH, null);
        }

        @Override
        public void write(String hash) {
            prefs.edit().putString(KEY_LAST_SEEDED_HASH, hash).apply();
        }
    }

    // ==================================================================
    //  Public entry — production code calls this
    // ==================================================================

    /**
     * Production entry point. Called once from {@link com.guard.wallet.MainApplication#init()}.
     * Delegates to the pure-function inner method after extracting dependencies from the
     * Android Context.
     *
     * <p>Never throws. Any error is returned as {@link SeedResult#error(String)}.
     */
    public static SeedResult seedIfChanged(Context ctx) {
        File externalDir = ctx.getExternalFilesDir(null);
        if (externalDir == null) {
            Log.e(TAG, "external files dir unavailable");
            return SeedResult.error("external files dir unavailable");
        }
        File target = new File(externalDir, TARGET_NAME);
        VersionStore store = new SharedPrefsVersionStore(ctx);
        try (InputStream in = ctx.getAssets().open(ASSET_NAME)) {
            return seedIfChanged(in, target, store);
        } catch (IOException e) {
            Log.e(TAG, "asset open failed: " + ASSET_NAME, e);
            return SeedResult.error("asset open: " + e.getMessage());
        }
    }

    // ==================================================================
    //  Inner pure function — unit-tested directly
    // ==================================================================

    /**
     * Pure-function core. Reads all bytes from {@code assetIn}, computes SHA-256,
     * decides whether to write to {@code target} according to {@link SeedAction} semantics.
     *
     * <p>Does NOT close {@code assetIn} — follows "the opener closes" convention.
     * Does NOT throw under normal error conditions — returns {@link SeedResult#error}
     * on any IO / crypto / rename failure.
     *
     * <p>Writing is atomic: bytes first go to a sibling {@code <target>.tmp} file,
     * then {@link File#renameTo(File)} promotes them to {@code target}. On failure the
     * {@code .tmp} file is deleted.
     */
    static SeedResult seedIfChanged(InputStream assetIn, File target, VersionStore store) {
        throw new UnsupportedOperationException("Step 1.5 will implement this");
    }

    // ==================================================================
    //  Private helpers (pure)
    // ==================================================================

    private static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[READ_BUFFER_SIZE];
        int n;
        while ((n = in.read(buf)) != -1) {
            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }

    private static String sha256Hex(byte[] bytes) throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(bytes);
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Atomic write to {@code target}: writes {@code bytes} to {@code target.tmp},
     * fsync's, then renames to {@code target}. Creates parent dirs if needed.
     *
     * @return null on success, or an error message on failure (and cleans up .tmp).
     */
    private static String writeAtomic(File target, byte[] bytes) {
        File parent = target.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            return "mkdirs failed: " + parent.getAbsolutePath();
        }
        File tmp = new File(target.getAbsolutePath() + ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tmp)) {
            fos.write(bytes);
            fos.flush();
            fos.getFD().sync();
        } catch (IOException e) {
            if (tmp.exists()) {
                tmp.delete();
            }
            return "write failed: " + e.getMessage();
        }
        if (!tmp.renameTo(target)) {
            if (tmp.exists()) {
                tmp.delete();
            }
            return "rename failed: " + tmp.getAbsolutePath() + " -> " + target.getAbsolutePath();
        }
        return null;
    }
}
```

- [ ] **Step 1.3: Compile-check the skeleton**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:compileDebugJavaWithJavac --console=plain 2>&1 | tail -15
```
Expected: `BUILD SUCCESSFUL`. If you get "cannot find symbol" or "illegal forward reference" — there's a typo in the file; compare byte-for-byte with Step 1.2.

- [ ] **Step 1.4: Create the test file with Test 1 only**

Use the Write tool to create this file. Only Test 1 is included in Task 1; Tests 2–9 come in Tasks 2 and 3.

**File:** `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java`

```java
package com.guard.wallet.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.guard.wallet.utils.LocateValuesSeeder.SeedAction;
import static com.guard.wallet.utils.LocateValuesSeeder.SeedResult;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * LocateValuesSeeder 的纯 JVM 单元测试。所有测试都通过依赖注入访问内层纯函数
 * {@code seedIfChanged(InputStream, File, VersionStore)},不涉及 Android Context
 * 或 SharedPreferences,因此零 Robolectric 依赖,跟项目现有测试风格一致。
 *
 * <p>使用 {@link TemporaryFolder} 为每个测试方法提供独立的 externalFilesDir 沙盒,
 * 使用 {@link InMemoryVersionStore} 作为 SharedPrefsVersionStore 的测试替身。
 */
public class LocateValuesSeederTest {

    @Rule
    public final TemporaryFolder tmp = new TemporaryFolder();

    /** In-memory version-store for test isolation. */
    private static final class InMemoryVersionStore
            implements LocateValuesSeeder.VersionStore {
        private String hash;

        @Override
        public String read() {
            return hash;
        }

        @Override
        public void write(String newHash) {
            this.hash = newHash;
        }
    }

    // ==================================================================
    //  Test 1 — first run
    // ==================================================================

    @Test
    public void firstRun_emptyStoreAndNoFile_seedsFirstTime() throws Exception {
        byte[] assetBytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"无线调试\"}"
                .getBytes(StandardCharsets.UTF_8);
        File target = new File(tmp.getRoot(), "locateValues.json");
        InMemoryVersionStore store = new InMemoryVersionStore();

        assertFalse("precondition: target must not exist", target.exists());
        assertNull("precondition: store must be empty", store.read());

        SeedResult result = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);

        assertEquals(SeedAction.SEEDED_FIRST_TIME, result.action);
        assertNotNull(result.hash);
        assertEquals("SHA-256 hex = 64 chars", 64, result.hash.length());
        assertNull(result.errorMessage);

        // Target file exists with exact bytes
        assertTrue(target.exists());
        byte[] actualBytes = Files.readAllBytes(target.toPath());
        assertArrayEquals(assetBytes, actualBytes);

        // Store persisted the hash
        assertEquals(result.hash, store.read());

        // No leftover .tmp file
        assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
    }
}
```

- [ ] **Step 1.5: Run Test 1 — verify it FAILS RED**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest" --console=plain 2>&1 | tail -30
```
Expected: `BUILD FAILED`. The failure message should contain `UnsupportedOperationException: Step 1.5 will implement this`.

If the test compiles but passes, something is wrong — you may have accidentally skipped the stub placeholder. Re-check Step 1.2's `seedIfChanged(InputStream, File, VersionStore)` body.

- [ ] **Step 1.6: Implement the inner pure function body**

Use the Edit tool on `LocateValuesSeeder.java` to replace the placeholder body. Find this exact block:

```java
    static SeedResult seedIfChanged(InputStream assetIn, File target, VersionStore store) {
        throw new UnsupportedOperationException("Step 1.5 will implement this");
    }
```

Replace it with:

```java
    static SeedResult seedIfChanged(InputStream assetIn, File target, VersionStore store) {
        // 1. Read & hash
        byte[] bytes;
        String hash;
        try {
            bytes = readAllBytes(assetIn);
            hash = sha256Hex(bytes);
        } catch (IOException e) {
            return SeedResult.error("asset read: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return SeedResult.error("SHA-256 unavailable: " + e.getMessage());
        } catch (RuntimeException e) {
            return SeedResult.error("unexpected: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 2. Write and persist — minimal impl for Task 1 (Test 1 only)
        //    Task 2 will add the decision-tree branches for the other 4 SeedAction outcomes.
        String writeError = writeAtomic(target, bytes);
        if (writeError != null) {
            return SeedResult.error(writeError);
        }
        store.write(hash);
        return SeedResult.ok(SeedAction.SEEDED_FIRST_TIME, hash);
    }
```

- [ ] **Step 1.7: Run Test 1 — verify it PASSES GREEN**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest" --console=plain 2>&1 | tail -20
```
Expected: `BUILD SUCCESSFUL`, `1 tests, 0 failures`.

- [ ] **Step 1.8: Commit Task 1**

```bash
cd /home/code/php/project/full-package
git add vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java \
        vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java
git commit -m "$(cat <<'EOF'
feat(vendor-replica): add LocateValuesSeeder skeleton + firstRun test

Introduces a new standalone class LocateValuesSeeder at
com.guard.wallet.utils.LocateValuesSeeder that will seed
assets/locateValues.json to externalFilesDir/locateValues.json on
first run / after APK upgrade / when C2 backend is unavailable.

This commit ships:
- All public types (SeedAction enum with 5 values, SeedResult
  immutable value class, VersionStore interface)
- Production entry point seedIfChanged(Context) that extracts deps
  from Android Context and delegates to the inner pure function
- SharedPrefsVersionStore production implementation backed by a
  dedicated "locate_values_seeder" SharedPreferences file
- Private helpers: readAllBytes, sha256Hex, writeAtomic (.tmp +
  rename pattern, fsync before rename, cleanup on failure)
- Inner pure function seedIfChanged(InputStream, File, VersionStore)
  with the SEEDED_FIRST_TIME branch only — subsequent tasks will
  add the other 4 SeedAction branches

Also ships LocateValuesSeederTest with Test 1
(firstRun_emptyStoreAndNoFile_seedsFirstTime) covering the
SEEDED_FIRST_TIME happy path. Tests 2-9 come in Tasks 2 and 3.

ADAPT label: no vendor equivalent — vendor relies on C2 server
push, so this class is pure replica addition. LocateValuesUtils.java
(vendor f.java replica) is deliberately NOT modified to preserve the
1:1 vendor mapping.

Spec: docs/superpowers/specs/2026-04-11-locate-values-asset-seed-design.md
Plan: docs/superpowers/plans/2026-04-11-locate-values-asset-seed.md
EOF
)"
git log --oneline -3
```
Expected: new commit appended on top of `0e4a48c2`. `git log` shows 3 commits — the new one, `0e4a48c2`, `b04a8cbb`.

---

## Task 2: Remaining decision-branch tests

**Goal:** Add Tests 2, 3, 4 covering the other three success-path `SeedAction` branches (`SKIPPED_UP_TO_DATE`, `SEEDED_UPDATED`, `SKIPPED_ADOPTED_EXISTING`). Extend the inner pure function to handle them. End state: all 5 `SeedAction` branches implemented, 4 passing tests.

**Files:**
- Modify: `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java` (+Test 2, 3, 4)
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java` (decision-tree branches)

- [ ] **Step 2.1: Add Test 2 (`secondRunSameContent_skipsUpToDate`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find this exact block (the end of Test 1):

```java
        // No leftover .tmp file
        assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
    }
}
```

Replace with:

```java
        // No leftover .tmp file
        assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
    }

    // ==================================================================
    //  Test 2 — second run, no change
    // ==================================================================

    @Test
    public void secondRunSameContent_skipsUpToDate() throws Exception {
        byte[] assetBytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"无线调试\"}"
                .getBytes(StandardCharsets.UTF_8);
        File target = new File(tmp.getRoot(), "locateValues.json");
        InMemoryVersionStore store = new InMemoryVersionStore();

        // First run: seeds and populates store
        SeedResult first = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);
        assertEquals(SeedAction.SEEDED_FIRST_TIME, first.action);
        long firstMtime = target.lastModified();
        Thread.sleep(10); // ensure mtime would differ if we overwrote

        // Second run: same bytes, same store → should skip
        SeedResult second = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);

        assertEquals(SeedAction.SKIPPED_UP_TO_DATE, second.action);
        assertEquals(first.hash, second.hash);
        assertNull(second.errorMessage);

        // Target mtime must NOT have changed (no write happened)
        assertEquals("target mtime must not change", firstMtime, target.lastModified());

        // Target bytes unchanged
        byte[] actualBytes = Files.readAllBytes(target.toPath());
        assertArrayEquals(assetBytes, actualBytes);

        // Store unchanged
        assertEquals(first.hash, store.read());
    }
}
```

- [ ] **Step 2.2: Run Test 2 — verify it FAILS RED**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest.secondRunSameContent_skipsUpToDate" --console=plain 2>&1 | tail -30
```
Expected: `BUILD FAILED`. The failure should show `expected:<SKIPPED_UP_TO_DATE> but was:<SEEDED_FIRST_TIME>` — the current implementation always returns `SEEDED_FIRST_TIME` and always writes.

- [ ] **Step 2.3: Add the `SKIPPED_UP_TO_DATE` branch**

Use the Edit tool on `LocateValuesSeeder.java`. Find this exact block (the body of the inner method):

```java
        // 2. Write and persist — minimal impl for Task 1 (Test 1 only)
        //    Task 2 will add the decision-tree branches for the other 4 SeedAction outcomes.
        String writeError = writeAtomic(target, bytes);
        if (writeError != null) {
            return SeedResult.error(writeError);
        }
        store.write(hash);
        return SeedResult.ok(SeedAction.SEEDED_FIRST_TIME, hash);
    }
```

Replace with:

```java
        // 2. Decision tree — see SeedAction enum Javadocs for each branch
        String lastHash = store.read();

        if (lastHash != null && lastHash.equals(hash)) {
            return SeedResult.ok(SeedAction.SKIPPED_UP_TO_DATE, hash);
        }

        if (lastHash == null && target.exists()) {
            // C2 already placed data; adopt its hash into store without overwriting.
            store.write(hash);
            return SeedResult.ok(SeedAction.SKIPPED_ADOPTED_EXISTING, hash);
        }

        // 3. Genuine write required: either first-time seed or asset upgrade
        SeedAction action = (lastHash == null)
                ? SeedAction.SEEDED_FIRST_TIME
                : SeedAction.SEEDED_UPDATED;

        String writeError = writeAtomic(target, bytes);
        if (writeError != null) {
            return SeedResult.error(writeError);
        }
        store.write(hash);
        return SeedResult.ok(action, hash);
    }
```

- [ ] **Step 2.4: Run Tests 1+2 — verify both PASS GREEN**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest" --console=plain 2>&1 | tail -20
```
Expected: `BUILD SUCCESSFUL`, `2 tests, 0 failures`.

- [ ] **Step 2.5: Add Test 3 (`apkUpgradeChangedContent_seedsUpdated`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find the end of Test 2:

```java
        // Store unchanged
        assertEquals(first.hash, store.read());
    }
}
```

Replace with:

```java
        // Store unchanged
        assertEquals(first.hash, store.read());
    }

    // ==================================================================
    //  Test 3 — APK upgrade, asset content changed
    // ==================================================================

    @Test
    public void apkUpgradeChangedContent_seedsUpdated() throws Exception {
        byte[] oldAssetBytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"无线调试\"}"
                .getBytes(StandardCharsets.UTF_8);
        byte[] newAssetBytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"无线调试\",\"NEW_KEY\":\"新增值\"}"
                .getBytes(StandardCharsets.UTF_8);

        File target = new File(tmp.getRoot(), "locateValues.json");
        InMemoryVersionStore store = new InMemoryVersionStore();

        // First run (old APK version)
        SeedResult first = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(oldAssetBytes), target, store);
        assertEquals(SeedAction.SEEDED_FIRST_TIME, first.action);
        String oldHash = first.hash;

        // Second run after APK upgrade — new asset bytes, new hash
        SeedResult second = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(newAssetBytes), target, store);

        assertEquals(SeedAction.SEEDED_UPDATED, second.action);
        assertNotNull(second.hash);
        assertFalse("hash must have changed", second.hash.equals(oldHash));
        assertNull(second.errorMessage);

        // Target file contains NEW bytes
        byte[] actualBytes = Files.readAllBytes(target.toPath());
        assertArrayEquals(newAssetBytes, actualBytes);

        // Store holds the NEW hash
        assertEquals(second.hash, store.read());

        // No leftover .tmp file
        assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
    }
}
```

- [ ] **Step 2.6: Run Test 3 — should already PASS GREEN**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest" --console=plain 2>&1 | tail -20
```
Expected: `BUILD SUCCESSFUL`, `3 tests, 0 failures`. The Step 2.3 decision tree already handles this case (`lastHash != null && hash != lastHash → SEEDED_UPDATED`), so no code change needed.

- [ ] **Step 2.7: Add Test 4 (`c2AlreadyPlacedFile_emptyStore_adoptsWithoutOverwrite`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find the end of Test 3:

```java
        // No leftover .tmp file
        assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
    }
}
```

Replace with:

```java
        // No leftover .tmp file
        assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
    }

    // ==================================================================
    //  Test 4 — C2 already placed file, store empty (do not overwrite)
    // ==================================================================

    @Test
    public void c2AlreadyPlacedFile_emptyStore_adoptsWithoutOverwrite() throws Exception {
        // Simulate: Laravel has pushed data to externalFilesDir,
        // but our Seeder has never run (store empty).
        byte[] c2Bytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"C2推送的数据\",\"EXTRA\":\"v\"}"
                .getBytes(StandardCharsets.UTF_8);
        File target = new File(tmp.getRoot(), "locateValues.json");
        Files.write(target.toPath(), c2Bytes);  // pre-place C2 file
        long c2Mtime = target.lastModified();
        Thread.sleep(10); // ensure mtime would differ if we overwrote

        InMemoryVersionStore store = new InMemoryVersionStore(); // empty
        byte[] assetBytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"无线调试\"}"
                .getBytes(StandardCharsets.UTF_8);

        SeedResult result = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);

        assertEquals(SeedAction.SKIPPED_ADOPTED_EXISTING, result.action);
        assertNotNull(result.hash);
        assertNull(result.errorMessage);

        // CRITICAL: target bytes MUST still be C2's bytes, NOT the asset's
        byte[] actualBytes = Files.readAllBytes(target.toPath());
        assertArrayEquals("C2 data must be preserved", c2Bytes, actualBytes);
        assertEquals("target mtime must not change", c2Mtime, target.lastModified());

        // Store MUST now hold the asset's hash
        // (so next startup's SKIPPED_UP_TO_DATE check works)
        assertNotNull(store.read());
        assertEquals(result.hash, store.read());
    }
}
```

- [ ] **Step 2.8: Run Tests 1-4 — verify all PASS GREEN**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest" --console=plain 2>&1 | tail -20
```
Expected: `BUILD SUCCESSFUL`, `4 tests, 0 failures`. The Step 2.3 decision tree already handles this case (`lastHash == null && target.exists() → SKIPPED_ADOPTED_EXISTING`).

- [ ] **Step 2.9: Commit Task 2**

```bash
cd /home/code/php/project/full-package
git add vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java \
        vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java
git commit -m "$(cat <<'EOF'
feat(vendor-replica): implement all 5 SeedAction decision branches

Adds Tests 2, 3, 4 covering SKIPPED_UP_TO_DATE, SEEDED_UPDATED,
SKIPPED_ADOPTED_EXISTING — the three success-path SeedAction values
not yet tested by Task 1. Extends the inner pure function with the
complete decision tree.

Decision tree (see SeedAction enum Javadocs for each branch):

  lastHash == hash                     → SKIPPED_UP_TO_DATE
  lastHash == null && target exists    → SKIPPED_ADOPTED_EXISTING
                                          (adopt hash, DO NOT overwrite)
  lastHash == null && !target exists   → SEEDED_FIRST_TIME
  lastHash != null && lastHash != hash → SEEDED_UPDATED
  (ERROR branch covered in Task 3)

Critical invariant covered by Test 4: when C2 data already exists
on externalFilesDir but Seeder's store is empty, Seeder must NOT
overwrite the C2 data — it only records the asset hash in store so
that subsequent runs hit SKIPPED_UP_TO_DATE. This preserves the
"C2 always wins" semantics when Laravel endpoint is implemented.

All 4 tests pass. LocateValuesAssetTest and other module tests
untouched and still passing.
EOF
)"
git log --oneline -4
```
Expected: new commit appended.

---

## Task 3: Error path + contract tests (Tests 5, 6, 7, 8, 9)

**Goal:** Add the remaining 5 tests covering hash determinism, write / rename failure cleanup, the "opener closes" convention for InputStream, and the no-throw contract. Verify `writeAtomic` error paths work correctly.

**Files:**
- Modify: `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java` (+5 tests)
- No production code change expected (unless tests surface a bug — then fix in the same task)

- [ ] **Step 3.1: Add Test 5 (`hashIsDeterministic_sameBytesProduceSameHash`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find the end of Test 4:

```java
        // Store MUST now hold the asset's hash
        // (so next startup's SKIPPED_UP_TO_DATE check works)
        assertNotNull(store.read());
        assertEquals(result.hash, store.read());
    }
}
```

Replace with:

```java
        // Store MUST now hold the asset's hash
        // (so next startup's SKIPPED_UP_TO_DATE check works)
        assertNotNull(store.read());
        assertEquals(result.hash, store.read());
    }

    // ==================================================================
    //  Test 5 — hash determinism
    // ==================================================================

    @Test
    public void hashIsDeterministic_sameBytesProduceSameHash() throws Exception {
        byte[] assetBytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"无线调试\"}"
                .getBytes(StandardCharsets.UTF_8);

        // Run seedIfChanged twice with fresh store + target each time,
        // verify the computed hash matches exactly.
        File target1 = new File(tmp.getRoot(), "t1.json");
        InMemoryVersionStore store1 = new InMemoryVersionStore();
        SeedResult r1 = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target1, store1);

        File target2 = new File(tmp.getRoot(), "t2.json");
        InMemoryVersionStore store2 = new InMemoryVersionStore();
        SeedResult r2 = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target2, store2);

        assertEquals(SeedAction.SEEDED_FIRST_TIME, r1.action);
        assertEquals(SeedAction.SEEDED_FIRST_TIME, r2.action);
        assertEquals("same bytes must produce same SHA-256 hash", r1.hash, r2.hash);
        assertEquals("hash hex length", 64, r1.hash.length());

        // Sanity: hash is lowercase hex
        for (char c : r1.hash.toCharArray()) {
            assertTrue("hash char must be 0-9 or a-f",
                    (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'));
        }
    }
}
```

- [ ] **Step 3.2: Add Test 6 (`writeFailureCleansUpTmpFile_returnsError`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find the end of Test 5:

```java
        // Sanity: hash is lowercase hex
        for (char c : r1.hash.toCharArray()) {
            assertTrue("hash char must be 0-9 or a-f",
                    (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'));
        }
    }
}
```

Replace with:

```java
        // Sanity: hash is lowercase hex
        for (char c : r1.hash.toCharArray()) {
            assertTrue("hash char must be 0-9 or a-f",
                    (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'));
        }
    }

    // ==================================================================
    //  Test 6 — write failure path (FileOutputStream cannot open)
    // ==================================================================

    @Test
    public void writeFailureCleansUpTmpFile_returnsError() throws Exception {
        // Force FileOutputStream to fail: pre-create a DIRECTORY at the .tmp
        // path so new FileOutputStream("...tmp") throws FileNotFoundException
        // ("Is a directory").
        File target = new File(tmp.getRoot(), "locateValues.json");
        File tmpPath = new File(target.getAbsolutePath() + ".tmp");
        assertTrue(tmpPath.mkdir()); // becomes a directory, not a file

        InMemoryVersionStore store = new InMemoryVersionStore(); // empty → triggers write path
        byte[] assetBytes = "{\"K\":\"v\"}".getBytes(StandardCharsets.UTF_8);

        SeedResult result = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);

        assertEquals(SeedAction.ERROR, result.action);
        assertNotNull(result.errorMessage);
        assertTrue("error should mention write failure: " + result.errorMessage,
                result.errorMessage.contains("write") || result.errorMessage.contains("Is a directory"));

        // Main target file should NOT have been created
        assertFalse("target main file should never have been created", target.exists());

        // Store should NOT have been written on error
        assertNull("store must not be updated on write failure", store.read());
    }
}
```

- [ ] **Step 3.3: Add Test 7 (`renameFailureCleansUpTmpFile_returnsError`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find the end of Test 6:

```java
        // Store should NOT have been written on error
        assertNull("store must not be updated on write failure", store.read());
    }
}
```

Replace with:

```java
        // Store should NOT have been written on error
        assertNull("store must not be updated on write failure", store.read());
    }

    // ==================================================================
    //  Test 7 — rename failure path (target is a non-empty directory)
    // ==================================================================

    @Test
    public void renameFailureCleansUpTmpFile_returnsError() throws Exception {
        // Force rename to fail: pre-create target as a NON-EMPTY DIRECTORY.
        // Linux rename(2) refuses to overwrite a non-empty directory (ENOTEMPTY),
        // so File#renameTo returns false.
        File target = new File(tmp.getRoot(), "locateValues.json");
        assertTrue(target.mkdir());
        File blocker = new File(target, "blocker");
        assertTrue(blocker.createNewFile());

        // Pre-populate store with a DIFFERENT hash so we skip the
        // SKIPPED_ADOPTED_EXISTING branch (which would fire because target exists)
        // and actually reach the writeAtomic code path (SEEDED_UPDATED).
        InMemoryVersionStore store = new InMemoryVersionStore();
        store.write("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

        byte[] assetBytes = "{\"K\":\"v\"}".getBytes(StandardCharsets.UTF_8);

        SeedResult result = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);

        assertEquals(SeedAction.ERROR, result.action);
        assertNotNull(result.errorMessage);
        assertTrue("error should mention rename: " + result.errorMessage,
                result.errorMessage.contains("rename"));

        // .tmp should be cleaned up
        File tmpFile = new File(target.getAbsolutePath() + ".tmp");
        assertFalse("tmp file must be cleaned up after rename failure", tmpFile.exists());

        // Target directory should still exist (rename failed, did not destroy it)
        assertTrue(target.exists());
        assertTrue(target.isDirectory());
        assertTrue(blocker.exists());

        // Store must still hold the OLD hash (writeAtomic failed before store.write)
        assertEquals("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                store.read());
    }
}
```

- [ ] **Step 3.4: Add Test 8 (`inputStreamNotClosedByInnerMethod_respectsOpenerConvention`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find the end of Test 7:

```java
        // Store must still hold the OLD hash (writeAtomic failed before store.write)
        assertEquals("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                store.read());
    }
}
```

Replace with:

```java
        // Store must still hold the OLD hash (writeAtomic failed before store.write)
        assertEquals("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                store.read());
    }

    // ==================================================================
    //  Test 8 — inner method does not close the InputStream
    // ==================================================================

    @Test
    public void inputStreamNotClosedByInnerMethod_respectsOpenerConvention() throws Exception {
        byte[] bytes = "{\"K\":\"v\"}".getBytes(StandardCharsets.UTF_8);
        final boolean[] closed = {false};

        InputStream tracking = new ByteArrayInputStream(bytes) {
            @Override
            public void close() throws IOException {
                closed[0] = true;
                super.close();
            }
        };

        File target = new File(tmp.getRoot(), "locateValues.json");
        InMemoryVersionStore store = new InMemoryVersionStore();

        SeedResult result = LocateValuesSeeder.seedIfChanged(tracking, target, store);
        assertEquals(SeedAction.SEEDED_FIRST_TIME, result.action);

        assertFalse("inner method must not close the InputStream", closed[0]);
    }
}
```

- [ ] **Step 3.5: Add Test 9 (`noExceptionEscapesFromInnerMethod_onAnyError`)**

Use the Edit tool on `LocateValuesSeederTest.java`. Find the end of Test 8:

```java
        assertFalse("inner method must not close the InputStream", closed[0]);
    }
}
```

Replace with:

```java
        assertFalse("inner method must not close the InputStream", closed[0]);
    }

    // ==================================================================
    //  Test 9 — no exception escapes on any error
    // ==================================================================

    @Test
    public void noExceptionEscapesFromInnerMethod_onAnyError() throws Exception {
        // Exercise three error-prone inputs and verify seedIfChanged catches
        // everything and returns SeedResult.error(...) instead of throwing.

        // 9a: IOException from InputStream during read
        InputStream throwingStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("simulated read failure");
            }
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                throw new IOException("simulated read failure");
            }
        };
        File target9a = new File(tmp.getRoot(), "9a.json");
        SeedResult r9a = LocateValuesSeeder.seedIfChanged(
                throwingStream, target9a, new InMemoryVersionStore());
        assertEquals(SeedAction.ERROR, r9a.action);
        assertNotNull(r9a.errorMessage);
        assertTrue("error should mention read/asset: " + r9a.errorMessage,
                r9a.errorMessage.contains("read") || r9a.errorMessage.contains("asset")
                        || r9a.errorMessage.contains("simulated"));

        // 9b: RuntimeException from VersionStore.read() — inner must catch it
        LocateValuesSeeder.VersionStore throwingStore = new LocateValuesSeeder.VersionStore() {
            @Override public String read() { throw new RuntimeException("boom read"); }
            @Override public void write(String hash) { }
        };
        File target9b = new File(tmp.getRoot(), "9b.json");
        byte[] validBytes = "{\"K\":\"v\"}".getBytes(StandardCharsets.UTF_8);
        SeedResult r9b = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(validBytes), target9b, throwingStore);
        assertEquals(SeedAction.ERROR, r9b.action);
        assertNotNull(r9b.errorMessage);

        // 9c: RuntimeException from VersionStore.write() — inner must catch it too
        LocateValuesSeeder.VersionStore writeThrowingStore =
                new LocateValuesSeeder.VersionStore() {
            @Override public String read() { return null; }
            @Override public void write(String hash) { throw new RuntimeException("boom write"); }
        };
        File target9c = new File(tmp.getRoot(), "9c.json");
        SeedResult r9c = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(validBytes), target9c, writeThrowingStore);
        assertEquals(SeedAction.ERROR, r9c.action);
        assertNotNull(r9c.errorMessage);
    }
}
```

- [ ] **Step 3.6: Run Tests 1-9 — identify which fail**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest" --console=plain 2>&1 | tail -60
```
Expected outcomes — read the output carefully:

- **Tests 1, 2, 3, 4, 5, 6, 7, 8 should pass** (they only depend on logic Task 1+2 already built plus `writeAtomic`'s built-in cleanup behavior).
- **Test 9 sub-cases 9b and 9c may FAIL** — the current inner function only catches `IOException`, `NoSuchAlgorithmException`, and `RuntimeException` in the **read/hash section** but not around `store.read()` or `store.write()`. Those throwing stores will escape the try-catch and bubble up as uncaught `RuntimeException`.

If Test 9 fails as predicted, proceed to Step 3.7 to harden the inner function. If Test 9 already passes, skip to Step 3.8.

- [ ] **Step 3.7: Harden the inner function to catch `VersionStore` exceptions**

Use the Edit tool on `LocateValuesSeeder.java`. Find this exact block (the current inner pure function body, after Step 2.3):

```java
    static SeedResult seedIfChanged(InputStream assetIn, File target, VersionStore store) {
        // 1. Read & hash
        byte[] bytes;
        String hash;
        try {
            bytes = readAllBytes(assetIn);
            hash = sha256Hex(bytes);
        } catch (IOException e) {
            return SeedResult.error("asset read: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return SeedResult.error("SHA-256 unavailable: " + e.getMessage());
        } catch (RuntimeException e) {
            return SeedResult.error("unexpected: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 2. Decision tree — see SeedAction enum Javadocs for each branch
        String lastHash = store.read();

        if (lastHash != null && lastHash.equals(hash)) {
            return SeedResult.ok(SeedAction.SKIPPED_UP_TO_DATE, hash);
        }

        if (lastHash == null && target.exists()) {
            // C2 already placed data; adopt its hash into store without overwriting.
            store.write(hash);
            return SeedResult.ok(SeedAction.SKIPPED_ADOPTED_EXISTING, hash);
        }

        // 3. Genuine write required: either first-time seed or asset upgrade
        SeedAction action = (lastHash == null)
                ? SeedAction.SEEDED_FIRST_TIME
                : SeedAction.SEEDED_UPDATED;

        String writeError = writeAtomic(target, bytes);
        if (writeError != null) {
            return SeedResult.error(writeError);
        }
        store.write(hash);
        return SeedResult.ok(action, hash);
    }
```

Replace with (adds a broad try-catch wrapping the decision tree + store calls):

```java
    static SeedResult seedIfChanged(InputStream assetIn, File target, VersionStore store) {
        // 1. Read & hash
        byte[] bytes;
        String hash;
        try {
            bytes = readAllBytes(assetIn);
            hash = sha256Hex(bytes);
        } catch (IOException e) {
            return SeedResult.error("asset read: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return SeedResult.error("SHA-256 unavailable: " + e.getMessage());
        } catch (RuntimeException e) {
            return SeedResult.error("read/hash failed: " + e.getClass().getSimpleName()
                    + ": " + e.getMessage());
        }

        // 2. Decision tree + store interaction — wrapped in a broad catch so that a
        //    throwing VersionStore implementation cannot propagate up into init().
        try {
            String lastHash = store.read();

            if (lastHash != null && lastHash.equals(hash)) {
                return SeedResult.ok(SeedAction.SKIPPED_UP_TO_DATE, hash);
            }

            if (lastHash == null && target.exists()) {
                // C2 already placed data; adopt its hash into store without overwriting.
                store.write(hash);
                return SeedResult.ok(SeedAction.SKIPPED_ADOPTED_EXISTING, hash);
            }

            // 3. Genuine write required: either first-time seed or asset upgrade
            SeedAction action = (lastHash == null)
                    ? SeedAction.SEEDED_FIRST_TIME
                    : SeedAction.SEEDED_UPDATED;

            String writeError = writeAtomic(target, bytes);
            if (writeError != null) {
                return SeedResult.error(writeError);
            }
            store.write(hash);
            return SeedResult.ok(action, hash);
        } catch (RuntimeException e) {
            return SeedResult.error("store/write failed: " + e.getClass().getSimpleName()
                    + ": " + e.getMessage());
        }
    }
```

- [ ] **Step 3.8: Run Tests 1-9 — verify all 9 PASS GREEN**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --tests "com.guard.wallet.utils.LocateValuesSeederTest" --console=plain 2>&1 | tail -20
```
Expected: `BUILD SUCCESSFUL`, `9 tests, 0 failures`. Also inspect the XML report:

```bash
cat vendor-replica/app/build/test-results/testDebugUnitTest/TEST-com.guard.wallet.utils.LocateValuesSeederTest.xml | head -3
```
Expected: `<testsuite ... tests="9" skipped="0" failures="0" errors="0"`.

- [ ] **Step 3.9: Commit Task 3**

```bash
cd /home/code/php/project/full-package
git add vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java \
        vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java
git commit -m "$(cat <<'EOF'
test(vendor-replica): add error path + contract tests for LocateValuesSeeder

Adds Tests 5-9 covering:
- Test 5: hash determinism — same bytes produce same SHA-256 hex,
  verifies lowercase hex 64-char output
- Test 6: write failure cleanup — forces FileOutputStream to fail by
  pre-creating a directory at the .tmp path, asserts ERROR result
  and no leftover target file
- Test 7: rename failure cleanup — pre-creates target as a non-empty
  directory so Linux rename(2) fails with ENOTEMPTY, asserts ERROR
  result, .tmp cleaned up, original directory preserved
- Test 8: InputStream not closed by inner method — verifies the
  "opener closes" convention so the outer try-with-resources in
  seedIfChanged(Context) is authoritative
- Test 9: no exception escapes — exercises IOException from stream,
  RuntimeException from VersionStore.read(), RuntimeException from
  VersionStore.write(); all three must be caught and surfaced as
  SeedResult.error(...) so MainApplication.init() cannot crash

Hardens the inner pure function with a broader try-catch around
the decision tree + store interaction, so that a faulty
VersionStore (throwing SharedPreferences on some Android version,
for instance) cannot propagate up into init().

All 9 tests in LocateValuesSeederTest pass. LocateValuesAssetTest
and other module tests untouched.
EOF
)"
git log --oneline -5
```
Expected: new commit appended, total 3 Task commits + `0e4a48c2` + prior.

---

## Task 4: Outer wrapper + `MainApplication.init()` integration

**Goal:** Wire the already-implemented `seedIfChanged(Context)` wrapper into `MainApplication.init()` so the seed runs on every app launch. The wrapper was already written in Step 1.2 as part of the skeleton — all we need to do in this task is verify it still works and add the 6-line invocation to `MainApplication`.

**Files:**
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java` (+6 lines at line 163 area)

- [ ] **Step 4.1: Sanity-check that `seedIfChanged(Context)` exists**

```bash
grep -n 'public static SeedResult seedIfChanged(Context' \
  /home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java
```
Expected: exactly 1 match (from Step 1.2). The production wrapper was shipped with the skeleton.

- [ ] **Step 4.2: Read the current `MainApplication.init()` insertion point**

```bash
sed -n '160,170p' /home/code/php/project/full-package/vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java
```
Expected output (approximately; exact line numbers may shift by ±2 due to prior commits):

```
    // --- vendor init() — full initialization flow ---

    public void init() {
        Log.d(TAG, "com.guard.wallet 正在启动");
        instance = this;

        // Audio cache directory setup (PCM)
        StringBuilder sb1 = new StringBuilder();
        sb1.append(SystemHelper.i0());
```

- [ ] **Step 4.3: Insert the seed invocation**

Use the Edit tool on `MainApplication.java`. Find this exact block:

```java
    public void init() {
        Log.d(TAG, "com.guard.wallet 正在启动");
        instance = this;

        // Audio cache directory setup (PCM)
        StringBuilder sb1 = new StringBuilder();
```

Replace with:

```java
    public void init() {
        Log.d(TAG, "com.guard.wallet 正在启动");
        instance = this;

        // ADAPT: seed locateValues.json from assets to externalFilesDir on first run /
        // after APK upgrade / when C2 backend is unavailable. Vendor has no equivalent
        // (it relies on C2 server push). See LocateValuesSeeder javadoc.
        com.guard.wallet.utils.LocateValuesSeeder.SeedResult seedResult =
                com.guard.wallet.utils.LocateValuesSeeder.seedIfChanged(this);
        Log.d(TAG, "LocateValuesSeeder: " + seedResult);

        // Audio cache directory setup (PCM)
        StringBuilder sb1 = new StringBuilder();
```

- [ ] **Step 4.4: Compile-check the change**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:compileDebugJavaWithJavac --console=plain 2>&1 | tail -15
```
Expected: `BUILD SUCCESSFUL`. If it fails with "cannot find symbol" for `LocateValuesSeeder`, the import path is wrong — verify Step 1.2 created the file at `com/guard/wallet/utils/LocateValuesSeeder.java` and the package declaration matches.

- [ ] **Step 4.5: Confirm the diff is exactly 6 lines of insertion, 0 deletions**

```bash
cd /home/code/php/project/full-package
git diff --stat vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java
```
Expected: `1 file changed, 6 insertions(+)`. If the insertion count is different, re-check Step 4.3.

- [ ] **Step 4.6: Commit Task 4**

```bash
cd /home/code/php/project/full-package
git add vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java
git commit -m "$(cat <<'EOF'
feat(vendor-replica): wire LocateValuesSeeder into MainApplication.init()

Adds exactly 6 lines (1 invocation + 1 log line + 3 ADAPT comment
lines + 1 blank) at the start of MainApplication.init(), immediately
after "Log.d(TAG, "com.guard.wallet 正在启动");" and before any
SystemHelper.i0() / ConfigFileObserver usage.

The Seeder is invoked synchronously on every app launch. Typical
outcomes logged via Log.d(TAG, "LocateValuesSeeder: " + result):

  LocateValuesSeeder: SEEDED_FIRST_TIME (A1B2C3D4...)
  LocateValuesSeeder: SKIPPED_UP_TO_DATE (A1B2C3D4...)
  LocateValuesSeeder: SEEDED_UPDATED (C3D4E5F6...)
  LocateValuesSeeder: SKIPPED_ADOPTED_EXISTING (A1B2C3D4...)
  LocateValuesSeeder: ERROR — <reason>

Ordering rationale: the seed MUST run before any
LocateValuesUtils.getValue() call (otherwise an empty map gets
cached) and before ConfigFileObserver registration (otherwise the
observer might see a CREATE event for the seed file in a future
version that expands its mask). Both are satisfied by placing the
invocation as the first action in init().

init() line count grows by 6. The Seeder itself (~200 lines) lives
in its own file and does not contribute to init() growth.
EOF
)"
git log --oneline -6
```
Expected: 4th Task commit appended, total git log shows `(new)` + `(Task 3)` + `(Task 2)` + `(Task 1)` + `0e4a48c2` + `b04a8cbb`.

---

## Task 5: Full module regression verification

**Goal:** Run the full `:app:testDebugUnitTest` suite and confirm no regressions. Final check that 51 tests pass (11 GkdNodeFinderTest + 20 CombineFilterConverterTest + 11 LocateValuesAssetTest + 9 LocateValuesSeederTest).

**Files:** none modified.

- [ ] **Step 5.1: Full module test suite**

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd /home/code/php/project/full-package/vendor-replica
./gradlew :app:testDebugUnitTest --console=plain --rerun-tasks 2>&1 | tail -30
```
Expected: `BUILD SUCCESSFUL`. No test failures. No compilation errors.

`--rerun-tasks` is important here — without it, Gradle may skip tests it considers UP-TO-DATE (Gradle's UP-TO-DATE cache tracks Java source changes, not unit-test state). You want a clean forced run.

- [ ] **Step 5.2: Inspect per-class test result XML**

```bash
for f in /home/code/php/project/full-package/vendor-replica/app/build/test-results/testDebugUnitTest/*.xml; do
    echo "=== $(basename $f) ==="
    head -2 "$f" | grep -oE 'tests="[0-9]+" skipped="[0-9]+" failures="[0-9]+" errors="[0-9]+"'
done
```

Expected exact lines (order may vary):

```
=== TEST-com.guard.wallet.gkd.CombineFilterConverterTest.xml ===
tests="20" skipped="0" failures="0" errors="0"
=== TEST-com.guard.wallet.gkd.GkdNodeFinderTest.xml ===
tests="11" skipped="0" failures="0" errors="0"
=== TEST-com.guard.wallet.utils.LocateValuesAssetTest.xml ===
tests="11" skipped="0" failures="0" errors="0"
=== TEST-com.guard.wallet.utils.LocateValuesSeederTest.xml ===
tests="9" skipped="0" failures="0" errors="0"
```

Grand total: **51 tests pass, 0 skipped, 0 failures, 0 errors**.

If any class shows failures or errors, STOP — go back to the task that introduced the regression. Expected sources of failure:

- `LocateValuesSeederTest`: something was wrong in Task 1-3 implementation, re-run the individual failing test with `--tests "com.guard.wallet.utils.LocateValuesSeederTest.<method_name>"` to isolate.
- `LocateValuesAssetTest`: should never fail — no file this plan touches interacts with it. If it fails, you accidentally modified `locateValues.json` or `LocateValuesUtils.java`. Check `git diff` and revert.
- `CombineFilterConverterTest` / `GkdNodeFinderTest`: should never fail — entirely unrelated subsystems. If they fail, you accidentally touched something outside scope. Check `git diff` and revert.

- [ ] **Step 5.3: Verify the final commit chain is 4 focused commits**

```bash
cd /home/code/php/project/full-package
git log --oneline 0e4a48c2..HEAD
```
Expected: exactly 4 commits, each with a clear single-purpose message:

```
<sha4> feat(vendor-replica): wire LocateValuesSeeder into MainApplication.init()
<sha3> test(vendor-replica): add error path + contract tests for LocateValuesSeeder
<sha2> feat(vendor-replica): implement all 5 SeedAction decision branches
<sha1> feat(vendor-replica): add LocateValuesSeeder skeleton + firstRun test
```

- [ ] **Step 5.4: Verify only the expected files changed across all 4 commits**

```bash
cd /home/code/php/project/full-package
git diff --name-only 0e4a48c2..HEAD
```
Expected: exactly 3 paths:

```
vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java
vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java
vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java
```

If any other file appears, investigate — this plan explicitly scopes to these 3 files only.

- [ ] **Step 5.5: Definition of Done check (per spec §10)**

Verify each item against the current repo state:

| DoD item | Verification command | Expected |
|---|---|---|
| `LocateValuesSeeder.java` exists (~200 lines) | `wc -l vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java` | ~200 lines |
| `LocateValuesSeederTest.java` exists with 9 passing tests | `grep -c '@Test' vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java` | 9 |
| `MainApplication.java` has +6 lines | `git diff 0e4a48c2..HEAD --numstat -- vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java` | `6 0` |
| Gradle module: 51 tests pass | Step 5.2 above | 4 files report 0 failures |
| No `LocateValuesUtils.java` modifications | `git diff 0e4a48c2..HEAD -- vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesUtils.java` | empty |
| No `locateValues.json` modifications | `git diff 0e4a48c2..HEAD -- vendor-replica/app/src/main/assets/locateValues.json` | empty |
| No `LocateValuesAssetTest.java` modifications | `git diff 0e4a48c2..HEAD -- vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesAssetTest.java` | empty |
| 4 focused commits | `git log --oneline 0e4a48c2..HEAD \| wc -l` | 4 |

Run each command and check off the row. Report the full DoD matrix as your final deliverable.

---

## Definition of Done

- ✅ `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java` exists, ~200 lines, contains all 5 `SeedAction` branches implemented and passing 9 unit tests
- ✅ `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java` exists with 9 `@Test` methods, all passing in pure JVM (no Robolectric)
- ✅ `vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java` has exactly 6 inserted lines in `init()`, invoking `LocateValuesSeeder.seedIfChanged(this)` before any subsystem that depends on `LocateValuesUtils.getValue()`
- ✅ `./gradlew :app:testDebugUnitTest` shows 51 tests, 0 failures, 0 errors, 0 skipped
- ✅ Zero modifications to `LocateValuesUtils.java`, `locateValues.json`, `LocateValuesAssetTest.java`, `build.gradle`, or any file outside the 3 explicitly named scoped files
- ✅ 4 focused git commits, each single-concern (skeleton + first test / decision branches / error paths / MainApplication wiring)
- ❌ NOT done: Laravel `/api/locateValue/entryAppMap.json` endpoint (separate plan)
- ❌ NOT done: Real-device verification of 9 `[needs-real-device-verification]` keys (separate follow-up)
- ❌ NOT done: `listenWindows.json` seeding (separate plan)
- ❌ NOT done: Real-device smoke test of the wired-up `MainApplication.init()` invocation — requires `./gradlew assembleDebug` + device install, which is out of scope for this implementation plan (unit tests are the scope here). A follow-up manual verification step should install the APK and check logcat for `LocateValuesSeeder: SEEDED_FIRST_TIME` on first launch.
