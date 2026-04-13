package android.sun.misc;

import java.io.EOFException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: classes.dex */
public class IOUtils {
    public static byte[] readFully(InputStream inputStream, int i2, boolean z2) {
        int length;
        int i3 = 0;
        byte[] bArr = new byte[0];
        if (i2 == -1) {
            i2 = Integer.MAX_VALUE;
        }
        while (i3 < i2) {
            if (i3 >= bArr.length) {
                length = Math.min(i2 - i3, bArr.length + 1024);
                int i4 = i3 + length;
                if (bArr.length < i4) {
                    bArr = Arrays.copyOf(bArr, i4);
                }
            } else {
                length = bArr.length - i3;
            }
            int read = inputStream.read(bArr, i3, length);
            if (read < 0) {
                if (!z2 || i2 == Integer.MAX_VALUE) {
                    return bArr.length != i3 ? Arrays.copyOf(bArr, i3) : bArr;
                }
                throw new EOFException("Detect premature EOF");
            }
            i3 += read;
        }
        return bArr;
    }
}
