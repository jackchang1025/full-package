package org.bouncycastle.mime.smime;

import android.support.v4.os.EnvironmentCompat;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.CMSSignedDataStreamGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.mime.Headers;
import org.bouncycastle.mime.MimeWriter;
import org.bouncycastle.mime.encoding.Base64OutputStream;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
public class SMIMESignedWriter extends MimeWriter {
    public static final Map RFC3851_MICALGS;
    public static final Map RFC5751_MICALGS;
    public static final Map STANDARD_MICALGS;
    private final String boundary;
    private final String contentTransferEncoding;
    private final OutputStream mimeOut;
    private final CMSSignedDataStreamGenerator sigGen;

    public static class Builder {
        private static final String[] detHeaders = {"Content-Type"};
        private static final String[] detValues = {"multipart/signed; protocol=\"application/pkcs7-signature\""};
        private static final String[] encHeaders = {"Content-Type", "Content-Disposition", "Content-Transfer-Encoding", "Content-Description"};
        private static final String[] encValues = {"application/pkcs7-mime; name=\"smime.p7m\"; smime-type=enveloped-data", "attachment; filename=\"smime.p7m\"", "base64", "S/MIME Signed Message"};
        String contentTransferEncoding;
        private final boolean encapsulated;
        private final Map<String, String> extraHeaders;
        private final Map micAlgs;
        private final CMSSignedDataStreamGenerator sigGen;

        public Builder() {
            this(false);
        }

        private void addBoundary(StringBuffer stringBuffer, String str) {
            stringBuffer.append(";\r\n\tboundary=\"");
            stringBuffer.append(str);
            stringBuffer.append("\"");
        }

        private void addHashHeader(StringBuffer stringBuffer, List list) {
            Iterator it = list.iterator();
            TreeSet treeSet = new TreeSet();
            while (it.hasNext()) {
                String str = (String) this.micAlgs.get(((AlgorithmIdentifier) it.next()).getAlgorithm());
                if (str == null) {
                    str = EnvironmentCompat.MEDIA_UNKNOWN;
                }
                treeSet.add(str);
            }
            Iterator it2 = treeSet.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                if (i2 == 0) {
                    stringBuffer.append(treeSet.size() != 1 ? "; micalg=\"" : "; micalg=");
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(str2);
                i2++;
            }
            if (i2 == 0 || treeSet.size() == 1) {
                return;
            }
            stringBuffer.append('\"');
        }

        private String generateBoundary() {
            return "==" + new BigInteger(CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA256, new SecureRandom()).setBit(CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384).toString(16) + "=";
        }

        public Builder addCertificate(X509CertificateHolder x509CertificateHolder) {
            this.sigGen.addCertificate(x509CertificateHolder);
            return this;
        }

        public Builder addCertificates(Store store) {
            this.sigGen.addCertificates(store);
            return this;
        }

        public Builder addSignerInfoGenerator(SignerInfoGenerator signerInfoGenerator) {
            this.sigGen.addSignerInfoGenerator(signerInfoGenerator);
            return this;
        }

        public SMIMESignedWriter build(OutputStream outputStream) {
            String generateBoundary;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int i2 = 0;
            if (!this.encapsulated) {
                generateBoundary = generateBoundary();
                StringBuffer stringBuffer = new StringBuffer(detValues[0]);
                addHashHeader(stringBuffer, this.sigGen.getDigestAlgorithms());
                addBoundary(stringBuffer, generateBoundary);
                linkedHashMap.put(detHeaders[0], stringBuffer.toString());
                int i3 = 1;
                while (true) {
                    String[] strArr = detHeaders;
                    if (i3 >= strArr.length) {
                        break;
                    }
                    linkedHashMap.put(strArr[i3], detValues[i3]);
                    i3++;
                }
            } else {
                while (true) {
                    String[] strArr2 = encHeaders;
                    if (i2 == strArr2.length) {
                        break;
                    }
                    linkedHashMap.put(strArr2[i2], encValues[i2]);
                    i2++;
                }
                generateBoundary = null;
            }
            String str = generateBoundary;
            for (Map.Entry<String, String> entry : this.extraHeaders.entrySet()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
            return new SMIMESignedWriter(this, linkedHashMap, str, SMimeUtils.autoBuffer(outputStream));
        }

        public Builder withHeader(String str, String str2) {
            this.extraHeaders.put(str, str2);
            return this;
        }

        public Builder(boolean z2) {
            this.sigGen = new CMSSignedDataStreamGenerator();
            this.extraHeaders = new LinkedHashMap();
            this.micAlgs = SMIMESignedWriter.STANDARD_MICALGS;
            this.contentTransferEncoding = "base64";
            this.encapsulated = z2;
        }
    }

    public class ContentOutputStream extends OutputStream {
        private final OutputStream backing;
        private final OutputStream main;
        private final OutputStream sigBase;
        private final ByteArrayOutputStream sigStream;

