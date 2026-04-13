package android.sun.security.x509;

import android.sun.security.pkcs.PKCS9Attribute;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class NameConstraintsExtension extends Extension implements CertAttrSet<String>, Cloneable {
    public static final String EXCLUDED_SUBTREES = "excluded_subtrees";
    public static final String IDENT = "x509.info.extensions.NameConstraints";
    public static final String NAME = "NameConstraints";
    public static final String PERMITTED_SUBTREES = "permitted_subtrees";
    private static final byte TAG_EXCLUDED = 1;
    private static final byte TAG_PERMITTED = 0;
    private GeneralSubtrees excluded;
    private GeneralSubtrees permitted;

    public NameConstraintsExtension(GeneralSubtrees generalSubtrees, GeneralSubtrees generalSubtrees2) {
        this.permitted = generalSubtrees;
        this.excluded = generalSubtrees2;
        this.extensionId = PKIXExtensions.NameConstraints_Id;
        this.critical = true;
        encodeThis();
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.permitted == null && this.excluded == null) {
            byteArray = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            if (this.permitted != null) {
                DerOutputStream derOutputStream3 = new DerOutputStream();
                this.permitted.encode(derOutputStream3);
                derOutputStream2.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), derOutputStream3);
            }
            if (this.excluded != null) {
                DerOutputStream derOutputStream4 = new DerOutputStream();
                this.excluded.encode(derOutputStream4);
                derOutputStream2.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 1), derOutputStream4);
            }
            derOutputStream.write((byte) 48, derOutputStream2);
            byteArray = derOutputStream.toByteArray();
        }
        this.extensionValue = byteArray;
    }

    @NonNull
    public Object clone() {
        try {
            NameConstraintsExtension nameConstraintsExtension = (NameConstraintsExtension) super.clone();
            GeneralSubtrees generalSubtrees = this.permitted;
            if (generalSubtrees != null) {
                nameConstraintsExtension.permitted = (GeneralSubtrees) generalSubtrees.clone();
            }
            GeneralSubtrees generalSubtrees2 = this.excluded;
            if (generalSubtrees2 != null) {
                nameConstraintsExtension.excluded = (GeneralSubtrees) generalSubtrees2.clone();
            }
            return nameConstraintsExtension;
        } catch (CloneNotSupportedException unused) {
            throw new RuntimeException("CloneNotSupportedException while cloning NameConstraintsException. This should never happen.");
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (str.equalsIgnoreCase(PERMITTED_SUBTREES)) {
            this.permitted = null;
        } else {
            if (!str.equalsIgnoreCase(EXCLUDED_SUBTREES)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
            }
            this.excluded = null;
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.NameConstraints_Id;
            this.critical = true;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase(PERMITTED_SUBTREES)) {
            return this.permitted;
        }
        if (str.equalsIgnoreCase(EXCLUDED_SUBTREES)) {
            return this.excluded;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(PERMITTED_SUBTREES);
        attributeNameEnumeration.addElement(EXCLUDED_SUBTREES);
        return attributeNameEnumeration.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    public void merge(NameConstraintsExtension nameConstraintsExtension) {
        GeneralSubtrees intersect;
        if (nameConstraintsExtension == null) {
            return;
        }
        GeneralSubtrees generalSubtrees = (GeneralSubtrees) nameConstraintsExtension.get(EXCLUDED_SUBTREES);
        GeneralSubtrees generalSubtrees2 = this.excluded;
        if (generalSubtrees2 == null) {
            this.excluded = generalSubtrees != null ? (GeneralSubtrees) generalSubtrees.clone() : null;
        } else if (generalSubtrees != null) {
            generalSubtrees2.union(generalSubtrees);
        }
        GeneralSubtrees generalSubtrees3 = (GeneralSubtrees) nameConstraintsExtension.get(PERMITTED_SUBTREES);
        GeneralSubtrees generalSubtrees4 = this.permitted;
        if (generalSubtrees4 == null) {
            this.permitted = generalSubtrees3 != null ? (GeneralSubtrees) generalSubtrees3.clone() : null;
        } else if (generalSubtrees3 != null && (intersect = generalSubtrees4.intersect(generalSubtrees3)) != null) {
            GeneralSubtrees generalSubtrees5 = this.excluded;
            if (generalSubtrees5 != null) {
                generalSubtrees5.union(intersect);
            } else {
                this.excluded = (GeneralSubtrees) intersect.clone();
            }
        }
        GeneralSubtrees generalSubtrees6 = this.permitted;
        if (generalSubtrees6 != null) {
            generalSubtrees6.reduce(this.excluded);
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (str.equalsIgnoreCase(PERMITTED_SUBTREES)) {
            if (!(obj instanceof GeneralSubtrees)) {
                throw new IOException("Attribute value should be of type GeneralSubtrees.");
            }
            this.permitted = (GeneralSubtrees) obj;
        } else {
            if (!str.equalsIgnoreCase(EXCLUDED_SUBTREES)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:NameConstraintsExtension.");
            }
            if (!(obj instanceof GeneralSubtrees)) {
                throw new IOException("Attribute value should be of type GeneralSubtrees.");
            }
            this.excluded = (GeneralSubtrees) obj;
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    @NonNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("NameConstraints: [");
        GeneralSubtrees generalSubtrees = this.permitted;
        String str2 = BuildConfig.FLAVOR;
        if (generalSubtrees == null) {
            str = BuildConfig.FLAVOR;
        } else {
            str = "\n    Permitted:" + this.permitted.toString();
        }
        sb.append(str);
        if (this.excluded != null) {
            str2 = "\n    Excluded:" + this.excluded.toString();
        }
        return AbstractC0000a.m18n(sb, str2, "   ]\n");
    }

    public boolean verify(GeneralNameInterface generalNameInterface) {
        GeneralName name;
        GeneralNameInterface name2;
        GeneralName name3;
        GeneralNameInterface name4;
        int constrains;
        if (generalNameInterface == null) {
            throw new IOException("name is null");
        }
        GeneralSubtrees generalSubtrees = this.excluded;
        if (generalSubtrees != null && generalSubtrees.size() > 0) {
            for (int i2 = 0; i2 < this.excluded.size(); i2++) {
                GeneralSubtree generalSubtree = this.excluded.get(i2);
                if (generalSubtree != null && (name3 = generalSubtree.getName()) != null && (name4 = name3.getName()) != null && ((constrains = name4.constrains(generalNameInterface)) == 0 || constrains == 1)) {
                    return false;
                }
            }
        }
        GeneralSubtrees generalSubtrees2 = this.permitted;
        if (generalSubtrees2 == null || generalSubtrees2.size() <= 0) {
            return true;
        }
        boolean z2 = false;
        for (int i3 = 0; i3 < this.permitted.size(); i3++) {
            GeneralSubtree generalSubtree2 = this.permitted.get(i3);
            if (generalSubtree2 != null && (name = generalSubtree2.getName()) != null && (name2 = name.getName()) != null) {
                int constrains2 = name2.constrains(generalNameInterface);
                if (constrains2 == 0 || constrains2 == 1) {
                    return true;
                }
                if (constrains2 == 2 || constrains2 == 3) {
                    z2 = true;
                }
            }
        }
        return !z2;
    }

    public boolean verifyRFC822SpecialCase(X500Name x500Name) {
        String valueString;
        for (AVA ava : x500Name.allAvas()) {
            if (ava.getObjectIdentifier().equals(PKCS9Attribute.EMAIL_ADDRESS_OID) && (valueString = ava.getValueString()) != null) {
                try {
                    if (!verify(new RFC822Name(valueString))) {
                        return false;
                    }
                } catch (IOException unused) {
                    continue;
                }
            }
        }
        return true;
    }

    public NameConstraintsExtension(Boolean bool, Object obj) {
        this.permitted = null;
        this.excluded = null;
        this.extensionId = PKIXExtensions.NameConstraints_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for NameConstraintsExtension.");
        }
        if (derValue.data == null) {
            return;
        }
        while (derValue.data.available() != 0) {
            DerValue derValue2 = derValue.data.getDerValue();
            if (derValue2.isContextSpecific((byte) 0) && derValue2.isConstructed()) {
                if (this.permitted != null) {
                    throw new IOException("Duplicate permitted GeneralSubtrees in NameConstraintsExtension.");
                }
                derValue2.resetTag((byte) 48);
                this.permitted = new GeneralSubtrees(derValue2);
            } else {
                if (!derValue2.isContextSpecific((byte) 1) || !derValue2.isConstructed()) {
                    throw new IOException("Invalid encoding of NameConstraintsExtension.");
                }
                if (this.excluded != null) {
                    throw new IOException("Duplicate excluded GeneralSubtrees in NameConstraintsExtension.");
                }
                derValue2.resetTag((byte) 48);
                this.excluded = new GeneralSubtrees(derValue2);
            }
        }
    }
}
