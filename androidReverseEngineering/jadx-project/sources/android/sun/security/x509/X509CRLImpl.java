package android.sun.security.x509;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.provider.X509Factory;
import android.sun.security.util.DerEncoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509CRLImpl extends X509CRL implements DerEncoder {
    private static final long YR_2050 = 2524636800000L;
    private static final boolean isExplicit = true;
    private CRLExtensions extensions;
    private AlgorithmId infoSigAlgId;
    private X500Name issuer;
    private X500Principal issuerPrincipal;
    private Date nextUpdate;
    private boolean readOnly;
    private Map<X509IssuerSerial, X509CRLEntry> revokedCerts;
    private AlgorithmId sigAlgId;
    private byte[] signature;
    private byte[] signedCRL;
    private byte[] tbsCertList;
    private Date thisUpdate;
    private String verifiedProvider;
    private PublicKey verifiedPublicKey;
    private int version;

    public static final class X509IssuerSerial {
        volatile int hashcode;
        final X500Principal issuer;
        final BigInteger serial;

        public X509IssuerSerial(X509Certificate x509Certificate) {
            this(x509Certificate.getIssuerX500Principal(), x509Certificate.getSerialNumber());
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return X509CRLImpl.isExplicit;
            }
            if (!(obj instanceof X509IssuerSerial)) {
                return false;
            }
            X509IssuerSerial x509IssuerSerial = (X509IssuerSerial) obj;
            if (this.serial.equals(x509IssuerSerial.getSerial()) && this.issuer.equals(x509IssuerSerial.getIssuer())) {
                return X509CRLImpl.isExplicit;
            }
            return false;
        }

        public X500Principal getIssuer() {
            return this.issuer;
        }

        public BigInteger getSerial() {
            return this.serial;
        }

        public int hashCode() {
            if (this.hashcode == 0) {
                this.hashcode = this.serial.hashCode() + ((this.issuer.hashCode() + 629) * 37);
            }
            return this.hashcode;
        }

        public X509IssuerSerial(X500Principal x500Principal, BigInteger bigInteger) {
            this.hashcode = 0;
            this.issuer = x500Principal;
            this.serial = bigInteger;
        }
    }

    private X509CRLImpl() {
        this.signedCRL = null;
        this.signature = null;
        this.tbsCertList = null;
        this.sigAlgId = null;
        this.issuer = null;
        this.issuerPrincipal = null;
        this.thisUpdate = null;
        this.nextUpdate = null;
        this.revokedCerts = new LinkedHashMap();
        this.extensions = null;
        this.readOnly = false;
    }

    private X500Principal getCertIssuer(X509CRLEntryImpl x509CRLEntryImpl, X500Principal x500Principal) {
        CertificateIssuerExtension certificateIssuerExtension = x509CRLEntryImpl.getCertificateIssuerExtension();
        return certificateIssuerExtension != null ? ((X500Name) ((GeneralNames) certificateIssuerExtension.get("issuer")).get(0).getName()).asX500Principal() : x500Principal;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00e4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parse(DerValue derValue) {
        Date generalizedTime;
        Date generalizedTime2;
        if (this.readOnly) {
            throw new CRLException("cannot over-write existing CRL");
        }
        if (derValue.getData() == null || derValue.tag != 48) {
            throw new CRLException("Invalid DER-encoded CRL data");
        }
        this.signedCRL = derValue.toByteArray();
        DerValue[] derValueArr = {derValue.data.getDerValue(), derValue.data.getDerValue(), derValue.data.getDerValue()};
        if (derValue.data.available() != 0) {
            throw new CRLException("signed overrun, bytes = " + derValue.data.available());
        }
        if (derValueArr[0].tag != 48) {
            throw new CRLException("signed CRL fields invalid");
        }
        this.sigAlgId = AlgorithmId.parse(derValueArr[1]);
        this.signature = derValueArr[2].getBitString();
        if (derValueArr[1].data.available() != 0) {
            throw new CRLException("AlgorithmId field overrun");
        }
        if (derValueArr[2].data.available() != 0) {
            throw new CRLException("Signature field overrun");
        }
        this.tbsCertList = derValueArr[0].toByteArray();
        DerInputStream derInputStream = derValueArr[0].data;
        this.version = 0;
        if (((byte) derInputStream.peekByte()) == 2) {
            int integer = derInputStream.getInteger();
            this.version = integer;
            if (integer != 1) {
                throw new CRLException("Invalid version");
            }
        }
        AlgorithmId parse = AlgorithmId.parse(derInputStream.getDerValue());
        if (!parse.equals(this.sigAlgId)) {
            throw new CRLException("Signature algorithm mismatch");
        }
        this.infoSigAlgId = parse;
        X500Name x500Name = new X500Name(derInputStream);
        this.issuer = x500Name;
        if (x500Name.isEmpty()) {
            throw new CRLException("Empty issuer DN not allowed in X509CRLs");
        }
        byte peekByte = (byte) derInputStream.peekByte();
        if (peekByte == 23) {
            generalizedTime = derInputStream.getUTCTime();
        } else {
            if (peekByte != 24) {
                throw new CRLException(AbstractC0000a.m12h("Invalid encoding for thisUpdate (tag=", peekByte, ")"));
            }
            generalizedTime = derInputStream.getGeneralizedTime();
        }
        this.thisUpdate = generalizedTime;
        if (derInputStream.available() == 0) {
            return;
        }
        byte peekByte2 = (byte) derInputStream.peekByte();
        if (peekByte2 != 23) {
            if (peekByte2 == 24) {
                generalizedTime2 = derInputStream.getGeneralizedTime();
            }
            if (derInputStream.available() != 0) {
                return;
            }
            byte peekByte3 = (byte) derInputStream.peekByte();
            if (peekByte3 == 48 && (peekByte3 & DerValue.TAG_PRIVATE) != 128) {
                DerValue[] sequence = derInputStream.getSequence(4);
                X500Principal issuerX500Principal = getIssuerX500Principal();
                X500Principal x500Principal = issuerX500Principal;
                for (DerValue derValue2 : sequence) {
                    X509CRLEntryImpl x509CRLEntryImpl = new X509CRLEntryImpl(derValue2);
                    x500Principal = getCertIssuer(x509CRLEntryImpl, x500Principal);
                    x509CRLEntryImpl.setCertificateIssuer(issuerX500Principal, x500Principal);
                    this.revokedCerts.put(new X509IssuerSerial(x500Principal, x509CRLEntryImpl.getSerialNumber()), x509CRLEntryImpl);
                }
            }
            if (derInputStream.available() == 0) {
                return;
            }
            DerValue derValue3 = derInputStream.getDerValue();
            if (derValue3.isConstructed() && derValue3.isContextSpecific((byte) 0)) {
                this.extensions = new CRLExtensions(derValue3.data);
            }
            this.readOnly = isExplicit;
            return;
        }
        generalizedTime2 = derInputStream.getUTCTime();
        this.nextUpdate = generalizedTime2;
        if (derInputStream.available() != 0) {
        }
    }

    public static X509CRLImpl toImpl(X509CRL x509crl) {
        return x509crl instanceof X509CRLImpl ? (X509CRLImpl) x509crl : X509Factory.intern(x509crl);
    }

    @Override // android.sun.security.util.DerEncoder
    public void derEncode(OutputStream outputStream) {
        byte[] bArr = this.signedCRL;
        if (bArr == null) {
            throw new IOException("Null CRL to encode");
        }
        outputStream.write((byte[]) bArr.clone());
    }

    public void encodeInfo(OutputStream outputStream) {
        try {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            DerOutputStream derOutputStream3 = new DerOutputStream();
            int i2 = this.version;
            if (i2 != 0) {
                derOutputStream.putInteger(i2);
            }
            this.infoSigAlgId.encode(derOutputStream);
            if (this.version == 0 && this.issuer.toString() == null) {
                throw new CRLException("Null Issuer DN not allowed in v1 CRL");
            }
            this.issuer.encode(derOutputStream);
            if (this.thisUpdate.getTime() < YR_2050) {
                derOutputStream.putUTCTime(this.thisUpdate);
            } else {
                derOutputStream.putGeneralizedTime(this.thisUpdate);
            }
            Date date = this.nextUpdate;
            if (date != null) {
                if (date.getTime() < YR_2050) {
                    derOutputStream.putUTCTime(this.nextUpdate);
                } else {
                    derOutputStream.putGeneralizedTime(this.nextUpdate);
                }
            }
            if (!this.revokedCerts.isEmpty()) {
                Iterator<X509CRLEntry> it = this.revokedCerts.values().iterator();
                while (it.hasNext()) {
                    ((X509CRLEntryImpl) it.next()).encode(derOutputStream2);
                }
                derOutputStream.write((byte) 48, derOutputStream2);
            }
            CRLExtensions cRLExtensions = this.extensions;
            if (cRLExtensions != null) {
                cRLExtensions.encode(derOutputStream, isExplicit);
            }
            derOutputStream3.write((byte) 48, derOutputStream);
            byte[] byteArray = derOutputStream3.toByteArray();
            this.tbsCertList = byteArray;
            outputStream.write(byteArray);
        } catch (IOException e2) {
            throw new CRLException(AbstractC0000a.m8d(e2, new StringBuilder("Encoding error: ")));
        }
    }

    public KeyIdentifier getAuthKeyId() {
        AuthorityKeyIdentifierExtension authKeyIdExtension = getAuthKeyIdExtension();
        if (authKeyIdExtension != null) {
            return (KeyIdentifier) authKeyIdExtension.get("key_id");
        }
        return null;
    }

    public AuthorityKeyIdentifierExtension getAuthKeyIdExtension() {
        return (AuthorityKeyIdentifierExtension) getExtension(PKIXExtensions.AuthorityKey_Id);
    }

    public BigInteger getBaseCRLNumber() {
        DeltaCRLIndicatorExtension deltaCRLIndicatorExtension = getDeltaCRLIndicatorExtension();
        if (deltaCRLIndicatorExtension != null) {
            return (BigInteger) deltaCRLIndicatorExtension.get("value");
        }
        return null;
    }

    public BigInteger getCRLNumber() {
        CRLNumberExtension cRLNumberExtension = getCRLNumberExtension();
        if (cRLNumberExtension != null) {
            return (BigInteger) cRLNumberExtension.get("value");
        }
        return null;
    }

    public CRLNumberExtension getCRLNumberExtension() {
        return (CRLNumberExtension) getExtension(PKIXExtensions.CRLNumber_Id);
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getCriticalExtensionOIDs() {
        if (this.extensions == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        for (Extension extension : this.extensions.getAllExtensions()) {
            if (extension.isCritical()) {
                hashSet.add(extension.getExtensionId().toString());
            }
        }
        return hashSet;
    }

    public DeltaCRLIndicatorExtension getDeltaCRLIndicatorExtension() {
        return (DeltaCRLIndicatorExtension) getExtension(PKIXExtensions.DeltaCRLIndicator_Id);
    }

    @Override // java.security.cert.X509CRL
    public byte[] getEncoded() {
        return (byte[]) getEncodedInternal().clone();
    }

    public byte[] getEncodedInternal() {
        byte[] bArr = this.signedCRL;
        if (bArr != null) {
            return bArr;
        }
        throw new CRLException("Null CRL to encode");
    }

    public Object getExtension(ObjectIdentifier objectIdentifier) {
        CRLExtensions cRLExtensions = this.extensions;
        if (cRLExtensions == null) {
            return null;
        }
        return cRLExtensions.get(OIDMap.getName(objectIdentifier));
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        Extension extension;
        byte[] extensionValue;
        if (this.extensions == null) {
            return null;
        }
        try {
            String name = OIDMap.getName(new ObjectIdentifier(str));
            if (name == null) {
                ObjectIdentifier objectIdentifier = new ObjectIdentifier(str);
                Enumeration<Extension> elements = this.extensions.getElements();
                while (true) {
                    if (!elements.hasMoreElements()) {
                        extension = null;
                        break;
                    }
                    extension = elements.nextElement();
                    if (extension.getExtensionId().equals(objectIdentifier)) {
                        break;
                    }
                }
            } else {
                extension = this.extensions.get(name);
            }
            if (extension == null || (extensionValue = extension.getExtensionValue()) == null) {
                return null;
            }
            DerOutputStream derOutputStream = new DerOutputStream();
            derOutputStream.putOctetString(extensionValue);
            return derOutputStream.toByteArray();
        } catch (Exception unused) {
            return null;
        }
    }

    public IssuerAlternativeNameExtension getIssuerAltNameExtension() {
        return (IssuerAlternativeNameExtension) getExtension(PKIXExtensions.IssuerAlternativeName_Id);
    }

    @Override // java.security.cert.X509CRL
    public Principal getIssuerDN() {
        return this.issuer;
    }

    @Override // java.security.cert.X509CRL
    public X500Principal getIssuerX500Principal() {
        if (this.issuerPrincipal == null) {
            this.issuerPrincipal = this.issuer.asX500Principal();
        }
        return this.issuerPrincipal;
    }

    public IssuingDistributionPointExtension getIssuingDistributionPointExtension() {
        return (IssuingDistributionPointExtension) getExtension(PKIXExtensions.IssuingDistributionPoint_Id);
    }

    @Override // java.security.cert.X509CRL
    public Date getNextUpdate() {
        if (this.nextUpdate == null) {
            return null;
        }
        return new Date(this.nextUpdate.getTime());
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getNonCriticalExtensionOIDs() {
        if (this.extensions == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        for (Extension extension : this.extensions.getAllExtensions()) {
            if (!extension.isCritical()) {
                hashSet.add(extension.getExtensionId().toString());
            }
        }
        return hashSet;
    }

    @Override // java.security.cert.X509CRL
    public X509CRLEntry getRevokedCertificate(BigInteger bigInteger) {
        if (this.revokedCerts.isEmpty()) {
            return null;
        }
        return this.revokedCerts.get(new X509IssuerSerial(getIssuerX500Principal(), bigInteger));
    }

    @Override // java.security.cert.X509CRL
    public Set<X509CRLEntry> getRevokedCertificates() {
        if (this.revokedCerts.isEmpty()) {
            return null;
        }
        return new HashSet(this.revokedCerts.values());
    }

    public AlgorithmId getSigAlgId() {
        return this.sigAlgId;
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgName() {
        AlgorithmId algorithmId = this.sigAlgId;
        if (algorithmId == null) {
            return null;
        }
        return algorithmId.getName();
    }

    @Override // java.security.cert.X509CRL
    public String getSigAlgOID() {
        AlgorithmId algorithmId = this.sigAlgId;
        if (algorithmId == null) {
            return null;
        }
        return algorithmId.getOID().toString();
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSigAlgParams() {
        AlgorithmId algorithmId = this.sigAlgId;
        if (algorithmId == null) {
            return null;
        }
        try {
            return algorithmId.getEncodedParams();
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // java.security.cert.X509CRL
    public byte[] getSignature() {
        byte[] bArr = this.signature;
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    @Override // java.security.cert.X509CRL
    public byte[] getTBSCertList() {
        byte[] bArr = this.tbsCertList;
        if (bArr == null) {
            throw new CRLException("Uninitialized CRL");
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    @Override // java.security.cert.X509CRL
    public Date getThisUpdate() {
        return new Date(this.thisUpdate.getTime());
    }

    @Override // java.security.cert.X509CRL
    public int getVersion() {
        return this.version + 1;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        CRLExtensions cRLExtensions = this.extensions;
        if (cRLExtensions == null) {
            return false;
        }
        return cRLExtensions.hasUnsupportedCriticalExtension();
    }

    @Override // java.security.cert.CRL
    public boolean isRevoked(Certificate certificate) {
        if (this.revokedCerts.isEmpty() || !(certificate instanceof X509Certificate)) {
            return false;
        }
        return this.revokedCerts.containsKey(new X509IssuerSerial((X509Certificate) certificate));
    }

    public void sign(PrivateKey privateKey, String str) {
        sign(privateKey, str, null);
    }

    @Override // java.security.cert.CRL
    public String toString() {
        String str;
        String extension;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("X.509 CRL v" + (this.version + 1) + "\n");
        if (this.sigAlgId != null) {
            stringBuffer.append("Signature Algorithm: " + this.sigAlgId.toString() + ", OID=" + this.sigAlgId.getOID().toString() + "\n");
        }
        if (this.issuer != null) {
            stringBuffer.append("Issuer: " + this.issuer.toString() + "\n");
        }
        if (this.thisUpdate != null) {
            stringBuffer.append("\nThis Update: " + this.thisUpdate.toString() + "\n");
        }
        if (this.nextUpdate != null) {
            stringBuffer.append("Next Update: " + this.nextUpdate.toString() + "\n");
        }
        if (this.revokedCerts.isEmpty()) {
            stringBuffer.append("\nNO certificates have been revoked\n");
        } else {
            stringBuffer.append("\nRevoked Certificates: " + this.revokedCerts.size());
            Iterator<X509CRLEntry> it = this.revokedCerts.values().iterator();
            int i2 = 1;
            while (it.hasNext()) {
                StringBuilder m21q = AbstractC0000a.m21q("\n[", i2, "] ");
                m21q.append(it.next().toString());
                stringBuffer.append(m21q.toString());
                i2++;
            }
        }
        CRLExtensions cRLExtensions = this.extensions;
        if (cRLExtensions != null) {
            Object[] array = cRLExtensions.getAllExtensions().toArray();
            stringBuffer.append("\nCRL Extensions: " + array.length);
            int i3 = 0;
            while (i3 < array.length) {
                StringBuilder sb = new StringBuilder("\n[");
                int i4 = i3 + 1;
                sb.append(i4);
                sb.append("]: ");
                stringBuffer.append(sb.toString());
                Extension extension2 = (Extension) array[i3];
                try {
                } catch (Exception unused) {
                    stringBuffer.append(", Error parsing this extension");
                }
                if (OIDMap.getClass(extension2.getExtensionId()) == null) {
                    stringBuffer.append(extension2.toString());
                    byte[] extensionValue = extension2.getExtensionValue();
                    if (extensionValue != null) {
                        DerOutputStream derOutputStream = new DerOutputStream();
                        derOutputStream.putOctetString(extensionValue);
                        byte[] byteArray = derOutputStream.toByteArray();
                        extension = "Extension unknown: DER encoded OCTET string =\n" + new HexDumpEncoder().encodeBuffer(byteArray) + "\n";
                    } else {
                        i3 = i4;
                    }
                } else {
                    extension = extension2.toString();
                }
                stringBuffer.append(extension);
                i3 = i4;
            }
        }
        if (this.signature != null) {
            str = "\nSignature:\n" + new HexDumpEncoder().encodeBuffer(this.signature) + "\n";
        } else {
            str = "NOT signed yet\n";
        }
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    @Override // java.security.cert.X509CRL
    public void verify(PublicKey publicKey) {
        verify(publicKey, BuildConfig.FLAVOR);
    }

    public X509CRLImpl(DerValue derValue) {
        this.signedCRL = null;
        this.signature = null;
        this.tbsCertList = null;
        this.sigAlgId = null;
        this.issuer = null;
        this.issuerPrincipal = null;
        this.thisUpdate = null;
        this.nextUpdate = null;
        this.revokedCerts = new LinkedHashMap();
        this.extensions = null;
        this.readOnly = false;
        try {
            parse(derValue);
        } catch (IOException e2) {
            this.signedCRL = null;
            throw new CRLException(AbstractC0000a.m8d(e2, new StringBuilder("Parsing error: ")));
        }
    }

    public static byte[] getEncodedInternal(X509CRL x509crl) {
        return x509crl instanceof X509CRLImpl ? ((X509CRLImpl) x509crl).getEncodedInternal() : x509crl.getEncoded();
    }

    public static X500Principal getIssuerX500Principal(X509CRL x509crl) {
        try {
            DerInputStream derInputStream = new DerInputStream(x509crl.getEncoded()).getSequence(3)[0].data;
            if (((byte) derInputStream.peekByte()) == 2) {
                derInputStream.getDerValue();
            }
            derInputStream.getDerValue();
            return new X500Principal(derInputStream.getDerValue().toByteArray());
        } catch (Exception e2) {
            throw new RuntimeException("Could not parse issuer", e2);
        }
    }

    @Override // java.security.cert.X509CRL
    public X509CRLEntry getRevokedCertificate(X509Certificate x509Certificate) {
        if (this.revokedCerts.isEmpty()) {
            return null;
        }
        return this.revokedCerts.get(new X509IssuerSerial(x509Certificate));
    }

    public void sign(PrivateKey privateKey, String str, String str2) {
        Signature signature;
        try {
            if (this.readOnly) {
                throw new CRLException("cannot over-write existing CRL");
            }
            if (str2 != null && str2.length() != 0) {
                signature = Signature.getInstance(str, str2);
                signature.initSign(privateKey);
                AlgorithmId algorithmId = AlgorithmId.get(signature.getAlgorithm());
                this.sigAlgId = algorithmId;
                this.infoSigAlgId = algorithmId;
                DerOutputStream derOutputStream = new DerOutputStream();
                DerOutputStream derOutputStream2 = new DerOutputStream();
                encodeInfo(derOutputStream2);
                this.sigAlgId.encode(derOutputStream2);
                byte[] bArr = this.tbsCertList;
                signature.update(bArr, 0, bArr.length);
                byte[] sign = signature.sign();
                this.signature = sign;
                derOutputStream2.putBitString(sign);
                derOutputStream.write((byte) 48, derOutputStream2);
                this.signedCRL = derOutputStream.toByteArray();
                this.readOnly = isExplicit;
            }
            signature = Signature.getInstance(str);
            signature.initSign(privateKey);
            AlgorithmId algorithmId2 = AlgorithmId.get(signature.getAlgorithm());
            this.sigAlgId = algorithmId2;
            this.infoSigAlgId = algorithmId2;
            DerOutputStream derOutputStream3 = new DerOutputStream();
            DerOutputStream derOutputStream22 = new DerOutputStream();
            encodeInfo(derOutputStream22);
            this.sigAlgId.encode(derOutputStream22);
            byte[] bArr2 = this.tbsCertList;
            signature.update(bArr2, 0, bArr2.length);
            byte[] sign2 = signature.sign();
            this.signature = sign2;
            derOutputStream22.putBitString(sign2);
            derOutputStream3.write((byte) 48, derOutputStream22);
            this.signedCRL = derOutputStream3.toByteArray();
            this.readOnly = isExplicit;
        } catch (IOException e2) {
            throw new CRLException(AbstractC0000a.m8d(e2, new StringBuilder("Error while encoding data: ")));
        }
    }

    @Override // java.security.cert.X509CRL
    public synchronized void verify(PublicKey publicKey, String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        PublicKey publicKey2 = this.verifiedPublicKey;
        if (publicKey2 != null && publicKey2.equals(publicKey) && str.equals(this.verifiedProvider)) {
            return;
        }
        if (this.signedCRL == null) {
            throw new CRLException("Uninitialized CRL");
        }
        Signature signature = str.length() == 0 ? Signature.getInstance(this.sigAlgId.getName()) : Signature.getInstance(this.sigAlgId.getName(), str);
        signature.initVerify(publicKey);
        byte[] bArr = this.tbsCertList;
        if (bArr == null) {
            throw new CRLException("Uninitialized CRL");
        }
        signature.update(bArr, 0, bArr.length);
        if (!signature.verify(this.signature)) {
            throw new SignatureException("Signature does not match.");
        }
        this.verifiedPublicKey = publicKey;
        this.verifiedProvider = str;
    }

    public X509CRLImpl(X500Name x500Name, Date date, Date date2) {
        this.signedCRL = null;
        this.signature = null;
        this.tbsCertList = null;
        this.sigAlgId = null;
        this.issuer = null;
        this.issuerPrincipal = null;
        this.thisUpdate = null;
        this.nextUpdate = null;
        this.revokedCerts = new LinkedHashMap();
        this.extensions = null;
        this.readOnly = false;
        this.issuer = x500Name;
        this.thisUpdate = date;
        this.nextUpdate = date2;
    }

    public X509CRLImpl(X500Name x500Name, Date date, Date date2, X509CRLEntry[] x509CRLEntryArr) {
        this.signedCRL = null;
        this.signature = null;
        this.tbsCertList = null;
        this.sigAlgId = null;
        this.issuer = null;
        this.issuerPrincipal = null;
        this.thisUpdate = null;
        this.nextUpdate = null;
        this.revokedCerts = new LinkedHashMap();
        this.extensions = null;
        this.readOnly = false;
        this.issuer = x500Name;
        this.thisUpdate = date;
        this.nextUpdate = date2;
        if (x509CRLEntryArr != null) {
            X500Principal issuerX500Principal = getIssuerX500Principal();
            X500Principal x500Principal = issuerX500Principal;
            for (X509CRLEntry x509CRLEntry : x509CRLEntryArr) {
                X509CRLEntryImpl x509CRLEntryImpl = (X509CRLEntryImpl) x509CRLEntry;
                try {
                    x500Principal = getCertIssuer(x509CRLEntryImpl, x500Principal);
                    x509CRLEntryImpl.setCertificateIssuer(issuerX500Principal, x500Principal);
                    this.revokedCerts.put(new X509IssuerSerial(x500Principal, x509CRLEntryImpl.getSerialNumber()), x509CRLEntryImpl);
                    if (x509CRLEntryImpl.hasExtensions()) {
                        this.version = 1;
                    }
                } catch (IOException e2) {
                    throw new CRLException(e2);
                }
            }
        }
    }

    public X509CRLImpl(X500Name x500Name, Date date, Date date2, X509CRLEntry[] x509CRLEntryArr, CRLExtensions cRLExtensions) {
        this(x500Name, date, date2, x509CRLEntryArr);
        if (cRLExtensions != null) {
            this.extensions = cRLExtensions;
            this.version = 1;
        }
    }

    public X509CRLImpl(InputStream inputStream) {
        this.signedCRL = null;
        this.signature = null;
        this.tbsCertList = null;
        this.sigAlgId = null;
        this.issuer = null;
        this.issuerPrincipal = null;
        this.thisUpdate = null;
        this.nextUpdate = null;
        this.revokedCerts = new LinkedHashMap();
        this.extensions = null;
        this.readOnly = false;
        try {
            parse(new DerValue(inputStream));
        } catch (IOException e2) {
            this.signedCRL = null;
            throw new CRLException(AbstractC0000a.m8d(e2, new StringBuilder("Parsing error: ")));
        }
    }

    public X509CRLImpl(byte[] bArr) {
        this.signedCRL = null;
        this.signature = null;
        this.tbsCertList = null;
        this.sigAlgId = null;
        this.issuer = null;
        this.issuerPrincipal = null;
        this.thisUpdate = null;
        this.nextUpdate = null;
        this.revokedCerts = new LinkedHashMap();
        this.extensions = null;
        this.readOnly = false;
        try {
            parse(new DerValue(bArr));
        } catch (IOException e2) {
            this.signedCRL = null;
            throw new CRLException(AbstractC0000a.m8d(e2, new StringBuilder("Parsing error: ")));
        }
    }
}
