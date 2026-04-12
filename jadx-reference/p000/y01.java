package p000;

import android.content.Context;
import com.storm.safe.rock.manager.C0262a4;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class y01 {
    public /* synthetic */ y01(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0262a4 getInstance(Context context) {
        C0262a4 c0262a4;
        t60.m214695b6(context, "context");
        C0262a4 c0262a42 = C0262a4.f52128b6;
        if (c0262a42 != null) {
            return c0262a42;
        }
        synchronized (this) {
            c0262a4 = C0262a4.f52128b6;
            if (c0262a4 == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                c0262a4 = new C0262a4(applicationContext);
                C0262a4.f52128b6 = c0262a4;
            }
        }
        return c0262a4;
    }

    private y01() {
    }
}
