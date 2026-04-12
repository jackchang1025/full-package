package p000;

/* renamed from: bn */
/* loaded from: classes2.dex */
public class C0140bn extends AbstractC0158c3 {
    private C0160c5 attrType;
    private AbstractC0402d4 attrValues;

    public C0140bn(C0160c5 c0160c5, AbstractC0402d4 abstractC0402d4) {
        this.attrType = c0160c5;
        this.attrValues = abstractC0402d4;
    }

    public static C0140bn getInstance(Object obj) {
        if (obj instanceof C0140bn) {
            return (C0140bn) obj;
        }
        if (obj != null) {
            return new C0140bn(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C0160c5 getAttrType() {
        return new C0160c5(this.attrType.getId());
    }

    public AbstractC0402d4 getAttrValues() {
        return this.attrValues;
    }

    public InterfaceC0117b0[] getAttributeValues() {
        return this.attrValues.toArray();
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(2);
        c0118b1.add(this.attrType);
        c0118b1.add(this.attrValues);
        return new C1064pc(c0118b1);
    }

    private C0140bn(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() == 2) {
            this.attrType = C0160c5.getInstance(abstractC0400d2.getObjectAt(0));
            this.attrValues = AbstractC0402d4.getInstance(abstractC0400d2.getObjectAt(1));
        } else {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
    }
}
