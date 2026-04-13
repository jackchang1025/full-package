package org.bouncycastle.its.operator;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.EccP384CurvePoint;
import org.bouncycastle.oer.its.EcdsaP256Signature;
import org.bouncycastle.oer.its.EcdsaP384Signature;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class ECDSAEncoder {
    public static Signature toITS(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(bArr);
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) SECObjectIdentifiers.secp256r1)) {
            return new Signature(0, new EcdsaP256Signature(new EccP256CurvePoint(0, new DEROctetString(BigIntegers.asUnsignedByteArray(32, ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue()))), new DEROctetString(BigIntegers.asUnsignedByteArray(32, ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).getValue()))));
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            return new Signature(1, new EcdsaP256Signature(new EccP256CurvePoint(0, new DEROctetString(BigIntegers.asUnsignedByteArray(32, ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue()))), new DEROctetString(BigIntegers.asUnsignedByteArray(32, ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).getValue()))));
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive) TeleTrusTObjectIdentifiers.brainpoolP384r1)) {
            return new Signature(3, new EcdsaP384Signature(new EccP384CurvePoint(0, new DEROctetString(BigIntegers.asUnsignedByteArray(48, ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue()))), new DEROctetString(BigIntegers.asUnsignedByteArray(48, ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).getValue()))));
        }
        throw new IllegalArgumentException("unknown curveID");
    }

    public static byte[] toX962(Signature signature) {
        byte[] octets;
        ASN1OctetString aSN1OctetString;
        if (signature.getChoice() == 0 || signature.getChoice() == 1) {
            EcdsaP256Signature ecdsaP256Signature = EcdsaP256Signature.getInstance(signature.getValue());
            octets = ASN1OctetString.getInstance(ecdsaP256Signature.getrSig().getValue()).getOctets();
            aSN1OctetString = ecdsaP256Signature.getsSig();
        } else {
            EcdsaP384Signature ecdsaP384Signature = EcdsaP384Signature.getInstance(signature.getValue());
            octets = ASN1OctetString.getInstance(ecdsaP384Signature.getrSig().getValue()).getOctets();
            aSN1OctetString = ecdsaP384Signature.getsSig();
        }
        try {
            return new DERSequence(new ASN1Encodable[]{new ASN1Integer(BigIntegers.fromUnsignedByteArray(octets)), new ASN1Integer(BigIntegers.fromUnsignedByteArray(aSN1OctetString.getOctets()))}).getEncoded();
        } catch (IOException unused) {
            throw new RuntimeException("der encoding r & s");
        }
    }
}
