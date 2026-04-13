package android.support.v4.text;

import android.graphics.Rect;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.TextPaint;
import android.view.DisplayCutout;
import java.util.List;

/* renamed from: android.support.v4.text.a */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0065a {
    /* renamed from: B */
    public static /* synthetic */ void m232B() {
    }

    /* renamed from: g */
    public static /* synthetic */ PrecomputedText.Params.Builder m241g(TextPaint textPaint) {
        return new PrecomputedText.Params.Builder(textPaint);
    }

    /* renamed from: i */
    public static /* bridge */ /* synthetic */ PrecomputedText m243i(Spannable spannable) {
        return (PrecomputedText) spannable;
    }

    /* renamed from: m */
    public static /* synthetic */ DisplayCutout m247m(Rect rect, List list) {
        return new DisplayCutout(rect, list);
    }

    /* renamed from: n */
    public static /* bridge */ /* synthetic */ DisplayCutout m248n(Object obj) {
        return (DisplayCutout) obj;
    }

    /* renamed from: p */
    public static /* synthetic */ void m250p() {
    }

    /* renamed from: v */
    public static /* bridge */ /* synthetic */ boolean m256v(Spannable spannable) {
        return spannable instanceof PrecomputedText;
    }
}
