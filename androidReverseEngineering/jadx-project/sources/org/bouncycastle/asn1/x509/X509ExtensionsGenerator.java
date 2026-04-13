package org.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class X509ExtensionsGenerator {
    private Hashtable extensions = new Hashtable();
    private Vector extOrdering = new Vector();

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z2, ASN1Encodable aSN1Encodable) {
        try {
            addExtension(aSN1ObjectIdentifier, z2, aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
        } catch (IOException e2) {
            throw new IllegalArgumentException(AbstractC0000a.m13i("error encoding value: ", e2));
        }
    }

    public X509Extensions generate() {
        return new X509Extensions(this.extOrdering, this.extensions);
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public void reset() {
        this.extensions = new Hashtable();
        this.extOrdering = new Vector();
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z2, byte[] bArr) {
        if (this.extensions.containsKey(aSN1ObjectIdentifier)) {
            throw new IllegalArgumentException(AbstractC0413b.m1010d("extension ", aSN1ObjectIdentifier, " already added"));
        }
        this.extOrdering.addElement(aSN1ObjectIdentifier);
        this.extensions.put(aSN1ObjectIdentifier, new X509Extension(z2, new DEROctetString(bArr)));
    }
}
