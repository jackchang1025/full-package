package p000;

import android.content.Intent;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: p6 */
/* loaded from: classes.dex */
public final class C1057p6 {
    public /* synthetic */ C1057p6(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final Intent createIntent$activity_release(String[] strArr) {
        t60.m214695b6(strArr, "input");
        Intent intentPutExtra = new Intent("androidx.activity.result.contract.action.REQUEST_PERMISSIONS").putExtra("androidx.activity.result.contract.extra.PERMISSIONS", strArr);
        t60.m214694b5(intentPutExtra, "Intent(ACTION_REQUEST_PE…EXTRA_PERMISSIONS, input)");
        return intentPutExtra;
    }

    private C1057p6() {
    }
}
