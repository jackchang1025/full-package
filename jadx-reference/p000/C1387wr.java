package p000;

import android.text.InputFilter;
import android.text.method.TransformationMethod;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wr */
/* loaded from: classes.dex */
public final class C1387wr extends b81 {

    /* renamed from: c2 */
    public final C1386wq f60964c2;

    public C1387wr(TextView textView) {
        this.f60964c2 = new C1386wq(textView);
    }

    @Override // p000.b81
    /* renamed from: b8 */
    public final InputFilter[] mo210603b8(InputFilter[] inputFilterArr) {
        return !(C1375wg.f60900a9 != null) ? inputFilterArr : this.f60964c2.mo210603b8(inputFilterArr);
    }

    @Override // p000.b81
    /* renamed from: d3 */
    public final boolean mo210604d3() {
        return this.f60964c2.f60963c4;
    }

    @Override // p000.b81
    /* renamed from: e6 */
    public final void mo210607e6(boolean z) {
        if (C1375wg.f60900a9 != null) {
            this.f60964c2.mo210607e6(z);
        }
    }

    @Override // p000.b81
    /* renamed from: e7 */
    public final void mo210608e7(boolean z) {
        C1386wq c1386wq = this.f60964c2;
        if (C1375wg.f60900a9 != null) {
            c1386wq.mo210608e7(z);
        } else {
            c1386wq.f60963c4 = z;
        }
    }

    @Override // p000.b81
    /* renamed from: f4 */
    public final TransformationMethod mo210609f4(TransformationMethod transformationMethod) {
        return !(C1375wg.f60900a9 != null) ? transformationMethod : this.f60964c2.mo210609f4(transformationMethod);
    }
}
