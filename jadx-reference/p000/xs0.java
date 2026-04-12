package p000;

/* loaded from: classes2.dex */
public class xs0 extends mc0 {
    private static final int DIGEST_LENGTH = 64;

    public xs0() {
    }

    @Override // p000.mc0, p000.xe0
    public xe0 copy() {
        return new xs0(this);
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        finish();
        wl0.longToBigEndian(this.f58322H1, bArr, i);
        wl0.longToBigEndian(this.f58323H2, bArr, i + 8);
        wl0.longToBigEndian(this.f58324H3, bArr, i + 16);
        wl0.longToBigEndian(this.f58325H4, bArr, i + 24);
        wl0.longToBigEndian(this.f58326H5, bArr, i + 32);
        wl0.longToBigEndian(this.f58327H6, bArr, i + 40);
        wl0.longToBigEndian(this.f58328H7, bArr, i + 48);
        wl0.longToBigEndian(this.f58329H8, bArr, i + 56);
        reset();
        return 64;
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return ki1.SHA_512;
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return 64;
    }

    @Override // p000.mc0, p000.InterfaceC1395wz
    public byte[] getEncodedState() {
        byte[] bArr = new byte[getEncodedStateSize()];
        super.populateState(bArr);
        return bArr;
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void reset() {
        super.reset();
        this.f58322H1 = 7640891576956012808L;
        this.f58323H2 = -4942790177534073029L;
        this.f58324H3 = 4354685564936845355L;
        this.f58325H4 = -6534734903238641935L;
        this.f58326H5 = 5840696475078001361L;
        this.f58327H6 = -7276294671716946913L;
        this.f58328H7 = 2270897969802886507L;
        this.f58329H8 = 6620516959819538809L;
    }

    public xs0(xs0 xs0Var) {
        super(xs0Var);
    }

    @Override // p000.mc0, p000.xe0
    public void reset(xe0 xe0Var) {
        copyIn((xs0) xe0Var);
    }

    public xs0(byte[] bArr) {
        restoreState(bArr);
    }
}
