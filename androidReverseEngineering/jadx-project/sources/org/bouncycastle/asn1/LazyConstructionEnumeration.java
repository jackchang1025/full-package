package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class LazyConstructionEnumeration implements Enumeration {
    private ASN1InputStream aIn;
    private Object nextObj = readObject();

    public LazyConstructionEnumeration(byte[] bArr) {
        this.aIn = new ASN1InputStream(bArr, true);
    }

    private Object readObject() {
        try {
            return this.aIn.readObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException(AbstractC0000a.m13i("malformed ASN.1: ", e2), e2);
        }
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.nextObj != null;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        Object obj = this.nextObj;
        if (obj == null) {
            throw new NoSuchElementException();
        }
        this.nextObj = readObject();
        return obj;
    }
}
