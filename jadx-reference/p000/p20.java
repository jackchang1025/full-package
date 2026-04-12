package p000;

/* loaded from: classes2.dex */
public abstract class p20 implements InterfaceC1432xw, xe0 {
    private static final int BYTE_LENGTH = 64;
    private long byteCount;
    private final byte[] xBuf;
    private int xBufOff;

    public p20() {
        this.xBuf = new byte[4];
        this.xBufOff = 0;
    }

    @Override // p000.xe0
    public abstract /* synthetic */ xe0 copy();

    public void copyIn(p20 p20Var) {
        byte[] bArr = p20Var.xBuf;
        System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
        this.xBufOff = p20Var.xBufOff;
        this.byteCount = p20Var.byteCount;
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public abstract /* synthetic */ int doFinal(byte[] bArr, int i);

    public void finish() {
        long j = this.byteCount << 3;
        byte b = Byte.MIN_VALUE;
        while (true) {
            update(b);
            if (this.xBufOff == 0) {
                processLength(j);
                processBlock();
                return;
            }
            b = 0;
        }
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public abstract /* synthetic */ String getAlgorithmName();

    @Override // p000.InterfaceC1432xw
    public int getByteLength() {
        return 64;
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public abstract /* synthetic */ int getDigestSize();

    public void populateState(byte[] bArr) {
        System.arraycopy(this.xBuf, 0, bArr, 0, this.xBufOff);
        wl0.intToBigEndian(this.xBufOff, bArr, 4);
        wl0.longToBigEndian(this.byteCount, bArr, 8);
    }

    public abstract void processBlock();

    public abstract void processLength(long j);

    public abstract void processWord(byte[] bArr, int i);

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void reset() {
        this.byteCount = 0L;
        this.xBufOff = 0;
        int i = 0;
        while (true) {
            byte[] bArr = this.xBuf;
            if (i >= bArr.length) {
                return;
            }
            bArr[i] = 0;
            i++;
        }
    }

    @Override // p000.xe0
    public abstract /* synthetic */ void reset(xe0 xe0Var);

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void update(byte b) {
        byte[] bArr = this.xBuf;
        int i = this.xBufOff;
        int i2 = i + 1;
        this.xBufOff = i2;
        bArr[i] = b;
        if (i2 == bArr.length) {
            processWord(bArr, 0);
            this.xBufOff = 0;
        }
        this.byteCount++;
    }

    public p20(p20 p20Var) {
        this.xBuf = new byte[4];
        copyIn(p20Var);
    }

    @Override // p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void update(byte[] bArr, int i, int i2) {
        int i3 = 0;
        int iMax = Math.max(0, i2);
        if (this.xBufOff != 0) {
            int i4 = 0;
            while (true) {
                if (i4 >= iMax) {
                    i3 = i4;
                    break;
                }
                byte[] bArr2 = this.xBuf;
                int i5 = this.xBufOff;
                int i6 = i5 + 1;
                this.xBufOff = i6;
                int i7 = i4 + 1;
                bArr2[i5] = bArr[i4 + i];
                if (i6 == 4) {
                    processWord(bArr2, 0);
                    this.xBufOff = 0;
                    i3 = i7;
                    break;
                }
                i4 = i7;
            }
        }
        int i8 = ((iMax - i3) & (-4)) + i3;
        while (i3 < i8) {
            processWord(bArr, i + i3);
            i3 += 4;
        }
        while (i3 < iMax) {
            byte[] bArr3 = this.xBuf;
            int i9 = this.xBufOff;
            this.xBufOff = i9 + 1;
            bArr3[i9] = bArr[i3 + i];
            i3++;
        }
        this.byteCount += iMax;
    }

    public p20(byte[] bArr) {
        byte[] bArr2 = new byte[4];
        this.xBuf = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        this.xBufOff = wl0.bigEndianToInt(bArr, 4);
        this.byteCount = wl0.bigEndianToLong(bArr, 8);
    }
}
