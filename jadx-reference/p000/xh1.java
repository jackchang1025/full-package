package p000;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;

/* loaded from: classes2.dex */
public class xh1 {
    private C1455yf extGenerator;
    private g91 tbsGen;

    public xh1(kh1 kh1Var, BigInteger bigInteger, p61 p61Var, p61 p61Var2, kh1 kh1Var2, u21 u21Var) {
        g91 g91Var = new g91();
        this.tbsGen = g91Var;
        g91Var.setSerialNumber(new C0155c0(bigInteger));
        this.tbsGen.setIssuer(kh1Var);
        this.tbsGen.setStartDate(p61Var);
        this.tbsGen.setEndDate(p61Var2);
        this.tbsGen.setSubject(kh1Var2);
        this.tbsGen.setSubjectPublicKeyInfo(u21Var);
        this.extGenerator = new C1455yf();
    }

    public static C0991oo booleanToBitString(boolean[] zArr) {
        byte[] bArr = new byte[(zArr.length + 7) / 8];
        for (int i = 0; i != zArr.length; i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) (bArr[i2] | (zArr[i] ? 1 << (7 - (i % 8)) : 0));
        }
        int length = zArr.length % 8;
        return length == 0 ? new C0991oo(bArr) : new C0991oo(bArr, 8 - length);
    }

    private C1452yc doGetExtension(C0160c5 c0160c5) {
        return this.extGenerator.generate().getExtension(c0160c5);
    }

    private static byte[] generateSig(InterfaceC0863mj interfaceC0863mj, AbstractC0158c3 abstractC0158c3) throws IOException {
        OutputStream outputStream = interfaceC0863mj.getOutputStream();
        abstractC0158c3.encodeTo(outputStream, "DER");
        outputStream.close();
        return interfaceC0863mj.getSignature();
    }

    private static C0544gp generateStructure(t41 t41Var, C1168r5 c1168r5, byte[] bArr) {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(t41Var);
        c0118b1.add(c1168r5);
        c0118b1.add(new C0991oo(bArr));
        return C0544gp.getInstance(new C1064pc(c0118b1));
    }

    public xh1 addExtension(C0160c5 c0160c5, boolean z, InterfaceC0117b0 interfaceC0117b0) throws CertIOException {
        try {
            this.extGenerator.addExtension(c0160c5, z, interfaceC0117b0);
            return this;
        } catch (IOException e) {
            throw new CertIOException(AbstractC0003a2.m26a7(e, new StringBuilder("cannot encode extension: ")), e);
        }
    }

    public X509CertificateHolder build(InterfaceC0863mj interfaceC0863mj) {
        this.tbsGen.setSignature(interfaceC0863mj.getAlgorithmIdentifier());
        if (!this.extGenerator.isEmpty()) {
            this.tbsGen.setExtensions(this.extGenerator.generate());
        }
        try {
            t41 t41VarGenerateTBSCertificate = this.tbsGen.generateTBSCertificate();
            return new X509CertificateHolder(generateStructure(t41VarGenerateTBSCertificate, interfaceC0863mj.getAlgorithmIdentifier(), generateSig(interfaceC0863mj, t41VarGenerateTBSCertificate)));
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot produce certificate signature");
        }
    }

    public xh1 copyAndAddExtension(C0160c5 c0160c5, boolean z, X509CertificateHolder x509CertificateHolder) {
        C1452yc extension = x509CertificateHolder.toASN1Structure().getTBSCertificate().getExtensions().getExtension(c0160c5);
        if (extension != null) {
            this.extGenerator.addExtension(c0160c5, z, extension.getExtnValue().getOctets());
            return this;
        }
        throw new NullPointerException("extension " + c0160c5 + " not present");
    }

    public C1452yc getExtension(C0160c5 c0160c5) {
        return doGetExtension(c0160c5);
    }

    public boolean hasExtension(C0160c5 c0160c5) {
        return doGetExtension(c0160c5) != null;
    }

    public xh1 removeExtension(C0160c5 c0160c5) {
        this.extGenerator = C0543go.doRemoveExtension(this.extGenerator, c0160c5);
        return this;
    }

    public xh1 replaceExtension(C0160c5 c0160c5, boolean z, InterfaceC0117b0 interfaceC0117b0) throws CertIOException {
        try {
            this.extGenerator = C0543go.doReplaceExtension(this.extGenerator, new C1452yc(c0160c5, z, interfaceC0117b0.toASN1Primitive().getEncoded("DER")));
            return this;
        } catch (IOException e) {
            throw new CertIOException(AbstractC0003a2.m26a7(e, new StringBuilder("cannot encode extension: ")), e);
        }
    }

    public xh1 setIssuerUniqueID(boolean[] zArr) {
        this.tbsGen.setIssuerUniqueID(booleanToBitString(zArr));
        return this;
    }

    public xh1 setSubjectUniqueID(boolean[] zArr) {
        this.tbsGen.setSubjectUniqueID(booleanToBitString(zArr));
        return this;
    }

    public xh1(kh1 kh1Var, BigInteger bigInteger, Date date, Date date2, kh1 kh1Var2, u21 u21Var) {
        this(kh1Var, bigInteger, new p61(date), new p61(date2), kh1Var2, u21Var);
    }

    public xh1(kh1 kh1Var, BigInteger bigInteger, Date date, Date date2, Locale locale, kh1 kh1Var2, u21 u21Var) {
        this(kh1Var, bigInteger, new p61(date, locale), new p61(date2, locale), kh1Var2, u21Var);
    }

    public xh1(X509CertificateHolder x509CertificateHolder) {
        g91 g91Var = new g91();
        this.tbsGen = g91Var;
        g91Var.setSerialNumber(new C0155c0(x509CertificateHolder.getSerialNumber()));
        this.tbsGen.setIssuer(x509CertificateHolder.getIssuer());
        this.tbsGen.setStartDate(new p61(x509CertificateHolder.getNotBefore()));
        this.tbsGen.setEndDate(new p61(x509CertificateHolder.getNotAfter()));
        this.tbsGen.setSubject(x509CertificateHolder.getSubject());
        this.tbsGen.setSubjectPublicKeyInfo(x509CertificateHolder.getSubjectPublicKeyInfo());
        this.extGenerator = new C1455yf();
        C1454ye extensions = x509CertificateHolder.getExtensions();
        Enumeration enumerationOids = extensions.oids();
        while (enumerationOids.hasMoreElements()) {
            this.extGenerator.addExtension(extensions.getExtension((C0160c5) enumerationOids.nextElement()));
        }
    }

    public xh1 addExtension(C0160c5 c0160c5, boolean z, byte[] bArr) throws CertIOException {
        this.extGenerator.addExtension(c0160c5, z, bArr);
        return this;
    }

    public xh1 replaceExtension(C0160c5 c0160c5, boolean z, byte[] bArr) throws CertIOException {
        this.extGenerator = C0543go.doReplaceExtension(this.extGenerator, new C1452yc(c0160c5, z, bArr));
        return this;
    }

    public xh1 addExtension(C1452yc c1452yc) throws CertIOException {
        this.extGenerator.addExtension(c1452yc);
        return this;
    }

    public xh1 replaceExtension(C1452yc c1452yc) throws CertIOException {
        this.extGenerator = C0543go.doReplaceExtension(this.extGenerator, c1452yc);
        return this;
    }
}
