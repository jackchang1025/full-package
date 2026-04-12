package p000;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import java.io.IOException;
import java.util.ArrayDeque;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class s91 extends j91 {

    /* renamed from: a9 */
    public static final PorterDuff.Mode f59932a9 = PorterDuff.Mode.SRC_IN;

    /* renamed from: a1 */
    public q91 f59933a1;

    /* renamed from: a2 */
    public PorterDuffColorFilter f59934a2;

    /* renamed from: a3 */
    public ColorFilter f59935a3;

    /* renamed from: a4 */
    public boolean f59936a4;

    /* renamed from: a5 */
    public boolean f59937a5;

    /* renamed from: a6 */
    public final float[] f59938a6;

    /* renamed from: a7 */
    public final Matrix f59939a7;

    /* renamed from: a8 */
    public final Rect f59940a8;

    public s91() {
        this.f59937a5 = true;
        this.f59938a6 = new float[9];
        this.f59939a7 = new Matrix();
        this.f59940a8 = new Rect();
        q91 q91Var = new q91();
        q91Var.f59443a2 = null;
        q91Var.f59444a3 = f59932a9;
        q91Var.f59442a1 = new p91();
        this.f59933a1 = q91Var;
    }

    /* renamed from: a0 */
    public final PorterDuffColorFilter m214585a0(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        Drawable drawable = this.f57309a0;
        if (drawable == null) {
            return false;
        }
        AbstractC1270tr.m214768a1(drawable);
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Paint paint;
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        Rect rect = this.f59940a8;
        copyBounds(rect);
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }
        ColorFilter colorFilter = this.f59935a3;
        if (colorFilter == null) {
            colorFilter = this.f59934a2;
        }
        Matrix matrix = this.f59939a7;
        canvas.getMatrix(matrix);
        float[] fArr = this.f59938a6;
        matrix.getValues(fArr);
        float fAbs = Math.abs(fArr[0]);
        float fAbs2 = Math.abs(fArr[4]);
        float fAbs3 = Math.abs(fArr[1]);
        float fAbs4 = Math.abs(fArr[3]);
        if (fAbs3 != 0.0f || fAbs4 != 0.0f) {
            fAbs = 1.0f;
            fAbs2 = 1.0f;
        }
        int iWidth = (int) (rect.width() * fAbs);
        int iMin = Math.min(2048, iWidth);
        int iMin2 = Math.min(2048, (int) (rect.height() * fAbs2));
        if (iMin <= 0 || iMin2 <= 0) {
            return;
        }
        int iSave = canvas.save();
        canvas.translate(rect.left, rect.top);
        if (isAutoMirrored() && AbstractC1271ts.m214778a0(this) == 1) {
            canvas.translate(rect.width(), 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        rect.offsetTo(0, 0);
        q91 q91Var = this.f59933a1;
        Bitmap bitmap = q91Var.f59446a5;
        if (bitmap == null || iMin != bitmap.getWidth() || iMin2 != q91Var.f59446a5.getHeight()) {
            q91Var.f59446a5 = Bitmap.createBitmap(iMin, iMin2, Bitmap.Config.ARGB_8888);
            q91Var.f59451b0 = true;
        }
        if (this.f59937a5) {
            q91 q91Var2 = this.f59933a1;
            if (q91Var2.f59451b0 || q91Var2.f59447a6 != q91Var2.f59443a2 || q91Var2.f59448a7 != q91Var2.f59444a3 || q91Var2.f59450a9 != q91Var2.f59445a4 || q91Var2.f59449a8 != q91Var2.f59442a1.getRootAlpha()) {
                q91 q91Var3 = this.f59933a1;
                q91Var3.f59446a5.eraseColor(0);
                Canvas canvas2 = new Canvas(q91Var3.f59446a5);
                p91 p91Var = q91Var3.f59442a1;
                p91Var.m214242a0(p91Var.f59177a6, p91.f59170b5, canvas2, iMin, iMin2);
                q91 q91Var4 = this.f59933a1;
                q91Var4.f59447a6 = q91Var4.f59443a2;
                q91Var4.f59448a7 = q91Var4.f59444a3;
                q91Var4.f59449a8 = q91Var4.f59442a1.getRootAlpha();
                q91Var4.f59450a9 = q91Var4.f59445a4;
                q91Var4.f59451b0 = false;
            }
        } else {
            q91 q91Var5 = this.f59933a1;
            q91Var5.f59446a5.eraseColor(0);
            Canvas canvas3 = new Canvas(q91Var5.f59446a5);
            p91 p91Var2 = q91Var5.f59442a1;
            p91Var2.m214242a0(p91Var2.f59177a6, p91.f59170b5, canvas3, iMin, iMin2);
        }
        q91 q91Var6 = this.f59933a1;
        if (q91Var6.f59442a1.getRootAlpha() >= 255 && colorFilter == null) {
            paint = null;
        } else {
            if (q91Var6.f59452b1 == null) {
                Paint paint2 = new Paint();
                q91Var6.f59452b1 = paint2;
                paint2.setFilterBitmap(true);
            }
            q91Var6.f59452b1.setAlpha(q91Var6.f59442a1.getRootAlpha());
            q91Var6.f59452b1.setColorFilter(colorFilter);
            paint = q91Var6.f59452b1;
        }
        canvas.drawBitmap(q91Var6.f59446a5, (Rect) null, rect, paint);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? AbstractC1269tq.m214762a0(drawable) : this.f59933a1.f59442a1.getRootAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.getChangingConfigurations() : super.getChangingConfigurations() | this.f59933a1.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? AbstractC1270tr.m214769a2(drawable) : this.f59935a3;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (this.f57309a0 != null) {
            return new r91(this.f57309a0.getConstantState());
        }
        this.f59933a1.f59441a0 = getChangingConfigurations();
        return this.f59933a1;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.getIntrinsicHeight() : (int) this.f59933a1.f59442a1.f59179a8;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.getIntrinsicWidth() : (int) this.f59933a1.f59442a1.f59178a7;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? AbstractC1269tq.m214765a3(drawable) : this.f59933a1.f59445a4;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            return drawable.isStateful();
        }
        if (super.isStateful()) {
            return true;
        }
        q91 q91Var = this.f59933a1;
        if (q91Var == null) {
            return false;
        }
        p91 p91Var = q91Var.f59442a1;
        if (p91Var.f59184b3 == null) {
            p91Var.f59184b3 = Boolean.valueOf(p91Var.f59177a6.mo213797a0());
        }
        if (p91Var.f59184b3.booleanValue()) {
            return true;
        }
        ColorStateList colorStateList = this.f59933a1.f59443a2;
        return colorStateList != null && colorStateList.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.f59936a4 && super.mutate() == this) {
            q91 q91Var = this.f59933a1;
            q91 q91Var2 = new q91();
            q91Var2.f59443a2 = null;
            q91Var2.f59444a3 = f59932a9;
            if (q91Var != null) {
                q91Var2.f59441a0 = q91Var.f59441a0;
                p91 p91Var = new p91(q91Var.f59442a1);
                q91Var2.f59442a1 = p91Var;
                if (q91Var.f59442a1.f59175a4 != null) {
                    p91Var.f59175a4 = new Paint(q91Var.f59442a1.f59175a4);
                }
                if (q91Var.f59442a1.f59174a3 != null) {
                    q91Var2.f59442a1.f59174a3 = new Paint(q91Var.f59442a1.f59174a3);
                }
                q91Var2.f59443a2 = q91Var.f59443a2;
                q91Var2.f59444a3 = q91Var.f59444a3;
                q91Var2.f59445a4 = q91Var.f59445a4;
            }
            this.f59933a1 = q91Var2;
            this.f59936a4 = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        boolean z;
        PorterDuff.Mode mode;
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        q91 q91Var = this.f59933a1;
        ColorStateList colorStateList = q91Var.f59443a2;
        if (colorStateList == null || (mode = q91Var.f59444a3) == null) {
            z = false;
        } else {
            this.f59934a2 = m214585a0(colorStateList, mode);
            invalidateSelf();
            z = true;
        }
        p91 p91Var = q91Var.f59442a1;
        if (p91Var.f59184b3 == null) {
            p91Var.f59184b3 = Boolean.valueOf(p91Var.f59177a6.mo213797a0());
        }
        if (p91Var.f59184b3.booleanValue()) {
            boolean zMo213798a1 = q91Var.f59442a1.f59177a6.mo213798a1(iArr);
            q91Var.f59451b0 |= zMo213798a1;
            if (zMo213798a1) {
                invalidateSelf();
                return true;
            }
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public final void scheduleSelf(Runnable runnable, long j) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j);
        } else {
            super.scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.setAlpha(i);
        } else if (this.f59933a1.f59442a1.getRootAlpha() != i) {
            this.f59933a1.f59442a1.setRootAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAutoMirrored(boolean z) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            AbstractC1269tq.m214766a4(drawable, z);
        } else {
            this.f59933a1.f59445a4 = z;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.f59935a3 = colorFilter;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            kj1.m213584d1(drawable, i);
        } else {
            setTintList(ColorStateList.valueOf(i));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            AbstractC1270tr.m214774a7(drawable, colorStateList);
            return;
        }
        q91 q91Var = this.f59933a1;
        if (q91Var.f59443a2 != colorStateList) {
            q91Var.f59443a2 = colorStateList;
            this.f59934a2 = m214585a0(colorStateList, q91Var.f59444a3);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            AbstractC1270tr.m214775a8(drawable, mode);
            return;
        }
        q91 q91Var = this.f59933a1;
        if (q91Var.f59444a3 != mode) {
            q91Var.f59444a3 = mode;
            this.f59934a2 = m214585a0(q91Var.f59443a2, mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.setVisible(z, z2) : super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int i;
        char c;
        int i2;
        Paint.Cap cap;
        Paint.Join join;
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            AbstractC1270tr.m214770a3(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        q91 q91Var = this.f59933a1;
        q91Var.f59442a1 = new p91();
        TypedArray typedArrayM210588d7 = b81.m210588d7(resources, theme, attributeSet, t60.f60148a0);
        q91 q91Var2 = this.f59933a1;
        p91 p91Var = q91Var2.f59442a1;
        int i3 = !b81.m210578c6(xmlPullParser, "tintMode") ? -1 : typedArrayM210588d7.getInt(6, -1);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        if (i3 == 3) {
            mode = PorterDuff.Mode.SRC_OVER;
        } else if (i3 != 5) {
            if (i3 != 9) {
                switch (i3) {
                    case 14:
                        mode = PorterDuff.Mode.MULTIPLY;
                        break;
                    case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                        mode = PorterDuff.Mode.SCREEN;
                        break;
                    case 16:
                        mode = PorterDuff.Mode.ADD;
                        break;
                }
            } else {
                mode = PorterDuff.Mode.SRC_ATOP;
            }
        }
        q91Var2.f59444a3 = mode;
        ColorStateList colorStateListM213328a0 = null;
        int i4 = 1;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "tint") != null) {
            TypedValue typedValue = new TypedValue();
            typedArrayM210588d7.getValue(1, typedValue);
            int i5 = typedValue.type;
            if (i5 == 2) {
                throw new UnsupportedOperationException("Failed to resolve attribute at index 1: " + typedValue);
            }
            if (i5 >= 28 && i5 <= 31) {
                colorStateListM213328a0 = ColorStateList.valueOf(typedValue.data);
            } else {
                Resources resources2 = typedArrayM210588d7.getResources();
                int resourceId = typedArrayM210588d7.getResourceId(1, 0);
                ThreadLocal threadLocal = AbstractC0723jm.f57346a0;
                try {
                    colorStateListM213328a0 = AbstractC0723jm.m213328a0(resources2, resources2.getXml(resourceId), theme);
                } catch (Exception unused) {
                }
            }
        }
        ColorStateList colorStateList = colorStateListM213328a0;
        if (colorStateList != null) {
            q91Var2.f59443a2 = colorStateList;
        }
        boolean z = q91Var2.f59445a4;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "autoMirrored") != null) {
            z = typedArrayM210588d7.getBoolean(5, z);
        }
        q91Var2.f59445a4 = z;
        float f = p91Var.f59180a9;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportWidth") != null) {
            f = typedArrayM210588d7.getFloat(7, f);
        }
        p91Var.f59180a9 = f;
        float f2 = p91Var.f59181b0;
        if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "viewportHeight") != null) {
            f2 = typedArrayM210588d7.getFloat(8, f2);
        }
        p91Var.f59181b0 = f2;
        if (p91Var.f59180a9 <= 0.0f) {
            throw new XmlPullParserException(typedArrayM210588d7.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        }
        if (f2 > 0.0f) {
            p91Var.f59178a7 = typedArrayM210588d7.getDimension(3, p91Var.f59178a7);
            float dimension = typedArrayM210588d7.getDimension(2, p91Var.f59179a8);
            p91Var.f59179a8 = dimension;
            if (p91Var.f59178a7 <= 0.0f) {
                throw new XmlPullParserException(typedArrayM210588d7.getPositionDescription() + "<vector> tag requires width > 0");
            }
            if (dimension > 0.0f) {
                float alpha = p91Var.getAlpha();
                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "alpha") != null) {
                    alpha = typedArrayM210588d7.getFloat(4, alpha);
                }
                p91Var.setAlpha(alpha);
                String string = typedArrayM210588d7.getString(0);
                if (string != null) {
                    p91Var.f59183b2 = string;
                    p91Var.f59185b4.put(string, p91Var);
                }
                typedArrayM210588d7.recycle();
                q91Var.f59441a0 = getChangingConfigurations();
                q91Var.f59451b0 = true;
                q91 q91Var3 = this.f59933a1;
                p91 p91Var2 = q91Var3.f59442a1;
                ArrayDeque arrayDeque = new ArrayDeque();
                m91 m91Var = p91Var2.f59177a6;
                C0130bd c0130bd = p91Var2.f59185b4;
                arrayDeque.push(m91Var);
                int eventType = xmlPullParser.getEventType();
                int depth = xmlPullParser.getDepth() + 1;
                boolean z2 = true;
                while (eventType != i4 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
                    if (eventType == 2) {
                        String name = xmlPullParser.getName();
                        m91 m91Var2 = (m91) arrayDeque.peek();
                        i = depth;
                        if ("path".equals(name)) {
                            l91 l91Var = new l91();
                            l91Var.f57848a4 = 0.0f;
                            l91Var.f57850a6 = 1.0f;
                            l91Var.f57851a7 = 1.0f;
                            l91Var.f57852a8 = 0.0f;
                            l91Var.f57853a9 = 1.0f;
                            l91Var.f57854b0 = 0.0f;
                            Paint.Cap cap2 = Paint.Cap.BUTT;
                            l91Var.f57855b1 = cap2;
                            Paint.Join join2 = Paint.Join.MITER;
                            l91Var.f57856b2 = join2;
                            l91Var.f57857b3 = 4.0f;
                            TypedArray typedArrayM210588d72 = b81.m210588d7(resources, theme, attributeSet, t60.f60150a2);
                            if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "pathData") != null) {
                                String string2 = typedArrayM210588d72.getString(0);
                                if (string2 != null) {
                                    l91Var.f58762a1 = string2;
                                }
                                String string3 = typedArrayM210588d72.getString(2);
                                if (string3 != null) {
                                    l91Var.f58761a0 = t60.m214701c2(string3);
                                }
                                l91Var.f57849a5 = b81.m210577b9(typedArrayM210588d72, xmlPullParser, theme, "fillColor", 1);
                                float f3 = l91Var.f57851a7;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "fillAlpha") != null) {
                                    f3 = typedArrayM210588d72.getFloat(12, f3);
                                }
                                l91Var.f57851a7 = f3;
                                int i6 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeLineCap") != null ? typedArrayM210588d72.getInt(8, -1) : -1;
                                Paint.Cap cap3 = l91Var.f57855b1;
                                if (i6 == 0) {
                                    cap = cap2;
                                } else if (i6 != 1) {
                                    cap = i6 != 2 ? cap3 : Paint.Cap.SQUARE;
                                } else {
                                    cap = Paint.Cap.ROUND;
                                }
                                l91Var.f57855b1 = cap;
                                int i7 = xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeLineJoin") != null ? typedArrayM210588d72.getInt(9, -1) : -1;
                                Paint.Join join3 = l91Var.f57856b2;
                                if (i7 == 0) {
                                    join = join2;
                                } else if (i7 != 1) {
                                    join = i7 != 2 ? join3 : Paint.Join.BEVEL;
                                } else {
                                    join = Paint.Join.ROUND;
                                }
                                l91Var.f57856b2 = join;
                                float f4 = l91Var.f57857b3;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeMiterLimit") != null) {
                                    f4 = typedArrayM210588d72.getFloat(10, f4);
                                }
                                l91Var.f57857b3 = f4;
                                l91Var.f57847a3 = b81.m210577b9(typedArrayM210588d72, xmlPullParser, theme, "strokeColor", 3);
                                float f5 = l91Var.f57850a6;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeAlpha") != null) {
                                    f5 = typedArrayM210588d72.getFloat(11, f5);
                                }
                                l91Var.f57850a6 = f5;
                                float f6 = l91Var.f57848a4;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "strokeWidth") != null) {
                                    f6 = typedArrayM210588d72.getFloat(4, f6);
                                }
                                l91Var.f57848a4 = f6;
                                float f7 = l91Var.f57853a9;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathEnd") != null) {
                                    f7 = typedArrayM210588d72.getFloat(6, f7);
                                }
                                l91Var.f57853a9 = f7;
                                float f8 = l91Var.f57854b0;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathOffset") != null) {
                                    f8 = typedArrayM210588d72.getFloat(7, f8);
                                }
                                l91Var.f57854b0 = f8;
                                float f9 = l91Var.f57852a8;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "trimPathStart") != null) {
                                    f9 = typedArrayM210588d72.getFloat(5, f9);
                                }
                                l91Var.f57852a8 = f9;
                                int i8 = l91Var.f58763a2;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "fillType") != null) {
                                    i8 = typedArrayM210588d72.getInt(13, i8);
                                }
                                l91Var.f58763a2 = i8;
                            }
                            typedArrayM210588d72.recycle();
                            m91Var2.f58307a1.add(l91Var);
                            if (l91Var.getPathName() != null) {
                                c0130bd.put(l91Var.getPathName(), l91Var);
                            }
                            q91Var3.f59441a0 = q91Var3.f59441a0;
                            z2 = false;
                            c = '\b';
                        } else {
                            c = '\b';
                            if ("clip-path".equals(name)) {
                                k91 k91Var = new k91();
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "pathData") != null) {
                                    TypedArray typedArrayM210588d73 = b81.m210588d7(resources, theme, attributeSet, t60.f60151a3);
                                    String string4 = typedArrayM210588d73.getString(0);
                                    if (string4 != null) {
                                        k91Var.f58762a1 = string4;
                                    }
                                    String string5 = typedArrayM210588d73.getString(1);
                                    if (string5 != null) {
                                        k91Var.f58761a0 = t60.m214701c2(string5);
                                    }
                                    k91Var.f58763a2 = !b81.m210578c6(xmlPullParser, "fillType") ? 0 : typedArrayM210588d73.getInt(2, 0);
                                    typedArrayM210588d73.recycle();
                                }
                                m91Var2.f58307a1.add(k91Var);
                                if (k91Var.getPathName() != null) {
                                    c0130bd.put(k91Var.getPathName(), k91Var);
                                }
                                q91Var3.f59441a0 = q91Var3.f59441a0;
                            } else if ("group".equals(name)) {
                                m91 m91Var3 = new m91();
                                TypedArray typedArrayM210588d74 = b81.m210588d7(resources, theme, attributeSet, t60.f60149a1);
                                float f10 = m91Var3.f58308a2;
                                if (b81.m210578c6(xmlPullParser, "rotation")) {
                                    f10 = typedArrayM210588d74.getFloat(5, f10);
                                }
                                m91Var3.f58308a2 = f10;
                                m91Var3.f58309a3 = typedArrayM210588d74.getFloat(1, m91Var3.f58309a3);
                                m91Var3.f58310a4 = typedArrayM210588d74.getFloat(2, m91Var3.f58310a4);
                                float f11 = m91Var3.f58311a5;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "scaleX") != null) {
                                    f11 = typedArrayM210588d74.getFloat(3, f11);
                                }
                                m91Var3.f58311a5 = f11;
                                float f12 = m91Var3.f58312a6;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "scaleY") != null) {
                                    f12 = typedArrayM210588d74.getFloat(4, f12);
                                }
                                m91Var3.f58312a6 = f12;
                                float f13 = m91Var3.f58313a7;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "translateX") != null) {
                                    f13 = typedArrayM210588d74.getFloat(6, f13);
                                }
                                m91Var3.f58313a7 = f13;
                                float f14 = m91Var3.f58314a8;
                                if (xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", "translateY") != null) {
                                    f14 = typedArrayM210588d74.getFloat(7, f14);
                                }
                                m91Var3.f58314a8 = f14;
                                String string6 = typedArrayM210588d74.getString(0);
                                if (string6 != null) {
                                    m91Var3.f58316b0 = string6;
                                }
                                m91Var3.m213952a2();
                                typedArrayM210588d74.recycle();
                                m91Var2.f58307a1.add(m91Var3);
                                arrayDeque.push(m91Var3);
                                if (m91Var3.getGroupName() != null) {
                                    c0130bd.put(m91Var3.getGroupName(), m91Var3);
                                }
                                q91Var3.f59441a0 = q91Var3.f59441a0;
                            }
                        }
                        i2 = 1;
                    } else {
                        i = depth;
                        c = '\b';
                        i2 = 1;
                        if (eventType == 3 && "group".equals(xmlPullParser.getName())) {
                            arrayDeque.pop();
                        }
                    }
                    eventType = xmlPullParser.next();
                    i4 = i2;
                    depth = i;
                }
                if (!z2) {
                    this.f59934a2 = m214585a0(q91Var.f59443a2, q91Var.f59444a3);
                    return;
                }
                throw new XmlPullParserException("no path defined");
            }
            throw new XmlPullParserException(typedArrayM210588d7.getPositionDescription() + "<vector> tag requires height > 0");
        }
        throw new XmlPullParserException(typedArrayM210588d7.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
    }

    public s91(q91 q91Var) {
        this.f59937a5 = true;
        this.f59938a6 = new float[9];
        this.f59939a7 = new Matrix();
        this.f59940a8 = new Rect();
        this.f59933a1 = q91Var;
        this.f59934a2 = m214585a0(q91Var.f59443a2, q91Var.f59444a3);
    }
}