        public ContentOutputStream(OutputStream outputStream, OutputStream outputStream2, ByteArrayOutputStream byteArrayOutputStream, OutputStream outputStream3) {
            this.main = outputStream;
            this.backing = outputStream2;
            this.sigStream = byteArrayOutputStream;
            this.sigBase = outputStream3;
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (SMIMESignedWriter.this.boundary != null) {
                this.main.close();
                this.backing.write(Strings.toByteArray("\r\n--"));
                this.backing.write(Strings.toByteArray(SMIMESignedWriter.this.boundary));
                this.backing.write(Strings.toByteArray("\r\n"));
                this.backing.write(Strings.toByteArray("Content-Type: application/pkcs7-signature; name=\"smime.p7s\"\r\n"));
                this.backing.write(Strings.toByteArray("Content-Transfer-Encoding: base64\r\n"));
                this.backing.write(Strings.toByteArray("Content-Disposition: attachment; filename=\"smime.p7s\"\r\n"));
                this.backing.write(Strings.toByteArray("\r\n"));
                OutputStream outputStream = this.sigBase;
                if (outputStream != null) {
                    outputStream.close();
                }
                this.backing.write(this.sigStream.toByteArray());
                this.backing.write(Strings.toByteArray("\r\n--"));
                this.backing.write(Strings.toByteArray(SMIMESignedWriter.this.boundary));
                this.backing.write(Strings.toByteArray("--\r\n"));
            }
            OutputStream outputStream2 = this.backing;
            if (outputStream2 != null) {
                outputStream2.close();
            }
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
            this.main.write(i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            this.main.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            this.main.write(bArr, i2, i3);
        }
    }

    static {
        HashMap hashMap = new HashMap();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = CMSAlgorithm.MD5;
        hashMap.put(aSN1ObjectIdentifier, "md5");
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = CMSAlgorithm.SHA1;
        hashMap.put(aSN1ObjectIdentifier2, "sha-1");
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = CMSAlgorithm.SHA224;
        hashMap.put(aSN1ObjectIdentifier3, "sha-224");
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = CMSAlgorithm.SHA256;
        hashMap.put(aSN1ObjectIdentifier4, "sha-256");
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = CMSAlgorithm.SHA384;
        hashMap.put(aSN1ObjectIdentifier5, "sha-384");
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = CMSAlgorithm.SHA512;
        hashMap.put(aSN1ObjectIdentifier6, "sha-512");
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = CMSAlgorithm.GOST3411;
        hashMap.put(aSN1ObjectIdentifier7, "gostr3411-94");
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = CMSAlgorithm.GOST3411_2012_256;
        hashMap.put(aSN1ObjectIdentifier8, "gostr3411-2012-256");
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = CMSAlgorithm.GOST3411_2012_512;
        hashMap.put(aSN1ObjectIdentifier9, "gostr3411-2012-512");
        Map unmodifiableMap = Collections.unmodifiableMap(hashMap);
        RFC5751_MICALGS = unmodifiableMap;
        HashMap hashMap2 = new HashMap();
        hashMap2.put(aSN1ObjectIdentifier, "md5");
        hashMap2.put(aSN1ObjectIdentifier2, "sha1");
        hashMap2.put(aSN1ObjectIdentifier3, "sha224");
        hashMap2.put(aSN1ObjectIdentifier4, "sha256");
        hashMap2.put(aSN1ObjectIdentifier5, "sha384");
        hashMap2.put(aSN1ObjectIdentifier6, "sha512");
        hashMap2.put(aSN1ObjectIdentifier7, "gostr3411-94");
        hashMap2.put(aSN1ObjectIdentifier8, "gostr3411-2012-256");
        hashMap2.put(aSN1ObjectIdentifier9, "gostr3411-2012-512");
        RFC3851_MICALGS = Collections.unmodifiableMap(hashMap2);
        STANDARD_MICALGS = unmodifiableMap;
    }

    private SMIMESignedWriter(Builder builder, Map<String, String> map, String str, OutputStream outputStream) {
        super(new Headers(MimeWriter.mapToLines(map), builder.contentTransferEncoding));
        this.sigGen = builder.sigGen;
        this.contentTransferEncoding = builder.contentTransferEncoding;
        this.boundary = str;
        this.mimeOut = outputStream;
    }

    @Override // org.bouncycastle.mime.MimeWriter
    public OutputStream getContentStream() {
        this.headers.dumpHeaders(this.mimeOut);
        this.mimeOut.write(Strings.toByteArray("\r\n"));
        if (this.boundary == null) {
            return null;
        }
        this.mimeOut.write(Strings.toByteArray("This is an S/MIME signed message\r\n"));
        this.mimeOut.write(Strings.toByteArray("\r\n--"));
        this.mimeOut.write(Strings.toByteArray(this.boundary));
        this.mimeOut.write(Strings.toByteArray("\r\n"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream);
        return new ContentOutputStream(this.sigGen.open((OutputStream) base64OutputStream, false, SMimeUtils.createUnclosable(this.mimeOut)), this.mimeOut, byteArrayOutputStream, base64OutputStream);
    }
}
