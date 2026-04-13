package org.bouncycastle.operator.bc;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.signers.Ed448Signer;
import org.bouncycastle.crypto.util.PublicKeyFactory;

/* loaded from: classes.dex */
public class BcEdDSAContentVerifierProviderBuilder extends BcContentVerifierProviderBuilder {
    public static final byte[] DEFAULT_CONTEXT = new byte[0];

    @Override // org.bouncycastle.operator.bc.BcContentVerifierProviderBuilder
    public Signer createSigner(AlgorithmIdentifier algorithmIdentifier) {
        return algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) EdECObjectIdentifiers.id_Ed448) ? new Ed448Signer(DEFAULT_CONTEXT) : new Ed25519Signer();
    }

    @Override // org.bouncycastle.operator.bc.BcContentVerifierProviderBuilder
    public AsymmetricKeyParameter extractKeyParameters(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        return PublicKeyFactory.createKey(subjectPublicKeyInfo);
    }
}
