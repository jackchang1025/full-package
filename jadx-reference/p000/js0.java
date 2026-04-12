package p000;

import java.util.TreeMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class js0 implements m31, l31 {

    /* renamed from: a8 */
    public static final is0 f57367a8 = new is0(null);

    /* renamed from: a9 */
    public static final TreeMap f57368a9 = new TreeMap();

    /* renamed from: a0 */
    public final int f57369a0;

    /* renamed from: a1 */
    public volatile String f57370a1;

    /* renamed from: a2 */
    public final long[] f57371a2;

    /* renamed from: a3 */
    public final double[] f57372a3;

    /* renamed from: a4 */
    public final String[] f57373a4;

    /* renamed from: a5 */
    public final byte[][] f57374a5;

    /* renamed from: a6 */
    public final int[] f57375a6;

    /* renamed from: a7 */
    public int f57376a7;

    public js0(int i) {
        this.f57369a0 = i;
        int i2 = i + 1;
        this.f57375a6 = new int[i2];
        this.f57371a2 = new long[i2];
        this.f57372a3 = new double[i2];
        this.f57373a4 = new String[i2];
        this.f57374a5 = new byte[i2][];
    }

    @Override // p000.m31
    /* renamed from: a0 */
    public final String mo213339a0() {
        String str = this.f57370a1;
        if (str != null) {
            return str;
        }
        throw new IllegalStateException("Required value was null.");
    }

    @Override // p000.m31
    /* renamed from: a5 */
    public final void mo213340a5(l31 l31Var) {
        int i = this.f57376a7;
        if (1 > i) {
            return;
        }
        int i2 = 1;
        while (true) {
            int i3 = this.f57375a6[i2];
            if (i3 == 1) {
                l31Var.mo213343a9(i2);
            } else if (i3 == 2) {
                l31Var.mo213346b6(i2, this.f57371a2[i2]);
            } else if (i3 == 3) {
                l31Var.mo213345b1(i2, this.f57372a3[i2]);
            } else if (i3 == 4) {
                String str = this.f57373a4[i2];
                if (str == null) {
                    throw new IllegalArgumentException("Required value was null.");
                }
                l31Var.mo213341a6(i2, str);
            } else if (i3 == 5) {
                byte[] bArr = this.f57374a5[i2];
                if (bArr == null) {
                    throw new IllegalArgumentException("Required value was null.");
                }
                l31Var.mo213347c1(i2, bArr);
            }
            if (i2 == i) {
                return;
            } else {
                i2++;
            }
        }
    }

    @Override // p000.l31
    /* renamed from: a6 */
    public final void mo213341a6(int i, String str) {
        t60.m214695b6(str, "value");
        this.f57375a6[i] = 4;
        this.f57373a4[i] = str;
    }

    @Override // p000.m31
    /* renamed from: a7 */
    public final int mo213342a7() {
        return this.f57376a7;
    }

    @Override // p000.l31
    /* renamed from: a9 */
    public final void mo213343a9(int i) {
        this.f57375a6[i] = 1;
    }

    /* renamed from: b0 */
    public final void m213344b0() {
        TreeMap treeMap = f57368a9;
        synchronized (treeMap) {
            treeMap.put(Integer.valueOf(this.f57369a0), this);
            f57367a8.prunePoolLocked$room_runtime_release();
        }
    }

    @Override // p000.l31
    /* renamed from: b1 */
    public final void mo213345b1(int i, double d) {
        this.f57375a6[i] = 3;
        this.f57372a3[i] = d;
    }

    @Override // p000.l31
    /* renamed from: b6 */
    public final void mo213346b6(int i, long j) {
        this.f57375a6[i] = 2;
        this.f57371a2[i] = j;
    }

    @Override // p000.l31
    /* renamed from: c1 */
    public final void mo213347c1(int i, byte[] bArr) {
        t60.m214695b6(bArr, "value");
        this.f57375a6[i] = 5;
        this.f57374a5[i] = bArr;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }
}
