package com.storm.safe.rock.service.modules.setup;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Iterator;
import java.util.List;
import kotlin.text.AbstractC0779a1;
import okio.Segment;
import okio.internal.Buffer;
import p000.AbstractC0003a2;
import p000.AbstractC0716jf;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.setup.a4 */
/* loaded from: classes2.dex */
public final class C0362a4 {

    /* renamed from: a0 */
    public static final List f53875a0;

    static {
        AbstractC0716jf.m213306g5("androidx.recyclerview.widget.RecyclerView", "android.widget.ListView", "android.widget.ScrollView", "android.support.v7.widget.RecyclerView", "android.widget.GridView");
        f53875a0 = AbstractC0716jf.m213306g5("android.widget.Switch", "android.widget.ToggleButton", "android.widget.CheckBox", "androidx.appcompat.widget.SwitchCompat");
    }

    /* renamed from: a0 */
    public static int m212105a0(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo child;
        int childCount = accessibilityNodeInfo.getChildCount();
        int i = 1;
        for (int i2 = 0; i2 < childCount; i2++) {
            try {
                child = accessibilityNodeInfo.getChild(i2);
            } catch (Exception unused) {
                child = null;
            }
            if (child != null) {
                int iM212105a0 = m212105a0(child) + i;
                try {
                    child.recycle();
                } catch (Exception unused2) {
                }
                i = iM212105a0;
            }
        }
        return i;
    }

