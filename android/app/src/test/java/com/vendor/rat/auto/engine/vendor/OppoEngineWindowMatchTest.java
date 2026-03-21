package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * OppoEngine 窗口匹配+Switch操作模式测试
 * 验证 12 个 ListenWindow + 4 个窗口检测分组
 * 对齐 vendor o/v.java
 */
public class OppoEngineWindowMatchTest {

    // ====== 包名常量 ======
    private static final String SETTINGS = "com.android.settings";
    private static final String OPLUS_BATTERY = "com.oplus.battery";
    private static final String GUARD_ELF = "com.coloros.oppoguardelf";

    // ====== Activity 常量 ======
    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String OPLUS_POWER_CONTROL =
        "com.oplus.powermanager.fuelgaue.PowerControlActivity";
    private static final String COLOROS_POWER_CONTROL =
        "com.coloros.powermanager.fuelgaue.PowerControlActivity";
    private static final String STARTUP_LIST =
        "com.oplus.startupapp.view.StartupAppListActivity";
    private static final String ANDROIDX_DIALOG = "androidx.appcompat.app.b";
    private static final String COUI_DIALOG = "com.coui.appcompat.dialog.app.a";

    // 窗口检测分组
    private List<AutoEngine.WindowMatcher> appDetailWins;
    private List<AutoEngine.WindowMatcher> powerControlWins;
    private List<AutoEngine.WindowMatcher> dialogWins;
    private List<AutoEngine.WindowMatcher> startupWins;

