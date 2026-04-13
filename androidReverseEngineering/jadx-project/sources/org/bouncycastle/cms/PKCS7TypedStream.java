package org.bouncycastle.cms;

import android.sun.security.util.DerValue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS7TypedStream extends CMSTypedStream {
    private final ASN1Encodable content;

    public PKCS7TypedStream(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        super(aSN1ObjectIdentifier);
        this.content = aSN1Encodable;
    }

    @Override // org.bouncycastle.cms.CMSTypedStream
    public void drain() {
        this.content.toASN1Primitive();
    }

    public ASN1Encodable getContent() {
        return this.content;
    }

    @Override // org.bouncycastle.cms.CMSTypedStream
    public InputStream getContentStream() {
        try {
            return getContentStream(this.content);
        } catch (IOException e2) {
            throw new CMSRuntimeException(AbstractC0000a.m8d(e2, new StringBuilder("unable to convert content to stream: ")), e2);
        }
    }

    private InputStream getContentStream(ASN1Encodable aSN1Encodable) {
        int i2;
        byte[] encoded = aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER);
        int i3 = 1;
        if ((encoded[0] & 31) == 31) {
            do {
                i2 = encoded[i3] & DerValue.TAG_CONTEXT;
                i3++;
            } while (i2 != 0);
        }
        int i4 = i3 + 1;
        byte b = encoded[i3];
        if ((b & DerValue.TAG_CONTEXT) != 0) {
            i4 += b & Byte.MAX_VALUE;
        }
        return new ByteArrayInputStream(encoded, i4, encoded.length - i4);
    }
}
