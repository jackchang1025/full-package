package com.vendor.rat.auto.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.selector.GkdTransformKt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import li.songe.selector.MatchOption;
import li.songe.selector.Selector;
import li.songe.selector.Transform;

/**
 * GKD Selector 辅助类 - Java 桥接层
 *
 * 修复:
 *   - CRITICAL-1: 使用 querySelector/querySelectorAll 遍历子树（非 match）
 *   - HIGH-2: ConcurrentHashMap 缓存已解析的 Selector
 *   - HIGH-3: escapeForSelector 防止选择器注入
 */
public class GkdSelectorHelper {
    private static final String TAG = "GkdSelectorHelper";
    private static final Transform<AccessibilityNodeInfo> transform = GkdTransformKt.createGkdTransform();
    private static final MatchOption defaultOption = new MatchOption();
    private static final ConcurrentHashMap<String, Selector> selectorCache = new ConcurrentHashMap<>();

    /**
     * 解析选择器（带缓存）
     */
    private static Selector getOrParseSelector(String selector) {
        return selectorCache.computeIfAbsent(selector, s -> {
            try {
                return Selector.Companion.parse(s);
            } catch (Exception e) {
                Log.e(TAG, "Invalid selector: " + s, e);
                return null;
            }
        });
    }

    /**
     * 在节点树中查找第一个匹配的节点
     * 使用 transform.querySelector() 遍历所有后代节点
     */
    public static UiNode findOne(UiNode root, String selector) {
        if (root == null) return null;
        AccessibilityNodeInfo nodeInfo = root.getNodeInfo();
        if (nodeInfo == null) return null;

        try {
            Selector sel = getOrParseSelector(selector);
            if (sel == null) return null;
            AccessibilityNodeInfo result = transform.querySelector(nodeInfo, sel, defaultOption);
            return result != null ? new UiNode(result) : null;
        } catch (Exception e) {
            Log.e(TAG, "findOne failed: " + selector, e);
            return null;
        }
    }

    /**
     * 在节点树中查找所有匹配的节点
     * 使用 transform.querySelectorAll() (Sequence) 遍历所有后代节点
     *
     * 注意: 不使用 querySelectorAllArray() 因为 Kotlin 的 toTypedArray() 返回 Object[]
     * 无法在 Java 侧安全转型为 AccessibilityNodeInfo[] (ClassCastException)
     */
    public static List<UiNode> findAll(UiNode root, String selector) {
        List<UiNode> results = new ArrayList<>();
        if (root == null) return results;
        AccessibilityNodeInfo nodeInfo = root.getNodeInfo();
        if (nodeInfo == null) return results;

        try {
            Selector sel = getOrParseSelector(selector);
            if (sel == null) return results;
            kotlin.sequences.Sequence<AccessibilityNodeInfo> seq =
                    transform.querySelectorAll(nodeInfo, sel, defaultOption);
            for (AccessibilityNodeInfo match : kotlin.sequences.SequencesKt.asIterable(seq)) {
                results.add(new UiNode(match));
            }
        } catch (Exception e) {
            Log.e(TAG, "findAll failed: " + selector, e);
        }
        return results;
    }

    /**
     * 转义选择器字符串中的特殊字符
     * 用于动态拼接用户/配置文本到选择器表达式
     */
    public static String escapeForSelector(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
