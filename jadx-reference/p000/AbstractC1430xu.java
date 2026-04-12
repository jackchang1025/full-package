package p000;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.WeakHashMap;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xu */
/* loaded from: classes.dex */
public abstract class AbstractC1430xu extends C0608i4 {

    /* renamed from: b3 */
    public static final Rect f61180b3 = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    /* renamed from: b4 */
    public static final C1351vv f61181b4 = new C1351vv(23);

    /* renamed from: b5 */
    public static final C1351vv f61182b5 = new C1351vv(24);

    /* renamed from: a7 */
    public final AccessibilityManager f61187a7;

    /* renamed from: a8 */
    public final View f61188a8;

    /* renamed from: a9 */
    public C1429xt f61189a9;

    /* renamed from: a3 */
    public final Rect f61183a3 = new Rect();

    /* renamed from: a4 */
    public final Rect f61184a4 = new Rect();

    /* renamed from: a5 */
    public final Rect f61185a5 = new Rect();

    /* renamed from: a6 */
    public final int[] f61186a6 = new int[2];

    /* renamed from: b0 */
    public int f61190b0 = Integer.MIN_VALUE;

    /* renamed from: b1 */
    public int f61191b1 = Integer.MIN_VALUE;

    /* renamed from: b2 */
    public int f61192b2 = Integer.MIN_VALUE;

    public AbstractC1430xu(View view) {
        this.f61188a8 = view;
        this.f61187a7 = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        view.setFocusable(true);
        WeakHashMap weakHashMap = xa1.f61054a0;
        if (fa1.m212765a2(view) == 0) {
            fa1.m212781b8(view, 1);
        }
    }

    @Override // p000.C0608i4
    /* renamed from: a1 */
    public final tg0 mo213098a1(View view) {
        if (this.f61189a9 == null) {
            this.f61189a9 = new C1429xt(this);
        }
        return this.f61189a9;
    }

    @Override // p000.C0608i4
    /* renamed from: a3 */
    public final void mo210912a3(View view, C0748k7 c0748k7) {
        this.f56792a0.onInitializeAccessibilityNodeInfo(view, c0748k7.f57472a0);
        mo213055b9(c0748k7);
    }

    /* renamed from: a9 */
    public final boolean m215208a9(int i) {
        if (this.f61191b1 != i) {
            return false;
        }
        this.f61191b1 = Integer.MIN_VALUE;
        mo213056c1(i, false);
        m215216c3(i, 8);
        return true;
    }

