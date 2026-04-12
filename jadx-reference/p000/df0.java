package p000;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.MenuItem;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class df0 {
    /* renamed from: a0 */
    public static int m212586a0(MenuItem menuItem) {
        return menuItem.getAlphabeticModifiers();
    }

    /* renamed from: a1 */
    public static CharSequence m212587a1(MenuItem menuItem) {
        return menuItem.getContentDescription();
    }

    /* renamed from: a2 */
    public static ColorStateList m212588a2(MenuItem menuItem) {
        return menuItem.getIconTintList();
    }

    /* renamed from: a3 */
    public static PorterDuff.Mode m212589a3(MenuItem menuItem) {
        return menuItem.getIconTintMode();
    }

    /* renamed from: a4 */
    public static int m212590a4(MenuItem menuItem) {
        return menuItem.getNumericModifiers();
    }

    /* renamed from: a5 */
    public static CharSequence m212591a5(MenuItem menuItem) {
        return menuItem.getTooltipText();
    }

    /* renamed from: a6 */
    public static MenuItem m212592a6(MenuItem menuItem, char c, int i) {
        return menuItem.setAlphabeticShortcut(c, i);
    }

    /* renamed from: a7 */
    public static MenuItem m212593a7(MenuItem menuItem, CharSequence charSequence) {
        return menuItem.setContentDescription(charSequence);
    }

    /* renamed from: a8 */
    public static MenuItem m212594a8(MenuItem menuItem, ColorStateList colorStateList) {
        return menuItem.setIconTintList(colorStateList);
    }

    /* renamed from: a9 */
    public static MenuItem m212595a9(MenuItem menuItem, PorterDuff.Mode mode) {
        return menuItem.setIconTintMode(mode);
    }

    /* renamed from: b0 */
    public static MenuItem m212596b0(MenuItem menuItem, char c, int i) {
        return menuItem.setNumericShortcut(c, i);
    }

    /* renamed from: b1 */
    public static MenuItem m212597b1(MenuItem menuItem, char c, char c2, int i, int i2) {
        return menuItem.setShortcut(c, c2, i, i2);
    }

    /* renamed from: b2 */
    public static MenuItem m212598b2(MenuItem menuItem, CharSequence charSequence) {
        return menuItem.setTooltipText(charSequence);
    }
}
