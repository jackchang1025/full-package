package org.bouncycastle.pqc.crypto.qtesla;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class QTESLASecurityCategory {
    public static final int PROVABLY_SECURE_I = 5;
    public static final int PROVABLY_SECURE_III = 6;

    private QTESLASecurityCategory() {
    }

    public static String getName(int i2) {
        if (i2 == 5) {
            return "qTESLA-p-I";
        }
        if (i2 == 6) {
            return "qTESLA-p-III";
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("unknown security category: ", i2));
    }

    public static int getPrivateSize(int i2) {
        if (i2 == 5) {
            return 5224;
        }
        if (i2 == 6) {
            return 12392;
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("unknown security category: ", i2));
    }

    public static int getPublicSize(int i2) {
        if (i2 == 5) {
            return 14880;
        }
        if (i2 == 6) {
            return 38432;
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("unknown security category: ", i2));
    }

    public static int getSignatureSize(int i2) {
        if (i2 == 5) {
            return 2592;
        }
        if (i2 == 6) {
            return 5664;
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("unknown security category: ", i2));
    }

    public static void validate(int i2) {
        if (i2 != 5 && i2 != 6) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("unknown security category: ", i2));
        }
    }
}
