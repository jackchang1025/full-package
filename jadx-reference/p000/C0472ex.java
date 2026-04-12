package p000;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.sidesheet.SideSheetBehavior;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ex */
/* loaded from: classes2.dex */
public final class C0472ex extends cq0 {

    /* renamed from: b0 */
    public final /* synthetic */ int f56118b0;

    /* renamed from: b1 */
    public final /* synthetic */ AbstractC0879my f56119b1;

    public /* synthetic */ C0472ex(AbstractC0879my abstractC0879my, int i) {
        this.f56118b0 = i;
        this.f56119b1 = abstractC0879my;
    }

    @Override // p000.cq0
    /* renamed from: a5 */
    public final int mo212501a5(View view, int i) {
        switch (this.f56118b0) {
            case 0:
                return view.getLeft();
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f56119b1;
                return cq0.m212476a4(i, sideSheetBehavior.f49781a0.m213325a7(), sideSheetBehavior.f49793b2);
        }
    }

    @Override // p000.cq0
    /* renamed from: a6 */
    public final int mo212502a6(View view, int i) {
        switch (this.f56118b0) {
            case 0:
                return cq0.m212476a4(i, ((BottomSheetBehavior) this.f56119b1).m210942c4(), mo212504b7());
            default:
                return view.getTop();
        }
    }

    @Override // p000.cq0
    /* renamed from: b6 */
    public int mo212503b6(View view) {
        switch (this.f56118b0) {
            case 1:
                return ((SideSheetBehavior) this.f56119b1).f49793b2;
            default:
                return super.mo212503b6(view);
        }
    }

    @Override // p000.cq0
    /* renamed from: b7 */
    public int mo212504b7() {
        switch (this.f56118b0) {
            case 0:
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f56119b1;
                int i = BottomSheetBehavior.f49178f7;
                return bottomSheetBehavior.f49213d4 ? bottomSheetBehavior.f49224e5 : bottomSheetBehavior.f49211d2;
            default:
                return super.mo212504b7();
        }
    }

    @Override // p000.cq0
    /* renamed from: d1 */
    public final void mo212513d1(int i) {
        switch (this.f56118b0) {
            case 0:
                if (i == 1) {
                    BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f56119b1;
                    if (bottomSheetBehavior.f49215d6) {
                        bottomSheetBehavior.m210947c9(1);
                        break;
                    }
                }
                break;
            default:
                if (i == 1) {
                    SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f56119b1;
                    if (sideSheetBehavior.f49787a6) {
                        sideSheetBehavior.m211100b8(1);
                        break;
                    }
                }
                break;
        }
    }

