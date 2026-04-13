package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class HeartbeatMode {
    public static final short peer_allowed_to_send = 1;
    public static final short peer_not_allowed_to_send = 2;

    public static String getName(short s2) {
        return s2 != 1 ? s2 != 2 ? "UNKNOWN" : "peer_not_allowed_to_send" : "peer_allowed_to_send";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }

    public static boolean isValid(short s2) {
        return s2 >= 1 && s2 <= 2;
    }
}
