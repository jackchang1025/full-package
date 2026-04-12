package p000;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.tabs.TabLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: vy */
/* loaded from: classes2.dex */
public final class C1356vy extends fh0 {

    /* renamed from: a2 */
    public final /* synthetic */ int f60717a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1356vy(int i) {
        super(13);
        this.f60717a2 = i;
    }

    @Override // p000.fh0
    /* renamed from: a6 */
    public final void mo212812a6(TabLayout tabLayout, View view, View view2, float f, Drawable drawable) {
        float fSin;
        float fCos;
        switch (this.f60717a2) {
            case 0:
                RectF rectFM212807a1 = fh0.m212807a1(tabLayout, view);
                RectF rectFM212807a12 = fh0.m212807a1(tabLayout, view2);
                if (rectFM212807a1.left < rectFM212807a12.left) {
                    double d = (f * 3.141592653589793d) / 2.0d;
                    fSin = (float) (1.0d - Math.cos(d));
                    fCos = (float) Math.sin(d);
                } else {
                    double d2 = (f * 3.141592653589793d) / 2.0d;
                    fSin = (float) Math.sin(d2);
                    fCos = (float) (1.0d - Math.cos(d2));
                }
                drawable.setBounds(AbstractC1249t7.m214729a2((int) rectFM212807a1.left, fSin, (int) rectFM212807a12.left), drawable.getBounds().top, AbstractC1249t7.m214729a2((int) rectFM212807a1.right, fCos, (int) rectFM212807a12.right), drawable.getBounds().bottom);
                break;
            default:
                if (f >= 0.5f) {
                    view = view2;
                }
                RectF rectFM212807a13 = fh0.m212807a1(tabLayout, view);
                float fM214728a1 = f < 0.5f ? AbstractC1249t7.m214728a1(1.0f, 0.0f, 0.0f, 0.5f, f) : AbstractC1249t7.m214728a1(0.0f, 1.0f, 0.5f, 1.0f, f);
                drawable.setBounds((int) rectFM212807a13.left, drawable.getBounds().top, (int) rectFM212807a13.right, drawable.getBounds().bottom);
                drawable.setAlpha((int) (fM214728a1 * 255.0f));
                break;
        }
    }
}
