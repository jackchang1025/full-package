package android.sun.security.provider;

import android.sun.security.pkcs.PKCS7;
import android.sun.security.pkcs.ParsingException;
import android.sun.security.provider.certpath.X509CertPath;
import android.sun.security.provider.certpath.X509CertificatePair;
import android.sun.security.util.Cache;
import android.sun.security.x509.X509CRLImpl;
import android.sun.security.x509.X509CertImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class X509Factory extends CertificateFactorySpi {
    public static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    private static final int ENC_MAX_LENGTH = 4194304;
    public static final String END_CERT = "-----END CERTIFICATE-----";
    private static final Cache certCache = Cache.newSoftMemoryCache(750);
    private static final Cache crlCache = Cache.newSoftMemoryCache(750);

    private static synchronized void addToCache(Cache cache, byte[] bArr, Object obj) {
        synchronized (X509Factory.class) {
            if (bArr.length > 4194304) {
                return;
            }
            cache.put(new Cache.EqualByteArray(bArr), obj);
        }
    }

    private static void checkHeaderFooter(String str, String str2) {
        if (str.length() < 16 || !str.startsWith("-----BEGIN ") || !str.endsWith("-----")) {
            throw new IOException("Illegal header: ".concat(str));
        }
        if (str2.length() < 14 || !str2.startsWith("-----END ") || !str2.endsWith("-----")) {
            throw new IOException("Illegal footer: ".concat(str2));
        }
        if (str.substring(11, str.length() - 5).equals(str2.substring(9, str2.length() - 5))) {
            return;
        }
        throw new IOException("Header and footer do not match: " + str + " " + str2);
    }

    private static synchronized Object getFromCache(Cache cache, byte[] bArr) {
        Object obj;
        synchronized (X509Factory.class) {
            obj = cache.get(new Cache.EqualByteArray(bArr));
        }
        return obj;
    }

    public static synchronized X509CRLImpl intern(X509CRL x509crl) {
        X509CRLImpl x509CRLImpl;
        synchronized (X509Factory.class) {
            if (x509crl == null) {
                return null;
            }
            boolean z2 = x509crl instanceof X509CRLImpl;
            byte[] encodedInternal = z2 ? ((X509CRLImpl) x509crl).getEncodedInternal() : x509crl.getEncoded();
            Cache cache = crlCache;
            X509CRLImpl x509CRLImpl2 = (X509CRLImpl) getFromCache(cache, encodedInternal);
            if (x509CRLImpl2 != null) {
                return x509CRLImpl2;
            }
            if (z2) {
                x509CRLImpl = (X509CRLImpl) x509crl;
            } else {
                x509CRLImpl = new X509CRLImpl(encodedInternal);
                encodedInternal = x509CRLImpl.getEncodedInternal();
            }
            addToCache(cache, encodedInternal, x509CRLImpl);
            return x509CRLImpl;
        }
    }

    private Collection<? extends CRL> parseX509orPKCS7CRL(InputStream inputStream) {
        ArrayList arrayList = new ArrayList();
        byte[] readOneBlock = readOneBlock(inputStream);
        if (readOneBlock == null) {
            return new ArrayList(0);
        }
        try {
            X509CRL[] cRLs = new PKCS7(readOneBlock).getCRLs();
            return cRLs != null ? Arrays.asList(cRLs) : new ArrayList(0);
        } catch (ParsingException unused) {
            while (readOneBlock != null) {
                arrayList.add(new X509CRLImpl(readOneBlock));
                readOneBlock = readOneBlock(inputStream);
            }
            return arrayList;
        }
    }

    private Collection<? extends Certificate> parseX509orPKCS7Cert(InputStream inputStream) {
        ArrayList arrayList = new ArrayList();
        byte[] readOneBlock = readOneBlock(inputStream);
        if (readOneBlock == null) {
            return new ArrayList(0);
        }
        try {
            X509Certificate[] certificates = new PKCS7(readOneBlock).getCertificates();
            return certificates != null ? Arrays.asList(certificates) : new ArrayList(0);
        } catch (ParsingException unused) {
            while (readOneBlock != null) {
                arrayList.add(new X509CertImpl(readOneBlock));
                readOneBlock = readOneBlock(inputStream);
            }
            return arrayList;
        }
    }

    private static int readBERInternal(InputStream inputStream, ByteArrayOutputStream byteArrayOutputStream, int i2) {
        if (i2 == -1) {
            i2 = inputStream.read();
            if (i2 == -1) {
                throw new IOException("BER/DER tag info absent");
            }
            if ((i2 & 31) == 31) {
                throw new IOException("Multi octets tag not supported");
            }
            byteArrayOutputStream.write(i2);
        }
        int read = inputStream.read();
        if (read == -1) {
            throw new IOException("BER/DER length info ansent");
        }
        byteArrayOutputStream.write(read);
        if (read != 128) {
            if (read >= 128) {
                if (read == 129) {
                    read = inputStream.read();
                    if (read == -1) {
                        throw new IOException("Incomplete BER/DER length info");
                    }
                    byteArrayOutputStream.write(read);
                } else if (read == 130) {
                    int read2 = inputStream.read();
                    int read3 = inputStream.read();
                    if (read3 == -1) {
                        throw new IOException("Incomplete BER/DER length info");
                    }
                    byteArrayOutputStream.write(read2);
                    byteArrayOutputStream.write(read3);
                    read = (read2 << 8) | read3;
                } else {
                    if (read != 131) {
                        throw new IOException("Invalid BER/DER data (too huge?)");
                    }
                    int read4 = inputStream.read();
                    int read5 = inputStream.read();
                    int read6 = inputStream.read();
                    if (read6 == -1) {
                        throw new IOException("Incomplete BER/DER length info");
                    }
                    byteArrayOutputStream.write(read4);
                    byteArrayOutputStream.write(read5);
                    byteArrayOutputStream.write(read6);
                    read = (read4 << 16) | (read5 << 8) | read6;
                }
            }
            if (readFully(inputStream, byteArrayOutputStream, read) != read) {
                throw new IOException("Incomplete BER/DER data");
            }
        } else {
            if ((i2 & 32) != 32) {
                throw new IOException("Non constructed encoding must have definite length");
            }
            while (readBERInternal(inputStream, byteArrayOutputStream, -1) != 0) {
            }
        }
        return i2;
    }

    private static int readFully(InputStream inputStream, ByteArrayOutputStream byteArrayOutputStream, int i2) {
        byte[] bArr = new byte[2048];
        int i3 = 0;
        while (i2 > 0) {
            int read = inputStream.read(bArr, 0, i2 < 2048 ? i2 : 2048);
            if (read <= 0) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, read);
            i3 += read;
            i2 -= read;
        }
        return i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x004e, code lost:
    
        r1 = r12.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0054, code lost:
    
        if (r1 == (-1)) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0056, code lost:
    
        if (r1 != 10) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005b, code lost:
    
        if (r1 != 13) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c2, code lost:
    
        r0.append((char) r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x005d, code lost:
    
        r1 = r12.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0061, code lost:
    
        if (r1 == (-1)) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0063, code lost:
    
        if (r1 != 10) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0066, code lost:
    
        r3[0] = (char) r1;
        r1 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x006a, code lost:
    
        r10 = r12.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x006e, code lost:
    
        if (r10 == (-1)) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0070, code lost:
    
        if (r10 == 45) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0072, code lost:
    
        r11 = r4 + 1;
        r3[r4] = (char) r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0078, code lost:
    
        if (r11 < r3.length) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x007a, code lost:
    
        r3 = java.util.Arrays.copyOf(r3, r3.length + 1024);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0081, code lost:
    
        r4 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0083, code lost:
    
        r5 = new java.lang.StringBuffer("-");
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008a, code lost:
    
        r7 = r12.read();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x008e, code lost:
    
        if (r7 == (-1)) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0090, code lost:
    
        if (r7 == r1) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0092, code lost:
    
        if (r7 != 10) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0095, code lost:
    
        if (r7 == 13) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0097, code lost:
    
        r5.append((char) r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x009c, code lost:
    
        checkHeaderFooter(r0.toString(), r5.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00b5, code lost:
    
        return new android.sun.misc.BASE64Decoder().decodeBuffer(new java.lang.String(r3, 0, r4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00bb, code lost:
    
        throw new java.io.IOException("Incomplete data");
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0058, code lost:
    
        r4 = 0;
        r1 = 10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00c1, code lost:
    
        throw new java.io.IOException("Incomplete data");
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00cc, code lost:
    
        throw new java.io.IOException("Incomplete data");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] readOneBlock(InputStream inputStream) {
        int read = inputStream.read();
        if (read == -1) {
            return null;
        }
        if (read == 48) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
            byteArrayOutputStream.write(read);
            readBERInternal(inputStream, byteArrayOutputStream, read);
            return byteArrayOutputStream.toByteArray();
        }
        char[] cArr = new char[2048];
        int i2 = 1;
        int i3 = read == 45 ? 1 : 0;
        if (read == 45) {
            read = -1;
        }
        while (true) {
            int read2 = inputStream.read();
            if (read2 == -1) {
                return null;
            }
            if (read2 == 45) {
                i3++;
            } else {
                i3 = 0;
                read = read2;
            }
            if (i3 != 5 || (read != -1 && read != 13 && read != 10)) {
            }
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public CRL engineGenerateCRL(InputStream inputStream) {
        if (inputStream == null) {
            crlCache.clear();
            throw new CRLException("Missing input stream");
        }
        try {
            byte[] readOneBlock = readOneBlock(inputStream);
            if (readOneBlock == null) {
                throw new IOException("Empty input");
            }
            Cache cache = crlCache;
            X509CRLImpl x509CRLImpl = (X509CRLImpl) getFromCache(cache, readOneBlock);
            if (x509CRLImpl != null) {
                return x509CRLImpl;
            }
            X509CRLImpl x509CRLImpl2 = new X509CRLImpl(readOneBlock);
            addToCache(cache, x509CRLImpl2.getEncodedInternal(), x509CRLImpl2);
            return x509CRLImpl2;
        } catch (IOException e2) {
            throw new CRLException(e2.getMessage());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public Collection<? extends CRL> engineGenerateCRLs(InputStream inputStream) {
        if (inputStream == null) {
            throw new CRLException("Missing input stream");
        }
        try {
            return parseX509orPKCS7CRL(inputStream);
        } catch (IOException e2) {
            throw new CRLException(e2.getMessage());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public CertPath engineGenerateCertPath(InputStream inputStream) {
        if (inputStream == null) {
            throw new CertificateException("Missing input stream");
        }
        try {
            byte[] readOneBlock = readOneBlock(inputStream);
            if (readOneBlock != null) {
                return new X509CertPath(new ByteArrayInputStream(readOneBlock));
            }
            throw new IOException("Empty input");
        } catch (IOException e2) {
            throw new CertificateException(e2.getMessage());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public Certificate engineGenerateCertificate(InputStream inputStream) {
        if (inputStream == null) {
            certCache.clear();
            X509CertificatePair.clearCache();
            throw new CertificateException("Missing input stream");
        }
        try {
            byte[] readOneBlock = readOneBlock(inputStream);
            if (readOneBlock == null) {
                throw new IOException("Empty input");
            }
            Cache cache = certCache;
            X509CertImpl x509CertImpl = (X509CertImpl) getFromCache(cache, readOneBlock);
            if (x509CertImpl != null) {
                return x509CertImpl;
            }
            X509CertImpl x509CertImpl2 = new X509CertImpl(readOneBlock);
            addToCache(cache, x509CertImpl2.getEncodedInternal(), x509CertImpl2);
            return x509CertImpl2;
        } catch (IOException e2) {
            throw ((CertificateException) new CertificateException("Could not parse certificate: " + e2.toString()).initCause(e2));
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public Collection<? extends Certificate> engineGenerateCertificates(InputStream inputStream) {
        if (inputStream == null) {
            throw new CertificateException("Missing input stream");
        }
        try {
            return parseX509orPKCS7Cert(inputStream);
        } catch (IOException e2) {
            throw new CertificateException(e2);
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public Iterator<String> engineGetCertPathEncodings() {
        return X509CertPath.getEncodingsStatic();
    }

    public static synchronized X509CertImpl intern(X509Certificate x509Certificate) {
        X509CertImpl x509CertImpl;
        synchronized (X509Factory.class) {
            if (x509Certificate == null) {
                return null;
            }
            boolean z2 = x509Certificate instanceof X509CertImpl;
            byte[] encodedInternal = z2 ? ((X509CertImpl) x509Certificate).getEncodedInternal() : x509Certificate.getEncoded();
            Cache cache = certCache;
            X509CertImpl x509CertImpl2 = (X509CertImpl) getFromCache(cache, encodedInternal);
            if (x509CertImpl2 != null) {
                return x509CertImpl2;
            }
            if (z2) {
                x509CertImpl = (X509CertImpl) x509Certificate;
            } else {
                x509CertImpl = new X509CertImpl(encodedInternal);
                encodedInternal = x509CertImpl.getEncodedInternal();
            }
            addToCache(cache, encodedInternal, x509CertImpl);
            return x509CertImpl;
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public CertPath engineGenerateCertPath(InputStream inputStream, String str) {
        if (inputStream == null) {
            throw new CertificateException("Missing input stream");
        }
        try {
            byte[] readOneBlock = readOneBlock(inputStream);
            if (readOneBlock != null) {
                return new X509CertPath(new ByteArrayInputStream(readOneBlock), str);
            }
            throw new IOException("Empty input");
        } catch (IOException e2) {
            throw new CertificateException(e2.getMessage());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public CertPath engineGenerateCertPath(List<? extends Certificate> list) {
        return new X509CertPath(list);
    }
}
