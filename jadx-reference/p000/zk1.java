package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zk1 {
    public /* synthetic */ zk1(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final al1 getInstance(Context context) {
        al1 al1Var;
        t60.m214695b6(context, "context");
        al1 al1Var2 = al1.f43715a6;
        if (al1Var2 != null) {
            return al1Var2;
        }
        synchronized (this) {
            al1Var = al1.f43715a6;
            if (al1Var == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                al1Var = new al1(applicationContext);
                al1.f43715a6 = al1Var;
            }
        }
        return al1Var;
    }

    private zk1() {
    }
}
