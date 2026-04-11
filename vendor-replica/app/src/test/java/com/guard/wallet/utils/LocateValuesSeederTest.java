package com.guard.wallet.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
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
