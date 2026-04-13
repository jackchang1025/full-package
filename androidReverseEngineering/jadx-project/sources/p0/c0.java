package p0;

import java.io.IOException;

/* loaded from: classes.dex */
public enum c0 {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2"),
    H2_PRIOR_KNOWLEDGE("h2_prior_knowledge"),
    QUIC("quic");


    /* renamed from: a */
    public final String f1754a;

    c0(String str) {
        this.f1754a = str;
    }

    /* renamed from: a */
    public static c0 m1243a(String str) {
        if (str.equals("http/1.0")) {
            return HTTP_1_0;
        }
        if (str.equals("http/1.1")) {
            return HTTP_1_1;
        }
        if (str.equals("h2_prior_knowledge")) {
            return H2_PRIOR_KNOWLEDGE;
        }
        if (str.equals("h2")) {
            return HTTP_2;
        }
        if (str.equals("spdy/3.1")) {
            return SPDY_3;
        }
        if (str.equals("quic")) {
            return QUIC;
        }
        throw new IOException("Unexpected protocol: ".concat(str));
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f1754a;
    }
}
