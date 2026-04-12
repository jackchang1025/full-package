package p000;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: c9 */
/* loaded from: classes2.dex */
public abstract class AbstractC0164c9 extends AbstractC0158c3 {
    public static AbstractC0164c9 fromByteArray(byte[] bArr) throws IOException {
        C0126b9 c0126b9 = new C0126b9(bArr);
        try {
            AbstractC0164c9 object = c0126b9.readObject();
            if (c0126b9.available() == 0) {
                return object;
            }
            throw new IOException("Extra data detected in stream");
        } catch (ClassCastException unused) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    public abstract boolean asn1Equals(AbstractC0164c9 abstractC0164c9);

    public abstract void encode(C0163c8 c0163c8, boolean z) throws IOException;

    public abstract boolean encodeConstructed();

    @Override // p000.AbstractC0158c3
    public void encodeTo(OutputStream outputStream) throws IOException {
        C0163c8 c0163c8Create = C0163c8.create(outputStream);
        c0163c8Create.writePrimitive(this, true);
        c0163c8Create.flushInternal();
    }

    public abstract int encodedLength(boolean z) throws IOException;

    public final boolean equals(InterfaceC0117b0 interfaceC0117b0) {
        if (this != interfaceC0117b0) {
            return interfaceC0117b0 != null && asn1Equals(interfaceC0117b0.toASN1Primitive());
        }
        return true;
    }

    @Override // p000.AbstractC0158c3
    public abstract int hashCode();

    @Override // p000.AbstractC0158c3
    public void encodeTo(OutputStream outputStream, String str) throws IOException {
        C0163c8 c0163c8Create = C0163c8.create(outputStream, str);
        c0163c8Create.writePrimitive(this, true);
        c0163c8Create.flushInternal();
    }

    public final boolean equals(AbstractC0164c9 abstractC0164c9) {
        return this == abstractC0164c9 || asn1Equals(abstractC0164c9);
    }

    @Override // p000.AbstractC0158c3
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InterfaceC0117b0) && asn1Equals(((InterfaceC0117b0) obj).toASN1Primitive());
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public final AbstractC0164c9 toASN1Primitive() {
        return this;
    }

    public AbstractC0164c9 toDERObject() {
        return this;
    }

    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
