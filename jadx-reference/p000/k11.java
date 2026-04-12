package p000;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.ViewGroup;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class k11 extends jl0 {

    /* renamed from: a1 */
    public final ViewGroupOnHierarchyChangeListenerC0906na f57419a1;

    public k11(Activity activity) {
        super(activity);
        this.f57419a1 = new ViewGroupOnHierarchyChangeListenerC0906na(this, activity);
    }

    @Override // p000.jl0
    /* renamed from: a8 */
    public final void mo213326a8() {
        Activity activity = (Activity) this.f57345a0;
        Resources.Theme theme = activity.getTheme();
        t60.m214694b5(theme, "activity.theme");
        m213327b0(theme, new TypedValue());
        ((ViewGroup) activity.getWindow().getDecorView()).setOnHierarchyChangeListener(this.f57419a1);
    }
}
