package p000;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import org.bouncycastle.asn1.ASN1ParsingException;

/* loaded from: classes2.dex */
public class aa0 extends AbstractC0400d2 {
    private byte[] encoded;

    public aa0(byte[] bArr) throws IOException {
        if (bArr == null) {
            throw new NullPointerException("'encoded' cannot be null");
        }
        this.encoded = bArr;
    }

    private synchronized void force() {
        if (this.encoded != null) {
            C0126b9 c0126b9 = new C0126b9(this.encoded, true);
            try {
                C0118b1 vector = c0126b9.readVector();
                c0126b9.close();
                this.elements = vector.takeElements();
                this.encoded = null;
            } catch (IOException e) {
                throw new ASN1ParsingException("malformed ASN.1: " + e, e);
            }
        }
    }

    private synchronized byte[] getContents() {
        return this.encoded;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        byte[] contents = getContents();
        if (contents != null) {
            c0163c8.writeEncodingDL(z, 48, contents);
        } else {
            super.toDLObject().encode(c0163c8, z);
        }
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        byte[] contents = getContents();
        return contents != null ? C0163c8.getLengthOfEncodingDL(z, contents.length) : super.toDLObject().encodedLength(z);
    }

    @Override // p000.AbstractC0400d2
    public InterfaceC0117b0 getObjectAt(int i) {
        force();
        return super.getObjectAt(i);
    }

    @Override // p000.AbstractC0400d2
    public Enumeration getObjects() {
        byte[] contents = getContents();
        return contents != null ? new z90(contents) : super.getObjects();
    }

    @Override // p000.AbstractC0400d2, p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        force();
        return super.hashCode();
    }

    @Override // p000.AbstractC0400d2, p000.d70, java.lang.Iterable
    public Iterator<InterfaceC0117b0> iterator() {
        force();
        return super.iterator();
    }

    @Override // p000.AbstractC0400d2
    public int size() {
        force();
        return super.size();
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0007a6 toASN1BitString() {
        return ((AbstractC0400d2) toDLObject()).toASN1BitString();
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0120b3 toASN1External() {
        return ((AbstractC0400d2) toDLObject()).toASN1External();
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0161c6 toASN1OctetString() {
        return ((AbstractC0400d2) toDLObject()).toASN1OctetString();
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0402d4 toASN1Set() {
        return ((AbstractC0400d2) toDLObject()).toASN1Set();
    }

    @Override // p000.AbstractC0400d2
    public InterfaceC0117b0[] toArray() {
        force();
        return super.toArray();
    }

    @Override // p000.AbstractC0400d2
    public InterfaceC0117b0[] toArrayInternal() {
        force();
        return super.toArrayInternal();
    }

    @Override // p000.AbstractC0400d2, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        force();
        return super.toDERObject();
    }

    @Override // p000.AbstractC0400d2, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        force();
        return super.toDLObject();
    }
}
