package org.bouncycastle.jcajce.provider.asymmetric.ecgost12;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ECPoint;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jce.ECGOST3410NamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class BCECGOST3410_2012PrivateKey implements ECPrivateKey, org.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    static final long serialVersionUID = 7245981689601667138L;
    private String algorithm;
    private transient PKCS12BagAttributeCarrierImpl attrCarrier;

    /* renamed from: d */
    private transient BigInteger f1361d;
    private transient ECParameterSpec ecSpec;
    private transient GOST3410PublicKeyAlgParameters gostParams;
    private transient ASN1BitString publicKey;
    private boolean withCompression;

    public BCECGOST3410_2012PrivateKey() {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void extractBytes(byte[] bArr, int i2, int i3, BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length < i2) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(byteArray, 0, bArr2, i2 - byteArray.length, byteArray.length);
            byteArray = bArr2;
        }
        for (int i4 = 0; i4 != i2; i4++) {
            bArr[i3 + i4] = byteArray[(byteArray.length - 1) - i4];
        }
    }

    private ASN1BitString getPublicKeyDetails(BCECGOST3410_2012PublicKey bCECGOST3410_2012PublicKey) {
        return SubjectPublicKeyInfo.getInstance(bCECGOST3410_2012PublicKey.getEncoded()).getPublicKeyData();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x017a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void populateFromPrivKeyInfo(PrivateKeyInfo privateKeyInfo) {
        ECParameterSpec eCParameterSpec;
        ASN1Encodable parsePrivateKey;
        BigInteger value;
        ASN1Primitive aSN1Primitive = privateKeyInfo.getPrivateKeyAlgorithm().getParameters().toASN1Primitive();
        if ((aSN1Primitive instanceof ASN1Sequence) && (ASN1Sequence.getInstance(aSN1Primitive).size() == 2 || ASN1Sequence.getInstance(aSN1Primitive).size() == 3)) {
            GOST3410PublicKeyAlgParameters gOST3410PublicKeyAlgParameters = GOST3410PublicKeyAlgParameters.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
            this.gostParams = gOST3410PublicKeyAlgParameters;
            ECNamedCurveParameterSpec parameterSpec = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(gOST3410PublicKeyAlgParameters.getPublicKeyParamSet()));
            this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()), EC5Util.convertCurve(parameterSpec.getCurve(), parameterSpec.getSeed()), EC5Util.convertPoint(parameterSpec.getG()), parameterSpec.getN(), parameterSpec.getH());
            ASN1OctetString privateKey = privateKeyInfo.getPrivateKey();
            if (privateKey.getOctets().length == 32 || privateKey.getOctets().length == 64) {
                value = new BigInteger(1, Arrays.reverse(privateKey.getOctets()));
            } else {
                ASN1Encodable parsePrivateKey2 = privateKeyInfo.parsePrivateKey();
                if (!(parsePrivateKey2 instanceof ASN1Integer)) {
                    this.f1361d = new BigInteger(1, Arrays.reverse(ASN1OctetString.getInstance(parsePrivateKey2).getOctets()));
                    return;
                }
                value = ASN1Integer.getInstance(parsePrivateKey2).getPositiveValue();
            }
        } else {
            X962Parameters x962Parameters = X962Parameters.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
            if (x962Parameters.isNamedCurve()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(x962Parameters.getParameters());
                X9ECParameters namedCurveByOid = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
                if (namedCurveByOid == null) {
                    X9ECParameters byOIDX9 = ECGOST3410NamedCurves.getByOIDX9(aSN1ObjectIdentifier);
                    eCParameterSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(aSN1ObjectIdentifier), EC5Util.convertCurve(byOIDX9.getCurve(), byOIDX9.getSeed()), EC5Util.convertPoint(byOIDX9.getG()), byOIDX9.getN(), byOIDX9.getH());
                } else {
                    eCParameterSpec = new ECNamedCurveSpec(ECUtil.getCurveName(aSN1ObjectIdentifier), EC5Util.convertCurve(namedCurveByOid.getCurve(), namedCurveByOid.getSeed()), EC5Util.convertPoint(namedCurveByOid.getG()), namedCurveByOid.getN(), namedCurveByOid.getH());
                }
            } else if (x962Parameters.isImplicitlyCA()) {
                this.ecSpec = null;
                parsePrivateKey = privateKeyInfo.parsePrivateKey();
                if (parsePrivateKey instanceof ASN1Integer) {
                    org.bouncycastle.asn1.sec.ECPrivateKey eCPrivateKey = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(parsePrivateKey);
                    this.f1361d = eCPrivateKey.getKey();
                    this.publicKey = eCPrivateKey.getPublicKey();
                    return;
                }
                value = ASN1Integer.getInstance(parsePrivateKey).getValue();
            } else {
                X9ECParameters x9ECParameters = X9ECParameters.getInstance(x962Parameters.getParameters());
                eCParameterSpec = new ECParameterSpec(EC5Util.convertCurve(x9ECParameters.getCurve(), x9ECParameters.getSeed()), EC5Util.convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
            }
            this.ecSpec = eCParameterSpec;
            parsePrivateKey = privateKeyInfo.parsePrivateKey();
            if (parsePrivateKey instanceof ASN1Integer) {
            }
        }
        this.f1361d = value;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    public org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        return eCParameterSpec != null ? EC5Util.convertSpec(eCParameterSpec) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BCECGOST3410_2012PrivateKey)) {
            return false;
        }
        BCECGOST3410_2012PrivateKey bCECGOST3410_2012PrivateKey = (BCECGOST3410_2012PrivateKey) obj;
        return getD().equals(bCECGOST3410_2012PrivateKey.getD()) && engineGetSpec().equals(bCECGOST3410_2012PrivateKey.engineGetSpec());
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    @Override // org.bouncycastle.jce.interfaces.ECPrivateKey
    public BigInteger getD() {
        return this.f1361d;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00db  */
    @Override // java.security.Key
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] getEncoded() {
        X962Parameters x962Parameters;
        int orderBitLength;
        boolean z2 = this.f1361d.bitLength() > 256;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = z2 ? RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512 : RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256;
        int i2 = z2 ? 64 : 32;
        if (this.gostParams != null) {
            byte[] bArr = new byte[i2];
            extractBytes(bArr, i2, 0, getS());
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(aSN1ObjectIdentifier, this.gostParams), new DEROctetString(bArr)).getEncoded(ASN1Encoding.DER);
            } catch (IOException unused) {
                return null;
            }
        }
        ECParameterSpec eCParameterSpec = this.ecSpec;
        try {
            if (eCParameterSpec instanceof ECNamedCurveSpec) {
                ASN1ObjectIdentifier namedCurveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec) eCParameterSpec).getName());
                if (namedCurveOid == null) {
                    namedCurveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.ecSpec).getName());
                }
                x962Parameters = new X962Parameters(namedCurveOid);
            } else {
                if (eCParameterSpec == null) {
                    x962Parameters = new X962Parameters((ASN1Null) DERNull.INSTANCE);
                    orderBitLength = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, null, getS());
                    return new PrivateKeyInfo(new AlgorithmIdentifier(aSN1ObjectIdentifier, x962Parameters.toASN1Primitive()), (this.publicKey == null ? new org.bouncycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), this.publicKey, x962Parameters) : new org.bouncycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), x962Parameters)).toASN1Primitive()).getEncoded(ASN1Encoding.DER);
                }
                ECCurve convertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
                x962Parameters = new X962Parameters(new X9ECParameters(convertCurve, new X9ECPoint(EC5Util.convertPoint(convertCurve, this.ecSpec.getGenerator()), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
            }
            return new PrivateKeyInfo(new AlgorithmIdentifier(aSN1ObjectIdentifier, x962Parameters.toASN1Primitive()), (this.publicKey == null ? new org.bouncycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), this.publicKey, x962Parameters) : new org.bouncycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), x962Parameters)).toASN1Primitive()).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused2) {
            return null;
        }
        orderBitLength = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, this.ecSpec.getOrder(), getS());
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
    }

    @Override // org.bouncycastle.jce.interfaces.ECKey
    public org.bouncycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.convertSpec(eCParameterSpec);
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.ecSpec;
    }

    @Override // java.security.interfaces.ECPrivateKey
    public BigInteger getS() {
        return this.f1361d;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    @Override // org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    @Override // org.bouncycastle.jce.interfaces.ECPointEncoder
    public void setPointFormat(String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        return ECUtil.privateKeyToString(this.algorithm, this.f1361d, engineGetSpec());
    }

    public BCECGOST3410_2012PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.algorithm = str;
        this.f1361d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    public BCECGOST3410_2012PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECGOST3410_2012PublicKey bCECGOST3410_2012PublicKey, ECParameterSpec eCParameterSpec) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f1361d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.gostParams = bCECGOST3410_2012PublicKey.getGostParams();
        this.publicKey = getPublicKeyDetails(bCECGOST3410_2012PublicKey);
    }

    public BCECGOST3410_2012PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECGOST3410_2012PublicKey bCECGOST3410_2012PublicKey, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f1361d = eCPrivateKeyParameters.getD();
        this.ecSpec = eCParameterSpec == null ? new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue()) : new ECParameterSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        this.gostParams = bCECGOST3410_2012PublicKey.getGostParams();
        this.publicKey = getPublicKeyDetails(bCECGOST3410_2012PublicKey);
    }

    public BCECGOST3410_2012PrivateKey(ECPrivateKey eCPrivateKey) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.f1361d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    public BCECGOST3410_2012PrivateKey(ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.f1361d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    public BCECGOST3410_2012PrivateKey(PrivateKeyInfo privateKeyInfo) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    public BCECGOST3410_2012PrivateKey(BCECGOST3410_2012PrivateKey bCECGOST3410_2012PrivateKey) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.f1361d = bCECGOST3410_2012PrivateKey.f1361d;
        this.ecSpec = bCECGOST3410_2012PrivateKey.ecSpec;
        this.withCompression = bCECGOST3410_2012PrivateKey.withCompression;
        this.attrCarrier = bCECGOST3410_2012PrivateKey.attrCarrier;
        this.publicKey = bCECGOST3410_2012PrivateKey.publicKey;
        this.gostParams = bCECGOST3410_2012PrivateKey.gostParams;
    }

    public BCECGOST3410_2012PrivateKey(org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = "ECGOST3410-2012";
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.f1361d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
    }
}
