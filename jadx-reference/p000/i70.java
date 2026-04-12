package p000;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

/* loaded from: classes2.dex */
public class i70 extends rh1 {

    /* renamed from: i70$a0 */
    public static class C0612a0 implements InterfaceC1238sx {
        private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        private MessageDigest digest;

        public C0612a0(MessageDigest messageDigest) {
            this.digest = messageDigest;
        }

        @Override // p000.InterfaceC1238sx
        public C1168r5 getAlgorithmIdentifier() {
            return new C1168r5(pk0.idSHA1);
        }

        @Override // p000.InterfaceC1238sx
        public byte[] getDigest() {
            byte[] bArrDigest = this.digest.digest(this.bOut.toByteArray());
            this.bOut.reset();
            return bArrDigest;
        }

        @Override // p000.InterfaceC1238sx
        public OutputStream getOutputStream() {
            return this.bOut;
        }
    }

    public i70() throws NoSuchAlgorithmException {
        super(new C0612a0(MessageDigest.getInstance("SHA1")));
    }

    private static Collection getAlternativeNames(byte[] bArr) throws CertificateParsingException, IOException {
        Object aSN1Primitive;
        if (bArr == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            ArrayList arrayList = new ArrayList();
            Enumeration objects = AbstractC0400d2.getInstance(parseExtensionValue(bArr)).getObjects();
            while (objects.hasMoreElements()) {
                q20 q20Var = q20.getInstance(objects.nextElement());
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(q60.valueOf(q20Var.getTagNo()));
                switch (q20Var.getTagNo()) {
                    case 0:
                    case 3:
                    case 5:
                        aSN1Primitive = q20Var.getName().toASN1Primitive();
                        break;
                    case 1:
                    case 2:
                    case 6:
                        aSN1Primitive = ((InterfaceC0405d7) q20Var.getName()).getString();
                        break;
                    case 4:
                        aSN1Primitive = kh1.getInstance(q20Var.getName()).toString();
                        break;
                    case 7:
                        aSN1Primitive = AbstractC0161c6.getInstance(q20Var.getName()).getOctets();
                        break;
                    case 8:
                        aSN1Primitive = C0160c5.getInstance(q20Var.getName()).getId();
                        break;
                    default:
                        throw new IOException("Bad tag number: " + q20Var.getTagNo());
                }
                arrayList2.add(aSN1Primitive);
                arrayList.add(arrayList2);
            }
            return Collections.unmodifiableCollection(arrayList);
        } catch (Exception e) {
            throw new CertificateParsingException(e.getMessage());
        }
    }

    public static Collection getIssuerAlternativeNames(X509Certificate x509Certificate) throws CertificateParsingException {
        return getAlternativeNames(x509Certificate.getExtensionValue(C1452yc.issuerAlternativeName.getId()));
    }

    public static Collection getSubjectAlternativeNames(X509Certificate x509Certificate) throws CertificateParsingException {
        return getAlternativeNames(x509Certificate.getExtensionValue(C1452yc.subjectAlternativeName.getId()));
    }

    public static AbstractC0164c9 parseExtensionValue(byte[] bArr) throws IOException {
        return AbstractC0164c9.fromByteArray(AbstractC0161c6.getInstance(bArr).getOctets());
    }

    public C0146bt createAuthorityKeyIdentifier(PublicKey publicKey) {
        return super.createAuthorityKeyIdentifier(u21.getInstance(publicKey.getEncoded()));
    }

    public t21 createSubjectKeyIdentifier(PublicKey publicKey) {
        return super.createSubjectKeyIdentifier(u21.getInstance(publicKey.getEncoded()));
    }

    public t21 createTruncatedSubjectKeyIdentifier(PublicKey publicKey) {
        return super.createTruncatedSubjectKeyIdentifier(u21.getInstance(publicKey.getEncoded()));
    }

    public i70(InterfaceC1238sx interfaceC1238sx) {
        super(interfaceC1238sx);
    }

    public C0146bt createAuthorityKeyIdentifier(PublicKey publicKey, r20 r20Var, BigInteger bigInteger) {
        return super.createAuthorityKeyIdentifier(u21.getInstance(publicKey.getEncoded()), r20Var, bigInteger);
    }

    public C0146bt createAuthorityKeyIdentifier(PublicKey publicKey, X500Principal x500Principal, BigInteger bigInteger) {
        return super.createAuthorityKeyIdentifier(u21.getInstance(publicKey.getEncoded()), new r20(new q20(kh1.getInstance(x500Principal.getEncoded()))), bigInteger);
    }

    public C0146bt createAuthorityKeyIdentifier(X509Certificate x509Certificate) throws CertificateEncodingException {
        return super.createAuthorityKeyIdentifier(new JcaX509CertificateHolder(x509Certificate));
    }
}
