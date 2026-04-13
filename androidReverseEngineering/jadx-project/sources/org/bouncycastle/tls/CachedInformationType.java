package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class CachedInformationType {
    public static final short cert = 1;
    public static final short cert_req = 2;

    public static String getName(short s2) {
        return s2 != 1 ? s2 != 2 ? "UNKNOWN" : "cert_req" : "cert";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }
}
