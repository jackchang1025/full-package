package p000;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.Property;
import android.view.View;
import com.google.android.material.R$attr;
import com.google.android.material.R$integer;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zy */
/* loaded from: classes2.dex */
public abstract class AbstractC1535zy {

    /* renamed from: c8 */
    public static final C1487yo f61600c8 = AbstractC1249t7.f60180a2;

    /* renamed from: c9 */
    public static final int f61601c9 = R$attr.motionDurationLong2;

    /* renamed from: d0 */
    public static final int f61602d0 = R$attr.motionEasingEmphasizedInterpolator;

    /* renamed from: d1 */
    public static final int f61603d1 = R$attr.motionDurationMedium1;

    /* renamed from: d2 */
    public static final int f61604d2 = R$attr.motionEasingEmphasizedAccelerateInterpolator;

    /* renamed from: d3 */
    public static final int[] f61605d3 = {R.attr.state_pressed, R.attr.state_enabled};

    /* renamed from: d4 */
    public static final int[] f61606d4 = {R.attr.state_hovered, R.attr.state_focused, R.attr.state_enabled};

    /* renamed from: d5 */
    public static final int[] f61607d5 = {R.attr.state_focused, R.attr.state_enabled};

    /* renamed from: d6 */
    public static final int[] f61608d6 = {R.attr.state_hovered, R.attr.state_enabled};

    /* renamed from: d7 */
    public static final int[] f61609d7 = {R.attr.state_enabled};

    /* renamed from: d8 */
    public static final int[] f61610d8 = new int[0];

    /* renamed from: a0 */
    public a01 f61611a0;

    /* renamed from: a1 */
    public ce0 f61612a1;

    /* renamed from: a2 */
    public Drawable f61613a2;

    /* renamed from: a3 */
    public C0457ei f61614a3;

    /* renamed from: a4 */
    public LayerDrawable f61615a4;

    /* renamed from: a5 */
    public boolean f61616a5;

    /* renamed from: a7 */
    public float f61618a7;

    /* renamed from: a8 */
    public float f61619a8;

    /* renamed from: a9 */
    public float f61620a9;

    /* renamed from: b0 */
    public int f61621b0;

    /* renamed from: b1 */
    public Animator f61622b1;

    /* renamed from: b2 */
    public yg0 f61623b2;

    /* renamed from: b3 */
    public yg0 f61624b3;

    /* renamed from: b4 */
    public float f61625b4;

    /* renamed from: b6 */
    public int f61627b6;

    /* renamed from: b8 */
    public ArrayList f61629b8;

    /* renamed from: b9 */
    public ArrayList f61630b9;

    /* renamed from: c0 */
    public ArrayList f61631c0;

    /* renamed from: c1 */
    public final FloatingActionButton f61632c1;

    /* renamed from: c2 */
    public final tg0 f61633c2;

    /* renamed from: c7 */
    public ViewTreeObserverOnPreDrawListenerC0908nc f61638c7;

    /* renamed from: a6 */
    public boolean f61617a6 = true;

    /* renamed from: b5 */
    public float f61626b5 = 1.0f;

    /* renamed from: b7 */
    public int f61628b7 = 0;

    /* renamed from: c3 */
    public final Rect f61634c3 = new Rect();

    /* renamed from: c4 */
    public final RectF f61635c4 = new RectF();

    /* renamed from: c5 */
    public final RectF f61636c5 = new RectF();

    /* renamed from: c6 */
    public final Matrix f61637c6 = new Matrix();

    public AbstractC1535zy(FloatingActionButton floatingActionButton, tg0 tg0Var) {
        this.f61632c1 = floatingActionButton;
        this.f61633c2 = tg0Var;
        zg1 zg1Var = new zg1(5);
        C0000a c0000a = (C0000a) this;
        zg1Var.m215405a1(f61605d3, m215435a3(new C1533zw(c0000a, 1)));
        zg1Var.m215405a1(f61606d4, m215435a3(new C1533zw(c0000a, 0)));
        zg1Var.m215405a1(f61607d5, m215435a3(new C1533zw(c0000a, 0)));
        zg1Var.m215405a1(f61608d6, m215435a3(new C1533zw(c0000a, 0)));
        zg1Var.m215405a1(f61609d7, m215435a3(new C1533zw(c0000a, 2)));
        zg1Var.m215405a1(f61610d8, m215435a3(new C1531zv(c0000a)));
        this.f61625b4 = floatingActionButton.getRotation();
    }

