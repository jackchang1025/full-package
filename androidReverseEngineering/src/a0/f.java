package a0;

import android.net.Network;
import android.net.ConnectivityManager.NetworkCallback;

public final class f extends NetworkCallback {
   public final void onAvailable(Network var1) {
      super.onAvailable(var1);
   }

   public final void onLosing(Network var1, int var2) {
      super.onLosing(var1, var2);
   }

   public final void onLost(Network var1) {
      super.onLost(var1);
   }

   public final void onUnavailable() {
      super.onUnavailable();
   }
}
