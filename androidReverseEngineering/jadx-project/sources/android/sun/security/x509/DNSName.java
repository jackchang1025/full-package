package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes.dex */
public class DNSName implements GeneralNameInterface {
    private static final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String alphaDigitsAndHyphen = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-";
    private static final String digitsAndHyphen = "0123456789-";
    private String name;

    public DNSName(DerValue derValue) {
        this.name = derValue.getIA5String();
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface == null || generalNameInterface.getType() != 2) {
            return -1;
        }
        String name = ((DNSName) generalNameInterface).getName();
        Locale locale = Locale.ENGLISH;
        String lowerCase = name.toLowerCase(locale);
        String lowerCase2 = this.name.toLowerCase(locale);
        if (lowerCase.equals(lowerCase2)) {
            return 0;
        }
        if (lowerCase2.endsWith(lowerCase)) {
            if (lowerCase2.charAt(lowerCase2.lastIndexOf(lowerCase) - 1) == '.') {
                return 2;
            }
        } else if (lowerCase.endsWith(lowerCase2) && lowerCase.charAt(lowerCase.lastIndexOf(lowerCase2) - 1) == '.') {
            return 1;
        }
        return 3;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putIA5String(this.name);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DNSName) {
            return this.name.equalsIgnoreCase(((DNSName) obj).name);
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 2;
    }

    public int hashCode() {
        return this.name.toUpperCase().hashCode();
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        String str = this.name;
        int i2 = 1;
        while (str.lastIndexOf(46) >= 0) {
            str = str.substring(0, str.lastIndexOf(46));
            i2++;
        }
        return i2;
    }

    public String toString() {
        return "DNSName: " + this.name;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0062, code lost:
    
        r0 = r1 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DNSName(String str) {
        if (str == null || str.length() == 0) {
            throw new IOException("DNS name must not be null");
        }
        if (str.indexOf(32) != -1) {
            throw new IOException("DNS names or NameConstraints with blank components are not permitted");
        }
        int i2 = 0;
        if (str.charAt(0) == '.' || str.charAt(str.length() - 1) == '.') {
            throw new IOException("DNS names or NameConstraints may not begin or end with a .");
        }
        while (i2 < str.length()) {
            int indexOf = str.indexOf(46, i2);
            indexOf = indexOf < 0 ? str.length() : indexOf;
            if (indexOf - i2 < 1) {
                throw new IOException("DNSName SubjectAltNames with empty components are not permitted");
            }
            if (alpha.indexOf(str.charAt(i2)) < 0) {
                throw new IOException("DNSName components must begin with a letter");
            }
            do {
                i2++;
                if (i2 < indexOf) {
                }
            } while (alphaDigitsAndHyphen.indexOf(str.charAt(i2)) >= 0);
            throw new IOException("DNSName components must consist of letters, digits, and hyphens");
        }
        this.name = str;
    }
}
