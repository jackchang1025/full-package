package t0;

import java.util.List;
import p0.e0;
import p0.f0;
import p0.j0;
import p0.v;
import p0.w;
import s0.l;

public final class f implements v {
   public final List a;
   public final l b;
   public final s0.e c;
   public final int d;
   public final f0 e;
   public final e0 f;
   public final int g;
   public final int h;
   public final int i;
   public int j;

   public f(List var1, l var2, s0.e var3, int var4, f0 var5, e0 var6, int var7, int var8, int var9) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
      this.h = var8;
      this.i = var9;
   }

   public final j0 a(f0 var1) {
      return this.b(var1, this.b, this.c);
   }

   public final j0 b(f0 var1, l var2, s0.e var3) {
      List var6 = this.a;
      int var5 = var6.size();
      int var4 = this.d;
      if (var4 < var5) {
         this.j++;
         s0.e var7 = this.c;
         if (var7 != null && !var7.a().j(var1.a)) {
            StringBuilder var10 = new StringBuilder("network interceptor ");
            var10.append(var6.get(var4 - 1));
            var10.append(" must retain the same host and port");
            throw new IllegalStateException(var10.toString());
         } else if (var7 != null && this.j > 1) {
            StringBuilder var9 = new StringBuilder("network interceptor ");
            var9.append(var6.get(var4 - 1));
            var9.append(" must call proceed() exactly once");
            throw new IllegalStateException(var9.toString());
         } else {
            List var16 = this.a;
            var5 = var4 + 1;
            f var11 = new f(var16, var2, var3, var5, var1, this.f, this.g, this.h, this.i);
            w var8 = (w)var6.get(var4);
            j0 var17 = var8.a(var11);
            if (var3 != null && var5 < var6.size() && var11.j != 1) {
               StringBuilder var14 = new StringBuilder("network interceptor ");
               var14.append(var8);
               var14.append(" must call proceed() exactly once");
               throw new IllegalStateException(var14.toString());
            } else if (var17 != null) {
               if (var17.g != null) {
                  return var17;
               } else {
                  StringBuilder var13 = new StringBuilder("interceptor ");
                  var13.append(var8);
                  var13.append(" returned a response with no body");
                  throw new IllegalStateException(var13.toString());
               }
            } else {
               StringBuilder var12 = new StringBuilder("interceptor ");
               var12.append(var8);
               var12.append(" returned null");
               throw new NullPointerException(var12.toString());
            }
         }
      } else {
         throw new AssertionError();
      }
   }

   @Override
   public final void finalize() {
      List var1 = this.a;
      if (!var1.isEmpty()) {
         var1.clear();
      }

      super.finalize();
   }
}
