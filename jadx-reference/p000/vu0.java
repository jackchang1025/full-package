package p000;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class vu0 extends sq0 {

    /* renamed from: a0 */
    public bc1 f60688a0;

    /* renamed from: a1 */
    public final ViewPager2 f60689a1;

    /* renamed from: a2 */
    public final ic1 f60690a2;

    /* renamed from: a3 */
    public final LinearLayoutManager f60691a3;

    /* renamed from: a4 */
    public int f60692a4;

    /* renamed from: a5 */
    public int f60693a5;

    /* renamed from: a6 */
    public final uu0 f60694a6;

    /* renamed from: a7 */
    public int f60695a7;

    /* renamed from: a8 */
    public int f60696a8;

    /* renamed from: a9 */
    public boolean f60697a9;

    /* renamed from: b0 */
    public boolean f60698b0;

    /* renamed from: b1 */
    public boolean f60699b1;

    public vu0(ViewPager2 viewPager2) {
        this.f60689a1 = viewPager2;
        ic1 ic1Var = viewPager2.f45481a9;
        this.f60690a2 = ic1Var;
        this.f60691a3 = (LinearLayoutManager) ic1Var.getLayoutManager();
        this.f60694a6 = new uu0();
        m214959a3();
    }

    @Override // p000.sq0
    /* renamed from: a0 */
    public final void mo211019a0(RecyclerView recyclerView, int i) {
        bc1 bc1Var;
        bc1 bc1Var2;
        int i2 = this.f60692a4;
        if (!(i2 == 1 && this.f60693a5 == 1) && i == 1) {
            this.f60692a4 = 1;
            int i3 = this.f60696a8;
            if (i3 != -1) {
                this.f60695a7 = i3;
                this.f60696a8 = -1;
            } else if (this.f60695a7 == -1) {
                this.f60695a7 = this.f60691a3.m210322i6();
            }
            m214958a2(1);
            return;
        }
        if ((i2 == 1 || i2 == 4) && i == 2) {
            if (this.f60698b0) {
                m214958a2(2);
                this.f60697a9 = true;
                return;
            }
            return;
        }
        uu0 uu0Var = this.f60694a6;
        if ((i2 == 1 || i2 == 4) && i == 0) {
            m214960a4();
            if (!this.f60698b0) {
                int i4 = uu0Var.f60518a0;
                if (i4 != -1 && (bc1Var2 = this.f60688a0) != null) {
                    bc1Var2.mo210662a1(i4, 0.0f, 0);
                }
            } else if (uu0Var.f60520a2 == 0) {
                int i5 = this.f60695a7;
                int i6 = uu0Var.f60518a0;
                if (i5 != i6 && (bc1Var = this.f60688a0) != null) {
                    bc1Var.mo210663a2(i6);
                }
            }
            m214958a2(0);
            m214959a3();
        }
        if (this.f60692a4 == 2 && i == 0 && this.f60699b1) {
            m214960a4();
            if (uu0Var.f60520a2 == 0) {
                int i7 = this.f60696a8;
                int i8 = uu0Var.f60518a0;
                if (i7 != i8) {
                    if (i8 == -1) {
                        i8 = 0;
                    }
                    bc1 bc1Var3 = this.f60688a0;
                    if (bc1Var3 != null) {
                        bc1Var3.mo210663a2(i8);
                    }
                }
                m214958a2(0);
                m214959a3();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0028  */
    @Override // p000.sq0
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo211020a1(RecyclerView recyclerView, int i, int i2) {
        int i3;
        bc1 bc1Var;
        this.f60698b0 = true;
        m214960a4();
        boolean z = this.f60697a9;
        uu0 uu0Var = this.f60694a6;
        if (z) {
            this.f60697a9 = false;
            if (i2 > 0) {
                i3 = uu0Var.f60520a2 != 0 ? uu0Var.f60518a0 + 1 : uu0Var.f60518a0;
                this.f60696a8 = i3;
                if (this.f60695a7 != i3 && (bc1Var = this.f60688a0) != null) {
                    bc1Var.mo210663a2(i3);
                }
            } else {
                if (i2 == 0) {
                    if ((i < 0) == (this.f60689a1.f45478a6.m214312c5() == 1)) {
                    }
                }
                this.f60696a8 = i3;
                if (this.f60695a7 != i3) {
                    bc1Var.mo210663a2(i3);
                }
            }
        } else if (this.f60692a4 == 0) {
            int i4 = uu0Var.f60518a0;
            if (i4 == -1) {
                i4 = 0;
            }
            bc1 bc1Var2 = this.f60688a0;
            if (bc1Var2 != null) {
                bc1Var2.mo210663a2(i4);
            }
        }
        int i5 = uu0Var.f60518a0;
        if (i5 == -1) {
            i5 = 0;
        }
        float f = uu0Var.f60519a1;
        int i6 = uu0Var.f60520a2;
        bc1 bc1Var3 = this.f60688a0;
        if (bc1Var3 != null) {
            bc1Var3.mo210662a1(i5, f, i6);
        }
        int i7 = uu0Var.f60518a0;
        int i8 = this.f60696a8;
        if ((i7 == i8 || i8 == -1) && uu0Var.f60520a2 == 0 && this.f60693a5 != 1) {
            m214958a2(0);
            m214959a3();
        }
    }

    /* renamed from: a2 */
    public final void m214958a2(int i) {
        if ((this.f60692a4 == 3 && this.f60693a5 == 0) || this.f60693a5 == i) {
            return;
        }
        this.f60693a5 = i;
        bc1 bc1Var = this.f60688a0;
        if (bc1Var != null) {
            bc1Var.mo210661a0(i);
        }
    }

    /* renamed from: a3 */
    public final void m214959a3() {
        this.f60692a4 = 0;
        this.f60693a5 = 0;
        uu0 uu0Var = this.f60694a6;
        uu0Var.f60518a0 = -1;
        uu0Var.f60519a1 = 0.0f;
        uu0Var.f60520a2 = 0;
        this.f60695a7 = -1;
        this.f60696a8 = -1;
        this.f60697a9 = false;
        this.f60698b0 = false;
        this.f60699b1 = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x013e  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214960a4() {
        int top;
        int iM214311c1;
        int top2;
        int i;
        int bottom;
        int i2;
        LinearLayoutManager linearLayoutManager = this.f60691a3;
        int iM210322i6 = linearLayoutManager.m210322i6();
        uu0 uu0Var = this.f60694a6;
        uu0Var.f60518a0 = iM210322i6;
        if (iM210322i6 == -1) {
            uu0Var.f60518a0 = -1;
            uu0Var.f60519a1 = 0.0f;
            uu0Var.f60520a2 = 0;
            return;
        }
        View viewMo210304b6 = linearLayoutManager.mo210304b6(iM210322i6);
        if (viewMo210304b6 == null) {
            uu0Var.f60518a0 = -1;
            uu0Var.f60519a1 = 0.0f;
            uu0Var.f60520a2 = 0;
            return;
        }
        int i3 = ((qq0) viewMo210304b6.getLayoutParams()).f59545a1.left;
        int i4 = ((qq0) viewMo210304b6.getLayoutParams()).f59545a1.right;
        int i5 = ((qq0) viewMo210304b6.getLayoutParams()).f59545a1.top;
        int i6 = ((qq0) viewMo210304b6.getLayoutParams()).f59545a1.bottom;
        ViewGroup.LayoutParams layoutParams = viewMo210304b6.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            i3 += marginLayoutParams.leftMargin;
            i4 += marginLayoutParams.rightMargin;
            i5 += marginLayoutParams.topMargin;
            i6 += marginLayoutParams.bottomMargin;
        }
        int height = viewMo210304b6.getHeight() + i5 + i6;
        int width = viewMo210304b6.getWidth() + i3 + i4;
        int i7 = linearLayoutManager.f45233b5;
        ic1 ic1Var = this.f60690a2;
        if (i7 == 0) {
            top = (viewMo210304b6.getLeft() - i3) - ic1Var.getPaddingLeft();
            if (this.f60689a1.f45478a6.m214312c5() == 1) {
                top = -top;
            }
            height = width;
        } else {
            top = (viewMo210304b6.getTop() - i5) - ic1Var.getPaddingTop();
        }
        int i8 = -top;
        uu0Var.f60520a2 = i8;
        if (i8 >= 0) {
            uu0Var.f60519a1 = height != 0 ? i8 / height : 0.0f;
            return;
        }
        int iM214311c12 = linearLayoutManager.m214311c1();
        if (iM214311c12 != 0) {
            boolean z = linearLayoutManager.f45233b5 == 0;
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, iM214311c12, 2);
            for (int i9 = 0; i9 < iM214311c12; i9++) {
                View viewM214310c0 = linearLayoutManager.m214310c0(i9);
                if (viewM214310c0 == null) {
                    throw new IllegalStateException("null view contained in the view hierarchy");
                }
                ViewGroup.LayoutParams layoutParams2 = viewM214310c0.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : C1242t0.f60108a0;
                int[] iArr2 = iArr[i9];
                if (z) {
                    top2 = viewM214310c0.getLeft();
                    i = marginLayoutParams2.leftMargin;
                } else {
                    top2 = viewM214310c0.getTop();
                    i = marginLayoutParams2.topMargin;
                }
                iArr2[0] = top2 - i;
                int[] iArr3 = iArr[i9];
                if (z) {
                    bottom = viewM214310c0.getRight();
                    i2 = marginLayoutParams2.rightMargin;
                } else {
                    bottom = viewM214310c0.getBottom();
                    i2 = marginLayoutParams2.bottomMargin;
                }
                iArr3[1] = bottom + i2;
            }
            Arrays.sort(iArr, new C1214s9(0));
            int i10 = 1;
            while (true) {
                if (i10 >= iM214311c12) {
                    int[] iArr4 = iArr[0];
                    int i11 = iArr4[1];
                    int i12 = iArr4[0];
                    int i13 = i11 - i12;
                    if (i12 > 0 || iArr[iM214311c12 - 1][1] < i13) {
                        break;
                    }
                } else if (iArr[i10 - 1][1] != iArr[i10][0]) {
                    break;
                } else {
                    i10++;
                }
            }
            iM214311c1 = linearLayoutManager.m214311c1();
            for (int i14 = 0; i14 < iM214311c1; i14++) {
                if (C1242t0.m214671a0(linearLayoutManager.m214310c0(i14))) {
                    throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
                }
            }
        } else if (linearLayoutManager.m214311c1() <= 1) {
            iM214311c1 = linearLayoutManager.m214311c1();
            while (i14 < iM214311c1) {
            }
        }
        Locale locale = Locale.US;
        throw new IllegalStateException(tz0.m214802a2(uu0Var.f60520a2, "Page can only be offset by a positive amount, not by "));
    }
}
