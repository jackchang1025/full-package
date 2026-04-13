package org.bouncycastle.pqc.crypto.qtesla;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public final class QTESLAPrivateKeyParameters extends AsymmetricKeyParameter {
    private byte[] privateKey;
    private int securityCategory;

    public QTESLAPrivateKeyParameters(int i2, byte[] bArr) {
        super(true);
        if (bArr.length != QTESLASecurityCategory.getPrivateSize(i2)) {
            throw new IllegalArgumentException("invalid key size for security category");
        }
        this.securityCategory = i2;
        this.privateKey = Arrays.clone(bArr);
    }

    public byte[] getSecret() {
        return Arrays.clone(this.privateKey);
    }

    public int getSecurityCategory() {
        return this.securityCategory;
    }
}
