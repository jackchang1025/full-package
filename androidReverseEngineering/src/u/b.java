package u;

import a1.q;
import android.util.Log;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.LinkedList;

public final class b implements Serializable {
   public final LinkedList a = new LinkedList();
   public int b = 0;

   public final boolean a() {
      if (this.b == 0 && h.s()) {
         String var1 = g.i0();
         if (!q.B(var1)) {
            var1 = var1.concat("/smsRecognizePlugs.json");
            Log.d("SmsMessageListener", var1);
            String var2 = q.K(var1);
            StringBuilder var4 = new StringBuilder("准备添加本地短信识别插件:");
            var4.append(var2);
            Log.d("SmsMessageListener", var4.toString());
            if (!q.B(var2) && g.E(var2) > 0) {
               Log.d("SmsMessageListener", "已加载本地短信识别插件");
            }

            this.b = 1;
            return true;
         }
      }

      return false;
   }
}
