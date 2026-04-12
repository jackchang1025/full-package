package p000;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lc */
/* loaded from: classes.dex */
public final class C0815lc {

    /* renamed from: a0 */
    public final float f57874a0;

    /* renamed from: a1 */
    public final float f57875a1;

    /* renamed from: a2 */
    public final float f57876a2;

    /* renamed from: a3 */
    public final float f57877a3;

    /* renamed from: a4 */
    public final int f57878a4;

    /* renamed from: a5 */
    public final C0825lm f57879a5;

    public C0815lc(Context context, XmlResourceParser xmlResourceParser) throws Resources.NotFoundException {
        this.f57874a0 = Float.NaN;
        this.f57875a1 = Float.NaN;
        this.f57876a2 = Float.NaN;
        this.f57877a3 = Float.NaN;
        this.f57878a4 = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.Variant);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.Variant_constraints) {
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f57878a4);
                this.f57878a4 = resourceId;
                String resourceTypeName = context.getResources().getResourceTypeName(resourceId);
                context.getResources().getResourceName(resourceId);
                if ("layout".equals(resourceTypeName)) {
                    C0825lm c0825lm = new C0825lm();
                    this.f57879a5 = c0825lm;
                    c0825lm.m213868a4((ConstraintLayout) LayoutInflater.from(context).inflate(resourceId, (ViewGroup) null));
                }
            } else if (index == R$styleable.Variant_region_heightLessThan) {
                this.f57877a3 = typedArrayObtainStyledAttributes.getDimension(index, this.f57877a3);
            } else if (index == R$styleable.Variant_region_heightMoreThan) {
                this.f57875a1 = typedArrayObtainStyledAttributes.getDimension(index, this.f57875a1);
            } else if (index == R$styleable.Variant_region_widthLessThan) {
                this.f57876a2 = typedArrayObtainStyledAttributes.getDimension(index, this.f57876a2);
            } else if (index == R$styleable.Variant_region_widthMoreThan) {
                this.f57874a0 = typedArrayObtainStyledAttributes.getDimension(index, this.f57874a0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* renamed from: a0 */
    public final boolean m213831a0(float f, float f2) {
        float f3 = this.f57874a0;
        if (!Float.isNaN(f3) && f < f3) {
            return false;
        }
        float f4 = this.f57875a1;
        if (!Float.isNaN(f4) && f2 < f4) {
            return false;
        }
        float f5 = this.f57876a2;
        if (!Float.isNaN(f5) && f > f5) {
            return false;
        }
        float f6 = this.f57877a3;
        return Float.isNaN(f6) || f2 <= f6;
    }
}
