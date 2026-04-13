package org.bouncycastle.pkcs;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.ContentVerifierProvider;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS10CertificationRequest {
    private static Attribute[] EMPTY_ARRAY = new Attribute[0];
    private CertificationRequest certificationRequest;

    public PKCS10CertificationRequest(CertificationRequest certificationRequest) {
        if (certificationRequest == null) {
            throw new NullPointerException("certificationRequest cannot be null");
        }
        this.certificationRequest = certificationRequest;
    }

    private static CertificationRequest parseBytes(byte[] bArr) {
        try {
            CertificationRequest certificationRequest = CertificationRequest.getInstance(ASN1Primitive.fromByteArray(bArr));
            if (certificationRequest != null) {
                return certificationRequest;
            }
            throw new PKCSIOException("empty data passed to constructor");
        } catch (ClassCastException e2) {
            throw new PKCSIOException("malformed data: " + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new PKCSIOException("malformed data: " + e3.getMessage(), e3);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PKCS10CertificationRequest) {
            return toASN1Structure().equals(((PKCS10CertificationRequest) obj).toASN1Structure());
        }
        return false;
    }

    public Attribute[] getAttributes() {
        ASN1Set attributes = this.certificationRequest.getCertificationRequestInfo().getAttributes();
        if (attributes == null) {
            return EMPTY_ARRAY;
        }
        Attribute[] attributeArr = new Attribute[attributes.size()];
        for (int i2 = 0; i2 != attributes.size(); i2++) {
            attributeArr[i2] = Attribute.getInstance(attributes.getObjectAt(i2));
        }
        return attributeArr;
    }

    public byte[] getEncoded() {
        return this.certificationRequest.getEncoded();
    }

    public Extensions getRequestedExtensions() {
        Attribute[] attributes = getAttributes();
        for (int i2 = 0; i2 != attributes.length; i2++) {
            Attribute attribute = attributes[i2];
            if (attribute.getAttrType() == PKCSObjectIdentifiers.pkcs_9_at_extensionRequest) {
                ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
                Enumeration objects = ASN1Sequence.getInstance(attribute.getAttrValues().getObjectAt(0)).getObjects();
                while (objects.hasMoreElements()) {
                    ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(objects.nextElement());
                    boolean z2 = aSN1Sequence.size() == 3 && ASN1Boolean.getInstance(aSN1Sequence.getObjectAt(1)).isTrue();
                    if (aSN1Sequence.size() == 2) {
                        extensionsGenerator.addExtension(ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0)), false, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets());
                    } else {
                        if (aSN1Sequence.size() != 3) {
                            throw new IllegalArgumentException("incorrect sequence size of Extension get " + aSN1Sequence.size() + " expected 2 or three");
                        }
                        extensionsGenerator.addExtension(ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0)), z2, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(2)).getOctets());
                    }
                }
                return extensionsGenerator.generate();
            }
        }
        return null;
    }

    public byte[] getSignature() {
        return this.certificationRequest.getSignature().getOctets();
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.certificationRequest.getSignatureAlgorithm();
    }

    public X500Name getSubject() {
        return X500Name.getInstance(this.certificationRequest.getCertificationRequestInfo().getSubject());
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.certificationRequest.getCertificationRequestInfo().getSubjectPublicKeyInfo();
    }

    public int hashCode() {
        return toASN1Structure().hashCode();
    }

    public boolean isSignatureValid(ContentVerifierProvider contentVerifierProvider) {
        CertificationRequestInfo certificationRequestInfo = this.certificationRequest.getCertificationRequestInfo();
        try {
            ContentVerifier contentVerifier = contentVerifierProvider.get(this.certificationRequest.getSignatureAlgorithm());
            OutputStream outputStream = contentVerifier.getOutputStream();
            outputStream.write(certificationRequestInfo.getEncoded(ASN1Encoding.DER));
            outputStream.close();
            return contentVerifier.verify(getSignature());
        } catch (Exception e2) {
            throw new PKCSException(AbstractC0000a.m9e(e2, new StringBuilder("unable to process signature: ")), e2);
        }
    }

    public CertificationRequest toASN1Structure() {
        return this.certificationRequest;
    }

    public PKCS10CertificationRequest(byte[] bArr) {
        this(parseBytes(bArr));
    }

    public Attribute[] getAttributes(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        ASN1Set attributes = this.certificationRequest.getCertificationRequestInfo().getAttributes();
        if (attributes == null) {
            return EMPTY_ARRAY;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 != attributes.size(); i2++) {
            Attribute attribute = Attribute.getInstance(attributes.getObjectAt(i2));
            if (attribute.getAttrType().equals((ASN1Primitive) aSN1ObjectIdentifier)) {
                arrayList.add(attribute);
            }
        }
        return arrayList.size() == 0 ? EMPTY_ARRAY : (Attribute[]) arrayList.toArray(new Attribute[arrayList.size()]);
    }
}
