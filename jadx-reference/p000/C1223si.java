package p000;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.provider.Settings;
import android.util.AndroidRuntimeException;
import android.view.Choreographer;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: si */
/* loaded from: classes2.dex */
public final class C1223si extends AbstractC1277tx {

    /* renamed from: b6 */
    public static final C1222sh f59988b6 = new C1222sh();

    /* renamed from: b1 */
    public final AbstractC1298uf f59989b1;

    /* renamed from: b2 */
    public final n11 f59990b2;

    /* renamed from: b3 */
    public final m11 f59991b3;

    /* renamed from: b4 */
    public float f59992b4;

    /* renamed from: b5 */
    public boolean f59993b5;

    public C1223si(Context context, AbstractC0411dd abstractC0411dd, AbstractC1298uf abstractC1298uf) {
        super(context, abstractC0411dd);
        this.f59993b5 = false;
        this.f59989b1 = abstractC1298uf;
        abstractC1298uf.f60421a1 = this;
        n11 n11Var = new n11();
        this.f59990b2 = n11Var;
        n11Var.f58420a1 = 1.0f;
        n11Var.f58421a2 = false;
        n11Var.f58419a0 = Math.sqrt(50.0f);
        n11Var.f58421a2 = false;
        m11 m11Var = new m11(this);
        this.f59991b3 = m11Var;
        m11Var.f58238b0 = n11Var;
        if (this.f60298a7 != 1.0f) {
            this.f60298a7 = 1.0f;
            invalidateSelf();
        }
    }

    @Override // p000.AbstractC1277tx
    /* renamed from: a5 */
    public final boolean mo214032a5(boolean z, boolean z2, boolean z3) {
        boolean zMo214032a5 = super.mo214032a5(z, z2, z3);
        C1250t8 c1250t8 = this.f60293a2;
        ContentResolver contentResolver = this.f60291a0.getContentResolver();
        c1250t8.getClass();
        float f = Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f);
        if (f == 0.0f) {
            this.f59993b5 = true;
            return zMo214032a5;
        }
        this.f59993b5 = false;
        float f2 = 50.0f / f;
        n11 n11Var = this.f59990b2;
        n11Var.getClass();
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException("Spring stiffness constant must be positive.");
        }
        n11Var.f58419a0 = Math.sqrt(f2);
        n11Var.f58421a2 = false;
        return zMo214032a5;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect rect = new Rect();
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(rect)) {
            canvas.save();
            Rect bounds = getBounds();
            float fM214794a1 = m214794a1();
            AbstractC1298uf abstractC1298uf = this.f59989b1;
            abstractC1298uf.f60420a0.mo211082a0();
            abstractC1298uf.mo213159a0(canvas, bounds, fM214794a1);
            AbstractC1298uf abstractC1298uf2 = this.f59989b1;
            Paint paint = this.f60299a8;
            abstractC1298uf2.mo213161a2(canvas, paint);
            int iM213561a8 = kj1.m213561a8(this.f60292a1.f55695a2[0], this.f60300a9);
            this.f59989b1.mo213160a1(canvas, paint, 0.0f, this.f59992b4, iM213561a8);
            canvas.restore();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.f59989b1.mo213162a3();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.f59989b1.mo213163a4();
    }

    @Override // android.graphics.drawable.Drawable
    public final void jumpToCurrentState() {
        this.f59991b3.m213931a1();
        this.f59992b4 = getLevel() / 10000.0f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        boolean z = this.f59993b5;
        m11 m11Var = this.f59991b3;
        if (z) {
            m11Var.m213931a1();
            this.f59992b4 = i / 10000.0f;
            invalidateSelf();
            return true;
        }
        m11Var.f58229a1 = this.f59992b4 * 10000.0f;
        m11Var.f58230a2 = true;
        float f = i;
        if (m11Var.f58233a5) {
            m11Var.f58239b1 = f;
            return true;
        }
        if (m11Var.f58238b0 == null) {
            m11Var.f58238b0 = new n11(f);
        }
        n11 n11Var = m11Var.f58238b0;
        double d = f;
        n11Var.f58427a8 = d;
        double d2 = (float) d;
        if (d2 > Float.MAX_VALUE) {
            throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
        }
        if (d2 < -3.4028235E38f) {
            throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
        }
        double dAbs = Math.abs(m11Var.f58235a7 * 0.75f);
        n11Var.f58422a3 = dAbs;
        n11Var.f58423a4 = dAbs * 62.5d;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        }
        boolean z2 = m11Var.f58233a5;
        if (!z2 && !z2) {
            m11Var.f58233a5 = true;
            if (!m11Var.f58230a2) {
                C1222sh c1222sh = m11Var.f58232a4;
                C1223si c1223si = m11Var.f58231a3;
                c1222sh.getClass();
                m11Var.f58229a1 = c1223si.f59992b4 * 10000.0f;
            }
            float f2 = m11Var.f58229a1;
            if (f2 > Float.MAX_VALUE || f2 < -3.4028235E38f) {
                throw new IllegalArgumentException("Starting value need to be in between min value and max value");
            }
            ThreadLocal threadLocal = C1248t6.f60142a5;
            if (threadLocal.get() == null) {
                threadLocal.set(new C1248t6());
            }
            C1248t6 c1248t6 = (C1248t6) threadLocal.get();
            ArrayList arrayList = c1248t6.f60144a1;
            if (arrayList.size() == 0) {
                if (c1248t6.f60146a3 == null) {
                    c1248t6.f60146a3 = new zg1(c1248t6.f60145a2);
                }
                zg1 zg1Var = c1248t6.f60146a3;
                ((Choreographer) zg1Var.f61552a1).postFrameCallback((ChoreographerFrameCallbackC1247t5) zg1Var.f61553a2);
            }
            if (!arrayList.contains(m11Var)) {
                arrayList.add(m11Var);
                return true;
            }
        }
        return true;
    }
}
