package org.bouncycastle.tls.crypto;

/* loaded from: classes.dex */
public class TlsECConfig {
    protected final int namedGroup;

    public TlsECConfig(int i2) {
        this.namedGroup = i2;
    }

    public int getNamedGroup() {
        return this.namedGroup;
    }
}
