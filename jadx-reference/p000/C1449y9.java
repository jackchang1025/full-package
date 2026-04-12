package p000;

import android.graphics.Typeface;
import android.os.Build;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: y9 */
/* loaded from: classes.dex */
public final class C1449y9 extends cq0 {

    /* renamed from: b0 */
    public final /* synthetic */ int f61265b0;

    /* renamed from: b1 */
    public final /* synthetic */ int f61266b1;

    /* renamed from: b2 */
    public final /* synthetic */ WeakReference f61267b2;

    /* renamed from: b3 */
    public final /* synthetic */ C1504z4 f61268b3;

    public C1449y9(C1504z4 c1504z4, int i, int i2, WeakReference weakReference) {
        this.f61268b3 = c1504z4;
        this.f61265b0 = i;
        this.f61266b1 = i2;
        this.f61267b2 = weakReference;
    }

    @Override // p000.cq0
    /* renamed from: c7 */
    public final void mo212509c7(Typeface typeface) {
        int i;
        int i2 = 1;
        if (Build.VERSION.SDK_INT >= 28 && (i = this.f61265b0) != -1) {
            typeface = AbstractC1503z3.m215338a0(typeface, i, (this.f61266b1 & 2) != 0);
        }
        C1504z4 c1504z4 = this.f61268b3;
        if (c1504z4.f61454b2) {
            c1504z4.f61453b1 = typeface;
            TextView textView = (TextView) this.f61267b2.get();
            if (textView != null) {
                WeakHashMap weakHashMap = xa1.f61054a0;
                if (ia1.m213141a1(textView)) {
                    textView.post(new RunnableC0707j6(c1504z4.f61451a9, i2, textView, typeface));
                } else {
                    textView.setTypeface(typeface, c1504z4.f61451a9);
                }
            }
        }
    }

    @Override // p000.cq0
    /* renamed from: c6 */
    public final void mo212508c6(int i) {
    }
}
