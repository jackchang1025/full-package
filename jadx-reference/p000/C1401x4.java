package p000;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Xml;
import android.widget.ImageView;
import androidx.core.R$styleable;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x4 */
/* loaded from: classes.dex */
public final class C1401x4 {

    /* renamed from: a0 */
    public int f61016a0;

    /* renamed from: a1 */
    public final Object f61017a1;

    /* renamed from: a2 */
    public Object f61018a2;

    public C1401x4(ImageView imageView) {
        this.f61016a0 = 0;
        this.f61017a1 = imageView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0202, code lost:
    
        r0 = new p000.og1(r5, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0208, code lost:
    
        if (r9 == 1) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x020b, code lost:
    
        if (r9 == 2) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x020d, code lost:
    
        r17 = (int[]) r0.f58832a0;
        r18 = (float[]) r0.f58833a1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x021b, code lost:
    
        if (r6 == 1) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x021d, code lost:
    
        if (r6 == 2) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x021f, code lost:
    
        r0 = android.graphics.Shader.TileMode.CLAMP;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x022a, code lost:
    
        r0 = android.graphics.Shader.TileMode.MIRROR;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x022d, code lost:
    
        r0 = android.graphics.Shader.TileMode.REPEAT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0230, code lost:
    
        r12 = new android.graphics.LinearGradient(r21, r22, r26, r16, r17, r18, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0234, code lost:
    
        r12 = new android.graphics.SweepGradient(r7, r10, (int[]) r0.f58832a0, (float[]) r0.f58833a1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0246, code lost:
    
        if (r25 <= 0.0f) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0248, code lost:
    
        r21 = (int[]) r0.f58832a0;
        r22 = (float[]) r0.f58833a1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0258, code lost:
    
        if (r6 == 1) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x025a, code lost:
    
        if (r6 == 2) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x025c, code lost:
    
        r0 = android.graphics.Shader.TileMode.CLAMP;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0267, code lost:
    
        r0 = android.graphics.Shader.TileMode.MIRROR;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x026a, code lost:
    
        r0 = android.graphics.Shader.TileMode.REPEAT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x026d, code lost:
    
        r12 = new android.graphics.RadialGradient(r7, r10, r25, r21, r22, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0279, code lost:
    
        return new p000.C1401x4(r12, null, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0281, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01ed, code lost:
    
        if (r2.size() <= 0) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x01ef, code lost:
    
        r0 = new p000.og1(r2, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01f5, code lost:
    
        r0 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01f6, code lost:
    
        if (r0 == null) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01fa, code lost:
    
        if (r19 == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01fc, code lost:
    
        r0 = new p000.og1(r5, r11, r12);
     */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static C1401x4 m215115a1(Resources resources, int i, Resources.Theme theme) {
        int next;
        float f;
        Resources resources2 = resources;
        XmlResourceParser xml = resources.getXml(i);
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
        do {
            next = xml.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        String name = xml.getName();
        name.getClass();
        if (!name.equals("gradient")) {
            if (name.equals("selector")) {
                ColorStateList colorStateListM213329a1 = AbstractC0723jm.m213329a1(resources2, xml, attributeSetAsAttributeSet, theme);
                return new C1401x4(null, colorStateListM213329a1, colorStateListM213329a1.getDefaultColor());
            }
            throw new XmlPullParserException(xml.getPositionDescription() + ": unsupported complex color tag " + name);
        }
        String name2 = xml.getName();
        if (!name2.equals("gradient")) {
            throw new XmlPullParserException(xml.getPositionDescription() + ": invalid gradient color tag " + name2);
        }
        TypedArray typedArrayM210588d7 = b81.m210588d7(resources2, theme, attributeSetAsAttributeSet, R$styleable.GradientColor);
        float f2 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "startX") != null ? typedArrayM210588d7.getFloat(R$styleable.GradientColor_android_startX, 0.0f) : 0.0f;
        float f3 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "startY") != null ? typedArrayM210588d7.getFloat(R$styleable.GradientColor_android_startY, 0.0f) : 0.0f;
        float f4 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "endX") != null ? typedArrayM210588d7.getFloat(R$styleable.GradientColor_android_endX, 0.0f) : 0.0f;
        float f5 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "endY") != null ? typedArrayM210588d7.getFloat(R$styleable.GradientColor_android_endY, 0.0f) : 0.0f;
        float f6 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerX") != null ? typedArrayM210588d7.getFloat(R$styleable.GradientColor_android_centerX, 0.0f) : 0.0f;
        float f7 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerY") != null ? typedArrayM210588d7.getFloat(R$styleable.GradientColor_android_centerY, 0.0f) : 0.0f;
        int i2 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "type") != null ? typedArrayM210588d7.getInt(R$styleable.GradientColor_android_type, 0) : 0;
        int i3 = 1;
        int color = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "startColor") != null ? typedArrayM210588d7.getColor(R$styleable.GradientColor_android_startColor, 0) : 0;
        boolean z = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerColor") != null;
        int color2 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "centerColor") != null ? typedArrayM210588d7.getColor(R$styleable.GradientColor_android_centerColor, 0) : 0;
        int color3 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "endColor") != null ? typedArrayM210588d7.getColor(R$styleable.GradientColor_android_endColor, 0) : 0;
        float f8 = f2;
        int i4 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "tileMode") != null ? typedArrayM210588d7.getInt(R$styleable.GradientColor_android_tileMode, 0) : 0;
        float f9 = f3;
        float f10 = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "gradientRadius") != null ? typedArrayM210588d7.getFloat(R$styleable.GradientColor_android_gradientRadius, 0.0f) : 0.0f;
        typedArrayM210588d7.recycle();
        int depth = xml.getDepth() + 1;
        ArrayList arrayList = new ArrayList(20);
        ArrayList arrayList2 = new ArrayList(20);
        while (true) {
            int next2 = xml.next();
            float f11 = f10;
            if (next2 == i3) {
                f = f4;
                break;
            }
            int depth2 = xml.getDepth();
            f = f4;
            if (depth2 < depth && next2 == 3) {
                break;
            }
            if (next2 == 2) {
                if (depth2 > depth) {
                    resources2 = resources;
                } else if (xml.getName().equals("item")) {
                    TypedArray typedArrayM210588d72 = b81.m210588d7(resources2, theme, attributeSetAsAttributeSet, R$styleable.GradientColorItem);
                    boolean zHasValue = typedArrayM210588d72.hasValue(R$styleable.GradientColorItem_android_color);
                    boolean zHasValue2 = typedArrayM210588d72.hasValue(R$styleable.GradientColorItem_android_offset);
                    if (!zHasValue || !zHasValue2) {
                        break;
                    }
                    int color4 = typedArrayM210588d72.getColor(R$styleable.GradientColorItem_android_color, 0);
                    float f12 = typedArrayM210588d72.getFloat(R$styleable.GradientColorItem_android_offset, 0.0f);
                    typedArrayM210588d72.recycle();
                    arrayList2.add(Integer.valueOf(color4));
                    arrayList.add(Float.valueOf(f12));
                    resources2 = resources;
                } else {
                    continue;
                }
            }
            f10 = f11;
            f4 = f;
            i3 = 1;
        }
        throw new XmlPullParserException(xml.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
    }

