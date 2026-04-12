package p000;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ag1 {

    /* renamed from: a0 */
    public final kg1 f43655a0;

    public ag1(WindowInsetsController windowInsetsController) {
        this.f43655a0 = new yf1(windowInsetsController);
    }

    public ag1(Window window, View view) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            yf1 yf1Var = new yf1(window.getInsetsController());
            yf1Var.f61308a5 = window;
            this.f43655a0 = yf1Var;
        } else if (i >= 26) {
            this.f43655a0 = new zf1(window, view);
        } else {
            this.f43655a0 = new yf1(window, view);
        }
    }
}
