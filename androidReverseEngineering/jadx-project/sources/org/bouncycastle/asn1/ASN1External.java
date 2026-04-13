package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.util.Objects;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class ASN1External extends ASN1Primitive {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1External.class, 8) { // from class: org.bouncycastle.asn1.ASN1External.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitConstructed(ASN1Sequence aSN1Sequence) {
            return aSN1Sequence.toASN1External();
        }
    };
    ASN1Primitive dataValueDescriptor;
    ASN1ObjectIdentifier directReference;
    int encoding;
    ASN1Primitive externalContent;
    ASN1Integer indirectReference;

    public ASN1External(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, int i2, ASN1Primitive aSN1Primitive2) {
        this.directReference = aSN1ObjectIdentifier;
        this.indirectReference = aSN1Integer;
        this.dataValueDescriptor = aSN1Primitive;
        this.encoding = checkEncoding(i2);
        this.externalContent = checkExternalContent(i2, aSN1Primitive2);
    }

    private static int checkEncoding(int i2) {
        if (i2 < 0 || i2 > 2) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("invalid encoding value: ", i2));
        }
        return i2;
    }

    private static ASN1Primitive checkExternalContent(int i2, ASN1Primitive aSN1Primitive) {
        ASN1UniversalType aSN1UniversalType;
        if (i2 == 1) {
            aSN1UniversalType = ASN1OctetString.TYPE;
        } else {
            if (i2 != 2) {
                return aSN1Primitive;
            }
            aSN1UniversalType = ASN1BitString.TYPE;
        }
        return aSN1UniversalType.checkedCast(aSN1Primitive);
    }

    public static ASN1External getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1External)) {
            return (ASN1External) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1External) {
                return (ASN1External) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (ASN1External) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e2) {
                throw new IllegalArgumentException(AbstractC0000a.m8d(e2, new StringBuilder("failed to construct external from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
    }

    private static ASN1Primitive getObjFromSequence(ASN1Sequence aSN1Sequence, int i2) {
        if (aSN1Sequence.size() > i2) {
            return aSN1Sequence.getObjectAt(i2).toASN1Primitive();
        }
        throw new IllegalArgumentException("too few objects in input sequence");
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (this == aSN1Primitive) {
            return true;
        }
        if (!(aSN1Primitive instanceof ASN1External)) {
            return false;
        }
        ASN1External aSN1External = (ASN1External) aSN1Primitive;
        return Objects.areEqual(this.directReference, aSN1External.directReference) && Objects.areEqual(this.indirectReference, aSN1External.indirectReference) && Objects.areEqual(this.dataValueDescriptor, aSN1External.dataValueDescriptor) && this.encoding == aSN1External.encoding && this.externalContent.equals(aSN1External.externalContent);
    }

    public abstract ASN1Sequence buildSequence();

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeIdentifier(z2, 40);
        buildSequence().encode(aSN1OutputStream, false);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return true;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return buildSequence().encodedLength(z2);
    }

    public ASN1Primitive getDataValueDescriptor() {
        return this.dataValueDescriptor;
    }

    public ASN1ObjectIdentifier getDirectReference() {
        return this.directReference;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public ASN1Primitive getExternalContent() {
        return this.externalContent;
    }

    public ASN1Integer getIndirectReference() {
        return this.indirectReference;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return (((Objects.hashCode(this.directReference) ^ Objects.hashCode(this.indirectReference)) ^ Objects.hashCode(this.dataValueDescriptor)) ^ this.encoding) ^ this.externalContent.hashCode();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return new DERExternal(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return new DLExternal(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    public ASN1External(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, DERTaggedObject dERTaggedObject) {
        this.directReference = aSN1ObjectIdentifier;
        this.indirectReference = aSN1Integer;
        this.dataValueDescriptor = aSN1Primitive;
        this.encoding = checkEncoding(dERTaggedObject.getTagNo());
        this.externalContent = getExternalContent(dERTaggedObject);
    }

    private static ASN1Primitive getExternalContent(ASN1TaggedObject aSN1TaggedObject) {
        int tagClass = aSN1TaggedObject.getTagClass();
        int tagNo = aSN1TaggedObject.getTagNo();
        if (128 != tagClass) {
            throw new IllegalArgumentException("invalid tag: " + ASN1Util.getTagText(tagClass, tagNo));
        }
        if (tagNo == 0) {
            return aSN1TaggedObject.getExplicitBaseObject().toASN1Primitive();
        }
        if (tagNo == 1) {
            return ASN1OctetString.getInstance(aSN1TaggedObject, false);
        }
        if (tagNo == 2) {
            return ASN1BitString.getInstance(aSN1TaggedObject, false);
        }
        throw new IllegalArgumentException("invalid tag: " + ASN1Util.getTagText(tagClass, tagNo));
    }

    public ASN1External(ASN1Sequence aSN1Sequence) {
        int i2 = 0;
        ASN1Primitive objFromSequence = getObjFromSequence(aSN1Sequence, 0);
        if (objFromSequence instanceof ASN1ObjectIdentifier) {
            this.directReference = (ASN1ObjectIdentifier) objFromSequence;
            objFromSequence = getObjFromSequence(aSN1Sequence, 1);
            i2 = 1;
        }
        if (objFromSequence instanceof ASN1Integer) {
            this.indirectReference = (ASN1Integer) objFromSequence;
            i2++;
            objFromSequence = getObjFromSequence(aSN1Sequence, i2);
        }
        if (!(objFromSequence instanceof ASN1TaggedObject)) {
            this.dataValueDescriptor = objFromSequence;
            i2++;
            objFromSequence = getObjFromSequence(aSN1Sequence, i2);
        }
        if (aSN1Sequence.size() != i2 + 1) {
            throw new IllegalArgumentException("input sequence too large");
        }
        if (!(objFromSequence instanceof ASN1TaggedObject)) {
            throw new IllegalArgumentException("No tagged object found in sequence. Structure doesn't seem to be of type External");
        }
        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objFromSequence;
        this.encoding = checkEncoding(aSN1TaggedObject.getTagNo());
        this.externalContent = getExternalContent(aSN1TaggedObject);
    }

    public static ASN1External getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return (ASN1External) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }
}
