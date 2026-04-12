package p000;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R$attr;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.C0041a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: n3 */
/* loaded from: classes.dex */
public final class C0885n3 extends AppCompatImageView implements InterfaceC0886n4 {

    /* renamed from: a3 */
    public final /* synthetic */ C0041a1 f58437a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0885n3(C0041a1 c0041a1, Context context) {
        super(context, null, R$attr.actionOverflowButtonStyle);
        this.f58437a3 = c0041a1;
        setClickable(true);
        setFocusable(true);
        setVisibility(0);
        setEnabled(true);
        kj1.m213587d4(this, getContentDescription());
        setOnTouchListener(new C0852m8(this, this, 1));
    }

    @Override // p000.InterfaceC0886n4
    /* renamed from: a0 */
    public final boolean mo209842a0() {
        return false;
    }

    @Override // p000.InterfaceC0886n4
    /* renamed from: a1 */
    public final boolean mo209843a1() {
        return false;
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        playSoundEffect(0);
        this.f58437a3.m209942b3();
        return true;
    }

    @Override // android.widget.ImageView
    public final boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        Drawable drawable = getDrawable();
        Drawable background = getBackground();
        if (drawable != null && background != null) {
            int width = getWidth();
            int height = getHeight();
            int iMax = Math.max(width, height) / 2;
            int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
            int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
            AbstractC1270tr.m214772a5(background, paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
        }
        return frame;
    }
}
