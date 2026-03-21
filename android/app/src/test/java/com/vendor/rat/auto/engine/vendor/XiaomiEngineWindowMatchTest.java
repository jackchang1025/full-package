package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * XiaomiEngine 三组窗口检测 (f0/g0/h0) 测试
 *
 * 复制 XiaomiEngine 的常量和 buildDetectionGroups 逻辑，
 * 验证每个窗口只匹配正确的组。
 *
 * 纯 JVM 测试，无需 Android 框架。
 *
 * 基于逆向: o/q.java (498 行)
 */
public class XiaomiEngineWindowMatchTest {

    // ====== 常量 — 复制自 XiaomiEngine ======
    private static final String SECURITY_CENTER = "com.miui.securitycenter";
    private static final String POWER_KEEPER = "com.miui.powerkeeper";

    private static final String AUTO_START_ACTIVITY =
            "com.miui.permcenter.autostart.AutoStartManagementActivity";
    private static final String HIDDEN_APPS_ACTIVITY =
            "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity";
    private static final String HIDDEN_APPS_CONFIG_ACTIVITY =
            "com.miui.powerkeeper.ui.HiddenAppsConfigActivity";
    private static final String PERMISSIONS_EDITOR_ACTIVITY =
            "com.miui.permcenter.permissions.PermissionsEditorActivity";
    private static final String OTHER_PERMISSIONS_ACTIVITY =
            "com.miui.permcenter.settings.OtherPermissionsActivity";
    private static final String ALERT_DIALOG = "miuix.appcompat.app.AlertDialog";

    // 额外: buildDetectionGroups 中用到
    private static final String APP_MANAGER_DETAILS =
            "com.miui.appmanager.ApplicationsDetailsActivity";
    private static final String APP_MANAGER_MAIN =
            "com.miui.appmanager.AppManagerMainActivity";
    private static final String FRAME_LAYOUT = "android.widget.FrameLayout";
    private static final String POWER_DETAIL_ACTIVITY =
            "com.miui.powercenter.legacypowerrank.PowerDetailActivity";

    // ====== 检测分组 — 复制自 XiaomiEngine.buildDetectionGroups() ======
    // f0() — App 详情
    private List<AutoEngine.WindowMatcher> appDetailWins;
    // h0() — 自启动管理
    private List<AutoEngine.WindowMatcher> autoStartWins;
    // g0() — 省电策略
    private List<AutoEngine.WindowMatcher> powerDetailWins;

