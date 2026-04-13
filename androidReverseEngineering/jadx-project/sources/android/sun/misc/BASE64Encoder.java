package android.sun.misc;

import java.io.OutputStream;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;

/* loaded from: classes.dex */
public class BASE64Encoder extends CharacterEncoder {
    private static final char[] pem_array = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', Matrix.MATRIX_TYPE_RANDOM_LT, 'M', 'N', 'O', 'P', 'Q', Matrix.MATRIX_TYPE_RANDOM_REGULAR, 'S', 'T', Matrix.MATRIX_TYPE_RANDOM_UT, 'V', 'W', 'X', 'Y', Matrix.MATRIX_TYPE_ZERO, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    @Override // android.sun.misc.CharacterEncoder
    public int bytesPerAtom() {
        return 3;
    }

    @Override // android.sun.misc.CharacterEncoder
    public int bytesPerLine() {
        return 57;
    }

    @Override // android.sun.misc.CharacterEncoder
    public void encodeAtom(OutputStream outputStream, byte[] bArr, int i2, int i3) {
        if (i3 == 1) {
            byte b = bArr[i2];
            char[] cArr = pem_array;
            outputStream.write(cArr[(b >>> 2) & 63]);
            outputStream.write(cArr[((b << 4) & 48) + 0]);
            outputStream.write(61);
        } else {
            if (i3 != 2) {
                byte b2 = bArr[i2];
                byte b3 = bArr[i2 + 1];
                byte b4 = bArr[i2 + 2];
                char[] cArr2 = pem_array;
                outputStream.write(cArr2[(b2 >>> 2) & 63]);
                outputStream.write(cArr2[((b2 << 4) & 48) + ((b3 >>> 4) & 15)]);
                outputStream.write(cArr2[((b3 << 2) & 60) + ((b4 >>> 6) & 3)]);
                outputStream.write(cArr2[b4 & 63]);
                return;
            }
            byte b5 = bArr[i2];
            byte b6 = bArr[i2 + 1];
            char[] cArr3 = pem_array;
            outputStream.write(cArr3[(b5 >>> 2) & 63]);
            outputStream.write(cArr3[((b5 << 4) & 48) + ((b6 >>> 4) & 15)]);
            outputStream.write(cArr3[((b6 << 2) & 60) + 0]);
        }
        outputStream.write(61);
    }
}
