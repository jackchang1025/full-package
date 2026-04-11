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
