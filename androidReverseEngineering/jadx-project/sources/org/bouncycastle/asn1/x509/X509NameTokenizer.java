package org.bouncycastle.asn1.x509;

/* loaded from: classes.dex */
public class X509NameTokenizer {
    private StringBuffer buf;
    private int index;
    private char separator;
    private String value;

    public X509NameTokenizer(String str) {
        this(str, ',');
    }

    public boolean hasMoreTokens() {
        return this.index != this.value.length();
    }

    public String nextToken() {
        if (this.index == this.value.length()) {
            return null;
        }
        int i2 = this.index + 1;
        this.buf.setLength(0);
        boolean z2 = false;
        boolean z3 = false;
        while (i2 != this.value.length()) {
            char charAt = this.value.charAt(i2);
            if (charAt == '\"') {
                if (!z2) {
                    z3 = !z3;
                }
            } else if (!z2 && !z3) {
                if (charAt == '\\') {
                    this.buf.append(charAt);
                    z2 = true;
                } else {
                    if (charAt == this.separator) {
                        break;
                    }
                    this.buf.append(charAt);
                }
                i2++;
            }
            this.buf.append(charAt);
            z2 = false;
            i2++;
        }
        this.index = i2;
        return this.buf.toString();
    }

    public X509NameTokenizer(String str, char c) {
        this.buf = new StringBuffer();
        this.value = str;
        this.index = -1;
        this.separator = c;
    }
}