    @Override // p000.cq0
    /* renamed from: d2 */
    public final void mo212514d2(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        switch (this.f56118b0) {
            case 0:
                ((BottomSheetBehavior) this.f56119b1).m210941c1(i2);
                return;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f56119b1;
                WeakReference weakReference = sideSheetBehavior.f49796b5;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null && (marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams()) != null) {
                    jl0 jl0Var = sideSheetBehavior.f49781a0;
                    int left = view.getLeft();
                    view.getRight();
                    int i3 = ((SideSheetBehavior) jl0Var.f57345a0).f49793b2;
                    if (left <= i3) {
                        marginLayoutParams.rightMargin = i3 - left;
                    }
                    view2.setLayoutParams(marginLayoutParams);
                }
                LinkedHashSet linkedHashSet = sideSheetBehavior.f49800b9;
                if (linkedHashSet.isEmpty()) {
                    return;
                }
                jl0 jl0Var2 = sideSheetBehavior.f49781a0;
                Object obj = jl0Var2.f57345a0;
                jl0Var2.m213325a7();
                Iterator it = linkedHashSet.iterator();
                if (it.hasNext()) {
                    throw AbstractC0003a2.m25a6(it);
                }
                return;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0103  */
    @Override // p000.cq0
    /* renamed from: d3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo212515d3(View view, float f, float f2) {
        switch (this.f56118b0) {
            case 0:
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f56119b1;
                int i = 6;
                if (f2 < 0.0f) {
                    if (bottomSheetBehavior.f49180a1) {
                        i = 3;
                    } else {
                        int top = view.getTop();
                        System.currentTimeMillis();
                        bottomSheetBehavior.getClass();
                        if (top <= bottomSheetBehavior.f49209d0) {
                        }
                    }
                } else if (bottomSheetBehavior.f49213d4 && bottomSheetBehavior.m210948d0(view, f2)) {
                    if (Math.abs(f) >= Math.abs(f2) || f2 <= bottomSheetBehavior.f49182a3) {
                        if (view.getTop() > (bottomSheetBehavior.m210942c4() + bottomSheetBehavior.f49224e5) / 2) {
                            i = 5;
                        } else if (bottomSheetBehavior.f49180a1 || Math.abs(view.getTop() - bottomSheetBehavior.m210942c4()) < Math.abs(view.getTop() - bottomSheetBehavior.f49209d0)) {
                        }
                    }
                } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                    int top2 = view.getTop();
                    if (!bottomSheetBehavior.f49180a1) {
                        int i2 = bottomSheetBehavior.f49209d0;
                        if (top2 < i2) {
                            if (top2 >= Math.abs(top2 - bottomSheetBehavior.f49211d2)) {
                                bottomSheetBehavior.getClass();
                            }
                        } else if (Math.abs(top2 - i2) < Math.abs(top2 - bottomSheetBehavior.f49211d2)) {
                            bottomSheetBehavior.getClass();
                        }
                    } else if (Math.abs(top2 - bottomSheetBehavior.f49208c9) >= Math.abs(top2 - bottomSheetBehavior.f49211d2)) {
                        i = 4;
                    }
                } else if (!bottomSheetBehavior.f49180a1) {
                    int top3 = view.getTop();
                    if (Math.abs(top3 - bottomSheetBehavior.f49209d0) < Math.abs(top3 - bottomSheetBehavior.f49211d2)) {
                        bottomSheetBehavior.getClass();
                    }
                }
                bottomSheetBehavior.getClass();
                bottomSheetBehavior.m210949d1(view, i, true);
                break;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f56119b1;
                jl0 jl0Var = sideSheetBehavior.f49781a0;
                SideSheetBehavior sideSheetBehavior2 = (SideSheetBehavior) jl0Var.f57345a0;
                int i3 = 3;
                if (f >= 0.0f) {
                    if (Math.abs((sideSheetBehavior2.f49791b0 * f) + view.getRight()) > 0.5f) {
                        if ((Math.abs(f) > Math.abs(f2) && f2 > 500) || view.getLeft() > (sideSheetBehavior2.f49793b2 - jl0Var.m213325a7()) / 2) {
                            i3 = 5;
                        }
                    } else if (f == 0.0f || Math.abs(f) <= Math.abs(f2)) {
                        int left = view.getLeft();
                        if (Math.abs(left - jl0Var.m213325a7()) >= Math.abs(left - sideSheetBehavior2.f49793b2)) {
                        }
                    }
                }
                sideSheetBehavior.m211102c0(view, i3, true);
                break;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0048  */
    @Override // p000.cq0
    /* renamed from: e1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean mo212516e1(View view, int i) {
        WeakReference weakReference;
        switch (this.f56118b0) {
            case 0:
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f56119b1;
                int i2 = bottomSheetBehavior.f49216d7;
                if (i2 != 1 && !bottomSheetBehavior.f49232f3) {
                    if (i2 == 3 && bottomSheetBehavior.f49230f1 == i) {
                        WeakReference weakReference2 = bottomSheetBehavior.f49227e8;
                        View view2 = weakReference2 != null ? (View) weakReference2.get() : null;
                        if (view2 == null || !view2.canScrollVertically(-1)) {
                        }
                    } else {
                        System.currentTimeMillis();
                        WeakReference weakReference3 = bottomSheetBehavior.f49225e6;
                        if (weakReference3 != null && weakReference3.get() == view) {
                            return true;
                        }
                    }
                }
                return false;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f56119b1;
                return (sideSheetBehavior.f49788a7 == 1 || (weakReference = sideSheetBehavior.f49795b4) == null || weakReference.get() != view) ? false : true;
        }
    }
}
