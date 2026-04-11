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
 * 1. 顶层必须是扁平 HashMap&lt;String, String&gt;，否则 vendor 的 Gson TypeToken
 *    会抛 JsonSyntaxException 并被外层 catch 吞掉，导致运行时 map 永远空。
 * 2. PAIR_SECURITY_OPENING_TEXT 必须是对话框状态文本 "正在开启"，
 *    而不是屏幕标题 "安全设置"。这是 vendor 在 o/a0.java:441 中
 *    针对小米安全中心 AdbInputApplyActivity "正在开启" 弹窗的匹配条件。
 * 3. 每个厂商类别至少有一个 canary key 解析为预期值。
 *
 * 详细的 82 key 全量校验见 allRequiredKeysPresent() (后续任务追加)。
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
