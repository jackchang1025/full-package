package p000;

/* loaded from: classes2.dex */
public class qc0 extends p20 implements InterfaceC1395wz {
    private static final int DIGEST_LENGTH = 16;
    private static final int S11 = 7;
    private static final int S12 = 12;
    private static final int S13 = 17;
    private static final int S14 = 22;
    private static final int S21 = 5;
    private static final int S22 = 9;
    private static final int S23 = 14;
    private static final int S24 = 20;
    private static final int S31 = 4;
    private static final int S32 = 11;
    private static final int S33 = 16;
    private static final int S34 = 23;
    private static final int S41 = 6;
    private static final int S42 = 10;
    private static final int S43 = 15;
    private static final int S44 = 21;

    /* renamed from: H1 */
    private int f59460H1;

    /* renamed from: H2 */
    private int f59461H2;

    /* renamed from: H3 */
    private int f59462H3;

    /* renamed from: H4 */
    private int f59463H4;

    /* renamed from: X */
    private int[] f59464X;
    private int xOff;

    public qc0() {
        this.f59464X = new int[16];
        reset();
    }

    /* renamed from: F */
    private int m214374F(int i, int i2, int i3) {
        return ((~i) & i3) | (i2 & i);
    }

    /* renamed from: G */
    private int m214375G(int i, int i2, int i3) {
        return (i & i3) | (i2 & (~i3));
    }

