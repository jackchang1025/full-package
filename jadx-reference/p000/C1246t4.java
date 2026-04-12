package p000;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: t4 */
/* loaded from: classes2.dex */
public final class C1246t4 extends j91 implements Animatable {

    /* renamed from: a2 */
    public final Context f60136a2;

    /* renamed from: a3 */
    public C0847m3 f60137a3 = null;

    /* renamed from: a4 */
    public ArrayList f60138a4 = null;

    /* renamed from: a5 */
    public final C1243t1 f60139a5 = new C1243t1(this);

    /* renamed from: a1 */
    public final C1244t2 f60135a1 = new C1244t2();

    public C1246t4(Context context, int i) {
        this.f60136a2 = context;
    }

    @Override // p000.j91, android.graphics.drawable.Drawable
    public final void applyTheme(Resources.Theme theme) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            AbstractC1270tr.m214767a0(drawable, theme);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            return AbstractC1270tr.m214768a1(drawable);
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        C1244t2 c1244t2 = this.f60135a1;
        c1244t2.f60125a0.draw(canvas);
        if (c1244t2.f60126a1.isStarted()) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? AbstractC1269tq.m214762a0(drawable) : this.f60135a1.f60125a0.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getChangingConfigurations() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            return drawable.getChangingConfigurations();
        }
        int changingConfigurations = super.getChangingConfigurations();
        this.f60135a1.getClass();
        return changingConfigurations;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? AbstractC1270tr.m214769a2(drawable) : this.f60135a1.f60125a0.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (this.f57309a0 != null) {
            return new C1245t3(this.f57309a0.getConstantState());
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.getIntrinsicHeight() : this.f60135a1.f60125a0.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.getIntrinsicWidth() : this.f60135a1.f60125a0.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.getOpacity() : this.f60135a1.f60125a0.getOpacity();
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ca, code lost:
    
        if (r3.f60126a1 != null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00cc, code lost:
    
        r3.f60126a1 = new android.animation.AnimatorSet();
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d3, code lost:
    
        r3.f60126a1.playTogether(r3.f60127a2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00da, code lost:
    
        return;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            AbstractC1270tr.m214770a3(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            C1244t2 c1244t2 = this.f60135a1;
            if (eventType == 1 || (xmlPullParser.getDepth() < depth && eventType == 3)) {
                break;
            }
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if ("animated-vector".equals(name)) {
                    TypedArray typedArrayM210588d7 = b81.m210588d7(resources, theme, attributeSet, t60.f60152a4);
                    int resourceId = typedArrayM210588d7.getResourceId(0, 0);
                    if (resourceId != 0) {
                        s91 s91Var = new s91();
                        ThreadLocal threadLocal = yr0.f61364a0;
                        s91Var.f57309a0 = tr0.m214776a0(resources, resourceId, theme);
                        new r91(s91Var.f57309a0.getConstantState());
                        s91Var.f59937a5 = false;
                        s91Var.setCallback(this.f60139a5);
                        s91 s91Var2 = c1244t2.f60125a0;
                        if (s91Var2 != null) {
                            s91Var2.setCallback(null);
                        }
                        c1244t2.f60125a0 = s91Var;
                    }
                    typedArrayM210588d7.recycle();
                } else if ("target".equals(name)) {
                    TypedArray typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, t60.f60153a5);
                    String string = typedArrayObtainAttributes.getString(0);
                    int resourceId2 = typedArrayObtainAttributes.getResourceId(1, 0);
                    if (resourceId2 != 0) {
                        Context context = this.f60136a2;
                        if (context == null) {
                            typedArrayObtainAttributes.recycle();
                            throw new IllegalStateException("Context can't be null when inflating animators");
                        }
                        Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, resourceId2);
                        animatorLoadAnimator.setTarget(c1244t2.f60125a0.f59933a1.f59442a1.f59185b4.getOrDefault(string, null));
                        if (c1244t2.f60127a2 == null) {
                            c1244t2.f60127a2 = new ArrayList();
                            c1244t2.f60128a3 = new C0130bd();
                        }
                        c1244t2.f60127a2.add(animatorLoadAnimator);
                        c1244t2.f60128a3.put(animatorLoadAnimator, string);
                    }
                    typedArrayObtainAttributes.recycle();
                } else {
                    continue;
                }
            }
            eventType = xmlPullParser.next();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? AbstractC1269tq.m214765a3(drawable) : this.f60135a1.f60125a0.isAutoMirrored();
    }

    @Override // android.graphics.drawable.Animatable
    public final boolean isRunning() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? ((AnimatedVectorDrawable) drawable).isRunning() : this.f60135a1.f60126a1.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.isStateful() : this.f60135a1.f60125a0.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable mutate() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.mutate();
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.setBounds(rect);
        } else {
            this.f60135a1.f60125a0.setBounds(rect);
        }
    }

    @Override // p000.j91, android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.setLevel(i) : this.f60135a1.f60125a0.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        Drawable drawable = this.f57309a0;
        return drawable != null ? drawable.setState(iArr) : this.f60135a1.f60125a0.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.setAlpha(i);
        } else {
            this.f60135a1.f60125a0.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAutoMirrored(boolean z) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            AbstractC1269tq.m214766a4(drawable, z);
        } else {
            this.f60135a1.f60125a0.setAutoMirrored(z);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        } else {
            this.f60135a1.f60125a0.setColorFilter(colorFilter);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            kj1.m213584d1(drawable, i);
        } else {
            this.f60135a1.f60125a0.setTint(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            kj1.m213585d2(drawable, colorStateList);
        } else {
            this.f60135a1.f60125a0.setTintList(colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            kj1.m213586d3(drawable, mode);
        } else {
            this.f60135a1.f60125a0.setTintMode(mode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            return drawable.setVisible(z, z2);
        }
        this.f60135a1.f60125a0.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Animatable
    public final void start() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            ((AnimatedVectorDrawable) drawable).start();
            return;
        }
        C1244t2 c1244t2 = this.f60135a1;
        if (c1244t2.f60126a1.isStarted()) {
            return;
        }
        c1244t2.f60126a1.start();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public final void stop() {
        Drawable drawable = this.f57309a0;
        if (drawable != null) {
            ((AnimatedVectorDrawable) drawable).stop();
        } else {
            this.f60135a1.f60126a1.end();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, Resources.NotFoundException, IOException {
        inflate(resources, xmlPullParser, attributeSet, null);
    }
}
