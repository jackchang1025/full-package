package p000;

import android.view.View;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class s11 {

    /* renamed from: a0 */
    public final ArrayList f59847a0 = new ArrayList();

    /* renamed from: a1 */
    public int f59848a1 = Integer.MIN_VALUE;

    /* renamed from: a2 */
    public int f59849a2 = Integer.MIN_VALUE;

    /* renamed from: a3 */
    public int f59850a3 = 0;

    /* renamed from: a4 */
    public final int f59851a4;

    /* renamed from: a5 */
    public final /* synthetic */ StaggeredGridLayoutManager f59852a5;

    public s11(StaggeredGridLayoutManager staggeredGridLayoutManager, int i) {
        this.f59852a5 = staggeredGridLayoutManager;
        this.f59851a4 = i;
    }

    /* renamed from: a0 */
    public final void m214554a0() {
        View view = (View) this.f59847a0.get(r0.size() - 1);
        r11 r11Var = (r11) view.getLayoutParams();
        this.f59849a2 = this.f59852a5.f45325b7.mo214621a1(view);
        r11Var.getClass();
    }

    /* renamed from: a1 */
    public final void m214555a1() {
        this.f59847a0.clear();
        this.f59848a1 = Integer.MIN_VALUE;
        this.f59849a2 = Integer.MIN_VALUE;
        this.f59850a3 = 0;
    }

    /* renamed from: a2 */
    public final int m214556a2() {
        return this.f59852a5.f45330c2 ? m214558a4(r1.size() - 1, -1) : m214558a4(0, this.f59847a0.size());
    }

    /* renamed from: a3 */
    public final int m214557a3() {
        return this.f59852a5.f45330c2 ? m214558a4(0, this.f59847a0.size()) : m214558a4(r1.size() - 1, -1);
    }

    /* renamed from: a4 */
    public final int m214558a4(int i, int i2) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = this.f59852a5;
        int iMo214630b0 = staggeredGridLayoutManager.f45325b7.mo214630b0();
        int iMo214626a6 = staggeredGridLayoutManager.f45325b7.mo214626a6();
        int i3 = i2 > i ? 1 : -1;
        while (i != i2) {
            View view = (View) this.f59847a0.get(i);
            int iMo214624a4 = staggeredGridLayoutManager.f45325b7.mo214624a4(view);
            int iMo214621a1 = staggeredGridLayoutManager.f45325b7.mo214621a1(view);
            boolean z = iMo214624a4 <= iMo214626a6;
            boolean z2 = iMo214621a1 >= iMo214630b0;
            if (z && z2 && (iMo214624a4 < iMo214630b0 || iMo214621a1 > iMo214626a6)) {
                return pq0.m214304d0(view);
            }
            i += i3;
        }
        return -1;
    }

    /* renamed from: a5 */
    public final int m214559a5(int i) {
        int i2 = this.f59849a2;
        if (i2 != Integer.MIN_VALUE) {
            return i2;
        }
        if (this.f59847a0.size() == 0) {
            return i;
        }
        m214554a0();
        return this.f59849a2;
    }

    /* renamed from: a6 */
    public final View m214560a6(int i, int i2) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = this.f59852a5;
        ArrayList arrayList = this.f59847a0;
        View view = null;
        if (i2 != -1) {
            int size = arrayList.size() - 1;
            while (size >= 0) {
                View view2 = (View) arrayList.get(size);
                if ((staggeredGridLayoutManager.f45330c2 && pq0.m214304d0(view2) >= i) || ((!staggeredGridLayoutManager.f45330c2 && pq0.m214304d0(view2) <= i) || !view2.hasFocusable())) {
                    break;
                }
                size--;
                view = view2;
            }
            return view;
        }
        int size2 = arrayList.size();
        int i3 = 0;
        while (i3 < size2) {
            View view3 = (View) arrayList.get(i3);
            if ((staggeredGridLayoutManager.f45330c2 && pq0.m214304d0(view3) <= i) || ((!staggeredGridLayoutManager.f45330c2 && pq0.m214304d0(view3) >= i) || !view3.hasFocusable())) {
                break;
            }
            i3++;
            view = view3;
        }
        return view;
    }

    /* renamed from: a7 */
    public final int m214561a7(int i) {
        int i2 = this.f59848a1;
        if (i2 != Integer.MIN_VALUE) {
            return i2;
        }
        ArrayList arrayList = this.f59847a0;
        if (arrayList.size() == 0) {
            return i;
        }
        View view = (View) arrayList.get(0);
        r11 r11Var = (r11) view.getLayoutParams();
        this.f59848a1 = this.f59852a5.f45325b7.mo214624a4(view);
        r11Var.getClass();
        return this.f59848a1;
    }
}
