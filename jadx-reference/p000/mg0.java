package p000;

import android.graphics.Rect;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class mg0 implements Comparable {

    /* renamed from: a2 */
    public int f58357a2;

    /* renamed from: a0 */
    public float f58355a0 = 1.0f;

    /* renamed from: a1 */
    public int f58356a1 = 0;

    /* renamed from: a3 */
    public float f58358a3 = 0.0f;

    /* renamed from: a4 */
    public float f58359a4 = 0.0f;

    /* renamed from: a5 */
    public float f58360a5 = 0.0f;

    /* renamed from: a6 */
    public float f58361a6 = 0.0f;

    /* renamed from: a7 */
    public float f58362a7 = 1.0f;

    /* renamed from: a8 */
    public float f58363a8 = 1.0f;

    /* renamed from: a9 */
    public float f58364a9 = Float.NaN;

    /* renamed from: b0 */
    public float f58365b0 = Float.NaN;

    /* renamed from: b1 */
    public float f58366b1 = 0.0f;

    /* renamed from: b2 */
    public float f58367b2 = 0.0f;

    /* renamed from: b3 */
    public float f58368b3 = 0.0f;

    /* renamed from: b4 */
    public float f58369b4 = Float.NaN;

    /* renamed from: b5 */
    public float f58370b5 = Float.NaN;

    /* renamed from: b6 */
    public final LinkedHashMap f58371b6 = new LinkedHashMap();

    /* renamed from: a1 */
    public static boolean m213999a1(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* renamed from: a0 */
    public final void m214000a0(HashMap map, int i) {
        for (String str : map.keySet()) {
            tc1 tc1Var = (tc1) map.get(str);
            str.getClass();
            char c = 65535;
            switch (str.hashCode()) {
                case -1249320806:
                    if (str.equals("rotationX")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1249320805:
                    if (str.equals("rotationY")) {
                        c = 1;
                        break;
                    }
                    break;
                case -1225497657:
                    if (str.equals("translationX")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1225497656:
                    if (str.equals("translationY")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1225497655:
                    if (str.equals("translationZ")) {
                        c = 4;
                        break;
                    }
                    break;
                case -1001078227:
                    if (str.equals("progress")) {
                        c = 5;
                        break;
                    }
                    break;
                case -908189618:
                    if (str.equals("scaleX")) {
                        c = 6;
                        break;
                    }
                    break;
                case -908189617:
                    if (str.equals("scaleY")) {
                        c = 7;
                        break;
                    }
                    break;
                case -760884510:
                    if (str.equals("transformPivotX")) {
                        c = '\b';
                        break;
                    }
                    break;
                case -760884509:
                    if (str.equals("transformPivotY")) {
                        c = '\t';
                        break;
                    }
                    break;
                case -40300674:
                    if (str.equals("rotation")) {
                        c = '\n';
                        break;
                    }
                    break;
                case -4379043:
                    if (str.equals("elevation")) {
                        c = 11;
                        break;
                    }
                    break;
                case 37232917:
                    if (str.equals("transitionPathRotate")) {
                        c = '\f';
                        break;
                    }
                    break;
                case 92909918:
                    if (str.equals("alpha")) {
                        c = '\r';
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    tc1Var.mo214378a1(Float.isNaN(this.f58360a5) ? 0.0f : this.f58360a5, i);
                    break;
                case 1:
                    tc1Var.mo214378a1(Float.isNaN(this.f58361a6) ? 0.0f : this.f58361a6, i);
                    break;
                case 2:
                    tc1Var.mo214378a1(Float.isNaN(this.f58366b1) ? 0.0f : this.f58366b1, i);
                    break;
                case 3:
                    tc1Var.mo214378a1(Float.isNaN(this.f58367b2) ? 0.0f : this.f58367b2, i);
                    break;
                case 4:
                    tc1Var.mo214378a1(Float.isNaN(this.f58368b3) ? 0.0f : this.f58368b3, i);
                    break;
                case 5:
                    tc1Var.mo214378a1(Float.isNaN(this.f58370b5) ? 0.0f : this.f58370b5, i);
                    break;
                case 6:
                    tc1Var.mo214378a1(Float.isNaN(this.f58362a7) ? 1.0f : this.f58362a7, i);
                    break;
                case 7:
                    tc1Var.mo214378a1(Float.isNaN(this.f58363a8) ? 1.0f : this.f58363a8, i);
                    break;
                case '\b':
                    tc1Var.mo214378a1(Float.isNaN(this.f58364a9) ? 0.0f : this.f58364a9, i);
                    break;
                case '\t':
                    tc1Var.mo214378a1(Float.isNaN(this.f58365b0) ? 0.0f : this.f58365b0, i);
                    break;
                case '\n':
                    tc1Var.mo214378a1(Float.isNaN(this.f58359a4) ? 0.0f : this.f58359a4, i);
                    break;
                case oe0.DEFAULT_M /* 11 */:
                    tc1Var.mo214378a1(Float.isNaN(this.f58358a3) ? 0.0f : this.f58358a3, i);
                    break;
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    tc1Var.mo214378a1(Float.isNaN(this.f58369b4) ? 0.0f : this.f58369b4, i);
                    break;
                case '\r':
                    tc1Var.mo214378a1(Float.isNaN(this.f58355a0) ? 1.0f : this.f58355a0, i);
                    break;
                default:
                    if (str.startsWith("CUSTOM")) {
                        String str2 = str.split(",")[1];
                        LinkedHashMap linkedHashMap = this.f58371b6;
                        if (linkedHashMap.containsKey(str2)) {
                            C0798kw c0798kw = (C0798kw) linkedHashMap.get(str2);
                            if (tc1Var instanceof qc1) {
                                ((qc1) tc1Var).f59465a5.append(i, c0798kw);
                                break;
                            } else {
                                c0798kw.m213760a0();
                                Objects.toString(tc1Var);
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
            }
        }
    }

    /* renamed from: a2 */
    public final void m214001a2(Rect rect, C0825lm c0825lm, int i, int i2) {
        rect.width();
        rect.height();
        C0820lh c0820lhM213869a7 = c0825lm.m213869a7(i2);
        C0823lk c0823lk = c0820lhM213869a7.f57928a2;
        C0822lj c0822lj = c0820lhM213869a7.f57929a3;
        int i3 = c0823lk.f58026a2;
        this.f58356a1 = i3;
        int i4 = c0823lk.f58025a1;
        this.f58357a2 = i4;
        this.f58355a0 = (i4 == 0 || i3 != 0) ? c0823lk.f58027a3 : 0.0f;
        C0824ll c0824ll = c0820lhM213869a7.f57931a5;
        boolean z = c0824ll.f58042b2;
        this.f58358a3 = c0824ll.f58043b3;
        this.f58359a4 = c0824ll.f58031a1;
        this.f58360a5 = c0824ll.f58032a2;
        this.f58361a6 = c0824ll.f58033a3;
        this.f58362a7 = c0824ll.f58034a4;
        this.f58363a8 = c0824ll.f58035a5;
        this.f58364a9 = c0824ll.f58036a6;
        this.f58365b0 = c0824ll.f58037a7;
        this.f58366b1 = c0824ll.f58039a9;
        this.f58367b2 = c0824ll.f58040b0;
        this.f58368b3 = c0824ll.f58041b1;
        C1347vr.m214949a2(c0822lj.f58010a3);
        this.f58369b4 = c0822lj.f58014a7;
        this.f58370b5 = c0820lhM213869a7.f57928a2.f58028a4;
        for (String str : c0820lhM213869a7.f57932a6.keySet()) {
            C0798kw c0798kw = (C0798kw) c0820lhM213869a7.f57932a6.get(str);
            int iOrdinal = c0798kw.f57734a2.ordinal();
            if (iOrdinal != 4 && iOrdinal != 5 && iOrdinal != 7) {
                this.f58371b6.put(str, c0798kw);
            }
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        return;
                    }
                }
            }
            float f = this.f58359a4 + 90.0f;
            this.f58359a4 = f;
            if (f > 180.0f) {
                this.f58359a4 = f - 360.0f;
                return;
            }
            return;
        }
        this.f58359a4 -= 90.0f;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        ((mg0) obj).getClass();
        return Float.compare(0.0f, 0.0f);
    }
}
