package p012o;

import android.os.Build;
import android.util.Log;
import com.guard.wallet.thread.CallableC0242k;
import com.guard.wallet.utils.AbstractC0249e;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import p017u.C0918a;

/* renamed from: o.r */
/* loaded from: classes.dex */
public final class C0429r {

    /* renamed from: a */
    public final ExecutorService f954a = Executors.newSingleThreadExecutor();

    /* renamed from: b */
    public final CallableC0242k f955b = new CallableC0242k(true);

    /* renamed from: c */
    public final AtomicLong f956c = new AtomicLong(0);

    /* JADX WARN: Removed duplicated region for block: B:21:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1134a() {
        boolean z2;
        String str;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 < 30) {
            str = "MiniCap use Media Projection";
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            if (!AbstractC0249e.m621j()) {
                AtomicLong atomicLong = this.f956c;
                if (currentTimeMillis - atomicLong.get() < 30000) {
                    str = "黑屏中,等待30秒...";
                } else {
                    atomicLong.set(currentTimeMillis);
                }
            }
            CallableC0242k callableC0242k = this.f955b;
            if (i2 >= 30) {
                C0918a c0918a = (C0918a) callableC0242k.f390b;
                if (c0918a != null && !c0918a.m1384b()) {
                    z2 = true;
                    if (z2) {
                        this.f954a.submit(callableC0242k);
                        return;
                    }
                    return;
                }
            } else {
                callableC0242k.getClass();
            }
            z2 = false;
            if (z2) {
            }
        }
        Log.d("o.r", str);
    }
}
