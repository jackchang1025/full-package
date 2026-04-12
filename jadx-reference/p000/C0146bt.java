package p000;

import java.math.BigInteger;
import java.util.Enumeration;

/* renamed from: bt */
/* loaded from: classes2.dex */
public class C0146bt extends AbstractC0158c3 {
    r20 certissuer;
    C0155c0 certserno;
    AbstractC0161c6 keyidentifier;

    public C0146bt(AbstractC0400d2 abstractC0400d2) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        Enumeration objects = abstractC0400d2.getObjects();
        while (objects.hasMoreElements()) {
            AbstractC0439e0 abstractC0439e0 = AbstractC0439e0.getInstance(objects.nextElement());
            int tagNo = abstractC0439e0.getTagNo();
            if (tagNo == 0) {
                this.keyidentifier = AbstractC0161c6.getInstance(abstractC0439e0, false);
            } else if (tagNo == 1) {
                this.certissuer = r20.getInstance(abstractC0439e0, false);
            } else {
                if (tagNo != 2) {
                    throw new IllegalArgumentException("illegal tag");
                }
                this.certserno = C0155c0.getInstance(abstractC0439e0, false);
            }
        }
    }

    public static C0146bt fromExtensions(C1454ye c1454ye) {
        return getInstance(C1454ye.getExtensionParsedValue(c1454ye, C1452yc.authorityKeyIdentifier));
    }

    public static C0146bt getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public r20 getAuthorityCertIssuer() {
        return this.certissuer;
    }

    public BigInteger getAuthorityCertSerialNumber() {
        C0155c0 c0155c0 = this.certserno;
        if (c0155c0 != null) {
            return c0155c0.getValue();
        }
        return null;
    }

    public byte[] getKeyIdentifier() {
        AbstractC0161c6 abstractC0161c6 = this.keyidentifier;
        if (abstractC0161c6 != null) {
            return abstractC0161c6.getOctets();
        }
        return null;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        AbstractC0161c6 abstractC0161c6 = this.keyidentifier;
        if (abstractC0161c6 != null) {
            c0118b1.add(new C1067pf(false, 0, (InterfaceC0117b0) abstractC0161c6));
        }
        r20 r20Var = this.certissuer;
        if (r20Var != null) {
            c0118b1.add(new C1067pf(false, 1, (InterfaceC0117b0) r20Var));
        }
        C0155c0 c0155c0 = this.certserno;
        if (c0155c0 != null) {
            c0118b1.add(new C1067pf(false, 2, (InterfaceC0117b0) c0155c0));
        }
        return new C1064pc(c0118b1);
    }

    public String toString() {
        AbstractC0161c6 abstractC0161c6 = this.keyidentifier;
        return AbstractC0003a2.m33b4("AuthorityKeyIdentifier: KeyID(", abstractC0161c6 != null ? c40.toHexString(abstractC0161c6.getOctets()) : "null", ")");
    }

    public C0146bt(r20 r20Var, BigInteger bigInteger) {
        this((byte[]) null, r20Var, bigInteger);
    }

    public static C0146bt getInstance(Object obj) {
        if (obj instanceof C0146bt) {
            return (C0146bt) obj;
        }
        if (obj != null) {
            return new C0146bt(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C0146bt(u21 u21Var) {
        this(u21Var, (r20) null, (BigInteger) null);
    }

    public C0146bt(u21 u21Var, r20 r20Var, BigInteger bigInteger) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        ss0 ss0Var = new ss0();
        byte[] bArr = new byte[ss0Var.getDigestSize()];
        byte[] bytes = u21Var.getPublicKeyData().getBytes();
        ss0Var.update(bytes, 0, bytes.length);
        ss0Var.doFinal(bArr, 0);
        this.keyidentifier = new C1048oy(bArr);
        this.certissuer = r20Var;
        this.certserno = bigInteger != null ? new C0155c0(bigInteger) : null;
    }

    public C0146bt(byte[] bArr) {
        this(bArr, (r20) null, (BigInteger) null);
    }

    public C0146bt(byte[] bArr, r20 r20Var, BigInteger bigInteger) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        this.keyidentifier = bArr != null ? new C1048oy(bArr) : null;
        this.certissuer = r20Var;
        this.certserno = bigInteger != null ? new C0155c0(bigInteger) : null;
    }
}