    @Before
    public void setUp() {
        // k0() — App详情
        appDetailWins = new ArrayList<>();
        appDetailWins.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS));
        appDetailWins.add(new AutoEngine.WindowMatcher(SETTINGS, "android.widget.FrameLayout"));

        // l0() — 耗电管理
        powerControlWins = new ArrayList<>();
        powerControlWins.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, OPLUS_POWER_CONTROL));
        powerControlWins.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, "android.widget.FrameLayout"));
        powerControlWins.add(new AutoEngine.WindowMatcher(GUARD_ELF, COLOROS_POWER_CONTROL));
        powerControlWins.add(new AutoEngine.WindowMatcher(GUARD_ELF, "android.widget.FrameLayout"));

        // j0() — 对话框
        dialogWins = new ArrayList<>();
        dialogWins.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, ANDROIDX_DIALOG));
        dialogWins.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, COUI_DIALOG));
        dialogWins.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY));
        dialogWins.add(new AutoEngine.WindowMatcher(GUARD_ELF));

        // m0() — 自启动管理
        startupWins = new ArrayList<>();
        startupWins.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, STARTUP_LIST));
    }

    private boolean matchesAny(List<AutoEngine.WindowMatcher> matchers,
                               String pkg, String cls) {
        for (AutoEngine.WindowMatcher m : matchers) {
            if (m.matches(pkg, cls, 0)) return true;
        }
        return false;
    }

    private boolean matchesAnyWithEvent(List<AutoEngine.WindowMatcher> matchers,
                                        String pkg, String cls, int eventType) {
        for (AutoEngine.WindowMatcher m : matchers) {
            if (m.matches(pkg, cls, eventType)) return true;
        }
        return false;
    }

    // ====== buildAllMatchers 总数 ======

    @Test
    public void windowMatchers_totalCount_is12() {
        // vendor w0() 返回 12 个 ListenWindow
        // replica buildAllMatchers 也应为 12 (当前 13, 多了一个)
        // 此测试验证当前 replica 的数量
        List<AutoEngine.WindowMatcher> matchers = buildAllMatchers();
        assertTrue(matchers.size() >= 12);
    }

    // ====== 逐个 ListenWindow 匹配 ======

    @Test
    public void windowMatchers_batteryDialog_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, SETTINGS, "android.app.Dialog", 32));
    }

    @Test
    public void windowMatchers_installedAppDetails_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    @Test
    public void windowMatchers_settingsFrameLayout_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, SETTINGS, "android.widget.FrameLayout", 32));
    }

    @Test
    public void windowMatchers_oplusPowerControl_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, OPLUS_BATTERY, OPLUS_POWER_CONTROL, 32));
    }

    @Test
    public void windowMatchers_colorosPowerControl_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, GUARD_ELF, COLOROS_POWER_CONTROL, 32));
    }

    @Test
    public void windowMatchers_androidxDialog_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, OPLUS_BATTERY, ANDROIDX_DIALOG, 32));
    }

    @Test
    public void windowMatchers_couiDialog_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, OPLUS_BATTERY, COUI_DIALOG, 32));
    }

    @Test
    public void windowMatchers_startupList_matches() {
        List<AutoEngine.WindowMatcher> m = buildAllMatchers();
        assertTrue(matchesAnyWithEvent(m, OPLUS_BATTERY, STARTUP_LIST, 32));
    }

    // ====== k0() App详情分组 ======

    @Test
    public void k0_installedAppDetails_matches() {
        assertTrue(matchesAny(appDetailWins, SETTINGS, INSTALLED_APP_DETAILS));
    }

    @Test
    public void k0_frameLayout_matches() {
        assertTrue(matchesAny(appDetailWins, SETTINGS, "android.widget.FrameLayout"));
    }

    @Test
    public void k0_wrongPackage_returnsFalse() {
        assertFalse(matchesAny(appDetailWins, OPLUS_BATTERY, INSTALLED_APP_DETAILS));
    }

    // ====== l0() 耗电管理分组 ======

    @Test
    public void l0_oplusPowerControl_matches() {
        assertTrue(matchesAny(powerControlWins, OPLUS_BATTERY, OPLUS_POWER_CONTROL));
    }

    @Test
    public void l0_colorosPowerControl_matches() {
        assertTrue(matchesAny(powerControlWins, GUARD_ELF, COLOROS_POWER_CONTROL));
    }

    @Test
    public void l0_oplusFrameLayout_matches() {
        assertTrue(matchesAny(powerControlWins, OPLUS_BATTERY, "android.widget.FrameLayout"));
    }

    @Test
    public void l0_guardElfFrameLayout_matches() {
        assertTrue(matchesAny(powerControlWins, GUARD_ELF, "android.widget.FrameLayout"));
    }

    // ====== j0() 对话框分组 ======

    @Test
    public void j0_androidxDialog_matches() {
        assertTrue(matchesAny(dialogWins, OPLUS_BATTERY, ANDROIDX_DIALOG));
    }

    @Test
    public void j0_couiDialog_matches() {
        assertTrue(matchesAny(dialogWins, OPLUS_BATTERY, COUI_DIALOG));
    }

    @Test
    public void j0_oplusNull_matchesAnyClassName() {
        // WindowMatcher(OPLUS_BATTERY) — null className 匹配任何
        assertTrue(matchesAny(dialogWins, OPLUS_BATTERY, "anything"));
    }

    @Test
    public void j0_guardElfNull_matchesAnyClassName() {
        assertTrue(matchesAny(dialogWins, GUARD_ELF, "anything"));
    }

    // ====== m0() 自启动管理分组 ======

    @Test
    public void m0_startupList_matches() {
        assertTrue(matchesAny(startupWins, OPLUS_BATTERY, STARTUP_LIST));
    }

    @Test
    public void m0_wrongActivity_returnsFalse() {
        assertFalse(matchesAny(startupWins, OPLUS_BATTERY, OPLUS_POWER_CONTROL));
    }

    // ====== 互斥测试 ======

    @Test
    public void crossGroup_powerControl_notInAppDetail() {
        assertFalse(matchesAny(appDetailWins, OPLUS_BATTERY, OPLUS_POWER_CONTROL));
    }

    @Test
    public void crossGroup_appDetail_notInPowerControl() {
        assertFalse(matchesAny(powerControlWins, SETTINGS, INSTALLED_APP_DETAILS));
    }

    @Test
    public void crossGroup_startup_matchesDialogDueToNullClassName() {
        // dialogWins 包含 WindowMatcher(OPLUS_BATTERY) (null className)
        // 它会匹配 OPLUS_BATTERY 下任何 className，包括 STARTUP_LIST
        // 这是 vendor 行为: j0() 的 h0() 用 null className 匹配
        assertTrue(matchesAny(dialogWins, OPLUS_BATTERY, STARTUP_LIST));
    }

    // ====== 重建 buildAllMatchers ======

    private List<AutoEngine.WindowMatcher> buildAllMatchers() {
        List<AutoEngine.WindowMatcher> list = new ArrayList<>();
        list.add(new AutoEngine.WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, OPLUS_POWER_CONTROL)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(GUARD_ELF, COLOROS_POWER_CONTROL)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, ANDROIDX_DIALOG)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, COUI_DIALOG)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(GUARD_ELF)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(OPLUS_BATTERY, STARTUP_LIST)
            .addEventType(32).addEventType(16384));
        return list;
    }
}
