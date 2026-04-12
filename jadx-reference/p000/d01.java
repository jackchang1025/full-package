package p000;

import android.graphics.Canvas;
import android.graphics.Matrix;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class d01 extends j01 {

    /* renamed from: a2 */
    public final /* synthetic */ ArrayList f55552a2;

    /* renamed from: a3 */
    public final /* synthetic */ Matrix f55553a3;

    public d01(ArrayList arrayList, Matrix matrix) {
        this.f55552a2 = arrayList;
        this.f55553a3 = matrix;
    }

    @Override // p000.j01
    /* renamed from: a0 */
    public final void mo212547a0(Matrix matrix, yz0 yz0Var, int i, Canvas canvas) {
        ArrayList arrayList = this.f55552a2;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((j01) obj).mo212547a0(this.f55553a3, yz0Var, i, canvas);
        }
    }
}
