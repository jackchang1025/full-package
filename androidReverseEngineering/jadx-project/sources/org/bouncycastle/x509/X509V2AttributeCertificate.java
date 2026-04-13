package org.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.AttributeCertificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509V2AttributeCertificate implements X509AttributeCertificate {
    private AttributeCertificate cert;
    private Date notAfter;
    private Date notBefore;

    public X509V2AttributeCertificate(InputStream inputStream) {
        this(getObject(inputStream));
    }

    private Set getExtensionOIDs(boolean z2) {
        Extensions extensions = this.cert.getAcinfo().getExtensions();
        if (extensions == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (extensions.getExtension(aSN1ObjectIdentifier).isCritical() == z2) {
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
        }
        return hashSet;
    }

    private static AttributeCertificate getObject(InputStream inputStream) {
        try {
            return AttributeCertificate.getInstance(new ASN1InputStream(inputStream).readObject());
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new IOException(AbstractC0000a.m29y(e3, new StringBuilder("exception decoding certificate structure: ")));
        }
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public void checkValidity() {
        checkValidity(new Date());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509AttributeCertificate)) {
            return false;
        }
        try {
            return Arrays.areEqual(getEncoded(), ((X509AttributeCertificate) obj).getEncoded());
        } catch (IOException unused) {
            return false;
        }
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public X509Attribute[] getAttributes() {
        ASN1Sequence attributes = this.cert.getAcinfo().getAttributes();
        X509Attribute[] x509AttributeArr = new X509Attribute[attributes.size()];
        for (int i2 = 0; i2 != attributes.size(); i2++) {
            x509AttributeArr[i2] = new X509Attribute(attributes.getObjectAt(i2));
        }
        return x509AttributeArr;
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public byte[] getEncoded() {
        return this.cert.getEncoded();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        Extension extension;
        Extensions extensions = this.cert.getAcinfo().getExtensions();
        if (extensions == null || (extension = extensions.getExtension(new ASN1ObjectIdentifier(str))) == null) {
            return null;
        }
        try {
            return extension.getExtnValue().getEncoded(ASN1Encoding.DER);
        } catch (Exception e2) {
            throw new RuntimeException(AbstractC0000a.m29y(e2, new StringBuilder("error encoding ")));
        }
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public AttributeCertificateHolder getHolder() {
        return new AttributeCertificateHolder((ASN1Sequence) this.cert.getAcinfo().getHolder().toASN1Primitive());
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public AttributeCertificateIssuer getIssuer() {
        return new AttributeCertificateIssuer(this.cert.getAcinfo().getIssuer());
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public boolean[] getIssuerUniqueID() {
        ASN1BitString issuerUniqueID = this.cert.getAcinfo().getIssuerUniqueID();
        if (issuerUniqueID == null) {
            return null;
        }
        byte[] bytes = issuerUniqueID.getBytes();
        int length = (bytes.length * 8) - issuerUniqueID.getPadBits();
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 != length; i2++) {
            zArr[i2] = (bytes[i2 / 8] & (128 >>> (i2 % 8))) != 0;
        }
        return zArr;
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public Date getNotAfter() {
        return this.notAfter;
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public Date getNotBefore() {
        return this.notBefore;
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public BigInteger getSerialNumber() {
        return this.cert.getAcinfo().getSerialNumber().getValue();
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public byte[] getSignature() {
        return this.cert.getSignatureValue().getOctets();
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public int getVersion() {
        return this.cert.getAcinfo().getVersion().intValueExact() + 1;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return (criticalExtensionOIDs == null || criticalExtensionOIDs.isEmpty()) ? false : true;
    }

    public int hashCode() {
        try {
            return Arrays.hashCode(getEncoded());
        } catch (IOException unused) {
            return 0;
        }
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public final void verify(PublicKey publicKey, String str) {
        if (!this.cert.getSignatureAlgorithm().equals(this.cert.getAcinfo().getSignature())) {
            throw new CertificateException("Signature algorithm in certificate info not same as outer certificate");
        }
        Signature signature = Signature.getInstance(this.cert.getSignatureAlgorithm().getAlgorithm().getId(), str);
        signature.initVerify(publicKey);
        try {
            signature.update(this.cert.getAcinfo().getEncoded());
            if (!signature.verify(getSignature())) {
                throw new InvalidKeyException("Public key presented not for certificate signature");
            }
        } catch (IOException unused) {
            throw new SignatureException("Exception encoding certificate info object");
        }
    }

    public X509V2AttributeCertificate(AttributeCertificate attributeCertificate) {
        this.cert = attributeCertificate;
        try {
            this.notAfter = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime().getDate();
            this.notBefore = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime().getDate();
        } catch (ParseException unused) {
            throw new IOException("invalid data structure in certificate!");
        }
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public void checkValidity(Date date) {
        if (date.after(getNotAfter())) {
            throw new CertificateExpiredException("certificate expired on " + getNotAfter());
        }
        if (date.before(getNotBefore())) {
            throw new CertificateNotYetValidException("certificate not valid till " + getNotBefore());
        }
    }

    @Override // org.bouncycastle.x509.X509AttributeCertificate
    public X509Attribute[] getAttributes(String str) {
        ASN1Sequence attributes = this.cert.getAcinfo().getAttributes();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 != attributes.size(); i2++) {
            X509Attribute x509Attribute = new X509Attribute(attributes.getObjectAt(i2));
            if (x509Attribute.getOID().equals(str)) {
                arrayList.add(x509Attribute);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return (X509Attribute[]) arrayList.toArray(new X509Attribute[arrayList.size()]);
    }

    public X509V2AttributeCertificate(byte[] bArr) {
        this(new ByteArrayInputStream(bArr));
    }
}
