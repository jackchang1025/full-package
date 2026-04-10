package com.guard.wallet.entity;

import com.guard.wallet.action.*;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.gkd.GkdNodeFinder;
import android.os.Bundle;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.guard.wallet.resp.UiObjectVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Wraps a list of UiObject nodes with convenience methods for searching,
 * filtering, iteration, and performing accessibility actions.
 *
 * Vendor: com.guard.wallet.entity.UiObjectCollection (411 lines)
 */
public class UiObjectCollection {
    private List<UiObject> nodes;

    public UiObjectCollection() {
        this.nodes = new LinkedList<>();
    }

    public UiObjectCollection(List<UiObject> nodes) {
        if (nodes == null) {
            nodes = new LinkedList<>();
        }
        this.nodes = nodes;
    }

    /** Factory: wrap an existing list */
    public static UiObjectCollection of(List<UiObject> list) {
        if (list == null) {
            list = new LinkedList<>();
        }
        return new UiObjectCollection(list);
    }

    public static UiObjectCollection of(ArrayList<UiObject> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return new UiObjectCollection(list);
    }

    // ═══════════════════════════════════════════
    // Size / state
    // ═══════════════════════════════════════════

    public int size() {
        List<UiObject> n = this.nodes;
        return n == null ? 0 : n.size();
    }

    public Boolean empty() {
        return size() == 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Boolean nonEmpty() {
        return size() != 0;
    }

    // ═══════════════════════════════════════════
    // Element access
    // ═══════════════════════════════════════════

    public UiObject get(int index) {
        if (empty()) {
            return null;
        }
        return this.nodes.size() > index ? this.nodes.get(index) : null;
    }

    public Boolean contains(UiObject node) {
        return empty() ? Boolean.FALSE : this.nodes.contains(node);
    }

    public int indexOf(UiObject node) {
        return empty() ? -1 : this.nodes.indexOf(node);
    }

    public int lastIndexOf(UiObject node) {
        return empty() ? -1 : this.nodes.lastIndexOf(node);
    }

    public List<UiObject> getNodes() {
        return this.nodes;
    }

    /** Alias for getNodes() — used in some replica call sites */
    public List<UiObject> getItems() {
        return this.nodes;
    }

    public Iterator<UiObject> iterator() {
        if (this.nodes == null) {
            this.nodes = new LinkedList<>();
        }
        return this.nodes.iterator();
    }

    public UiObject[] toArray() {
        UiObject[] arr = new UiObject[this.nodes.size()];
        this.nodes.toArray(arr);
        return arr;
    }

    // ═══════════════════════════════════════════
    // Iteration
    // ═══════════════════════════════════════════

    public UiObjectCollection each(Consumer<UiObject> consumer) {
        Iterator<UiObject> it = this.nodes.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
        return this;
    }

    // ═══════════════════════════════════════════
    // Search
    // ═══════════════════════════════════════════

    /** Find all nodes matching the GKD selector string across all nodes. */
    public UiObjectCollection find(String gkdSelector) {
        LinkedList<UiObject> result = new LinkedList<>();
        List<UiObject> n = this.nodes;
        if (n != null && !n.isEmpty()) {
            for (UiObject node : this.nodes) {
                if (node != null) {
                    result.addAll(GkdNodeFinder.findAll(node, gkdSelector));
                }
            }
        }
        return of(result);
    }

    /** Find the first node matching the GKD selector string. */
    public UiObject findOne(String gkdSelector) {
        List<UiObject> n = this.nodes;
        if (n != null && !n.isEmpty()) {
            for (UiObject node : this.nodes) {
                if (node != null) {
                    UiObject found = GkdNodeFinder.findOne(node, gkdSelector);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }

    // ═══════════════════════════════════════════
    // Conversion
    // ═══════════════════════════════════════════

    /** Convert to a list of UiObjectVO (for JSON serialization / server response). */
    public List<UiObjectVO> toListVO() {
        LinkedList<UiObjectVO> result = new LinkedList<>();
        List<UiObject> n = this.nodes;
        if (n != null && !n.isEmpty()) {
            for (UiObject node : this.nodes) {
                if (node != null) {
                    result.add(new UiObjectVO(node));
                }
            }
        }
        return result;
    }

    // ═══════════════════════════════════════════
    // performAction variants
    // ═══════════════════════════════════════════

    /** Perform action on ALL nodes. Returns true if ALL succeed. */
    public Boolean performAction(int action) {
        Iterator<UiObject> it = this.nodes.iterator();
        boolean anyFailed = false;
        while (it.hasNext()) {
            UiObject node = it.next();
            if (node != null && !node.performAction(action)) {
                anyFailed = true;
            }
        }
        return !anyFailed;
    }

    /** Perform action on a specific node by index. */
    public Boolean performAction(int action, int index) {
        if (index >= 0 && this.nodes.size() > index) {
            UiObject node = this.nodes.get(index);
            if (node != null) {
                return node.performAction(action);
            }
        }
        return Boolean.FALSE;
    }

    /** Perform action with Bundle args on a specific node by index. */
    public Boolean performAction(int action, int index, BundleArg... args) {
        if (index >= 0 && this.nodes.size() > index) {
            UiObject node = this.nodes.get(index);
            Bundle bundle = AppUtils.a(args);
            if (node != null) {
                return node.performAction(action, bundle);
            }
        }
        return Boolean.FALSE;
    }

    /** Perform action with Bundle args on ALL nodes. Returns true if ALL succeed. */
    public Boolean performAction(int action, BundleArg... args) {
        Bundle bundle = AppUtils.a(args);
        Iterator<UiObject> it = this.nodes.iterator();
        boolean anyFailed = false;
        while (it.hasNext()) {
            UiObject node = it.next();
            if (node != null && !node.performAction(action, bundle)) {
                anyFailed = true;
            }
        }
        return !anyFailed;
    }

    // ═══════════════════════════════════════════
    // Action convenience methods (all nodes)
    // ═══════════════════════════════════════════

    public boolean click() { return performAction(16); }
    public boolean click(int index) { return performAction(16, index); }

    public boolean longClick() { return performAction(32); }
    public boolean longClick(int index) { return performAction(32, index); }

    public boolean focus() { return performAction(1); }
    public boolean focus(int index) { return performAction(1, index); }

    public boolean clearFocus() { return performAction(2); }
    public boolean clearFocus(int index) { return performAction(2, index); }

    public boolean select() { return performAction(4); }
    public boolean select(int index) { return performAction(4, index); }

    public boolean accessibilityFocus() { return performAction(64); }
    public boolean accessibilityFocus(int index) { return performAction(64, index); }

    public boolean clearAccessibilityFocus() { return performAction(128); }
    public boolean clearAccessibilityFocus(int index) { return performAction(128, index); }

    public boolean scrollForward() { return performAction(4096); }
    public boolean scrollForward(int index) { return performAction(4096, index); }

    public boolean scrollBackward() { return performAction(8192); }
    public boolean scrollBackward(int index) { return performAction(8192, index); }

    public boolean copy(int index) { return performAction(16384, index); }

    public boolean cut() { return performAction(65536); }
    public boolean cut(int index) { return performAction(65536, index); }

    public boolean paste() { return performAction(32768); }
    public boolean paste(int index) { return performAction(32768, index); }

    public boolean expand() { return performAction(262144); }
    public boolean expand(int index) { return performAction(262144, index); }

    public boolean collapse() { return performAction(524288); }
    public boolean collapse(int index) { return performAction(524288, index); }

    public boolean dismiss() { return performAction(1048576); }
    public boolean dismiss(int index) { return performAction(1048576, index); }

    public boolean contextClick() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId());
    }
    public boolean contextClick(int index) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId(), index);
    }

