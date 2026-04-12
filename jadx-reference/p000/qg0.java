package p000;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.constraintlayout.utils.widget.MotionLabel;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class qg0 extends ViewOutlineProvider {

    /* renamed from: a0 */
    public final /* synthetic */ int f59504a0;

    /* renamed from: a1 */
    public final /* synthetic */ MotionLabel f59505a1;

    public /* synthetic */ qg0(MotionLabel motionLabel, int i) {
        this.f59504a0 = i;
        this.f59505a1 = motionLabel;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        switch (this.f59504a0) {
            case 0:
                MotionLabel motionLabel = this.f59505a1;
                outline.setRoundRect(0, 0, motionLabel.getWidth(), motionLabel.getHeight(), (Math.min(r9, r10) * motionLabel.f44702a5) / 2.0f);
                break;
            default:
                MotionLabel motionLabel2 = this.f59505a1;
                outline.setRoundRect(0, 0, motionLabel2.getWidth(), motionLabel2.getHeight(), motionLabel2.f44703a6);
                break;
        }
    }
}
