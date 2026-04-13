package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class HeartbeatMessageType {
    public static final short heartbeat_request = 1;
    public static final short heartbeat_response = 2;

    public static String getName(short s2) {
        return s2 != 1 ? s2 != 2 ? "UNKNOWN" : "heartbeat_response" : "heartbeat_request";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }

    public static boolean isValid(short s2) {
        return s2 >= 1 && s2 <= 2;
    }
}
