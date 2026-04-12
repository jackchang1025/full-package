package p000;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import androidx.constraintlayout.widget.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class z11 {

    /* renamed from: a0 */
    public final float f61423a0;

    /* renamed from: a1 */
    public final float f61424a1;

    /* renamed from: a2 */
    public final float f61425a2;

    /* renamed from: a3 */
    public final float f61426a3;

    /* renamed from: a4 */
    public final int f61427a4;

    public z11(Context context, XmlResourceParser xmlResourceParser) throws Resources.NotFoundException {
        this.f61423a0 = Float.NaN;
        this.f61424a1 = Float.NaN;
        this.f61425a2 = Float.NaN;
        this.f61426a3 = Float.NaN;
        this.f61427a4 = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.Variant);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.Variant_constraints) {
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f61427a4);
                this.f61427a4 = resourceId;
                String resourceTypeName = context.getResources().getResourceTypeName(resourceId);
                context.getResources().getResourceName(resourceId);
                "layout".equals(resourceTypeName);
            } else if (index == R$styleable.Variant_region_heightLessThan) {
                this.f61426a3 = typedArrayObtainStyledAttributes.getDimension(index, this.f61426a3);
            } else if (index == R$styleable.Variant_region_heightMoreThan) {
                this.f61424a1 = typedArrayObtainStyledAttributes.getDimension(index, this.f61424a1);
            } else if (index == R$styleable.Variant_region_widthLessThan) {
                this.f61425a2 = typedArrayObtainStyledAttributes.getDimension(index, this.f61425a2);
            } else if (index == R$styleable.Variant_region_widthMoreThan) {
                this.f61423a0 = typedArrayObtainStyledAttributes.getDimension(index, this.f61423a0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* renamed from: a0 */
    public final boolean m215333a0(float f, float f2) {
        float f3 = this.f61423a0;
        if (!Float.isNaN(f3) && f < f3) {
            return false;
        }
        float f4 = this.f61424a1;
        if (!Float.isNaN(f4) && f2 < f4) {
            return false;
        }
        float f5 = this.f61425a2;
        if (!Float.isNaN(f5) && f > f5) {
            return false;
        }
        float f6 = this.f61426a3;
        return Float.isNaN(f6) || f2 <= f6;
    }
}
