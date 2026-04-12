package p000;

/* loaded from: classes2.dex */
public class y80 {
    public static byte[] getEncodedPrivateKeyInfo(C1168r5 c1168r5, InterfaceC0117b0 interfaceC0117b0) {
        try {
            return getEncodedPrivateKeyInfo(new io0(c1168r5, interfaceC0117b0.toASN1Primitive()));
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(C1168r5 c1168r5, InterfaceC0117b0 interfaceC0117b0) {
        try {
            return getEncodedSubjectPublicKeyInfo(new u21(c1168r5, interfaceC0117b0));
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedPrivateKeyInfo(io0 io0Var) {
        try {
            return io0Var.getEncoded("DER");
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(C1168r5 c1168r5, byte[] bArr) {
        try {
            return getEncodedSubjectPublicKeyInfo(new u21(c1168r5, bArr));
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(u21 u21Var) {
        try {
            return u21Var.getEncoded("DER");
        } catch (Exception unused) {
            return null;
        }
    }
}
