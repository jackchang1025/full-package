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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * LocateValuesSeeder 的纯 JVM 单元测试。所有测试都通过依赖注入访问内层纯函数
 * {@code seedIfChanged(InputStream, File, VersionStore)}, 不涉及 Android Context
 * 或 SharedPreferences, 因此零 Robolectric 依赖, 跟项目现有测试风格一致。
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
        Thread.sleep(10); // 10ms is sufficient on Linux tmpfs (ms-resolution mtime);
                          // if this flakes on CI, increase to 1100ms for second-precision FS

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
        assertNotEquals("hash must have changed after content change", oldHash, second.hash);
        assertNull(second.errorMessage);

        // Target file contains NEW bytes
        byte[] actualBytes = Files.readAllBytes(target.toPath());
        assertArrayEquals(newAssetBytes, actualBytes);

        // Store holds the NEW hash
        assertEquals(second.hash, store.read());

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
        Thread.sleep(10); // 10ms is sufficient on Linux tmpfs (ms-resolution mtime);
                          // if this flakes on CI, increase to 1100ms for second-precision FS

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

    // ==================================================================
    //  Test 4b — store has hash but file was manually deleted (edge case)
    // ==================================================================

    /**
     * Decision-tree fourth-branch coverage: when the store carries a hash but
     * the target file has been manually deleted (e.g., {@code adb shell rm}
     * during development, or user-level cleanup), the seeder must re-seed.
     *
     * <p>The current behavior is to fall through to the write path with
     * {@code SEEDED_UPDATED} as the action label. The label is slightly
     * imprecise (nothing was "updated"; the file was re-created), but the
     * behavior is correct and observable — a future enhancement could
     * introduce a dedicated {@code SEEDED_RECOVERED} action value, but that
     * is a spec change outside this task's scope.
     */
    @Test
    public void storeHasHashButFileManuallyDeleted_reseeds() throws Exception {
        byte[] assetBytes = "{\"PAIR_WIFI_DEBUG_TEXT\":\"无线调试\"}"
                .getBytes(StandardCharsets.UTF_8);
        File target = new File(tmp.getRoot(), "locateValues.json");
        InMemoryVersionStore store = new InMemoryVersionStore();

        // First run: normal seed, populates store + target file
        SeedResult first = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);
        assertEquals(SeedAction.SEEDED_FIRST_TIME, first.action);
        assertTrue(target.exists());
        assertNotNull(store.read());

        // Simulate user / adb manually deleting the file, store remains intact
        assertTrue("precondition: target must be deletable", target.delete());
        assertFalse("precondition: target must be gone", target.exists());
        assertNotNull("precondition: store still holds the old hash", store.read());

        // Second run: store has hash, target gone → re-seed
        SeedResult second = LocateValuesSeeder.seedIfChanged(
                new ByteArrayInputStream(assetBytes), target, store);

        // Current decision-tree behavior labels this as SEEDED_UPDATED because
        // lastHash != null. The file IS re-created from scratch.
        assertEquals(SeedAction.SEEDED_UPDATED, second.action);
        assertEquals("same asset → same hash", first.hash, second.hash);
        assertNull(second.errorMessage);

        // Target file has been re-created with exact asset bytes
        assertTrue(target.exists());
        byte[] actualBytes = Files.readAllBytes(target.toPath());
        assertArrayEquals(assetBytes, actualBytes);

        // Store continues to hold the (unchanged) hash
        assertEquals(second.hash, store.read());

        // No leftover .tmp file
        assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
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
