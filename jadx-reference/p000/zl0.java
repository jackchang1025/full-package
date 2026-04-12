package p000;

import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zl0 extends View {

    /* renamed from: a0 */
    public final /* synthetic */ int f61557a0;

    /* renamed from: a1 */
    public final /* synthetic */ float f61558a1;

    /* renamed from: a2 */
    public final /* synthetic */ Drawable f61559a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ zl0(ContextWrapper contextWrapper, float f, Drawable drawable, int i) {
        super(contextWrapper);
        this.f61557a0 = i;
        this.f61558a1 = f;
        this.f61559a2 = drawable;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        switch (this.f61557a0) {
            case 0:
                t60.m214695b6(canvas, "canvas");
                Path path = new Path();
                RectF rectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
                float f = this.f61558a1;
                path.addRoundRect(rectF, f, f, Path.Direction.CW);
                canvas.clipPath(path);
                int width = getWidth();
                int height = getHeight();
                Drawable drawable = this.f61559a2;
                drawable.setBounds(0, 0, width, height);
                drawable.draw(canvas);
                break;
            default:
                t60.m214695b6(canvas, "canvas");
                Path path2 = new Path();
                RectF rectF2 = new RectF(0.0f, 0.0f, getWidth(), getHeight());
                float f2 = this.f61558a1;
                path2.addRoundRect(rectF2, f2, f2, Path.Direction.CW);
                canvas.clipPath(path2);
                int width2 = getWidth();
                int height2 = getHeight();
                Drawable drawable2 = this.f61559a2;
                drawable2.setBounds(0, 0, width2, height2);
                drawable2.draw(canvas);
                break;
        }
    }
}
