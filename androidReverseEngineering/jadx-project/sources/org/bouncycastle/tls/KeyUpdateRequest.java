package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class KeyUpdateRequest {
    public static final short update_not_requested = 0;
    public static final short update_requested = 1;

    public static String getName(short s2) {
        return s2 != 0 ? s2 != 1 ? "UNKNOWN" : "update_requested" : "update_not_requested";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }

    public static boolean isValid(short s2) {
        return s2 >= 0 && s2 <= 1;
    }
}