    /* renamed from: a1 */
    public static AccessibilityNodeInfo m212106a1(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212106a1;
        if (accessibilityNodeInfo.isScrollable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212106a1 = m212106a1(child)) != null) {
                return accessibilityNodeInfoM212106a1;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0037 A[EDGE_INSN: B:50:0x0037->B:20:0x0037 BREAK  A[LOOP:1: B:13:0x001d->B:52:?]] */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static AccessibilityNodeInfo m212107a2(AccessibilityNodeInfo accessibilityNodeInfo, int i, int i2, int i3) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212107a2;
        int i4;
        int i5;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        List list = f53875a0;
        if (list == null || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                    break;
                }
            }
            if (accessibilityNodeInfo.isCheckable()) {
                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                if (rectM24a5.left >= 0 && rectM24a5.right <= i3 && (i4 = rectM24a5.top) >= 0 && (i5 = (i4 + rectM24a5.bottom) / 2) >= i - 50 && i5 <= i2 + 50) {
                    StringBuilder sbM40c1 = AbstractC0003a2.m40c1("[同行开关] 找到: class=", string, ", Y=", i5, " (目标: ");
                    sbM40c1.append(i);
                    sbM40c1.append("-");
                    sbM40c1.append(i2);
                    sbM40c1.append(")");
                    t60.m214702c3("UiNodeHelper", sbM40c1.toString());
                    return accessibilityNodeInfo;
                }
            }
        } else if (accessibilityNodeInfo.isCheckable()) {
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        int i6 = 0;
        while (true) {
            AccessibilityNodeInfo child = null;
            if (i6 >= childCount) {
                return null;
            }
            try {
                child = accessibilityNodeInfo.getChild(i6);
            } catch (Exception unused) {
            }
            if (child != null && (accessibilityNodeInfoM212107a2 = m212107a2(child, i, i2, i3)) != null) {
                return accessibilityNodeInfoM212107a2;
            }
            i6++;
        }
    }

    /* renamed from: a3 */
    public static boolean m212108a3(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212106a1 = m212106a1(accessibilityNodeInfo);
        if (accessibilityNodeInfoM212106a1 == null) {
            t60.m214726f4("UiNodeHelper", "[滚动] 未找到可滚动节点");
            return false;
        }
        accessibilityNodeInfoM212106a1.refresh();
        List<AccessibilityNodeInfo.AccessibilityAction> actionList = accessibilityNodeInfoM212106a1.getActionList();
        if (actionList != null && !actionList.isEmpty()) {
            Iterator<T> it = actionList.iterator();
            while (it.hasNext()) {
                if (((AccessibilityNodeInfo.AccessibilityAction) it.next()).getId() == 4096) {
                    if (!accessibilityNodeInfoM212106a1.performAction(Buffer.SEGMENTING_THRESHOLD)) {
                        return false;
                    }
                    t60.m214702c3("UiNodeHelper", "[滚动] 系统滚动成功");
                    return true;
                }
            }
        }
        t60.m214702c3("UiNodeHelper", "[滚动] 已到底部，无法继续向下滚动");
        return false;
    }

    /* renamed from: a4 */
    public static boolean m212109a4(AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityService accessibilityService, Context context) {
        boolean zM212108a3 = m212108a3(accessibilityNodeInfo);
        if (zM212108a3) {
            m212113a8(accessibilityService, 1000L);
        }
        return zM212108a3;
    }

    /* renamed from: a5 */
    public static boolean m212110a5(AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityService accessibilityService, Context context) {
        AccessibilityNodeInfo accessibilityNodeInfoM212106a1 = m212106a1(accessibilityNodeInfo);
        if (accessibilityNodeInfoM212106a1 != null) {
            accessibilityNodeInfoM212106a1.refresh();
            List<AccessibilityNodeInfo.AccessibilityAction> actionList = accessibilityNodeInfoM212106a1.getActionList();
            if (actionList != null && !actionList.isEmpty()) {
                Iterator<T> it = actionList.iterator();
                while (it.hasNext()) {
                    if (((AccessibilityNodeInfo.AccessibilityAction) it.next()).getId() == 8192) {
                        if (accessibilityNodeInfoM212106a1.performAction(Segment.SIZE)) {
                            t60.m214702c3("UiNodeHelper", "[滚动] 系统向上滚动成功");
                            return true;
                        }
                    }
                }
            }
            t60.m214702c3("UiNodeHelper", "[滚动] 已到顶部，无法继续向上滚动");
            return false;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float f = displayMetrics.widthPixels / 2.0f;
        float f2 = displayMetrics.heightPixels;
        float f3 = 0.3f * f2;
        float f4 = f2 * 0.7f;
        try {
            Path path = new Path();
            path.moveTo(f, f3);
            path.lineTo(f, f4);
            accessibilityService.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 300L)).build(), null, null);
            return true;
        } catch (Exception e) {
            t60.m214705c6("UiNodeHelper", "手势滑动异常", e);
            return false;
        }
    }

    /* renamed from: a6 */
    public static boolean m212111a6(AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityService accessibilityService, Context context) {
        boolean zM212110a5 = m212110a5(accessibilityNodeInfo, accessibilityService, context);
        if (zM212110a5) {
            m212113a8(accessibilityService, 1000L);
        }
        return zM212110a5;
    }

    /* renamed from: a7 */
    public static void m212112a7() {
        try {
            Thread.sleep(300L);
        } catch (Exception unused) {
        }
    }

    /* renamed from: a8 */
    public static boolean m212113a8(AccessibilityService accessibilityService, long j) {
        t60.m214695b6(accessibilityService, "service");
        UiNodeHelper$waitForPageStable$1 uiNodeHelper$waitForPageStable$1 = new UiNodeHelper$waitForPageStable$1(accessibilityService);
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = -1;
        int i2 = 0;
        int i3 = 0;
        while (System.currentTimeMillis() - jCurrentTimeMillis < j) {
            i2++;
            AccessibilityNodeInfo rootInActiveWindow = uiNodeHelper$waitForPageStable$1.f53790a0.getRootInActiveWindow();
            int iM212105a0 = rootInActiveWindow != null ? m212105a0(rootInActiveWindow) : 0;
            if (rootInActiveWindow != null) {
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused) {
                }
            }
            if (iM212105a0 != i || iM212105a0 <= 0) {
                i3 = 0;
                i = iM212105a0;
            } else {
                i3++;
                if (i3 >= 3) {
                    t60.m214702c3("UiNodeHelper", "[PageStable] stable (" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms, " + iM212105a0 + " nodes)");
                    return true;
                }
            }
            try {
                Thread.sleep(50L);
            } catch (Exception unused2) {
            }
        }
        t60.m214726f4("UiNodeHelper", "[PageStable] timeout (" + j + "ms, " + i2 + " checks, " + i + " nodes)");
        return false;
    }
}
