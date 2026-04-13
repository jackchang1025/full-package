package l0;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import java.util.Locale;
import java.util.Objects;
import org.json.JSONObject;

public final class d extends h {
   public final d p;
   public o q;
   public String r;
   public String s;
   public boolean t;
   public boolean u;
   public b v;
   public boolean w;
   public final f0.k x;
   public final e y;

   public d(e var1, f0.k var2) {
      this.y = var1;
      this.x = var2;
      f var3 = var1.d;
      super();
      this.p = this;
   }

   @Override
   public final void a(Exception var1) {
      f var4 = this.y.d;
      b var3 = this.v;
      var4.getClass();
      boolean var2;
      if (var3.l == 101) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         this.u = true;
         this.c(var1);
         f0.k var6 = super.k;
         c var5 = new c(this);
         f0.b var7 = (f0.b)var6;
         var7.k = var5;
         if (var1 != null) {
            var7.close();
         } else {
            this.m();
            if (super.o.f()) {
               this.n();
            }
         }
      }
   }

   @Override
   public final void l() {
      if (!this.w && "100-continue".equals(super.j.i("Expect"))) {
         ((f0.b)super.k).m();
         a1.q.T(super.k, "HTTP/1.1 100 Continue\r\n\r\n".getBytes(), new a(this));
      } else {
         this.v = new b(this, this.x, this);
         this.y.d.getClass();
         if (this.q == null) {
            b var1 = this.v;
            var1.l = 404;
            var1.l();
         } else {
            if (!super.o.f() || this.u) {
               this.n();
            }
         }
      }
   }

   public final void m() {
      if (this.u && this.t) {
         e var4 = this.y;
         f var3 = var4.d;
         b var5 = this.v;
         var3.getClass();
         int var1 = var5.l;
         boolean var2 = true;
         boolean var6;
         if (var1 == 101) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (!var6) {
            b var7 = this.v;
            var4.d.getClass();
            String var8 = var7.m;
            String var12 = this.p.j.i("Connection");
            if (var12 == null) {
               i0.h var10;
               if (var8 == null) {
                  i0.h var9 = i0.h.b;
                  var10 = null;
               } else {
                  var10 = (i0.h)i0.h.c.get(var8.toLowerCase(Locale.US));
               }

               if (var10 != i0.h.b) {
                  var2 = false;
               }
            } else {
               var2 = "keep-alive".equalsIgnoreCase(var12);
            }

            f0.k var11 = this.x;
            if (var2) {
               var4.b(var11);
            } else {
               ((f0.b)var11).close();
            }
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void n() {
      f var1 = this.y.d;
      o var3 = this.q;
      b var2 = this.v;
      var1.getClass();
      if (var3 != null) {
         Exception var10000;
         label105: {
            com.guard.wallet.server.b var4;
            try {
               var4 = (com.guard.wallet.server.b)var3;
            } catch (Exception var14) {
               var10000 = var14;
               boolean var10001 = false;
               break label105;
            }

            label106: {
               try {
                  com.guard.wallet.server.b.c.set(1);
                  var23 = this.s;
                  var15 = super.n;
                  var2.d.k("Content-Type", "application/json");
                  if (w.a.a() && !com.guard.wallet.server.b.r1(var23)) {
                     com.guard.wallet.server.b.m1(var2);
                     return;
                  }
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var27 = false;
                  break label106;
               }

               label107: {
                  label109: {
                     try {
                        if (!Objects.equals(var15.toUpperCase(), "GET")) {
                           break label109;
                        }

                        var16 = this.r.split("\\?", 2);
                        if (var16.length < 2) {
                           var17 = new i0.e();
                           break label107;
                        }
                     } catch (Exception var12) {
                        var10000 = var12;
                        boolean var28 = false;
                        break label106;
                     }

                     try {
                        var17 = i0.e.c(var16[1], "&", false, i0.e.a);
                        break label107;
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var29 = false;
                        break label106;
                     }
                  }

                  label78: {
                     try {
                        if (!Objects.equals(var15.toUpperCase(), "POST")) {
                           break label78;
                        }

                        var18 = super.o;
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var31 = false;
                        break label106;
                     }

                     label73:
                     if (var18 != null) {
                        try {
                           if (var18.length() <= 0) {
                              break label73;
                           }

                           var19 = (JSONObject)super.o.get();
                        } catch (Exception var9) {
                           var10000 = var9;
                           boolean var32 = false;
                           break label106;
                        }

                        if (var19 != null) {
                           try {
                              com.guard.wallet.server.b.X1(var23, var19.toString(), var2);
                              return;
                           } catch (Exception var7) {
                              var10000 = var7;
                              boolean var33 = false;
                              break label106;
                           }
                        }
                     }
                  }

                  try {
                     com.guard.wallet.server.b.F2(var2, "访问地址或参数不合法,详见接口文档");
                     return;
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var34 = false;
                     break label106;
                  }
               }

               try {
                  var4.e1(var23, var17, var2);
                  return;
               } catch (Exception var6) {
                  var10000 = var6;
                  boolean var30 = false;
               }
            }

            Exception var20 = var10000;

            try {
               a1.q.s("HttpServer", var20);
               return;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var35 = false;
            }
         }

         Exception var21 = var10000;
         Log.e("AsyncHttpServer", "request callback raised uncaught exception. Catching versus crashing process", var21);
         ApiResult var22 = new ApiResult();
         s.a var24 = new s.a();
         var24.b = 2;
         var24.d = "Internal Server Error";
         var24.c = "Internal Server Error";
         var22.setData(var24);
         var22.setCode(500);
         var22.setMsg("request callback raised uncaught exception. Catching versus crashing process");
         var22.setCount(1);
         var22.setSuccess(Boolean.FALSE);
         String var25 = com.guard.wallet.utils.h.N(var22);
         var2.l = var22.getCode();
         var2.h(var25);
         var2.l();
      }
   }
}
