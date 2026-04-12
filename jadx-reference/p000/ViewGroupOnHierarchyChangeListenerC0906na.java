package p000;

import android.app.Activity;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.window.SplashScreenView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: na */
/* loaded from: classes.dex */
public final class ViewGroupOnHierarchyChangeListenerC0906na implements ViewGroup.OnHierarchyChangeListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f58466a0 = 0;

    /* renamed from: a1 */
    public final /* synthetic */ KeyEvent.Callback f58467a1;

    public ViewGroupOnHierarchyChangeListenerC0906na(k11 k11Var, Activity activity) {
        this.f58467a1 = activity;
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public final void onChildViewAdded(View view, View view2) {
        switch (this.f58466a0) {
            case 0:
                ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = ((CoordinatorLayout) this.f58467a1).f44824b6;
                if (onHierarchyChangeListener != null) {
                    onHierarchyChangeListener.onChildViewAdded(view, view2);
                    break;
                }
                break;
            default:
                if (AbstractC0858me.m213987b4(view2)) {
                    SplashScreenView splashScreenViewM213981a8 = AbstractC0858me.m213981a8(view2);
                    t60.m214695b6(splashScreenViewM213981a8, "child");
                    WindowInsets windowInsetsBuild = AbstractC0742k2.m213420a6().build();
                    t60.m214694b5(windowInsetsBuild, "Builder().build()");
                    Rect rect = new Rect(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
                    if (windowInsetsBuild == splashScreenViewM213981a8.getRootView().computeSystemWindowInsets(windowInsetsBuild, rect)) {
                        rect.isEmpty();
                    }
                    ((ViewGroup) ((Activity) this.f58467a1).getWindow().getDecorView()).setOnHierarchyChangeListener(null);
                    break;
                }
                break;
        }
    }

    @Override // android.view.ViewGroup.OnHierarchyChangeListener
    public final void onChildViewRemoved(View view, View view2) {
        switch (this.f58466a0) {
            case 0:
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.f58467a1;
                coordinatorLayout.m210067b5(2);
                ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = coordinatorLayout.f44824b6;
                if (onHierarchyChangeListener != null) {
                    onHierarchyChangeListener.onChildViewRemoved(view, view2);
                    break;
                }
                break;
        }
    }

    public ViewGroupOnHierarchyChangeListenerC0906na(CoordinatorLayout coordinatorLayout) {
        this.f58467a1 = coordinatorLayout;
    }

    /* renamed from: a0 */
    private final void m214054a0(View view, View view2) {
    }
}
