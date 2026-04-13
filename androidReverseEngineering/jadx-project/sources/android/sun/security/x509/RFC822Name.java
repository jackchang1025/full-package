package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.util.Locale;

/* loaded from: classes.dex */
public class RFC822Name implements GeneralNameInterface {
    private String name;

    public RFC822Name(DerValue derValue) {
        String iA5String = derValue.getIA5String();
        this.name = iA5String;
        parseName(iA5String);
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface == null || generalNameInterface.getType() != 1) {
            return -1;
        }
        String name = ((RFC822Name) generalNameInterface).getName();
        Locale locale = Locale.ENGLISH;
        String lowerCase = name.toLowerCase(locale);
        String lowerCase2 = this.name.toLowerCase(locale);
        if (lowerCase.equals(lowerCase2)) {
            return 0;
        }
        if (lowerCase2.endsWith(lowerCase)) {
            if (lowerCase.indexOf(64) == -1 && (lowerCase.startsWith(".") || lowerCase2.charAt(lowerCase2.lastIndexOf(lowerCase) - 1) == '@')) {
                return 2;
            }
        } else if (lowerCase.endsWith(lowerCase2) && lowerCase2.indexOf(64) == -1 && (lowerCase2.startsWith(".") || lowerCase.charAt(lowerCase.lastIndexOf(lowerCase2) - 1) == '@')) {
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
        if (obj instanceof RFC822Name) {
            return this.name.equalsIgnoreCase(((RFC822Name) obj).name);
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 1;
    }

    public int hashCode() {
        return this.name.toUpperCase().hashCode();
    }

    public void parseName(String str) {
        if (str == null || str.length() == 0) {
            throw new IOException("RFC822Name may not be null or empty");
        }
        String substring = str.substring(str.indexOf(64) + 1);
        if (substring.length() == 0) {
            throw new IOException("RFC822Name may not end with @");
        }
        if (substring.startsWith(".") && substring.length() == 1) {
            throw new IOException("RFC822Name domain may not be just .");
        }
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        String str = this.name;
        int lastIndexOf = str.lastIndexOf(64);
        int i2 = 1;
        if (lastIndexOf >= 0) {
            str = str.substring(lastIndexOf + 1);
            i2 = 2;
        }
        while (str.lastIndexOf(46) >= 0) {
            str = str.substring(0, str.lastIndexOf(46));
            i2++;
        }
        return i2;
    }

    public String toString() {
        return "RFC822Name: " + this.name;
    }

    public RFC822Name(String str) {
        parseName(str);
        this.name = str;
    }
}
