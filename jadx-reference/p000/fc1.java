package p000;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class fc1 implements InterfaceC0812l9 {

    /* renamed from: a0 */
    public final /* synthetic */ int f56196a0;

    /* renamed from: a1 */
    public final /* synthetic */ x31 f56197a1;

    public /* synthetic */ fc1(x31 x31Var, int i) {
        this.f56196a0 = i;
        this.f56197a1 = x31Var;
    }

    @Override // p000.InterfaceC0812l9
    /* renamed from: a2 */
    public final boolean mo210913a2(View view) {
        switch (this.f56196a0) {
            case 0:
                int currentItem = ((ViewPager2) view).getCurrentItem() + 1;
                ViewPager2 viewPager2 = (ViewPager2) this.f56197a1.f61015a3;
                if (viewPager2.f45489b7) {
                    viewPager2.m210450a2(currentItem);
                    break;
                }
                break;
            default:
                int currentItem2 = ((ViewPager2) view).getCurrentItem() - 1;
                ViewPager2 viewPager22 = (ViewPager2) this.f56197a1.f61015a3;
                if (viewPager22.f45489b7) {
                    viewPager22.m210450a2(currentItem2);
                    break;
                }
                break;
        }
        return true;
    }
}
