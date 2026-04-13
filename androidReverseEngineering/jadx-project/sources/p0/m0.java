package p0;

import java.net.InetSocketAddress;
import java.net.Proxy;

/* loaded from: classes.dex */
public final class m0 {

    /* renamed from: a */
    public final C0859a f1874a;

    /* renamed from: b */
    public final Proxy f1875b;

    /* renamed from: c */
    public final InetSocketAddress f1876c;

    public m0(C0859a c0859a, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (c0859a == null) {
            throw new NullPointerException("address == null");
        }
        if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        }
        this.f1874a = c0859a;
        this.f1875b = proxy;
        this.f1876c = inetSocketAddress;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof m0) {
            m0 m0Var = (m0) obj;
            if (m0Var.f1874a.equals(this.f1874a) && m0Var.f1875b.equals(this.f1875b) && m0Var.f1876c.equals(this.f1876c)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f1876c.hashCode() + ((this.f1875b.hashCode() + ((this.f1874a.hashCode() + 527) * 31)) * 31);
    }

    public final String toString() {
        return "Route{" + this.f1876c + "}";
    }
}
