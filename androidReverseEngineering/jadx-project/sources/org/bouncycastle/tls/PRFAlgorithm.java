package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class PRFAlgorithm {
    public static final int ssl_prf_legacy = 0;
    public static final int tls13_hkdf_sha256 = 4;
    public static final int tls13_hkdf_sha384 = 5;
    public static final int tls13_hkdf_sm3 = 7;
    public static final int tls_prf_legacy = 1;
    public static final int tls_prf_sha256 = 2;
    public static final int tls_prf_sha384 = 3;

    public static String getName(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 7 ? "UNKNOWN" : "tls13_hkdf_sm3" : "tls13_hkdf_sha384" : "tls13_hkdf_sha256" : "tls_prf_sha384" : "tls_prf_sha256" : "tls_prf_legacy" : "ssl_prf_legacy";
    }

    public static String getText(int i2) {
        return getName(i2) + "(" + i2 + ")";
    }
}
