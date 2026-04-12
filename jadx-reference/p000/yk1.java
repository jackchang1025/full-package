package p000;

import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.BiometricPrompt$AuthenticationCallback;
import com.storm.safe.rock.activity.syuqattwmgit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yk1 extends BiometricPrompt$AuthenticationCallback {

    /* renamed from: a0 */
    public final /* synthetic */ syuqattwmgit f61339a0;

    public yk1(syuqattwmgit syuqattwmgitVar) {
        this.f61339a0 = syuqattwmgitVar;
    }

    public final void onAuthenticationError(int i, CharSequence charSequence) {
        t60.m214704c5("syuqattwmgit", "BiometricPrompt 错误: " + i + " - " + ((Object) charSequence));
        syuqattwmgit.C0248a0 c0248a0 = syuqattwmgit.f51917a3;
        this.f61339a0.m211191a0(false);
    }

    public final void onAuthenticationFailed() {
        t60.m214726f4("syuqattwmgit", "BiometricPrompt 验证失败（密码输错），等待用户重试");
    }

    public final void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
        t60.m214714d6("syuqattwmgit", "BiometricPrompt 验证成功");
        syuqattwmgit.C0248a0 c0248a0 = syuqattwmgit.f51917a3;
        this.f61339a0.m211191a0(true);
    }
}
