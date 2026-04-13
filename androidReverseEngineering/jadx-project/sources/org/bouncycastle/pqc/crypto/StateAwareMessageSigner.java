package org.bouncycastle.pqc.crypto;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes.dex */
public interface StateAwareMessageSigner extends MessageSigner {
    AsymmetricKeyParameter getUpdatedPrivateKey();
}
