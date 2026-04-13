package n0;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class c extends AbstractCollection implements Queue, Cloneable, Serializable {
   public transient Object[] a = new Object[16];
   public transient int b;
   public transient int c;

   public final void a(Object[] var1) {
      int var2 = this.b;
      int var3 = this.c;
      if (var2 < var3) {
         System.arraycopy(this.a, var2, var1, 0, this.size());
      } else if (var2 > var3) {
         Object[] var4 = this.a;
         var3 = var4.length - var2;
         System.arraycopy(var4, var2, var1, 0, var3);
         System.arraycopy(this.a, 0, var1, var3, this.c);
      }
   }

   @Override
   public final boolean add(Object var1) {
      this.addLast(var1);
      return true;
   }

   public final void addFirst(Object var1) {
      if (var1 != null) {
         Object[] var3 = this.a;
         int var2 = this.b - 1 & var3.length - 1;
         this.b = var2;
         var3[var2] = var1;
         if (var2 == this.c) {
            this.c();
         }
      } else {
         throw new NullPointerException("e == null");
      }
   }

   public final void addLast(Object var1) {
      if (var1 != null) {
         Object[] var3 = this.a;
         int var2 = this.c;
         var3[var2] = var1;
         var2 = var3.length - 1 & var2 + 1;
         this.c = var2;
         if (var2 == this.b) {
            this.c();
         }
      } else {
         throw new NullPointerException("e == null");
      }
   }

   public final boolean b(int var1) {
      Object[] var7 = this.a;
      int var3 = var7.length - 1;
      int var2 = this.b;
      int var6 = this.c;
      int var4 = var1 - var2 & var3;
      int var5 = var6 - var1 & var3;
      if (var4 < (var6 - var2 & var3)) {
         if (var4 < var5) {
            if (var2 <= var1) {
               System.arraycopy(var7, var2, var7, var2 + 1, var4);
            } else {
               System.arraycopy(var7, 0, var7, 1, var1);
               var7[0] = var7[var3];
               System.arraycopy(var7, var2, var7, var2 + 1, var3 - var2);
            }

            var7[var2] = null;
            this.b = var2 + 1 & var3;
            return false;
         } else {
            if (var1 < var6) {
               System.arraycopy(var7, var1 + 1, var7, var1, var5);
               this.c = var6 - 1;
            } else {
               System.arraycopy(var7, var1 + 1, var7, var1, var3 - var1);
               var7[var3] = var7[0];
               System.arraycopy(var7, 1, var7, 0, var6);
               this.c = var6 - 1 & var3;
            }

            return true;
         }
      } else {
         throw new ConcurrentModificationException();
      }
   }

   public final void c() {
      int var3 = this.b;
      Object[] var6 = this.a;
      int var1 = var6.length;
      int var2 = var1 - var3;
      int var4 = var1 << 1;
      if (var4 >= 0) {
         Object[] var5 = new Object[var4];
         System.arraycopy(var6, var3, var5, 0, var2);
         System.arraycopy(this.a, 0, var5, var2, var3);
         this.a = var5;
         this.b = 0;
         this.c = var1;
      } else {
         throw new IllegalStateException("Sorry, deque too big");
      }
   }

   @Override
   public final void clear() {
      int var1 = this.b;
      int var4 = this.c;
      if (var1 != var4) {
         this.c = 0;
         this.b = 0;
         int var3 = this.a.length;

         int var2;
         do {
            this.a[var1] = null;
            var2 = var1 + 1 & var3 - 1;
            var1 = var2;
         } while (var2 != var4);
      }
   }

   @Override
   public final Object clone() {
      try {
         c var2 = (c)super.clone();
         Object[] var1 = this.a;
         System.arraycopy(var1, 0, var2.a, 0, var1.length);
         return var2;
      } catch (CloneNotSupportedException var3) {
         throw new AssertionError();
      }
   }

   @Override
   public final boolean contains(Object var1) {
      if (var1 == null) {
         return false;
      } else {
         int var3 = this.a.length;
         int var2 = this.b;

         while (true) {
            Object var4 = this.a[var2];
            if (var4 == null) {
               return false;
            }

            if (var1.equals(var4)) {
               return true;
            }

            var2 = var2 + 1 & var3 - 1;
         }
      }
   }

   @Override
   public final Object element() {
      Object var1 = this.a[this.b];
      if (var1 != null) {
         return var1;
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public final boolean isEmpty() {
      boolean var1;
      if (this.b == this.c) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public final Iterator iterator() {
      return new b(this);
   }

   @Override
   public final boolean offer(Object var1) {
      this.addLast(var1);
      return true;
   }

   @Override
   public final Object peek() {
      return this.a[this.b];
   }

   @Override
   public final Object poll() {
      int var1 = this.b;
      Object[] var3 = this.a;
      Object var2 = var3[var1];
      if (var2 == null) {
         var2 = null;
      } else {
         var3[var1] = null;
         this.b = var1 + 1 & var3.length - 1;
      }

      return var2;
   }

   @Override
   public final Object remove() {
      int var1 = this.b;
      Object[] var3 = this.a;
      Object var2 = var3[var1];
      if (var2 == null) {
         var2 = null;
      } else {
         var3[var1] = null;
         this.b = var1 + 1 & var3.length - 1;
      }

      if (var2 != null) {
         return var2;
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public final boolean remove(Object var1) {
      boolean var5 = false;
      boolean var4;
      if (var1 == null) {
         var4 = var5;
      } else {
         int var3 = this.a.length;
         int var2 = this.b;

         while (true) {
            Object var6 = this.a[var2];
            var4 = var5;
            if (var6 == null) {
               break;
            }

            if (var1.equals(var6)) {
               this.b(var2);
               var4 = true;
               break;
            }

            var2 = var2 + 1 & var3 - 1;
         }
      }

      return var4;
   }

   @Override
   public final int size() {
      return this.c - this.b & this.a.length - 1;
   }

   @Override
   public final Object[] toArray() {
      Object[] var1 = new Object[this.size()];
      this.a(var1);
      return var1;
   }

   @Override
   public final Object[] toArray(Object[] var1) {
      int var2 = this.size();
      Object[] var3 = var1;
      if (var1.length < var2) {
         var3 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var2);
      }

      this.a(var3);
      if (var3.length > var2) {
         var3[var2] = null;
      }

      return var3;
   }
}
