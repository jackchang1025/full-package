package p000;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ka1 implements View.OnApplyWindowInsetsListener {

    /* renamed from: a0 */
    public xf1 f57487a0 = null;

    /* renamed from: a1 */
    public final /* synthetic */ View f57488a1;

    /* renamed from: a2 */
    public final /* synthetic */ vk0 f57489a2;

    public ka1(View view, vk0 vk0Var) {
        this.f57488a1 = view;
        this.f57489a2 = vk0Var;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        xf1 xf1VarM215170a6 = xf1.m215170a6(view, windowInsets);
        int i = Build.VERSION.SDK_INT;
        vk0 vk0Var = this.f57489a2;
        if (i < 30) {
            la1.m213801a0(windowInsets, this.f57488a1);
            if (xf1VarM215170a6.equals(this.f57487a0)) {
                return vk0Var.mo213324a6(view, xf1VarM215170a6).m215175a5();
            }
        }
        this.f57487a0 = xf1VarM215170a6;
        xf1 xf1VarMo213324a6 = vk0Var.mo213324a6(view, xf1VarM215170a6);
        if (i >= 30) {
            return xf1VarMo213324a6.m215175a5();
        }
        WeakHashMap weakHashMap = xa1.f61054a0;
        ja1.m213282a2(view);
        return xf1VarMo213324a6.m215175a5();
    }
}
