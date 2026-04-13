package f0;

import a1.AbstractC0026q;
import a1.C0017h;
import b0.C0078b;
import com.guard.wallet.thread.C0241j;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;
import g0.InterfaceC0311c;
import h0.FutureC0326h;
import i0.C0334e;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import l0.C0377g;
import m0.C0400a;
import p0.C0864f;
import p0.C0877s;
import p0.C0882x;
import p0.C0883y;
import p0.C0884z;

/* renamed from: f0.t */
/* loaded from: classes.dex */
public final class C0299t implements InterfaceC0310b, InterfaceC0311c, InterfaceC0309a {

    /* renamed from: d */
    public final /* synthetic */ int f552d;

    /* renamed from: e */
    public Object f553e;

    /* renamed from: f */
    public Object f554f;

    /* renamed from: g */
    public Object f555g;

    public C0299t() {
        this.f552d = 0;
        this.f553e = new C0292m();
        this.f554f = null;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception e2) {
        switch (this.f552d) {
            case 3:
                try {
                    if (e2 != null) {
                        throw e2;
                    }
                    C0241j c0241j = (C0241j) this.f555g;
                    C0292m c0292m = (C0292m) this.f553e;
                    String m809h = c0292m.m809h(null);
                    c0292m.m811k();
                    c0241j.f387e = C0334e.m874c(m809h, "&", false, C0334e.f648b);
                    ((InterfaceC0309a) this.f554f).mo293a(null);
                    return;
                } catch (Exception e3) {
                    ((InterfaceC0309a) this.f554f).mo293a(e3);
                    return;
                }
            default:
                if (e2 == null) {
                    try {
                        ((FutureC0326h) this.f554f).m871g(null, (C0292m) this.f553e, null);
                        return;
                    } catch (Exception e4) {
                        e2 = e4;
                    }
                }
                ((FutureC0326h) this.f554f).m871g(e2, null, null);
                return;
        }
    }

    @Override // g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        ByteBuffer allocate = ByteBuffer.allocate(c0292m.f541c);
        while (c0292m.f541c > 0) {
            byte b = c0292m.m810i(1).get();
            c0292m.f541c--;
            if (b == 10) {
                allocate.flip();
                ((C0292m) this.f553e).m803a(allocate);
                InterfaceC0298s interfaceC0298s = (InterfaceC0298s) this.f555g;
                C0292m c0292m2 = (C0292m) this.f553e;
                String m809h = c0292m2.m809h((Charset) this.f554f);
                c0292m2.m811k();
                interfaceC0298s.mo588c(m809h);
                this.f553e = new C0292m();
                return;
            }
            allocate.put(b);
        }
        allocate.flip();
        ((C0292m) this.f553e).m803a(allocate);
    }

    @Override // g0.InterfaceC0311c
    /* renamed from: c */
    public final void mo800c() {
        ((InterfaceC0295p) this.f554f).mo778c((C0292m) this.f553e);
        if (((C0292m) this.f553e).f541c != 0 || ((InterfaceC0309a) this.f555g) == null) {
            return;
        }
        ((InterfaceC0295p) this.f554f).mo779d(null);
        ((InterfaceC0309a) this.f555g).mo293a(null);
    }

    /* renamed from: d */
    public final void m817d(String str, AbstractC0026q abstractC0026q) {
        StringBuilder sb = new StringBuilder("form-data; name=");
        C0884z.m1302W(sb, "files");
        if (str != null) {
            sb.append("; filename=");
            C0884z.m1302W(sb, str);
        }
        C0864f c0864f = new C0864f();
        String sb2 = sb.toString();
        C0877s.m1278a("Content-Disposition");
        c0864f.m1251a("Content-Disposition", sb2);
        C0877s c0877s = new C0877s(c0864f);
        if (c0877s.m1280c("Content-Type") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Type");
        }
        if (c0877s.m1280c("Content-Length") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
        ((List) this.f555g).add(new C0883y(c0877s, abstractC0026q));
    }

    /* renamed from: e */
    public final C0884z m818e() {
        if (((List) this.f555g).isEmpty()) {
            throw new IllegalStateException("Multipart body must have at least one part.");
        }
        return new C0884z((C0017h) this.f554f, (C0882x) this.f553e, (List) this.f555g);
    }

    /* renamed from: f */
    public final void m819f(C0882x c0882x) {
        if (c0882x == null) {
            throw new NullPointerException("type == null");
        }
        if (c0882x.f1918b.equals("multipart")) {
            this.f553e = c0882x;
        } else {
            throw new IllegalArgumentException("multipart != " + c0882x);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C0299t(int i2) {
        this();
        this.f552d = i2;
        if (i2 != 2) {
            if (i2 != 5) {
            } else {
                this(UUID.randomUUID().toString());
            }
        }
    }

    public C0299t(C0078b c0078b, C0400a c0400a, C0292m c0292m) {
        this.f552d = 4;
        this.f555g = c0078b;
        this.f554f = c0400a;
        this.f553e = c0292m;
    }

    public C0299t(C0241j c0241j, C0292m c0292m, C0377g c0377g) {
        this.f552d = 3;
        this.f555g = c0241j;
        this.f553e = c0292m;
        this.f554f = c0377g;
    }

    public /* synthetic */ C0299t(Object obj, Object obj2, Object obj3, int i2) {
        this.f552d = i2;
        this.f554f = obj;
        this.f553e = obj2;
        this.f555g = obj3;
    }

    public C0299t(String str) {
        this.f552d = 5;
        this.f553e = C0884z.f1922s;
        this.f555g = new ArrayList();
        this.f554f = C0017h.m118d(str);
    }
}
