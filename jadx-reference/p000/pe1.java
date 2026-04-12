package p000;

import android.content.Context;
import com.storm.safe.rock.service.modules.overlay.C0354a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class pe1 {
    public /* synthetic */ pe1(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0354a1 getInstance(Context context) {
        C0354a1 c0354a1;
        t60.m214695b6(context, "context");
        C0354a1 c0354a12 = C0354a1.f53622b1;
        if (c0354a12 != null) {
            return c0354a12;
        }
        synchronized (this) {
            c0354a1 = C0354a1.f53622b1;
            if (c0354a1 == null) {
                c0354a1 = new C0354a1(context);
                C0354a1.f53622b1 = c0354a1;
            }
        }
        return c0354a1;
    }

    public final void releaseInstance() {
        C0354a1 c0354a1 = C0354a1.f53622b1;
        if (c0354a1 != null) {
            c0354a1.m211903a3(true);
        }
        C0354a1.f53622b1 = null;
    }

    private pe1() {
    }
}
