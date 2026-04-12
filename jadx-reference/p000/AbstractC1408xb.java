package p000;

import android.content.Context;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xb */
/* loaded from: classes2.dex */
public abstract class AbstractC1408xb {

    /* renamed from: a0 */
    public static boolean f61060a0;

    /* renamed from: a1 */
    public static byte[] f61061a1 = new byte[0];

    /* renamed from: a2 */
    public static final LinkedHashMap f61062a2 = new LinkedHashMap();

    /* renamed from: a3 */
    public static final byte[] f61063a3 = {90, 77, 50, 54};

    /* JADX WARN: Removed duplicated region for block: B:23:0x0093  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String m215154a0(Context context, String str) throws IOException {
        InputStream inputStreamOpen;
        byte[] bArr;
        t60.m214695b6(context, "context");
        t60.m214695b6(str, "originalName");
        if (!f61060a0 || f61061a1.length == 0) {
            inputStreamOpen = context.getAssets().open(str);
            t60.m214694b5(inputStreamOpen, "context.assets.open(originalName)");
        } else {
            String str2 = (String) f61062a2.get(str);
            if (str2 == null) {
                inputStreamOpen = context.getAssets().open(str);
                t60.m214694b5(inputStreamOpen, "context.assets.open(originalName)");
            } else {
                InputStream inputStreamOpen2 = context.getAssets().open(str2);
                t60.m214694b5(inputStreamOpen2, "context.assets.open(mappedName)");
                byte[] bArrM212491d4 = cq0.m212491d4(inputStreamOpen2);
                int i = 0;
                if (bArrM212491d4.length > 20) {
                    byte b = bArrM212491d4[0];
                    byte[] bArr2 = f61063a3;
                    if (b == bArr2[0] && bArrM212491d4[1] == bArr2[1] && bArrM212491d4[2] == bArr2[2] && bArrM212491d4[3] == bArr2[3]) {
                        byte[] bArrM210722e5 = AbstractC0134bh.m210722e5(bArrM212491d4, 4, 12);
                        byte[] bArr3 = f61061a1;
                        int length = bArr3.length + bArrM210722e5.length;
                        byte[] bArr4 = new byte[length];
                        System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                        System.arraycopy(bArrM210722e5, 0, bArr4, f61061a1.length, bArrM210722e5.length);
                        byte[] bArrM210722e52 = AbstractC0134bh.m210722e5(bArrM212491d4, 20, bArrM212491d4.length);
                        bArr = new byte[bArrM210722e52.length];
                        int length2 = bArrM210722e52.length;
                        while (i < length2) {
                            bArr[i] = (byte) (bArrM210722e52[i] ^ bArr4[i % length]);
                            i++;
                        }
                    } else {
                        byte[] bArr5 = f61061a1;
                        byte[] bArr6 = new byte[bArrM212491d4.length];
                        int length3 = bArrM212491d4.length;
                        while (i < length3) {
                            bArr6[i] = (byte) (bArrM212491d4[i] ^ bArr5[i % bArr5.length]);
                            i++;
                        }
                        bArr = bArr6;
                    }
                    inputStreamOpen = new ByteArrayInputStream(bArr);
                }
            }
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpen, AbstractC0577hd.f56650a0), Segment.SIZE);
        try {
            String strM210590e1 = b81.m210590e1(bufferedReader);
            bufferedReader.close();
            return strM210590e1;
        } finally {
        }
    }
}
