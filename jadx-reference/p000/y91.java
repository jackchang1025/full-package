package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class y91 {

    /* renamed from: a0 */
    public int f61269a0;

    /* renamed from: a1 */
    public int f61270a1;

    /* renamed from: a2 */
    public int f61271a2;

    /* renamed from: a3 */
    public int f61272a3;

    /* renamed from: a4 */
    public int f61273a4;

    /* renamed from: a0 */
    public final boolean m215271a0() {
        int i = this.f61269a0;
        int i2 = 2;
        if ((i & 7) != 0) {
            int i3 = this.f61272a3;
            int i4 = this.f61270a1;
            if (((i3 > i4 ? 1 : i3 == i4 ? 2 : 4) & i) == 0) {
                return false;
            }
        }
        if ((i & 112) != 0) {
            int i5 = this.f61272a3;
            int i6 = this.f61271a2;
            if ((((i5 > i6 ? 1 : i5 == i6 ? 2 : 4) << 4) & i) == 0) {
                return false;
            }
        }
        if ((i & 1792) != 0) {
            int i7 = this.f61273a4;
            int i8 = this.f61270a1;
            if ((((i7 > i8 ? 1 : i7 == i8 ? 2 : 4) << 8) & i) == 0) {
                return false;
            }
        }
        if ((i & 28672) != 0) {
            int i9 = this.f61273a4;
            int i10 = this.f61271a2;
            if (i9 > i10) {
                i2 = 1;
            } else if (i9 != i10) {
                i2 = 4;
            }
            if ((i & (i2 << 12)) == 0) {
                return false;
            }
        }
        return true;
    }
}
