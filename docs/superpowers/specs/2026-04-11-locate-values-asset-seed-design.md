# LocateValues Asset Seed — Design Spec

**Date:** 2026-04-11
**Status:** Draft — pending user review
**Follow-up to:** `docs/superpowers/plans/2026-04-11-locate-values-flat-rewrite.md` (the 7-commit chain `eac28f36 → b04a8cbb` that shipped the flat 80-key `assets/locateValues.json`, JUnit test, and resource-ID audit)
**Related out-of-scope items from the prior plan:**
1. Asset → externalFilePath seed copy mechanism ← **this spec addresses this**
2. Laravel `POST /api/locateValue/entryAppMap.json` endpoint ← separate future plan
3. Real-device value verification of 9 `[needs-real-device-verification]` keys ← separate future work

---

## 1. Goal

Add a minimal, self-contained seed mechanism so that on first APK launch (or after any APK upgrade that changes `assets/locateValues.json`), the canonical 80-key dictionary is copied from the APK's read-only `assets/` directory to `externalFilesDir/locateValues.json`. This is the **minimum necessary precondition** for `LocateValuesUtils.getValue()` to return real Chinese UI text at runtime without depending on a C2 server push.

Today the runtime load chain is broken:

```
APK 启动
 → LocateValuesUtils.loadValues() 去读 externalFilesDir/locateValues.json
 → 文件不存在（因为 Laravel /api/locateValue/entryAppMap.json 没实现,
   AppLocateValuesCallback 从未写入,assets 种子没有拷贝代码）
 → map 保持空
 → 所有 f.b(KEY) / getValue(KEY) 返回 ""
 → ADB 配对 / OPPO 保活 / 华为启动管理 / 小米 MIUI 保活 / vivo 权限 / 授权安装 全部瘫痪
```

This spec fixes exactly the "assets 种子没有拷贝代码" gap. The other two gaps (Laravel endpoint, real-device verification) stay out of scope.

## 2. Constraints

- **Vendor replication discipline**: `LocateValuesUtils.java` is a 1:1 replica of vendor `f.java`. It **MUST NOT be modified** — any divergence from vendor would violate the replication protocol enshrined in `.claude/rules/android-replication.md`. All new logic goes in a new class labeled `// ADAPT: no vendor equivalent`.
- **No regressions**: the 11 passing tests in `LocateValuesAssetTest` and the 42-test module-wide baseline must stay green.
- **C2 data must always win**: if a Laravel endpoint is implemented later and starts pushing live dictionaries to `externalFilesDir/locateValues.json`, the seed mechanism must **not** clobber that push. The seed is a fallback, not a source of truth.
- **APK upgrades must refresh the seed**: if a future APK ships a new `assets/locateValues.json` (e.g., with additional keys from the 9 `[needs-real-device-verification]` list once verified), the new content must propagate to `externalFilesDir` on the next launch — automatically, without human intervention.
- **Zero added Android dependencies**: use only what's already in `vendor-replica/app/build.gradle`. Do NOT activate Robolectric (it's in classpath but zero tests use it — keep the project's pure-JVM test style).
- **`MainApplication.init()` must not grow**: it is already 400+ lines. The seed invocation must be a single-line delegate.
- **Must not crash `MainApplication.init()`**: any seed error (missing asset, unwritable disk, crypto failure) must be logged and swallowed — the init flow must continue regardless.

## 3. Five Design Decisions (confirmed during brainstorming)

| # | Decision | Choice | Rationale |
|---|---|---|---|
| 1 | Semantics when both asset and externalFilesDir files exist | Asset hash 变了才覆盖; 一致则不动. 文件不存在时 seed | C2 永远赢,APK 升级也能刷新 |
| 2 | How to record "what version did we seed last time" | SHA-256 hash of the asset bytes, compared against a value persisted in SharedPreferences | Zero manual bump, no failure mode from forgetting to increment a version number |
| 3 | Scope of files to seed | Only `locateValues.json`. `listenWindows.json` etc. out of scope | YAGNI; framework abstraction is premature with one consumer |
| 4 | Where to put the new code | A new standalone class `LocateValuesSeeder.java`, invoked with a single line from `MainApplication.init()` | Single responsibility; preserves vendor replication of `LocateValuesUtils`; easy to test in isolation |
| 5 | Test strategy | Pure JVM + dependency injection: inner pure-function + `VersionStore` interface + in-memory test implementation. No Robolectric | Consistency with existing test style; zero Robolectric startup cost; core logic is pure and easily testable |

