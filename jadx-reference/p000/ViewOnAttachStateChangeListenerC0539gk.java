package p000;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import com.google.android.material.search.SearchBar;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gk */
/* loaded from: classes.dex */
public final class ViewOnAttachStateChangeListenerC0539gk implements View.OnAttachStateChangeListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f56507a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f56508a1;

    public /* synthetic */ ViewOnAttachStateChangeListenerC0539gk(int i, Object obj) {
        this.f56507a0 = i;
        this.f56508a1 = obj;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        int i = this.f56507a0;
        Object obj = this.f56508a1;
        switch (i) {
            case 1:
                C1415xf c1415xf = (C1415xf) obj;
                AccessibilityManager accessibilityManager = c1415xf.f61098b9;
                if (c1415xf.f61099c0 != null && accessibilityManager != null) {
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    if (ia1.m213141a1(c1415xf)) {
                        AbstractC0701j0.m213201a0(accessibilityManager, c1415xf.f61099c0);
                        break;
                    }
                }
                break;
            case 2:
                View view2 = (View) obj;
                view2.removeOnAttachStateChangeListener(this);
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                ja1.m213282a2(view2);
                break;
            case 3:
                SearchBar searchBar = (SearchBar) obj;
                AbstractC0701j0.m213201a0(searchBar.f49724f6, searchBar.f49725f7);
                break;
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        AccessibilityManager accessibilityManager;
        switch (this.f56507a0) {
            case 0:
                ViewOnKeyListenerC0542gn viewOnKeyListenerC0542gn = (ViewOnKeyListenerC0542gn) this.f56508a1;
                ViewTreeObserver viewTreeObserver = viewOnKeyListenerC0542gn.f56542c3;
                if (viewTreeObserver != null) {
                    if (!viewTreeObserver.isAlive()) {
                        viewOnKeyListenerC0542gn.f56542c3 = view.getViewTreeObserver();
                    }
                    viewOnKeyListenerC0542gn.f56542c3.removeGlobalOnLayoutListener(viewOnKeyListenerC0542gn.f56527a8);
                }
                view.removeOnAttachStateChangeListener(this);
                break;
            case 1:
                C1415xf c1415xf = (C1415xf) this.f56508a1;
                InterfaceC0702j1 interfaceC0702j1 = c1415xf.f61099c0;
                if (interfaceC0702j1 != null && (accessibilityManager = c1415xf.f61098b9) != null) {
                    AbstractC0701j0.m213202a1(accessibilityManager, interfaceC0702j1);
                    break;
                }
                break;
            case 2:
                break;
            case 3:
                SearchBar searchBar = (SearchBar) this.f56508a1;
                AbstractC0701j0.m213202a1(searchBar.f49724f6, searchBar.f49725f7);
                break;
            default:
                v11 v11Var = (v11) this.f56508a1;
                ViewTreeObserver viewTreeObserver2 = v11Var.f60561b4;
                if (viewTreeObserver2 != null) {
                    if (!viewTreeObserver2.isAlive()) {
                        v11Var.f60561b4 = view.getViewTreeObserver();
                    }
                    v11Var.f60561b4.removeGlobalOnLayoutListener(v11Var.f60555a8);
                }
                view.removeOnAttachStateChangeListener(this);
                break;
        }
    }

    /* renamed from: a0 */
    private final void m212961a0(View view) {
    }

    /* renamed from: a1 */
    private final void m212962a1(View view) {
    }

    /* renamed from: a2 */
    private final void m212963a2(View view) {
    }
}
