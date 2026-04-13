package android.sun.security.x509;

import android.sun.security.util.BitArray;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DistributionPoint {
    public static final int AA_COMPROMISE = 8;
    public static final int AFFILIATION_CHANGED = 3;
    public static final int CA_COMPROMISE = 2;
    public static final int CERTIFICATE_HOLD = 6;
    public static final int CESSATION_OF_OPERATION = 5;
    public static final int KEY_COMPROMISE = 1;
    public static final int PRIVILEGE_WITHDRAWN = 7;
    private static final String[] REASON_STRINGS = {null, "key compromise", "CA compromise", "affiliation changed", ReasonFlags.SUPERSEDED, "cessation of operation", "certificate hold", "privilege withdrawn", "AA compromise"};
    public static final int SUPERSEDED = 4;
    private static final byte TAG_DIST_PT = 0;
    private static final byte TAG_FULL_NAME = 0;
    private static final byte TAG_ISSUER = 2;
    private static final byte TAG_REASONS = 1;
    private static final byte TAG_REL_NAME = 1;
    private GeneralNames crlIssuer;
    private GeneralNames fullName;
    private volatile int hashCode;
    private boolean[] reasonFlags;
    private RDN relativeName;

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c9, code lost:
    
        throw new java.io.IOException("Invalid encoding of DistributionPoint.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0076, code lost:
    
        throw new java.io.IOException("Duplicate DistributionPointName in DistributionPoint.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00cc, code lost:
    
        if (r6.crlIssuer != null) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00d0, code lost:
    
        if (r6.fullName != null) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00d4, code lost:
    
        if (r6.relativeName == null) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00de, code lost:
    
        throw new java.io.IOException("One of fullName, relativeName,  and crlIssuer has to be set");
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00df, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DistributionPoint(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding of DistributionPoint.");
        }
        while (true) {
            DerInputStream derInputStream = derValue.data;
            if (derInputStream == null || derInputStream.available() == 0) {
                break;
            }
            DerValue derValue2 = derValue.data.getDerValue();
            if (derValue2.isContextSpecific((byte) 0) && derValue2.isConstructed()) {
                if (this.fullName != null || this.relativeName != null) {
                    break;
                }
                DerValue derValue3 = derValue2.data.getDerValue();
                if (derValue3.isContextSpecific((byte) 0) && derValue3.isConstructed()) {
                    derValue3.resetTag((byte) 48);
                    this.fullName = new GeneralNames(derValue3);
                } else {
                    if (!derValue3.isContextSpecific((byte) 1) || !derValue3.isConstructed()) {
                        break;
                    }
                    derValue3.resetTag((byte) 49);
                    this.relativeName = new RDN(derValue3);
                }
            } else if (!derValue2.isContextSpecific((byte) 1) || derValue2.isConstructed()) {
                if (!derValue2.isContextSpecific((byte) 2) || !derValue2.isConstructed()) {
                    break;
                }
                if (this.crlIssuer != null) {
                    throw new IOException("Duplicate CRLIssuer in DistributionPoint.");
                }
                derValue2.resetTag((byte) 48);
                this.crlIssuer = new GeneralNames(derValue2);
            } else {
                if (this.reasonFlags != null) {
                    throw new IOException("Duplicate Reasons in DistributionPoint.");
                }
                derValue2.resetTag((byte) 3);
                this.reasonFlags = derValue2.getUnalignedBitString().toBooleanArray();
            }
        }
        throw new IOException("Invalid DistributionPointName in DistributionPoint");
    }

    private static String reasonToString(int i2) {
        if (i2 > 0) {
            String[] strArr = REASON_STRINGS;
            if (i2 < strArr.length) {
                return strArr[i2];
            }
        }
        return AbstractC0000a.m11g("Unknown reason ", i2);
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2;
        byte createTag;
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.fullName != null || this.relativeName != null) {
            DerOutputStream derOutputStream4 = new DerOutputStream();
            if (this.fullName != null) {
                derOutputStream2 = new DerOutputStream();
                this.fullName.encode(derOutputStream2);
                createTag = DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0);
            } else {
                if (this.relativeName != null) {
                    derOutputStream2 = new DerOutputStream();
                    this.relativeName.encode(derOutputStream2);
                    createTag = DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 1);
                }
                derOutputStream3.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), derOutputStream4);
            }
            derOutputStream4.writeImplicit(createTag, derOutputStream2);
            derOutputStream3.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), derOutputStream4);
        }
        if (this.reasonFlags != null) {
            DerOutputStream derOutputStream5 = new DerOutputStream();
            derOutputStream5.putTruncatedUnalignedBitString(new BitArray(this.reasonFlags));
            derOutputStream3.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 1), derOutputStream5);
        }
        if (this.crlIssuer != null) {
            DerOutputStream derOutputStream6 = new DerOutputStream();
            this.crlIssuer.encode(derOutputStream6);
            derOutputStream3.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 2), derOutputStream6);
        }
        derOutputStream.write((byte) 48, derOutputStream3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DistributionPoint)) {
            return false;
        }
        DistributionPoint distributionPoint = (DistributionPoint) obj;
        return equals(this.fullName, distributionPoint.fullName) && equals(this.relativeName, distributionPoint.relativeName) && equals(this.crlIssuer, distributionPoint.crlIssuer) && Arrays.equals(this.reasonFlags, distributionPoint.reasonFlags);
    }

    public GeneralNames getCRLIssuer() {
        return this.crlIssuer;
    }

    public GeneralNames getFullName() {
        return this.fullName;
    }

    public boolean[] getReasonFlags() {
        return this.reasonFlags;
    }

    public RDN getRelativeName() {
        return this.relativeName;
    }

    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        GeneralNames generalNames = this.fullName;
        int hashCode = generalNames != null ? 1 + generalNames.hashCode() : 1;
        RDN rdn = this.relativeName;
        if (rdn != null) {
            hashCode += rdn.hashCode();
        }
        GeneralNames generalNames2 = this.crlIssuer;
        if (generalNames2 != null) {
            hashCode += generalNames2.hashCode();
        }
        if (this.reasonFlags != null) {
            int i3 = 0;
            while (true) {
                boolean[] zArr = this.reasonFlags;
                if (i3 >= zArr.length) {
                    break;
                }
                if (zArr[i3]) {
                    hashCode += i3;
                }
                i3++;
            }
        }
        int i4 = hashCode;
        this.hashCode = i4;
        return i4;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.fullName != null) {
            sb.append("DistributionPoint:\n     " + this.fullName + "\n");
        }
        if (this.relativeName != null) {
            sb.append("DistributionPoint:\n     " + this.relativeName + "\n");
        }
        if (this.reasonFlags != null) {
            sb.append("   ReasonFlags:\n");
            int i2 = 0;
            while (true) {
                boolean[] zArr = this.reasonFlags;
                if (i2 >= zArr.length) {
                    break;
                }
                if (zArr[i2]) {
                    sb.append("    " + reasonToString(i2) + "\n");
                }
                i2++;
            }
        }
        if (this.crlIssuer != null) {
            sb.append("   CRLIssuer:" + this.crlIssuer + "\n");
        }
        return sb.toString();
    }

    public DistributionPoint(GeneralNames generalNames, boolean[] zArr, GeneralNames generalNames2) {
        if (generalNames == null && generalNames2 == null) {
            throw new IllegalArgumentException("fullName and crlIssuer may not both be null");
        }
        this.fullName = generalNames;
        this.reasonFlags = zArr;
        this.crlIssuer = generalNames2;
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public DistributionPoint(RDN rdn, boolean[] zArr, GeneralNames generalNames) {
        if (rdn == null && generalNames == null) {
            throw new IllegalArgumentException("relativeName and crlIssuer may not both be null");
        }
        this.relativeName = rdn;
        this.reasonFlags = zArr;
        this.crlIssuer = generalNames;
    }
}
