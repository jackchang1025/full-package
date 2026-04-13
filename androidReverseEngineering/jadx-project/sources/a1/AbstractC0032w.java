package a1;

import java.nio.charset.Charset;

/* renamed from: a1.w */
/* loaded from: classes.dex */
public abstract class AbstractC0032w {

    /* renamed from: a */
    public static final Charset f75a = Charset.forName("UTF-8");

    /* renamed from: a */
    public static void m200a(long j2, long j3, long j4) {
        if ((j3 | j4) < 0 || j3 > j2 || j2 - j3 < j4) {
            throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", Long.valueOf(j2), Long.valueOf(j3), Long.valueOf(j4)));
        }
    }
}
