package p000;

import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.google.android.material.imageview.ShapeableImageView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class m01 extends ViewOutlineProvider {

    /* renamed from: a0 */
    public final Rect f58219a0 = new Rect();

    /* renamed from: a1 */
    public final /* synthetic */ ShapeableImageView f58220a1;

    public m01(ShapeableImageView shapeableImageView) {
        this.f58220a1 = shapeableImageView;
    }

    @Override // android.view.ViewOutlineProvider
    public final void getOutline(View view, Outline outline) {
        ShapeableImageView shapeableImageView = this.f58220a1;
        if (shapeableImageView.f49526b1 == null) {
            return;
        }
        if (shapeableImageView.f49525b0 == null) {
            shapeableImageView.f49525b0 = new ce0(shapeableImageView.f49526b1);
        }
        RectF rectF = shapeableImageView.f49519a4;
        Rect rect = this.f58219a0;
        rectF.round(rect);
        shapeableImageView.f49525b0.setBounds(rect);
        shapeableImageView.f49525b0.getOutline(outline);
    }
}
