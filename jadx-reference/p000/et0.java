package p000;

/* loaded from: classes2.dex */
public class et0 extends AbstractC0158c3 {
    private final C1168r5 treeDigest;
    private final C0155c0 version;

    private et0(AbstractC0400d2 abstractC0400d2) {
        this.version = C0155c0.getInstance(abstractC0400d2.getObjectAt(0));
        this.treeDigest = C1168r5.getInstance(abstractC0400d2.getObjectAt(1));
    }

    public static final et0 getInstance(Object obj) {
        if (obj instanceof et0) {
            return (et0) obj;
        }
        if (obj != null) {
            return new et0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C1168r5 getTreeDigest() {
        return this.treeDigest;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(this.version);
        c0118b1.add(this.treeDigest);
        return new C1064pc(c0118b1);
    }

    public et0(C1168r5 c1168r5) {
        this.version = new C0155c0(0L);
        this.treeDigest = c1168r5;
    }
}
