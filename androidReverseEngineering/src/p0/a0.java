package p0;

import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;

public final class a0 {
   public final o a;
   public final List b;
   public final List c;
   public final ArrayList d = new ArrayList();
   public final ArrayList e = new ArrayList();
   public final f0.l f;
   public final ProxySelector g;
   public n h;
   public final SocketFactory i;
   public final z0.c j;
   public final g k;
   public final m0.b l;
   public final m0.b m;
   public final com.guard.wallet.http.h n;
   public final m0.b o;
   public boolean p;
   public boolean q;
   public boolean r;
   public int s;
   public int t;
   public int u;
   public int v;
   public int w;

   public a0() {
      this.a = new o();
      this.b = b0.z;
      this.c = b0.A;
      this.f = new f0.l(p0.q.b);
      ProxySelector var1 = ProxySelector.getDefault();
      this.g = var1;
      if (var1 == null) {
         this.g = new x0.a();
      }

      this.h = p0.n.b;
      this.i = SocketFactory.getDefault();
      this.j = z0.c.a;
      this.k = p0.g.c;
      m0.b var2 = p0.b.a;
      this.l = var2;
      this.m = var2;
      this.n = new com.guard.wallet.http.h(8);
      this.o = p0.p.c;
      this.p = true;
      this.q = true;
      this.r = true;
      this.s = 0;
      this.t = 10000;
      this.u = 10000;
      this.v = 10000;
      this.w = 0;
   }
}
