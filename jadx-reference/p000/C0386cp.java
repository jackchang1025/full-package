package p000;

import java.io.IOException;

/* renamed from: cp */
/* loaded from: classes2.dex */
public class C0386cp extends AbstractC0439e0 {
    public C0386cp(int i) {
        super(false, i, new C0176cl());
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        AbstractC0164c9 aSN1Primitive = this.obj.toASN1Primitive();
        boolean zIsExplicit = isExplicit();
        if (z) {
            int i = this.tagClass;
            if (zIsExplicit || aSN1Primitive.encodeConstructed()) {
                i |= 32;
            }
            c0163c8.writeIdentifier(true, i, this.tagNo);
        }
        if (!zIsExplicit) {
            aSN1Primitive.encode(c0163c8, false);
            return;
        }
        c0163c8.write(128);
        aSN1Primitive.encode(c0163c8, true);
        c0163c8.write(0);
        c0163c8.write(0);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return isExplicit() || this.obj.toASN1Primitive().encodeConstructed();
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        AbstractC0164c9 aSN1Primitive = this.obj.toASN1Primitive();
        boolean zIsExplicit = isExplicit();
        int iEncodedLength = aSN1Primitive.encodedLength(zIsExplicit);
        if (zIsExplicit) {
            iEncodedLength += 3;
        }
        return iEncodedLength + (z ? C0163c8.getLengthOfIdentifier(this.tagNo) : 0);
    }

    @Override // p000.AbstractC0439e0
    public String getASN1Encoding() {
        return "BER";
    }

    @Override // p000.AbstractC0439e0
    public AbstractC0400d2 rebuildConstructed(AbstractC0164c9 abstractC0164c9) {
        return new C0176cl(abstractC0164c9);
    }

    @Override // p000.AbstractC0439e0
    public AbstractC0439e0 replaceTag(int i, int i2) {
        return new C0386cp(this.explicitness, i, i2, this.obj);
    }

    public C0386cp(int i, int i2, int i3, InterfaceC0117b0 interfaceC0117b0) {
        super(i, i2, i3, interfaceC0117b0);
    }

    public C0386cp(int i, int i2, InterfaceC0117b0 interfaceC0117b0) {
        super(true, i, i2, interfaceC0117b0);
    }

    public C0386cp(int i, InterfaceC0117b0 interfaceC0117b0) {
        super(true, i, interfaceC0117b0);
    }

    public C0386cp(boolean z, int i, int i2, InterfaceC0117b0 interfaceC0117b0) {
        super(z, i, i2, interfaceC0117b0);
    }

    public C0386cp(boolean z, int i, InterfaceC0117b0 interfaceC0117b0) {
        super(z, i, interfaceC0117b0);
    }
}
