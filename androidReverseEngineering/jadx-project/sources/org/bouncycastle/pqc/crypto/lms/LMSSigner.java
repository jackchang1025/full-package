package org.bouncycastle.pqc.crypto.lms;

import java.io.IOException;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.crypto.MessageSigner;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class LMSSigner implements MessageSigner {
    private LMSPrivateKeyParameters privKey;
    private LMSPublicKeyParameters pubKey;

    @Override // org.bouncycastle.pqc.crypto.MessageSigner
    public byte[] generateSignature(byte[] bArr) {
        try {
            return LMS.generateSign(this.privKey, bArr).getEncoded();
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("unable to encode signature: ")));
        }
    }

    @Override // org.bouncycastle.pqc.crypto.MessageSigner
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (z2) {
            this.privKey = (LMSPrivateKeyParameters) cipherParameters;
        } else {
            this.pubKey = (LMSPublicKeyParameters) cipherParameters;
        }
    }

    @Override // org.bouncycastle.pqc.crypto.MessageSigner
    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        try {
            return LMS.verifySignature(this.pubKey, LMSSignature.getInstance(bArr2), bArr);
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("unable to decode signature: ")));
        }
    }
}
