package p000;

import java.util.Enumeration;

/* loaded from: classes2.dex */
public class kh1 extends AbstractC0158c3 implements InterfaceC0010a9 {
    private static mh1 defaultStyle = C0168cd.INSTANCE;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private C1064pc rdnSeq;
    private np0[] rdns;
    private mh1 style;

    private kh1(AbstractC0400d2 abstractC0400d2) {
        this(defaultStyle, abstractC0400d2);
    }

    public static mh1 getDefaultStyle() {
        return defaultStyle;
    }

    public static kh1 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, true));
    }

    public static void setDefaultStyle(mh1 mh1Var) {
        if (mh1Var == null) {
            throw new NullPointerException("cannot set style to null");
        }
        defaultStyle = mh1Var;
    }

    @Override // p000.AbstractC0158c3
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof kh1) && !(obj instanceof AbstractC0400d2)) {
            return false;
        }
        if (toASN1Primitive().equals(((InterfaceC0117b0) obj).toASN1Primitive())) {
            return true;
        }
        try {
            return this.style.areEqual(this, new kh1(AbstractC0400d2.getInstance(((InterfaceC0117b0) obj).toASN1Primitive())));
        } catch (Exception unused) {
            return false;
        }
    }

    public C0160c5[] getAttributeTypes() {
        int length = this.rdns.length;
        int size = 0;
        for (int i = 0; i < length; i++) {
            size += this.rdns[i].size();
        }
        C0160c5[] c0160c5Arr = new C0160c5[size];
        int iCollectAttributeTypes = 0;
        for (int i2 = 0; i2 < length; i2++) {
            iCollectAttributeTypes += this.rdns[i2].collectAttributeTypes(c0160c5Arr, iCollectAttributeTypes);
        }
        return c0160c5Arr;
    }

    public np0[] getRDNs() {
        return (np0[]) this.rdns.clone();
    }

    @Override // p000.AbstractC0158c3
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        int iCalculateHashCode = this.style.calculateHashCode(this);
        this.hashCodeValue = iCalculateHashCode;
        return iCalculateHashCode;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.rdnSeq;
    }

    public String toString() {
        return this.style.toString(this);
    }

    private kh1(mh1 mh1Var, AbstractC0400d2 abstractC0400d2) {
        this.style = mh1Var;
        this.rdns = new np0[abstractC0400d2.size()];
        Enumeration objects = abstractC0400d2.getObjects();
        boolean z = true;
        int i = 0;
        while (objects.hasMoreElements()) {
            Object objNextElement = objects.nextElement();
            np0 np0Var = np0.getInstance(objNextElement);
            z &= np0Var == objNextElement;
            this.rdns[i] = np0Var;
            i++;
        }
        this.rdnSeq = z ? C1064pc.convert(abstractC0400d2) : new C1064pc(this.rdns);
    }

    public static kh1 getInstance(mh1 mh1Var, Object obj) {
        if (obj instanceof kh1) {
            return new kh1(mh1Var, (kh1) obj);
        }
        if (obj != null) {
            return new kh1(mh1Var, AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public np0[] getRDNs(C0160c5 c0160c5) {
        int length = this.rdns.length;
        np0[] np0VarArr = new np0[length];
        int i = 0;
        int i2 = 0;
        while (true) {
            np0[] np0VarArr2 = this.rdns;
            if (i == np0VarArr2.length) {
                break;
            }
            np0 np0Var = np0VarArr2[i];
            if (np0Var.containsAttributeType(c0160c5)) {
                np0VarArr[i2] = np0Var;
                i2++;
            }
            i++;
        }
        if (i2 >= length) {
            return np0VarArr;
        }
        np0[] np0VarArr3 = new np0[i2];
        System.arraycopy(np0VarArr, 0, np0VarArr3, 0, i2);
        return np0VarArr3;
    }

    public kh1(mh1 mh1Var, kh1 kh1Var) {
        this.style = mh1Var;
        this.rdns = kh1Var.rdns;
        this.rdnSeq = kh1Var.rdnSeq;
    }

    public static kh1 getInstance(Object obj) {
        if (obj instanceof kh1) {
            return (kh1) obj;
        }
        if (obj != null) {
            return new kh1(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public kh1(mh1 mh1Var, String str) {
        this(mh1Var.fromString(str));
        this.style = mh1Var;
    }

    public kh1(mh1 mh1Var, np0[] np0VarArr) {
        this.style = mh1Var;
        this.rdns = (np0[]) np0VarArr.clone();
        this.rdnSeq = new C1064pc(this.rdns);
    }

    public kh1(String str) {
        this(defaultStyle, str);
    }

    public kh1(np0[] np0VarArr) {
        this(defaultStyle, np0VarArr);
    }
}
