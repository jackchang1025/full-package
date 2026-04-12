package p000;

import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class u21 extends AbstractC0158c3 {
    private C1168r5 algId;
    private AbstractC0007a6 keyData;

    public u21(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        Enumeration objects = abstractC0400d2.getObjects();
        this.algId = C1168r5.getInstance(objects.nextElement());
        this.keyData = C0991oo.getInstance(objects.nextElement());
    }

    public static u21 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public C1168r5 getAlgorithm() {
        return this.algId;
    }

    public C1168r5 getAlgorithmId() {
        return this.algId;
    }

    public AbstractC0164c9 getPublicKey() throws IOException {
        return AbstractC0164c9.fromByteArray(this.keyData.getOctets());
    }

    public AbstractC0007a6 getPublicKeyData() {
        return this.keyData;
    }

    public AbstractC0164c9 parsePublicKey() throws IOException {
        return AbstractC0164c9.fromByteArray(this.keyData.getOctets());
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(2);
        c0118b1.add(this.algId);
        c0118b1.add(this.keyData);
        return new C1064pc(c0118b1);
    }

    public u21(C1168r5 c1168r5, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        this.keyData = new C0991oo(interfaceC0117b0);
        this.algId = c1168r5;
    }

    public static u21 getInstance(Object obj) {
        if (obj instanceof u21) {
            return (u21) obj;
        }
        if (obj != null) {
            return new u21(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public u21(C1168r5 c1168r5, byte[] bArr) {
        this.keyData = new C0991oo(bArr);
        this.algId = c1168r5;
    }
}
