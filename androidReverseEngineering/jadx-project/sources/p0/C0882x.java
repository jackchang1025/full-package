package p0;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: p0.x */
/* loaded from: classes.dex */
public final class C0882x {

    /* renamed from: d */
    public static final Pattern f1915d = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");

    /* renamed from: e */
    public static final Pattern f1916e = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");

    /* renamed from: a */
    public final String f1917a;

    /* renamed from: b */
    public final String f1918b;

    /* renamed from: c */
    public final String f1919c;

    public C0882x(String str, String str2, String str3) {
        this.f1917a = str;
        this.f1918b = str2;
        this.f1919c = str3;
    }

    /* renamed from: a */
    public static C0882x m1301a(String str) {
        Matcher matcher = f1915d.matcher(str);
        if (!matcher.lookingAt()) {
            throw new IllegalArgumentException("No subtype found for: \"" + str + '\"');
        }
        String group = matcher.group(1);
        Locale locale = Locale.US;
        String lowerCase = group.toLowerCase(locale);
        matcher.group(2).toLowerCase(locale);
        Matcher matcher2 = f1916e.matcher(str);
        String str2 = null;
        for (int end = matcher.end(); end < str.length(); end = matcher2.end()) {
            matcher2.region(end, str.length());
            if (!matcher2.lookingAt()) {
                throw new IllegalArgumentException("Parameter is not formatted correctly: \"" + str.substring(end) + "\" for: \"" + str + '\"');
            }
            String group2 = matcher2.group(1);
            if (group2 != null && group2.equalsIgnoreCase("charset")) {
                String group3 = matcher2.group(2);
                if (group3 == null) {
                    group3 = matcher2.group(3);
                } else if (group3.startsWith("'") && group3.endsWith("'") && group3.length() > 2) {
                    group3 = group3.substring(1, group3.length() - 1);
                }
                if (str2 != null && !group3.equalsIgnoreCase(str2)) {
                    throw new IllegalArgumentException("Multiple charsets defined: \"" + str2 + "\" and: \"" + group3 + "\" for: \"" + str + '\"');
                }
                str2 = group3;
            }
        }
        return new C0882x(str, lowerCase, str2);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C0882x) && ((C0882x) obj).f1917a.equals(this.f1917a);
    }

    public final int hashCode() {
        return this.f1917a.hashCode();
    }

    public final String toString() {
        return this.f1917a;
    }
}
