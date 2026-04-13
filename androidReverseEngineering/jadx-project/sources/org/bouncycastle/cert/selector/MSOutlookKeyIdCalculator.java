package org.bouncycastle.cert.selector;

import android.sun.security.util.DerValue;
import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class MSOutlookKeyIdCalculator {

    public static abstract class GeneralDigest {
        private static final int BYTE_LENGTH = 64;
        private long byteCount;
        private byte[] xBuf;
        private int xBufOff;

        public GeneralDigest() {
            this.xBuf = new byte[4];
            this.xBufOff = 0;
        }

        public void copyIn(GeneralDigest generalDigest) {
            byte[] bArr = generalDigest.xBuf;
            System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
            this.xBufOff = generalDigest.xBufOff;
            this.byteCount = generalDigest.byteCount;
        }

        public void finish() {
            long j2 = this.byteCount << 3;
            byte b = DerValue.TAG_CONTEXT;
            while (true) {
                update(b);
                if (this.xBufOff == 0) {
                    processLength(j2);
                    processBlock();
                    return;
                }
                b = 0;
            }
        }

        public abstract void processBlock();

        public abstract void processLength(long j2);

        public abstract void processWord(byte[] bArr, int i2);

        public void reset() {
            this.byteCount = 0L;
            this.xBufOff = 0;
            int i2 = 0;
            while (true) {
                byte[] bArr = this.xBuf;
                if (i2 >= bArr.length) {
                    return;
                }
                bArr[i2] = 0;
                i2++;
            }
        }

        public void update(byte b) {
            byte[] bArr = this.xBuf;
            int i2 = this.xBufOff;
            int i3 = i2 + 1;
            this.xBufOff = i3;
            bArr[i2] = b;
            if (i3 == bArr.length) {
                processWord(bArr, 0);
                this.xBufOff = 0;
            }
            this.byteCount++;
        }

        public GeneralDigest(GeneralDigest generalDigest) {
            this.xBuf = new byte[generalDigest.xBuf.length];
            copyIn(generalDigest);
        }

        public void update(byte[] bArr, int i2, int i3) {
            while (this.xBufOff != 0 && i3 > 0) {
                update(bArr[i2]);
                i2++;
                i3--;
            }
            while (i3 > this.xBuf.length) {
                processWord(bArr, i2);
                byte[] bArr2 = this.xBuf;
                i2 += bArr2.length;
                i3 -= bArr2.length;
                this.byteCount += bArr2.length;
            }
            while (i3 > 0) {
                update(bArr[i2]);
                i2++;
                i3--;
            }
        }
    }

    public static class SHA1Digest extends GeneralDigest {
        private static final int DIGEST_LENGTH = 20;
        private static final int Y1 = 1518500249;
        private static final int Y2 = 1859775393;
        private static final int Y3 = -1894007588;
        private static final int Y4 = -899497514;
        private int H1;
        private int H2;
        private int H3;
        private int H4;
        private int H5;

        /* renamed from: X */
        private int[] f1119X = new int[80];
        private int xOff;

        public SHA1Digest() {
            reset();
        }

        /* renamed from: f */
        private int m1184f(int i2, int i3, int i4) {
            return ((~i2) & i4) | (i3 & i2);
        }

        /* renamed from: g */
        private int m1185g(int i2, int i3, int i4) {
            return (i2 & i4) | (i2 & i3) | (i3 & i4);
        }

        /* renamed from: h */
        private int m1186h(int i2, int i3, int i4) {
            return (i2 ^ i3) ^ i4;
        }

        public int doFinal(byte[] bArr, int i2) {
            finish();
            Pack.intToBigEndian(this.H1, bArr, i2);
            Pack.intToBigEndian(this.H2, bArr, i2 + 4);
            Pack.intToBigEndian(this.H3, bArr, i2 + 8);
            Pack.intToBigEndian(this.H4, bArr, i2 + 12);
            Pack.intToBigEndian(this.H5, bArr, i2 + 16);
            reset();
            return 20;
        }

        public String getAlgorithmName() {
            return McElieceCCA2KeyGenParameterSpec.SHA1;
        }

        public int getDigestSize() {
            return 20;
        }

        @Override // org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        public void processBlock() {
            for (int i2 = 16; i2 < 80; i2++) {
                int[] iArr = this.f1119X;
                int i3 = ((iArr[i2 - 3] ^ iArr[i2 - 8]) ^ iArr[i2 - 14]) ^ iArr[i2 - 16];
                iArr[i2] = (i3 >>> 31) | (i3 << 1);
            }
            int i4 = this.H1;
            int i5 = this.H2;
            int i6 = this.H3;
            int i7 = this.H4;
            int i8 = this.H5;
            int i9 = 0;
            int i10 = 0;
            while (i9 < 4) {
                int i11 = i10 + 1;
                int m5a = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1184f(i5, i6, i7), this.f1119X[i10], Y1, i8);
                int i12 = (i5 >>> 2) | (i5 << 30);
                int i13 = i11 + 1;
                int m5a2 = AbstractC0000a.m5a(((m5a << 5) | (m5a >>> 27)) + m1184f(i4, i12, i6), this.f1119X[i11], Y1, i7);
                int i14 = (i4 >>> 2) | (i4 << 30);
                int i15 = i13 + 1;
                int m5a3 = AbstractC0000a.m5a(((m5a2 << 5) | (m5a2 >>> 27)) + m1184f(m5a, i14, i12), this.f1119X[i13], Y1, i6);
                i8 = (m5a >>> 2) | (m5a << 30);
                int i16 = i15 + 1;
                i5 = AbstractC0000a.m5a(((m5a3 << 5) | (m5a3 >>> 27)) + m1184f(m5a2, i8, i14), this.f1119X[i15], Y1, i12);
                i7 = (m5a2 >>> 2) | (m5a2 << 30);
                i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1184f(m5a3, i7, i8), this.f1119X[i16], Y1, i14);
                i6 = (m5a3 >>> 2) | (m5a3 << 30);
                i9++;
                i10 = i16 + 1;
            }
            int i17 = 0;
            while (i17 < 4) {
                int i18 = i10 + 1;
                int m5a4 = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1186h(i5, i6, i7), this.f1119X[i10], Y2, i8);
                int i19 = (i5 >>> 2) | (i5 << 30);
                int i20 = i18 + 1;
                int m5a5 = AbstractC0000a.m5a(((m5a4 << 5) | (m5a4 >>> 27)) + m1186h(i4, i19, i6), this.f1119X[i18], Y2, i7);
                int i21 = (i4 >>> 2) | (i4 << 30);
                int i22 = i20 + 1;
                int m5a6 = AbstractC0000a.m5a(((m5a5 << 5) | (m5a5 >>> 27)) + m1186h(m5a4, i21, i19), this.f1119X[i20], Y2, i6);
                i8 = (m5a4 >>> 2) | (m5a4 << 30);
                int i23 = i22 + 1;
                i5 = AbstractC0000a.m5a(((m5a6 << 5) | (m5a6 >>> 27)) + m1186h(m5a5, i8, i21), this.f1119X[i22], Y2, i19);
                i7 = (m5a5 >>> 2) | (m5a5 << 30);
                i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1186h(m5a6, i7, i8), this.f1119X[i23], Y2, i21);
                i6 = (m5a6 >>> 2) | (m5a6 << 30);
                i17++;
                i10 = i23 + 1;
            }
            int i24 = 0;
            while (i24 < 4) {
                int i25 = i10 + 1;
                int m5a7 = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1185g(i5, i6, i7), this.f1119X[i10], Y3, i8);
                int i26 = (i5 >>> 2) | (i5 << 30);
                int i27 = i25 + 1;
                int m5a8 = AbstractC0000a.m5a(((m5a7 << 5) | (m5a7 >>> 27)) + m1185g(i4, i26, i6), this.f1119X[i25], Y3, i7);
                int i28 = (i4 >>> 2) | (i4 << 30);
                int i29 = i27 + 1;
                int m5a9 = AbstractC0000a.m5a(((m5a8 << 5) | (m5a8 >>> 27)) + m1185g(m5a7, i28, i26), this.f1119X[i27], Y3, i6);
                i8 = (m5a7 >>> 2) | (m5a7 << 30);
                int i30 = i29 + 1;
                i5 = AbstractC0000a.m5a(((m5a9 << 5) | (m5a9 >>> 27)) + m1185g(m5a8, i8, i28), this.f1119X[i29], Y3, i26);
                i7 = (m5a8 >>> 2) | (m5a8 << 30);
                i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1185g(m5a9, i7, i8), this.f1119X[i30], Y3, i28);
                i6 = (m5a9 >>> 2) | (m5a9 << 30);
                i24++;
                i10 = i30 + 1;
            }
            int i31 = 0;
            while (i31 <= 3) {
                int i32 = i10 + 1;
                int m5a10 = AbstractC0000a.m5a(((i4 << 5) | (i4 >>> 27)) + m1186h(i5, i6, i7), this.f1119X[i10], Y4, i8);
                int i33 = (i5 >>> 2) | (i5 << 30);
                int i34 = i32 + 1;
                int m5a11 = AbstractC0000a.m5a(((m5a10 << 5) | (m5a10 >>> 27)) + m1186h(i4, i33, i6), this.f1119X[i32], Y4, i7);
                int i35 = (i4 >>> 2) | (i4 << 30);
                int i36 = i34 + 1;
                int m5a12 = AbstractC0000a.m5a(((m5a11 << 5) | (m5a11 >>> 27)) + m1186h(m5a10, i35, i33), this.f1119X[i34], Y4, i6);
                i8 = (m5a10 >>> 2) | (m5a10 << 30);
                int i37 = i36 + 1;
                i5 = AbstractC0000a.m5a(((m5a12 << 5) | (m5a12 >>> 27)) + m1186h(m5a11, i8, i35), this.f1119X[i36], Y4, i33);
                i7 = (m5a11 >>> 2) | (m5a11 << 30);
                i4 = AbstractC0000a.m5a(((i5 << 5) | (i5 >>> 27)) + m1186h(m5a12, i7, i8), this.f1119X[i37], Y4, i35);
                i6 = (m5a12 >>> 2) | (m5a12 << 30);
                i31++;
                i10 = i37 + 1;
            }
            this.H1 += i4;
            this.H2 += i5;
            this.H3 += i6;
            this.H4 += i7;
            this.H5 += i8;
            this.xOff = 0;
            for (int i38 = 0; i38 < 16; i38++) {
                this.f1119X[i38] = 0;
            }
        }

        @Override // org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        public void processLength(long j2) {
            if (this.xOff > 14) {
                processBlock();
            }
            int[] iArr = this.f1119X;
            iArr[14] = (int) (j2 >>> 32);
            iArr[15] = (int) (j2 & (-1));
        }

        @Override // org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        public void processWord(byte[] bArr, int i2) {
            int i3 = bArr[i2] << DerValue.tag_GeneralizedTime;
            int i4 = i2 + 1;
            int i5 = i3 | ((bArr[i4] & 255) << 16);
            int i6 = i4 + 1;
            int i7 = (bArr[i6 + 1] & 255) | i5 | ((bArr[i6] & 255) << 8);
            int[] iArr = this.f1119X;
            int i8 = this.xOff;
            iArr[i8] = i7;
            int i9 = i8 + 1;
            this.xOff = i9;
            if (i9 == 16) {
                processBlock();
            }
        }

        @Override // org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        public void reset() {
            super.reset();
            this.H1 = 1732584193;
            this.H2 = -271733879;
            this.H3 = -1732584194;
            this.H4 = 271733878;
            this.H5 = -1009589776;
            this.xOff = 0;
            int i2 = 0;
            while (true) {
                int[] iArr = this.f1119X;
                if (i2 == iArr.length) {
                    return;
                }
                iArr[i2] = 0;
                i2++;
            }
        }
    }

    public static byte[] calculateKeyId(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        SHA1Digest sHA1Digest = new SHA1Digest();
        byte[] bArr = new byte[sHA1Digest.getDigestSize()];
        try {
            byte[] encoded = subjectPublicKeyInfo.getEncoded(ASN1Encoding.DER);
            sHA1Digest.update(encoded, 0, encoded.length);
            sHA1Digest.doFinal(bArr, 0);
            return bArr;
        } catch (IOException unused) {
            return new byte[0];
        }
    }
}
