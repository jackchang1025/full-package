package p000;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.progressindicator.AbstractC0217a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dc */
/* loaded from: classes2.dex */
public final class C0410dc {

    /* renamed from: a0 */
    public C1213s8 f55687a0;

    /* renamed from: a1 */
    public final /* synthetic */ int f55688a1;

    /* renamed from: a2 */
    public final /* synthetic */ View f55689a2;

    public /* synthetic */ C0410dc(View view, int i) {
        this.f55688a1 = i;
        this.f55689a2 = view;
    }

    /* renamed from: a0 */
    public final void m212579a0(Drawable drawable) {
        switch (this.f55688a1) {
            case 0:
                AbstractC0217a0 abstractC0217a0 = (AbstractC0217a0) this.f55689a2;
                abstractC0217a0.setIndeterminate(false);
                abstractC0217a0.mo211083a1(abstractC0217a0.f49696a1);
                break;
            case 1:
                AbstractC0217a0 abstractC0217a02 = (AbstractC0217a0) this.f55689a2;
                if (!abstractC0217a02.f49700a5) {
                    abstractC0217a02.setVisibility(abstractC0217a02.f49701a6);
                    break;
                }
                break;
            default:
                ColorStateList colorStateList = ((MaterialCheckBox) this.f55689a2).f49317b4;
                if (colorStateList != null) {
                    AbstractC1270tr.m214774a7(drawable, colorStateList);
                    break;
                }
                break;
        }
    }

    /* renamed from: a1 */
    public void m212580a1(Drawable drawable) {
        switch (this.f55688a1) {
            case 2:
                MaterialCheckBox materialCheckBox = (MaterialCheckBox) this.f55689a2;
                ColorStateList colorStateList = materialCheckBox.f49317b4;
                if (colorStateList != null) {
                    AbstractC1270tr.m214773a6(drawable, colorStateList.getColorForState(materialCheckBox.f49321b8, colorStateList.getDefaultColor()));
                    break;
                }
                break;
        }
    }

    /* renamed from: a2 */
    public final void m212581a2(Drawable drawable) {
    }
}
