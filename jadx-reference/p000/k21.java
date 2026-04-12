package p000;

import android.util.Base64;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class k21 {

    /* renamed from: a0 */
    public static final k21 f57429a0 = new k21();

    /* renamed from: a1 */
    public static final byte[] f57430a1 = {74, Byte.MAX_VALUE, 43, 94, 28, -115, 58, 111, -98, 13, 76, 123, 42, 95, 30, -116};

    /* renamed from: a2 */
    public static final byte[] f57431a2 = {59, 110, 26, 77, 12, 124, 43, 94, -113, 14, 61, 108, 27, 78, 15, 125};

    /* renamed from: a0 */
    public static String m213444a0(String str) {
        t60.m214695b6(str, "cipherText");
        if (str.length() == 0) {
            return "";
        }
        try {
            byte[] bArrDecode = Base64.decode(str, 2);
            t60.m214694b5(bArrDecode, "data");
            byte[] bArrCopyOf = Arrays.copyOf(bArrDecode, bArrDecode.length);
            t60.m214694b5(bArrCopyOf, "copyOf(this, size)");
            k60 k60VarM214461g0 = AbstractC1117qo.m214461g0(AbstractC1117qo.m214463g2(0, bArrCopyOf.length - 1), 2);
            int i = k60VarM214461g0.f57461a0;
            int i2 = k60VarM214461g0.f57462a1;
            int i3 = k60VarM214461g0.f57463a2;
            if ((i3 > 0 && i <= i2) || (i3 < 0 && i2 <= i)) {
                while (true) {
                    byte b = bArrCopyOf[i];
                    int i4 = i + 1;
                    bArrCopyOf[i] = bArrCopyOf[i4];
                    bArrCopyOf[i4] = b;
                    if (i == i2) {
                        break;
                    }
                    i += i3;
                }
            }
            byte[] bArr = f57431a2;
            int length = bArrCopyOf.length;
            byte[] bArr2 = new byte[length];
            int length2 = bArrCopyOf.length;
            for (int i5 = 0; i5 < length2; i5++) {
                bArr2[i5] = (byte) (bArrCopyOf[i5] ^ bArr[i5 % 16]);
            }
            byte[] bArr3 = f57430a1;
            byte[] bArr4 = new byte[length];
            for (int i6 = 0; i6 < length; i6++) {
                bArr4[i6] = (byte) (bArr2[i6] ^ bArr3[i6 % 16]);
            }
            return new String(bArr4, AbstractC0577hd.f56650a0);
        } catch (Exception unused) {
            return "";
        }
    }
}
