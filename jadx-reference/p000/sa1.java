package p000;

import android.view.View;
import android.view.WindowInsetsController;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class sa1 {
    /* renamed from: a0 */
    public static CharSequence m214586a0(View view) {
        return view.getStateDescription();
    }

    /* renamed from: a1 */
    public static ag1 m214587a1(View view) {
        WindowInsetsController windowInsetsController = view.getWindowInsetsController();
        if (windowInsetsController != null) {
            return new ag1(windowInsetsController);
        }
        return null;
    }

    /* renamed from: a2 */
    public static void m214588a2(View view, CharSequence charSequence) {
        view.setStateDescription(charSequence);
    }
}
