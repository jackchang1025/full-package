package org.bouncycastle.jsse.provider;

import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLPermission;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.auth.x500.X500Principal;
import javax.security.cert.X509Certificate;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.tls.Certificate;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
abstract class ProvSSLSessionBase extends BCExtendedSSLSession {
    protected final long creationTime;
    protected final JcaTlsCrypto crypto;
    protected final SSLSession exportSSLSession;
    protected final boolean isFips;
    protected final AtomicLong lastAccessedTime;
    protected final String peerHost;
    protected final int peerPort;
    protected final AtomicReference<ProvSSLSessionContext> sslSessionContext;
    protected final Map<String, Object> valueMap = Collections.synchronizedMap(new HashMap());

    public static class X509CertificateWrapper extends X509Certificate {

        /* renamed from: c */
        private final java.security.cert.X509Certificate f1408c;

        private X509CertificateWrapper(java.security.cert.X509Certificate x509Certificate) {
            this.f1408c = x509Certificate;
        }

        @Override // javax.security.cert.X509Certificate
        public void checkValidity() {
            try {
                this.f1408c.checkValidity();
            } catch (CertificateExpiredException e2) {
                throw new javax.security.cert.CertificateExpiredException(e2.getMessage());
            } catch (CertificateNotYetValidException e3) {
                throw new javax.security.cert.CertificateNotYetValidException(e3.getMessage());
            }
        }

        @Override // javax.security.cert.Certificate
        public byte[] getEncoded() {
            try {
                return this.f1408c.getEncoded();
            } catch (CertificateEncodingException e2) {
                throw new javax.security.cert.CertificateEncodingException(e2.getMessage());
            }
        }

        @Override // javax.security.cert.X509Certificate
        public Principal getIssuerDN() {
            return this.f1408c.getIssuerX500Principal();
        }

        @Override // javax.security.cert.X509Certificate
        public Date getNotAfter() {
            return this.f1408c.getNotAfter();
        }

        @Override // javax.security.cert.X509Certificate
        public Date getNotBefore() {
            return this.f1408c.getNotBefore();
        }

        @Override // javax.security.cert.Certificate
        public PublicKey getPublicKey() {
            return this.f1408c.getPublicKey();
        }

        @Override // javax.security.cert.X509Certificate
        public BigInteger getSerialNumber() {
            return this.f1408c.getSerialNumber();
        }

        @Override // javax.security.cert.X509Certificate
        public String getSigAlgName() {
            return this.f1408c.getSigAlgName();
        }

        @Override // javax.security.cert.X509Certificate
        public String getSigAlgOID() {
            return this.f1408c.getSigAlgOID();
        }

        @Override // javax.security.cert.X509Certificate
        public byte[] getSigAlgParams() {
            return this.f1408c.getSigAlgParams();
        }

        @Override // javax.security.cert.X509Certificate
        public Principal getSubjectDN() {
            return this.f1408c.getSubjectX500Principal();
        }

        @Override // javax.security.cert.X509Certificate
        public int getVersion() {
            return this.f1408c.getVersion() - 1;
        }

        @Override // javax.security.cert.Certificate
        public String toString() {
            return this.f1408c.toString();
        }

        @Override // javax.security.cert.Certificate
        public void verify(PublicKey publicKey) {
            try {
                this.f1408c.verify(publicKey);
            } catch (CertificateEncodingException e2) {
                throw new javax.security.cert.CertificateEncodingException(e2.getMessage());
            } catch (CertificateExpiredException e3) {
                throw new javax.security.cert.CertificateExpiredException(e3.getMessage());
            } catch (CertificateNotYetValidException e4) {
                throw new javax.security.cert.CertificateNotYetValidException(e4.getMessage());
            } catch (CertificateParsingException e5) {
                throw new javax.security.cert.CertificateParsingException(e5.getMessage());
            } catch (CertificateException e6) {
                throw new javax.security.cert.CertificateException(e6.getMessage());
            }
        }

        @Override // javax.security.cert.X509Certificate
        public void checkValidity(Date date) {
            try {
                this.f1408c.checkValidity(date);
            } catch (CertificateExpiredException e2) {
                throw new javax.security.cert.CertificateExpiredException(e2.getMessage());
            } catch (CertificateNotYetValidException e3) {
                throw new javax.security.cert.CertificateNotYetValidException(e3.getMessage());
            }
        }

