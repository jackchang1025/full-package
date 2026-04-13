package org.bouncycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.AccessController;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X962Parameters;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Fingerprint;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ECUtil {
    public static int[] convertMidTerms(int[] iArr) {
        int i2;
        int[] iArr2 = new int[3];
        if (iArr.length == 1) {
            iArr2[0] = iArr[0];
        } else {
            if (iArr.length != 3) {
                throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
            }
            int i3 = iArr[0];
            int i4 = iArr[1];
            if (i3 >= i4 || i3 >= (i2 = iArr[2])) {
                int i5 = iArr[2];
                if (i4 < i5) {
                    iArr2[0] = i4;
                    int i6 = iArr[0];
                    if (i6 < i5) {
                        iArr2[1] = i6;
                        iArr2[2] = i5;
                    } else {
                        iArr2[1] = i5;
                        iArr2[2] = i6;
                    }
                } else {
                    iArr2[0] = i5;
                    int i7 = iArr[0];
                    if (i7 < i4) {
                        iArr2[1] = i7;
                        iArr2[2] = iArr[1];
                    } else {
                        iArr2[1] = i4;
                        iArr2[2] = i7;
                    }
                }
            } else {
                iArr2[0] = i3;
                if (i4 < i2) {
                    iArr2[1] = i4;
                    iArr2[2] = i2;
                } else {
                    iArr2[1] = i2;
                    iArr2[2] = iArr[1];
                }
            }
        }
        return iArr2;
    }

    public static String generateKeyFingerprint(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        ECCurve curve = eCParameterSpec.getCurve();
        return curve != null ? new Fingerprint(Arrays.concatenate(eCPoint.getEncoded(false), curve.getA().getEncoded(), curve.getB().getEncoded(), eCParameterSpec.getG().getEncoded(false))).toString() : new Fingerprint(eCPoint.getEncoded(false)).toString();
    }

    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) {
        if (privateKey instanceof ECPrivateKey) {
            ECPrivateKey eCPrivateKey = (ECPrivateKey) privateKey;
            ECParameterSpec parameters = eCPrivateKey.getParameters();
            if (parameters == null) {
                parameters = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            }
            if (!(eCPrivateKey.getParameters() instanceof ECNamedCurveParameterSpec)) {
                return new ECPrivateKeyParameters(eCPrivateKey.getD(), new ECDomainParameters(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
            }
            return new ECPrivateKeyParameters(eCPrivateKey.getD(), new ECNamedDomainParameters(ECNamedCurveTable.getOID(((ECNamedCurveParameterSpec) eCPrivateKey.getParameters()).getName()), parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
        }
        if (privateKey instanceof java.security.interfaces.ECPrivateKey) {
            java.security.interfaces.ECPrivateKey eCPrivateKey2 = (java.security.interfaces.ECPrivateKey) privateKey;
            ECParameterSpec convertSpec = EC5Util.convertSpec(eCPrivateKey2.getParams());
            return new ECPrivateKeyParameters(eCPrivateKey2.getS(), new ECDomainParameters(convertSpec.getCurve(), convertSpec.getG(), convertSpec.getN(), convertSpec.getH(), convertSpec.getSeed()));
        }
        try {
            byte[] encoded = privateKey.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("no encoding for EC private key");
            }
            PrivateKey privateKey2 = BouncyCastleProvider.getPrivateKey(PrivateKeyInfo.getInstance(encoded));
            if (privateKey2 instanceof java.security.interfaces.ECPrivateKey) {
                return generatePrivateKeyParameter(privateKey2);
            }
            throw new InvalidKeyException("can't identify EC private key.");
        } catch (Exception e2) {
            throw new InvalidKeyException(AbstractC0000a.m29y(e2, new StringBuilder("cannot identify EC private key: ")));
        }
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) {
        if (publicKey instanceof ECPublicKey) {
            ECPublicKey eCPublicKey = (ECPublicKey) publicKey;
            ECParameterSpec parameters = eCPublicKey.getParameters();
            return new ECPublicKeyParameters(eCPublicKey.getQ(), new ECDomainParameters(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed()));
        }
        if (publicKey instanceof java.security.interfaces.ECPublicKey) {
            java.security.interfaces.ECPublicKey eCPublicKey2 = (java.security.interfaces.ECPublicKey) publicKey;
            ECParameterSpec convertSpec = EC5Util.convertSpec(eCPublicKey2.getParams());
            return new ECPublicKeyParameters(EC5Util.convertPoint(eCPublicKey2.getParams(), eCPublicKey2.getW()), new ECDomainParameters(convertSpec.getCurve(), convertSpec.getG(), convertSpec.getN(), convertSpec.getH(), convertSpec.getSeed()));
        }
        try {
            byte[] encoded = publicKey.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("no encoding for EC public key");
            }
            PublicKey publicKey2 = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(encoded));
            if (publicKey2 instanceof java.security.interfaces.ECPublicKey) {
                return generatePublicKeyParameter(publicKey2);
            }
            throw new InvalidKeyException("cannot identify EC public key.");
        } catch (Exception e2) {
            throw new InvalidKeyException(AbstractC0000a.m29y(e2, new StringBuilder("cannot identify EC public key: ")));
        }
    }

    public static String getCurveName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return ECNamedCurveTable.getName(aSN1ObjectIdentifier);
    }

    public static ECDomainParameters getDomainParameters(ProviderConfiguration providerConfiguration, X962Parameters x962Parameters) {
        ECDomainParameters eCDomainParameters;
        if (x962Parameters.isNamedCurve()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(x962Parameters.getParameters());
            X9ECParameters namedCurveByOid = getNamedCurveByOid(aSN1ObjectIdentifier);
            if (namedCurveByOid == null) {
                namedCurveByOid = (X9ECParameters) providerConfiguration.getAdditionalECParameters().get(aSN1ObjectIdentifier);
            }
            return new ECNamedDomainParameters(aSN1ObjectIdentifier, namedCurveByOid);
        }
        if (x962Parameters.isImplicitlyCA()) {
            ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
            eCDomainParameters = new ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
        } else {
            X9ECParameters x9ECParameters = X9ECParameters.getInstance(x962Parameters.getParameters());
            eCDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
        }
        return eCDomainParameters;
    }

    public static String getNameFrom(final AlgorithmParameterSpec algorithmParameterSpec) {
        return (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                try {
                    return algorithmParameterSpec.getClass().getMethod("getName", new Class[0]).invoke(algorithmParameterSpec, new Object[0]);
                } catch (Exception unused) {
                    return null;
                }
            }
        });
    }

    public static X9ECParameters getNamedCurveByName(String str) {
        X9ECParameters byName = CustomNamedCurves.getByName(str);
        return byName == null ? ECNamedCurveTable.getByName(str) : byName;
    }

    public static X9ECParameters getNamedCurveByOid(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParameters byOID = CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
        return byOID == null ? ECNamedCurveTable.getByOID(aSN1ObjectIdentifier) : byOID;
    }

    public static ASN1ObjectIdentifier getNamedCurveOid(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        int indexOf = str.indexOf(32);
        if (indexOf > 0) {
            str = str.substring(indexOf + 1);
        }
        ASN1ObjectIdentifier oid = getOID(str);
        return oid != null ? oid : ECNamedCurveTable.getOID(str);
    }

    private static ASN1ObjectIdentifier getOID(String str) {
        char charAt = str.charAt(0);
        if (charAt < '0' || charAt > '2') {
            return null;
        }
        try {
            return new ASN1ObjectIdentifier(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static int getOrderBitLength(ProviderConfiguration providerConfiguration, BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger != null) {
            return bigInteger.bitLength();
        }
        ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
        return ecImplicitlyCa == null ? bigInteger2.bitLength() : ecImplicitlyCa.getN().bitLength();
    }

    public static String privateKeyToString(String str, BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        StringBuffer stringBuffer = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        ECPoint normalize = new FixedPointCombMultiplier().multiply(eCParameterSpec.getG(), bigInteger).normalize();
        stringBuffer.append(str);
        stringBuffer.append(" Private Key [");
        stringBuffer.append(generateKeyFingerprint(normalize, eCParameterSpec));
        stringBuffer.append("]");
        stringBuffer.append(lineSeparator);
        stringBuffer.append("            X: ");
        stringBuffer.append(normalize.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(lineSeparator);
        stringBuffer.append("            Y: ");
        stringBuffer.append(normalize.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(lineSeparator);
        return stringBuffer.toString();
    }

    public static String publicKeyToString(String str, ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        StringBuffer stringBuffer = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        stringBuffer.append(str);
        stringBuffer.append(" Public Key [");
        stringBuffer.append(generateKeyFingerprint(eCPoint, eCParameterSpec));
        stringBuffer.append("]");
        stringBuffer.append(lineSeparator);
        stringBuffer.append("            X: ");
        stringBuffer.append(eCPoint.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(lineSeparator);
        stringBuffer.append("            Y: ");
        stringBuffer.append(eCPoint.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(lineSeparator);
        return stringBuffer.toString();
    }

    public static ECDomainParameters getDomainParameters(ProviderConfiguration providerConfiguration, ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec instanceof ECNamedCurveParameterSpec) {
            ECNamedCurveParameterSpec eCNamedCurveParameterSpec = (ECNamedCurveParameterSpec) eCParameterSpec;
            return new ECNamedDomainParameters(getNamedCurveOid(eCNamedCurveParameterSpec.getName()), eCNamedCurveParameterSpec.getCurve(), eCNamedCurveParameterSpec.getG(), eCNamedCurveParameterSpec.getN(), eCNamedCurveParameterSpec.getH(), eCNamedCurveParameterSpec.getSeed());
        }
        if (eCParameterSpec != null) {
            return new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH(), eCParameterSpec.getSeed());
        }
        ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
        return new ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
    }

    public static ASN1ObjectIdentifier getNamedCurveOid(ECParameterSpec eCParameterSpec) {
        Enumeration names = ECNamedCurveTable.getNames();
        while (names.hasMoreElements()) {
            String str = (String) names.nextElement();
            X9ECParameters byName = ECNamedCurveTable.getByName(str);
            if (byName.getN().equals(eCParameterSpec.getN()) && byName.getH().equals(eCParameterSpec.getH()) && byName.getCurve().equals(eCParameterSpec.getCurve()) && byName.getG().equals(eCParameterSpec.getG())) {
                return ECNamedCurveTable.getOID(str);
            }
        }
        return null;
    }
}
