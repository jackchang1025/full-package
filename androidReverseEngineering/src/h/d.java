package h;

import a1.q;
import com.guard.wallet.http.l;
import com.guard.wallet.resp.PushResponseVO;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

// $VF: synthetic class
public final class d implements Runnable {
   public final e a;
   public final String b;
   public final String c;
   public final Future d;
   public final String e;

   @Override
   public final void run() {
      e var5 = this.a;
      var5.getClass();
      PushResponseVO var4 = new PushResponseVO();
      var4.setLogId(this.b);
      String var3 = this.c;
      var4.setFileUrl(var3);
      Integer var6 = 1;
      var4.setInstallMethod(var6);
      Integer var2 = -1;
      var4.setDownloadResult(var2);
      var4.setInstallResult(var2);
      var4.setStartResult(var2);

      do {
         var10 = this.d;
      } while (!var10.isDone());

      try {
         var11 = (String)var10.get();
      } catch (Exception var9) {
         q.s("AdbConnectionManager", var9);
         var11 = null;
      }

      boolean var1 = q.B(var11);
      ConcurrentHashMap var7 = var5.r;
      if (var1) {
         if (!q.B(var3)) {
            var7.remove(var3);
         }

         var4.setDownloadResult(0);
      } else {
         var4.setDownloadResult(var6);
         String var8 = "pm install -d -t -r".concat(" ").concat(var11);
         if (!q.E(7912)) {
            if (var5.P(var8, new i.a("Success", true, 1), new i.a("INSTALL_FAILED", true, 0)) == 1) {
               var4.setInstallResult(var6);
            }

            var8 = this.e;
            if (!q.B(var8) && var5.N(var8)) {
               var4.setStartResult(var6);
            }
         }

         q.n(var11);
         if (!q.B(var3)) {
            var7.remove(var3);
         }
      }

      l.s(var4);
   }
}
