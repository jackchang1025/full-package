package p000;

import android.content.Context;
import com.storm.safe.rock.network.C0268a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class f40 {
    public /* synthetic */ f40(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0268a1 getInstance(Context context) {
        C0268a1 c0268a1;
        t60.m214695b6(context, "context");
        C0268a1 c0268a12 = C0268a1.f52276a7;
        if (c0268a12 != null) {
            return c0268a12;
        }
        synchronized (this) {
            c0268a1 = C0268a1.f52276a7;
            if (c0268a1 == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                c0268a1 = new C0268a1(applicationContext);
                C0268a1.f52276a7 = c0268a1;
            }
        }
        return c0268a1;
    }

    private f40() {
    }
}
