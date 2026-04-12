package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintAttribute$AttributeType;
import androidx.constraintlayout.widget.R$styleable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kw */
/* loaded from: classes.dex */
public final class C0798kw {

    /* renamed from: a0 */
    public boolean f57732a0 = false;

    /* renamed from: a1 */
    public String f57733a1;

    /* renamed from: a2 */
    public ConstraintAttribute$AttributeType f57734a2;

    /* renamed from: a3 */
    public int f57735a3;

    /* renamed from: a4 */
    public float f57736a4;

    /* renamed from: a5 */
    public String f57737a5;

    /* renamed from: a6 */
    public boolean f57738a6;

    /* renamed from: a7 */
    public int f57739a7;

    public C0798kw(C0798kw c0798kw, Object obj) {
        this.f57733a1 = c0798kw.f57733a1;
        this.f57734a2 = c0798kw.f57734a2;
        m213763a5(obj);
    }

    /* renamed from: a3 */
    public static void m213758a3(Context context, XmlResourceParser xmlResourceParser, HashMap map) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.CustomAttribute);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        String string = null;
        Object objValueOf = null;
        ConstraintAttribute$AttributeType constraintAttribute$AttributeType = null;
        boolean z = false;
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.CustomAttribute_attributeName) {
                string = typedArrayObtainStyledAttributes.getString(index);
                if (string != null && string.length() > 0) {
                    string = Character.toUpperCase(string.charAt(0)) + string.substring(1);
                }
            } else if (index == R$styleable.CustomAttribute_methodName) {
                string = typedArrayObtainStyledAttributes.getString(index);
                z = true;
            } else if (index == R$styleable.CustomAttribute_customBoolean) {
                objValueOf = Boolean.valueOf(typedArrayObtainStyledAttributes.getBoolean(index, false));
                constraintAttribute$AttributeType = ConstraintAttribute$AttributeType.f44758a5;
            } else if (index == R$styleable.CustomAttribute_customColorValue) {
                objValueOf = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(index, 0));
                constraintAttribute$AttributeType = ConstraintAttribute$AttributeType.f44755a2;
            } else if (index == R$styleable.CustomAttribute_customColorDrawableValue) {
                objValueOf = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(index, 0));
                constraintAttribute$AttributeType = ConstraintAttribute$AttributeType.f44756a3;
            } else {
                int i2 = R$styleable.CustomAttribute_customPixelDimension;
                ConstraintAttribute$AttributeType constraintAttribute$AttributeType2 = ConstraintAttribute$AttributeType.f44759a6;
                if (index == i2) {
                    objValueOf = Float.valueOf(TypedValue.applyDimension(1, typedArrayObtainStyledAttributes.getDimension(index, 0.0f), context.getResources().getDisplayMetrics()));
                } else if (index == R$styleable.CustomAttribute_customDimension) {
                    objValueOf = Float.valueOf(typedArrayObtainStyledAttributes.getDimension(index, 0.0f));
                } else if (index == R$styleable.CustomAttribute_customFloatValue) {
                    objValueOf = Float.valueOf(typedArrayObtainStyledAttributes.getFloat(index, Float.NaN));
                    constraintAttribute$AttributeType = ConstraintAttribute$AttributeType.f44754a1;
                } else if (index == R$styleable.CustomAttribute_customIntegerValue) {
                    objValueOf = Integer.valueOf(typedArrayObtainStyledAttributes.getInteger(index, -1));
                    constraintAttribute$AttributeType = ConstraintAttribute$AttributeType.f44753a0;
                } else if (index == R$styleable.CustomAttribute_customStringValue) {
                    objValueOf = typedArrayObtainStyledAttributes.getString(index);
                    constraintAttribute$AttributeType = ConstraintAttribute$AttributeType.f44757a4;
                } else if (index == R$styleable.CustomAttribute_customReference) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    if (resourceId == -1) {
                        resourceId = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    objValueOf = Integer.valueOf(resourceId);
                    constraintAttribute$AttributeType = ConstraintAttribute$AttributeType.f44760a7;
                }
                constraintAttribute$AttributeType = constraintAttribute$AttributeType2;
            }
        }
        if (string != null && objValueOf != null) {
            C0798kw c0798kw = new C0798kw();
            c0798kw.f57733a1 = string;
            c0798kw.f57734a2 = constraintAttribute$AttributeType;
            c0798kw.f57732a0 = z;
            c0798kw.m213763a5(objValueOf);
            map.put(string, c0798kw);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* renamed from: a4 */
    public static void m213759a4(View view, HashMap map) {
        Class<?> cls = view.getClass();
        for (String strM48c9 : map.keySet()) {
            C0798kw c0798kw = (C0798kw) map.get(strM48c9);
            if (!c0798kw.f57732a0) {
                strM48c9 = AbstractC0003a2.m48c9("set", strM48c9);
            }
            try {
                try {
                    int iOrdinal = c0798kw.f57734a2.ordinal();
                    Class cls2 = Float.TYPE;
                    Class cls3 = Integer.TYPE;
                    switch (iOrdinal) {
                        case 0:
                            cls.getMethod(strM48c9, cls3).invoke(view, Integer.valueOf(c0798kw.f57735a3));
                            break;
                        case 1:
                            cls.getMethod(strM48c9, cls2).invoke(view, Float.valueOf(c0798kw.f57736a4));
                            break;
                        case 2:
                            cls.getMethod(strM48c9, cls3).invoke(view, Integer.valueOf(c0798kw.f57739a7));
                            break;
                        case 3:
                            Method method = cls.getMethod(strM48c9, Drawable.class);
                            ColorDrawable colorDrawable = new ColorDrawable();
                            colorDrawable.setColor(c0798kw.f57739a7);
                            method.invoke(view, colorDrawable);
                            break;
                        case 4:
                            cls.getMethod(strM48c9, CharSequence.class).invoke(view, c0798kw.f57737a5);
                            break;
                        case 5:
                            cls.getMethod(strM48c9, Boolean.TYPE).invoke(view, Boolean.valueOf(c0798kw.f57738a6));
                            break;
                        case 6:
                            cls.getMethod(strM48c9, cls2).invoke(view, Float.valueOf(c0798kw.f57736a4));
                            break;
                        case 7:
                            cls.getMethod(strM48c9, cls3).invoke(view, Integer.valueOf(c0798kw.f57735a3));
                            break;
                    }
                } catch (NoSuchMethodException e) {
                    e.getMessage();
                }
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        }
    }

    /* renamed from: a0 */
    public final float m213760a0() {
        switch (this.f57734a2.ordinal()) {
            case 0:
                return this.f57735a3;
            case 1:
                return this.f57736a4;
            case 2:
            case 3:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case 4:
                throw new RuntimeException("Cannot interpolate String");
            case 5:
                return this.f57738a6 ? 1.0f : 0.0f;
            case 6:
                return this.f57736a4;
            default:
                return Float.NaN;
        }
    }

    /* renamed from: a1 */
    public final void m213761a1(float[] fArr) {
        switch (this.f57734a2.ordinal()) {
            case 0:
                fArr[0] = this.f57735a3;
                return;
            case 1:
                fArr[0] = this.f57736a4;
                return;
            case 2:
            case 3:
                int i = this.f57739a7;
                int i2 = (i >> 24) & v10.MASK;
                int i3 = (i >> 16) & v10.MASK;
                int i4 = (i >> 8) & v10.MASK;
                int i5 = i & v10.MASK;
                float fPow = (float) Math.pow(i3 / 255.0f, 2.2d);
                float fPow2 = (float) Math.pow(i4 / 255.0f, 2.2d);
                float fPow3 = (float) Math.pow(i5 / 255.0f, 2.2d);
                fArr[0] = fPow;
                fArr[1] = fPow2;
                fArr[2] = fPow3;
                fArr[3] = i2 / 255.0f;
                return;
            case 4:
                throw new RuntimeException("Color does not have a single color to interpolate");
            case 5:
                fArr[0] = this.f57738a6 ? 1.0f : 0.0f;
                return;
            case 6:
                fArr[0] = this.f57736a4;
                return;
            default:
                return;
        }
    }

    /* renamed from: a2 */
    public final int m213762a2() {
        int iOrdinal = this.f57734a2.ordinal();
        return (iOrdinal == 2 || iOrdinal == 3) ? 4 : 1;
    }

    /* renamed from: a5 */
    public final void m213763a5(Object obj) {
        switch (this.f57734a2.ordinal()) {
            case 0:
            case 7:
                this.f57735a3 = ((Integer) obj).intValue();
                break;
            case 1:
                this.f57736a4 = ((Float) obj).floatValue();
                break;
            case 2:
            case 3:
                this.f57739a7 = ((Integer) obj).intValue();
                break;
            case 4:
                this.f57737a5 = (String) obj;
                break;
            case 5:
                this.f57738a6 = ((Boolean) obj).booleanValue();
                break;
            case 6:
                this.f57736a4 = ((Float) obj).floatValue();
                break;
        }
    }
}
