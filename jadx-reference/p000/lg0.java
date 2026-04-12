package p000;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.constraintlayout.utils.widget.MotionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class lg0 extends ViewOutlineProvider {

    /* renamed from: a0 */
    public final /* synthetic */ int f57924a0;

    /* renamed from: a1 */
    public final /* synthetic */ MotionButton f57925a1;

    public /* synthetic */ lg0(MotionButton motionButton, int i) {
        this.f57924a0 = i;
        this.f57925a1 = motionButton;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        switch (this.f57924a0) {
            case 0:
                MotionButton motionButton = this.f57925a1;
                outline.setRoundRect(0, 0, motionButton.getWidth(), motionButton.getHeight(), (Math.min(r9, r10) * motionButton.f44692a3) / 2.0f);
                break;
            default:
                MotionButton motionButton2 = this.f57925a1;
                outline.setRoundRect(0, 0, motionButton2.getWidth(), motionButton2.getHeight(), motionButton2.f44693a4);
                break;
        }
    }
}
