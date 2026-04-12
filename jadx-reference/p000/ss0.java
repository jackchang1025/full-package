package p000;

/* loaded from: classes2.dex */
public class ss0 extends p20 implements InterfaceC1395wz {
    private static final int DIGEST_LENGTH = 20;

    /* renamed from: Y1 */
    private static final int f60073Y1 = 1518500249;

    /* renamed from: Y2 */
    private static final int f60074Y2 = 1859775393;

    /* renamed from: Y3 */
    private static final int f60075Y3 = -1894007588;

    /* renamed from: Y4 */
    private static final int f60076Y4 = -899497514;

    /* renamed from: H1 */
    private int f60077H1;

    /* renamed from: H2 */
    private int f60078H2;

    /* renamed from: H3 */
    private int f60079H3;

    /* renamed from: H4 */
    private int f60080H4;

    /* renamed from: H5 */
    private int f60081H5;

    /* renamed from: X */
    private int[] f60082X;
    private int xOff;

    public ss0() {
        this.f60082X = new int[80];
        reset();
    }

    private void copyIn(ss0 ss0Var) {
        this.f60077H1 = ss0Var.f60077H1;
        this.f60078H2 = ss0Var.f60078H2;
        this.f60079H3 = ss0Var.f60079H3;
        this.f60080H4 = ss0Var.f60080H4;
        this.f60081H5 = ss0Var.f60081H5;
        int[] iArr = ss0Var.f60082X;
        System.arraycopy(iArr, 0, this.f60082X, 0, iArr.length);
        this.xOff = ss0Var.xOff;
    }

    /* renamed from: f */
    private int m214665f(int i, int i2, int i3) {
        return ((~i) & i3) | (i2 & i);
    }

    /* renamed from: g */
    private int m214666g(int i, int i2, int i3) {
        return (i & (i2 | i3)) | (i2 & i3);
    }

