package l0;

import java.util.HashMap;

public abstract class h extends f0.q implements g0.a {
   public String i;
   public final com.guard.wallet.http.h j = new com.guard.wallet.http.h(4);
   public f0.k k;
   public final g l;
   public final g m;
   public String n;
   public j0.a o;

   public h() {
      new HashMap();
      this.l = new g(this);
      this.m = new g(this);
   }

   @Override
   public final boolean e() {
      return ((f0.b)this.k).p;
   }

   @Override
   public final void h(g0.b var1) {
      ((f0.b)this.k).k = var1;
   }

   @Override
   public final g0.b k() {
      return ((f0.b)this.k).k;
   }

   public abstract void l();

   @Override
   public final String toString() {
      com.guard.wallet.http.h var1 = this.j;
      return var1 == null ? super.toString() : var1.l(this.i);
   }
}
