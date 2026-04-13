package org.bouncycastle.crypto.agreement.kdf;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.crypto.DerivationParameters;

/* loaded from: classes.dex */
public class DHKDFParameters implements DerivationParameters {
    private ASN1ObjectIdentifier algorithm;
    private byte[] extraInfo;
    private int keySize;

    /* renamed from: z */
    private byte[] f1132z;

    public DHKDFParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, byte[] bArr) {
        this(aSN1ObjectIdentifier, i2, bArr, null);
    }

    public ASN1ObjectIdentifier getAlgorithm() {
        return this.algorithm;
    }

    public byte[] getExtraInfo() {
        return this.extraInfo;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public byte[] getZ() {
        return this.f1132z;
    }

    public DHKDFParameters(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, byte[] bArr, byte[] bArr2) {
        this.algorithm = aSN1ObjectIdentifier;
        this.keySize = i2;
        this.f1132z = bArr;
        this.extraInfo = bArr2;
    }
}
