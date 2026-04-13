package android.sun.security.pkcs;

import android.sun.security.util.DerEncoder;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class PKCS10Attribute implements DerEncoder {
    protected ObjectIdentifier attributeId;
    protected Object attributeValue;

    public PKCS10Attribute(PKCS9Attribute pKCS9Attribute) {
        this.attributeId = null;
        this.attributeValue = null;
        this.attributeId = pKCS9Attribute.getOID();
        this.attributeValue = pKCS9Attribute.getValue();
    }

    @Override // android.sun.security.util.DerEncoder
    public void derEncode(OutputStream outputStream) {
        new PKCS9Attribute(this.attributeId, this.attributeValue).derEncode(outputStream);
    }

    public ObjectIdentifier getAttributeId() {
        return this.attributeId;
    }

    public Object getAttributeValue() {
        return this.attributeValue;
    }

    public String toString() {
        return this.attributeValue.toString();
    }

    public PKCS10Attribute(DerValue derValue) {
        this.attributeId = null;
        this.attributeValue = null;
        PKCS9Attribute pKCS9Attribute = new PKCS9Attribute(derValue);
        this.attributeId = pKCS9Attribute.getOID();
        this.attributeValue = pKCS9Attribute.getValue();
    }

    public PKCS10Attribute(ObjectIdentifier objectIdentifier, Object obj) {
        this.attributeId = objectIdentifier;
        this.attributeValue = obj;
    }
}
