package p004g;

import android.hardware.biometrics.BiometricPrompt;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.helper.AbstractC0192o;

/* renamed from: g.a */
/* loaded from: classes.dex */
public final class C0307a extends BiometricPrompt.AuthenticationCallback {
    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public final void onAuthenticationError(int i2, CharSequence charSequence) {
        super.onAuthenticationError(i2, charSequence);
        if (ConfirmDeviceActivity.m335b() != null) {
            ConfirmDeviceActivity.m335b().finish();
        }
        if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
            AbstractC0192o.m365f(null, false);
        }
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public final void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        if (ConfirmDeviceActivity.m335b() != null) {
            ConfirmDeviceActivity.m335b().finish();
        }
        if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
            AbstractC0192o.m365f(null, false);
        }
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public final void onAuthenticationHelp(int i2, CharSequence charSequence) {
        super.onAuthenticationHelp(i2, charSequence);
    }

    @Override // android.hardware.biometrics.BiometricPrompt.AuthenticationCallback
    public final void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
        super.onAuthenticationSucceeded(authenticationResult);
        if (ConfirmDeviceActivity.m335b() != null) {
            ConfirmDeviceActivity.m335b().finish();
        }
        ConfirmDeviceActivity.m334a();
    }
}
