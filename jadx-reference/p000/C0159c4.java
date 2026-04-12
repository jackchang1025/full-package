package p000;

import java.io.IOException;

/* renamed from: c4 */
/* loaded from: classes2.dex */
public final class C0159c4 extends AbstractC0164c9 {
    static final AbstractC0445e6 TYPE = new a0(C0159c4.class, 7);
    private final AbstractC0124b7 baseGraphicString;

    /* renamed from: c4$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitConstructed(AbstractC0400d2 abstractC0400d2) {
            return new C0159c4((AbstractC0124b7) AbstractC0124b7.TYPE.fromImplicitConstructed(abstractC0400d2));
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return new C0159c4((AbstractC0124b7) AbstractC0124b7.TYPE.fromImplicitPrimitive(c1048oy));
        }
    }

    public C0159c4(AbstractC0124b7 abstractC0124b7) {
        if (abstractC0124b7 == null) {
            throw new NullPointerException("'baseGraphicString' cannot be null");
        }
        this.baseGraphicString = abstractC0124b7;
    }

    public static C0159c4 createPrimitive(byte[] bArr) {
        return new C0159c4(AbstractC0124b7.createPrimitive(bArr));
    }

    public static C0159c4 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (C0159c4) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof C0159c4) {
            return this.baseGraphicString.asn1Equals(((C0159c4) abstractC0164c9).baseGraphicString);
        }
        return false;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeIdentifier(z, 7);
        this.baseGraphicString.encode(c0163c8, false);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return this.baseGraphicString.encodedLength(z);
    }

    public AbstractC0124b7 getBaseGraphicString() {
        return this.baseGraphicString;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return ~this.baseGraphicString.hashCode();
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        AbstractC0124b7 abstractC0124b7 = (AbstractC0124b7) this.baseGraphicString.toDERObject();
        return abstractC0124b7 == this.baseGraphicString ? this : new C0159c4(abstractC0124b7);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        AbstractC0124b7 abstractC0124b7 = (AbstractC0124b7) this.baseGraphicString.toDLObject();
        return abstractC0124b7 == this.baseGraphicString ? this : new C0159c4(abstractC0124b7);
    }

    public static C0159c4 getInstance(Object obj) {
        if (obj == null || (obj instanceof C0159c4)) {
            return (C0159c4) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof C0159c4) {
                return (C0159c4) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (C0159c4) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct object descriptor from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }
}
