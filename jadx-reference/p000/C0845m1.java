package p000;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.ActionBarContainer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: m1 */
/* loaded from: classes.dex */
public final class C0845m1 extends Drawable {

    /* renamed from: a0 */
    public final ActionBarContainer f58221a0;

    public C0845m1(ActionBarContainer actionBarContainer) {
        this.f58221a0 = actionBarContainer;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        ActionBarContainer actionBarContainer = this.f58221a0;
        if (actionBarContainer.f43812a6) {
            Drawable drawable = actionBarContainer.f43811a5;
            if (drawable != null) {
                drawable.draw(canvas);
                return;
            }
            return;
        }
        Drawable drawable2 = actionBarContainer.f43809a3;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Drawable drawable3 = actionBarContainer.f43810a4;
        if (drawable3 == null || !actionBarContainer.f43813a7) {
            return;
        }
        drawable3.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        ActionBarContainer actionBarContainer = this.f58221a0;
        if (actionBarContainer.f43812a6) {
            if (actionBarContainer.f43811a5 != null) {
                actionBarContainer.f43809a3.getOutline(outline);
            }
        } else {
            Drawable drawable = actionBarContainer.f43809a3;
            if (drawable != null) {
                drawable.getOutline(outline);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
