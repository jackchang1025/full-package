package p000;

import java.io.IOException;

/* renamed from: pf */
/* loaded from: classes2.dex */
public class C1067pf extends AbstractC0439e0 {
    public C1067pf(int i, int i2, int i3, InterfaceC0117b0 interfaceC0117b0) {
        super(i, i2, i3, interfaceC0117b0);
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        AbstractC0164c9 dERObject = this.obj.toASN1Primitive().toDERObject();
        boolean zIsExplicit = isExplicit();
        if (z) {
            int i = this.tagClass;
            if (zIsExplicit || dERObject.encodeConstructed()) {
                i |= 32;
            }
            c0163c8.writeIdentifier(true, i, this.tagNo);
        }
        if (zIsExplicit) {
            c0163c8.writeDL(dERObject.encodedLength(true));
        }
        dERObject.encode(c0163c8.getDERSubStream(), zIsExplicit);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return isExplicit() || this.obj.toASN1Primitive().toDERObject().encodeConstructed();
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        AbstractC0164c9 dERObject = this.obj.toASN1Primitive().toDERObject();
        boolean zIsExplicit = isExplicit();
        int iEncodedLength = dERObject.encodedLength(zIsExplicit);
        if (zIsExplicit) {
            iEncodedLength += C0163c8.getLengthOfDL(iEncodedLength);
        }
        return iEncodedLength + (z ? C0163c8.getLengthOfIdentifier(this.tagNo) : 0);
    }

    @Override // p000.AbstractC0439e0
    public String getASN1Encoding() {
        return "DER";
    }

    @Override // p000.AbstractC0439e0
    public AbstractC0400d2 rebuildConstructed(AbstractC0164c9 abstractC0164c9) {
        return new C1064pc(abstractC0164c9);
    }

    @Override // p000.AbstractC0439e0
    public AbstractC0439e0 replaceTag(int i, int i2) {
        return new C1067pf(this.explicitness, i, i2, this.obj);
    }

    public C1067pf(int i, int i2, InterfaceC0117b0 interfaceC0117b0) {
        super(true, i, i2, interfaceC0117b0);
    }

    public C1067pf(int i, InterfaceC0117b0 interfaceC0117b0) {
        super(true, i, interfaceC0117b0);
    }

    public C1067pf(boolean z, int i, int i2, InterfaceC0117b0 interfaceC0117b0) {
        super(z, i, i2, interfaceC0117b0);
    }

    public C1067pf(boolean z, int i, InterfaceC0117b0 interfaceC0117b0) {
        super(z, i, interfaceC0117b0);
    }

    @Override // p000.AbstractC0439e0, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this;
    }

    @Override // p000.AbstractC0439e0, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
