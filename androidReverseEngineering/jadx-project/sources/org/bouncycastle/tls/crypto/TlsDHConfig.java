package org.bouncycastle.tls.crypto;

/* loaded from: classes.dex */
public class TlsDHConfig {
    protected final DHGroup explicitGroup;
    protected final int namedGroup;
    protected final boolean padded;

    public TlsDHConfig(int i2, boolean z2) {
        this.explicitGroup = null;
        this.namedGroup = i2;
        this.padded = z2;
    }

    public DHGroup getExplicitGroup() {
        return this.explicitGroup;
    }

    public int getNamedGroup() {
        return this.namedGroup;
    }

    public boolean isPadded() {
        return this.padded;
    }

    public TlsDHConfig(DHGroup dHGroup) {
        this.explicitGroup = dHGroup;
        this.namedGroup = -1;
        this.padded = false;
    }
}
