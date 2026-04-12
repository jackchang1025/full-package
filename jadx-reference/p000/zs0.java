package p000;

/* loaded from: classes2.dex */
public class zs0 extends i80 implements gj1 {
    public zs0() {
        this(128);
    }

    private static int checkBitLength(int i) {
        if (i == 128 || i == 256) {
            return i;
        }
        throw new IllegalArgumentException(AbstractC0003a2.m30b1("'bitLength' ", i, " not supported for SHAKE"));
    }

    @Override // p000.i80, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        return doFinal(bArr, i, getDigestSize());
    }

    @Override // p000.gj1
    public int doOutput(byte[] bArr, int i, int i2) {
        if (!this.squeezing) {
            absorbBits(15, 4);
        }
        squeeze(bArr, i, i2 * 8);
        return i2;
    }

    @Override // p000.i80, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return "SHAKE" + this.fixedOutputLength;
    }

    @Override // p000.i80, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return this.fixedOutputLength / 4;
    }

    public zs0(int i) {
        super(checkBitLength(i));
    }

    @Override // p000.i80
    public int doFinal(byte[] bArr, int i, byte b, int i2) {
        return doFinal(bArr, i, getDigestSize(), b, i2);
    }

    public zs0(zs0 zs0Var) {
        super(zs0Var);
    }

    @Override // p000.gj1
    public int doFinal(byte[] bArr, int i, int i2) {
        int iDoOutput = doOutput(bArr, i, i2);
        reset();
        return iDoOutput;
    }

    public int doFinal(byte[] bArr, int i, int i2, byte b, int i3) {
        if (i3 < 0 || i3 > 7) {
            throw new IllegalArgumentException("'partialBits' must be in the range [0,7]");
        }
        int i4 = (b & ((1 << i3) - 1)) | (15 << i3);
        int i5 = i3 + 4;
        if (i5 >= 8) {
            absorb((byte) i4);
            i5 = i3 - 4;
            i4 >>>= 8;
        }
        if (i5 > 0) {
            absorbBits(i4, i5);
        }
        squeeze(bArr, i, i2 * 8);
        reset();
        return i2;
    }
}
