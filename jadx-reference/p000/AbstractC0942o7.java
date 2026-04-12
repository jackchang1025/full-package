package p000;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: o7 */
/* loaded from: classes.dex */
public abstract class AbstractC0942o7 {
    /* renamed from: a0 */
    public static void m214157a0(Activity activity) {
        activity.finishAffinity();
    }

    /* renamed from: a1 */
    public static void m214158a1(Activity activity, Intent intent, int i, Bundle bundle) {
        activity.startActivityForResult(intent, i, bundle);
    }

    /* renamed from: a2 */
    public static void m214159a2(Activity activity, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        activity.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }
}
