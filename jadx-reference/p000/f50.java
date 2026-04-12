package p000;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.constraintlayout.utils.widget.ImageFilterView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class f50 extends ViewOutlineProvider {

    /* renamed from: a0 */
    public final /* synthetic */ int f56151a0;

    /* renamed from: a1 */
    public final /* synthetic */ ImageFilterView f56152a1;

    public /* synthetic */ f50(ImageFilterView imageFilterView, int i) {
        this.f56151a0 = i;
        this.f56152a1 = imageFilterView;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        switch (this.f56151a0) {
            case 0:
                ImageFilterView imageFilterView = this.f56152a1;
                outline.setRoundRect(0, 0, imageFilterView.getWidth(), imageFilterView.getHeight(), (Math.min(r9, r10) * imageFilterView.f44670a8) / 2.0f);
                break;
            default:
                ImageFilterView imageFilterView2 = this.f56152a1;
                outline.setRoundRect(0, 0, imageFilterView2.getWidth(), imageFilterView2.getHeight(), imageFilterView2.f44671a9);
                break;
        }
    }
}
