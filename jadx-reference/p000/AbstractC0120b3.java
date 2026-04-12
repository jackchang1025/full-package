package p000;

import java.io.IOException;

/* renamed from: b3 */
/* loaded from: classes2.dex */
public abstract class AbstractC0120b3 extends AbstractC0164c9 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0120b3.class, 8);
    AbstractC0164c9 dataValueDescriptor;
    C0160c5 directReference;
    int encoding;
    AbstractC0164c9 externalContent;
    C0155c0 indirectReference;

    /* renamed from: b3$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitConstructed(AbstractC0400d2 abstractC0400d2) {
            return abstractC0400d2.toASN1External();
        }
    }

    public AbstractC0120b3(C0160c5 c0160c5, C0155c0 c0155c0, AbstractC0164c9 abstractC0164c9, int i, AbstractC0164c9 abstractC0164c92) {
        this.directReference = c0160c5;
        this.indirectReference = c0155c0;
        this.dataValueDescriptor = abstractC0164c9;
        this.encoding = checkEncoding(i);
        this.externalContent = checkExternalContent(i, abstractC0164c92);
    }

    private static int checkEncoding(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException(tz0.m214802a2(i, "invalid encoding value: "));
        }
        return i;
    }

    private static AbstractC0164c9 checkExternalContent(int i, AbstractC0164c9 abstractC0164c9) {
        AbstractC0445e6 abstractC0445e6;
        if (i == 1) {
            abstractC0445e6 = AbstractC0161c6.TYPE;
        } else {
            if (i != 2) {
                return abstractC0164c9;
            }
            abstractC0445e6 = AbstractC0007a6.TYPE;
        }
        return abstractC0445e6.checkedCast(abstractC0164c9);
    }

    public static AbstractC0120b3 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0120b3) TYPE.getContextInstance(abstractC0439e0, z);
    }

    private static AbstractC0164c9 getObjFromSequence(AbstractC0400d2 abstractC0400d2, int i) {
        if (abstractC0400d2.size() > i) {
            return abstractC0400d2.getObjectAt(i).toASN1Primitive();
        }
        throw new IllegalArgumentException("too few objects in input sequence");
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (this == abstractC0164c9) {
            return true;
        }
        if (!(abstractC0164c9 instanceof AbstractC0120b3)) {
            return false;
        }
        AbstractC0120b3 abstractC0120b3 = (AbstractC0120b3) abstractC0164c9;
        return sk0.areEqual(this.directReference, abstractC0120b3.directReference) && sk0.areEqual(this.indirectReference, abstractC0120b3.indirectReference) && sk0.areEqual(this.dataValueDescriptor, abstractC0120b3.dataValueDescriptor) && this.encoding == abstractC0120b3.encoding && this.externalContent.equals(abstractC0120b3.externalContent);
    }

    public abstract AbstractC0400d2 buildSequence();

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeIdentifier(z, 40);
        buildSequence().encode(c0163c8, false);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return true;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        return buildSequence().encodedLength(z);
    }

    public AbstractC0164c9 getDataValueDescriptor() {
        return this.dataValueDescriptor;
    }

    public C0160c5 getDirectReference() {
        return this.directReference;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public AbstractC0164c9 getExternalContent() {
        return this.externalContent;
    }

    public C0155c0 getIndirectReference() {
        return this.indirectReference;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return (((sk0.hashCode(this.directReference) ^ sk0.hashCode(this.indirectReference)) ^ sk0.hashCode(this.dataValueDescriptor)) ^ this.encoding) ^ this.externalContent.hashCode();
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return new C0992op(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return new C1079pr(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    public AbstractC0120b3(C0160c5 c0160c5, C0155c0 c0155c0, AbstractC0164c9 abstractC0164c9, C1067pf c1067pf) {
        this.directReference = c0160c5;
        this.indirectReference = c0155c0;
        this.dataValueDescriptor = abstractC0164c9;
        this.encoding = checkEncoding(c1067pf.getTagNo());
        this.externalContent = getExternalContent(c1067pf);
    }

    private static AbstractC0164c9 getExternalContent(AbstractC0439e0 abstractC0439e0) {
        int tagClass = abstractC0439e0.getTagClass();
        int tagNo = abstractC0439e0.getTagNo();
        if (128 != tagClass) {
            throw new IllegalArgumentException("invalid tag: " + AbstractC0447e8.getTagText(tagClass, tagNo));
        }
        if (tagNo == 0) {
            return abstractC0439e0.getExplicitBaseObject().toASN1Primitive();
        }
        if (tagNo == 1) {
            return AbstractC0161c6.getInstance(abstractC0439e0, false);
        }
        if (tagNo == 2) {
            return AbstractC0007a6.getInstance(abstractC0439e0, false);
        }
        throw new IllegalArgumentException("invalid tag: " + AbstractC0447e8.getTagText(tagClass, tagNo));
    }

    public static AbstractC0120b3 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0120b3)) {
            return (AbstractC0120b3) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0120b3) {
                return (AbstractC0120b3) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (AbstractC0120b3) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct external from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }

    public AbstractC0120b3(AbstractC0400d2 abstractC0400d2) {
        int i = 0;
        AbstractC0164c9 objFromSequence = getObjFromSequence(abstractC0400d2, 0);
        if (objFromSequence instanceof C0160c5) {
            this.directReference = (C0160c5) objFromSequence;
            objFromSequence = getObjFromSequence(abstractC0400d2, 1);
            i = 1;
        }
        if (objFromSequence instanceof C0155c0) {
            this.indirectReference = (C0155c0) objFromSequence;
            i++;
            objFromSequence = getObjFromSequence(abstractC0400d2, i);
        }
        if (!(objFromSequence instanceof AbstractC0439e0)) {
            this.dataValueDescriptor = objFromSequence;
            i++;
            objFromSequence = getObjFromSequence(abstractC0400d2, i);
        }
        if (abstractC0400d2.size() != i + 1) {
            throw new IllegalArgumentException("input sequence too large");
        }
        if (!(objFromSequence instanceof AbstractC0439e0)) {
            throw new IllegalArgumentException("No tagged object found in sequence. Structure doesn't seem to be of type External");
        }
        AbstractC0439e0 abstractC0439e0 = (AbstractC0439e0) objFromSequence;
        this.encoding = checkEncoding(abstractC0439e0.getTagNo());
        this.externalContent = getExternalContent(abstractC0439e0);
    }
}
