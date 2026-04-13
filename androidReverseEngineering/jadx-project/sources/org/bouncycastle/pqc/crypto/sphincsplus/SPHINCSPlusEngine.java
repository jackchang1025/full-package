package org.bouncycastle.pqc.crypto.sphincsplus;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.crypto.generators.MGF1BytesGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.MGFParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
abstract class SPHINCSPlusEngine {

    /* renamed from: A */
    final int f1611A;

    /* renamed from: D */
    final int f1612D;

    /* renamed from: H */
    final int f1613H;
    final int H_PRIME;

    /* renamed from: K */
    final int f1614K;

    /* renamed from: N */
    final int f1615N;

    /* renamed from: T */
    final int f1616T;
    final int WOTS_LEN;
    final int WOTS_LEN1;
    final int WOTS_LEN2;
    final int WOTS_LOGW;
    final int WOTS_W;
    final boolean robust;

    public static class Sha256Engine extends SPHINCSPlusEngine {
        private final byte[] digestBuf;
        private final byte[] hmacBuf;
        private final MGF1BytesGenerator mgf1;
        private final Digest msgDigest;
        private final byte[] padding;
        private final Digest treeDigest;
        private final HMac treeHMac;

        public Sha256Engine(boolean z2, int i2, int i3, int i4, int i5, int i6, int i7) {
            super(z2, i2, i3, i4, i5, i6, i7);
            MGF1BytesGenerator mGF1BytesGenerator;
            this.padding = new byte[64];
            SHA256Digest sHA256Digest = new SHA256Digest();
            this.treeDigest = sHA256Digest;
            if (i2 == 32) {
                this.msgDigest = new SHA512Digest();
                this.treeHMac = new HMac(new SHA512Digest());
                mGF1BytesGenerator = new MGF1BytesGenerator(new SHA512Digest());
            } else {
                this.msgDigest = new SHA256Digest();
                this.treeHMac = new HMac(new SHA256Digest());
                mGF1BytesGenerator = new MGF1BytesGenerator(new SHA256Digest());
            }
            this.mgf1 = mGF1BytesGenerator;
            this.digestBuf = new byte[sHA256Digest.getDigestSize()];
            this.hmacBuf = new byte[this.treeHMac.getMacSize()];
        }

