package com.vendor.rat.auto.entity;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vendor.rat.auto.filter.NodeFilter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Vendor: com/guard/wallet/entity/UiObjectCollection.java (370行)
 * ADAPT: UiObject → UiNode
 * ADAPT: 移除 UiObjectVO/f.b/f.c/f.d/k.a 等未复刻依赖
 */
public class UiNodeCollection {

    private List<UiNode> nodes;

    public UiNodeCollection(List<UiNode> list) {
        this.nodes = new LinkedList<>();
        this.nodes = list == null ? new LinkedList<>() : list;
    }

    public static UiNodeCollection of(List<UiNode> list) {
        if (list == null) {
            list = new LinkedList<>();
        }
        return new UiNodeCollection(list);
    }

    public boolean accessibilityFocus() { return performAction(64).booleanValue(); }
    public boolean accessibilityFocus(int i) { return performAction(64, i).booleanValue(); }
    public boolean clearAccessibilityFocus() { return performAction(128).booleanValue(); }
    public boolean clearAccessibilityFocus(int i) { return performAction(128, i).booleanValue(); }
    public boolean clearFocus() { return performAction(2).booleanValue(); }
    public boolean clearFocus(int i) { return performAction(2, i).booleanValue(); }
    public boolean click() { return performAction(16).booleanValue(); }
    public boolean click(int i) { return performAction(16, i).booleanValue(); }
    public boolean collapse() { return performAction(524288).booleanValue(); }
    public boolean collapse(int i) { return performAction(524288, i).booleanValue(); }

    public Boolean contains(UiNode node) {
        return empty().booleanValue() ? Boolean.FALSE : Boolean.valueOf(this.nodes.contains(node));
    }

    public boolean contextClick() { return performAction(16908348).booleanValue(); }
    public boolean contextClick(int i) { return performAction(16908348, i).booleanValue(); }
    public boolean copy(int i) { return performAction(16384, i).booleanValue(); }
    public boolean cut() { return performAction(65536).booleanValue(); }
    public boolean cut(int i) { return performAction(65536, i).booleanValue(); }
    public boolean dismiss() { return performAction(1048576).booleanValue(); }
    public boolean dismiss(int i) { return performAction(1048576, i).booleanValue(); }

    public UiNodeCollection each(Consumer<UiNode> consumer) {
        Iterator<UiNode> it = this.nodes.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
        return this;
    }

    public Boolean empty() { return Boolean.valueOf(size() == 0); }

    public boolean expand() { return performAction(262144).booleanValue(); }
    public boolean expand(int i) { return performAction(262144, i).booleanValue(); }
    public boolean focus() { return performAction(1).booleanValue(); }
    public boolean focus(int i) { return performAction(1, i).booleanValue(); }

    public UiNode get(int i) {
        if (!empty().booleanValue() && this.nodes.size() > i) {
            return this.nodes.get(i);
        }
        return null;
    }

    public List<UiNode> getNodes() { return this.nodes; }

    public int indexOf(UiNode node) {
        if (empty().booleanValue()) { return -1; }
        return this.nodes.indexOf(node);
    }

    public Iterator<UiNode> iterator() {
        if (this.nodes == null) { this.nodes = new LinkedList<>(); }
        return this.nodes.iterator();
    }

    public int lastIndexOf(UiNode node) {
        if (empty().booleanValue()) { return -1; }
        return this.nodes.lastIndexOf(node);
    }

    public boolean longClick() { return performAction(32).booleanValue(); }
    public boolean longClick(int i) { return performAction(32, i).booleanValue(); }
    public Boolean nonEmpty() { return Boolean.valueOf(size() != 0); }
    public boolean paste() { return performAction(32768).booleanValue(); }
    public boolean paste(int i) { return performAction(32768, i).booleanValue(); }

    public Boolean performAction(int action) {
        boolean failed = false;
        for (UiNode node : this.nodes) {
            if (node != null && !node.performAction(action)) {
                failed = true;
            }
        }
        return Boolean.valueOf(!failed);
    }

    public Boolean performAction(int action, int index) {
        UiNode node;
        return (index < 0 || this.nodes.size() <= index
                || (node = this.nodes.get(index)) == null)
                ? Boolean.FALSE
                : Boolean.valueOf(node.performAction(action));
    }

