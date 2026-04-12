package p000;

import java.io.IOException;

/* renamed from: oo */
/* loaded from: classes2.dex */
public class C0991oo extends AbstractC0007a6 {
    public C0991oo(byte b, int i) {
        super(b, i);
    }

    public static C0991oo convert(AbstractC0007a6 abstractC0007a6) {
        return (C0991oo) abstractC0007a6.toDERObject();
    }

    public static C0991oo fromOctetString(AbstractC0161c6 abstractC0161c6) {
        return new C0991oo(abstractC0161c6.getOctets(), true);
    }

    public static C0991oo getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        AbstractC0164c9 object = abstractC0439e0.getObject();
        return (z || (object instanceof C0991oo)) ? getInstance((Object) object) : fromOctetString(AbstractC0161c6.getInstance(object));
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        byte[] bArr = this.contents;
        int i = bArr[0] & 255;
        int length = bArr.length - 1;
        byte b = bArr[length];
        byte b2 = (byte) ((v10.MASK << i) & b);
        if (b == b2) {
            c0163c8.writeEncodingDL(z, 3, bArr);
        } else {
            c0163c8.writeEncodingDL(z, 3, bArr, 0, length, b2);
        }
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.contents.length);
    }

    public C0991oo(int i) {
        super(AbstractC0007a6.getBytes(i), AbstractC0007a6.getPadBits(i));
    }

    public static C0991oo getInstance(Object obj) {
        if (obj == null || (obj instanceof C0991oo)) {
            return (C0991oo) obj;
        }
        if (obj instanceof AbstractC0007a6) {
            return convert((AbstractC0007a6) obj);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return convert((AbstractC0007a6) AbstractC0164c9.fromByteArray((byte[]) obj));
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public C0991oo(InterfaceC0117b0 interfaceC0117b0) throws IOException {
        super(interfaceC0117b0.toASN1Primitive().getEncoded("DER"), 0);
    }

    public C0991oo(byte[] bArr) {
        this(bArr, 0);
    }

    public C0991oo(byte[] bArr, int i) {
        super(bArr, i);
    }

    public C0991oo(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    @Override // p000.AbstractC0007a6, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this;
    }

    @Override // p000.AbstractC0007a6, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
