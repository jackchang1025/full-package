package android.sun.security.x509;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509CertInfo implements CertAttrSet<String> {
    public static final String ALGORITHM_ID = "algorithmID";
    private static final int ATTR_ALGORITHM = 3;
    private static final int ATTR_EXTENSIONS = 10;
    private static final int ATTR_ISSUER = 4;
    private static final int ATTR_ISSUER_ID = 8;
    private static final int ATTR_KEY = 7;
    private static final int ATTR_SERIAL = 2;
    private static final int ATTR_SUBJECT = 6;
    private static final int ATTR_SUBJECT_ID = 9;
    private static final int ATTR_VALIDITY = 5;
    private static final int ATTR_VERSION = 1;
    public static final String EXTENSIONS = "extensions";
    public static final String IDENT = "x509.info";
    public static final String ISSUER = "issuer";
    public static final String ISSUER_ID = "issuerID";
    public static final String KEY = "key";
    public static final String NAME = "info";
    public static final String SERIAL_NUMBER = "serialNumber";
    public static final String SUBJECT = "subject";
    public static final String SUBJECT_ID = "subjectID";
    public static final String VALIDITY = "validity";
    public static final String VERSION = "version";
    private static final Map<String, Integer> map;
    protected CertificateVersion version = new CertificateVersion();
    protected CertificateSerialNumber serialNum = null;
    protected CertificateAlgorithmId algId = null;
    protected CertificateIssuerName issuer = null;
    protected CertificateValidity interval = null;
    protected CertificateSubjectName subject = null;
    protected CertificateX509Key pubKey = null;
    protected CertificateIssuerUniqueIdentity issuerUniqueId = null;
    protected CertificateSubjectUniqueIdentity subjectUniqueId = null;
    protected CertificateExtensions extensions = null;
    private byte[] rawCertInfo = null;

    static {
        HashMap hashMap = new HashMap();
        map = hashMap;
        hashMap.put("version", 1);
        hashMap.put("serialNumber", 2);
        hashMap.put("algorithmID", 3);
        hashMap.put("issuer", 4);
        hashMap.put("validity", 5);
        hashMap.put("subject", 6);
        hashMap.put("key", 7);
        hashMap.put("issuerID", 8);
        hashMap.put("subjectID", 9);
        hashMap.put("extensions", 10);
    }

    public X509CertInfo() {
    }

    private int attributeMap(String str) {
        Integer num = map.get(str);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    private void emit(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.version.encode(derOutputStream2);
        this.serialNum.encode(derOutputStream2);
        this.algId.encode(derOutputStream2);
        if (this.version.compare(0) == 0 && this.issuer.toString() == null) {
            throw new CertificateParsingException("Null issuer DN not allowed in v1 certificate");
        }
        this.issuer.encode(derOutputStream2);
        this.interval.encode(derOutputStream2);
        if (this.version.compare(0) == 0 && this.subject.toString() == null) {
            throw new CertificateParsingException("Null subject DN not allowed in v1 certificate");
        }
        this.subject.encode(derOutputStream2);
        this.pubKey.encode(derOutputStream2);
        CertificateIssuerUniqueIdentity certificateIssuerUniqueIdentity = this.issuerUniqueId;
        if (certificateIssuerUniqueIdentity != null) {
            certificateIssuerUniqueIdentity.encode(derOutputStream2);
        }
        CertificateSubjectUniqueIdentity certificateSubjectUniqueIdentity = this.subjectUniqueId;
        if (certificateSubjectUniqueIdentity != null) {
            certificateSubjectUniqueIdentity.encode(derOutputStream2);
        }
        CertificateExtensions certificateExtensions = this.extensions;
        if (certificateExtensions != null) {
            certificateExtensions.encode(derOutputStream2);
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    private void parse(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new CertificateParsingException("signed fields invalid");
        }
        this.rawCertInfo = derValue.toByteArray();
        DerInputStream derInputStream = derValue.data;
        DerValue derValue2 = derInputStream.getDerValue();
        if (derValue2.isContextSpecific((byte) 0)) {
            this.version = new CertificateVersion(derValue2);
            derValue2 = derInputStream.getDerValue();
        }
        this.serialNum = new CertificateSerialNumber(derValue2);
        this.algId = new CertificateAlgorithmId(derInputStream);
        CertificateIssuerName certificateIssuerName = new CertificateIssuerName(derInputStream);
        this.issuer = certificateIssuerName;
        if (((X500Name) certificateIssuerName.get("dname")).isEmpty()) {
            throw new CertificateParsingException("Empty issuer DN not allowed in X509Certificates");
        }
        this.interval = new CertificateValidity(derInputStream);
        CertificateSubjectName certificateSubjectName = new CertificateSubjectName(derInputStream);
        this.subject = certificateSubjectName;
        X500Name x500Name = (X500Name) certificateSubjectName.get("dname");
        if (this.version.compare(0) == 0 && x500Name.isEmpty()) {
            throw new CertificateParsingException("Empty subject DN not allowed in v1 certificate");
        }
        this.pubKey = new CertificateX509Key(derInputStream);
        if (derInputStream.available() != 0) {
            if (this.version.compare(0) == 0) {
                throw new CertificateParsingException("no more data allowed for version 1 certificate");
            }
            DerValue derValue3 = derInputStream.getDerValue();
            if (derValue3.isContextSpecific((byte) 1)) {
                this.issuerUniqueId = new CertificateIssuerUniqueIdentity(derValue3);
                if (derInputStream.available() == 0) {
                    return;
                } else {
                    derValue3 = derInputStream.getDerValue();
                }
            }
            if (derValue3.isContextSpecific((byte) 2)) {
                this.subjectUniqueId = new CertificateSubjectUniqueIdentity(derValue3);
                if (derInputStream.available() == 0) {
                    return;
                } else {
                    derValue3 = derInputStream.getDerValue();
                }
            }
            if (this.version.compare(2) != 0) {
                throw new CertificateParsingException("Extensions not allowed in v2 certificate");
            }
            if (derValue3.isConstructed() && derValue3.isContextSpecific((byte) 3)) {
                this.extensions = new CertificateExtensions(derValue3.data);
            }
            verifyCert(this.subject, this.extensions);
        }
    }

    private void setAlgorithmId(Object obj) {
        if (!(obj instanceof CertificateAlgorithmId)) {
            throw new CertificateException("AlgorithmId class type invalid.");
        }
        this.algId = (CertificateAlgorithmId) obj;
    }

    private void setExtensions(Object obj) {
        if (this.version.compare(2) < 0) {
            throw new CertificateException("Invalid version");
        }
        if (!(obj instanceof CertificateExtensions)) {
            throw new CertificateException("Extensions class type invalid.");
        }
        this.extensions = (CertificateExtensions) obj;
    }

    private void setIssuer(Object obj) {
        if (!(obj instanceof CertificateIssuerName)) {
            throw new CertificateException("Issuer class type invalid.");
        }
        this.issuer = (CertificateIssuerName) obj;
    }

    private void setIssuerUniqueId(Object obj) {
        if (this.version.compare(1) < 0) {
            throw new CertificateException("Invalid version");
        }
        if (!(obj instanceof CertificateIssuerUniqueIdentity)) {
            throw new CertificateException("IssuerUniqueId class type invalid.");
        }
        this.issuerUniqueId = (CertificateIssuerUniqueIdentity) obj;
    }

    private void setKey(Object obj) {
        if (!(obj instanceof CertificateX509Key)) {
            throw new CertificateException("Key class type invalid.");
        }
        this.pubKey = (CertificateX509Key) obj;
    }

    private void setSerialNumber(Object obj) {
        if (!(obj instanceof CertificateSerialNumber)) {
            throw new CertificateException("SerialNumber class type invalid.");
        }
        this.serialNum = (CertificateSerialNumber) obj;
    }

    private void setSubject(Object obj) {
        if (!(obj instanceof CertificateSubjectName)) {
            throw new CertificateException("Subject class type invalid.");
        }
        this.subject = (CertificateSubjectName) obj;
    }

    private void setSubjectUniqueId(Object obj) {
        if (this.version.compare(1) < 0) {
            throw new CertificateException("Invalid version");
        }
        if (!(obj instanceof CertificateSubjectUniqueIdentity)) {
            throw new CertificateException("SubjectUniqueId class type invalid.");
        }
        this.subjectUniqueId = (CertificateSubjectUniqueIdentity) obj;
    }

    private void setValidity(Object obj) {
        if (!(obj instanceof CertificateValidity)) {
            throw new CertificateException("CertificateValidity class type invalid.");
        }
        this.interval = (CertificateValidity) obj;
    }

    private void setVersion(Object obj) {
        if (!(obj instanceof CertificateVersion)) {
            throw new CertificateException("Version class type invalid.");
        }
        this.version = (CertificateVersion) obj;
    }

    private void verifyCert(CertificateSubjectName certificateSubjectName, CertificateExtensions certificateExtensions) {
        if (((X500Name) certificateSubjectName.get("dname")).isEmpty()) {
            if (certificateExtensions == null) {
                throw new CertificateParsingException("X.509 Certificate is incomplete: subject field is empty, and certificate has no extensions");
            }
            try {
                SubjectAlternativeNameExtension subjectAlternativeNameExtension = (SubjectAlternativeNameExtension) certificateExtensions.get(SubjectAlternativeNameExtension.NAME);
                GeneralNames generalNames = (GeneralNames) subjectAlternativeNameExtension.get(SubjectAlternativeNameExtension.SUBJECT_NAME);
                if (generalNames == null || generalNames.isEmpty()) {
                    throw new CertificateParsingException("X.509 Certificate is incomplete: subject field is empty, and SubjectAlternativeName extension is empty");
                }
                if (!subjectAlternativeNameExtension.isCritical()) {
                    throw new CertificateParsingException("X.509 Certificate is incomplete: SubjectAlternativeName extension MUST be marked critical when subject field is empty");
                }
            } catch (IOException unused) {
                throw new CertificateParsingException("X.509 Certificate is incomplete: subject field is empty, and SubjectAlternativeName extension is absent");
            }
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        X509AttributeName x509AttributeName = new X509AttributeName(str);
        int attributeMap = attributeMap(x509AttributeName.getPrefix());
        if (attributeMap == 0) {
            throw new CertificateException(AbstractC0000a.m15k("Attribute name not recognized: ", str));
        }
        this.rawCertInfo = null;
        String suffix = x509AttributeName.getSuffix();
        switch (attributeMap) {
            case 1:
                if (suffix == null) {
                    this.version = null;
                    return;
                } else {
                    this.version.delete(suffix);
                    return;
                }
            case 2:
                if (suffix == null) {
                    this.serialNum = null;
                    return;
                } else {
                    this.serialNum.delete(suffix);
                    return;
                }
            case 3:
                if (suffix == null) {
                    this.algId = null;
                    return;
                } else {
                    this.algId.delete(suffix);
                    return;
                }
            case 4:
                if (suffix == null) {
                    this.issuer = null;
                    return;
                } else {
                    this.issuer.delete(suffix);
                    return;
                }
            case 5:
                if (suffix == null) {
                    this.interval = null;
                    return;
                } else {
                    this.interval.delete(suffix);
                    return;
                }
            case 6:
                if (suffix == null) {
                    this.subject = null;
                    return;
                } else {
                    this.subject.delete(suffix);
                    return;
                }
            case 7:
                if (suffix == null) {
                    this.pubKey = null;
                    return;
                } else {
                    this.pubKey.delete(suffix);
                    return;
                }
            case 8:
                if (suffix == null) {
                    this.issuerUniqueId = null;
                    return;
                } else {
                    this.issuerUniqueId.delete(suffix);
                    return;
                }
            case 9:
                if (suffix == null) {
                    this.subjectUniqueId = null;
                    return;
                } else {
                    this.subjectUniqueId.delete(suffix);
                    return;
                }
            case 10:
                if (suffix == null) {
                    this.extensions = null;
                    return;
                }
                CertificateExtensions certificateExtensions = this.extensions;
                if (certificateExtensions != null) {
                    certificateExtensions.delete(suffix);
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        if (this.rawCertInfo == null) {
            DerOutputStream derOutputStream = new DerOutputStream();
            emit(derOutputStream);
            this.rawCertInfo = derOutputStream.toByteArray();
        }
        outputStream.write((byte[]) this.rawCertInfo.clone());
    }

    public boolean equals(X509CertInfo x509CertInfo) {
        byte[] bArr;
        if (this == x509CertInfo) {
            return true;
        }
        byte[] bArr2 = this.rawCertInfo;
        if (bArr2 == null || (bArr = x509CertInfo.rawCertInfo) == null || bArr2.length != bArr.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr3 = this.rawCertInfo;
            if (i2 >= bArr3.length) {
                return true;
            }
            if (bArr3[i2] != x509CertInfo.rawCertInfo[i2]) {
                return false;
            }
            i2++;
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        X509AttributeName x509AttributeName = new X509AttributeName(str);
        int attributeMap = attributeMap(x509AttributeName.getPrefix());
        if (attributeMap == 0) {
            throw new CertificateParsingException(AbstractC0000a.m15k("Attribute name not recognized: ", str));
        }
        String suffix = x509AttributeName.getSuffix();
        switch (attributeMap) {
            case 1:
                return suffix == null ? this.version : this.version.get(suffix);
            case 2:
                return suffix == null ? this.serialNum : this.serialNum.get(suffix);
            case 3:
                return suffix == null ? this.algId : this.algId.get(suffix);
            case 4:
                return suffix == null ? this.issuer : this.issuer.get(suffix);
            case 5:
                return suffix == null ? this.interval : this.interval.get(suffix);
            case 6:
                return suffix == null ? this.subject : this.subject.get(suffix);
            case 7:
                return suffix == null ? this.pubKey : this.pubKey.get(suffix);
            case 8:
                if (suffix == null) {
                    return this.issuerUniqueId;
                }
                CertificateIssuerUniqueIdentity certificateIssuerUniqueIdentity = this.issuerUniqueId;
                if (certificateIssuerUniqueIdentity == null) {
                    return null;
                }
                return certificateIssuerUniqueIdentity.get(suffix);
            case 9:
                if (suffix == null) {
                    return this.subjectUniqueId;
                }
                CertificateSubjectUniqueIdentity certificateSubjectUniqueIdentity = this.subjectUniqueId;
                if (certificateSubjectUniqueIdentity == null) {
                    return null;
                }
                return certificateSubjectUniqueIdentity.get(suffix);
            case 10:
                if (suffix == null) {
                    return this.extensions;
                }
                CertificateExtensions certificateExtensions = this.extensions;
                if (certificateExtensions == null) {
                    return null;
                }
                return certificateExtensions.get(suffix);
            default:
                return null;
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement("version");
        attributeNameEnumeration.addElement("serialNumber");
        attributeNameEnumeration.addElement("algorithmID");
        attributeNameEnumeration.addElement("issuer");
        attributeNameEnumeration.addElement("validity");
        attributeNameEnumeration.addElement("subject");
        attributeNameEnumeration.addElement("key");
        attributeNameEnumeration.addElement("issuerID");
        attributeNameEnumeration.addElement("subjectID");
        attributeNameEnumeration.addElement("extensions");
        return attributeNameEnumeration.elements();
    }

    public byte[] getEncodedInfo() {
        try {
            if (this.rawCertInfo == null) {
                DerOutputStream derOutputStream = new DerOutputStream();
                emit(derOutputStream);
                this.rawCertInfo = derOutputStream.toByteArray();
            }
            return (byte[]) this.rawCertInfo.clone();
        } catch (IOException e2) {
            throw new CertificateEncodingException(e2.toString());
        } catch (CertificateException e3) {
            throw new CertificateEncodingException(e3.toString());
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "info";
    }

    public int hashCode() {
        int i2 = 0;
        int i3 = 1;
        while (true) {
            byte[] bArr = this.rawCertInfo;
            if (i3 >= bArr.length) {
                return i2;
            }
            i2 += bArr[i3] * i3;
            i3++;
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        X509AttributeName x509AttributeName = new X509AttributeName(str);
        int attributeMap = attributeMap(x509AttributeName.getPrefix());
        if (attributeMap == 0) {
            throw new CertificateException(AbstractC0000a.m15k("Attribute name not recognized: ", str));
        }
        this.rawCertInfo = null;
        String suffix = x509AttributeName.getSuffix();
        switch (attributeMap) {
            case 1:
                if (suffix == null) {
                    setVersion(obj);
                    return;
                } else {
                    this.version.set(suffix, obj);
                    return;
                }
            case 2:
                if (suffix == null) {
                    setSerialNumber(obj);
                    return;
                } else {
                    this.serialNum.set(suffix, obj);
                    return;
                }
            case 3:
                if (suffix == null) {
                    setAlgorithmId(obj);
                    return;
                } else {
                    this.algId.set(suffix, obj);
                    return;
                }
            case 4:
                if (suffix == null) {
                    setIssuer(obj);
                    return;
                } else {
                    this.issuer.set(suffix, obj);
                    return;
                }
            case 5:
                if (suffix == null) {
                    setValidity(obj);
                    return;
                } else {
                    this.interval.set(suffix, obj);
                    return;
                }
            case 6:
                if (suffix == null) {
                    setSubject(obj);
                    return;
                } else {
                    this.subject.set(suffix, obj);
                    return;
                }
            case 7:
                if (suffix == null) {
                    setKey(obj);
                    return;
                } else {
                    this.pubKey.set(suffix, obj);
                    return;
                }
            case 8:
                if (suffix == null) {
                    setIssuerUniqueId(obj);
                    return;
                } else {
                    this.issuerUniqueId.set(suffix, obj);
                    return;
                }
            case 9:
                if (suffix == null) {
                    setSubjectUniqueId(obj);
                    return;
                } else {
                    this.subjectUniqueId.set(suffix, obj);
                    return;
                }
            case 10:
                if (suffix == null) {
                    setExtensions(obj);
                    return;
                }
                if (this.extensions == null) {
                    this.extensions = new CertificateExtensions();
                }
                this.extensions.set(suffix, obj);
                return;
            default:
                return;
        }
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        String extension;
        if (this.subject == null || this.pubKey == null || this.interval == null || this.issuer == null || this.algId == null || this.serialNum == null) {
            throw new NullPointerException("X.509 cert is incomplete");
        }
        StringBuilder sb = new StringBuilder("[\n");
        sb.append("  " + this.version.toString() + "\n");
        sb.append("  Subject: " + this.subject.toString() + "\n");
        sb.append("  Signature Algorithm: " + this.algId.toString() + "\n");
        sb.append("  Key:  " + this.pubKey.toString() + "\n");
        sb.append("  " + this.interval.toString() + "\n");
        sb.append("  Issuer: " + this.issuer.toString() + "\n");
        sb.append("  " + this.serialNum.toString() + "\n");
        if (this.issuerUniqueId != null) {
            sb.append("  Issuer Id:\n" + this.issuerUniqueId.toString() + "\n");
        }
        if (this.subjectUniqueId != null) {
            sb.append("  Subject Id:\n" + this.subjectUniqueId.toString() + "\n");
        }
        CertificateExtensions certificateExtensions = this.extensions;
        if (certificateExtensions != null) {
            Object[] array = certificateExtensions.getAllExtensions().toArray();
            sb.append("\nCertificate Extensions: " + array.length);
            int i2 = 0;
            while (i2 < array.length) {
                StringBuilder sb2 = new StringBuilder("\n[");
                int i3 = i2 + 1;
                sb2.append(i3);
                sb2.append("]: ");
                sb.append(sb2.toString());
                Extension extension2 = (Extension) array[i2];
                try {
                } catch (Exception unused) {
                    sb.append(", Error parsing this extension");
                }
                if (OIDMap.getClass(extension2.getExtensionId()) == null) {
                    sb.append(extension2.toString());
                    byte[] extensionValue = extension2.getExtensionValue();
                    if (extensionValue != null) {
                        DerOutputStream derOutputStream = new DerOutputStream();
                        derOutputStream.putOctetString(extensionValue);
                        byte[] byteArray = derOutputStream.toByteArray();
                        extension = "Extension unknown: DER encoded OCTET string =\n" + new HexDumpEncoder().encodeBuffer(byteArray) + "\n";
                    } else {
                        i2 = i3;
                    }
                } else {
                    extension = extension2.toString();
                }
                sb.append(extension);
                i2 = i3;
            }
            Map<String, Extension> unparseableExtensions = this.extensions.getUnparseableExtensions();
            if (!unparseableExtensions.isEmpty()) {
                sb.append("\nUnparseable certificate extensions: " + unparseableExtensions.size());
                int i4 = 1;
                for (Extension extension3 : unparseableExtensions.values()) {
                    sb.append("\n[" + i4 + "]: ");
                    sb.append(extension3);
                    i4++;
                }
            }
        }
        sb.append("\n]");
        return sb.toString();
    }

    public X509CertInfo(DerValue derValue) {
        try {
            parse(derValue);
        } catch (IOException e2) {
            CertificateParsingException certificateParsingException = new CertificateParsingException(e2.toString());
            certificateParsingException.initCause(e2);
            throw certificateParsingException;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof X509CertInfo) {
            return equals((X509CertInfo) obj);
        }
        return false;
    }

    public X509CertInfo(byte[] bArr) {
        try {
            parse(new DerValue(bArr));
        } catch (IOException e2) {
            CertificateParsingException certificateParsingException = new CertificateParsingException(e2.toString());
            certificateParsingException.initCause(e2);
            throw certificateParsingException;
        }
    }
}
