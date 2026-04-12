package p000;

import org.bouncycastle.util.MemoableResetException;

/* loaded from: classes2.dex */
public class ys0 extends mc0 {
    private long H1t;
    private long H2t;
    private long H3t;
    private long H4t;
    private long H5t;
    private long H6t;
    private long H7t;
    private long H8t;
    private int digestLength;

    public ys0(int i) {
        if (i >= 512) {
            throw new IllegalArgumentException("bitLength cannot be >= 512");
        }
        if (i % 8 != 0) {
            throw new IllegalArgumentException("bitLength needs to be a multiple of 8");
        }
        if (i == 384) {
            throw new IllegalArgumentException("bitLength cannot be 384 use SHA384 instead");
        }
        int i2 = i / 8;
        this.digestLength = i2;
        tIvGenerate(i2 * 8);
        reset();
    }

    private static void intToBigEndian(int i, byte[] bArr, int i2, int i3) {
        int iMin = Math.min(4, i3);
        while (true) {
            iMin--;
            if (iMin < 0) {
                return;
            } else {
                bArr[i2 + iMin] = (byte) (i >>> ((3 - iMin) * 8));
            }
        }
    }

    private static void longToBigEndian(long j, byte[] bArr, int i, int i2) {
        if (i2 > 0) {
            intToBigEndian((int) (j >>> 32), bArr, i, i2);
            if (i2 > 4) {
                intToBigEndian((int) (j & 4294967295L), bArr, i + 4, i2 - 4);
            }
        }
    }

    private static int readDigestLength(byte[] bArr) {
        return wl0.bigEndianToInt(bArr, bArr.length - 4);
    }

    private void tIvGenerate(int i) {
        this.f58322H1 = -3482333909917012819L;
        this.f58323H2 = 2216346199247487646L;
        this.f58324H3 = -7364697282686394994L;
        this.f58325H4 = 65953792586715988L;
        this.f58326H5 = -816286391624063116L;
        this.f58327H6 = 4512832404995164602L;
        this.f58328H7 = -5033199132376557362L;
        this.f58329H8 = -124578254951840548L;
        update((byte) 83);
        update((byte) 72);
        update((byte) 65);
        update((byte) 45);
        update((byte) 53);
        update((byte) 49);
        update((byte) 50);
        update((byte) 47);
        if (i <= 100) {
            if (i > 10) {
            }
            update((byte) (i + 48));
            finish();
            this.H1t = this.f58322H1;
            this.H2t = this.f58323H2;
            this.H3t = this.f58324H3;
            this.H4t = this.f58325H4;
            this.H5t = this.f58326H5;
            this.H6t = this.f58327H6;
            this.H7t = this.f58328H7;
            this.H8t = this.f58329H8;
        }
        update((byte) ((i / 100) + 48));
        i %= 100;
        update((byte) ((i / 10) + 48));
        i %= 10;
        update((byte) (i + 48));
        finish();
        this.H1t = this.f58322H1;
        this.H2t = this.f58323H2;
        this.H3t = this.f58324H3;
        this.H4t = this.f58325H4;
        this.H5t = this.f58326H5;
        this.H6t = this.f58327H6;
        this.H7t = this.f58328H7;
        this.H8t = this.f58329H8;
    }

    @Override // p000.mc0, p000.xe0
    public xe0 copy() {
        return new ys0(this);
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        finish();
        longToBigEndian(this.f58322H1, bArr, i, this.digestLength);
        longToBigEndian(this.f58323H2, bArr, i + 8, this.digestLength - 8);
        longToBigEndian(this.f58324H3, bArr, i + 16, this.digestLength - 16);
        longToBigEndian(this.f58325H4, bArr, i + 24, this.digestLength - 24);
        longToBigEndian(this.f58326H5, bArr, i + 32, this.digestLength - 32);
        longToBigEndian(this.f58327H6, bArr, i + 40, this.digestLength - 40);
        longToBigEndian(this.f58328H7, bArr, i + 48, this.digestLength - 48);
        longToBigEndian(this.f58329H8, bArr, i + 56, this.digestLength - 56);
        reset();
        return this.digestLength;
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return "SHA-512/" + Integer.toString(this.digestLength * 8);
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return this.digestLength;
    }

    @Override // p000.mc0, p000.InterfaceC1395wz
    public byte[] getEncodedState() {
        int encodedStateSize = getEncodedStateSize();
        byte[] bArr = new byte[encodedStateSize + 4];
        populateState(bArr);
        wl0.intToBigEndian(this.digestLength * 8, bArr, encodedStateSize);
        return bArr;
    }

    @Override // p000.mc0, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void reset() {
        super.reset();
        this.f58322H1 = this.H1t;
        this.f58323H2 = this.H2t;
        this.f58324H3 = this.H3t;
        this.f58325H4 = this.H4t;
        this.f58326H5 = this.H5t;
        this.f58327H6 = this.H6t;
        this.f58328H7 = this.H7t;
        this.f58329H8 = this.H8t;
    }

    public ys0(ys0 ys0Var) {
        super(ys0Var);
        this.digestLength = ys0Var.digestLength;
        reset(ys0Var);
    }

    @Override // p000.mc0, p000.xe0
    public void reset(xe0 xe0Var) {
        ys0 ys0Var = (ys0) xe0Var;
        if (this.digestLength != ys0Var.digestLength) {
            throw new MemoableResetException("digestLength inappropriate in other");
        }
        super.copyIn(ys0Var);
        this.H1t = ys0Var.H1t;
        this.H2t = ys0Var.H2t;
        this.H3t = ys0Var.H3t;
        this.H4t = ys0Var.H4t;
        this.H5t = ys0Var.H5t;
        this.H6t = ys0Var.H6t;
        this.H7t = ys0Var.H7t;
        this.H8t = ys0Var.H8t;
    }

    public ys0(byte[] bArr) {
        this(readDigestLength(bArr));
        restoreState(bArr);
    }
}
