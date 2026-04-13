package com.guard.wallet.entity;

import a0.AbstractC0004d;
import a1.AbstractC0026q;
import android.R;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.condition.ActionValueCondition;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.filter.PointFilter;
import com.guard.wallet.helper.AbstractC0178a;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.bouncycastle.i18n.TextBundle;
import org.bouncycastle.tls.NamedGroup;
import p003f.AbstractC0276a;
import p003f.C0277b;
import p003f.C0278c;
import p003f.C0279d;
import p008k.C0356a;
import p022z.InterfaceC0978a;
import p022z.InterfaceC0979b;

/* loaded from: classes.dex */
public class UiObject implements Serializable {
    private static final String TAG = "UiObject";
    private final HashMap<String, String> cacheProperties;
    private final int depth;
    private final int indexInParent;
    private boolean rootRecycle;
    private final AtomicReference<AccessibilityNodeInfoCompat> source;
    private String uniqueId;

    public UiObject(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, int i2, int i3) {
        this.rootRecycle = false;
        this.cacheProperties = new LinkedHashMap();
        this.source = new AtomicReference<>(accessibilityNodeInfoCompat);
        this.depth = i2;
        this.indexInParent = i3;
    }

    public static UiObject createRoot(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return null;
        }
        try {
            return new UiObject(accessibilityNodeInfo, 0, -1);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-createRoot:", e2);
            return null;
        }
    }

    public boolean accessibilityFocus() {
        return performAction(64);
    }

    public boolean accessibilityFocused() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isAccessibilityFocused();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-accessibilityFocused:", e2);
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean actionByName(TargetActionCondition targetActionCondition) {
        char c;
        ActionValueCondition actionValueCondition;
        int i2;
        int i3;
        float f2;
        int i4;
        int i5;
        if (targetActionCondition != null) {
            try {
                if (!AbstractC0026q.m151B(targetActionCondition.getActionName())) {
                    String actionName = targetActionCondition.getActionName();
                    switch (actionName.hashCode()) {
                        case -1965304401:
                            if (actionName.equals("clickLeft")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1289167206:
                            if (actionName.equals("expand")) {
                                c = 16;
                                break;
                            }
                            c = 65535;
                            break;
                        case -906021636:
                            if (actionName.equals("select")) {
                                c = '\r';
                                break;
                            }
                            c = 65535;
                            break;
                        case -842925616:
                            if (actionName.equals("scrollBackward")) {
                                c = 20;
                                break;
                            }
                            c = 65535;
                            break;
                        case -789233292:
                            if (actionName.equals("clickRight")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -756050293:
                            if (actionName.equals("clearFocus")) {
                                c = '\n';
                                break;
                            }
                            c = 65535;
                            break;
                        case -632085587:
                            if (actionName.equals("collapse")) {
                                c = 15;
                                break;
                            }
                            c = 65535;
                            break;
                        case -402165208:
                            if (actionName.equals("scrollTo")) {
                                c = 29;
                                break;
                            }
                            c = 65535;
                            break;
                        case -402165176:
                            if (actionName.equals("scrollUp")) {
                                c = 23;
                                break;
                            }
                            c = 65535;
                            break;
                        case -218598600:
                            if (actionName.equals("scrollForward")) {
                                c = 19;
                                break;
                            }
                            c = 65535;
                            break;
                        case -176577718:
                            if (actionName.equals("setSelection")) {
                                c = 26;
                                break;
                            }
                            c = 65535;
                            break;
                        case 98882:
                            if (actionName.equals("cut")) {
                                c = 14;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3059573:
                            if (actionName.equals("copy")) {
                                c = 11;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3529469:
                            if (actionName.equals("show")) {
                                c = 18;
                                break;
                            }
                            c = 65535;
                            break;
                        case 65818895:
                            if (actionName.equals("scrollDown")) {
                                c = 24;
                                break;
                            }
                            c = 65535;
                            break;
                        case 66047092:
                            if (actionName.equals("scrollLeft")) {
                                c = 21;
                                break;
                            }
                            c = 65535;
                            break;
                        case 94750088:
                            if (actionName.equals("click")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 96667352:
                            if (actionName.equals("enter")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 97604824:
                            if (actionName.equals("focus")) {
                                c = '\t';
                                break;
                            }
                            c = 65535;
                            break;
                        case 102022252:
                            if (actionName.equals("longClick")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 106438291:
                            if (actionName.equals("paste")) {
                                c = '\f';
                                break;
                            }
                            c = 65535;
                            break;
                        case 870660093:
                            if (actionName.equals("clickCenter")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 988242095:
                            if (actionName.equals("setProgress")) {
                                c = 28;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1090835737:
                            if (actionName.equals("contextClick")) {
                                c = 25;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1141720106:
                            if (actionName.equals("accessibilityFocus")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1571418285:
                            if (actionName.equals("repeatClick")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1671672458:
                            if (actionName.equals("dismiss")) {
                                c = 17;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1978965335:
                            if (actionName.equals("clearAccessibilityFocus")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 1984984239:
                            if (actionName.equals("setText")) {
                                c = 27;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2053120847:
                            if (actionName.equals("scrollRight")) {
                                c = 22;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            return click();
                        case 1:
                            return clickPosition(0.9f, 0.5f);
                        case 2:
                            return clickPosition(0.1f, 0.5f);
                        case 3:
                            repeatClick(Integer.valueOf((targetActionCondition.getValues() == null || targetActionCondition.getValues().isEmpty() || (actionValueCondition = targetActionCondition.getValues().get(0)) == null || AbstractC0026q.m151B(actionValueCondition.getValue()) || AbstractC0026q.m151B(actionValueCondition.getKey()) || !"Int".equals(actionValueCondition.getType()) || !"count".equals(actionValueCondition.getKey()) || !AbstractC0026q.m153D(actionValueCondition.getValue())) ? 0 : Integer.parseInt(actionValueCondition.getValue())));
                            break;
                        case 4:
                            break;
                        case 5:
                            return longClick();
                        case 6:
                            GlobalActionCondition globalActionCondition = new GlobalActionCondition();
                            globalActionCondition.setActionName("click");
                            Point centerInScreen = centerInScreen();
                            if (centerInScreen != null) {
                                globalActionCondition.setPoints(new LinkedList());
                                globalActionCondition.getPoints().add(centerInScreen);
                            }
                            return AbstractC0251g.m654a(globalActionCondition);
                        case 7:
                            return accessibilityFocus();
                        case '\b':
                            return clearAccessibilityFocus();
                        case '\t':
                            return focus();
                        case '\n':
                            return clearFocus();
                        case 11:
                            return copy();
                        case '\f':
                            return paste();
                        case '\r':
                            return select();
                        case 14:
                            return cut();
                        case 15:
                            return collapse();
                        case 16:
                            return expand();
                        case 17:
                            return dismiss();
                        case 18:
                            return show();
                        case 19:
                            return scrollForward();
                        case 20:
                            return scrollBackward();
                        case 21:
                            return scrollLeft();
                        case 22:
                            return scrollRight();
                        case 23:
                            return scrollUp();
                        case 24:
                            return scrollDown();
                        case 25:
                            return contextClick();
                        case 26:
                            if (targetActionCondition.getValues() == null || targetActionCondition.getValues().isEmpty()) {
                                i2 = 0;
                                i3 = 0;
                            } else {
                                i2 = 0;
                                i3 = 0;
                                for (ActionValueCondition actionValueCondition2 : targetActionCondition.getValues()) {
                                    if (actionValueCondition2 != null && !AbstractC0026q.m151B(actionValueCondition2.getValue()) && !AbstractC0026q.m151B(actionValueCondition2.getKey()) && "Int".equals(actionValueCondition2.getType()) && AbstractC0026q.m153D(actionValueCondition2.getValue())) {
                                        if ("start".equals(actionValueCondition2.getKey())) {
                                            i2 = Integer.parseInt(actionValueCondition2.getValue());
                                        }
                                        if ("end".equals(actionValueCondition2.getKey())) {
                                            i3 = Integer.parseInt(actionValueCondition2.getValue());
                                        }
                                    }
                                }
                            }
                            return setSelection(i2, i3);
                        case 27:
                            String str = org.conscrypt.BuildConfig.FLAVOR;
                            if (targetActionCondition.getValues() != null && !targetActionCondition.getValues().isEmpty()) {
                                Iterator<ActionValueCondition> it = targetActionCondition.getValues().iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        ActionValueCondition next = it.next();
                                        if (next != null && !AbstractC0026q.m151B(next.getValue()) && !AbstractC0026q.m151B(next.getKey()) && "String".equals(next.getType()) && TextBundle.TEXT_ENTRY.equals(next.getKey())) {
                                            str = next.getValue();
                                        }
                                    }
                                }
                            }
                            return setText(str);
                        case 28:
                            if (targetActionCondition.getValues() != null && !targetActionCondition.getValues().isEmpty()) {
                                for (ActionValueCondition actionValueCondition3 : targetActionCondition.getValues()) {
                                    if (actionValueCondition3 != null && AbstractC0026q.m153D(actionValueCondition3.getValue()) && !AbstractC0026q.m151B(actionValueCondition3.getKey()) && "Float".equals(actionValueCondition3.getType()) && NotificationCompat.CATEGORY_PROGRESS.equals(actionValueCondition3.getKey())) {
                                        f2 = Float.parseFloat(actionValueCondition3.getValue());
                                        return setProgress(f2);
                                    }
                                }
                            }
                            f2 = 0.0f;
                            return setProgress(f2);
                        case 29:
                            if (targetActionCondition.getValues() == null || targetActionCondition.getValues().isEmpty()) {
                                i4 = 0;
                                i5 = 0;
                            } else {
                                i4 = 0;
                                i5 = 0;
                                for (ActionValueCondition actionValueCondition4 : targetActionCondition.getValues()) {
                                    if (actionValueCondition4 != null && !AbstractC0026q.m151B(actionValueCondition4.getKey()) && "Int".equals(actionValueCondition4.getType()) && AbstractC0026q.m153D(actionValueCondition4.getValue())) {
                                        if ("row".equals(actionValueCondition4.getKey())) {
                                            i4 = Integer.parseInt(actionValueCondition4.getValue());
                                        }
                                        if ("column".equals(actionValueCondition4.getKey())) {
                                            i5 = Integer.parseInt(actionValueCondition4.getValue());
                                        }
                                    }
                                }
                            }
                            return scrollTo(i4, i5);
                        default:
                            return false;
                    }
                    if (Build.VERSION.SDK_INT >= 30) {
                        return enter();
                    }
                    return false;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-actionByName:", e2);
            }
        }
        return false;
    }

    public Rect boundsInParent() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return null;
            }
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.source.get();
            Rect rect = new Rect();
            accessibilityNodeInfoCompat.getBoundsInParent(rect);
            return rect;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-boundsInParent:", e2);
            return null;
        }
    }

    public Rect boundsInScreen() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return null;
            }
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.source.get();
            Rect rect = new Rect();
            accessibilityNodeInfoCompat.getBoundsInScreen(rect);
            AbstractC0178a.m341c(rect);
            return rect;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-boundsInScreen:", e2);
            return null;
        }
    }

    public Rect boundsInWindow() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return null;
            }
            return AbstractC0178a.m339a(this.source.get());
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-boundsInWindow:", e2);
            return null;
        }
    }

    public boolean canOpenPopup() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().canOpenPopup();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-canOpenPopup:", e2);
            return false;
        }
    }

    public boolean canScrollBackward() {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actionList;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (actionList = this.source.get().getActionList()) == null || actionList.isEmpty()) {
                return false;
            }
            Iterator<AccessibilityNodeInfoCompat.AccessibilityActionCompat> it = actionList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(Integer.valueOf(it.next().getId()), Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId()))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-canScrollBackward:", e2);
            return false;
        }
    }

    public boolean canScrollDown() {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actionList;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (actionList = this.source.get().getActionList()) == null || actionList.isEmpty()) {
                return false;
            }
            Iterator<AccessibilityNodeInfoCompat.AccessibilityActionCompat> it = actionList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(Integer.valueOf(it.next().getId()), Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId()))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-canScrollDown:", e2);
            return false;
        }
    }

    public boolean canScrollForward() {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actionList;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (actionList = this.source.get().getActionList()) == null || actionList.isEmpty()) {
                return false;
            }
            Iterator<AccessibilityNodeInfoCompat.AccessibilityActionCompat> it = actionList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(Integer.valueOf(it.next().getId()), Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId()))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-canScrollForward:", e2);
            return false;
        }
    }

    public boolean canScrollLeft() {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actionList;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (actionList = this.source.get().getActionList()) == null || actionList.isEmpty()) {
                return false;
            }
            Iterator<AccessibilityNodeInfoCompat.AccessibilityActionCompat> it = actionList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(Integer.valueOf(it.next().getId()), Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId()))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-canScrollLeft:", e2);
            return false;
        }
    }

    public boolean canScrollRight() {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actionList;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (actionList = this.source.get().getActionList()) == null || actionList.isEmpty()) {
                return false;
            }
            Iterator<AccessibilityNodeInfoCompat.AccessibilityActionCompat> it = actionList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(Integer.valueOf(it.next().getId()), Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId()))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-canScrollRight:", e2);
            return false;
        }
    }

    public boolean canScrollUp() {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actionList;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (actionList = this.source.get().getActionList()) == null || actionList.isEmpty()) {
                return false;
            }
            Iterator<AccessibilityNodeInfoCompat.AccessibilityActionCompat> it = actionList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(Integer.valueOf(it.next().getId()), Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId()))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-canScrollUp:", e2);
            return false;
        }
    }

    public Point centerInParent() {
        try {
            Rect boundsInParent = boundsInParent();
            return new Point(boundsInParent.centerX(), boundsInParent.centerY());
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-centerInParent:", e2);
            return null;
        }
    }

    public Point centerInScreen() {
        try {
            Rect boundsInScreen = boundsInScreen();
            return new Point(boundsInScreen.exactCenterX(), boundsInScreen.exactCenterY());
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-centerInScreen:", e2);
            return null;
        }
    }

    public boolean checkable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isCheckable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-checkable:", e2);
            return false;
        }
    }

    public boolean checked() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isChecked();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-checked:", e2);
            return false;
        }
    }

    public UiObject child(int i2) {
        AccessibilityNodeInfoCompat child;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || i2 < 0 || i2 >= childCount() || (child = this.source.get().getChild(i2)) == null) {
                return null;
            }
            return new UiObject(child, this.depth + 1, i2);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-child:", e2);
            return null;
        }
    }

    public int childCount() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return 0;
            }
            return this.source.get().getChildCount();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-childCount:", e2);
            return 0;
        }
    }

    public String className() {
        CharSequence className;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (className = this.source.get().getClassName()) == null) {
                return null;
            }
            return className.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-className:", e2);
        }
        return null;
    }

    public boolean clearAccessibilityFocus() {
        return performAction(128);
    }

    public boolean clearFocus() {
        return performAction(2);
    }

    public boolean click() {
        try {
            if (clickable() && performAction(16)) {
                return true;
            }
            return AbstractC0251g.m672s(Integer.valueOf((int) centerInScreen().getX()), Integer.valueOf((int) centerInScreen().getY()));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-click:", e2);
            return false;
        }
    }

    public boolean clickPosition(float f2, float f3) {
        if (f2 > 1.0f || f2 <= 0.0f) {
            f2 = 0.5f;
        }
        if (f3 > 1.0f || f3 <= 0.0f) {
            f3 = 0.5f;
        }
        try {
            return AbstractC0251g.m672s(Integer.valueOf(boundsInScreen().left + ((int) (boundsInScreen().width() * f2))), Integer.valueOf(boundsInScreen().top + ((int) (boundsInScreen().height() * f3))));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-clickPosition:", e2);
            return false;
        }
    }

    public boolean clickable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isClickable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-clickable:", e2);
            return false;
        }
    }

    public boolean collapse() {
        return performAction(524288);
    }

    public int column() {
        AccessibilityNodeInfoCompat.CollectionItemInfoCompat collectionItemInfo;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference != null && atomicReference.get() != null && (collectionItemInfo = this.source.get().getCollectionItemInfo()) != null) {
                return collectionItemInfo.getColumnIndex();
            }
            return -1;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-column:", e2);
        }
        return -1;
    }

    public int columnCount() {
        AccessibilityNodeInfoCompat.CollectionInfoCompat collectionInfo;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference != null && atomicReference.get() != null && (collectionInfo = this.source.get().getCollectionInfo()) != null) {
                return collectionInfo.getColumnCount();
            }
            return 0;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-columnCount:", e2);
        }
        return 0;
    }

    public int columnSpan() {
        AccessibilityNodeInfoCompat.CollectionItemInfoCompat collectionItemInfo;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference != null && atomicReference.get() != null && (collectionItemInfo = this.source.get().getCollectionItemInfo()) != null) {
                return collectionItemInfo.getColumnSpan();
            }
            return -1;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-columnSpan:", e2);
        }
        return -1;
    }

    public boolean contentInvalid() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isContentInvalid();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-contentInvalid:", e2);
            return false;
        }
    }

    public boolean contextClick() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId());
    }

    public boolean contextClickable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isContextClickable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-contextClickable:", e2);
            return false;
        }
    }

    public boolean copy() {
        return performAction(16384);
    }

    public UiObject currentFocusedNode() {
        try {
            CombineFilter combineFilter = new CombineFilter();
            LinkedList linkedList = new LinkedList();
            linkedList.add(new BoolCondition("focused", true, true));
            combineFilter.setBoolConditions(linkedList);
            return findOneByCombine(combineFilter);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-currentFocusedNode:", e2);
            return null;
        }
    }

    public boolean cut() {
        return performAction(65536);
    }

    public int depth() {
        return this.depth;
    }

    public String desc() {
        CharSequence contentDescription;
        try {
            if (!AbstractC0026q.m151B(this.cacheProperties.get("desc"))) {
                return this.cacheProperties.get("desc");
            }
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (contentDescription = this.source.get().getContentDescription()) == null) {
                return null;
            }
            return contentDescription.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-desc:", e2);
        }
        return null;
    }

    public boolean dismiss() {
        return performAction(1048576);
    }

    public boolean dismissable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isDismissable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-dismissable:", e2);
            return false;
        }
    }

    public int drawingOrder() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return -1;
            }
            return this.source.get().getDrawingOrder();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-drawingOrder:", e2);
            return -1;
        }
    }

    public boolean editable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isEditable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-editable:", e2);
            return false;
        }
    }

    public boolean enabled() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isEnabled();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-enabled:", e2);
            return false;
        }
    }

    @RequiresApi(api = 30)
    public boolean enter() {
        return performAction(R.id.accessibilityActionImeEnter);
    }

    public boolean expand() {
        return performAction(262144);
    }

    public UiObjectCollection findByBounds(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m914d(i2, i3, i4, i5);
        return c0356a.m928r(this);
    }

    public UiObjectCollection findByBoundsContains(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m915e(i2, i3, i4, i5);
        return c0356a.m928r(this);
    }

    public UiObjectCollection findByBoundsInside(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m916f(i2, i3, i4, i5);
        return c0356a.m928r(this);
    }

    public UiObjectCollection findByClassName(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m917g(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByClassNameContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m918h(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByClassNameEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m919i(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findByClassNameEndsWith:", e2);
            return null;
        }
    }

    public UiObjectCollection findByClassNameMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m920j(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findByClassNameMatches:", e2);
            return null;
        }
    }

    public UiObjectCollection findByClassNameStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m921k(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByCombine(CombineFilter combineFilter) {
        if (combineFilter != null) {
            try {
                C0356a globalSelector = combineFilter.toGlobalSelector(null);
                if (globalSelector != null) {
                    return globalSelector.m928r(this);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findByCombine:", e2);
            }
        }
        return null;
    }

    public UiObjectCollection findByCombineWithChild(CombineFilterWithChild combineFilterWithChild) {
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    UiObjectCollection of = UiObjectCollection.of(null);
                    UiObjectCollection findByCombine = findByCombine(combineFilterWithChild.getParentFilter());
                    if (findByCombine != null && findByCombine.size() > 0) {
                        for (UiObject uiObject : findByCombine.getNodes()) {
                            if (uiObject != null && uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) != null) {
                                of.getNodes().add(uiObject);
                            }
                        }
                    }
                    return of;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findByCombineWithChild:", e2);
            }
        }
        return null;
    }

    public UiObjectCollection findByCombineWithoutChild(CombineFilterWithChild combineFilterWithChild) {
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    UiObjectCollection of = UiObjectCollection.of(null);
                    UiObjectCollection findByCombine = findByCombine(combineFilterWithChild.getParentFilter());
                    if (findByCombine != null && findByCombine.size() > 0) {
                        for (UiObject uiObject : findByCombine.getNodes()) {
                            if (uiObject != null && uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) == null) {
                                of.getNodes().add(uiObject);
                            }
                        }
                    }
                    return of;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findByCombineWithoutChild:", e2);
            }
        }
        return null;
    }

    public UiObjectCollection findByDesc(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m922l(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByDescContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m923m(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByDescEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m924n(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByDescMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m925o(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByDescStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m926p(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findById(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m931u(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findById:", e2);
            return null;
        }
    }

    public UiObjectCollection findByIdContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m932v(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByIdEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m933w(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByIdMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m934x(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByIdStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m935y(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByOperateOr(CombineFiltersWithOr combineFiltersWithOr) {
        if (combineFiltersWithOr != null) {
            try {
                if (combineFiltersWithOr.getFilters() != null && !combineFiltersWithOr.getFilters().isEmpty()) {
                    UiObjectCollection of = UiObjectCollection.of(null);
                    Iterator<CombineFilter> it = combineFiltersWithOr.getFilters().iterator();
                    while (it.hasNext()) {
                        UiObjectCollection findByCombine = findByCombine(it.next());
                        if (findByCombine.size() > 0) {
                            of.getNodes().addAll(findByCombine.getNodes());
                        }
                    }
                    return of;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findByOperateOr:", e2);
            }
        }
        return null;
    }

    public UiObjectCollection findByPackageName(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m888A(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByPackageNameContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m889B(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByPackageNameEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m890C(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByPackageNameMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m891D(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByPackageNameStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m892E(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByText(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m905R(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findByText:", e2);
            return null;
        }
    }

    public UiObjectCollection findByTextContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m906S(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findByTextContains:", e2);
            return null;
        }
    }

    public UiObjectCollection findByTextEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m907T(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findByTextEndsWith:", e2);
            return null;
        }
    }

    public UiObjectCollection findByTextMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m908U(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObjectCollection findByTextStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m909V(str);
            return c0356a.m928r(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findByTextStartsWith:", e2);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0007, code lost:
    
        if (r5.intValue() < 1) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public UiObject findChildUtilUpLevel(CombineFilter combineFilter, Integer num) {
        if (num != null) {
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findChildUtilUpLevel:", e2);
                return null;
            }
        }
        num = 10;
        if (combineFilter == null) {
            return null;
        }
        UiObject uiObject = this;
        while (uiObject != null) {
            if (num.intValue() <= 0) {
                return null;
            }
            UiObject findOneByCombine = uiObject.findOneByCombine(combineFilter);
            if (findOneByCombine != null) {
                return findOneByCombine;
            }
            uiObject = uiObject.parent();
            num = Integer.valueOf(num.intValue() - 1);
        }
        return null;
    }

    public UiObject findLastByBounds(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m914d(i2, i3, i4, i5);
        return c0356a.m927q(this);
    }

    public UiObject findLastByBoundsContains(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m915e(i2, i3, i4, i5);
        return c0356a.m927q(this);
    }

    public UiObject findLastByBoundsInside(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m916f(i2, i3, i4, i5);
        return c0356a.m927q(this);
    }

    public UiObject findLastByClassName(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m917g(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByClassNameContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m918h(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByClassNameEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m919i(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findLastByClassNameEndsWith:", e2);
            return null;
        }
    }

    public UiObject findLastByClassNameMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m920j(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findLastByClassNameMatches:", e2);
            return null;
        }
    }

    public UiObject findLastByClassNameStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m921k(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findLastByClassNameStartsWith:", e2);
            return null;
        }
    }

    public UiObject findLastByCombine(CombineFilter combineFilter) {
        if (combineFilter != null) {
            try {
                C0356a globalSelector = combineFilter.toGlobalSelector(null);
                if (globalSelector != null) {
                    return globalSelector.m927q(this);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findLastByCombine:", e2);
            }
        }
        return null;
    }

    public UiObject findLastByDesc(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m922l(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByDescContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m923m(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByDescEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m924n(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByDescMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m925o(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByDescStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m926p(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastById(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m931u(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByIdContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m932v(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByIdEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m933w(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByIdMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m934x(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByIdStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m935y(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByOperateOr(CombineFiltersWithOr combineFiltersWithOr) {
        if (combineFiltersWithOr == null) {
            return null;
        }
        try {
            if (combineFiltersWithOr.getFilters() == null || combineFiltersWithOr.getFilters().isEmpty()) {
                return null;
            }
            Iterator<CombineFilter> it = combineFiltersWithOr.getFilters().iterator();
            while (it.hasNext()) {
                UiObject findLastByCombine = findLastByCombine(it.next());
                if (findLastByCombine != null) {
                    return findLastByCombine;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findLastByOperateOr:", e2);
            return null;
        }
    }

    public UiObject findLastByText(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m905R(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findLastByText:", e2);
            return null;
        }
    }

    public UiObject findLastByTextContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m906S(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findLastByTextContains:", e2);
            return null;
        }
    }

    public UiObject findLastByTextEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m907T(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByTextMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m908U(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findLastByTextStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m909V(str);
            return c0356a.m927q(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findLastByTextStartsWith:", e2);
            return null;
        }
    }

    public UiObject findOneByBounds(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m914d(i2, i3, i4, i5);
        return c0356a.m930t(this);
    }

    public UiObject findOneByBoundsContains(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m915e(i2, i3, i4, i5);
        return c0356a.m930t(this);
    }

    public UiObject findOneByBoundsInside(int i2, int i3, int i4, int i5) {
        C0356a c0356a = new C0356a();
        c0356a.m916f(i2, i3, i4, i5);
        return c0356a.m930t(this);
    }

    public UiObject findOneByClassName(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m917g(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByClassNameContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m918h(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByClassNameEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m919i(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByClassNameEndsWith:", e2);
            return null;
        }
    }

    public UiObject findOneByClassNameMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m920j(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByClassNameMatches:", e2);
            return null;
        }
    }

    public UiObject findOneByClassNameStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m921k(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByClassNameStartsWith:", e2);
            return null;
        }
    }

    public UiObject findOneByCombine(CombineFilter combineFilter) {
        if (combineFilter != null) {
            try {
                C0356a globalSelector = combineFilter.toGlobalSelector(null);
                if (globalSelector != null) {
                    return globalSelector.m930t(this);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findOneByCombine:", e2);
            }
        }
        return null;
    }

    public UiObject findOneByCombineLoop(CombineFilter combineFilter) {
        if (combineFilter == null) {
            return null;
        }
        try {
            UiObject findOneByCombine = findOneByCombine(combineFilter);
            for (int i2 = 0; findOneByCombine == null && i2 < 20; i2++) {
                AbstractC0251g.T0(1);
                refresh();
                findOneByCombine = findOneByCombine(combineFilter);
            }
            return findOneByCombine;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByCombineLoop:", e2);
            return null;
        }
    }

    public UiObject findOneByCombineWithChild(CombineFilterWithChild combineFilterWithChild) {
        UiObjectCollection findByCombine;
        if (combineFilterWithChild == null) {
            return null;
        }
        try {
            if (combineFilterWithChild.getParentFilter() == null || (findByCombine = findByCombine(combineFilterWithChild.getParentFilter())) == null || findByCombine.size() <= 0) {
                return null;
            }
            for (int size = findByCombine.size() - 1; size >= 0; size--) {
                UiObject uiObject = findByCombine.get(size);
                if (uiObject != null && uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) != null) {
                    return uiObject;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByCombineWithChild:", e2);
            return null;
        }
    }

    public UiObject findOneByCombineWithParent(CombineFilterWithChild combineFilterWithChild) {
        UiObject findOneByCombine;
        if (combineFilterWithChild == null) {
            return null;
        }
        try {
            if (combineFilterWithChild.getParentFilter() == null) {
                return null;
            }
            UiObjectCollection findByCombine = findByCombine(combineFilterWithChild.getParentFilter());
            while (findByCombine != null) {
                if (findByCombine.size() <= 0) {
                    return null;
                }
                for (UiObject uiObject : findByCombine.getNodes()) {
                    if (uiObject != null && (findOneByCombine = uiObject.findOneByCombine(combineFilterWithChild.getChildFilter())) != null) {
                        return findOneByCombine;
                    }
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByCombineWithParent:", e2);
            return null;
        }
    }

    public UiObject findOneByCombineWithoutChild(CombineFilterWithChild combineFilterWithChild) {
        UiObjectCollection findByCombine;
        if (combineFilterWithChild == null) {
            return null;
        }
        try {
            if (combineFilterWithChild.getParentFilter() == null || (findByCombine = findByCombine(combineFilterWithChild.getParentFilter())) == null || findByCombine.size() <= 0) {
                return null;
            }
            for (UiObject uiObject : findByCombine.getNodes()) {
                if (uiObject != null && uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) == null) {
                    return uiObject;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByCombineWithoutChild:", e2);
            return null;
        }
    }

    public UiObject findOneByDesc(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m922l(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByDescContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m923m(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByDescEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m924n(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByDescMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m925o(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByDescStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m926p(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneById(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m931u(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneById:", e2);
            return null;
        }
    }

    public UiObject findOneByIdContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m932v(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByIdEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m933w(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByIdMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m934x(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByIdStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m935y(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByOperateOr(CombineFiltersWithOr combineFiltersWithOr) {
        if (combineFiltersWithOr == null) {
            return null;
        }
        try {
            if (combineFiltersWithOr.getFilters() == null || combineFiltersWithOr.getFilters().isEmpty()) {
                return null;
            }
            Iterator<CombineFilter> it = combineFiltersWithOr.getFilters().iterator();
            while (it.hasNext()) {
                UiObject findOneByCombine = findOneByCombine(it.next());
                if (findOneByCombine != null) {
                    return findOneByCombine;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByOperateOr:", e2);
            return null;
        }
    }

    public UiObject findOneByOperateOrLoop(CombineFiltersWithOr combineFiltersWithOr) {
        if (combineFiltersWithOr == null) {
            return null;
        }
        try {
            UiObject findOneByOperateOr = findOneByOperateOr(combineFiltersWithOr);
            for (int i2 = 0; findOneByOperateOr == null && i2 < 20; i2++) {
                AbstractC0251g.T0(1);
                refresh();
                findOneByOperateOr = findOneByOperateOr(combineFiltersWithOr);
            }
            return findOneByOperateOr;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByOperateOrLoop:", e2);
            return null;
        }
    }

    public UiObject findOneByPackageName(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m888A(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByPackageNameContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m889B(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByPackageNameEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m890C(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByPackageNameMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m891D(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByPackageNameStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m892E(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByPointContains(float f2, float f3) {
        C0356a c0356a = new C0356a();
        try {
            c0356a.f700a.add(new PointFilter(new Point(f2, f3), 1));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        return c0356a.m930t(this);
    }

    public UiObject findOneByText(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m905R(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByText:", e2);
            return null;
        }
    }

    public UiObject findOneByTextContains(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m906S(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByTextContains:", e2);
            return null;
        }
    }

    public UiObject findOneByTextEndsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m907T(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByTextEndsWith:", e2);
            return null;
        }
    }

    public UiObject findOneByTextMatches(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m908U(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
            return null;
        }
    }

    public UiObject findOneByTextStartsWith(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            C0356a c0356a = new C0356a();
            c0356a.m909V(str);
            return c0356a.m930t(this);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-findOneByTextStartsWith:", e2);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0008, code lost:
    
        if (r7.intValue() < 1) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public UiObject findParentByCombine(CombineFilter combineFilter, Integer num) {
        C0356a globalSelector;
        UiObject m930t;
        if (num != null) {
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findParentByCombine:", e2);
            }
        }
        num = 1;
        if (combineFilter != null && (globalSelector = combineFilter.toGlobalSelector(null)) != null && (m930t = globalSelector.m930t(this)) != null) {
            int intValue = num.intValue();
            while (intValue >= 1) {
                if (m930t.getParent() == null) {
                    break;
                }
                intValue--;
                m930t = new UiObject(m930t.getParent(), m930t.depth() - 1, -1);
            }
            return m930t;
        }
        return null;
    }

    public UiObject findParentUtilCombine(CombineFilter combineFilter) {
        if (combineFilter == null) {
            return null;
        }
        UiObject uiObject = this;
        do {
            try {
                if (uiObject.parent() == null) {
                    return null;
                }
                uiObject = uiObject.parent();
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-findParentUtilCombine:", e2);
                return null;
            }
        } while (uiObject.findOneByCombine(combineFilter) == null);
        return uiObject;
    }

    public boolean focus() {
        return performAction(1);
    }

    public boolean focusable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isFocusable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-focusable:", e2);
            return false;
        }
    }

    public boolean focused() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isFocused();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-focused:", e2);
            return false;
        }
    }

    public AccessibilityNodeInfoCompat getChild(int i2) {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || childCount() <= 0 || childCount() <= i2) {
                return null;
            }
            return this.source.get().getChild(i2);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-getChild:", e2);
            return null;
        }
    }

    public AccessibilityNodeInfoCompat getParent() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return null;
            }
            return this.source.get().getParent();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-getParent:", e2);
            return null;
        }
    }

    @SuppressLint({"DefaultLocale"})
    public String getProperty(String str) {
        char c;
        try {
            if (!AbstractC0026q.m151B(str)) {
                switch (str.hashCode()) {
                    case -2105498688:
                        if (str.equals("columnSpan")) {
                            c = '*';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2086369598:
                        if (str.equals("stateDesc")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2012029532:
                        if (str.equals("centerInParent")) {
                            c = '/';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1979905218:
                        if (str.equals("contentInvalid")) {
                            c = 26;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1964681502:
                        if (str.equals("clickable")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1924295322:
                        if (str.equals("centerInScreen")) {
                            c = '.';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1724171933:
                        if (str.equals("textSelectable")) {
                            c = 23;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1609594047:
                        if (str.equals("enabled")) {
                            c = 20;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1591577989:
                        if (str.equals("regionCount")) {
                            c = '+';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1504006192:
                        if (str.equals("paneTitle")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1473774508:
                        if (str.equals("hintText")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1354837162:
                        if (str.equals("column")) {
                            c = '(';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1207192371:
                        if (str.equals("multiLine")) {
                            c = 28;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1140076541:
                        if (str.equals("tooltip")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -994557277:
                        if (str.equals("screenReaderFocusable")) {
                            c = ' ';
                            break;
                        }
                        c = 65535;
                        break;
                    case -860736679:
                        if (str.equals("columnCount")) {
                            c = ')';
                            break;
                        }
                        c = 65535;
                        break;
                    case -713407024:
                        if (str.equals("drawingOrder")) {
                            c = '\"';
                            break;
                        }
                        c = 65535;
                        break;
                    case -691041417:
                        if (str.equals("focused")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case -294460212:
                        if (str.equals("uniqueId")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -267073497:
                        if (str.equals("roleDesc")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -9888733:
                        if (str.equals("className")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3355:
                        if (str.equals("id")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 113114:
                        if (str.equals("row")) {
                            c = '%';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3079825:
                        if (str.equals("desc")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3556653:
                        if (str.equals(TextBundle.TEXT_ENTRY)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 17743701:
                        if (str.equals("rowCount")) {
                            c = '&';
                            break;
                        }
                        c = 65535;
                        break;
                    case 66669991:
                        if (str.equals("scrollable")) {
                            c = 22;
                            break;
                        }
                        c = 65535;
                        break;
                    case 95472323:
                        if (str.equals("depth")) {
                            c = '#';
                            break;
                        }
                        c = 65535;
                        break;
                    case 346647841:
                        if (str.equals("indexInParent")) {
                            c = '$';
                            break;
                        }
                        c = 65535;
                        break;
                    case 398964322:
                        if (str.equals("checkable")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 742313895:
                        if (str.equals("checked")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 746986311:
                        if (str.equals("importantForAccessibility")) {
                            c = 30;
                            break;
                        }
                        c = 65535;
                        break;
                    case 783360658:
                        if (str.equals("canOpenPopup")) {
                            c = 29;
                            break;
                        }
                        c = 65535;
                        break;
                    case 795311618:
                        if (str.equals("heading")) {
                            c = 27;
                            break;
                        }
                        c = 65535;
                        break;
                    case 908759025:
                        if (str.equals("packageName")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 918550520:
                        if (str.equals("visibleToUser")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case 997604294:
                        if (str.equals("longClickable")) {
                            c = 19;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1191572123:
                        if (str.equals("selected")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1216985755:
                        if (str.equals("password")) {
                            c = 21;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1329151315:
                        if (str.equals("childCount")) {
                            c = '!';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1338877956:
                        if (str.equals("boundsInParent")) {
                            c = '-';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1386522692:
                        if (str.equals("rowSpan")) {
                            c = '\'';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1426612166:
                        if (str.equals("boundsInScreen")) {
                            c = ',';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1602416228:
                        if (str.equals("editable")) {
                            c = 24;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1629011506:
                        if (str.equals("focusable")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1933057242:
                        if (str.equals("textEntryKey")) {
                            c = 25;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1976364617:
                        if (str.equals("accessibilityFocused")) {
                            c = 16;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2062895929:
                        if (str.equals("showingHintText")) {
                            c = 31;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        return id();
                    case 1:
                        return uniqueId();
                    case 2:
                        return text();
                    case 3:
                        return desc();
                    case 4:
                        return paneTitle();
                    case 5:
                        return hintText();
                    case 6:
                        return tooltipText();
                    case 7:
                        return stateDesc();
                    case '\b':
                        return roleDesc();
                    case '\t':
                        return className();
                    case '\n':
                        return packageName();
                    case 11:
                        return String.valueOf(checkable());
                    case '\f':
                        return String.valueOf(checked());
                    case '\r':
                        return String.valueOf(focusable());
                    case 14:
                        return String.valueOf(focused());
                    case 15:
                        return String.valueOf(visibleToUser());
                    case 16:
                        return String.valueOf(accessibilityFocused());
                    case 17:
                        return String.valueOf(selected());
                    case 18:
                        return String.valueOf(clickable());
                    case 19:
                        return String.valueOf(longClickable());
                    case 20:
                        return String.valueOf(enabled());
                    case 21:
                        return String.valueOf(password());
                    case 22:
                        return String.valueOf(scrollable());
                    case 23:
                        return String.valueOf(textSelectable());
                    case 24:
                        return String.valueOf(editable());
                    case 25:
                        return String.valueOf(textEntryKey());
                    case 26:
                        return String.valueOf(contentInvalid());
                    case 27:
                        return String.valueOf(heading());
                    case 28:
                        return String.valueOf(multiLine());
                    case 29:
                        return String.valueOf(canOpenPopup());
                    case 30:
                        return String.valueOf(importantForAccessibility());
                    case NamedGroup.brainpoolP256r1tls13 /* 31 */:
                        return String.valueOf(showingHintText());
                    case ' ':
                        return String.valueOf(screenReaderFocusable());
                    case '!':
                        return String.valueOf(childCount());
                    case '\"':
                        return String.valueOf(drawingOrder());
                    case '#':
                        return String.valueOf(depth());
                    case '$':
                        return String.valueOf(indexInParent());
                    case '%':
                        return String.valueOf(row());
                    case '&':
                        return String.valueOf(rowCount());
                    case '\'':
                        return String.valueOf(rowSpan());
                    case '(':
                        return String.valueOf(column());
                    case ')':
                        return String.valueOf(columnCount());
                    case '*':
                        return String.valueOf(columnSpan());
                    case '+':
                        return String.valueOf(regionCount());
                    case ',':
                        return AbstractC0252h.m693N(boundsInScreen());
                    case '-':
                        return AbstractC0252h.m693N(boundsInParent());
                    case '.':
                        return AbstractC0252h.m693N(centerInScreen());
                    case '/':
                        return AbstractC0252h.m693N(centerInParent());
                    default:
                        Log.d(TAG, "未识别属性");
                        return null;
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-getProperty:", e2);
        }
        return null;
    }

    public Region getRegionAt(int i2) {
        AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo;
        AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo2;
        Region regionAt;
        if (Build.VERSION.SDK_INT < 29 || source() == null) {
            return null;
        }
        touchDelegateInfo = source().getTouchDelegateInfo();
        if (touchDelegateInfo == null) {
            return null;
        }
        touchDelegateInfo2 = source().getTouchDelegateInfo();
        Objects.requireNonNull(touchDelegateInfo2);
        regionAt = AbstractC0004d.m43l(touchDelegateInfo2).getRegionAt(i2);
        return regionAt;
    }

    public UiObject getTargetForRegion(int i2) {
        AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo;
        Region regionAt;
        AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo2;
        AccessibilityNodeInfo targetForRegion;
        if (Build.VERSION.SDK_INT < 29 || source() == null) {
            return null;
        }
        touchDelegateInfo = source().getTouchDelegateInfo();
        if (touchDelegateInfo == null || regionCount() <= i2 || (regionAt = getRegionAt(i2)) == null) {
            return null;
        }
        touchDelegateInfo2 = source().getTouchDelegateInfo();
        Objects.requireNonNull(touchDelegateInfo2);
        targetForRegion = AbstractC0004d.m43l(touchDelegateInfo2).getTargetForRegion(regionAt);
        if (targetForRegion != null) {
            return new UiObject(targetForRegion, this.depth + 1, i2);
        }
        return null;
    }

    public CharSequence getText() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return null;
            }
            return this.source.get().getText();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-getText:", e2);
            return null;
        }
    }

    public int hashCode() {
        AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
        if (atomicReference == null || atomicReference.get() == null) {
            return 0;
        }
        return this.source.get().hashCode();
    }

    public boolean heading() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isHeading();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-heading:", e2);
            return false;
        }
    }

    public String hintText() {
        CharSequence hintText;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (hintText = this.source.get().getHintText()) == null) {
                return null;
            }
            return hintText.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-hintText:", e2);
        }
        return null;
    }

    public String id() {
        try {
            if (!AbstractC0026q.m151B(this.cacheProperties.get("id"))) {
                return this.cacheProperties.get("id");
            }
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return null;
            }
            return this.source.get().getViewIdResourceName();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-id:", e2);
            return null;
        }
    }

    public boolean importantForAccessibility() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isImportantForAccessibility();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-importantForAccessibility:", e2);
            return false;
        }
    }

    public int indexInParent() {
        return this.indexInParent;
    }

    public boolean isRootRecycle() {
        return this.rootRecycle;
    }

    public boolean longClick() {
        return performAction(32);
    }

    public boolean longClickable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isLongClickable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-longClickable:", e2);
            return false;
        }
    }

    public boolean multiLine() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isMultiLine();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-multiLine:", e2);
            return false;
        }
    }

    public String packageName() {
        CharSequence packageName;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (packageName = this.source.get().getPackageName()) == null) {
                return null;
            }
            return packageName.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-packageName:", e2);
        }
        return null;
    }

    public String paneTitle() {
        CharSequence paneTitle;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (paneTitle = this.source.get().getPaneTitle()) == null) {
                return null;
            }
            return paneTitle.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-paneTitle:", e2);
        }
        return null;
    }

    public UiObject parent() {
        AccessibilityNodeInfoCompat parent;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (parent = this.source.get().getParent()) == null) {
                return null;
            }
            return new UiObject(parent, this.depth - 1, -1);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-parent:", e2);
            return null;
        }
    }

    public boolean password() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isPassword();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-password:", e2);
            return false;
        }
    }

    public boolean paste() {
        return performAction(32768);
    }

    public boolean performAction(int i2) {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().performAction(i2);
        } catch (IllegalStateException e2) {
            AbstractC0026q.m186s("UiObject-performAction:", e2);
            return false;
        }
    }

    public void recycle() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || MyAccessibilityService.m556Z(this.source.get().unwrap())) {
                return;
            }
            this.source.get().recycle();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-recycle:", e2);
        }
    }

    public boolean refresh() {
        try {
            if (AbstractC0249e.m621j()) {
                return MyAccessibilityService.m548I(this);
            }
            return false;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-refresh:", e2);
            return false;
        }
    }

    public int regionCount() {
        AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo;
        AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo2;
        int regionCount;
        if (Build.VERSION.SDK_INT < 29 || source() == null) {
            return 0;
        }
        touchDelegateInfo = source().getTouchDelegateInfo();
        if (touchDelegateInfo == null) {
            return 0;
        }
        touchDelegateInfo2 = source().getTouchDelegateInfo();
        Objects.requireNonNull(touchDelegateInfo2);
        regionCount = AbstractC0004d.m43l(touchDelegateInfo2).getRegionCount();
        return regionCount;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0007, code lost:
    
        if (r5.intValue() <= 0) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean repeatClick(Integer num) {
        if (num != null) {
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-repeatClick:", e2);
            }
        }
        num = 7;
        int i2 = 0;
        while (i2 < num.intValue()) {
            try {
                if (click()) {
                    AbstractC0251g.T0(1);
                    i2++;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s(TAG, e3);
            }
        }
        return i2 == num.intValue();
    }

    public String roleDesc() {
        CharSequence roleDescription;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (roleDescription = this.source.get().getRoleDescription()) == null) {
                return null;
            }
            return roleDescription.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-roleDesc:", e2);
        }
        return null;
    }

    public int row() {
        AccessibilityNodeInfoCompat.CollectionItemInfoCompat collectionItemInfo;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference != null && atomicReference.get() != null && (collectionItemInfo = this.source.get().getCollectionItemInfo()) != null) {
                return collectionItemInfo.getRowIndex();
            }
            return -1;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-row:", e2);
        }
        return -1;
    }

    public int rowCount() {
        AccessibilityNodeInfoCompat.CollectionInfoCompat collectionInfo;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference != null && atomicReference.get() != null && (collectionInfo = this.source.get().getCollectionInfo()) != null) {
                return collectionInfo.getRowCount();
            }
            return 0;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-rowCount:", e2);
        }
        return 0;
    }

    public int rowSpan() {
        AccessibilityNodeInfoCompat.CollectionItemInfoCompat collectionItemInfo;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference != null && atomicReference.get() != null && (collectionItemInfo = this.source.get().getCollectionItemInfo()) != null) {
                return collectionItemInfo.getRowSpan();
            }
            return -1;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-rowSpan:", e2);
        }
        return -1;
    }

    public boolean screenReaderFocusable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isScreenReaderFocusable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-screenReaderFocusable:", e2);
            return false;
        }
    }

    public boolean scrollBackward() {
        boolean z2 = false;
        try {
            if (canScrollBackward() && performAction(8192)) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollBackward:", e2);
        }
        return z2;
    }

    public boolean scrollBackwardByGesture() {
        boolean z2 = false;
        try {
            if (canScrollBackward() && simulationScrollBackward()) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollBackwardByGesture:", e2);
        }
        return z2;
    }

    public void scrollBackwardEnd() {
        while (scrollBackward()) {
            try {
                AbstractC0251g.T0(1);
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-scrollBackwardEnd:", e2);
                return;
            }
        }
    }

    public UiObject scrollBackwardUtil(InterfaceC0978a interfaceC0978a) {
        if (interfaceC0978a == null) {
            return null;
        }
        try {
            UiObject mo1471c = interfaceC0978a.mo1471c(this);
            if (mo1471c != null && mo1471c.visibleToUser()) {
                return mo1471c;
            }
            for (int i2 = 0; scrollBackward() && i2 <= interfaceC0978a.mo1470a(); i2++) {
                UiObject utilRefresh = utilRefresh(interfaceC0978a);
                if (utilRefresh != null && utilRefresh.visibleToUser()) {
                    return utilRefresh;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollBackwardUtil:", e2);
            return null;
        }
    }

    public UiObjectCollection scrollBackwardUtilMultiple(InterfaceC0979b interfaceC0979b) {
        if (interfaceC0979b == null) {
            return null;
        }
        try {
            UiObjectCollection mo1472b = interfaceC0979b.mo1472b(this);
            if (mo1472b != null && mo1472b.size() > 0) {
                return mo1472b;
            }
            for (int i2 = 0; scrollBackward() && i2 <= interfaceC0979b.mo1470a(); i2++) {
                UiObjectCollection utilMultipleRefresh = utilMultipleRefresh(interfaceC0979b);
                if (utilMultipleRefresh != null && utilMultipleRefresh.size() > 0) {
                    return utilMultipleRefresh;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollBackwardUtilMultiple:", e2);
            return null;
        }
    }

    public boolean scrollDown() {
        boolean z2 = false;
        try {
            if (canScrollDown() && performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId())) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollDown:", e2);
        }
        return z2;
    }

    public boolean scrollForward() {
        boolean z2 = false;
        try {
            if (canScrollForward() && performAction(4096)) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollForward:", e2);
        }
        return z2;
    }

    public boolean scrollForwardByGesture() {
        boolean z2 = false;
        try {
            if (canScrollForward() && simulationScrollForward()) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollForwardByGesture:", e2);
        }
        return z2;
    }

    public void scrollForwardEnd() {
        while (scrollForward()) {
            try {
                AbstractC0251g.T0(1);
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-scrollForwardEnd:", e2);
                return;
            }
        }
    }

    public UiObject scrollForwardUtil(InterfaceC0978a interfaceC0978a) {
        if (interfaceC0978a == null) {
            return null;
        }
        try {
            UiObject mo1471c = interfaceC0978a.mo1471c(this);
            if (mo1471c != null && mo1471c.visibleToUser()) {
                return mo1471c;
            }
            for (int i2 = 0; scrollForward() && i2 <= interfaceC0978a.mo1470a(); i2++) {
                UiObject utilRefresh = utilRefresh(interfaceC0978a);
                if (utilRefresh != null && utilRefresh.visibleToUser()) {
                    return utilRefresh;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollForwardUtil:", e2);
            return null;
        }
    }

    public UiObjectCollection scrollForwardUtilMultiple(InterfaceC0979b interfaceC0979b) {
        if (interfaceC0979b == null) {
            return null;
        }
        try {
            UiObjectCollection mo1472b = interfaceC0979b.mo1472b(this);
            if (mo1472b != null && mo1472b.size() > 0) {
                return mo1472b;
            }
            for (int i2 = 0; scrollForward() && i2 <= interfaceC0979b.mo1470a(); i2++) {
                UiObjectCollection utilMultipleRefresh = utilMultipleRefresh(interfaceC0979b);
                if (utilMultipleRefresh != null && utilMultipleRefresh.size() > 0) {
                    return utilMultipleRefresh;
                }
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollForwardUtilMultiple:", e2);
            return null;
        }
    }

    public boolean scrollLeft() {
        boolean z2 = false;
        try {
            if (canScrollLeft() && performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId())) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollLeft:", e2);
        }
        return z2;
    }

    public boolean scrollRight() {
        boolean z2 = false;
        try {
            if (canScrollRight() && performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId())) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollRight:", e2);
        }
        return z2;
    }

    public boolean scrollTo(int i2, int i3) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_ROW_INT, i2), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_COLUMN_INT, i3));
    }

    public boolean scrollUp() {
        boolean z2 = false;
        try {
            if (canScrollUp() && performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId())) {
                z2 = true;
            }
            if (z2) {
                refresh();
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollUp:", e2);
        }
        return z2;
    }

    public boolean scrollable() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isScrollable();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-scrollable:", e2);
            return false;
        }
    }

    public boolean select() {
        return performAction(4);
    }

    public boolean selected() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isSelected();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-selected:", e2);
            return false;
        }
    }

    public void setBoundsInScreen(Rect rect) {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || rect == null || rect.isEmpty()) {
                return;
            }
            this.source.get().setBoundsInScreen(rect);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-setBoundsInScreen:", e2);
        }
    }

    public boolean setProgress(float f2) {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(), new C0278c(f2));
    }

    public void setRootRecycle(boolean z2) {
        this.rootRecycle = z2;
    }

    public boolean setSelection(int i2, int i3) {
        return performAction(131072, new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_START_INT, i2), new C0279d(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_END_INT, i3));
    }

    public boolean setText(String str) {
        return performAction(2097152, new C0277b(str));
    }

    public void setUniqueId(String str) {
        this.uniqueId = str;
    }

    public boolean show() {
        return performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId());
    }

    public boolean showingHintText() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isShowingHintText();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-showingHintText:", e2);
            return false;
        }
    }

    public boolean simulationScrollBackward() {
        AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
        if (atomicReference != null && atomicReference.get() != null) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.source.get();
            Rect rect = new Rect();
            accessibilityNodeInfoCompat.getBoundsInScreen(rect);
            AbstractC0178a.m341c(rect);
            Point centerInScreen = centerInScreen();
            if (rect.width() > 0 && rect.height() > 0 && centerInScreen != null) {
                Point point = new Point(centerInScreen.getX(), rect.top + 200);
                return AbstractC0251g.m646S(10L, 100L, point, new Point(point.getX(), point.getY() + 100.0f));
            }
        }
        return false;
    }

    public boolean simulationScrollForward() {
        AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
        if (atomicReference != null && atomicReference.get() != null) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = this.source.get();
            Rect rect = new Rect();
            accessibilityNodeInfoCompat.getBoundsInScreen(rect);
            AbstractC0178a.m341c(rect);
            Point centerInScreen = centerInScreen();
            if (rect.width() > 0 && rect.height() > 0 && centerInScreen != null) {
                Point point = new Point(centerInScreen.getX(), rect.bottom - 200);
                return AbstractC0251g.m646S(10L, 100L, point, new Point(point.getX(), point.getY() - 100.0f));
            }
        }
        return false;
    }

    public AccessibilityNodeInfo source() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return null;
            }
            return this.source.get().unwrap();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-source:", e2);
            return null;
        }
    }

    public String stateDesc() {
        return null;
    }

    public String text() {
        CharSequence text;
        try {
            if (!AbstractC0026q.m151B(this.cacheProperties.get(TextBundle.TEXT_ENTRY))) {
                return this.cacheProperties.get(TextBundle.TEXT_ENTRY);
            }
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (text = this.source.get().getText()) == null) {
                return null;
            }
            return text.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-text:", e2);
        }
        return null;
    }

    public boolean textEntryKey() {
        return false;
    }

    public boolean textSelectable() {
        return false;
    }

    @NonNull
    public String toString() {
        AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
        return (atomicReference == null || atomicReference.get() == null) ? "{}" : this.source.get().toString();
    }

    public String tooltipText() {
        CharSequence tooltipText;
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null || (tooltipText = this.source.get().getTooltipText()) == null) {
                return null;
            }
            return tooltipText.toString();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-tooltipText:", e2);
        }
        return null;
    }

    public String uniqueId() {
        return this.uniqueId;
    }

    public UiObjectCollection utilMultipleRefresh(InterfaceC0979b interfaceC0979b) {
        UiObjectCollection mo1472b;
        int i2 = 0;
        while (i2 < 10) {
            try {
                refresh();
                Thread.sleep(100L);
                mo1472b = interfaceC0979b.mo1472b(this);
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-utilMultipleRefresh:", e2);
            }
            if (mo1472b != null && mo1472b.size() > 0) {
                return mo1472b;
            }
            i2++;
        }
        return null;
    }

    public UiObject utilRefresh(InterfaceC0978a interfaceC0978a) {
        UiObject mo1471c;
        int i2 = 0;
        while (i2 < 10) {
            try {
                refresh();
                Thread.sleep(100L);
                mo1471c = interfaceC0978a.mo1471c(this);
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObject-utilRefresh:", e2);
            }
            if (mo1471c != null && mo1471c.visibleToUser()) {
                return mo1471c;
            }
            i2++;
        }
        return null;
    }

    public boolean visibleToUser() {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().isVisibleToUser();
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-visibleToUser:", e2);
            return false;
        }
    }

    public UiObject(AccessibilityNodeInfo accessibilityNodeInfo, int i2, int i3) {
        this.rootRecycle = false;
        this.cacheProperties = new LinkedHashMap();
        this.source = new AtomicReference<>(AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo));
        this.depth = i2;
        this.indexInParent = i3;
    }

    public static UiObject createRoot(AccessibilityNodeInfo accessibilityNodeInfo, boolean z2) {
        try {
            if (accessibilityNodeInfo != null) {
                return new UiObject(accessibilityNodeInfo, 0, -1, z2);
            }
            Log.d(TAG, "createRoot source is null");
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-createRoot:", e2);
            return null;
        }
    }

    public boolean performAction(int i2, Bundle bundle) {
        try {
            AtomicReference<AccessibilityNodeInfoCompat> atomicReference = this.source;
            if (atomicReference == null || atomicReference.get() == null) {
                return false;
            }
            return this.source.get().performAction(i2, bundle);
        } catch (IllegalStateException e2) {
            AbstractC0026q.m186s("UiObject-performAction:", e2);
            return false;
        }
    }

    public UiObject(AccessibilityNodeInfo accessibilityNodeInfo, int i2, int i3, boolean z2) {
        this.rootRecycle = false;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.cacheProperties = linkedHashMap;
        this.source = new AtomicReference<>(AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo));
        this.depth = i2;
        this.indexInParent = i3;
        if (z2) {
            String text = text();
            if (!AbstractC0026q.m151B(text)) {
                linkedHashMap.put(TextBundle.TEXT_ENTRY, text);
            }
            String id = id();
            if (!AbstractC0026q.m151B(id)) {
                Log.d(TAG, "cache node id:" + id);
                linkedHashMap.put("id", id);
            }
            String desc = desc();
            if (AbstractC0026q.m151B(desc)) {
                return;
            }
            linkedHashMap.put("desc", desc);
        }
    }

    public UiObject findOneByPointContains(int i2, int i3) {
        C0356a c0356a = new C0356a();
        try {
            c0356a.f700a.add(new PointFilter(new Point(i2, i3), 1));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        return c0356a.m930t(this);
    }

    public boolean performAction(int i2, AbstractC0276a... abstractC0276aArr) {
        try {
            return performAction(i2, AbstractC0026q.m171a(abstractC0276aArr));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiObject-performAction:", e2);
            return false;
        }
    }
}
