package p000;

import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wu */
/* loaded from: classes.dex */
public final class C1390wu implements TransformationMethod {

    /* renamed from: a0 */
    public final TransformationMethod f60972a0;

    public C1390wu(TransformationMethod transformationMethod) {
        this.f60972a0 = transformationMethod;
    }

    @Override // android.text.method.TransformationMethod
    public final CharSequence getTransformation(CharSequence charSequence, View view) {
        if (view.isInEditMode()) {
            return charSequence;
        }
        TransformationMethod transformationMethod = this.f60972a0;
        if (transformationMethod != null) {
            charSequence = transformationMethod.getTransformation(charSequence, view);
        }
        if (charSequence == null || C1375wg.m215058a0().m215059a1() != 1) {
            return charSequence;
        }
        C1375wg c1375wgM215058a0 = C1375wg.m215058a0();
        c1375wgM215058a0.getClass();
        return c1375wgM215058a0.m215062a4(charSequence, 0, charSequence.length());
    }

    @Override // android.text.method.TransformationMethod
    public final void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
        TransformationMethod transformationMethod = this.f60972a0;
        if (transformationMethod != null) {
            transformationMethod.onFocusChanged(view, charSequence, z, i, rect);
        }
    }
}
