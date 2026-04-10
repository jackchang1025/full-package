package com.guard.wallet.gkd;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import li.songe.selector.MatchOption;
import li.songe.selector.Selector;
import li.songe.selector.Transform;

/**
 * GKD 选择器 Java 桥接层。
 *
 * 提供两类 API:
 * 1. 直接 GKD 查询: findOne(root, "[text='x']") / findAll(root, "Switch[checked=true]")
 * 2. CombineFilter 兼容: findOneByCombine(root, filter) — 运行时转为 GKD 字符串
 *
 * 所有方法接受 vendor-replica 的 UiObject, 内部通过 source().get().unwrap()
 * 获取原始 AccessibilityNodeInfo 传给 GKD Transform。
 */
public final class GkdNodeFinder {
    private static final String TAG = "GkdNodeFinder";
    private static final Transform<AccessibilityNodeInfo> transform = GkdTransformKt.createGkdTransform();
    private static final MatchOption defaultOption = new MatchOption();
    private static final ConcurrentHashMap<String, Selector> selectorCache = new ConcurrentHashMap<>();
    private static final java.util.Set<String> badSelectors =
            java.util.Collections.newSetFromMap(new ConcurrentHashMap<>());

    private GkdNodeFinder() {}

    // ═══════ 核心: 解析选择器 (带缓存) ═══════

    private static Selector getOrParse(String selector) {
        if (badSelectors.contains(selector)) return null;
        Selector cached = selectorCache.get(selector);
        if (cached != null) return cached;
        try {
            Selector parsed = Selector.Companion.parse(selector);
            if (parsed != null) {
                selectorCache.put(selector, parsed);
            }
            return parsed;
        } catch (Exception e) {
            Log.e(TAG, "Invalid selector: " + selector, e);
            badSelectors.add(selector);
            return null;
        }
    }

    // ═══════ 直接 GKD 查询 ═══════

    /**
     * 在 UiObject 子树中查找第一个匹配 GKD 选择器的节点。
     *
     * @param root     搜索起点 (vendor-replica UiObject)
     * @param selector GKD 选择器字符串, 如 "[text*='无线调试']" 或 "Switch[checked=true]"
     * @return 匹配的 UiObject, 或 null
     */
    public static UiObject findOne(UiObject root, String selector) {
        if (root == null || selector == null) return null;
        AccessibilityNodeInfo rawRoot = extractNodeInfo(root);
        if (rawRoot == null) return null;

        try {
            Selector sel = getOrParse(selector);
            if (sel == null) return null;
            AccessibilityNodeInfo result = transform.querySelector(rawRoot, sel, defaultOption);
            return result != null ? new UiObject(result, 0, -1) : null;
        } catch (Exception e) {
            Log.e(TAG, "findOne failed: " + selector, e);
            return null;
        }
    }

    /**
     * 在 UiObject 子树中查找所有匹配 GKD 选择器的节点。
     */
    public static List<UiObject> findAll(UiObject root, String selector) {
        List<UiObject> results = new ArrayList<>();
        if (root == null || selector == null) return results;
        AccessibilityNodeInfo rawRoot = extractNodeInfo(root);
        if (rawRoot == null) return results;

        try {
            Selector sel = getOrParse(selector);
            if (sel == null) return results;
            kotlin.sequences.Sequence<AccessibilityNodeInfo> seq =
                    transform.querySelectorAll(rawRoot, sel, defaultOption);
            for (AccessibilityNodeInfo match : kotlin.sequences.SequencesKt.asIterable(seq)) {
                results.add(new UiObject(match, 0, -1));
            }
        } catch (Exception e) {
            Log.e(TAG, "findAll failed: " + selector, e);
        }
        return results;
    }

    // ═══════ CombineFilter 兼容层 ═══════

