package org.bouncycastle.crypto.agreement;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.RawAgreement;
import org.bouncycastle.crypto.params.XDHUPrivateParameters;
import org.bouncycastle.crypto.params.XDHUPublicParameters;

/* loaded from: classes.dex */
public class XDHUnifiedAgreement implements RawAgreement {
    private XDHUPrivateParameters privParams;
    private final RawAgreement xAgreement;

    public XDHUnifiedAgreement(RawAgreement rawAgreement) {
        this.xAgreement = rawAgreement;
    }

    @Override // org.bouncycastle.crypto.RawAgreement
    public void calculateAgreement(CipherParameters cipherParameters, byte[] bArr, int i2) {
        XDHUPublicParameters xDHUPublicParameters = (XDHUPublicParameters) cipherParameters;
        this.xAgreement.init(this.privParams.getEphemeralPrivateKey());
        this.xAgreement.calculateAgreement(xDHUPublicParameters.getEphemeralPublicKey(), bArr, i2);
        this.xAgreement.init(this.privParams.getStaticPrivateKey());
        this.xAgreement.calculateAgreement(xDHUPublicParameters.getStaticPublicKey(), bArr, this.xAgreement.getAgreementSize() + i2);
    }

    @Override // org.bouncycastle.crypto.RawAgreement
    public int getAgreementSize() {
        return this.xAgreement.getAgreementSize() * 2;
    }

    @Override // org.bouncycastle.crypto.RawAgreement
    public void init(CipherParameters cipherParameters) {
        this.privParams = (XDHUPrivateParameters) cipherParameters;
    }
}
