package n0;

import java.nio.charset.Charset;
import org.bouncycastle.i18n.LocalizedMessage;

/* renamed from: n0.d */
/* loaded from: classes.dex */
public abstract class AbstractC0408d {

    /* renamed from: a */
    public static final Charset f818a;

    static {
        Charset.forName("US-ASCII");
        f818a = Charset.forName("UTF-8");
        Charset.forName(LocalizedMessage.DEFAULT_ENCODING);
    }
}
