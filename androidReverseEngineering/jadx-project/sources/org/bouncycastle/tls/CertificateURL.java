package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

/* loaded from: classes.dex */
public class CertificateURL {
    protected short type;
    protected Vector urlAndHashList;

    public class ListBuffer16 extends ByteArrayOutputStream {
        public ListBuffer16() {
            TlsUtils.writeUint16(0, this);
        }

        public void encodeTo(OutputStream outputStream) {
            int i2 = ((ByteArrayOutputStream) this).count - 2;
            TlsUtils.checkUint16(i2);
            TlsUtils.writeUint16(i2, ((ByteArrayOutputStream) this).buf, 0);
            outputStream.write(((ByteArrayOutputStream) this).buf, 0, ((ByteArrayOutputStream) this).count);
            ((ByteArrayOutputStream) this).buf = null;
        }
    }

    public CertificateURL(short s2, Vector vector) {
        if (!CertChainType.isValid(s2)) {
            throw new IllegalArgumentException("'type' is not a valid CertChainType value");
        }
        if (vector == null || vector.isEmpty()) {
            throw new IllegalArgumentException("'urlAndHashList' must have length > 0");
        }
        if (s2 != 1 || vector.size() == 1) {
            this.type = s2;
            this.urlAndHashList = vector;
        } else {
            throw new IllegalArgumentException("'urlAndHashList' must contain exactly one entry when type is " + CertChainType.getText(s2));
        }
    }

    public static CertificateURL parse(TlsContext tlsContext, InputStream inputStream) {
        short readUint8 = TlsUtils.readUint8(inputStream);
        if (!CertChainType.isValid(readUint8)) {
            throw new TlsFatalAlert((short) 50);
        }
        int readUint16 = TlsUtils.readUint16(inputStream);
        if (readUint16 < 1) {
            throw new TlsFatalAlert((short) 50);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TlsUtils.readFully(readUint16, inputStream));
        Vector vector = new Vector();
        while (byteArrayInputStream.available() > 0) {
            vector.addElement(URLAndHash.parse(tlsContext, byteArrayInputStream));
        }
        if (readUint8 != 1 || vector.size() == 1) {
            return new CertificateURL(readUint8, vector);
        }
        throw new TlsFatalAlert((short) 50);
    }

    public void encode(OutputStream outputStream) {
        TlsUtils.writeUint8(this.type, outputStream);
        ListBuffer16 listBuffer16 = new ListBuffer16();
        for (int i2 = 0; i2 < this.urlAndHashList.size(); i2++) {
            ((URLAndHash) this.urlAndHashList.elementAt(i2)).encode(listBuffer16);
        }
        listBuffer16.encodeTo(outputStream);
    }

    public short getType() {
        return this.type;
    }

    public Vector getURLAndHashList() {
        return this.urlAndHashList;
    }
}
