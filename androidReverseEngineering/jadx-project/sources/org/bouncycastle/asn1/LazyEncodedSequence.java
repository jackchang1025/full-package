package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/* loaded from: classes.dex */
class LazyEncodedSequence extends ASN1Sequence {
    private byte[] encoded;

    public LazyEncodedSequence(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'encoded' cannot be null");
        }
        this.encoded = bArr;
    }

    private synchronized void force() {
        if (this.encoded != null) {
            ASN1InputStream aSN1InputStream = new ASN1InputStream(this.encoded, true);
            try {
                ASN1EncodableVector readVector = aSN1InputStream.readVector();
                aSN1InputStream.close();
                this.elements = readVector.takeElements();
                this.encoded = null;
            } catch (IOException e2) {
                throw new ASN1ParsingException("malformed ASN.1: " + e2, e2);
            }
        }
    }

    private synchronized byte[] getContents() {
        return this.encoded;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        byte[] contents = getContents();
        if (contents != null) {
            aSN1OutputStream.writeEncodingDL(z2, 48, contents);
        } else {
            super.toDLObject().encode(aSN1OutputStream, z2);
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        byte[] contents = getContents();
        return contents != null ? ASN1OutputStream.getLengthOfEncodingDL(z2, contents.length) : super.toDLObject().encodedLength(z2);
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1Encodable getObjectAt(int i2) {
        force();
        return super.getObjectAt(i2);
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public Enumeration getObjects() {
        byte[] contents = getContents();
        return contents != null ? new LazyConstructionEnumeration(contents) : super.getObjects();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        force();
        return super.hashCode();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.util.Iterable, java.lang.Iterable
    public Iterator<ASN1Encodable> iterator() {
        force();
        return super.iterator();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public int size() {
        force();
        return super.size();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1BitString toASN1BitString() {
        return ((ASN1Sequence) toDLObject()).toASN1BitString();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1External toASN1External() {
        return ((ASN1Sequence) toDLObject()).toASN1External();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1OctetString toASN1OctetString() {
        return ((ASN1Sequence) toDLObject()).toASN1OctetString();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1Set toASN1Set() {
        return ((ASN1Sequence) toDLObject()).toASN1Set();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1Encodable[] toArray() {
        force();
        return super.toArray();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public ASN1Encodable[] toArrayInternal() {
        force();
        return super.toArrayInternal();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        force();
        return super.toDERObject();
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        force();
        return super.toDLObject();
    }
}
