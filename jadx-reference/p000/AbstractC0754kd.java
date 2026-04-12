package p000;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.CompoundButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kd */
/* loaded from: classes.dex */
public abstract class AbstractC0754kd {
    /* renamed from: a0 */
    public static ColorStateList m213489a0(CompoundButton compoundButton) {
        return compoundButton.getButtonTintList();
    }

    /* renamed from: a1 */
    public static PorterDuff.Mode m213490a1(CompoundButton compoundButton) {
        return compoundButton.getButtonTintMode();
    }

    /* renamed from: a2 */
    public static void m213491a2(CompoundButton compoundButton, ColorStateList colorStateList) {
        compoundButton.setButtonTintList(colorStateList);
    }

    /* renamed from: a3 */
    public static void m213492a3(CompoundButton compoundButton, PorterDuff.Mode mode) {
        compoundButton.setButtonTintMode(mode);
    }
}
