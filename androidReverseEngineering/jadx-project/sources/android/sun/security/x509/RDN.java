package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class RDN {
    final AVA[] assertion;
    private volatile List<AVA> avaList;
    private volatile String canonicalString;

    public RDN(int i2) {
        this.assertion = new AVA[i2];
    }

    private String toRFC2253StringInternal(boolean z2, Map<String, String> map) {
        AVA[] avaArr = this.assertion;
        int i2 = 0;
        if (avaArr.length == 1) {
            return z2 ? avaArr[0].toRFC2253CanonicalString() : avaArr[0].toRFC2253String(map);
        }
        StringBuilder sb = new StringBuilder();
        if (z2) {
            ArrayList arrayList = new ArrayList(this.assertion.length);
            int i3 = 0;
            while (true) {
                AVA[] avaArr2 = this.assertion;
                if (i3 >= avaArr2.length) {
                    break;
                }
                arrayList.add(avaArr2[i3]);
                i3++;
            }
            Collections.sort(arrayList, AVAComparator.getInstance());
            while (i2 < arrayList.size()) {
                if (i2 > 0) {
                    sb.append('+');
                }
                sb.append(((AVA) arrayList.get(i2)).toRFC2253CanonicalString());
                i2++;
            }
        } else {
            while (i2 < this.assertion.length) {
                if (i2 > 0) {
                    sb.append('+');
                }
                sb.append(this.assertion[i2].toRFC2253String(map));
                i2++;
            }
        }
        return sb.toString();
    }

    public List<AVA> avas() {
        List<AVA> list = this.avaList;
        if (list != null) {
            return list;
        }
        List<AVA> unmodifiableList = Collections.unmodifiableList(Arrays.asList(this.assertion));
        this.avaList = unmodifiableList;
        return unmodifiableList;
    }

    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putOrderedSetOf((byte) 49, this.assertion);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RDN)) {
            return false;
        }
        RDN rdn = (RDN) obj;
        if (this.assertion.length != rdn.assertion.length) {
            return false;
        }
        return toRFC2253String(true).equals(rdn.toRFC2253String(true));
    }

    public DerValue findAttribute(ObjectIdentifier objectIdentifier) {
        int i2 = 0;
        while (true) {
            AVA[] avaArr = this.assertion;
            if (i2 >= avaArr.length) {
                return null;
            }
            if (avaArr[i2].oid.equals(objectIdentifier)) {
                return this.assertion[i2].value;
            }
            i2++;
        }
    }

    public int hashCode() {
        return toRFC2253String(true).hashCode();
    }

    public int size() {
        return this.assertion.length;
    }

    public String toRFC1779String() {
        return toRFC1779String(Collections.emptyMap());
    }

    public String toRFC2253String() {
        return toRFC2253StringInternal(false, Collections.emptyMap());
    }

    public String toString() {
        AVA[] avaArr = this.assertion;
        if (avaArr.length == 1) {
            return avaArr[0].toString();
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.assertion.length; i2++) {
            if (i2 != 0) {
                sb.append(" + ");
            }
            sb.append(this.assertion[i2].toString());
        }
        return sb.toString();
    }

    public RDN(DerValue derValue) {
        if (derValue.tag != 49) {
            throw new IOException("X500 RDN");
        }
        DerValue[] set = new DerInputStream(derValue.toByteArray()).getSet(5);
        this.assertion = new AVA[set.length];
        for (int i2 = 0; i2 < set.length; i2++) {
            this.assertion[i2] = new AVA(set[i2]);
        }
    }

    public String toRFC1779String(Map<String, String> map) {
        AVA[] avaArr = this.assertion;
        if (avaArr.length == 1) {
            return avaArr[0].toRFC1779String(map);
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.assertion.length; i2++) {
            if (i2 != 0) {
                sb.append(" + ");
            }
            sb.append(this.assertion[i2].toRFC1779String(map));
        }
        return sb.toString();
    }

    public String toRFC2253String(Map<String, String> map) {
        return toRFC2253StringInternal(false, map);
    }

    public RDN(AVA ava) {
        ava.getClass();
        this.assertion = new AVA[]{ava};
    }

    public String toRFC2253String(boolean z2) {
        if (!z2) {
            return toRFC2253StringInternal(false, Collections.emptyMap());
        }
        String str = this.canonicalString;
        if (str != null) {
            return str;
        }
        String rFC2253StringInternal = toRFC2253StringInternal(true, Collections.emptyMap());
        this.canonicalString = rFC2253StringInternal;
        return rFC2253StringInternal;
    }

    public RDN(String str) {
        this(str, (Map<String, String>) Collections.emptyMap());
    }

    public RDN(String str, String str2) {
        this(str, str2, Collections.emptyMap());
    }

    public RDN(String str, String str2, Map<String, String> map) {
        if (!str2.equalsIgnoreCase("RFC2253")) {
            throw new IOException("Unsupported format ".concat(str2));
        }
        ArrayList arrayList = new ArrayList(3);
        int indexOf = str.indexOf(43);
        int i2 = 0;
        while (indexOf >= 0) {
            if (indexOf > 0 && str.charAt(indexOf - 1) != '\\') {
                String substring = str.substring(i2, indexOf);
                if (substring.length() == 0) {
                    throw new IOException(AbstractC0000a.m16l("empty AVA in RDN \"", str, "\""));
                }
                arrayList.add(new AVA(new StringReader(substring), 3, map));
                i2 = indexOf + 1;
            }
            indexOf = str.indexOf(43, indexOf + 1);
        }
        String substring2 = str.substring(i2);
        if (substring2.length() == 0) {
            throw new IOException(AbstractC0000a.m16l("empty AVA in RDN \"", str, "\""));
        }
        arrayList.add(new AVA(new StringReader(substring2), 3, map));
        this.assertion = (AVA[]) arrayList.toArray(new AVA[arrayList.size()]);
    }

    public RDN(String str, Map<String, String> map) {
        ArrayList arrayList = new ArrayList(3);
        int indexOf = str.indexOf(43);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (indexOf >= 0) {
            int countQuotes = X500Name.countQuotes(str, i3, indexOf) + i4;
            if (indexOf <= 0 || str.charAt(indexOf - 1) == '\\' || countQuotes == 1) {
                i4 = countQuotes;
            } else {
                String substring = str.substring(i2, indexOf);
                if (substring.length() == 0) {
                    throw new IOException(AbstractC0000a.m16l("empty AVA in RDN \"", str, "\""));
                }
                arrayList.add(new AVA(new StringReader(substring), map));
                i2 = indexOf + 1;
                i4 = 0;
            }
            i3 = indexOf + 1;
            indexOf = str.indexOf(43, i3);
        }
        String substring2 = str.substring(i2);
        if (substring2.length() == 0) {
            throw new IOException(AbstractC0000a.m16l("empty AVA in RDN \"", str, "\""));
        }
        arrayList.add(new AVA(new StringReader(substring2), map));
        this.assertion = (AVA[]) arrayList.toArray(new AVA[arrayList.size()]);
    }

    public RDN(AVA[] avaArr) {
        this.assertion = (AVA[]) avaArr.clone();
        int i2 = 0;
        while (true) {
            AVA[] avaArr2 = this.assertion;
            if (i2 >= avaArr2.length) {
                return;
            }
            avaArr2[i2].getClass();
            i2++;
        }
    }
}
