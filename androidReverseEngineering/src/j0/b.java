package j0;

import android.text.TextUtils;
import com.guard.wallet.http.h;
import com.guard.wallet.thread.j;
import f0.m;
import f0.o;
import f0.q;
import f0.t;
import i0.e;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import l0.g;

public final class b extends q implements a {
   public byte[] i;
   public int j = 2;
   public h k;
   public m l;
   public c m;
   public ArrayList n;

   public b(String var1) {
      var1 = i0.e.c(var1, ";", true, null).a("boundary");
      if (var1 == null) {
         this.c(new Exception("No boundary found for multipart/form-data"));
      } else {
         this.i = "\r\n--".concat(var1).getBytes();
      }
   }

   @Override
   public final void b(o var1, m var2) {
      if (this.j > 0) {
         ByteBuffer var11 = f0.m.g(this.i.length);
         var11.put(this.i, 0, this.j);
         ((Buffer)var11).flip();
         var2.b(var11);
         this.j = 0;
      }

      int var8 = var2.c;
      byte[] var10 = new byte[var8];
      var2.e(var10);
      int var3 = 0;
      int var4 = 0;

      while (true) {
         if (var3 >= var8) {
            if (var4 < var8) {
               var3 = var8 - var4 - Math.max(this.j, 0);
               ByteBuffer var18 = f0.m.g(var3).put(var10, var4, var3);
               ((Buffer)var18).flip();
               var2 = new m();
               var2.a(var18);
               super.b(this, var2);
            }
            break;
         }

         label84: {
            int var6;
            int var24;
            label83: {
               label95: {
                  int var9 = this.j;
                  byte var7 = -1;
                  if (var9 >= 0) {
                     byte var28 = var10[var3];
                     byte[] var17 = this.i;
                     if (var28 == var17[var9]) {
                        this.j = ++var9;
                        var24 = var4;
                        var6 = var3;
                        if (var9 != var17.length) {
                           break label83;
                        }

                        var5 = var7;
                        break label95;
                     }

                     var24 = var4;
                     var6 = var3;
                     if (var9 <= 0) {
                        break label83;
                     }

                     var3 -= var9;
                  } else {
                     label94: {
                        if (var9 == -1) {
                           int var26 = var10[var3];
                           if (var26 == 13) {
                              this.j = -4;
                              var26 = var3 - var4 - this.i.length;
                              if (var4 != 0 || var26 != 0) {
                                 ByteBuffer var15 = f0.m.g(var26).put(var10, var4, var26);
                                 ((Buffer)var15).flip();
                                 var2 = new m();
                                 var2.a(var15);
                                 super.b(this, var2);
                              }

                              h var16 = new h(4);
                              t var21 = new t(0);
                              var21.g = new j(this, var16, 3);
                              super.f = var21;
                              var24 = var4;
                              var6 = var3;
                           } else {
                              if (var26 != 45) {
                                 var14 = new i0.b("Invalid multipart/form-data. Expected \r or -");
                                 break label84;
                              }

                              this.j = -2;
                              var24 = var4;
                              var6 = var3;
                           }
                           break label83;
                        }

                        var5 = -3;
                        if (var9 == -2) {
                           if (var10[var3] != 45) {
                              var14 = new i0.b("Invalid multipart/form-data. Expected -");
                              break label84;
                           }
                           break label95;
                        }

                        if (var9 == -3) {
                           if (var10[var3] != 13) {
                              var14 = new i0.b("Invalid multipart/form-data. Expected \r");
                              break label84;
                           }

                           this.j = -4;
                           var24 = var3 - var4;
                           ByteBuffer var19 = f0.m.g(var24 - this.i.length - 2).put(var10, var4, var24 - this.i.length - 2);
                           ((Buffer)var19).flip();
                           m var13 = new m();
                           var13.a(var19);
                           super.b(this, var13);
                           this.l();
                           var24 = var4;
                           var6 = var3;
                           break label83;
                        }

                        i0.b var12;
                        if (var9 == -4) {
                           if (var10[var3] == 10) {
                              var4 = var3 + 1;
                              break label94;
                           }

                           var12 = new i0.b("Invalid multipart/form-data. Expected \n");
                        } else {
                           var12 = new i0.b("Invalid multipart/form-data. Unknown state?");
                        }

                        this.c(var12);
                        var6 = var3;
                        var24 = var4;
                        break label83;
                     }
                  }

                  this.j = 0;
                  var24 = var4;
                  var6 = var3;
                  break label83;
               }

               this.j = var5;
               var24 = var4;
               var6 = var3;
            }

            var3 = var6 + 1;
            var4 = var24;
            continue;
         }

         this.c(var14);
         break;
      }
   }

   @Override
   public final void d(q var1, g var2) {
      this.i(var1);
      super.e = var2;
   }

   @Override
   public final boolean f() {
      return false;
   }

   @Override
   public final Object get() {
      return new e((e)this.k.e);
   }

   public final void l() {
      if (this.l != null) {
         if (this.k == null) {
            this.k = new h(4);
         }

         String var2 = this.l.h(null);
         String var1;
         if (TextUtils.isEmpty(this.m.b.a("name"))) {
            var1 = "unnamed";
         } else {
            var1 = this.m.b.a("name");
         }

         d var3 = new d(var1, var2);
         var3.a = this.m.a;
         if (this.n == null) {
            this.n = new ArrayList();
         }

         this.n.add(var3);
         this.k.f(var1, var2);
         this.m = null;
         this.l = null;
      }
   }

   @Override
   public final int length() {
      byte[] var6 = this.i;
      String var10;
      if (var6 == null) {
         var10 = null;
      } else {
         var10 = new String(var6, 4, var6.length - 4);
      }

      if (var10 == null) {
         StringBuilder var11 = new StringBuilder("----------------------------");
         var11.append(UUID.randomUUID().toString().replace("-", ""));
         String var12 = var11.toString();
         StringBuilder var7 = new StringBuilder("\r\n--");
         var7.append(var12);
         this.i = var7.toString().getBytes();
      }

      Iterator var13 = this.n.iterator();
      int var1 = 0;

      while (var13.hasNext()) {
         c var15 = (c)var13.next();
         h var9 = var15.a;
         byte[] var8 = this.i;
         String var16 = var9.l(new String(var8, 2, var8.length - 2));
         long var2 = var15.c;
         if (var2 == -1L) {
            return -1;
         }

         long var4 = (long)var1;
         var1 = (int)(var2 + (long)var16.getBytes().length + (long)2 + var4);
      }

      var6 = this.i;
      return var1 + new String(var6, 2, var6.length - 2).concat("--\r\n").getBytes().length;
   }

   @Override
   public final String toString() {
      ArrayList var1;
      if (this.n == null) {
         var1 = null;
      } else {
         var1 = new ArrayList(this.n);
      }

      Iterator var2 = var1.iterator();
      return var2.hasNext() ? ((c)var2.next()).toString() : "multipart content is empty";
   }
}
