package org.bouncycastle.crypto.engines;

import org.bouncycastle.util.Memoable;

/* loaded from: classes.dex */
public final class Zuc256Engine extends Zuc256CoreEngine {
    public Zuc256Engine() {
    }

    public Zuc256Engine(int i2) {
        super(i2);
    }

    @Override // org.bouncycastle.crypto.engines.Zuc256CoreEngine, org.bouncycastle.crypto.engines.Zuc128CoreEngine, org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new Zuc256Engine(this);
    }

    private Zuc256Engine(Zuc256Engine zuc256Engine) {
        super(zuc256Engine);
    }
}
