package p000;

import java.math.BigInteger;
import java.security.AccessController;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Enumeration;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;

/* renamed from: vp */
/* loaded from: classes2.dex */
public class C1345vp {

    /* renamed from: vp$a0 */
    public static class a0 implements PrivilegedAction {
        final /* synthetic */ AlgorithmParameterSpec val$paramSpec;

        public a0(AlgorithmParameterSpec algorithmParameterSpec) {
            this.val$paramSpec = algorithmParameterSpec;
        }

        @Override // java.security.PrivilegedAction
        public Object run() {
            try {
                return this.val$paramSpec.getClass().getMethod("getName", null).invoke(this.val$paramSpec, null);
            } catch (Exception unused) {
                return null;
            }
        }
    }

    public static int[] convertMidTerms(int[] iArr) {
        int i;
        int[] iArr2 = new int[3];
        if (iArr.length == 1) {
            iArr2[0] = iArr[0];
            return iArr2;
        }
        if (iArr.length != 3) {
            throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
        }
        int i2 = iArr[0];
        int i3 = iArr[1];
        if (i2 < i3 && i2 < (i = iArr[2])) {
            iArr2[0] = i2;
            if (i3 < i) {
                iArr2[1] = i3;
                iArr2[2] = i;
                return iArr2;
            }
            iArr2[1] = i;
            iArr2[2] = iArr[1];
            return iArr2;
        }
        int i4 = iArr[2];
        if (i3 < i4) {
            iArr2[0] = i3;
            int i5 = iArr[0];
            if (i5 < i4) {
                iArr2[1] = i5;
                iArr2[2] = i4;
                return iArr2;
            }
            iArr2[1] = i4;
            iArr2[2] = i5;
            return iArr2;
        }
        iArr2[0] = i4;
        int i6 = iArr[0];
        if (i6 < i3) {
            iArr2[1] = i6;
            iArr2[2] = iArr[1];
            return iArr2;
        }
        iArr2[1] = i3;
        iArr2[2] = i6;
        return iArr2;
    }

    public static String generateKeyFingerprint(AbstractC1341vl abstractC1341vl, C1340vk c1340vk) {
        AbstractC1316ux curve = c1340vk.getCurve();
        return curve != null ? new C1518zi(C0133bg.concatenate(abstractC1341vl.getEncoded(false), curve.getA().getEncoded(), curve.getB().getEncoded(), c1340vk.getG().getEncoded(false))).toString() : new C1518zi(abstractC1341vl.getEncoded(false)).toString();
    }

