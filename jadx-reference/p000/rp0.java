package p000;

/* loaded from: classes2.dex */
public class rp0 implements InterfaceC0617ic {
    private final int[] DEFAULT_VI;

    /* renamed from: vi */
    private int[] f59802vi;

    public rp0() {
        int[] iArr = {6, 12, 17, 22, 33};
        this.DEFAULT_VI = iArr;
        this.f59802vi = iArr;
    }

    private void checkParams() {
        int[] iArr;
        int i;
        int[] iArr2 = this.f59802vi;
        if (iArr2 == null) {
            throw new IllegalArgumentException("no layers defined.");
        }
        if (iArr2.length <= 1) {
            throw new IllegalArgumentException("Rainbow needs at least 1 layer, such that v1 < v2.");
        }
        int i2 = 0;
        do {
            iArr = this.f59802vi;
            if (i2 >= iArr.length - 1) {
                return;
            }
            i = iArr[i2];
            i2++;
        } while (i < iArr[i2]);
        throw new IllegalArgumentException("v[i] has to be smaller than v[i+1]");
    }

    public int getDocLength() {
        int[] iArr = this.f59802vi;
        return iArr[iArr.length - 1] - iArr[0];
    }

    public int getNumOfLayers() {
        return this.f59802vi.length - 1;
    }

    public int[] getVi() {
        return this.f59802vi;
    }

    public rp0(int[] iArr) {
        this.DEFAULT_VI = new int[]{6, 12, 17, 22, 33};
        this.f59802vi = iArr;
        checkParams();
    }
}
