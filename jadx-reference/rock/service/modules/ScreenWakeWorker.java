package com.storm.safe.rock.service.modules;

import android.content.Context;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import p000.C1106qd;
import p000.mj1;
import p000.nj1;
import p000.qb0;
import p000.rb0;
import p000.sb0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ScreenWakeWorker extends Worker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenWakeWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
        t60.m214695b6(context, "context");
        t60.m214695b6(workerParameters, "params");
    }

    @Override // androidx.work.Worker
    /* renamed from: a6 */
    public final sb0 mo210458a6() {
        mj1 mj1Var = nj1.f58634a4;
        Context context = this.f60190a0;
        t60.m214694b5(context, "applicationContext");
        return mj1.wakeScreen$default(mj1Var, context, false, 2, null) ? new rb0(C1106qd.f59467a1) : new qb0();
    }
}
