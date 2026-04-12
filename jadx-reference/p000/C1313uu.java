package p000;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import p000.AbstractC1316ux;

/* renamed from: uu */
/* loaded from: classes2.dex */
public class C1313uu {
    private static Map customCurves = new HashMap();

    static {
        Enumeration names = C0953oi.getNames();
        while (names.hasMoreElements()) {
            String str = (String) names.nextElement();
            bi1 byName = C1338vi.getByName(str);
            if (byName != null) {
                customCurves.put(byName.getCurve(), C0953oi.getByName(str).getCurve());
            }
        }
        AbstractC1316ux curve = C0953oi.getByName("Curve25519").getCurve();
        customCurves.put(new AbstractC1316ux.a5(curve.getField().getCharacteristic(), curve.getA().toBigInteger(), curve.getB().toBigInteger(), curve.getOrder(), curve.getCofactor()), curve);
    }

    public static AbstractC1316ux convertCurve(EllipticCurve ellipticCurve) {
        ECField field = ellipticCurve.getField();
        BigInteger a = ellipticCurve.getA();
        BigInteger b = ellipticCurve.getB();
        if (field instanceof ECFieldFp) {
            AbstractC1316ux.a5 a5Var = new AbstractC1316ux.a5(((ECFieldFp) field).getP(), a, b);
            return customCurves.containsKey(a5Var) ? (AbstractC1316ux) customCurves.get(a5Var) : a5Var;
        }
        ECFieldF2m eCFieldF2m = (ECFieldF2m) field;
        int m = eCFieldF2m.getM();
        int[] iArrConvertMidTerms = C1345vp.convertMidTerms(eCFieldF2m.getMidTermsOfReductionPolynomial());
        return new AbstractC1316ux.a4(m, iArrConvertMidTerms[0], iArrConvertMidTerms[1], iArrConvertMidTerms[2], a, b);
    }

    public static ECField convertField(InterfaceC1519zj interfaceC1519zj) {
        if (C1314uv.isFpField(interfaceC1519zj)) {
            return new ECFieldFp(interfaceC1519zj.getCharacteristic());
        }
        qn0 minimalPolynomial = ((rn0) interfaceC1519zj).getMinimalPolynomial();
        int[] exponentsPresent = minimalPolynomial.getExponentsPresent();
        return new ECFieldF2m(minimalPolynomial.getDegree(), C0133bg.reverseInPlace(C0133bg.copyOfRange(exponentsPresent, 1, exponentsPresent.length - 1)));
    }

    public static AbstractC1341vl convertPoint(AbstractC1316ux abstractC1316ux, ECPoint eCPoint) {
        return abstractC1316ux.createPoint(eCPoint.getAffineX(), eCPoint.getAffineY());
    }

    public static C1340vk convertSpec(ECParameterSpec eCParameterSpec) {
        AbstractC1316ux abstractC1316uxConvertCurve = convertCurve(eCParameterSpec.getCurve());
        AbstractC1341vl abstractC1341vlConvertPoint = convertPoint(abstractC1316uxConvertCurve, eCParameterSpec.getGenerator());
        BigInteger order = eCParameterSpec.getOrder();
        BigInteger bigIntegerValueOf = BigInteger.valueOf(eCParameterSpec.getCofactor());
        byte[] seed = eCParameterSpec.getCurve().getSeed();
        return eCParameterSpec instanceof C1337vh ? new C1336vg(((C1337vh) eCParameterSpec).getName(), abstractC1316uxConvertCurve, abstractC1341vlConvertPoint, order, bigIntegerValueOf, seed) : new C1340vk(abstractC1316uxConvertCurve, abstractC1341vlConvertPoint, order, bigIntegerValueOf, seed);
    }

    public static ECParameterSpec convertToSpec(C1317uy c1317uy) {
        return new ECParameterSpec(convertCurve(c1317uy.getCurve(), null), convertPoint(c1317uy.getG()), c1317uy.getN(), c1317uy.getH().intValue());
    }

    public static AbstractC1316ux getCurve(dp0 dp0Var, zh1 zh1Var) {
        Set acceptableNamedCurves = dp0Var.getAcceptableNamedCurves();
        if (!zh1Var.isNamedCurve()) {
            if (zh1Var.isImplicitlyCA()) {
                return dp0Var.getEcImplicitlyCa().getCurve();
            }
            AbstractC0400d2 abstractC0400d2 = AbstractC0400d2.getInstance(zh1Var.getParameters());
            if (acceptableNamedCurves.isEmpty()) {
                return abstractC0400d2.size() > 3 ? bi1.getInstance(abstractC0400d2).getCurve() : C1332vc.getByOIDX9(C0160c5.getInstance(abstractC0400d2.getObjectAt(0))).getCurve();
            }
            throw new IllegalStateException("encoded parameters not acceptable");
        }
        C0160c5 c0160c5 = C0160c5.getInstance(zh1Var.getParameters());
        if (!acceptableNamedCurves.isEmpty() && !acceptableNamedCurves.contains(c0160c5)) {
            throw new IllegalStateException("named curve not acceptable");
        }
        bi1 namedCurveByOid = C1345vp.getNamedCurveByOid(c0160c5);
        if (namedCurveByOid == null) {
            namedCurveByOid = (bi1) dp0Var.getAdditionalECParameters().get(c0160c5);
        }
        return namedCurveByOid.getCurve();
    }

