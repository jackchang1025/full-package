package p000;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class qq0 extends ViewGroup.MarginLayoutParams {

    /* renamed from: a0 */
    public dr0 f59544a0;

    /* renamed from: a1 */
    public final Rect f59545a1;

    /* renamed from: a2 */
    public boolean f59546a2;

    /* renamed from: a3 */
    public boolean f59547a3;

    public qq0(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f59545a1 = new Rect();
        this.f59546a2 = true;
        this.f59547a3 = false;
    }

    public qq0(int i, int i2) {
        super(i, i2);
        this.f59545a1 = new Rect();
        this.f59546a2 = true;
        this.f59547a3 = false;
    }

    public qq0(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
        this.f59545a1 = new Rect();
        this.f59546a2 = true;
        this.f59547a3 = false;
    }

    public qq0(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f59545a1 = new Rect();
        this.f59546a2 = true;
        this.f59547a3 = false;
    }

    public qq0(qq0 qq0Var) {
        super((ViewGroup.LayoutParams) qq0Var);
        this.f59545a1 = new Rect();
        this.f59546a2 = true;
        this.f59547a3 = false;
    }
}
