package p000;

import android.content.Context;
import com.storm.safe.rock.service.modules.overlay.C0353a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: s3 */
/* loaded from: classes2.dex */
public final class C1205s3 {
    public /* synthetic */ C1205s3(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0353a0 getInstance(Context context) {
        C0353a0 c0353a0;
        t60.m214695b6(context, "context");
        C0353a0 c0353a02 = C0353a0.f53610b1;
        if (c0353a02 != null) {
            return c0353a02;
        }
        synchronized (this) {
            c0353a0 = C0353a0.f53610b1;
            if (c0353a0 == null) {
                c0353a0 = new C0353a0(context);
                C0353a0.f53610b1 = c0353a0;
            }
        }
        return c0353a0;
    }

    public final void releaseInstance() {
        C0353a0 c0353a0 = C0353a0.f53610b1;
        if (c0353a0 != null) {
            c0353a0.m211897a2(true);
        }
        C0353a0.f53610b1 = null;
    }

    private C1205s3() {
    }
}
