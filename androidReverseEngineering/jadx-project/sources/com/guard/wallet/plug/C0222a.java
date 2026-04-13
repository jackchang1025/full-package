package com.guard.wallet.plug;

import a1.AbstractC0026q;
import android.graphics.Rect;
import com.google.json.Gson;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

/* renamed from: com.guard.wallet.plug.a */
/* loaded from: classes.dex */
public final class C0222a implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ ReqUnlockDeviceVO f255a;

    public C0222a(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        this.f255a = reqUnlockDeviceVO;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ListenPropResponse listenPropResponse = (ListenPropResponse) obj;
        boolean equals = Objects.equals(listenPropResponse.getProp(), "boundsInScreen");
        ReqUnlockDeviceVO reqUnlockDeviceVO = this.f255a;
        try {
            if (equals) {
                if (!AbstractC0026q.m151B(listenPropResponse.getValue())) {
                    Rect rect = (Rect) new Gson().fromJson(listenPropResponse.getValue(), new TypeToken<Rect>() { // from class: com.guard.wallet.plug.CrackLockCipherPlug$CrackRunnable$1$1
                    }.getType());
                    if (rect != null) {
                        reqUnlockDeviceVO.setBoundsInScreen(rect);
                    }
                }
            } else {
                if (!Objects.equals(listenPropResponse.getProp(), "boundsInParent")) {
                    return false;
                }
                if (!AbstractC0026q.m151B(listenPropResponse.getValue())) {
                    Rect rect2 = (Rect) new Gson().fromJson(listenPropResponse.getValue(), new TypeToken<Rect>() { // from class: com.guard.wallet.plug.CrackLockCipherPlug$CrackRunnable$1$2
                    }.getType());
                    if (rect2 != null) {
                        reqUnlockDeviceVO.setBoundsInParent(rect2);
                    }
                }
            }
        } catch (Exception e2) {
            ConcurrentLinkedQueue concurrentLinkedQueue = C0224c.f261a;
            AbstractC0026q.m186s("com.guard.wallet.plug.c", e2);
        }
        return true;
    }
}