    @Before
    public void setUp() {
        appDetailWins = new ArrayList<>();
        appDetailWins.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, APP_MANAGER_DETAILS));
        appDetailWins.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, APP_MANAGER_MAIN));
        appDetailWins.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, FRAME_LAYOUT));

        autoStartWins = new ArrayList<>();
        autoStartWins.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, AUTO_START_ACTIVITY));

        powerDetailWins = new ArrayList<>();
        powerDetailWins.add(new AutoEngine.WindowMatcher(POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
        powerDetailWins.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, POWER_DETAIL_ACTIVITY));
    }

    // ============ 工具方法 ============

    private boolean matchesAny(List<AutoEngine.WindowMatcher> matchers,
                               String pkg, String cls) {
        for (AutoEngine.WindowMatcher m : matchers) {
            if (m.matches(pkg, cls, 0)) return true;
        }
        return false;
    }

    /** 带 eventType 的匹配 — 用于 buildWindowMatchers 测试 */
    private boolean matchesAnyWithEvent(List<AutoEngine.WindowMatcher> matchers,
                                        String pkg, String cls, int eventType) {
        for (AutoEngine.WindowMatcher m : matchers) {
            if (m.matches(pkg, cls, eventType)) return true;
        }
        return false;
    }

    // ============ f0(): App 详情组 ============

    @Test
    public void f0_appManagerDetails_matchesAppDetailGroup() {
        assertTrue(matchesAny(appDetailWins, SECURITY_CENTER, APP_MANAGER_DETAILS));
    }

    @Test
    public void f0_appManagerMain_matchesAppDetailGroup() {
        assertTrue(matchesAny(appDetailWins, SECURITY_CENTER, APP_MANAGER_MAIN));
    }

    @Test
    public void f0_frameLayout_matchesAppDetailGroup() {
        assertTrue(matchesAny(appDetailWins, SECURITY_CENTER, FRAME_LAYOUT));
    }

    @Test
    public void f0_autoStartActivity_notInAppDetailGroup() {
        assertFalse(matchesAny(appDetailWins, SECURITY_CENTER, AUTO_START_ACTIVITY));
    }

    @Test
    public void f0_powerKeeperActivity_notInAppDetailGroup() {
        assertFalse(matchesAny(appDetailWins, POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
    }

    // ============ h0(): 自启动管理组 ============

    @Test
    public void h0_autoStartActivity_matchesAutoStartGroup() {
        assertTrue(matchesAny(autoStartWins, SECURITY_CENTER, AUTO_START_ACTIVITY));
    }

    @Test
    public void h0_appManagerDetails_notInAutoStartGroup() {
        assertFalse(matchesAny(autoStartWins, SECURITY_CENTER, APP_MANAGER_DETAILS));
    }

    @Test
    public void h0_powerKeeper_notInAutoStartGroup() {
        assertFalse(matchesAny(autoStartWins, POWER_KEEPER, AUTO_START_ACTIVITY));
    }

    // ============ g0(): 省电策略组 ============

    @Test
    public void g0_hiddenAppsConfig_matchesPowerDetailGroup() {
        assertTrue(matchesAny(powerDetailWins, POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
    }

    @Test
    public void g0_powerDetailActivity_matchesPowerDetailGroup() {
        assertTrue(matchesAny(powerDetailWins, SECURITY_CENTER, POWER_DETAIL_ACTIVITY));
    }

    @Test
    public void g0_autoStartActivity_notInPowerDetailGroup() {
        assertFalse(matchesAny(powerDetailWins, SECURITY_CENTER, AUTO_START_ACTIVITY));
    }

    @Test
    public void g0_hiddenAppsActivity_notInPowerDetailGroup() {
        assertFalse(matchesAny(powerDetailWins, POWER_KEEPER, HIDDEN_APPS_ACTIVITY));
    }

    // ============ 跨组互斥验证 ============

    @Test
    public void crossGroup_autoStartOnlyInAutoStartGroup() {
        assertFalse(matchesAny(appDetailWins, SECURITY_CENTER, AUTO_START_ACTIVITY));
        assertTrue(matchesAny(autoStartWins, SECURITY_CENTER, AUTO_START_ACTIVITY));
        assertFalse(matchesAny(powerDetailWins, SECURITY_CENTER, AUTO_START_ACTIVITY));
    }

    @Test
    public void crossGroup_hiddenAppsConfigOnlyInPowerGroup() {
        assertFalse(matchesAny(appDetailWins, POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
        assertFalse(matchesAny(autoStartWins, POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
        assertTrue(matchesAny(powerDetailWins, POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY));
    }

    @Test
    public void crossGroup_alertDialogNotInAnyDetectionGroup() {
        assertFalse(matchesAny(appDetailWins, SECURITY_CENTER, ALERT_DIALOG));
        assertFalse(matchesAny(autoStartWins, SECURITY_CENTER, ALERT_DIALOG));
        assertFalse(matchesAny(powerDetailWins, SECURITY_CENTER, ALERT_DIALOG));

        assertFalse(matchesAny(appDetailWins, POWER_KEEPER, ALERT_DIALOG));
        assertFalse(matchesAny(autoStartWins, POWER_KEEPER, ALERT_DIALOG));
        assertFalse(matchesAny(powerDetailWins, POWER_KEEPER, ALERT_DIALOG));
    }

    // ============ buildWindowMatchers() — 全局监听列表验证 ============

    /**
     * 重建 buildWindowMatchers() 列表 (对应逆向 q.l0())
     * 验证所有应监听的窗口都已注册。
     */
    private List<AutoEngine.WindowMatcher> buildWindowMatchers() {
        List<AutoEngine.WindowMatcher> list = new ArrayList<>();
        // vendor l0():97-98 — 电池优化对话框 (共享 c.J())
        list.add(new AutoEngine.WindowMatcher("com.android.settings", "android.app.Dialog")
                .addEventType(32).addEventType(16384));
        // vendor l0():98 — 自启动管理
        AutoEngine.WindowMatcher autoStart =
                new AutoEngine.WindowMatcher(SECURITY_CENTER, AUTO_START_ACTIVITY);
        autoStart.addEventType(32);
        autoStart.addEventType(16384);
        list.add(autoStart);
        // vendor l0():99-102 — 后台应用管理
        list.add(new AutoEngine.WindowMatcher(POWER_KEEPER, HIDDEN_APPS_ACTIVITY)
                .addEventType(32).addEventType(16384));
        // vendor l0():103-108 — App详情 (主/备份包名)
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, APP_DETAILS)
                .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, APP_DETAILS)
                .addEventType(32).addEventType(16384));
        // vendor l0():103-108 — AppManager (主/备份包名)
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, APP_MANAGER_MAIN)
                .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, APP_MANAGER_MAIN)
                .addEventType(32).addEventType(16384));
        // vendor l0():107-108 — FrameLayout (主/备份包名)
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, "android.widget.FrameLayout")
                .addEventType(32).addEventType(16384));
        // vendor l0():109 — 省电策略配置
        list.add(new AutoEngine.WindowMatcher(POWER_KEEPER, HIDDEN_APPS_CONFIG_ACTIVITY)
                .addEventType(32).addEventType(16384));
        // vendor l0():110 — 电量详情
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, POWER_DETAIL_ACTIVITY)
                .addEventType(32).addEventType(16384));
        // vendor l0():111-114 — 权限编辑/其他权限/权限修改
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, PERMISSIONS_EDITOR_ACTIVITY)
                .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, OTHER_PERMISSIONS_ACTIVITY)
                .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, PERMISSION_APPS_MODIFY)
                .addEventType(32).addEventType(16384));
        // vendor l0():126-135 — MIUI AlertDialog
        list.add(new AutoEngine.WindowMatcher(POWER_KEEPER, MIUI_ALERT_DIALOG)
                .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SECURITY_CENTER, MIUI_ALERT_DIALOG)
                .addEventType(32).addEventType(16384));
        return list;
    }

    @Test
    public void windowMatchers_containsAllExpectedWindows() {
        List<AutoEngine.WindowMatcher> list = buildWindowMatchers();
        assertEquals(16, list.size());
    }

    @Test
    public void windowMatchers_autoStartActivity_matchesWithEventType32() {
        List<AutoEngine.WindowMatcher> list = buildWindowMatchers();
        boolean matched = false;
        for (AutoEngine.WindowMatcher m : list) {
            if (m.matches(SECURITY_CENTER, AUTO_START_ACTIVITY, 32)) {
                matched = true;
                break;
            }
        }
        assertTrue(matched);
    }

    @Test
    public void windowMatchers_autoStartActivity_matchesWithEventType16384() {
        List<AutoEngine.WindowMatcher> list = buildWindowMatchers();
        boolean matched = false;
        for (AutoEngine.WindowMatcher m : list) {
            if (m.matches(SECURITY_CENTER, AUTO_START_ACTIVITY, 16384)) {
                matched = true;
                break;
            }
        }
        assertTrue(matched);
    }

    @Test
    public void windowMatchers_alertDialog_matchesBothPackages() {
        List<AutoEngine.WindowMatcher> list = buildWindowMatchers();
        boolean miuiMatched = false;
        boolean powerMatched = false;
        for (AutoEngine.WindowMatcher m : list) {
            if (m.matches(SECURITY_CENTER, ALERT_DIALOG, 32)) miuiMatched = true;
            if (m.matches(POWER_KEEPER, ALERT_DIALOG, 32)) powerMatched = true;
        }
        assertTrue(miuiMatched);
        assertTrue(powerMatched);
    }

    @Test
    public void windowMatchers_hiddenAppsActivity_matchesPowerKeeper() {
        List<AutoEngine.WindowMatcher> list = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(list, POWER_KEEPER, HIDDEN_APPS_ACTIVITY, 32));
    }

    // ============ 边界 ============

    @Test
    public void emptyList_returnsFalse() {
        assertFalse(matchesAny(new ArrayList<AutoEngine.WindowMatcher>(),
                SECURITY_CENTER, AUTO_START_ACTIVITY));
    }

    @Test
    public void nullPackage_returnsFalse() {
        assertFalse(matchesAny(autoStartWins, null, AUTO_START_ACTIVITY));
    }

    @Test
    public void wrongPackage_returnsFalse() {
        assertFalse(matchesAny(autoStartWins, "com.wrong.package", AUTO_START_ACTIVITY));
    }

    @Test
    public void wrongClassName_returnsFalse() {
        assertFalse(matchesAny(autoStartWins, SECURITY_CENTER, "com.wrong.Class"));
    }

    // ============ Phase 1: 补全 ListenWindow 验证 ============

    private static final String PERMISSION_APPS_MODIFY =
            "com.miui.permcenter.permissions.PermissionAppsModifyActivity";
    private static final String MIUI_ALERT_DIALOG = "miuix.appcompat.app.AlertDialog";
    private static final String APP_DETAILS =
            "com.miui.appmanager.ApplicationsDetailsActivity";

    @Test
    public void windowMatchers_permissionAppsModifyActivity_matches() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(matchers, SECURITY_CENTER, PERMISSION_APPS_MODIFY, 32));
    }

    @Test
    public void windowMatchers_miuiAlertDialog_powerKeeper_matches() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(matchers, POWER_KEEPER, MIUI_ALERT_DIALOG, 32));
    }

    @Test
    public void windowMatchers_miuiAlertDialog_securityCenter_matches() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(matchers, SECURITY_CENTER, MIUI_ALERT_DIALOG, 32));
    }

    @Test
    public void windowMatchers_appDetailsActivity_matches() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(matchers, SECURITY_CENTER, APP_DETAILS, 32));
    }

    @Test
    public void windowMatchers_appManagerMainActivity_matches() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(matchers, SECURITY_CENTER, APP_MANAGER_MAIN, 32));
    }

    @Test
    public void windowMatchers_frameLayout_matches() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(matchers, SECURITY_CENTER, FRAME_LAYOUT, 32));
    }

    @Test
    public void windowMatchers_powerDetailActivity_matches() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertTrue(matchesAnyWithEvent(matchers, SECURITY_CENTER, POWER_DETAIL_ACTIVITY, 32));
    }

    @Test
    public void windowMatchers_totalCount_is16() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertEquals(16, matchers.size());
    }
}