    public Boolean performAction(int action, Bundle bundle) {
        boolean failed = false;
        for (UiNode node : this.nodes) {
            if (node != null && !node.performAction(action, bundle)) {
                failed = true;
            }
        }
        return Boolean.valueOf(!failed);
    }

    public Boolean performAction(int action, int index, Bundle bundle) {
        if (index >= 0 && this.nodes.size() > index) {
            UiNode node = this.nodes.get(index);
            if (node != null) {
                return Boolean.valueOf(node.performAction(action, bundle));
            }
        }
        return Boolean.FALSE;
    }

    public boolean scrollBackward() { return performAction(8192).booleanValue(); }
    public boolean scrollBackward(int i) { return performAction(8192, i).booleanValue(); }
    public boolean scrollForward() { return performAction(4096).booleanValue(); }
    public boolean scrollForward(int i) { return performAction(4096, i).booleanValue(); }
    public boolean scrollDown() { return performAction(16908346).booleanValue(); }
    public boolean scrollDown(int i) { return performAction(16908346, i).booleanValue(); }
    public boolean scrollLeft() { return performAction(16908345).booleanValue(); }
    public boolean scrollLeft(int i) { return performAction(16908345, i).booleanValue(); }
    public boolean scrollRight() { return performAction(16908347).booleanValue(); }
    public boolean scrollRight(int i) { return performAction(16908347, i).booleanValue(); }
    public boolean scrollUp() { return performAction(16908344).booleanValue(); }
    public boolean scrollUp(int i) { return performAction(16908344, i).booleanValue(); }
    public boolean select() { return performAction(4).booleanValue(); }
    public boolean select(int i) { return performAction(4, i).booleanValue(); }
    public boolean show() { return performAction(16908354).booleanValue(); }
    public boolean show(int i) { return performAction(16908354, i).booleanValue(); }

    public boolean setText(String str) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        return performAction(2097152, bundle).booleanValue();
    }

    public boolean setText(int i, String str) {
        if (i < 0 || this.nodes.size() <= i || this.nodes.get(i) == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        return this.nodes.get(i).performAction(2097152, bundle);
    }

    public int size() {
        List<UiNode> list = this.nodes;
        if (list == null) { return 0; }
        return list.size();
    }

    // ADAPT: vendor find(k.a) → find(NodeFilter), k.a.r() → findAllByCombine()
    public UiNodeCollection find(NodeFilter filter) {
        LinkedList<UiNode> result = new LinkedList<>();
        List<UiNode> list = this.nodes;
        if (list != null && !list.isEmpty()) {
            for (UiNode node : this.nodes) {
                if (node != null) {
                    result.addAll(node.findAllByCombine(filter));
                }
            }
        }
        return of(result);
    }

    // ADAPT: vendor findOne(k.a) → findOne(NodeFilter), k.a.t() → findOneByCombine()
    public UiNode findOne(NodeFilter filter) {
        List<UiNode> list = this.nodes;
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (UiNode node : this.nodes) {
            if (node != null) {
                UiNode found = node.findOneByCombine(filter);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public boolean scrollTo(int row, int column) {
        Bundle bundle = new Bundle();
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, row);
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_COLUMN_INT, column);
        return performAction(16908343, bundle).booleanValue(); // ACTION_SCROLL_TO_POSITION
    }

    public boolean scrollTo(int index, int row, int column) {
        Bundle bundle = new Bundle();
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, row);
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_COLUMN_INT, column);
        return performAction(16908343, index, bundle).booleanValue();
    }

    public boolean setProgress(float value) {
        Bundle bundle = new Bundle();
        bundle.putFloat(AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE, value);
        return performAction(16908349, bundle).booleanValue(); // ACTION_SET_PROGRESS
    }

    public boolean setProgress(int index, float value) {
        Bundle bundle = new Bundle();
        bundle.putFloat(AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE, value);
        return performAction(16908349, index, bundle).booleanValue();
    }

    public boolean setSelection(int start, int end) {
        Bundle bundle = new Bundle();
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, start);
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, end);
        return performAction(131072, bundle).booleanValue();
    }

    public boolean setSelection(int index, int start, int end) {
        Bundle bundle = new Bundle();
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, start);
        bundle.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, end);
        return performAction(131072, index, bundle).booleanValue();
    }

    public UiNode[] toArray() {
        UiNode[] arr = new UiNode[this.nodes.size()];
        this.nodes.toArray(arr);
        return arr;
    }
}
