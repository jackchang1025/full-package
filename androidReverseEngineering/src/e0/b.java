package e0;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public final class b extends Handler {
   public final c a;

   public b(c var1) {
      this.a = var1;
   }

   public final void handleMessage(Message var1) {
      Object var2 = var1.obj;
      if (var2 != null) {
         var2 = (Bitmap)var2;
         this.a.setImageBitmap((Bitmap)var2);
      }
   }
}
