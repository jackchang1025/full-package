package org.bouncycastle.its.jcajce;

import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPublicKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.its.ITSPublicVerificationKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.oer.its.EccCurvePoint;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.EccP384CurvePoint;
import org.bouncycastle.oer.its.PublicVerificationKey;

/* loaded from: classes.dex */
public class JcaITSPublicVerificationKey extends ITSPublicVerificationKey {
    private final JcaJceHelper helper;

    public static class Builder {
        private JcaJceHelper helper = new DefaultJcaJceHelper();

        public JcaITSPublicVerificationKey build(PublicKey publicKey) {
            return new JcaITSPublicVerificationKey(publicKey, this.helper);
        }

        public Builder setProvider(String str) {
            this.helper = new NamedJcaJceHelper(str);
            return this;
        }

        public JcaITSPublicVerificationKey build(PublicVerificationKey publicVerificationKey) {
            return new JcaITSPublicVerificationKey(publicVerificationKey, this.helper);
        }

        public Builder setProvider(Provider provider) {
            this.helper = new ProviderJcaJceHelper(provider);
            return this;
        }
    }

    public JcaITSPublicVerificationKey(PublicKey publicKey, JcaJceHelper jcaJceHelper) {
        super(fromKeyParameters((ECPublicKey) publicKey));
        this.helper = jcaJceHelper;
    }

    public static PublicVerificationKey fromKeyParameters(ECPublicKey eCPublicKey) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(SubjectPublicKeyInfo.getInstance(eCPublicKey.getEncoded()).getAlgorithm().getParameters());
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) SECObjectIdentifiers.secp256r1)) {
            return new PublicVerificationKey(0, EccP256CurvePoint.builder().createUncompressedP256(eCPublicKey.getW().getAffineX(), eCPublicKey.getW().getAffineY()));
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            return new PublicVerificationKey(1, EccP256CurvePoint.builder().createUncompressedP256(eCPublicKey.getW().getAffineX(), eCPublicKey.getW().getAffineY()));
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP384r1)) {
            return new PublicVerificationKey(3, EccP384CurvePoint.builder().createUncompressedP384(eCPublicKey.getW().getAffineX(), eCPublicKey.getW().getAffineY()));
        }
        throw new IllegalArgumentException("unknown curve in public encryption key");
    }

    public PublicKey getKey() {
        X9ECParameters byOID;
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        int choice = this.verificationKey.getChoice();
        if (choice != 0) {
            if (choice == 1) {
                aSN1ObjectIdentifier = TeleTrusTObjectIdentifiers.brainpoolP256r1;
            } else {
                if (choice != 3) {
                    throw new IllegalStateException("unknown key type");
                }
                aSN1ObjectIdentifier = TeleTrusTObjectIdentifiers.brainpoolP384r1;
            }
            byOID = TeleTrusTNamedCurves.getByOID(aSN1ObjectIdentifier);
        } else {
            byOID = NISTNamedCurves.getByOID(SECObjectIdentifiers.secp256r1);
        }
        ECCurve curve = byOID.getCurve();
        if (!(this.verificationKey.getCurvePoint() instanceof EccCurvePoint)) {
            throw new IllegalStateException("extension to public verification key not supported");
        }
        EccCurvePoint eccCurvePoint = (EccCurvePoint) this.verificationKey.getCurvePoint();
        if (!(eccCurvePoint instanceof EccP256CurvePoint) && !(eccCurvePoint instanceof EccP384CurvePoint)) {
            throw new IllegalStateException("unknown key type");
        }
        ECPoint normalize = curve.decodePoint(eccCurvePoint.getEncodedPoint()).normalize();
        try {
            return this.helper.createKeyFactory("EC").generatePublic(new ECPublicKeySpec(EC5Util.convertPoint(normalize), EC5Util.convertToSpec(byOID)));
        } catch (Exception e2) {
            throw new IllegalStateException(e2.getMessage(), e2);
        }
    }

    public JcaITSPublicVerificationKey(PublicVerificationKey publicVerificationKey, JcaJceHelper jcaJceHelper) {
        super(publicVerificationKey);
        this.helper = jcaJceHelper;
    }
}
