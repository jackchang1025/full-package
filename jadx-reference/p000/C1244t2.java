package p000;

import android.animation.AnimatorSet;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: t2 */
/* loaded from: classes2.dex */
public final class C1244t2 extends Drawable.ConstantState {

    /* renamed from: a0 */
    public s91 f60125a0;

    /* renamed from: a1 */
    public AnimatorSet f60126a1;

    /* renamed from: a2 */
    public ArrayList f60127a2;

    /* renamed from: a3 */
    public C0130bd f60128a3;

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        throw new IllegalStateException("No constant state support for SDK < 24.");
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable(Resources resources) {
        throw new IllegalStateException("No constant state support for SDK < 24.");
    }
}
