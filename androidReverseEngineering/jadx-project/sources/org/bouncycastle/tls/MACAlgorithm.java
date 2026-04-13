package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class MACAlgorithm {
    public static final int _null = 0;
    public static final int hmac_md5 = 1;
    public static final int hmac_sha1 = 2;
    public static final int hmac_sha256 = 3;
    public static final int hmac_sha384 = 4;
    public static final int hmac_sha512 = 5;
    public static final int md5 = 1;
    public static final int sha = 2;

    public static String getName(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "UNKNOWN" : "hmac_sha512" : "hmac_sha384" : "hmac_sha256" : "hmac_sha1" : "hmac_md5" : "null";
    }

    public static String getText(int i2) {
        return getName(i2) + "(" + i2 + ")";
    }

    public static boolean isHMAC(int i2) {
        return i2 == 1 || i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5;
    }
}
