package p000;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.widget.ImageView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: cz */
/* loaded from: classes2.dex */
public final class C0396cz extends Property {

    /* renamed from: a0 */
    public final /* synthetic */ int f55541a0 = 2;

    /* renamed from: a1 */
    public Object f55542a1;

    public /* synthetic */ C0396cz(Class cls, String str) {
        super(cls, str);
    }

    @Override // android.util.Property
    public final Object get(Object obj) {
        switch (this.f55541a0) {
            case 0:
                ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) obj;
                return Float.valueOf(AbstractC1249t7.m214727a0(0.0f, 1.0f, (Color.alpha(extendedFloatingActionButton.getCurrentTextColor()) / 255.0f) / Color.alpha(extendedFloatingActionButton.f49493d2.getColorForState(extendedFloatingActionButton.getDrawableState(), ((AbstractC0408da) this.f55542a1).f55588a1.f49493d2.getDefaultColor()))));
            case 1:
                ((Drawable) obj).copyBounds((Rect) this.f55542a1);
                return new PointF(r0.left, r0.top);
            default:
                Matrix matrix = (Matrix) this.f55542a1;
                matrix.set(((ImageView) obj).getImageMatrix());
                return matrix;
        }
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        switch (this.f55541a0) {
            case 0:
                ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) obj;
                Float f = (Float) obj2;
                int colorForState = extendedFloatingActionButton.f49493d2.getColorForState(extendedFloatingActionButton.getDrawableState(), ((AbstractC0408da) this.f55542a1).f55588a1.f49493d2.getDefaultColor());
                ColorStateList colorStateListValueOf = ColorStateList.valueOf(Color.argb((int) (AbstractC1249t7.m214727a0(0.0f, Color.alpha(colorForState) / 255.0f, f.floatValue()) * 255.0f), Color.red(colorForState), Color.green(colorForState), Color.blue(colorForState)));
                if (f.floatValue() != 1.0f) {
                    extendedFloatingActionButton.m211037a5(colorStateListValueOf);
                    break;
                } else {
                    extendedFloatingActionButton.m211037a5(extendedFloatingActionButton.f49493d2);
                    break;
                }
            case 1:
                Drawable drawable = (Drawable) obj;
                PointF pointF = (PointF) obj2;
                Rect rect = (Rect) this.f55542a1;
                drawable.copyBounds(rect);
                rect.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
                drawable.setBounds(rect);
                break;
            default:
                ((ImageView) obj).setImageMatrix((Matrix) obj2);
                break;
        }
    }

    public C0396cz() {
        super(Matrix.class, "imageMatrixProperty");
        this.f55542a1 = new Matrix();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0396cz(AbstractC0408da abstractC0408da) {
        super(Float.class, "LABEL_OPACITY_PROPERTY");
        this.f55542a1 = abstractC0408da;
    }
}
