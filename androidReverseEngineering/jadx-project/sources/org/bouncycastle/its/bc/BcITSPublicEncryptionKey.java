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
import org.bouncycastle.its.ITSPublicEncryptionKey;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.oer.its.BasePublicEncryptionKey;
import org.bouncycastle.oer.its.EccCurvePoint;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.EccP384CurvePoint;
import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.SymmAlgorithm;

/* loaded from: classes.dex */
public class BcITSPublicEncryptionKey extends ITSPublicEncryptionKey {
    public BcITSPublicEncryptionKey(AsymmetricKeyParameter asymmetricKeyParameter) {
        super(fromKeyParameters((ECPublicKeyParameters) asymmetricKeyParameter));
    }

    public static PublicEncryptionKey fromKeyParameters(ECPublicKeyParameters eCPublicKeyParameters) {
        ASN1ObjectIdentifier name = ((ECNamedDomainParameters) eCPublicKeyParameters.getParameters()).getName();
        ECPoint q = eCPublicKeyParameters.getQ();
        if (name.equals((ASN1Primitive) SECObjectIdentifiers.secp256r1)) {
            return new PublicEncryptionKey(SymmAlgorithm.aes128Ccm, new BasePublicEncryptionKey.Builder().setChoice(0).setValue(EccP256CurvePoint.builder().createUncompressedP256(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger())).createBasePublicEncryptionKey());
        }
        if (name.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            return new PublicEncryptionKey(SymmAlgorithm.aes128Ccm, new BasePublicEncryptionKey.Builder().setChoice(1).setValue(EccP256CurvePoint.builder().createUncompressedP256(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger())).createBasePublicEncryptionKey());
        }
        throw new IllegalArgumentException("unknown curve in public encryption key");
    }

    public AsymmetricKeyParameter getKey() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        X9ECParameters byOID;
        BasePublicEncryptionKey basePublicEncryptionKey = this.encryptionKey.getBasePublicEncryptionKey();
        int choice = basePublicEncryptionKey.getChoice();
        if (choice == 0) {
            aSN1ObjectIdentifier = SECObjectIdentifiers.secp256r1;
            byOID = NISTNamedCurves.getByOID(aSN1ObjectIdentifier);
        } else {
            if (choice != 1) {
                throw new IllegalStateException("unknown key type");
            }
            aSN1ObjectIdentifier = TeleTrusTObjectIdentifiers.brainpoolP256r1;
            byOID = TeleTrusTNamedCurves.getByOID(aSN1ObjectIdentifier);
        }
        ECCurve curve = byOID.getCurve();
        if (!(this.encryptionKey.getBasePublicEncryptionKey().getValue() instanceof EccCurvePoint)) {
            throw new IllegalStateException("extension to public verification key not supported");
        }
        EccCurvePoint eccCurvePoint = (EccCurvePoint) basePublicEncryptionKey.getValue();
        if ((eccCurvePoint instanceof EccP256CurvePoint) || (eccCurvePoint instanceof EccP384CurvePoint)) {
            return new ECPublicKeyParameters(curve.decodePoint(eccCurvePoint.getEncodedPoint()).normalize(), new ECNamedDomainParameters(aSN1ObjectIdentifier, byOID));
        }
        throw new IllegalStateException("unknown key type");
    }

    public BcITSPublicEncryptionKey(PublicEncryptionKey publicEncryptionKey) {
        super(publicEncryptionKey);
    }
}