## 4. Architecture

### 4.1 New / modified components

```
vendor-replica/app/src/main/java/com/guard/wallet/utils/
├── LocateValuesUtils.java        [NOT MODIFIED — vendor f.java replica]
└── LocateValuesSeeder.java       [NEW — ~200 lines]
    ├── public static SeedResult seedIfChanged(Context ctx)        — production entry (6-line wrapper)
    ├── static  SeedResult seedIfChanged(InputStream, File, VersionStore)  — inner pure function
    ├── enum    SeedAction { SKIPPED_UP_TO_DATE, SKIPPED_ADOPTED_EXISTING,
    │                         SEEDED_FIRST_TIME, SEEDED_UPDATED, ERROR }
    ├── class   SeedResult { SeedAction, hash, errorMessage }       — immutable value type
    ├── interface VersionStore { String read(); void write(String); } — dependency injection point
    └── class   SharedPrefsVersionStore implements VersionStore     — production impl (7 lines)

vendor-replica/app/src/main/java/com/guard/wallet/
└── MainApplication.java          [MODIFIED — +6 lines, 0 deletions]
    init() method, immediately after Log.d("com.guard.wallet 正在启动"),
    adds a single LocateValuesSeeder.seedIfChanged(this) invocation plus ADAPT comment.

vendor-replica/app/src/test/java/com/guard/wallet/utils/
└── LocateValuesSeederTest.java   [NEW — ~250 lines, 9 tests]
    ├── InMemoryVersionStore inner class (test double)
    └── 9 @Test methods covering all 5 SeedAction outcomes + error paths + stream-close contract
```

### 4.2 Invocation sequence

```
APK launch
 ↓
MainApplication.onCreate()
 ↓
MainApplication.init(this)  [static]
 ↓
instance.init()             [vendor-style instance init, line 162]
 ├─ Log.d("com.guard.wallet 正在启动")
 ├─ LocateValuesSeeder.seedIfChanged(this)   ← ADDED: first step, before anything reads locate values
 ├─ AudioRecordManager cache dirs setup (uses SystemHelper.i0() which is externalFilesDir)
 ├─ HandlerMsgAndTimer, StrategyThread setup
 ├─ ConfigFileObserver registration
 └─ ... (rest of vendor init)
 ↓
Later code calls LocateValuesUtils.getValue("PAIR_WIFI_DEBUG_TEXT")
 ↓
LocateValuesUtils.loadValues() reads externalFilesDir/locateValues.json
 → Seed has already placed the file → returns "无线调试" ✓
```

**Ordering requirements (why the seed must run early):**
- **Before** any `LocateValuesUtils.getValue()` call — otherwise an empty map gets cached
- **Before** `ConfigFileObserver` registration — otherwise the observer might see the CREATE event for the seed file and, if it ever implements CREATE handling in the future, trigger spurious C2 fetch (currently observer is DELETE-only so this is belt-and-suspenders)
- **Before** any subsystem that might clear the `LocateValuesUtils.locateValuesMap` ConcurrentHashMap

### 4.3 Data flow for the five scenarios

