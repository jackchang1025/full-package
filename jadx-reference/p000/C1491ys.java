package p000;

import android.R;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ys */
/* loaded from: classes.dex */
public final class C1491ys extends mq0 {

    /* renamed from: c8 */
    public static final int[] f61367c8 = {R.attr.state_pressed};

    /* renamed from: c9 */
    public static final int[] f61368c9 = new int[0];

    /* renamed from: a0 */
    public final int f61369a0;

    /* renamed from: a1 */
    public final int f61370a1;

    /* renamed from: a2 */
    public final StateListDrawable f61371a2;

    /* renamed from: a3 */
    public final Drawable f61372a3;

    /* renamed from: a4 */
    public final int f61373a4;

    /* renamed from: a5 */
    public final int f61374a5;

    /* renamed from: a6 */
    public final StateListDrawable f61375a6;

    /* renamed from: a7 */
    public final Drawable f61376a7;

    /* renamed from: a8 */
    public final int f61377a8;

    /* renamed from: a9 */
    public final int f61378a9;

    /* renamed from: b0 */
    public int f61379b0;

    /* renamed from: b1 */
    public int f61380b1;

    /* renamed from: b2 */
    public float f61381b2;

    /* renamed from: b3 */
    public int f61382b3;

    /* renamed from: b4 */
    public int f61383b4;

    /* renamed from: b5 */
    public float f61384b5;

    /* renamed from: b8 */
    public final RecyclerView f61387b8;

    /* renamed from: c5 */
    public final ValueAnimator f61394c5;

    /* renamed from: c6 */
    public int f61395c6;

    /* renamed from: c7 */
    public final RunnableC0165ca f61396c7;

    /* renamed from: b6 */
    public int f61385b6 = 0;

    /* renamed from: b7 */
    public int f61386b7 = 0;

    /* renamed from: b9 */
    public boolean f61388b9 = false;

    /* renamed from: c0 */
    public boolean f61389c0 = false;

    /* renamed from: c1 */
    public int f61390c1 = 0;

    /* renamed from: c2 */
    public int f61391c2 = 0;

    /* renamed from: c3 */
    public final int[] f61392c3 = new int[2];

    /* renamed from: c4 */
    public final int[] f61393c4 = new int[2];

