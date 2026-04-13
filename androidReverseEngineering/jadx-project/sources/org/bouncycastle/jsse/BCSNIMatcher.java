package org.bouncycastle.jsse;

import org.bouncycastle.tls.TlsUtils;

/* loaded from: classes.dex */
public abstract class BCSNIMatcher {
    private final int nameType;

    public BCSNIMatcher(int i2) {
        if (!TlsUtils.isValidUint8(i2)) {
            throw new IllegalArgumentException("'nameType' should be between 0 and 255");
        }
        this.nameType = i2;
    }

    public final int getType() {
        return this.nameType;
    }

    public abstract boolean matches(BCSNIServerName bCSNIServerName);
}