    /* renamed from: h */
    private int m214667h(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    @Override // p000.p20, p000.xe0
    public xe0 copy() {
        return new ss0(this);
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        finish();
        wl0.intToBigEndian(this.f60077H1, bArr, i);
        wl0.intToBigEndian(this.f60078H2, bArr, i + 4);
        wl0.intToBigEndian(this.f60079H3, bArr, i + 8);
        wl0.intToBigEndian(this.f60080H4, bArr, i + 12);
        wl0.intToBigEndian(this.f60081H5, bArr, i + 16);
        reset();
        return DIGEST_LENGTH;
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return "SHA-1";
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return DIGEST_LENGTH;
    }

    @Override // p000.InterfaceC1395wz
    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 40];
        super.populateState(bArr);
        wl0.intToBigEndian(this.f60077H1, bArr, 16);
        wl0.intToBigEndian(this.f60078H2, bArr, DIGEST_LENGTH);
        wl0.intToBigEndian(this.f60079H3, bArr, 24);
        wl0.intToBigEndian(this.f60080H4, bArr, 28);
        wl0.intToBigEndian(this.f60081H5, bArr, 32);
        wl0.intToBigEndian(this.xOff, bArr, 36);
        for (int i = 0; i != this.xOff; i++) {
            wl0.intToBigEndian(this.f60082X[i], bArr, (i * 4) + 40);
        }
        return bArr;
    }

    @Override // p000.p20
    public void processBlock() {
        for (int i = 16; i < 80; i++) {
            int[] iArr = this.f60082X;
            int i2 = ((iArr[i - 3] ^ iArr[i - 8]) ^ iArr[i - 14]) ^ iArr[i - 16];
            iArr[i] = (i2 >>> 31) | (i2 << 1);
        }
        int iM20a1 = this.f60077H1;
        int iM20a12 = this.f60078H2;
        int i3 = this.f60079H3;
        int i4 = this.f60080H4;
        int i5 = this.f60081H5;
        int i6 = 0;
        for (int i7 = 0; i7 < 4; i7++) {
            int iM20a13 = AbstractC0003a2.m20a1(((iM20a1 << 5) | (iM20a1 >>> 27)) + m214665f(iM20a12, i3, i4), this.f60082X[i6], f60073Y1, i5);
            int i8 = (iM20a12 >>> 2) | (iM20a12 << 30);
            int iM20a14 = AbstractC0003a2.m20a1(((iM20a13 << 5) | (iM20a13 >>> 27)) + m214665f(iM20a1, i8, i3), this.f60082X[i6 + 1], f60073Y1, i4);
            int i9 = (iM20a1 >>> 2) | (iM20a1 << 30);
            int iM20a15 = AbstractC0003a2.m20a1(((iM20a14 << 5) | (iM20a14 >>> 27)) + m214665f(iM20a13, i9, i8), this.f60082X[i6 + 2], f60073Y1, i3);
            i5 = (iM20a13 >>> 2) | (iM20a13 << 30);
            int i10 = i6 + 4;
            iM20a12 = AbstractC0003a2.m20a1(((iM20a15 << 5) | (iM20a15 >>> 27)) + m214665f(iM20a14, i5, i9), this.f60082X[i6 + 3], f60073Y1, i8);
            i4 = (iM20a14 >>> 2) | (iM20a14 << 30);
            i6 += 5;
            iM20a1 = AbstractC0003a2.m20a1(((iM20a12 << 5) | (iM20a12 >>> 27)) + m214665f(iM20a15, i4, i5), this.f60082X[i10], f60073Y1, i9);
            i3 = (iM20a15 >>> 2) | (iM20a15 << 30);
        }
        for (int i11 = 0; i11 < 4; i11++) {
            int iM20a16 = AbstractC0003a2.m20a1(((iM20a1 << 5) | (iM20a1 >>> 27)) + m214667h(iM20a12, i3, i4), this.f60082X[i6], f60074Y2, i5);
            int i12 = (iM20a12 >>> 2) | (iM20a12 << 30);
            int iM20a17 = AbstractC0003a2.m20a1(((iM20a16 << 5) | (iM20a16 >>> 27)) + m214667h(iM20a1, i12, i3), this.f60082X[i6 + 1], f60074Y2, i4);
            int i13 = (iM20a1 >>> 2) | (iM20a1 << 30);
            int iM20a18 = AbstractC0003a2.m20a1(((iM20a17 << 5) | (iM20a17 >>> 27)) + m214667h(iM20a16, i13, i12), this.f60082X[i6 + 2], f60074Y2, i3);
            i5 = (iM20a16 >>> 2) | (iM20a16 << 30);
            int i14 = i6 + 4;
            iM20a12 = AbstractC0003a2.m20a1(((iM20a18 << 5) | (iM20a18 >>> 27)) + m214667h(iM20a17, i5, i13), this.f60082X[i6 + 3], f60074Y2, i12);
            i4 = (iM20a17 >>> 2) | (iM20a17 << 30);
            i6 += 5;
            iM20a1 = AbstractC0003a2.m20a1(((iM20a12 << 5) | (iM20a12 >>> 27)) + m214667h(iM20a18, i4, i5), this.f60082X[i14], f60074Y2, i13);
            i3 = (iM20a18 >>> 2) | (iM20a18 << 30);
        }
        for (int i15 = 0; i15 < 4; i15++) {
            int iM20a19 = AbstractC0003a2.m20a1(((iM20a1 << 5) | (iM20a1 >>> 27)) + m214666g(iM20a12, i3, i4), this.f60082X[i6], f60075Y3, i5);
            int i16 = (iM20a12 >>> 2) | (iM20a12 << 30);
            int iM20a110 = AbstractC0003a2.m20a1(((iM20a19 << 5) | (iM20a19 >>> 27)) + m214666g(iM20a1, i16, i3), this.f60082X[i6 + 1], f60075Y3, i4);
            int i17 = (iM20a1 >>> 2) | (iM20a1 << 30);
            int iM20a111 = AbstractC0003a2.m20a1(((iM20a110 << 5) | (iM20a110 >>> 27)) + m214666g(iM20a19, i17, i16), this.f60082X[i6 + 2], f60075Y3, i3);
            i5 = (iM20a19 >>> 2) | (iM20a19 << 30);
            int i18 = i6 + 4;
            iM20a12 = AbstractC0003a2.m20a1(((iM20a111 << 5) | (iM20a111 >>> 27)) + m214666g(iM20a110, i5, i17), this.f60082X[i6 + 3], f60075Y3, i16);
            i4 = (iM20a110 >>> 2) | (iM20a110 << 30);
            i6 += 5;
            iM20a1 = AbstractC0003a2.m20a1(((iM20a12 << 5) | (iM20a12 >>> 27)) + m214666g(iM20a111, i4, i5), this.f60082X[i18], f60075Y3, i17);
            i3 = (iM20a111 >>> 2) | (iM20a111 << 30);
        }
        for (int i19 = 0; i19 <= 3; i19++) {
            int iM20a112 = AbstractC0003a2.m20a1(((iM20a1 << 5) | (iM20a1 >>> 27)) + m214667h(iM20a12, i3, i4), this.f60082X[i6], f60076Y4, i5);
            int i20 = (iM20a12 >>> 2) | (iM20a12 << 30);
            int iM20a113 = AbstractC0003a2.m20a1(((iM20a112 << 5) | (iM20a112 >>> 27)) + m214667h(iM20a1, i20, i3), this.f60082X[i6 + 1], f60076Y4, i4);
            int i21 = (iM20a1 >>> 2) | (iM20a1 << 30);
            int iM20a114 = AbstractC0003a2.m20a1(((iM20a113 << 5) | (iM20a113 >>> 27)) + m214667h(iM20a112, i21, i20), this.f60082X[i6 + 2], f60076Y4, i3);
            i5 = (iM20a112 >>> 2) | (iM20a112 << 30);
            int i22 = i6 + 4;
            iM20a12 = AbstractC0003a2.m20a1(((iM20a114 << 5) | (iM20a114 >>> 27)) + m214667h(iM20a113, i5, i21), this.f60082X[i6 + 3], f60076Y4, i20);
            i4 = (iM20a113 >>> 2) | (iM20a113 << 30);
            i6 += 5;
            iM20a1 = AbstractC0003a2.m20a1(((iM20a12 << 5) | (iM20a12 >>> 27)) + m214667h(iM20a114, i4, i5), this.f60082X[i22], f60076Y4, i21);
            i3 = (iM20a114 >>> 2) | (iM20a114 << 30);
        }
        this.f60077H1 += iM20a1;
        this.f60078H2 += iM20a12;
        this.f60079H3 += i3;
        this.f60080H4 += i4;
        this.f60081H5 += i5;
        this.xOff = 0;
        for (int i23 = 0; i23 < 16; i23++) {
            this.f60082X[i23] = 0;
        }
    }

    @Override // p000.p20
    public void processLength(long j) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.f60082X;
        iArr[14] = (int) (j >>> 32);
        iArr[15] = (int) j;
    }

    @Override // p000.p20
    public void processWord(byte[] bArr, int i) {
        int i2 = (bArr[i + 3] & 255) | (bArr[i] << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
        int[] iArr = this.f60082X;
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
        this.f60077H1 = 1732584193;
        this.f60078H2 = -271733879;
        this.f60079H3 = -1732584194;
        this.f60080H4 = 271733878;
        this.f60081H5 = -1009589776;
        this.xOff = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.f60082X;
            if (i == iArr.length) {
                return;
            }
            iArr[i] = 0;
            i++;
        }
    }

    public ss0(ss0 ss0Var) {
        super(ss0Var);
        this.f60082X = new int[80];
        copyIn(ss0Var);
    }

    @Override // p000.p20, p000.xe0
    public void reset(xe0 xe0Var) {
        ss0 ss0Var = (ss0) xe0Var;
        super.copyIn((p20) ss0Var);
        copyIn(ss0Var);
    }

    public ss0(byte[] bArr) {
        super(bArr);
        this.f60082X = new int[80];
        this.f60077H1 = wl0.bigEndianToInt(bArr, 16);
        this.f60078H2 = wl0.bigEndianToInt(bArr, DIGEST_LENGTH);
        this.f60079H3 = wl0.bigEndianToInt(bArr, 24);
        this.f60080H4 = wl0.bigEndianToInt(bArr, 28);
        this.f60081H5 = wl0.bigEndianToInt(bArr, 32);
        this.xOff = wl0.bigEndianToInt(bArr, 36);
        for (int i = 0; i != this.xOff; i++) {
            this.f60082X[i] = wl0.bigEndianToInt(bArr, (i * 4) + 40);
        }
    }
}
