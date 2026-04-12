package p000;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.google.android.material.chip.Chip;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hn */
/* loaded from: classes2.dex */
public final class C0587hn extends ViewOutlineProvider {

    /* renamed from: a0 */
    public final /* synthetic */ int f56684a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f56685a1;

    public /* synthetic */ C0587hn(int i, Object obj) {
        this.f56684a0 = i;
        this.f56685a1 = obj;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        switch (this.f56684a0) {
            case 0:
                C0590hq c0590hq = ((Chip) this.f56685a1).f49332a4;
                if (c0590hq == null) {
                    outline.setAlpha(0.0f);
                    break;
                } else {
                    c0590hq.getOutline(outline);
                    break;
                }
            case 1:
                jd0 jd0Var = (jd0) this.f56685a1;
                if (((a01) jd0Var.f56866a1) != null && !((RectF) jd0Var.f56867a2).isEmpty()) {
                    RectF rectF = (RectF) jd0Var.f56867a2;
                    int i = (int) rectF.left;
                    int i2 = (int) rectF.top;
                    int i3 = (int) rectF.right;
                    int i4 = (int) rectF.bottom;
                    a01 a01Var = (a01) jd0Var.f56866a1;
                    jd0Var.getClass();
                    outline.setRoundRect(i, i2, i3, i4, a01Var.f12a5.mo212732a0(rectF));
                    break;
                }
                break;
            case 2:
                Path path = (Path) ((kd0) this.f56685a1).f56868a3;
                if (!path.isEmpty()) {
                    outline.setPath(path);
                    break;
                }
                break;
            default:
                t60.m214695b6(view, "view");
                t60.m214695b6(outline, "outline");
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), ((sj1) this.f56685a1).f60006a6);
                break;
        }
    }
}
