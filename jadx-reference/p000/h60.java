package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class h60 {
    public /* synthetic */ h60(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final i60 getInstance(Context context) {
        i60 i60Var;
        t60.m214695b6(context, "context");
        i60 i60Var2 = i60.f56804a3;
        if (i60Var2 != null) {
            return i60Var2;
        }
        synchronized (this) {
            i60Var = i60.f56804a3;
            if (i60Var == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                i60Var = new i60(applicationContext);
                i60.f56804a3 = i60Var;
            }
        }
        return i60Var;
    }

    private h60() {
    }
}
