package p000;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tx */
/* loaded from: classes2.dex */
public abstract class AbstractC1277tx extends Drawable implements Animatable {

    /* renamed from: b0 */
    public static final C0556gt f60290b0 = new C0556gt(Float.class, "growFraction", 7);

    /* renamed from: a0 */
    public final Context f60291a0;

    /* renamed from: a1 */
    public final AbstractC0411dd f60292a1;

    /* renamed from: a3 */
    public ObjectAnimator f60294a3;

    /* renamed from: a4 */
    public ObjectAnimator f60295a4;

    /* renamed from: a5 */
    public ArrayList f60296a5;

    /* renamed from: a6 */
    public boolean f60297a6;

    /* renamed from: a7 */
    public float f60298a7;

    /* renamed from: a9 */
    public int f60300a9;

    /* renamed from: a8 */
    public final Paint f60299a8 = new Paint();

    /* renamed from: a2 */
    public C1250t8 f60293a2 = new C1250t8();

    public AbstractC1277tx(Context context, AbstractC0411dd abstractC0411dd) {
        this.f60291a0 = context;
        this.f60292a1 = abstractC0411dd;
        setAlpha(v10.MASK);
    }

    /* renamed from: a1 */
    public final float m214794a1() {
        AbstractC0411dd abstractC0411dd = this.f60292a1;
        if (abstractC0411dd.f55697a4 == 0 && abstractC0411dd.f55698a5 == 0) {
            return 1.0f;
        }
        return this.f60298a7;
    }

    /* renamed from: a2 */
    public final boolean m214795a2() {
        ObjectAnimator objectAnimator = this.f60295a4;
        return objectAnimator != null && objectAnimator.isRunning();
    }

    /* renamed from: a3 */
    public final boolean m214796a3() {
        ObjectAnimator objectAnimator = this.f60294a3;
        return objectAnimator != null && objectAnimator.isRunning();
    }

    /* renamed from: a4 */
    public final boolean m214797a4(boolean z, boolean z2, boolean z3) {
        C1250t8 c1250t8 = this.f60293a2;
        ContentResolver contentResolver = this.f60291a0.getContentResolver();
        c1250t8.getClass();
        return mo214032a5(z, z2, z3 && Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f) > 0.0f);
    }

    /* renamed from: a5 */
    public boolean mo214032a5(boolean z, boolean z2, boolean z3) {
        ObjectAnimator objectAnimator = this.f60294a3;
        int i = 0;
        C0556gt c0556gt = f60290b0;
        if (objectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, c0556gt, 0.0f, 1.0f);
            this.f60294a3 = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(500L);
            this.f60294a3.setInterpolator(AbstractC1249t7.f60179a1);
            ObjectAnimator objectAnimator2 = this.f60294a3;
            if (objectAnimator2 != null && objectAnimator2.isRunning()) {
                throw new IllegalArgumentException("Cannot set showAnimator while the current showAnimator is running.");
            }
            this.f60294a3 = objectAnimator2;
            objectAnimator2.addListener(new C1275tw(this, i));
        }
        int i2 = 1;
        if (this.f60295a4 == null) {
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, c0556gt, 1.0f, 0.0f);
            this.f60295a4 = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setDuration(500L);
            this.f60295a4.setInterpolator(AbstractC1249t7.f60179a1);
            ObjectAnimator objectAnimator3 = this.f60295a4;
            if (objectAnimator3 != null && objectAnimator3.isRunning()) {
                throw new IllegalArgumentException("Cannot set hideAnimator while the current hideAnimator is running.");
            }
            this.f60295a4 = objectAnimator3;
            objectAnimator3.addListener(new C1275tw(this, i2));
        }
        if (isVisible() || z) {
            ObjectAnimator objectAnimator4 = z ? this.f60294a3 : this.f60295a4;
            ObjectAnimator objectAnimator5 = z ? this.f60295a4 : this.f60294a3;
            if (!z3) {
                if (objectAnimator5.isRunning()) {
                    boolean z4 = this.f60297a6;
                    this.f60297a6 = true;
                    new ValueAnimator[]{objectAnimator5}[0].cancel();
                    this.f60297a6 = z4;
                }
                if (objectAnimator4.isRunning()) {
                    objectAnimator4.end();
                } else {
                    boolean z5 = this.f60297a6;
                    this.f60297a6 = true;
                    new ValueAnimator[]{objectAnimator4}[0].end();
                    this.f60297a6 = z5;
                }
                return super.setVisible(z, false);
            }
            if (!z3 || !objectAnimator4.isRunning()) {
                boolean z6 = !z || super.setVisible(z, false);
                AbstractC0411dd abstractC0411dd = this.f60292a1;
                if (!z ? abstractC0411dd.f55698a5 != 0 : abstractC0411dd.f55697a4 != 0) {
                    boolean z7 = this.f60297a6;
                    this.f60297a6 = true;
                    new ValueAnimator[]{objectAnimator4}[0].end();
                    this.f60297a6 = z7;
                    return z6;
                }
                if (z2 || !objectAnimator4.isPaused()) {
                    objectAnimator4.start();
                    return z6;
                }
                objectAnimator4.resume();
                return z6;
            }
        }
        return false;
    }

    /* renamed from: a6 */
    public final void m214798a6(C0410dc c0410dc) {
        ArrayList arrayList = this.f60296a5;
        if (arrayList == null || !arrayList.contains(c0410dc)) {
            return;
        }
        this.f60296a5.remove(c0410dc);
        if (this.f60296a5.isEmpty()) {
            this.f60296a5 = null;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.f60300a9;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Animatable
    public final boolean isRunning() {
        return m214796a3() || m214795a2();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.f60300a9 = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f60299a8.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        return m214797a4(z, z2, true);
    }

    @Override // android.graphics.drawable.Animatable
    public final void start() {
        mo214032a5(true, true, false);
    }

    @Override // android.graphics.drawable.Animatable
    public final void stop() {
        mo214032a5(false, true, false);
    }
}
