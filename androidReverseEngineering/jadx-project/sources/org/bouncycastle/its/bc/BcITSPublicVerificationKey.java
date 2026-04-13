package org.bouncycastle.its.bc;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.its.ITSPublicVerificationKey;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.oer.its.EccCurvePoint;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.EccP384CurvePoint;
import org.bouncycastle.oer.its.PublicVerificationKey;

/* loaded from: classes.dex */
public class BcITSPublicVerificationKey extends ITSPublicVerificationKey {
    public BcITSPublicVerificationKey(AsymmetricKeyParameter asymmetricKeyParameter) {
        super(fromKeyParameters((ECPublicKeyParameters) asymmetricKeyParameter));
    }

    public static PublicVerificationKey fromKeyParameters(ECPublicKeyParameters eCPublicKeyParameters) {
        ASN1ObjectIdentifier name = ((ECNamedDomainParameters) eCPublicKeyParameters.getParameters()).getName();
        ECPoint q = eCPublicKeyParameters.getQ();
        if (name.equals((ASN1Primitive) SECObjectIdentifiers.secp256r1)) {
            return new PublicVerificationKey(0, EccP256CurvePoint.builder().createUncompressedP256(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger()));
        }
        if (name.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            return new PublicVerificationKey(1, EccP256CurvePoint.builder().createUncompressedP256(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger()));
        }
        if (name.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP384r1)) {
            return new PublicVerificationKey(3, EccP384CurvePoint.builder().createUncompressedP384(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger()));
        }
        throw new IllegalArgumentException("unknown curve in public encryption key");
    }

    public AsymmetricKeyParameter getKey() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        X9ECParameters byOID;
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
            aSN1ObjectIdentifier = SECObjectIdentifiers.secp256r1;
            byOID = NISTNamedCurves.getByOID(aSN1ObjectIdentifier);
        }
        ECCurve curve = byOID.getCurve();
        if (!(this.verificationKey.getCurvePoint() instanceof EccCurvePoint)) {
            throw new IllegalStateException("extension to public verification key not supported");
        }
        EccCurvePoint eccCurvePoint = (EccCurvePoint) this.verificationKey.getCurvePoint();
        if ((eccCurvePoint instanceof EccP256CurvePoint) || (eccCurvePoint instanceof EccP384CurvePoint)) {
            return new ECPublicKeyParameters(curve.decodePoint(eccCurvePoint.getEncodedPoint()).normalize(), new ECNamedDomainParameters(aSN1ObjectIdentifier, byOID));
        }
        throw new IllegalStateException("unknown key type");
    }

    public BcITSPublicVerificationKey(PublicVerificationKey publicVerificationKey) {
        super(publicVerificationKey);
    }
}
