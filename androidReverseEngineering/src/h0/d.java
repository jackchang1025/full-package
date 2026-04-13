package h0;

public abstract class d implements a {
   public boolean a;
   public boolean b;
   public a c;

   static {
      new c(0);
      new c(1);
   }

   public void a() {
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean b() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label146: {
         try {
            if (this.b) {
               // $VF: monitorexit
               return false;
            }
         } catch (Throwable var20) {
            var10000 = var20;
            boolean var10001 = false;
            break label146;
         }

         try {
            if (this.a) {
               // $VF: monitorexit
               return false;
            }
         } catch (Throwable var21) {
            var10000 = var21;
            boolean var22 = false;
            break label146;
         }

         label133:
         try {
            this.a = true;
            this.c = null;
            // $VF: monitorexit
            return true;
         } catch (Throwable var19) {
            var10000 = var19;
            boolean var23 = false;
            break label133;
         }
      }

      while (true) {
         Throwable var1 = var10000;

         try {
            // $VF: monitorexit
            throw var1;
         } catch (Throwable var18) {
            var10000 = var18;
            boolean var24 = false;
            continue;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public boolean cancel() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label163: {
         try {
            if (this.a) {
               // $VF: monitorexit
               return false;
            }
         } catch (Throwable var20) {
            var10000 = var20;
            boolean var10001 = false;
            break label163;
         }

         try {
            if (this.b) {
               // $VF: monitorexit
               return true;
            }
         } catch (Throwable var21) {
            var10000 = var21;
            boolean var23 = false;
            break label163;
         }

         a var22;
         try {
            this.b = true;
            var22 = this.c;
            this.c = null;
            // $VF: monitorexit
         } catch (Throwable var19) {
            var10000 = var19;
            boolean var24 = false;
            break label163;
         }

         if (var22 != null) {
            var22.cancel();
         }

         this.a();
         return true;
      }

      while (true) {
         Throwable var1 = var10000;

         try {
            // $VF: monitorexit
            throw var1;
         } catch (Throwable var18) {
            var10000 = var18;
            boolean var25 = false;
            continue;
         }
      }
   }

   public boolean cancel(boolean var1) {
      return this.cancel();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final boolean isCancelled() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label168: {
         boolean var1;
         label167: {
            label166: {
               label172: {
                  a var2;
                  try {
                     if (this.b) {
                        break label172;
                     }

                     var2 = this.c;
                  } catch (Throwable var22) {
                     var10000 = var22;
                     boolean var10001 = false;
                     break label168;
                  }

                  if (var2 == null) {
                     break label166;
                  }

                  try {
                     if (!var2.isCancelled()) {
                        break label166;
                     }
                  } catch (Throwable var21) {
                     var10000 = var21;
                     boolean var24 = false;
                     break label168;
                  }
               }

               var1 = true;
               break label167;
            }

            var1 = false;
         }

         label153:
         try {
            // $VF: monitorexit
            return var1;
         } catch (Throwable var20) {
            var10000 = var20;
            boolean var25 = false;
            break label153;
         }
      }

      while (true) {
         Throwable var23 = var10000;

         try {
            // $VF: monitorexit
            throw var23;
         } catch (Throwable var19) {
            var10000 = var19;
            boolean var26 = false;
            continue;
         }
      }
   }

   public final boolean isDone() {
      return this.a;
   }
}
