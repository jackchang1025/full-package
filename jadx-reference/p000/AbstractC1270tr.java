package p000;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tr */
/* loaded from: classes.dex */
public abstract class AbstractC1270tr {
    /* renamed from: a0 */
    public static void m214767a0(Drawable drawable, Resources.Theme theme) {
        drawable.applyTheme(theme);
    }

    /* renamed from: a1 */
    public static boolean m214768a1(Drawable drawable) {
        return drawable.canApplyTheme();
    }

    /* renamed from: a2 */
    public static ColorFilter m214769a2(Drawable drawable) {
        return drawable.getColorFilter();
    }

    /* renamed from: a3 */
    public static void m214770a3(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        drawable.inflate(resources, xmlPullParser, attributeSet, theme);
    }

    /* renamed from: a4 */
    public static void m214771a4(Drawable drawable, float f, float f2) {
        drawable.setHotspot(f, f2);
    }

    /* renamed from: a5 */
    public static void m214772a5(Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setHotspotBounds(i, i2, i3, i4);
    }

    /* renamed from: a6 */
    public static void m214773a6(Drawable drawable, int i) {
        drawable.setTint(i);
    }

    /* renamed from: a7 */
    public static void m214774a7(Drawable drawable, ColorStateList colorStateList) {
        drawable.setTintList(colorStateList);
    }

    /* renamed from: a8 */
    public static void m214775a8(Drawable drawable, PorterDuff.Mode mode) {
        drawable.setTintMode(mode);
    }
}
