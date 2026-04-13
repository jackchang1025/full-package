package org.bouncycastle.crypto.engines;

/* loaded from: classes.dex */
public class AESWrapEngine extends RFC3394WrapEngine {
    public AESWrapEngine() {
        super(new AESEngine());
    }

    public AESWrapEngine(boolean z2) {
        super(new AESEngine(), z2);
    }
}
