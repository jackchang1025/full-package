package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes.dex */
public class Extensions extends ASN1Object {
    private Hashtable extensions;
    private Vector ordering;

    private Extensions(ASN1Sequence aSN1Sequence) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            Extension extension = Extension.getInstance(objects.nextElement());
            if (this.extensions.containsKey(extension.getExtnId())) {
                throw new IllegalArgumentException("repeated extension found: " + extension.getExtnId());
            }
            this.extensions.put(extension.getExtnId(), extension);
            this.ordering.addElement(extension.getExtnId());
        }
    }

    public static Extensions getInstance(Object obj) {
        if (obj instanceof Extensions) {
            return (Extensions) obj;
        }
        if (obj != null) {
            return new Extensions(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private ASN1ObjectIdentifier[] toOidArray(Vector vector) {
        int size = vector.size();
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[size];
        for (int i2 = 0; i2 != size; i2++) {
            aSN1ObjectIdentifierArr[i2] = (ASN1ObjectIdentifier) vector.elementAt(i2);
        }
        return aSN1ObjectIdentifierArr;
    }

    public boolean equivalent(Extensions extensions) {
        if (this.extensions.size() != extensions.extensions.size()) {
            return false;
        }
        Enumeration keys = this.extensions.keys();
        while (keys.hasMoreElements()) {
            Object nextElement = keys.nextElement();
            if (!this.extensions.get(nextElement).equals(extensions.extensions.get(nextElement))) {
                return false;
            }
        }
        return true;
    }

    public ASN1ObjectIdentifier[] getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    public Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (Extension) this.extensions.get(aSN1ObjectIdentifier);
    }

    public ASN1ObjectIdentifier[] getExtensionOIDs() {
        return toOidArray(this.ordering);
    }

    public ASN1Encodable getExtensionParsedValue(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extension extension = getExtension(aSN1ObjectIdentifier);
        if (extension != null) {
            return extension.getParsedValue();
        }
        return null;
    }

    public ASN1ObjectIdentifier[] getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    public Enumeration oids() {
        return this.ordering.elements();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(this.ordering.size());
        Enumeration elements = this.ordering.elements();
        while (elements.hasMoreElements()) {
            aSN1EncodableVector.add((Extension) this.extensions.get((ASN1ObjectIdentifier) elements.nextElement()));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public Extensions(Extension extension) {
        this.extensions = new Hashtable();
        Vector vector = new Vector();
        this.ordering = vector;
        vector.addElement(extension.getExtnId());
        this.extensions.put(extension.getExtnId(), extension);
    }

    public static Extension getExtension(Extensions extensions, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (extensions == null) {
            return null;
        }
        return extensions.getExtension(aSN1ObjectIdentifier);
    }

    private ASN1ObjectIdentifier[] getExtensionOIDs(boolean z2) {
        Vector vector = new Vector();
        for (int i2 = 0; i2 != this.ordering.size(); i2++) {
            Object elementAt = this.ordering.elementAt(i2);
            if (((Extension) this.extensions.get(elementAt)).isCritical() == z2) {
                vector.addElement(elementAt);
            }
        }
        return toOidArray(vector);
    }

    public static ASN1Encodable getExtensionParsedValue(Extensions extensions, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (extensions == null) {
            return null;
        }
        return extensions.getExtensionParsedValue(aSN1ObjectIdentifier);
    }

    public static Extensions getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public Extensions(Extension[] extensionArr) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        for (int i2 = 0; i2 != extensionArr.length; i2++) {
            Extension extension = extensionArr[i2];
            this.ordering.addElement(extension.getExtnId());
            this.extensions.put(extension.getExtnId(), extension);
        }
    }
}
