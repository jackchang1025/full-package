package android.sun.security.x509;

/* loaded from: classes.dex */
public class X509AttributeName {
    private static final char SEPARATOR = '.';
    private String prefix;
    private String suffix;

    public X509AttributeName(String str) {
        this.prefix = null;
        this.suffix = null;
        int indexOf = str.indexOf(46);
        if (indexOf == -1) {
            this.prefix = str;
        } else {
            this.prefix = str.substring(0, indexOf);
            this.suffix = str.substring(indexOf + 1);
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }
}
