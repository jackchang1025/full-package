package p0;

import a1.InterfaceC0016g;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;

/* loaded from: classes.dex */
public abstract class l0 implements Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        AbstractC0887c.m1306c(mo1267y());
    }

    /* renamed from: x */
    public final byte[] m1268x() {
        k0 k0Var = (k0) this;
        long j2 = k0Var.f1849b;
        if (j2 > 2147483647L) {
            throw new IOException("Cannot buffer entire body for content length: " + j2);
        }
        InterfaceC0016g interfaceC0016g = k0Var.f1850c;
        try {
            byte[] mo103m = interfaceC0016g.mo103m();
            interfaceC0016g.close();
            if (j2 == -1 || j2 == mo103m.length) {
                return mo103m;
            }
            StringBuilder sb = new StringBuilder("Content-Length (");
            sb.append(j2);
            sb.append(") and stream length (");
            throw new IOException(AbstractC0000a.m17m(sb, mo103m.length, ") disagree"));
        } catch (Throwable th) {
            if (interfaceC0016g != null) {
                try {
                    interfaceC0016g.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* renamed from: y */
    public abstract InterfaceC0016g mo1267y();

    /* renamed from: z */
    public final String m1269z() {
        C0882x c0882x;
        Charset charset;
        InterfaceC0016g interfaceC0016g = ((k0) this).f1850c;
        try {
            k0 k0Var = (k0) this;
            int i2 = k0Var.f1848a;
            Object obj = k0Var.f1851d;
            switch (i2) {
                case 0:
                    c0882x = (C0882x) obj;
                    break;
                default:
                    String str = (String) obj;
                    if (str != null) {
                        try {
                            c0882x = C0882x.m1301a(str);
                            break;
                        } catch (IllegalArgumentException unused) {
                        }
                    }
                    c0882x = null;
                    break;
            }
            if (c0882x != null) {
                charset = StandardCharsets.UTF_8;
                try {
                    String str2 = c0882x.f1919c;
                    if (str2 != null) {
                        charset = Charset.forName(str2);
                    }
                } catch (IllegalArgumentException unused2) {
                }
            } else {
                charset = StandardCharsets.UTF_8;
            }
            int mo93b = interfaceC0016g.mo93b(AbstractC0887c.f1938e);
            if (mo93b != -1) {
                if (mo93b == 0) {
                    charset = StandardCharsets.UTF_8;
                } else if (mo93b == 1) {
                    charset = StandardCharsets.UTF_16BE;
                } else if (mo93b == 2) {
                    charset = StandardCharsets.UTF_16LE;
                } else if (mo93b == 3) {
                    charset = AbstractC0887c.f1939f;
                } else {
                    if (mo93b != 4) {
                        throw new AssertionError();
                    }
                    charset = AbstractC0887c.f1940g;
                }
            }
            String mo112w = interfaceC0016g.mo112w(charset);
            interfaceC0016g.close();
            return mo112w;
        } catch (Throwable th) {
            if (interfaceC0016g != null) {
                try {
                    interfaceC0016g.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
