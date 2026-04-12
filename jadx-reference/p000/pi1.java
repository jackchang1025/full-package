package p000;

/* loaded from: classes2.dex */
public class pi1 extends AbstractC0158c3 {
    private final int height;
    private final int layers;
    private final C1168r5 treeDigest;
    private final C0155c0 version;

    public pi1(int i, int i2, C1168r5 c1168r5) {
        this.version = new C0155c0(0L);
        this.height = i;
        this.layers = i2;
        this.treeDigest = c1168r5;
    }

    public static pi1 getInstance(Object obj) {
        if (obj instanceof pi1) {
            return (pi1) obj;
        }
        if (obj != null) {
            return new pi1(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public int getHeight() {
        return this.height;
    }

    public int getLayers() {
        return this.layers;
    }

    public C1168r5 getTreeDigest() {
        return this.treeDigest;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(this.version);
        c0118b1.add(new C0155c0(this.height));
        c0118b1.add(new C0155c0(this.layers));
        c0118b1.add(this.treeDigest);
        return new C1064pc(c0118b1);
    }

    private pi1(AbstractC0400d2 abstractC0400d2) {
        this.version = C0155c0.getInstance(abstractC0400d2.getObjectAt(0));
        this.height = C0155c0.getInstance(abstractC0400d2.getObjectAt(1)).intValueExact();
        this.layers = C0155c0.getInstance(abstractC0400d2.getObjectAt(2)).intValueExact();
        this.treeDigest = C1168r5.getInstance(abstractC0400d2.getObjectAt(3));
    }
}
