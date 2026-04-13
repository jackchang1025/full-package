package p004g;

import android.os.CancellationSignal;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.helper.AbstractC0192o;

/* renamed from: g.b */
/* loaded from: classes.dex */
public final class C0308b implements CancellationSignal.OnCancelListener {
    @Override // android.os.CancellationSignal.OnCancelListener
    public final void onCancel() {
        if (ConfirmDeviceActivity.m335b() != null) {
            ConfirmDeviceActivity.m335b().finish();
        }
        if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
            AbstractC0192o.m365f(null, false);
        }
    }
}
