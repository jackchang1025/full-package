package com.vendor.rat.auto.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vendor.rat.auto.entity.UiNode;
import com.vendor.rat.auto.selector.GkdTransformKt;

import java.util.ArrayList;
import java.util.List;

import li.songe.selector.MatchOption;
import li.songe.selector.Selector;
import li.songe.selector.Transform;

/**
 * GKD Selector 辅助类 - Java 桥接层
 */
public class GkdSelectorHelper {
    private static final String TAG = "GkdSelectorHelper";
    private static final Transform<AccessibilityNodeInfo> transform = GkdTransformKt.createGkdTransform();
    private static final MatchOption defaultOption = new MatchOption();

    public static UiNode findOne(UiNode root, String selector) {
        if (root == null) return null;
        AccessibilityNodeInfo nodeInfo = root.getNodeInfo();
        if (nodeInfo == null) return null;

        try {
            Selector sel = Selector.Companion.parse(selector);
            AccessibilityNodeInfo result = sel.match(nodeInfo, transform, defaultOption);
            return result != null ? new UiNode(result) : null;
        } catch (Exception e) {
            Log.e(TAG, "findOne failed: " + selector, e);
            return null;
        }
    }

    public static List<UiNode> findAll(UiNode root, String selector) {
        List<UiNode> results = new ArrayList<>();
        if (root == null) return results;
        AccessibilityNodeInfo nodeInfo = root.getNodeInfo();
        if (nodeInfo == null) return results;

        try {
            Selector sel = Selector.Companion.parse(selector);
            // querySelectorAll returns Sequence, convert to array
            AccessibilityNodeInfo[] matches = transform.querySelectorAllArray(nodeInfo, sel, defaultOption);
            for (AccessibilityNodeInfo match : matches) {
                results.add(new UiNode(match));
            }
        } catch (Exception e) {
            Log.e(TAG, "findAll failed: " + selector, e);
        }
        return results;
    }
}
