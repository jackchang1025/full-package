package p000;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: i0 */
/* loaded from: classes2.dex */
public abstract class AbstractC0604i0 implements mh1 {
    private int calcHashCode(InterfaceC0117b0 interfaceC0117b0) {
        return o40.canonicalString(interfaceC0117b0).hashCode();
    }

    public static Hashtable copyHashTable(Hashtable hashtable) {
        Hashtable hashtable2 = new Hashtable();
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            hashtable2.put(objNextElement, hashtable.get(objNextElement));
        }
        return hashtable2;
    }

    private boolean foundMatch(boolean z, np0 np0Var, np0[] np0VarArr) {
        if (z) {
            for (int length = np0VarArr.length - 1; length >= 0; length--) {
                np0 np0Var2 = np0VarArr[length];
                if (np0Var2 != null && rdnAreEqual(np0Var, np0Var2)) {
                    np0VarArr[length] = null;
                    return true;
                }
            }
        } else {
            for (int i = 0; i != np0VarArr.length; i++) {
                np0 np0Var3 = np0VarArr[i];
                if (np0Var3 != null && rdnAreEqual(np0Var, np0Var3)) {
                    np0VarArr[i] = null;
                    return true;
                }
            }
        }
        return false;
    }

    @Override // p000.mh1
    public boolean areEqual(kh1 kh1Var, kh1 kh1Var2) {
        np0[] rDNs = kh1Var.getRDNs();
        np0[] rDNs2 = kh1Var2.getRDNs();
        if (rDNs.length != rDNs2.length) {
            return false;
        }
        boolean z = (rDNs[0].getFirst() == null || rDNs2[0].getFirst() == null) ? false : !rDNs[0].getFirst().getType().equals((AbstractC0164c9) rDNs2[0].getFirst().getType());
        for (int i = 0; i != rDNs.length; i++) {
            if (!foundMatch(z, rDNs[i], rDNs2)) {
                return false;
            }
        }
        return true;
    }

    @Override // p000.mh1
    public abstract /* synthetic */ C0160c5 attrNameToOID(String str);

    @Override // p000.mh1
    public int calculateHashCode(kh1 kh1Var) {
        np0[] rDNs = kh1Var.getRDNs();
        int iHashCode = 0;
        for (int i = 0; i != rDNs.length; i++) {
            if (rDNs[i].isMultiValued()) {
                C0145bs[] typesAndValues = rDNs[i].getTypesAndValues();
                for (int i2 = 0; i2 != typesAndValues.length; i2++) {
                    iHashCode = (iHashCode ^ typesAndValues[i2].getType().hashCode()) ^ calcHashCode(typesAndValues[i2].getValue());
                }
            } else {
                iHashCode = (iHashCode ^ rDNs[i].getFirst().getType().hashCode()) ^ calcHashCode(rDNs[i].getFirst().getValue());
            }
        }
        return iHashCode;
    }

    public InterfaceC0117b0 encodeStringValue(C0160c5 c0160c5, String str) {
        return new C1069ph(str);
    }

    @Override // p000.mh1
    public abstract /* synthetic */ np0[] fromString(String str);

    @Override // p000.mh1
    public abstract /* synthetic */ String[] oidToAttrNames(C0160c5 c0160c5);

    @Override // p000.mh1
    public abstract /* synthetic */ String oidToDisplayName(C0160c5 c0160c5);

    public boolean rdnAreEqual(np0 np0Var, np0 np0Var2) {
        return o40.rDNAreEqual(np0Var, np0Var2);
    }

    @Override // p000.mh1
    public InterfaceC0117b0 stringToValue(C0160c5 c0160c5, String str) {
        if (str.length() == 0 || str.charAt(0) != '#') {
            if (str.length() != 0 && str.charAt(0) == '\\') {
                str = str.substring(1);
            }
            return encodeStringValue(c0160c5, str);
        }
        try {
            return o40.valueFromHexString(str, 1);
        } catch (IOException unused) {
            throw new ASN1ParsingException("can't recode value for oid " + c0160c5.getId());
        }
    }

    @Override // p000.mh1
    public abstract /* synthetic */ String toString(kh1 kh1Var);
}
