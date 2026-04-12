package p000;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.R$dimen;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class i71 implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {

    /* renamed from: b0 */
    public static i71 f56806b0;

    /* renamed from: b1 */
    public static i71 f56807b1;

    /* renamed from: a0 */
    public final View f56808a0;

    /* renamed from: a1 */
    public final CharSequence f56809a1;

    /* renamed from: a2 */
    public final int f56810a2;

    /* renamed from: a3 */
    public final h71 f56811a3;

    /* renamed from: a4 */
    public final h71 f56812a4;

    /* renamed from: a5 */
    public int f56813a5;

    /* renamed from: a6 */
    public int f56814a6;

    /* renamed from: a7 */
    public k71 f56815a7;

    /* renamed from: a8 */
    public boolean f56816a8;

    /* renamed from: a9 */
    public boolean f56817a9;

    /* JADX WARN: Type inference failed for: r0v0, types: [h71] */
    /* JADX WARN: Type inference failed for: r0v1, types: [h71] */
    public i71(View view, CharSequence charSequence) {
        final int i = 0;
        this.f56811a3 = new Runnable(this) { // from class: h71

            /* renamed from: a1 */
            public final /* synthetic */ i71 f56624a1;

            {
                this.f56624a1 = this;
            }

            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                switch (i) {
                    case 0:
                        this.f56624a1.m213109a2(false);
                        break;
                    default:
                        this.f56624a1.m213108a0();
                        break;
                }
            }
        };
        final int i2 = 1;
        this.f56812a4 = new Runnable(this) { // from class: h71

            /* renamed from: a1 */
            public final /* synthetic */ i71 f56624a1;

            {
                this.f56624a1 = this;
            }

            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                switch (i2) {
                    case 0:
                        this.f56624a1.m213109a2(false);
                        break;
                    default:
                        this.f56624a1.m213108a0();
                        break;
                }
            }
        };
        this.f56808a0 = view;
        this.f56809a1 = charSequence;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(view.getContext());
        Method method = ab1.f43607a0;
        this.f56810a2 = Build.VERSION.SDK_INT >= 28 ? za1.m215385a0(viewConfiguration) : viewConfiguration.getScaledTouchSlop() / 2;
        this.f56817a9 = true;
        view.setOnLongClickListener(this);
        view.setOnHoverListener(this);
    }

    /* renamed from: a1 */
    public static void m213107a1(i71 i71Var) {
        i71 i71Var2 = f56806b0;
        if (i71Var2 != null) {
            i71Var2.f56808a0.removeCallbacks(i71Var2.f56811a3);
        }
        f56806b0 = i71Var;
        if (i71Var != null) {
            i71Var.f56808a0.postDelayed(i71Var.f56811a3, ViewConfiguration.getLongPressTimeout());
        }
    }

    /* renamed from: a0 */
    public final void m213108a0() {
        i71 i71Var = f56807b1;
        View view = this.f56808a0;
        if (i71Var == this) {
            f56807b1 = null;
            k71 k71Var = this.f56815a7;
            if (k71Var != null) {
                View view2 = k71Var.f57475a1;
                if (view2.getParent() != null) {
                    ((WindowManager) k71Var.f57474a0.getSystemService("window")).removeView(view2);
                }
                this.f56815a7 = null;
                this.f56817a9 = true;
                view.removeOnAttachStateChangeListener(this);
            }
        }
        if (f56806b0 == this) {
            m213107a1(null);
        }
        view.removeCallbacks(this.f56812a4);
    }

    /* renamed from: a2 */
    public final void m213109a2(boolean z) throws Resources.NotFoundException {
        int height;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        long longPressTimeout;
        long j;
        long j2;
        WeakHashMap weakHashMap = xa1.f61054a0;
        View view = this.f56808a0;
        if (ia1.m213141a1(view)) {
            m213107a1(null);
            i71 i71Var = f56807b1;
            if (i71Var != null) {
                i71Var.m213108a0();
            }
            f56807b1 = this;
            this.f56816a8 = z;
            k71 k71Var = new k71(view.getContext());
            this.f56815a7 = k71Var;
            int width = this.f56813a5;
            int i6 = this.f56814a6;
            boolean z2 = this.f56816a8;
            View view2 = k71Var.f57475a1;
            ViewParent parent = view2.getParent();
            Context context = k71Var.f57474a0;
            if (parent != null && view2.getParent() != null) {
                ((WindowManager) context.getSystemService("window")).removeView(view2);
            }
            k71Var.f57476a2.setText(this.f56809a1);
            IBinder applicationWindowToken = view.getApplicationWindowToken();
            WindowManager.LayoutParams layoutParams = k71Var.f57477a3;
            layoutParams.token = applicationWindowToken;
            int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R$dimen.tooltip_precise_anchor_threshold);
            if (view.getWidth() < dimensionPixelOffset) {
                width = view.getWidth() / 2;
            }
            if (view.getHeight() >= dimensionPixelOffset) {
                int dimensionPixelOffset2 = context.getResources().getDimensionPixelOffset(R$dimen.tooltip_precise_anchor_extra_offset);
                height = i6 + dimensionPixelOffset2;
                i = i6 - dimensionPixelOffset2;
            } else {
                height = view.getHeight();
                i = 0;
            }
            layoutParams.gravity = 49;
            int dimensionPixelOffset3 = context.getResources().getDimensionPixelOffset(z2 ? R$dimen.tooltip_y_offset_touch : R$dimen.tooltip_y_offset_non_touch);
            View rootView = view.getRootView();
            ViewGroup.LayoutParams layoutParams2 = rootView.getLayoutParams();
            if (!(layoutParams2 instanceof WindowManager.LayoutParams) || ((WindowManager.LayoutParams) layoutParams2).type != 2) {
                Context context2 = view.getContext();
                while (true) {
                    if (!(context2 instanceof ContextWrapper)) {
                        break;
                    }
                    if (context2 instanceof Activity) {
                        rootView = ((Activity) context2).getWindow().getDecorView();
                        break;
                    }
                    context2 = ((ContextWrapper) context2).getBaseContext();
                }
            }
            if (rootView == null) {
                i5 = 1;
            } else {
                Rect rect = k71Var.f57478a4;
                rootView.getWindowVisibleDisplayFrame(rect);
                if (rect.left >= 0 || rect.top >= 0) {
                    i2 = width;
                    i3 = i;
                    i4 = 0;
                    i5 = 1;
                } else {
                    Resources resources = context.getResources();
                    i5 = 1;
                    i2 = width;
                    i3 = i;
                    int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    i4 = 0;
                    rect.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
                }
                int[] iArr = k71Var.f57480a6;
                rootView.getLocationOnScreen(iArr);
                int[] iArr2 = k71Var.f57479a5;
                view.getLocationOnScreen(iArr2);
                int i7 = iArr2[i4] - iArr[i4];
                iArr2[i4] = i7;
                iArr2[i5] = iArr2[i5] - iArr[i5];
                layoutParams.x = (i7 + i2) - (rootView.getWidth() / 2);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i4, i4);
                view2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredHeight = view2.getMeasuredHeight();
                int i8 = iArr2[i5];
                int i9 = ((i8 + i3) - dimensionPixelOffset3) - measuredHeight;
                int i10 = i8 + height + dimensionPixelOffset3;
                if (z2) {
                    if (i9 >= 0) {
                        layoutParams.y = i9;
                    } else {
                        layoutParams.y = i10;
                    }
                } else if (measuredHeight + i10 <= rect.height()) {
                    layoutParams.y = i10;
                } else {
                    layoutParams.y = i9;
                }
            }
            ((WindowManager) context.getSystemService("window")).addView(view2, layoutParams);
            view.addOnAttachStateChangeListener(this);
            if (this.f56816a8) {
                j2 = 2500;
            } else {
                if ((fa1.m212769a6(view) & 1) == i5) {
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    j = 3000;
                } else {
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    j = 15000;
                }
                j2 = j - longPressTimeout;
            }
            h71 h71Var = this.f56812a4;
            view.removeCallbacks(h71Var);
            view.postDelayed(h71Var, j2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0066  */
    @Override // android.view.View.OnHoverListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onHover(View view, MotionEvent motionEvent) {
        if (this.f56815a7 == null || !this.f56816a8) {
            View view2 = this.f56808a0;
            AccessibilityManager accessibilityManager = (AccessibilityManager) view2.getContext().getSystemService("accessibility");
            if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
                int action = motionEvent.getAction();
                if (action != 7) {
                    if (action == 10) {
                        this.f56817a9 = true;
                        m213108a0();
                        return false;
                    }
                } else if (view2.isEnabled() && this.f56815a7 == null) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (!this.f56817a9) {
                        int iAbs = Math.abs(x - this.f56813a5);
                        int i = this.f56810a2;
                        if (iAbs > i || Math.abs(y - this.f56814a6) > i) {
                            this.f56813a5 = x;
                            this.f56814a6 = y;
                            this.f56817a9 = false;
                            m213107a1(this);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) throws Resources.NotFoundException {
        this.f56813a5 = view.getWidth() / 2;
        this.f56814a6 = view.getHeight() / 2;
        m213109a2(true);
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        m213108a0();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
