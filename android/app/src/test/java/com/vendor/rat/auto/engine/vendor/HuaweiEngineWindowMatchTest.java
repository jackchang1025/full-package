package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * HuaweiEngine 四组窗口检测 (j0/i0/k0/h0) 测试
 *
 * 复制 HuaweiEngine 的常量和 buildDetectionGroups 逻辑，
 * 验证每个窗口只匹配正确的组。
 *
 * 纯 JVM 测试，无需 Android 框架。
 */
public class HuaweiEngineWindowMatchTest {

    // ====== 常量 — 复制自 HuaweiEngine ======
    private static final String HUAWEI_SM = "com.huawei.systemmanager";
    private static final String HONOR_SM = "com.hihonor.systemmanager";
    private static final String SETTINGS = "com.android.settings";

    private static final String HW_SETTINGS = "com.android.settings.HWSettings";
    private static final String SUB_SETTINGS = "com.android.settings.SubSettings";
    private static final String CLEAN_SUB_SETTINGS = "com.android.settings.CleanSubSettings";
    private static final String APP_AND_NOTIFICATION =
            "com.android.settings.Settings$AppAndNotificationDashboardActivity";
    private static final String INSTALLED_APP_DETAILS =
            "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String STARTUP_APP_CONTROL =
            "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity";
    private static final String STARTUP_NORMAL_LIST =
            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity";
    private static final String HONOR_STARTUP_APP_CONTROL =
            "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity";
    private static final String ALERT_DIALOG = "android.app.AlertDialog";

    // ====== 检测分组 — 复制自 HuaweiEngine.buildDetectionGroups() ======
    private List<AutoEngine.WindowMatcher> hwSettingsWins;
    private List<AutoEngine.WindowMatcher> appNotifWins;
    private List<AutoEngine.WindowMatcher> startupWindows;
    private List<AutoEngine.WindowMatcher> dialogWins;

