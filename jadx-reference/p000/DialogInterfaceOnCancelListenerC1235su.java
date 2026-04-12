package p000;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0071a7;
import androidx.lifecycle.C0077a1;
import androidx.lifecycle.runtime.R$id;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: su */
/* loaded from: classes.dex */
public class DialogInterfaceOnCancelListenerC1235su extends AbstractComponentCallbacksC0069a5 implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    /* renamed from: e4 */
    public Handler f60085e4;

    /* renamed from: f3 */
    public boolean f60094f3;

    /* renamed from: f5 */
    public Dialog f60096f5;

    /* renamed from: f6 */
    public boolean f60097f6;

    /* renamed from: f7 */
    public boolean f60098f7;

    /* renamed from: e5 */
    public final RunnableC0165ca f60086e5 = new RunnableC0165ca(6, this);

    /* renamed from: e6 */
    public final DialogInterfaceOnCancelListenerC1232sr f60087e6 = new DialogInterfaceOnCancelListenerC1232sr(this);

    /* renamed from: e7 */
    public final DialogInterfaceOnDismissListenerC1233ss f60088e7 = new DialogInterfaceOnDismissListenerC1233ss(this);

    /* renamed from: e8 */
    public int f60089e8 = 0;

    /* renamed from: e9 */
    public int f60090e9 = 0;

    /* renamed from: f0 */
    public boolean f60091f0 = true;

    /* renamed from: f1 */
    public boolean f60092f1 = true;

    /* renamed from: f2 */
    public int f60093f2 = -1;

    /* renamed from: f4 */
    public final tg0 f60095f4 = new tg0(15, this);

    /* renamed from: f8 */
    public boolean f60099f8 = false;

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: a1 */
    public final t60 mo210132a1() {
        return new C1234st(this, new C1396x(this));
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b1 */
    public final void mo210138b1(Context context) {
        super.mo210138b1(context);
        this.f45116d9.m210241a3(this.f60095f4);
        this.f60098f7 = false;
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b2 */
    public void mo210139b2(Bundle bundle) {
        super.mo210139b2(bundle);
        this.f60085e4 = new Handler();
        this.f60092f1 = this.f45099c2 == 0;
        if (bundle != null) {
            this.f60089e8 = bundle.getInt("android:style", 0);
            this.f60090e9 = bundle.getInt("android:theme", 0);
            this.f60091f0 = bundle.getBoolean("android:cancelable", true);
            this.f60092f1 = bundle.getBoolean("android:showsDialog", this.f60092f1);
            this.f60093f2 = bundle.getInt("android:backStackId", -1);
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b4 */
    public final void mo210141b4() {
        this.f45105c8 = true;
        Dialog dialog = this.f60096f5;
        if (dialog != null) {
            this.f60097f6 = true;
            dialog.setOnDismissListener(null);
            this.f60096f5.dismiss();
            if (!this.f60098f7) {
                onDismiss(this.f60096f5);
            }
            this.f60096f5 = null;
            this.f60099f8 = false;
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b5 */
    public final void mo210142b5() {
        this.f45105c8 = true;
        if (!this.f60098f7) {
            this.f60098f7 = true;
        }
        C0077a1 c0077a1 = this.f45116d9;
        c0077a1.getClass();
        C0077a1.m210238a0("removeObserver");
        wb0 wb0Var = (wb0) c0077a1.f45199a1.mo214144a1(this.f60095f4);
        if (wb0Var == null) {
            return;
        }
        wb0Var.mo210227a3();
        wb0Var.m215044a1(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0042 A[Catch: all -> 0x004a, TryCatch #0 {all -> 0x004a, blocks: (B:12:0x0018, B:14:0x0024, B:24:0x003c, B:26:0x0042, B:29:0x004c, B:20:0x002e, B:22:0x0034, B:23:0x0039, B:30:0x0064), top: B:45:0x0018 }] */
    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final LayoutInflater mo210143b6(Bundle bundle) {
        Context contextM210135a8;
        LayoutInflater layoutInflaterMo210143b6 = super.mo210143b6(bundle);
        boolean z = this.f60092f1;
        if (z && !this.f60094f3) {
            if (z && !this.f60099f8) {
                try {
                    this.f60094f3 = true;
                    Dialog dialogMo211023d1 = mo211023d1();
                    this.f60096f5 = dialogMo211023d1;
                    if (this.f60092f1) {
                        int i = this.f60089e8;
                        if (i == 1 || i == 2) {
                            dialogMo211023d1.requestWindowFeature(1);
                            contextM210135a8 = m210135a8();
                            if (contextM210135a8 != null) {
                                this.f60096f5.setOwnerActivity((Activity) contextM210135a8);
                            }
                            this.f60096f5.setCancelable(this.f60091f0);
                            this.f60096f5.setOnCancelListener(this.f60087e6);
                            this.f60096f5.setOnDismissListener(this.f60088e7);
                            this.f60099f8 = true;
                        } else if (i == 3) {
                            Window window = dialogMo211023d1.getWindow();
                            if (window != null) {
                                window.addFlags(24);
                            }
                            dialogMo211023d1.requestWindowFeature(1);
                            contextM210135a8 = m210135a8();
                            if (contextM210135a8 != null) {
                            }
                            this.f60096f5.setCancelable(this.f60091f0);
                            this.f60096f5.setOnCancelListener(this.f60087e6);
                            this.f60096f5.setOnDismissListener(this.f60088e7);
                            this.f60099f8 = true;
                        } else {
                            contextM210135a8 = m210135a8();
                            if (contextM210135a8 != null) {
                            }
                            this.f60096f5.setCancelable(this.f60091f0);
                            this.f60096f5.setOnCancelListener(this.f60087e6);
                            this.f60096f5.setOnDismissListener(this.f60088e7);
                            this.f60099f8 = true;
                        }
                    } else {
                        this.f60096f5 = null;
                    }
                    this.f60094f3 = false;
                } catch (Throwable th) {
                    this.f60094f3 = false;
                    throw th;
                }
            }
            if (C0071a7.m210158c7(2)) {
                toString();
            }
            Dialog dialog = this.f60096f5;
            if (dialog != null) {
                return layoutInflaterMo210143b6.cloneInContext(dialog.getContext());
            }
        } else if (C0071a7.m210158c7(2)) {
            toString();
        }
        return layoutInflaterMo210143b6;
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b7 */
    public void mo210144b7(Bundle bundle) {
        Dialog dialog = this.f60096f5;
        if (dialog != null) {
            Bundle bundleOnSaveInstanceState = dialog.onSaveInstanceState();
            bundleOnSaveInstanceState.putBoolean("android:dialogShowing", false);
            bundle.putBundle("android:savedDialogState", bundleOnSaveInstanceState);
        }
        int i = this.f60089e8;
        if (i != 0) {
            bundle.putInt("android:style", i);
        }
        int i2 = this.f60090e9;
        if (i2 != 0) {
            bundle.putInt("android:theme", i2);
        }
        boolean z = this.f60091f0;
        if (!z) {
            bundle.putBoolean("android:cancelable", z);
        }
        boolean z2 = this.f60092f1;
        if (!z2) {
            bundle.putBoolean("android:showsDialog", z2);
        }
        int i3 = this.f60093f2;
        if (i3 != -1) {
            bundle.putInt("android:backStackId", i3);
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b8 */
    public void mo210145b8() {
        this.f45105c8 = true;
        Dialog dialog = this.f60096f5;
        if (dialog != null) {
            this.f60097f6 = false;
            dialog.show();
            View decorView = this.f60096f5.getWindow().getDecorView();
            t60.m214695b6(decorView, "<this>");
            decorView.setTag(R$id.view_tree_lifecycle_owner, this);
            decorView.setTag(androidx.lifecycle.viewmodel.R$id.view_tree_view_model_store_owner, this);
            decorView.setTag(androidx.savedstate.R$id.view_tree_saved_state_registry_owner, this);
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b9 */
    public void mo210146b9() {
        this.f45105c8 = true;
        Dialog dialog = this.f60096f5;
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: c0 */
    public final void mo210147c0(Bundle bundle) {
        Bundle bundle2;
        this.f45105c8 = true;
        if (this.f60096f5 == null || bundle == null || (bundle2 = bundle.getBundle("android:savedDialogState")) == null) {
            return;
        }
        this.f60096f5.onRestoreInstanceState(bundle2);
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: c1 */
    public final void mo210148c1(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Bundle bundle2;
        super.mo210148c1(layoutInflater, viewGroup, bundle);
        if (this.f45107d0 != null || this.f60096f5 == null || bundle == null || (bundle2 = bundle.getBundle("android:savedDialogState")) == null) {
            return;
        }
        this.f60096f5.onRestoreInstanceState(bundle2);
    }

    /* renamed from: d0 */
    public final void m214670d0(boolean z, boolean z2) {
        if (this.f60098f7) {
            return;
        }
        this.f60098f7 = true;
        Dialog dialog = this.f60096f5;
        if (dialog != null) {
            dialog.setOnDismissListener(null);
            this.f60096f5.dismiss();
            if (!z2) {
                if (Looper.myLooper() == this.f60085e4.getLooper()) {
                    onDismiss(this.f60096f5);
                } else {
                    this.f60085e4.post(this.f60086e5);
                }
            }
        }
        this.f60097f6 = true;
        if (this.f60093f2 >= 0) {
            C0071a7 c0071a7M210137b0 = m210137b0();
            int i = this.f60093f2;
            if (i < 0) {
                throw new IllegalArgumentException(tz0.m214802a2(i, "Bad id: "));
            }
            c0071a7M210137b0.m210179b7(new i00(c0071a7M210137b0, i), false);
            this.f60093f2 = -1;
            return;
        }
        C0389cs c0389cs = new C0389cs(m210137b0());
        C0071a7 c0071a7 = this.f45094b7;
        if (c0071a7 != null && c0071a7 != c0389cs.f55498b5) {
            throw new IllegalStateException("Cannot remove Fragment attached to a different FragmentManager. Fragment " + toString() + " is already attached to a FragmentManager.");
        }
        c0389cs.m212520a1(new m00(3, this));
        if (z) {
            c0389cs.m212522a3(true);
        } else {
            c0389cs.m212522a3(false);
        }
    }

    /* renamed from: d1 */
    public Dialog mo211023d1() {
        if (C0071a7.m210158c7(3)) {
            toString();
        }
        return new Dialog(m210152c5(), this.f60090e9);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (this.f60097f6) {
            return;
        }
        if (C0071a7.m210158c7(3)) {
            toString();
        }
        m214670d0(true, true);
    }

    public void onCancel(DialogInterface dialogInterface) {
    }
}
