package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class CertChainType {
    public static final short individual_certs = 0;
    public static final short pkipath = 1;

    public static String getName(short s2) {
        return s2 != 0 ? s2 != 1 ? "UNKNOWN" : "pkipath" : "individual_certs";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }

    public static boolean isValid(short s2) {
        return s2 >= 0 && s2 <= 1;
    }
}
