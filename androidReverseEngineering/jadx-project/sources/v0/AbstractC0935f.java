package v0;

import a1.C0017h;
import android.sun.security.x509.InvalidityDateExtension;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: v0.f */
/* loaded from: classes.dex */
public abstract class AbstractC0935f {

    /* renamed from: a */
    public static final C0932c[] f2161a;

    /* renamed from: b */
    public static final Map f2162b;

    static {
        C0932c c0932c = new C0932c(C0932c.f2139i, BuildConfig.FLAVOR);
        C0017h c0017h = C0932c.f2136f;
        C0017h c0017h2 = C0932c.f2137g;
        C0017h c0017h3 = C0932c.f2138h;
        C0017h c0017h4 = C0932c.f2135e;
        C0932c[] c0932cArr = {c0932c, new C0932c(c0017h, "GET"), new C0932c(c0017h, "POST"), new C0932c(c0017h2, "/"), new C0932c(c0017h2, "/index.html"), new C0932c(c0017h3, "http"), new C0932c(c0017h3, "https"), new C0932c(c0017h4, "200"), new C0932c(c0017h4, "204"), new C0932c(c0017h4, "206"), new C0932c(c0017h4, "304"), new C0932c(c0017h4, "400"), new C0932c(c0017h4, "404"), new C0932c(c0017h4, "500"), new C0932c("accept-charset", BuildConfig.FLAVOR), new C0932c("accept-encoding", "gzip, deflate"), new C0932c("accept-language", BuildConfig.FLAVOR), new C0932c("accept-ranges", BuildConfig.FLAVOR), new C0932c("accept", BuildConfig.FLAVOR), new C0932c("access-control-allow-origin", BuildConfig.FLAVOR), new C0932c("age", BuildConfig.FLAVOR), new C0932c("allow", BuildConfig.FLAVOR), new C0932c("authorization", BuildConfig.FLAVOR), new C0932c("cache-control", BuildConfig.FLAVOR), new C0932c("content-disposition", BuildConfig.FLAVOR), new C0932c("content-encoding", BuildConfig.FLAVOR), new C0932c("content-language", BuildConfig.FLAVOR), new C0932c("content-length", BuildConfig.FLAVOR), new C0932c("content-location", BuildConfig.FLAVOR), new C0932c("content-range", BuildConfig.FLAVOR), new C0932c("content-type", BuildConfig.FLAVOR), new C0932c("cookie", BuildConfig.FLAVOR), new C0932c(InvalidityDateExtension.DATE, BuildConfig.FLAVOR), new C0932c("etag", BuildConfig.FLAVOR), new C0932c("expect", BuildConfig.FLAVOR), new C0932c("expires", BuildConfig.FLAVOR), new C0932c("from", BuildConfig.FLAVOR), new C0932c("host", BuildConfig.FLAVOR), new C0932c("if-match", BuildConfig.FLAVOR), new C0932c("if-modified-since", BuildConfig.FLAVOR), new C0932c("if-none-match", BuildConfig.FLAVOR), new C0932c("if-range", BuildConfig.FLAVOR), new C0932c("if-unmodified-since", BuildConfig.FLAVOR), new C0932c("last-modified", BuildConfig.FLAVOR), new C0932c("link", BuildConfig.FLAVOR), new C0932c("location", BuildConfig.FLAVOR), new C0932c("max-forwards", BuildConfig.FLAVOR), new C0932c("proxy-authenticate", BuildConfig.FLAVOR), new C0932c("proxy-authorization", BuildConfig.FLAVOR), new C0932c("range", BuildConfig.FLAVOR), new C0932c("referer", BuildConfig.FLAVOR), new C0932c("refresh", BuildConfig.FLAVOR), new C0932c("retry-after", BuildConfig.FLAVOR), new C0932c("server", BuildConfig.FLAVOR), new C0932c("set-cookie", BuildConfig.FLAVOR), new C0932c("strict-transport-security", BuildConfig.FLAVOR), new C0932c("transfer-encoding", BuildConfig.FLAVOR), new C0932c("user-agent", BuildConfig.FLAVOR), new C0932c("vary", BuildConfig.FLAVOR), new C0932c("via", BuildConfig.FLAVOR), new C0932c("www-authenticate", BuildConfig.FLAVOR)};
        f2161a = c0932cArr;
        LinkedHashMap linkedHashMap = new LinkedHashMap(c0932cArr.length);
        for (int i2 = 0; i2 < c0932cArr.length; i2++) {
            if (!linkedHashMap.containsKey(c0932cArr[i2].f2140a)) {
                linkedHashMap.put(c0932cArr[i2].f2140a, Integer.valueOf(i2));
            }
        }
        f2162b = Collections.unmodifiableMap(linkedHashMap);
    }

    /* renamed from: a */
    public static void m1405a(C0017h c0017h) {
        int mo125j = c0017h.mo125j();
        for (int i2 = 0; i2 < mo125j; i2++) {
            byte mo121e = c0017h.mo121e(i2);
            if (mo121e >= 65 && mo121e <= 90) {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + c0017h.mo128m());
            }
        }
    }
}
