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

    private GkdNodeFinder() {}

    // ═══════ 核心: 解析选择器 (带缓存) ═══════

    private static Selector getOrParse(String selector) {
        return selectorCache.computeIfAbsent(selector, s -> {
            try {
                return Selector.Companion.parse(s);
            } catch (Exception e) {
                Log.e(TAG, "Invalid selector: " + s, e);
                return null;
            }
        });
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
     * CombineFilter 兼容 — 运行时转为 GKD 字符串后查询。
     */
    public static UiObject findOneByCombine(UiObject root, CombineFilter filter) {
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector == null || selector.isEmpty()) return null;
        return findOne(root, selector);
    }

    /**
     * CombineFiltersWithOr 兼容 — OR 条件转为 GKD "a || b" 语法。
     */
    public static UiObject findOneByOperateOr(UiObject root, CombineFiltersWithOr filter) {
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector == null || selector.isEmpty()) return null;
        return findOne(root, selector);
    }

    /**
     * CombineFilterWithChild 兼容 — 父子条件转为 GKD "> " 语法。
     */
    public static UiObject findOneByCombineWithChild(UiObject root, CombineFilterWithChild filter) {
        String selector = CombineFilterConverter.toGkdSelector(filter);
        if (selector == null || selector.isEmpty()) return null;
        return findOne(root, selector);
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
