package android.sun.security.x509;

import android.sun.security.util.BitArray;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ReasonFlags {
    private boolean[] bitString;
    public static final String UNUSED = "unused";
    public static final String KEY_COMPROMISE = "key_compromise";
    public static final String CA_COMPROMISE = "ca_compromise";
    public static final String AFFILIATION_CHANGED = "affiliation_changed";
    public static final String SUPERSEDED = "superseded";
    public static final String CESSATION_OF_OPERATION = "cessation_of_operation";
    public static final String CERTIFICATE_HOLD = "certificate_hold";
    public static final String PRIVILEGE_WITHDRAWN = "privilege_withdrawn";
    public static final String AA_COMPROMISE = "aa_compromise";
    private static final String[] NAMES = {UNUSED, KEY_COMPROMISE, CA_COMPROMISE, AFFILIATION_CHANGED, SUPERSEDED, CESSATION_OF_OPERATION, CERTIFICATE_HOLD, PRIVILEGE_WITHDRAWN, AA_COMPROMISE};

    public ReasonFlags(BitArray bitArray) {
        this.bitString = bitArray.toBooleanArray();
    }

    private boolean isSet(int i2) {
        return this.bitString[i2];
    }

    private static int name2Index(String str) {
        int i2 = 0;
        while (true) {
            String[] strArr = NAMES;
            if (i2 >= strArr.length) {
                throw new IOException("Name not recognized by ReasonFlags");
            }
            if (strArr[i2].equalsIgnoreCase(str)) {
                return i2;
            }
            i2++;
        }
    }

    private void set(int i2, boolean z2) {
        boolean[] zArr = this.bitString;
        if (i2 >= zArr.length) {
            boolean[] zArr2 = new boolean[i2 + 1];
            System.arraycopy(zArr, 0, zArr2, 0, zArr.length);
            this.bitString = zArr2;
        }
        this.bitString[i2] = z2;
    }

    public void delete(String str) {
        set(str, Boolean.FALSE);
    }

    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
    }

    public Object get(String str) {
        return Boolean.valueOf(isSet(name2Index(str)));
    }

    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        int i2 = 0;
        while (true) {
            String[] strArr = NAMES;
            if (i2 >= strArr.length) {
                return attributeNameEnumeration.elements();
            }
            attributeNameEnumeration.addElement(strArr[i2]);
            i2++;
        }
    }

    public boolean[] getFlags() {
        return this.bitString;
    }

    public String toString() {
        String str;
        str = "Reason Flags [\n";
        try {
            str = isSet(0) ? "Reason Flags [\n  Unused\n" : "Reason Flags [\n";
            if (isSet(1)) {
                str = str + "  Key Compromise\n";
            }
            if (isSet(2)) {
                str = str + "  CA Compromise\n";
            }
            if (isSet(3)) {
                str = str + "  Affiliation_Changed\n";
            }
            if (isSet(4)) {
                str = str + "  Superseded\n";
            }
            if (isSet(5)) {
                str = str + "  Cessation Of Operation\n";
            }
            if (isSet(6)) {
                str = str + "  Certificate Hold\n";
            }
            if (isSet(7)) {
                str = str + "  Privilege Withdrawn\n";
            }
            if (isSet(8)) {
                str = str + "  AA Compromise\n";
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
        return AbstractC0000a.m30z(str, "]\n");
    }

    public ReasonFlags(DerInputStream derInputStream) {
        this.bitString = derInputStream.getDerValue().getUnalignedBitString(true).toBooleanArray();
    }

    public void set(String str, Object obj) {
        if (!(obj instanceof Boolean)) {
            throw new IOException("Attribute must be of type Boolean.");
        }
        set(name2Index(str), ((Boolean) obj).booleanValue());
    }

    public ReasonFlags(DerValue derValue) {
        this.bitString = derValue.getUnalignedBitString(true).toBooleanArray();
    }

    public ReasonFlags(byte[] bArr) {
        this.bitString = new BitArray(bArr.length * 8, bArr).toBooleanArray();
    }

    public ReasonFlags(boolean[] zArr) {
        this.bitString = zArr;
    }
}
