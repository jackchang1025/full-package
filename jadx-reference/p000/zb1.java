package p000;

import android.view.View;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class zb1 {

    /* renamed from: a0 */
    public o80 f61493a0;

    /* renamed from: a1 */
    public String f61494a1;

    /* renamed from: a2 */
    public final ArrayList f61495a2 = new ArrayList();

    /* renamed from: a2 */
    public static zb1 m215388a2(String str) {
        if (str.startsWith("CUSTOM")) {
            wb1 wb1Var = new wb1();
            wb1Var.f60886a3 = new float[1];
            return wb1Var;
        }
        switch (str) {
            case "rotationX":
                return new vb1(3);
            case "rotationY":
                return new vb1(4);
            case "translationX":
                return new vb1(7);
            case "translationY":
                return new vb1(8);
            case "translationZ":
                return new vb1(9);
            case "progress":
                yb1 yb1Var = new yb1();
                yb1Var.f61289a3 = false;
                return yb1Var;
            case "scaleX":
                return new vb1(5);
            case "scaleY":
                return new vb1(6);
            case "waveVariesBy":
                return new vb1(0);
            case "rotation":
                return new vb1(2);
            case "elevation":
                return new vb1(1);
            case "transitionPathRotate":
                return new xb1();
            case "alpha":
                return new vb1(0);
            case "waveOffset":
                return new vb1(0);
            default:
                return null;
        }
    }

    /* renamed from: a0 */
    public final float m215389a0(float f) {
        o80 o80Var = this.f61493a0;
        b81 b81Var = o80Var.f58757a6;
        if (b81Var != null) {
            b81Var.mo210517c1(f, o80Var.f58758a7);
        } else {
            double[] dArr = o80Var.f58758a7;
            dArr[0] = o80Var.f58755a4[0];
            dArr[1] = o80Var.f58756a5[0];
            dArr[2] = o80Var.f58752a1[0];
        }
        double[] dArr2 = o80Var.f58758a7;
        return (float) ((o80Var.f58751a0.m214284c9(f, dArr2[1]) * o80Var.f58758a7[2]) + dArr2[0]);
    }

    /* renamed from: a1 */
    public final float m215390a1(float f) {
        o80 o80Var = this.f61493a0;
        pg1 pg1Var = o80Var.f58751a0;
        b81 b81Var = o80Var.f58757a6;
        double d = 0.0d;
        if (b81Var != null) {
            double d2 = f;
            b81Var.mo210520c4(d2, o80Var.f58759a8);
            o80Var.f58757a6.mo210517c1(d2, o80Var.f58758a7);
        } else {
            double[] dArr = o80Var.f58759a8;
            dArr[0] = 0.0d;
            dArr[1] = 0.0d;
            dArr[2] = 0.0d;
        }
        double d3 = f;
        double dM214284c9 = pg1Var.m214284c9(d3, o80Var.f58758a7[1]);
        double d4 = o80Var.f58758a7[1];
        double d5 = o80Var.f58759a8[1];
        double dM214281c6 = pg1Var.m214281c6(d3) + d4;
        if (d3 <= 0.0d) {
            d3 = 1.0E-5d;
        } else if (d3 >= 1.0d) {
            d3 = 0.999999d;
        }
        int iBinarySearch = Arrays.binarySearch((double[]) pg1Var.f59230a2, d3);
        if (iBinarySearch <= 0 && iBinarySearch != 0) {
            int i = -iBinarySearch;
            int i2 = i - 1;
            float[] fArr = (float[]) pg1Var.f59229a1;
            float f2 = fArr[i2];
            int i3 = i - 2;
            float f3 = fArr[i3];
            double[] dArr2 = (double[]) pg1Var.f59230a2;
            double d6 = dArr2[i2];
            double d7 = dArr2[i3];
            double d8 = (f2 - f3) / (d6 - d7);
            d = (f3 - (d8 * d7)) + (d3 * d8);
        }
        double dCos = Math.cos(6.283185307179586d * dM214281c6) * (d + d5) * 6.283185307179586d;
        double[] dArr3 = o80Var.f58759a8;
        return (float) ((dCos * o80Var.f58758a7[2]) + (dM214284c9 * dArr3[2]) + dArr3[0]);
    }

    /* renamed from: a3 */
    public abstract void mo214920a3(View view, float f);

    /* renamed from: a4 */
    public final void m215391a4() {
        int i;
        ArrayList arrayList = this.f61495a2;
        int size = arrayList.size();
        if (size == 0) {
            return;
        }
        Collections.sort(arrayList, new C1214s9(8));
        double[] dArr = new double[size];
        int i2 = 0;
        Class cls = Double.TYPE;
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) cls, size, 3);
        o80 o80Var = new o80();
        pg1 pg1Var = new pg1();
        pg1Var.f59229a1 = new float[0];
        pg1Var.f59230a2 = new double[0];
        o80Var.f58751a0 = pg1Var;
        o80Var.f58752a1 = new float[size];
        o80Var.f58753a2 = new double[size];
        o80Var.f58754a3 = new float[size];
        o80Var.f58755a4 = new float[size];
        o80Var.f58756a5 = new float[size];
        float[] fArr = new float[size];
        this.f61493a0 = o80Var;
        Iterator it = arrayList.iterator();
        if (it.hasNext()) {
            throw AbstractC0003a2.m25a6(it);
        }
        o80 o80Var2 = this.f61493a0;
        float[] fArr2 = o80Var2.f58754a3;
        pg1 pg1Var2 = o80Var2.f58751a0;
        double[] dArr3 = o80Var2.f58753a2;
        double[][] dArr4 = (double[][]) Array.newInstance((Class<?>) cls, dArr3.length, 3);
        float[] fArr3 = o80Var2.f58752a1;
        o80Var2.f58758a7 = new double[fArr3.length + 2];
        o80Var2.f58759a8 = new double[fArr3.length + 2];
        if (dArr3[0] > 0.0d) {
            pg1Var2.m214265a9(0.0d, fArr2[0]);
        }
        int length = dArr3.length - 1;
        if (dArr3[length] < 1.0d) {
            pg1Var2.m214265a9(1.0d, fArr2[length]);
        }
        for (int i3 = 0; i3 < dArr4.length; i3++) {
            double[] dArr5 = dArr4[i3];
            dArr5[0] = o80Var2.f58755a4[i3];
            dArr5[1] = o80Var2.f58756a5[i3];
            dArr5[2] = fArr3[i3];
            pg1Var2.m214265a9(dArr3[i3], fArr2[i3]);
        }
        int i4 = 0;
        double d = 0.0d;
        while (true) {
            if (i4 >= ((float[]) pg1Var2.f59229a1).length) {
                break;
            }
            d += r4[i4];
            i4++;
        }
        int i5 = 1;
        double d2 = 0.0d;
        while (true) {
            float[] fArr4 = (float[]) pg1Var2.f59229a1;
            if (i5 >= fArr4.length) {
                break;
            }
            int i6 = i5 - 1;
            float f = (fArr4[i6] + fArr4[i5]) / 2.0f;
            int i7 = i2;
            double[] dArr6 = (double[]) pg1Var2.f59230a2;
            d2 = ((dArr6[i5] - dArr6[i6]) * f) + d2;
            i5++;
            i2 = i7;
        }
        int i8 = i2;
        int i9 = i8;
        while (true) {
            float[] fArr5 = (float[]) pg1Var2.f59229a1;
            if (i9 >= fArr5.length) {
                break;
            }
            fArr5[i9] = (float) (fArr5[i9] * (d / d2));
            i9++;
        }
        ((double[]) pg1Var2.f59231a3)[i8] = 0.0d;
        int i10 = 1;
        while (true) {
            float[] fArr6 = (float[]) pg1Var2.f59229a1;
            if (i10 >= fArr6.length) {
                break;
            }
            int i11 = i10 - 1;
            float f2 = (fArr6[i11] + fArr6[i10]) / 2.0f;
            double[] dArr7 = (double[]) pg1Var2.f59230a2;
            double d3 = dArr7[i10] - dArr7[i11];
            double[] dArr8 = (double[]) pg1Var2.f59231a3;
            dArr8[i10] = (d3 * f2) + dArr8[i11];
            i10++;
        }
        if (dArr3.length > 1) {
            i = i8;
            o80Var2.f58757a6 = b81.m210573b3(i, dArr3, dArr4);
        } else {
            i = i8;
            o80Var2.f58757a6 = null;
        }
        b81.m210573b3(i, dArr, dArr2);
    }

    public final String toString() {
        String str = this.f61494a1;
        new DecimalFormat("##.##");
        Iterator it = this.f61495a2.iterator();
        if (it.hasNext()) {
            throw AbstractC0003a2.m25a6(it);
        }
        return str;
    }
}
