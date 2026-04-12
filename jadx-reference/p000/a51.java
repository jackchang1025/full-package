package p000;

import android.annotation.SuppressLint;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class a51 {
    public /* synthetic */ a51(AbstractC1120qr abstractC1120qr) {
        this();
    }

    private final boolean containsSurroundingParenthesis(String str) {
        if (str.length() == 0) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            int i4 = i3 + 1;
            if (i3 == 0 && cCharAt != '(') {
                return false;
            }
            if (cCharAt == '(') {
                i2++;
            } else if (cCharAt == ')' && i2 - 1 == 0 && i3 != str.length() - 1) {
                return false;
            }
            i++;
            i3 = i4;
        }
        return i2 == 0;
    }

    @SuppressLint({"SyntheticAccessor"})
    public final boolean defaultValueEquals(String str, String str2) {
        t60.m214695b6(str, "current");
        if (str.equals(str2)) {
            return true;
        }
        if (!containsSurroundingParenthesis(str)) {
            return false;
        }
        String strSubstring = str.substring(1, str.length() - 1);
        t60.m214694b5(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return t60.m214686a2(AbstractC0779a1.m213687e0(strSubstring).toString(), str2);
    }

    private a51() {
    }
}
