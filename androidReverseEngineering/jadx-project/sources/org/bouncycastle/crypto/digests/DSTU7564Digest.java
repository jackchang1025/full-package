package org.bouncycastle.crypto.digests;

import android.sun.security.util.DerValue;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class DSTU7564Digest implements ExtendedDigest, Memoable {
    private static final int NB_1024 = 16;
    private static final int NB_512 = 8;
    private static final int NR_1024 = 14;
    private static final int NR_512 = 10;
    private static final byte[] S0 = {-88, 67, 95, 6, 107, 117, 108, 89, 113, -33, -121, -107, DerValue.tag_UtcTime, -16, -40, 9, 109, -13, 29, -53, -55, 77, 44, -81, 121, -32, -105, -3, 111, 75, 69, 57, 62, -35, -93, 79, -76, -74, -102, 14, 31, -65, 21, -31, 73, -46, -109, -58, -110, 114, -98, 97, -47, 99, -6, -18, -12, 25, -43, -83, 88, -92, -69, -95, -36, -14, -125, 55, 66, -28, 122, 50, -100, -52, -85, 74, -113, 110, 4, 39, 46, -25, -30, 90, -106, DerValue.tag_IA5String, 35, 43, -62, 101, 102, 15, PSSSigner.TRAILER_IMPLICIT, -87, 71, 65, 52, 72, -4, -73, 106, -120, -91, 83, -122, -7, 91, -37, 56, 123, -61, DerValue.tag_BMPString, 34, 51, 36, 40, 54, -57, -78, 59, -114, 119, -70, -11, DerValue.tag_T61String, -97, 8, 85, -101, 76, -2, 96, 92, -38, DerValue.tag_GeneralizedTime, 70, -51, 125, 33, -80, 63, DerValue.tag_GeneralString, -119, -1, -21, -124, 105, 58, -99, -41, -45, 112, 103, DerValue.TAG_APPLICATION, -75, -34, 93, 48, -111, -79, 120, 17, 1, -27, 0, 104, -104, -96, -59, 2, -90, 116, 45, 11, -94, 118, -77, -66, -50, -67, -82, -23, -118, 49, DerValue.tag_UniversalString, -20, -15, -103, -108, -86, -10, 38, 47, -17, -24, -116, 53, 3, -44, Byte.MAX_VALUE, -5, 5, -63, 94, -112, 32, 61, -126, -9, -22, 10, 13, 126, -8, 80, 26, -60, 7, 87, -72, 60, 98, -29, -56, -84, 82, 100, Tnaf.POW_2_WIDTH, -48, -39, DerValue.tag_PrintableString, DerValue.tag_UTF8String, 18, 41, 81, -71, -49, -42, 115, -115, -127, 84, DerValue.TAG_PRIVATE, -19, 78, 68, -89, 42, -123, 37, -26, -54, 124, -117, 86, DerValue.TAG_CONTEXT};
    private static final byte[] S1 = {-50, -69, -21, -110, -22, -53, DerValue.tag_PrintableString, -63, -23, 58, -42, -78, -46, -112, DerValue.tag_UtcTime, -8, 66, 21, 86, -76, 101, DerValue.tag_UniversalString, -120, 67, -59, 92, 54, -70, -11, 87, 103, -115, 49, -10, 100, 88, -98, -12, 34, -86, 117, 15, 2, -79, -33, 109, 115, 77, 124, 38, 46, -9, 8, 93, 68, 62, -97, DerValue.tag_T61String, -56, -82, 84, Tnaf.POW_2_WIDTH, -40, PSSSigner.TRAILER_IMPLICIT, 26, 107, 105, -13, -67, 51, -85, -6, -47, -101, 104, 78, DerValue.tag_IA5String, -107, -111, -18, 76, 99, -114, 91, -52, 60, 25, -95, -127, 73, 123, -39, 111, 55, 96, -54, -25, 43, 72, -3, -106, 69, -4, 65, 18, 13, 121, -27, -119, -116, -29, 32, 48, -36, -73, 108, 74, -75, 63, -105, -44, 98, 45, 6, -92, -91, -125, 95, 42, -38, -55, 0, 126, -94, 85, -65, 17, -43, -100, -49, 14, 10, 61, 81, 125, -109, DerValue.tag_GeneralString, -2, -60, 71, 9, -122, 11, -113, -99, 106, 7, -71, -80, -104, DerValue.tag_GeneralizedTime, 50, 113, 75, -17, 59, 112, -96, -28, DerValue.TAG_APPLICATION, -1, -61, -87, -26, 120, -7, -117, 70, DerValue.TAG_CONTEXT, DerValue.tag_BMPString, 56, -31, -72, -88, -32, DerValue.tag_UTF8String, 35, 118, 29, 37, 36, 5, -15, 110, -108, 40, -102, -124, -24, -93, 79, 119, -45, -123, -30, 82, -14, -126, 80, 122, 47, 116, 83, -77, 97, -81, 57, 53, -34, -51, 31, -103, -84, -83, 114, 44, -35, -48, -121, -66, 94, -90, -20, 4, -58, 3, 52, -5, -37, 89, -74, -62, 1, -16, 90, -19, -89, 102, 33, Byte.MAX_VALUE, -118, 39, -57, DerValue.TAG_PRIVATE, 41, -41};
    private static final byte[] S2 = {-109, -39, -102, -75, -104, 34, 69, -4, -70, 106, -33, 2, -97, -36, 81, 89, 74, DerValue.tag_UtcTime, 43, -62, -108, -12, -69, -93, 98, -28, 113, -44, -51, 112, DerValue.tag_IA5String, -31, 73, 60, DerValue.TAG_PRIVATE, -40, 92, -101, -83, -123, 83, -95, 122, -56, 45, -32, -47, 114, -90, 44, -60, -29, 118, 120, -73, -76, 9, 59, 14, 65, 76, -34, -78, -112, 37, -91, -41, 3, 17, 0, -61, 46, -110, -17, 78, 18, -99, 125, -53, 53, Tnaf.POW_2_WIDTH, -43, 79, -98, 77, -87, 85, -58, -48, 123, DerValue.tag_GeneralizedTime, -105, -45, 54, -26, 72, 86, -127, -113, 119, -52, -100, -71, -30, -84, -72, 47, 21, -92, 124, -38, 56, DerValue.tag_BMPString, 11, 5, -42, DerValue.tag_T61String, 110, 108, 126, 102, -3, -79, -27, 96, -81, 94, 51, -121, -55, -16, 93, 109, 63, -120, -115, -57, -9, 29, -23, -20, -19, DerValue.TAG_CONTEXT, 41, 39, -49, -103, -88, 80, 15, 55, 36, 40, 48, -107, -46, 62, 91, DerValue.TAG_APPLICATION, -125, -77, 105, 87, 31, 7, DerValue.tag_UniversalString, -118, PSSSigner.TRAILER_IMPLICIT, 32, -21, -50, -114, -85, -18, 49, -94, 115, -7, -54, 58, 26, -5, 13, -63, -2, -6, -14, 111, -67, -106, -35, 67, 82, -74, 8, -13, -82, -66, 25, -119, 50, 38, -80, -22, 75, 100, -124, -126, 107, -11, 121, -65, 1, 95, 117, 99, DerValue.tag_GeneralString, 35, 61, 104, 42, 101, -24, -111, -10, -1, DerValue.tag_PrintableString, 88, -15, 71, 10, Byte.MAX_VALUE, -59, -89, -25, 97, 90, 6, 70, 68, 66, 4, -96, -37, 57, -122, 84, -86, -116, 52, 33, -117, -8, DerValue.tag_UTF8String, 116, 103};
    private static final byte[] S3 = {104, -115, -54, 77, 115, 75, 78, 42, -44, 82, 38, -77, 84, DerValue.tag_BMPString, 25, 31, 34, 3, 70, 61, 45, 74, 83, -125, DerValue.tag_PrintableString, -118, -73, -43, 37, 121, -11, -67, 88, 47, 13, 2, -19, 81, -98, 17, -14, 62, 85, 94, -47, DerValue.tag_IA5String, 60, 102, 112, 93, -13, 69, DerValue.TAG_APPLICATION, -52, -24, -108, 86, 8, -50, 26, 58, -46, -31, -33, -75, 56, 110, 14, -27, -12, -7, -122, -23, 79, -42, -123, 35, -49, 50, -103, 49, DerValue.tag_T61String, -82, -18, -56, 72, -45, 48, -95, -110, 65, -79, DerValue.tag_GeneralizedTime, -60, 44, 113, 114, 68, 21, -3, 55, -66, 95, -86, -101, -120, -40, -85, -119, -100, -6, 96, -22, PSSSigner.TRAILER_IMPLICIT, 98, DerValue.tag_UTF8String, 36, -90, -88, -20, 103, 32, -37, 124, 40, -35, -84, 91, 52, 126, Tnaf.POW_2_WIDTH, -15, 123, -113, 99, -96, 5, -102, 67, 119, 33, -65, 39, 9, -61, -97, -74, -41, 41, -62, -21, DerValue.TAG_PRIVATE, -92, -117, -116, 29, -5, -1, -63, -78, -105, 46, -8, 101, -10, 117, 7, 4, 73, 51, -28, -39, -71, -48, 66, -57, 108, -112, 0, -114, 111, 80, 1, -59, -38, 71, 63, -51, 105, -94, -30, 122, -89, -58, -109, 15, 10, 6, -26, 43, -106, -93, DerValue.tag_UniversalString, -81, 106, 18, -124, 57, -25, -80, -126, -9, -2, -99, -121, 92, -127, 53, -34, -76, -91, -4, DerValue.TAG_CONTEXT, -17, -53, -69, 107, 118, -70, 90, 125, 120, 11, -107, -29, -83, 116, -104, 59, 54, 100, 109, -36, -16, 89, -87, 76, DerValue.tag_UtcTime, Byte.MAX_VALUE, -111, -72, -55, 87, DerValue.tag_GeneralString, -32, 97};
    private int blockSize;
    private byte[] buf;
    private int bufOff;
    private int columns;
    private int hashSize;
    private long inputBlocks;
    private int rounds;
    private long[] state;
    private long[] tempState1;
    private long[] tempState2;

    public DSTU7564Digest(int i2) {
        int i3;
        if (i2 != 256 && i2 != 384 && i2 != 512) {
            throw new IllegalArgumentException("Hash size is not recommended. Use 256/384/512 instead");
        }
        this.hashSize = i2 >>> 3;
        if (i2 > 256) {
            this.columns = 16;
            i3 = 14;
        } else {
            this.columns = 8;
            i3 = 10;
        }
        this.rounds = i3;
        int i4 = this.columns;
        int i5 = i4 << 3;
        this.blockSize = i5;
        long[] jArr = new long[i4];
        this.state = jArr;
        jArr[0] = i5;
        this.tempState1 = new long[i4];
        this.tempState2 = new long[i4];
        this.buf = new byte[i5];
    }

    /* renamed from: P */
    private void m1189P(long[] jArr) {
        for (int i2 = 0; i2 < this.rounds; i2++) {
            long j2 = i2;
            for (int i3 = 0; i3 < this.columns; i3++) {
                jArr[i3] = jArr[i3] ^ j2;
                j2 += 16;
            }
            shiftRows(jArr);
            subBytes(jArr);
            mixColumns(jArr);
        }
    }

    /* renamed from: Q */
    private void m1190Q(long[] jArr) {
        for (int i2 = 0; i2 < this.rounds; i2++) {
            long j2 = ((((this.columns - 1) << 4) ^ i2) << 56) | 67818912035696883L;
            for (int i3 = 0; i3 < this.columns; i3++) {
                jArr[i3] = jArr[i3] + j2;
                j2 -= 1152921504606846976L;
            }
            shiftRows(jArr);
            subBytes(jArr);
            mixColumns(jArr);
        }
    }

    private void copyIn(DSTU7564Digest dSTU7564Digest) {
        this.hashSize = dSTU7564Digest.hashSize;
        this.blockSize = dSTU7564Digest.blockSize;
        this.rounds = dSTU7564Digest.rounds;
        int i2 = this.columns;
        if (i2 <= 0 || i2 != dSTU7564Digest.columns) {
            this.columns = dSTU7564Digest.columns;
            this.state = Arrays.clone(dSTU7564Digest.state);
            int i3 = this.columns;
            this.tempState1 = new long[i3];
            this.tempState2 = new long[i3];
            this.buf = Arrays.clone(dSTU7564Digest.buf);
        } else {
            System.arraycopy(dSTU7564Digest.state, 0, this.state, 0, i2);
            System.arraycopy(dSTU7564Digest.buf, 0, this.buf, 0, this.blockSize);
        }
        this.inputBlocks = dSTU7564Digest.inputBlocks;
        this.bufOff = dSTU7564Digest.bufOff;
    }

    private static long mixColumn(long j2) {
        long j3 = ((9187201950435737471L & j2) << 1) ^ (((j2 & (-9187201950435737472L)) >>> 7) * 29);
        long rotate = rotate(8, j2) ^ j2;
        long rotate2 = (rotate ^ rotate(16, rotate)) ^ rotate(48, j2);
        long j4 = (j2 ^ rotate2) ^ j3;
        return ((rotate(32, (((j4 & 4629771061636907072L) >>> 6) * 29) ^ (((((-9187201950435737472L) & j4) >>> 6) * 29) ^ ((4557430888798830399L & j4) << 2))) ^ rotate2) ^ rotate(40, j3)) ^ rotate(48, j3);
    }

    private void mixColumns(long[] jArr) {
        for (int i2 = 0; i2 < this.columns; i2++) {
            jArr[i2] = mixColumn(jArr[i2]);
        }
    }

    private void processBlock(byte[] bArr, int i2) {
        for (int i3 = 0; i3 < this.columns; i3++) {
            long littleEndianToLong = Pack.littleEndianToLong(bArr, i2);
            i2 += 8;
            this.tempState1[i3] = this.state[i3] ^ littleEndianToLong;
            this.tempState2[i3] = littleEndianToLong;
        }
        m1189P(this.tempState1);
        m1190Q(this.tempState2);
        for (int i4 = 0; i4 < this.columns; i4++) {
            long[] jArr = this.state;
            jArr[i4] = jArr[i4] ^ (this.tempState1[i4] ^ this.tempState2[i4]);
        }
    }

    private static long rotate(int i2, long j2) {
        return (j2 << (-i2)) | (j2 >>> i2);
    }

    private void shiftRows(long[] jArr) {
        int i2 = this.columns;
        if (i2 == 8) {
            long j2 = jArr[0];
            long j3 = jArr[1];
            long j4 = jArr[2];
            long j5 = jArr[3];
            long j6 = jArr[4];
            long j7 = jArr[5];
            long j8 = jArr[6];
            long j9 = jArr[7];
            long j10 = (j2 ^ j6) & (-4294967296L);
            long j11 = j2 ^ j10;
            long j12 = j6 ^ j10;
            long j13 = (j3 ^ j7) & 72057594021150720L;
            long j14 = j3 ^ j13;
            long j15 = j7 ^ j13;
            long j16 = (j4 ^ j8) & 281474976645120L;
            long j17 = j4 ^ j16;
            long j18 = j8 ^ j16;
            long j19 = (j5 ^ j9) & 1099511627520L;
            long j20 = j5 ^ j19;
            long j21 = j9 ^ j19;
            long j22 = (j11 ^ j17) & (-281470681808896L);
            long j23 = j11 ^ j22;
            long j24 = j17 ^ j22;
            long j25 = (j14 ^ j20) & 72056494543077120L;
            long j26 = j14 ^ j25;
            long j27 = j20 ^ j25;
            long j28 = (j12 ^ j18) & (-281470681808896L);
            long j29 = j12 ^ j28;
            long j30 = j18 ^ j28;
            long j31 = (j15 ^ j21) & 72056494543077120L;
            long j32 = j15 ^ j31;
            long j33 = j21 ^ j31;
            long j34 = (j23 ^ j26) & (-71777214294589696L);
            long j35 = j23 ^ j34;
            long j36 = j26 ^ j34;
            long j37 = (j24 ^ j27) & (-71777214294589696L);
            long j38 = j24 ^ j37;
            long j39 = j27 ^ j37;
            long j40 = (j29 ^ j32) & (-71777214294589696L);
            long j41 = (j30 ^ j33) & (-71777214294589696L);
            jArr[0] = j35;
            jArr[1] = j36;
            jArr[2] = j38;
            jArr[3] = j39;
            jArr[4] = j29 ^ j40;
            jArr[5] = j32 ^ j40;
            jArr[6] = j30 ^ j41;
            jArr[7] = j33 ^ j41;
            return;
        }
        if (i2 != 16) {
            throw new IllegalStateException("unsupported state size: only 512/1024 are allowed");
        }
        long j42 = jArr[0];
        long j43 = jArr[1];
        long j44 = jArr[2];
        long j45 = jArr[3];
        long j46 = jArr[4];
        long j47 = jArr[5];
        long j48 = jArr[6];
        long j49 = jArr[7];
        long j50 = jArr[8];
        long j51 = jArr[9];
        long j52 = jArr[10];
        long j53 = jArr[11];
        long j54 = jArr[12];
        long j55 = jArr[13];
        long j56 = jArr[14];
        long j57 = jArr[15];
        long j58 = (j42 ^ j50) & (-72057594037927936L);
        long j59 = j42 ^ j58;
        long j60 = j50 ^ j58;
        long j61 = (j43 ^ j51) & (-72057594037927936L);
        long j62 = j43 ^ j61;
        long j63 = j51 ^ j61;
        long j64 = (j44 ^ j52) & (-281474976710656L);
        long j65 = j44 ^ j64;
        long j66 = j52 ^ j64;
        long j67 = (j45 ^ j53) & (-1099511627776L);
        long j68 = j45 ^ j67;
        long j69 = j53 ^ j67;
        long j70 = (j46 ^ j54) & (-4294967296L);
        long j71 = j46 ^ j70;
        long j72 = j54 ^ j70;
        long j73 = (j47 ^ j55) & 72057594021150720L;
        long j74 = j47 ^ j73;
        long j75 = j55 ^ j73;
        long j76 = (j48 ^ j56) & 72057594037862400L;
        long j77 = j48 ^ j76;
        long j78 = j56 ^ j76;
        long j79 = (j49 ^ j57) & 72057594037927680L;
        long j80 = j49 ^ j79;
        long j81 = j57 ^ j79;
        long j82 = (j59 ^ j71) & 72057589742960640L;
        long j83 = j59 ^ j82;
        long j84 = j71 ^ j82;
        long j85 = (j62 ^ j74) & (-16777216);
        long j86 = j62 ^ j85;
        long j87 = j74 ^ j85;
        long j88 = (j65 ^ j77) & (-71776119061282816L);
        long j89 = j65 ^ j88;
        long j90 = j77 ^ j88;
        long j91 = (j68 ^ j80) & (-72056494526300416L);
        long j92 = j68 ^ j91;
        long j93 = j80 ^ j91;
        long j94 = (j60 ^ j72) & 72057589742960640L;
        long j95 = j60 ^ j94;
        long j96 = j72 ^ j94;
        long j97 = (j63 ^ j75) & (-16777216);
        long j98 = j63 ^ j97;
        long j99 = j75 ^ j97;
        long j100 = (j66 ^ j78) & (-71776119061282816L);
        long j101 = j66 ^ j100;
        long j102 = j78 ^ j100;
        long j103 = (j69 ^ j81) & (-72056494526300416L);
        long j104 = j69 ^ j103;
        long j105 = j81 ^ j103;
        long j106 = (j83 ^ j89) & (-281470681808896L);
        long j107 = j83 ^ j106;
        long j108 = j89 ^ j106;
        long j109 = (j86 ^ j92) & 72056494543077120L;
        long j110 = j86 ^ j109;
        long j111 = j92 ^ j109;
        long j112 = (j84 ^ j90) & (-281470681808896L);
        long j113 = j84 ^ j112;
        long j114 = j90 ^ j112;
        long j115 = (j87 ^ j93) & 72056494543077120L;
        long j116 = j87 ^ j115;
        long j117 = j93 ^ j115;
        long j118 = (j95 ^ j101) & (-281470681808896L);
        long j119 = j95 ^ j118;
        long j120 = j101 ^ j118;
        long j121 = (j98 ^ j104) & 72056494543077120L;
        long j122 = j98 ^ j121;
        long j123 = j104 ^ j121;
        long j124 = (j96 ^ j102) & (-281470681808896L);
        long j125 = j96 ^ j124;
        long j126 = j102 ^ j124;
        long j127 = (j99 ^ j105) & 72056494543077120L;
        long j128 = j99 ^ j127;
        long j129 = j105 ^ j127;
        long j130 = (j107 ^ j110) & (-71777214294589696L);
        long j131 = j107 ^ j130;
        long j132 = j110 ^ j130;
        long j133 = (j108 ^ j111) & (-71777214294589696L);
        long j134 = j108 ^ j133;
        long j135 = j111 ^ j133;
        long j136 = (j113 ^ j116) & (-71777214294589696L);
        long j137 = j113 ^ j136;
        long j138 = j116 ^ j136;
        long j139 = (j114 ^ j117) & (-71777214294589696L);
        long j140 = j114 ^ j139;
        long j141 = j117 ^ j139;
        long j142 = (j119 ^ j122) & (-71777214294589696L);
        long j143 = j119 ^ j142;
        long j144 = j122 ^ j142;
        long j145 = (j120 ^ j123) & (-71777214294589696L);
        long j146 = j120 ^ j145;
        long j147 = j123 ^ j145;
        long j148 = (j125 ^ j128) & (-71777214294589696L);
        long j149 = (j126 ^ j129) & (-71777214294589696L);
        jArr[0] = j131;
        jArr[1] = j132;
        jArr[2] = j134;
        jArr[3] = j135;
        jArr[4] = j137;
        jArr[5] = j138;
        jArr[6] = j140;
        jArr[7] = j141;
        jArr[8] = j143;
        jArr[9] = j144;
        jArr[10] = j146;
        jArr[11] = j147;
        jArr[12] = j125 ^ j148;
        jArr[13] = j128 ^ j148;
        jArr[14] = j126 ^ j149;
        jArr[15] = j129 ^ j149;
    }

    private void subBytes(long[] jArr) {
        for (int i2 = 0; i2 < this.columns; i2++) {
            long j2 = jArr[i2];
            int i3 = (int) j2;
            int i4 = (int) (j2 >>> 32);
            byte[] bArr = S0;
            byte b = bArr[i3 & 255];
            byte[] bArr2 = S1;
            byte b2 = bArr2[(i3 >>> 8) & 255];
            byte[] bArr3 = S2;
            byte b3 = bArr3[(i3 >>> 16) & 255];
            int i5 = (S3[i3 >>> 24] << DerValue.tag_GeneralizedTime) | (b & 255) | ((b2 & 255) << 8) | ((b3 & 255) << 16);
            byte b4 = bArr[i4 & 255];
            byte b5 = bArr2[(i4 >>> 8) & 255];
            byte b6 = bArr3[(i4 >>> 16) & 255];
            jArr[i2] = (i5 & BodyPartID.bodyIdMax) | (((r10[i4 >>> 24] << DerValue.tag_GeneralizedTime) | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16))) << 32);
        }
    }

    @Override // org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new DSTU7564Digest(this);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        int i3;
        int i4;
        int i5 = this.bufOff;
        byte[] bArr2 = this.buf;
        int i6 = i5 + 1;
        this.bufOff = i6;
        bArr2[i5] = DerValue.TAG_CONTEXT;
        int i7 = this.blockSize - 12;
        int i8 = 0;
        if (i6 > i7) {
            while (true) {
                int i9 = this.bufOff;
                if (i9 >= this.blockSize) {
                    break;
                }
                byte[] bArr3 = this.buf;
                this.bufOff = i9 + 1;
                bArr3[i9] = 0;
            }
            this.bufOff = 0;
            processBlock(this.buf, 0);
        }
        while (true) {
            i3 = this.bufOff;
            if (i3 >= i7) {
                break;
            }
            byte[] bArr4 = this.buf;
            this.bufOff = i3 + 1;
            bArr4[i3] = 0;
        }
        long j2 = (((this.inputBlocks & BodyPartID.bodyIdMax) * this.blockSize) + i5) << 3;
        Pack.intToLittleEndian((int) j2, this.buf, i3);
        int i10 = this.bufOff + 4;
        this.bufOff = i10;
        Pack.longToLittleEndian((j2 >>> 32) + (((this.inputBlocks >>> 32) * this.blockSize) << 3), this.buf, i10);
        processBlock(this.buf, 0);
        System.arraycopy(this.state, 0, this.tempState1, 0, this.columns);
        m1189P(this.tempState1);
        while (true) {
            i4 = this.columns;
            if (i8 >= i4) {
                break;
            }
            long[] jArr = this.state;
            jArr[i8] = jArr[i8] ^ this.tempState1[i8];
            i8++;
        }
        for (int i11 = i4 - (this.hashSize >>> 3); i11 < this.columns; i11++) {
            Pack.longToLittleEndian(this.state[i11], bArr, i2);
            i2 += 8;
        }
        reset();
        return this.hashSize;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "DSTU7564";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return this.blockSize;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.hashSize;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        Arrays.fill(this.state, 0L);
        this.state[0] = this.blockSize;
        this.inputBlocks = 0L;
        this.bufOff = 0;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        byte[] bArr = this.buf;
        int i2 = this.bufOff;
        int i3 = i2 + 1;
        this.bufOff = i3;
        bArr[i2] = b;
        if (i3 == this.blockSize) {
            processBlock(bArr, 0);
            this.bufOff = 0;
            this.inputBlocks++;
        }
    }

    public DSTU7564Digest(DSTU7564Digest dSTU7564Digest) {
        copyIn(dSTU7564Digest);
    }

    @Override // org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        copyIn((DSTU7564Digest) memoable);
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (this.bufOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i3--;
            i2++;
        }
        if (i3 > 0) {
            while (i3 >= this.blockSize) {
                processBlock(bArr, i2);
                int i4 = this.blockSize;
                i2 += i4;
                i3 -= i4;
                this.inputBlocks++;
            }
            while (i3 > 0) {
                update(bArr[i2]);
                i3--;
                i2++;
            }
        }
    }
}
