package n0;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class b implements Iterator {
   public int a;
   public int b;
   public int c;
   public final c d;

   public b(c var1) {
      this.d = var1;
      this.a = var1.b;
      this.b = var1.c;
      this.c = -1;
   }

   @Override
   public final boolean hasNext() {
      boolean var1;
      if (this.a != this.b) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public final Object next() {
      int var1 = this.a;
      int var2 = this.b;
      if (var1 != var2) {
         c var3 = this.d;
         Object[] var4 = var3.a;
         Object var5 = var4[var1];
         if (var3.c == var2 && var5 != null) {
            this.c = var1;
            this.a = var1 + 1 & var4.length - 1;
            return var5;
         } else {
            throw new ConcurrentModificationException();
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public final void remove() {
      int var1 = this.c;
      if (var1 >= 0) {
         c var2 = this.d;
         if (var2.b(var1)) {
            this.a = this.a - 1 & var2.a.length - 1;
            this.b = var2.c;
         }

         this.c = -1;
      } else {
         throw new IllegalStateException();
      }
   }
}
