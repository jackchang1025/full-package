package org.bouncycastle.crypto.digests;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public final class Kangaroo {
    private static final int DIGESTLEN = 32;

    public static abstract class KangarooBase implements ExtendedDigest, Xof {
        private static final int BLKSIZE = 8192;
        private final byte[] singleByte = new byte[1];
        private boolean squeezing;
        private final int theChainLen;
        private int theCurrNode;
        private final KangarooSponge theLeaf;
        private byte[] thePersonal;
        private int theProcessed;
        private final KangarooSponge theTree;
        private static final byte[] SINGLE = {7};
        private static final byte[] INTERMEDIATE = {11};
        private static final byte[] FINAL = {-1, -1, 6};
        private static final byte[] FIRST = {3, 0, 0, 0, 0, 0, 0, 0};

        public KangarooBase(int i2, int i3, int i4) {
            this.theTree = new KangarooSponge(i2, i3);
            this.theLeaf = new KangarooSponge(i2, i3);
            this.theChainLen = i2 >> 2;
            buildPersonal(null);
        }

        private void buildPersonal(byte[] bArr) {
            int length = bArr == null ? 0 : bArr.length;
            byte[] lengthEncode = lengthEncode(length);
            byte[] copyOf = bArr == null ? new byte[lengthEncode.length + length] : Arrays.copyOf(bArr, lengthEncode.length + length);
            this.thePersonal = copyOf;
            System.arraycopy(lengthEncode, 0, copyOf, length, lengthEncode.length);
        }

        private static byte[] lengthEncode(long j2) {
            byte b;
            if (j2 != 0) {
                long j3 = j2;
                b = 1;
                while (true) {
                    j3 >>= 8;
                    if (j3 == 0) {
                        break;
                    }
                    b = (byte) (b + 1);
                }
            } else {
                b = 0;
            }
            byte[] bArr = new byte[b + 1];
            bArr[b] = b;
            for (int i2 = 0; i2 < b; i2++) {
                bArr[i2] = (byte) (j2 >> (((b - i2) - 1) * 8));
            }
            return bArr;
        }

        private void processData(byte[] bArr, int i2, int i3) {
            if (this.squeezing) {
                throw new IllegalStateException("attempt to absorb while squeezing");
            }
            KangarooSponge kangarooSponge = this.theCurrNode == 0 ? this.theTree : this.theLeaf;
            int i4 = 8192 - this.theProcessed;
            if (i4 >= i3) {
                kangarooSponge.absorb(bArr, i2, i3);
                this.theProcessed += i3;
                return;
            }
            if (i4 > 0) {
                kangarooSponge.absorb(bArr, i2, i4);
                this.theProcessed += i4;
            }
            while (i4 < i3) {
                if (this.theProcessed == 8192) {
                    switchLeaf(true);
                }
                int min = Math.min(i3 - i4, 8192);
                this.theLeaf.absorb(bArr, i2 + i4, min);
                this.theProcessed += min;
                i4 += min;
            }
        }

        private void switchFinal() {
            switchLeaf(false);
            byte[] lengthEncode = lengthEncode(this.theCurrNode);
            this.theTree.absorb(lengthEncode, 0, lengthEncode.length);
            KangarooSponge kangarooSponge = this.theTree;
            byte[] bArr = FINAL;
            kangarooSponge.absorb(bArr, 0, bArr.length);
            this.theTree.padAndSwitchToSqueezingPhase();
        }

        private void switchLeaf(boolean z2) {
            if (this.theCurrNode == 0) {
                KangarooSponge kangarooSponge = this.theTree;
                byte[] bArr = FIRST;
                kangarooSponge.absorb(bArr, 0, bArr.length);
            } else {
                KangarooSponge kangarooSponge2 = this.theLeaf;
                byte[] bArr2 = INTERMEDIATE;
                kangarooSponge2.absorb(bArr2, 0, bArr2.length);
                int i2 = this.theChainLen;
                byte[] bArr3 = new byte[i2];
                this.theLeaf.squeeze(bArr3, 0, i2);
                this.theTree.absorb(bArr3, 0, this.theChainLen);
                this.theLeaf.initSponge();
            }
            if (z2) {
                this.theCurrNode++;
            }
            this.theProcessed = 0;
        }

        private void switchSingle() {
            this.theTree.absorb(SINGLE, 0, 1);
            this.theTree.padAndSwitchToSqueezingPhase();
        }

        private void switchToSqueezing() {
            byte[] bArr = this.thePersonal;
            processData(bArr, 0, bArr.length);
            if (this.theCurrNode == 0) {
                switchSingle();
            } else {
                switchFinal();
            }
        }

        @Override // org.bouncycastle.crypto.Digest
        public int doFinal(byte[] bArr, int i2) {
            return doFinal(bArr, i2, getDigestSize());
        }

        @Override // org.bouncycastle.crypto.Xof
        public int doOutput(byte[] bArr, int i2, int i3) {
            if (!this.squeezing) {
                switchToSqueezing();
            }
            if (i3 < 0) {
                throw new IllegalArgumentException("Invalid output length");
            }
            this.theTree.squeeze(bArr, i2, i3);
            return i3;
        }

        @Override // org.bouncycastle.crypto.ExtendedDigest
        public int getByteLength() {
            return this.theTree.theRateBytes;
        }

        @Override // org.bouncycastle.crypto.Digest
        public int getDigestSize() {
            return this.theChainLen >> 1;
        }

        public void init(KangarooParameters kangarooParameters) {
            buildPersonal(kangarooParameters.getPersonalisation());
            reset();
        }

        @Override // org.bouncycastle.crypto.Digest
        public void reset() {
            this.theTree.initSponge();
            this.theLeaf.initSponge();
            this.theCurrNode = 0;
            this.theProcessed = 0;
            this.squeezing = false;
        }

        @Override // org.bouncycastle.crypto.Digest
        public void update(byte b) {
            byte[] bArr = this.singleByte;
            bArr[0] = b;
            update(bArr, 0, 1);
        }

        @Override // org.bouncycastle.crypto.Xof
        public int doFinal(byte[] bArr, int i2, int i3) {
            if (this.squeezing) {
                throw new IllegalStateException("Already outputting");
            }
            int doOutput = doOutput(bArr, i2, i3);
            reset();
            return doOutput;
        }

        @Override // org.bouncycastle.crypto.Digest
        public void update(byte[] bArr, int i2, int i3) {
            processData(bArr, i2, i3);
        }
    }

    public static class KangarooParameters implements CipherParameters {
        private byte[] thePersonal;

        public static class Builder {
            private byte[] thePersonal;

            public KangarooParameters build() {
                KangarooParameters kangarooParameters = new KangarooParameters();
                byte[] bArr = this.thePersonal;
                if (bArr != null) {
                    kangarooParameters.thePersonal = bArr;
                }
                return kangarooParameters;
            }

            public Builder setPersonalisation(byte[] bArr) {
                this.thePersonal = Arrays.clone(bArr);
                return this;
            }
        }

        public byte[] getPersonalisation() {
            return Arrays.clone(this.thePersonal);
        }
    }

    public static class KangarooSponge {
        private static long[] KeccakRoundConstants = {1, 32898, -9223372036854742902L, -9223372034707259392L, 32907, 2147483649L, -9223372034707259263L, -9223372036854743031L, 138, 136, 2147516425L, 2147483658L, 2147516555L, -9223372036854775669L, -9223372036854742903L, -9223372036854743037L, -9223372036854743038L, -9223372036854775680L, 32778, -9223372034707292150L, -9223372034707259263L, -9223372036854742912L, 2147483649L, -9223372034707259384L};
        private int bytesInQueue;
        private boolean squeezing;
        private final byte[] theQueue;
        private final int theRateBytes;
        private final int theRounds;
        private final long[] theState = new long[25];

        public KangarooSponge(int i2, int i3) {
            int i4 = (1600 - (i2 << 1)) >> 3;
            this.theRateBytes = i4;
            this.theRounds = i3;
            this.theQueue = new byte[i4];
            initSponge();
        }

        private void KangarooAbsorb(byte[] bArr, int i2) {
            int i3 = this.theRateBytes >> 3;
            for (int i4 = 0; i4 < i3; i4++) {
                long[] jArr = this.theState;
                jArr[i4] = jArr[i4] ^ Pack.littleEndianToLong(bArr, i2);
                i2 += 8;
            }
            KangarooPermutation();
        }

        private void KangarooExtract() {
            Pack.longToLittleEndian(this.theState, 0, this.theRateBytes >> 3, this.theQueue, 0);
        }

        private void KangarooPermutation() {
            KangarooSponge kangarooSponge = this;
            long[] jArr = kangarooSponge.theState;
            long j2 = jArr[0];
            char c = 1;
            long j3 = jArr[1];
            long j4 = jArr[2];
            long j5 = jArr[3];
            long j6 = jArr[4];
            long j7 = jArr[5];
            long j8 = jArr[6];
            long j9 = jArr[7];
            long j10 = jArr[8];
            long j11 = jArr[9];
            long j12 = jArr[10];
            long j13 = jArr[11];
            long j14 = jArr[12];
            long j15 = jArr[13];
            long j16 = jArr[14];
            long j17 = jArr[15];
            long j18 = jArr[16];
            long j19 = jArr[17];
            long j20 = jArr[18];
            long j21 = jArr[19];
            long j22 = jArr[20];
            long j23 = jArr[21];
            long j24 = jArr[22];
            long j25 = jArr[23];
            long j26 = jArr[24];
            int length = KeccakRoundConstants.length - kangarooSponge.theRounds;
            int i2 = 0;
            while (i2 < kangarooSponge.theRounds) {
                long j27 = (((j2 ^ j7) ^ j12) ^ j17) ^ j22;
                long j28 = (((j3 ^ j8) ^ j13) ^ j18) ^ j23;
                long j29 = (((j4 ^ j9) ^ j14) ^ j19) ^ j24;
                long j30 = (((j5 ^ j10) ^ j15) ^ j20) ^ j25;
                long j31 = (((j6 ^ j11) ^ j16) ^ j21) ^ j26;
                long j32 = ((j28 << c) | (j28 >>> (-1))) ^ j31;
                long j33 = ((j29 << c) | (j29 >>> (-1))) ^ j27;
                long j34 = ((j30 << c) | (j30 >>> (-1))) ^ j28;
                long j35 = ((j31 << c) | (j31 >>> (-1))) ^ j29;
                long j36 = ((j27 << c) | (j27 >>> (-1))) ^ j30;
                long j37 = j2 ^ j32;
                long j38 = j7 ^ j32;
                long j39 = j12 ^ j32;
                long j40 = j17 ^ j32;
                long j41 = j22 ^ j32;
                long j42 = j3 ^ j33;
                long j43 = j8 ^ j33;
                long j44 = j13 ^ j33;
                long j45 = j18 ^ j33;
                long j46 = j23 ^ j33;
                long j47 = j4 ^ j34;
                long j48 = j9 ^ j34;
                long j49 = j14 ^ j34;
                long j50 = j19 ^ j34;
                long j51 = j24 ^ j34;
                long j52 = j5 ^ j35;
                long j53 = j10 ^ j35;
                long j54 = j15 ^ j35;
                long j55 = j20 ^ j35;
                long j56 = j25 ^ j35;
                long j57 = j6 ^ j36;
                long j58 = j11 ^ j36;
                long j59 = j16 ^ j36;
                long j60 = j21 ^ j36;
                long j61 = j26 ^ j36;
                long j62 = (j42 << c) | (j42 >>> 63);
                long j63 = (j43 << 44) | (j43 >>> 20);
                long j64 = (j58 << 20) | (j58 >>> 44);
                long j65 = (j51 << 61) | (j51 >>> 3);
                int i3 = length;
                long j66 = (j59 << 39) | (j59 >>> 25);
                long j67 = (j41 << 18) | (j41 >>> 46);
                long j68 = (j47 << 62) | (j47 >>> 2);
                long j69 = (j49 << 43) | (j49 >>> 21);
                long j70 = (j54 << 25) | (j54 >>> 39);
                long j71 = (j60 << 8) | (j60 >>> 56);
                long j72 = (j56 << 56) | (j56 >>> 8);
                long j73 = (j40 << 41) | (j40 >>> 23);
                long j74 = (j57 << 27) | (j57 >>> 37);
                long j75 = (j61 << 14) | (j61 >>> 50);
                long j76 = (j46 << 2) | (j46 >>> 62);
                long j77 = (j53 << 55) | (j53 >>> 9);
                long j78 = (j45 << 45) | (j45 >>> 19);
                long j79 = (j38 << 36) | (j38 >>> 28);
                long j80 = (j52 << 28) | (j52 >>> 36);
                long j81 = (j55 << 21) | (j55 >>> 43);
                long j82 = (j50 << 15) | (j50 >>> 49);
                long j83 = (j44 << 10) | (j44 >>> 54);
                long j84 = (j48 << 6) | (j48 >>> 58);
                long j85 = (j39 << 3) | (j39 >>> 61);
                long j86 = ((~j63) & j69) ^ j37;
                long j87 = ((~j69) & j81) ^ j63;
                j4 = j69 ^ ((~j81) & j75);
                long j88 = ((~j75) & j37) ^ j81;
                long j89 = ((~j37) & j63) ^ j75;
                long j90 = j80 ^ ((~j64) & j85);
                long j91 = ((~j85) & j78) ^ j64;
                long j92 = ((~j78) & j65) ^ j85;
                long j93 = j78 ^ ((~j65) & j80);
                long j94 = ((~j80) & j64) ^ j65;
                j12 = j62 ^ ((~j84) & j70);
                long j95 = ((~j70) & j71) ^ j84;
                long j96 = ((~j71) & j67) ^ j70;
                long j97 = j71 ^ ((~j67) & j62);
                long j98 = ((~j62) & j84) ^ j67;
                long j99 = j74 ^ ((~j79) & j83);
                long j100 = ((~j83) & j82) ^ j79;
                long j101 = j83 ^ ((~j82) & j72);
                long j102 = ((~j72) & j74) ^ j82;
                long j103 = ((~j74) & j79) ^ j72;
                j22 = j68 ^ ((~j77) & j66);
                long j104 = ((~j66) & j73) ^ j77;
                long j105 = ((~j73) & j76) ^ j66;
                long j106 = j73 ^ ((~j76) & j68);
                long j107 = ((~j68) & j77) ^ j76;
                long j108 = j86 ^ KeccakRoundConstants[i3 + i2];
                i2++;
                j7 = j90;
                j14 = j96;
                j13 = j95;
                j15 = j97;
                j24 = j105;
                j23 = j104;
                j10 = j93;
                j18 = j100;
                j26 = j107;
                j2 = j108;
                j19 = j101;
                j3 = j87;
                c = 1;
                j25 = j106;
                j17 = j99;
                jArr = jArr;
                kangarooSponge = this;
                length = i3;
                j5 = j88;
                j6 = j89;
                j20 = j102;
                j16 = j98;
                j9 = j92;
                j8 = j91;
                j11 = j94;
                j21 = j103;
            }
            long[] jArr2 = jArr;
            jArr2[0] = j2;
            jArr2[1] = j3;
            jArr2[2] = j4;
            jArr2[3] = j5;
            jArr2[4] = j6;
            jArr2[5] = j7;
            jArr2[6] = j8;
            jArr2[7] = j9;
            jArr2[8] = j10;
            jArr2[9] = j11;
            jArr2[10] = j12;
            jArr2[11] = j13;
            jArr2[12] = j14;
            jArr2[13] = j15;
            jArr2[14] = j16;
            jArr2[15] = j17;
            jArr2[16] = j18;
            jArr2[17] = j19;
            jArr2[18] = j20;
            jArr2[19] = j21;
            jArr2[20] = j22;
            jArr2[21] = j23;
            jArr2[22] = j24;
            jArr2[23] = j25;
            jArr2[24] = j26;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void absorb(byte[] bArr, int i2, int i3) {
            int i4;
            if (this.squeezing) {
                throw new IllegalStateException("attempt to absorb while squeezing");
            }
            int i5 = 0;
            while (i5 < i3) {
                int i6 = this.bytesInQueue;
                if (i6 != 0 || i5 > i3 - this.theRateBytes) {
                    int min = Math.min(this.theRateBytes - i6, i3 - i5);
                    System.arraycopy(bArr, i2 + i5, this.theQueue, this.bytesInQueue, min);
                    int i7 = this.bytesInQueue + min;
                    this.bytesInQueue = i7;
                    i5 += min;
                    if (i7 == this.theRateBytes) {
                        KangarooAbsorb(this.theQueue, 0);
                        this.bytesInQueue = 0;
                    }
                } else {
                    do {
                        KangarooAbsorb(bArr, i2 + i5);
                        i4 = this.theRateBytes;
                        i5 += i4;
                    } while (i5 <= i3 - i4);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initSponge() {
            Arrays.fill(this.theState, 0L);
            Arrays.fill(this.theQueue, (byte) 0);
            this.bytesInQueue = 0;
            this.squeezing = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void padAndSwitchToSqueezingPhase() {
            int i2 = this.bytesInQueue;
            while (true) {
                int i3 = this.theRateBytes;
                if (i2 >= i3) {
                    byte[] bArr = this.theQueue;
                    int i4 = i3 - 1;
                    bArr[i4] = (byte) (bArr[i4] ^ DerValue.TAG_CONTEXT);
                    KangarooAbsorb(bArr, 0);
                    KangarooExtract();
                    this.bytesInQueue = this.theRateBytes;
                    this.squeezing = true;
                    return;
                }
                this.theQueue[i2] = 0;
                i2++;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void squeeze(byte[] bArr, int i2, int i3) {
            if (!this.squeezing) {
                padAndSwitchToSqueezingPhase();
            }
            int i4 = 0;
            while (i4 < i3) {
                if (this.bytesInQueue == 0) {
                    KangarooPermutation();
                    KangarooExtract();
                    this.bytesInQueue = this.theRateBytes;
                }
                int min = Math.min(this.bytesInQueue, i3 - i4);
                System.arraycopy(this.theQueue, this.theRateBytes - this.bytesInQueue, bArr, i2 + i4, min);
                this.bytesInQueue -= min;
                i4 += min;
            }
        }
    }

    public static class KangarooTwelve extends KangarooBase {
        public KangarooTwelve() {
            this(32);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ int doFinal(byte[] bArr, int i2) {
            return super.doFinal(bArr, i2);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Xof
        public /* bridge */ /* synthetic */ int doOutput(byte[] bArr, int i2, int i3) {
            return super.doOutput(bArr, i2, i3);
        }

        @Override // org.bouncycastle.crypto.Digest
        public String getAlgorithmName() {
            return "KangarooTwelve";
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.ExtendedDigest
        public /* bridge */ /* synthetic */ int getByteLength() {
            return super.getByteLength();
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ int getDigestSize() {
            return super.getDigestSize();
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase
        public /* bridge */ /* synthetic */ void init(KangarooParameters kangarooParameters) {
            super.init(kangarooParameters);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ void update(byte b) {
            super.update(b);
        }

        public KangarooTwelve(int i2) {
            super(128, 12, i2);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Xof
        public /* bridge */ /* synthetic */ int doFinal(byte[] bArr, int i2, int i3) {
            return super.doFinal(bArr, i2, i3);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ void update(byte[] bArr, int i2, int i3) {
            super.update(bArr, i2, i3);
        }
    }

    public static class MarsupilamiFourteen extends KangarooBase {
        public MarsupilamiFourteen() {
            this(32);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ int doFinal(byte[] bArr, int i2) {
            return super.doFinal(bArr, i2);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Xof
        public /* bridge */ /* synthetic */ int doOutput(byte[] bArr, int i2, int i3) {
            return super.doOutput(bArr, i2, i3);
        }

        @Override // org.bouncycastle.crypto.Digest
        public String getAlgorithmName() {
            return "MarsupilamiFourteen";
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.ExtendedDigest
        public /* bridge */ /* synthetic */ int getByteLength() {
            return super.getByteLength();
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ int getDigestSize() {
            return super.getDigestSize();
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase
        public /* bridge */ /* synthetic */ void init(KangarooParameters kangarooParameters) {
            super.init(kangarooParameters);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ void update(byte b) {
            super.update(b);
        }

        public MarsupilamiFourteen(int i2) {
            super(256, 14, i2);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Xof
        public /* bridge */ /* synthetic */ int doFinal(byte[] bArr, int i2, int i3) {
            return super.doFinal(bArr, i2, i3);
        }

        @Override // org.bouncycastle.crypto.digests.Kangaroo.KangarooBase, org.bouncycastle.crypto.Digest
        public /* bridge */ /* synthetic */ void update(byte[] bArr, int i2, int i3) {
            super.update(bArr, i2, i3);
        }
    }
}
