package p000;

import android.content.Context;
import com.storm.safe.rock.service.modules.C0323a8;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class lj0 {
    public /* synthetic */ lj0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0323a8 getInstance() {
        return C0323a8.f53099e2;
    }

    public final C0323a8 getOrCreate(Context context) {
        C0323a8 c0323a8;
        t60.m214695b6(context, "context");
        C0323a8 c0323a82 = C0323a8.f53099e2;
        if (c0323a82 != null) {
            return c0323a82;
        }
        synchronized (C0323a8.f53098e1) {
            c0323a8 = C0323a8.f53099e2;
            if (c0323a8 == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                c0323a8 = new C0323a8(applicationContext);
                c0323a8.m211647b3();
            }
        }
        return c0323a8;
    }

    private lj0() {
    }
}
