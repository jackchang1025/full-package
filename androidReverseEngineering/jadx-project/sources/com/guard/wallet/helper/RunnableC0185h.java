package com.guard.wallet.helper;

import com.guard.wallet.entity.Point;
import com.guard.wallet.utils.AbstractC0251g;

/* renamed from: com.guard.wallet.helper.h */
/* loaded from: classes.dex */
public final class RunnableC0185h implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f209a;

    /* renamed from: b */
    public final /* synthetic */ long f210b;

    /* renamed from: c */
    public final /* synthetic */ Point[] f211c;

    public /* synthetic */ RunnableC0185h(long j2, Point[] pointArr, int i2) {
        this.f209a = i2;
        this.f210b = j2;
        this.f211c = pointArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f209a;
        Point[] pointArr = this.f211c;
        long j2 = this.f210b;
        switch (i2) {
            case 0:
                AbstractC0251g.m646S(10L, Long.valueOf(j2), pointArr);
                break;
            default:
                AbstractC0251g.m646S(10L, Long.valueOf(j2), pointArr);
                break;
        }
    }
}
