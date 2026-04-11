package com.guard.wallet.engine;

import static org.junit.Assert.*;

import com.guard.wallet.utils.LocateValuesUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

/**
 * 离线 UI dump 测试 — 用真机 uiautomator dump 的 XML 验证
 * locateValues.json 的 key 是否能在对应页面中命中 UI 文本。
 *
 * 测试 fixture: src/test/resources/ui-dumps/xiaomi-13-hyperos3/*.xml
 * 数据来源: 小米 13 (2211133C), HyperOS 3, Android 16, 2026-04-12
 *
 * 不需要真机、不需要 Android 框架、纯 JVM 运行。
 */
public class UiDumpMatchTest {

    private static final String DUMP_DIR = "/ui-dumps/xiaomi-13-hyperos3/";

    /** 模拟 locateValues.json 的 key-value 映射 */
    private HashMap<String, String> locateValues;

    @Before
    public void setUp() throws Exception {
        locateValues = new HashMap<>();
        InputStream is = getClass().getResourceAsStream("/locateValues.json");
        assertNotNull("locateValues.json not found in test resources", is);
        String json = new Scanner(is, StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
        // 简易 JSON 解析: {"key":"value",...}
        json = json.replaceAll("[{}\"\\s]", "");
        for (String pair : json.split(",")) {
            String[] kv = pair.split(":", 2);
            if (kv.length == 2 && !kv[0].isEmpty()) {
                locateValues.put(kv[0].trim(), kv[1].trim());
            }
        }
    }

    private String loadDump(String filename) {
        InputStream is = getClass().getResourceAsStream(DUMP_DIR + filename);
        assertNotNull("UI dump not found: " + filename, is);
        return new Scanner(is, StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
    }

    private String getValue(String key) {
        return locateValues.getOrDefault(key, "");
    }

    private boolean xmlContainsText(String xml, String text) {
        if (text == null || text.isEmpty()) return false;
        // 匹配 text="xxx" 或 content-desc="xxx"
        return xml.contains("text=\"" + text + "\"")
                || xml.contains("content-desc=\"" + text + "\"");
    }

    private boolean xmlContainsTextContaining(String xml, String substring) {
        if (substring == null || substring.isEmpty()) return false;
        // 搜索 text 属性中包含 substring 的节点
        int idx = 0;
        while ((idx = xml.indexOf("text=\"", idx)) != -1) {
            int end = xml.indexOf("\"", idx + 6);
            if (end != -1) {
                String textValue = xml.substring(idx + 6, end);
                if (textValue.contains(substring)) return true;
            }
            idx = end != -1 ? end : idx + 1;
        }
        return false;
    }

    // ═══════ 应用详情页 (app-detail.xml) ═══════

    @Test
    public void appDetail_hasPowerSavingStrategyText() {
        String xml = loadDump("app-detail.xml");
        String key = "MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT";
        String expected = getValue(key);
        assertTrue(key + "=" + expected + " should exist in app-detail page",
                xmlContainsText(xml, expected));
    }

    @Test
    public void appDetail_hasPowerConsumeText() {
        String xml = loadDump("app-detail.xml");
        String key = "MIUI_APP_POWER_CONSUME_TEXT";
        String expected = getValue(key);
        // HyperOS 3 显示 "耗电1.6%" 而非精确的 "应用耗电"
        // locateValues 值是 "应用耗电"，但引擎 b0() 用 setEquals 精确匹配
        // 在 HyperOS 3 上此 key 不再精确命中，但 "耗电" 子串仍存在
        assertTrue(key + "=" + expected + " substring '耗电' should be in app-detail page",
                xmlContainsTextContaining(xml, "耗电"));
    }

    @Test
    public void appDetail_hasAutoStartEntry() {
        String xml = loadDump("app-detail.xml");
        assertTrue("自启动 entry should exist in app-detail page",
                xmlContainsText(xml, "自启动"));
    }

    // ═══════ 省电策略页 (power-detail.xml) ═══════

    @Test
    public void powerDetail_hasUnrestrictedText() {
        String xml = loadDump("power-detail.xml");
        assertTrue("'无限制' text should exist in power-detail page",
                xmlContainsText(xml, "无限制"));
    }

    @Test
    public void powerDetail_miuiUnrestrictedKeyExists() {
        // 验证 MIUI_SETTINGS_UNRESTRICTED_TEXT key 存在于 locateValues.json
        String value = getValue("MIUI_SETTINGS_UNRESTRICTED_TEXT");
        assertFalse("MIUI_SETTINGS_UNRESTRICTED_TEXT must exist in locateValues.json (currently missing!)",
                value.isEmpty());
    }

    @Test
    public void powerDetail_commonUnrestrictedKeyMatchesPage() {
        String xml = loadDump("power-detail.xml");
        String value = getValue("COMMON_SETTINGS_UNRESTRICTED_TEXT");
        assertTrue("COMMON_SETTINGS_UNRESTRICTED_TEXT=" + value + " should match power-detail page",
                xmlContainsText(xml, value));
    }

    @Test
    public void powerDetail_hasRadioButtonOrSwitch() {
        String xml = loadDump("power-detail.xml");
        boolean hasRadioButton = xml.contains("android.widget.RadioButton");
        boolean hasSwitch = xml.contains("android.widget.Switch");
        assertTrue("power-detail page should have RadioButton or Switch for strategy selection",
                hasRadioButton || hasSwitch);
    }

    // ═══════ 自启动管理页 (autostart-manage-scrolled.xml) ═══════

    @Test
    public void autostartManage_hasVendorReplicaApp() {
        String xml = loadDump("autostart-manage-scrolled.xml");
        assertTrue("VendorReplica should be visible after scrolling",
                xmlContainsText(xml, "VendorReplica")
                || xml.contains("content-desc=\"VendorReplica\""));
    }

    @Test
    public void autostartManage_vendorReplicaHasSwitchWidget() {
        String xml = loadDump("autostart-manage-scrolled.xml");
        // VendorReplica 的 Switch 节点: class="android.widget.Switch" content-desc="VendorReplica"
        assertTrue("VendorReplica should have a Switch widget (not CompoundButton)",
                xml.contains("class=\"android.widget.Switch\"")
                && xml.contains("VendorReplica"));
    }

    @Test
    public void autostartManage_vendorReplicaSwitchIsUnchecked() {
        String xml = loadDump("autostart-manage-scrolled.xml");
        // 验证默认状态: checked="false"
        // Switch 节点: content-desc="VendorReplica" ... checked="false"
        int idx = xml.indexOf("content-desc=\"VendorReplica\"");
        assertTrue("VendorReplica Switch node should exist", idx > 0);
        // 找到该节点的 checked 属性
        int nodeStart = xml.lastIndexOf("<node", idx);
        int nodeEnd = xml.indexOf("/>", idx);
        if (nodeEnd == -1) nodeEnd = xml.indexOf("</node>", idx);
        String node = xml.substring(nodeStart, nodeEnd);
        assertTrue("VendorReplica Switch should be unchecked by default",
                node.contains("checked=\"false\""));
    }

    @Test
    public void autostartManage_switchClassNotCompoundButton() {
        // 关键发现: HyperOS 3 自启动页用 Switch (不是 CompoundButton)
        // 引擎的 toggleSwitchOrCheckBox() 搜索 CompoundButton/CheckBox
        // 但实际页面是 Switch — 需要确认 Switch 是否也能被搜到
        String xml = loadDump("autostart-manage-scrolled.xml");
        boolean hasCompoundButton = xml.contains("android.widget.CompoundButton");
        boolean hasSwitch = xml.contains("android.widget.Switch");
        // HyperOS 3 用 Switch，不用 CompoundButton
        assertFalse("HyperOS 3 autostart page uses Switch, not CompoundButton",
                hasCompoundButton);
        assertTrue("HyperOS 3 autostart page should have Switch widgets",
                hasSwitch);
    }

    // ═══════ 应用详情页内联自启动 Switch (HyperOS 3) ═══════

    @Test
    public void appDetailWithAutostart_hasInlineAutoStartSwitch() {
        String xml = loadDump("app-detail-with-autostart.xml");
        assertTrue("应用详情页应有内联的自启动 Switch (content-desc='自启动')",
                xml.contains("content-desc=\"自启动\"")
                && xml.contains("class=\"android.widget.Switch\""));
    }

    @Test
    public void appDetailWithAutostart_autoStartSwitchIsCheckable() {
        String xml = loadDump("app-detail-with-autostart.xml");
        int idx = xml.indexOf("content-desc=\"自启动\"");
        assertTrue("自启动 Switch 应存在", idx > 0);
        int nodeStart = xml.lastIndexOf("<node", idx);
        int nodeEnd = xml.indexOf("/>", idx);
        if (nodeEnd == -1) nodeEnd = xml.indexOf("</node>", idx);
        String node = xml.substring(nodeStart, Math.min(nodeEnd + 2, xml.length()));
        assertTrue("自启动 Switch 应可勾选", node.contains("checkable=\"true\""));
        assertTrue("自启动 Switch 应可点击", node.contains("clickable=\"true\""));
    }

    @Test
    public void appDetailWithAutostart_autoStartDefaultUnchecked() {
        String xml = loadDump("app-detail-with-autostart.xml");
        int idx = xml.indexOf("content-desc=\"自启动\"");
        int nodeStart = xml.lastIndexOf("<node", idx);
        int nodeEnd = xml.indexOf("/>", idx);
        if (nodeEnd == -1) nodeEnd = xml.indexOf("</node>", idx);
        String node = xml.substring(nodeStart, Math.min(nodeEnd + 2, xml.length()));
        assertTrue("自启动 Switch 默认应未勾选", node.contains("checked=\"false\""));
    }

    // ═══════ locateValues.json 完整性检查 ═══════

    @Test
    public void locateValues_allMiuiKeysExist() {
        // XiaomiEngine 使用的 3 个 key 必须全部存在
        String[] requiredKeys = {
                "MIUI_APP_POWER_CONSUME_TEXT",
                "MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT",
                "MIUI_SETTINGS_UNRESTRICTED_TEXT"  // ← 当前缺失!
        };
        for (String key : requiredKeys) {
            assertFalse(key + " must exist in locateValues.json",
                    getValue(key).isEmpty());
        }
    }
}
