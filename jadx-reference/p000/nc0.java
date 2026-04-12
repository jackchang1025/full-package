package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class nc0 implements Cloneable {

    /* renamed from: a4 */
    public static final Object f58492a4 = new Object();

    /* renamed from: a0 */
    public boolean f58493a0 = false;

    /* renamed from: a1 */
    public long[] f58494a1;

    /* renamed from: a2 */
    public Object[] f58495a2;

    /* renamed from: a3 */
    public int f58496a3;

    public nc0() {
        int i;
        int i2 = 4;
        while (true) {
            i = 80;
            if (i2 >= 32) {
                break;
            }
            int i3 = (1 << i2) - 12;
            if (80 <= i3) {
                i = i3;
                break;
            }
            i2++;
        }
        int i4 = i / 8;
        this.f58494a1 = new long[i4];
        this.f58495a2 = new Object[i4];
    }

    /* renamed from: a0 */
    public final void m214064a0() {
        int i = this.f58496a3;
        Object[] objArr = this.f58495a2;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.f58496a3 = 0;
        this.f58493a0 = false;
    }

    /* renamed from: a1 */
    public final void m214065a1() {
        int i = this.f58496a3;
        long[] jArr = this.f58494a1;
        Object[] objArr = this.f58495a2;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f58492a4) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.f58493a0 = false;
        this.f58496a3 = i2;
    }

    /* renamed from: a2 */
    public final Object m214066a2(long j, Long l) {
        Object obj;
        int iM214688a6 = t60.m214688a6(j, this.f58494a1, this.f58496a3);
        return (iM214688a6 < 0 || (obj = this.f58495a2[iM214688a6]) == f58492a4) ? l : obj;
    }

    /* renamed from: a3 */
    public final void m214067a3(long j, Object obj) {
        int iM214688a6 = t60.m214688a6(j, this.f58494a1, this.f58496a3);
        if (iM214688a6 >= 0) {
            this.f58495a2[iM214688a6] = obj;
            return;
        }
        int i = ~iM214688a6;
        int i2 = this.f58496a3;
        if (i < i2) {
            Object[] objArr = this.f58495a2;
            if (objArr[i] == f58492a4) {
                this.f58494a1[i] = j;
                objArr[i] = obj;
                return;
            }
        }
        if (this.f58493a0 && i2 >= this.f58494a1.length) {
            m214065a1();
            i = ~t60.m214688a6(j, this.f58494a1, this.f58496a3);
        }
        int i3 = this.f58496a3;
        if (i3 >= this.f58494a1.length) {
            int i4 = (i3 + 1) * 8;
            int i5 = 4;
            while (true) {
                if (i5 >= 32) {
                    break;
                }
                int i6 = (1 << i5) - 12;
                if (i4 <= i6) {
                    i4 = i6;
                    break;
                }
                i5++;
            }
            int i7 = i4 / 8;
            long[] jArr = new long[i7];
            Object[] objArr2 = new Object[i7];
            long[] jArr2 = this.f58494a1;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.f58495a2;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f58494a1 = jArr;
            this.f58495a2 = objArr2;
        }
        int i8 = this.f58496a3 - i;
        if (i8 != 0) {
            long[] jArr3 = this.f58494a1;
            int i9 = i + 1;
            System.arraycopy(jArr3, i, jArr3, i9, i8);
            Object[] objArr4 = this.f58495a2;
            System.arraycopy(objArr4, i, objArr4, i9, this.f58496a3 - i);
        }
        this.f58494a1[i] = j;
        this.f58495a2[i] = obj;
        this.f58496a3++;
    }

    /* renamed from: a4 */
    public final int m214068a4() {
        if (this.f58493a0) {
            m214065a1();
        }
        return this.f58496a3;
    }

    /* renamed from: a5 */
    public final Object m214069a5(int i) {
        if (this.f58493a0) {
            m214065a1();
        }
        return this.f58495a2[i];
    }

    public final Object clone() {
        try {
            nc0 nc0Var = (nc0) super.clone();
            nc0Var.f58494a1 = (long[]) this.f58494a1.clone();
            nc0Var.f58495a2 = (Object[]) this.f58495a2.clone();
            return nc0Var;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final String toString() {
        if (m214068a4() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f58496a3 * 28);
        sb.append('{');
        for (int i = 0; i < this.f58496a3; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            if (this.f58493a0) {
                m214065a1();
            }
            sb.append(this.f58494a1[i]);
            sb.append('=');
            Object objM214069a5 = m214069a5(i);
            if (objM214069a5 != this) {
                sb.append(objM214069a5);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