    public static C0136bj generatePrivateKeyParameter(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof ECPrivateKey) {
            ECPrivateKey eCPrivateKey = (ECPrivateKey) privateKey;
            C1340vk c1340vkConvertSpec = C1313uu.convertSpec(eCPrivateKey.getParams());
            return new C1343vn(eCPrivateKey.getS(), new C1317uy(c1340vkConvertSpec.getCurve(), c1340vkConvertSpec.getG(), c1340vkConvertSpec.getN(), c1340vkConvertSpec.getH(), c1340vkConvertSpec.getSeed()));
        }
        try {
            byte[] encoded = privateKey.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("no encoding for EC private key");
            }
            PrivateKey privateKey2 = BouncyCastleProvider.getPrivateKey(io0.getInstance(encoded));
            if (privateKey2 instanceof ECPrivateKey) {
                return generatePrivateKeyParameter(privateKey2);
            }
            throw new InvalidKeyException("can't identify EC private key.");
        } catch (Exception e) {
            throw new InvalidKeyException(AbstractC0003a2.m27a8(e, new StringBuilder("cannot identify EC private key: ")));
        }
    }

    public static C0136bj generatePublicKeyParameter(PublicKey publicKey) throws InvalidKeyException {
        if (publicKey instanceof ECPublicKey) {
            ECPublicKey eCPublicKey = (ECPublicKey) publicKey;
            C1340vk c1340vkConvertSpec = C1313uu.convertSpec(eCPublicKey.getParams());
            return new C1344vo(C1313uu.convertPoint(eCPublicKey.getParams(), eCPublicKey.getW()), new C1317uy(c1340vkConvertSpec.getCurve(), c1340vkConvertSpec.getG(), c1340vkConvertSpec.getN(), c1340vkConvertSpec.getH(), c1340vkConvertSpec.getSeed()));
        }
        try {
            byte[] encoded = publicKey.getEncoded();
            if (encoded == null) {
                throw new InvalidKeyException("no encoding for EC public key");
            }
            PublicKey publicKey2 = BouncyCastleProvider.getPublicKey(u21.getInstance(encoded));
            if (publicKey2 instanceof ECPublicKey) {
                return generatePublicKeyParameter(publicKey2);
            }
            throw new InvalidKeyException("cannot identify EC public key.");
        } catch (Exception e) {
            throw new InvalidKeyException(AbstractC0003a2.m27a8(e, new StringBuilder("cannot identify EC public key: ")));
        }
    }

    public static String getCurveName(C0160c5 c0160c5) {
        return C1338vi.getName(c0160c5);
    }

    public static C1317uy getDomainParameters(dp0 dp0Var, C1340vk c1340vk) {
        if (c1340vk instanceof C1336vg) {
            C1336vg c1336vg = (C1336vg) c1340vk;
            return new C1339vj(getNamedCurveOid(c1336vg.getName()), c1336vg.getCurve(), c1336vg.getG(), c1336vg.getN(), c1336vg.getH(), c1336vg.getSeed());
        }
        if (c1340vk != null) {
            return new C1317uy(c1340vk.getCurve(), c1340vk.getG(), c1340vk.getN(), c1340vk.getH(), c1340vk.getSeed());
        }
        C1340vk ecImplicitlyCa = dp0Var.getEcImplicitlyCa();
        return new C1317uy(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
    }

    public static String getNameFrom(AlgorithmParameterSpec algorithmParameterSpec) {
        return (String) AccessController.doPrivileged(new a0(algorithmParameterSpec));
    }

    public static bi1 getNamedCurveByName(String str) {
        bi1 byName = C0953oi.getByName(str);
        return byName == null ? C1338vi.getByName(str) : byName;
    }

    public static bi1 getNamedCurveByOid(C0160c5 c0160c5) {
        bi1 byOID = C0953oi.getByOID(c0160c5);
        return byOID == null ? C1338vi.getByOID(c0160c5) : byOID;
    }

    public static C0160c5 getNamedCurveOid(C1340vk c1340vk) {
        Enumeration names = C1338vi.getNames();
        while (names.hasMoreElements()) {
            String str = (String) names.nextElement();
            bi1 byName = C1338vi.getByName(str);
            if (byName.getN().equals(c1340vk.getN()) && byName.getH().equals(c1340vk.getH()) && byName.getCurve().equals(c1340vk.getCurve()) && byName.getG().equals(c1340vk.getG())) {
                return C1338vi.getOID(str);
            }
        }
        return null;
    }

    private static C0160c5 getOID(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt < '0' || cCharAt > '2') {
            return null;
        }
        try {
            return new C0160c5(str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static int getOrderBitLength(dp0 dp0Var, BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger != null) {
            return bigInteger.bitLength();
        }
        C1340vk ecImplicitlyCa = dp0Var.getEcImplicitlyCa();
        return ecImplicitlyCa == null ? bigInteger2.bitLength() : ecImplicitlyCa.getN().bitLength();
    }

    public static String privateKeyToString(String str, BigInteger bigInteger, C1340vk c1340vk) {
        StringBuffer stringBuffer = new StringBuffer();
        String strLineSeparator = Strings.lineSeparator();
        AbstractC1341vl abstractC1341vlNormalize = new C1522zm().multiply(c1340vk.getG(), bigInteger).normalize();
        stringBuffer.append(str);
        stringBuffer.append(" Private Key [");
        stringBuffer.append(generateKeyFingerprint(abstractC1341vlNormalize, c1340vk));
        stringBuffer.append("]");
        stringBuffer.append(strLineSeparator);
        stringBuffer.append("            X: ");
        stringBuffer.append(abstractC1341vlNormalize.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(strLineSeparator);
        stringBuffer.append("            Y: ");
        stringBuffer.append(abstractC1341vlNormalize.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(strLineSeparator);
        return stringBuffer.toString();
    }

    public static String publicKeyToString(String str, AbstractC1341vl abstractC1341vl, C1340vk c1340vk) {
        StringBuffer stringBuffer = new StringBuffer();
        String strLineSeparator = Strings.lineSeparator();
        stringBuffer.append(str);
        stringBuffer.append(" Public Key [");
        stringBuffer.append(generateKeyFingerprint(abstractC1341vl, c1340vk));
        stringBuffer.append("]");
        stringBuffer.append(strLineSeparator);
        stringBuffer.append("            X: ");
        stringBuffer.append(abstractC1341vl.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(strLineSeparator);
        stringBuffer.append("            Y: ");
        stringBuffer.append(abstractC1341vl.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(strLineSeparator);
        return stringBuffer.toString();
    }

    public static C1317uy getDomainParameters(dp0 dp0Var, zh1 zh1Var) {
        if (zh1Var.isNamedCurve()) {
            C0160c5 c0160c5 = C0160c5.getInstance(zh1Var.getParameters());
            bi1 namedCurveByOid = getNamedCurveByOid(c0160c5);
            if (namedCurveByOid == null) {
                namedCurveByOid = (bi1) dp0Var.getAdditionalECParameters().get(c0160c5);
            }
            return new C1339vj(c0160c5, namedCurveByOid);
        }
        if (zh1Var.isImplicitlyCA()) {
            C1340vk ecImplicitlyCa = dp0Var.getEcImplicitlyCa();
            return new C1317uy(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
        }
        bi1 bi1Var = bi1.getInstance(zh1Var.getParameters());
        return new C1317uy(bi1Var.getCurve(), bi1Var.getG(), bi1Var.getN(), bi1Var.getH(), bi1Var.getSeed());
    }

    public static C0160c5 getNamedCurveOid(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        int iIndexOf = str.indexOf(32);
        if (iIndexOf > 0) {
            str = str.substring(iIndexOf + 1);
        }
        C0160c5 oid = getOID(str);
        return oid != null ? oid : C1338vi.getOID(str);
    }
}
