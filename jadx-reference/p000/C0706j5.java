package p000;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: j5 */
/* loaded from: classes2.dex */
public final class C0706j5 extends ViewOutlineProvider {

    /* renamed from: a0 */
    public final /* synthetic */ int f57265a0;

    /* renamed from: a1 */
    public final /* synthetic */ float f57266a1;

    public /* synthetic */ C0706j5(float f, int i) {
        this.f57265a0 = i;
        this.f57266a1 = f;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        switch (this.f57265a0) {
            case 0:
                t60.m214695b6(view, "view");
                t60.m214695b6(outline, "outline");
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 16 * this.f57266a1);
                break;
            default:
                t60.m214695b6(view, "view");
                t60.m214695b6(outline, "outline");
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.f57266a1);
                break;
        }
    }
}
