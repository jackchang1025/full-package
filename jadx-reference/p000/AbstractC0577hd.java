package p000;

import java.nio.charset.Charset;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hd */
/* loaded from: classes2.dex */
public abstract class AbstractC0577hd {

    /* renamed from: a0 */
    public static final Charset f56650a0;

    /* renamed from: a1 */
    public static volatile Charset f56651a1;

    /* renamed from: a2 */
    public static volatile Charset f56652a2;

    static {
        Charset charsetForName = Charset.forName("UTF-8");
        t60.m214694b5(charsetForName, "forName(\"UTF-8\")");
        f56650a0 = charsetForName;
        t60.m214694b5(Charset.forName("UTF-16"), "forName(\"UTF-16\")");
        t60.m214694b5(Charset.forName("UTF-16BE"), "forName(\"UTF-16BE\")");
        t60.m214694b5(Charset.forName("UTF-16LE"), "forName(\"UTF-16LE\")");
        t60.m214694b5(Charset.forName("US-ASCII"), "forName(\"US-ASCII\")");
        t60.m214694b5(Charset.forName("ISO-8859-1"), "forName(\"ISO-8859-1\")");
    }
}
