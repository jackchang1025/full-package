package p000;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.EmptyList;
import kotlin.text.AbstractC0779a1;

/* loaded from: classes2.dex */
public abstract class m21 extends AbstractC0779a1 {
    /* renamed from: e2 */
    public static String m213934e2(String str) {
        t60.m214695b6(str, "<this>");
        int length = str.length() - 1;
        if (length < 0) {
            length = 0;
        }
        return m213937e5(length, str);
    }

    /* renamed from: e3 */
    public static char m213935e3(CharSequence charSequence) {
        if (charSequence.length() != 0) {
            return charSequence.charAt(AbstractC0779a1.m213657b0(charSequence));
        }
        throw new NoSuchElementException("Char sequence is empty.");
    }

    /* renamed from: e4 */
    public static Character m213936e4(String str) {
        if (str.length() == 0) {
            return null;
        }
        return Character.valueOf(str.charAt(str.length() - 1));
    }

    /* renamed from: e5 */
    public static String m213937e5(int i, String str) {
        t60.m214695b6(str, "<this>");
        if (i < 0) {
            throw new IllegalArgumentException(AbstractC0003a2.m30b1("Requested character count ", i, " is less than zero.").toString());
        }
        int length = str.length();
        if (i > length) {
            i = length;
        }
        String strSubstring = str.substring(0, i);
        t60.m214694b5(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    /* renamed from: e6 */
    public static List m213938e6(String str) {
        t60.m214695b6(str, "<this>");
        int length = str.length();
        if (length == 0) {
            return EmptyList.f57568a0;
        }
        if (length == 1) {
            return AbstractC1117qo.m214451e7(Character.valueOf(str.charAt(0)));
        }
        ArrayList arrayList = new ArrayList(str.length());
        for (int i = 0; i < str.length(); i++) {
            arrayList.add(Character.valueOf(str.charAt(i)));
        }
        return arrayList;
    }
}
