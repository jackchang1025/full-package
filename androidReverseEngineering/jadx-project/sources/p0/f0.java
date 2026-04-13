package p0;

import a1.AbstractC0026q;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import l0.C0383m;
import q0.AbstractC0887c;

/* loaded from: classes.dex */
public final class f0 {

    /* renamed from: a */
    public final C0879u f1777a;

    /* renamed from: b */
    public final String f1778b;

    /* renamed from: c */
    public final C0877s f1779c;

    /* renamed from: d */
    public final AbstractC0026q f1780d;

    /* renamed from: e */
    public final Map f1781e;

    /* renamed from: f */
    public volatile C0862d f1782f;

    public f0(C0383m c0383m) {
        this.f1777a = (C0879u) c0383m.f778b;
        this.f1778b = c0383m.f777a;
        C0864f c0864f = (C0864f) c0383m.f779c;
        c0864f.getClass();
        this.f1779c = new C0877s(c0864f);
        this.f1780d = (AbstractC0026q) c0383m.f780d;
        Map map = (Map) c0383m.f781e;
        byte[] bArr = AbstractC0887c.f1934a;
        this.f1781e = map.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(new LinkedHashMap(map));
    }

    /* renamed from: a */
    public final String m1254a(String str) {
        return this.f1779c.m1280c(str);
    }

    public final String toString() {
        return "Request{method=" + this.f1778b + ", url=" + this.f1777a + ", tags=" + this.f1781e + '}';
    }
}
