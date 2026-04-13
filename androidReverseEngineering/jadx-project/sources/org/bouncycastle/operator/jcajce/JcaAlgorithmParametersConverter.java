package org.bouncycastle.operator.jcajce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JcaAlgorithmParametersConverter {
    public AlgorithmIdentifier getAlgorithmIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, AlgorithmParameters algorithmParameters) {
        try {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, ASN1Primitive.fromByteArray(algorithmParameters.getEncoded()));
        } catch (IOException e2) {
            throw new InvalidAlgorithmParameterException(AbstractC0000a.m8d(e2, new StringBuilder("unable to encode parameters object: ")));
        }
    }

    public AlgorithmIdentifier getAlgorithmIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof OAEPParameterSpec)) {
            throw new InvalidAlgorithmParameterException("unknown parameter spec passed.");
        }
        if (algorithmParameterSpec.equals(OAEPParameterSpec.DEFAULT)) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, new RSAESOAEPparams(RSAESOAEPparams.DEFAULT_HASH_ALGORITHM, RSAESOAEPparams.DEFAULT_MASK_GEN_FUNCTION, RSAESOAEPparams.DEFAULT_P_SOURCE_ALGORITHM));
        }
        OAEPParameterSpec oAEPParameterSpec = (OAEPParameterSpec) algorithmParameterSpec;
        PSource pSource = oAEPParameterSpec.getPSource();
        if (!oAEPParameterSpec.getMGFAlgorithm().equals(OAEPParameterSpec.DEFAULT.getMGFAlgorithm())) {
            throw new InvalidAlgorithmParameterException("only " + OAEPParameterSpec.DEFAULT.getMGFAlgorithm() + " mask generator supported.");
        }
        AlgorithmIdentifier find = new DefaultDigestAlgorithmIdentifierFinder().find(oAEPParameterSpec.getDigestAlgorithm());
        if (find.getParameters() == null) {
            find = new AlgorithmIdentifier(find.getAlgorithm(), DERNull.INSTANCE);
        }
        AlgorithmIdentifier find2 = new DefaultDigestAlgorithmIdentifierFinder().find(((MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters()).getDigestAlgorithm());
        if (find2.getParameters() == null) {
            find2 = new AlgorithmIdentifier(find2.getAlgorithm(), DERNull.INSTANCE);
        }
        return new AlgorithmIdentifier(aSN1ObjectIdentifier, new RSAESOAEPparams(find, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, find2), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(((PSource.PSpecified) pSource).getValue()))));
    }
}
