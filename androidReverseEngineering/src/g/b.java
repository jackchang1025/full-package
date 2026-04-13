package g;

import android.os.CancellationSignal.OnCancelListener;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.helper.o;

public final class b implements OnCancelListener {
   public final void onCancel() {
      if (ConfirmDeviceActivity.b() != null) {
         ConfirmDeviceActivity.b().finish();
      }

      if (o.i() || o.h()) {
         o.f(null, false);
      }
   }
}
