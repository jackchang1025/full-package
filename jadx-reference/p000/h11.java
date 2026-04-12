package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class h11 implements Cloneable {

    /* renamed from: a3 */
    public static final Object f56594a3 = new Object();

    /* renamed from: a0 */
    public int[] f56595a0;

    /* renamed from: a1 */
    public Object[] f56596a1;

    /* renamed from: a2 */
    public int f56597a2;

    public h11() {
        int i;
        int i2 = 4;
        while (true) {
            i = 40;
            if (i2 >= 32) {
                break;
            }
            int i3 = (1 << i2) - 12;
            if (40 <= i3) {
                i = i3;
                break;
            }
            i2++;
        }
        int i4 = i / 4;
        this.f56595a0 = new int[i4];
        this.f56596a1 = new Object[i4];
    }

    /* renamed from: a0 */
    public final void m212991a0(int i, Object obj) {
        int i2 = this.f56597a2;
        if (i2 != 0 && i <= this.f56595a0[i2 - 1]) {
            m212993a2(i, obj);
            return;
        }
        if (i2 >= this.f56595a0.length) {
            int i3 = (i2 + 1) * 4;
            int i4 = 4;
            while (true) {
                if (i4 >= 32) {
                    break;
                }
                int i5 = (1 << i4) - 12;
                if (i3 <= i5) {
                    i3 = i5;
                    break;
                }
                i4++;
            }
            int i6 = i3 / 4;
            int[] iArr = new int[i6];
            Object[] objArr = new Object[i6];
            int[] iArr2 = this.f56595a0;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr2 = this.f56596a1;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.f56595a0 = iArr;
            this.f56596a1 = objArr;
        }
        this.f56595a0[i2] = i;
        this.f56596a1[i2] = obj;
        this.f56597a2 = i2 + 1;
    }

    /* renamed from: a1 */
    public final Object m212992a1(int i, Integer num) {
        Object obj;
        int iM214687a3 = t60.m214687a3(this.f56597a2, i, this.f56595a0);
        return (iM214687a3 < 0 || (obj = this.f56596a1[iM214687a3]) == f56594a3) ? num : obj;
    }

    /* renamed from: a2 */
    public final void m212993a2(int i, Object obj) {
        int iM214687a3 = t60.m214687a3(this.f56597a2, i, this.f56595a0);
        if (iM214687a3 >= 0) {
            this.f56596a1[iM214687a3] = obj;
            return;
        }
        int i2 = ~iM214687a3;
        int i3 = this.f56597a2;
        if (i2 < i3) {
            Object[] objArr = this.f56596a1;
            if (objArr[i2] == f56594a3) {
                this.f56595a0[i2] = i;
                objArr[i2] = obj;
                return;
            }
        }
        if (i3 >= this.f56595a0.length) {
            int i4 = (i3 + 1) * 4;
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
            int i7 = i4 / 4;
            int[] iArr = new int[i7];
            Object[] objArr2 = new Object[i7];
            int[] iArr2 = this.f56595a0;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.f56596a1;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f56595a0 = iArr;
            this.f56596a1 = objArr2;
        }
        int i8 = this.f56597a2 - i2;
        if (i8 != 0) {
            int[] iArr3 = this.f56595a0;
            int i9 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i9, i8);
            Object[] objArr4 = this.f56596a1;
            System.arraycopy(objArr4, i2, objArr4, i9, this.f56597a2 - i2);
        }
        this.f56595a0[i2] = i;
        this.f56596a1[i2] = obj;
        this.f56597a2++;
    }

    public final Object clone() {
        try {
            h11 h11Var = (h11) super.clone();
            h11Var.f56595a0 = (int[]) this.f56595a0.clone();
            h11Var.f56596a1 = (Object[]) this.f56596a1.clone();
            return h11Var;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final String toString() {
        int i = this.f56597a2;
        if (i <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(i * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f56597a2; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append(this.f56595a0[i2]);
            sb.append('=');
            Object obj = this.f56596a1[i2];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
