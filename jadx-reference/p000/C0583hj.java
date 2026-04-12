package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hj */
/* loaded from: classes.dex */
public final class C0583hj {

    /* renamed from: a0 */
    public long f56673a0 = 0;

    /* renamed from: a1 */
    public C0583hj f56674a1;

    /* renamed from: a0 */
    public final void m213040a0(int i) {
        if (i < 64) {
            this.f56673a0 &= ~(1 << i);
            return;
        }
        C0583hj c0583hj = this.f56674a1;
        if (c0583hj != null) {
            c0583hj.m213040a0(i - 64);
        }
    }

    /* renamed from: a1 */
    public final int m213041a1(int i) {
        C0583hj c0583hj = this.f56674a1;
        if (c0583hj == null) {
            return i >= 64 ? Long.bitCount(this.f56673a0) : Long.bitCount(this.f56673a0 & ((1 << i) - 1));
        }
        if (i < 64) {
            return Long.bitCount(this.f56673a0 & ((1 << i) - 1));
        }
        return Long.bitCount(this.f56673a0) + c0583hj.m213041a1(i - 64);
    }

    /* renamed from: a2 */
    public final void m213042a2() {
        if (this.f56674a1 == null) {
            this.f56674a1 = new C0583hj();
        }
    }

    /* renamed from: a3 */
    public final boolean m213043a3(int i) {
        if (i < 64) {
            return (this.f56673a0 & (1 << i)) != 0;
        }
        m213042a2();
        return this.f56674a1.m213043a3(i - 64);
    }

    /* renamed from: a4 */
    public final void m213044a4(int i, boolean z) {
        if (i >= 64) {
            m213042a2();
            this.f56674a1.m213044a4(i - 64, z);
            return;
        }
        long j = this.f56673a0;
        boolean z2 = (Long.MIN_VALUE & j) != 0;
        long j2 = (1 << i) - 1;
        this.f56673a0 = ((j & (~j2)) << 1) | (j & j2);
        if (z) {
            m213047a7(i);
        } else {
            m213040a0(i);
        }
        if (z2 || this.f56674a1 != null) {
            m213042a2();
            this.f56674a1.m213044a4(0, z2);
        }
    }

    /* renamed from: a5 */
    public final boolean m213045a5(int i) {
        if (i >= 64) {
            m213042a2();
            return this.f56674a1.m213045a5(i - 64);
        }
        long j = 1 << i;
        long j2 = this.f56673a0;
        boolean z = (j2 & j) != 0;
        long j3 = j2 & (~j);
        this.f56673a0 = j3;
        long j4 = j - 1;
        this.f56673a0 = (j3 & j4) | Long.rotateRight((~j4) & j3, 1);
        C0583hj c0583hj = this.f56674a1;
        if (c0583hj != null) {
            if (c0583hj.m213043a3(0)) {
                m213047a7(63);
            }
            this.f56674a1.m213045a5(0);
        }
        return z;
    }

    /* renamed from: a6 */
    public final void m213046a6() {
        this.f56673a0 = 0L;
        C0583hj c0583hj = this.f56674a1;
        if (c0583hj != null) {
            c0583hj.m213046a6();
        }
    }

    /* renamed from: a7 */
    public final void m213047a7(int i) {
        if (i < 64) {
            this.f56673a0 |= 1 << i;
        } else {
            m213042a2();
            this.f56674a1.m213047a7(i - 64);
        }
    }

    public final String toString() {
        if (this.f56674a1 == null) {
            return Long.toBinaryString(this.f56673a0);
        }
        return this.f56674a1.toString() + "xx" + Long.toBinaryString(this.f56673a0);
    }
}