        @Override // javax.security.cert.Certificate
        public void verify(PublicKey publicKey, String str) {
            try {
                this.f1408c.verify(publicKey, str);
            } catch (CertificateEncodingException e2) {
                throw new javax.security.cert.CertificateEncodingException(e2.getMessage());
            } catch (CertificateExpiredException e3) {
                throw new javax.security.cert.CertificateExpiredException(e3.getMessage());
            } catch (CertificateNotYetValidException e4) {
                throw new javax.security.cert.CertificateNotYetValidException(e4.getMessage());
            } catch (CertificateParsingException e5) {
                throw new javax.security.cert.CertificateParsingException(e5.getMessage());
            } catch (CertificateException e6) {
                throw new javax.security.cert.CertificateException(e6.getMessage());
            }
        }
    }

    public ProvSSLSessionBase(ProvSSLSessionContext provSSLSessionContext, String str, int i2) {
        this.sslSessionContext = new AtomicReference<>(provSSLSessionContext);
        this.isFips = provSSLSessionContext == null ? false : provSSLSessionContext.getSSLContext().isFips();
        this.crypto = provSSLSessionContext == null ? null : provSSLSessionContext.getCrypto();
        this.peerHost = str;
        this.peerPort = i2;
        long currentTimeMillis = System.currentTimeMillis();
        this.creationTime = currentTimeMillis;
        this.exportSSLSession = SSLSessionUtil.exportSSLSession(this);
        this.lastAccessedTime = new AtomicLong(currentTimeMillis);
    }

    private void implInvalidate(boolean z2) {
        if (z2) {
            ProvSSLSessionContext andSet = this.sslSessionContext.getAndSet(null);
            if (andSet != null) {
                andSet.removeSession(getIDArray());
            }
        } else {
            this.sslSessionContext.set(null);
        }
        invalidateTLS();
    }

    public void accessedAt(long j2) {
        long j3 = this.lastAccessedTime.get();
        if (j2 > j3) {
            this.lastAccessedTime.compareAndSet(j3, j2);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ProvSSLSessionBase) {
            return Arrays.areEqual(getIDArray(), ((ProvSSLSessionBase) obj).getIDArray());
        }
        return false;
    }

    @Override // javax.net.ssl.SSLSession
    public int getApplicationBufferSize() {
        return 16384;
    }

    @Override // javax.net.ssl.SSLSession
    public String getCipherSuite() {
        return ProvSSLContextSpi.getCipherSuiteName(getCipherSuiteTLS());
    }

    public abstract int getCipherSuiteTLS();

    @Override // javax.net.ssl.SSLSession
    public long getCreationTime() {
        return this.creationTime;
    }

    public SSLSession getExportSSLSession() {
        return this.exportSSLSession;
    }

    public abstract byte[] getIDArray();

    @Override // javax.net.ssl.SSLSession
    public byte[] getId() {
        byte[] iDArray = getIDArray();
        return TlsUtils.isNullOrEmpty(iDArray) ? TlsUtils.EMPTY_BYTES : (byte[]) iDArray.clone();
    }

    public abstract JsseSecurityParameters getJsseSecurityParameters();

    public abstract JsseSessionParameters getJsseSessionParameters();

    @Override // javax.net.ssl.SSLSession
    public long getLastAccessedTime() {
        return this.lastAccessedTime.get();
    }

    public abstract Certificate getLocalCertificateTLS();

    @Override // javax.net.ssl.SSLSession
    public java.security.cert.Certificate[] getLocalCertificates() {
        java.security.cert.X509Certificate[] x509CertificateChain;
        JcaTlsCrypto jcaTlsCrypto = this.crypto;
        if (jcaTlsCrypto == null || (x509CertificateChain = JsseUtils.getX509CertificateChain(jcaTlsCrypto, getLocalCertificateTLS())) == null || x509CertificateChain.length <= 0) {
            return null;
        }
        return x509CertificateChain;
    }

    @Override // javax.net.ssl.SSLSession
    public Principal getLocalPrincipal() {
        JcaTlsCrypto jcaTlsCrypto = this.crypto;
        if (jcaTlsCrypto != null) {
            return JsseUtils.getSubject(jcaTlsCrypto, getLocalCertificateTLS());
        }
        return null;
    }

    @Override // javax.net.ssl.SSLSession
    public int getPacketBufferSize() {
        ProtocolVersion protocolTLS = getProtocolTLS();
        if (protocolTLS == null || !TlsUtils.isTLSv12(protocolTLS)) {
            return 18443;
        }
        return TlsUtils.isTLSv13(protocolTLS) ? 16911 : 17413;
    }

