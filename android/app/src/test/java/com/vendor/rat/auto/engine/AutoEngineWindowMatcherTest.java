package com.vendor.rat.auto.engine;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * AutoEngine.WindowMatcher.matches() 边界测试
 *
 * 纯 JVM 测试，无需 Android 框架
 */
public class AutoEngineWindowMatcherTest {

    // ============ 精确匹配 ============

    @Test
    public void matches_exactPackageAndClass_returnsTrue() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main");
        assertTrue(m.matches("com.example", "com.example.Main", 0));
    }

    @Test
    public void matches_packageOnly_nullClassName_returnsTrue() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example");
        assertTrue(m.matches("com.example", "anything", 0));
    }

    @Test
    public void matches_packageOnly_nullClassName_anyClass_returnsTrue() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", null);
        assertTrue(m.matches("com.example", "com.example.Foo", 0));
    }

    @Test
    public void matches_emptyClassName_actsAsWildcard() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "");
        assertTrue(m.matches("com.example", "com.example.Bar", 0));
    }

    // ============ 不匹配 ============

    @Test
    public void matches_wrongPackage_returnsFalse() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main");
        assertFalse(m.matches("com.other", "com.example.Main", 0));
    }

    @Test
    public void matches_wrongClass_returnsFalse() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main");
        assertFalse(m.matches("com.example", "com.example.Other", 0));
    }

    @Test
    public void matches_nullInputPackage_returnsFalse() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example");
        assertFalse(m.matches(null, "com.example.Main", 0));
    }

    @Test
    public void matches_nullInputClass_matcherHasClass_returnsFalse() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main");
        assertFalse(m.matches("com.example", null, 0));
    }

    // ============ eventType 过滤 ============

    @Test
    public void matches_eventTypeInSet_returnsTrue() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main")
                .addEventType(32).addEventType(16384);
        assertTrue(m.matches("com.example", "com.example.Main", 32));
    }

    @Test
    public void matches_eventTypeNotInSet_returnsFalse() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main")
                .addEventType(32).addEventType(16384);
        assertFalse(m.matches("com.example", "com.example.Main", 1));
    }

    @Test
    public void matches_emptyEventTypes_anyEventPasses() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main");
        assertTrue(m.matches("com.example", "com.example.Main", 999));
    }

    @Test
    public void matches_eventTypeZero_notInSet_returnsFalse() {
        // 关键边界: matchesAny() 总传 eventType=0
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main")
                .addEventType(32).addEventType(16384);
        assertFalse(m.matches("com.example", "com.example.Main", 0));
    }

    @Test
    public void matches_eventTypeZero_inSet_returnsTrue() {
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.example", "com.example.Main")
                .addEventType(0).addEventType(32);
        assertTrue(m.matches("com.example", "com.example.Main", 0));
    }

    // ============ buildAllMatchers 风格 vs buildDetectionGroups 风格 ============

    @Test
    public void buildAllMatchers_style_withEventTypes_filtersOnEventType() {
        // buildAllMatchers 中有些 matcher 带 eventTypes (如 HWSettings: 32, 16384)
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.android.settings",
                "com.android.settings.HWSettings")
                .addEventType(32).addEventType(16384);
        // matchesAny 传 0 → 不匹配 (因为 0 不在 {32, 16384} 中)
        assertFalse(m.matches("com.android.settings", "com.android.settings.HWSettings", 0));
        // 直接传 32 → 匹配
        assertTrue(m.matches("com.android.settings", "com.android.settings.HWSettings", 32));
    }

    @Test
    public void buildDetectionGroups_style_noEventTypes_alwaysMatches() {
        // buildDetectionGroups 中的 matcher 不带 eventTypes
        AutoEngine.WindowMatcher m = new AutoEngine.WindowMatcher("com.android.settings",
                "com.android.settings.HWSettings");
        // matchesAny 传 0 → 匹配 (eventTypes 为空不过滤)
        assertTrue(m.matches("com.android.settings", "com.android.settings.HWSettings", 0));
    }
}
