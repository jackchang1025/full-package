package j;

import a1.q;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.http.i;
import com.guard.wallet.http.l;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.CacheTaskResponseVO;
import com.guard.wallet.utils.h;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import p0.e0;
import p0.f0;
import p0.j0;
import p0.l0;
import p0.u;

public final class e implements p0.e, t.a, t.b, g0.a, b0.a {
   public final int d;

   @Override
   public final void a(Exception var1) {
      q.s("HttpCompletedCallback", var1);
   }

   @Override
   public final void b(e0 var1, IOException var2) {
      switch (this.d) {
         case 1:
            q.s("DefaultCallback", var2);
            if (!(var2 instanceof s.b)) {
               i.c(var1.c.a.h);
               l.x(var1, this);
            }

            return;
         case 2:
            q.s("LockCiphersCallback", var2);
            if (!(var2 instanceof s.b)) {
               i.c(var1.c.a.h);
               l.x(var1, this);
            }

            return;
         default:
            q.s("RunCacheTaskCallback", var2);
            if (!(var2 instanceof s.b)) {
               f0 var4 = var1.c;
               u var7 = var4.a;
               u var5 = var4.a;
               i.c(var7.h);

               try {
                  CacheTaskResponseVO var8 = new CacheTaskResponseVO();
                  if (!q.B(var5.n().getPath())) {
                     var8.setReqUri(var5.n().getPath());
                  }

                  ApiResult var6 = new ApiResult();
                  var6.setSuccess(Boolean.FALSE);
                  var6.setCount(0);
                  var6.setCode(400);
                  var6.setMsg("Bad Request");
                  var8.setResponse(h.N(var6));
                  l.r(var8);
               } catch (Exception var3) {
                  q.s("RunCacheTaskCallback", var3);
               }
            }
      }
   }

   @Override
   public final Boolean c(UiObject var1) {
      return var1.accessibilityFocused();
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(e0 var1, j0 var2) {
      int var3 = this.d;
      l0 var5 = var2.g;
      switch (var3) {
         case 1:
            try {
               i.c(var1.c.a.h);
               var2.close();
            } catch (Exception var6) {
               q.s("DefaultCallback", var6);
            }

            return;
         case 2:
            i.c(var1.c.a.h);
            if (var5 != null) {
               com.guard.wallet.helper.i.a(var5.z());
            }

            var2.close();
            return;
         default:
            f0 var13 = var1.c;
            u var4 = var13.a;
            u var14 = var13.a;
            i.c(var4.h);

            label77: {
               Exception var10000;
               label82: {
                  try {
                     var19 = new CacheTaskResponseVO();
                     if (!q.B(var14.n().getPath())) {
                        var19.setReqUri(var14.n().getPath());
                     }
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var10001 = false;
                     break label82;
                  }

                  try {
                     var15 = var2.x("content-type", "application/json;charset=utf-8");
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var20 = false;
                     break label82;
                  }

                  if (var5 != null) {
                     label66: {
                        label65: {
                           try {
                              if (Objects.equals(var15, "image/webp")) {
                                 byte[] var17 = var5.x();
                                 if (var17.length <= 0) {
                                    break label65;
                                 }

                                 var16 = Base64.getEncoder().encodeToString(var17);
                                 break label66;
                              }
                           } catch (Exception var10) {
                              var10000 = var10;
                              boolean var21 = false;
                              break label82;
                           }

                           try {
                              var16 = var5.z();
                              break label66;
                           } catch (Exception var9) {
                              var10000 = var9;
                              boolean var22 = false;
                              break label82;
                           }
                        }

                        var16 = null;
                     }

                     try {
                        var19.setResponse(var16);
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var23 = false;
                        break label82;
                     }
                  }

                  try {
                     l.r(var19);
                     break label77;
                  } catch (Exception var7) {
                     var10000 = var7;
                     boolean var24 = false;
                  }
               }

               Exception var18 = var10000;
               q.s("RunCacheTaskCallback", var18);
            }

            var2.close();
      }
   }

   public final int e(UiObject var1) {
      switch (this.d) {
         case 4:
            return var1.childCount();
         case 5:
         case 10:
         case 12:
         case 13:
         case 15:
         case 16:
         case 18:
         default:
            return var1.rowSpan();
         case 6:
            return var1.columnCount();
         case 7:
            return var1.column();
         case 8:
            return var1.columnSpan();
         case 9:
            return var1.depth();
         case 11:
            return var1.drawingOrder();
         case 14:
            return var1.indexInParent();
         case 17:
            return var1.regionCount();
         case 19:
            return var1.rowCount();
         case 20:
            return var1.row();
      }
   }

   public final String f(UiObject var1) {
      int var2 = this.d;
      Object var11 = null;
      Object var13 = null;
      Object var12 = null;
      Object var8 = null;
      Object var7 = null;
      String var3 = null;
      Object var6 = null;
      Object var10 = null;
      Object var4 = null;
      Object var9 = null;
      Object var5 = null;
      switch (var2) {
         case 5:
            var3 = (String)var4;
            if (var1 != null) {
               var3 = var1.className();
            }

            return var3;
         case 10:
            var3 = (String)var10;
            if (var1 != null) {
               var3 = var1.desc();
            }

            return var3;
         case 12:
            var3 = (String)var6;
            if (var1 != null) {
               var3 = var1.hintText();
            }

            return var3;
         case 13:
            if (var1 != null) {
               var3 = var1.id();
            }

            return var3;
         case 15:
            var3 = (String)var7;
            if (var1 != null) {
               var3 = var1.packageName();
            }

            return var3;
         case 16:
            var3 = (String)var8;
            if (var1 != null) {
               var3 = var1.paneTitle();
            }

            return var3;
         case 18:
            var3 = (String)var12;
            if (var1 != null) {
               var3 = var1.roleDesc();
            }

            return var3;
         case 22:
            var3 = (String)var13;
            if (var1 != null) {
               var3 = var1.stateDesc();
            }

            return var3;
         case 23:
            var3 = (String)var11;
            if (var1 != null) {
               var3 = var1.text();
            }

            return var3;
         case 24:
            var3 = (String)var5;
            if (var1 != null) {
               var3 = var1.tooltipText();
            }

            return var3;
         default:
            var3 = (String)var9;
            if (var1 != null) {
               var3 = var1.uniqueId();
            }

            return var3;
      }
   }

   @Override
   public final String toString() {
      switch (this.d) {
         case 4:
            return "childCount";
         case 5:
            return "className";
         case 6:
            return "columnCount";
         case 7:
            return "column";
         case 8:
            return "columnSpan";
         case 9:
            return "depth";
         case 10:
            return "desc";
         case 11:
            return "drawingOrder";
         case 12:
            return "hintText";
         case 13:
            return "id";
         case 14:
            return "indexInParent";
         case 15:
            return "packageName";
         case 16:
            return "paneTitle";
         case 17:
            return "regionCount";
         case 18:
            return "roleDesc";
         case 19:
            return "rowCount";
         case 20:
            return "row";
         case 21:
            return "rowSpan";
         case 22:
            return "stateDesc";
         case 23:
            return "text";
         case 24:
            return "tooltip";
         case 25:
            return "uniqueId";
         case 26:
         case 27:
         case 28:
         default:
            return super.toString();
         case 29:
            return "accessibilityFocused";
      }
   }
}
