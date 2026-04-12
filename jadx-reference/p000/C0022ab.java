package p000;

import android.text.StaticLayout;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ab */
/* loaded from: classes.dex */
public final class C0022ab extends C0011aa {
    @Override // p000.C0011aa, p000.AbstractC0023ac
    /* renamed from: a0 */
    public void mo72a0(StaticLayout.Builder builder, TextView textView) {
        builder.setTextDirection(textView.getTextDirectionHeuristic());
    }

    @Override // p000.AbstractC0023ac
    /* renamed from: a1 */
    public boolean mo209757a1(TextView textView) {
        return textView.isHorizontallyScrollable();
    }
}
