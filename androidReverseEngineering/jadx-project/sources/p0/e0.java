package p0;

import android.support.v4.app.NotificationCompat;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import com.guard.wallet.entity.BuildConfig;
import q0.AbstractC0887c;
import r0.C0895a;
import s0.C0898a;
import s0.C0909l;
import t0.C0912a;
import t0.C0917f;
import w0.C0966i;

/* loaded from: classes.dex */
public final class e0 implements Cloneable {

    /* renamed from: a */
    public final b0 f1771a;

    /* renamed from: b */
    public C0909l f1772b;

    /* renamed from: c */
    public final f0 f1773c;

    /* renamed from: d */
    public final boolean f1774d;

    /* renamed from: e */
    public boolean f1775e;

    public e0(b0 b0Var, f0 f0Var, boolean z2) {
        this.f1771a = b0Var;
        this.f1773c = f0Var;
        this.f1774d = z2;
    }

    /* renamed from: d */
    public static e0 m1246d(b0 b0Var, f0 f0Var, boolean z2) {
        e0 e0Var = new e0(b0Var, f0Var, z2);
        e0Var.f1772b = new C0909l(b0Var, e0Var);
        return e0Var;
    }

    /* renamed from: a */
    public final void m1247a(InterfaceC0863e interfaceC0863e) {
        d0 m1273a;
        synchronized (this) {
            if (this.f1775e) {
                throw new IllegalStateException("Already Executed");
            }
            this.f1775e = true;
        }
        C0909l c0909l = this.f1772b;
        c0909l.getClass();
        c0909l.f2058f = C0966i.f2293a.mo1453k();
        c0909l.f2056d.getClass();
        C0873o c0873o = this.f1771a.f1719a;
        d0 d0Var = new d0(this, interfaceC0863e);
        synchronized (c0873o) {
            try {
                c0873o.f1886b.add(d0Var);
                if (!this.f1774d && (m1273a = c0873o.m1273a(this.f1773c.f1777a.f1910d)) != null) {
                    d0Var.f1769c = m1273a.f1769c;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        c0873o.m1275c();
    }

    /* renamed from: b */
    public final j0 m1248b() {
        synchronized (this) {
            if (this.f1775e) {
                throw new IllegalStateException("Already Executed");
            }
            this.f1775e = true;
        }
        this.f1772b.f2057e.m71i();
        C0909l c0909l = this.f1772b;
        c0909l.getClass();
        c0909l.f2058f = C0966i.f2293a.mo1453k();
        c0909l.f2056d.getClass();
        try {
            C0873o c0873o = this.f1771a.f1719a;
            synchronized (c0873o) {
                c0873o.f1888d.add(this);
            }
            j0 m1249c = m1249c();
            C0873o c0873o2 = this.f1771a.f1719a;
            ArrayDeque arrayDeque = c0873o2.f1888d;
            synchronized (c0873o2) {
                if (!arrayDeque.remove(this)) {
                    throw new AssertionError("Call wasn't in-flight!");
                }
            }
            c0873o2.m1275c();
            return m1249c;
        } catch (Throwable th) {
            C0873o c0873o3 = this.f1771a.f1719a;
            ArrayDeque arrayDeque2 = c0873o3.f1888d;
            synchronized (c0873o3) {
                if (!arrayDeque2.remove(this)) {
                    throw new AssertionError("Call wasn't in-flight!");
                }
                c0873o3.m1275c();
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0088  */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final j0 m1249c() {
        boolean z2;
        ArrayList arrayList = new ArrayList();
        b0 b0Var = this.f1771a;
        arrayList.addAll(b0Var.f1722d);
        int i2 = 1;
        arrayList.add(new C0898a(b0Var, i2));
        arrayList.add(new C0895a(b0Var.f1726h, i2));
        int i3 = 0;
        arrayList.add(new C0895a(null, i3));
        arrayList.add(new C0898a(b0Var, i3));
        boolean z3 = this.f1774d;
        if (!z3) {
            arrayList.addAll(b0Var.f1723e);
        }
        arrayList.add(new C0912a(z3));
        try {
            j0 m1381a = new C0917f(arrayList, this.f1772b, null, 0, this.f1773c, this, b0Var.f1740v, b0Var.f1741w, b0Var.f1742x).m1381a(this.f1773c);
            C0909l c0909l = this.f1772b;
            synchronized (c0909l.f2054b) {
                z2 = c0909l.f2065m;
            }
            if (z2) {
                AbstractC0887c.m1306c(m1381a);
                throw new IOException("Canceled");
            }
            this.f1772b.m1366e(null);
            return m1381a;
        } catch (IOException e2) {
            try {
                throw this.f1772b.m1366e(e2);
            } catch (Throwable th) {
                th = th;
                if (i2 == 0) {
                    this.f1772b.m1366e(null);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            i2 = 0;
            if (i2 == 0) {
            }
            throw th;
        }
    }

    public final Object clone() {
        return m1246d(this.f1771a, this.f1773c, this.f1774d);
    }

    /* renamed from: e */
    public final String m1250e() {
        boolean z2;
        StringBuilder sb = new StringBuilder();
        C0909l c0909l = this.f1772b;
        synchronized (c0909l.f2054b) {
            z2 = c0909l.f2065m;
        }
        sb.append(z2 ? "canceled " : BuildConfig.FLAVOR);
        sb.append(this.f1774d ? "web socket" : NotificationCompat.CATEGORY_CALL);
        sb.append(" to ");
        sb.append(this.f1773c.f1777a.m1298m());
        return sb.toString();
    }
}
