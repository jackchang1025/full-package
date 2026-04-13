package l0;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.server.C0230b;
import com.guard.wallet.utils.AbstractC0252h;
import f0.C0281b;
import f0.InterfaceC0290k;
import i0.C0334e;
import i0.EnumC0337h;
import j0.InterfaceC0351a;
import java.util.Locale;
import java.util.Objects;
import org.json.JSONObject;
import p015s.C0896a;
import p019w.AbstractC0956a;

/* renamed from: l0.d */
/* loaded from: classes.dex */
public final class C0374d extends AbstractC0378h {

    /* renamed from: p */
    public final C0374d f734p;

    /* renamed from: q */
    public InterfaceC0385o f735q;

    /* renamed from: r */
    public String f736r;

    /* renamed from: s */
    public String f737s;

    /* renamed from: t */
    public boolean f738t;

    /* renamed from: u */
    public boolean f739u;

    /* renamed from: v */
    public C0372b f740v;

    /* renamed from: w */
    public boolean f741w;

    /* renamed from: x */
    public final /* synthetic */ InterfaceC0290k f742x;

    /* renamed from: y */
    public final /* synthetic */ C0375e f743y;

    public C0374d(C0375e c0375e, InterfaceC0290k interfaceC0290k) {
        this.f743y = c0375e;
        this.f742x = interfaceC0290k;
        C0376f c0376f = c0375e.f744d;
        this.f734p = this;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        C0376f c0376f = this.f743y.f744d;
        C0372b c0372b = this.f740v;
        c0376f.getClass();
        if (c0372b.f771l == 101) {
            return;
        }
        this.f739u = true;
        mo813c(exc);
        C0281b c0281b = (C0281b) this.f752k;
        c0281b.f496k = new C0373c(this);
        if (exc != null) {
            c0281b.close();
            return;
        }
        m948m();
        if (this.f756o.mo590f()) {
            m949n();
        }
    }

    @Override // l0.AbstractC0378h
    /* renamed from: l */
    public final void mo947l() {
        if (!this.f741w && "100-continue".equals(this.f751j.m395i("Expect"))) {
            ((C0281b) this.f752k).m788m();
            AbstractC0026q.m169T(this.f752k, "HTTP/1.1 100 Continue\r\n\r\n".getBytes(), new C0371a(this));
            return;
        }
        this.f740v = new C0372b(this, this.f742x, this);
        this.f743y.f744d.getClass();
        if (this.f735q == null) {
            C0372b c0372b = this.f740v;
            c0372b.f771l = 404;
            c0372b.mo787l();
        } else if (!this.f756o.mo590f() || this.f739u) {
            m949n();
        }
    }

    /* renamed from: m */
    public final void m948m() {
        EnumC0337h enumC0337h;
        if (this.f739u && this.f738t) {
            C0375e c0375e = this.f743y;
            C0376f c0376f = c0375e.f744d;
            C0372b c0372b = this.f740v;
            c0376f.getClass();
            boolean z2 = true;
            if (c0372b.f771l == 101) {
                return;
            }
            C0372b c0372b2 = this.f740v;
            c0375e.f744d.getClass();
            String str = c0372b2.f772m;
            String m395i = this.f734p.f751j.m395i("Connection");
            if (m395i == null) {
                if (str == null) {
                    EnumC0337h enumC0337h2 = EnumC0337h.f649b;
                    enumC0337h = null;
                } else {
                    enumC0337h = (EnumC0337h) EnumC0337h.f650c.get(str.toLowerCase(Locale.US));
                }
                if (enumC0337h != EnumC0337h.f649b) {
                    z2 = false;
                }
            } else {
                z2 = "keep-alive".equalsIgnoreCase(m395i);
            }
            InterfaceC0290k interfaceC0290k = this.f742x;
            if (z2) {
                c0375e.m950b(interfaceC0290k);
            } else {
                ((C0281b) interfaceC0290k).close();
            }
        }
    }

    /* renamed from: n */
    public final void m949n() {
        InterfaceC0351a interfaceC0351a;
        JSONObject jSONObject;
        C0376f c0376f = this.f743y.f744d;
        InterfaceC0385o interfaceC0385o = this.f735q;
        C0372b c0372b = this.f740v;
        c0376f.getClass();
        if (interfaceC0385o != null) {
            try {
                C0230b c0230b = (C0230b) interfaceC0385o;
                try {
                    C0230b.f292c.set(1);
                    String str = this.f737s;
                    String str2 = this.f755n;
                    c0372b.f763d.m397k("Content-Type", "application/json");
                    if (AbstractC0956a.m1443a() && !C0230b.r1(str)) {
                        C0230b.m1(c0372b);
                        return;
                    }
                    if (Objects.equals(str2.toUpperCase(), "GET")) {
                        String[] split = this.f736r.split("\\?", 2);
                        c0230b.e1(str, split.length < 2 ? new C0334e() : C0334e.m874c(split[1], "&", false, C0334e.f647a), c0372b);
                    } else if (!Objects.equals(str2.toUpperCase(), "POST") || (interfaceC0351a = this.f756o) == null || interfaceC0351a.length() <= 0 || (jSONObject = (JSONObject) this.f756o.get()) == null) {
                        C0230b.F2(c0372b, "访问地址或参数不合法,详见接口文档");
                    } else {
                        C0230b.X1(str, jSONObject.toString(), c0372b);
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("HttpServer", e2);
                }
            } catch (Exception e3) {
                Log.e("AsyncHttpServer", "request callback raised uncaught exception. Catching versus crashing process", e3);
                ApiResult apiResult = new ApiResult();
                C0896a c0896a = new C0896a();
                c0896a.f1989b = 2;
                c0896a.f1991d = "Internal Server Error";
                c0896a.f1990c = "Internal Server Error";
                apiResult.setData(c0896a);
                apiResult.setCode(500);
                apiResult.setMsg("request callback raised uncaught exception. Catching versus crashing process");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.FALSE);
                String m693N = AbstractC0252h.m693N(apiResult);
                c0372b.f771l = apiResult.getCode().intValue();
                c0372b.m952h(m693N);
                c0372b.mo787l();
            }
        }
    }
}
