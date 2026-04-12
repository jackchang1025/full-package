package p000;

/* loaded from: classes2.dex */
public class vs0 extends mc0 {
    private static final int DIGEST_LENGTH = 48;

    public vs0() {
    }

    @Override // p000.mc0, p000.xe0
    public xe0 copy() {
        return new vs0(this);
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
        reset();
        return DIGEST_LENGTH;
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return "SHA-384";
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return DIGEST_LENGTH;
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
        this.f58322H1 = -3766243637369397544L;
        this.f58323H2 = 7105036623409894663L;
        this.f58324H3 = -7973340178411365097L;
        this.f58325H4 = 1526699215303891257L;
        this.f58326H5 = 7436329637833083697L;
        this.f58327H6 = -8163818279084223215L;
        this.f58328H7 = -2662702644619276377L;
        this.f58329H8 = 5167115440072839076L;
    }

    public vs0(vs0 vs0Var) {
        super(vs0Var);
    }

    @Override // p000.mc0, p000.xe0
    public void reset(xe0 xe0Var) {
        super.copyIn((vs0) xe0Var);
    }

    public vs0(byte[] bArr) {
        restoreState(bArr);
    }
}
