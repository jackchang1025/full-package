package p000;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Exception;

/* renamed from: a3 */
/* loaded from: classes2.dex */
public abstract class AbstractC0004a3 extends AbstractC0164c9 implements InterfaceC0005a4 {
    final AbstractC0439e0 taggedObject;

    public AbstractC0004a3(AbstractC0439e0 abstractC0439e0) {
        checkTagClass(abstractC0439e0.getTagClass());
        this.taggedObject = abstractC0439e0;
    }

    private static int checkTagClass(int i) {
        if (64 == i) {
            return i;
        }
        throw new IllegalArgumentException();
    }

    public static AbstractC0004a3 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0004a3)) {
            return (AbstractC0004a3) obj;
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in getInstance: "));
        }
        try {
            return getInstance(AbstractC0164c9.fromByteArray((byte[]) obj));
        } catch (IOException e) {
            throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("Failed to construct object from byte[]: ")));
        }
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        AbstractC0439e0 abstractC0439e0;
        if (abstractC0164c9 instanceof AbstractC0004a3) {
            abstractC0439e0 = ((AbstractC0004a3) abstractC0164c9).taggedObject;
        } else {
            if (!(abstractC0164c9 instanceof AbstractC0439e0)) {
                return false;
            }
            abstractC0439e0 = (AbstractC0439e0) abstractC0164c9;
        }
        return this.taggedObject.equals((AbstractC0164c9) abstractC0439e0);
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        this.taggedObject.encode(c0163c8, z);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return this.taggedObject.encodeConstructed();
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        return this.taggedObject.encodedLength(z);
    }

    public int getApplicationTag() {
        return this.taggedObject.getTagNo();
    }

    public byte[] getContents() {
        return this.taggedObject.getContents();
    }

    public AbstractC0164c9 getEnclosedObject() throws IOException {
        return this.taggedObject.getBaseObject().toASN1Primitive();
    }

    public AbstractC0164c9 getObject() throws IOException {
        return getEnclosedObject();
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public InterfaceC0117b0 getObjectParser(int i, boolean z) throws IOException {
        throw new ASN1Exception("this method only valid for CONTEXT_SPECIFIC tags");
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public int getTagClass() {
        return 64;
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public int getTagNo() {
        return this.taggedObject.getTagNo();
    }

    public AbstractC0439e0 getTaggedObject() {
        return this.taggedObject;
    }

    public boolean hasApplicationTag(int i) {
        return this.taggedObject.hasTag(64, i);
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public boolean hasContextTag(int i) {
        return false;
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public boolean hasTag(int i, int i2) {
        return this.taggedObject.hasTag(i, i2);
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return this.taggedObject.hashCode();
    }

    public boolean isConstructed() {
        return this.taggedObject.isConstructed();
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public InterfaceC0117b0 parseBaseUniversal(boolean z, int i) throws IOException {
        return this.taggedObject.parseBaseUniversal(z, i);
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public InterfaceC0117b0 parseExplicitBaseObject() throws IOException {
        return this.taggedObject.parseExplicitBaseObject();
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public InterfaceC0440e1 parseExplicitBaseTagged() throws IOException {
        return this.taggedObject.parseExplicitBaseTagged();
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1
    public InterfaceC0440e1 parseImplicitBaseTagged(int i, int i2) throws IOException {
        return this.taggedObject.parseImplicitBaseTagged(i, i2);
    }

    @Override // p000.InterfaceC0005a4
    public InterfaceC0117b0 readObject() throws IOException {
        return parseExplicitBaseObject();
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return new C0989om((AbstractC0439e0) this.taggedObject.toDERObject());
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return new C1076po((AbstractC0439e0) this.taggedObject.toDLObject());
    }

    public AbstractC0164c9 getObject(int i) throws IOException {
        return this.taggedObject.getBaseUniversal(false, i);
    }

    @Override // p000.InterfaceC0005a4, p000.InterfaceC0440e1, p000.i50
    public final AbstractC0164c9 getLoadedObject() {
        return this;
    }
}
