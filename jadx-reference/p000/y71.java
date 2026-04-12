package p000;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class y71 {

    /* renamed from: a1 */
    public final View f61263a1;

    /* renamed from: a0 */
    public final HashMap f61262a0 = new HashMap();

    /* renamed from: a2 */
    public final ArrayList f61264a2 = new ArrayList();

    public y71(View view) {
        this.f61263a1 = view;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof y71)) {
            return false;
        }
        y71 y71Var = (y71) obj;
        return this.f61263a1 == y71Var.f61263a1 && this.f61262a0.equals(y71Var.f61262a0);
    }

    public final int hashCode() {
        return this.f61262a0.hashCode() + (this.f61263a1.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sbM39c0 = AbstractC0003a2.m39c0("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n", "    view = ");
        sbM39c0.append(this.f61263a1);
        sbM39c0.append("\n");
        String strM32b3 = AbstractC0003a2.m32b3(sbM39c0.toString(), "    values:");
        HashMap map = this.f61262a0;
        for (String str : map.keySet()) {
            strM32b3 = strM32b3 + "    " + str + ": " + map.get(str) + "\n";
        }
        return strM32b3;
    }
}