    /**
     * CombineFilter 兼容 — 运行时转为 GKD 字符串后查询第一个匹配节点。
     */
    public static UiObject findOneByCombine(UiObject root, CombineFilter filter) {
        if (root == null || filter == null) return null;
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector != null && !selector.isEmpty()) {
            return findOne(root, selector);
        }
        Log.w(TAG, "findOneByCombine: CombineFilter could not be converted to GKD selector: " + filter);
        return null;
    }

    /**
     * CombineFilter 兼容 — 运行时转为 GKD 字符串后查询所有匹配节点。
     */
    public static List<UiObject> findAllByCombine(UiObject root, CombineFilter filter) {
        if (root == null || filter == null) return new ArrayList<>();
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector != null && !selector.isEmpty()) {
            return findAll(root, selector);
        }
        Log.w(TAG, "findAllByCombine: CombineFilter could not be converted to GKD selector: " + filter);
        return new ArrayList<>();
    }

    /**
     * CombineFiltersWithOr 兼容 — OR 条件转为 GKD "a || b" 语法。
     */
    public static UiObject findOneByOperateOr(UiObject root, CombineFiltersWithOr filter) {
        if (root == null || filter == null) return null;
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector != null && !selector.isEmpty()) {
            return findOne(root, selector);
        }
        // Fallback: try each sub-filter individually
        if (filter.getFilters() != null) {
            for (CombineFilter cf : filter.getFilters()) {
                UiObject found = findOneByCombine(root, cf);
                if (found != null) return found;
            }
        }
        return null;
    }

    /**
     * CombineFiltersWithOr 兼容 — OR 条件查找所有匹配节点。
     */
    public static List<UiObject> findAllByOperateOr(UiObject root, CombineFiltersWithOr filter) {
        List<UiObject> results = new ArrayList<>();
        if (root == null || filter == null) return results;
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector != null && !selector.isEmpty()) {
            return findAll(root, selector);
        }
        // Fallback: try each sub-filter individually
        if (filter.getFilters() != null) {
            for (CombineFilter cf : filter.getFilters()) {
                results.addAll(findAllByCombine(root, cf));
            }
        }
        return results;
    }

    /**
     * CombineFilterWithChild 兼容 — 父子条件转为 GKD ">n" 语法。
     */
    public static UiObject findOneByCombineWithChild(UiObject root, CombineFilterWithChild filter) {
        if (root == null || filter == null) return null;
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector != null && !selector.isEmpty()) {
            return findOne(root, selector);
        }
        // Fallback: find parents matching parentFilter, then check each for childFilter match
        if (filter.getParentFilter() != null) {
            List<UiObject> parents = findAllByCombine(root, filter.getParentFilter());
            for (UiObject p : parents) {
                if (p != null && filter.getChildFilter() != null
                        && findOneByCombine(p, filter.getChildFilter()) != null) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * CombineFilterWithChild 兼容 — 查找所有含匹配子节点的父节点。
     */
    public static List<UiObject> findAllByCombineWithChild(UiObject root, CombineFilterWithChild filter) {
        List<UiObject> results = new ArrayList<>();
        if (root == null || filter == null) return results;
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector != null && !selector.isEmpty()) {
            return findAll(root, selector);
        }
        // Fallback: find parents matching parentFilter, then check each for childFilter match
        if (filter.getParentFilter() != null) {
            List<UiObject> parents = findAllByCombine(root, filter.getParentFilter());
            for (UiObject p : parents) {
                if (p != null && filter.getChildFilter() != null
                        && findOneByCombine(p, filter.getChildFilter()) != null) {
                    results.add(p);
                }
            }
        }
        return results;
    }

    // ═══════ 工具方法 ═══════

    /**
     * 转义选择器字符串中的特殊字符。
     * 用于动态拼接文本到选择器: "[text=\"" + escape(userText) + "\"]"
     */
    public static String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /**
     * 从 vendor-replica UiObject 提取原始 AccessibilityNodeInfo。
     * UiObject 内部用 AtomicReference&lt;AccessibilityNodeInfoCompat&gt; 包装。
     */
    private static AccessibilityNodeInfo extractNodeInfo(UiObject uiObject) {
        try {
            if (uiObject.source() == null || uiObject.source().get() == null) return null;
            return uiObject.source().get().unwrap();
        } catch (Exception e) {
            Log.e(TAG, "extractNodeInfo failed", e);
            return null;
        }
    }
}
