package p000;

import android.hardware.biometrics.BiometricPrompt;
import android.text.PrecomputedText;
import android.text.TextPaint;
import com.storm.safe.rock.activity.syuqattwmgit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: j8 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0709j8 {
    /* renamed from: a6 */
    public static /* synthetic */ BiometricPrompt.Builder m213225a6(syuqattwmgit syuqattwmgitVar) {
        return new BiometricPrompt.Builder(syuqattwmgitVar);
    }

    /* renamed from: b0 */
    public static /* synthetic */ PrecomputedText.Params.Builder m213229b0(TextPaint textPaint) {
        return new PrecomputedText.Params.Builder(textPaint);
    }

    /* renamed from: b6 */
    public static /* synthetic */ void m213235b6() {
    }

    /* renamed from: c4 */
    public static /* bridge */ /* synthetic */ boolean m213243c4(CharSequence charSequence) {
        return charSequence instanceof PrecomputedText;
    }
}