    public static C1317uy getDomainParameters(dp0 dp0Var, ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec != null) {
            return C1345vp.getDomainParameters(dp0Var, convertSpec(eCParameterSpec));
        }
        C1340vk ecImplicitlyCa = dp0Var.getEcImplicitlyCa();
        return new C1317uy(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
    }

    public static EllipticCurve convertCurve(AbstractC1316ux abstractC1316ux, byte[] bArr) {
        return new EllipticCurve(convertField(abstractC1316ux.getField()), abstractC1316ux.getA().toBigInteger(), abstractC1316ux.getB().toBigInteger(), null);
    }

    public static AbstractC1341vl convertPoint(ECParameterSpec eCParameterSpec, ECPoint eCPoint) {
        return convertPoint(convertCurve(eCParameterSpec.getCurve()), eCPoint);
    }

    public static ECParameterSpec convertSpec(EllipticCurve ellipticCurve, C1340vk c1340vk) {
        ECPoint eCPointConvertPoint = convertPoint(c1340vk.getG());
        return c1340vk instanceof C1336vg ? new C1337vh(((C1336vg) c1340vk).getName(), ellipticCurve, eCPointConvertPoint, c1340vk.getN(), c1340vk.getH()) : new ECParameterSpec(ellipticCurve, eCPointConvertPoint, c1340vk.getN(), c1340vk.getH().intValue());
    }

    public static ECParameterSpec convertToSpec(zh1 zh1Var, AbstractC1316ux abstractC1316ux) {
        if (zh1Var.isNamedCurve()) {
            C0160c5 c0160c5 = (C0160c5) zh1Var.getParameters();
            bi1 namedCurveByOid = C1345vp.getNamedCurveByOid(c0160c5);
            if (namedCurveByOid == null) {
                Map additionalECParameters = BouncyCastleProvider.CONFIGURATION.getAdditionalECParameters();
                if (!additionalECParameters.isEmpty()) {
                    namedCurveByOid = (bi1) additionalECParameters.get(c0160c5);
                }
            }
            return new C1337vh(C1345vp.getCurveName(c0160c5), convertCurve(abstractC1316ux, namedCurveByOid.getSeed()), convertPoint(namedCurveByOid.getG()), namedCurveByOid.getN(), namedCurveByOid.getH());
        }
        if (zh1Var.isImplicitlyCA()) {
            return null;
        }
        AbstractC0400d2 abstractC0400d2 = AbstractC0400d2.getInstance(zh1Var.getParameters());
        if (abstractC0400d2.size() > 3) {
            bi1 bi1Var = bi1.getInstance(abstractC0400d2);
            EllipticCurve ellipticCurveConvertCurve = convertCurve(abstractC1316ux, bi1Var.getSeed());
            return bi1Var.getH() != null ? new ECParameterSpec(ellipticCurveConvertCurve, convertPoint(bi1Var.getG()), bi1Var.getN(), bi1Var.getH().intValue()) : new ECParameterSpec(ellipticCurveConvertCurve, convertPoint(bi1Var.getG()), bi1Var.getN(), 1);
        }
        i20 i20Var = i20.getInstance(abstractC0400d2);
        C1336vg parameterSpec = C1331vb.getParameterSpec(C1332vc.getName(i20Var.getPublicKeyParamSet()));
        return new C1337vh(C1332vc.getName(i20Var.getPublicKeyParamSet()), convertCurve(parameterSpec.getCurve(), parameterSpec.getSeed()), convertPoint(parameterSpec.getG()), parameterSpec.getN(), parameterSpec.getH());
    }

    public static ECPoint convertPoint(AbstractC1341vl abstractC1341vl) {
        AbstractC1341vl abstractC1341vlNormalize = abstractC1341vl.normalize();
        return new ECPoint(abstractC1341vlNormalize.getAffineXCoord().toBigInteger(), abstractC1341vlNormalize.getAffineYCoord().toBigInteger());
    }

    public static ECParameterSpec convertToSpec(bi1 bi1Var) {
        return new ECParameterSpec(convertCurve(bi1Var.getCurve(), null), convertPoint(bi1Var.getG()), bi1Var.getN(), bi1Var.getH().intValue());
    }
}
