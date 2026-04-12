package p000;

import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class v51 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f60585a0;

    /* renamed from: a1 */
    public final /* synthetic */ TextInputLayout f60586a1;

    public /* synthetic */ v51(TextInputLayout textInputLayout, int i) {
        this.f60585a0 = i;
        this.f60586a1 = textInputLayout;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f60585a0) {
            case 0:
                CheckableImageButton checkableImageButton = this.f60586a1.f49935a2.f61085a6;
                checkableImageButton.performClick();
                checkableImageButton.jumpDrawablesToCurrentState();
                break;
            default:
                this.f60586a1.f49936a3.requestLayout();
                break;
        }
    }
}
