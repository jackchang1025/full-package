package com.guard.wallet.http;

import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.DeviceInfoVO;
import java.io.IOException;
import p0.f0;
import p0.j0;
import p0.l0;

public final class e implements p0.e {
   @Override
   public final void b(p0.e0 var1, IOException var2) {
      a1.q.s("DeviceIdCallback", var2);
      if (!(var2 instanceof s.b)) {
         if (!a1.q.B(var1.c.a.h)) {
            f0 var3 = var1.c;
            i.c(var3.a.h);
            p0.u var4 = var3.a;
            Log.d("DeviceIdCallback", var4.h);
            if (var4.h.contains("127.0.0.1:7911")) {
               l.g("http://127.0.0.1:7912");
            }

            if (var4.h.contains("127.0.0.1:7912")) {
               String var6 = com.guard.wallet.utils.h.l("deviceId");
               DeviceInfoVO var5 = DeviceInfoVO.of();
               var5.setDeviceId(var6);
               w var7 = new w();
               new i().h(var5, "/api/device/register.json", var7);
            }
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void d(p0.e0 var1, j0 var2) {
      i.c(var1.c.a.h);
      l0 var7 = var2.g;
      label41:
      if (var7 != null) {
         Exception var10000;
         label43: {
            try {
               DeviceIdCallback$1 var3 = new DeviceIdCallback$1();
               var8 = (ApiResult)com.guard.wallet.utils.h.c(var7.z(), var3);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var10001 = false;
               break label43;
            }

            if (var8 == null) {
               break label41;
            }

            try {
               if (!var8.getSuccess() || a1.q.B(var8.getData())) {
                  break label41;
               }

               com.guard.wallet.utils.h.D((String)var8.getData(), "deviceId");
               if (!a1.q.v(com.guard.wallet.utils.g.i0())) {
                  l.u();
               }
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10 = false;
               break label43;
            }

            try {
               l.z();
               l.c();
               break label41;
            } catch (Exception var4) {
               var10000 = var4;
               boolean var11 = false;
            }
         }

         Exception var9 = var10000;
         a1.q.s("DeviceIdCallback", var9);
      }

      var2.close();
   }
}
