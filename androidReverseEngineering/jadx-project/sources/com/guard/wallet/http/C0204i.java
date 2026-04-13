package com.guard.wallet.http;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.JsonObject;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.utils.AbstractC0252h;
import f0.C0299t;
import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import l0.C0383m;
import p0.C0882x;
import p0.C0884z;
import p0.InterfaceC0863e;
import p0.f0;
import p0.g0;
import p0.h0;
import p0.j0;
import p0.l0;
import p015s.C0897b;
import q0.AbstractC0887c;

/* renamed from: com.guard.wallet.http.i */
/* loaded from: classes.dex */
public final class C0204i {

    /* renamed from: b */
    public static final C0882x f246b;

    /* renamed from: a */
    public final String f247a;

    static {
        C0882x c0882x;
        try {
            c0882x = C0882x.m1301a("application/json; charset=utf-8");
        } catch (IllegalArgumentException unused) {
            c0882x = null;
        }
        f246b = c0882x;
    }

    public C0204i() {
        this.f247a = AbstractC0207l.f252a;
    }

    /* renamed from: c */
    public static void m400c(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        Log.d("FetchClient", "finishFetch:" + str);
        AbstractC0207l.f254c.remove(str);
    }

    /* renamed from: g */
    public static JsonObject m401g(j0 j0Var) {
        String str;
        ApiResult apiResult = new ApiResult();
        apiResult.setSuccess(Boolean.FALSE);
        if (j0Var != null) {
            apiResult.setCode(Integer.valueOf(j0Var.f1831c));
            str = j0Var.f1832d;
        } else {
            apiResult.setCode(500);
            str = "Network Error";
        }
        apiResult.setMsg(str);
        apiResult.setCount(0);
        return AbstractC0252h.m692M(AbstractC0252h.m693N(apiResult));
    }

    /* renamed from: l */
    public static boolean m402l(String str, p0.e0 e0Var, InterfaceC0863e interfaceC0863e) {
        if (AbstractC0026q.m151B(str)) {
            return false;
        }
        LinkedHashMap linkedHashMap = AbstractC0207l.f254c;
        if (!linkedHashMap.containsKey(str) || AbstractC0207l.f253b.contains(str)) {
            linkedHashMap.put(str, Long.valueOf(new Date().getTime()));
            return false;
        }
        interfaceC0863e.mo389b(e0Var, new C0897b(str));
        return true;
    }

    /* renamed from: a */
    public final p0.b0 m403a() {
        p0.a0 a0Var = new p0.a0();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        a0Var.f1712t = AbstractC0887c.m1305b("timeout", 60L, timeUnit);
        a0Var.f1713u = AbstractC0887c.m1305b("timeout", 120L, timeUnit);
        a0Var.f1714v = AbstractC0887c.m1305b("timeout", 120L, timeUnit);
        a0Var.f1711s = AbstractC0887c.m1305b("timeout", 240L, timeUnit);
        a0Var.f1710r = true;
        a0Var.f1709q = true;
        a0Var.f1708p = true;
        a0Var.f1715w = AbstractC0887c.m1305b("interval", 30L, timeUnit);
        a0Var.f1700h = new C0203h(this, 0);
        return new p0.b0(a0Var);
    }

    /* renamed from: b */
    public final JsonObject m404b(f0 f0Var) {
        try {
            j0 m1248b = p0.e0.m1246d(m403a(), f0Var, false).m1248b();
            l0 l0Var = m1248b.f1835g;
            return l0Var != null ? AbstractC0252h.m692M(l0Var.m1269z()) : m401g(m1248b);
        } catch (Exception e2) {
            AbstractC0026q.m186s("FetchClient", e2);
            return m401g(null);
        }
    }

    /* renamed from: d */
    public final void m405d(Object obj, String str, InterfaceC0863e interfaceC0863e) {
        String m406e = m406e(obj, str);
        C0383m c0383m = new C0383m();
        c0383m.m956d(m406e);
        c0383m.m954b("GET", null);
        f0 m953a = c0383m.m953a();
        p0.e0 m1246d = p0.e0.m1246d(m403a(), m953a, false);
        if (m402l(m953a.f1777a.f1914h, m1246d, interfaceC0863e)) {
            return;
        }
        m1246d.m1247a(interfaceC0863e);
    }

    /* renamed from: e */
    public final String m406e(Object obj, String str) {
        String m407f = m407f(str);
        JsonObject m692M = obj != null ? AbstractC0252h.m692M(AbstractC0252h.m693N(obj)) : null;
        if (m692M == null || m692M.keySet().isEmpty()) {
            return m407f;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : m692M.keySet()) {
            if (m692M.get(str2) != null && !m692M.get(str2).isJsonNull()) {
                String asString = m692M.get(str2).getAsString();
                if (!AbstractC0026q.m151B(sb.toString())) {
                    sb.append("&");
                }
                sb.append(str2);
                sb.append("=");
                sb.append(asString);
            }
        }
        if (AbstractC0026q.m151B(sb.toString())) {
            return m407f;
        }
        return (m407f.contains("?") ? m407f.concat("&") : m407f.concat("?")).concat(sb.toString());
    }