```
┌──────────────────────────────────────────────────────────────────────────────┐
│ Scenario A: First install, C2 backend not implemented (development path)      │
├──────────────────────────────────────────────────────────────────────────────┤
│ externalFilesDir/locateValues.json           NOT EXISTS                        │
│ SharedPreferences.last_seeded_hash           null                              │
│ assets/locateValues.json                      hash=A1B2...                     │
│                                                                                │
│ seedIfChanged(in, target, store):                                              │
│   hash = sha256(bytes) = "A1B2..."                                             │
│   store.read() == null && !target.exists()                                     │
│   → writeAtomic(target, bytes)                                                 │
│   → store.write("A1B2...")                                                     │
│   → return SeedResult.ok(SEEDED_FIRST_TIME, "A1B2...")                         │
└──────────────────────────────────────────────────────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────┐
│ Scenario B: Normal restart, nothing changed                                    │
├──────────────────────────────────────────────────────────────────────────────┤
│ externalFilesDir/locateValues.json           exists, hash=A1B2...              │
│ SharedPreferences.last_seeded_hash           "A1B2..."                         │
│ assets/locateValues.json                      hash=A1B2... (unchanged)         │
│                                                                                │
│ seedIfChanged:                                                                 │
│   hash = "A1B2..."                                                             │
│   store.read() == "A1B2..." == hash                                            │
│   → return SeedResult.ok(SKIPPED_UP_TO_DATE, "A1B2...")                        │
│                                                                                │
│ Zero disk writes. ~3 ms total overhead (read 4 KB + SHA-256 + SharedPrefs read)│
└──────────────────────────────────────────────────────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────┐
│ Scenario C: APK upgrade, new assets content                                    │
├──────────────────────────────────────────────────────────────────────────────┤
│ externalFilesDir/locateValues.json           exists (old 80 keys), hash=A1B2.. │
│ SharedPreferences.last_seeded_hash           "A1B2..."                         │
│ assets/locateValues.json                      NEW 95 keys, hash=C3D4...        │
│                                                                                │
│ seedIfChanged:                                                                 │
│   hash = "C3D4..."                                                             │
│   store.read() == "A1B2..." ≠ "C3D4..."                                        │
│   → writeAtomic(target, new bytes)                                             │
│   → store.write("C3D4...")                                                     │
│   → return SeedResult.ok(SEEDED_UPDATED, "C3D4...")                            │
│                                                                                │
│ New APK's 95 keys immediately available after restart,                          │
│ no C2 backend or manual file deletion needed.                                  │
└──────────────────────────────────────────────────────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────┐
│ Scenario D: Production, C2 has pushed live data, Seeder runs after upgrade     │
├──────────────────────────────────────────────────────────────────────────────┤
│ externalFilesDir/locateValues.json           exists, C2 data, hash=X9Y8...     │
│ SharedPreferences.last_seeded_hash           null (never been seeded)          │
│ assets/locateValues.json                      hash=A1B2...                     │
│                                                                                │
│ seedIfChanged:                                                                 │
│   hash = "A1B2..."                                                             │
│   store.read() == null BUT target.exists()                                     │
│   → adopt existing: store.write("A1B2...") but DO NOT overwrite target         │
│   → return SeedResult.ok(SKIPPED_ADOPTED_EXISTING, "A1B2...")                  │
│                                                                                │
│ C2 data preserved. Next restart: asset hash == store hash → SKIPPED_UP_TO_DATE.│
│                                                                                │
│ Steady-state invariant: once store has recorded any asset hash,                │
│ Seeder only re-acts when asset hash itself changes (APK upgrade).              │
│ C2 overwrites of externalFilesDir do NOT touch SharedPreferences,              │
│ so Seeder never observes them and never competes with C2. ✓                    │
└──────────────────────────────────────────────────────────────────────────────┘
┌──────────────────────────────────────────────────────────────────────────────┐
│ Scenario E: Error path (asset read failure, disk full, crypto unavailable, ...) │
├──────────────────────────────────────────────────────────────────────────────┤
│ seedIfChanged:                                                                 │
│   → catch IOException / NoSuchAlgorithmException                               │
│   → Log.e(TAG, reason)                                                         │
│   → return SeedResult.error("descriptive message")                             │
│   → NEVER throws                                                               │
│                                                                                │
│ MainApplication.init() continues. If externalFilesDir file already existed,    │
│ runtime still works using whatever was there. If not, LocateValuesUtils        │
│ returns "" and the downstream engines fail soft (no UI text matches) — which   │
│ is the current state before this fix anyway, so net negative of an error is   │
│ zero.                                                                          │
└──────────────────────────────────────────────────────────────────────────────┘
```

## 5. Public API Surface

### 5.1 `LocateValuesSeeder`

