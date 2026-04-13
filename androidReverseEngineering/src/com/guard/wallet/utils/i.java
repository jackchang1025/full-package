package com.guard.wallet.utils;

public final class i {
   public final long a;
   public long b = 0L;
   public long c = -1L;

   public i(long var1) {
      if (var1 <= 31L && var1 >= 0L) {
         long var3 = ~(-1L << (int)1000L);
         if (1000L <= var3) {
            this.a = var1;
         } else {
            throw new IllegalArgumentException(String.format("maxDataCenterId Id can't be greater than %d or less than 0", var3));
         }
      } else {
         throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final long a() {
      synchronized (this){} // $VF: monitorenter 

      Throwable var10000;
      label364: {
         long var4;
         long var8;
         try {
            var4 = System.currentTimeMillis();
            var8 = this.c;
         } catch (Throwable var82) {
            var10000 = var82;
            boolean var10001 = false;
            break label364;
         }

         label361:
         if (var4 >= var8) {
            if (var8 == var4) {
               long var6;
               try {
                  var6 = ~(-1L << (int)12L) & this.b + 1L;
                  this.b = var6;
               } catch (Throwable var80) {
                  var10000 = var80;
                  boolean var87 = false;
                  break label361;
               }

               if (var6 == 0L) {
                  do {
                     try {
                        var6 = System.currentTimeMillis();
                     } catch (Throwable var79) {
                        var10000 = var79;
                        boolean var88 = false;
                        break label361;
                     }

                     var4 = var6;
                  } while (var6 <= var8);
               }
            } else {
               try {
                  this.b = 0L;
               } catch (Throwable var78) {
                  var10000 = var78;
                  boolean var89 = false;
                  break label361;
               }
            }

            try {
               this.c = var4;
            } catch (Throwable var77) {
               var10000 = var77;
               boolean var90 = false;
               break label361;
            }

            int var2 = (int)22L;
            int var3 = (int)17L;

            try {
               var8 = this.a;
            } catch (Throwable var76) {
               var10000 = var76;
               boolean var91 = false;
               break label361;
            }

            int var1 = (int)12L;

            long var84;
            try {
               var84 = this.b;
            } catch (Throwable var75) {
               var10000 = var75;
               boolean var92 = false;
               break label361;
            }

            // $VF: monitorexit
            return var4 - 1565020800000L << var2 | 1000L << var3 | var8 << var1 | var84;
         } else {
            label360:
            try {
               RuntimeException var86 = new RuntimeException(
                  String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.c - var4)
               );
               throw var86;
            } catch (Throwable var81) {
               var10000 = var81;
               boolean var93 = false;
               break label360;
            }
         }
      }

      Throwable var10 = var10000;
      // $VF: monitorexit
      throw var10;
   }
}
