package p000;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class cf0 implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, sf0 {

    /* renamed from: a0 */
    public r21 f46129a0;

    /* renamed from: a1 */
    public DialogC1167r4 f46130a1;

    /* renamed from: a2 */
    public db0 f46131a2;

    @Override // p000.sf0
    /* renamed from: a0 */
    public final void mo210850a0(bf0 bf0Var, boolean z) {
        DialogC1167r4 dialogC1167r4;
        if ((z || bf0Var == this.f46129a0) && (dialogC1167r4 = this.f46130a1) != null) {
            dialogC1167r4.dismiss();
        }
    }

    @Override // p000.sf0
    /* renamed from: b6 */
    public final boolean mo210851b6(bf0 bf0Var) {
        return false;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        r21 r21Var = this.f46129a0;
        db0 db0Var = this.f46131a2;
        if (db0Var.f55684a6 == null) {
            db0Var.f55684a6 = new cb0(db0Var);
        }
        r21Var.m210704b6(db0Var.f55684a6.getItem(i), null, 0);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.f46131a2.mo61a0(this.f46129a0, true);
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        r21 r21Var = this.f46129a0;
        if (i == 82 || i == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.f46130a1.getWindow();
                if (window2 != null && (decorView2 = window2.getDecorView()) != null && (keyDispatcherState2 = decorView2.getKeyDispatcherState()) != null) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.f46130a1.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                r21Var.m210690a2(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return r21Var.performShortcut(i, keyEvent, 0);
    }
}
