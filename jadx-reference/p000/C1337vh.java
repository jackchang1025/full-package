package p000;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

/* renamed from: vh */
/* loaded from: classes2.dex */
public class C1337vh extends ECParameterSpec {
    private String name;

    public C1337vh(String str, AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        super(convertCurve(abstractC1316ux, null), C1313uu.convertPoint(abstractC1341vl), bigInteger, 1);
        this.name = str;
    }

    private static EllipticCurve convertCurve(AbstractC1316ux abstractC1316ux, byte[] bArr) {
        return new EllipticCurve(convertField(abstractC1316ux.getField()), abstractC1316ux.getA().toBigInteger(), abstractC1316ux.getB().toBigInteger(), bArr);
    }

    private static ECField convertField(InterfaceC1519zj interfaceC1519zj) {
        if (C1314uv.isFpField(interfaceC1519zj)) {
            return new ECFieldFp(interfaceC1519zj.getCharacteristic());
        }
        qn0 minimalPolynomial = ((rn0) interfaceC1519zj).getMinimalPolynomial();
        int[] exponentsPresent = minimalPolynomial.getExponentsPresent();
        return new ECFieldF2m(minimalPolynomial.getDegree(), C0133bg.reverseInPlace(C0133bg.copyOfRange(exponentsPresent, 1, exponentsPresent.length - 1)));
    }

    public String getName() {
        return this.name;
    }

    public C1337vh(String str, AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2) {
        super(convertCurve(abstractC1316ux, null), C1313uu.convertPoint(abstractC1341vl), bigInteger, bigInteger2.intValue());
        this.name = str;
    }

    public C1337vh(String str, AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        super(convertCurve(abstractC1316ux, bArr), C1313uu.convertPoint(abstractC1341vl), bigInteger, bigInteger2.intValue());
        this.name = str;
    }

    public C1337vh(String str, EllipticCurve ellipticCurve, ECPoint eCPoint, BigInteger bigInteger) {
        super(ellipticCurve, eCPoint, bigInteger, 1);
        this.name = str;
    }

    public C1337vh(String str, EllipticCurve ellipticCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        super(ellipticCurve, eCPoint, bigInteger, bigInteger2.intValue());
        this.name = str;
    }
}
