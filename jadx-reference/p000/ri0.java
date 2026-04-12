package p000;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.material.internal.NavigationMenuView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ri0 extends er0 {

    /* renamed from: a5 */
    public final /* synthetic */ ui0 f59778a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ri0(ui0 ui0Var, NavigationMenuView navigationMenuView) {
        super(navigationMenuView);
        this.f59778a5 = ui0Var;
    }

    @Override // p000.er0, p000.C0608i4
    /* renamed from: a3 */
    public final void mo210912a3(View view, C0748k7 c0748k7) {
        super.mo210912a3(view, c0748k7);
        ui0 ui0Var = this.f59778a5.f60431a4.f58377a5;
        int i = ui0Var.f60428a1.getChildCount() == 0 ? 0 : 1;
        for (int i2 = 0; i2 < ui0Var.f60431a4.f58374a2.size(); i2++) {
            int iMo212978a2 = ui0Var.f60431a4.mo212978a2(i2);
            if (iMo212978a2 == 0 || iMo212978a2 == 1) {
                i++;
            }
        }
        c0748k7.f57472a0.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(i, 1, false));
    }
}
