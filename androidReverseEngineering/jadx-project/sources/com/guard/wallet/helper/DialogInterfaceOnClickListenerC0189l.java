package com.guard.wallet.helper;

import android.content.DialogInterface;
import com.guard.wallet.utils.AbstractC0251g;

/* renamed from: com.guard.wallet.helper.l */
/* loaded from: classes.dex */
public final class DialogInterfaceOnClickListenerC0189l implements DialogInterface.OnClickListener {

    /* renamed from: a */
    public final /* synthetic */ String f214a;

    /* renamed from: b */
    public final /* synthetic */ String f215b;

    public DialogInterfaceOnClickListenerC0189l(String str, String str2) {
        this.f214a = str;
        this.f215b = str2;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        AbstractC0251g.Y0(this.f214a, this.f215b);
    }
}