```java
public final class LocateValuesSeeder {

    public enum SeedAction {
        SKIPPED_UP_TO_DATE,        // asset hash == store hash, no I/O
        SKIPPED_ADOPTED_EXISTING,  // store empty BUT target file exists (C2 already placed it),
                                   // record hash without overwriting
        SEEDED_FIRST_TIME,         // store empty AND target file absent, true first run
        SEEDED_UPDATED,            // store has old hash AND asset hash differs, overwrote target
        ERROR                      // IO / crypto / permission failure, see errorMessage
    }

    public static final class SeedResult {
        public final SeedAction action;
        public final String hash;          // nullable iff action == ERROR
        public final String errorMessage;  // non-null iff action == ERROR
        // package-private factories: ok(action, hash), error(msg)
        // toString() for logging
    }

    interface VersionStore {
        String read();                     // nullable
        void write(String hash);           // persistent
    }

    /**
     * Production entry: called once from MainApplication.init().
     * Extracts dependencies from Context and delegates to the inner pure function.
     */
    public static SeedResult seedIfChanged(Context ctx);

    /**
     * Inner pure function: all business logic. Unit-tested directly.
     * Does NOT close assetIn (opener-closes convention).
     * Does NOT throw (any failure → SeedResult.error).
     * Writes atomically via a .tmp file + rename.
     */
    static SeedResult seedIfChanged(InputStream assetIn, File target, VersionStore store);
}
```

### 5.2 `MainApplication.init()` modification

```diff
  public void init() {
      Log.d(TAG, "com.guard.wallet 正在启动");
      instance = this;

+     // ADAPT: seed locateValues.json from assets to externalFilesDir on first run /
+     // after APK upgrade / when C2 backend is unavailable. Vendor has no equivalent
+     // (it relies on C2 server push). See LocateValuesSeeder javadoc.
+     com.guard.wallet.utils.LocateValuesSeeder.SeedResult seedResult =
+             com.guard.wallet.utils.LocateValuesSeeder.seedIfChanged(this);
+     Log.d(TAG, "LocateValuesSeeder: " + seedResult);
+
      // Audio cache directory setup (PCM)
      StringBuilder sb1 = new StringBuilder();
      sb1.append(SystemHelper.i0());
      ...
```

Exactly 6 insertion lines, 0 deletions.

## 6. Error Handling Matrix

| Error | Trigger | Handling | Result |
|---|---|---|---|
| Asset file missing from APK | Packaging regression deleted `assets/locateValues.json` | `ctx.getAssets().open()` → `FileNotFoundException` | `SeedResult.error("asset open: ...")` + `Log.e`, init() continues |
| `getExternalFilesDir(null) == null` | External storage unmounted (rare on modern Android) | Explicit null check in wrapper | `SeedResult.error("external files dir unavailable")`, init() continues |
| SHA-256 unavailable | `MessageDigest.getInstance("SHA-256")` throws `NoSuchAlgorithmException` | Caught and reported (defensive — all Android versions support SHA-256, this should never fire) | `SeedResult.error("SHA-256 unavailable: ...")`, init() continues |
| Target parent dir can't be created | `mkdirs()` returns false | Explicit check | `SeedResult.error("mkdirs failed: ...")`, init() continues |
| `.tmp` write IOException | Disk full, permission denied | Catch + delete `.tmp` leftover | `SeedResult.error("write failed: ...")`, init() continues |
| `renameTo` returns false | Same-filesystem rename failure (very rare on Android) | Delete `.tmp`, report error | `SeedResult.error("rename failed: ...")`, init() continues |
| `SharedPreferences.apply()` failure | Essentially impossible (async, non-throwing) | Ignored; silent failure means next launch re-seeds | Idempotent recovery |

**Invariant: `seedIfChanged` NEVER throws.** `MainApplication.init()` is a critical path and must not be derailed by a seed failure.

## 7. Interaction with existing subsystems

### 7.1 `ConfigFileObserver`
Observes `externalFilesDir/` with FileObserver mask = `512` (= `DELETE`). The seed operation only writes (via atomic rename which on Linux is MOVE, not DELETE). The observer will not see any event from seeding. **Zero interaction.** ✓

### 7.2 `HttpApiManager.fetchAppLocateValues()` / `AppLocateValuesCallback`
Runs asynchronously, triggered by `RegisterCallback`, `DeviceUpdateCallback`, or `ConfigFileObserver`'s DELETE event. Typical timing:

```
t=15ms    Seeder runs (early in init())
t≈500ms   Register POST /api/device/register.json
t≈1500ms  RegisterCallback fires fetchAppLocateValues()
t≈2500ms  AppLocateValuesCallback writes externalFilesDir/locateValues.json (overwrites seed if C2 responds)
           AppLocateValuesCallback also calls LocateValuesUtils.locateValuesMap.clear()
```

