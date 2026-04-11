package com.guard.wallet.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * 4. 完整 80-key 覆盖由 allRequiredKeysPresent() 断言。
 *
 * <p><b>Working directory requirement:</b> this test reads the asset file via
 * the relative path {@code src/main/assets/locateValues.json}, which resolves
 * correctly when Gradle's JVM unit test runner sets cwd to the module directory
 * {@code vendor-replica/app/}. IDE runners with a different working directory
 * will fail with "asset file missing: &lt;absolute path&gt;".
 */
public class LocateValuesAssetTest {

    /** Gradle JVM 测试的工作目录是 vendor-replica/app/，因此用相对路径读取 assets。 */
    private static final File ASSET_FILE = new File("src/main/assets/locateValues.json");

    /** 解析结果 — @BeforeClass 填充，每个测试方法共享。 */
    private static HashMap<String, String> MAP;

    /** 若 @BeforeClass 遇到 JsonSyntaxException，保留下来供 parser-smoke 测试断言。 */
    private static JsonSyntaxException LOAD_EXCEPTION;

    /** 用于在 @Before 中识别当前测试名，给 parsesAsFlatStringMap 网开一面。 */
    @Rule
    public TestName testName = new TestName();

    /**
     * 用 vendor f.java:31 的同款 Gson TypeToken 加载 asset 文件一次。
     * 任何嵌套对象 value 都会在这里抛 JsonSyntaxException，被捕获到 LOAD_EXCEPTION。
     */
    @BeforeClass
    public static void loadAsset() throws Exception {
        assertTrue(
            "asset file missing: " + ASSET_FILE.getAbsolutePath(),
            ASSET_FILE.exists()
        );
        String content = new String(
            Files.readAllBytes(ASSET_FILE.toPath()),
            StandardCharsets.UTF_8
        );
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        try {
            MAP = new Gson().fromJson(content, type);
        } catch (JsonSyntaxException e) {
            LOAD_EXCEPTION = e;
            MAP = null;
        }
    }

    /**
     * 所有 @Test 方法（除 parsesAsFlatStringMap）都依赖 MAP 非空。
     * 若 @BeforeClass 遇到 JsonSyntaxException，MAP 为 null，
     * 这些测试应当被 SKIP 而不是报 NPE — 让 parsesAsFlatStringMap
     * 成为唯一报告 JSON 语法错误的地方，保持诊断信息聚焦。
     *
     * <p>parsesAsFlatStringMap 显式豁免：它需要运行到方法体内部
     * 以便调用 fail(...) 输出 "vendor f.java line 31" 诊断。
     */
    @Before
    public void requireMapLoaded() {
        // 豁免 parsesAsFlatStringMap — 这是唯一允许在 MAP==null 时仍然运行的测试
        // （它本身检查 LOAD_EXCEPTION 并 fail() 报告 JSON 语法错误诊断）。
        // ⚠️ 如果你重命名 parsesAsFlatStringMap 方法，必须同步更新下面的字符串字面量，
        // 否则重命名后的方法在 MAP==null 时会被跳过，丢失核心诊断。
        // 参见 parsesAsFlatStringMap 的 Javadoc。
        if ("parsesAsFlatStringMap".equals(testName.getMethodName())) {
            return;
        }
        Assume.assumeNotNull(MAP);
    }

