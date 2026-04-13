package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class IdentifierType {
    public static final short cert_sha1_hash = 3;
    public static final short key_sha1_hash = 1;
    public static final short pre_agreed = 0;
    public static final short x509_name = 2;

    public static String getName(short s2) {
        return s2 != 0 ? s2 != 1 ? s2 != 2 ? s2 != 3 ? "UNKNOWN" : "cert_sha1_hash" : "x509_name" : "key_sha1_hash" : "pre_agreed";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }
}
