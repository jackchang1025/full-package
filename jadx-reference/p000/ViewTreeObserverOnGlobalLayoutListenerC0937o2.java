package p000;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.C0041a1;
import androidx.appcompat.widget.C0043a3;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: o2 */
/* loaded from: classes.dex */
public final class ViewTreeObserverOnGlobalLayoutListenerC0937o2 implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f58718a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58719a1;

    public /* synthetic */ ViewTreeObserverOnGlobalLayoutListenerC0937o2(int i, Object obj) {
        this.f58718a0 = i;
        this.f58719a1 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        C0041a1 c0041a1;
        sf0 sf0Var;
        Activity activity;
        Rect rect;
        int i = this.f58718a0;
        int i2 = 0;
        Object obj = this.f58719a1;
        switch (i) {
            case 0:
                ActivityChooserView activityChooserView = (ActivityChooserView) obj;
                if (activityChooserView.m209876a1()) {
                    if (!activityChooserView.isShown()) {
                        activityChooserView.getListPopupWindow().dismiss();
                        break;
                    } else {
                        activityChooserView.getListPopupWindow().mo209888a3();
                        AbstractC0904n8 abstractC0904n8 = activityChooserView.f43881a6;
                        if (abstractC0904n8 != null && (c0041a1 = abstractC0904n8.f58463a0) != null && (sf0Var = c0041a1.f44142a4) != null) {
                            sf0Var.mo210851b6(c0041a1.f44140a2);
                            break;
                        }
                    }
                }
                break;
            case 1:
                AppCompatSpinner appCompatSpinner = (AppCompatSpinner) obj;
                if (!appCompatSpinner.getInternalPopup().mo215224a1()) {
                    appCompatSpinner.f43930a5.mo215232b3(AbstractC1440y0.m215220a1(appCompatSpinner), AbstractC1440y0.m215219a0(appCompatSpinner));
                }
                ViewTreeObserver viewTreeObserver = appCompatSpinner.getViewTreeObserver();
                if (viewTreeObserver != null) {
                    AbstractC1406x9.m215137a0(viewTreeObserver, this);
                    break;
                }
                break;
            case 2:
                C1446y6 c1446y6 = (C1446y6) obj;
                AppCompatSpinner appCompatSpinner2 = c1446y6.f61244d2;
                c1446y6.getClass();
                WeakHashMap weakHashMap = xa1.f61054a0;
                if (!ia1.m213141a1(appCompatSpinner2) || !appCompatSpinner2.getGlobalVisibleRect(c1446y6.f61242d0)) {
                    c1446y6.dismiss();
                    break;
                } else {
                    c1446y6.m215245b8();
                    c1446y6.mo209888a3();
                    break;
                }
                break;
            case 3:
                ViewOnKeyListenerC0542gn viewOnKeyListenerC0542gn = (ViewOnKeyListenerC0542gn) obj;
                ArrayList arrayList = viewOnKeyListenerC0542gn.f56526a7;
                if (viewOnKeyListenerC0542gn.mo209886a1() && arrayList.size() > 0 && !((C0541gm) arrayList.get(0)).f56516a0.f43996c4) {
                    View view = viewOnKeyListenerC0542gn.f56533b4;
                    if (view != null && view.isShown()) {
                        int size = arrayList.size();
                        while (i2 < size) {
                            Object obj2 = arrayList.get(i2);
                            i2++;
                            ((C0541gm) obj2).f56516a0.mo209888a3();
                        }
                        break;
                    } else {
                        viewOnKeyListenerC0542gn.dismiss();
                        break;
                    }
                }
                break;
            case 4:
                NavigationView navigationView = (NavigationView) obj;
                int[] iArr = navigationView.f49665b0;
                navigationView.getLocationOnScreen(iArr);
                boolean z = iArr[1] == 0;
                ui0 ui0Var = navigationView.f49663a8;
                if (ui0Var.f60449c2 != z) {
                    ui0Var.f60449c2 = z;
                    int i3 = (ui0Var.f60428a1.getChildCount() == 0 && ui0Var.f60449c2) ? ui0Var.f60451c4 : 0;
                    NavigationMenuView navigationMenuView = ui0Var.f60427a0;
                    navigationMenuView.setPadding(0, i3, 0, navigationMenuView.getPaddingBottom());
                }
                navigationView.setDrawTopInsetForeground(z && navigationView.f49668b3);
                int i4 = iArr[0];
                navigationView.setDrawLeftInsetForeground(i4 == 0 || navigationView.getWidth() + i4 == 0);
                Context context = navigationView.getContext();
                while (true) {
                    if (!(context instanceof ContextWrapper)) {
                        activity = null;
                    } else if (context instanceof Activity) {
                        activity = (Activity) context;
                    } else {
                        context = ((ContextWrapper) context).getBaseContext();
                    }
                }
                if (activity != null) {
                    int i5 = Build.VERSION.SDK_INT;
                    int i6 = kj1.f57537a5;
                    WindowManager windowManager = (WindowManager) activity.getSystemService("window");
                    if (i5 >= 30) {
                        rect = windowManager.getCurrentWindowMetrics().getBounds();
                    } else {
                        Display defaultDisplay = windowManager.getDefaultDisplay();
                        Point point = new Point();
                        defaultDisplay.getRealSize(point);
                        rect = new Rect();
                        rect.right = point.x;
                        rect.bottom = point.y;
                    }
                    navigationView.setDrawBottomInsetForeground((rect.height() - navigationView.getHeight() == iArr[1]) == true && (Color.alpha(activity.getWindow().getNavigationBarColor()) != 0) == true && navigationView.f49669b4);
                    navigationView.setDrawRightInsetForeground(rect.width() == iArr[0] || rect.width() - navigationView.getWidth() == iArr[0]);
                    break;
                }
                break;
            default:
                v11 v11Var = (v11) obj;
                C0043a3 c0043a3 = v11Var.f60554a7;
                if (v11Var.mo209886a1() && !c0043a3.f43996c4) {
                    View view2 = v11Var.f60559b2;
                    if (view2 != null && view2.isShown()) {
                        c0043a3.mo209888a3();
                        break;
                    } else {
                        v11Var.dismiss();
                        break;
                    }
                }
                break;
        }
    }
}
