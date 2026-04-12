package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.R$styleable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nb */
/* loaded from: classes.dex */
public final class C0907nb extends ViewGroup.MarginLayoutParams {

    /* renamed from: a0 */
    public AbstractC0879my f58470a0;

    /* renamed from: a1 */
    public boolean f58471a1;

    /* renamed from: a2 */
    public final int f58472a2;

    /* renamed from: a3 */
    public int f58473a3;

    /* renamed from: a4 */
    public final int f58474a4;

    /* renamed from: a5 */
    public final int f58475a5;

    /* renamed from: a6 */
    public final int f58476a6;

    /* renamed from: a7 */
    public int f58477a7;

    /* renamed from: a8 */
    public int f58478a8;

    /* renamed from: a9 */
    public int f58479a9;

    /* renamed from: b0 */
    public View f58480b0;

    /* renamed from: b1 */
    public View f58481b1;

    /* renamed from: b2 */
    public boolean f58482b2;

    /* renamed from: b3 */
    public boolean f58483b3;

    /* renamed from: b4 */
    public boolean f58484b4;

    /* renamed from: b5 */
    public final Rect f58485b5;

    public C0907nb() {
        super(-2, -2);
        this.f58471a1 = false;
        this.f58472a2 = 0;
        this.f58473a3 = 0;
        this.f58474a4 = -1;
        this.f58475a5 = -1;
        this.f58476a6 = 0;
        this.f58477a7 = 0;
        this.f58485b5 = new Rect();
    }

    /* renamed from: a0 */
    public final boolean m214063a0(int i) {
        if (i == 0) {
            return this.f58482b2;
        }
        if (i != 1) {
            return false;
        }
        return this.f58483b3;
    }

    public C0907nb(Context context, AttributeSet attributeSet) throws NoSuchMethodException, SecurityException {
        AbstractC0879my abstractC0879my;
        super(context, attributeSet);
        this.f58471a1 = false;
        this.f58472a2 = 0;
        this.f58473a3 = 0;
        this.f58474a4 = -1;
        this.f58475a5 = -1;
        this.f58476a6 = 0;
        this.f58477a7 = 0;
        this.f58485b5 = new Rect();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout_Layout);
        this.f58472a2 = typedArrayObtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
        this.f58475a5 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.CoordinatorLayout_Layout_layout_anchor, -1);
        this.f58473a3 = typedArrayObtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
        this.f58474a4 = typedArrayObtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_layout_keyline, -1);
        this.f58476a6 = typedArrayObtainStyledAttributes.getInt(R$styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
        this.f58477a7 = typedArrayObtainStyledAttributes.getInt(R$styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R$styleable.CoordinatorLayout_Layout_layout_behavior);
        this.f58471a1 = zHasValue;
        if (zHasValue) {
            String string = typedArrayObtainStyledAttributes.getString(R$styleable.CoordinatorLayout_Layout_layout_behavior);
            String str = CoordinatorLayout.f44803b9;
            if (TextUtils.isEmpty(string)) {
                abstractC0879my = null;
            } else {
                if (string.startsWith(".")) {
                    string = context.getPackageName() + string;
                } else if (string.indexOf(46) < 0) {
                    String str2 = CoordinatorLayout.f44803b9;
                    if (!TextUtils.isEmpty(str2)) {
                        string = str2 + '.' + string;
                    }
                }
                try {
                    ThreadLocal threadLocal = CoordinatorLayout.f44805c1;
                    Map map = (Map) threadLocal.get();
                    if (map == null) {
                        map = new HashMap();
                        threadLocal.set(map);
                    }
                    Constructor<?> constructor = (Constructor) map.get(string);
                    if (constructor == null) {
                        constructor = Class.forName(string, false, context.getClassLoader()).getConstructor(CoordinatorLayout.f44804c0);
                        constructor.setAccessible(true);
                        map.put(string, constructor);
                    }
                    abstractC0879my = (AbstractC0879my) constructor.newInstance(context, attributeSet);
                } catch (Exception e) {
                    throw new RuntimeException(AbstractC0003a2.m48c9("Could not inflate Behavior subclass ", string), e);
                }
            }
            this.f58470a0 = abstractC0879my;
        }
        typedArrayObtainStyledAttributes.recycle();
        AbstractC0879my abstractC0879my2 = this.f58470a0;
        if (abstractC0879my2 != null) {
            abstractC0879my2.mo210935a2(this);
        }
    }

    public C0907nb(C0907nb c0907nb) {
        super((ViewGroup.MarginLayoutParams) c0907nb);
        this.f58471a1 = false;
        this.f58472a2 = 0;
        this.f58473a3 = 0;
        this.f58474a4 = -1;
        this.f58475a5 = -1;
        this.f58476a6 = 0;
        this.f58477a7 = 0;
        this.f58485b5 = new Rect();
    }

    public C0907nb(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
        this.f58471a1 = false;
        this.f58472a2 = 0;
        this.f58473a3 = 0;
        this.f58474a4 = -1;
        this.f58475a5 = -1;
        this.f58476a6 = 0;
        this.f58477a7 = 0;
        this.f58485b5 = new Rect();
    }

    public C0907nb(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f58471a1 = false;
        this.f58472a2 = 0;
        this.f58473a3 = 0;
        this.f58474a4 = -1;
        this.f58475a5 = -1;
        this.f58476a6 = 0;
        this.f58477a7 = 0;
        this.f58485b5 = new Rect();
    }
}
