package android.support.v4.view;

import android.content.Context;
import android.database.CursorWindow;
import android.hardware.biometrics.BiometricPrompt;
import android.view.View;

/* renamed from: android.support.v4.view.a */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0073a {
    /* renamed from: b */
    public static /* synthetic */ CursorWindow m267b(String str, long j2) {
        return new CursorWindow(str, j2);
    }

    /* renamed from: d */
    public static /* synthetic */ BiometricPrompt.Builder m269d(Context context) {
        return new BiometricPrompt.Builder(context);
    }

    /* renamed from: g */
    public static /* bridge */ /* synthetic */ View.OnUnhandledKeyEventListener m272g(Object obj) {
        return (View.OnUnhandledKeyEventListener) obj;
    }

    /* renamed from: o */
    public static /* synthetic */ void m280o() {
    }
}
