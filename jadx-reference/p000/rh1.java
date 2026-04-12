package p000;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.cert.CertRuntimeException;
import org.bouncycastle.cert.X509CertificateHolder;

/* loaded from: classes2.dex */
public class rh1 {
    private InterfaceC1238sx calculator;

    public rh1(InterfaceC1238sx interfaceC1238sx) {
        this.calculator = interfaceC1238sx;
    }

    private byte[] calculateIdentifier(u21 u21Var) throws IOException {
        byte[] bytes = u21Var.getPublicKeyData().getBytes();
        OutputStream outputStream = this.calculator.getOutputStream();
        try {
            outputStream.write(bytes);
            outputStream.close();
            return this.calculator.getDigest();
        } catch (IOException e) {
            throw new CertRuntimeException(AbstractC0003a2.m26a7(e, new StringBuilder("unable to calculate identifier: ")), e);
        }
    }

    private byte[] getSubjectKeyIdentifier(X509CertificateHolder x509CertificateHolder) {
        if (x509CertificateHolder.getVersionNumber() != 3) {
            return calculateIdentifier(x509CertificateHolder.getSubjectPublicKeyInfo());
        }
        C1452yc extension = x509CertificateHolder.getExtension(C1452yc.subjectKeyIdentifier);
        return extension != null ? AbstractC0161c6.getInstance(extension.getParsedValue()).getOctets() : calculateIdentifier(x509CertificateHolder.getSubjectPublicKeyInfo());
    }

    public C0146bt createAuthorityKeyIdentifier(u21 u21Var) {
        return new C0146bt(calculateIdentifier(u21Var));
    }

    public t21 createSubjectKeyIdentifier(u21 u21Var) {
        return new t21(calculateIdentifier(u21Var));
    }

    public t21 createTruncatedSubjectKeyIdentifier(u21 u21Var) throws IOException {
        byte[] bArrCalculateIdentifier = calculateIdentifier(u21Var);
        byte[] bArr = new byte[8];
        System.arraycopy(bArrCalculateIdentifier, bArrCalculateIdentifier.length - 8, bArr, 0, 8);
        byte b = (byte) (bArr[0] & 15);
        bArr[0] = b;
        bArr[0] = (byte) (b | 64);
        return new t21(bArr);
    }

    public C0146bt createAuthorityKeyIdentifier(u21 u21Var, r20 r20Var, BigInteger bigInteger) {
        return new C0146bt(calculateIdentifier(u21Var), r20Var, bigInteger);
    }

    public C0146bt createAuthorityKeyIdentifier(X509CertificateHolder x509CertificateHolder) {
        return new C0146bt(getSubjectKeyIdentifier(x509CertificateHolder), new r20(new q20(x509CertificateHolder.getIssuer())), x509CertificateHolder.getSerialNumber());
    }
}
