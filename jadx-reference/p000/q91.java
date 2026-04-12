package p000;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class q91 extends Drawable.ConstantState {

    /* renamed from: a0 */
    public int f59441a0;

    /* renamed from: a1 */
    public p91 f59442a1;

    /* renamed from: a2 */
    public ColorStateList f59443a2;

    /* renamed from: a3 */
    public PorterDuff.Mode f59444a3;

    /* renamed from: a4 */
    public boolean f59445a4;

    /* renamed from: a5 */
    public Bitmap f59446a5;

    /* renamed from: a6 */
    public ColorStateList f59447a6;

    /* renamed from: a7 */
    public PorterDuff.Mode f59448a7;

    /* renamed from: a8 */
    public int f59449a8;

    /* renamed from: a9 */
    public boolean f59450a9;

    /* renamed from: b0 */
    public boolean f59451b0;

    /* renamed from: b1 */
    public Paint f59452b1;

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        return this.f59441a0;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        return new s91(this);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources) {
        return new s91(this);
    }
}
