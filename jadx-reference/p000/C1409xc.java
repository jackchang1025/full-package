package p000;

import android.text.Editable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xc */
/* loaded from: classes2.dex */
public final class C1409xc extends i61 {

    /* renamed from: a0 */
    public final /* synthetic */ C1415xf f61064a0;

    public C1409xc(C1415xf c1415xf) {
        this.f61064a0 = c1415xf;
    }

    @Override // p000.i61, android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        this.f61064a0.m215157a1().mo213189a0();
    }

    @Override // p000.i61, android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.f61064a0.m215157a1().mo214231a1();
    }
}
