package android.sun.security.pkcs;

import android.sun.security.util.DerEncoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS9Attributes {
    private final Hashtable<ObjectIdentifier, PKCS9Attribute> attributes;
    private final byte[] derEncoding;
    private boolean ignoreUnsupportedAttributes;
    private final Hashtable<ObjectIdentifier, ObjectIdentifier> permittedAttributes;

    public PKCS9Attributes(DerInputStream derInputStream) {
        this(derInputStream, false);
    }

    public static DerEncoder[] castToDerEncoder(Object[] objArr) {
        int length = objArr.length;
        DerEncoder[] derEncoderArr = new DerEncoder[length];
        for (int i2 = 0; i2 < length; i2++) {
            derEncoderArr[i2] = (DerEncoder) objArr[i2];
        }
        return derEncoderArr;
    }

    private byte[] decode(DerInputStream derInputStream) {
        PKCS9Attribute pKCS9Attribute;
        ObjectIdentifier oid;
        byte[] byteArray = derInputStream.getDerValue().toByteArray();
        byteArray[0] = 49;
        boolean z2 = true;
        for (DerValue derValue : new DerInputStream(byteArray).getSet(3, true)) {
            try {
                pKCS9Attribute = new PKCS9Attribute(derValue);
                oid = pKCS9Attribute.getOID();
            } catch (ParsingException e2) {
                if (!this.ignoreUnsupportedAttributes) {
                    throw e2;
                }
                z2 = false;
            }
            if (this.attributes.get(oid) != null) {
                throw new IOException("Duplicate PKCS9 attribute: " + oid);
            }
            Hashtable<ObjectIdentifier, ObjectIdentifier> hashtable = this.permittedAttributes;
            if (hashtable != null && !hashtable.containsKey(oid)) {
                throw new IOException("Attribute " + oid + " not permitted in this attribute set");
            }
            this.attributes.put(oid, pKCS9Attribute);
        }
        return z2 ? byteArray : generateDerEncoding();
    }

    private byte[] generateDerEncoding() {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putOrderedSetOf((byte) 49, castToDerEncoder(this.attributes.values().toArray()));
        return derOutputStream.toByteArray();
    }

    public void encode(byte b, OutputStream outputStream) {
        outputStream.write(b);
        byte[] bArr = this.derEncoding;
        outputStream.write(bArr, 1, bArr.length - 1);
    }

    public PKCS9Attribute getAttribute(ObjectIdentifier objectIdentifier) {
        return this.attributes.get(objectIdentifier);
    }

    public Object getAttributeValue(ObjectIdentifier objectIdentifier) {
        try {
            return getAttribute(objectIdentifier).getValue();
        } catch (NullPointerException unused) {
            throw new IOException("No value found for attribute " + objectIdentifier);
        }
    }

    public PKCS9Attribute[] getAttributes() {
        int size = this.attributes.size();
        PKCS9Attribute[] pKCS9AttributeArr = new PKCS9Attribute[size];
        int i2 = 0;
        int i3 = 1;
        while (true) {
            ObjectIdentifier[] objectIdentifierArr = PKCS9Attribute.PKCS9_OIDS;
            if (i3 >= objectIdentifierArr.length || i2 >= size) {
                break;
            }
            PKCS9Attribute attribute = getAttribute(objectIdentifierArr[i3]);
            pKCS9AttributeArr[i2] = attribute;
            if (attribute != null) {
                i2++;
            }
            i3++;
        }
        return pKCS9AttributeArr;
    }

    public byte[] getDerEncoding() {
        return (byte[]) this.derEncoding.clone();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("PKCS9 Attributes: [\n\t");
        int i2 = 1;
        boolean z2 = true;
        while (true) {
            ObjectIdentifier[] objectIdentifierArr = PKCS9Attribute.PKCS9_OIDS;
            if (i2 >= objectIdentifierArr.length) {
                stringBuffer.append("\n\t] (end PKCS9 Attributes)");
                return stringBuffer.toString();
            }
            PKCS9Attribute attribute = getAttribute(objectIdentifierArr[i2]);
            if (attribute != null) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(";\n\t");
                }
                stringBuffer.append(attribute.toString());
            }
            i2++;
        }
    }

    public PKCS9Attributes(DerInputStream derInputStream, boolean z2) {
        this.attributes = new Hashtable<>(3);
        this.ignoreUnsupportedAttributes = z2;
        this.derEncoding = decode(derInputStream);
        this.permittedAttributes = null;
    }

    public PKCS9Attribute getAttribute(String str) {
        return this.attributes.get(PKCS9Attribute.getOID(str));
    }

    public Object getAttributeValue(String str) {
        ObjectIdentifier oid = PKCS9Attribute.getOID(str);
        if (oid != null) {
            return getAttributeValue(oid);
        }
        throw new IOException(AbstractC0000a.m16l("Attribute name ", str, " not recognized or not supported."));
    }

    public PKCS9Attributes(PKCS9Attribute[] pKCS9AttributeArr) {
        this.attributes = new Hashtable<>(3);
        this.ignoreUnsupportedAttributes = false;
        for (int i2 = 0; i2 < pKCS9AttributeArr.length; i2++) {
            ObjectIdentifier oid = pKCS9AttributeArr[i2].getOID();
            if (this.attributes.containsKey(oid)) {
                throw new IllegalArgumentException("PKCSAttribute " + pKCS9AttributeArr[i2].getOID() + " duplicated while constructing PKCS9Attributes.");
            }
            this.attributes.put(oid, pKCS9AttributeArr[i2]);
        }
        this.derEncoding = generateDerEncoding();
        this.permittedAttributes = null;
    }

    public PKCS9Attributes(ObjectIdentifier[] objectIdentifierArr, DerInputStream derInputStream) {
        this.attributes = new Hashtable<>(3);
        this.ignoreUnsupportedAttributes = false;
        if (objectIdentifierArr != null) {
            this.permittedAttributes = new Hashtable<>(objectIdentifierArr.length);
            for (ObjectIdentifier objectIdentifier : objectIdentifierArr) {
                this.permittedAttributes.put(objectIdentifier, objectIdentifier);
            }
        } else {
            this.permittedAttributes = null;
        }
        this.derEncoding = decode(derInputStream);
    }
}