    public C1491ys(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i, int i2, int i3) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.f61394c5 = valueAnimatorOfFloat;
        this.f61395c6 = 0;
        RunnableC0165ca runnableC0165ca = new RunnableC0165ca(9, this);
        this.f61396c7 = runnableC0165ca;
        C1489yq c1489yq = new C1489yq(this);
        this.f61371a2 = stateListDrawable;
        this.f61372a3 = drawable;
        this.f61375a6 = stateListDrawable2;
        this.f61376a7 = drawable2;
        this.f61373a4 = Math.max(i, stateListDrawable.getIntrinsicWidth());
        this.f61374a5 = Math.max(i, drawable.getIntrinsicWidth());
        this.f61377a8 = Math.max(i, stateListDrawable2.getIntrinsicWidth());
        this.f61378a9 = Math.max(i, drawable2.getIntrinsicWidth());
        this.f61369a0 = i2;
        this.f61370a1 = i3;
        stateListDrawable.setAlpha(v10.MASK);
        drawable.setAlpha(v10.MASK);
        valueAnimatorOfFloat.addListener(new C1490yr(this));
        valueAnimatorOfFloat.addUpdateListener(new C0470ev(2, this));
        RecyclerView recyclerView2 = this.f61387b8;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            ArrayList arrayList = recyclerView2.f45266b2;
            pq0 pq0Var = recyclerView2.f45265b1;
            if (pq0Var != null) {
                pq0Var.mo210297a2("Cannot remove item decoration during a scroll  or layout");
            }
            arrayList.remove(this);
            if (arrayList.isEmpty()) {
                recyclerView2.setWillNotDraw(recyclerView2.getOverScrollMode() == 2);
            }
            recyclerView2.m210378e0();
            recyclerView2.requestLayout();
            RecyclerView recyclerView3 = this.f61387b8;
            recyclerView3.f45267b3.remove(this);
            if (recyclerView3.f45268b4 == this) {
                recyclerView3.f45268b4 = null;
            }
            ArrayList arrayList2 = this.f61387b8.f45308f4;
            if (arrayList2 != null) {
                arrayList2.remove(c1489yq);
            }
            this.f61387b8.removeCallbacks(runnableC0165ca);
        }
        this.f61387b8 = recyclerView;
        recyclerView.m210347a6(this);
        this.f61387b8.f45267b3.add(this);
        this.f61387b8.m210348a7(c1489yq);
    }

    /* renamed from: a4 */
    public static int m215306a4(float f, float f2, int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[1] - iArr[0];
        if (i4 != 0) {
            int i5 = i - i3;
            int i6 = (int) (((f2 - f) / i4) * i5);
            int i7 = i2 + i6;
            if (i7 < i5 && i7 >= 0) {
                return i6;
            }
        }
        return 0;
    }

    @Override // p000.mq0
    /* renamed from: a1 */
    public final void mo212957a1(Canvas canvas, RecyclerView recyclerView) {
        int i = this.f61385b6;
        RecyclerView recyclerView2 = this.f61387b8;
        if (i != recyclerView2.getWidth() || this.f61386b7 != recyclerView2.getHeight()) {
            this.f61385b6 = recyclerView2.getWidth();
            this.f61386b7 = recyclerView2.getHeight();
            m215309a5(0);
            return;
        }
        if (this.f61395c6 != 0) {
            if (this.f61388b9) {
                int i2 = this.f61385b6;
                int i3 = this.f61373a4;
                int i4 = i2 - i3;
                int i5 = this.f61380b1;
                int i6 = this.f61379b0;
                int i7 = i5 - (i6 / 2);
                StateListDrawable stateListDrawable = this.f61371a2;
                stateListDrawable.setBounds(0, 0, i3, i6);
                int i8 = this.f61374a5;
                int i9 = this.f61386b7;
                Drawable drawable = this.f61372a3;
                drawable.setBounds(0, 0, i8, i9);
                WeakHashMap weakHashMap = xa1.f61054a0;
                if (ga1.m212904a3(recyclerView2) == 1) {
                    drawable.draw(canvas);
                    canvas.translate(i3, i7);
                    canvas.scale(-1.0f, 1.0f);
                    stateListDrawable.draw(canvas);
                    canvas.scale(1.0f, 1.0f);
                    canvas.translate(-i3, -i7);
                } else {
                    canvas.translate(i4, 0.0f);
                    drawable.draw(canvas);
                    canvas.translate(0.0f, i7);
                    stateListDrawable.draw(canvas);
                    canvas.translate(-i4, -i7);
                }
            }
            if (this.f61389c0) {
                int i10 = this.f61386b7;
                int i11 = this.f61377a8;
                int i12 = i10 - i11;
                int i13 = this.f61383b4;
                int i14 = this.f61382b3;
                int i15 = i13 - (i14 / 2);
                StateListDrawable stateListDrawable2 = this.f61375a6;
                stateListDrawable2.setBounds(0, 0, i14, i11);
                int i16 = this.f61385b6;
                int i17 = this.f61378a9;
                Drawable drawable2 = this.f61376a7;
                drawable2.setBounds(0, 0, i16, i17);
                canvas.translate(0.0f, i12);
                drawable2.draw(canvas);
                canvas.translate(i15, 0.0f);
                stateListDrawable2.draw(canvas);
                canvas.translate(-i15, -i12);
            }
        }
    }

    /* renamed from: a2 */
    public final boolean m215307a2(float f, float f2) {
        if (f2 < this.f61386b7 - this.f61377a8) {
            return false;
        }
        int i = this.f61383b4;
        int i2 = this.f61382b3;
        return f >= ((float) (i - (i2 / 2))) && f <= ((float) ((i2 / 2) + i));
    }

    /* renamed from: a3 */
    public final boolean m215308a3(float f, float f2) {
        WeakHashMap weakHashMap = xa1.f61054a0;
        int iM212904a3 = ga1.m212904a3(this.f61387b8);
        int i = this.f61373a4;
        if (iM212904a3 == 1) {
            if (f > i / 2) {
                return false;
            }
        } else if (f < this.f61385b6 - i) {
            return false;
        }
        int i2 = this.f61380b1;
        int i3 = this.f61379b0 / 2;
        return f2 >= ((float) (i2 - i3)) && f2 <= ((float) (i3 + i2));
    }

    /* renamed from: a5 */
    public final void m215309a5(int i) {
        RunnableC0165ca runnableC0165ca = this.f61396c7;
        StateListDrawable stateListDrawable = this.f61371a2;
        if (i == 2 && this.f61390c1 != 2) {
            stateListDrawable.setState(f61367c8);
            this.f61387b8.removeCallbacks(runnableC0165ca);
        }
        if (i == 0) {
            this.f61387b8.invalidate();
        } else {
            m215310a6();
        }
        if (this.f61390c1 == 2 && i != 2) {
            stateListDrawable.setState(f61368c9);
            this.f61387b8.removeCallbacks(runnableC0165ca);
            this.f61387b8.postDelayed(runnableC0165ca, 1200);
        } else if (i == 1) {
            this.f61387b8.removeCallbacks(runnableC0165ca);
            this.f61387b8.postDelayed(runnableC0165ca, 1500);
        }
        this.f61390c1 = i;
    }

    /* renamed from: a6 */
    public final void m215310a6() {
        int i = this.f61395c6;
        ValueAnimator valueAnimator = this.f61394c5;
        if (i != 0) {
            if (i != 3) {
                return;
            } else {
                valueAnimator.cancel();
            }
        }
        this.f61395c6 = 1;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        valueAnimator.setDuration(500L);
        valueAnimator.setStartDelay(0L);
        valueAnimator.start();
    }
}
