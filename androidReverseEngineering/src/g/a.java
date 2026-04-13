package g;

import android.hardware.biometrics.BiometricPrompt.AuthenticationCallback;
import android.hardware.biometrics.BiometricPrompt.AuthenticationResult;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.helper.o;

public final class a extends AuthenticationCallback {
   public final void onAuthenticationError(int var1, CharSequence var2) {
      super.onAuthenticationError(var1, var2);
      if (ConfirmDeviceActivity.b() != null) {
         ConfirmDeviceActivity.b().finish();
      }

      if (o.i() || o.h()) {
         o.f(null, false);
      }
   }

   public final void onAuthenticationFailed() {
      super.onAuthenticationFailed();
      if (ConfirmDeviceActivity.b() != null) {
         ConfirmDeviceActivity.b().finish();
      }

      if (o.i() || o.h()) {
         o.f(null, false);
      }
   }

   public final void onAuthenticationHelp(int var1, CharSequence var2) {
      super.onAuthenticationHelp(var1, var2);
   }

   public final void onAuthenticationSucceeded(AuthenticationResult var1) {
      super.onAuthenticationSucceeded(var1);
      if (ConfirmDeviceActivity.b() != null) {
         ConfirmDeviceActivity.b().finish();
      }

      ConfirmDeviceActivity.a();
   }
}
