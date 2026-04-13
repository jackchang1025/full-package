package o;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class c0 {
   public final ExecutorService a = Executors.newFixedThreadPool(2);
   public final AtomicBoolean b;
   public final AtomicBoolean c;

   public c0() {
      new AtomicLong(0L);
      this.b = new AtomicBoolean(false);
      this.c = new AtomicBoolean(false);
   }

   public static boolean a(int var0) {
      boolean var2 = true;
      boolean var1 = var2;
      if (!Objects.equals(var0, 1)) {
         var1 = var2;
         if (!Objects.equals(var0, 2)) {
            var1 = var2;
            if (!Objects.equals(var0, 8388608)) {
               if (Objects.equals(var0, 8)) {
                  var1 = var2;
               } else {
                  var1 = false;
               }
            }
         }
      }

      return var1;
   }

   public static boolean b(int var0) {
      boolean var1;
      if (!Objects.equals(var0, 2048) && !Objects.equals(var0, 32) && !Objects.equals(var0, 16384) && !Objects.equals(var0, 4096) && !Objects.equals(var0, 4)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
