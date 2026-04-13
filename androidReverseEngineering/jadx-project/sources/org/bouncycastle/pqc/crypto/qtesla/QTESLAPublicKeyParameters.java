package org.bouncycastle.pqc.crypto.qtesla;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public final class QTESLAPublicKeyParameters extends AsymmetricKeyParameter {
    private byte[] publicKey;
    private int securityCategory;

    public QTESLAPublicKeyParameters(int i2, byte[] bArr) {
        super(false);
        if (bArr.length != QTESLASecurityCategory.getPublicSize(i2)) {
            throw new IllegalArgumentException("invalid key size for security category");
        }
        this.securityCategory = i2;
        this.publicKey = Arrays.clone(bArr);
    }

    public byte[] getPublicData() {
        return Arrays.clone(this.publicKey);
    }

    public int getSecurityCategory() {
        return this.securityCategory;
    }
}