    /* renamed from: b0 */
    public final AccessibilityEvent m215209b0(int i, int i2) {
        View view = this.f61188a8;
        if (i == -1) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2);
            view.onInitializeAccessibilityEvent(accessibilityEventObtain);
            return accessibilityEventObtain;
        }
        AccessibilityEvent accessibilityEventObtain2 = AccessibilityEvent.obtain(i2);
        C0748k7 c0748k7M215214b7 = m215214b7(i);
        accessibilityEventObtain2.getText().add(c0748k7M215214b7.m213462a5());
        AccessibilityNodeInfo accessibilityNodeInfo = c0748k7M215214b7.f57472a0;
        accessibilityEventObtain2.setContentDescription(accessibilityNodeInfo.getContentDescription());
        accessibilityEventObtain2.setScrollable(accessibilityNodeInfo.isScrollable());
        accessibilityEventObtain2.setPassword(accessibilityNodeInfo.isPassword());
        accessibilityEventObtain2.setEnabled(accessibilityNodeInfo.isEnabled());
        accessibilityEventObtain2.setChecked(accessibilityNodeInfo.isChecked());
        if (accessibilityEventObtain2.getText().isEmpty() && accessibilityEventObtain2.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        accessibilityEventObtain2.setClassName(accessibilityNodeInfo.getClassName());
        AbstractC0804l1.m213772a0(accessibilityEventObtain2, view, i);
        accessibilityEventObtain2.setPackageName(view.getContext().getPackageName());
        return accessibilityEventObtain2;
    }

    /* renamed from: b1 */
    public final C0748k7 m215210b1(int i) {
        AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain();
        C0748k7 c0748k7 = new C0748k7(accessibilityNodeInfoObtain);
        accessibilityNodeInfoObtain.setEnabled(true);
        accessibilityNodeInfoObtain.setFocusable(true);
        c0748k7.m213464a7("android.view.View");
        Rect rect = f61180b3;
        accessibilityNodeInfoObtain.setBoundsInParent(rect);
        accessibilityNodeInfoObtain.setBoundsInScreen(rect);
        View view = this.f61188a8;
        accessibilityNodeInfoObtain.setParent(view);
        mo211131c0(i, c0748k7);
        if (c0748k7.m213462a5() == null && accessibilityNodeInfoObtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        Rect rect2 = this.f61184a4;
        c0748k7.m213461a4(rect2);
        if (rect2.equals(rect)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = accessibilityNodeInfoObtain.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoObtain.setPackageName(view.getContext().getPackageName());
        c0748k7.f57473a1 = i;
        accessibilityNodeInfoObtain.setSource(view, i);
        if (this.f61190b0 == i) {
            accessibilityNodeInfoObtain.setAccessibilityFocused(true);
            c0748k7.m213458a0(128);
        } else {
            accessibilityNodeInfoObtain.setAccessibilityFocused(false);
            c0748k7.m213458a0(64);
        }
        boolean z = this.f61191b1 == i;
        if (z) {
            c0748k7.m213458a0(2);
        } else if (accessibilityNodeInfoObtain.isFocusable()) {
            c0748k7.m213458a0(1);
        }
        accessibilityNodeInfoObtain.setFocused(z);
        int[] iArr = this.f61186a6;
        view.getLocationOnScreen(iArr);
        Rect rect3 = this.f61183a3;
        accessibilityNodeInfoObtain.getBoundsInScreen(rect3);
        if (rect3.equals(rect)) {
            c0748k7.m213461a4(rect3);
            rect3.offset(iArr[0] - view.getScrollX(), iArr[1] - view.getScrollY());
        }
        Rect rect4 = this.f61185a5;
        if (view.getLocalVisibleRect(rect4)) {
            rect4.offset(iArr[0] - view.getScrollX(), iArr[1] - view.getScrollY());
            if (rect3.intersect(rect4)) {
                accessibilityNodeInfoObtain.setBoundsInScreen(rect3);
                if (!rect3.isEmpty() && view.getWindowVisibility() == 0) {
                    Object parent = view.getParent();
                    while (true) {
                        if (parent instanceof View) {
                            View view2 = (View) parent;
                            if (view2.getAlpha() <= 0.0f || view2.getVisibility() != 0) {
                                break;
                            }
                            parent = view2.getParent();
                        } else if (parent != null) {
                            c0748k7.f57472a0.setVisibleToUser(true);
                        }
                    }
                }
            }
        }
        return c0748k7;
    }

    /* renamed from: b2 */
    public final boolean m215211b2(MotionEvent motionEvent) {
        int i;
        AccessibilityManager accessibilityManager = this.f61187a7;
        if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            int iMo211128b3 = mo211128b3(motionEvent.getX(), motionEvent.getY());
            int i2 = this.f61192b2;
            if (i2 != iMo211128b3) {
                this.f61192b2 = iMo211128b3;
                m215216c3(iMo211128b3, 128);
                m215216c3(i2, PSKKeyManager.MAX_KEY_LENGTH_BYTES);
            }
            if (iMo211128b3 == Integer.MIN_VALUE) {
                return false;
            }
        } else {
            if (action != 10 || (i = this.f61192b2) == Integer.MIN_VALUE) {
                return false;
            }
            if (i != Integer.MIN_VALUE) {
                this.f61192b2 = Integer.MIN_VALUE;
                m215216c3(Integer.MIN_VALUE, 128);
                m215216c3(i, PSKKeyManager.MAX_KEY_LENGTH_BYTES);
                return true;
            }
        }
        return true;
    }

    /* renamed from: b3 */
    public abstract int mo211128b3(float f, float f2);

    /* renamed from: b4 */
    public abstract void mo211129b4(ArrayList arrayList);

    /* renamed from: b5 */
    public final void m215212b5(int i) {
        View view;
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.f61187a7.isEnabled() || (parent = (view = this.f61188a8).getParent()) == null) {
            return;
        }
        AccessibilityEvent accessibilityEventM215209b0 = m215209b0(i, 2048);
        AbstractC0609i5.m213103a1(accessibilityEventM215209b0, 0);
        parent.requestSendAccessibilityEvent(view, accessibilityEventM215209b0);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01a5  */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m215213b6(int i, Rect rect) {
        int i2;
        int i3;
        Object obj;
        C0748k7 c0748k7;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        ArrayList arrayList = new ArrayList();
        mo211129b4(arrayList);
        h11 h11Var = new h11();
        for (int i9 = 0; i9 < arrayList.size(); i9++) {
            h11Var.m212993a2(((Integer) arrayList.get(i9)).intValue(), m215210b1(((Integer) arrayList.get(i9)).intValue()));
        }
        int i10 = this.f61191b1;
        C0748k7 c0748k72 = i10 == Integer.MIN_VALUE ? null : (C0748k7) h11Var.m212992a1(i10, null);
        C1351vv c1351vv = f61181b4;
        C1351vv c1351vv2 = f61182b5;
        View view = this.f61188a8;
        if (i == 1 || i == 2) {
            i2 = -1;
            i3 = 0;
            WeakHashMap weakHashMap = xa1.f61054a0;
            boolean z = ga1.m212904a3(view) == 1;
            c1351vv2.getClass();
            int i11 = h11Var.f56597a2;
            ArrayList arrayList2 = new ArrayList(i11);
            for (int i12 = 0; i12 < i11; i12++) {
                arrayList2.add((C0748k7) h11Var.f56596a1[i12]);
            }
            Collections.sort(arrayList2, new C0397d(c1351vv, z));
            if (i == 1) {
                int size = arrayList2.size();
                if (c0748k72 != null) {
                    size = arrayList2.indexOf(c0748k72);
                }
                int i13 = size - 1;
                if (i13 >= 0) {
                    obj = arrayList2.get(i13);
                }
                c0748k7 = (C0748k7) obj;
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
                }
                int size2 = arrayList2.size();
                int iLastIndexOf = (c0748k72 == null ? -1 : arrayList2.lastIndexOf(c0748k72)) + 1;
                obj = iLastIndexOf < size2 ? arrayList2.get(iLastIndexOf) : null;
                c0748k7 = (C0748k7) obj;
            }
        } else {
            if (i != 17 && i != 33 && i != 66 && i != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            Rect rect2 = new Rect();
            int i14 = this.f61191b1;
            if (i14 != Integer.MIN_VALUE) {
                m215214b7(i14).m213461a4(rect2);
            } else if (rect != null) {
                rect2.set(rect);
            } else {
                int width = view.getWidth();
                int height = view.getHeight();
                if (i == 17) {
                    i6 = -1;
                    rect2.set(width, 0, width, height);
                } else if (i == 33) {
                    i6 = -1;
                    rect2.set(0, height, width, height);
                } else if (i == 66) {
                    i6 = -1;
                    rect2.set(-1, 0, -1, height);
                } else {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    i6 = -1;
                    rect2.set(0, -1, width, -1);
                }
                Rect rect3 = new Rect(rect2);
                if (i != 17) {
                    i3 = 0;
                    rect3.offset(rect2.width() + 1, 0);
                } else if (i == 33) {
                    i3 = 0;
                    rect3.offset(0, rect2.height() + 1);
                } else if (i == 66) {
                    i3 = 0;
                    rect3.offset(-(rect2.width() + 1), 0);
                } else {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    i3 = 0;
                    rect3.offset(0, -(rect2.height() + 1));
                }
                c1351vv2.getClass();
                i7 = h11Var.f56597a2;
                Rect rect4 = new Rect();
                c0748k7 = null;
                for (i8 = i3; i8 < i7; i8++) {
                    C0748k7 c0748k73 = (C0748k7) h11Var.f56596a1[i8];
                    if (c0748k73 != c0748k72) {
                        c1351vv.getClass();
                        c0748k73.m213461a4(rect4);
                        if (kg1.m213517c3(i, rect2, rect4)) {
                            if (kg1.m213517c3(i, rect2, rect3) && !kg1.m213501a1(i, rect2, rect4, rect3)) {
                                if (!kg1.m213501a1(i, rect2, rect3, rect4)) {
                                    int iM213525d1 = kg1.m213525d1(i, rect2, rect4);
                                    int iM213526d2 = kg1.m213526d2(i, rect2, rect4);
                                    int i15 = (iM213526d2 * iM213526d2) + (iM213525d1 * 13 * iM213525d1);
                                    int iM213525d12 = kg1.m213525d1(i, rect2, rect3);
                                    int iM213526d22 = kg1.m213526d2(i, rect2, rect3);
                                    if (i15 < (iM213526d22 * iM213526d22) + (iM213525d12 * 13 * iM213525d12)) {
                                        rect3.set(rect4);
                                        c0748k7 = c0748k73;
                                    }
                                }
                            }
                        }
                    }
                }
                i2 = i6;
            }
            i6 = -1;
            Rect rect32 = new Rect(rect2);
            if (i != 17) {
            }
            c1351vv2.getClass();
            i7 = h11Var.f56597a2;
            Rect rect42 = new Rect();
            c0748k7 = null;
            while (i8 < i7) {
            }
            i2 = i6;
        }
        C0748k7 c0748k74 = c0748k7;
        if (c0748k74 == null) {
            i5 = Integer.MIN_VALUE;
        } else {
            int i16 = i3;
            while (true) {
                if (i16 >= h11Var.f56597a2) {
                    i4 = i2;
                    break;
                }
                if (h11Var.f56596a1[i16] == c0748k74) {
                    i4 = i16;
                    break;
                }
                i16++;
            }
            i5 = h11Var.f56595a0[i4];
        }
        return m215215c2(i5);
    }

    /* renamed from: b7 */
    public final C0748k7 m215214b7(int i) {
        if (i != -1) {
            return m215210b1(i);
        }
        View view = this.f61188a8;
        AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain(view);
        C0748k7 c0748k7 = new C0748k7(accessibilityNodeInfoObtain);
        WeakHashMap weakHashMap = xa1.f61054a0;
        view.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoObtain);
        ArrayList arrayList = new ArrayList();
        mo211129b4(arrayList);
        if (accessibilityNodeInfoObtain.getChildCount() > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            c0748k7.f57472a0.addChild(view, ((Integer) arrayList.get(i2)).intValue());
        }
        return c0748k7;
    }

    /* renamed from: b8 */
    public abstract boolean mo211130b8(int i, int i2, Bundle bundle);

    /* renamed from: c0 */
    public abstract void mo211131c0(int i, C0748k7 c0748k7);

    /* renamed from: c2 */
    public final boolean m215215c2(int i) {
        int i2;
        View view = this.f61188a8;
        if ((!view.isFocused() && !view.requestFocus()) || (i2 = this.f61191b1) == i) {
            return false;
        }
        if (i2 != Integer.MIN_VALUE) {
            m215208a9(i2);
        }
        if (i == Integer.MIN_VALUE) {
            return false;
        }
        this.f61191b1 = i;
        mo213056c1(i, true);
        m215216c3(i, 8);
        return true;
    }

    /* renamed from: c3 */
    public final void m215216c3(int i, int i2) {
        View view;
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.f61187a7.isEnabled() || (parent = (view = this.f61188a8).getParent()) == null) {
            return;
        }
        parent.requestSendAccessibilityEvent(view, m215209b0(i, i2));
    }

    /* renamed from: b9 */
    public void mo213055b9(C0748k7 c0748k7) {
    }

    /* renamed from: c1 */
    public void mo213056c1(int i, boolean z) {
    }
}
