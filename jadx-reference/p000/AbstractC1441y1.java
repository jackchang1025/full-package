package p000;

import android.content.res.Resources;
import android.widget.ThemedSpinnerAdapter;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: y1 */
/* loaded from: classes.dex */
public abstract class AbstractC1441y1 {
    /* renamed from: a0 */
    public static void m215223a0(ThemedSpinnerAdapter themedSpinnerAdapter, Resources.Theme theme) {
        if (tk0.m214759a0(themedSpinnerAdapter.getDropDownViewTheme(), theme)) {
            return;
        }
        themedSpinnerAdapter.setDropDownViewTheme(theme);
    }
}
