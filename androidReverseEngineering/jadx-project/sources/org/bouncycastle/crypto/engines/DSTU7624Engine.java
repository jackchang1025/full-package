package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class DSTU7624Engine implements BlockCipher {
    private static final int ROUNDS_128 = 10;
    private static final int ROUNDS_256 = 14;
    private static final int ROUNDS_512 = 18;
    private static final byte[] S0 = {-88, 67, 95, 6, 107, 117, 108, 89, 113, -33, -121, -107, DerValue.tag_UtcTime, -16, -40, 9, 109, -13, 29, -53, -55, 77, 44, -81, 121, -32, -105, -3, 111, 75, 69, 57, 62, -35, -93, 79, -76, -74, -102, 14, 31, -65, 21, -31, 73, -46, -109, -58, -110, 114, -98, 97, -47, 99, -6, -18, -12, 25, -43, -83, 88, -92, -69, -95, -36, -14, -125, 55, 66, -28, 122, 50, -100, -52, -85, 74, -113, 110, 4, 39, 46, -25, -30, 90, -106, DerValue.tag_IA5String, 35, 43, -62, 101, 102, 15, PSSSigner.TRAILER_IMPLICIT, -87, 71, 65, 52, 72, -4, -73, 106, -120, -91, 83, -122, -7, 91, -37, 56, 123, -61, DerValue.tag_BMPString, 34, 51, 36, 40, 54, -57, -78, 59, -114, 119, -70, -11, DerValue.tag_T61String, -97, 8, 85, -101, 76, -2, 96, 92, -38, DerValue.tag_GeneralizedTime, 70, -51, 125, 33, -80, 63, DerValue.tag_GeneralString, -119, -1, -21, -124, 105, 58, -99, -41, -45, 112, 103, DerValue.TAG_APPLICATION, -75, -34, 93, 48, -111, -79, 120, 17, 1, -27, 0, 104, -104, -96, -59, 2, -90, 116, 45, 11, -94, 118, -77, -66, -50, -67, -82, -23, -118, 49, DerValue.tag_UniversalString, -20, -15, -103, -108, -86, -10, 38, 47, -17, -24, -116, 53, 3, -44, Byte.MAX_VALUE, -5, 5, -63, 94, -112, 32, 61, -126, -9, -22, 10, 13, 126, -8, 80, 26, -60, 7, 87, -72, 60, 98, -29, -56, -84, 82, 100, Tnaf.POW_2_WIDTH, -48, -39, DerValue.tag_PrintableString, DerValue.tag_UTF8String, 18, 41, 81, -71, -49, -42, 115, -115, -127, 84, DerValue.TAG_PRIVATE, -19, 78, 68, -89, 42, -123, 37, -26, -54, 124, -117, 86, DerValue.TAG_CONTEXT};
    private static final byte[] S1 = {-50, -69, -21, -110, -22, -53, DerValue.tag_PrintableString, -63, -23, 58, -42, -78, -46, -112, DerValue.tag_UtcTime, -8, 66, 21, 86, -76, 101, DerValue.tag_UniversalString, -120, 67, -59, 92, 54, -70, -11, 87, 103, -115, 49, -10, 100, 88, -98, -12, 34, -86, 117, 15, 2, -79, -33, 109, 115, 77, 124, 38, 46, -9, 8, 93, 68, 62, -97, DerValue.tag_T61String, -56, -82, 84, Tnaf.POW_2_WIDTH, -40, PSSSigner.TRAILER_IMPLICIT, 26, 107, 105, -13, -67, 51, -85, -6, -47, -101, 104, 78, DerValue.tag_IA5String, -107, -111, -18, 76, 99, -114, 91, -52, 60, 25, -95, -127, 73, 123, -39, 111, 55, 96, -54, -25, 43, 72, -3, -106, 69, -4, 65, 18, 13, 121, -27, -119, -116, -29, 32, 48, -36, -73, 108, 74, -75, 63, -105, -44, 98, 45, 6, -92, -91, -125, 95, 42, -38, -55, 0, 126, -94, 85, -65, 17, -43, -100, -49, 14, 10, 61, 81, 125, -109, DerValue.tag_GeneralString, -2, -60, 71, 9, -122, 11, -113, -99, 106, 7, -71, -80, -104, DerValue.tag_GeneralizedTime, 50, 113, 75, -17, 59, 112, -96, -28, DerValue.TAG_APPLICATION, -1, -61, -87, -26, 120, -7, -117, 70, DerValue.TAG_CONTEXT, DerValue.tag_BMPString, 56, -31, -72, -88, -32, DerValue.tag_UTF8String, 35, 118, 29, 37, 36, 5, -15, 110, -108, 40, -102, -124, -24, -93, 79, 119, -45, -123, -30, 82, -14, -126, 80, 122, 47, 116, 83, -77, 97, -81, 57, 53, -34, -51, 31, -103, -84, -83, 114, 44, -35, -48, -121, -66, 94, -90, -20, 4, -58, 3, 52, -5, -37, 89, -74, -62, 1, -16, 90, -19, -89, 102, 33, Byte.MAX_VALUE, -118, 39, -57, DerValue.TAG_PRIVATE, 41, -41};
    private static final byte[] S2 = {-109, -39, -102, -75, -104, 34, 69, -4, -70, 106, -33, 2, -97, -36, 81, 89, 74, DerValue.tag_UtcTime, 43, -62, -108, -12, -69, -93, 98, -28, 113, -44, -51, 112, DerValue.tag_IA5String, -31, 73, 60, DerValue.TAG_PRIVATE, -40, 92, -101, -83, -123, 83, -95, 122, -56, 45, -32, -47, 114, -90, 44, -60, -29, 118, 120, -73, -76, 9, 59, 14, 65, 76, -34, -78, -112, 37, -91, -41, 3, 17, 0, -61, 46, -110, -17, 78, 18, -99, 125, -53, 53, Tnaf.POW_2_WIDTH, -43, 79, -98, 77, -87, 85, -58, -48, 123, DerValue.tag_GeneralizedTime, -105, -45, 54, -26, 72, 86, -127, -113, 119, -52, -100, -71, -30, -84, -72, 47, 21, -92, 124, -38, 56, DerValue.tag_BMPString, 11, 5, -42, DerValue.tag_T61String, 110, 108, 126, 102, -3, -79, -27, 96, -81, 94, 51, -121, -55, -16, 93, 109, 63, -120, -115, -57, -9, 29, -23, -20, -19, DerValue.TAG_CONTEXT, 41, 39, -49, -103, -88, 80, 15, 55, 36, 40, 48, -107, -46, 62, 91, DerValue.TAG_APPLICATION, -125, -77, 105, 87, 31, 7, DerValue.tag_UniversalString, -118, PSSSigner.TRAILER_IMPLICIT, 32, -21, -50, -114, -85, -18, 49, -94, 115, -7, -54, 58, 26, -5, 13, -63, -2, -6, -14, 111, -67, -106, -35, 67, 82, -74, 8, -13, -82, -66, 25, -119, 50, 38, -80, -22, 75, 100, -124, -126, 107, -11, 121, -65, 1, 95, 117, 99, DerValue.tag_GeneralString, 35, 61, 104, 42, 101, -24, -111, -10, -1, DerValue.tag_PrintableString, 88, -15, 71, 10, Byte.MAX_VALUE, -59, -89, -25, 97, 90, 6, 70, 68, 66, 4, -96, -37, 57, -122, 84, -86, -116, 52, 33, -117, -8, DerValue.tag_UTF8String, 116, 103};
    private static final byte[] S3 = {104, -115, -54, 77, 115, 75, 78, 42, -44, 82, 38, -77, 84, DerValue.tag_BMPString, 25, 31, 34, 3, 70, 61, 45, 74, 83, -125, DerValue.tag_PrintableString, -118, -73, -43, 37, 121, -11, -67, 88, 47, 13, 2, -19, 81, -98, 17, -14, 62, 85, 94, -47, DerValue.tag_IA5String, 60, 102, 112, 93, -13, 69, DerValue.TAG_APPLICATION, -52, -24, -108, 86, 8, -50, 26, 58, -46, -31, -33, -75, 56, 110, 14, -27, -12, -7, -122, -23, 79, -42, -123, 35, -49, 50, -103, 49, DerValue.tag_T61String, -82, -18, -56, 72, -45, 48, -95, -110, 65, -79, DerValue.tag_GeneralizedTime, -60, 44, 113, 114, 68, 21, -3, 55, -66, 95, -86, -101, -120, -40, -85, -119, -100, -6, 96, -22, PSSSigner.TRAILER_IMPLICIT, 98, DerValue.tag_UTF8String, 36, -90, -88, -20, 103, 32, -37, 124, 40, -35, -84, 91, 52, 126, Tnaf.POW_2_WIDTH, -15, 123, -113, 99, -96, 5, -102, 67, 119, 33, -65, 39, 9, -61, -97, -74, -41, 41, -62, -21, DerValue.TAG_PRIVATE, -92, -117, -116, 29, -5, -1, -63, -78, -105, 46, -8, 101, -10, 117, 7, 4, 73, 51, -28, -39, -71, -48, 66, -57, 108, -112, 0, -114, 111, 80, 1, -59, -38, 71, 63, -51, 105, -94, -30, 122, -89, -58, -109, 15, 10, 6, -26, 43, -106, -93, DerValue.tag_UniversalString, -81, 106, 18, -124, 57, -25, -80, -126, -9, -2, -99, -121, 92, -127, 53, -34, -76, -91, -4, DerValue.TAG_CONTEXT, -17, -53, -69, 107, 118, -70, 90, 125, 120, 11, -107, -29, -83, 116, -104, 59, 54, 100, 109, -36, -16, 89, -87, 76, DerValue.tag_UtcTime, Byte.MAX_VALUE, -111, -72, -55, 87, DerValue.tag_GeneralString, -32, 97};
    private static final byte[] T0 = {-92, -94, -87, -59, 78, -55, 3, -39, 126, 15, -46, -83, -25, -45, 39, 91, -29, -95, -24, -26, 124, 42, 85, DerValue.tag_UTF8String, -122, 57, -41, -115, -72, 18, 111, 40, -51, -118, 112, 86, 114, -7, -65, 79, 115, -23, -9, 87, DerValue.tag_IA5String, -84, 80, DerValue.TAG_PRIVATE, -99, -73, 71, 113, 96, -60, 116, 67, 108, 31, -109, 119, -36, -50, 32, -116, -103, 95, 68, 1, -11, DerValue.tag_BMPString, -121, 94, 97, 44, 75, 29, -127, 21, -12, 35, -42, -22, -31, 103, -15, Byte.MAX_VALUE, -2, -38, 60, 7, 83, 106, -124, -100, -53, 2, -125, 51, -35, 53, -30, 89, 90, -104, -91, -110, 100, 4, 6, Tnaf.POW_2_WIDTH, 77, DerValue.tag_UniversalString, -105, 8, 49, -18, -85, 5, -81, 121, -96, DerValue.tag_GeneralizedTime, 70, 109, -4, -119, -44, -57, -1, -16, -49, 66, -111, -8, 104, 10, 101, -114, -74, -3, -61, -17, 120, 76, -52, -98, 48, 46, PSSSigner.TRAILER_IMPLICIT, 11, 84, 26, -90, -69, 38, DerValue.TAG_CONTEXT, 72, -108, 50, 125, -89, 63, -82, 34, 61, 102, -86, -10, 0, 93, -67, 74, -32, 59, -76, DerValue.tag_UtcTime, -117, -97, 118, -80, 36, -102, 37, 99, -37, -21, 122, 62, 92, -77, -79, 41, -14, -54, 88, 110, -40, -88, 47, 117, -33, DerValue.tag_T61String, -5, DerValue.tag_PrintableString, 73, -120, -78, -20, -28, 52, 45, -106, -58, 58, -19, -107, 14, -27, -123, 107, DerValue.TAG_APPLICATION, 33, -101, 9, 25, 43, 82, -34, 69, -93, -6, 81, -62, -75, -47, -112, -71, -13, 55, -63, 13, -70, 65, 17, 56, 123, -66, -48, -43, 105, 54, -56, 98, DerValue.tag_GeneralString, -126, -113};
    private static final byte[] T1 = {-125, -14, 42, -21, -23, -65, 123, -100, 52, -106, -115, -104, -71, 105, -116, 41, 61, -120, 104, 6, 57, 17, 76, 14, -96, 86, DerValue.TAG_APPLICATION, -110, 21, PSSSigner.TRAILER_IMPLICIT, -77, -36, 111, -8, 38, -70, -66, -67, 49, -5, -61, -2, DerValue.TAG_CONTEXT, 97, -31, 122, 50, -46, 112, 32, -95, 69, -20, -39, 26, 93, -76, -40, 9, -91, 85, -114, 55, 118, -87, 103, Tnaf.POW_2_WIDTH, DerValue.tag_UtcTime, 54, 101, -79, -107, 98, 89, 116, -93, 80, 47, 75, -56, -48, -113, -51, -44, 60, -122, 18, 29, 35, -17, -12, 83, 25, 53, -26, Byte.MAX_VALUE, 94, -42, 121, 81, 34, DerValue.tag_T61String, -9, DerValue.tag_BMPString, 74, 66, -101, 65, 115, 45, -63, 92, -90, -94, -32, 46, -45, 40, -69, -55, -82, 106, -47, 90, 48, -112, -124, -7, -78, 88, -49, 126, -59, -53, -105, -28, DerValue.tag_IA5String, 108, -6, -80, 109, 31, 82, -103, 13, 78, 3, -111, -62, 77, 100, 119, -97, -35, -60, 73, -118, -102, 36, 56, -89, 87, -123, -57, 124, 125, -25, -10, -73, -84, 39, 70, -34, -33, 59, -41, -98, 43, 11, -43, DerValue.tag_PrintableString, 117, -16, 114, -74, -99, DerValue.tag_GeneralString, 1, 63, 68, -27, -121, -3, 7, -15, -85, -108, DerValue.tag_GeneralizedTime, -22, -4, 58, -126, 95, 5, 84, -37, 0, -117, -29, 72, DerValue.tag_UTF8String, -54, 120, -119, 10, -1, 62, 91, -127, -18, 113, -30, -38, 44, -72, -75, -52, 110, -88, 107, -83, 96, -58, 8, 4, 2, -24, -11, 79, -92, -13, DerValue.TAG_PRIVATE, -50, 67, 37, DerValue.tag_UniversalString, 33, 51, 15, -81, 71, -19, 102, 99, -109, -86};
    private static final byte[] T2 = {69, -44, 11, 67, -15, 114, -19, -92, -62, 56, -26, 113, -3, -74, 58, -107, 80, 68, 75, -30, 116, 107, DerValue.tag_BMPString, 17, 90, -58, -76, -40, -91, -118, 112, -93, -88, -6, 5, -39, -105, DerValue.TAG_APPLICATION, -55, -112, -104, -113, -36, 18, 49, 44, 71, 106, -103, -82, -56, Byte.MAX_VALUE, -7, 79, 93, -106, 111, -12, -77, 57, 33, -38, -100, -123, -98, 59, -16, -65, -17, 6, -18, -27, 95, 32, Tnaf.POW_2_WIDTH, -52, 60, 84, 74, 82, -108, 14, DerValue.TAG_PRIVATE, 40, -10, 86, 96, -94, -29, 15, -20, -99, 36, -125, 126, -43, 124, -21, DerValue.tag_GeneralizedTime, -41, -51, -35, 120, -1, -37, -95, 9, -48, 118, -124, 117, -69, 29, 26, 47, -80, -2, -42, 52, 99, 53, -46, 42, 89, 109, 77, 119, -25, -114, 97, -49, -97, -50, 39, -11, DerValue.TAG_CONTEXT, -122, -57, -90, -5, -8, -121, -85, 98, 63, -33, 72, 0, DerValue.tag_T61String, -102, -67, 91, 4, -110, 2, 37, 101, 76, 83, DerValue.tag_UTF8String, -14, 41, -81, DerValue.tag_UtcTime, 108, 65, 48, -23, -109, 85, -9, -84, 104, 38, -60, 125, -54, 122, 62, -96, 55, 3, -63, 54, 105, 102, 8, DerValue.tag_IA5String, -89, PSSSigner.TRAILER_IMPLICIT, -59, -45, 34, -73, DerValue.tag_PrintableString, 70, 50, -24, 87, -120, 43, -127, -78, 78, 100, DerValue.tag_UniversalString, -86, -111, 88, 46, -101, 92, DerValue.tag_GeneralString, 81, 115, 66, 35, 1, 110, -13, 13, -66, 61, 10, 45, 31, 103, 51, 25, 123, 94, -22, -34, -117, -53, -87, -116, -115, -83, 73, -126, -28, -70, -61, 21, -47, -32, -119, -4, -79, -71, -75, 7, 121, -72, -31};
    private static final byte[] T3 = {-78, -74, 35, 17, -89, -120, -59, -90, 57, -113, -60, -24, 115, 34, 67, -61, -126, 39, -51, DerValue.tag_GeneralizedTime, 81, 98, 45, -9, 92, 14, 59, -3, -54, -101, 13, 15, 121, -116, Tnaf.POW_2_WIDTH, 76, 116, DerValue.tag_UniversalString, 10, -114, 124, -108, 7, -57, 94, DerValue.tag_T61String, -95, 33, 87, 80, 78, -87, DerValue.TAG_CONTEXT, -39, -17, 100, 65, -49, 60, -18, 46, DerValue.tag_PrintableString, 41, -70, 52, 90, -82, -118, 97, 51, 18, -71, 85, -88, 21, 5, -10, 3, 6, 73, -75, 37, 9, DerValue.tag_IA5String, DerValue.tag_UTF8String, 42, 56, -4, 32, -12, -27, Byte.MAX_VALUE, -41, 49, 43, 102, 111, -1, 114, -122, -16, -93, 47, 120, 0, PSSSigner.TRAILER_IMPLICIT, -52, -30, -80, -15, 66, -76, 48, 95, 96, 4, -20, -91, -29, -117, -25, 29, -65, -124, 123, -26, -127, -8, -34, -40, -46, DerValue.tag_UtcTime, -50, 75, 71, -42, 105, 108, 25, -103, -102, 1, -77, -123, -79, -7, 89, -62, 55, -23, -56, -96, -19, 79, -119, 104, 109, -43, 38, -111, -121, 88, -67, -55, -104, -36, 117, DerValue.TAG_PRIVATE, 118, -11, 103, 107, 126, -21, 82, -53, -47, 91, -97, 11, -37, DerValue.TAG_APPLICATION, -110, 26, -6, -84, -28, -31, 113, 31, 101, -115, -105, -98, -107, -112, 93, -73, -63, -81, 84, -5, 2, -32, 53, -69, 58, 77, -83, 44, 61, 86, 8, DerValue.tag_GeneralString, 74, -109, 106, -85, -72, 122, -14, 125, -38, 63, -2, 62, -66, -22, -86, 68, -58, -48, 54, 72, 112, -106, 119, 36, 83, -33, -13, -125, 40, 50, 69, DerValue.tag_BMPString, -92, -45, -94, 70, 110, -100, -35, 99, -44, -99};
    private boolean forEncryption;
    private long[] internalState;
    private long[][] roundKeys;
    private int roundsAmount;
    private int wordsInBlock;
    private int wordsInKey;
    private long[] workingKey;

    public DSTU7624Engine(int i2) {
        if (i2 != 128 && i2 != 256 && i2 != 512) {
            throw new IllegalArgumentException("unsupported block length: only 128/256/512 are allowed");
        }
        int i3 = i2 >>> 6;
        this.wordsInBlock = i3;
        this.internalState = new long[i3];
    }

    private void addRoundKey(int i2) {
        long[] jArr = this.roundKeys[i2];
        for (int i3 = 0; i3 < this.wordsInBlock; i3++) {
            long[] jArr2 = this.internalState;
            jArr2[i3] = jArr2[i3] + jArr[i3];
        }
    }

    private void decryptBlock_128(byte[] bArr, int i2, byte[] bArr2, int i3) {
        long littleEndianToLong = Pack.littleEndianToLong(bArr, i2);
        long littleEndianToLong2 = Pack.littleEndianToLong(bArr, i2 + 8);
        long[][] jArr = this.roundKeys;
        int i4 = this.roundsAmount;
        long[] jArr2 = jArr[i4];
        long j2 = littleEndianToLong - jArr2[0];
        long j3 = littleEndianToLong2 - jArr2[1];
        while (true) {
            long mixColumnInv = mixColumnInv(j2);
            long mixColumnInv2 = mixColumnInv(j3);
            int i5 = (int) mixColumnInv;
            int i6 = (int) (mixColumnInv >>> 32);
            int i7 = (int) mixColumnInv2;
            int i8 = (int) (mixColumnInv2 >>> 32);
            byte[] bArr3 = T0;
            byte b = bArr3[i5 & 255];
            byte[] bArr4 = T1;
            byte b2 = bArr4[(i5 >>> 8) & 255];
            byte[] bArr5 = T2;
            byte b3 = bArr5[(i5 >>> 16) & 255];
            byte[] bArr6 = T3;
            int i9 = (bArr6[i5 >>> 24] << DerValue.tag_GeneralizedTime) | ((b3 & 255) << 16) | (b & 255) | ((b2 & 255) << 8);
            byte b4 = bArr3[i8 & 255];
            byte b5 = bArr4[(i8 >>> 8) & 255];
            byte b6 = bArr5[(i8 >>> 16) & 255];
            long j4 = (((bArr6[i8 >>> 24] << DerValue.tag_GeneralizedTime) | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16))) << 32) | (i9 & BodyPartID.bodyIdMax);
            int i10 = (bArr6[i7 >>> 24] << DerValue.tag_GeneralizedTime) | (bArr3[i7 & 255] & 255) | ((bArr4[(i7 >>> 8) & 255] & 255) << 8) | ((bArr5[(i7 >>> 16) & 255] & 255) << 16);
            byte b7 = bArr3[i6 & 255];
            byte b8 = bArr4[(i6 >>> 8) & 255];
            byte b9 = bArr5[(i6 >>> 16) & 255];
            long j5 = (i10 & BodyPartID.bodyIdMax) | (((bArr6[i6 >>> 24] << DerValue.tag_GeneralizedTime) | (((b7 & 255) | ((b8 & 255) << 8)) | ((b9 & 255) << 16))) << 32);
            i4--;
            if (i4 == 0) {
                long[] jArr3 = this.roundKeys[0];
                long j6 = j4 - jArr3[0];
                long j7 = j5 - jArr3[1];
                Pack.longToLittleEndian(j6, bArr2, i3);
                Pack.longToLittleEndian(j7, bArr2, i3 + 8);
                return;
            }
            long[] jArr4 = this.roundKeys[i4];
            long j8 = j4 ^ jArr4[0];
            j3 = j5 ^ jArr4[1];
            j2 = j8;
        }
    }

    private void encryptBlock_128(byte[] bArr, int i2, byte[] bArr2, int i3) {
        long littleEndianToLong = Pack.littleEndianToLong(bArr, i2);
        long littleEndianToLong2 = Pack.littleEndianToLong(bArr, i2 + 8);
        long[] jArr = this.roundKeys[0];
        long j2 = littleEndianToLong + jArr[0];
        long j3 = littleEndianToLong2 + jArr[1];
        int i4 = 0;
        while (true) {
            int i5 = (int) j2;
            int i6 = (int) (j2 >>> 32);
            int i7 = (int) j3;
            int i8 = (int) (j3 >>> 32);
            byte[] bArr3 = S0;
            byte b = bArr3[i5 & 255];
            byte[] bArr4 = S1;
            byte b2 = bArr4[(i5 >>> 8) & 255];
            byte[] bArr5 = S2;
            byte b3 = bArr5[(i5 >>> 16) & 255];
            byte[] bArr6 = S3;
            int i9 = ((b3 & 255) << 16) | (b & 255) | ((b2 & 255) << 8) | (bArr6[i5 >>> 24] << DerValue.tag_GeneralizedTime);
            byte b4 = bArr3[i8 & 255];
            byte b5 = bArr4[(i8 >>> 8) & 255];
            byte b6 = bArr5[(i8 >>> 16) & 255];
            long j4 = (((bArr6[i8 >>> 24] << DerValue.tag_GeneralizedTime) | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16))) << 32) | (i9 & BodyPartID.bodyIdMax);
            int i10 = (bArr6[i7 >>> 24] << DerValue.tag_GeneralizedTime) | (bArr3[i7 & 255] & 255) | ((bArr4[(i7 >>> 8) & 255] & 255) << 8) | ((bArr5[(i7 >>> 16) & 255] & 255) << 16);
            byte b7 = bArr3[i6 & 255];
            byte b8 = bArr4[(i6 >>> 8) & 255];
            byte b9 = bArr5[(i6 >>> 16) & 255];
            long j5 = (i10 & BodyPartID.bodyIdMax) | (((bArr6[i6 >>> 24] << DerValue.tag_GeneralizedTime) | (((b7 & 255) | ((b8 & 255) << 8)) | ((b9 & 255) << 16))) << 32);
            long mixColumn = mixColumn(j4);
            long mixColumn2 = mixColumn(j5);
            i4++;
            int i11 = this.roundsAmount;
            if (i4 == i11) {
                long[] jArr2 = this.roundKeys[i11];
                long j6 = mixColumn + jArr2[0];
                long j7 = mixColumn2 + jArr2[1];
                Pack.longToLittleEndian(j6, bArr2, i3);
                Pack.longToLittleEndian(j7, bArr2, i3 + 8);
                return;
            }
            long[] jArr3 = this.roundKeys[i4];
            long j8 = mixColumn ^ jArr3[0];
            j3 = mixColumn2 ^ jArr3[1];
            j2 = j8;
        }
    }

    private void invShiftRows() {
        int i2 = this.wordsInBlock;
        if (i2 == 2) {
            long[] jArr = this.internalState;
            long j2 = jArr[0];
            long j3 = jArr[1];
            long j4 = (-4294967296L) & (j2 ^ j3);
            jArr[0] = j2 ^ j4;
            jArr[1] = j4 ^ j3;
            return;
        }
        if (i2 == 4) {
            long[] jArr2 = this.internalState;
            long j5 = jArr2[0];
            long j6 = jArr2[1];
            long j7 = jArr2[2];
            long j8 = jArr2[3];
            long j9 = (j5 ^ j6) & (-281470681808896L);
            long j10 = j5 ^ j9;
            long j11 = j6 ^ j9;
            long j12 = (j7 ^ j8) & (-281470681808896L);
            long j13 = j7 ^ j12;
            long j14 = j8 ^ j12;
            long j15 = (j10 ^ j13) & (-4294967296L);
            long j16 = j10 ^ j15;
            long j17 = (j11 ^ j14) & 281474976645120L;
            jArr2[0] = j16;
            jArr2[1] = j11 ^ j17;
            jArr2[2] = j13 ^ j15;
            jArr2[3] = j17 ^ j14;
            return;
        }
        if (i2 != 8) {
            throw new IllegalStateException("unsupported block length: only 128/256/512 are allowed");
        }
        long[] jArr3 = this.internalState;
        long j18 = jArr3[0];
        long j19 = jArr3[1];
        long j20 = jArr3[2];
        long j21 = jArr3[3];
        long j22 = jArr3[4];
        long j23 = jArr3[5];
        long j24 = jArr3[6];
        long j25 = jArr3[7];
        long j26 = (j18 ^ j19) & (-71777214294589696L);
        long j27 = j18 ^ j26;
        long j28 = j19 ^ j26;
        long j29 = (j20 ^ j21) & (-71777214294589696L);
        long j30 = j20 ^ j29;
        long j31 = j21 ^ j29;
        long j32 = (j22 ^ j23) & (-71777214294589696L);
        long j33 = j22 ^ j32;
        long j34 = j23 ^ j32;
        long j35 = (j24 ^ j25) & (-71777214294589696L);
        long j36 = j24 ^ j35;
        long j37 = j25 ^ j35;
        long j38 = (j27 ^ j30) & (-281470681808896L);
        long j39 = j27 ^ j38;
        long j40 = j30 ^ j38;
        long j41 = (j28 ^ j31) & 72056494543077120L;
        long j42 = j28 ^ j41;
        long j43 = j31 ^ j41;
        long j44 = (j33 ^ j36) & (-281470681808896L);
        long j45 = j33 ^ j44;
        long j46 = j36 ^ j44;
        long j47 = (j34 ^ j37) & 72056494543077120L;
        long j48 = j34 ^ j47;
        long j49 = j37 ^ j47;
        long j50 = (j39 ^ j45) & (-4294967296L);
        long j51 = j39 ^ j50;
        long j52 = j45 ^ j50;
        long j53 = (j42 ^ j48) & 72057594021150720L;
        long j54 = j42 ^ j53;
        long j55 = (j40 ^ j46) & 281474976645120L;
        long j56 = j40 ^ j55;
        long j57 = j55 ^ j46;
        long j58 = (j43 ^ j49) & 1099511627520L;
        jArr3[0] = j51;
        jArr3[1] = j54;
        jArr3[2] = j56;
        jArr3[3] = j43 ^ j58;
        jArr3[4] = j52;
        jArr3[5] = j48 ^ j53;
        jArr3[6] = j57;
        jArr3[7] = j49 ^ j58;
    }

    private void invSubBytes() {
        for (int i2 = 0; i2 < this.wordsInBlock; i2++) {
            long[] jArr = this.internalState;
            long j2 = jArr[i2];
            int i3 = (int) j2;
            int i4 = (int) (j2 >>> 32);
            byte[] bArr = T0;
            byte b = bArr[i3 & 255];
            byte[] bArr2 = T1;
            byte b2 = bArr2[(i3 >>> 8) & 255];
            byte[] bArr3 = T2;
            byte b3 = bArr3[(i3 >>> 16) & 255];
            int i5 = (T3[i3 >>> 24] << DerValue.tag_GeneralizedTime) | (b & 255) | ((b2 & 255) << 8) | ((b3 & 255) << 16);
            byte b4 = bArr[i4 & 255];
            byte b5 = bArr2[(i4 >>> 8) & 255];
            byte b6 = bArr3[(i4 >>> 16) & 255];
            jArr[i2] = (i5 & BodyPartID.bodyIdMax) | (((r11[i4 >>> 24] << DerValue.tag_GeneralizedTime) | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16))) << 32);
        }
    }

    private static long mixColumn(long j2) {
        long mulX = mulX(j2);
        long rotate = rotate(8, j2) ^ j2;
        long rotate2 = (rotate ^ rotate(16, rotate)) ^ rotate(48, j2);
        return ((rotate(32, mulX2((j2 ^ rotate2) ^ mulX)) ^ rotate2) ^ rotate(40, mulX)) ^ rotate(48, mulX);
    }

    private static long mixColumnInv(long j2) {
        long rotate = rotate(8, j2) ^ j2;
        long rotate2 = (rotate ^ rotate(32, rotate)) ^ rotate(48, j2);
        long j3 = rotate2 ^ j2;
        long rotate3 = rotate(48, j2);
        long rotate4 = rotate(56, j2);
        long mulX = mulX(j3 ^ rotate4) ^ rotate(56, j3);
        long mulX2 = mulX(rotate(40, mulX(mulX) ^ j2) ^ (rotate(16, j3) ^ j2)) ^ (j3 ^ rotate3);
        return mulX(rotate(40, ((j2 ^ rotate(32, j3)) ^ rotate4) ^ mulX(((rotate3 ^ (rotate(24, j2) ^ j3)) ^ rotate4) ^ mulX(mulX(mulX2) ^ rotate(16, rotate2))))) ^ rotate2;
    }

    private void mixColumns() {
        for (int i2 = 0; i2 < this.wordsInBlock; i2++) {
            long[] jArr = this.internalState;
            jArr[i2] = mixColumn(jArr[i2]);
        }
    }

    private void mixColumnsInv() {
        for (int i2 = 0; i2 < this.wordsInBlock; i2++) {
            long[] jArr = this.internalState;
            jArr[i2] = mixColumnInv(jArr[i2]);
        }
    }

    private static long mulX(long j2) {
        return (((j2 & (-9187201950435737472L)) >>> 7) * 29) ^ ((9187201950435737471L & j2) << 1);
    }

    private static long mulX2(long j2) {
        return (((j2 & 4629771061636907072L) >>> 6) * 29) ^ (((4557430888798830399L & j2) << 2) ^ ((((-9187201950435737472L) & j2) >>> 6) * 29));
    }

    private static long rotate(int i2, long j2) {
        return (j2 << (-i2)) | (j2 >>> i2);
    }

    private void rotateLeft(long[] jArr, long[] jArr2) {
        int i2 = this.wordsInBlock;
        if (i2 == 2) {
            long j2 = jArr[0];
            long j3 = jArr[1];
            jArr2[0] = (j2 >>> 56) | (j3 << 8);
            jArr2[1] = (j2 << 8) | (j3 >>> 56);
            return;
        }
        if (i2 == 4) {
            long j4 = jArr[0];
            long j5 = jArr[1];
            long j6 = jArr[2];
            long j7 = jArr[3];
            jArr2[0] = (j5 >>> 24) | (j6 << 40);
            jArr2[1] = (j6 >>> 24) | (j7 << 40);
            jArr2[2] = (j7 >>> 24) | (j4 << 40);
            jArr2[3] = (j4 >>> 24) | (j5 << 40);
            return;
        }
        if (i2 != 8) {
            throw new IllegalStateException("unsupported block length: only 128/256/512 are allowed");
        }
        long j8 = jArr[0];
        long j9 = jArr[1];
        long j10 = jArr[2];
        long j11 = jArr[3];
        long j12 = jArr[4];
        long j13 = jArr[5];
        long j14 = jArr[6];
        long j15 = jArr[7];
        jArr2[0] = (j10 >>> 24) | (j11 << 40);
        jArr2[1] = (j11 >>> 24) | (j12 << 40);
        jArr2[2] = (j12 >>> 24) | (j13 << 40);
        jArr2[3] = (j13 >>> 24) | (j14 << 40);
        jArr2[4] = (j14 >>> 24) | (j15 << 40);
        jArr2[5] = (j15 >>> 24) | (j8 << 40);
        jArr2[6] = (j8 >>> 24) | (j9 << 40);
        jArr2[7] = (j9 >>> 24) | (j10 << 40);
    }

    private void shiftRows() {
        int i2 = this.wordsInBlock;
        if (i2 == 2) {
            long[] jArr = this.internalState;
            long j2 = jArr[0];
            long j3 = jArr[1];
            long j4 = (-4294967296L) & (j2 ^ j3);
            jArr[0] = j2 ^ j4;
            jArr[1] = j4 ^ j3;
            return;
        }
        if (i2 == 4) {
            long[] jArr2 = this.internalState;
            long j5 = jArr2[0];
            long j6 = jArr2[1];
            long j7 = jArr2[2];
            long j8 = jArr2[3];
            long j9 = (j5 ^ j7) & (-4294967296L);
            long j10 = j5 ^ j9;
            long j11 = j7 ^ j9;
            long j12 = (j6 ^ j8) & 281474976645120L;
            long j13 = j6 ^ j12;
            long j14 = j8 ^ j12;
            long j15 = (j10 ^ j13) & (-281470681808896L);
            long j16 = (j11 ^ j14) & (-281470681808896L);
            jArr2[0] = j10 ^ j15;
            jArr2[1] = j13 ^ j15;
            jArr2[2] = j11 ^ j16;
            jArr2[3] = j14 ^ j16;
            return;
        }
        if (i2 != 8) {
            throw new IllegalStateException("unsupported block length: only 128/256/512 are allowed");
        }
        long[] jArr3 = this.internalState;
        long j17 = jArr3[0];
        long j18 = jArr3[1];
        long j19 = jArr3[2];
        long j20 = jArr3[3];
        long j21 = jArr3[4];
        long j22 = jArr3[5];
        long j23 = jArr3[6];
        long j24 = jArr3[7];
        long j25 = (j17 ^ j21) & (-4294967296L);
        long j26 = j17 ^ j25;
        long j27 = j21 ^ j25;
        long j28 = (j18 ^ j22) & 72057594021150720L;
        long j29 = j18 ^ j28;
        long j30 = j22 ^ j28;
        long j31 = (j19 ^ j23) & 281474976645120L;
        long j32 = j19 ^ j31;
        long j33 = j23 ^ j31;
        long j34 = (j20 ^ j24) & 1099511627520L;
        long j35 = j20 ^ j34;
        long j36 = j24 ^ j34;
        long j37 = (j26 ^ j32) & (-281470681808896L);
        long j38 = j26 ^ j37;
        long j39 = j32 ^ j37;
        long j40 = (j29 ^ j35) & 72056494543077120L;
        long j41 = j29 ^ j40;
        long j42 = j35 ^ j40;
        long j43 = (j27 ^ j33) & (-281470681808896L);
        long j44 = j27 ^ j43;
        long j45 = j33 ^ j43;
        long j46 = (j30 ^ j36) & 72056494543077120L;
        long j47 = j30 ^ j46;
        long j48 = j36 ^ j46;
        long j49 = (j38 ^ j41) & (-71777214294589696L);
        long j50 = j38 ^ j49;
        long j51 = j41 ^ j49;
        long j52 = (j39 ^ j42) & (-71777214294589696L);
        long j53 = j39 ^ j52;
        long j54 = j42 ^ j52;
        long j55 = (j44 ^ j47) & (-71777214294589696L);
        long j56 = j44 ^ j55;
        long j57 = j47 ^ j55;
        long j58 = (j45 ^ j48) & (-71777214294589696L);
        jArr3[0] = j50;
        jArr3[1] = j51;
        jArr3[2] = j53;
        jArr3[3] = j54;
        jArr3[4] = j56;
        jArr3[5] = j57;
        jArr3[6] = j45 ^ j58;
        jArr3[7] = j48 ^ j58;
    }

    private void subBytes() {
        for (int i2 = 0; i2 < this.wordsInBlock; i2++) {
            long[] jArr = this.internalState;
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
            jArr[i2] = (i5 & BodyPartID.bodyIdMax) | (((r11[i4 >>> 24] << DerValue.tag_GeneralizedTime) | (((b4 & 255) | ((b5 & 255) << 8)) | ((b6 & 255) << 16))) << 32);
        }
    }

    private void subRoundKey(int i2) {
        long[] jArr = this.roundKeys[i2];
        for (int i3 = 0; i3 < this.wordsInBlock; i3++) {
            long[] jArr2 = this.internalState;
            jArr2[i3] = jArr2[i3] - jArr[i3];
        }
    }

    private void workingKeyExpandEven(long[] jArr, long[] jArr2) {
        int i2;
        int i3;
        int i4 = this.wordsInKey;
        long[] jArr3 = new long[i4];
        long[] jArr4 = new long[this.wordsInBlock];
        System.arraycopy(jArr, 0, jArr3, 0, i4);
        long j2 = 281479271743489L;
        int i5 = 0;
        while (true) {
            for (int i6 = 0; i6 < this.wordsInBlock; i6++) {
                jArr4[i6] = jArr2[i6] + j2;
            }
            for (int i7 = 0; i7 < this.wordsInBlock; i7++) {
                this.internalState[i7] = jArr3[i7] + jArr4[i7];
            }
            subBytes();
            shiftRows();
            mixColumns();
            for (int i8 = 0; i8 < this.wordsInBlock; i8++) {
                long[] jArr5 = this.internalState;
                jArr5[i8] = jArr5[i8] ^ jArr4[i8];
            }
            subBytes();
            shiftRows();
            mixColumns();
            int i9 = 0;
            while (true) {
                i2 = this.wordsInBlock;
                if (i9 >= i2) {
                    break;
                }
                long[] jArr6 = this.internalState;
                jArr6[i9] = jArr6[i9] + jArr4[i9];
                i9++;
            }
            System.arraycopy(this.internalState, 0, this.roundKeys[i5], 0, i2);
            if (this.roundsAmount == i5) {
                return;
            }
            if (this.wordsInBlock != this.wordsInKey) {
                i5 += 2;
                j2 <<= 1;
                for (int i10 = 0; i10 < this.wordsInBlock; i10++) {
                    jArr4[i10] = jArr2[i10] + j2;
                }
                int i11 = 0;
                while (true) {
                    int i12 = this.wordsInBlock;
                    if (i11 >= i12) {
                        break;
                    }
                    this.internalState[i11] = jArr3[i12 + i11] + jArr4[i11];
                    i11++;
                }
                subBytes();
                shiftRows();
                mixColumns();
                for (int i13 = 0; i13 < this.wordsInBlock; i13++) {
                    long[] jArr7 = this.internalState;
                    jArr7[i13] = jArr7[i13] ^ jArr4[i13];
                }
                subBytes();
                shiftRows();
                mixColumns();
                int i14 = 0;
                while (true) {
                    i3 = this.wordsInBlock;
                    if (i14 >= i3) {
                        break;
                    }
                    long[] jArr8 = this.internalState;
                    jArr8[i14] = jArr8[i14] + jArr4[i14];
                    i14++;
                }
                System.arraycopy(this.internalState, 0, this.roundKeys[i5], 0, i3);
                if (this.roundsAmount == i5) {
                    return;
                }
            }
            i5 += 2;
            j2 <<= 1;
            long j3 = jArr3[0];
            for (int i15 = 1; i15 < i4; i15++) {
                jArr3[i15 - 1] = jArr3[i15];
            }
            jArr3[i4 - 1] = j3;
        }
    }

    private void workingKeyExpandKT(long[] jArr, long[] jArr2) {
        int i2 = this.wordsInBlock;
        long[] jArr3 = new long[i2];
        long[] jArr4 = new long[i2];
        long[] jArr5 = new long[i2];
        this.internalState = jArr5;
        long j2 = jArr5[0];
        int i3 = this.wordsInKey;
        jArr5[0] = j2 + i2 + i3 + 1;
        System.arraycopy(jArr, 0, jArr3, 0, i2);
        if (i2 == i3) {
            System.arraycopy(jArr, 0, jArr4, 0, i2);
        } else {
            int i4 = this.wordsInBlock;
            System.arraycopy(jArr, i4, jArr4, 0, i4);
        }
        int i5 = 0;
        while (true) {
            long[] jArr6 = this.internalState;
            if (i5 >= jArr6.length) {
                break;
            }
            jArr6[i5] = jArr6[i5] + jArr3[i5];
            i5++;
        }
        subBytes();
        shiftRows();
        mixColumns();
        int i6 = 0;
        while (true) {
            long[] jArr7 = this.internalState;
            if (i6 >= jArr7.length) {
                break;
            }
            jArr7[i6] = jArr7[i6] ^ jArr4[i6];
            i6++;
        }
        subBytes();
        shiftRows();
        mixColumns();
        int i7 = 0;
        while (true) {
            long[] jArr8 = this.internalState;
            if (i7 >= jArr8.length) {
                subBytes();
                shiftRows();
                mixColumns();
                System.arraycopy(this.internalState, 0, jArr2, 0, this.wordsInBlock);
                return;
            }
            jArr8[i7] = jArr8[i7] + jArr3[i7];
            i7++;
        }
    }

    private void workingKeyExpandOdd() {
        for (int i2 = 1; i2 < this.roundsAmount; i2 += 2) {
            long[][] jArr = this.roundKeys;
            rotateLeft(jArr[i2 - 1], jArr[i2]);
        }
    }

    private void xorRoundKey(int i2) {
        long[] jArr = this.roundKeys[i2];
        for (int i3 = 0; i3 < this.wordsInBlock; i3++) {
            long[] jArr2 = this.internalState;
            jArr2[i3] = jArr2[i3] ^ jArr[i3];
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "DSTU7624";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.wordsInBlock << 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005b A[LOOP:0: B:21:0x0056->B:23:0x005b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064 A[EDGE_INSN: B:24:0x0064->B:25:0x0064 BREAK  A[LOOP:0: B:21:0x0056->B:23:0x005b], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0084  */
    @Override // org.bouncycastle.crypto.BlockCipher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void init(boolean z2, CipherParameters cipherParameters) {
        int i2;
        int i3;
        long[][] jArr;
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("Invalid parameter passed to DSTU7624Engine init");
        }
        this.forEncryption = z2;
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        int length = key.length << 3;
        int i4 = this.wordsInBlock << 6;
        if (length != 128 && length != 256 && length != 512) {
            throw new IllegalArgumentException("unsupported key length: only 128/256/512 are allowed");
        }
        if (length != i4 && length != i4 * 2) {
            throw new IllegalArgumentException("Unsupported key length");
        }
        if (length == 128) {
            i2 = 10;
        } else {
            if (length != 256) {
                if (length == 512) {
                    i2 = 18;
                }
                this.wordsInKey = length >>> 6;
                this.roundKeys = new long[this.roundsAmount + 1][];
                i3 = 0;
                while (true) {
                    jArr = this.roundKeys;
                    if (i3 < jArr.length) {
                        break;
                    }
                    jArr[i3] = new long[this.wordsInBlock];
                    i3++;
                }
                long[] jArr2 = new long[this.wordsInKey];
                this.workingKey = jArr2;
                if (key.length == (length >>> 3)) {
                    throw new IllegalArgumentException("Invalid key parameter passed to DSTU7624Engine init");
                }
                Pack.littleEndianToLong(key, 0, jArr2);
                long[] jArr3 = new long[this.wordsInBlock];
                workingKeyExpandKT(this.workingKey, jArr3);
                workingKeyExpandEven(this.workingKey, jArr3);
                workingKeyExpandOdd();
                return;
            }
            i2 = 14;
        }
        this.roundsAmount = i2;
        this.wordsInKey = length >>> 6;
        this.roundKeys = new long[this.roundsAmount + 1][];
        i3 = 0;
        while (true) {
            jArr = this.roundKeys;
            if (i3 < jArr.length) {
            }
            jArr[i3] = new long[this.wordsInBlock];
            i3++;
        }
        long[] jArr22 = new long[this.wordsInKey];
        this.workingKey = jArr22;
        if (key.length == (length >>> 3)) {
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4;
        if (this.workingKey == null) {
            throw new IllegalStateException("DSTU7624Engine not initialised");
        }
        if (getBlockSize() + i2 > bArr.length) {
            throw new DataLengthException("Input buffer too short");
        }
        if (getBlockSize() + i3 > bArr2.length) {
            throw new OutputLengthException("Output buffer too short");
        }
        int i5 = 0;
        if (this.forEncryption) {
            if (this.wordsInBlock != 2) {
                Pack.littleEndianToLong(bArr, i2, this.internalState);
                addRoundKey(0);
                while (true) {
                    subBytes();
                    shiftRows();
                    mixColumns();
                    i5++;
                    i4 = this.roundsAmount;
                    if (i5 == i4) {
                        break;
                    }
                    xorRoundKey(i5);
                }
                addRoundKey(i4);
                Pack.longToLittleEndian(this.internalState, bArr2, i3);
            } else {
                encryptBlock_128(bArr, i2, bArr2, i3);
            }
        } else if (this.wordsInBlock != 2) {
            Pack.littleEndianToLong(bArr, i2, this.internalState);
            subRoundKey(this.roundsAmount);
            int i6 = this.roundsAmount;
            while (true) {
                mixColumnsInv();
                invShiftRows();
                invSubBytes();
                i6--;
                if (i6 == 0) {
                    break;
                }
                xorRoundKey(i6);
            }
            subRoundKey(0);
            Pack.longToLittleEndian(this.internalState, bArr2, i3);
        } else {
            decryptBlock_128(bArr, i2, bArr2, i3);
        }
        return getBlockSize();
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        Arrays.fill(this.internalState, 0L);
    }
}
