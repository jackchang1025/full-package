package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class PskKeyExchangeMode {
    public static final short psk_dhe_ke = 1;
    public static final short psk_ke = 0;

    public static String getName(short s2) {
        return s2 != 0 ? s2 != 1 ? "UNKNOWN" : "psk_dhe_ke" : "psk_ke";
    }

    public static String getText(short s2) {
        return getName(s2) + "(" + ((int) s2) + ")";
    }
}
