package l0;

import b0.C0078b;
import f0.AbstractC0296q;
import j0.InterfaceC0351a;
import java.net.ProtocolException;
import com.guard.wallet.entity.BuildConfig;
import p0.c0;

/* renamed from: l0.q */
/* loaded from: classes.dex */
public final class C0387q implements InterfaceC0351a {

    /* renamed from: d */
    public final /* synthetic */ int f783d;

    /* renamed from: e */
    public final int f784e;

    /* renamed from: f */
    public final String f785f;

    /* renamed from: g */
    public Object f786g;

    public C0387q(String str) {
        this.f783d = 0;
        this.f784e = -1;
        this.f785f = str;
    }

    /* renamed from: a */
    public static C0387q m959a(String str) {
        int i2;
        String str2;
        boolean startsWith = str.startsWith("HTTP/1.");
        c0 c0Var = c0.HTTP_1_0;
        if (startsWith) {
            i2 = 9;
            if (str.length() < 9 || str.charAt(8) != ' ') {
                throw new ProtocolException("Unexpected status line: ".concat(str));
            }
            int charAt = str.charAt(7) - '0';
            if (charAt != 0) {
                if (charAt != 1) {
                    throw new ProtocolException("Unexpected status line: ".concat(str));
                }
                c0Var = c0.HTTP_1_1;
            }
        } else {
            if (!str.startsWith("ICY ")) {
                throw new ProtocolException("Unexpected status line: ".concat(str));
            }
            i2 = 4;
        }
        int i3 = i2 + 3;
        if (str.length() < i3) {
            throw new ProtocolException("Unexpected status line: ".concat(str));
        }
        try {
            int parseInt = Integer.parseInt(str.substring(i2, i3));
            if (str.length() <= i3) {
                str2 = BuildConfig.FLAVOR;
            } else {
                if (str.charAt(i3) != ' ') {
                    throw new ProtocolException("Unexpected status line: ".concat(str));
                }
                str2 = str.substring(i2 + 4);
            }
            return new C0387q(c0Var, parseInt, str2);
        } catch (NumberFormatException unused) {
            throw new ProtocolException("Unexpected status line: ".concat(str));
        }
    }

    @Override // j0.InterfaceC0351a
    /* renamed from: d */
    public final void mo589d(AbstractC0296q abstractC0296q, C0377g c0377g) {
        this.f786g = abstractC0296q;
        abstractC0296q.f544e = c0377g;
        abstractC0296q.mo783h(new C0078b(24));
    }

    @Override // j0.InterfaceC0351a
    /* renamed from: f */
    public final boolean mo590f() {
        return false;
    }

    @Override // j0.InterfaceC0351a
    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    @Override // j0.InterfaceC0351a
    public final int length() {
        return this.f784e;
    }

    public final String toString() {
        switch (this.f783d) {
            case 1:
                StringBuilder sb = new StringBuilder();
                sb.append(((c0) this.f786g) == c0.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1");
                sb.append(' ');
                sb.append(this.f784e);
                String str = this.f785f;
                if (str != null) {
                    sb.append(' ');
                    sb.append(str);
                }
                return sb.toString();
            default:
                return super.toString();
        }
    }

    public C0387q(c0 c0Var, int i2, String str) {
        this.f783d = 1;
        this.f786g = c0Var;
        this.f784e = i2;
        this.f785f = str;
    }
}
