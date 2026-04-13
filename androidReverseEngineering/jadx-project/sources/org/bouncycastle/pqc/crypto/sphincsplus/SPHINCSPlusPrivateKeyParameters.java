package org.bouncycastle.pqc.crypto.sphincsplus;

import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class SPHINCSPlusPrivateKeyParameters extends SPHINCSPlusKeyParameters {
    final PK pk;
    final SK sk;

    public SPHINCSPlusPrivateKeyParameters(SPHINCSPlusParameters sPHINCSPlusParameters, SK sk, PK pk) {
        super(true, sPHINCSPlusParameters);
        this.sk = sk;
        this.pk = pk;
    }

    public byte[] getEncoded() {
        SK sk = this.sk;
        byte[] bArr = sk.seed;
        byte[] bArr2 = sk.prf;
        PK pk = this.pk;
        return Arrays.concatenate(bArr, bArr2, pk.seed, pk.root);
    }

    public byte[] getPrf() {
        return Arrays.clone(this.sk.prf);
    }

    public byte[] getPublicKey() {
        PK pk = this.pk;
        return Arrays.concatenate(pk.seed, pk.root);
    }

    public byte[] getPublicSeed() {
        return Arrays.clone(this.pk.seed);
    }

    public byte[] getSeed() {
        return Arrays.clone(this.sk.seed);
    }

    public SPHINCSPlusPrivateKeyParameters(SPHINCSPlusParameters sPHINCSPlusParameters, byte[] bArr) {
        super(true, sPHINCSPlusParameters);
        int i2 = sPHINCSPlusParameters.getEngine().f1615N;
        int i3 = i2 * 4;
        if (bArr.length != i3) {
            throw new IllegalArgumentException("private key encoding does not match parameters");
        }
        int i4 = i2 * 2;
        this.sk = new SK(Arrays.copyOfRange(bArr, 0, i2), Arrays.copyOfRange(bArr, i2, i4));
        int i5 = i2 * 3;
        this.pk = new PK(Arrays.copyOfRange(bArr, i4, i5), Arrays.copyOfRange(bArr, i5, i3));
    }
}
