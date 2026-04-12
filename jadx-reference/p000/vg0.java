package p000;

import java.util.LinkedHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class vg0 implements Comparable {

    /* renamed from: b7 */
    public static final String[] f60625b7 = {"position", "x", "y", "width", "height", "pathRotate"};

    /* renamed from: a0 */
    public C1347vr f60626a0;

    /* renamed from: a2 */
    public float f60628a2;

    /* renamed from: a3 */
    public float f60629a3;

    /* renamed from: a4 */
    public float f60630a4;

    /* renamed from: a5 */
    public float f60631a5;

    /* renamed from: a6 */
    public float f60632a6;

    /* renamed from: a7 */
    public float f60633a7;

    /* renamed from: a1 */
    public int f60627a1 = 0;

    /* renamed from: a8 */
    public float f60634a8 = Float.NaN;

    /* renamed from: a9 */
    public int f60635a9 = -1;

    /* renamed from: b0 */
    public int f60636b0 = -1;

    /* renamed from: b1 */
    public float f60637b1 = Float.NaN;

    /* renamed from: b2 */
    public og0 f60638b2 = null;

    /* renamed from: b3 */
    public LinkedHashMap f60639b3 = new LinkedHashMap();

    /* renamed from: b4 */
    public int f60640b4 = 0;

    /* renamed from: b5 */
    public double[] f60641b5 = new double[18];

    /* renamed from: b6 */
    public double[] f60642b6 = new double[18];

    /* renamed from: a1 */
    public static boolean m214923a1(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    /* renamed from: a4 */
    public static void m214924a4(float f, float f2, float[] fArr, int[] iArr, double[] dArr, double[] dArr2) {
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float f7 = (float) dArr[i];
            double d = dArr2[i];
            int i2 = iArr[i];
            if (i2 == 1) {
                f3 = f7;
            } else if (i2 == 2) {
                f5 = f7;
            } else if (i2 == 3) {
                f4 = f7;
            } else if (i2 == 4) {
                f6 = f7;
            }
        }
        float f8 = f3 - ((0.0f * f4) / 2.0f);
        float f9 = f5 - ((0.0f * f6) / 2.0f);
        fArr[0] = (((f4 * 1.0f) + f8) * f) + ((1.0f - f) * f8) + 0.0f;
        fArr[1] = (((f6 * 1.0f) + f9) * f2) + ((1.0f - f2) * f9) + 0.0f;
    }

    /* renamed from: a0 */
    public final void m214925a0(C0820lh c0820lh) {
        int iOrdinal;
        this.f60626a0 = C1347vr.m214949a2(c0820lh.f57929a3.f58010a3);
        C0822lj c0822lj = c0820lh.f57929a3;
        this.f60635a9 = c0822lj.f58011a4;
        this.f60636b0 = c0822lj.f58008a1;
        this.f60634a8 = c0822lj.f58014a7;
        this.f60627a1 = c0822lj.f58012a5;
        this.f60637b1 = c0820lh.f57930a4.f57963c8;
        for (String str : c0820lh.f57932a6.keySet()) {
            C0798kw c0798kw = (C0798kw) c0820lh.f57932a6.get(str);
            if (c0798kw != null && (iOrdinal = c0798kw.f57734a2.ordinal()) != 4 && iOrdinal != 5 && iOrdinal != 7) {
                this.f60639b3.put(str, c0798kw);
            }
        }
    }

    /* renamed from: a2 */
    public final void m214926a2(double d, int[] iArr, double[] dArr, float[] fArr, int i) {
        float fSin = this.f60630a4;
        float fCos = this.f60631a5;
        float f = this.f60632a6;
        float f2 = this.f60633a7;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            float f3 = (float) dArr[i2];
            int i3 = iArr[i2];
            if (i3 == 1) {
                fSin = f3;
            } else if (i3 == 2) {
                fCos = f3;
            } else if (i3 == 3) {
                f = f3;
            } else if (i3 == 4) {
                f2 = f3;
            }
        }
        og0 og0Var = this.f60638b2;
        if (og0Var != null) {
            float[] fArr2 = new float[2];
            og0Var.m214194a2(d, fArr2, new float[2]);
            float f4 = fArr2[0];
            float f5 = fArr2[1];
            double d2 = f4;
            double d3 = fSin;
            double d4 = fCos;
            fSin = (float) (((Math.sin(d4) * d3) + d2) - (f / 2.0f));
            fCos = (float) ((f5 - (Math.cos(d4) * d3)) - (f2 / 2.0f));
        }
        fArr[i] = (f / 2.0f) + fSin + 0.0f;
        fArr[i + 1] = (f2 / 2.0f) + fCos + 0.0f;
    }

    /* renamed from: a3 */
    public final void m214927a3(float f, float f2, float f3, float f4) {
        this.f60630a4 = f;
        this.f60631a5 = f2;
        this.f60632a6 = f3;
        this.f60633a7 = f4;
    }

    /* renamed from: a5 */
    public final void m214928a5(og0 og0Var, vg0 vg0Var) {
        double d = (((this.f60632a6 / 2.0f) + this.f60630a4) - vg0Var.f60630a4) - (vg0Var.f60632a6 / 2.0f);
        double d2 = (((this.f60633a7 / 2.0f) + this.f60631a5) - vg0Var.f60631a5) - (vg0Var.f60633a7 / 2.0f);
        this.f60638b2 = og0Var;
        this.f60630a4 = (float) Math.hypot(d2, d);
        if (Float.isNaN(this.f60637b1)) {
            this.f60631a5 = (float) (Math.atan2(d2, d) + 1.5707963267948966d);
        } else {
            this.f60631a5 = (float) Math.toRadians(this.f60637b1);
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Float.compare(this.f60629a3, ((vg0) obj).f60629a3);
    }
}
