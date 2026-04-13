package v0;

import java.io.IOException;

public final class j extends q0.a {
   public final int b;
   public final Object c;

   public j(s var1) {
      this.b = 1;
      this.c = var1;
      super(new Object[]{var1.d}, "OkHttp %s ping");
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void a() {
      switch (this.b) {
         case 0:
            s var37 = (s)this.c;
            var37.getClass();

            try {
               var37.u.B(false, 2, 0);
            } catch (IOException var30) {
               var37.y(var30);
            }

            return;
         case 1:
            s var35 = (s)this.c;
            synchronized (var35){} // $VF: monitorenter 

            boolean var1;
            Object var4;
            label214: {
               Throwable var10000;
               label207: {
                  label206: {
                     label215: {
                        try {
                           var4 = this.c;
                           if (((s)var4).l < ((s)var4).k) {
                              break label215;
                           }
                        } catch (Throwable var34) {
                           var10000 = var34;
                           boolean var10001 = false;
                           break label207;
                        }

                        try {
                           s var3 = (s)var4;
                           var3.k++;
                        } catch (Throwable var33) {
                           var10000 = var33;
                           boolean var39 = false;
                           break label207;
                        }

                        var1 = false;
                        break label206;
                     }

                     var1 = true;
                  }

                  label197:
                  try {
                     // $VF: monitorexit
                     break label214;
                  } catch (Throwable var32) {
                     var10000 = var32;
                     boolean var40 = false;
                     break label197;
                  }
               }

               while (true) {
                  Throwable var38 = var10000;

                  try {
                     // $VF: monitorexit
                     throw var38;
                  } catch (Throwable var31) {
                     var10000 = var31;
                     boolean var41 = false;
                     continue;
                  }
               }
            }

            var35 = (s)var4;
            if (var1) {
               var35.y(null);
            } else {
               var35.getClass();

               try {
                  var35.u.B(false, 1, 0);
               } catch (IOException var29) {
                  var35.y(var29);
               }
            }

            return;
         default:
            s var2 = (s)((q)this.c).d;
            var2.b.a(var2);
      }
   }
}
