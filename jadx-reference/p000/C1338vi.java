package p000;

import java.util.Enumeration;
import java.util.Vector;

/* renamed from: vi */
/* loaded from: classes2.dex */
public class C1338vi {
    private static void addEnumeration(Vector vector, Enumeration enumeration) {
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
    }

    public static bi1 getByName(String str) {
        bi1 byName = yh1.getByName(str);
        if (byName == null) {
            byName = qs0.getByName(str);
        }
        if (byName == null) {
            byName = kh0.getByName(str);
        }
        if (byName == null) {
            byName = n51.getByName(str);
        }
        if (byName == null) {
            byName = C0001a0.getByName(str);
        }
        if (byName == null) {
            byName = C1332vc.getByNameX9(str);
        }
        return byName == null ? f20.getByName(str) : byName;
    }

    public static bi1 getByOID(C0160c5 c0160c5) {
        bi1 byOID = yh1.getByOID(c0160c5);
        if (byOID == null) {
            byOID = qs0.getByOID(c0160c5);
        }
        if (byOID == null) {
            byOID = n51.getByOID(c0160c5);
        }
        if (byOID == null) {
            byOID = C0001a0.getByOID(c0160c5);
        }
        if (byOID == null) {
            byOID = C1332vc.getByOIDX9(c0160c5);
        }
        return byOID == null ? f20.getByOID(c0160c5) : byOID;
    }

    public static String getName(C0160c5 c0160c5) {
        String name = yh1.getName(c0160c5);
        if (name == null) {
            name = qs0.getName(c0160c5);
        }
        if (name == null) {
            name = kh0.getName(c0160c5);
        }
        if (name == null) {
            name = n51.getName(c0160c5);
        }
        if (name == null) {
            name = C0001a0.getName(c0160c5);
        }
        if (name == null) {
            name = C1332vc.getName(c0160c5);
        }
        if (name == null) {
            name = f20.getName(c0160c5);
        }
        return name == null ? C0953oi.getName(c0160c5) : name;
    }

    public static Enumeration getNames() {
        Vector vector = new Vector();
        addEnumeration(vector, yh1.getNames());
        addEnumeration(vector, qs0.getNames());
        addEnumeration(vector, kh0.getNames());
        addEnumeration(vector, n51.getNames());
        addEnumeration(vector, C0001a0.getNames());
        addEnumeration(vector, C1332vc.getNames());
        addEnumeration(vector, f20.getNames());
        return vector.elements();
    }

    public static C0160c5 getOID(String str) {
        C0160c5 oid = yh1.getOID(str);
        if (oid == null) {
            oid = qs0.getOID(str);
        }
        if (oid == null) {
            oid = kh0.getOID(str);
        }
        if (oid == null) {
            oid = n51.getOID(str);
        }
        if (oid == null) {
            oid = C0001a0.getOID(str);
        }
        if (oid == null) {
            oid = C1332vc.getOID(str);
        }
        if (oid == null) {
            oid = f20.getOID(str);
        }
        return (oid == null && str.equals("curve25519")) ? C0927nv.curvey25519 : oid;
    }
}
