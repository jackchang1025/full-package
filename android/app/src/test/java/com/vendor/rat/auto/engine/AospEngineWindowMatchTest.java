package com.vendor.rat.auto.engine;

import com.vendor.rat.auto.engine.AutoEngine.WindowMatcher;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * AOSP ListenWindow 匹配测试 (对齐 vendor o/g.java k0())
 */
public class AospEngineWindowMatchTest {

    private static final String SETTINGS = "com.android.settings";

    private static final String INSTALLED_APP_DETAILS =
        "com.android.settings.applications.InstalledAppDetailsTop";
    private static final String SPA_ACTIVITY =
        "com.android.settings.spa.SpaActivity";
    private static final String SUB_SETTINGS =
        "com.android.settings.SubSettings";

    private List<WindowMatcher> matchers;

    @Before
    public void setUp() {
        matchers = buildWindowMatchers();
    }

    private boolean matchesAny(List<WindowMatcher> list, String pkg, String cls, int eventType) {
        for (WindowMatcher m : list) {
            if (m.matches(pkg, cls, eventType)) return true;
        }
        return false;
    }

    @Test
    public void windowMatchers_totalCount_is8() {
        assertEquals(8, matchers.size());
    }

    @Test
    public void windowMatchers_batteryDialog_matches() {
        assertTrue(matchesAny(matchers, SETTINGS, "android.app.Dialog", 32));
    }

    @Test
    public void windowMatchers_installedAppDetails_matches() {
        assertTrue(matchesAny(matchers, SETTINGS, INSTALLED_APP_DETAILS, 32));
    }

    @Test
    public void windowMatchers_spaActivity_matches() {
        assertTrue(matchesAny(matchers, SETTINGS, SPA_ACTIVITY, 32));
    }

    @Test
    public void windowMatchers_frameLayout_matches() {
        assertTrue(matchesAny(matchers, SETTINGS, "android.widget.FrameLayout", 32));
    }

    @Test
    public void windowMatchers_subSettings_matches() {
        assertTrue(matchesAny(matchers, SETTINGS, SUB_SETTINGS, 32));
    }

    private List<WindowMatcher> buildWindowMatchers() {
        List<WindowMatcher> list = new ArrayList<>();
        list.add(new WindowMatcher(SETTINGS, "android.app.Dialog")
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, INSTALLED_APP_DETAILS)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, SPA_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, SPA_ACTIVITY)
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, "android.widget.FrameLayout")
            .addEventType(32).addEventType(16384));
        list.add(new WindowMatcher(SETTINGS, SUB_SETTINGS)
            .addEventType(32).addEventType(16384));
        return list;
    }
}
