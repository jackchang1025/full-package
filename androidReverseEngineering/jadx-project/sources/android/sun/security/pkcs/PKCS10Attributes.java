package android.sun.security.pkcs;

import android.sun.security.util.DerEncoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

/* loaded from: classes.dex */
public class PKCS10Attributes implements DerEncoder {
    private Hashtable<String, PKCS10Attribute> map = new Hashtable<>(3);

    public PKCS10Attributes() {
    }

    public void deleteAttribute(String str) {
        this.map.remove(str);
    }

    @Override // android.sun.security.util.DerEncoder
    public void derEncode(OutputStream outputStream) {
        PKCS10Attribute[] pKCS10AttributeArr = (PKCS10Attribute[]) this.map.values().toArray(new PKCS10Attribute[this.map.size()]);
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putOrderedSetOf(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 0), pKCS10AttributeArr);
        outputStream.write(derOutputStream.toByteArray());
    }

    public void encode(OutputStream outputStream) {
        derEncode(outputStream);
    }

    public boolean equals(Object obj) {
        PKCS10Attribute pKCS10Attribute;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PKCS10Attributes)) {
            return false;
        }
        Collection<PKCS10Attribute> attributes = ((PKCS10Attributes) obj).getAttributes();
        PKCS10Attribute[] pKCS10AttributeArr = (PKCS10Attribute[]) attributes.toArray(new PKCS10Attribute[attributes.size()]);
        if (pKCS10AttributeArr.length != this.map.size()) {
            return false;
        }
        for (PKCS10Attribute pKCS10Attribute2 : pKCS10AttributeArr) {
            String objectIdentifier = pKCS10Attribute2.getAttributeId().toString();
            if (objectIdentifier == null || (pKCS10Attribute = this.map.get(objectIdentifier)) == null || !pKCS10Attribute.equals(pKCS10Attribute2)) {
                return false;
            }
        }
        return true;
    }

    public Object getAttribute(String str) {
        return this.map.get(str);
    }

    public Collection<PKCS10Attribute> getAttributes() {
        return Collections.unmodifiableCollection(this.map.values());
    }

    public Enumeration<PKCS10Attribute> getElements() {
        return this.map.elements();
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public void setAttribute(String str, Object obj) {
        if (obj instanceof PKCS10Attribute) {
            this.map.put(str, (PKCS10Attribute) obj);
        }
    }

    public String toString() {
        return this.map.size() + "\n" + this.map.toString();
    }

    public PKCS10Attributes(DerInputStream derInputStream) {
        DerValue[] set = derInputStream.getSet(3, true);
        if (set == null) {
            throw new IOException("Illegal encoding of attributes");
        }
        for (DerValue derValue : set) {
            PKCS10Attribute pKCS10Attribute = new PKCS10Attribute(derValue);
            this.map.put(pKCS10Attribute.getAttributeId().toString(), pKCS10Attribute);
        }
    }

    public PKCS10Attributes(PKCS10Attribute[] pKCS10AttributeArr) {
        for (int i2 = 0; i2 < pKCS10AttributeArr.length; i2++) {
            this.map.put(pKCS10AttributeArr[i2].getAttributeId().toString(), pKCS10AttributeArr[i2]);
        }
    }
}
