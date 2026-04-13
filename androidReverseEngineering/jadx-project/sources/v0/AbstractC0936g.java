package v0;

import a1.C0017h;
import java.io.IOException;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;

/* renamed from: v0.g */
/* loaded from: classes.dex */
public abstract class AbstractC0936g {

    /* renamed from: a */
    public static final C0017h f2163a = C0017h.m118d("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");

    /* renamed from: b */
    public static final String[] f2164b = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};

    /* renamed from: c */
    public static final String[] f2165c = new String[64];

    /* renamed from: d */
    public static final String[] f2166d = new String[256];

    static {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            String[] strArr = f2166d;
            if (i3 >= strArr.length) {
                break;
            }
            strArr[i3] = AbstractC0887c.m1312i(new Object[]{Integer.toBinaryString(i3)}, "%8s").replace(' ', '0');
            i3++;
        }
        String[] strArr2 = f2165c;
        strArr2[0] = BuildConfig.FLAVOR;
        strArr2[1] = "END_STREAM";
        int[] iArr = {1};
        strArr2[8] = "PADDED";
        strArr2[1 | 8] = AbstractC0000a.m18n(new StringBuilder(), strArr2[1], "|PADDED");
        strArr2[4] = "END_HEADERS";
        strArr2[32] = "PRIORITY";
        strArr2[36] = "END_HEADERS|PRIORITY";
        int[] iArr2 = {4, 32, 36};
        for (int i4 = 0; i4 < 3; i4++) {
            int i5 = iArr2[i4];
            int i6 = iArr[0];
            String[] strArr3 = f2165c;
            int i7 = i6 | i5;
            strArr3[i7] = strArr3[i6] + '|' + strArr3[i5];
            StringBuilder sb = new StringBuilder();
            sb.append(strArr3[i6]);
            sb.append('|');
            strArr3[i7 | 8] = AbstractC0000a.m18n(sb, strArr3[i5], "|PADDED");
        }
        while (true) {
            String[] strArr4 = f2165c;
            if (i2 >= strArr4.length) {
                return;
            }
            if (strArr4[i2] == null) {
                strArr4[i2] = f2166d[i2];
            }
            i2++;
        }
    }

    /* renamed from: a */
    public static String m1406a(boolean z2, int i2, int i3, byte b, byte b2) {
        String str;
        String str2;
        String str3;
        String[] strArr = f2164b;
        String m1312i = b < strArr.length ? strArr[b] : AbstractC0887c.m1312i(new Object[]{Byte.valueOf(b)}, "0x%02x");
        if (b2 == 0) {
            str = BuildConfig.FLAVOR;
        } else {
            String[] strArr2 = f2166d;
            if (b != 2 && b != 3) {
                if (b == 4 || b == 6) {
                    str = b2 == 1 ? "ACK" : strArr2[b2];
                } else if (b != 7 && b != 8) {
                    String[] strArr3 = f2165c;
                    String str4 = b2 < strArr3.length ? strArr3[b2] : strArr2[b2];
                    if (b == 5 && (b2 & 4) != 0) {
                        str2 = "HEADERS";
                        str3 = "PUSH_PROMISE";
                    } else if (b != 0 || (b2 & 32) == 0) {
                        str = str4;
                    } else {
                        str2 = "PRIORITY";
                        str3 = "COMPRESSED";
                    }
                    str = str4.replace(str2, str3);
                }
            }
            str = strArr2[b2];
        }
        Object[] objArr = new Object[5];
        objArr[0] = z2 ? "<<" : ">>";
        objArr[1] = Integer.valueOf(i2);
        objArr[2] = Integer.valueOf(i3);
        objArr[3] = m1312i;
        objArr[4] = str;
        return AbstractC0887c.m1312i(objArr, "%s 0x%08x %5d %-13s %s");
    }

    /* renamed from: b */
    public static void m1407b(Object[] objArr, String str) {
        throw new IOException(AbstractC0887c.m1312i(objArr, str));
    }
}
