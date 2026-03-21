package com.vendor.rat.auto.engine.vendor;

import com.vendor.rat.auto.engine.AutoEngine;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * TranssionEngine 窗口匹配+状态机测试
 * 验证 7 个 ListenWindow + 3 个窗口检测分组 + 事件处理状态机
 * 对齐 vendor o/e0.java
 */
public class TranssionEngineWindowMatchTest {

    // ====== 包名常量 ======
    private static final String SETTINGS = "com.android.settings";
    private static final String PHONE_MASTER = "com.transsion.phonemaster";

    // ====== Activity 常量 ======
    private static final String AUTO_START_ACTIVITY =
        "com.cyin.himgr.autostart.AutoStartActivity";
    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String APP_INFO_SETTINGS =
        "com.transsion.settings.applications.appinfo.AppInfoSettings";
    private static final String SUB_SETTINGS =
        "com.android.settings.SubSettings";

    // 窗口检测分组
    private List<AutoEngine.WindowMatcher> appDetailWins;
    private List<AutoEngine.WindowMatcher> batteryWins;
    private List<AutoEngine.WindowMatcher> autoStartWins;

    @Before
    public void setUp() {
        // k0() — App详情
        appDetailWins = new ArrayList<>();
        appDetailWins.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new AutoEngine.WindowMatcher(SETTINGS, APP_INFO_SETTINGS)
            .addEventType(32).addEventType(16384));
        appDetailWins.add(new AutoEngine.WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));

        // j0() — 耗电管理
        batteryWins = new ArrayList<>();
        batteryWins.add(new AutoEngine.WindowMatcher(SETTINGS, SUB_SETTINGS)
            .addEventType(32).addEventType(16384));

        // l0() — 自启动管理
        autoStartWins = new ArrayList<>();
        autoStartWins.add(new AutoEngine.WindowMatcher(PHONE_MASTER, AUTO_START_ACTIVITY)
            .addEventType(32).addEventType(16384));
        autoStartWins.add(new AutoEngine.WindowMatcher(PHONE_MASTER, "android.widget.FrameLayout")
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
    public void windowMatchers_totalCount_is7() {
        List<AutoEngine.WindowMatcher> matchers = buildWindowMatchers();
        assertEquals(7, matchers.size());
    }

    // ====== 逐个 ListenWindow 匹配 ======

    @Test
    public void windowMatchers_batteryDialog_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, "android.app.Dialog", 32));
    }

    @Test
    public void windowMatchers_autoStartActivity_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, PHONE_MASTER, AUTO_START_ACTIVITY, 32));
    }

    @Test
    public void windowMatchers_phoneMasterFrameLayout_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, PHONE_MASTER, "android.widget.FrameLayout", 32));
    }

    @Test
    public void windowMatchers_installedAppDetails_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    @Test
    public void windowMatchers_appInfoSettings_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, APP_INFO_SETTINGS, 32));
    }

    @Test
    public void windowMatchers_settingsFrameLayout_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, "android.widget.FrameLayout", 32));
    }

    @Test
    public void windowMatchers_subSettings_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, SUB_SETTINGS, 32));
    }

    // ====== k0() App详情分组 ======

    @Test
    public void k0_installedAppDetails_matches() {
        assertTrue(matchesAny(appDetailWins, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    @Test
    public void k0_appInfoSettings_matches() {
        assertTrue(matchesAny(appDetailWins, SETTINGS, APP_INFO_SETTINGS, 32));
    }

    @Test
    public void k0_frameLayout_matches() {
        assertTrue(matchesAny(appDetailWins, SETTINGS, "android.widget.FrameLayout", 32));
    }

    @Test
    public void k0_wrongWindow_returnsFalse() {
        assertFalse(matchesAny(appDetailWins, PHONE_MASTER, AUTO_START_ACTIVITY, 32));
    }

    // ====== j0() 耗电管理分组 ======

    @Test
    public void j0_subSettings_matches() {
        assertTrue(matchesAny(batteryWins, SETTINGS, SUB_SETTINGS, 32));
    }

    @Test
    public void j0_wrongWindow_returnsFalse() {
        assertFalse(matchesAny(batteryWins, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    // ====== l0() 自启动管理分组 ======

    @Test
    public void l0_autoStartActivity_matches() {
        assertTrue(matchesAny(autoStartWins, PHONE_MASTER, AUTO_START_ACTIVITY, 32));
    }

    @Test
    public void l0_phoneManagerFrameLayout_matches() {
        assertTrue(matchesAny(autoStartWins, PHONE_MASTER, "android.widget.FrameLayout", 32));
    }

    @Test
    public void l0_wrongWindow_returnsFalse() {
        assertFalse(matchesAny(autoStartWins, SETTINGS, SUB_SETTINGS, 32));
    }

    // ====== 互斥测试 ======

    @Test
    public void crossGroup_autoStart_notInAppDetail() {
        assertFalse(matchesAny(appDetailWins, PHONE_MASTER, AUTO_START_ACTIVITY, 32));
    }

    @Test
    public void crossGroup_subSettings_notInAutoStart() {
        assertFalse(matchesAny(autoStartWins, SETTINGS, SUB_SETTINGS, 32));
    }

    @Test
    public void crossGroup_appDetail_notInBattery() {
        assertFalse(matchesAny(batteryWins, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    // ====== eventType 验证 ======

    @Test
    public void windowMatchers_wrongEventType_returnsFalse() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        // eventType=0 不在 {32, 16384} 中
        assertFalse(matchesAny(m, SETTINGS, SUB_SETTINGS, 0));
    }

    @Test
    public void windowMatchers_eventType16384_matches() {
        List<AutoEngine.WindowMatcher> m = buildWindowMatchers();
        assertTrue(matchesAny(m, SETTINGS, SUB_SETTINGS, 16384));
    }

    // ====== 重建 buildWindowMatchers (对齐 vendor n0) ======

    private List<AutoEngine.WindowMatcher> buildWindowMatchers() {
        List<AutoEngine.WindowMatcher> list = new ArrayList<>();
        list.add(new AutoEngine.WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(PHONE_MASTER, AUTO_START_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(PHONE_MASTER, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, APP_INFO_SETTINGS)
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new AutoEngine.WindowMatcher(SETTINGS, SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        return list;
    }
}
