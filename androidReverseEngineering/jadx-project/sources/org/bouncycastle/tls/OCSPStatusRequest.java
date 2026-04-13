package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ocsp.ResponderID;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public class OCSPStatusRequest {
    protected Extensions requestExtensions;
    protected Vector responderIDList;

    public OCSPStatusRequest(Vector vector, Extensions extensions) {
        this.responderIDList = vector;
        this.requestExtensions = extensions;
    }

    public static OCSPStatusRequest parse(InputStream inputStream) {
        Extensions extensions;
        Vector vector = new Vector();
        byte[] readOpaque16 = TlsUtils.readOpaque16(inputStream);
        if (readOpaque16.length > 0) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readOpaque16);
            do {
                byte[] readOpaque162 = TlsUtils.readOpaque16(byteArrayInputStream, 1);
                ResponderID responderID = ResponderID.getInstance(TlsUtils.readASN1Object(readOpaque162));
                TlsUtils.requireDEREncoding(responderID, readOpaque162);
                vector.addElement(responderID);
            } while (byteArrayInputStream.available() > 0);
        }
        byte[] readOpaque163 = TlsUtils.readOpaque16(inputStream);
        if (readOpaque163.length > 0) {
            extensions = Extensions.getInstance(TlsUtils.readASN1Object(readOpaque163));
            TlsUtils.requireDEREncoding(extensions, readOpaque163);
        } else {
            extensions = null;
        }
        return new OCSPStatusRequest(vector, extensions);
    }

    public void encode(OutputStream outputStream) {
        Vector vector = this.responderIDList;
        if (vector == null || vector.isEmpty()) {
            TlsUtils.writeUint16(0, outputStream);
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i2 = 0; i2 < this.responderIDList.size(); i2++) {
                TlsUtils.writeOpaque16(((ResponderID) this.responderIDList.elementAt(i2)).getEncoded(ASN1Encoding.DER), byteArrayOutputStream);
            }
            TlsUtils.checkUint16(byteArrayOutputStream.size());
            TlsUtils.writeUint16(byteArrayOutputStream.size(), outputStream);
            Streams.writeBufTo(byteArrayOutputStream, outputStream);
        }
        Extensions extensions = this.requestExtensions;
        if (extensions == null) {
            TlsUtils.writeUint16(0, outputStream);
            return;
        }
        byte[] encoded = extensions.getEncoded(ASN1Encoding.DER);
        TlsUtils.checkUint16(encoded.length);
        TlsUtils.writeUint16(encoded.length, outputStream);
        outputStream.write(encoded);
    }

    public Extensions getRequestExtensions() {
        return this.requestExtensions;
    }

    public Vector getResponderIDList() {
        return this.responderIDList;
    }
}
