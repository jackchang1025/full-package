package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.R$attr;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.badge.BadgeState$State;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ct */
/* loaded from: classes2.dex */
public final class C0390ct extends Drawable implements t51 {

    /* renamed from: b3 */
    public static final int f55502b3 = R$style.Widget_MaterialComponents_Badge;

    /* renamed from: b4 */
    public static final int f55503b4 = R$attr.badgeStyle;

    /* renamed from: a0 */
    public final WeakReference f55504a0;

    /* renamed from: a1 */
    public final ce0 f55505a1;

    /* renamed from: a2 */
    public final u51 f55506a2;

    /* renamed from: a3 */
    public final Rect f55507a3;

    /* renamed from: a4 */
    public final C0391cu f55508a4;

    /* renamed from: a5 */
    public float f55509a5;

    /* renamed from: a6 */
    public float f55510a6;

    /* renamed from: a7 */
    public final int f55511a7;

    /* renamed from: a8 */
    public float f55512a8;

    /* renamed from: a9 */
    public float f55513a9;

    /* renamed from: b0 */
    public float f55514b0;

    /* renamed from: b1 */
    public WeakReference f55515b1;

    /* renamed from: b2 */
    public WeakReference f55516b2;

    public C0390ct(Context context, BadgeState$State badgeState$State) {
        r51 r51Var;
        WeakReference weakReference = new WeakReference(context);
        this.f55504a0 = weakReference;
        j61.m213208a2(context, j61.f57274a1, "Theme.MaterialComponents");
        this.f55507a3 = new Rect();
        u51 u51Var = new u51(this);
        this.f55506a2 = u51Var;
        Paint.Align align = Paint.Align.CENTER;
        TextPaint textPaint = u51Var.f60328a0;
        textPaint.setTextAlign(align);
        C0391cu c0391cu = new C0391cu(context, badgeState$State);
        this.f55508a4 = c0391cu;
        boolean zM212531a0 = c0391cu.m212531a0();
        BadgeState$State badgeState$State2 = c0391cu.f55520a1;
        ce0 ce0Var = new ce0(a01.m11a0(context, zM212531a0 ? badgeState$State2.f49109a6.intValue() : badgeState$State2.f49107a4.intValue(), c0391cu.m212531a0() ? badgeState$State2.f49110a7.intValue() : badgeState$State2.f49108a5.intValue()).m215177a0());
        this.f55505a1 = ce0Var;
        m212528a3();
        Context context2 = (Context) weakReference.get();
        if (context2 != null && u51Var.f60333a5 != (r51Var = new r51(context2, badgeState$State2.f49106a3.intValue()))) {
            u51Var.m214817a1(r51Var, context2);
            textPaint.setColor(badgeState$State2.f49105a2.intValue());
            invalidateSelf();
            m212530a5();
            invalidateSelf();
        }
        this.f55511a7 = ((int) Math.pow(10.0d, badgeState$State2.f49113b0 - 1.0d)) - 1;
        u51Var.f60331a3 = true;
        m212530a5();
        invalidateSelf();
        u51Var.f60331a3 = true;
        m212528a3();
        m212530a5();
        invalidateSelf();
        textPaint.setAlpha(getAlpha());
        invalidateSelf();
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(badgeState$State2.f49104a1.intValue());
        if (ce0Var.f46107a0.f45839a2 != colorStateListValueOf) {
            ce0Var.m210840b2(colorStateListValueOf);
            invalidateSelf();
        }
        textPaint.setColor(badgeState$State2.f49105a2.intValue());
        invalidateSelf();
        WeakReference weakReference2 = this.f55515b1;
        if (weakReference2 != null && weakReference2.get() != null) {
            View view = (View) this.f55515b1.get();
            WeakReference weakReference3 = this.f55516b2;
            m212529a4(view, weakReference3 != null ? (FrameLayout) weakReference3.get() : null);
        }
        m212530a5();
        setVisible(badgeState$State2.f49119b6.booleanValue(), false);
    }

    @Override // p000.t51
    /* renamed from: a0 */
    public final void mo210828a0() {
        invalidateSelf();
    }

    /* renamed from: a1 */
    public final String m212526a1() {
        int iM212527a2 = m212527a2();
        int i = this.f55511a7;
        C0391cu c0391cu = this.f55508a4;
        if (iM212527a2 <= i) {
            return NumberFormat.getInstance(c0391cu.f55520a1.f49114b1).format(m212527a2());
        }
        Context context = (Context) this.f55504a0.get();
        return context == null ? "" : String.format(c0391cu.f55520a1.f49114b1, context.getString(R$string.mtrl_exceed_max_badge_number_suffix), Integer.valueOf(this.f55511a7), "+");
    }

    /* renamed from: a2 */
    public final int m212527a2() {
        C0391cu c0391cu = this.f55508a4;
        if (c0391cu.m212531a0()) {
            return c0391cu.f55520a1.f49112a9;
        }
        return 0;
    }

    /* renamed from: a3 */
    public final void m212528a3() {
        Context context = (Context) this.f55504a0.get();
        if (context == null) {
            return;
        }
        C0391cu c0391cu = this.f55508a4;
        boolean zM212531a0 = c0391cu.m212531a0();
        BadgeState$State badgeState$State = c0391cu.f55520a1;
        this.f55505a1.setShapeAppearanceModel(a01.m11a0(context, zM212531a0 ? badgeState$State.f49109a6.intValue() : badgeState$State.f49107a4.intValue(), c0391cu.m212531a0() ? badgeState$State.f49110a7.intValue() : badgeState$State.f49108a5.intValue()).m215177a0());
        invalidateSelf();
    }

