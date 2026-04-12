package p000;

import android.util.SparseArray;
import android.view.View;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class wc1 extends zc1 {

    /* renamed from: a6 */
    public String f60891a6;

    /* renamed from: a7 */
    public SparseArray f60892a7;

    /* renamed from: a8 */
    public SparseArray f60893a8;

    /* renamed from: a9 */
    public float[] f60894a9;

    /* renamed from: b0 */
    public float[] f60895b0;

    @Override // p000.zc1
    /* renamed from: a1 */
    public final boolean mo214921a1(float f, long j, C1105qc c1105qc, View view) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.f61501a0.mo210518c2(f, this.f60894a9);
        float[] fArr = this.f60894a9;
        float f2 = fArr[fArr.length - 2];
        float f3 = fArr[fArr.length - 1];
        long j2 = j - this.f61505a4;
        if (Float.isNaN(this.f61506a5)) {
            float fM214372a0 = c1105qc.m214372a0(view, this.f60891a6);
            this.f61506a5 = fM214372a0;
            if (Float.isNaN(fM214372a0)) {
                this.f61506a5 = 0.0f;
            }
        }
        this.f61506a5 = (float) ((((j2 * 1.0E-9d) * f2) + this.f61506a5) % 1.0d);
        this.f61505a4 = j;
        float fSin = (float) Math.sin(r14 * 6.2831855f);
        this.f61504a3 = false;
        int i = 0;
        while (true) {
            float[] fArr2 = this.f60895b0;
            if (i >= fArr2.length) {
                break;
            }
            boolean z = this.f61504a3;
            float f4 = this.f60894a9[i];
            this.f61504a3 = z | (((double) f4) != 0.0d);
            fArr2[i] = (f4 * fSin) + f3;
            i++;
        }
        kg1.m213540e9((C0798kw) this.f60892a7.valueAt(0), view, this.f60895b0);
        if (f2 != 0.0f) {
            this.f61504a3 = true;
        }
        return this.f61504a3;
    }

    @Override // p000.zc1
    /* renamed from: a2 */
    public final void mo215046a2(int i) {
        SparseArray sparseArray = this.f60892a7;
        int size = sparseArray.size();
        int iM213762a2 = ((C0798kw) sparseArray.valueAt(0)).m213762a2();
        double[] dArr = new double[size];
        int i2 = iM213762a2 + 2;
        this.f60894a9 = new float[i2];
        this.f60895b0 = new float[iM213762a2];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i2);
        for (int i3 = 0; i3 < size; i3++) {
            int iKeyAt = sparseArray.keyAt(i3);
            C0798kw c0798kw = (C0798kw) sparseArray.valueAt(i3);
            float[] fArr = (float[]) this.f60893a8.valueAt(i3);
            dArr[i3] = iKeyAt * 0.01d;
            c0798kw.m213761a1(this.f60894a9);
            int i4 = 0;
            while (true) {
                if (i4 < this.f60894a9.length) {
                    dArr2[i3][i4] = r10[i4];
                    i4++;
                }
            }
            double[] dArr3 = dArr2[i3];
            dArr3[iM213762a2] = fArr[0];
            dArr3[iM213762a2 + 1] = fArr[1];
        }
        this.f61501a0 = b81.m210573b3(i, dArr, dArr2);
    }
}
