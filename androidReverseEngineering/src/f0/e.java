package f0;

import java.util.PriorityQueue;

public final class e extends Thread {
   public final z a;
   public final PriorityQueue b;
   public final j c;

   public e(j var1, String var2, z var3, PriorityQueue var4) {
      super(var2);
      this.c = var1;
      this.a = var3;
      this.b = var4;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void run() {
      j var1 = this.c;
      boolean var4 = false /* VF: Semaphore variable */;

      ThreadLocal var2;
      try {
         var4 = true;
         var2 = j.h;
         var2.set(var1);
         j.a(var1, this.a, this.b);
         var4 = false;
      } finally {
         if (var4) {
            j.h.remove();
         }
      }

      var2.remove();
   }
}