    @Override // javax.net.ssl.SSLSession
    public X509Certificate[] getPeerCertificateChain() {
        java.security.cert.X509Certificate[] x509CertificateArr = (java.security.cert.X509Certificate[]) getPeerCertificates();
        X509Certificate[] x509CertificateArr2 = new X509Certificate[x509CertificateArr.length];
        for (int i2 = 0; i2 < x509CertificateArr.length; i2++) {
            try {
                if (this.isFips) {
                    x509CertificateArr2[i2] = new X509CertificateWrapper(x509CertificateArr[i2]);
                } else {
                    x509CertificateArr2[i2] = X509Certificate.getInstance(x509CertificateArr[i2].getEncoded());
                }
            } catch (Exception e2) {
                throw new SSLPeerUnverifiedException(e2.getMessage());
            }
        }
        return x509CertificateArr2;
    }

    public abstract Certificate getPeerCertificateTLS();

    @Override // javax.net.ssl.SSLSession
    public java.security.cert.Certificate[] getPeerCertificates() {
        java.security.cert.X509Certificate[] x509CertificateChain;
        JcaTlsCrypto jcaTlsCrypto = this.crypto;
        if (jcaTlsCrypto == null || (x509CertificateChain = JsseUtils.getX509CertificateChain(jcaTlsCrypto, getPeerCertificateTLS())) == null || x509CertificateChain.length <= 0) {
            throw new SSLPeerUnverifiedException("No peer identity established");
        }
        return x509CertificateChain;
    }

    @Override // javax.net.ssl.SSLSession
    public String getPeerHost() {
        return this.peerHost;
    }

    @Override // javax.net.ssl.SSLSession
    public int getPeerPort() {
        return this.peerPort;
    }

    @Override // javax.net.ssl.SSLSession
    public Principal getPeerPrincipal() {
        X500Principal subject;
        JcaTlsCrypto jcaTlsCrypto = this.crypto;
        if (jcaTlsCrypto == null || (subject = JsseUtils.getSubject(jcaTlsCrypto, getPeerCertificateTLS())) == null) {
            throw new SSLPeerUnverifiedException("No peer identity established");
        }
        return subject;
    }

    @Override // javax.net.ssl.SSLSession
    public String getProtocol() {
        return ProvSSLContextSpi.getProtocolVersionName(getProtocolTLS());
    }

    public abstract ProtocolVersion getProtocolTLS();

    @Override // javax.net.ssl.SSLSession
    public SSLSessionContext getSessionContext() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new SSLPermission("getSSLSessionContext"));
        }
        return this.sslSessionContext.get();
    }

    @Override // javax.net.ssl.SSLSession
    public Object getValue(String str) {
        return this.valueMap.get(str);
    }

    @Override // javax.net.ssl.SSLSession
    public String[] getValueNames() {
        String[] strArr;
        synchronized (this.valueMap) {
            strArr = (String[]) this.valueMap.keySet().toArray(new String[this.valueMap.size()]);
        }
        return strArr;
    }

    public int hashCode() {
        return Arrays.hashCode(getIDArray());
    }

    @Override // javax.net.ssl.SSLSession
    public final void invalidate() {
        implInvalidate(true);
    }

    public abstract void invalidateTLS();

    public final void invalidatedBySessionContext() {
        implInvalidate(false);
    }

    @Override // javax.net.ssl.SSLSession
    public boolean isValid() {
        if (this.sslSessionContext.get() == null) {
            return false;
        }
        return !TlsUtils.isNullOrEmpty(getIDArray());
    }

    public void notifyBound(String str, Object obj) {
        if (obj instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener) obj).valueBound(new SSLSessionBindingEvent(this, str));
        }
    }

    public void notifyUnbound(String str, Object obj) {
        if (obj instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener) obj).valueUnbound(new SSLSessionBindingEvent(this, str));
        }
    }

    @Override // javax.net.ssl.SSLSession
    public void putValue(String str, Object obj) {
        notifyUnbound(str, this.valueMap.put(str, obj));
        notifyBound(str, obj);
    }

    @Override // javax.net.ssl.SSLSession
    public void removeValue(String str) {
        notifyUnbound(str, this.valueMap.remove(str));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Session(");
        sb.append(getCreationTime());
        sb.append("|");
        return AbstractC0000a.m18n(sb, getCipherSuite(), ")");
    }
}
