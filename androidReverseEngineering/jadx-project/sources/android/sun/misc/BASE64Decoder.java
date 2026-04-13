package android.sun.misc;

import java.io.OutputStream;
import java.io.PushbackInputStream;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;

/* loaded from: classes.dex */
public class BASE64Decoder extends CharacterDecoder {
    private static final char[] pem_array = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', Matrix.MATRIX_TYPE_RANDOM_LT, 'M', 'N', 'O', 'P', 'Q', Matrix.MATRIX_TYPE_RANDOM_REGULAR, 'S', 'T', Matrix.MATRIX_TYPE_RANDOM_UT, 'V', 'W', 'X', 'Y', Matrix.MATRIX_TYPE_ZERO, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] pem_convert_array = new byte[256];
    byte[] decode_buffer = new byte[4];

    static {
        int i2 = 0;
        for (int i3 = 0; i3 < 255; i3++) {
            pem_convert_array[i3] = -1;
        }
        while (true) {
            char[] cArr = pem_array;
            if (i2 >= cArr.length) {
                return;
            }
            pem_convert_array[cArr[i2]] = (byte) i2;
            i2++;
        }
    }

    @Override // android.sun.misc.CharacterDecoder
    public int bytesPerAtom() {
        return 4;
    }

    @Override // android.sun.misc.CharacterDecoder
    public int bytesPerLine() {
        return 72;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ab  */
    @Override // android.sun.misc.CharacterDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void decodeAtom(PushbackInputStream pushbackInputStream, OutputStream outputStream, int i2) {
        byte b;
        byte b2;
        byte b3;
        byte b4;
        int i3;
        int i4;
        int i5;
        if (i2 < 2) {
            throw new CEFormatException("BASE64Decoder: Not enough bytes for an atom.");
        }
        while (true) {
            int read = pushbackInputStream.read();
            byte b5 = -1;
            if (read == -1) {
                throw new CEStreamExhausted();
            }
            if (read != 10 && read != 13) {
                byte[] bArr = this.decode_buffer;
                bArr[0] = (byte) read;
                if (readFully(pushbackInputStream, bArr, 1, i2 - 1) == -1) {
                    throw new CEStreamExhausted();
                }
                if (i2 > 3 && this.decode_buffer[3] == 61) {
                    i2 = 3;
                }
                if (i2 > 2 && this.decode_buffer[2] == 61) {
                    i2 = 2;
                }
                if (i2 != 2) {
                    if (i2 != 3) {
                        if (i2 != 4) {
                            b3 = -1;
                            b4 = -1;
                            b2 = -1;
                            if (i2 == 2) {
                                if (i2 == 3) {
                                    outputStream.write((byte) (((b5 << 2) & 252) | (3 & (b2 >>> 4))));
                                    i4 = (b2 << 4) & 240;
                                    i5 = (b3 >>> 2) & 15;
                                } else {
                                    if (i2 != 4) {
                                        return;
                                    }
                                    outputStream.write((byte) (((b5 << 2) & 252) | ((b2 >>> 4) & 3)));
                                    outputStream.write((byte) (((b2 << 4) & 240) | ((b3 >>> 2) & 15)));
                                    i4 = (b3 << 6) & 192;
                                    i5 = b4 & 63;
                                }
                                i3 = i4 | i5;
                            } else {
                                i3 = ((b2 >>> 4) & 3) | ((b5 << 2) & 252);
                            }
                            outputStream.write((byte) i3);
                            return;
                        }
                        b5 = pem_convert_array[this.decode_buffer[3] & 255];
                    }
                    b = b5;
                    b5 = pem_convert_array[this.decode_buffer[2] & 255];
                } else {
                    b = -1;
                }
                byte[] bArr2 = pem_convert_array;
                byte[] bArr3 = this.decode_buffer;
                b2 = bArr2[bArr3[1] & 255];
                byte b6 = b;
                b3 = b5;
                b5 = bArr2[bArr3[0] & 255];
                b4 = b6;
                if (i2 == 2) {
                }
                outputStream.write((byte) i3);
                return;
            }
        }
    }
}
