package p000;

import java.io.IOException;

/* renamed from: oy */
/* loaded from: classes2.dex */
public class C1048oy extends AbstractC0161c6 {
    public C1048oy(InterfaceC0117b0 interfaceC0117b0) throws IOException {
        super(interfaceC0117b0.toASN1Primitive().getEncoded("DER"));
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 4, this.string);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.string.length);
    }

    public C1048oy(byte[] bArr) {
        super(bArr);
    }

    public static void encode(C0163c8 c0163c8, boolean z, byte[] bArr, int i, int i2) throws IOException {
        c0163c8.writeEncodingDL(z, 4, bArr, i, i2);
    }

    public static int encodedLength(boolean z, int i) {
        return C0163c8.getLengthOfEncodingDL(z, i);
    }

    @Override // p000.AbstractC0161c6, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this;
    }

    @Override // p000.AbstractC0161c6, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
