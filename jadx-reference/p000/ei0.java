package p000;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$drawable;
import com.google.android.material.R$id;
import com.google.android.material.R$integer;
import com.google.android.material.R$string;
import com.google.android.material.R$styleable;
import com.google.android.material.badge.BadgeState$State;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class ei0 extends FrameLayout implements uf0 {

    /* renamed from: d0 */
    public static final int[] f56032d0 = {R.attr.state_checked};

    /* renamed from: d1 */
    public static final fh0 f56033d1 = new fh0(1);

    /* renamed from: d2 */
    public static final di0 f56034d2 = new di0(1);

    /* renamed from: a0 */
    public boolean f56035a0;

    /* renamed from: a1 */
    public ColorStateList f56036a1;

    /* renamed from: a2 */
    public Drawable f56037a2;

    /* renamed from: a3 */
    public int f56038a3;

    /* renamed from: a4 */
    public int f56039a4;

    /* renamed from: a5 */
    public float f56040a5;

    /* renamed from: a6 */
    public float f56041a6;

    /* renamed from: a7 */
    public float f56042a7;

    /* renamed from: a8 */
    public int f56043a8;

    /* renamed from: a9 */
    public boolean f56044a9;

    /* renamed from: b0 */
    public final FrameLayout f56045b0;

    /* renamed from: b1 */
    public final View f56046b1;

    /* renamed from: b2 */
    public final ImageView f56047b2;

    /* renamed from: b3 */
    public final ViewGroup f56048b3;

    /* renamed from: b4 */
    public final TextView f56049b4;

    /* renamed from: b5 */
    public final TextView f56050b5;

    /* renamed from: b6 */
    public int f56051b6;

    /* renamed from: b7 */
    public ff0 f56052b7;

    /* renamed from: b8 */
    public ColorStateList f56053b8;

    /* renamed from: b9 */
    public Drawable f56054b9;

    /* renamed from: c0 */
    public Drawable f56055c0;

    /* renamed from: c1 */
    public ValueAnimator f56056c1;

    /* renamed from: c2 */
    public fh0 f56057c2;

    /* renamed from: c3 */
    public float f56058c3;

    /* renamed from: c4 */
    public boolean f56059c4;

    /* renamed from: c5 */
    public int f56060c5;

    /* renamed from: c6 */
    public int f56061c6;

    /* renamed from: c7 */
    public boolean f56062c7;

    /* renamed from: c8 */
    public int f56063c8;

    /* renamed from: c9 */
    public C0390ct f56064c9;

    public ei0(Context context) {
        super(context);
        this.f56035a0 = false;
        this.f56051b6 = -1;
        this.f56057c2 = f56033d1;
        this.f56058c3 = 0.0f;
        this.f56059c4 = false;
        this.f56060c5 = 0;
        this.f56061c6 = 0;
        this.f56062c7 = false;
        this.f56063c8 = 0;
        LayoutInflater.from(context).inflate(getItemLayoutResId(), (ViewGroup) this, true);
        this.f56045b0 = (FrameLayout) findViewById(R$id.navigation_bar_item_icon_container);
        this.f56046b1 = findViewById(R$id.navigation_bar_item_active_indicator_view);
        ImageView imageView = (ImageView) findViewById(R$id.navigation_bar_item_icon_view);
        this.f56047b2 = imageView;
        ViewGroup viewGroup = (ViewGroup) findViewById(R$id.navigation_bar_item_labels_group);
        this.f56048b3 = viewGroup;
        TextView textView = (TextView) findViewById(R$id.navigation_bar_item_small_label_view);
        this.f56049b4 = textView;
        TextView textView2 = (TextView) findViewById(R$id.navigation_bar_item_large_label_view);
        this.f56050b5 = textView2;
        setBackgroundResource(getItemBackgroundResId());
        this.f56038a3 = getResources().getDimensionPixelSize(getItemDefaultMarginResId());
        this.f56039a4 = viewGroup.getPaddingBottom();
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212781b8(textView, 2);
        fa1.m212781b8(textView2, 2);
        setFocusable(true);
        m212681a0(textView.getTextSize(), textView2.getTextSize());
        if (imageView != null) {
            imageView.addOnLayoutChangeListener(new bi0(0, this));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m212677a4(TextView textView, int i) throws Resources.NotFoundException {
        int iRound;
        textView.setTextAppearance(i);
        Context context = textView.getContext();
        if (i == 0) {
            iRound = 0;
        } else {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, R$styleable.TextAppearance);
            TypedValue typedValue = new TypedValue();
            boolean value = typedArrayObtainStyledAttributes.getValue(R$styleable.TextAppearance_android_textSize, typedValue);
            typedArrayObtainStyledAttributes.recycle();
            if (value) {
                iRound = typedValue.getComplexUnit() == 2 ? Math.round(TypedValue.complexToFloat(typedValue.data) * context.getResources().getDisplayMetrics().density) : TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
            }
        }
        if (iRound != 0) {
            textView.setTextSize(0, iRound);
        }
    }

    /* renamed from: a5 */
    public static void m212678a5(View view, float f, float f2, int i) {
        view.setScaleX(f);
        view.setScaleY(f2);
        view.setVisibility(i);
    }

    /* renamed from: a6 */
    public static void m212679a6(View view, int i, int i2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i;
        layoutParams.bottomMargin = i;
        layoutParams.gravity = i2;
        view.setLayoutParams(layoutParams);
    }

    /* renamed from: a8 */
    public static void m212680a8(View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }

    private View getIconOrContainer() {
        FrameLayout frameLayout = this.f56045b0;
        return frameLayout != null ? frameLayout : this.f56047b2;
    }

    private int getItemVisiblePosition() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        int iIndexOfChild = viewGroup.indexOfChild(this);
        int i = 0;
        for (int i2 = 0; i2 < iIndexOfChild; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if ((childAt instanceof ei0) && childAt.getVisibility() == 0) {
                i++;
            }
        }
        return i;
    }

    private int getSuggestedIconHeight() {
        C0390ct c0390ct = this.f56064c9;
        int minimumHeight = c0390ct != null ? c0390ct.getMinimumHeight() / 2 : 0;
        return this.f56047b2.getMeasuredWidth() + Math.max(minimumHeight, ((FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams()).topMargin) + minimumHeight;
    }

    private int getSuggestedIconWidth() {
        C0390ct c0390ct = this.f56064c9;
        int minimumWidth = c0390ct == null ? 0 : c0390ct.getMinimumWidth() - this.f56064c9.f55508a4.f55520a1.f49120b7.intValue();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams();
        return Math.max(minimumWidth, layoutParams.rightMargin) + this.f56047b2.getMeasuredWidth() + Math.max(minimumWidth, layoutParams.leftMargin);
    }

    /* renamed from: a0 */
    public final void m212681a0(float f, float f2) {
        this.f56040a5 = f - f2;
        this.f56041a6 = (f2 * 1.0f) / f;
        this.f56042a7 = (f * 1.0f) / f2;
    }

    /* renamed from: a1 */
    public final void m212682a1() {
        Drawable rippleDrawable = this.f56037a2;
        ColorStateList colorStateList = this.f56036a1;
        FrameLayout frameLayout = this.f56045b0;
        RippleDrawable rippleDrawable2 = null;
        boolean z = true;
        if (colorStateList != null) {
            Drawable activeIndicatorDrawable = getActiveIndicatorDrawable();
            if (this.f56059c4 && getActiveIndicatorDrawable() != null && frameLayout != null && activeIndicatorDrawable != null) {
                rippleDrawable2 = new RippleDrawable(b81.m210594e5(this.f56036a1), null, activeIndicatorDrawable);
                z = false;
            } else if (rippleDrawable == null) {
                rippleDrawable = new RippleDrawable(b81.m210570b0(this.f56036a1), null, null);
            }
        }
        if (frameLayout != null) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            fa1.m212779b6(frameLayout, rippleDrawable2);
        }
        WeakHashMap weakHashMap2 = xa1.f61054a0;
        fa1.m212779b6(this, rippleDrawable);
        if (Build.VERSION.SDK_INT >= 26) {
            setDefaultFocusHighlightEnabled(z);
        }
    }

    @Override // p000.uf0
    /* renamed from: a2 */
    public final void mo209844a2(ff0 ff0Var) {
        this.f56052b7 = ff0Var;
        setCheckable(ff0Var.isCheckable());
        setChecked(ff0Var.isChecked());
        setEnabled(ff0Var.isEnabled());
        setIcon(ff0Var.getIcon());
        setTitle(ff0Var.f56209a4);
        setId(ff0Var.f56205a0);
        if (!TextUtils.isEmpty(ff0Var.f56221b6)) {
            setContentDescription(ff0Var.f56221b6);
        }
        kj1.m213587d4(this, !TextUtils.isEmpty(ff0Var.f56222b7) ? ff0Var.f56222b7 : ff0Var.f56209a4);
        setVisibility(ff0Var.isVisible() ? 0 : 8);
        this.f56035a0 = true;
    }

    /* renamed from: a3 */
    public final void m212683a3(float f, float f2) {
        View view = this.f56046b1;
        if (view != null) {
            fh0 fh0Var = this.f56057c2;
            fh0Var.getClass();
            view.setScaleX(AbstractC1249t7.m214727a0(0.4f, 1.0f, f));
            view.setScaleY(fh0Var.mo212608a2(f, f2));
            view.setAlpha(AbstractC1249t7.m214728a1(0.0f, 1.0f, f2 == 0.0f ? 0.8f : 0.0f, f2 == 0.0f ? 1.0f : 0.2f, f));
        }
        this.f56058c3 = f;
    }

    /* renamed from: a7 */
    public final void m212684a7(int i) {
        View view = this.f56046b1;
        if (view == null) {
            return;
        }
        int iMin = Math.min(this.f56060c5, i - (this.f56063c8 * 2));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height = (this.f56062c7 && this.f56043a8 == 2) ? iMin : this.f56061c6;
        layoutParams.width = iMin;
        view.setLayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        FrameLayout frameLayout = this.f56045b0;
        if (frameLayout != null && this.f56059c4) {
            frameLayout.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public Drawable getActiveIndicatorDrawable() {
        View view = this.f56046b1;
        if (view == null) {
            return null;
        }
        return view.getBackground();
    }

    public C0390ct getBadge() {
        return this.f56064c9;
    }

    public int getItemBackgroundResId() {
        return R$drawable.mtrl_navigation_bar_item_background;
    }

    @Override // p000.uf0
    public ff0 getItemData() {
        return this.f56052b7;
    }

    public int getItemDefaultMarginResId() {
        return R$dimen.mtrl_navigation_bar_item_default_margin;
    }

    public abstract int getItemLayoutResId();

    public int getItemPosition() {
        return this.f56051b6;
    }

    @Override // android.view.View
    public int getSuggestedMinimumHeight() {
        ViewGroup viewGroup = this.f56048b3;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        return viewGroup.getMeasuredHeight() + getSuggestedIconHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override // android.view.View
    public int getSuggestedMinimumWidth() {
        ViewGroup viewGroup = this.f56048b3;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        return Math.max(getSuggestedIconWidth(), viewGroup.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        ff0 ff0Var = this.f56052b7;
        if (ff0Var != null && ff0Var.isCheckable() && this.f56052b7.isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, f56032d0);
        }
        return iArrOnCreateDrawableState;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x007e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        Object quantityString;
        Context context;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        C0390ct c0390ct = this.f56064c9;
        if (c0390ct != null && c0390ct.isVisible()) {
            ff0 ff0Var = this.f56052b7;
            CharSequence charSequence = ff0Var.f56209a4;
            if (!TextUtils.isEmpty(ff0Var.f56221b6)) {
                charSequence = this.f56052b7.f56221b6;
            }
            StringBuilder sb = new StringBuilder();
            sb.append((Object) charSequence);
            sb.append(", ");
            C0390ct c0390ct2 = this.f56064c9;
            C0391cu c0391cu = c0390ct2.f55508a4;
            if (c0390ct2.isVisible()) {
                boolean zM212531a0 = c0391cu.m212531a0();
                BadgeState$State badgeState$State = c0391cu.f55520a1;
                if (!zM212531a0) {
                    quantityString = badgeState$State.f49115b2;
                } else if (badgeState$State.f49116b3 != 0 && (context = (Context) c0390ct2.f55504a0.get()) != null) {
                    int iM212527a2 = c0390ct2.m212527a2();
                    int i = c0390ct2.f55511a7;
                    quantityString = iM212527a2 <= i ? context.getResources().getQuantityString(badgeState$State.f49116b3, c0390ct2.m212527a2(), Integer.valueOf(c0390ct2.m212527a2())) : context.getString(badgeState$State.f49117b4, Integer.valueOf(i));
                }
                sb.append(quantityString);
                accessibilityNodeInfo.setContentDescription(sb.toString());
            } else {
                quantityString = null;
                sb.append(quantityString);
                accessibilityNodeInfo.setContentDescription(sb.toString());
            }
        }
        accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) C0747k6.m213451a1(0, 1, getItemVisiblePosition(), 1, false, isSelected()).f57459a0);
        if (isSelected()) {
            accessibilityNodeInfo.setClickable(false);
            accessibilityNodeInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) C0745k4.f57437a6.f57446a0);
        }
        AbstractC0746k5.m213449a0(accessibilityNodeInfo).putCharSequence("AccessibilityNodeInfo.roleDescription", getResources().getString(R$string.item_view_role_description));
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new RunnableC0503fo(this, i, 3));
    }

    public void setActiveIndicatorDrawable(Drawable drawable) {
        View view = this.f56046b1;
        if (view == null) {
            return;
        }
        view.setBackgroundDrawable(drawable);
        m212682a1();
    }

    public void setActiveIndicatorEnabled(boolean z) {
        this.f56059c4 = z;
        m212682a1();
        View view = this.f56046b1;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
            requestLayout();
        }
    }

    public void setActiveIndicatorHeight(int i) {
        this.f56061c6 = i;
        m212684a7(getWidth());
    }

    public void setActiveIndicatorMarginHorizontal(int i) {
        this.f56063c8 = i;
        m212684a7(getWidth());
    }

    public void setActiveIndicatorResizeable(boolean z) {
        this.f56062c7 = z;
    }

    public void setActiveIndicatorWidth(int i) {
        this.f56060c5 = i;
        m212684a7(getWidth());
    }

    public void setBadge(C0390ct c0390ct) {
        C0390ct c0390ct2 = this.f56064c9;
        if (c0390ct2 == c0390ct) {
            return;
        }
        ImageView imageView = this.f56047b2;
        if (c0390ct2 != null && imageView != null && c0390ct2 != null) {
            setClipChildren(true);
            setClipToPadding(true);
            C0390ct c0390ct3 = this.f56064c9;
            if (c0390ct3 != null) {
                WeakReference weakReference = c0390ct3.f55516b2;
                if ((weakReference != null ? (FrameLayout) weakReference.get() : null) != null) {
                    WeakReference weakReference2 = c0390ct3.f55516b2;
                    (weakReference2 != null ? (FrameLayout) weakReference2.get() : null).setForeground(null);
                } else {
                    imageView.getOverlay().remove(c0390ct3);
                }
            }
            this.f56064c9 = null;
        }
        this.f56064c9 = c0390ct;
        if (imageView == null || c0390ct == null) {
            return;
        }
        setClipChildren(false);
        setClipToPadding(false);
        C0390ct c0390ct4 = this.f56064c9;
        Rect rect = new Rect();
        imageView.getDrawingRect(rect);
        c0390ct4.setBounds(rect);
        c0390ct4.m212529a4(imageView, null);
        WeakReference weakReference3 = c0390ct4.f55516b2;
        if ((weakReference3 != null ? (FrameLayout) weakReference3.get() : null) == null) {
            imageView.getOverlay().add(c0390ct4);
        } else {
            WeakReference weakReference4 = c0390ct4.f55516b2;
            (weakReference4 != null ? (FrameLayout) weakReference4.get() : null).setForeground(c0390ct4);
        }
    }

    public void setCheckable(boolean z) {
        refreshDrawableState();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setChecked(boolean z) {
        TextView textView = this.f56050b5;
        textView.setPivotX(textView.getWidth() / 2);
        textView.setPivotY(textView.getBaseline());
        TextView textView2 = this.f56049b4;
        textView2.setPivotX(textView2.getWidth() / 2);
        textView2.setPivotY(textView2.getBaseline());
        float f = z ? 1.0f : 0.0f;
        if (this.f56059c4 && this.f56035a0) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            if (ia1.m213141a1(this)) {
                ValueAnimator valueAnimator = this.f56056c1;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.f56056c1 = null;
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.f56058c3, f);
                this.f56056c1 = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ci0(this, f));
                this.f56056c1.setInterpolator(kg1.m213537e4(getContext(), R$attr.motionEasingEmphasizedInterpolator, AbstractC1249t7.f60179a1));
                this.f56056c1.setDuration(kg1.m213536e3(getContext(), R$attr.motionDurationLong2, getResources().getInteger(R$integer.material_motion_duration_long_1)));
                this.f56056c1.start();
            }
        } else {
            m212683a3(f, f);
        }
        int i = this.f56043a8;
        ViewGroup viewGroup = this.f56048b3;
        if (i != -1) {
            if (i == 0) {
                if (z) {
                    m212679a6(getIconOrContainer(), this.f56038a3, 49);
                    m212680a8(viewGroup, this.f56039a4);
                    textView.setVisibility(0);
                } else {
                    m212679a6(getIconOrContainer(), this.f56038a3, 17);
                    m212680a8(viewGroup, 0);
                    textView.setVisibility(4);
                }
                textView2.setVisibility(4);
            } else if (i == 1) {
                m212680a8(viewGroup, this.f56039a4);
                if (z) {
                    m212679a6(getIconOrContainer(), (int) (this.f56038a3 + this.f56040a5), 49);
                    m212678a5(textView, 1.0f, 1.0f, 0);
                    float f2 = this.f56041a6;
                    m212678a5(textView2, f2, f2, 4);
                } else {
                    m212679a6(getIconOrContainer(), this.f56038a3, 49);
                    float f3 = this.f56042a7;
                    m212678a5(textView, f3, f3, 4);
                    m212678a5(textView2, 1.0f, 1.0f, 0);
                }
            } else if (i == 2) {
                m212679a6(getIconOrContainer(), this.f56038a3, 17);
                textView.setVisibility(8);
                textView2.setVisibility(8);
            }
        } else if (this.f56044a9) {
            if (z) {
                m212679a6(getIconOrContainer(), this.f56038a3, 49);
                m212680a8(viewGroup, this.f56039a4);
                textView.setVisibility(0);
            } else {
                m212679a6(getIconOrContainer(), this.f56038a3, 17);
                m212680a8(viewGroup, 0);
                textView.setVisibility(4);
            }
            textView2.setVisibility(4);
        } else {
            m212680a8(viewGroup, this.f56039a4);
            if (z) {
                m212679a6(getIconOrContainer(), (int) (this.f56038a3 + this.f56040a5), 49);
                m212678a5(textView, 1.0f, 1.0f, 0);
                float f4 = this.f56041a6;
                m212678a5(textView2, f4, f4, 4);
            } else {
                m212679a6(getIconOrContainer(), this.f56038a3, 49);
                float f5 = this.f56042a7;
                m212678a5(textView, f5, f5, 4);
                m212678a5(textView2, 1.0f, 1.0f, 0);
            }
        }
        refreshDrawableState();
        setSelected(z);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.f56049b4.setEnabled(z);
        this.f56050b5.setEnabled(z);
        this.f56047b2.setEnabled(z);
        if (!z) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            na1.m214060a3(this, null);
        } else {
            PointerIcon pointerIconM214298a1 = pn0.m214298a1(getContext(), 1002);
            WeakHashMap weakHashMap2 = xa1.f61054a0;
            na1.m214060a3(this, pointerIconM214298a1);
        }
    }

    public void setIcon(Drawable drawable) {
        if (drawable == this.f56054b9) {
            return;
        }
        this.f56054b9 = drawable;
        if (drawable != null) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                drawable = constantState.newDrawable();
            }
            drawable = drawable.mutate();
            this.f56055c0 = drawable;
            ColorStateList colorStateList = this.f56053b8;
            if (colorStateList != null) {
                AbstractC1270tr.m214774a7(drawable, colorStateList);
            }
        }
        this.f56047b2.setImageDrawable(drawable);
    }

    public void setIconSize(int i) {
        ImageView imageView = this.f56047b2;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        imageView.setLayoutParams(layoutParams);
    }

    public void setIconTintList(ColorStateList colorStateList) {
        Drawable drawable;
        this.f56053b8 = colorStateList;
        if (this.f56052b7 == null || (drawable = this.f56055c0) == null) {
            return;
        }
        AbstractC1270tr.m214774a7(drawable, colorStateList);
        this.f56055c0.invalidateSelf();
    }

    public void setItemBackground(int i) {
        setItemBackground(i == 0 ? null : AbstractC0870mp.m214013a1(getContext(), i));
    }

    public void setItemPaddingBottom(int i) {
        if (this.f56039a4 != i) {
            this.f56039a4 = i;
            ff0 ff0Var = this.f56052b7;
            if (ff0Var != null) {
                setChecked(ff0Var.isChecked());
            }
        }
    }

    public void setItemPaddingTop(int i) {
        if (this.f56038a3 != i) {
            this.f56038a3 = i;
            ff0 ff0Var = this.f56052b7;
            if (ff0Var != null) {
                setChecked(ff0Var.isChecked());
            }
        }
    }

    public void setItemPosition(int i) {
        this.f56051b6 = i;
    }

    public void setItemRippleColor(ColorStateList colorStateList) {
        this.f56036a1 = colorStateList;
        m212682a1();
    }

    public void setLabelVisibilityMode(int i) {
        if (this.f56043a8 != i) {
            this.f56043a8 = i;
            if (this.f56062c7 && i == 2) {
                this.f56057c2 = f56034d2;
            } else {
                this.f56057c2 = f56033d1;
            }
            m212684a7(getWidth());
            ff0 ff0Var = this.f56052b7;
            if (ff0Var != null) {
                setChecked(ff0Var.isChecked());
            }
        }
    }

    public void setShifting(boolean z) {
        if (this.f56044a9 != z) {
            this.f56044a9 = z;
            ff0 ff0Var = this.f56052b7;
            if (ff0Var != null) {
                setChecked(ff0Var.isChecked());
            }
        }
    }

    public void setTextAppearanceActive(int i) throws Resources.NotFoundException {
        TextView textView = this.f56050b5;
        m212677a4(textView, i);
        m212681a0(this.f56049b4.getTextSize(), textView.getTextSize());
        textView.setTypeface(textView.getTypeface(), 1);
    }

    public void setTextAppearanceInactive(int i) throws Resources.NotFoundException {
        TextView textView = this.f56049b4;
        m212677a4(textView, i);
        m212681a0(textView.getTextSize(), this.f56050b5.getTextSize());
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.f56049b4.setTextColor(colorStateList);
            this.f56050b5.setTextColor(colorStateList);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.f56049b4.setText(charSequence);
        this.f56050b5.setText(charSequence);
        ff0 ff0Var = this.f56052b7;
        if (ff0Var == null || TextUtils.isEmpty(ff0Var.f56221b6)) {
            setContentDescription(charSequence);
        }
        ff0 ff0Var2 = this.f56052b7;
        if (ff0Var2 != null && !TextUtils.isEmpty(ff0Var2.f56222b7)) {
            charSequence = this.f56052b7.f56222b7;
        }
        kj1.m213587d4(this, charSequence);
    }

    public void setItemBackground(Drawable drawable) {
        if (drawable != null && drawable.getConstantState() != null) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        this.f56037a2 = drawable;
        m212682a1();
    }
}
