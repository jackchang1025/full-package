package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;

/* loaded from: classes.dex */
public class DistributionPointName {
    private static final byte TAG_FULL_NAME = 0;
    private static final byte TAG_RELATIVE_NAME = 1;
    private GeneralNames fullName;
    private volatile int hashCode;
    private RDN relativeName;

    public DistributionPointName(DerValue derValue) {
        this.fullName = null;
        this.relativeName = null;
        if (derValue.isContextSpecific((byte) 0) && derValue.isConstructed()) {
            derValue.resetTag((byte) 48);
            this.fullName = new GeneralNames(derValue);
        } else {
            if (!derValue.isContextSpecific((byte) 1) || !derValue.isConstructed()) {
                throw new IOException("Invalid encoding for DistributionPointName");
            }
            derValue.resetTag((byte) 49);
            this.relativeName = new RDN(derValue);
        }
    }

    public void encode(DerOutputStream derOutputStream) {
        byte createTag;
        DerOutputStream derOutputStream2 = new DerOutputStream();
        GeneralNames generalNames = this.fullName;
        if (generalNames != null) {
            generalNames.encode(derOutputStream2);
            createTag = DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0);
        } else {
            this.relativeName.encode(derOutputStream2);
            createTag = DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 1);
        }
        derOutputStream.writeImplicit(createTag, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DistributionPointName)) {
            return false;
        }
        DistributionPointName distributionPointName = (DistributionPointName) obj;
        return equals(this.fullName, distributionPointName.fullName) && equals(this.relativeName, distributionPointName.relativeName);
    }

    public GeneralNames getFullName() {
        return this.fullName;
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
        int hashCode = (generalNames != null ? generalNames.hashCode() : this.relativeName.hashCode()) + 1;
        this.hashCode = hashCode;
        return hashCode;
    }

    public String toString() {
        StringBuilder sb;
        Object obj;
        StringBuilder sb2 = new StringBuilder();
        if (this.fullName != null) {
            sb = new StringBuilder("DistributionPointName:\n     ");
            obj = this.fullName;
        } else {
            sb = new StringBuilder("DistributionPointName:\n     ");
            obj = this.relativeName;
        }
        sb.append(obj);
        sb.append("\n");
        sb2.append(sb.toString());
        return sb2.toString();
    }

    public DistributionPointName(GeneralNames generalNames) {
        this.fullName = null;
        this.relativeName = null;
        if (generalNames == null) {
            throw new IllegalArgumentException("fullName must not be null");
        }
        this.fullName = generalNames;
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public DistributionPointName(RDN rdn) {
        this.fullName = null;
        this.relativeName = null;
        if (rdn == null) {
            throw new IllegalArgumentException("relativeName must not be null");
        }
        this.relativeName = rdn;
    }
}
