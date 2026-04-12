package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ar */
/* loaded from: classes2.dex */
public final class C0106ar {
    public /* synthetic */ C0106ar(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0107as getInstance(Context context) {
        C0107as c0107as;
        t60.m214695b6(context, "context");
        C0107as c0107as2 = C0107as.f45617b0;
        if (c0107as2 != null) {
            return c0107as2;
        }
        synchronized (this) {
            c0107as = C0107as.f45617b0;
            if (c0107as == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                c0107as = new C0107as(applicationContext);
                C0107as.f45617b0 = c0107as;
            }
        }
        return c0107as;
    }

    private C0106ar() {
    }
}
