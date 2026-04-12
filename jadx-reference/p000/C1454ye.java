package p000;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* renamed from: ye */
/* loaded from: classes2.dex */
public class C1454ye extends AbstractC0158c3 {
    private Hashtable extensions;
    private Vector ordering;

    private C1454ye(AbstractC0400d2 abstractC0400d2) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        Enumeration objects = abstractC0400d2.getObjects();
        while (objects.hasMoreElements()) {
            C1452yc c1452yc = C1452yc.getInstance(objects.nextElement());
            if (this.extensions.containsKey(c1452yc.getExtnId())) {
                throw new IllegalArgumentException("repeated extension found: " + c1452yc.getExtnId());
            }
            this.extensions.put(c1452yc.getExtnId(), c1452yc);
            this.ordering.addElement(c1452yc.getExtnId());
        }
    }

    public static C1454ye getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    private C0160c5[] toOidArray(Vector vector) {
        int size = vector.size();
        C0160c5[] c0160c5Arr = new C0160c5[size];
        for (int i = 0; i != size; i++) {
            c0160c5Arr[i] = (C0160c5) vector.elementAt(i);
        }
        return c0160c5Arr;
    }

    public boolean equivalent(C1454ye c1454ye) {
        if (this.extensions.size() != c1454ye.extensions.size()) {
            return false;
        }
        Enumeration enumerationKeys = this.extensions.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            if (!this.extensions.get(objNextElement).equals(c1454ye.extensions.get(objNextElement))) {
                return false;
            }
        }
        return true;
    }

    public C0160c5[] getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    public C1452yc getExtension(C0160c5 c0160c5) {
        return (C1452yc) this.extensions.get(c0160c5);
    }

    public C0160c5[] getExtensionOIDs() {
        return toOidArray(this.ordering);
    }

    public InterfaceC0117b0 getExtensionParsedValue(C0160c5 c0160c5) {
        C1452yc extension = getExtension(c0160c5);
        if (extension != null) {
            return extension.getParsedValue();
        }
        return null;
    }

    public C0160c5[] getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    public Enumeration oids() {
        return this.ordering.elements();
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(this.ordering.size());
        Enumeration enumerationElements = this.ordering.elements();
        while (enumerationElements.hasMoreElements()) {
            c0118b1.add((C1452yc) this.extensions.get((C0160c5) enumerationElements.nextElement()));
        }
        return new C1064pc(c0118b1);
    }

    public C1454ye(C1452yc c1452yc) {
        this.extensions = new Hashtable();
        Vector vector = new Vector();
        this.ordering = vector;
        vector.addElement(c1452yc.getExtnId());
        this.extensions.put(c1452yc.getExtnId(), c1452yc);
    }

    public static C1452yc getExtension(C1454ye c1454ye, C0160c5 c0160c5) {
        if (c1454ye == null) {
            return null;
        }
        return c1454ye.getExtension(c0160c5);
    }

    private C0160c5[] getExtensionOIDs(boolean z) {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); i++) {
            Object objElementAt = this.ordering.elementAt(i);
            if (((C1452yc) this.extensions.get(objElementAt)).isCritical() == z) {
                vector.addElement(objElementAt);
            }
        }
        return toOidArray(vector);
    }

    public static InterfaceC0117b0 getExtensionParsedValue(C1454ye c1454ye, C0160c5 c0160c5) {
        if (c1454ye == null) {
            return null;
        }
        return c1454ye.getExtensionParsedValue(c0160c5);
    }

    public static C1454ye getInstance(Object obj) {
        if (obj instanceof C1454ye) {
            return (C1454ye) obj;
        }
        if (obj != null) {
            return new C1454ye(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C1454ye(C1452yc[] c1452ycArr) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        for (int i = 0; i != c1452ycArr.length; i++) {
            C1452yc c1452yc = c1452ycArr[i];
            this.ordering.addElement(c1452yc.getExtnId());
            this.extensions.put(c1452yc.getExtnId(), c1452yc);
        }
    }
}
