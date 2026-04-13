package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class AuthorityKeyIdentifierExtension extends Extension implements CertAttrSet<String> {
    public static final String AUTH_NAME = "auth_name";
    public static final String IDENT = "x509.info.extensions.AuthorityKeyIdentifier";
    public static final String KEY_ID = "key_id";
    public static final String NAME = "AuthorityKeyIdentifier";
    public static final String SERIAL_NUMBER = "serial_number";
    private static final byte TAG_ID = 0;
    private static final byte TAG_NAMES = 1;
    private static final byte TAG_SERIAL_NUM = 2;
    private KeyIdentifier id;
    private GeneralNames names;
    private SerialNumber serialNum;

    public AuthorityKeyIdentifierExtension(KeyIdentifier keyIdentifier, GeneralNames generalNames, SerialNumber serialNumber) {
        this.id = keyIdentifier;
        this.names = generalNames;
        this.serialNum = serialNumber;
        this.extensionId = PKIXExtensions.AuthorityKey_Id;
        this.critical = false;
        encodeThis();
    }

    private void encodeThis() {
        byte[] byteArray;
        if (this.id == null && this.names == null && this.serialNum == null) {
            byteArray = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            if (this.id != null) {
                DerOutputStream derOutputStream3 = new DerOutputStream();
                this.id.encode(derOutputStream3);
                derOutputStream2.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 0), derOutputStream3);
            }
            try {
                if (this.names != null) {
                    DerOutputStream derOutputStream4 = new DerOutputStream();
                    this.names.encode(derOutputStream4);
                    derOutputStream2.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte) 1), derOutputStream4);
                }
                if (this.serialNum != null) {
                    DerOutputStream derOutputStream5 = new DerOutputStream();
                    this.serialNum.encode(derOutputStream5);
                    derOutputStream2.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT, false, (byte) 2), derOutputStream5);
                }
                derOutputStream.write((byte) 48, derOutputStream2);
                byteArray = derOutputStream.toByteArray();
            } catch (Exception e2) {
                throw new IOException(e2.toString());
            }
        }
        this.extensionValue = byteArray;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (str.equalsIgnoreCase("key_id")) {
            this.id = null;
        } else if (str.equalsIgnoreCase(AUTH_NAME)) {
            this.names = null;
        } else {
            if (!str.equalsIgnoreCase(SERIAL_NUMBER)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
            }
            this.serialNum = null;
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.AuthorityKey_Id;
            this.critical = false;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("key_id")) {
            return this.id;
        }
        if (str.equalsIgnoreCase(AUTH_NAME)) {
            return this.names;
        }
        if (str.equalsIgnoreCase(SERIAL_NUMBER)) {
            return this.serialNum;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement("key_id");
        attributeNameEnumeration.addElement(AUTH_NAME);
        attributeNameEnumeration.addElement(SERIAL_NUMBER);
        return attributeNameEnumeration.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (str.equalsIgnoreCase("key_id")) {
            if (!(obj instanceof KeyIdentifier)) {
                throw new IOException("Attribute value should be of type KeyIdentifier.");
            }
            this.id = (KeyIdentifier) obj;
        } else if (str.equalsIgnoreCase(AUTH_NAME)) {
            if (!(obj instanceof GeneralNames)) {
                throw new IOException("Attribute value should be of type GeneralNames.");
            }
            this.names = (GeneralNames) obj;
        } else {
            if (!str.equalsIgnoreCase(SERIAL_NUMBER)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:AuthorityKeyIdentifier.");
            }
            if (!(obj instanceof SerialNumber)) {
                throw new IOException("Attribute value should be of type SerialNumber.");
            }
            this.serialNum = (SerialNumber) obj;
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        String m18n = AbstractC0000a.m18n(new StringBuilder(), super.toString(), "AuthorityKeyIdentifier [\n");
        if (this.id != null) {
            StringBuilder m20p = AbstractC0000a.m20p(m18n);
            m20p.append(this.id.toString());
            m18n = m20p.toString();
        }
        if (this.names != null) {
            StringBuilder m20p2 = AbstractC0000a.m20p(m18n);
            m20p2.append(this.names.toString());
            m20p2.append("\n");
            m18n = m20p2.toString();
        }
        if (this.serialNum != null) {
            StringBuilder m20p3 = AbstractC0000a.m20p(m18n);
            m20p3.append(this.serialNum.toString());
            m20p3.append("\n");
            m18n = m20p3.toString();
        }
        return AbstractC0000a.m30z(m18n, "]\n");
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a7, code lost:
    
        throw new java.io.IOException("Invalid encoding of AuthorityKeyIdentifierExtension.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AuthorityKeyIdentifierExtension(Boolean bool, Object obj) {
        this.id = null;
        this.names = null;
        this.serialNum = null;
        this.extensionId = PKIXExtensions.AuthorityKey_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        this.extensionValue = bArr;
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for AuthorityKeyIdentifierExtension.");
        }
        while (true) {
            DerInputStream derInputStream = derValue.data;
            if (derInputStream == null || derInputStream.available() == 0) {
                return;
            }
            DerValue derValue2 = derValue.data.getDerValue();
            if (!derValue2.isContextSpecific((byte) 0) || derValue2.isConstructed()) {
                if (derValue2.isContextSpecific((byte) 1) && derValue2.isConstructed()) {
                    if (this.names != null) {
                        throw new IOException("Duplicate GeneralNames in AuthorityKeyIdentifier.");
                    }
                    derValue2.resetTag((byte) 48);
                    this.names = new GeneralNames(derValue2);
                } else {
                    if (!derValue2.isContextSpecific((byte) 2) || derValue2.isConstructed()) {
                        break;
                    }
                    if (this.serialNum != null) {
                        throw new IOException("Duplicate SerialNumber in AuthorityKeyIdentifier.");
                    }
                    derValue2.resetTag((byte) 2);
                    this.serialNum = new SerialNumber(derValue2);
                }
            } else {
                if (this.id != null) {
                    throw new IOException("Duplicate KeyIdentifier in AuthorityKeyIdentifier.");
                }
                derValue2.resetTag((byte) 4);
                this.id = new KeyIdentifier(derValue2);
            }
        }
    }
}
