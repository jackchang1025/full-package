package p000;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

/* loaded from: classes2.dex */
public class j70 extends xh1 {
    public j70(kh1 kh1Var, BigInteger bigInteger, p61 p61Var, p61 p61Var2, kh1 kh1Var2, PublicKey publicKey) {
        super(kh1Var, bigInteger, p61Var, p61Var2, kh1Var2, u21.getInstance(publicKey.getEncoded()));
    }

    public j70 copyAndAddExtension(C0160c5 c0160c5, boolean z, X509Certificate x509Certificate) throws CertificateEncodingException {
        copyAndAddExtension(c0160c5, z, new JcaX509CertificateHolder(x509Certificate));
        return this;
    }

    public j70(kh1 kh1Var, BigInteger bigInteger, Date date, Date date2, kh1 kh1Var2, PublicKey publicKey) {
        super(kh1Var, bigInteger, date, date2, kh1Var2, u21.getInstance(publicKey.getEncoded()));
    }

    public j70(X509Certificate x509Certificate) throws CertificateEncodingException {
        super(new JcaX509CertificateHolder(x509Certificate));
    }

    public j70(X509Certificate x509Certificate, BigInteger bigInteger, Date date, Date date2, kh1 kh1Var, PublicKey publicKey) {
        this(kh1.getInstance(x509Certificate.getSubjectX500Principal().getEncoded()), bigInteger, date, date2, kh1Var, publicKey);
    }

    public j70(X509Certificate x509Certificate, BigInteger bigInteger, Date date, Date date2, X500Principal x500Principal, PublicKey publicKey) {
        this(x509Certificate.getSubjectX500Principal(), bigInteger, date, date2, x500Principal, publicKey);
    }

    public j70(X500Principal x500Principal, BigInteger bigInteger, Date date, Date date2, X500Principal x500Principal2, PublicKey publicKey) {
        super(kh1.getInstance(x500Principal.getEncoded()), bigInteger, date, date2, kh1.getInstance(x500Principal2.getEncoded()), u21.getInstance(publicKey.getEncoded()));
    }
}
