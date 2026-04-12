package com.storm.safe.rock.util;

import android.util.Base64;
import p000.AbstractC0577hd;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class StringUtil {

    /* renamed from: a0 */
    public static final byte[] f55228a0 = {75, 57, 113, 90, 45, 88, 108, 78, 55, 81};

    /* renamed from: a0 */
    public static String m212470a0(String str) {
        try {
            byte[] bArrDecode = Base64.decode(str, 2);
            byte[] bArr = new byte[bArrDecode.length];
            int length = bArrDecode.length;
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) (bArrDecode[i] ^ f55228a0[i % 10]);
            }
            return new String(bArr, AbstractC0577hd.f56650a0);
        } catch (Exception unused) {
            return str;
        }
    }
}
