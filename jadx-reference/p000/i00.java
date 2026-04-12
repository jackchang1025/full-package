package p000;

import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0071a7;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class i00 implements h00 {

    /* renamed from: a0 */
    public final int f56778a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0071a7 f56779a1;

    public i00(C0071a7 c0071a7, int i) {
        this.f56779a1 = c0071a7;
        this.f56778a0 = i;
    }

    @Override // p000.h00
    /* renamed from: a0 */
    public final boolean mo212519a0(ArrayList arrayList, ArrayList arrayList2) {
        C0071a7 c0071a7 = this.f56779a1;
        AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = c0071a7.f45138b6;
        int i = this.f56778a0;
        if (abstractComponentCallbacksC0069a5 == null || i >= 0 || !abstractComponentCallbacksC0069a5.m210134a7().m210191d3()) {
            return c0071a7.m210192d4(arrayList, arrayList2, i, 1);
        }
        return false;
    }
}