    /* renamed from: H */
    private int m214376H(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    /* renamed from: K */
    private int m214377K(int i, int i2, int i3) {
        return (i | (~i3)) ^ i2;
    }

    private void copyIn(qc0 qc0Var) {
        super.copyIn((p20) qc0Var);
        this.f59460H1 = qc0Var.f59460H1;
        this.f59461H2 = qc0Var.f59461H2;
        this.f59462H3 = qc0Var.f59462H3;
        this.f59463H4 = qc0Var.f59463H4;
        int[] iArr = qc0Var.f59464X;
        System.arraycopy(iArr, 0, this.f59464X, 0, iArr.length);
        this.xOff = qc0Var.xOff;
    }

    private int rotateLeft(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private void unpackWord(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 3] = (byte) (i >>> 24);
    }

    @Override // p000.p20, p000.xe0
    public xe0 copy() {
        return new qc0(this);
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int doFinal(byte[] bArr, int i) {
        finish();
        unpackWord(this.f59460H1, bArr, i);
        unpackWord(this.f59461H2, bArr, i + 4);
        unpackWord(this.f59462H3, bArr, i + 8);
        unpackWord(this.f59463H4, bArr, i + 12);
        reset();
        return 16;
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public String getAlgorithmName() {
        return "MD5";
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public int getDigestSize() {
        return 16;
    }

    @Override // p000.InterfaceC1395wz
    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 36];
        super.populateState(bArr);
        wl0.intToBigEndian(this.f59460H1, bArr, 16);
        wl0.intToBigEndian(this.f59461H2, bArr, S24);
        wl0.intToBigEndian(this.f59462H3, bArr, 24);
        wl0.intToBigEndian(this.f59463H4, bArr, 28);
        wl0.intToBigEndian(this.xOff, bArr, 32);
        for (int i = 0; i != this.xOff; i++) {
            wl0.intToBigEndian(this.f59464X[i], bArr, (i * 4) + 36);
        }
        return bArr;
    }

    @Override // p000.p20
    public void processBlock() {
        int i = this.f59460H1;
        int i2 = this.f59461H2;
        int i3 = this.f59462H3;
        int i4 = this.f59463H4;
        int iRotateLeft = rotateLeft(((i + m214374F(i2, i3, i4)) + this.f59464X[0]) - 680876936, 7) + i2;
        int iRotateLeft2 = rotateLeft(((i4 + m214374F(iRotateLeft, i2, i3)) + this.f59464X[1]) - 389564586, 12) + iRotateLeft;
        int iRotateLeft3 = rotateLeft(i3 + m214374F(iRotateLeft2, iRotateLeft, i2) + this.f59464X[2] + 606105819, S13) + iRotateLeft2;
        int iRotateLeft4 = rotateLeft(((i2 + m214374F(iRotateLeft3, iRotateLeft2, iRotateLeft)) + this.f59464X[3]) - 1044525330, S14) + iRotateLeft3;
        int iRotateLeft5 = rotateLeft(((iRotateLeft + m214374F(iRotateLeft4, iRotateLeft3, iRotateLeft2)) + this.f59464X[4]) - 176418897, 7) + iRotateLeft4;
        int iRotateLeft6 = rotateLeft(iRotateLeft2 + m214374F(iRotateLeft5, iRotateLeft4, iRotateLeft3) + this.f59464X[5] + 1200080426, 12) + iRotateLeft5;
        int iRotateLeft7 = rotateLeft(((iRotateLeft3 + m214374F(iRotateLeft6, iRotateLeft5, iRotateLeft4)) + this.f59464X[6]) - 1473231341, S13) + iRotateLeft6;
        int iRotateLeft8 = rotateLeft(((iRotateLeft4 + m214374F(iRotateLeft7, iRotateLeft6, iRotateLeft5)) + this.f59464X[7]) - 45705983, S14) + iRotateLeft7;
        int iRotateLeft9 = rotateLeft(iRotateLeft5 + m214374F(iRotateLeft8, iRotateLeft7, iRotateLeft6) + this.f59464X[8] + 1770035416, 7) + iRotateLeft8;
        int iRotateLeft10 = rotateLeft(((iRotateLeft6 + m214374F(iRotateLeft9, iRotateLeft8, iRotateLeft7)) + this.f59464X[9]) - 1958414417, 12) + iRotateLeft9;
        int iRotateLeft11 = rotateLeft(((iRotateLeft7 + m214374F(iRotateLeft10, iRotateLeft9, iRotateLeft8)) + this.f59464X[10]) - 42063, S13) + iRotateLeft10;
        int iRotateLeft12 = rotateLeft(((iRotateLeft8 + m214374F(iRotateLeft11, iRotateLeft10, iRotateLeft9)) + this.f59464X[11]) - 1990404162, S14) + iRotateLeft11;
        int iRotateLeft13 = rotateLeft(iRotateLeft9 + m214374F(iRotateLeft12, iRotateLeft11, iRotateLeft10) + this.f59464X[12] + 1804603682, 7) + iRotateLeft12;
        int iRotateLeft14 = rotateLeft(((iRotateLeft10 + m214374F(iRotateLeft13, iRotateLeft12, iRotateLeft11)) + this.f59464X[13]) - 40341101, 12) + iRotateLeft13;
        int iRotateLeft15 = rotateLeft(((iRotateLeft11 + m214374F(iRotateLeft14, iRotateLeft13, iRotateLeft12)) + this.f59464X[S23]) - 1502002290, S13) + iRotateLeft14;
        int iRotateLeft16 = rotateLeft(iRotateLeft12 + m214374F(iRotateLeft15, iRotateLeft14, iRotateLeft13) + this.f59464X[15] + 1236535329, S14) + iRotateLeft15;
        int iRotateLeft17 = rotateLeft(((iRotateLeft13 + m214375G(iRotateLeft16, iRotateLeft15, iRotateLeft14)) + this.f59464X[1]) - 165796510, 5) + iRotateLeft16;
        int iRotateLeft18 = rotateLeft(((iRotateLeft14 + m214375G(iRotateLeft17, iRotateLeft16, iRotateLeft15)) + this.f59464X[6]) - 1069501632, 9) + iRotateLeft17;
        int iRotateLeft19 = rotateLeft(iRotateLeft15 + m214375G(iRotateLeft18, iRotateLeft17, iRotateLeft16) + this.f59464X[11] + 643717713, S23) + iRotateLeft18;
        int iRotateLeft20 = rotateLeft(((iRotateLeft16 + m214375G(iRotateLeft19, iRotateLeft18, iRotateLeft17)) + this.f59464X[0]) - 373897302, S24) + iRotateLeft19;
        int iRotateLeft21 = rotateLeft(((iRotateLeft17 + m214375G(iRotateLeft20, iRotateLeft19, iRotateLeft18)) + this.f59464X[5]) - 701558691, 5) + iRotateLeft20;
        int iRotateLeft22 = rotateLeft(iRotateLeft18 + m214375G(iRotateLeft21, iRotateLeft20, iRotateLeft19) + this.f59464X[10] + 38016083, 9) + iRotateLeft21;
        int iRotateLeft23 = rotateLeft(((iRotateLeft19 + m214375G(iRotateLeft22, iRotateLeft21, iRotateLeft20)) + this.f59464X[15]) - 660478335, S23) + iRotateLeft22;
        int iRotateLeft24 = rotateLeft(((iRotateLeft20 + m214375G(iRotateLeft23, iRotateLeft22, iRotateLeft21)) + this.f59464X[4]) - 405537848, S24) + iRotateLeft23;
        int iRotateLeft25 = rotateLeft(iRotateLeft21 + m214375G(iRotateLeft24, iRotateLeft23, iRotateLeft22) + this.f59464X[9] + 568446438, 5) + iRotateLeft24;
        int iRotateLeft26 = rotateLeft(((iRotateLeft22 + m214375G(iRotateLeft25, iRotateLeft24, iRotateLeft23)) + this.f59464X[S23]) - 1019803690, 9) + iRotateLeft25;
        int iRotateLeft27 = rotateLeft(((iRotateLeft23 + m214375G(iRotateLeft26, iRotateLeft25, iRotateLeft24)) + this.f59464X[3]) - 187363961, S23) + iRotateLeft26;
        int iRotateLeft28 = rotateLeft(iRotateLeft24 + m214375G(iRotateLeft27, iRotateLeft26, iRotateLeft25) + this.f59464X[8] + 1163531501, S24) + iRotateLeft27;
        int iRotateLeft29 = rotateLeft(((iRotateLeft25 + m214375G(iRotateLeft28, iRotateLeft27, iRotateLeft26)) + this.f59464X[13]) - 1444681467, 5) + iRotateLeft28;
        int iRotateLeft30 = rotateLeft(((iRotateLeft26 + m214375G(iRotateLeft29, iRotateLeft28, iRotateLeft27)) + this.f59464X[2]) - 51403784, 9) + iRotateLeft29;
        int iRotateLeft31 = rotateLeft(iRotateLeft27 + m214375G(iRotateLeft30, iRotateLeft29, iRotateLeft28) + this.f59464X[7] + 1735328473, S23) + iRotateLeft30;
        int iRotateLeft32 = rotateLeft(((iRotateLeft28 + m214375G(iRotateLeft31, iRotateLeft30, iRotateLeft29)) + this.f59464X[12]) - 1926607734, S24) + iRotateLeft31;
        int iRotateLeft33 = rotateLeft(((iRotateLeft29 + m214376H(iRotateLeft32, iRotateLeft31, iRotateLeft30)) + this.f59464X[5]) - 378558, 4) + iRotateLeft32;
        int iRotateLeft34 = rotateLeft(((iRotateLeft30 + m214376H(iRotateLeft33, iRotateLeft32, iRotateLeft31)) + this.f59464X[8]) - 2022574463, 11) + iRotateLeft33;
        int iRotateLeft35 = rotateLeft(iRotateLeft31 + m214376H(iRotateLeft34, iRotateLeft33, iRotateLeft32) + this.f59464X[11] + 1839030562, 16) + iRotateLeft34;
        int iRotateLeft36 = rotateLeft(((iRotateLeft32 + m214376H(iRotateLeft35, iRotateLeft34, iRotateLeft33)) + this.f59464X[S23]) - 35309556, S34) + iRotateLeft35;
        int iRotateLeft37 = rotateLeft(((iRotateLeft33 + m214376H(iRotateLeft36, iRotateLeft35, iRotateLeft34)) + this.f59464X[1]) - 1530992060, 4) + iRotateLeft36;
        int iRotateLeft38 = rotateLeft(iRotateLeft34 + m214376H(iRotateLeft37, iRotateLeft36, iRotateLeft35) + this.f59464X[4] + 1272893353, 11) + iRotateLeft37;
        int iRotateLeft39 = rotateLeft(((iRotateLeft35 + m214376H(iRotateLeft38, iRotateLeft37, iRotateLeft36)) + this.f59464X[7]) - 155497632, 16) + iRotateLeft38;
        int iRotateLeft40 = rotateLeft(((iRotateLeft36 + m214376H(iRotateLeft39, iRotateLeft38, iRotateLeft37)) + this.f59464X[10]) - 1094730640, S34) + iRotateLeft39;
        int iRotateLeft41 = rotateLeft(iRotateLeft37 + m214376H(iRotateLeft40, iRotateLeft39, iRotateLeft38) + this.f59464X[13] + 681279174, 4) + iRotateLeft40;
        int iRotateLeft42 = rotateLeft(((iRotateLeft38 + m214376H(iRotateLeft41, iRotateLeft40, iRotateLeft39)) + this.f59464X[0]) - 358537222, 11) + iRotateLeft41;
        int iRotateLeft43 = rotateLeft(((iRotateLeft39 + m214376H(iRotateLeft42, iRotateLeft41, iRotateLeft40)) + this.f59464X[3]) - 722521979, 16) + iRotateLeft42;
        int iRotateLeft44 = rotateLeft(iRotateLeft40 + m214376H(iRotateLeft43, iRotateLeft42, iRotateLeft41) + this.f59464X[6] + 76029189, S34) + iRotateLeft43;
        int iRotateLeft45 = rotateLeft(((iRotateLeft41 + m214376H(iRotateLeft44, iRotateLeft43, iRotateLeft42)) + this.f59464X[9]) - 640364487, 4) + iRotateLeft44;
        int iRotateLeft46 = rotateLeft(((iRotateLeft42 + m214376H(iRotateLeft45, iRotateLeft44, iRotateLeft43)) + this.f59464X[12]) - 421815835, 11) + iRotateLeft45;
        int iRotateLeft47 = rotateLeft(iRotateLeft43 + m214376H(iRotateLeft46, iRotateLeft45, iRotateLeft44) + this.f59464X[15] + 530742520, 16) + iRotateLeft46;
        int iRotateLeft48 = rotateLeft(((iRotateLeft44 + m214376H(iRotateLeft47, iRotateLeft46, iRotateLeft45)) + this.f59464X[2]) - 995338651, S34) + iRotateLeft47;
        int iRotateLeft49 = rotateLeft(((iRotateLeft45 + m214377K(iRotateLeft48, iRotateLeft47, iRotateLeft46)) + this.f59464X[0]) - 198630844, 6) + iRotateLeft48;
        int iRotateLeft50 = rotateLeft(iRotateLeft46 + m214377K(iRotateLeft49, iRotateLeft48, iRotateLeft47) + this.f59464X[7] + 1126891415, 10) + iRotateLeft49;
        int iRotateLeft51 = rotateLeft(((iRotateLeft47 + m214377K(iRotateLeft50, iRotateLeft49, iRotateLeft48)) + this.f59464X[S23]) - 1416354905, 15) + iRotateLeft50;
        int iRotateLeft52 = rotateLeft(((iRotateLeft48 + m214377K(iRotateLeft51, iRotateLeft50, iRotateLeft49)) + this.f59464X[5]) - 57434055, S44) + iRotateLeft51;
        int iRotateLeft53 = rotateLeft(iRotateLeft49 + m214377K(iRotateLeft52, iRotateLeft51, iRotateLeft50) + this.f59464X[12] + 1700485571, 6) + iRotateLeft52;
        int iRotateLeft54 = rotateLeft(((iRotateLeft50 + m214377K(iRotateLeft53, iRotateLeft52, iRotateLeft51)) + this.f59464X[3]) - 1894986606, 10) + iRotateLeft53;
        int iRotateLeft55 = rotateLeft(((iRotateLeft51 + m214377K(iRotateLeft54, iRotateLeft53, iRotateLeft52)) + this.f59464X[10]) - 1051523, 15) + iRotateLeft54;
        int iRotateLeft56 = rotateLeft(((iRotateLeft52 + m214377K(iRotateLeft55, iRotateLeft54, iRotateLeft53)) + this.f59464X[1]) - 2054922799, S44) + iRotateLeft55;
        int iRotateLeft57 = rotateLeft(iRotateLeft53 + m214377K(iRotateLeft56, iRotateLeft55, iRotateLeft54) + this.f59464X[8] + 1873313359, 6) + iRotateLeft56;
        int iRotateLeft58 = rotateLeft(((iRotateLeft54 + m214377K(iRotateLeft57, iRotateLeft56, iRotateLeft55)) + this.f59464X[15]) - 30611744, 10) + iRotateLeft57;
        int iRotateLeft59 = rotateLeft(((iRotateLeft55 + m214377K(iRotateLeft58, iRotateLeft57, iRotateLeft56)) + this.f59464X[6]) - 1560198380, 15) + iRotateLeft58;
        int iRotateLeft60 = rotateLeft(iRotateLeft56 + m214377K(iRotateLeft59, iRotateLeft58, iRotateLeft57) + this.f59464X[13] + 1309151649, S44) + iRotateLeft59;
        int iRotateLeft61 = rotateLeft(((iRotateLeft57 + m214377K(iRotateLeft60, iRotateLeft59, iRotateLeft58)) + this.f59464X[4]) - 145523070, 6) + iRotateLeft60;
        int iRotateLeft62 = rotateLeft(((iRotateLeft58 + m214377K(iRotateLeft61, iRotateLeft60, iRotateLeft59)) + this.f59464X[11]) - 1120210379, 10) + iRotateLeft61;
        int iRotateLeft63 = rotateLeft(iRotateLeft59 + m214377K(iRotateLeft62, iRotateLeft61, iRotateLeft60) + this.f59464X[2] + 718787259, 15) + iRotateLeft62;
        int iRotateLeft64 = rotateLeft(((iRotateLeft60 + m214377K(iRotateLeft63, iRotateLeft62, iRotateLeft61)) + this.f59464X[9]) - 343485551, S44) + iRotateLeft63;
        this.f59460H1 += iRotateLeft61;
        this.f59461H2 += iRotateLeft64;
        this.f59462H3 += iRotateLeft63;
        this.f59463H4 += iRotateLeft62;
        this.xOff = 0;
        int i5 = 0;
        while (true) {
            int[] iArr = this.f59464X;
            if (i5 == iArr.length) {
                return;
            }
            iArr[i5] = 0;
            i5++;
        }
    }

    @Override // p000.p20
    public void processLength(long j) {
        if (this.xOff > S23) {
            processBlock();
        }
        int[] iArr = this.f59464X;
        iArr[S23] = (int) j;
        iArr[15] = (int) (j >>> 32);
    }

    @Override // p000.p20
    public void processWord(byte[] bArr, int i) {
        int[] iArr = this.f59464X;
        int i2 = this.xOff;
        int i3 = i2 + 1;
        this.xOff = i3;
        iArr[i2] = ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
        if (i3 == 16) {
            processBlock();
        }
    }

    @Override // p000.p20, p000.InterfaceC1432xw, p000.InterfaceC1236sv
    public void reset() {
        super.reset();
        this.f59460H1 = 1732584193;
        this.f59461H2 = -271733879;
        this.f59462H3 = -1732584194;
        this.f59463H4 = 271733878;
        this.xOff = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.f59464X;
            if (i == iArr.length) {
                return;
            }
            iArr[i] = 0;
            i++;
        }
    }

    public qc0(qc0 qc0Var) {
        super(qc0Var);
        this.f59464X = new int[16];
        copyIn(qc0Var);
    }

    @Override // p000.p20, p000.xe0
    public void reset(xe0 xe0Var) {
        copyIn((qc0) xe0Var);
    }

    public qc0(byte[] bArr) {
        super(bArr);
        this.f59464X = new int[16];
        this.f59460H1 = wl0.bigEndianToInt(bArr, 16);
        this.f59461H2 = wl0.bigEndianToInt(bArr, S24);
        this.f59462H3 = wl0.bigEndianToInt(bArr, 24);
        this.f59463H4 = wl0.bigEndianToInt(bArr, 28);
        this.xOff = wl0.bigEndianToInt(bArr, 32);
        for (int i = 0; i != this.xOff; i++) {
            this.f59464X[i] = wl0.bigEndianToInt(bArr, (i * 4) + 36);
        }
    }
}
