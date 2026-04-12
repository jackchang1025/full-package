package p000;

import android.text.StaticLayout;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ac */
/* loaded from: classes.dex */
public abstract class AbstractC0023ac {
    /* renamed from: a0 */
    public abstract void mo72a0(StaticLayout.Builder builder, TextView textView);

    /* renamed from: a1 */
    public boolean mo209757a1(TextView textView) {
        Object objInvoke = Boolean.FALSE;
        try {
            objInvoke = C0024ad.m209781a3("getHorizontallyScrolling").invoke(textView, null);
        } catch (Exception unused) {
        }
        return ((Boolean) objInvoke).booleanValue();
    }
}
