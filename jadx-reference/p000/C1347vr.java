package p000;

import java.lang.reflect.Array;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: vr */
/* loaded from: classes.dex */
public class C1347vr {

    /* renamed from: a2 */
    public static final C1347vr f60675a2 = new C1347vr();

    /* renamed from: a3 */
    public static final String[] f60676a3 = {"standard", "accelerate", "decelerate", "linear"};

    /* renamed from: a0 */
    public final /* synthetic */ int f60677a0;

    /* renamed from: a1 */
    public String f60678a1;

    public C1347vr(String str) {
        this.f60677a0 = 1;
        this.f60678a1 = str;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0134  */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static C1347vr m214949a2(String str) {
        if (str == null) {
            return null;
        }
        if (str.startsWith("cubic")) {
            return new C1346vq(str);
        }
        char c = 3;
        if (str.startsWith("spline")) {
            b21 b21Var = new b21();
            b21Var.f60678a1 = str;
            double[] dArr = new double[str.length() / 2];
            int iIndexOf = str.indexOf(40) + 1;
            int iIndexOf2 = str.indexOf(44, iIndexOf);
            int i = 0;
            while (iIndexOf2 != -1) {
                dArr[i] = Double.parseDouble(str.substring(iIndexOf, iIndexOf2).trim());
                iIndexOf = iIndexOf2 + 1;
                iIndexOf2 = str.indexOf(44, iIndexOf);
                i++;
            }
            dArr[i] = Double.parseDouble(str.substring(iIndexOf, str.indexOf(41, iIndexOf)).trim());
            double[] dArrCopyOf = Arrays.copyOf(dArr, i + 1);
            int length = (dArrCopyOf.length * 3) - 2;
            int length2 = dArrCopyOf.length - 1;
            double d = 1.0d / length2;
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
            double[] dArr3 = new double[length];
            for (int i2 = 0; i2 < dArrCopyOf.length; i2++) {
                double d2 = dArrCopyOf[i2];
                int i3 = i2 + length2;
                dArr2[i3][0] = d2;
                double d3 = i2 * d;
                dArr3[i3] = d3;
                if (i2 > 0) {
                    int i4 = (length2 * 2) + i2;
                    dArr2[i4][0] = d2 + 1.0d;
                    dArr3[i4] = d3 + 1.0d;
                    int i5 = i2 - 1;
                    dArr2[i5][0] = (d2 - 1.0d) - d;
                    dArr3[i5] = (d3 - 1.0d) - d;
                }
            }
            kg0 kg0Var = new kg0(dArr3, dArr2);
            System.out.println(" 0 " + kg0Var.mo210516c0(0.0d));
            System.out.println(" 1 " + kg0Var.mo210516c0(1.0d));
            b21Var.f45677a4 = kg0Var;
            return b21Var;
        }
        if (str.startsWith("Schlick")) {
            gu0 gu0Var = new gu0();
            gu0Var.f60678a1 = str;
            int iIndexOf3 = str.indexOf(40);
            int iIndexOf4 = str.indexOf(44, iIndexOf3);
            gu0Var.f56571a4 = Double.parseDouble(str.substring(iIndexOf3 + 1, iIndexOf4).trim());
            int i6 = iIndexOf4 + 1;
            gu0Var.f56572a5 = Double.parseDouble(str.substring(i6, str.indexOf(44, i6)).trim());
            return gu0Var;
        }
        switch (str.hashCode()) {
            case -1354466595:
                if (str.equals("accelerate")) {
                    c = 0;
                    break;
                } else {
                    c = 65535;
                    break;
                }
            case -1263948740:
                if (str.equals("decelerate")) {
                    c = 1;
                    break;
                }
                break;
            case -1197605014:
                if (str.equals("anticipate")) {
                    c = 2;
                    break;
                }
                break;
            case -1102672091:
                if (!str.equals("linear")) {
                }
                break;
            case -749065269:
                if (str.equals("overshoot")) {
                    c = 4;
                    break;
                }
                break;
            case 1312628413:
                if (str.equals("standard")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return new C1346vq("cubic(0.4, 0.05, 0.8, 0.7)");
            case 1:
                return new C1346vq("cubic(0.0, 0.0, 0.2, 0.95)");
            case 2:
                return new C1346vq("cubic(0.36, 0, 0.66, -0.56)");
            case 3:
                return new C1346vq("cubic(1, 1, 0, 0)");
            case 4:
                return new C1346vq("cubic(0.34, 1.56, 0.64, 1)");
            case 5:
                return new C1346vq("cubic(0.4, 0.0, 0.2, 1)");
            default:
                System.err.println("transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or " + Arrays.toString(f60676a3));
                return f60675a2;
        }
    }

    /* renamed from: a1 */
    public double mo210532a1(double d) {
        return 1.0d;
    }

    public final String toString() {
        switch (this.f60677a0) {
            case 0:
                return this.f60678a1;
            default:
                return "<" + this.f60678a1 + '>';
        }
    }

    public C1347vr() {
        this.f60677a0 = 0;
        this.f60678a1 = "identity";
    }

    /* renamed from: a0 */
    public double mo210531a0(double d) {
        return d;
    }
}
