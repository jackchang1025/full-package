package v0;

import java.io.IOException;
import java.util.Arrays;
import p022z.C0981d;
import q0.AbstractRunnableC0885a;

/* renamed from: v0.r */
/* loaded from: classes.dex */
public final class C0947r extends AbstractRunnableC0885a {

    /* renamed from: b */
    public final /* synthetic */ boolean f2196b = false;

    /* renamed from: c */
    public final /* synthetic */ C0981d f2197c;

    /* renamed from: d */
    public final /* synthetic */ C0946q f2198d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0947r(C0946q c0946q, Object[] objArr, C0981d c0981d) {
        super(objArr, "OkHttp %s ACK Settings");
        this.f2198d = c0946q;
        this.f2197c = c0981d;
    }

    @Override // q0.AbstractRunnableC0885a
    /* renamed from: a */
    public final void mo1245a() {
        C0954y[] c0954yArr;
        long j2;
        C0946q c0946q = this.f2198d;
        boolean z2 = this.f2196b;
        C0981d c0981d = this.f2197c;
        synchronized (((C0948s) c0946q.f2195d).f2220u) {
            synchronized (((C0948s) c0946q.f2195d)) {
                try {
                    int m1473d = ((C0948s) c0946q.f2195d).f2218s.m1473d();
                    if (z2) {
                        C0981d c0981d2 = ((C0948s) c0946q.f2195d).f2218s;
                        c0981d2.f2326b = 0;
                        Arrays.fill((int[]) c0981d2.f2327c, 0);
                    }
                    C0981d c0981d3 = ((C0948s) c0946q.f2195d).f2218s;
                    c0981d3.getClass();
                    int i2 = 0;
                    while (true) {
                        boolean z3 = true;
                        if (i2 >= 10) {
                            break;
                        }
                        if (((1 << i2) & c0981d.f2326b) == 0) {
                            z3 = false;
                        }
                        if (z3) {
                            c0981d3.m1474e(i2, ((int[]) c0981d.f2327c)[i2]);
                        }
                        i2++;
                    }
                    int m1473d2 = ((C0948s) c0946q.f2195d).f2218s.m1473d();
                    c0954yArr = null;
                    if (m1473d2 == -1 || m1473d2 == m1473d) {
                        j2 = 0;
                    } else {
                        j2 = m1473d2 - m1473d;
                        if (!((C0948s) c0946q.f2195d).f2202c.isEmpty()) {
                            c0954yArr = (C0954y[]) ((C0948s) c0946q.f2195d).f2202c.values().toArray(new C0954y[((C0948s) c0946q.f2195d).f2202c.size()]);
                        }
                    }
                } finally {
                }
            }
            try {
                Object obj = c0946q.f2195d;
                ((C0948s) obj).f2220u.m1440x(((C0948s) obj).f2218s);
            } catch (IOException e2) {
                ((C0948s) c0946q.f2195d).m1416y(e2);
            }
        }
        if (c0954yArr != null) {
            for (C0954y c0954y : c0954yArr) {
                synchronized (c0954y) {
                    c0954y.f2253b += j2;
                    if (j2 > 0) {
                        c0954y.notifyAll();
                    }
                }
            }
        }
        C0948s.f2199x.execute(new C0939j(c0946q, "OkHttp %s settings", new Object[]{((C0948s) c0946q.f2195d).f2203d}, 2));
    }
}
