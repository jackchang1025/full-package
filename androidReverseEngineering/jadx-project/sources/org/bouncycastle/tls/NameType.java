package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class NameType {
    public static final short host_name = 0;

    public static String getName(short s2) {
        return s2 != 0 ? "UNKNOWN" : "host_name";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }

    public static boolean isRecognized(short s2) {
        return s2 == 0;
    }

    public static boolean isValid(short s2) {
        return TlsUtils.isValidUint8(s2);
    }
}