    /* renamed from: f */
    public final String m407f(String str) {
        boolean m151B = AbstractC0026q.m151B(str);
        String str2 = this.f247a;
        return !m151B ? str.startsWith("/") ? str2.concat(str) : str2.concat("/").concat(str) : str2;
    }

    /* renamed from: h */
    public final void m408h(Object obj, String str, InterfaceC0863e interfaceC0863e) {
        f0 m409i = m409i(obj, str);
        p0.e0 m1246d = p0.e0.m1246d(m403a(), m409i, false);
        if (m402l(m409i.f1777a.f1914h, m1246d, interfaceC0863e)) {
            return;
        }
        m1246d.m1247a(interfaceC0863e);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001b  */
    /* renamed from: i */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final f0 m409i(Object obj, String str) {
        Charset charset;
        String str2;
        String m407f = m407f(str);
        String m693N = AbstractC0252h.m693N(obj);
        Charset charset2 = StandardCharsets.UTF_8;
        C0882x c0882x = f246b;
        if (c0882x != null) {
            C0882x c0882x2 = null;
            try {
                str2 = c0882x.f1919c;
            } catch (IllegalArgumentException unused) {
            }
            if (str2 != null) {
                charset = Charset.forName(str2);
                if (charset == null) {
                    charset = StandardCharsets.UTF_8;
                    try {
                        c0882x2 = C0882x.m1301a(c0882x + "; charset=utf-8");
                    } catch (IllegalArgumentException unused2) {
                    }
                    c0882x = c0882x2;
                }
                charset2 = charset;
            }
            charset = null;
            if (charset == null) {
            }
            charset2 = charset;
        }
        byte[] bytes = m693N.getBytes(charset2);
        int length = bytes.length;
        long length2 = bytes.length;
        long j2 = 0;
        long j3 = length;
        byte[] bArr = AbstractC0887c.f1934a;
        if ((j2 | j3) < 0 || j2 > length2 || length2 - j2 < j3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        g0 g0Var = new g0(length, c0882x, bytes);
        C0383m c0383m = new C0383m();
        c0383m.m956d(m407f);
        c0383m.m954b("POST", g0Var);
        return c0383m.m953a();
    }

    /* renamed from: j */
    public final void m410j(UploadFileVO uploadFileVO, String str, LinkedList linkedList, InterfaceC0863e interfaceC0863e) {
        C0882x c0882x;
        String m406e = m406e(uploadFileVO, str);
        C0299t c0299t = new C0299t(5);
        if (linkedList != null && !linkedList.isEmpty()) {
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                File file = (File) it.next();
                if (file != null) {
                    try {
                        if (file.exists() && file.isFile()) {
                            try {
                                c0882x = C0882x.m1301a("multipart/form-data");
                            } catch (IllegalArgumentException unused) {
                                c0882x = null;
                            }
                            c0299t.m817d(file.getName(), new h0(c0882x, file));
                        }
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("FetchClient", e2);
                    }
                }
            }
        }
        c0299t.m819f(C0884z.f1923t);
        C0884z m818e = c0299t.m818e();
        C0383m c0383m = new C0383m();
        c0383m.m956d(m406e);
        c0383m.m954b("PATCH", m818e);
        p0.e0.m1246d(m403a(), c0383m.m953a(), false).m1247a(interfaceC0863e);
    }

    /* renamed from: k */
    public final void m411k(Serializable serializable, String str, String str2, byte[] bArr, InterfaceC0863e interfaceC0863e) {
        C0882x c0882x;
        String concat = AbstractC0026q.m151B(str2) ? "minicap-".concat(String.valueOf(System.currentTimeMillis())).concat(".webp") : str2;
        String m406e = m406e(serializable, str);
        C0299t c0299t = new C0299t(5);
        if (bArr.length > 0) {
            try {
                try {
                    c0882x = C0882x.m1301a("multipart/form-data");
                } catch (IllegalArgumentException unused) {
                    c0882x = null;
                }
                int length = bArr.length;
                long length2 = bArr.length;
                long j2 = 0;
                long j3 = length;
                byte[] bArr2 = AbstractC0887c.f1934a;
                if ((j2 | j3) < 0 || j2 > length2 || length2 - j2 < j3) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                c0299t.m817d(concat, new g0(length, c0882x, bArr));
            } catch (Exception e2) {
                AbstractC0026q.m186s("FetchClient", e2);
            }
        }
        c0299t.m819f(C0884z.f1923t);
        C0884z m818e = c0299t.m818e();
        C0383m c0383m = new C0383m();
        c0383m.m956d(m406e);
        c0383m.m954b("PATCH", m818e);
        p0.e0.m1246d(m403a(), c0383m.m953a(), false).m1247a(interfaceC0863e);
    }

    public C0204i(String str) {
        this.f247a = AbstractC0026q.m151B(str) ? AbstractC0207l.f252a : str;
    }
}
