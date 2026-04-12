package p000;

import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes2.dex */
public class io0 extends AbstractC0158c3 {
    private AbstractC0402d4 attributes;
    private AbstractC0161c6 privateKey;
    private C1168r5 privateKeyAlgorithm;
    private AbstractC0007a6 publicKey;
    private C0155c0 version;

    private io0(AbstractC0400d2 abstractC0400d2) {
        Enumeration objects = abstractC0400d2.getObjects();
        C0155c0 c0155c0 = C0155c0.getInstance(objects.nextElement());
        this.version = c0155c0;
        int versionValue = getVersionValue(c0155c0);
        this.privateKeyAlgorithm = C1168r5.getInstance(objects.nextElement());
        this.privateKey = AbstractC0161c6.getInstance(objects.nextElement());
        int i = -1;
        while (objects.hasMoreElements()) {
            AbstractC0439e0 abstractC0439e0 = (AbstractC0439e0) objects.nextElement();
            int tagNo = abstractC0439e0.getTagNo();
            if (tagNo <= i) {
                throw new IllegalArgumentException("invalid optional field in private key info");
            }
            if (tagNo == 0) {
                this.attributes = AbstractC0402d4.getInstance(abstractC0439e0, false);
            } else {
                if (tagNo != 1) {
                    throw new IllegalArgumentException("unknown optional field in private key info");
                }
                if (versionValue < 1) {
                    throw new IllegalArgumentException("'publicKey' requires version v2(1) or later");
                }
                this.publicKey = C0991oo.getInstance(abstractC0439e0, false);
            }
            i = tagNo;
        }
    }

    public static io0 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    private static int getVersionValue(C0155c0 c0155c0) {
        int iIntValueExact = c0155c0.intValueExact();
        if (iIntValueExact < 0 || iIntValueExact > 1) {
            throw new IllegalArgumentException("invalid version for private key info");
        }
        return iIntValueExact;
    }

    public AbstractC0402d4 getAttributes() {
        return this.attributes;
    }

    public AbstractC0161c6 getPrivateKey() {
        return new C1048oy(this.privateKey.getOctets());
    }

    public C1168r5 getPrivateKeyAlgorithm() {
        return this.privateKeyAlgorithm;
    }

    public AbstractC0007a6 getPublicKeyData() {
        return this.publicKey;
    }

    public C0155c0 getVersion() {
        return this.version;
    }

    public boolean hasPublicKey() {
        return this.publicKey != null;
    }

    public InterfaceC0117b0 parsePrivateKey() throws IOException {
        return AbstractC0164c9.fromByteArray(this.privateKey.getOctets());
    }

    public InterfaceC0117b0 parsePublicKey() throws IOException {
        AbstractC0007a6 abstractC0007a6 = this.publicKey;
        if (abstractC0007a6 == null) {
            return null;
        }
        return AbstractC0164c9.fromByteArray(abstractC0007a6.getOctets());
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(5);
        c0118b1.add(this.version);
        c0118b1.add(this.privateKeyAlgorithm);
        c0118b1.add(this.privateKey);
        AbstractC0402d4 abstractC0402d4 = this.attributes;
        if (abstractC0402d4 != null) {
            c0118b1.add(new C1067pf(false, 0, (InterfaceC0117b0) abstractC0402d4));
        }
        AbstractC0007a6 abstractC0007a6 = this.publicKey;
        if (abstractC0007a6 != null) {
            c0118b1.add(new C1067pf(false, 1, (InterfaceC0117b0) abstractC0007a6));
        }
        return new C1064pc(c0118b1);
    }

    public io0(C1168r5 c1168r5, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        this(c1168r5, interfaceC0117b0, null, null);
    }

    public static io0 getInstance(Object obj) {
        if (obj instanceof io0) {
            return (io0) obj;
        }
        if (obj != null) {
            return new io0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public io0(C1168r5 c1168r5, InterfaceC0117b0 interfaceC0117b0, AbstractC0402d4 abstractC0402d4) throws IOException {
        this(c1168r5, interfaceC0117b0, abstractC0402d4, null);
    }

    public io0(C1168r5 c1168r5, InterfaceC0117b0 interfaceC0117b0, AbstractC0402d4 abstractC0402d4, byte[] bArr) throws IOException {
        this.version = new C0155c0(bArr != null ? C0427ds.ONE : C0427ds.ZERO);
        this.privateKeyAlgorithm = c1168r5;
        this.privateKey = new C1048oy(interfaceC0117b0);
        this.attributes = abstractC0402d4;
        this.publicKey = bArr == null ? null : new C0991oo(bArr);
    }
}