        private byte[] compressedADRS(ADRS adrs) {
            byte[] bArr = new byte[22];
            System.arraycopy(adrs.value, 3, bArr, 0, 1);
            System.arraycopy(adrs.value, 8, bArr, 1, 8);
            System.arraycopy(adrs.value, 19, bArr, 9, 1);
            System.arraycopy(adrs.value, 20, bArr, 10, 12);
            return bArr;
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        /* renamed from: F */
        public byte[] mo1230F(byte[] bArr, ADRS adrs, byte[] bArr2) {
            byte[] compressedADRS = compressedADRS(adrs);
            if (this.robust) {
                bArr2 = bitmask256(Arrays.concatenate(bArr, compressedADRS), bArr2);
            }
            this.treeDigest.update(bArr, 0, bArr.length);
            this.treeDigest.update(this.padding, 0, 64 - bArr.length);
            this.treeDigest.update(compressedADRS, 0, compressedADRS.length);
            this.treeDigest.update(bArr2, 0, bArr2.length);
            this.treeDigest.doFinal(this.digestBuf, 0);
            return Arrays.copyOfRange(this.digestBuf, 0, this.f1615N);
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        /* renamed from: H */
        public byte[] mo1231H(byte[] bArr, ADRS adrs, byte[] bArr2, byte[] bArr3) {
            byte[] concatenate = Arrays.concatenate(bArr2, bArr3);
            byte[] compressedADRS = compressedADRS(adrs);
            if (this.robust) {
                concatenate = bitmask256(Arrays.concatenate(bArr, compressedADRS), concatenate);
            }
            this.treeDigest.update(bArr, 0, bArr.length);
            this.treeDigest.update(this.padding, 0, 64 - this.f1615N);
            this.treeDigest.update(compressedADRS, 0, compressedADRS.length);
            this.treeDigest.update(concatenate, 0, concatenate.length);
            this.treeDigest.doFinal(this.digestBuf, 0);
            return Arrays.copyOfRange(this.digestBuf, 0, this.f1615N);
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public IndexedDigest H_msg(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
            int i2 = ((this.f1611A * this.f1614K) + 7) / 8;
            int i3 = this.f1613H;
            int i4 = i3 / this.f1612D;
            int i5 = i3 - i4;
            int i6 = (i4 + 7) / 8;
            int i7 = (i5 + 7) / 8;
            byte[] bArr5 = new byte[this.msgDigest.getDigestSize()];
            this.msgDigest.update(bArr, 0, bArr.length);
            this.msgDigest.update(bArr2, 0, bArr2.length);
            this.msgDigest.update(bArr3, 0, bArr3.length);
            this.msgDigest.update(bArr4, 0, bArr4.length);
            this.msgDigest.doFinal(bArr5, 0);
            byte[] bitmask = bitmask(Arrays.concatenate(bArr, bArr2, bArr5), new byte[i2 + i6 + i7]);
            byte[] bArr6 = new byte[8];
            System.arraycopy(bitmask, i2, bArr6, 8 - i7, i7);
            long bigEndianToLong = Pack.bigEndianToLong(bArr6, 0) & ((-1) >>> (64 - i5));
            byte[] bArr7 = new byte[4];
            System.arraycopy(bitmask, i7 + i2, bArr7, 4 - i6, i6);
            return new IndexedDigest(bigEndianToLong, Pack.bigEndianToInt(bArr7, 0) & ((-1) >>> (32 - i4)), Arrays.copyOfRange(bitmask, 0, i2));
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public byte[] PRF(byte[] bArr, ADRS adrs) {
            int length = bArr.length;
            this.treeDigest.update(bArr, 0, bArr.length);
            byte[] compressedADRS = compressedADRS(adrs);
            this.treeDigest.update(compressedADRS, 0, compressedADRS.length);
            this.treeDigest.doFinal(this.digestBuf, 0);
            return Arrays.copyOfRange(this.digestBuf, 0, length);
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public byte[] PRF_msg(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            this.treeHMac.init(new KeyParameter(bArr));
            this.treeHMac.update(bArr2, 0, bArr2.length);
            this.treeHMac.update(bArr3, 0, bArr3.length);
            this.treeHMac.doFinal(this.hmacBuf, 0);
            return Arrays.copyOfRange(this.hmacBuf, 0, this.f1615N);
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public byte[] T_l(byte[] bArr, ADRS adrs, byte[] bArr2) {
            byte[] compressedADRS = compressedADRS(adrs);
            if (this.robust) {
                bArr2 = bitmask256(Arrays.concatenate(bArr, compressedADRS), bArr2);
            }
            this.treeDigest.update(bArr, 0, bArr.length);
            this.treeDigest.update(this.padding, 0, 64 - this.f1615N);
            this.treeDigest.update(compressedADRS, 0, compressedADRS.length);
            this.treeDigest.update(bArr2, 0, bArr2.length);
            this.treeDigest.doFinal(this.digestBuf, 0);
            return Arrays.copyOfRange(this.digestBuf, 0, this.f1615N);
        }

        public byte[] bitmask(byte[] bArr, byte[] bArr2) {
            int length = bArr2.length;
            byte[] bArr3 = new byte[length];
            this.mgf1.init(new MGFParameters(bArr));
            this.mgf1.generateBytes(bArr3, 0, length);
            for (int i2 = 0; i2 < bArr2.length; i2++) {
                bArr3[i2] = (byte) (bArr3[i2] ^ bArr2[i2]);
            }
            return bArr3;
        }

        public byte[] bitmask256(byte[] bArr, byte[] bArr2) {
            int length = bArr2.length;
            byte[] bArr3 = new byte[length];
            MGF1BytesGenerator mGF1BytesGenerator = new MGF1BytesGenerator(new SHA256Digest());
            mGF1BytesGenerator.init(new MGFParameters(bArr));
            mGF1BytesGenerator.generateBytes(bArr3, 0, length);
            for (int i2 = 0; i2 < bArr2.length; i2++) {
                bArr3[i2] = (byte) (bArr3[i2] ^ bArr2[i2]);
            }
            return bArr3;
        }
    }

    public static class Shake256Engine extends SPHINCSPlusEngine {
        private final Xof treeDigest;

        public Shake256Engine(boolean z2, int i2, int i3, int i4, int i5, int i6, int i7) {
            super(z2, i2, i3, i4, i5, i6, i7);
            this.treeDigest = new SHAKEDigest(256);
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        /* renamed from: F */
        public byte[] mo1230F(byte[] bArr, ADRS adrs, byte[] bArr2) {
            if (this.robust) {
                bArr2 = bitmask(bArr, adrs, bArr2);
            }
            int i2 = this.f1615N;
            byte[] bArr3 = new byte[i2];
            this.treeDigest.update(bArr, 0, bArr.length);
            Xof xof = this.treeDigest;
            byte[] bArr4 = adrs.value;
            xof.update(bArr4, 0, bArr4.length);
            this.treeDigest.update(bArr2, 0, bArr2.length);
            this.treeDigest.doFinal(bArr3, 0, i2);
            return bArr3;
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        /* renamed from: H */
        public byte[] mo1231H(byte[] bArr, ADRS adrs, byte[] bArr2, byte[] bArr3) {
            byte[] concatenate = Arrays.concatenate(bArr2, bArr3);
            if (this.robust) {
                concatenate = bitmask(bArr, adrs, concatenate);
            }
            int i2 = this.f1615N;
            byte[] bArr4 = new byte[i2];
            this.treeDigest.update(bArr, 0, bArr.length);
            Xof xof = this.treeDigest;
            byte[] bArr5 = adrs.value;
            xof.update(bArr5, 0, bArr5.length);
            this.treeDigest.update(concatenate, 0, concatenate.length);
            this.treeDigest.doFinal(bArr4, 0, i2);
            return bArr4;
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public IndexedDigest H_msg(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
            int i2 = ((this.f1611A * this.f1614K) + 7) / 8;
            int i3 = this.f1613H;
            int i4 = i3 / this.f1612D;
            int i5 = i3 - i4;
            int i6 = (i4 + 7) / 8;
            int i7 = (i5 + 7) / 8;
            int i8 = i2 + i6 + i7;
            byte[] bArr5 = new byte[i8];
            this.treeDigest.update(bArr, 0, bArr.length);
            this.treeDigest.update(bArr2, 0, bArr2.length);
            this.treeDigest.update(bArr3, 0, bArr3.length);
            this.treeDigest.update(bArr4, 0, bArr4.length);
            this.treeDigest.doFinal(bArr5, 0, i8);
            byte[] bArr6 = new byte[8];
            System.arraycopy(bArr5, i2, bArr6, 8 - i7, i7);
            long bigEndianToLong = Pack.bigEndianToLong(bArr6, 0) & ((-1) >>> (64 - i5));
            byte[] bArr7 = new byte[4];
            System.arraycopy(bArr5, i7 + i2, bArr7, 4 - i6, i6);
            return new IndexedDigest(bigEndianToLong, Pack.bigEndianToInt(bArr7, 0) & ((-1) >>> (32 - i4)), Arrays.copyOfRange(bArr5, 0, i2));
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public byte[] PRF(byte[] bArr, ADRS adrs) {
            this.treeDigest.update(bArr, 0, bArr.length);
            Xof xof = this.treeDigest;
            byte[] bArr2 = adrs.value;
            xof.update(bArr2, 0, bArr2.length);
            int i2 = this.f1615N;
            byte[] bArr3 = new byte[i2];
            this.treeDigest.doFinal(bArr3, 0, i2);
            return bArr3;
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public byte[] PRF_msg(byte[] bArr, byte[] bArr2, byte[] bArr3) {
            this.treeDigest.update(bArr, 0, bArr.length);
            this.treeDigest.update(bArr2, 0, bArr2.length);
            this.treeDigest.update(bArr3, 0, bArr3.length);
            int i2 = this.f1615N;
            byte[] bArr4 = new byte[i2];
            this.treeDigest.doFinal(bArr4, 0, i2);
            return bArr4;
        }

        @Override // org.bouncycastle.pqc.crypto.sphincsplus.SPHINCSPlusEngine
        public byte[] T_l(byte[] bArr, ADRS adrs, byte[] bArr2) {
            if (this.robust) {
                bArr2 = bitmask(bArr, adrs, bArr2);
            }
            int i2 = this.f1615N;
            byte[] bArr3 = new byte[i2];
            this.treeDigest.update(bArr, 0, bArr.length);
            Xof xof = this.treeDigest;
            byte[] bArr4 = adrs.value;
            xof.update(bArr4, 0, bArr4.length);
            this.treeDigest.update(bArr2, 0, bArr2.length);
            this.treeDigest.doFinal(bArr3, 0, i2);
            return bArr3;
        }

        public byte[] bitmask(byte[] bArr, ADRS adrs, byte[] bArr2) {
            int length = bArr2.length;
            byte[] bArr3 = new byte[length];
            this.treeDigest.update(bArr, 0, bArr.length);
            Xof xof = this.treeDigest;
            byte[] bArr4 = adrs.value;
            xof.update(bArr4, 0, bArr4.length);
            this.treeDigest.doFinal(bArr3, 0, length);
            for (int i2 = 0; i2 < bArr2.length; i2++) {
                bArr3[i2] = (byte) (bArr3[i2] ^ bArr2[i2]);
            }
            return bArr3;
        }
    }

    public SPHINCSPlusEngine(boolean z2, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.f1615N = i2;
        if (i3 == 16) {
            this.WOTS_LOGW = 4;
            this.WOTS_LEN1 = (i2 * 8) / 4;
            if (i2 > 8) {
                if (i2 <= 136) {
                    this.WOTS_LEN2 = 3;
                } else {
                    if (i2 > 256) {
                        throw new IllegalArgumentException("cannot precompute SPX_WOTS_LEN2 for n outside {2, .., 256}");
                    }
                    this.WOTS_LEN2 = 4;
                }
                this.WOTS_W = i3;
                this.WOTS_LEN = this.WOTS_LEN1 + this.WOTS_LEN2;
                this.robust = z2;
                this.f1612D = i4;
                this.f1611A = i5;
                this.f1614K = i6;
                this.f1613H = i7;
                this.H_PRIME = i7 / i4;
                this.f1616T = 1 << i5;
            }
            this.WOTS_LEN2 = 2;
            this.WOTS_W = i3;
            this.WOTS_LEN = this.WOTS_LEN1 + this.WOTS_LEN2;
            this.robust = z2;
            this.f1612D = i4;
            this.f1611A = i5;
            this.f1614K = i6;
            this.f1613H = i7;
            this.H_PRIME = i7 / i4;
            this.f1616T = 1 << i5;
        }
        if (i3 != 256) {
            throw new IllegalArgumentException("wots_w assumed 16 or 256");
        }
        this.WOTS_LOGW = 8;
        this.WOTS_LEN1 = (i2 * 8) / 8;
        if (i2 <= 1) {
            this.WOTS_LEN2 = 1;
            this.WOTS_W = i3;
            this.WOTS_LEN = this.WOTS_LEN1 + this.WOTS_LEN2;
            this.robust = z2;
            this.f1612D = i4;
            this.f1611A = i5;
            this.f1614K = i6;
            this.f1613H = i7;
            this.H_PRIME = i7 / i4;
            this.f1616T = 1 << i5;
        }
        if (i2 > 256) {
            throw new IllegalArgumentException("cannot precompute SPX_WOTS_LEN2 for n outside {2, .., 256}");
        }
        this.WOTS_LEN2 = 2;
        this.WOTS_W = i3;
        this.WOTS_LEN = this.WOTS_LEN1 + this.WOTS_LEN2;
        this.robust = z2;
        this.f1612D = i4;
        this.f1611A = i5;
        this.f1614K = i6;
        this.f1613H = i7;
        this.H_PRIME = i7 / i4;
        this.f1616T = 1 << i5;
    }

    public static byte[] xor(byte[] bArr, byte[] bArr2) {
        byte[] clone = Arrays.clone(bArr);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            clone[i2] = (byte) (clone[i2] ^ bArr2[i2]);
        }
        return clone;
    }

    /* renamed from: F */
    public abstract byte[] mo1230F(byte[] bArr, ADRS adrs, byte[] bArr2);

    /* renamed from: H */
    public abstract byte[] mo1231H(byte[] bArr, ADRS adrs, byte[] bArr2, byte[] bArr3);

    public abstract IndexedDigest H_msg(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public abstract byte[] PRF(byte[] bArr, ADRS adrs);

    public abstract byte[] PRF_msg(byte[] bArr, byte[] bArr2, byte[] bArr3);

    public abstract byte[] T_l(byte[] bArr, ADRS adrs, byte[] bArr2);
}
