package p0;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

public final class h0 extends a1.q {
   public final x o;
   public final File p;

   public h0(x var1, File var2) {
      this.o = var1;
      this.p = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void V(a1.f var1) {
      Logger var2 = a1.l.a;
      File var9 = this.p;
      if (var9 != null) {
         FileInputStream var10 = new FileInputStream(var9);
         a1.b var11 = new a1.b(new a1.v(), var10);

         try {
            var1.d(var11);
         } catch (Throwable var8) {
            try {
               var11.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
               throw var8;
            }

            throw var8;
         }

         var11.close();
      } else {
         throw new IllegalArgumentException("file == null");
      }
   }

   @Override
   public final long i() {
      return this.p.length();
   }

   @Override
   public final x j() {
      return this.o;
   }
}
