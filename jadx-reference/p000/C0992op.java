package p000;

/* renamed from: op */
/* loaded from: classes2.dex */
public class C0992op extends AbstractC0120b3 {
    public C0992op(C0118b1 c0118b1) {
        this(C0994or.createSequence(c0118b1));
    }

    @Override // p000.AbstractC0120b3
    public AbstractC0400d2 buildSequence() {
        C0118b1 c0118b1 = new C0118b1(4);
        C0160c5 c0160c5 = this.directReference;
        if (c0160c5 != null) {
            c0118b1.add(c0160c5);
        }
        C0155c0 c0155c0 = this.indirectReference;
        if (c0155c0 != null) {
            c0118b1.add(c0155c0);
        }
        AbstractC0164c9 abstractC0164c9 = this.dataValueDescriptor;
        if (abstractC0164c9 != null) {
            c0118b1.add(abstractC0164c9.toDERObject());
        }
        int i = this.encoding;
        c0118b1.add(new C1067pf(i == 0, i, this.externalContent));
        return new C1064pc(c0118b1);
    }

    public C0992op(C0160c5 c0160c5, C0155c0 c0155c0, AbstractC0164c9 abstractC0164c9, int i, AbstractC0164c9 abstractC0164c92) {
        super(c0160c5, c0155c0, abstractC0164c9, i, abstractC0164c92);
    }

    public C0992op(C0160c5 c0160c5, C0155c0 c0155c0, AbstractC0164c9 abstractC0164c9, C1067pf c1067pf) {
        super(c0160c5, c0155c0, abstractC0164c9, c1067pf);
    }

    public C0992op(C1064pc c1064pc) {
        super(c1064pc);
    }

    @Override // p000.AbstractC0120b3, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this;
    }

    @Override // p000.AbstractC0120b3, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
