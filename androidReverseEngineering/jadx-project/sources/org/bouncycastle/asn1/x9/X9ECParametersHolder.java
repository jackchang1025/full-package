package org.bouncycastle.asn1.x9;

/* loaded from: classes.dex */
public abstract class X9ECParametersHolder {
    private X9ECParameters params;

    public abstract X9ECParameters createParameters();

    public synchronized X9ECParameters getParameters() {
        if (this.params == null) {
            this.params = createParameters();
        }
        return this.params;
    }
}
