package org.bouncycastle.its.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.io.DigestOutputStream;
import org.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.DSADigestSigner;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.operator.ITSContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDefaultDigestProvider;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class BcITSContentSigner implements ITSContentSigner {
    private final ASN1ObjectIdentifier curveID;
    private final Digest digest;
    private final AlgorithmIdentifier digestAlgo;
    private final byte[] parentData;
    private final byte[] parentDigest;
    private final ECPrivateKeyParameters privKey;
    private final ITSCertificate signerCert;

    public BcITSContentSigner(ECPrivateKeyParameters eCPrivateKeyParameters) {
        this(eCPrivateKeyParameters, null);
    }

    @Override // org.bouncycastle.its.operator.ITSContentSigner
    public ITSCertificate getAssociatedCertificate() {
        return this.signerCert;
    }

    @Override // org.bouncycastle.its.operator.ITSContentSigner
    public byte[] getAssociatedCertificateDigest() {
        return Arrays.clone(this.parentDigest);
    }

    @Override // org.bouncycastle.its.operator.ITSContentSigner
    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgo;
    }

    @Override // org.bouncycastle.its.operator.ITSContentSigner
    public OutputStream getOutputStream() {
        return new DigestOutputStream(this.digest);
    }

    @Override // org.bouncycastle.its.operator.ITSContentSigner
    public byte[] getSignature() {
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        this.digest.doFinal(bArr, 0);
        DSADigestSigner dSADigestSigner = new DSADigestSigner(new ECDSASigner(), this.digest);
        dSADigestSigner.init(true, this.privKey);
        dSADigestSigner.update(bArr, 0, digestSize);
        byte[] bArr2 = this.parentDigest;
        dSADigestSigner.update(bArr2, 0, bArr2.length);
        return dSADigestSigner.generateSignature();
    }

    @Override // org.bouncycastle.its.operator.ITSContentSigner
    public boolean isForSelfSigning() {
        return this.parentData == null;
    }

    public BcITSContentSigner(ECPrivateKeyParameters eCPrivateKeyParameters, ITSCertificate iTSCertificate) {
        AlgorithmIdentifier algorithmIdentifier;
        this.privKey = eCPrivateKeyParameters;
        ASN1ObjectIdentifier name = ((ECNamedDomainParameters) eCPrivateKeyParameters.getParameters()).getName();
        this.curveID = name;
        this.signerCert = iTSCertificate;
        if (name.equals((ASN1Primitive) SECObjectIdentifiers.secp256r1)) {
            algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
        } else if (name.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
        } else {
            if (!name.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP384r1)) {
                throw new IllegalArgumentException("unknown key type");
            }
            algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384);
        }
        this.digestAlgo = algorithmIdentifier;
        try {
            ExtendedDigest extendedDigest = BcDefaultDigestProvider.INSTANCE.get(this.digestAlgo);
            this.digest = extendedDigest;
            if (iTSCertificate == null) {
                this.parentData = null;
                byte[] bArr = new byte[extendedDigest.getDigestSize()];
                this.parentDigest = bArr;
                extendedDigest.doFinal(bArr, 0);
                return;
            }
            try {
                byte[] encoded = iTSCertificate.getEncoded();
                this.parentData = encoded;
                byte[] bArr2 = new byte[extendedDigest.getDigestSize()];
                this.parentDigest = bArr2;
                extendedDigest.update(encoded, 0, encoded.length);
                extendedDigest.doFinal(bArr2, 0);
            } catch (IOException e2) {
                throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("signer certificate encoding failed: ")));
            }
        } catch (OperatorCreationException unused) {
            throw new IllegalStateException("cannot recognise digest type: " + this.digestAlgo.getAlgorithm());
        }
    }
}
