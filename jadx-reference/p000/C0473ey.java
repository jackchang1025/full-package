package p000;

import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.sidesheet.SideSheetBehavior;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ey */
/* loaded from: classes2.dex */
public final class C0473ey {

    /* renamed from: a0 */
    public final /* synthetic */ int f56120a0;

    /* renamed from: a1 */
    public int f56121a1;

    /* renamed from: a2 */
    public boolean f56122a2;

    /* renamed from: a3 */
    public final Runnable f56123a3;

    /* renamed from: a4 */
    public final /* synthetic */ AbstractC0879my f56124a4;

    public C0473ey(SideSheetBehavior sideSheetBehavior) {
        this.f56120a0 = 1;
        this.f56124a4 = sideSheetBehavior;
        this.f56123a3 = new RunnableC0941o6(19, this);
    }

    /* renamed from: a0 */
    public final void m212726a0(int i) {
        int i2 = this.f56120a0;
        Runnable runnable = this.f56123a3;
        AbstractC0879my abstractC0879my = this.f56124a4;
        switch (i2) {
            case 0:
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) abstractC0879my;
                WeakReference weakReference = bottomSheetBehavior.f49225e6;
                if (weakReference != null && weakReference.get() != null) {
                    this.f56121a1 = i;
                    if (!this.f56122a2) {
                        WeakHashMap weakHashMap = xa1.f61054a0;
                        fa1.m212775b2((View) bottomSheetBehavior.f49225e6.get(), (RunnableC0165ca) runnable);
                        this.f56122a2 = true;
                        break;
                    }
                }
                break;
            default:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) abstractC0879my;
                WeakReference weakReference2 = sideSheetBehavior.f49795b4;
                if (weakReference2 != null && weakReference2.get() != null) {
                    this.f56121a1 = i;
                    if (!this.f56122a2) {
                        WeakHashMap weakHashMap2 = xa1.f61054a0;
                        fa1.m212775b2((View) sideSheetBehavior.f49795b4.get(), (RunnableC0941o6) runnable);
                        this.f56122a2 = true;
                        break;
                    }
                }
                break;
        }
    }

    public C0473ey(BottomSheetBehavior bottomSheetBehavior) {
        this.f56120a0 = 0;
        this.f56124a4 = bottomSheetBehavior;
        this.f56123a3 = new RunnableC0165ca(2, this);
    }
}
