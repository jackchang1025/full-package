package p000;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: yf */
/* loaded from: classes2.dex */
public class C1455yf {
    private static final Set dupsAllowed;
    private Hashtable extensions = new Hashtable();
    private Vector extOrdering = new Vector();

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(C1452yc.subjectAlternativeName);
        hashSet.add(C1452yc.issuerAlternativeName);
        hashSet.add(C1452yc.subjectDirectoryAttributes);
        hashSet.add(C1452yc.certificateIssuer);
        dupsAllowed = Collections.unmodifiableSet(hashSet);
    }

    public void addExtension(C0160c5 c0160c5, boolean z, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        addExtension(c0160c5, z, interfaceC0117b0.toASN1Primitive().getEncoded("DER"));
    }

    public C1454ye generate() {
        C1452yc[] c1452ycArr = new C1452yc[this.extOrdering.size()];
        for (int i = 0; i != this.extOrdering.size(); i++) {
            c1452ycArr[i] = (C1452yc) this.extensions.get(this.extOrdering.elementAt(i));
        }
        return new C1454ye(c1452ycArr);
    }

    public C1452yc getExtension(C0160c5 c0160c5) {
        return (C1452yc) this.extensions.get(c0160c5);
    }

    public boolean hasExtension(C0160c5 c0160c5) {
        return this.extensions.containsKey(c0160c5);
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public void removeExtension(C0160c5 c0160c5) {
        if (this.extensions.containsKey(c0160c5)) {
            this.extOrdering.removeElement(c0160c5);
            this.extensions.remove(c0160c5);
        } else {
            throw new IllegalArgumentException("extension " + c0160c5 + " not present");
        }
    }

    public void replaceExtension(C0160c5 c0160c5, boolean z, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        replaceExtension(c0160c5, z, interfaceC0117b0.toASN1Primitive().getEncoded("DER"));
    }

    public void reset() {
        this.extensions = new Hashtable();
        this.extOrdering = new Vector();
    }

    public void addExtension(C0160c5 c0160c5, boolean z, byte[] bArr) {
        if (!this.extensions.containsKey(c0160c5)) {
            this.extOrdering.addElement(c0160c5);
            this.extensions.put(c0160c5, new C1452yc(c0160c5, z, new C1048oy(bArr)));
            return;
        }
        if (!dupsAllowed.contains(c0160c5)) {
            throw new IllegalArgumentException("extension " + c0160c5 + " already added");
        }
        AbstractC0400d2 abstractC0400d2 = AbstractC0400d2.getInstance(AbstractC0161c6.getInstance(((C1452yc) this.extensions.get(c0160c5)).getExtnValue()).getOctets());
        AbstractC0400d2 abstractC0400d22 = AbstractC0400d2.getInstance(bArr);
        C0118b1 c0118b1 = new C0118b1(abstractC0400d22.size() + abstractC0400d2.size());
        Enumeration objects = abstractC0400d2.getObjects();
        while (objects.hasMoreElements()) {
            c0118b1.add((InterfaceC0117b0) objects.nextElement());
        }
        Enumeration objects2 = abstractC0400d22.getObjects();
        while (objects2.hasMoreElements()) {
            c0118b1.add((InterfaceC0117b0) objects2.nextElement());
        }
        try {
            this.extensions.put(c0160c5, new C1452yc(c0160c5, z, new C1064pc(c0118b1).getEncoded()));
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }

    public void replaceExtension(C0160c5 c0160c5, boolean z, byte[] bArr) {
        replaceExtension(new C1452yc(c0160c5, z, bArr));
    }

    public void addExtension(C1452yc c1452yc) {
        if (!this.extensions.containsKey(c1452yc.getExtnId())) {
            this.extOrdering.addElement(c1452yc.getExtnId());
            this.extensions.put(c1452yc.getExtnId(), c1452yc);
        } else {
            throw new IllegalArgumentException("extension " + c1452yc.getExtnId() + " already added");
        }
    }

    public void replaceExtension(C1452yc c1452yc) {
        if (this.extensions.containsKey(c1452yc.getExtnId())) {
            this.extensions.put(c1452yc.getExtnId(), c1452yc);
            return;
        }
        throw new IllegalArgumentException("extension " + c1452yc.getExtnId() + " not present");
    }

    public void addExtension(C1454ye c1454ye) {
        C0160c5[] extensionOIDs = c1454ye.getExtensionOIDs();
        for (int i = 0; i != extensionOIDs.length; i++) {
            C0160c5 c0160c5 = extensionOIDs[i];
            C1452yc extension = c1454ye.getExtension(c0160c5);
            addExtension(C0160c5.getInstance(c0160c5), extension.isCritical(), extension.getExtnValue().getOctets());
        }
    }
}
