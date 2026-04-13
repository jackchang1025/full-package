package l0;

import a1.AbstractC0026q;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import p0.C0864f;
import p0.C0878t;
import p0.C0879u;
import p0.f0;
import p000a.AbstractC0000a;

/* renamed from: l0.m */
/* loaded from: classes.dex */
public final class C0383m {

    /* renamed from: a */
    public String f777a;

    /* renamed from: b */
    public Object f778b;

    /* renamed from: c */
    public Object f779c;

    /* renamed from: d */
    public Object f780d;

    /* renamed from: e */
    public final Object f781e;

    public C0383m() {
        this.f781e = Collections.emptyMap();
        this.f777a = "GET";
        this.f779c = new C0864f();
    }

    /* renamed from: a */
    public final f0 m953a() {
        if (((C0879u) this.f778b) != null) {
            return new f0(this);
        }
        throw new IllegalStateException("url == null");
    }

    /* renamed from: b */
    public final void m954b(String str, AbstractC0026q abstractC0026q) {
        if (str.length() == 0) {
            throw new IllegalArgumentException("method.length() == 0");
        }
        if (abstractC0026q != null && !AbstractC0026q.m158I(str)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("method ", str, " must not have a request body."));
        }
        if (abstractC0026q == null) {
            if (str.equals("POST") || str.equals("PUT") || str.equals("PATCH") || str.equals("PROPPATCH") || str.equals("REPORT")) {
                throw new IllegalArgumentException(AbstractC0000a.m16l("method ", str, " must have a request body."));
            }
        }
        this.f777a = str;
        this.f780d = abstractC0026q;
    }

    /* renamed from: c */
    public final void m955c(String str) {
        ((C0864f) this.f779c).m1252b(str);
    }

    /* renamed from: d */
    public final void m956d(String str) {
        StringBuilder sb;
        int i2;
        if (str == null) {
            throw new NullPointerException("url == null");
        }
        if (!str.regionMatches(true, 0, "ws:", 0, 3)) {
            if (str.regionMatches(true, 0, "wss:", 0, 4)) {
                sb = new StringBuilder("https:");
                i2 = 4;
            }
            C0878t c0878t = new C0878t();
            c0878t.m1285b(null, str);
            this.f778b = c0878t.m1284a();
        }
        sb = new StringBuilder("http:");
        i2 = 3;
        sb.append(str.substring(i2));
        str = sb.toString();
        C0878t c0878t2 = new C0878t();
        c0878t2.m1285b(null, str);
        this.f778b = c0878t2.m1284a();
    }

    public C0383m(String str, String str2, Matcher matcher, InterfaceC0385o interfaceC0385o) {
        this.f777a = str;
        this.f778b = str2;
        this.f779c = matcher;
        this.f780d = interfaceC0385o;
        this.f781e = null;
    }

    public C0383m(f0 f0Var) {
        this.f781e = Collections.emptyMap();
        this.f778b = f0Var.f1777a;
        this.f777a = f0Var.f1778b;
        this.f780d = f0Var.f1780d;
        Map map = f0Var.f1781e;
        this.f781e = map.isEmpty() ? Collections.emptyMap() : new LinkedHashMap(map);
        this.f779c = f0Var.f1779c.m1282e();
    }
}
