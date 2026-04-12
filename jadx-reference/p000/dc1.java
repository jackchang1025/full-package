package p000;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class dc1 extends LinearLayoutManager {

    /* renamed from: d0 */
    public final /* synthetic */ ViewPager2 f55692d0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dc1(ViewPager2 viewPager2) {
        super(1);
        this.f55692d0 = viewPager2;
    }

    @Override // p000.pq0
    /* renamed from: e2 */
    public final void mo212583e2(vq0 vq0Var, ar0 ar0Var, C0748k7 c0748k7) {
        super.mo212583e2(vq0Var, ar0Var, c0748k7);
        this.f55692d0.f45491b9.getClass();
    }

    @Override // p000.pq0
    /* renamed from: f5 */
    public final boolean mo212584f5(vq0 vq0Var, ar0 ar0Var, int i, Bundle bundle) {
        this.f55692d0.f45491b9.getClass();
        return super.mo212584f5(vq0Var, ar0Var, i, bundle);
    }

    @Override // p000.pq0
    /* renamed from: g0 */
    public final boolean mo210970g0(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        return false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    /* renamed from: h6 */
    public final void mo210313h6(ar0 ar0Var, int[] iArr) {
        ViewPager2 viewPager2 = this.f55692d0;
        int offscreenPageLimit = viewPager2.getOffscreenPageLimit();
        if (offscreenPageLimit == -1) {
            super.mo210313h6(ar0Var, iArr);
            return;
        }
        int pageSize = viewPager2.getPageSize() * offscreenPageLimit;
        iArr[0] = pageSize;
        iArr[1] = pageSize;
    }
}