    /* renamed from: a3 */
    public static ValueAnimator m215435a3(AbstractC1534zx abstractC1534zx) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(f61600c8);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener(abstractC1534zx);
        valueAnimator.addUpdateListener(abstractC1534zx);
        valueAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimator;
    }

    /* renamed from: a0 */
    public final void m215436a0(float f, Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.f61632c1.getDrawable();
        if (drawable == null || this.f61627b6 == 0) {
            return;
        }
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        RectF rectF = this.f61635c4;
        rectF.set(0.0f, 0.0f, intrinsicWidth, intrinsicHeight);
        float f2 = this.f61627b6;
        RectF rectF2 = this.f61636c5;
        rectF2.set(0.0f, 0.0f, f2, f2);
        matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
        float f3 = this.f61627b6 / 2.0f;
        matrix.postScale(f, f, f3, f3);
    }

    /* renamed from: a1 */
    public final AnimatorSet m215437a1(yg0 yg0Var, float f, float f2, float f3) {
        ArrayList arrayList = new ArrayList();
        Property property = View.ALPHA;
        float[] fArr = {f};
        FloatingActionButton floatingActionButton = this.f61632c1;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) property, fArr);
        yg0Var.m215285a5("opacity").m215402a0(objectAnimatorOfFloat);
        arrayList.add(objectAnimatorOfFloat);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.SCALE_X, f2);
        yg0Var.m215285a5("scale").m215402a0(objectAnimatorOfFloat2);
        int i = Build.VERSION.SDK_INT;
        if (i == 26) {
            C1530zu c1530zu = new C1530zu();
            c1530zu.f61588a1 = new FloatEvaluator();
            objectAnimatorOfFloat2.setEvaluator(c1530zu);
        }
        arrayList.add(objectAnimatorOfFloat2);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.SCALE_Y, f2);
        yg0Var.m215285a5("scale").m215402a0(objectAnimatorOfFloat3);
        if (i == 26) {
            C1530zu c1530zu2 = new C1530zu();
            c1530zu2.f61588a1 = new FloatEvaluator();
            objectAnimatorOfFloat3.setEvaluator(c1530zu2);
        }
        arrayList.add(objectAnimatorOfFloat3);
        Matrix matrix = this.f61637c6;
        m215436a0(f3, matrix);
        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(floatingActionButton, new C0396cz(), new C1528zs(this), new Matrix(matrix));
        yg0Var.m215285a5("iconScale").m215402a0(objectAnimatorOfObject);
        arrayList.add(objectAnimatorOfObject);
        AnimatorSet animatorSet = new AnimatorSet();
        t60.m214718e2(animatorSet, arrayList);
        return animatorSet;
    }

    /* renamed from: a2 */
    public final AnimatorSet m215438a2(float f, float f2, float f3, int i, int i2) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        FloatingActionButton floatingActionButton = this.f61632c1;
        valueAnimatorOfFloat.addUpdateListener(new C1529zt(this, floatingActionButton.getAlpha(), f, floatingActionButton.getScaleX(), f2, floatingActionButton.getScaleY(), this.f61626b5, f3, new Matrix(this.f61637c6)));
        arrayList.add(valueAnimatorOfFloat);
        t60.m214718e2(animatorSet, arrayList);
        animatorSet.setDuration(kg1.m213536e3(floatingActionButton.getContext(), i, floatingActionButton.getContext().getResources().getInteger(R$integer.material_motion_duration_long_1)));
        animatorSet.setInterpolator(kg1.m213537e4(floatingActionButton.getContext(), i2, AbstractC1249t7.f60179a1));
        return animatorSet;
    }

    /* renamed from: a4 */
    public abstract float mo0a4();

    /* renamed from: a5 */
    public void mo1a5(Rect rect) {
        int sizeDimension = this.f61616a5 ? (this.f61621b0 - this.f61632c1.getSizeDimension()) / 2 : 0;
        int iMax = Math.max(sizeDimension, (int) Math.ceil(this.f61617a6 ? mo0a4() + this.f61620a9 : 0.0f));
        int iMax2 = Math.max(sizeDimension, (int) Math.ceil(r1 * 1.5f));
        rect.set(iMax, iMax2, iMax, iMax2);
    }

    /* renamed from: a6 */
    public abstract void mo2a6(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i);

    /* renamed from: a7 */
    public abstract void mo3a7();

    /* renamed from: a8 */
    public abstract void mo4a8();

    /* renamed from: a9 */
    public abstract void mo5a9(int[] iArr);

    /* renamed from: b0 */
    public abstract void mo6b0(float f, float f2, float f3);

    /* renamed from: b1 */
    public final void m215439b1() {
        ArrayList arrayList = this.f61631c0;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                C1526zq c1526zq = (C1526zq) obj;
                C0460el c0460el = c1526zq.f61563a0;
                FloatingActionButton floatingActionButton = c1526zq.f61564a1;
                c0460el.getClass();
                BottomAppBar bottomAppBar = c0460el.f56072a0;
                bottomAppBar.f49149e4.m210841b3((floatingActionButton.getVisibility() == 0 && bottomAppBar.f49154e9 == 1) ? floatingActionButton.getScaleY() : 0.0f);
            }
        }
    }

    /* renamed from: b2 */
    public final void m215440b2() {
        ArrayList arrayList = this.f61631c0;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                C1526zq c1526zq = (C1526zq) obj;
                C0460el c0460el = c1526zq.f61563a0;
                FloatingActionButton floatingActionButton = c1526zq.f61564a1;
                c0460el.getClass();
                BottomAppBar bottomAppBar = c0460el.f56072a0;
                int i2 = bottomAppBar.f49154e9;
                ce0 ce0Var = bottomAppBar.f49149e4;
                if (i2 == 1) {
                    float translationX = floatingActionButton.getTranslationX();
                    if (bottomAppBar.getTopEdgeTreatment().f56098b8 != translationX) {
                        bottomAppBar.getTopEdgeTreatment().f56098b8 = translationX;
                        ce0Var.invalidateSelf();
                    }
                    float fMax = Math.max(0.0f, -floatingActionButton.getTranslationY());
                    if (bottomAppBar.getTopEdgeTreatment().f56097b7 != fMax) {
                        bottomAppBar.getTopEdgeTreatment().m212720b2(fMax);
                        ce0Var.invalidateSelf();
                    }
                    ce0Var.m210841b3(floatingActionButton.getVisibility() == 0 ? floatingActionButton.getScaleY() : 0.0f);
                }
            }
        }
    }

    /* renamed from: b3 */
    public void mo7b3(ColorStateList colorStateList) {
        Drawable drawable = this.f61613a2;
        if (drawable != null) {
            AbstractC1270tr.m214774a7(drawable, b81.m210594e5(colorStateList));
        }
    }

    /* renamed from: b4 */
    public final void m215441b4(a01 a01Var) {
        this.f61611a0 = a01Var;
        ce0 ce0Var = this.f61612a1;
        if (ce0Var != null) {
            ce0Var.setShapeAppearanceModel(a01Var);
        }
        Object obj = this.f61613a2;
        if (obj instanceof l01) {
            ((l01) obj).setShapeAppearanceModel(a01Var);
        }
        C0457ei c0457ei = this.f61614a3;
        if (c0457ei != null) {
            c0457ei.f56030b4 = a01Var;
            c0457ei.invalidateSelf();
        }
    }

    /* renamed from: b5 */
    public abstract boolean mo8b5();

    /* renamed from: b6 */
    public abstract void mo9b6();

    /* renamed from: b7 */
    public final void m215442b7() {
        Rect rect = this.f61634c3;
        mo1a5(rect);
        b81.m210568a8(this.f61615a4, "Didn't initialize content background");
        boolean zMo8b5 = mo8b5();
        tg0 tg0Var = this.f61633c2;
        if (zMo8b5) {
            super/*android.view.View*/.setBackgroundDrawable(new InsetDrawable((Drawable) this.f61615a4, rect.left, rect.top, rect.right, rect.bottom));
        } else {
            LayerDrawable layerDrawable = this.f61615a4;
            if (layerDrawable != null) {
                super/*android.view.View*/.setBackgroundDrawable(layerDrawable);
            } else {
                tg0Var.getClass();
            }
        }
        int i = rect.left;
        int i2 = rect.top;
        int i3 = rect.right;
        int i4 = rect.bottom;
        FloatingActionButton floatingActionButton = (FloatingActionButton) tg0Var.f60218a1;
        floatingActionButton.f49510b1.set(i, i2, i3, i4);
        int i5 = floatingActionButton.f49507a8;
        floatingActionButton.setPadding(i + i5, i2 + i5, i3 + i5, i4 + i5);
    }
}
