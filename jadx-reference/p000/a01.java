package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import com.google.android.material.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class a01 {

    /* renamed from: b2 */
    public static final hr0 f6b2 = new hr0(0.5f);

    /* renamed from: a0 */
    public b81 f7a0 = new ns0();

    /* renamed from: a1 */
    public b81 f8a1 = new ns0();

    /* renamed from: a2 */
    public b81 f9a2 = new ns0();

    /* renamed from: a3 */
    public b81 f10a3 = new ns0();

    /* renamed from: a4 */
    public InterfaceC0909nd f11a4 = new C0481f3(0.0f);

    /* renamed from: a5 */
    public InterfaceC0909nd f12a5 = new C0481f3(0.0f);

    /* renamed from: a6 */
    public InterfaceC0909nd f13a6 = new C0481f3(0.0f);

    /* renamed from: a7 */
    public InterfaceC0909nd f14a7 = new C0481f3(0.0f);

    /* renamed from: a8 */
    public C1351vv f15a8;

    /* renamed from: a9 */
    public C1351vv f16a9;

    /* renamed from: b0 */
    public C1351vv f17b0;

    /* renamed from: b1 */
    public C1351vv f18b1;

    public a01() {
        int i = 0;
        this.f15a8 = new C1351vv(i);
        this.f16a9 = new C1351vv(i);
        this.f17b0 = new C1351vv(i);
        this.f18b1 = new C1351vv(i);
    }

    /* renamed from: a0 */
    public static xg1 m11a0(Context context, int i, int i2) {
        return m12a1(context, i, i2, new C0481f3(0));
    }

    /* renamed from: a1 */
    public static xg1 m12a1(Context context, int i, int i2, InterfaceC0909nd interfaceC0909nd) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        if (i2 != 0) {
            contextThemeWrapper = new ContextThemeWrapper(contextThemeWrapper, i2);
        }
        TypedArray typedArrayObtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R$styleable.ShapeAppearance);
        try {
            int i3 = typedArrayObtainStyledAttributes.getInt(R$styleable.ShapeAppearance_cornerFamily, 0);
            int i4 = typedArrayObtainStyledAttributes.getInt(R$styleable.ShapeAppearance_cornerFamilyTopLeft, i3);
            int i5 = typedArrayObtainStyledAttributes.getInt(R$styleable.ShapeAppearance_cornerFamilyTopRight, i3);
            int i6 = typedArrayObtainStyledAttributes.getInt(R$styleable.ShapeAppearance_cornerFamilyBottomRight, i3);
            int i7 = typedArrayObtainStyledAttributes.getInt(R$styleable.ShapeAppearance_cornerFamilyBottomLeft, i3);
            InterfaceC0909nd interfaceC0909ndM15a4 = m15a4(typedArrayObtainStyledAttributes, R$styleable.ShapeAppearance_cornerSize, interfaceC0909nd);
            InterfaceC0909nd interfaceC0909ndM15a42 = m15a4(typedArrayObtainStyledAttributes, R$styleable.ShapeAppearance_cornerSizeTopLeft, interfaceC0909ndM15a4);
            InterfaceC0909nd interfaceC0909ndM15a43 = m15a4(typedArrayObtainStyledAttributes, R$styleable.ShapeAppearance_cornerSizeTopRight, interfaceC0909ndM15a4);
            InterfaceC0909nd interfaceC0909ndM15a44 = m15a4(typedArrayObtainStyledAttributes, R$styleable.ShapeAppearance_cornerSizeBottomRight, interfaceC0909ndM15a4);
            InterfaceC0909nd interfaceC0909ndM15a45 = m15a4(typedArrayObtainStyledAttributes, R$styleable.ShapeAppearance_cornerSizeBottomLeft, interfaceC0909ndM15a4);
            xg1 xg1Var = new xg1();
            xg1Var.f61125a0 = t60.m214699c0(i4);
            xg1Var.f61129a4 = interfaceC0909ndM15a42;
            xg1Var.f61126a1 = t60.m214699c0(i5);
            xg1Var.f61130a5 = interfaceC0909ndM15a43;
            xg1Var.f61127a2 = t60.m214699c0(i6);
            xg1Var.f61131a6 = interfaceC0909ndM15a44;
            xg1Var.f61128a3 = t60.m214699c0(i7);
            xg1Var.f61132a7 = interfaceC0909ndM15a45;
            return xg1Var;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a2 */
    public static xg1 m13a2(Context context, AttributeSet attributeSet, int i, int i2, InterfaceC0909nd interfaceC0909nd) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MaterialShape, i, i2);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialShape_shapeAppearance, 0);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialShape_shapeAppearanceOverlay, 0);
        typedArrayObtainStyledAttributes.recycle();
        return m12a1(context, resourceId, resourceId2, interfaceC0909nd);
    }

    /* renamed from: a3 */
    public static xg1 m14a3(Context context, AttributeSet attributeSet, int i, int i2) {
        return m13a2(context, attributeSet, i, i2, new C0481f3(0));
    }

    /* renamed from: a4 */
    public static InterfaceC0909nd m15a4(TypedArray typedArray, int i, InterfaceC0909nd interfaceC0909nd) {
        TypedValue typedValuePeekValue = typedArray.peekValue(i);
        if (typedValuePeekValue != null) {
            int i2 = typedValuePeekValue.type;
            if (i2 == 5) {
                return new C0481f3(TypedValue.complexToDimensionPixelSize(typedValuePeekValue.data, typedArray.getResources().getDisplayMetrics()));
            }
            if (i2 == 6) {
                return new hr0(typedValuePeekValue.getFraction(1.0f, 1.0f));
            }
        }
        return interfaceC0909nd;
    }

    /* renamed from: a5 */
    public final boolean m16a5(RectF rectF) {
        boolean z = this.f18b1.getClass().equals(C1351vv.class) && this.f16a9.getClass().equals(C1351vv.class) && this.f15a8.getClass().equals(C1351vv.class) && this.f17b0.getClass().equals(C1351vv.class);
        float fMo212732a0 = this.f11a4.mo212732a0(rectF);
        return z && ((this.f12a5.mo212732a0(rectF) > fMo212732a0 ? 1 : (this.f12a5.mo212732a0(rectF) == fMo212732a0 ? 0 : -1)) == 0 && (this.f14a7.mo212732a0(rectF) > fMo212732a0 ? 1 : (this.f14a7.mo212732a0(rectF) == fMo212732a0 ? 0 : -1)) == 0 && (this.f13a6.mo212732a0(rectF) > fMo212732a0 ? 1 : (this.f13a6.mo212732a0(rectF) == fMo212732a0 ? 0 : -1)) == 0) && ((this.f8a1 instanceof ns0) && (this.f7a0 instanceof ns0) && (this.f9a2 instanceof ns0) && (this.f10a3 instanceof ns0));
    }

    /* renamed from: a6 */
    public final xg1 m17a6() {
        xg1 xg1Var = new xg1();
        xg1Var.f61125a0 = this.f7a0;
        xg1Var.f61126a1 = this.f8a1;
        xg1Var.f61127a2 = this.f9a2;
        xg1Var.f61128a3 = this.f10a3;
        xg1Var.f61129a4 = this.f11a4;
        xg1Var.f61130a5 = this.f12a5;
        xg1Var.f61131a6 = this.f13a6;
        xg1Var.f61132a7 = this.f14a7;
        xg1Var.f61133a8 = this.f15a8;
        xg1Var.f61134a9 = this.f16a9;
        xg1Var.f61135b0 = this.f17b0;
        xg1Var.f61136b1 = this.f18b1;
        return xg1Var;
    }

    /* renamed from: a7 */
    public final a01 m18a7(zz0 zz0Var) {
        xg1 xg1VarM17a6 = m17a6();
        xg1VarM17a6.f61129a4 = zz0Var.mo209792a0(this.f11a4);
        xg1VarM17a6.f61130a5 = zz0Var.mo209792a0(this.f12a5);
        xg1VarM17a6.f61132a7 = zz0Var.mo209792a0(this.f14a7);
        xg1VarM17a6.f61131a6 = zz0Var.mo209792a0(this.f13a6);
        return xg1VarM17a6.m215177a0();
    }
}
