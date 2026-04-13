package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class CertificateStatusRequestItemV2 {
    protected Object request;
    protected short statusType;

    public CertificateStatusRequestItemV2(short s2, Object obj) {
        if (!isCorrectType(s2, obj)) {
            throw new IllegalArgumentException("'request' is not an instance of the correct type");
        }
        this.statusType = s2;
        this.request = obj;
    }

    public static boolean isCorrectType(short s2, Object obj) {
        if (s2 == 1 || s2 == 2) {
            return obj instanceof OCSPStatusRequest;
        }
        throw new IllegalArgumentException("'statusType' is an unsupported CertificateStatusType");
    }

    public static CertificateStatusRequestItemV2 parse(InputStream inputStream) {
        short readUint8 = TlsUtils.readUint8(inputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TlsUtils.readOpaque16(inputStream));
        if (readUint8 != 1 && readUint8 != 2) {
            throw new TlsFatalAlert((short) 50);
        }
        OCSPStatusRequest parse = OCSPStatusRequest.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        return new CertificateStatusRequestItemV2(readUint8, parse);
    }

    public void encode(OutputStream outputStream) {
        TlsUtils.writeUint8(this.statusType, outputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        short s2 = this.statusType;
        if (s2 != 1 && s2 != 2) {
            throw new TlsFatalAlert((short) 80);
        }
        ((OCSPStatusRequest) this.request).encode(byteArrayOutputStream);
        TlsUtils.writeOpaque16(byteArrayOutputStream.toByteArray(), outputStream);
    }

    public OCSPStatusRequest getOCSPStatusRequest() {
        Object obj = this.request;
        if (obj instanceof OCSPStatusRequest) {
            return (OCSPStatusRequest) obj;
        }
        throw new IllegalStateException("'request' is not an OCSPStatusRequest");
    }

    public Object getRequest() {
        return this.request;
    }

    public short getStatusType() {
        return this.statusType;
    }
}
