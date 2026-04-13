package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class GeneralName {
    private GeneralNameInterface name;

    public GeneralName(DerValue derValue) {
        this(derValue, false);
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.name.encode(derOutputStream2);
        int type = this.name.getType();
        boolean z2 = true;
        if (type != 0 && type != 3 && type != 5) {
            if (type == 4) {
                derOutputStream.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) type), derOutputStream2);
                return;
            }
            z2 = false;
        }
        derOutputStream.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, z2, (byte) type), derOutputStream2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GeneralName)) {
            return false;
        }
        try {
            return this.name.constrains(((GeneralName) obj).name) == 0;
        } catch (UnsupportedOperationException unused) {
            return false;
        }
    }

    public GeneralNameInterface getName() {
        return this.name;
    }

    public int getType() {
        return this.name.getType();
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name.toString();
    }

    public GeneralName(DerValue derValue, boolean z2) {
        GeneralNameInterface otherName;
        this.name = null;
        short s2 = (byte) (derValue.tag & 31);
        switch (s2) {
            case 0:
                if (!derValue.isContextSpecific() || !derValue.isConstructed()) {
                    throw new IOException("Invalid encoding of Other-Name");
                }
                derValue.resetTag((byte) 48);
                otherName = new OtherName(derValue);
                break;
                break;
            case 1:
                if (derValue.isContextSpecific() && !derValue.isConstructed()) {
                    derValue.resetTag(DerValue.tag_IA5String);
                    otherName = new RFC822Name(derValue);
                    break;
                } else {
                    throw new IOException("Invalid encoding of RFC822 name");
                }
                break;
            case 2:
                if (derValue.isContextSpecific() && !derValue.isConstructed()) {
                    derValue.resetTag(DerValue.tag_IA5String);
                    otherName = new DNSName(derValue);
                    break;
                } else {
                    throw new IOException("Invalid encoding of DNS name");
                }
                break;
            case 3:
            default:
                throw new IOException(AbstractC0000a.m12h("Unrecognized GeneralName tag, (", s2, ")"));
            case 4:
                if (!derValue.isContextSpecific() || !derValue.isConstructed()) {
                    throw new IOException("Invalid encoding of Directory name");
                }
                otherName = new X500Name(derValue.getData());
                break;
            case 5:
                if (!derValue.isContextSpecific() || !derValue.isConstructed()) {
                    throw new IOException("Invalid encoding of EDI name");
                }
                derValue.resetTag((byte) 48);
                otherName = new EDIPartyName(derValue);
                break;
            case 6:
                if (!derValue.isContextSpecific() || derValue.isConstructed()) {
                    throw new IOException("Invalid encoding of URI");
                }
                derValue.resetTag(DerValue.tag_IA5String);
                this.name = z2 ? URIName.nameConstraint(derValue) : new URIName(derValue);
                return;
            case 7:
                if (derValue.isContextSpecific() && !derValue.isConstructed()) {
                    derValue.resetTag((byte) 4);
                    otherName = new IPAddressName(derValue);
                    break;
                } else {
                    throw new IOException("Invalid encoding of IP address");
                }
                break;
            case 8:
                if (derValue.isContextSpecific() && !derValue.isConstructed()) {
                    derValue.resetTag((byte) 6);
                    otherName = new OIDName(derValue);
                    break;
                } else {
                    throw new IOException("Invalid encoding of OID name");
                }
                break;
        }
        this.name = otherName;
    }

    public GeneralName(GeneralNameInterface generalNameInterface) {
        this.name = null;
        if (generalNameInterface == null) {
            throw new NullPointerException("GeneralName must not be null");
        }
        this.name = generalNameInterface;
    }
}
