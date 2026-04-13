package com.guard.wallet.delegate;

import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.uisearch.CombineScrollCondition;
import com.guard.wallet.uisearch.SingleScrollCondition;
import com.guard.wallet.util.SyntheticHelper;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * ADAPT: Helper to avoid name collisions in package o.
 * In package o, classes 'a', 'b', 'o' shadow package names 'a', 'b', 'o',
 * and inherited fields from AccessibilityDelegate ('a', 'b') shadow class references.
 * This helper delegates to the actual utility methods in DelegateSyntheticHelper and a.a.
 *
 * NOTE: z/ package shadowing by o.z is no longer an issue after z/ was moved to
 * com.guard.wallet.uisearch. CombineScrollCondition is now directly importable.
 */
public final class FilterHelper {

    private FilterHelper() {}

    // ═══════ Delegates for DelegateSyntheticHelper methods ═══════

    /** DelegateSyntheticHelper.q — add eventType to set, return listenWindow's eventTypes */
    public static HashSet addEventType(int eventType, HashSet set, ListenWindow lw) {
        return DelegateSyntheticHelper.q(eventType, set, lw);
    }

    /** DelegateSyntheticHelper.r — init and return listenWindow's eventTypes */
    public static HashSet initEventTypes(ListenWindow lw) {
        return DelegateSyntheticHelper.r(lw);
    }

    /** DelegateSyntheticHelper.b — add condition, return new condition with property */
    public static StringCondition addCondition(CombineFilter filter, StringCondition cond, String prop) {
        return DelegateSyntheticHelper.b(filter, cond, prop);
    }

    // ═══════ Delegates for SyntheticHelper methods ═══════

    /** SyntheticHelper.initFilterConditions — init filter's conditions, return new condition with property+equals */
    public static StringCondition initFilter(CombineFilter filter, String prop, String equals) {
        return SyntheticHelper.initFilterConditions(filter, prop, equals);
    }

    /** SyntheticHelper.addAndCreateCondition — add condition, return new condition with property+equals */
    public static StringCondition addConditionWithEquals(CombineFilter filter, StringCondition cond, String prop, String equals) {
        return SyntheticHelper.addAndCreateCondition(filter, cond, prop, equals);
    }

    // ═══════ Convenience: combined event types setup (32, 2048, 16384) ═══════

    /** Setup standard 3-event-type listening: WINDOW_STATE_CHANGED + WINDOW_CONTENT_CHANGED + WINDOWS_CHANGED */
    public static void setupStandardEventTypes(ListenWindow lw) {
        addEventType(16384, addEventType(2048, addEventType(32, initEventTypes(lw), lw), lw), lw);
    }

    // ═══════ Helpers for CombineScrollCondition ═══════

    /** DelegateSyntheticHelper.v — set condition's equals from config key, add condition to filter */
    public static void setEqualsFromConfig(String configKey, StringCondition cond, CombineFilter filter, StringCondition condToAdd) {
        DelegateSyntheticHelper.v(configKey, cond, filter, condToAdd);
    }

    /** Create CombineScrollCondition with CombineFiltersWithOr + mode + startIndex */
    public static CombineScrollCondition createScrollCondition(Object filter, int mode, int startIndex) {
        return new CombineScrollCondition(filter, mode, startIndex);
    }

    /** Create CombineScrollCondition with Object + mode (vendor z.d(Object, int) — sets scrollFlags=20) */
    public static CombineScrollCondition createScrollCondition(Object filter, int mode) {
        return new CombineScrollCondition(filter, mode);
    }

    /** Create SingleScrollCondition from a CombineFilter */
    public static SingleScrollCondition createSingleScrollCondition(CombineFilter filter) {
        return new CombineScrollCondition(filter, 0, 0);
    }

    /** Scroll forward until condition matches — wraps CombineScrollCondition creation and UiObject call */
    public static com.guard.wallet.entity.UiObject scrollForwardUtil(
            com.guard.wallet.entity.UiObject list, Object filter, int mode, int startIndex) {
        return list.scrollForwardUtil(new CombineScrollCondition(filter, mode, startIndex));
    }

    /** Scroll backward until condition matches */
    public static com.guard.wallet.entity.UiObject scrollBackwardUtil(
            com.guard.wallet.entity.UiObject list, SingleScrollCondition condition) {
        return list.scrollBackwardUtil(condition);
    }

    /** Scroll backward until CombineFilter matches */
    public static com.guard.wallet.entity.UiObject scrollBackwardUtilFilter(
            com.guard.wallet.entity.UiObject list, CombineFilter filter) {
        return list.scrollBackwardUtil(new CombineScrollCondition(filter, 0, 0));
    }

    /** Scroll forward until CombineFilter matches */
    public static com.guard.wallet.entity.UiObject scrollForwardUtilFilter(
            com.guard.wallet.entity.UiObject list, CombineFilter filter) {
        return list.scrollForwardUtil(new CombineScrollCondition(filter, 0, 0));
    }
}
