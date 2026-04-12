package p000;

/* loaded from: classes2.dex */
public class iz0 {

    /* renamed from: I */
    private final byte[] f57247I;
    private final InterfaceC1236sv digest;

    /* renamed from: j */
    private int f57248j;
    private final byte[] masterSeed;

    /* renamed from: q */
    private int f57249q;

    public iz0(byte[] bArr, byte[] bArr2, InterfaceC1236sv interfaceC1236sv) {
        this.f57247I = bArr;
        this.masterSeed = bArr2;
        this.digest = interfaceC1236sv;
    }

    public void deriveSeed(byte[] bArr, boolean z) {
        deriveSeed(bArr, z, 0);
    }

    public byte[] getI() {
        return this.f57247I;
    }

    public int getJ() {
        return this.f57248j;
    }

    public byte[] getMasterSeed() {
        return this.masterSeed;
    }

    public int getQ() {
        return this.f57249q;
    }

    public void setJ(int i) {
        this.f57248j = i;
    }

    public void setQ(int i) {
        this.f57249q = i;
    }

    public void deriveSeed(byte[] bArr, boolean z, int i) {
        deriveSeed(bArr, i);
        if (z) {
            this.f57248j++;
        }
    }

    public byte[] deriveSeed(byte[] bArr, int i) {
        if (bArr.length < this.digest.getDigestSize()) {
            throw new IllegalArgumentException("target length is less than digest size.");
        }
        InterfaceC1236sv interfaceC1236sv = this.digest;
        byte[] bArr2 = this.f57247I;
        interfaceC1236sv.update(bArr2, 0, bArr2.length);
        this.digest.update((byte) (this.f57249q >>> 24));
        this.digest.update((byte) (this.f57249q >>> 16));
        this.digest.update((byte) (this.f57249q >>> 8));
        this.digest.update((byte) this.f57249q);
        this.digest.update((byte) (this.f57248j >>> 8));
        this.digest.update((byte) this.f57248j);
        this.digest.update((byte) -1);
        InterfaceC1236sv interfaceC1236sv2 = this.digest;
        byte[] bArr3 = this.masterSeed;
        interfaceC1236sv2.update(bArr3, 0, bArr3.length);
        this.digest.doFinal(bArr, i);
        return bArr;
    }
}
