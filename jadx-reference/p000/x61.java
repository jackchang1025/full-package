package p000;

import androidx.appcompat.widget.Toolbar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final /* synthetic */ class x61 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f61020a0;

    /* renamed from: a1 */
    public final /* synthetic */ Toolbar f61021a1;

    public /* synthetic */ x61(Toolbar toolbar, int i) {
        this.f61020a0 = i;
        this.f61021a1 = toolbar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f61020a0) {
            case 0:
                a71 a71Var = this.f61021a1.f44126d7;
                ff0 ff0Var = a71Var == null ? null : a71Var.f44a1;
                if (ff0Var != null) {
                    ff0Var.collapseActionView();
                    break;
                }
                break;
            default:
                this.f61021a1.m209929b3();
                break;
        }
    }
}
