package android.sun.security.pkcs;

import android.sun.security.util.Debug;
import android.sun.security.util.DerEncoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import android.sun.security.x509.AlgorithmId;
import android.sun.security.x509.X500Name;
import android.sun.security.x509.X509CRLImpl;
import android.sun.security.x509.X509CertImpl;
import android.sun.security.x509.X509CertInfo;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Vector;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS7 {
    private Principal[] certIssuerNames;
    private X509Certificate[] certificates;
    private ContentInfo contentInfo;
    private ObjectIdentifier contentType;
    private X509CRL[] crls;
    private AlgorithmId[] digestAlgorithmIds;
    private boolean oldStyle;
    private SignerInfo[] signerInfos;
    private BigInteger version;

    public PKCS7(DerInputStream derInputStream) {
        this.version = null;
        this.digestAlgorithmIds = null;
        this.contentInfo = null;
        this.certificates = null;
        this.crls = null;
        this.signerInfos = null;
        this.oldStyle = false;
        parse(derInputStream);
    }

    private void parse(DerInputStream derInputStream) {
        try {
            try {
                derInputStream.mark(derInputStream.available());
                parse(derInputStream, false);
            } catch (IOException e2) {
                ParsingException parsingException = new ParsingException(e2.getMessage());
                parsingException.initCause(e2);
                throw parsingException;
            }
        } catch (IOException unused) {
            derInputStream.reset();
            parse(derInputStream, true);
            this.oldStyle = true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseNetscapeCertChain(DerValue derValue) {
        CertificateFactory certificateFactory;
        DerValue[] sequence = new DerInputStream(derValue.toByteArray()).getSequence(2);
        this.certificates = new X509Certificate[sequence.length];
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException unused) {
            certificateFactory = null;
        }
        for (int i2 = 0; i2 < sequence.length; i2++) {
            if (certificateFactory == null) {
                try {
                    try {
                        this.certificates[i2] = new X509CertImpl(sequence[i2]);
                    } catch (Throwable th) {
                        th = th;
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    ParsingException parsingException = new ParsingException(e.getMessage());
                    parsingException.initCause(e);
                    throw parsingException;
                } catch (CertificateException e3) {
                    e = e3;
                    ParsingException parsingException2 = new ParsingException(e.getMessage());
                    parsingException2.initCause(e);
                    throw parsingException2;
                }
            } else {
                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(sequence[i2].toByteArray());
                try {
                    this.certificates[i2] = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream2);
                    byteArrayInputStream2.close();
                } catch (IOException e4) {
                    e = e4;
                    ParsingException parsingException3 = new ParsingException(e.getMessage());
                    parsingException3.initCause(e);
                    throw parsingException3;
                } catch (CertificateException e5) {
                    e = e5;
                    ParsingException parsingException22 = new ParsingException(e.getMessage());
                    parsingException22.initCause(e);
                    throw parsingException22;
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayInputStream = byteArrayInputStream2;
                    if (byteArrayInputStream != null) {
                    }
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseOldSignedData(DerValue derValue) {
        CertificateFactory certificateFactory;
        DerInputStream derInputStream = derValue.toDerInputStream();
        this.version = derInputStream.getBigInteger();
        DerValue[] set = derInputStream.getSet(1);
        int length = set.length;
        this.digestAlgorithmIds = new AlgorithmId[length];
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.digestAlgorithmIds[i2] = AlgorithmId.parse(set[i2]);
            } catch (IOException unused) {
                throw new ParsingException("Error parsing digest AlgorithmId IDs");
            }
        }
        this.contentInfo = new ContentInfo(derInputStream, true);
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException unused2) {
            certificateFactory = null;
        }
        DerValue[] set2 = derInputStream.getSet(2);
        int length2 = set2.length;
        this.certificates = new X509Certificate[length2];
        for (int i3 = 0; i3 < length2; i3++) {
            if (certificateFactory == null) {
                try {
                    try {
                        this.certificates[i3] = new X509CertImpl(set2[i3]);
                    } catch (Throwable th) {
                        th = th;
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    ParsingException parsingException = new ParsingException(e.getMessage());
                    parsingException.initCause(e);
                    throw parsingException;
                } catch (CertificateException e3) {
                    e = e3;
                    ParsingException parsingException2 = new ParsingException(e.getMessage());
                    parsingException2.initCause(e);
                    throw parsingException2;
                }
            } else {
                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(set2[i3].toByteArray());
                try {
                    this.certificates[i3] = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream2);
                    byteArrayInputStream2.close();
                } catch (IOException e4) {
                    e = e4;
                    ParsingException parsingException3 = new ParsingException(e.getMessage());
                    parsingException3.initCause(e);
                    throw parsingException3;
                } catch (CertificateException e5) {
                    e = e5;
                    ParsingException parsingException22 = new ParsingException(e.getMessage());
                    parsingException22.initCause(e);
                    throw parsingException22;
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayInputStream = byteArrayInputStream2;
                    if (byteArrayInputStream != null) {
                    }
                    throw th;
                }
            }
        }
        derInputStream.getSet(0);
        DerValue[] set3 = derInputStream.getSet(1);
        int length3 = set3.length;
        this.signerInfos = new SignerInfo[length3];
        for (int i4 = 0; i4 < length3; i4++) {
            this.signerInfos[i4] = new SignerInfo(set3[i4].toDerInputStream(), true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseSignedData(DerValue derValue) {
        CertificateFactory certificateFactory;
        Throwable th;
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayInputStream byteArrayInputStream2;
        DerInputStream derInputStream = derValue.toDerInputStream();
        this.version = derInputStream.getBigInteger();
        DerValue[] set = derInputStream.getSet(1);
        int length = set.length;
        this.digestAlgorithmIds = new AlgorithmId[length];
        for (int i2 = 0; i2 < length; i2++) {
            try {
                this.digestAlgorithmIds[i2] = AlgorithmId.parse(set[i2]);
            } catch (IOException e2) {
                ParsingException parsingException = new ParsingException(AbstractC0000a.m8d(e2, new StringBuilder("Error parsing digest AlgorithmId IDs: ")));
                parsingException.initCause(e2);
                throw parsingException;
            }
        }
        this.contentInfo = new ContentInfo(derInputStream);
        ByteArrayInputStream byteArrayInputStream3 = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException unused) {
            certificateFactory = null;
        }
        if (((byte) derInputStream.peekByte()) == -96) {
            DerValue[] set2 = derInputStream.getSet(2, true);
            int length2 = set2.length;
            this.certificates = new X509Certificate[length2];
            for (int i3 = 0; i3 < length2; i3++) {
                if (certificateFactory == null) {
                    try {
                        try {
                            this.certificates[i3] = new X509CertImpl(set2[i3]);
                        } catch (Throwable th2) {
                            th = th2;
                            byteArrayInputStream2 = null;
                            if (byteArrayInputStream2 != null) {
                                byteArrayInputStream2.close();
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        ParsingException parsingException2 = new ParsingException(e.getMessage());
                        parsingException2.initCause(e);
                        throw parsingException2;
                    } catch (CertificateException e4) {
                        e = e4;
                        ParsingException parsingException3 = new ParsingException(e.getMessage());
                        parsingException3.initCause(e);
                        throw parsingException3;
                    }
                } else {
                    byteArrayInputStream2 = new ByteArrayInputStream(set2[i3].toByteArray());
                    try {
                        this.certificates[i3] = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream2);
                        byteArrayInputStream2.close();
                    } catch (IOException e5) {
                        e = e5;
                        ParsingException parsingException22 = new ParsingException(e.getMessage());
                        parsingException22.initCause(e);
                        throw parsingException22;
                    } catch (CertificateException e6) {
                        e = e6;
                        ParsingException parsingException32 = new ParsingException(e.getMessage());
                        parsingException32.initCause(e);
                        throw parsingException32;
                    } catch (Throwable th3) {
                        th = th3;
                        if (byteArrayInputStream2 != null) {
                        }
                        throw th;
                    }
                }
            }
        }
        if (((byte) derInputStream.peekByte()) == -95) {
            DerValue[] set3 = derInputStream.getSet(1, true);
            int length3 = set3.length;
            this.crls = new X509CRL[length3];
            for (int i4 = 0; i4 < length3; i4++) {
                if (certificateFactory == null) {
                    try {
                        try {
                            this.crls[i4] = new X509CRLImpl(set3[i4]);
                        } catch (Throwable th4) {
                            th = th4;
                            byteArrayInputStream = byteArrayInputStream3;
                            if (byteArrayInputStream != null) {
                                byteArrayInputStream.close();
                            }
                            throw th;
                        }
                    } catch (CRLException e7) {
                        e = e7;
                        ParsingException parsingException4 = new ParsingException(e.getMessage());
                        parsingException4.initCause(e);
                        throw parsingException4;
                    }
                } else {
                    byteArrayInputStream = new ByteArrayInputStream(set3[i4].toByteArray());
                    try {
                        this.crls[i4] = (X509CRL) certificateFactory.generateCRL(byteArrayInputStream);
                        byteArrayInputStream.close();
                    } catch (CRLException e8) {
                        e = e8;
                        byteArrayInputStream3 = byteArrayInputStream;
                        ParsingException parsingException42 = new ParsingException(e.getMessage());
                        parsingException42.initCause(e);
                        throw parsingException42;
                    } catch (Throwable th5) {
                        th = th5;
                        if (byteArrayInputStream != null) {
                        }
                        throw th;
                    }
                }
            }
        }
        DerValue[] set4 = derInputStream.getSet(1);
        int length4 = set4.length;
        this.signerInfos = new SignerInfo[length4];
        for (int i5 = 0; i5 < length4; i5++) {
            this.signerInfos[i5] = new SignerInfo(set4[i5].toDerInputStream());
        }
    }

    private void populateCertIssuerNames() {
        X509Certificate[] x509CertificateArr = this.certificates;
        if (x509CertificateArr == null) {
            return;
        }
        this.certIssuerNames = new Principal[x509CertificateArr.length];
        int i2 = 0;
        while (true) {
            X509Certificate[] x509CertificateArr2 = this.certificates;
            if (i2 >= x509CertificateArr2.length) {
                return;
            }
            X509Certificate x509Certificate = x509CertificateArr2[i2];
            Principal issuerDN = x509Certificate.getIssuerDN();
            if (!(issuerDN instanceof X500Name)) {
                try {
                    issuerDN = (Principal) new X509CertInfo(x509Certificate.getTBSCertificate()).get("issuer.dname");
                } catch (Exception unused) {
                }
            }
            this.certIssuerNames[i2] = issuerDN;
            i2++;
        }
    }

    public void encodeSignedData(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putInteger(this.version);
        derOutputStream2.putOrderedSetOf((byte) 49, this.digestAlgorithmIds);
        this.contentInfo.encode(derOutputStream2);
        X509Certificate[] x509CertificateArr = this.certificates;
        if (x509CertificateArr != null && x509CertificateArr.length != 0) {
            X509CertImpl[] x509CertImplArr = new X509CertImpl[x509CertificateArr.length];
            int i2 = 0;
            while (true) {
                X509Certificate[] x509CertificateArr2 = this.certificates;
                if (i2 >= x509CertificateArr2.length) {
                    break;
                }
                X509Certificate x509Certificate = x509CertificateArr2[i2];
                if (x509Certificate instanceof X509CertImpl) {
                    x509CertImplArr[i2] = (X509CertImpl) x509Certificate;
                } else {
                    try {
                        x509CertImplArr[i2] = new X509CertImpl(x509Certificate.getEncoded());
                    } catch (CertificateException e2) {
                        IOException iOException = new IOException(e2.getMessage());
                        iOException.initCause(e2);
                        throw iOException;
                    }
                }
                i2++;
            }
            derOutputStream2.putOrderedSetOf((byte) -96, x509CertImplArr);
        }
        X509CRL[] x509crlArr = this.crls;
        if (x509crlArr != null && x509crlArr.length != 0) {
            HashSet hashSet = new HashSet(this.crls.length);
            for (X509CRL x509crl : this.crls) {
                if (x509crl instanceof X509CRLImpl) {
                    hashSet.add((X509CRLImpl) x509crl);
                } else {
                    try {
                        hashSet.add(new X509CRLImpl(x509crl.getEncoded()));
                    } catch (CRLException e3) {
                        IOException iOException2 = new IOException(e3.getMessage());
                        iOException2.initCause(e3);
                        throw iOException2;
                    }
                }
            }
            derOutputStream2.putOrderedSetOf((byte) -95, (DerEncoder[]) hashSet.toArray(new X509CRLImpl[hashSet.size()]));
        }
        derOutputStream2.putOrderedSetOf((byte) 49, this.signerInfos);
        new ContentInfo(ContentInfo.SIGNED_DATA_OID, new DerValue((byte) 48, derOutputStream2.toByteArray())).encode(derOutputStream);
    }

    public X509CRL[] getCRLs() {
        X509CRL[] x509crlArr = this.crls;
        if (x509crlArr != null) {
            return (X509CRL[]) x509crlArr.clone();
        }
        return null;
    }

    public X509Certificate getCertificate(BigInteger bigInteger, X500Name x500Name) {
        if (this.certificates == null) {
            return null;
        }
        if (this.certIssuerNames == null) {
            populateCertIssuerNames();
        }
        int i2 = 0;
        while (true) {
            X509Certificate[] x509CertificateArr = this.certificates;
            if (i2 >= x509CertificateArr.length) {
                return null;
            }
            X509Certificate x509Certificate = x509CertificateArr[i2];
            if (bigInteger.equals(x509Certificate.getSerialNumber()) && x500Name.equals(this.certIssuerNames[i2])) {
                return x509Certificate;
            }
            i2++;
        }
    }

    public X509Certificate[] getCertificates() {
        X509Certificate[] x509CertificateArr = this.certificates;
        if (x509CertificateArr != null) {
            return (X509Certificate[]) x509CertificateArr.clone();
        }
        return null;
    }

    public ContentInfo getContentInfo() {
        return this.contentInfo;
    }

    public AlgorithmId[] getDigestAlgorithmIds() {
        return this.digestAlgorithmIds;
    }

    public SignerInfo[] getSignerInfos() {
        return this.signerInfos;
    }

    public BigInteger getVersion() {
        return this.version;
    }

    public boolean isOldStyle() {
        return this.oldStyle;
    }

    public String toString() {
        String str = BuildConfig.FLAVOR + this.contentInfo + "\n";
        if (this.version != null) {
            StringBuilder m22r = AbstractC0000a.m22r(str, "PKCS7 :: version: ");
            m22r.append(Debug.toHexString(this.version));
            m22r.append("\n");
            str = m22r.toString();
        }
        if (this.digestAlgorithmIds != null) {
            str = AbstractC0000a.m30z(str, "PKCS7 :: digest AlgorithmIds: \n");
            for (int i2 = 0; i2 < this.digestAlgorithmIds.length; i2++) {
                StringBuilder m22r2 = AbstractC0000a.m22r(str, "\t");
                m22r2.append(this.digestAlgorithmIds[i2]);
                m22r2.append("\n");
                str = m22r2.toString();
            }
        }
        if (this.certificates != null) {
            str = AbstractC0000a.m30z(str, "PKCS7 :: certificates: \n");
            for (int i3 = 0; i3 < this.certificates.length; i3++) {
                str = str + "\t" + i3 + ".   " + this.certificates[i3] + "\n";
            }
        }
        if (this.crls != null) {
            str = AbstractC0000a.m30z(str, "PKCS7 :: crls: \n");
            for (int i4 = 0; i4 < this.crls.length; i4++) {
                str = str + "\t" + i4 + ".   " + this.crls[i4] + "\n";
            }
        }
        if (this.signerInfos != null) {
            str = AbstractC0000a.m30z(str, "PKCS7 :: signer infos: \n");
            for (int i5 = 0; i5 < this.signerInfos.length; i5++) {
                str = str + "\t" + i5 + ".  " + this.signerInfos[i5] + "\n";
            }
        }
        return str;
    }

    public SignerInfo verify(SignerInfo signerInfo, byte[] bArr) {
        return signerInfo.verify(this, bArr);
    }

    public PKCS7(InputStream inputStream) {
        this.version = null;
        this.digestAlgorithmIds = null;
        this.contentInfo = null;
        this.certificates = null;
        this.crls = null;
        this.signerInfos = null;
        this.oldStyle = false;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte[] bArr = new byte[dataInputStream.available()];
        dataInputStream.readFully(bArr);
        parse(new DerInputStream(bArr));
    }

    private void parse(DerInputStream derInputStream, boolean z2) {
        ContentInfo contentInfo = new ContentInfo(derInputStream, z2);
        this.contentInfo = contentInfo;
        this.contentType = contentInfo.contentType;
        DerValue content = contentInfo.getContent();
        if (this.contentType.equals(ContentInfo.SIGNED_DATA_OID)) {
            parseSignedData(content);
            return;
        }
        if (this.contentType.equals(ContentInfo.OLD_SIGNED_DATA_OID)) {
            parseOldSignedData(content);
        } else {
            if (this.contentType.equals(ContentInfo.NETSCAPE_CERT_SEQUENCE_OID)) {
                parseNetscapeCertChain(content);
                return;
            }
            throw new ParsingException("content type " + this.contentType + " not supported.");
        }
    }

    public void encodeSignedData(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        encodeSignedData(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    public SignerInfo[] verify() {
        return verify(null);
    }

    public PKCS7(byte[] bArr) {
        this.version = null;
        this.digestAlgorithmIds = null;
        this.contentInfo = null;
        this.certificates = null;
        this.crls = null;
        this.signerInfos = null;
        this.oldStyle = false;
        try {
            parse(new DerInputStream(bArr));
        } catch (IOException e2) {
            ParsingException parsingException = new ParsingException("Unable to parse the encoded bytes");
            parsingException.initCause(e2);
            throw parsingException;
        }
    }

    public SignerInfo[] verify(byte[] bArr) {
        Vector vector = new Vector();
        int i2 = 0;
        while (true) {
            SignerInfo[] signerInfoArr = this.signerInfos;
            if (i2 >= signerInfoArr.length) {
                break;
            }
            SignerInfo verify = verify(signerInfoArr[i2], bArr);
            if (verify != null) {
                vector.addElement(verify);
            }
            i2++;
        }
        if (vector.size() == 0) {
            return null;
        }
        SignerInfo[] signerInfoArr2 = new SignerInfo[vector.size()];
        vector.copyInto(signerInfoArr2);
        return signerInfoArr2;
    }

    public PKCS7(AlgorithmId[] algorithmIdArr, ContentInfo contentInfo, X509Certificate[] x509CertificateArr, SignerInfo[] signerInfoArr) {
        this(algorithmIdArr, contentInfo, x509CertificateArr, null, signerInfoArr);
    }

    public PKCS7(AlgorithmId[] algorithmIdArr, ContentInfo contentInfo, X509Certificate[] x509CertificateArr, X509CRL[] x509crlArr, SignerInfo[] signerInfoArr) {
        this.version = null;
        this.digestAlgorithmIds = null;
        this.contentInfo = null;
        this.certificates = null;
        this.crls = null;
        this.signerInfos = null;
        this.oldStyle = false;
        this.version = BigInteger.ONE;
        this.digestAlgorithmIds = algorithmIdArr;
        this.contentInfo = contentInfo;
        this.certificates = x509CertificateArr;
        this.crls = x509crlArr;
        this.signerInfos = signerInfoArr;
    }
}
