package android.sun.security.provider.certpath;

import android.sun.security.pkcs.ContentInfo;
import android.sun.security.pkcs.PKCS7;
import android.sun.security.pkcs.SignerInfo;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.x509.AlgorithmId;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509CertPath extends CertPath {
    private static final String COUNT_ENCODING = "count";
    private static final String PKCS7_ENCODING = "PKCS7";
    private static final String PKIPATH_ENCODING = "PkiPath";
    private static final Collection<String> encodingList;
    private static final long serialVersionUID = 4989800333263052980L;
    private List<X509Certificate> certs;

    static {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(PKIPATH_ENCODING);
        arrayList.add("PKCS7");
        encodingList = Collections.unmodifiableCollection(arrayList);
    }

    public X509CertPath(InputStream inputStream) {
        this(inputStream, PKIPATH_ENCODING);
    }

    private byte[] encodePKCS7() {
        ContentInfo contentInfo = new ContentInfo(ContentInfo.DATA_OID, (DerValue) null);
        List<X509Certificate> list = this.certs;
        PKCS7 pkcs7 = new PKCS7(new AlgorithmId[0], contentInfo, (X509Certificate[]) list.toArray(new X509Certificate[list.size()]), new SignerInfo[0]);
        DerOutputStream derOutputStream = new DerOutputStream();
        try {
            pkcs7.encodeSignedData(derOutputStream);
            return derOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new CertificateEncodingException(e2.getMessage());
        }
    }

    private byte[] encodePKIPATH() {
        List<X509Certificate> list = this.certs;
        ListIterator<X509Certificate> listIterator = list.listIterator(list.size());
        try {
            DerOutputStream derOutputStream = new DerOutputStream();
            while (listIterator.hasPrevious()) {
                X509Certificate previous = listIterator.previous();
                if (this.certs.lastIndexOf(previous) != this.certs.indexOf(previous)) {
                    throw new CertificateEncodingException("Duplicate Certificate");
                }
                derOutputStream.write(previous.getEncoded());
            }
            DerOutputStream derOutputStream2 = new DerOutputStream();
            derOutputStream2.write((byte) 48, derOutputStream);
            return derOutputStream2.toByteArray();
        } catch (IOException e2) {
            CertificateEncodingException certificateEncodingException = new CertificateEncodingException(AbstractC0000a.m13i("IOException encoding PkiPath data: ", e2));
            certificateEncodingException.initCause(e2);
            throw certificateEncodingException;
        }
    }

    public static Iterator<String> getEncodingsStatic() {
        return encodingList.iterator();
    }

    private static List<X509Certificate> parsePKCS7(InputStream inputStream) {
        if (inputStream == null) {
            throw new CertificateException("input stream is null");
        }
        try {
            if (!inputStream.markSupported()) {
                inputStream = new ByteArrayInputStream(readAllBytes(inputStream));
            }
            X509Certificate[] certificates = new PKCS7(inputStream).getCertificates();
            return Collections.unmodifiableList(certificates != null ? Arrays.asList(certificates) : new ArrayList(0));
        } catch (IOException e2) {
            throw new CertificateException(AbstractC0000a.m13i("IOException parsing PKCS7 data: ", e2));
        }
    }

    private static List<X509Certificate> parsePKIPATH(InputStream inputStream) {
        if (inputStream == null) {
            throw new CertificateException("input stream is null");
        }
        try {
            DerValue[] sequence = new DerInputStream(readAllBytes(inputStream)).getSequence(3);
            if (sequence.length == 0) {
                return Collections.emptyList();
            }
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ArrayList arrayList = new ArrayList(sequence.length);
            int length = sequence.length;
            while (true) {
                length--;
                if (length < 0) {
                    return Collections.unmodifiableList(arrayList);
                }
                arrayList.add((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(sequence[length].toByteArray())));
            }
        } catch (IOException e2) {
            CertificateException certificateException = new CertificateException(AbstractC0000a.m13i("IOException parsing PkiPath data: ", e2));
            certificateException.initCause(e2);
            throw certificateException;
        }
    }

    private static byte[] readAllBytes(InputStream inputStream) {
        byte[] bArr = new byte[8192];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    @Override // java.security.cert.CertPath
    public List<X509Certificate> getCertificates() {
        return this.certs;
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded() {
        return encodePKIPATH();
    }

    @Override // java.security.cert.CertPath
    public Iterator<String> getEncodings() {
        return getEncodingsStatic();
    }

    public X509CertPath(InputStream inputStream, String str) {
        super("X.509");
        List<X509Certificate> parsePKCS7;
        if (PKIPATH_ENCODING.equals(str)) {
            parsePKCS7 = parsePKIPATH(inputStream);
        } else {
            if (!"PKCS7".equals(str)) {
                throw new CertificateException("unsupported encoding");
            }
            parsePKCS7 = parsePKCS7(inputStream);
        }
        this.certs = parsePKCS7;
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded(String str) {
        if (PKIPATH_ENCODING.equals(str)) {
            return encodePKIPATH();
        }
        if ("PKCS7".equals(str)) {
            return encodePKCS7();
        }
        throw new CertificateEncodingException("unsupported encoding");
    }

    public X509CertPath(List<? extends Certificate> list) {
        super("X.509");
        for (Certificate certificate : list) {
            if (!(certificate instanceof X509Certificate)) {
                throw new CertificateException(AbstractC0000a.m10f(certificate, "List is not all X509Certificates: "));
            }
        }
        this.certs = Collections.unmodifiableList(new ArrayList(list));
    }
}
