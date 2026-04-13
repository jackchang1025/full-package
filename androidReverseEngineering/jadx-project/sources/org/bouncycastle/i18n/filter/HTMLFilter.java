package org.bouncycastle.i18n.filter;

/* loaded from: classes.dex */
public class HTMLFilter implements Filter {
    @Override // org.bouncycastle.i18n.filter.Filter
    public String doFilter(String str) {
        int i2;
        String str2;
        StringBuffer stringBuffer = new StringBuffer(str);
        int i3 = 0;
        while (i3 < stringBuffer.length()) {
            char charAt = stringBuffer.charAt(i3);
            if (charAt == '\"') {
                i2 = i3 + 1;
                str2 = "&#34";
            } else if (charAt == '#') {
                i2 = i3 + 1;
                str2 = "&#35";
            } else if (charAt == '+') {
                i2 = i3 + 1;
                str2 = "&#43";
            } else if (charAt == '-') {
                i2 = i3 + 1;
                str2 = "&#45";
            } else if (charAt == '>') {
                i2 = i3 + 1;
                str2 = "&#62";
            } else if (charAt == ';') {
                i2 = i3 + 1;
                str2 = "&#59";
            } else if (charAt != '<') {
                switch (charAt) {
                    case '%':
                        i2 = i3 + 1;
                        str2 = "&#37";
                        break;
                    case '&':
                        i2 = i3 + 1;
                        str2 = "&#38";
                        break;
                    case '\'':
                        i2 = i3 + 1;
                        str2 = "&#39";
                        break;
                    case '(':
                        i2 = i3 + 1;
                        str2 = "&#40";
                        break;
                    case ')':
                        i2 = i3 + 1;
                        str2 = "&#41";
                        break;
                    default:
                        i3 -= 3;
                        continue;
                }
            } else {
                i2 = i3 + 1;
                str2 = "&#60";
            }
            stringBuffer.replace(i3, i2, str2);
            i3 += 4;
        }
        return stringBuffer.toString();
    }

    @Override // org.bouncycastle.i18n.filter.Filter
    public String doFilterUrl(String str) {
        return doFilter(str);
    }
}
