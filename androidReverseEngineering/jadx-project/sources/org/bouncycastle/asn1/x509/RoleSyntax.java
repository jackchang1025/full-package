package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import com.guard.wallet.entity.BuildConfig;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class RoleSyntax extends ASN1Object {
    private GeneralNames roleAuthority;
    private GeneralName roleName;

    public RoleSyntax(String str) {
        this(new GeneralName(6, str == null ? BuildConfig.FLAVOR : str));
    }

    public static RoleSyntax getInstance(Object obj) {
        if (obj instanceof RoleSyntax) {
            return (RoleSyntax) obj;
        }
        if (obj != null) {
            return new RoleSyntax(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public GeneralNames getRoleAuthority() {
        return this.roleAuthority;
    }

    public String[] getRoleAuthorityAsString() {
        GeneralNames generalNames = this.roleAuthority;
        if (generalNames == null) {
            return new String[0];
        }
        GeneralName[] names = generalNames.getNames();
        String[] strArr = new String[names.length];
        for (int i2 = 0; i2 < names.length; i2++) {
            ASN1Encodable name = names[i2].getName();
            if (name instanceof ASN1String) {
                strArr[i2] = ((ASN1String) name).getString();
            } else {
                strArr[i2] = name.toString();
            }
        }
        return strArr;
    }

    public GeneralName getRoleName() {
        return this.roleName;
    }

    public String getRoleNameAsString() {
        return ((ASN1String) this.roleName.getName()).getString();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        GeneralNames generalNames = this.roleAuthority;
        if (generalNames != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, (ASN1Encodable) generalNames));
        }
        aSN1EncodableVector.add(new DERTaggedObject(true, 1, (ASN1Encodable) this.roleName));
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("Name: " + getRoleNameAsString() + " - Auth: ");
        GeneralNames generalNames = this.roleAuthority;
        if (generalNames == null || generalNames.getNames().length == 0) {
            stringBuffer.append("N/A");
        } else {
            String[] roleAuthorityAsString = getRoleAuthorityAsString();
            stringBuffer.append('[');
            stringBuffer.append(roleAuthorityAsString[0]);
            for (int i2 = 1; i2 < roleAuthorityAsString.length; i2++) {
                stringBuffer.append(", ");
                stringBuffer.append(roleAuthorityAsString[i2]);
            }
            stringBuffer.append(']');
        }
        return stringBuffer.toString();
    }

    private RoleSyntax(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            throw new IllegalArgumentException(AbstractC0413b.m1012f(aSN1Sequence, new StringBuilder("Bad sequence size: ")));
        }
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i2));
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.roleAuthority = GeneralNames.getInstance(aSN1TaggedObject, false);
            } else {
                if (tagNo != 1) {
                    throw new IllegalArgumentException("Unknown tag in RoleSyntax");
                }
                this.roleName = GeneralName.getInstance(aSN1TaggedObject, true);
            }
        }
    }

    public RoleSyntax(GeneralName generalName) {
        this(null, generalName);
    }

    public RoleSyntax(GeneralNames generalNames, GeneralName generalName) {
        if (generalName == null || generalName.getTagNo() != 6 || ((ASN1String) generalName.getName()).getString().equals(BuildConfig.FLAVOR)) {
            throw new IllegalArgumentException("the role name MUST be non empty and MUST use the URI option of GeneralName");
        }
        this.roleAuthority = generalNames;
        this.roleName = generalName;
    }
}
