package p000;

import android.content.Context;
import com.storm.safe.rock.service.account.C0287a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: m0 */
/* loaded from: classes2.dex */
public final class C0844m0 {
    public /* synthetic */ C0844m0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0287a0 getInstance(Context context) {
        C0287a0 c0287a0;
        t60.m214695b6(context, "context");
        C0287a0 c0287a02 = C0287a0.f52352a3;
        if (c0287a02 != null) {
            return c0287a02;
        }
        synchronized (this) {
            c0287a0 = C0287a0.f52352a3;
            if (c0287a0 == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                c0287a0 = new C0287a0(applicationContext);
                C0287a0.f52352a3 = c0287a0;
            }
        }
        return c0287a0;
    }

    private C0844m0() {
    }
}
