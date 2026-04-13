package org.bouncycastle.cert.crmf;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.cert.CertIOException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class CRMFUtil {
    public static void addExtension(ExtensionsGenerator extensionsGenerator, ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z2, ASN1Encodable aSN1Encodable) {
        try {
            extensionsGenerator.addExtension(aSN1ObjectIdentifier, z2, aSN1Encodable);
        } catch (IOException e2) {
            throw new CertIOException(AbstractC0000a.m8d(e2, new StringBuilder("cannot encode extension: ")), e2);
        }
    }

    public static void derEncodeToStream(ASN1Object aSN1Object, OutputStream outputStream) {
        try {
            aSN1Object.encodeTo(outputStream, ASN1Encoding.DER);
            outputStream.close();
        } catch (IOException e2) {
            throw new CRMFRuntimeException(AbstractC0000a.m8d(e2, new StringBuilder("unable to DER encode object: ")), e2);
        }
    }
}
