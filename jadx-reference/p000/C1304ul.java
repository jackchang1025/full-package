package p000;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.R$attr;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ul */
/* loaded from: classes.dex */
public class C1304ul extends ListView {

    /* renamed from: a0 */
    public final Rect f60460a0;

    /* renamed from: a1 */
    public int f60461a1;

    /* renamed from: a2 */
    public int f60462a2;

    /* renamed from: a3 */
    public int f60463a3;

    /* renamed from: a4 */
    public int f60464a4;

    /* renamed from: a5 */
    public int f60465a5;

    /* renamed from: a6 */
    public C1302uj f60466a6;

    /* renamed from: a7 */
    public boolean f60467a7;

    /* renamed from: a8 */
    public final boolean f60468a8;

    /* renamed from: a9 */
    public boolean f60469a9;

    /* renamed from: b0 */
    public kb0 f60470b0;

    /* renamed from: b1 */
    public RunnableC0165ca f60471b1;

    public C1304ul(Context context, boolean z) {
        super(context, null, R$attr.dropDownListViewStyle);
        this.f60460a0 = new Rect();
        this.f60461a1 = 0;
        this.f60462a2 = 0;
        this.f60463a3 = 0;
        this.f60464a4 = 0;
        this.f60468a8 = z;
        setCacheColorHint(0);
    }

