package p000;

import android.util.SparseArray;
import android.view.View;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class qc1 extends tc1 {

    /* renamed from: a5 */
    public SparseArray f59465a5;

    /* renamed from: a6 */
    public float[] f59466a6;

    @Override // p000.tc1
    /* renamed from: a1 */
    public final void mo214378a1(float f, int i) {
        throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
    }

    @Override // p000.tc1
    /* renamed from: a2 */
    public final void mo214245a2(View view, float f) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.f60200a0.mo210518c2(f, this.f59466a6);
        kg1.m213540e9((C0798kw) this.f59465a5.valueAt(0), view, this.f59466a6);
    }

    @Override // p000.tc1
    /* renamed from: a3 */
    public final void mo214379a3(int i) {
        SparseArray sparseArray = this.f59465a5;
        int size = sparseArray.size();
        int iM213762a2 = ((C0798kw) sparseArray.valueAt(0)).m213762a2();
        double[] dArr = new double[size];
        this.f59466a6 = new float[iM213762a2];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, iM213762a2);
        for (int i2 = 0; i2 < size; i2++) {
            int iKeyAt = sparseArray.keyAt(i2);
            C0798kw c0798kw = (C0798kw) sparseArray.valueAt(i2);
            dArr[i2] = iKeyAt * 0.01d;
            c0798kw.m213761a1(this.f59466a6);
            int i3 = 0;
            while (true) {
                if (i3 < this.f59466a6.length) {
                    dArr2[i2][i3] = r7[i3];
                    i3++;
                }
            }
        }
        this.f60200a0 = b81.m210573b3(i, dArr, dArr2);
    }
}
