package p000;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.appcompat.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class k61 {

    /* renamed from: a0 */
    public static final ThreadLocal f57464a0 = new ThreadLocal();

    /* renamed from: a1 */
    public static final int[] f57465a1 = {-16842910};

    /* renamed from: a2 */
    public static final int[] f57466a2 = {R.attr.state_focused};

    /* renamed from: a3 */
    public static final int[] f57467a3 = {R.attr.state_pressed};

    /* renamed from: a4 */
    public static final int[] f57468a4 = {R.attr.state_checked};

    /* renamed from: a5 */
    public static final int[] f57469a5 = new int[0];

    /* renamed from: a6 */
    public static final int[] f57470a6 = new int[1];

    /* renamed from: a0 */
    public static void m213453a0(View view, Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R$styleable.AppCompatTheme);
        try {
            if (!typedArrayObtainStyledAttributes.hasValue(R$styleable.AppCompatTheme_windowActionBar)) {
                view.getClass().toString();
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a1 */
    public static int m213454a1(Context context, int i) {
        ColorStateList colorStateListM213456a3 = m213456a3(context, i);
        if (colorStateListM213456a3 != null && colorStateListM213456a3.isStateful()) {
            return colorStateListM213456a3.getColorForState(f57465a1, colorStateListM213456a3.getDefaultColor());
        }
        ThreadLocal threadLocal = f57464a0;
        TypedValue typedValue = (TypedValue) threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        float f = typedValue.getFloat();
        return AbstractC0724jn.m213334a4(m213455a2(context, i), Math.round(Color.alpha(r4) * f));
    }

    /* renamed from: a2 */
    public static int m213455a2(Context context, int i) {
        int[] iArr = f57470a6;
        iArr[0] = i;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, iArr);
        try {
            return typedArrayObtainStyledAttributes.getColor(0, 0);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a3 */
    public static ColorStateList m213456a3(Context context, int i) {
        ColorStateList colorStateList;
        int resourceId;
        int[] iArr = f57470a6;
        iArr[0] = i;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, iArr);
        try {
            if (!typedArrayObtainStyledAttributes.hasValue(0) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0)) == 0 || (colorStateList = AbstractC1117qo.m214426c2(context, resourceId)) == null) {
                colorStateList = typedArrayObtainStyledAttributes.getColorStateList(0);
            }
            return colorStateList;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
