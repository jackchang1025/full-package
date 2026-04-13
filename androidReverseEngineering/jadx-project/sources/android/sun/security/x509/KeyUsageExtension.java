package android.sun.security.x509;

import android.sun.security.util.BitArray;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class KeyUsageExtension extends Extension implements CertAttrSet<String> {
    public static final String CRL_SIGN = "crl_sign";
    public static final String DATA_ENCIPHERMENT = "data_encipherment";
    public static final String DECIPHER_ONLY = "decipher_only";
    public static final String DIGITAL_SIGNATURE = "digital_signature";
    public static final String ENCIPHER_ONLY = "encipher_only";
    public static final String IDENT = "x509.info.extensions.KeyUsage";
    public static final String KEY_AGREEMENT = "key_agreement";
    public static final String KEY_CERTSIGN = "key_certsign";
    public static final String KEY_ENCIPHERMENT = "key_encipherment";
    public static final String NAME = "KeyUsage";
    public static final String NON_REPUDIATION = "non_repudiation";
    private boolean[] bitString;

    public KeyUsageExtension() {
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        this.bitString = new boolean[0];
    }

    private void encodeThis() {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putTruncatedUnalignedBitString(new BitArray(this.bitString));
        this.extensionValue = derOutputStream.toByteArray();
    }

    private boolean isSet(int i2) {
        return this.bitString[i2];
    }

    private void set(int i2, boolean z2) {
        boolean[] zArr = this.bitString;
        if (i2 >= zArr.length) {
            boolean[] zArr2 = new boolean[i2 + 1];
            System.arraycopy(zArr, 0, zArr2, 0, zArr.length);
            this.bitString = zArr2;
        }
        this.bitString[i2] = z2;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        int i2;
        if (str.equalsIgnoreCase(DIGITAL_SIGNATURE)) {
            set(0, false);
        } else {
            if (str.equalsIgnoreCase(NON_REPUDIATION)) {
                i2 = 1;
            } else if (str.equalsIgnoreCase(KEY_ENCIPHERMENT)) {
                i2 = 2;
            } else if (str.equalsIgnoreCase(DATA_ENCIPHERMENT)) {
                i2 = 3;
            } else if (str.equalsIgnoreCase(KEY_AGREEMENT)) {
                i2 = 4;
            } else if (str.equalsIgnoreCase(KEY_CERTSIGN)) {
                i2 = 5;
            } else if (str.equalsIgnoreCase(CRL_SIGN)) {
                i2 = 6;
            } else if (str.equalsIgnoreCase(ENCIPHER_ONLY)) {
                i2 = 7;
            } else {
                if (!str.equalsIgnoreCase(DECIPHER_ONLY)) {
                    throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
                }
                i2 = 8;
            }
            set(i2, false);
        }
        encodeThis();
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.KeyUsage_Id;
            this.critical = true;
            encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        int i2;
        if (str.equalsIgnoreCase(DIGITAL_SIGNATURE)) {
            i2 = 0;
        } else if (str.equalsIgnoreCase(NON_REPUDIATION)) {
            i2 = 1;
        } else if (str.equalsIgnoreCase(KEY_ENCIPHERMENT)) {
            i2 = 2;
        } else if (str.equalsIgnoreCase(DATA_ENCIPHERMENT)) {
            i2 = 3;
        } else if (str.equalsIgnoreCase(KEY_AGREEMENT)) {
            i2 = 4;
        } else if (str.equalsIgnoreCase(KEY_CERTSIGN)) {
            i2 = 5;
        } else if (str.equalsIgnoreCase(CRL_SIGN)) {
            i2 = 6;
        } else if (str.equalsIgnoreCase(ENCIPHER_ONLY)) {
            i2 = 7;
        } else {
            if (!str.equalsIgnoreCase(DECIPHER_ONLY)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
            }
            i2 = 8;
        }
        return Boolean.valueOf(isSet(i2));
    }

    public boolean[] getBits() {
        return (boolean[]) this.bitString.clone();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(DIGITAL_SIGNATURE);
        attributeNameEnumeration.addElement(NON_REPUDIATION);
        attributeNameEnumeration.addElement(KEY_ENCIPHERMENT);
        attributeNameEnumeration.addElement(DATA_ENCIPHERMENT);
        attributeNameEnumeration.addElement(KEY_AGREEMENT);
        attributeNameEnumeration.addElement(KEY_CERTSIGN);
        attributeNameEnumeration.addElement(CRL_SIGN);
        attributeNameEnumeration.addElement(ENCIPHER_ONLY);
        attributeNameEnumeration.addElement(DECIPHER_ONLY);
        return attributeNameEnumeration.elements();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return NAME;
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        String m18n = AbstractC0000a.m18n(new StringBuilder(), super.toString(), "KeyUsage [\n");
        try {
            if (isSet(0)) {
                m18n = m18n + "  DigitalSignature\n";
            }
            if (isSet(1)) {
                m18n = m18n + "  Non_repudiation\n";
            }
            if (isSet(2)) {
                m18n = m18n + "  Key_Encipherment\n";
            }
            if (isSet(3)) {
                m18n = m18n + "  Data_Encipherment\n";
            }
            if (isSet(4)) {
                m18n = m18n + "  Key_Agreement\n";
            }
            if (isSet(5)) {
                m18n = m18n + "  Key_CertSign\n";
            }
            if (isSet(6)) {
                m18n = m18n + "  Crl_Sign\n";
            }
            if (isSet(7)) {
                m18n = m18n + "  Encipher_Only\n";
            }
            if (isSet(8)) {
                m18n = m18n + "  Decipher_Only\n";
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
        return AbstractC0000a.m30z(m18n, "]\n");
    }

    public KeyUsageExtension(BitArray bitArray) {
        this.bitString = bitArray.toBooleanArray();
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        encodeThis();
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        int i2;
        if (!(obj instanceof Boolean)) {
            throw new IOException("Attribute must be of type Boolean.");
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (str.equalsIgnoreCase(DIGITAL_SIGNATURE)) {
            i2 = 0;
        } else if (str.equalsIgnoreCase(NON_REPUDIATION)) {
            i2 = 1;
        } else if (str.equalsIgnoreCase(KEY_ENCIPHERMENT)) {
            i2 = 2;
        } else if (str.equalsIgnoreCase(DATA_ENCIPHERMENT)) {
            i2 = 3;
        } else if (str.equalsIgnoreCase(KEY_AGREEMENT)) {
            i2 = 4;
        } else if (str.equalsIgnoreCase(KEY_CERTSIGN)) {
            i2 = 5;
        } else if (str.equalsIgnoreCase(CRL_SIGN)) {
            i2 = 6;
        } else if (str.equalsIgnoreCase(ENCIPHER_ONLY)) {
            i2 = 7;
        } else {
            if (!str.equalsIgnoreCase(DECIPHER_ONLY)) {
                throw new IOException("Attribute name not recognized by CertAttrSet:KeyUsage.");
            }
            i2 = 8;
        }
        set(i2, booleanValue);
        encodeThis();
    }

    public KeyUsageExtension(Boolean bool, Object obj) {
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = bool.booleanValue();
        byte[] bArr = (byte[]) obj;
        if (bArr[0] == 4) {
            this.extensionValue = new DerValue(bArr).getOctetString();
        } else {
            this.extensionValue = bArr;
        }
        this.bitString = new DerValue(this.extensionValue).getUnalignedBitString().toBooleanArray();
    }

    public KeyUsageExtension(byte[] bArr) {
        this.bitString = new BitArray(bArr.length * 8, bArr).toBooleanArray();
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        encodeThis();
    }

    public KeyUsageExtension(boolean[] zArr) {
        this.bitString = zArr;
        this.extensionId = PKIXExtensions.KeyUsage_Id;
        this.critical = true;
        encodeThis();
    }
}