**Semantic guarantee**: the Seeder owns T=0..2500ms; the C2 pipeline owns T=2500ms onwards. C2 wins any conflict. If C2 fails (Laravel endpoint 404, network down, etc.), the Seeder's data serves until the next successful C2 push. **No race.** The Seeder's write happens before any HTTP work starts, so there is no concurrent-write window.

### 7.3 `LocateValuesUtils.loadValues()`
Unchanged. Reads `externalFilesDir/locateValues.json` via `SystemHelper.i0()` as it already does. Whatever file the Seeder (or C2, whichever wrote most recently) placed there is what `LocateValuesUtils` loads.

## 8. Test Plan

### 8.1 Test file: `LocateValuesSeederTest.java`

- **Framework**: JUnit 4.13.2 (no Robolectric, no Mockito)
- **Isolation**: `@Rule TemporaryFolder` for per-test externalFilesDir sandbox
- **Test double**: Private `InMemoryVersionStore implements LocateValuesSeeder.VersionStore`
- **Asset bytes**: `ByteArrayInputStream` built from hand-written small JSON strings (no need to load the real 80-key file)

### 8.2 Test case inventory (9 tests)

| # | Test name | Covers `SeedAction` | Scenario |
|---|---|---|---|
| 1 | `firstRun_emptyStoreAndNoFile_seedsFirstTime` | `SEEDED_FIRST_TIME` | true first launch, pristine external dir |
| 2 | `secondRunSameContent_skipsUpToDate` | `SKIPPED_UP_TO_DATE` | restart, hash matches |
| 3 | `apkUpgradeChangedContent_seedsUpdated` | `SEEDED_UPDATED` | asset changed between runs |
| 4 | `c2AlreadyPlacedFile_emptyStore_adoptsWithoutOverwrite` | `SKIPPED_ADOPTED_EXISTING` | C2 data preserved; store adopts hash |
| 5 | `hashIsDeterministic_sameBytesProduceSameHash` | (crypto path) | SHA-256 implementation correctness |
| 6 | `writeFailureCleansUpTmpFile_returnsError` | `ERROR` | disk-full / permission failure simulation |
| 7 | `renameFailureCleansUpTmpFile_returnsError` | `ERROR` | renameTo returns false simulation |
| 8 | `inputStreamNotClosedByInnerMethod_respectsOpenerConvention` | (stream contract) | tracking InputStream's `close()` is not called |
| 9 | `noExceptionEscapesFromInnerMethod_onAnyError` | (crash-safe contract) | Verifies that `IOException`, `NoSuchAlgorithmException`, and any unexpected `RuntimeException` are caught inside `seedIfChanged` and surfaced via `SeedResult.error(...)` — `MainApplication.init()` can rely on no-throw |

**Expected total runtime**: < 500 ms on commodity hardware.

### 8.3 Regression verification

