package p000;

import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class cr0 implements Runnable {

    /* renamed from: a0 */
    public int f55476a0;

    /* renamed from: a1 */
    public int f55477a1;

    /* renamed from: a2 */
    public OverScroller f55478a2;

    /* renamed from: a3 */
    public Interpolator f55479a3;

    /* renamed from: a4 */
    public boolean f55480a4;

    /* renamed from: a5 */
    public boolean f55481a5;

    /* renamed from: a6 */
    public final /* synthetic */ RecyclerView f55482a6;

    public cr0(RecyclerView recyclerView) {
        this.f55482a6 = recyclerView;
        eq0 eq0Var = RecyclerView.f45253h0;
        this.f55479a3 = eq0Var;
        this.f55480a4 = false;
        this.f55481a5 = false;
        this.f55478a2 = new OverScroller(recyclerView.getContext(), eq0Var);
    }

    /* renamed from: a0 */
    public final void m212517a0() {
        if (this.f55480a4) {
            this.f55481a5 = true;
            return;
        }
        RecyclerView recyclerView = this.f55482a6;
        recyclerView.removeCallbacks(this);
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212775b2(recyclerView, this);
    }

    /* renamed from: a1 */
    public final void m212518a1(int i, int i2, int i3, Interpolator interpolator) {
        int iRound;
        RecyclerView recyclerView = this.f55482a6;
        if (i3 == Integer.MIN_VALUE) {
            int iAbs = Math.abs(i);
            int iAbs2 = Math.abs(i2);
            boolean z = iAbs > iAbs2;
            int iSqrt = (int) Math.sqrt(0);
            int iSqrt2 = (int) Math.sqrt((i2 * i2) + (i * i));
            int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
            int i4 = width / 2;
            float f = width;
            float f2 = i4;
            float fSin = (((float) Math.sin((Math.min(1.0f, (iSqrt2 * 1.0f) / f) - 0.5f) * 0.47123894f)) * f2) + f2;
            if (iSqrt > 0) {
                iRound = Math.round(Math.abs(fSin / iSqrt) * 1000.0f) * 4;
            } else {
                if (!z) {
                    iAbs = iAbs2;
                }
                iRound = (int) (((iAbs / f) + 1.0f) * 300.0f);
            }
            i3 = Math.min(iRound, 2000);
        }
        int i5 = i3;
        if (interpolator == null) {
            interpolator = RecyclerView.f45253h0;
        }
        if (this.f55479a3 != interpolator) {
            this.f55479a3 = interpolator;
            this.f55478a2 = new OverScroller(recyclerView.getContext(), interpolator);
        }
        this.f55477a1 = 0;
        this.f55476a0 = 0;
        recyclerView.setScrollState(2);
        this.f55478a2.startScroll(0, 0, i, i2, i5);
        m212517a0();
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        RecyclerView recyclerView = this.f55482a6;
        int[] iArr = recyclerView.f45318g4;
        if (recyclerView.f45265b1 == null) {
            recyclerView.removeCallbacks(this);
            this.f55478a2.abortAnimation();
            return;
        }
        this.f55481a5 = false;
        this.f55480a4 = true;
        recyclerView.m210352b2();
        OverScroller overScroller = this.f55478a2;
        if (overScroller.computeScrollOffset()) {
            int currX = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            int i6 = currX - this.f55476a0;
            int i7 = currY - this.f55477a1;
            this.f55476a0 = currX;
            this.f55477a1 = currY;
            int[] iArr2 = recyclerView.f45318g4;
            iArr2[0] = 0;
            iArr2[1] = 0;
            int i8 = i6;
            if (recyclerView.m210358b8(i8, iArr2, i7, 1, null)) {
                i8 -= iArr[0];
                i = i7 - iArr[1];
            } else {
                i = i7;
            }
            int i9 = i8;
            if (recyclerView.getOverScrollMode() != 2) {
                recyclerView.m210351b1(i9, i);
            }
            if (recyclerView.f45264b0 != null) {
                iArr[0] = 0;
                iArr[1] = 0;
                recyclerView.m210389f1(i9, i, iArr);
                i3 = iArr[0];
                int i10 = iArr[1];
                int i11 = i9 - i3;
                int i12 = i - i10;
                za0 za0Var = recyclerView.f45265b1.f59322a4;
                if (za0Var != null && !za0Var.f61475a3 && za0Var.f61476a4) {
                    int iM210500a1 = recyclerView.f45306f2.m210500a1();
                    if (iM210500a1 == 0) {
                        za0Var.m215384a7();
                    } else if (za0Var.f61472a0 >= iM210500a1) {
                        za0Var.f61472a0 = iM210500a1 - 1;
                        za0Var.m215383a5(i3, i10);
                    } else {
                        za0Var.m215383a5(i3, i10);
                    }
                }
                i2 = i12;
                i4 = i11;
                i5 = i10;
            } else {
                i2 = i;
                i3 = 0;
                i4 = i9;
                i5 = 0;
            }
            if (!recyclerView.f45266b2.isEmpty()) {
                recyclerView.invalidate();
            }
            int[] iArr3 = recyclerView.f45318g4;
            iArr3[0] = 0;
            iArr3[1] = 0;
            recyclerView.m210359b9(i3, null, i5, i4, i2, iArr3, 1);
            int i13 = i4 - iArr[0];
            int i14 = i2 - iArr[1];
            if (i3 != 0 || i5 != 0) {
                recyclerView.m210360c0(i3, i5);
            }
            if (!recyclerView.awakenScrollBars()) {
                recyclerView.invalidate();
            }
            boolean z = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i13 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i14 != 0));
            za0 za0Var2 = recyclerView.f45265b1.f59322a4;
            if ((za0Var2 == null || !za0Var2.f61475a3) && z) {
                if (recyclerView.getOverScrollMode() != 2) {
                    int currVelocity = (int) overScroller.getCurrVelocity();
                    int i15 = i13 < 0 ? -currVelocity : i13 > 0 ? currVelocity : 0;
                    if (i14 < 0) {
                        currVelocity = -currVelocity;
                    } else if (i14 <= 0) {
                        currVelocity = 0;
                    }
                    if (i15 < 0) {
                        recyclerView.m210362c2();
                        if (recyclerView.f45284d0.isFinished()) {
                            recyclerView.f45284d0.onAbsorb(-i15);
                        }
                    } else if (i15 > 0) {
                        recyclerView.m210363c3();
                        if (recyclerView.f45286d2.isFinished()) {
                            recyclerView.f45286d2.onAbsorb(i15);
                        }
                    }
                    if (currVelocity < 0) {
                        recyclerView.m210364c4();
                        if (recyclerView.f45285d1.isFinished()) {
                            recyclerView.f45285d1.onAbsorb(-currVelocity);
                        }
                    } else if (currVelocity > 0) {
                        recyclerView.m210361c1();
                        if (recyclerView.f45287d3.isFinished()) {
                            recyclerView.f45287d3.onAbsorb(currVelocity);
                        }
                    }
                    if (i15 != 0 || currVelocity != 0) {
                        WeakHashMap weakHashMap = xa1.f61054a0;
                        fa1.m212773b0(recyclerView);
                    }
                }
                m20 m20Var = recyclerView.f45305f1;
                int[] iArr4 = m20Var.f58245a2;
                if (iArr4 != null) {
                    Arrays.fill(iArr4, -1);
                }
                m20Var.f58246a3 = 0;
            } else {
                m212517a0();
                o20 o20Var = recyclerView.f45304f0;
                if (o20Var != null) {
                    o20Var.m214150a0(recyclerView, i3, i5);
                }
            }
        }
        za0 za0Var3 = recyclerView.f45265b1.f59322a4;
        if (za0Var3 != null && za0Var3.f61475a3) {
            za0Var3.m215383a5(0, 0);
        }
        this.f55480a4 = false;
        if (!this.f55481a5) {
            recyclerView.setScrollState(0);
            recyclerView.m210394f6(1);
        } else {
            recyclerView.removeCallbacks(this);
            WeakHashMap weakHashMap2 = xa1.f61054a0;
            fa1.m212775b2(recyclerView, this);
        }
    }
}
