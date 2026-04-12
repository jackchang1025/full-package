package p000;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.asn1.ASN1Exception;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: e0 */
/* loaded from: classes2.dex */
public abstract class AbstractC0439e0 extends AbstractC0164c9 implements InterfaceC0440e1 {
    private static final int DECLARED_EXPLICIT = 1;
    private static final int DECLARED_IMPLICIT = 2;
    private static final int PARSED_EXPLICIT = 3;
    private static final int PARSED_IMPLICIT = 4;
    final int explicitness;
    final InterfaceC0117b0 obj;
    final int tagClass;
    final int tagNo;

    public AbstractC0439e0(int i, int i2, int i3, InterfaceC0117b0 interfaceC0117b0) {
        if (interfaceC0117b0 == null) {
            throw new NullPointerException("'obj' cannot be null");
        }
        if (i2 == 0 || (i2 & 192) != i2) {
            throw new IllegalArgumentException(tz0.m214802a2(i2, "invalid tag class: "));
        }
        this.explicitness = interfaceC0117b0 instanceof InterfaceC0010a9 ? 1 : i;
        this.tagClass = i2;
        this.tagNo = i3;
        this.obj = interfaceC0117b0;
    }

    private static AbstractC0439e0 checkedCast(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof AbstractC0439e0) {
            return (AbstractC0439e0) abstractC0164c9;
        }
        throw new IllegalStateException("unexpected object: ".concat(abstractC0164c9.getClass().getName()));
    }

    public static AbstractC0164c9 createConstructedDL(int i, int i2, C0118b1 c0118b1) {
        C1089py c1089py = c0118b1.size() == 1 ? new C1089py(3, i, i2, c0118b1.get(0)) : new C1089py(4, i, i2, C1080ps.createSequence(c0118b1));
        return i != 64 ? c1089py : new C1076po(c1089py);
    }

    public static AbstractC0164c9 createConstructedIL(int i, int i2, C0118b1 c0118b1) {
        C0386cp c0386cp = c0118b1.size() == 1 ? new C0386cp(3, i, i2, c0118b1.get(0)) : new C0386cp(4, i, i2, C0173ci.createSequence(c0118b1));
        return i != 64 ? c0386cp : new C0169ce(c0386cp);
    }

    public static AbstractC0164c9 createPrimitive(int i, int i2, byte[] bArr) {
        C1089py c1089py = new C1089py(4, i, i2, new C1048oy(bArr));
        return i != 64 ? c1089py : new C1076po(c1089py);
    }

    public static AbstractC0439e0 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        if (128 != abstractC0439e0.getTagClass()) {
            throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
        }
        if (z) {
            return abstractC0439e0.getExplicitBaseTagged();
        }
        throw new IllegalArgumentException("this method not valid for implicitly tagged tagged objects");
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof AbstractC0004a3) {
            return abstractC0164c9.equals((AbstractC0164c9) this);
        }
        if (!(abstractC0164c9 instanceof AbstractC0439e0)) {
            return false;
        }
        AbstractC0439e0 abstractC0439e0 = (AbstractC0439e0) abstractC0164c9;
        if (this.tagNo != abstractC0439e0.tagNo || this.tagClass != abstractC0439e0.tagClass) {
            return false;
        }
        if (this.explicitness != abstractC0439e0.explicitness && isExplicit() != abstractC0439e0.isExplicit()) {
            return false;
        }
        AbstractC0164c9 aSN1Primitive = this.obj.toASN1Primitive();
        AbstractC0164c9 aSN1Primitive2 = abstractC0439e0.obj.toASN1Primitive();
        if (aSN1Primitive == aSN1Primitive2) {
            return true;
        }
        if (isExplicit()) {
            return aSN1Primitive.asn1Equals(aSN1Primitive2);
        }
        try {
            return C0133bg.areEqual(getEncoded(), abstractC0439e0.getEncoded());
        } catch (IOException unused) {
            return false;
        }
    }

    public abstract String getASN1Encoding();

    public AbstractC0158c3 getBaseObject() {
        InterfaceC0117b0 interfaceC0117b0 = this.obj;
        return interfaceC0117b0 instanceof AbstractC0158c3 ? (AbstractC0158c3) interfaceC0117b0 : interfaceC0117b0.toASN1Primitive();
    }

    public AbstractC0164c9 getBaseUniversal(boolean z, int i) {
        AbstractC0445e6 abstractC0445e6 = C0446e7.get(i);
        if (abstractC0445e6 != null) {
            return getBaseUniversal(z, abstractC0445e6);
        }
        throw new IllegalArgumentException(tz0.m214802a2(i, "unsupported UNIVERSAL tag number: "));
    }

    public byte[] getContents() {
        try {
            byte[] encoded = this.obj.toASN1Primitive().getEncoded(getASN1Encoding());
            if (isExplicit()) {
                return encoded;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encoded);
            C0126b9.readTagNumber(byteArrayInputStream, byteArrayInputStream.read());
            int length = C0126b9.readLength(byteArrayInputStream, byteArrayInputStream.available(), false);
            int iAvailable = byteArrayInputStream.available();
            int i = length < 0 ? iAvailable - 2 : iAvailable;
            if (i < 0) {
                throw new ASN1ParsingException("failed to get contents");
            }
            byte[] bArr = new byte[i];
            System.arraycopy(encoded, encoded.length - iAvailable, bArr, 0, i);
            return bArr;
        } catch (IOException e) {
            throw new ASN1ParsingException("failed to get contents", e);
        }
    }

    public AbstractC0158c3 getExplicitBaseObject() {
        if (!isExplicit()) {
            throw new IllegalStateException("object implicit - explicit expected.");
        }
        InterfaceC0117b0 interfaceC0117b0 = this.obj;
        return interfaceC0117b0 instanceof AbstractC0158c3 ? (AbstractC0158c3) interfaceC0117b0 : interfaceC0117b0.toASN1Primitive();
    }

    public AbstractC0439e0 getExplicitBaseTagged() {
        if (isExplicit()) {
            return checkedCast(this.obj.toASN1Primitive());
        }
        throw new IllegalStateException("object implicit - explicit expected.");
    }

    public AbstractC0439e0 getImplicitBaseTagged(int i, int i2) {
        if (i == 0 || (i & 192) != i) {
            throw new IllegalArgumentException(tz0.m214802a2(i, "invalid base tag class: "));
        }
        int i3 = this.explicitness;
        if (i3 != 1) {
            return i3 != 2 ? replaceTag(i, i2) : AbstractC0447e8.checkTag(checkedCast(this.obj.toASN1Primitive()), i, i2);
        }
        throw new IllegalStateException("object explicit - implicit expected.");
    }

    public AbstractC0164c9 getObject() {
        if (128 == getTagClass()) {
            return this.obj.toASN1Primitive();
        }
        throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0117b0 getObjectParser(int i, boolean z) throws IOException {
        if (128 == getTagClass()) {
            return parseBaseUniversal(z, i);
        }
        throw new ASN1Exception("this method only valid for CONTEXT_SPECIFIC tags");
    }

    @Override // p000.InterfaceC0440e1
    public int getTagClass() {
        return this.tagClass;
    }

    @Override // p000.InterfaceC0440e1
    public int getTagNo() {
        return this.tagNo;
    }

    @Override // p000.InterfaceC0440e1
    public boolean hasContextTag(int i) {
        return this.tagClass == 128 && this.tagNo == i;
    }

    @Override // p000.InterfaceC0440e1
    public boolean hasTag(int i, int i2) {
        return this.tagClass == i && this.tagNo == i2;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return (((this.tagClass * 7919) ^ this.tagNo) ^ (isExplicit() ? 15 : 240)) ^ this.obj.toASN1Primitive().hashCode();
    }

    public boolean isConstructed() {
        return encodeConstructed();
    }

    public boolean isExplicit() {
        int i = this.explicitness;
        return i == 1 || i == 3;
    }

    public boolean isParsed() {
        int i = this.explicitness;
        return i == 3 || i == 4;
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0117b0 parseBaseUniversal(boolean z, int i) throws IOException {
        AbstractC0164c9 baseUniversal = getBaseUniversal(z, i);
        return i != 3 ? i != 4 ? i != 16 ? i != 17 ? baseUniversal : ((AbstractC0402d4) baseUniversal).parser() : ((AbstractC0400d2) baseUniversal).parser() : ((AbstractC0161c6) baseUniversal).parser() : ((AbstractC0007a6) baseUniversal).parser();
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0117b0 parseExplicitBaseObject() throws IOException {
        return getExplicitBaseObject();
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0440e1 parseExplicitBaseTagged() throws IOException {
        return getExplicitBaseTagged();
    }

    @Override // p000.InterfaceC0440e1
    public InterfaceC0440e1 parseImplicitBaseTagged(int i, int i2) throws IOException {
        return getImplicitBaseTagged(i, i2);
    }

    public abstract AbstractC0400d2 rebuildConstructed(AbstractC0164c9 abstractC0164c9);

    public abstract AbstractC0439e0 replaceTag(int i, int i2);

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return new C1067pf(this.explicitness, this.tagClass, this.tagNo, this.obj);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return new C1089py(this.explicitness, this.tagClass, this.tagNo, this.obj);
    }

    public String toString() {
        return AbstractC0447e8.getTagText(this.tagClass, this.tagNo) + this.obj;
    }

    public static AbstractC0439e0 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0439e0)) {
            return (AbstractC0439e0) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0439e0) {
                return (AbstractC0439e0) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return checkedCast(AbstractC0164c9.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct tagged object from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in getInstance: "));
    }

    public AbstractC0439e0(boolean z, int i, int i2, InterfaceC0117b0 interfaceC0117b0) {
        this(z ? 1 : 2, i, i2, interfaceC0117b0);
    }

    public AbstractC0164c9 getBaseUniversal(boolean z, AbstractC0445e6 abstractC0445e6) {
        if (z) {
            if (isExplicit()) {
                return abstractC0445e6.checkedCast(this.obj.toASN1Primitive());
            }
            throw new IllegalStateException("object explicit - implicit expected.");
        }
        if (1 == this.explicitness) {
            throw new IllegalStateException("object explicit - implicit expected.");
        }
        AbstractC0164c9 aSN1Primitive = this.obj.toASN1Primitive();
        int i = this.explicitness;
        return i != 3 ? i != 4 ? abstractC0445e6.checkedCast(aSN1Primitive) : aSN1Primitive instanceof AbstractC0400d2 ? abstractC0445e6.fromImplicitConstructed((AbstractC0400d2) aSN1Primitive) : abstractC0445e6.fromImplicitPrimitive((C1048oy) aSN1Primitive) : abstractC0445e6.fromImplicitConstructed(rebuildConstructed(aSN1Primitive));
    }

    public AbstractC0439e0(boolean z, int i, InterfaceC0117b0 interfaceC0117b0) {
        this(z, 128, i, interfaceC0117b0);
    }

    @Override // p000.InterfaceC0440e1, p000.i50
    public final AbstractC0164c9 getLoadedObject() {
        return this;
    }
}
