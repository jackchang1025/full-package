package p000;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.internal.NavigationMenuItemView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: m2 */
/* loaded from: classes.dex */
public final class ViewOnClickListenerC0846m2 implements View.OnClickListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f58241a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58242a1;

    public /* synthetic */ ViewOnClickListenerC0846m2(int i, Object obj) {
        this.f58241a0 = i;
        this.f58242a1 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.f58241a0) {
            case 0:
                ((AbstractC0903n7) this.f58242a1).mo214038a0();
                break;
            case 1:
                C1165r2 c1165r2 = (C1165r2) this.f58242a1;
                c1165r2.f59603c1.obtainMessage(1, c1165r2.f59583a1).sendToTarget();
                break;
            case 2:
                ff0 itemData = ((ei0) view).getItemData();
                gi0 gi0Var = (gi0) this.f58242a1;
                if (!gi0Var.f56504c8.m210704b6(itemData, gi0Var.f56503c7, 0)) {
                    itemData.setChecked(true);
                    break;
                }
                break;
            case 3:
                NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) view;
                ui0 ui0Var = (ui0) this.f58242a1;
                mi0 mi0Var = ui0Var.f60431a4;
                boolean z = true;
                if (mi0Var != null) {
                    mi0Var.f58376a4 = true;
                }
                ff0 itemData2 = navigationMenuItemView.getItemData();
                boolean zM210704b6 = ui0Var.f60429a2.m210704b6(itemData2, ui0Var, 0);
                if (itemData2 != null && itemData2.isCheckable() && zM210704b6) {
                    ui0Var.f60431a4.m214003a7(itemData2);
                } else {
                    z = false;
                }
                mi0 mi0Var2 = ui0Var.f60431a4;
                if (mi0Var2 != null) {
                    mi0Var2.f58376a4 = false;
                }
                if (z) {
                    ui0Var.mo66a9(false);
                    break;
                }
                break;
            default:
                a71 a71Var = ((Toolbar) this.f58242a1).f44126d7;
                ff0 ff0Var = a71Var == null ? null : a71Var.f44a1;
                if (ff0Var != null) {
                    ff0Var.collapseActionView();
                    break;
                }
                break;
        }
    }
}
