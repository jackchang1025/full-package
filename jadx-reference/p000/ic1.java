package p000;

import android.content.Context;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ic1 extends RecyclerView {

    /* renamed from: h1 */
    public final /* synthetic */ ViewPager2 f56864h1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ic1(ViewPager2 viewPager2, Context context) {
        super(context, null);
        this.f56864h1 = viewPager2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        this.f56864h1.f45491b9.getClass();
        return super.getAccessibilityClassName();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        ViewPager2 viewPager2 = this.f56864h1;
        accessibilityEvent.setFromIndex(viewPager2.f45475a3);
        accessibilityEvent.setToIndex(viewPager2.f45475a3);
        accessibilityEvent.setSource((ViewPager2) viewPager2.f45491b9.f61015a3);
        accessibilityEvent.setClassName("androidx.viewpager.widget.ViewPager");
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.f56864h1.f45489b7 && super.onInterceptTouchEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return this.f56864h1.f45489b7 && super.onTouchEvent(motionEvent);
    }
}
