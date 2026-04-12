package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ec */
/* loaded from: classes2.dex */
public final class C0451ec {
    public /* synthetic */ C0451ec(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0454ef getInstance(Context context) {
        t60.m214695b6(context, "context");
        C0454ef c0454ef = C0454ef.f55977c4;
        if (c0454ef != null && c0454ef.f55978a0 == context) {
            return c0454ef;
        }
        synchronized (this) {
            C0454ef c0454ef2 = C0454ef.f55977c4;
            if (c0454ef2 != null && c0454ef2.f55978a0 == context) {
                return c0454ef2;
            }
            if (c0454ef2 != null) {
                c0454ef2.f55985a7 = false;
                c0454ef2.f55996b8.removeCallbacksAndMessages(null);
                RunnableC0165ca runnableC0165ca = c0454ef2.f55995b7;
                if (runnableC0165ca != null) {
                    c0454ef2.f55996b8.removeCallbacks(runnableC0165ca);
                }
                c0454ef2.f55995b7 = null;
                c0454ef2.f55994b6 = 0;
                c0454ef2.f55992b4 = null;
                c0454ef2.f55993b5 = null;
                c0454ef2.m212667a0();
                c0454ef2.f55983a5 = false;
                c0454ef2.f55984a6 = false;
            }
            C0454ef c0454ef3 = new C0454ef(context);
            C0454ef.f55977c4 = c0454ef3;
            return c0454ef3;
        }
    }

    public final void releaseInstance() {
        C0454ef c0454ef = C0454ef.f55977c4;
        if (c0454ef != null) {
            c0454ef.f55985a7 = false;
            c0454ef.f55996b8.removeCallbacksAndMessages(null);
            RunnableC0165ca runnableC0165ca = c0454ef.f55995b7;
            if (runnableC0165ca != null) {
                c0454ef.f55996b8.removeCallbacks(runnableC0165ca);
            }
            c0454ef.f55995b7 = null;
            c0454ef.f55994b6 = 0;
            c0454ef.f55992b4 = null;
            c0454ef.f55993b5 = null;
            c0454ef.m212667a0();
            c0454ef.f55983a5 = false;
            c0454ef.f55984a6 = false;
        }
        C0454ef.f55977c4 = null;
    }

    private C0451ec() {
    }
}
