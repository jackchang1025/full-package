package p000;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.widget.ListAdapter;
import androidx.appcompat.app.AlertController$RecycleListView;
import androidx.appcompat.widget.AppCompatSpinner;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: y2 */
/* loaded from: classes.dex */
public final class DialogInterfaceOnClickListenerC1442y2 implements InterfaceC1447y7, DialogInterface.OnClickListener {

    /* renamed from: a0 */
    public DialogC1167r4 f61225a0;

    /* renamed from: a1 */
    public C1443y3 f61226a1;

    /* renamed from: a2 */
    public CharSequence f61227a2;

    /* renamed from: a3 */
    public final /* synthetic */ AppCompatSpinner f61228a3;

    public DialogInterfaceOnClickListenerC1442y2(AppCompatSpinner appCompatSpinner) {
        this.f61228a3 = appCompatSpinner;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: a1 */
    public final boolean mo215224a1() {
        DialogC1167r4 dialogC1167r4 = this.f61225a0;
        if (dialogC1167r4 != null) {
            return dialogC1167r4.isShowing();
        }
        return false;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: a2 */
    public final int mo215225a2() {
        return 0;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: a4 */
    public final Drawable mo215226a4() {
        return null;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: a6 */
    public final void mo215227a6(CharSequence charSequence) {
        this.f61227a2 = charSequence;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b3 */
    public final void mo215232b3(int i, int i2) {
        if (this.f61226a1 == null) {
            return;
        }
        AppCompatSpinner appCompatSpinner = this.f61228a3;
        C1166r3 c1166r3 = new C1166r3(appCompatSpinner.getPopupContext());
        C1102q9 c1102q9 = (C1102q9) c1166r3.f59608a1;
        CharSequence charSequence = this.f61227a2;
        if (charSequence != null) {
            c1102q9.f59432a3 = charSequence;
        }
        C1443y3 c1443y3 = this.f61226a1;
        int selectedItemPosition = appCompatSpinner.getSelectedItemPosition();
        c1102q9.f59435a6 = c1443y3;
        c1102q9.f59436a7 = this;
        c1102q9.f59438a9 = selectedItemPosition;
        c1102q9.f59437a8 = true;
        DialogC1167r4 dialogC1167r4M214470a0 = c1166r3.m214470a0();
        this.f61225a0 = dialogC1167r4M214470a0;
        AlertController$RecycleListView alertController$RecycleListView = dialogC1167r4M214470a0.f59623a4.f59586a4;
        AbstractC1440y0.m215222a3(alertController$RecycleListView, i);
        AbstractC1440y0.m215221a2(alertController$RecycleListView, i2);
        this.f61225a0.show();
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b4 */
    public final int mo215233b4() {
        return 0;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b5 */
    public final CharSequence mo215234b5() {
        return this.f61227a2;
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b6 */
    public final void mo209895b6(ListAdapter listAdapter) {
        this.f61226a1 = (C1443y3) listAdapter;
    }

    @Override // p000.InterfaceC1447y7
    public final void dismiss() {
        DialogC1167r4 dialogC1167r4 = this.f61225a0;
        if (dialogC1167r4 != null) {
            dialogC1167r4.dismiss();
            this.f61225a0 = null;
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        AppCompatSpinner appCompatSpinner = this.f61228a3;
        appCompatSpinner.setSelection(i);
        if (appCompatSpinner.getOnItemClickListener() != null) {
            appCompatSpinner.performItemClick(null, i, this.f61226a1.getItemId(i));
        }
        dismiss();
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: a8 */
    public final void mo215228a8(Drawable drawable) {
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b0 */
    public final void mo215229b0(int i) {
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b1 */
    public final void mo215230b1(int i) {
    }

    @Override // p000.InterfaceC1447y7
    /* renamed from: b2 */
    public final void mo215231b2(int i) {
    }
}
