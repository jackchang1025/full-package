package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class EDIPartyName implements GeneralNameInterface {
    private static final byte TAG_ASSIGNER = 0;
    private static final byte TAG_PARTYNAME = 1;
    private String assigner;
    private int myhash;
    private String party;

    public EDIPartyName(DerValue derValue) {
        this.assigner = null;
        this.party = null;
        this.myhash = -1;
        DerValue[] sequence = new DerInputStream(derValue.toByteArray()).getSequence(2);
        int length = sequence.length;
        if (length < 1 || length > 2) {
            throw new IOException("Invalid encoding of EDIPartyName");
        }
        for (int i2 = 0; i2 < length; i2++) {
            DerValue derValue2 = sequence[i2];
            if (derValue2.isContextSpecific((byte) 0) && !derValue2.isConstructed()) {
                if (this.assigner != null) {
                    throw new IOException("Duplicate nameAssigner found in EDIPartyName");
                }
                derValue2 = derValue2.data.getDerValue();
                this.assigner = derValue2.getAsString();
            }
            if (derValue2.isContextSpecific((byte) 1) && !derValue2.isConstructed()) {
                if (this.party != null) {
                    throw new IOException("Duplicate partyName found in EDIPartyName");
                }
                this.party = derValue2.data.getDerValue().getAsString();
            }
        }
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface != null && generalNameInterface.getType() == 5) {
            throw new UnsupportedOperationException("Narrowing, widening, and matching of names not supported for EDIPartyName");
        }
        return -1;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.assigner != null) {
            DerOutputStream derOutputStream4 = new DerOutputStream();
            derOutputStream4.putPrintableString(this.assigner);
            derOutputStream2.write(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 0), derOutputStream4);
        }
        String str = this.party;
        if (str == null) {
            throw new IOException("Cannot have null partyName");
        }
        derOutputStream3.putPrintableString(str);
        derOutputStream2.write(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 1), derOutputStream3);
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof EDIPartyName)) {
            return false;
        }
        EDIPartyName eDIPartyName = (EDIPartyName) obj;
        String str = eDIPartyName.assigner;
        String str2 = this.assigner;
        if (str2 == null) {
            if (str != null) {
                return false;
            }
        } else if (!str2.equals(str)) {
            return false;
        }
        String str3 = eDIPartyName.party;
        String str4 = this.party;
        return str4 == null ? str3 == null : str4.equals(str3);
    }

    public String getAssignerName() {
        return this.assigner;
    }

    public String getPartyName() {
        return this.party;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 5;
    }

    public int hashCode() {
        if (this.myhash == -1) {
            int hashCode = this.party.hashCode() + 37;
            this.myhash = hashCode;
            String str = this.assigner;
            if (str != null) {
                this.myhash = str.hashCode() + (hashCode * 37);
            }
        }
        return this.myhash;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        throw new UnsupportedOperationException("subtreeDepth() not supported for EDIPartyName");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("EDIPartyName: ");
        sb.append(this.assigner == null ? BuildConfig.FLAVOR : AbstractC0000a.m18n(new StringBuilder("  nameAssigner = "), this.assigner, ","));
        sb.append("  partyName = ");
        sb.append(this.party);
        return sb.toString();
    }

    public EDIPartyName(String str) {
        this.assigner = null;
        this.myhash = -1;
        this.party = str;
    }

    public EDIPartyName(String str, String str2) {
        this.myhash = -1;
        this.assigner = str;
        this.party = str2;
    }
}
