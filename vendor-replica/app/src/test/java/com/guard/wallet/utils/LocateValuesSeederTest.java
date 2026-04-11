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
}