    public boolean scrollUp() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId());
    }
    public boolean scrollUp(int index) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId(), index);
    }

    public boolean scrollDown() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId());
    }
    public boolean scrollDown(int index) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId(), index);
    }

    public boolean scrollLeft() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId());
    }
    public boolean scrollLeft(int index) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId(), index);
    }

    public boolean scrollRight() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId());
    }
    public boolean scrollRight(int index) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId(), index);
    }

    public boolean show() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId());
    }
    public boolean show(int index) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId(), index);
    }

    // ═══════════════════════════════════════════
    // Text / selection / progress actions
    // ═══════════════════════════════════════════

    public boolean setText(String text) {
        return performAction(2097152, new SetTextArg(text));
    }
    public boolean setText(int index, String text) {
        return performAction(2097152, index, new SetTextArg(text));
    }

    public boolean setSelection(int start, int end) {
        return performAction(131072,
                new PutIntArg("ACTION_ARGUMENT_SELECTION_START_INT", start),
                new PutIntArg("ACTION_ARGUMENT_SELECTION_END_INT", end));
    }
    public boolean setSelection(int index, int start, int end) {
        return performAction(131072, index,
                new PutIntArg("ACTION_ARGUMENT_SELECTION_START_INT", start),
                new PutIntArg("ACTION_ARGUMENT_SELECTION_END_INT", end));
    }

    public boolean setProgress(float value) {
        return performAction(
                AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(),
                new SetProgressArg(value));
    }
    public boolean setProgress(int index, float value) {
        return performAction(
                AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(),
                index, new SetProgressArg(value));
    }

    public boolean scrollTo(int row, int column) {
        return performAction(
                AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(),
                new PutIntArg("android.view.accessibility.action.ARGUMENT_ROW_INT", row),
                new PutIntArg("android.view.accessibility.action.ARGUMENT_COLUMN_INT", column));
    }
    public boolean scrollTo(int index, int row, int column) {
        return performAction(
                AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(),
                index,
                new PutIntArg("android.view.accessibility.action.ARGUMENT_ROW_INT", row),
                new PutIntArg("android.view.accessibility.action.ARGUMENT_COLUMN_INT", column));
    }
}