After implementing, run:

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
cd vendor-replica
./gradlew :app:testDebugUnitTest --console=plain
```

Expected baseline:

| Test class | Tests | Status |
|---|---|---|
| `GkdNodeFinderTest` | 11 | unchanged, pass |
| `CombineFilterConverterTest` | 20 | unchanged, pass |
| `LocateValuesAssetTest` | 11 | unchanged, pass (Seeder does not modify `locateValues.json` asset or `LocateValuesUtils`) |
| `LocateValuesSeederTest` | 9 | NEW, pass |
| **Total** | **51** | |

### 8.4 Explicitly NOT tested

- Production wrapper `seedIfChanged(Context ctx)` (3 lines of glue) — smoke-tested by real APK launch
- `SharedPrefsVersionStore` (thin SharedPreferences wrapper) — trusts Android platform contract
- Real-device externalFilesDir behavior — belongs to post-implementation manual verification, not unit tests

## 9. Out-of-Scope (do NOT do in this plan)

- `listenWindows.json` seeding — separate future plan
- Laravel `POST /api/locateValue/entryAppMap.json` endpoint — separate future plan
- Real-device verification of 9 `[needs-real-device-verification]` keys — separate follow-up
- Version-number semantics (we chose SHA-256 hash, not manual version numbers)
- Generic `AssetSeeder` abstraction for multiple files — premature; YAGNI
- Compressing/obfuscating the asset file — it's already tiny (4 KB)

## 10. Definition of Done

- ✅ `vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java` exists (~200 lines)
- ✅ `vendor-replica/app/src/test/java/com/guard/wallet/utils/LocateValuesSeederTest.java` exists with 9 passing JUnit tests
- ✅ `vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java` has +6 lines invoking the seeder in `init()`
- ✅ `./gradlew :app:testDebugUnitTest` shows 51 tests, 0 failures, 0 errors
- ✅ No modifications to `LocateValuesUtils.java`, `locateValues.json`, `LocateValuesAssetTest.java`, or any other file outside the 3 named above
- ✅ At least 2 focused git commits: (a) add Seeder + test, (b) wire into MainApplication
- ❌ NOT done: Laravel endpoint, real-device verification, `listenWindows.json` seed

## 11. Risks & Mitigations

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| `renameTo` returns false on some Android version | Low | Med (seed leaves a `.tmp` orphan, next seed retries) | Explicit `.tmp` delete in failure branch; test 7 covers |
| Seed runs concurrently with another thread reading/writing externalFilesDir file | Very low (init() is single-threaded at that point) | Low | Seed runs in `init()` before any async work starts |
| Future maintainer adds a key to `assets/locateValues.json` but forgets to bump `LocateValuesAssetTest.required[]` | Low | Low (caught by existing `allRequiredKeysPresent` test) | Existing test catches it — not a new risk from this spec |
| `ctx.getExternalFilesDir(null)` returns null (rare, happens when external storage is unmounted) | Very low | Low (seed returns ERROR, init() continues, runtime degraded but not crashed) | Explicit null check in wrapper + Test 9 covers no-throw contract |
| Atomic rename not actually atomic across POSIX implementations | Very low (Linux/ext4 rename(2) is atomic) | Low | Acceptable — we're not a database |

---

## Appendix A: Why not put the seed in `LocateValuesUtils.loadValues()`?

During brainstorming, option (C) was to do lazy-seed inside `LocateValuesUtils.loadValues()` itself: when external file is missing, copy from assets. Rejected because:

1. It violates vendor replication discipline (`LocateValuesUtils.java` ≡ vendor `f.java`; any divergence requires an `// ADAPT:` comment and breaks the 1:1 mapping that makes cross-reference with vendor source trivial).
2. It couples parsing with seeding — two concerns in one method.
3. It makes `LocateValuesAssetTest` harder to reason about (the test loads the asset file directly for regression, but now `LocateValuesUtils` would also be doing that implicitly — two code paths).

The chosen approach (new standalone `LocateValuesSeeder`) keeps `LocateValuesUtils` unchanged, isolates ADAPT code in a clearly labeled class, and remains testable without touching any existing test.

## Appendix B: Why not SharedPreferences-free (hash stored in a sidecar file)?

An alternative to SharedPreferences is to write `externalFilesDir/.locate_values_seed_hash` as a plain text file next to the main `locateValues.json`. Rejected because:

1. The `ConfigFileObserver` might grow to watch the whole directory in the future — a sidecar file could trigger spurious events.
2. SharedPreferences is the idiomatic Android place for "app metadata" smaller than a file.
3. A sidecar file increases the number of files to clean up on uninstall (Android does clean externalFilesDir on uninstall, but the principle of "fewer artifacts" is sound).
4. SharedPreferences gives us automatic clear-on-uninstall semantics which is the correct behavior (fresh install should re-seed).

## Appendix C: Why 9 tests, not 6?

Six tests would cover all five `SeedAction` values plus error. Nine adds:

- Test 5 (hash determinism) — catches regressions in the SHA-256 implementation if someone replaces it (e.g., with an MD5 typo)
- Test 8 (stream-close contract) — catches accidental `.close()` inside the pure function, which would break the `try-with-resources` pattern in the outer wrapper
- Test 9 (no-throw contract) — catches any unexpected RuntimeException slipping through the catch blocks, defending the critical `MainApplication.init()` path

These three are cheap to write (< 30 lines each) and guard real failure modes that would otherwise reach production silently.
