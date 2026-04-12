package p000;

import android.os.Build;
import androidx.work.NetworkType;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dk */
/* loaded from: classes2.dex */
public final class C0420dk extends AbstractC0799kx {

    /* renamed from: a5 */
    public final /* synthetic */ int f55829a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0420dk(AbstractC0826ln abstractC0826ln, int i) {
        super(abstractC0826ln);
        this.f55829a5 = i;
    }

    @Override // p000.AbstractC0799kx
    /* renamed from: a0 */
    public final boolean mo212609a0(wg1 wg1Var) {
        switch (this.f55829a5) {
            case 0:
                t60.m214695b6(wg1Var, "workSpec");
                return wg1Var.f60921a9.f58194a1;
            case 1:
                t60.m214695b6(wg1Var, "workSpec");
                return wg1Var.f60921a9.f58196a3;
            case 2:
                t60.m214695b6(wg1Var, "workSpec");
                return wg1Var.f60921a9.f58193a0 == NetworkType.f45517a1;
            case 3:
                t60.m214695b6(wg1Var, "workSpec");
                NetworkType networkType = wg1Var.f60921a9.f58193a0;
                return networkType == NetworkType.f45518a2 || (Build.VERSION.SDK_INT >= 30 && networkType == NetworkType.f45521a5);
            default:
                t60.m214695b6(wg1Var, "workSpec");
                return wg1Var.f60921a9.f58197a4;
        }
    }

    @Override // p000.AbstractC0799kx
    /* renamed from: a1 */
    public final boolean mo212610a1(Object obj) {
        boolean zBooleanValue;
        switch (this.f55829a5) {
            case 0:
                zBooleanValue = ((Boolean) obj).booleanValue();
                break;
            case 1:
                zBooleanValue = ((Boolean) obj).booleanValue();
                break;
            case 2:
                rj0 rj0Var = (rj0) obj;
                t60.m214695b6(rj0Var, "value");
                boolean z = rj0Var.f59779a0;
                return Build.VERSION.SDK_INT < 26 ? !z : !(z && rj0Var.f59780a1);
            case 3:
                rj0 rj0Var2 = (rj0) obj;
                t60.m214695b6(rj0Var2, "value");
                return !rj0Var2.f59779a0 || rj0Var2.f59781a2;
            default:
                zBooleanValue = ((Boolean) obj).booleanValue();
                break;
        }
        return !zBooleanValue;
    }
}
