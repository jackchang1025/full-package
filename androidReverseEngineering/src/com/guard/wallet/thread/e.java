package com.guard.wallet.thread;

import a1.q;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.stat.AccessibilityEventStatVO;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class e {
   public final Timer a;
   public boolean b;
   public Long c;
   public String d;
   public final ConcurrentLinkedQueue e;
   public final ConcurrentLinkedQueue f;

   public e() {
      Timer var1 = new Timer();
      this.a = var1;
      this.b = false;
      this.c = new Date().getTime();
      this.d = "";
      this.e = new ConcurrentLinkedQueue();
      this.f = new ConcurrentLinkedQueue();
      var1.schedule(new d(this, 0), 10000L, 10000L);
   }

   public final void a(MessageRecordVO var1) {
      String var2 = com.guard.wallet.utils.h.l("deviceId");
      if (!q.B(var2)) {
         var1.setDeviceId(var2);
         ReqMessageVO var3 = new ReqMessageVO();
         var3.setDeviceId(var1.getDeviceId());
         var3.setIntentCode(var1.getIntentCode());
         if (var1.getExtraBody() != null) {
            var3.setExtraBody(com.guard.wallet.utils.h.N(var1.getExtraBody()));
         }

         this.f.offer(var3);
      }
   }

   public final void b(MessageRecordVO var1) {
      String var6 = com.guard.wallet.utils.h.l("deviceId");
      if (!q.B(var6)) {
         boolean var2;
         label40: {
            var1.setDeviceId(var6);
            if (Objects.equals(var1.getIntentCode(), "android.accessibility.service.USAGE_SUMMARY")) {
               AccessibilityEventStatVO var8 = (AccessibilityEventStatVO)var1.getExtraBody();
               if (var8 != null) {
                  boolean var5 = q.B(var8.getEventPackageName());
                  var6 = "";
                  if (!var5) {
                     var6 = "".concat(var8.getEventPackageName());
                  }

                  String var7 = var6;
                  if (!q.B(var8.getEventClassName())) {
                     var7 = var6.concat(":").concat(var8.getEventClassName());
                  }

                  var6 = var7.concat(":").concat(String.valueOf(var8.getEventValue()));
                  if (Objects.equals(var6, this.d)) {
                     if (var8.getTimestamp() - this.c < 1000L) {
                        var2 = false;
                        break label40;
                     }
                  } else {
                     this.d = var6;
                  }
               }
            }

            var2 = true;
         }

         if (var2) {
            ReqMessageVO var11 = new ReqMessageVO();
            var11.setDeviceId(var1.getDeviceId());
            var11.setIntentCode(var1.getIntentCode());
            if (var1.getExtraBody() != null) {
               var11.setExtraBody(com.guard.wallet.utils.h.N(var1.getExtraBody()));
            }

            this.e.offer(var11);
         }

         long var3;
         if (var1.getExtraBody() != null) {
            var3 = var1.getExtraBody().getTimestamp();
         } else {
            var3 = new Date().getTime();
         }

         this.c = var3;
      }
   }
}
