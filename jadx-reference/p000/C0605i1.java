package p000;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: i1 */
/* loaded from: classes.dex */
public final class C0605i1 extends ClickableSpan {

    /* renamed from: a0 */
    public final int f56781a0;

    /* renamed from: a1 */
    public final C0748k7 f56782a1;

    /* renamed from: a2 */
    public final int f56783a2;

    public C0605i1(int i, C0748k7 c0748k7, int i2) {
        this.f56781a0 = i;
        this.f56782a1 = c0748k7;
        this.f56783a2 = i2;
    }

    @Override // android.text.style.ClickableSpan
    public final void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", this.f56781a0);
        this.f56782a1.f57472a0.performAction(this.f56783a2, bundle);
    }
}