    /* renamed from: a0 */
    public final int m214852a0(int i, int i2) {
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom;
        }
        int measuredHeight = listPaddingTop + listPaddingBottom;
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int count = adapter.getCount();
        int i3 = 0;
        View view = null;
        for (int i4 = 0; i4 < count; i4++) {
            int itemViewType = adapter.getItemViewType(i4);
            if (itemViewType != i3) {
                view = null;
                i3 = itemViewType;
            }
            view = adapter.getView(i4, view, this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            int i5 = layoutParams.height;
            view.measure(i, i5 > 0 ? View.MeasureSpec.makeMeasureSpec(i5, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
            view.forceLayout();
            if (i4 > 0) {
                measuredHeight += dividerHeight;
            }
            measuredHeight += view.getMeasuredHeight();
            if (measuredHeight >= i2) {
                return i2;
            }
        }
        return measuredHeight;
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0016  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m214853a1(MotionEvent motionEvent, int i) throws IllegalAccessException, IllegalArgumentException {
        boolean z;
        boolean zM214845a0;
        View childAt;
        View childAt2;
        int actionMasked = motionEvent.getActionMasked();
        boolean z2 = true;
        if (actionMasked == 1) {
            z = false;
        } else {
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    z = true;
                    z2 = false;
                } else {
                    z2 = false;
                    z = false;
                }
                if (z || z2) {
                    this.f60469a9 = false;
                    setPressed(false);
                    drawableStateChanged();
                    childAt2 = getChildAt(this.f60465a5 - getFirstVisiblePosition());
                    if (childAt2 != null) {
                        childAt2.setPressed(false);
                    }
                }
                if (z) {
                    kb0 kb0Var = this.f60470b0;
                    if (kb0Var != null) {
                        if (kb0Var.f57506b5) {
                            kb0Var.m213479a3();
                        }
                        kb0Var.f57506b5 = false;
                    }
                } else {
                    if (this.f60470b0 == null) {
                        this.f60470b0 = new kb0(this);
                    }
                    kb0 kb0Var2 = this.f60470b0;
                    boolean z3 = kb0Var2.f57506b5;
                    kb0Var2.f57506b5 = true;
                    kb0Var2.onTouch(this, motionEvent);
                }
                return z;
            }
            z = true;
        }
        int iFindPointerIndex = motionEvent.findPointerIndex(i);
        if (iFindPointerIndex >= 0) {
            int x = (int) motionEvent.getX(iFindPointerIndex);
            int y = (int) motionEvent.getY(iFindPointerIndex);
            int iPointToPosition = pointToPosition(x, y);
            if (iPointToPosition != -1) {
                View childAt3 = getChildAt(iPointToPosition - getFirstVisiblePosition());
                float f = x;
                float f2 = y;
                this.f60469a9 = true;
                AbstractC1299ug.m214844a0(this, f, f2);
                if (!isPressed()) {
                    setPressed(true);
                }
                layoutChildren();
                int i2 = this.f60465a5;
                if (i2 != -1 && (childAt = getChildAt(i2 - getFirstVisiblePosition())) != null && childAt != childAt3 && childAt.isPressed()) {
                    childAt.setPressed(false);
                }
                this.f60465a5 = iPointToPosition;
                AbstractC1299ug.m214844a0(childAt3, f - childAt3.getLeft(), f2 - childAt3.getTop());
                if (!childAt3.isPressed()) {
                    childAt3.setPressed(true);
                }
                Drawable selector = getSelector();
                boolean z4 = (selector == null || iPointToPosition == -1) ? false : true;
                if (z4) {
                    selector.setVisible(false, false);
                }
                int left = childAt3.getLeft();
                int top = childAt3.getTop();
                int right = childAt3.getRight();
                int bottom = childAt3.getBottom();
                Rect rect = this.f60460a0;
                rect.set(left, top, right, bottom);
                rect.left -= this.f60461a1;
                rect.top -= this.f60462a2;
                rect.right += this.f60463a3;
                rect.bottom += this.f60464a4;
                if (AbstractC0496fi.m212821a0()) {
                    zM214845a0 = AbstractC1301ui.m214845a0(this);
                } else {
                    Field field = AbstractC1303uk.f60457a0;
                    if (field != null) {
                        try {
                            zM214845a0 = field.getBoolean(this);
                        } catch (IllegalAccessException unused) {
                        }
                    } else {
                        zM214845a0 = false;
                    }
                }
                if (childAt3.isEnabled() != zM214845a0) {
                    boolean z5 = !zM214845a0;
                    if (AbstractC0496fi.m212821a0()) {
                        AbstractC1301ui.m214846a1(this, z5);
                    } else {
                        Field field2 = AbstractC1303uk.f60457a0;
                        if (field2 != null) {
                            try {
                                field2.set(this, Boolean.valueOf(z5));
                            } catch (IllegalAccessException unused2) {
                            }
                        }
                    }
                    if (iPointToPosition != -1) {
                        refreshDrawableState();
                    }
                }
                if (z4) {
                    float fExactCenterX = rect.exactCenterX();
                    float fExactCenterY = rect.exactCenterY();
                    selector.setVisible(getVisibility() == 0, false);
                    AbstractC1270tr.m214771a4(selector, fExactCenterX, fExactCenterY);
                }
                Drawable selector2 = getSelector();
                if (selector2 != null && iPointToPosition != -1) {
                    AbstractC1270tr.m214771a4(selector2, f, f2);
                }
                C1302uj c1302uj = this.f60466a6;
                if (c1302uj != null) {
                    c1302uj.f60456a1 = false;
                }
                refreshDrawableState();
                if (actionMasked == 1) {
                    performItemClick(childAt3, iPointToPosition, getItemIdAtPosition(iPointToPosition));
                }
                z2 = false;
                z = true;
            }
        }
        if (z) {
            this.f60469a9 = false;
            setPressed(false);
            drawableStateChanged();
            childAt2 = getChildAt(this.f60465a5 - getFirstVisiblePosition());
            if (childAt2 != null) {
            }
        }
        if (z) {
        }
        return z;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        Drawable selector;
        Rect rect = this.f60460a0;
        if (!rect.isEmpty() && (selector = getSelector()) != null) {
            selector.setBounds(rect);
            selector.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        if (this.f60471b1 != null) {
            return;
        }
        super.drawableStateChanged();
        C1302uj c1302uj = this.f60466a6;
        if (c1302uj != null) {
            c1302uj.f60456a1 = true;
        }
        Drawable selector = getSelector();
        if (selector != null && this.f60469a9 && isPressed()) {
            selector.setState(getDrawableState());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean hasFocus() {
        return this.f60468a8 || super.hasFocus();
    }

    @Override // android.view.View
    public final boolean hasWindowFocus() {
        return this.f60468a8 || super.hasWindowFocus();
    }

    @Override // android.view.View
    public final boolean isFocused() {
        return this.f60468a8 || super.isFocused();
    }

    @Override // android.view.View
    public final boolean isInTouchMode() {
        return (this.f60468a8 && this.f60467a7) || super.isInTouchMode();
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.f60471b1 = null;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i = Build.VERSION.SDK_INT;
        if (i < 26) {
            return super.onHoverEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.f60471b1 == null) {
            RunnableC0165ca runnableC0165ca = new RunnableC0165ca(8, this);
            this.f60471b1 = runnableC0165ca;
            post(runnableC0165ca);
        }
        boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked != 9 && actionMasked != 7) {
            setSelection(-1);
            return zOnHoverEvent;
        }
        int iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        if (iPointToPosition != -1 && iPointToPosition != getSelectedItemPosition()) {
            View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
            if (childAt.isEnabled()) {
                requestFocus();
                if (i < 30 || !AbstractC1300uh.f60426a3) {
                    setSelectionFromTop(iPointToPosition, childAt.getTop() - getTop());
                } else {
                    try {
                        AbstractC1300uh.f60423a0.invoke(this, Integer.valueOf(iPointToPosition), childAt, Boolean.FALSE, -1, -1);
                        AbstractC1300uh.f60424a1.invoke(this, Integer.valueOf(iPointToPosition));
                        AbstractC1300uh.f60425a2.invoke(this, Integer.valueOf(iPointToPosition));
                    } catch (IllegalAccessException | InvocationTargetException unused) {
                    }
                }
            }
            Drawable selector = getSelector();
            if (selector != null && this.f60469a9 && isPressed()) {
                selector.setState(getDrawableState());
            }
        }
        return zOnHoverEvent;
    }

    @Override // android.widget.AbsListView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.f60465a5 = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        RunnableC0165ca runnableC0165ca = this.f60471b1;
        if (runnableC0165ca != null) {
            C1304ul c1304ul = (C1304ul) runnableC0165ca.f46085a1;
            c1304ul.f60471b1 = null;
            c1304ul.removeCallbacks(runnableC0165ca);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setListSelectionHidden(boolean z) {
        this.f60467a7 = z;
    }

    @Override // android.widget.AbsListView
    public void setSelector(Drawable drawable) {
        C1302uj c1302uj = null;
        if (drawable != null) {
            C1302uj c1302uj2 = new C1302uj();
            Drawable drawable2 = c1302uj2.f60455a0;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            c1302uj2.f60455a0 = drawable;
            drawable.setCallback(c1302uj2);
            c1302uj2.f60456a1 = true;
            c1302uj = c1302uj2;
        }
        this.f60466a6 = c1302uj;
        super.setSelector(c1302uj);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.f60461a1 = rect.left;
        this.f60462a2 = rect.top;
        this.f60463a3 = rect.right;
        this.f60464a4 = rect.bottom;
    }
}
