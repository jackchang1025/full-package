package p000;

/* loaded from: classes2.dex */
public class ts0 extends p20 implements InterfaceC1395wz {
    private static final int DIGEST_LENGTH = 28;

    /* renamed from: K */
    static final int[] f60253K = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};

    /* renamed from: H1 */
    private int f60254H1;

    /* renamed from: H2 */
    private int f60255H2;

    /* renamed from: H3 */
    private int f60256H3;

    /* renamed from: H4 */
    private int f60257H4;

    /* renamed from: H5 */
    private int f60258H5;

    /* renamed from: H6 */
    private int f60259H6;

    /* renamed from: H7 */
    private int f60260H7;

    /* renamed from: H8 */
    private int f60261H8;

    /* renamed from: X */
    private int[] f60262X;
    private int xOff;

    public ts0() {
        this.f60262X = new int[64];
        reset();
    }

    /* renamed from: Ch */
    private int m214780Ch(int i, int i2, int i3) {
        return ((~i) & i3) ^ (i2 & i);
    }

    private int Maj(int i, int i2, int i3) {
        return ((i & i3) ^ (i & i2)) ^ (i2 & i3);
    }

    private int Sum0(int i) {
        return ((i << 10) | (i >>> 22)) ^ (((i >>> 2) | (i << 30)) ^ ((i >>> 13) | (i << 19)));
    }

    private int Sum1(int i) {
        return ((i << 7) | (i >>> 25)) ^ (((i >>> 6) | (i << 26)) ^ ((i >>> 11) | (i << 21)));
    }

    private int Theta0(int i) {
        return (i >>> 3) ^ (((i >>> 7) | (i << 25)) ^ ((i >>> 18) | (i << 14)));
    }

    private int Theta1(int i) {
        return (i >>> 10) ^ (((i >>> 17) | (i << 15)) ^ ((i >>> 19) | (i << 13)));
    }

    private void doCopy(ts0 ts0Var) {
        super.copyIn(ts0Var);
        this.f60254H1 = ts0Var.f60254H1;
        this.f60255H2 = ts0Var.f60255H2;
        this.f60256H3 = ts0Var.f60256H3;
        this.f60257H4 = ts0Var.f60257H4;
        this.f60258H5 = ts0Var.f60258H5;
        this.f60259H6 = ts0Var.f60259H6;
        this.f60260H7 = ts0Var.f60260H7;
        this.f60261H8 = ts0Var.f60261H8;
        int[] iArr = ts0Var.f60262X;
        System.arraycopy(iArr, 0, this.f60262X, 0, iArr.length);
        this.xOff = ts0Var.xOff;
    }

    @Override // p000.p20, p000.xe0
    public xe0 copy() {
        return new ts0(this);
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        finish();
        wl0.intToBigEndian(this.f60254H1, bArr, i);
        wl0.intToBigEndian(this.f60255H2, bArr, i + 4);
        wl0.intToBigEndian(this.f60256H3, bArr, i + 8);
        wl0.intToBigEndian(this.f60257H4, bArr, i + 12);
        wl0.intToBigEndian(this.f60258H5, bArr, i + 16);
        wl0.intToBigEndian(this.f60259H6, bArr, i + 20);
        wl0.intToBigEndian(this.f60260H7, bArr, i + 24);
        reset();
        return DIGEST_LENGTH;
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return "SHA-224";
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return DIGEST_LENGTH;
    }

    @Override // p000.InterfaceC1395wz
    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 52];
        super.populateState(bArr);
        wl0.intToBigEndian(this.f60254H1, bArr, 16);
        wl0.intToBigEndian(this.f60255H2, bArr, 20);
        wl0.intToBigEndian(this.f60256H3, bArr, 24);
        wl0.intToBigEndian(this.f60257H4, bArr, DIGEST_LENGTH);
        wl0.intToBigEndian(this.f60258H5, bArr, 32);
        wl0.intToBigEndian(this.f60259H6, bArr, 36);
        wl0.intToBigEndian(this.f60260H7, bArr, 40);
        wl0.intToBigEndian(this.f60261H8, bArr, 44);
        wl0.intToBigEndian(this.xOff, bArr, 48);
        for (int i = 0; i != this.xOff; i++) {
            wl0.intToBigEndian(this.f60262X[i], bArr, (i * 4) + 52);
        }
        return bArr;
    }

    @Override // p000.p20
    public void processBlock() {
        for (int i = 16; i <= 63; i++) {
            int[] iArr = this.f60262X;
            int iTheta1 = Theta1(iArr[i - 2]);
            int[] iArr2 = this.f60262X;
            iArr[i] = iTheta1 + iArr2[i - 7] + Theta0(iArr2[i - 15]) + this.f60262X[i - 16];
        }
        int iSum0 = this.f60254H1;
        int iSum02 = this.f60255H2;
        int iSum03 = this.f60256H3;
        int iSum04 = this.f60257H4;
        int i2 = this.f60258H5;
        int i3 = this.f60259H6;
        int i4 = this.f60260H7;
        int i5 = this.f60261H8;
        int i6 = 0;
        for (int i7 = 0; i7 < 8; i7++) {
            int iSum1 = Sum1(i2) + m214780Ch(i2, i3, i4);
            int[] iArr3 = f60253K;
            int i8 = iSum1 + iArr3[i6] + this.f60262X[i6] + i5;
            int i9 = iSum04 + i8;
            int iSum05 = Sum0(iSum0) + Maj(iSum0, iSum02, iSum03) + i8;
            int i10 = i6 + 1;
            int iSum12 = Sum1(i9) + m214780Ch(i9, i2, i3) + iArr3[i10] + this.f60262X[i10] + i4;
            int i11 = iSum03 + iSum12;
            int iSum06 = Sum0(iSum05) + Maj(iSum05, iSum0, iSum02) + iSum12;
            int i12 = i6 + 2;
            int iSum13 = Sum1(i11) + m214780Ch(i11, i9, i2) + iArr3[i12] + this.f60262X[i12] + i3;
            int i13 = iSum02 + iSum13;
            int iSum07 = Sum0(iSum06) + Maj(iSum06, iSum05, iSum0) + iSum13;
            int i14 = i6 + 3;
            int iSum14 = Sum1(i13) + m214780Ch(i13, i11, i9) + iArr3[i14] + this.f60262X[i14] + i2;
            int i15 = iSum0 + iSum14;
            int iSum08 = Sum0(iSum07) + Maj(iSum07, iSum06, iSum05) + iSum14;
            int i16 = i6 + 4;
            int iSum15 = Sum1(i15) + m214780Ch(i15, i13, i11) + iArr3[i16] + this.f60262X[i16] + i9;
            i5 = iSum05 + iSum15;
            iSum04 = Sum0(iSum08) + Maj(iSum08, iSum07, iSum06) + iSum15;
            int i17 = i6 + 5;
            int iSum16 = Sum1(i5) + m214780Ch(i5, i15, i13) + iArr3[i17] + this.f60262X[i17] + i11;
            i4 = iSum06 + iSum16;
            iSum03 = Sum0(iSum04) + Maj(iSum04, iSum08, iSum07) + iSum16;
            int i18 = i6 + 6;
            int iSum17 = Sum1(i4) + m214780Ch(i4, i5, i15) + iArr3[i18] + this.f60262X[i18] + i13;
            i3 = iSum07 + iSum17;
            iSum02 = Sum0(iSum03) + Maj(iSum03, iSum04, iSum08) + iSum17;
            int i19 = i6 + 7;
            int iSum18 = Sum1(i3) + m214780Ch(i3, i4, i5) + iArr3[i19] + this.f60262X[i19] + i15;
            i2 = iSum08 + iSum18;
            iSum0 = Sum0(iSum02) + Maj(iSum02, iSum03, iSum04) + iSum18;
            i6 += 8;
        }
        this.f60254H1 += iSum0;
        this.f60255H2 += iSum02;
        this.f60256H3 += iSum03;
        this.f60257H4 += iSum04;
        this.f60258H5 += i2;
        this.f60259H6 += i3;
        this.f60260H7 += i4;
        this.f60261H8 += i5;
        this.xOff = 0;
        for (int i20 = 0; i20 < 16; i20++) {
            this.f60262X[i20] = 0;
        }
    }

    @Override // p000.p20
    public void processLength(long j) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.f60262X;
        iArr[14] = (int) (j >>> 32);
        iArr[15] = (int) j;
    }

    @Override // p000.p20
    public void processWord(byte[] bArr, int i) {
        int i2 = (bArr[i + 3] & 255) | (bArr[i] << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
        int[] iArr = this.f60262X;
        int i3 = this.xOff;
        iArr[i3] = i2;
        int i4 = i3 + 1;
        this.xOff = i4;
        if (i4 == 16) {
            processBlock();
        }
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void reset() {
        super.reset();
        this.f60254H1 = -1056596264;
        this.f60255H2 = 914150663;
        this.f60256H3 = 812702999;
        this.f60257H4 = -150054599;
        this.f60258H5 = -4191439;
        this.f60259H6 = 1750603025;
        this.f60260H7 = 1694076839;
        this.f60261H8 = -1090891868;
        this.xOff = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.f60262X;
            if (i == iArr.length) {
                return;
            }
            iArr[i] = 0;
            i++;
        }
    }

    public ts0(ts0 ts0Var) {
        super(ts0Var);
        this.f60262X = new int[64];
        doCopy(ts0Var);
    }

    @Override // p000.p20, p000.xe0
    public void reset(xe0 xe0Var) {
        doCopy((ts0) xe0Var);
    }

    public ts0(byte[] bArr) {
        super(bArr);
        this.f60262X = new int[64];
        this.f60254H1 = wl0.bigEndianToInt(bArr, 16);
        this.f60255H2 = wl0.bigEndianToInt(bArr, 20);
        this.f60256H3 = wl0.bigEndianToInt(bArr, 24);
        this.f60257H4 = wl0.bigEndianToInt(bArr, DIGEST_LENGTH);
        this.f60258H5 = wl0.bigEndianToInt(bArr, 32);
        this.f60259H6 = wl0.bigEndianToInt(bArr, 36);
        this.f60260H7 = wl0.bigEndianToInt(bArr, 40);
        this.f60261H8 = wl0.bigEndianToInt(bArr, 44);
        this.xOff = wl0.bigEndianToInt(bArr, 48);
        for (int i = 0; i != this.xOff; i++) {
            this.f60262X[i] = wl0.bigEndianToInt(bArr, (i * 4) + 52);
        }
    }
}
