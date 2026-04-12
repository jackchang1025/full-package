package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class g31 {

    /* renamed from: a0 */
    public final Context f56372a0;

    /* renamed from: a1 */
    public String f56373a1;

    /* renamed from: a2 */
    public f31 f56374a2;

    /* renamed from: a3 */
    public boolean f56375a3;

    /* renamed from: a4 */
    public boolean f56376a4;

    public g31(Context context) {
        this.f56372a0 = context;
    }

    /* renamed from: a0 */
    public final i31 m212883a0() {
        String str;
        f31 f31Var = this.f56374a2;
        if (f31Var == null) {
            throw new IllegalArgumentException("Must set a callback to create the configuration.");
        }
        if (this.f56375a3 && ((str = this.f56373a1) == null || str.length() == 0)) {
            throw new IllegalArgumentException("Must set a non-null database name to a configuration that uses the no backup directory.");
        }
        return new i31(this.f56372a0, this.f56373a1, f31Var, this.f56375a3, this.f56376a4);
    }
}
