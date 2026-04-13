package f0;

import android.os.Looper;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public final class m {
   public static final PriorityQueue d = new PriorityQueue(8, new n.a(3));
   public static final int e = 1048576;
   public static final int f = 262144;
   public static int g = 0;
   public static int h = 0;
   public static final Object i = new Object();
   public static final ByteBuffer j = ByteBuffer.allocate(0);
   public final n0.c a = new n0.c();
   public ByteOrder b = ByteOrder.BIG_ENDIAN;
   public int c = 0;

   public m() {
   }

   public m(byte[] var1) {
      this.a(ByteBuffer.wrap(var1));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static ByteBuffer g(int var0) {
      if (var0 <= h) {
         Looper var1 = Looper.getMainLooper();
         PriorityQueue var24;
         if (var1 != null && Thread.currentThread() == var1.getThread()) {
            var24 = null;
         } else {
            var24 = d;
         }

         if (var24 != null) {
            Object var2 = i;
            synchronized (var2){} // $VF: monitorenter 

            Throwable var10000;
            while (true) {
               ByteBuffer var3;
               label219: {
                  try {
                     if (var24.size() > 0) {
                        var3 = (ByteBuffer)var24.remove();
                        if (var24.size() == 0) {
                           h = 0;
                        }
                        break label219;
                     }
                  } catch (Throwable var23) {
                     var10000 = var23;
                     boolean var10001 = false;
                     break;
                  }

                  try {
                     // $VF: monitorexit
                     return ByteBuffer.allocate(Math.max(8192, var0));
                  } catch (Throwable var22) {
                     var10000 = var22;
                     boolean var26 = false;
                     break;
                  }
               }

               try {
                  g = g - var3.capacity();
                  if (var3.capacity() >= var0) {
                     // $VF: monitorexit
                     return var3;
                  }
               } catch (Throwable var21) {
                  var10000 = var21;
                  boolean var27 = false;
                  break;
               }
            }

            while (true) {
               Throwable var25 = var10000;

               try {
                  // $VF: monitorexit
                  throw var25;
               } catch (Throwable var20) {
                  var10000 = var20;
                  boolean var28 = false;
                  continue;
               }
            }
         }
      }

      return ByteBuffer.allocate(Math.max(8192, var0));
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void j(ByteBuffer var0) {
      if (var0 != null && !var0.isDirect() && var0.arrayOffset() == 0 && var0.array().length == var0.capacity()) {
         if (var0.capacity() >= 8192) {
            if (var0.capacity() <= f) {
               Looper var3 = Looper.getMainLooper();
               PriorityQueue var37;
               if (var3 != null && Thread.currentThread() == var3.getThread()) {
                  var37 = null;
               } else {
                  var37 = d;
               }

               if (var37 != null) {
                  Object var4 = i;
                  synchronized (var4){} // $VF: monitorenter 

                  while (true) {
                     Throwable var10000;
                     label337: {
                        int var1;
                        int var2;
                        try {
                           var2 = g;
                           var1 = e;
                        } catch (Throwable var34) {
                           var10000 = var34;
                           boolean var10001 = false;
                           break label337;
                        }

                        if (var2 > var1) {
                           try {
                              if (var37.size() > 0 && ((ByteBuffer)var37.peek()).capacity() < var0.capacity()) {
                                 ByteBuffer var5 = (ByteBuffer)var37.remove();
                                 g = g - var5.capacity();
                                 continue;
                              }
                           } catch (Throwable var33) {
                              var10000 = var33;
                              boolean var38 = false;
                              break label337;
                           }
                        }

                        try {
                           if (g > var1) {
                              // $VF: monitorexit
                              return;
                           }
                        } catch (Throwable var35) {
                           var10000 = var35;
                           boolean var39 = false;
                           break label337;
                        }

                        label300:
                        try {
                           ((Buffer)var0).position(0);
                           ((Buffer)var0).limit(var0.capacity());
                           g = g + var0.capacity();
                           var37.add(var0);
                           h = Math.max(h, var0.capacity());
                           // $VF: monitorexit
                           return;
                        } catch (Throwable var32) {
                           var10000 = var32;
                           boolean var40 = false;
                           break label300;
                        }
                     }

                     while (true) {
                        Throwable var36 = var10000;

                        try {
                           // $VF: monitorexit
                           throw var36;
                        } catch (Throwable var31) {
                           var10000 = var31;
                           boolean var41 = false;
                           continue;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public final void a(ByteBuffer var1) {
      if (var1.remaining() <= 0) {
         j(var1);
      } else {
         int var2 = var1.remaining();
         int var3 = this.c;
         if (var3 >= 0) {
            this.c = var3 + var2;
         }

         label21: {
            n0.c var4 = this.a;
            if (var4.size() > 0) {
               Object[] var5 = var4.a;
               var5 = (Object[])var5[var4.c - 1 & var5.length - 1];
               if (var5 == null) {
                  throw new NoSuchElementException();
               }

               ByteBuffer var7 = (ByteBuffer)var5;
               if (var7.capacity() - var7.limit() >= var1.remaining()) {
                  ((Buffer)var7).mark();
                  ((Buffer)var7).position(var7.limit());
                  ((Buffer)var7).limit(var7.capacity());
                  var7.put(var1);
                  ((Buffer)var7).limit(var7.position());
                  ((Buffer)var7).reset();
                  j(var1);
                  break label21;
               }
            }

            var4.addLast(var1);
         }

         this.i(0);
      }
   }

   public final void b(ByteBuffer var1) {
      if (var1.remaining() <= 0) {
         j(var1);
      } else {
         int var3 = var1.remaining();
         int var2 = this.c;
         if (var2 >= 0) {
            this.c = var2 + var3;
         }

         n0.c var4 = this.a;
         if (var4.size() > 0) {
            ByteBuffer var5 = (ByteBuffer)var4.a[var4.b];
            if (var5 == null) {
               throw new NoSuchElementException();
            }

            var5 = var5;
            if (var5.position() >= var1.remaining()) {
               ((Buffer)var5).position(var5.position() - var1.remaining());
               ((Buffer)var5).mark();
               var5.put(var1);
               ((Buffer)var5).reset();
               j(var1);
               return;
            }
         }

         var4.addFirst(var1);
      }
   }

   public final void c(m var1) {
      this.d(var1, this.c);
   }

   public final void d(m var1, int var2) {
      if (this.c < var2) {
         throw new IllegalArgumentException("length");
      } else {
         int var3 = 0;

         while (var3 < var2) {
            n0.c var5 = this.a;
            ByteBuffer var6 = (ByteBuffer)var5.remove();
            int var4 = var6.remaining();
            if (var4 == 0) {
               j(var6);
            } else {
               var4 += var3;
               if (var4 > var2) {
                  var3 = var2 - var3;
                  ByteBuffer var7 = g(var3);
                  ((Buffer)var7).limit(var3);
                  var6.get(var7.array(), 0, var3);
                  var1.a(var7);
                  var5.addFirst(var6);
                  break;
               }

               var1.a(var6);
               var3 = var4;
            }
         }

         this.c -= var2;
      }
   }

   public final void e(byte[] var1) {
      int var4 = var1.length;
      if (this.c >= var4) {
         int var3 = 0;
         int var2 = var4;

         while (var2 > 0) {
            n0.c var8 = this.a;
            ByteBuffer var7 = (ByteBuffer)var8.peek();
            int var6 = Math.min(var7.remaining(), var2);
            var7.get(var1, var3, var6);
            int var5 = var2 - var6;
            var6 = var3 + var6;
            var3 = var6;
            var2 = var5;
            if (var7.remaining() == 0) {
               ByteBuffer var10 = (ByteBuffer)var8.remove();
               j(var7);
               var3 = var6;
               var2 = var5;
            }
         }

         this.c -= var4;
      } else {
         throw new IllegalArgumentException("length");
      }
   }

   public final char f() {
      char var1 = (char)this.i(1).get();
      this.c--;
      return var1;
   }

   public final String h(Charset var1) {
      Charset var5 = var1;
      if (var1 == null) {
         var5 = n0.d.a;
      }

      StringBuilder var6 = new StringBuilder();
      Iterator var7 = this.a.iterator();

      while (true) {
         n0.b var9 = (n0.b)var7;
         if (!var9.hasNext()) {
            return var6.toString();
         }

         ByteBuffer var8 = (ByteBuffer)var9.next();
         int var2;
         int var3;
         byte[] var10;
         if (var8.isDirect()) {
            var10 = new byte[var8.remaining()];
            var3 = var8.remaining();
            var8.get(var10);
            var2 = 0;
         } else {
            var10 = var8.array();
            int var4 = var8.arrayOffset();
            var2 = var8.position();
            var3 = var8.remaining();
            var2 = var4 + var2;
         }

         var6.append(new String(var10, var2, var3, var5));
      }
   }

   public final ByteBuffer i(int var1) {
      if (this.c < var1) {
         StringBuilder var11 = new StringBuilder("count : ");
         var11.append(this.c);
         var11.append("/");
         var11.append(var1);
         throw new IllegalArgumentException(var11.toString());
      } else {
         n0.c var7 = this.a;

         while (true) {
            ByteBuffer var4 = (ByteBuffer)var7.peek();
            if (var4 == null || var4.hasRemaining()) {
               if (var4 == null) {
                  return j;
               } else {
                  if (var4.remaining() < var1) {
                     ByteBuffer var5 = g(var1);
                     ((Buffer)var5).limit(var1);
                     byte[] var8 = var5.array();
                     int var2 = 0;

                     while (true) {
                        var4 = null;

                        ByteBuffer var6;
                        int var9;
                        do {
                           if (var2 >= var1) {
                              if (var4 != null && var4.remaining() > 0) {
                                 var7.addFirst(var4);
                              }

                              var7.addFirst(var5);
                              return var5.order(this.b);
                           }

                           var6 = (ByteBuffer)var7.remove();
                           int var3 = Math.min(var1 - var2, var6.remaining());
                           var6.get(var8, var2, var3);
                           var9 = var2 + var3;
                           var2 = var9;
                           var4 = var6;
                        } while (var6.remaining() != 0);

                        j(var6);
                        var2 = var9;
                     }
                  }

                  return var4.order(this.b);
               }
            }

            j((ByteBuffer)var7.remove());
         }
      }
   }

   public final void k() {
      while (true) {
         n0.c var1 = this.a;
         if (var1.size() <= 0) {
            this.c = 0;
            return;
         }

         j((ByteBuffer)var1.remove());
      }
   }

   public final ByteBuffer l() {
      ByteBuffer var1 = (ByteBuffer)this.a.remove();
      this.c = this.c - var1.remaining();
      return var1;
   }
}
