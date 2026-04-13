package com.guard.wallet.entity;

import a1.AbstractC0026q;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.guard.wallet.resp.UiObjectVO;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import p003f.AbstractC0276a;
import p003f.C0277b;
import p003f.C0278c;
import p003f.C0279d;
import p008k.C0356a;

/* loaded from: classes.dex */
public class UiObjectCollection {
    private List<UiObject> nodes;

    public UiObjectCollection(List<UiObject> list) {
        this.nodes = new LinkedList();
        this.nodes = list == null ? new LinkedList<>() : list;
    }

    public static UiObjectCollection of(List<UiObject> list) {
        if (list == null) {
            list = new LinkedList<>();
        }
        return new UiObjectCollection(list);
    }

    public boolean accessibilityFocus() {
        return performAction(64).booleanValue();
    }

    public boolean clearAccessibilityFocus() {
        return performAction(128).booleanValue();
    }

    public boolean clearFocus() {
        return performAction(2).booleanValue();
    }

    public boolean click() {
        return performAction(16).booleanValue();
    }

    public boolean collapse() {
        return performAction(524288).booleanValue();
    }

    public Boolean contains(UiObject uiObject) {
        return empty().booleanValue() ? Boolean.FALSE : Boolean.valueOf(this.nodes.contains(uiObject));
    }