    @Before
    public void setUp() {
        hwSettingsWins = new ArrayList<>();
        hwSettingsWins.add(new AutoEngine.WindowMatcher(SETTINGS, HW_SETTINGS));
        hwSettingsWins.add(new AutoEngine.WindowMatcher(SETTINGS, CLEAN_SUB_SETTINGS));

        appNotifWins = new ArrayList<>();
        appNotifWins.add(new AutoEngine.WindowMatcher(SETTINGS, SUB_SETTINGS));
        appNotifWins.add(new AutoEngine.WindowMatcher(SETTINGS, APP_AND_NOTIFICATION));
        appNotifWins.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS));

        startupWindows = new ArrayList<>();
        startupWindows.add(new AutoEngine.WindowMatcher(HUAWEI_SM, STARTUP_APP_CONTROL));
        startupWindows.add(new AutoEngine.WindowMatcher(HUAWEI_SM, STARTUP_NORMAL_LIST));
        startupWindows.add(new AutoEngine.WindowMatcher(HONOR_SM, HONOR_STARTUP_APP_CONTROL));

        dialogWins = new ArrayList<>();
        dialogWins.add(new AutoEngine.WindowMatcher(HUAWEI_SM, ALERT_DIALOG));
        dialogWins.add(new AutoEngine.WindowMatcher(HONOR_SM, ALERT_DIALOG));
    }

    /** 辅助: 模拟 matchesAny — 与 AutoEngine.matchesAny 逻辑一致 (eventType=0) */
    private boolean matchesAny(List<AutoEngine.WindowMatcher> matchers, String pkg, String cls) {
        for (AutoEngine.WindowMatcher m : matchers) {
            if (m.matches(pkg, cls, 0)) return true;
        }
        return false;
    }

    // ============ hwSettingsWins (j0) ============

    @Test
    public void hwSettings_matchesHWSettings() {
        assertTrue(matchesAny(hwSettingsWins, SETTINGS, HW_SETTINGS));
    }

    @Test
    public void hwSettings_matchesCleanSubSettings() {
        assertTrue(matchesAny(hwSettingsWins, SETTINGS, CLEAN_SUB_SETTINGS));
    }

    @Test
    public void hwSettings_rejectsSubSettings() {
        assertFalse(matchesAny(hwSettingsWins, SETTINGS, SUB_SETTINGS));
    }

    // ============ appNotifWins (i0) ============

    @Test
    public void appNotif_matchesSubSettings() {
        assertTrue(matchesAny(appNotifWins, SETTINGS, SUB_SETTINGS));
    }

    @Test
    public void appNotif_matchesAppAndNotification() {
        assertTrue(matchesAny(appNotifWins, SETTINGS, APP_AND_NOTIFICATION));
    }

    @Test
    public void appNotif_matchesInstalledAppDetails() {
        assertTrue(matchesAny(appNotifWins, SETTINGS, INSTALLED_APP_DETAILS));
    }

    @Test
    public void appNotif_rejectsHWSettings() {
        assertFalse(matchesAny(appNotifWins, SETTINGS, HW_SETTINGS));
    }

    // ============ startupWindows (k0) ============

    @Test
    public void startup_matchesStartupAppControl() {
        assertTrue(matchesAny(startupWindows, HUAWEI_SM, STARTUP_APP_CONTROL));
    }

    @Test
    public void startup_matchesStartupNormalList() {
        assertTrue(matchesAny(startupWindows, HUAWEI_SM, STARTUP_NORMAL_LIST));
    }

    @Test
    public void startup_matchesHonorStartup() {
        assertTrue(matchesAny(startupWindows, HONOR_SM, HONOR_STARTUP_APP_CONTROL));
    }

    @Test
    public void startup_rejectsHWSettings() {
        assertFalse(matchesAny(startupWindows, SETTINGS, HW_SETTINGS));
    }

    // ============ dialogWins (h0) ============

    @Test
    public void dialog_matchesHuaweiAlertDialog() {
        assertTrue(matchesAny(dialogWins, HUAWEI_SM, ALERT_DIALOG));
    }

    @Test
    public void dialog_matchesHonorAlertDialog() {
        assertTrue(matchesAny(dialogWins, HONOR_SM, ALERT_DIALOG));
    }

    @Test
    public void dialog_rejectsSettingsAlertDialog() {
        assertFalse(matchesAny(dialogWins, SETTINGS, ALERT_DIALOG));
    }

    // ============ 跨组互斥 ============

    @Test
    public void crossGroup_hwSettingsOnlyInHwGroup() {
        assertTrue(matchesAny(hwSettingsWins, SETTINGS, HW_SETTINGS));
        assertFalse(matchesAny(appNotifWins, SETTINGS, HW_SETTINGS));
        assertFalse(matchesAny(startupWindows, SETTINGS, HW_SETTINGS));
        assertFalse(matchesAny(dialogWins, SETTINGS, HW_SETTINGS));
    }

    @Test
    public void crossGroup_subSettingsOnlyInAppNotifGroup() {
        assertFalse(matchesAny(hwSettingsWins, SETTINGS, SUB_SETTINGS));
        assertTrue(matchesAny(appNotifWins, SETTINGS, SUB_SETTINGS));
        assertFalse(matchesAny(startupWindows, SETTINGS, SUB_SETTINGS));
        assertFalse(matchesAny(dialogWins, SETTINGS, SUB_SETTINGS));
    }

    @Test
    public void crossGroup_startupOnlyInStartupGroup() {
        assertFalse(matchesAny(hwSettingsWins, HUAWEI_SM, STARTUP_APP_CONTROL));
        assertFalse(matchesAny(appNotifWins, HUAWEI_SM, STARTUP_APP_CONTROL));
        assertTrue(matchesAny(startupWindows, HUAWEI_SM, STARTUP_APP_CONTROL));
        assertFalse(matchesAny(dialogWins, HUAWEI_SM, STARTUP_APP_CONTROL));
    }

    @Test
    public void crossGroup_dialogOnlyInDialogGroup() {
        assertFalse(matchesAny(hwSettingsWins, HUAWEI_SM, ALERT_DIALOG));
        assertFalse(matchesAny(appNotifWins, HUAWEI_SM, ALERT_DIALOG));
        assertFalse(matchesAny(startupWindows, HUAWEI_SM, ALERT_DIALOG));
        assertTrue(matchesAny(dialogWins, HUAWEI_SM, ALERT_DIALOG));
    }

    // ============ 边界 ============

    @Test
    public void emptyList_returnsFalse() {
        assertFalse(matchesAny(new ArrayList<AutoEngine.WindowMatcher>(), SETTINGS, HW_SETTINGS));
    }

    @Test
    public void nullPackage_returnsFalse() {
        assertFalse(matchesAny(hwSettingsWins, null, HW_SETTINGS));
    }

    @Test
    public void wrongPackage_returnsFalse() {
        assertFalse(matchesAny(hwSettingsWins, "com.wrong.package", HW_SETTINGS));
    }
}
