package org.bouncycastle.pqc.crypto.sphincsplus;

import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class SPHINCSPlusPublicKeyParameters extends SPHINCSPlusKeyParameters {
    private final PK pk;

    public SPHINCSPlusPublicKeyParameters(SPHINCSPlusParameters sPHINCSPlusParameters, PK pk) {
        super(false, sPHINCSPlusParameters);
        this.pk = pk;
    }

    public byte[] getEncoded() {
        PK pk = this.pk;
        return Arrays.concatenate(pk.seed, pk.root);
    }

    public byte[] getRoot() {
        return Arrays.clone(this.pk.root);
    }

    public byte[] getSeed() {
        return Arrays.clone(this.pk.seed);
    }

    public SPHINCSPlusPublicKeyParameters(SPHINCSPlusParameters sPHINCSPlusParameters, byte[] bArr) {
        super(false, sPHINCSPlusParameters);
        int i2 = sPHINCSPlusParameters.getEngine().f1615N;
        int i3 = i2 * 2;
        if (bArr.length != i3) {
            throw new IllegalArgumentException("public key encoding does not match parameters");
        }
        this.pk = new PK(Arrays.copyOfRange(bArr, 0, i2), Arrays.copyOfRange(bArr, i2, i3));
    }
}
