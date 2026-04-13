package com.guard.wallet.helper;

import a1.AbstractC0026q;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.thread.AbstractC0243l;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;

/* renamed from: com.guard.wallet.helper.b */
/* loaded from: classes.dex */
public final class C0179b implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ int f200a;

    public /* synthetic */ C0179b(int i2) {
        this.f200a = i2;
    }

    /* renamed from: a */
    public final boolean m344a(Future future) {
        switch (this.f200a) {
            case 1:
                try {
                    future.cancel(true);
                    break;
                } catch (Exception e2) {
                    ThreadPoolExecutor threadPoolExecutor = AbstractC0243l.f391a;
                    AbstractC0026q.m186s("com.guard.wallet.thread.l", e2);
                    break;
                }
            default:
                if (future.isDone() || future.isCancelled()) {
                }
                break;
        }
        return true;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.f200a) {
            case 0:
                UiObject uiObject = (UiObject) obj;
                if (uiObject == null) {
                    return true;
                }
                uiObject.recycle();
                return true;
            case 1:
                return m344a((Future) obj);
            default:
                return m344a((Future) obj);
        }
    }
}
