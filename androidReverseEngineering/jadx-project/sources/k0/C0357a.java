package k0;

import a1.AbstractC0026q;
import f0.AbstractC0296q;
import f0.C0292m;
import f0.InterfaceC0294o;
import i0.C0331b;
import p000a.AbstractC0000a;
import p014r.AbstractC0888a;

/* renamed from: k0.a */
/* loaded from: classes.dex */
public final class C0357a extends AbstractC0296q {

    /* renamed from: i */
    public int f702i = 0;

    /* renamed from: j */
    public int f703j = 0;

    /* renamed from: k */
    public int f704k = 1;

    /* renamed from: l */
    public final C0292m f705l = new C0292m();

    @Override // f0.AbstractC0296q, g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        int i2;
        int i3;
        C0292m c0292m2 = this.f705l;
        if (this.f704k == 8) {
            c0292m.m811k();
            return;
        }
        while (c0292m.f541c > 0) {
            try {
                int m1325a = AbstractC0888a.m1325a(this.f704k);
                if (m1325a == 0) {
                    char m808f = c0292m.m808f();
                    if (m808f == '\r') {
                        this.f704k = 2;
                    } else {
                        int i4 = this.f702i * 16;
                        this.f702i = i4;
                        if (m808f >= 'a' && m808f <= 'f') {
                            i2 = -97;
                        } else if (m808f >= '0' && m808f <= '9') {
                            i3 = (m808f - '0') + i4;
                            this.f702i = i3;
                        } else {
                            if (m808f < 'A' || m808f > 'F') {
                                mo813c(new C0331b("invalid chunk length: " + m808f));
                                return;
                            }
                            i2 = -65;
                        }
                        i3 = AbstractC0000a.m5a(m808f, i2, 10, i4);
                        this.f702i = i3;
                    }
                    this.f703j = this.f702i;
                } else if (m1325a != 1) {
                    if (m1325a == 3) {
                        int min = Math.min(this.f703j, c0292m.f541c);
                        int i5 = this.f703j - min;
                        this.f703j = i5;
                        if (i5 == 0) {
                            this.f704k = 5;
                        }
                        if (min != 0) {
                            c0292m.m806d(c0292m2, min);
                            AbstractC0026q.m183p(this, c0292m2);
                        }
                    } else if (m1325a != 4) {
                        if (m1325a != 5) {
                            if (m1325a == 6) {
                                return;
                            }
                        } else {
                            if (!m937l(c0292m.m808f(), '\n')) {
                                return;
                            }
                            if (this.f702i > 0) {
                                this.f704k = 1;
                            } else {
                                this.f704k = 7;
                                mo813c(null);
                            }
                            this.f702i = 0;
                        }
                    } else if (!m937l(c0292m.m808f(), '\r')) {
                        return;
                    } else {
                        this.f704k = 6;
                    }
                } else if (!m937l(c0292m.m808f(), '\n')) {
                    return;
                } else {
                    this.f704k = 4;
                }
            } catch (Exception e2) {
                mo813c(e2);
                return;
            }
        }
    }

    @Override // f0.AbstractC0296q
    /* renamed from: c */
    public final void mo813c(Exception exc) {
        if (exc == null && this.f704k != 7) {
            exc = new C0331b("chunked input ended before final chunk");
        }
        super.mo813c(exc);
    }

    /* renamed from: l */
    public final boolean m937l(char c, char c2) {
        if (c == c2) {
            return true;
        }
        this.f704k = 8;
        mo813c(new C0331b(c2 + " was expected, got " + c));
        return false;
    }
}
