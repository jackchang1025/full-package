package p000;

import android.view.View;
import androidx.drawerlayout.widget.DrawerLayout;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ue */
/* loaded from: classes.dex */
public final class C1297ue extends cq0 {

    /* renamed from: b0 */
    public final int f60413b0;

    /* renamed from: b1 */
    public bb1 f60414b1;

    /* renamed from: b2 */
    public final RunnableC0165ca f60415b2 = new RunnableC0165ca(7, this);

    /* renamed from: b3 */
    public final /* synthetic */ DrawerLayout f60416b3;

    public C1297ue(DrawerLayout drawerLayout, int i) {
        this.f60416b3 = drawerLayout;
        this.f60413b0 = i;
    }

    @Override // p000.cq0
    /* renamed from: a5 */
    public final int mo212501a5(View view, int i) {
        DrawerLayout drawerLayout = this.f60416b3;
        if (drawerLayout.m210104a0(view, 3)) {
            return Math.max(-view.getWidth(), Math.min(i, 0));
        }
        int width = drawerLayout.getWidth();
        return Math.max(width - view.getWidth(), Math.min(i, width));
    }

    @Override // p000.cq0
    /* renamed from: a6 */
    public final int mo212502a6(View view, int i) {
        return view.getTop();
    }

    @Override // p000.cq0
    /* renamed from: b6 */
    public final int mo212503b6(View view) {
        if (DrawerLayout.m210103b0(view)) {
            return view.getWidth();
        }
        return 0;
    }

    @Override // p000.cq0
    /* renamed from: c3 */
    public final void mo212505c3(int i, int i2) {
        int i3 = i & 1;
        DrawerLayout drawerLayout = this.f60416b3;
        View viewM210107a3 = i3 == 1 ? drawerLayout.m210107a3(3) : drawerLayout.m210107a3(5);
        if (viewM210107a3 == null || drawerLayout.m210109a5(viewM210107a3) != 0) {
            return;
        }
        this.f60414b1.m210632a1(viewM210107a3, i2);
    }

    @Override // p000.cq0
    /* renamed from: c4 */
    public final void mo212506c4() {
        this.f60416b3.postDelayed(this.f60415b2, 160L);
    }

    @Override // p000.cq0
    /* renamed from: d0 */
    public final void mo212512d0(View view, int i) {
        ((C1296ud) view.getLayoutParams()).f60380a2 = false;
        int i2 = this.f60413b0 == 3 ? 5 : 3;
        DrawerLayout drawerLayout = this.f60416b3;
        View viewM210107a3 = drawerLayout.m210107a3(i2);
        if (viewM210107a3 != null) {
            drawerLayout.m210105a1(viewM210107a3);
        }
    }

    @Override // p000.cq0
    /* renamed from: d1 */
    public final void mo212513d1(int i) {
        int i2;
        int size;
        int size2;
        View rootView;
        int size3;
        View view = this.f60414b1.f45801b9;
        DrawerLayout drawerLayout = this.f60416b3;
        int i3 = drawerLayout.f44973a6.f45782a0;
        int i4 = drawerLayout.f44974a7.f45782a0;
        if (i3 == 1 || i4 == 1) {
            i2 = 1;
        } else {
            i2 = 2;
            if (i3 != 2 && i4 != 2) {
                i2 = 0;
            }
        }
        if (view != null && i == 0) {
            float f = ((C1296ud) view.getLayoutParams()).f60379a1;
            if (f == 0.0f) {
                C1296ud c1296ud = (C1296ud) view.getLayoutParams();
                if ((c1296ud.f60381a3 & 1) == 1) {
                    c1296ud.f60381a3 = 0;
                    ArrayList arrayList = drawerLayout.f44985b8;
                    if (arrayList != null && (size3 = arrayList.size() - 1) >= 0) {
                        drawerLayout.f44985b8.get(size3).getClass();
                        throw new ClassCastException();
                    }
                    drawerLayout.m210115b5(view, false);
                    drawerLayout.m210114b4(view);
                    if (drawerLayout.hasWindowFocus() && (rootView = drawerLayout.getRootView()) != null) {
                        rootView.sendAccessibilityEvent(32);
                    }
                }
            } else if (f == 1.0f) {
                C1296ud c1296ud2 = (C1296ud) view.getLayoutParams();
                if ((c1296ud2.f60381a3 & 1) == 0) {
                    c1296ud2.f60381a3 = 1;
                    ArrayList arrayList2 = drawerLayout.f44985b8;
                    if (arrayList2 != null && (size2 = arrayList2.size() - 1) >= 0) {
                        drawerLayout.f44985b8.get(size2).getClass();
                        throw new ClassCastException();
                    }
                    drawerLayout.m210115b5(view, true);
                    drawerLayout.m210114b4(view);
                    if (drawerLayout.hasWindowFocus()) {
                        drawerLayout.sendAccessibilityEvent(32);
                    }
                }
            }
        }
        if (i2 != drawerLayout.f44977b0) {
            drawerLayout.f44977b0 = i2;
            ArrayList arrayList3 = drawerLayout.f44985b8;
            if (arrayList3 == null || (size = arrayList3.size() - 1) < 0) {
                return;
            }
            drawerLayout.f44985b8.get(size).getClass();
            throw new ClassCastException();
        }
    }

    @Override // p000.cq0
    /* renamed from: d2 */
    public final void mo212514d2(View view, int i, int i2) {
        int width = view.getWidth();
        DrawerLayout drawerLayout = this.f60416b3;
        float width2 = (drawerLayout.m210104a0(view, 3) ? i + width : drawerLayout.getWidth() - i) / width;
        drawerLayout.m210113b3(view, width2);
        view.setVisibility(width2 == 0.0f ? 4 : 0);
        drawerLayout.invalidate();
    }

    @Override // p000.cq0
    /* renamed from: d3 */
    public final void mo212515d3(View view, float f, float f2) {
        int i;
        int[] iArr = DrawerLayout.f44962c8;
        float f3 = ((C1296ud) view.getLayoutParams()).f60379a1;
        int width = view.getWidth();
        DrawerLayout drawerLayout = this.f60416b3;
        if (drawerLayout.m210104a0(view, 3)) {
            i = (f > 0.0f || (f == 0.0f && f3 > 0.5f)) ? 0 : -width;
        } else {
            int width2 = drawerLayout.getWidth();
            if (f < 0.0f || (f == 0.0f && f3 > 0.5f)) {
                width2 -= width;
            }
            i = width2;
        }
        this.f60414b1.m210646b5(i, view.getTop());
        drawerLayout.invalidate();
    }

    @Override // p000.cq0
    /* renamed from: e1 */
    public final boolean mo212516e1(View view, int i) {
        if (!DrawerLayout.m210103b0(view)) {
            return false;
        }
        int i2 = this.f60413b0;
        DrawerLayout drawerLayout = this.f60416b3;
        return drawerLayout.m210104a0(view, i2) && drawerLayout.m210109a5(view) == 0;
    }
}
