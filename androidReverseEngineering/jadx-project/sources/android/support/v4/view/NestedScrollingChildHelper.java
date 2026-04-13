package android.support.v4.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewParent;

/* loaded from: classes.dex */
public class NestedScrollingChildHelper {
    private boolean mIsNestedScrollingEnabled;
    private ViewParent mNestedScrollingParentNonTouch;
    private ViewParent mNestedScrollingParentTouch;
    private int[] mTempNestedScrollConsumed;
    private final View mView;

    public NestedScrollingChildHelper(@NonNull View view) {
        this.mView = view;
    }

    private ViewParent getNestedScrollingParentForType(int i2) {
        if (i2 == 0) {
            return this.mNestedScrollingParentTouch;
        }
        if (i2 != 1) {
            return null;
        }
        return this.mNestedScrollingParentNonTouch;
    }

    private void setNestedScrollingParentForType(int i2, ViewParent viewParent) {
        if (i2 == 0) {
            this.mNestedScrollingParentTouch = viewParent;
        } else {
            if (i2 != 1) {
                return;
            }
            this.mNestedScrollingParentNonTouch = viewParent;
        }
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        ViewParent nestedScrollingParentForType;
        if (!isNestedScrollingEnabled() || (nestedScrollingParentForType = getNestedScrollingParentForType(0)) == null) {
            return false;
        }
        return ViewParentCompat.onNestedFling(nestedScrollingParentForType, this.mView, f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        ViewParent nestedScrollingParentForType;
        if (!isNestedScrollingEnabled() || (nestedScrollingParentForType = getNestedScrollingParentForType(0)) == null) {
            return false;
        }
        return ViewParentCompat.onNestedPreFling(nestedScrollingParentForType, this.mView, f2, f3);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, @Nullable int[] iArr, @Nullable int[] iArr2) {
        return dispatchNestedPreScroll(i2, i3, iArr, iArr2, 0);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, @Nullable int[] iArr) {
        return dispatchNestedScroll(i2, i3, i4, i5, iArr, 0);
    }

    public boolean hasNestedScrollingParent() {
        return hasNestedScrollingParent(0);
    }

    public boolean isNestedScrollingEnabled() {
        return this.mIsNestedScrollingEnabled;
    }

    public void onDetachedFromWindow() {
        ViewCompat.stopNestedScroll(this.mView);
    }

    public void onStopNestedScroll(@NonNull View view) {
        ViewCompat.stopNestedScroll(this.mView);
    }

    public void setNestedScrollingEnabled(boolean z2) {
        if (this.mIsNestedScrollingEnabled) {
            ViewCompat.stopNestedScroll(this.mView);
        }
        this.mIsNestedScrollingEnabled = z2;
    }

    public boolean startNestedScroll(int i2) {
        return startNestedScroll(i2, 0);
    }

    public void stopNestedScroll() {
        stopNestedScroll(0);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, @Nullable int[] iArr, @Nullable int[] iArr2, int i4) {
        ViewParent nestedScrollingParentForType;
        int i5;
        int i6;
        if (!isNestedScrollingEnabled() || (nestedScrollingParentForType = getNestedScrollingParentForType(i4)) == null) {
            return false;
        }
        if (i2 == 0 && i3 == 0) {
            if (iArr2 == null) {
                return false;
            }
            iArr2[0] = 0;
            iArr2[1] = 0;
            return false;
        }
        if (iArr2 != null) {
            this.mView.getLocationInWindow(iArr2);
            i5 = iArr2[0];
            i6 = iArr2[1];
        } else {
            i5 = 0;
            i6 = 0;
        }
        if (iArr == null) {
            if (this.mTempNestedScrollConsumed == null) {
                this.mTempNestedScrollConsumed = new int[2];
            }
            iArr = this.mTempNestedScrollConsumed;
        }
        iArr[0] = 0;
        iArr[1] = 0;
        ViewParentCompat.onNestedPreScroll(nestedScrollingParentForType, this.mView, i2, i3, iArr, i4);
        if (iArr2 != null) {
            this.mView.getLocationInWindow(iArr2);
            iArr2[0] = iArr2[0] - i5;
            iArr2[1] = iArr2[1] - i6;
        }
        return (iArr[0] == 0 && iArr[1] == 0) ? false : true;
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, @Nullable int[] iArr, int i6) {
        ViewParent nestedScrollingParentForType;
        int i7;
        int i8;
        if (!isNestedScrollingEnabled() || (nestedScrollingParentForType = getNestedScrollingParentForType(i6)) == null) {
            return false;
        }
        if (i2 == 0 && i3 == 0 && i4 == 0 && i5 == 0) {
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
            return false;
        }
        if (iArr != null) {
            this.mView.getLocationInWindow(iArr);
            i7 = iArr[0];
            i8 = iArr[1];
        } else {
            i7 = 0;
            i8 = 0;
        }
        ViewParentCompat.onNestedScroll(nestedScrollingParentForType, this.mView, i2, i3, i4, i5, i6);
        if (iArr != null) {
            this.mView.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - i7;
            iArr[1] = iArr[1] - i8;
        }
        return true;
    }

    public boolean hasNestedScrollingParent(int i2) {
        return getNestedScrollingParentForType(i2) != null;
    }

    public boolean startNestedScroll(int i2, int i3) {
        if (hasNestedScrollingParent(i3)) {
            return true;
        }
        if (!isNestedScrollingEnabled()) {
            return false;
        }
        View view = this.mView;
        for (ViewParent parent = this.mView.getParent(); parent != null; parent = parent.getParent()) {
            if (ViewParentCompat.onStartNestedScroll(parent, view, this.mView, i2, i3)) {
                setNestedScrollingParentForType(i3, parent);
                ViewParentCompat.onNestedScrollAccepted(parent, view, this.mView, i2, i3);
                return true;
            }
            if (parent instanceof View) {
                view = (View) parent;
            }
        }
        return false;
    }

    public void stopNestedScroll(int i2) {
        ViewParent nestedScrollingParentForType = getNestedScrollingParentForType(i2);
        if (nestedScrollingParentForType != null) {
            ViewParentCompat.onStopNestedScroll(nestedScrollingParentForType, this.mView, i2);
            setNestedScrollingParentForType(i2, null);
        }
    }
}
