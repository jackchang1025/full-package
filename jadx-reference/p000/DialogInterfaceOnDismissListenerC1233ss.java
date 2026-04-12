package p000;

import android.app.Dialog;
import android.content.DialogInterface;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ss */
/* loaded from: classes.dex */
public final class DialogInterfaceOnDismissListenerC1233ss implements DialogInterface.OnDismissListener {

    /* renamed from: a0 */
    public final /* synthetic */ DialogInterfaceOnCancelListenerC1235su f60072a0;

    public DialogInterfaceOnDismissListenerC1233ss(DialogInterfaceOnCancelListenerC1235su dialogInterfaceOnCancelListenerC1235su) {
        this.f60072a0 = dialogInterfaceOnCancelListenerC1235su;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        DialogInterfaceOnCancelListenerC1235su dialogInterfaceOnCancelListenerC1235su = this.f60072a0;
        Dialog dialog = dialogInterfaceOnCancelListenerC1235su.f60096f5;
        if (dialog != null) {
            dialogInterfaceOnCancelListenerC1235su.onDismiss(dialog);
        }
    }
}