    /* renamed from: a4 */
    public final void m212529a4(View view, FrameLayout frameLayout) {
        this.f55515b1 = new WeakReference(view);
        this.f55516b2 = new WeakReference(frameLayout);
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        m212530a5();
        invalidateSelf();
    }

    /* renamed from: a5 */
    public final void m212530a5() {
        Context context = (Context) this.f55504a0.get();
        WeakReference weakReference = this.f55515b1;
        View view = weakReference != null ? (View) weakReference.get() : null;
        if (context == null || view == null) {
            return;
        }
        Rect rect = new Rect();
        Rect rect2 = this.f55507a3;
        rect.set(rect2);
        Rect rect3 = new Rect();
        view.getDrawingRect(rect3);
        WeakReference weakReference2 = this.f55516b2;
        ViewGroup viewGroup = weakReference2 != null ? (ViewGroup) weakReference2.get() : null;
        if (viewGroup != null) {
            viewGroup.offsetDescendantRectToMyCoords(view, rect3);
        }
        C0391cu c0391cu = this.f55508a4;
        boolean zM212531a0 = c0391cu.m212531a0();
        BadgeState$State badgeState$State = c0391cu.f55520a1;
        int i = c0391cu.f55530b1;
        float f = !zM212531a0 ? c0391cu.f55521a2 : c0391cu.f55522a3;
        this.f55512a8 = f;
        if (f != -1.0f) {
            this.f55514b0 = f;
            this.f55513a9 = f;
        } else {
            this.f55514b0 = Math.round((!c0391cu.m212531a0() ? c0391cu.f55524a5 : c0391cu.f55526a7) / 2.0f);
            this.f55513a9 = Math.round((!c0391cu.m212531a0() ? c0391cu.f55523a4 : c0391cu.f55525a6) / 2.0f);
        }
        if (m212527a2() > 9) {
            this.f55513a9 = Math.max(this.f55513a9, (this.f55506a2.m214816a0(m212526a1()) / 2.0f) + c0391cu.f55527a8);
        }
        int iIntValue = c0391cu.m212531a0() ? badgeState$State.f49123c0.intValue() : badgeState$State.f49121b8.intValue();
        if (i == 0) {
            iIntValue -= Math.round(this.f55514b0);
        }
        int iIntValue2 = badgeState$State.f49125c2.intValue() + iIntValue;
        int iIntValue3 = badgeState$State.f49118b5.intValue();
        if (iIntValue3 == 8388691 || iIntValue3 == 8388693) {
            this.f55510a6 = rect3.bottom - iIntValue2;
        } else {
            this.f55510a6 = rect3.top + iIntValue2;
        }
        int iIntValue4 = c0391cu.m212531a0() ? badgeState$State.f49122b9.intValue() : badgeState$State.f49120b7.intValue();
        if (i == 1) {
            iIntValue4 += c0391cu.m212531a0() ? c0391cu.f55529b0 : c0391cu.f55528a9;
        }
        int iIntValue5 = badgeState$State.f49124c1.intValue() + iIntValue4;
        int iIntValue6 = badgeState$State.f49118b5.intValue();
        if (iIntValue6 == 8388659 || iIntValue6 == 8388691) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            this.f55509a5 = ga1.m212904a3(view) == 0 ? (rect3.left - this.f55513a9) + iIntValue5 : (rect3.right + this.f55513a9) - iIntValue5;
        } else {
            WeakHashMap weakHashMap2 = xa1.f61054a0;
            this.f55509a5 = ga1.m212904a3(view) == 0 ? (rect3.right + this.f55513a9) - iIntValue5 : (rect3.left - this.f55513a9) + iIntValue5;
        }
        float f2 = this.f55509a5;
        float f3 = this.f55510a6;
        float f4 = this.f55513a9;
        float f5 = this.f55514b0;
        rect2.set((int) (f2 - f4), (int) (f3 - f5), (int) (f2 + f4), (int) (f3 + f5));
        float f6 = this.f55512a8;
        ce0 ce0Var = this.f55505a1;
        if (f6 != -1.0f) {
            xg1 xg1VarM17a6 = ce0Var.f46107a0.f45837a0.m17a6();
            xg1VarM17a6.m215188b1(f6);
            ce0Var.setShapeAppearanceModel(xg1VarM17a6.m215177a0());
        }
        if (rect.equals(rect2)) {
            return;
        }
        ce0Var.setBounds(rect2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (getBounds().isEmpty() || getAlpha() == 0 || !isVisible()) {
            return;
        }
        this.f55505a1.draw(canvas);
        if (this.f55508a4.m212531a0()) {
            Rect rect = new Rect();
            String strM212526a1 = m212526a1();
            u51 u51Var = this.f55506a2;
            u51Var.f60328a0.getTextBounds(strM212526a1, 0, strM212526a1.length(), rect);
            canvas.drawText(strM212526a1, this.f55509a5, this.f55510a6 + (rect.height() / 2), u51Var.f60328a0);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.f55508a4.f55520a1.f49111a8;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.f55507a3.height();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.f55507a3.width();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return false;
    }

    @Override // android.graphics.drawable.Drawable, p000.t51
    public final boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        C0391cu c0391cu = this.f55508a4;
        c0391cu.f55519a0.f49111a8 = i;
        c0391cu.f55520a1.f49111a8 = i;
        this.f55506a2.f60328a0.setAlpha(getAlpha());
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
    }
}
