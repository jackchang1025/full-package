package com.storm.safe.rock.service.account;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import p000.AbstractC1120qr;
import p000.qk1;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ndaochvetz extends Service {

    /* renamed from: a0 */
    public static qk1 f52356a0;

    /* renamed from: a1 */
    public static final Object f52357a1;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.account.ndaochvetz$a0 */
    public static final class C0289a0 {
        public /* synthetic */ C0289a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0289a0() {
        }
    }

    static {
        new C0289a0(null);
        f52357a1 = new Object();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        qk1 qk1Var = f52356a0;
        if (qk1Var != null) {
            return qk1Var.getSyncAdapterBinder();
        }
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        synchronized (f52357a1) {
            if (f52356a0 == null) {
                Context applicationContext = getApplicationContext();
                t60.m214694b5(applicationContext, "applicationContext");
                f52356a0 = new qk1(applicationContext, true);
            }
        }
        t60.m214714d6("ndaochvetz", "ndaochvetz 已创建");
    }
}
