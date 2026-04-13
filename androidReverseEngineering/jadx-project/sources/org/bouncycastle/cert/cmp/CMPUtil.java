package org.bouncycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Object;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class CMPUtil {
    public static void derEncodeToStream(ASN1Object aSN1Object, OutputStream outputStream) {
        try {
            aSN1Object.encodeTo(outputStream, ASN1Encoding.DER);
            outputStream.close();
        } catch (IOException e2) {
            throw new CMPRuntimeException(AbstractC0000a.m8d(e2, new StringBuilder("unable to DER encode object: ")), e2);
        }
    }
}
