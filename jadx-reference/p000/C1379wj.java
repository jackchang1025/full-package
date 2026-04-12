package p000;

import android.text.Editable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wj */
/* loaded from: classes.dex */
public final class C1379wj extends Editable.Factory {

    /* renamed from: a0 */
    public static final Object f60936a0 = new Object();

    /* renamed from: a1 */
    public static volatile C1379wj f60937a1;

    /* renamed from: a2 */
    public static Class f60938a2;

    @Override // android.text.Editable.Factory
    public final Editable newEditable(CharSequence charSequence) {
        Class cls = f60938a2;
        return cls != null ? new g11(cls, charSequence) : super.newEditable(charSequence);
    }
}
