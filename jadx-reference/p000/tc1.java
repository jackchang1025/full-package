package p000;

import android.view.View;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class tc1 {

    /* renamed from: a0 */
    public b81 f60200a0;

    /* renamed from: a1 */
    public int[] f60201a1 = new int[10];

    /* renamed from: a2 */
    public float[] f60202a2 = new float[10];

    /* renamed from: a3 */
    public int f60203a3;

    /* renamed from: a4 */
    public String f60204a4;

    /* renamed from: a0 */
    public final float m214736a0(float f) {
        return (float) this.f60200a0.mo210516c0(f);
    }

    /* renamed from: a1 */
    public void mo214378a1(float f, int i) {
        int[] iArr = this.f60201a1;
        if (iArr.length < this.f60203a3 + 1) {
            this.f60201a1 = Arrays.copyOf(iArr, iArr.length * 2);
            float[] fArr = this.f60202a2;
            this.f60202a2 = Arrays.copyOf(fArr, fArr.length * 2);
        }
        int[] iArr2 = this.f60201a1;
        int i2 = this.f60203a3;
        iArr2[i2] = i;
        this.f60202a2[i2] = f;
        this.f60203a3 = i2 + 1;
    }

    /* renamed from: a2 */
    public abstract void mo214245a2(View view, float f);

    /* JADX WARN: Removed duplicated region for block: B:31:0x009d  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void mo214379a3(int i) {
        int i2 = this.f60203a3;
        if (i2 == 0) {
            return;
        }
        int[] iArr = this.f60201a1;
        float[] fArr = this.f60202a2;
        int[] iArr2 = new int[iArr.length + 10];
        iArr2[0] = i2 - 1;
        iArr2[1] = 0;
        int i3 = 2;
        while (i3 > 0) {
            int i4 = i3 - 1;
            int i5 = iArr2[i4];
            int i6 = i3 - 2;
            int i7 = iArr2[i6];
            if (i5 < i7) {
                int i8 = iArr[i7];
                int i9 = i5;
                int i10 = i9;
                while (i9 < i7) {
                    int i11 = iArr[i9];
                    if (i11 <= i8) {
                        int i12 = iArr[i10];
                        iArr[i10] = i11;
                        iArr[i9] = i12;
                        float f = fArr[i10];
                        fArr[i10] = fArr[i9];
                        fArr[i9] = f;
                        i10++;
                    }
                    i9++;
                }
                int i13 = iArr[i10];
                iArr[i10] = iArr[i7];
                iArr[i7] = i13;
                float f2 = fArr[i10];
                fArr[i10] = fArr[i7];
                fArr[i7] = f2;
                iArr2[i6] = i10 - 1;
                iArr2[i4] = i5;
                int i14 = i3 + 1;
                iArr2[i3] = i7;
                i3 += 2;
                iArr2[i14] = i10 + 1;
            } else {
                i3 = i6;
            }
        }
        int i15 = 1;
        for (int i16 = 1; i16 < this.f60203a3; i16++) {
            int[] iArr3 = this.f60201a1;
            if (iArr3[i16 - 1] != iArr3[i16]) {
                i15++;
            }
        }
        double[] dArr = new double[i15];
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, i15, 1);
        int i17 = 0;
        for (int i18 = 0; i18 < this.f60203a3; i18++) {
            if (i18 > 0) {
                int[] iArr4 = this.f60201a1;
                if (iArr4[i18] != iArr4[i18 - 1]) {
                    dArr[i17] = this.f60201a1[i18] * 0.01d;
                    dArr2[i17][0] = this.f60202a2[i18];
                    i17++;
                }
            }
        }
        this.f60200a0 = b81.m210573b3(i, dArr, dArr2);
    }

    public final String toString() {
        String string = this.f60204a4;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i = 0; i < this.f60203a3; i++) {
            StringBuilder sbM39c0 = AbstractC0003a2.m39c0(string, "[");
            sbM39c0.append(this.f60201a1[i]);
            sbM39c0.append(" , ");
            sbM39c0.append(decimalFormat.format(this.f60202a2[i]));
            sbM39c0.append("] ");
            string = sbM39c0.toString();
        }
        return string;
    }
}
