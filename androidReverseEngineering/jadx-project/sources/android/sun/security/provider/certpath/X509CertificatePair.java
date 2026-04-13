package android.sun.security.provider.certpath;

import android.sun.security.provider.X509Factory;
import android.sun.security.util.Cache;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.x509.X509CertImpl;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import javax.security.auth.x500.X500Principal;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509CertificatePair {
    private static final byte TAG_FORWARD = 0;
    private static final byte TAG_REVERSE = 1;
    private static final Cache cache = Cache.newSoftMemoryCache(750);
    private byte[] encoded;
    private X509Certificate forward;
    private X509Certificate reverse;

    public X509CertificatePair() {
    }

    public X509CertificatePair(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        if (x509Certificate == null && x509Certificate2 == null) {
            throw new CertificateException("at least one of certificate pair must be non-null");
        }
        this.forward = x509Certificate;
        this.reverse = x509Certificate2;
        checkPair();
    }

    private void checkPair() {
        X509Certificate x509Certificate = this.forward;
        if (x509Certificate == null || this.reverse == null) {
            return;
        }
        X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
        X500Principal issuerX500Principal = this.forward.getIssuerX500Principal();
        X500Principal subjectX500Principal2 = this.reverse.getSubjectX500Principal();
        X500Principal issuerX500Principal2 = this.reverse.getIssuerX500Principal();
        if (!issuerX500Principal.equals(subjectX500Principal2) || !issuerX500Principal2.equals(subjectX500Principal)) {
            throw new CertificateException("subject and issuer names in forward and reverse certificates do not match");
        }
        try {
            PublicKey publicKey = this.reverse.getPublicKey();
            if (!(publicKey instanceof DSAPublicKey) || ((DSAPublicKey) publicKey).getParams() != null) {
                this.forward.verify(publicKey);
            }
            PublicKey publicKey2 = this.forward.getPublicKey();
            if ((publicKey2 instanceof DSAPublicKey) && ((DSAPublicKey) publicKey2).getParams() == null) {
                return;
            }
            this.reverse.verify(publicKey2);
        } catch (GeneralSecurityException e2) {
            throw new CertificateException(AbstractC0000a.m19o(e2, new StringBuilder("invalid signature: ")));
        }
    }

    public static synchronized void clearCache() {
        synchronized (X509CertificatePair.class) {
            cache.clear();
        }
    }

    private void emit(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        if (this.forward != null) {
            DerOutputStream derOutputStream3 = new DerOutputStream();
            derOutputStream3.putDerValue(new DerValue(this.forward.getEncoded()));
            derOutputStream2.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), derOutputStream3);
        }
        if (this.reverse != null) {
            DerOutputStream derOutputStream4 = new DerOutputStream();
            derOutputStream4.putDerValue(new DerValue(this.reverse.getEncoded()));
            derOutputStream2.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 1), derOutputStream4);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public static synchronized X509CertificatePair generateCertificatePair(byte[] bArr) {
        synchronized (X509CertificatePair.class) {
            Cache.EqualByteArray equalByteArray = new Cache.EqualByteArray(bArr);
            Cache cache2 = cache;
            X509CertificatePair x509CertificatePair = (X509CertificatePair) cache2.get(equalByteArray);
            if (x509CertificatePair != null) {
                return x509CertificatePair;
            }
            X509CertificatePair x509CertificatePair2 = new X509CertificatePair(bArr);
            cache2.put(new Cache.EqualByteArray(x509CertificatePair2.encoded), x509CertificatePair2);
            return x509CertificatePair2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0087, code lost:
    
        if (r3.forward != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x008b, code lost:
    
        if (r3.reverse == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0095, code lost:
    
        throw new java.security.cert.CertificateException("at least one of certificate pair must be non-null");
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0096, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parse(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new IOException("Sequence tag missing for X509CertificatePair");
        }
        while (true) {
            DerInputStream derInputStream = derValue.data;
            if (derInputStream == null || derInputStream.available() == 0) {
                break;
            }
            DerValue derValue2 = derValue.data.getDerValue();
            short s2 = (byte) (derValue2.tag & 31);
            if (s2 != 0) {
                if (s2 != 1) {
                    throw new IOException("Invalid encoding of X509CertificatePair");
                }
                if (derValue2.isContextSpecific() && derValue2.isConstructed()) {
                    if (this.reverse != null) {
                        throw new IOException("Duplicate reverse certificate in X509CertificatePair");
                    }
                    this.reverse = X509Factory.intern(new X509CertImpl(derValue2.data.getDerValue().toByteArray()));
                }
            } else if (derValue2.isContextSpecific() && derValue2.isConstructed()) {
                if (this.forward != null) {
                    throw new IOException("Duplicate forward certificate in X509CertificatePair");
                }
                this.forward = X509Factory.intern(new X509CertImpl(derValue2.data.getDerValue().toByteArray()));
            }
        }
    }

    public byte[] getEncoded() {
        try {
            if (this.encoded == null) {
                DerOutputStream derOutputStream = new DerOutputStream();
                emit(derOutputStream);
                this.encoded = derOutputStream.toByteArray();
            }
            return this.encoded;
        } catch (IOException e2) {
            throw new CertificateEncodingException(e2.toString());
        }
    }

    public X509Certificate getForward() {
        return this.forward;
    }

    public X509Certificate getReverse() {
        return this.reverse;
    }

    public void setForward(X509Certificate x509Certificate) {
        checkPair();
        this.forward = x509Certificate;
    }

    public void setReverse(X509Certificate x509Certificate) {
        checkPair();
        this.reverse = x509Certificate;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("X.509 Certificate Pair: [\n");
        if (this.forward != null) {
            stringBuffer.append("  Forward: " + this.forward + "\n");
        }
        if (this.reverse != null) {
            stringBuffer.append("  Reverse: " + this.reverse + "\n");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    private X509CertificatePair(byte[] bArr) {
        try {
            parse(new DerValue(bArr));
            this.encoded = bArr;
            checkPair();
        } catch (IOException e2) {
            throw new CertificateException(e2.toString());
        }
    }
}
