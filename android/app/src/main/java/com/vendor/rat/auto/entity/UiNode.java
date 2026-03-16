package com.vendor.rat.auto.entity;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * UI 节点封装 (模块 04 核心)
 *
 * 封装 AccessibilityNodeInfo，提供简洁的查询和操作 API
 */
public class UiNode {

    private final AccessibilityNodeInfo nodeInfo;

    public UiNode(AccessibilityNodeInfo nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    // ============ 属性访问 ============

    public String getText() {
        return nodeInfo.getText() != null ? nodeInfo.getText().toString() : "";
    }

    public String getClassName() {
        return nodeInfo.getClassName() != null ? nodeInfo.getClassName().toString() : "";
    }

    public String getViewIdResourceName() {
        return nodeInfo.getViewIdResourceName() != null
            ? nodeInfo.getViewIdResourceName() : "";
    }

    public String getContentDescription() {
        return nodeInfo.getContentDescription() != null
            ? nodeInfo.getContentDescription().toString() : "";
    }

    public boolean isClickable() { return nodeInfo.isClickable(); }
    public boolean isEnabled() { return nodeInfo.isEnabled(); }
    public boolean isChecked() { return nodeInfo.isChecked(); }
    public boolean isScrollable() { return nodeInfo.isScrollable(); }
    public boolean isCheckable() { return nodeInfo.isCheckable(); }

    // ============ 操作 ============

    public boolean click() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    public boolean longClick() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }

    public boolean setText(String text) {
        android.os.Bundle args = new android.os.Bundle();
        args.putCharSequence(
            AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args);
    }

    public boolean scrollForward() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
    }

    public boolean scrollBackward() {
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }

    // ============ 子节点查询 ============

    public int getChildCount() { return nodeInfo.getChildCount(); }

    public UiNode getChild(int index) {
        AccessibilityNodeInfo child = nodeInfo.getChild(index);
        return child != null ? new UiNode(child) : null;
    }

    public List<UiNode> getChildren() {
        List<UiNode> children = new ArrayList<>();
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo child = nodeInfo.getChild(i);
            if (child != null) {
                children.add(new UiNode(child));
            }
        }
        return children;
    }

    public UiNode getParent() {
        AccessibilityNodeInfo parent = nodeInfo.getParent();
        return parent != null ? new UiNode(parent) : null;
    }

    // ============ 查找方法 ============

    public List<UiNode> findByText(String text) {
        List<UiNode> result = new ArrayList<>();
        List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodes != null) {
            for (AccessibilityNodeInfo node : nodes) {
                result.add(new UiNode(node));
            }
        }
        return result;
    }

    public List<UiNode> findById(String viewId) {
        List<UiNode> result = new ArrayList<>();
        List<AccessibilityNodeInfo> nodes =
            nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
        if (nodes != null) {
            for (AccessibilityNodeInfo node : nodes) {
                result.add(new UiNode(node));
            }
        }
        return result;
    }

    public AccessibilityNodeInfo getNodeInfo() { return nodeInfo; }

    public void recycle() {
        nodeInfo.recycle();
    }
}