    /**
     * Parser 冒烟测试 — 校验 locateValues.json 能被 vendor f.java 同款
     * Gson TypeToken 解析为扁平 HashMap&lt;String,String&gt;。
     *
     * <p><b>⚠️ 重命名危险：</b>此方法名被 {@link #requireMapLoaded()} 中的字符串
     * 字面量 <code>"parsesAsFlatStringMap"</code> 显式引用，用于在 JSON
     * 解析失败时豁免跳过逻辑，让本方法成为唯一报告 {@code JsonSyntaxException}
     * 诊断的出口。如果你重命名本方法，<b>必须同步更新</b>
     * {@code requireMapLoaded()} 中的字符串字面量 — 否则重命名后的
     * 方法会在 JSON 损坏时被静默跳过，丢失 "vendor f.java line 31"
     * 诊断信息，且不会有任何编译错误提示此耦合断裂。
     *
     * <p>更健壮的长期方案：把本方法抽到一个不继承 @Before 的独立测试类。
     * 目前耦合通过本 Javadoc + {@code requireMapLoaded()} 内的注释显式标注。
     */
    @Test
    public void parsesAsFlatStringMap() {
        if (LOAD_EXCEPTION != null) {
            fail(
                "locateValues.json must be a flat HashMap<String,String> "
                + "(vendor f.java line 31 enforces this). "
                + "Nested object values cause Gson to throw: " + LOAD_EXCEPTION.getMessage()
            );
        }
        assertNotNull("Gson returned null — JSON is malformed or empty", MAP);
        assertFalse("Parsed map must not be empty", MAP.isEmpty());
    }

    @Test
    public void pairSecurityOpeningTextIsDialogStateNotScreenName() {
        assertEquals(
            "PAIR_SECURITY_OPENING_TEXT must be the dialog state '正在开启' "
            + "(o/a0.java:441 — Xiaomi security center AdbInputApplyActivity opening dialog), "
            + "NOT the screen title '安全设置'",
            "正在开启",
            MAP.get("PAIR_SECURITY_OPENING_TEXT")
        );
    }

    @Test
    public void canaryPairKey() {
        assertEquals("无线调试", MAP.get("PAIR_WIFI_DEBUG_TEXT"));
    }

    @Test
    public void canaryColorsKey() {
        assertEquals("耗电管理", MAP.get("COLORS_SETTINGS_POWER_MANAGE_TEXT"));
    }

    @Test
    public void canaryHuaweiKey() {
        assertEquals("应用与通知", MAP.get("HUA_WEI_APP_AND_NOTIFICATION_TEXT"));
    }

    @Test
    public void canaryMiuiKey() {
        assertEquals("MIUI版本", MAP.get("MIUI_VERSION_TEXT"));
    }

    @Test
    public void canaryVivoKey() {
        assertEquals("允许自启动", MAP.get("VIVO_AUTO_START_TEXT"));
    }

    @Test
    public void canaryCommonKey() {
        assertEquals("电池", MAP.get("COMMON_SETTINGS_BATTERY_TEXT"));
    }

    @Test
    public void canaryOppoInstallKey() {
        assertEquals("授权安装", MAP.get("OPPO_AUTHORIZE_INSTALL_BTN_TEXT"));
    }

    @Test
    public void canaryVersionKey() {
        assertEquals("HarmonyOS版本", MAP.get("HARMONY_OS_VERSION_TEXT"));
    }

    /**
     * 全量 80-key 存在性断言。若以后新增 key 到 locateValues.json，
     * 同步把 key 名加到下面的 required[] 数组；若删除 key，也同步删。
     * 断言同时检查 map.size() == required.length，防止遗留 dead key。
     */
    @Test
    public void allRequiredKeysPresent() {
        String[] required = {
            // PAIR_* (29) — ADB wireless pairing — vendor o/a0.java
            "PAIR_WIFI_DEBUG_TEXT",
            "PAIR_WIFI_DEBUG_2_TEXT",
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

            // MIUI_* (5) — Xiaomi MIUI keep-alive + install — vendor o/q.java
            "MIUI_APP_POWER_CONSUME_TEXT",
            "MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT",
            "MIUI_SETTINGS_UNRESTRICTED_TEXT",
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
            81,
            required.length
        );

        List<String> missing = new ArrayList<>();
        List<String> empty = new ArrayList<>();
        for (String key : required) {
            String value = MAP.get(key);
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

        Set<String> extras = new HashSet<>(MAP.keySet());
        extras.removeAll(new HashSet<>(Arrays.asList(required)));
        assertEquals(
            "JSON contains unexpected extra keys — either add them to the required[] list "
            + "or remove them from locateValues.json. Extra keys: " + extras,
            required.length,
            MAP.size()
        );
    }
}
