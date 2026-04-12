package p000;

/* loaded from: classes2.dex */
public class vh1 {
    private StringBuffer buf;
    private int index;
    private char separator;
    private String value;

    public vh1(String str) {
        this(str, ',');
    }

    public boolean hasMoreTokens() {
        return this.index != this.value.length();
    }

    public String nextToken() {
        if (this.index == this.value.length()) {
            return null;
        }
        int i = this.index + 1;
        this.buf.setLength(0);
        boolean z = false;
        boolean z2 = false;
        while (i != this.value.length()) {
            char cCharAt = this.value.charAt(i);
            if (cCharAt != '\"') {
                if (!z && !z2) {
                    if (cCharAt == '\\') {
                        this.buf.append(cCharAt);
                        z = true;
                    } else {
                        if (cCharAt == this.separator) {
                            break;
                        }
                        this.buf.append(cCharAt);
                    }
                }
                i++;
            } else if (!z) {
                z2 = !z2;
            }
            this.buf.append(cCharAt);
            z = false;
            i++;
        }
        this.index = i;
        return this.buf.toString();
    }

    public vh1(String str, char c) {
        this.buf = new StringBuffer();
        this.value = str;
        this.index = -1;
        this.separator = c;
    }
}
