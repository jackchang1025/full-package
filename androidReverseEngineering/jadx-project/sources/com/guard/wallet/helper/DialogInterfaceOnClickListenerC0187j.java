package com.guard.wallet.helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import java.lang.ref.WeakReference;
import java.util.Objects;
import p002e.C0262b;

/* renamed from: com.guard.wallet.helper.j */
/* loaded from: classes.dex */
public final class DialogInterfaceOnClickListenerC0187j implements DialogInterface.OnClickListener {

    /* renamed from: a */
    public final /* synthetic */ int f212a;

    public /* synthetic */ DialogInterfaceOnClickListenerC0187j(int i2) {
        this.f212a = i2;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        switch (this.f212a) {
            case 0:
                AbstractC0251g.n1();
                break;
            case 1:
                Log.d("AccessibilityUtils", "NeutralButton click");
                WeakReference weakReference = AbstractC0246b.f395a;
                if (weakReference != null && weakReference.get() != null) {
                    ((AlertDialog) AbstractC0246b.f395a.get()).dismiss();
                }
                AbstractC0246b.m596a();
                break;
            default:
                WeakReference weakReference2 = AbstractC0246b.f395a;
                if (weakReference2 != null && weakReference2.get() != null) {
                    ((AlertDialog) AbstractC0246b.f395a.get()).dismiss();
                    AbstractC0246b.f396b.set(false);
                }
                if (Objects.equals(0, AbstractC0248d.m609g()) && C0262b.f433a != null && AbstractC0249e.m623l()) {
                    C0262b.m739e();
                }
                AbstractC0251g.V0();
                break;
        }
    }
}
