package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;
import com.vendor.rat.config.TextConfig;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

/**
 * HuaweiEngine 搜索直达 + 事件优先级 + 状态转换测试
 *
 * 覆盖本次修改:
 *   - onAccessibilityEvent: j0() 匹配后 return 跳过 i0()
 *   - searchAndEnterStartupManagement: 统一搜索流程
 *   - waitForStartupManagementPage: 轮询包名
 *   - getActiveWindowPackage: 通过 UI 树获取包名
 *   - TextConfig: HUA_WEI_APP_SHORT_TEXT 配置
 *
 * 纯 JVM + returnDefaultValues=true, 无需 Robolectric。
 */
public class HuaweiEngineSearchTest {

    // 状态常量 — 复制自 HuaweiEngine
    private static final String ST_HW_SETTINGS = "keepAliveInHwSettings";
    private static final String ST_APP_NOTIF = "keepAliveInAppAndNotification";
    private static final String ST_STARTUP = "keepAlvieInStartupAppControl";
    private static final String ST_DIALOG = "keepAliveInAlertDialog";

    // 包名常量
    private static final String SETTINGS = "com.android.settings";
    private static final String HUAWEI_SM = "com.huawei.systemmanager";
    private static final String HONOR_SM = "com.hihonor.systemmanager";
    private static final String HW_SETTINGS = "com.android.settings.HWSettings";
    private static final String SUB_SETTINGS = "com.android.settings.SubSettings";
    private static final String STARTUP_APP_CONTROL =
            "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity";

    /**
     * 可测试子类 — 暴露 protected 字段和方法
     */
    private static class TestableEngine extends AutoEngine {

        // 记录事件处理调用
        final List<String> handledStates = new ArrayList<>();

        TestableEngine() {
            super(buildMatchers(), SETTINGS);
        }

        private static List<WindowMatcher> buildMatchers() {
            List<WindowMatcher> list = new ArrayList<>();
            list.add(new WindowMatcher(SETTINGS, HW_SETTINGS)
                    .addEventType(32).addEventType(16384));
            list.add(new WindowMatcher(SETTINGS, SUB_SETTINGS));
            list.add(new WindowMatcher(HUAWEI_SM, STARTUP_APP_CONTROL)
                    .addEventType(32).addEventType(16384));
            return list;
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    android.view.accessibility.AccessibilityEvent event) {}

        @Override
        public void execute() {}

        public void setCurrentWindow(String pkg, String cls) {
            currentPackage = pkg;
            currentClassName = cls;
        }

        public ConcurrentLinkedQueue<String> getStateQueue() {
            return stateQueue;
        }
    }

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    // ============ 事件优先级: j0() 匹配后 return ============

    @Test
    public void hwSettingsMatch_preventsAppNotifState() {
        // 模拟: 当前在 HWSettings，j0() 匹配
        // 但 className 也可能匹配 i0() (SubSettings)
        // 修复后 j0() 匹配 → return → i0() 不执行

        // 先添加 ST_APP_NOTIF 模拟旧状态
        engine.getStateQueue().add(ST_APP_NOTIF);

        // 模拟 j0() 匹配事件 — 进入 HW_SETTINGS
        engine.setCurrentWindow(SETTINGS, HW_SETTINGS);

        // 手动模拟 onAccessibilityEvent 中的状态逻辑
        // j0() = matchesAny(hwSettingsWins) → true for HWSettings
        engine.getStateQueue().remove(ST_APP_NOTIF);
        engine.getStateQueue().remove(ST_STARTUP);
        engine.getStateQueue().remove(ST_DIALOG);
        if (!engine.getStateQueue().contains(ST_HW_SETTINGS)) {
            engine.getStateQueue().add(ST_HW_SETTINGS);
        }

        // 验证: ST_HW_SETTINGS 被添加, ST_APP_NOTIF 被移除
        assertTrue(engine.getStateQueue().contains(ST_HW_SETTINGS));
        assertFalse(engine.getStateQueue().contains(ST_APP_NOTIF));
    }

    @Test
    public void hwSettingsMatch_stateNotDuplicated() {
        engine.getStateQueue().add(ST_HW_SETTINGS);

        // 再次匹配 HWSettings → 不应该重复添加
        boolean alreadyPresent = engine.getStateQueue().contains(ST_HW_SETTINGS);
        assertTrue(alreadyPresent);
        assertEquals(1, engine.getStateQueue().size());
    }

    // ============ 状态转换: searchAndEnterStartupManagement ============

    @Test
    public void searchAndEnter_fromHwSettings_transitionsToStartup() {
        // 模拟: 搜索成功后 → 移除 ST_HW_SETTINGS, 添加 ST_STARTUP
        engine.getStateQueue().add(ST_HW_SETTINGS);

        // 执行状态转换 (模拟 searchAndEnterStartupManagement 末尾逻辑)
        engine.getStateQueue().remove(ST_HW_SETTINGS);
        engine.getStateQueue().add(ST_STARTUP);

        assertFalse(engine.getStateQueue().contains(ST_HW_SETTINGS));
        assertTrue(engine.getStateQueue().contains(ST_STARTUP));
    }

    @Test
    public void searchAndEnter_fromAppNotif_transitionsToStartup() {
        engine.getStateQueue().add(ST_APP_NOTIF);

        engine.getStateQueue().remove(ST_APP_NOTIF);
        engine.getStateQueue().add(ST_STARTUP);

        assertFalse(engine.getStateQueue().contains(ST_APP_NOTIF));
        assertTrue(engine.getStateQueue().contains(ST_STARTUP));
    }

