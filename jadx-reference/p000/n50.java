package p000;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class n50 extends AbstractC1277tx {

    /* renamed from: b1 */
    public final AbstractC1298uf f58454b1;

    /* renamed from: b2 */
    public AbstractC0395cy f58455b2;

    public n50(Context context, AbstractC0411dd abstractC0411dd, AbstractC1298uf abstractC1298uf, AbstractC0395cy abstractC0395cy) {
        super(context, abstractC0411dd);
        this.f58454b1 = abstractC1298uf;
        abstractC1298uf.f60421a1 = this;
        this.f58455b2 = abstractC0395cy;
        abstractC0395cy.f55538a0 = this;
    }

    @Override // p000.AbstractC1277tx
    /* renamed from: a5 */
    public final boolean mo214032a5(boolean z, boolean z2, boolean z3) {
        boolean zMo214032a5 = super.mo214032a5(z, z2, z3);
        if (!isRunning()) {
            this.f58455b2.mo212538a0();
        }
        C1250t8 c1250t8 = this.f60293a2;
        ContentResolver contentResolver = this.f60291a0.getContentResolver();
        c1250t8.getClass();
        Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f);
        if (z && z3) {
            this.f58455b2.mo212544b6();
        }
        return zMo214032a5;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect rect = new Rect();
        if (getBounds().isEmpty() || !isVisible() || !canvas.getClipBounds(rect)) {
            return;
        }
        canvas.save();
        Rect bounds = getBounds();
        float fM214794a1 = m214794a1();
        AbstractC1298uf abstractC1298uf = this.f58454b1;
        abstractC1298uf.f60420a0.mo211082a0();
        abstractC1298uf.mo213159a0(canvas, bounds, fM214794a1);
        AbstractC1298uf abstractC1298uf2 = this.f58454b1;
        Paint paint = this.f60299a8;
        abstractC1298uf2.mo213161a2(canvas, paint);
        int i = 0;
        while (true) {
            AbstractC0395cy abstractC0395cy = this.f58455b2;
            int[] iArr = (int[]) abstractC0395cy.f55540a2;
            if (i >= iArr.length) {
                canvas.restore();
                return;
            }
            float[] fArr = (float[]) abstractC0395cy.f55539a1;
            int i2 = i * 2;
            this.f58454b1.mo213160a1(canvas, paint, fArr[i2], fArr[i2 + 1], iArr[i]);
            i++;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.f58454b1.mo213162a3();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.f58454b1.mo213163a4();
    }
}
