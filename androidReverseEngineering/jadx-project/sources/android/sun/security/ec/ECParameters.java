package android.sun.security.ec;

import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.AlgorithmParametersSpi;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidParameterSpecException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class ECParameters extends AlgorithmParametersSpi {
    private ECParameterSpec paramSpec;

    public static ECParameterSpec decodeParameters(byte[] bArr) {
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 6) {
            throw new IOException("Only named ECParameters supported");
        }
        ObjectIdentifier oid = derValue.getOID();
        ECParameterSpec eCParameterSpec = NamedCurve.getECParameterSpec(oid);
        if (eCParameterSpec != null) {
            return eCParameterSpec;
        }
        throw new IOException("Unknown named curve: " + oid);
    }

    public static ECPoint decodePoint(byte[] bArr, EllipticCurve ellipticCurve) {
        if (bArr.length == 0 || bArr[0] != 4) {
            throw new IOException("Only uncompressed point format supported");
        }
        int fieldSize = (ellipticCurve.getField().getFieldSize() + 7) >> 3;
        if (bArr.length != (fieldSize * 2) + 1) {
            throw new IOException("Point does not match field size");
        }
        byte[] bArr2 = new byte[fieldSize];
        byte[] bArr3 = new byte[fieldSize];
        System.arraycopy(bArr, 1, bArr2, 0, fieldSize);
        System.arraycopy(bArr, fieldSize + 1, bArr3, 0, fieldSize);
        return new ECPoint(new BigInteger(1, bArr2), new BigInteger(1, bArr3));
    }

    public static byte[] encodeParameters(ECParameterSpec eCParameterSpec) {
        NamedCurve namedCurve = getNamedCurve(eCParameterSpec);
        if (namedCurve != null) {
            return namedCurve.getEncoded();
        }
        throw new RuntimeException("Not a known named curve: " + eCParameterSpec);
    }

    public static byte[] encodePoint(ECPoint eCPoint, EllipticCurve ellipticCurve) {
        int fieldSize = (ellipticCurve.getField().getFieldSize() + 7) >> 3;
        byte[] trimZeroes = trimZeroes(eCPoint.getAffineX().toByteArray());
        byte[] trimZeroes2 = trimZeroes(eCPoint.getAffineY().toByteArray());
        if (trimZeroes.length > fieldSize || trimZeroes2.length > fieldSize) {
            throw new RuntimeException("Point coordinates do not match field size");
        }
        int i2 = (fieldSize << 1) + 1;
        byte[] bArr = new byte[i2];
        bArr[0] = 4;
        System.arraycopy(trimZeroes, 0, bArr, (fieldSize - trimZeroes.length) + 1, trimZeroes.length);
        System.arraycopy(trimZeroes2, 0, bArr, i2 - trimZeroes2.length, trimZeroes2.length);
        return bArr;
    }

    public static AlgorithmParameters getAlgorithmParameters(ECParameterSpec eCParameterSpec) {
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC", ECKeyFactory.ecInternalProvider);
            algorithmParameters.init(eCParameterSpec);
            return algorithmParameters;
        } catch (GeneralSecurityException e2) {
            throw new InvalidKeyException("EC parameters error", e2);
        }
    }

    public static String getCurveName(ECParameterSpec eCParameterSpec) {
        NamedCurve namedCurve = getNamedCurve(eCParameterSpec);
        if (namedCurve == null) {
            return null;
        }
        return namedCurve.getObjectIdentifier().toString();
    }

    public static NamedCurve getNamedCurve(ECParameterSpec eCParameterSpec) {
        if ((eCParameterSpec instanceof NamedCurve) || eCParameterSpec == null) {
            return (NamedCurve) eCParameterSpec;
        }
        int fieldSize = eCParameterSpec.getCurve().getField().getFieldSize();
        for (ECParameterSpec eCParameterSpec2 : NamedCurve.knownECParameterSpecs()) {
            if (eCParameterSpec2.getCurve().getField().getFieldSize() == fieldSize && eCParameterSpec2.getCurve().equals(eCParameterSpec.getCurve()) && eCParameterSpec2.getGenerator().equals(eCParameterSpec.getGenerator()) && eCParameterSpec2.getOrder().equals(eCParameterSpec.getOrder()) && eCParameterSpec2.getCofactor() == eCParameterSpec.getCofactor()) {
                return (NamedCurve) eCParameterSpec2;
            }
        }
        return null;
    }

    public static byte[] trimZeroes(byte[] bArr) {
        int i2 = 0;
        while (i2 < bArr.length - 1 && bArr[i2] == 0) {
            i2++;
        }
        if (i2 == 0) {
            return bArr;
        }
        int length = bArr.length - i2;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i2, bArr2, 0, length);
        return bArr2;
    }

    @Override // java.security.AlgorithmParametersSpi
    public byte[] engineGetEncoded() {
        return encodeParameters(this.paramSpec);
    }

    @Override // java.security.AlgorithmParametersSpi
    public <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> cls) {
        if (cls.isAssignableFrom(ECParameterSpec.class)) {
            return this.paramSpec;
        }
        if (cls.isAssignableFrom(ECGenParameterSpec.class)) {
            return new ECGenParameterSpec(getCurveName(this.paramSpec));
        }
        throw new InvalidParameterSpecException("Only ECParameterSpec and ECGenParameterSpec supported");
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (algorithmParameterSpec instanceof ECParameterSpec) {
            NamedCurve namedCurve = getNamedCurve((ECParameterSpec) algorithmParameterSpec);
            this.paramSpec = namedCurve;
            if (namedCurve != null) {
                return;
            }
            throw new InvalidParameterSpecException("Not a supported named curve: " + algorithmParameterSpec);
        }
        if (!(algorithmParameterSpec instanceof ECGenParameterSpec)) {
            if (algorithmParameterSpec != null) {
                throw new InvalidParameterSpecException("Only ECParameterSpec and ECGenParameterSpec supported");
            }
            throw new InvalidParameterSpecException("paramSpec must not be null");
        }
        String name = ((ECGenParameterSpec) algorithmParameterSpec).getName();
        ECParameterSpec eCParameterSpec = NamedCurve.getECParameterSpec(name);
        if (eCParameterSpec == null) {
            throw new InvalidParameterSpecException(AbstractC0000a.m15k("Unknown curve: ", name));
        }
        this.paramSpec = eCParameterSpec;
    }

    @Override // java.security.AlgorithmParametersSpi
    public String engineToString() {
        return this.paramSpec.toString();
    }

    @Override // java.security.AlgorithmParametersSpi
    public byte[] engineGetEncoded(String str) {
        return engineGetEncoded();
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(byte[] bArr) {
        this.paramSpec = decodeParameters(bArr);
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(byte[] bArr, String str) {
        engineInit(bArr);
    }
}
