package org.bouncycastle.i18n.filter;

/* loaded from: classes.dex */
public class SQLFilter implements Filter {
    @Override // org.bouncycastle.i18n.filter.Filter
    public String doFilter(String str) {
        int i2;
        String str2;
        StringBuffer stringBuffer = new StringBuffer(str);
        int i3 = 0;
        while (i3 < stringBuffer.length()) {
            char charAt = stringBuffer.charAt(i3);
            if (charAt == '\n') {
                i2 = i3 + 1;
                str2 = "\\n";
            } else if (charAt == '\r') {
                i2 = i3 + 1;
                str2 = "\\r";
            } else if (charAt == '\"') {
                i2 = i3 + 1;
                str2 = "\\\"";
            } else if (charAt == '\'') {
                i2 = i3 + 1;
                str2 = "\\'";
            } else if (charAt == '-') {
                i2 = i3 + 1;
                str2 = "\\-";
            } else if (charAt == '/') {
                i2 = i3 + 1;
                str2 = "\\/";
            } else if (charAt == ';') {
                i2 = i3 + 1;
                str2 = "\\;";
            } else if (charAt == '=') {
                i2 = i3 + 1;
                str2 = "\\=";
            } else if (charAt != '\\') {
                i3++;
            } else {
                i2 = i3 + 1;
                str2 = "\\\\";
            }
            stringBuffer.replace(i3, i2, str2);
            i3 = i2;
            i3++;
        }
        return stringBuffer.toString();
    }

    @Override // org.bouncycastle.i18n.filter.Filter
    public String doFilterUrl(String str) {
        return doFilter(str);
    }
}
