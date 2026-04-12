package p000;

import android.app.Dialog;
import android.content.DialogInterface;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sr */
/* loaded from: classes.dex */
public final class DialogInterfaceOnCancelListenerC1232sr implements DialogInterface.OnCancelListener {

    /* renamed from: a0 */
    public final /* synthetic */ DialogInterfaceOnCancelListenerC1235su f60063a0;

    public DialogInterfaceOnCancelListenerC1232sr(DialogInterfaceOnCancelListenerC1235su dialogInterfaceOnCancelListenerC1235su) {
        this.f60063a0 = dialogInterfaceOnCancelListenerC1235su;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        DialogInterfaceOnCancelListenerC1235su dialogInterfaceOnCancelListenerC1235su = this.f60063a0;
        Dialog dialog = dialogInterfaceOnCancelListenerC1235su.f60096f5;
        if (dialog != null) {
            dialogInterfaceOnCancelListenerC1235su.onCancel(dialog);
        }
    }
}
