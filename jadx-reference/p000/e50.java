package p000;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.constraintlayout.utils.widget.ImageFilterButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class e50 extends ViewOutlineProvider {

    /* renamed from: a0 */
    public final /* synthetic */ int f55930a0;

    /* renamed from: a1 */
    public final /* synthetic */ ImageFilterButton f55931a1;

    public /* synthetic */ e50(ImageFilterButton imageFilterButton, int i) {
        this.f55930a0 = i;
        this.f55931a1 = imageFilterButton;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        switch (this.f55930a0) {
            case 0:
                ImageFilterButton imageFilterButton = this.f55931a1;
                outline.setRoundRect(0, 0, imageFilterButton.getWidth(), imageFilterButton.getHeight(), (Math.min(r9, r10) * imageFilterButton.f44651a5) / 2.0f);
                break;
            default:
                ImageFilterButton imageFilterButton2 = this.f55931a1;
                outline.setRoundRect(0, 0, imageFilterButton2.getWidth(), imageFilterButton2.getHeight(), imageFilterButton2.f44652a6);
                break;
        }
    }
}
