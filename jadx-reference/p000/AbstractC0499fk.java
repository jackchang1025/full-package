package p000;

import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fk */
/* loaded from: classes.dex */
public abstract class AbstractC0499fk {
    /* renamed from: a0 */
    public static final void m212824a0(Bundle bundle, String str, Size size) {
        t60.m214695b6(bundle, "bundle");
        t60.m214695b6(str, "key");
        bundle.putSize(str, size);
    }

    /* renamed from: a1 */
    public static final void m212825a1(Bundle bundle, String str, SizeF sizeF) {
        t60.m214695b6(bundle, "bundle");
        t60.m214695b6(str, "key");
        bundle.putSizeF(str, sizeF);
    }
}
