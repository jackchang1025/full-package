package com.guard.wallet.helper;

import android.content.DialogInterface;
import com.guard.wallet.utils.AbstractC0246b;

/* renamed from: com.guard.wallet.helper.k */
/* loaded from: classes.dex */
public final class DialogInterfaceOnDismissListenerC0188k implements DialogInterface.OnDismissListener {

    /* renamed from: a */
    public final /* synthetic */ int f213a;

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        switch (this.f213a) {
            case 0:
                AbstractC0191n.f222a = null;
                break;
            default:
                AbstractC0246b.f395a = null;
                break;
        }
    }
}
