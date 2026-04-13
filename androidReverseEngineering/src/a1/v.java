package a1;

import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class v {
   public static final u d = new u();
   public boolean a;
   public long b;
   public long c;

   public v a() {
      this.a = false;
      return this;
   }

   public v b() {
      this.c = 0L;
      return this;
   }

   public long c() {
      if (this.a) {
         return this.b;
      } else {
         throw new IllegalStateException("No deadline");
      }
   }

   public v d(long var1) {
      this.a = true;
      this.b = var1;
      return this;
   }

   public boolean e() {
      return this.a;
   }

   public void f() {
      if (!Thread.interrupted()) {
         if (this.a && this.b - System.nanoTime() <= 0L) {
            throw new InterruptedIOException("deadline reached");
         }
      } else {
         Thread.currentThread().interrupt();
         throw new InterruptedIOException("interrupted");
      }
   }

   public v g(long var1, TimeUnit var3) {
      if (var1 >= 0L) {
         if (var3 != null) {
            this.c = var3.toNanos(var1);
            return this;
         } else {
            throw new IllegalArgumentException("unit == null");
         }
      } else {
         StringBuilder var4 = new StringBuilder("timeout < 0: ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString());
      }
   }
}