    public boolean contextClick() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId()).booleanValue();
    }

    public boolean copy(int i2) {
        return performAction(16384, i2).booleanValue();
    }

    public boolean cut() {
        return performAction(65536).booleanValue();
    }

    public boolean dismiss() {
        return performAction(1048576).booleanValue();
    }

    public UiObjectCollection each(Consumer<UiObject> consumer) {
        Iterator<UiObject> it = this.nodes.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
        return this;
    }

    public Boolean empty() {
        return Boolean.valueOf(size() == 0);
    }

    public boolean expand() {
        return performAction(262144).booleanValue();
    }

    public UiObjectCollection find(C0356a c0356a) {
        LinkedList linkedList = new LinkedList();
        List<UiObject> list = this.nodes;
        if (list != null && !list.isEmpty()) {
            for (UiObject uiObject : this.nodes) {
                if (uiObject != null) {
                    linkedList.addAll(c0356a.m928r(uiObject).nodes);
                }
            }
        }
        return of(linkedList);
    }

    public UiObject findOne(C0356a c0356a) {
        UiObject m930t;
        List<UiObject> list = this.nodes;
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (UiObject uiObject : this.nodes) {
            if (uiObject != null && (m930t = c0356a.m930t(uiObject)) != null) {
                return m930t;
            }
        }
        return null;
    }

    public boolean focus() {
        return performAction(1).booleanValue();
    }

    public UiObject get(int i2) {
        if (!empty().booleanValue() && this.nodes.size() > i2) {
            return this.nodes.get(i2);
        }
        return null;
    }

    public List<UiObject> getNodes() {
        return this.nodes;
    }

    public int indexOf(UiObject uiObject) {
        if (empty().booleanValue()) {
            return -1;
        }
        return this.nodes.indexOf(uiObject);
    }

    public Iterator<UiObject> iterator() {
        if (this.nodes == null) {
            this.nodes = new LinkedList();
        }
        return this.nodes.iterator();
    }

    public int lastIndexOf(UiObject uiObject) {
        if (empty().booleanValue()) {
            return -1;
        }
        return this.nodes.lastIndexOf(uiObject);
    }

    public boolean longClick() {
        return performAction(32).booleanValue();
    }

    public Boolean nonEmpty() {
        return Boolean.valueOf(size() != 0);
    }

    public boolean paste() {
        return performAction(32768).booleanValue();
    }

    public Boolean performAction(int i2) {
        boolean z2 = false;
        for (UiObject uiObject : this.nodes) {
            if (uiObject != null && !uiObject.performAction(i2)) {
                z2 = true;
            }
        }
        return Boolean.valueOf(!z2);
    }

    public boolean scrollBackward() {
        return performAction(8192).booleanValue();
    }

    public boolean scrollDown() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()).booleanValue();
    }

    public boolean scrollForward() {
        return performAction(4096).booleanValue();
    }

    public boolean scrollLeft() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId()).booleanValue();
    }

    public boolean scrollRight() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId()).booleanValue();
    }

    public boolean scrollTo(int i2, int i3) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_ROW_INT, i2), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_COLUMN_INT, i3)).booleanValue();
    }

    public boolean scrollUp() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId()).booleanValue();
    }

    public boolean select() {
        return performAction(4).booleanValue();
    }

    public boolean setProgress(float f2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(), new C0278c(f2)).booleanValue();
    }

    public boolean setSelection(int i2, int i3) {
        return performAction(131072, new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_START_INT, i2), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_END_INT, i3)).booleanValue();
    }

    public boolean setText(int i2, String str) {
        return performAction(2097152, i2, new C0277b(str)).booleanValue();
    }

    public boolean show() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId()).booleanValue();
    }

    public int size() {
        List<UiObject> list = this.nodes;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public UiObject[] toArray() {
        UiObject[] uiObjectArr = new UiObject[this.nodes.size()];
        this.nodes.toArray(uiObjectArr);
        return uiObjectArr;
    }

    public List<UiObjectVO> toListVO() {
        LinkedList linkedList = new LinkedList();
        List<UiObject> list = this.nodes;
        if (list != null && !list.isEmpty()) {
            for (UiObject uiObject : this.nodes) {
                if (uiObject != null) {
                    linkedList.add(new UiObjectVO(uiObject));
                }
            }
        }
        return linkedList;
    }

    public boolean accessibilityFocus(int i2) {
        return performAction(64, i2).booleanValue();
    }

    public boolean clearAccessibilityFocus(int i2) {
        return performAction(128, i2).booleanValue();
    }

    public boolean clearFocus(int i2) {
        return performAction(2, i2).booleanValue();
    }

    public boolean click(int i2) {
        return performAction(16, i2).booleanValue();
    }

    public boolean collapse(int i2) {
        return performAction(524288, i2).booleanValue();
    }

    public boolean contextClick(int i2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId(), i2).booleanValue();
    }

    public boolean cut(int i2) {
        return performAction(65536, i2).booleanValue();
    }

    public boolean dismiss(int i2) {
        return performAction(1048576, i2).booleanValue();
    }

    public boolean expand(int i2) {
        return performAction(262144, i2).booleanValue();
    }

    public boolean focus(int i2) {
        return performAction(1, i2).booleanValue();
    }

    public boolean longClick(int i2) {
        return performAction(32, i2).booleanValue();
    }

    public boolean paste(int i2) {
        return performAction(32768, i2).booleanValue();
    }

    public Boolean performAction(int i2, int i3) {
        UiObject uiObject;
        return (i3 < 0 || this.nodes.size() <= i3 || (uiObject = this.nodes.get(i3)) == null) ? Boolean.FALSE : Boolean.valueOf(uiObject.performAction(i2));
    }

    public boolean scrollBackward(int i2) {
        return performAction(8192, i2).booleanValue();
    }

    public boolean scrollDown(int i2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId(), i2).booleanValue();
    }

    public boolean scrollForward(int i2) {
        return performAction(4096, i2).booleanValue();
    }

    public boolean scrollLeft(int i2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId(), i2).booleanValue();
    }

    public boolean scrollRight(int i2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId(), i2).booleanValue();
    }

    public boolean scrollTo(int i2, int i3, int i4) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(), i2, new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_ROW_INT, i3), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_COLUMN_INT, i4)).booleanValue();
    }

    public boolean scrollUp(int i2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId(), i2).booleanValue();
    }

    public boolean select(int i2) {
        return performAction(4, i2).booleanValue();
    }

    public boolean setProgress(int i2, float f2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(), i2, new C0278c(f2)).booleanValue();
    }

    public boolean setSelection(int i2, int i3, int i4) {
        return performAction(131072, i2, new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_START_INT, i3), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_END_INT, i4)).booleanValue();
    }

    public boolean setText(String str) {
        return performAction(2097152, new C0277b(str)).booleanValue();
    }

    public boolean show(int i2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId(), i2).booleanValue();
    }

    public Boolean performAction(int i2, int i3, AbstractC0276a... abstractC0276aArr) {
        if (i3 >= 0 && this.nodes.size() > i3) {
            UiObject uiObject = this.nodes.get(i3);
            Bundle m171a = AbstractC0026q.m171a(abstractC0276aArr);
            if (uiObject != null) {
                return Boolean.valueOf(uiObject.performAction(i2, m171a));
            }
        }
        return Boolean.FALSE;
    }

    public Boolean performAction(int i2, AbstractC0276a... abstractC0276aArr) {
        Bundle m171a = AbstractC0026q.m171a(abstractC0276aArr);
        boolean z2 = false;
        for (UiObject uiObject : this.nodes) {
            if (uiObject != null && !uiObject.performAction(i2, m171a)) {
                z2 = true;
            }
        }
        return Boolean.valueOf(!z2);
    }
}
