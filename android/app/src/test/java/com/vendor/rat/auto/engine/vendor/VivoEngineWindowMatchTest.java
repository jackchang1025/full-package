package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * VivoEngine 窗口匹配测试 — 验证 17 个 ListenWindow 对齐 vendor o/i0.java u0()
 */
public class VivoEngineWindowMatchTest {

    // ====== 包名常量 ======
    private static final String SETTINGS = "com.android.settings";
    private static final String PERMISSION_CONTROLLER = "com.android.permissioncontroller";
    private static final String PERMISSION_MANAGER = "com.vivo.permissionmanager";
    private static final String VIVO_ABE = "com.vivo.abe";
    private static final String IQOO_POWERSAVING = "com.iqoo.powersaving";

    // ====== Activity 常量 ======
    private static final String INSTALLED_APP_DETAILS =
        "com.vivo.settings.applications.InstalledAppDetailsTop";
    private static final String VIVO_SUB_SETTINGS =
        "com.vivo.settings.VivoSubSettings";
    private static final String MANAGE_PERMISSIONS =
        "com.android.permissioncontroller.permission.ui.ManagePermissionsActivity";
    private static final String SOFT_PERMISSION_DETAIL =
        "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity";
    private static final String VIVO_DIALOG = "com.originui.widget.dialog.h";
    private static final String POWER_RANK =
        "com.iqoo.powersaving.fuelgauge.PowerRankActivity";
    private static final String EXCESSIVE_POWER =
        "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity";
    private static final String EXCESSIVE_POWER_DESC =
        "com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity";

    // 窗口检测分组
    private List<AutoEngine.WindowMatcher> appDetailWins;
    private List<AutoEngine.WindowMatcher> permDetailWins;
    private List<AutoEngine.WindowMatcher> permManageWins;
    private List<AutoEngine.WindowMatcher> excessiveDescWins;
    private List<AutoEngine.WindowMatcher> excessivePowerWins;
    private List<AutoEngine.WindowMatcher> permDialogWins;
    private List<AutoEngine.WindowMatcher> powerRankWins;

    @Before
    public void setUp() {
        appDetailWins = new ArrayList<>();
        appDetailWins.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new AutoEngine.WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
            .addEventType(32).addEventType(16384));

