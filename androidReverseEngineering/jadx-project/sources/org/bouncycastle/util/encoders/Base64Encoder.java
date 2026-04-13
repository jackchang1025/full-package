package org.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class Base64Encoder implements Encoder {
    protected final byte[] encodingTable = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    protected byte padding = 61;
    protected final byte[] decodingTable = new byte[128];

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    private int decodeLastBlock(OutputStream outputStream, char c, char c2, char c3, char c4) {
        char c5 = this.padding;
        if (c3 == c5) {
            if (c4 != c5) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            byte[] bArr = this.decodingTable;
            byte b = bArr[c];
            byte b2 = bArr[c2];
            if ((b | b2) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b << 2) | (b2 >> 4));
            return 1;
        }
        if (c4 == c5) {
            byte[] bArr2 = this.decodingTable;
            byte b3 = bArr2[c];
            byte b4 = bArr2[c2];
            byte b5 = bArr2[c3];
            if ((b3 | b4 | b5) < 0) {
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            outputStream.write((b3 << 2) | (b4 >> 4));
            outputStream.write((b4 << 4) | (b5 >> 2));
            return 2;
        }
        byte[] bArr3 = this.decodingTable;
        byte b6 = bArr3[c];
        byte b7 = bArr3[c2];
        byte b8 = bArr3[c3];
        byte b9 = bArr3[c4];
        if ((b6 | b7 | b8 | b9) < 0) {
            throw new IOException("invalid characters encountered at end of base64 data");
        }
        outputStream.write((b6 << 2) | (b7 >> 4));
        outputStream.write((b7 << 4) | (b8 >> 2));
        outputStream.write((b8 << 6) | b9);
        return 3;
    }

    private boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    private int nextI(String str, int i2, int i3) {
        while (i2 < i3 && ignore(str.charAt(i2))) {
            i2++;
        }
        return i2;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int decode(String str, OutputStream outputStream) {
        byte[] bArr = new byte[54];
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        if (length == 0) {
            return 0;
        }
        int i2 = length;
        int i3 = 0;
        while (i2 > 0 && i3 != 4) {
            if (!ignore(str.charAt(i2 - 1))) {
                i3++;
            }
            i2--;
        }
        int nextI = nextI(str, 0, i2);
        int i4 = 0;
        int i5 = 0;
        while (nextI < i2) {
            int i6 = nextI + 1;
            byte b = this.decodingTable[str.charAt(nextI)];
            int nextI2 = nextI(str, i6, i2);
            int i7 = nextI2 + 1;
            byte b2 = this.decodingTable[str.charAt(nextI2)];
            int nextI3 = nextI(str, i7, i2);
            int i8 = nextI3 + 1;
            byte b3 = this.decodingTable[str.charAt(nextI3)];
            int nextI4 = nextI(str, i8, i2);
            int i9 = nextI4 + 1;
            byte b4 = this.decodingTable[str.charAt(nextI4)];
            if ((b | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            int i10 = i4 + 1;
            bArr[i4] = (byte) ((b << 2) | (b2 >> 4));
            int i11 = i10 + 1;
            bArr[i10] = (byte) ((b2 << 4) | (b3 >> 2));
            i4 = i11 + 1;
            bArr[i11] = (byte) ((b3 << 6) | b4);
            i5 += 3;
            if (i4 == 54) {
                outputStream.write(bArr);
                i4 = 0;
            }
            nextI = nextI(str, i9, i2);
        }
        if (i4 > 0) {
            outputStream.write(bArr, 0, i4);
        }
        int nextI5 = nextI(str, nextI, length);
        int nextI6 = nextI(str, nextI5 + 1, length);
        int nextI7 = nextI(str, nextI6 + 1, length);
        return i5 + decodeLastBlock(outputStream, str.charAt(nextI5), str.charAt(nextI6), str.charAt(nextI7), str.charAt(nextI(str, nextI7 + 1, length)));
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int encode(byte[] bArr, int i2, int i3, OutputStream outputStream) {
        if (i3 < 0) {
            return 0;
        }
        byte[] bArr2 = new byte[72];
        int i4 = i3;
        while (i4 > 0) {
            int min = Math.min(54, i4);
            outputStream.write(bArr2, 0, encode(bArr, i2, min, bArr2, 0));
            i2 += min;
            i4 -= min;
        }
        return ((i3 + 2) / 3) * 4;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int getEncodedLength(int i2) {
        return ((i2 + 2) / 3) * 4;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int getMaxDecodedLength(int i2) {
        return (i2 / 4) * 3;
    }

    public void initialiseDecodingTable() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.decodingTable;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = -1;
            i3++;
        }
        while (true) {
            byte[] bArr2 = this.encodingTable;
            if (i2 >= bArr2.length) {
                return;
            }
            this.decodingTable[bArr2[i2]] = (byte) i2;
            i2++;
        }
    }

    private int nextI(byte[] bArr, int i2, int i3) {
        while (i2 < i3 && ignore((char) bArr[i2])) {
            i2++;
        }
        return i2;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] bArr, int i2, int i3, OutputStream outputStream) {
        byte[] bArr2 = new byte[54];
        int i4 = i2 + i3;
        while (i4 > i2 && ignore((char) bArr[i4 - 1])) {
            i4--;
        }
        if (i4 == 0) {
            return 0;
        }
        int i5 = i4;
        int i6 = 0;
        while (i5 > i2 && i6 != 4) {
            if (!ignore((char) bArr[i5 - 1])) {
                i6++;
            }
            i5--;
        }
        int nextI = nextI(bArr, i2, i5);
        int i7 = 0;
        int i8 = 0;
        while (nextI < i5) {
            int i9 = nextI + 1;
            byte b = this.decodingTable[bArr[nextI]];
            int nextI2 = nextI(bArr, i9, i5);
            int i10 = nextI2 + 1;
            byte b2 = this.decodingTable[bArr[nextI2]];
            int nextI3 = nextI(bArr, i10, i5);
            int i11 = nextI3 + 1;
            byte b3 = this.decodingTable[bArr[nextI3]];
            int nextI4 = nextI(bArr, i11, i5);
            int i12 = nextI4 + 1;
            byte b4 = this.decodingTable[bArr[nextI4]];
            if ((b | b2 | b3 | b4) < 0) {
                throw new IOException("invalid characters encountered in base64 data");
            }
            int i13 = i7 + 1;
            bArr2[i7] = (byte) ((b << 2) | (b2 >> 4));
            int i14 = i13 + 1;
            bArr2[i13] = (byte) ((b2 << 4) | (b3 >> 2));
            i7 = i14 + 1;
            bArr2[i14] = (byte) ((b3 << 6) | b4);
            if (i7 == 54) {
                outputStream.write(bArr2);
                i7 = 0;
            }
            i8 += 3;
            nextI = nextI(bArr, i12, i5);
        }
        if (i7 > 0) {
            outputStream.write(bArr2, 0, i7);
        }
        int nextI5 = nextI(bArr, nextI, i4);
        int nextI6 = nextI(bArr, nextI5 + 1, i4);
        int nextI7 = nextI(bArr, nextI6 + 1, i4);
        return i8 + decodeLastBlock(outputStream, (char) bArr[nextI5], (char) bArr[nextI6], (char) bArr[nextI7], (char) bArr[nextI(bArr, nextI7 + 1, i4)]);
    }

    public int encode(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = (i2 + i3) - 2;
        int i6 = i2;
        int i7 = i4;
        while (i6 < i5) {
            int i8 = i6 + 1;
            byte b = bArr[i6];
            int i9 = i8 + 1;
            int i10 = bArr[i8] & 255;
            int i11 = i9 + 1;
            int i12 = bArr[i9] & 255;
            int i13 = i7 + 1;
            byte[] bArr3 = this.encodingTable;
            bArr2[i7] = bArr3[(b >>> 2) & 63];
            int i14 = i13 + 1;
            bArr2[i13] = bArr3[((b << 4) | (i10 >>> 4)) & 63];
            int i15 = i14 + 1;
            bArr2[i14] = bArr3[((i10 << 2) | (i12 >>> 6)) & 63];
            i7 = i15 + 1;
            bArr2[i15] = bArr3[i12 & 63];
            i6 = i11;
        }
        int i16 = i3 - (i6 - i2);
        if (i16 == 1) {
            int i17 = bArr[i6] & 255;
            int i18 = i7 + 1;
            byte[] bArr4 = this.encodingTable;
            bArr2[i7] = bArr4[(i17 >>> 2) & 63];
            int i19 = i18 + 1;
            bArr2[i18] = bArr4[(i17 << 4) & 63];
            int i20 = i19 + 1;
            byte b2 = this.padding;
            bArr2[i19] = b2;
            i7 = i20 + 1;
            bArr2[i20] = b2;
        } else if (i16 == 2) {
            int i21 = bArr[i6] & 255;
            int i22 = bArr[i6 + 1] & 255;
            int i23 = i7 + 1;
            byte[] bArr5 = this.encodingTable;
            bArr2[i7] = bArr5[(i21 >>> 2) & 63];
            int i24 = i23 + 1;
            bArr2[i23] = bArr5[((i21 << 4) | (i22 >>> 4)) & 63];
            int i25 = i24 + 1;
            bArr2[i24] = bArr5[(i22 << 2) & 63];
            i7 = i25 + 1;
            bArr2[i25] = this.padding;
        }
        return i7 - i4;
    }
}
