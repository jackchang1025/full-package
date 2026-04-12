package p000;

import java.io.IOException;

/* renamed from: pp */
/* loaded from: classes2.dex */
public class C1077pp extends AbstractC0007a6 {
    public C1077pp(byte b, int i) {
        super(b, i);
    }

    public static C1077pp fromOctetString(AbstractC0161c6 abstractC0161c6) {
        return new C1077pp(abstractC0161c6.getOctets(), true);
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 3, this.contents);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.contents.length);
    }

    public C1077pp(int i) {
        super(AbstractC0007a6.getBytes(i), AbstractC0007a6.getPadBits(i));
    }

    public static void encode(C0163c8 c0163c8, boolean z, byte b, byte[] bArr, int i, int i2) throws IOException {
        c0163c8.writeEncodingDL(z, 3, b, bArr, i, i2);
    }

    public static int encodedLength(boolean z, int i) {
        return C0163c8.getLengthOfEncodingDL(z, i);
    }

    public C1077pp(InterfaceC0117b0 interfaceC0117b0) throws IOException {
        super(interfaceC0117b0.toASN1Primitive().getEncoded("DER"), 0);
    }

    public static void encode(C0163c8 c0163c8, boolean z, byte[] bArr, int i, int i2) throws IOException {
        c0163c8.writeEncodingDL(z, 3, bArr, i, i2);
    }

    public C1077pp(byte[] bArr) {
        this(bArr, 0);
    }

    public C1077pp(byte[] bArr, int i) {
        super(bArr, i);
    }

    public C1077pp(byte[] bArr, boolean z) {
        super(bArr, z);
    }

    @Override // p000.AbstractC0007a6, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
