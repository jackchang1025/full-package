package android.support.v4.view;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

/* loaded from: classes.dex */
public final class ViewParentCompat {
    private static final String TAG = "ViewParentCompat";

    private ViewParentCompat() {
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i2) {
        viewParent.notifySubtreeAccessibilityStateChanged(view, view2, i2);
    }

    public static boolean onNestedFling(ViewParent viewParent, View view, float f2, float f3, boolean z2) {
        try {
            return viewParent.onNestedFling(view, f2, f3, z2);
        } catch (AbstractMethodError e2) {
            Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedFling", e2);
            return false;
        }
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View view, float f2, float f3) {
        try {
            return viewParent.onNestedPreFling(view, f2, f3);
        } catch (AbstractMethodError e2) {
            Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedPreFling", e2);
            return false;
        }
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int i2, int i3, int[] iArr) {
        onNestedPreScroll(viewParent, view, i2, i3, iArr, 0);
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int i2, int i3, int i4, int i5) {
        onNestedScroll(viewParent, view, i2, i3, i4, i5, 0);
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i2) {
        onNestedScrollAccepted(viewParent, view, view2, i2, 0);
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i2) {
        return onStartNestedScroll(viewParent, view, view2, i2, 0);
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view) {
        onStopNestedScroll(viewParent, view, 0);
    }

    @Deprecated
    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return viewParent.requestSendAccessibilityEvent(view, accessibilityEvent);
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int i2, int i3, int[] iArr, int i4) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedPreScroll(view, i2, i3, iArr, i4);
            return;
        }
        if (i4 == 0) {
            try {
                viewParent.onNestedPreScroll(view, i2, i3, iArr);
            } catch (AbstractMethodError e2) {
                Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedPreScroll", e2);
            }
        }
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int i2, int i3, int i4, int i5, int i6) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedScroll(view, i2, i3, i4, i5, i6);
            return;
        }
        if (i6 == 0) {
            try {
                viewParent.onNestedScroll(view, i2, i3, i4, i5);
            } catch (AbstractMethodError e2) {
                Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedScroll", e2);
            }
        }
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i2, int i3) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onNestedScrollAccepted(view, view2, i2, i3);
            return;
        }
        if (i3 == 0) {
            try {
                viewParent.onNestedScrollAccepted(view, view2, i2);
            } catch (AbstractMethodError e2) {
                Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onNestedScrollAccepted", e2);
            }
        }
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i2, int i3) {
        if (viewParent instanceof NestedScrollingParent2) {
            return ((NestedScrollingParent2) viewParent).onStartNestedScroll(view, view2, i2, i3);
        }
        if (i3 != 0) {
            return false;
        }
        try {
            return viewParent.onStartNestedScroll(view, view2, i2);
        } catch (AbstractMethodError e2) {
            Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onStartNestedScroll", e2);
            return false;
        }
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view, int i2) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2) viewParent).onStopNestedScroll(view, i2);
            return;
        }
        if (i2 == 0) {
            try {
                viewParent.onStopNestedScroll(view);
            } catch (AbstractMethodError e2) {
                Log.e(TAG, "ViewParent " + viewParent + " does not implement interface method onStopNestedScroll", e2);
            }
        }
    }
}
