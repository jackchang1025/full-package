package l0;

import b0.C0078b;
import com.guard.wallet.http.C0203h;
import com.guard.wallet.thread.C0241j;
import f0.AbstractC0296q;
import f0.C0281b;
import f0.C0289j;
import f0.InterfaceC0290k;
import f0.InterfaceC0298s;
import g0.InterfaceC0309a;
import i0.C0331b;
import i0.C0333d;
import i0.EnumC0337h;
import j0.C0352b;
import j0.InterfaceC0351a;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.zip.Inflater;
import k0.C0357a;
import k0.C0359c;
import k0.C0362f;
import k0.C0363g;
import p000a.AbstractC0000a;
import p012o.RunnableC0415d;

/* renamed from: l0.g */
/* loaded from: classes.dex */
public final class C0377g implements InterfaceC0309a, InterfaceC0298s {

    /* renamed from: d */
    public final /* synthetic */ AbstractC0378h f749d;

    public /* synthetic */ C0377g(AbstractC0378h abstractC0378h) {
        this.f749d = abstractC0378h;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        this.f749d.mo293a(exc);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x007f  */
    @Override // f0.InterfaceC0298s
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo588c(String str) {
        long j2;
        AbstractC0296q c0357a;
        C0333d c0333d;
        AbstractC0296q c0363g;
        C0383m m958b;
        String m395i;
        String m395i2;
        AbstractC0378h abstractC0378h = this.f749d;
        if (abstractC0378h.f750i == null) {
            abstractC0378h.f750i = str;
            if (str.contains("HTTP/")) {
                return;
            }
            System.out.println("not http!");
            ((C0281b) abstractC0378h.f752k).f496k = new C0078b(24);
            abstractC0378h.mo813c(new IOException("data/header received was not not http"));
            return;
        }
        boolean equals = "\r".equals(str);
        C0203h c0203h = abstractC0378h.f751j;
        if (!equals) {
            c0203h.m393g(str);
            return;
        }
        InterfaceC0290k interfaceC0290k = abstractC0378h.f752k;
        EnumC0337h enumC0337h = EnumC0337h.f649b;
        try {
            m395i2 = c0203h.m395i("Content-Length");
        } catch (NumberFormatException unused) {
        }
        if (m395i2 != null) {
            j2 = Long.parseLong(m395i2);
            InterfaceC0351a interfaceC0351a = null;
            if (-1 != j2) {
                if ("chunked".equalsIgnoreCase(c0203h.m395i("Transfer-Encoding"))) {
                    c0357a = new C0357a();
                    c0357a.m814i(interfaceC0290k);
                    if ("gzip".equals(c0203h.m395i("Content-Encoding"))) {
                    }
                    c0363g.m814i(c0357a);
                    c0357a = c0363g;
                    C0374d c0374d = (C0374d) abstractC0378h;
                    String[] split = c0374d.f750i.split(" ");
                    String str2 = split[1];
                    c0374d.f736r = str2;
                    int i2 = 0;
                    String decode = URLDecoder.decode(str2.split("\\?")[0]);
                    c0374d.f737s = decode;
                    String str3 = split[0];
                    c0374d.f755n = str3;
                    m958b = c0374d.f743y.f744d.m958b(str3, decode);
                    if (m958b != null) {
                    }
                    abstractC0378h.f756o = null;
                    m395i = c0203h.m395i("Content-Type");
                    if (m395i != null) {
                    }
                    abstractC0378h.f756o = interfaceC0351a;
                    if (interfaceC0351a == null) {
                    }
                    abstractC0378h.f756o.mo589d(c0357a, abstractC0378h.f753l);
                    abstractC0378h.mo947l();
                    return;
                }
                C0289j c0289j = ((C0281b) interfaceC0290k).f491f;
                c0333d = new C0333d();
                c0289j.m796c(new RunnableC0415d(c0333d, null));
                c0333d.m814i(interfaceC0290k);
                c0357a = c0333d;
                C0374d c0374d2 = (C0374d) abstractC0378h;
                String[] split2 = c0374d2.f750i.split(" ");
                String str22 = split2[1];
                c0374d2.f736r = str22;
                int i22 = 0;
                String decode2 = URLDecoder.decode(str22.split("\\?")[0]);
                c0374d2.f737s = decode2;
                String str32 = split2[0];
                c0374d2.f755n = str32;
                m958b = c0374d2.f743y.f744d.m958b(str32, decode2);
                if (m958b != null) {
                }
                abstractC0378h.f756o = null;
                m395i = c0203h.m395i("Content-Type");
                if (m395i != null) {
                }
                abstractC0378h.f756o = interfaceC0351a;
                if (interfaceC0351a == null) {
                }
                abstractC0378h.f756o.mo589d(c0357a, abstractC0378h.f753l);
                abstractC0378h.mo947l();
                return;
            }
            if (j2 < 0) {
                C0289j c0289j2 = ((C0281b) interfaceC0290k).f491f;
                C0331b c0331b = new C0331b("not using chunked encoding, and no content-length found.");
                c0333d = new C0333d();
                c0289j2.m796c(new RunnableC0415d(c0333d, c0331b));
                c0333d.m814i(interfaceC0290k);
                c0357a = c0333d;
                C0374d c0374d22 = (C0374d) abstractC0378h;
                String[] split22 = c0374d22.f750i.split(" ");
                String str222 = split22[1];
                c0374d22.f736r = str222;
                int i222 = 0;
                String decode22 = URLDecoder.decode(str222.split("\\?")[0]);
                c0374d22.f737s = decode22;
                String str322 = split22[0];
                c0374d22.f755n = str322;
                m958b = c0374d22.f743y.f744d.m958b(str322, decode22);
                if (m958b != null) {
                    c0374d22.f735q = (InterfaceC0385o) m958b.f780d;
                    AbstractC0000a.m27w(m958b.f781e);
                }
                abstractC0378h.f756o = null;
                m395i = c0203h.m395i("Content-Type");
                if (m395i != null) {
                    String[] split3 = m395i.split(";");
                    for (int i3 = 0; i3 < split3.length; i3++) {
                        split3[i3] = split3[i3].trim();
                    }
                    int length = split3.length;
                    while (true) {
                        if (i222 >= length) {
                            break;
                        }
                        String str4 = split3[i222];
                        if ("application/x-www-form-urlencoded".equals(str4)) {
                            interfaceC0351a = new C0241j(7);
                            break;
                        }
                        if ("application/json".equals(str4)) {
                            interfaceC0351a = new C0241j(2);
                            break;
                        }
                        if ("text/plain".equals(str4)) {
                            interfaceC0351a = new C0241j(5);
                            break;
                        }
                        if (str4 != null && str4.startsWith("multipart/")) {
                            interfaceC0351a = new C0352b(m395i);
                            break;
                        }
                        i222++;
                    }
                }
                abstractC0378h.f756o = interfaceC0351a;
                if (interfaceC0351a == null) {
                    c0374d22.f743y.f744d.getClass();
                    abstractC0378h.f756o = new C0387q(c0203h.m395i("Content-Type"));
                }
                abstractC0378h.f756o.mo589d(c0357a, abstractC0378h.f753l);
                abstractC0378h.mo947l();
                return;
            }
            if (j2 != 0) {
                c0357a = new C0359c(j2);
                c0357a.m814i(interfaceC0290k);
                if ("gzip".equals(c0203h.m395i("Content-Encoding"))) {
                    if ("deflate".equals(c0203h.m395i("Content-Encoding"))) {
                        c0363g = new C0363g(new Inflater());
                    }
                    C0374d c0374d222 = (C0374d) abstractC0378h;
                    String[] split222 = c0374d222.f750i.split(" ");
                    String str2222 = split222[1];
                    c0374d222.f736r = str2222;
                    int i2222 = 0;
                    String decode222 = URLDecoder.decode(str2222.split("\\?")[0]);
                    c0374d222.f737s = decode222;
                    String str3222 = split222[0];
                    c0374d222.f755n = str3222;
                    m958b = c0374d222.f743y.f744d.m958b(str3222, decode222);
                    if (m958b != null) {
                    }
                    abstractC0378h.f756o = null;
                    m395i = c0203h.m395i("Content-Type");
                    if (m395i != null) {
                    }
                    abstractC0378h.f756o = interfaceC0351a;
                    if (interfaceC0351a == null) {
                    }
                    abstractC0378h.f756o.mo589d(c0357a, abstractC0378h.f753l);
                    abstractC0378h.mo947l();
                    return;
                }
                c0363g = new C0362f();
                c0363g.m814i(c0357a);
                c0357a = c0363g;
                C0374d c0374d2222 = (C0374d) abstractC0378h;
                String[] split2222 = c0374d2222.f750i.split(" ");
                String str22222 = split2222[1];
                c0374d2222.f736r = str22222;
                int i22222 = 0;
                String decode2222 = URLDecoder.decode(str22222.split("\\?")[0]);
                c0374d2222.f737s = decode2222;
                String str32222 = split2222[0];
                c0374d2222.f755n = str32222;
                m958b = c0374d2222.f743y.f744d.m958b(str32222, decode2222);
                if (m958b != null) {
                }
                abstractC0378h.f756o = null;
                m395i = c0203h.m395i("Content-Type");
                if (m395i != null) {
                }
                abstractC0378h.f756o = interfaceC0351a;
                if (interfaceC0351a == null) {
                }
                abstractC0378h.f756o.mo589d(c0357a, abstractC0378h.f753l);
                abstractC0378h.mo947l();
                return;
            }
            C0289j c0289j3 = ((C0281b) interfaceC0290k).f491f;
            c0333d = new C0333d();
            c0289j3.m796c(new RunnableC0415d(c0333d, null));
            c0333d.m814i(interfaceC0290k);
            c0357a = c0333d;
            C0374d c0374d22222 = (C0374d) abstractC0378h;
            String[] split22222 = c0374d22222.f750i.split(" ");
            String str222222 = split22222[1];
            c0374d22222.f736r = str222222;
            int i222222 = 0;
            String decode22222 = URLDecoder.decode(str222222.split("\\?")[0]);
            c0374d22222.f737s = decode22222;
            String str322222 = split22222[0];
            c0374d22222.f755n = str322222;
            m958b = c0374d22222.f743y.f744d.m958b(str322222, decode22222);
            if (m958b != null) {
            }
            abstractC0378h.f756o = null;
            m395i = c0203h.m395i("Content-Type");
            if (m395i != null) {
            }
            abstractC0378h.f756o = interfaceC0351a;
            if (interfaceC0351a == null) {
            }
            abstractC0378h.f756o.mo589d(c0357a, abstractC0378h.f753l);
            abstractC0378h.mo947l();
            return;
        }
        j2 = -1;
        InterfaceC0351a interfaceC0351a2 = null;
        if (-1 != j2) {
        }
    }
}