    /* renamed from: a0 */
    public void m215116a0() {
        t61 t61Var;
        ImageView imageView = (ImageView) this.f61017a1;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            AbstractC1274tv.m214790a0(drawable);
        }
        if (drawable == null || (t61Var = (t61) this.f61018a2) == null) {
            return;
        }
        C1398x1.m215098a4(drawable, t61Var, imageView.getDrawableState());
    }

    /* renamed from: a2 */
    public boolean m215117a2() {
        ColorStateList colorStateList;
        return ((Shader) this.f61017a1) == null && (colorStateList = (ColorStateList) this.f61018a2) != null && colorStateList.isStateful();
    }

    /* renamed from: a3 */
    public void m215118a3(AttributeSet attributeSet, int i) {
        int resourceId;
        ImageView imageView = (ImageView) this.f61017a1;
        pg1 pg1VarM214255d2 = pg1.m214255d2(imageView.getContext(), attributeSet, androidx.appcompat.R$styleable.AppCompatImageView, i);
        TypedArray typedArray = (TypedArray) pg1VarM214255d2.f59230a2;
        xa1.m215151b3(imageView, imageView.getContext(), androidx.appcompat.R$styleable.AppCompatImageView, attributeSet, (TypedArray) pg1VarM214255d2.f59230a2, i);
        try {
            Drawable drawable = imageView.getDrawable();
            if (drawable == null && (resourceId = typedArray.getResourceId(androidx.appcompat.R$styleable.AppCompatImageView_srcCompat, -1)) != -1 && (drawable = b81.m210576b7(imageView.getContext(), resourceId)) != null) {
                imageView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                AbstractC1274tv.m214790a0(drawable);
            }
            if (typedArray.hasValue(androidx.appcompat.R$styleable.AppCompatImageView_tint)) {
                h50.m212997a2(imageView, pg1VarM214255d2.m214276c0(androidx.appcompat.R$styleable.AppCompatImageView_tint));
            }
            if (typedArray.hasValue(androidx.appcompat.R$styleable.AppCompatImageView_tintMode)) {
                h50.m212998a3(imageView, AbstractC1274tv.m214792a2(typedArray.getInt(androidx.appcompat.R$styleable.AppCompatImageView_tintMode, -1), null));
            }
            pg1VarM214255d2.m214288d4();
        } catch (Throwable th) {
            pg1VarM214255d2.m214288d4();
            throw th;
        }
    }

    /* renamed from: a4 */
    public void m215119a4(int i) {
        ImageView imageView = (ImageView) this.f61017a1;
        if (i != 0) {
            Drawable drawableM210576b7 = b81.m210576b7(imageView.getContext(), i);
            if (drawableM210576b7 != null) {
                AbstractC1274tv.m214790a0(drawableM210576b7);
            }
            imageView.setImageDrawable(drawableM210576b7);
        } else {
            imageView.setImageDrawable(null);
        }
        m215116a0();
    }

    public C1401x4(Shader shader, ColorStateList colorStateList, int i) {
        this.f61017a1 = shader;
        this.f61018a2 = colorStateList;
        this.f61016a0 = i;
    }
}
