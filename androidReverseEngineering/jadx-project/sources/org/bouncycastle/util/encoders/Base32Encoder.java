package org.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
public class Base32Encoder implements Encoder {
    private static final byte[] DEAULT_ENCODING_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 50, 51, 52, 53, 54, 55};
    private static final byte DEFAULT_PADDING = 61;
    private final byte[] decodingTable;
    private final byte[] encodingTable;
    private final byte padding;

    public Base32Encoder() {
        this.decodingTable = new byte[128];
        this.encodingTable = DEAULT_ENCODING_TABLE;
        this.padding = DEFAULT_PADDING;
        initialiseDecodingTable();
    }

    private int decodeLastBlock(OutputStream outputStream, char c, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        char c9 = this.padding;
        if (c8 != c9) {
            byte[] bArr = this.decodingTable;
            byte b = bArr[c];
            byte b2 = bArr[c2];
            byte b3 = bArr[c3];
            byte b4 = bArr[c4];
            byte b5 = bArr[c5];
            byte b6 = bArr[c6];
            byte b7 = bArr[c7];
            byte b8 = bArr[c8];
            if ((b | b2 | b3 | b4 | b5 | b6 | b7 | b8) < 0) {
                throw new IOException("invalid characters encountered at end of base32 data");
            }
            outputStream.write((b << 3) | (b2 >> 2));
            outputStream.write((b2 << 6) | (b3 << 1) | (b4 >> 4));
            outputStream.write((b4 << 4) | (b5 >> 1));
            outputStream.write((b5 << 7) | (b6 << 2) | (b7 >> 3));
            outputStream.write((b7 << 5) | b8);
            return 5;
        }
        if (c7 != c9) {
            byte[] bArr2 = this.decodingTable;
            byte b9 = bArr2[c];
            byte b10 = bArr2[c2];
            byte b11 = bArr2[c3];
            byte b12 = bArr2[c4];
            byte b13 = bArr2[c5];
            byte b14 = bArr2[c6];
            byte b15 = bArr2[c7];
            if ((b9 | b10 | b11 | b12 | b13 | b14 | b15) < 0) {
                throw new IOException("invalid characters encountered at end of base32 data");
            }
            outputStream.write((b9 << 3) | (b10 >> 2));
            outputStream.write((b10 << 6) | (b11 << 1) | (b12 >> 4));
            outputStream.write((b12 << 4) | (b13 >> 1));
            outputStream.write((b13 << 7) | (b14 << 2) | (b15 >> 3));
            return 4;
        }
        if (c6 != c9) {
            throw new IOException("invalid characters encountered at end of base32 data");
        }
        if (c5 != c9) {
            byte[] bArr3 = this.decodingTable;
            byte b16 = bArr3[c];
            byte b17 = bArr3[c2];
            byte b18 = bArr3[c3];
            byte b19 = bArr3[c4];
            byte b20 = bArr3[c5];
            if ((b16 | b17 | b18 | b19 | b20) < 0) {
                throw new IOException("invalid characters encountered at end of base32 data");
            }
            outputStream.write((b16 << 3) | (b17 >> 2));
            outputStream.write((b17 << 6) | (b18 << 1) | (b19 >> 4));
            outputStream.write((b19 << 4) | (b20 >> 1));
            return 3;
        }
        if (c4 == c9) {
            if (c3 != c9) {
                throw new IOException("invalid characters encountered at end of base32 data");
            }
            byte[] bArr4 = this.decodingTable;
            byte b21 = bArr4[c];
            byte b22 = bArr4[c2];
            if ((b21 | b22) < 0) {
                throw new IOException("invalid characters encountered at end of base32 data");
            }
            outputStream.write((b21 << 3) | (b22 >> 2));
            return 1;
        }
        byte[] bArr5 = this.decodingTable;
        byte b23 = bArr5[c];
        byte b24 = bArr5[c2];
        byte b25 = bArr5[c3];
        byte b26 = bArr5[c4];
        if ((b23 | b24 | b25 | b26) < 0) {
            throw new IOException("invalid characters encountered at end of base32 data");
        }
        outputStream.write((b23 << 3) | (b24 >> 2));
        outputStream.write((b24 << 6) | (b25 << 1) | (b26 >> 4));
        return 2;
    }

    private void encodeBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int i4 = i2 + 1;
        byte b = bArr[i2];
        int i5 = i4 + 1;
        int i6 = bArr[i4] & 255;
        int i7 = i5 + 1;
        int i8 = bArr[i5] & 255;
        int i9 = i7 + 1;
        int i10 = bArr[i7] & 255;
        int i11 = bArr[i9] & 255;
        int i12 = i3 + 1;
        byte[] bArr3 = this.encodingTable;
        bArr2[i3] = bArr3[(b >>> 3) & 31];
        int i13 = i12 + 1;
        bArr2[i12] = bArr3[((b << 2) | (i6 >>> 6)) & 31];
        int i14 = i13 + 1;
        bArr2[i13] = bArr3[(i6 >>> 1) & 31];
        int i15 = i14 + 1;
        bArr2[i14] = bArr3[((i6 << 4) | (i8 >>> 4)) & 31];
        int i16 = i15 + 1;
        bArr2[i15] = bArr3[((i8 << 1) | (i10 >>> 7)) & 31];
        int i17 = i16 + 1;
        bArr2[i16] = bArr3[(i10 >>> 2) & 31];
        bArr2[i17] = bArr3[((i10 << 3) | (i11 >>> 5)) & 31];
        bArr2[i17 + 1] = bArr3[i11 & 31];
    }

    private boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    private int nextI(byte[] bArr, int i2, int i3) {
        while (i2 < i3 && ignore((char) bArr[i2])) {
            i2++;
        }
        return i2;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int decode(String str, OutputStream outputStream) {
        byte[] byteArray = Strings.toByteArray(str);
        return decode(byteArray, 0, byteArray.length, outputStream);
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int encode(byte[] bArr, int i2, int i3, OutputStream outputStream) {
        if (i3 < 0) {
            return 0;
        }
        byte[] bArr2 = new byte[72];
        int i4 = i3;
        while (i4 > 0) {
            int min = Math.min(45, i4);
            outputStream.write(bArr2, 0, encode(bArr, i2, min, bArr2, 0));
            i2 += min;
            i4 -= min;
        }
        return ((i3 + 2) / 3) * 4;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int getEncodedLength(int i2) {
        return ((i2 + 4) / 5) * 8;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int getMaxDecodedLength(int i2) {
        return (i2 / 8) * 5;
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

    public Base32Encoder(byte[] bArr, byte b) {
        this.decodingTable = new byte[128];
        if (bArr.length != 32) {
            throw new IllegalArgumentException("encoding table needs to be length 32");
        }
        this.encodingTable = Arrays.clone(bArr);
        this.padding = b;
        initialiseDecodingTable();
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] bArr, int i2, int i3, OutputStream outputStream) {
        byte[] bArr2 = new byte[55];
        int i4 = i2 + i3;
        while (i4 > i2 && ignore((char) bArr[i4 - 1])) {
            i4--;
        }
        if (i4 == 0) {
            return 0;
        }
        int i5 = i4;
        int i6 = 0;
        while (i5 > i2 && i6 != 8) {
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
            int nextI5 = nextI(bArr, i12, i5);
            int i13 = nextI5 + 1;
            byte b5 = this.decodingTable[bArr[nextI5]];
            int nextI6 = nextI(bArr, i13, i5);
            int i14 = nextI6 + 1;
            byte b6 = this.decodingTable[bArr[nextI6]];
            int nextI7 = nextI(bArr, i14, i5);
            int i15 = i4;
            int i16 = nextI7 + 1;
            byte b7 = this.decodingTable[bArr[nextI7]];
            int nextI8 = nextI(bArr, i16, i5);
            int i17 = i5;
            int i18 = nextI8 + 1;
            byte b8 = this.decodingTable[bArr[nextI8]];
            if ((b | b2 | b3 | b4 | b5 | b6 | b7 | b8) < 0) {
                throw new IOException("invalid characters encountered in base32 data");
            }
            int i19 = i7 + 1;
            bArr2[i7] = (byte) ((b << 3) | (b2 >> 2));
            int i20 = i19 + 1;
            bArr2[i19] = (byte) ((b2 << 6) | (b3 << 1) | (b4 >> 4));
            int i21 = i20 + 1;
            bArr2[i20] = (byte) ((b4 << 4) | (b5 >> 1));
            int i22 = i21 + 1;
            bArr2[i21] = (byte) ((b6 << 2) | (b5 << 7) | (b7 >> 3));
            int i23 = i22 + 1;
            bArr2[i22] = (byte) ((b7 << 5) | b8);
            if (i23 == 55) {
                outputStream.write(bArr2);
                i7 = 0;
            } else {
                i7 = i23;
            }
            i8 += 5;
            int nextI9 = nextI(bArr, i18, i17);
            i5 = i17;
            i4 = i15;
            nextI = nextI9;
        }
        int i24 = i4;
        if (i7 > 0) {
            outputStream.write(bArr2, 0, i7);
        }
        int nextI10 = nextI(bArr, nextI, i24);
        int nextI11 = nextI(bArr, nextI10 + 1, i24);
        int nextI12 = nextI(bArr, nextI11 + 1, i24);
        int nextI13 = nextI(bArr, nextI12 + 1, i24);
        int nextI14 = nextI(bArr, nextI13 + 1, i24);
        int nextI15 = nextI(bArr, nextI14 + 1, i24);
        int nextI16 = nextI(bArr, nextI15 + 1, i24);
        return i8 + decodeLastBlock(outputStream, (char) bArr[nextI10], (char) bArr[nextI11], (char) bArr[nextI12], (char) bArr[nextI13], (char) bArr[nextI14], (char) bArr[nextI15], (char) bArr[nextI16], (char) bArr[nextI(bArr, nextI16 + 1, i24)]);
    }

    public int encode(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = (i2 + i3) - 4;
        int i6 = i2;
        int i7 = i4;
        while (i6 < i5) {
            encodeBlock(bArr, i6, bArr2, i7);
            i6 += 5;
            i7 += 8;
        }
        int i8 = i3 - (i6 - i2);
        if (i8 > 0) {
            byte[] bArr3 = new byte[5];
            System.arraycopy(bArr, i6, bArr3, 0, i8);
            encodeBlock(bArr3, 0, bArr2, i7);
            if (i8 == 1) {
                byte b = this.padding;
                bArr2[i7 + 2] = b;
                bArr2[i7 + 3] = b;
                bArr2[i7 + 4] = b;
                bArr2[i7 + 5] = b;
                bArr2[i7 + 6] = b;
                bArr2[i7 + 7] = b;
            } else if (i8 == 2) {
                byte b2 = this.padding;
                bArr2[i7 + 4] = b2;
                bArr2[i7 + 5] = b2;
                bArr2[i7 + 6] = b2;
                bArr2[i7 + 7] = b2;
            } else if (i8 == 3) {
                byte b3 = this.padding;
                bArr2[i7 + 5] = b3;
                bArr2[i7 + 6] = b3;
                bArr2[i7 + 7] = b3;
            } else if (i8 == 4) {
                bArr2[i7 + 7] = this.padding;
            }
            i7 += 8;
        }
        return i7 - i4;
    }
}
