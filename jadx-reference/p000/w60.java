package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class w60 {

    /* renamed from: a0 */
    public final long[] f60777a0;

    /* renamed from: a1 */
    public final boolean[] f60778a1;

    /* renamed from: a2 */
    public final int[] f60779a2;

    /* renamed from: a3 */
    public boolean f60780a3;

    static {
        new v60(null);
    }

    public w60(int i) {
        this.f60777a0 = new long[i];
        this.f60778a1 = new boolean[i];
        this.f60779a2 = new int[i];
    }

    /* renamed from: a0 */
    public final int[] m215009a0() {
        synchronized (this) {
            try {
                if (!this.f60780a3) {
                    return null;
                }
                long[] jArr = this.f60777a0;
                int length = jArr.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    int i3 = i2 + 1;
                    int i4 = 1;
                    boolean z = jArr[i] > 0;
                    boolean[] zArr = this.f60778a1;
                    if (z != zArr[i2]) {
                        int[] iArr = this.f60779a2;
                        if (!z) {
                            i4 = 2;
                        }
                        iArr[i2] = i4;
                    } else {
                        this.f60779a2[i2] = 0;
                    }
                    zArr[i2] = z;
                    i++;
                    i2 = i3;
                }
                this.f60780a3 = false;
                return (int[]) this.f60779a2.clone();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