        permDetailWins = new ArrayList<>();
        permDetailWins.add(new AutoEngine.WindowMatcher(PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL)
            .addEventType(32).addEventType(16384));
        permDetailWins.add(new AutoEngine.WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));

        permManageWins = new ArrayList<>();
        permManageWins.add(new AutoEngine.WindowMatcher(PERMISSION_CONTROLLER, MANAGE_PERMISSIONS)
            .addEventType(32).addEventType(16384));
        permManageWins.add(new AutoEngine.WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        permManageWins.add(new AutoEngine.WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));

        excessiveDescWins = new ArrayList<>();
        excessiveDescWins.add(new AutoEngine.WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_DESC)
            .addEventType(32).addEventType(16384));
        excessiveDescWins.add(new AutoEngine.WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_DESC)
            .addEventType(32).addEventType(16384));

        excessivePowerWins = new ArrayList<>();
        excessivePowerWins.add(new AutoEngine.WindowMatcher(VIVO_ABE, EXCESSIVE_POWER)
            .addEventType(32).addEventType(16384));
        excessivePowerWins.add(new AutoEngine.WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER)
            .addEventType(32).addEventType(16384));

        permDialogWins = new ArrayList<>();
        permDialogWins.add(new AutoEngine.WindowMatcher(PERMISSION_MANAGER, VIVO_DIALOG)
            .addEventType(32).addEventType(16384));
        permDialogWins.add(new AutoEngine.WindowMatcher(PERMISSION_MANAGER, "android.app.AlertDialog")
            .addEventType(32).addEventType(16384));

        powerRankWins = new ArrayList<>();
        powerRankWins.add(new AutoEngine.WindowMatcher(IQOO_POWERSAVING, POWER_RANK)
            .addEventType(32).addEventType(16384));
    }

    // ====== 工具方法 ======

    private boolean matchesAny(List<AutoEngine.WindowMatcher> matchers,
                               String pkg, String cls, int eventType) {
        for (AutoEngine.WindowMatcher m : matchers) {
            if (m.matches(pkg, cls, eventType)) return true;
        }
        return false;
    }

    // ====== buildWindowMatchers 总数 ======

    @Test
    public void windowMatchers_totalCount_is17() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertEquals(17, matchers.size());
    }

    // ====== 逐个 ListenWindow 匹配验证 ======

    @Test
    public void windowMatchers_batteryDialog_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, "android.app.Dialog", 32));
    }

    @Test
    public void windowMatchers_installedAppDetailsTop_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    @Test
    public void windowMatchers_vivoSubSettings_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, VIVO_SUB_SETTINGS, 32));
    }

    @Test
    public void windowMatchers_managePermissions_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, PERMISSION_CONTROLLER, MANAGE_PERMISSIONS, 32));
    }

    @Test
    public void windowMatchers_frameLayout_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, "android.widget.FrameLayout", 32));
    }

    @Test
    public void windowMatchers_softPermissionDetail_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL, 32));
    }

    @Test
    public void windowMatchers_vivoDialog_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, PERMISSION_MANAGER, VIVO_DIALOG, 32));
    }

    @Test
    public void windowMatchers_alertDialog_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, PERMISSION_MANAGER, "android.app.AlertDialog", 32));
    }

    @Test
    public void windowMatchers_powerRank_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, IQOO_POWERSAVING, POWER_RANK, 32));
    }

    @Test
    public void windowMatchers_vivoExcessivePower_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, VIVO_ABE, EXCESSIVE_POWER, 32));
    }

    @Test
    public void windowMatchers_iqooExcessivePower_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, IQOO_POWERSAVING, EXCESSIVE_POWER, 32));
    }

    @Test
    public void windowMatchers_vivoExcessivePowerDesc_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, VIVO_ABE, EXCESSIVE_POWER_DESC, 32));
    }

    @Test
    public void windowMatchers_iqooExcessivePowerDesc_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, IQOO_POWERSAVING, EXCESSIVE_POWER_DESC, 32));
    }

    // ====== 窗口检测分组: j0 ======

    @Test
    public void j0_installedAppDetails_matches() {
        assertTrue(matchesAny(appDetailWins, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    @Test
    public void j0_vivoSubSettings_matches() {
        assertTrue(matchesAny(appDetailWins, SETTINGS, VIVO_SUB_SETTINGS, 32));
    }

    @Test
    public void j0_wrongWindow_returnsFalse() {
        assertFalse(matchesAny(appDetailWins, IQOO_POWERSAVING, POWER_RANK, 32));
    }

    // ====== 窗口检测分组: k0 ======

    @Test
    public void k0_softPermissionDetail_matches() {
        assertTrue(matchesAny(permDetailWins, PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL, 32));
    }

    // ====== 窗口检测分组: l0 ======

    @Test
    public void l0_managePermissions_matches() {
        assertTrue(matchesAny(permManageWins, PERMISSION_CONTROLLER, MANAGE_PERMISSIONS, 32));
    }

    @Test
    public void l0_frameLayout_matches() {
        assertTrue(matchesAny(permManageWins, SETTINGS, "android.widget.FrameLayout", 32));
    }

    // ====== 窗口检测分组: m0 ======

    @Test
    public void m0_vivoAbe_matches() {
        assertTrue(matchesAny(excessiveDescWins, VIVO_ABE, EXCESSIVE_POWER_DESC, 32));
    }

    @Test
    public void m0_iqoo_matches() {
        assertTrue(matchesAny(excessiveDescWins, IQOO_POWERSAVING, EXCESSIVE_POWER_DESC, 32));
    }

    // ====== 窗口检测分组: n0 ======

    @Test
    public void n0_vivoAbe_matches() {
        assertTrue(matchesAny(excessivePowerWins, VIVO_ABE, EXCESSIVE_POWER, 32));
    }

    @Test
    public void n0_iqoo_matches() {
        assertTrue(matchesAny(excessivePowerWins, IQOO_POWERSAVING, EXCESSIVE_POWER, 32));
    }

    // ====== 窗口检测分组: o0 ======

    @Test
    public void o0_vivoDialog_matches() {
        assertTrue(matchesAny(permDialogWins, PERMISSION_MANAGER, VIVO_DIALOG, 32));
    }

    @Test
    public void o0_alertDialog_matches() {
        assertTrue(matchesAny(permDialogWins, PERMISSION_MANAGER, "android.app.AlertDialog", 32));
    }

    // ====== 窗口检测分组: p0 ======

    @Test
    public void p0_powerRank_matches() {
        assertTrue(matchesAny(powerRankWins, IQOO_POWERSAVING, POWER_RANK, 32));
    }

    @Test
    public void p0_wrongPackage_returnsFalse() {
        assertFalse(matchesAny(powerRankWins, VIVO_ABE, POWER_RANK, 32));
    }

    // ====== 互斥测试 ======

    @Test
    public void crossGroup_powerRank_notInAppDetail() {
        assertFalse(matchesAny(appDetailWins, IQOO_POWERSAVING, POWER_RANK, 32));
    }

    @Test
    public void crossGroup_appDetail_notInPowerRank() {
        assertFalse(matchesAny(powerRankWins, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    // ====== 重建 buildWindowMatchers ======

    private List<AutoEngine.WindowMatcher> buildWindowMatchers() {
        List<AutoEngine.WindowMatcher> list = new ArrayList<>();
        list.add(new AutoEngine.WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, VIVO_SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(PERMISSION_CONTROLLER, MANAGE_PERMISSIONS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(PERMISSION_MANAGER, SOFT_PERMISSION_DETAIL)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(null, null)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(PERMISSION_MANAGER, VIVO_DIALOG)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(PERMISSION_MANAGER, "android.app.AlertDialog")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(IQOO_POWERSAVING, POWER_RANK)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(VIVO_ABE, EXCESSIVE_POWER)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(VIVO_ABE, EXCESSIVE_POWER_DESC)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(IQOO_POWERSAVING, EXCESSIVE_POWER_DESC)
            .addEventType(32).addEventType(16384));
        return list;
    }
}
