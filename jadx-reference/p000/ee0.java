package p000;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.R$attr;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class ee0 {

    /* renamed from: a0 */
    public static final int[] f55974a0 = {R.attr.theme, R$attr.theme};

    /* renamed from: a1 */
    public static final int[] f55975a1 = {R$attr.materialThemeOverlay};

    /* renamed from: a0 */
    public static Context m212666a0(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f55975a1, i, i2);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        boolean z = (context instanceof C0875mu) && ((C0875mu) context).f58398a0 == resourceId;
        if (resourceId == 0 || z) {
            return context;
        }
        C0875mu c0875mu = new C0875mu(context, resourceId);
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, f55974a0);
        int resourceId2 = typedArrayObtainStyledAttributes2.getResourceId(0, 0);
        int resourceId3 = typedArrayObtainStyledAttributes2.getResourceId(1, 0);
        typedArrayObtainStyledAttributes2.recycle();
        if (resourceId2 == 0) {
            resourceId2 = resourceId3;
        }
        if (resourceId2 != 0) {
            c0875mu.getTheme().applyStyle(resourceId2, true);
        }
        return c0875mu;
    }
}
