package org.bouncycastle.crypto.agreement.kdf;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.DigestDerivationFunction;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ECDHKEKGenerator implements DigestDerivationFunction {
    private ASN1ObjectIdentifier algorithm;
    private DigestDerivationFunction kdf;
    private int keySize;

    /* renamed from: z */
    private byte[] f1134z;

    public ECDHKEKGenerator(Digest digest) {
        this.kdf = new KDF2BytesGenerator(digest);
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public int generateBytes(byte[] bArr, int i2, int i3) {
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("output buffer too small");
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new AlgorithmIdentifier(this.algorithm, DERNull.INSTANCE));
        aSN1EncodableVector.add(new DERTaggedObject(true, 2, (ASN1Encodable) new DEROctetString(Pack.intToBigEndian(this.keySize))));
        try {
            this.kdf.init(new KDFParameters(this.f1134z, new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER)));
            return this.kdf.generateBytes(bArr, i2, i3);
        } catch (IOException e2) {
            throw new IllegalArgumentException(AbstractC0000a.m8d(e2, new StringBuilder("unable to initialise kdf: ")));
        }
    }

    @Override // org.bouncycastle.crypto.DigestDerivationFunction
    public Digest getDigest() {
        return this.kdf.getDigest();
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public void init(DerivationParameters derivationParameters) {
        DHKDFParameters dHKDFParameters = (DHKDFParameters) derivationParameters;
        this.algorithm = dHKDFParameters.getAlgorithm();
        this.keySize = dHKDFParameters.getKeySize();
        this.f1134z = dHKDFParameters.getZ();
    }
}
