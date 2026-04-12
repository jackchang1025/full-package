package p000;

import java.io.IOException;
import java.security.AlgorithmParameters;

/* renamed from: r7 */
/* loaded from: classes2.dex */
public class C1170r7 {
    private C1170r7() {
    }

    public static InterfaceC0117b0 extractParameters(AlgorithmParameters algorithmParameters) throws IOException {
        try {
            return AbstractC0164c9.fromByteArray(algorithmParameters.getEncoded("ASN.1"));
        } catch (Exception unused) {
            return AbstractC0164c9.fromByteArray(algorithmParameters.getEncoded());
        }
    }

    public static void loadParameters(AlgorithmParameters algorithmParameters, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        try {
            algorithmParameters.init(interfaceC0117b0.toASN1Primitive().getEncoded(), "ASN.1");
        } catch (Exception unused) {
            algorithmParameters.init(interfaceC0117b0.toASN1Primitive().getEncoded());
        }
    }
}
