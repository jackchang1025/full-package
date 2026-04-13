package u0;

import a1.i;
import a1.s;
import a1.v;

public final class b implements s {
   public final i a;
   public boolean b;
   public final g c;

   public b(g var1) {
      this.c = var1;
      this.a = new i(var1.d.a());
   }

   @Override
   public final v a() {
      return this.a;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void close() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label60: {
         boolean var1;
         try {
            var1 = this.b;
         } catch (Throwable var9) {
            var10000 = var9;
            boolean var10001 = false;
            break label60;
         }

         if (var1) {
            // $VF: monitorexit
            return;
         }

         try {
            this.b = true;
            this.c.d.s("0\r\n\r\n");
            g var3 = this.c;
            i var10 = this.a;
            var3.getClass();
            v var11 = var10.e;
            var10.e = v.d;
            var11.a();
            var11.b();
            this.c.e = 3;
         } catch (Throwable var8) {
            var10000 = var8;
            boolean var12 = false;
            break label60;
         }

         // $VF: monitorexit
         return;
      }

      Throwable var2 = var10000;
      // $VF: monitorexit
      throw var2;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void flush() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label60: {
         boolean var1;
         try {
            var1 = this.b;
         } catch (Throwable var8) {
            var10000 = var8;
            boolean var10001 = false;
            break label60;
         }

         if (var1) {
            // $VF: monitorexit
            return;
         }

         try {
            this.c.d.flush();
         } catch (Throwable var7) {
            var10000 = var7;
            boolean var9 = false;
            break label60;
         }

         // $VF: monitorexit
         return;
      }

      Throwable var2 = var10000;
      // $VF: monitorexit
      throw var2;
   }

   @Override
   public final void i(a1.e var1, long var2) {
      if (!this.b) {
         if (var2 != 0L) {
            g var4 = this.c;
            var4.d.e(var2);
            a1.f var5 = var4.d;
            var5.s("\r\n");
            var5.i(var1, var2);
            var5.s("\r\n");
         }
      } else {
         throw new IllegalStateException("closed");
      }
   }
}