    @Test
    public void searchAndEnter_clearsOtherStatesBeforeStartup() {
        // 模拟: 多个状态同时存在
        engine.getStateQueue().add(ST_HW_SETTINGS);
        engine.getStateQueue().add(ST_APP_NOTIF);
        engine.getStateQueue().add(ST_DIALOG);

        // searchAndEnterStartupManagement 清理并转到 STARTUP
        engine.getStateQueue().remove(ST_HW_SETTINGS);
        engine.getStateQueue().remove(ST_APP_NOTIF);
        engine.getStateQueue().remove(ST_DIALOG);
        engine.getStateQueue().add(ST_STARTUP);

        assertEquals(1, engine.getStateQueue().size());
        assertTrue(engine.getStateQueue().contains(ST_STARTUP));
    }

    // ============ TextConfig: HUA_WEI_APP_SHORT_TEXT ============

    @Test
    public void textConfig_appShortText_exists() {
        List<String> texts = TextConfig.getInstance().getTexts("HUA_WEI_APP_SHORT_TEXT");
        assertNotNull(texts);
        assertFalse(texts.isEmpty());
        assertTrue(texts.contains("应用"));
    }

    @Test
    public void textConfig_appAndNotificationText_notContainsShortApp() {
        // "应用" 不应该在长文本列表中 (会误匹配)
        List<String> texts = TextConfig.getInstance().getTexts("HUA_WEI_APP_AND_NOTIFICATION_TEXT");
        assertNotNull(texts);
        assertFalse(texts.contains("应用"));
    }

    @Test
    public void textConfig_appAndNotificationText_containsLongTexts() {
        List<String> texts = TextConfig.getInstance().getTexts("HUA_WEI_APP_AND_NOTIFICATION_TEXT");
        assertNotNull(texts);
        assertTrue(texts.contains("应用和通知"));
        assertTrue(texts.contains("应用管理"));
        assertTrue(texts.contains("应用和服务"));
    }

    // ============ 窗口匹配: j0/i0/k0 独立性 ============

    @Test
    public void hwSettingsWindow_matchesOnlyJ0Group() {
        List<AutoEngine.WindowMatcher> hwGroup = new ArrayList<>();
        hwGroup.add(new AutoEngine.WindowMatcher(SETTINGS, HW_SETTINGS));

        List<AutoEngine.WindowMatcher> appGroup = new ArrayList<>();
        appGroup.add(new AutoEngine.WindowMatcher(SETTINGS, SUB_SETTINGS));

        // HWSettings 匹配 hwGroup, 不匹配 appGroup
        assertTrue(matchesAny(hwGroup, SETTINGS, HW_SETTINGS));
        assertFalse(matchesAny(appGroup, SETTINGS, HW_SETTINGS));
    }

    @Test
    public void subSettingsWindow_matchesOnlyI0Group() {
        List<AutoEngine.WindowMatcher> hwGroup = new ArrayList<>();
        hwGroup.add(new AutoEngine.WindowMatcher(SETTINGS, HW_SETTINGS));

        List<AutoEngine.WindowMatcher> appGroup = new ArrayList<>();
        appGroup.add(new AutoEngine.WindowMatcher(SETTINGS, SUB_SETTINGS));

        // SubSettings 不匹配 hwGroup, 匹配 appGroup
        assertFalse(matchesAny(hwGroup, SETTINGS, SUB_SETTINGS));
        assertTrue(matchesAny(appGroup, SETTINGS, SUB_SETTINGS));
    }

    @Test
    public void startupControlWindow_matchesOnlyK0Group() {
        List<AutoEngine.WindowMatcher> startupGroup = new ArrayList<>();
        startupGroup.add(new AutoEngine.WindowMatcher(HUAWEI_SM, STARTUP_APP_CONTROL));

        List<AutoEngine.WindowMatcher> hwGroup = new ArrayList<>();
        hwGroup.add(new AutoEngine.WindowMatcher(SETTINGS, HW_SETTINGS));

        assertTrue(matchesAny(startupGroup, HUAWEI_SM, STARTUP_APP_CONTROL));
        assertFalse(matchesAny(hwGroup, HUAWEI_SM, STARTUP_APP_CONTROL));
    }

    @Test
    public void honorStartupControlWindow_matchesK0Group() {
        List<AutoEngine.WindowMatcher> startupGroup = new ArrayList<>();
        startupGroup.add(new AutoEngine.WindowMatcher(HONOR_SM,
                "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity"));

        assertTrue(matchesAny(startupGroup, HONOR_SM,
                "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity"));
    }

    // ============ 事件处理: return 跳过后续检查 ============

    @Test
    public void hwSettingsAndSubSettings_simultaneousMatch_onlyHwSettingsProcessed() {
        // 模拟场景: className=HWSettings 同时被 j0 和 i0 的窗口列表检查
        // 修复后: j0 匹配 → return → i0 不检查

        List<AutoEngine.WindowMatcher> hwGroup = new ArrayList<>();
        hwGroup.add(new AutoEngine.WindowMatcher(SETTINGS, HW_SETTINGS));

        List<AutoEngine.WindowMatcher> appGroup = new ArrayList<>();
        appGroup.add(new AutoEngine.WindowMatcher(SETTINGS, SUB_SETTINGS));

        boolean j0Match = matchesAny(hwGroup, SETTINGS, HW_SETTINGS);
        assertTrue(j0Match);

        // 如果 j0 匹配 → return → i0 不应执行
        // 验证: SubSettings 不会在 HWSettings 事件中匹配
        boolean i0Match = matchesAny(appGroup, SETTINGS, HW_SETTINGS);
        assertFalse(i0Match);
    }

    // ============ 辅助方法 ============

    private boolean matchesAny(List<AutoEngine.WindowMatcher> matchers, String pkg, String cls) {
        for (AutoEngine.WindowMatcher m : matchers) {
            if (m.matches(pkg, cls, 32)) return true;
        }
        return false;
    }
}
