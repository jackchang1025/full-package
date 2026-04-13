package org.bouncycastle.cert;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.AttributeCertificate;
import org.bouncycastle.asn1.x509.AttributeCertificateInfo;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.TBSCertList;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.util.Properties;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
class CertUtils {
    private static Set EMPTY_SET = Collections.unmodifiableSet(new HashSet());
    private static List EMPTY_LIST = Collections.unmodifiableList(new ArrayList());

    public static void addExtension(ExtensionsGenerator extensionsGenerator, ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z2, ASN1Encodable aSN1Encodable) {
        try {
            extensionsGenerator.addExtension(aSN1ObjectIdentifier, z2, aSN1Encodable);
        } catch (IOException e2) {
            throw new CertIOException(AbstractC0000a.m8d(e2, new StringBuilder("cannot encode extension: ")), e2);
        }
    }

    public static boolean[] bitStringToBoolean(ASN1BitString aSN1BitString) {
        if (aSN1BitString == null) {
            return null;
        }
        byte[] bytes = aSN1BitString.getBytes();
        int length = (bytes.length * 8) - aSN1BitString.getPadBits();
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 != length; i2++) {
            zArr[i2] = (bytes[i2 / 8] & (128 >>> (i2 % 8))) != 0;
        }
        return zArr;
    }

    public static DERBitString booleanToBitString(boolean[] zArr) {
        byte[] bArr = new byte[(zArr.length + 7) / 8];
        for (int i2 = 0; i2 != zArr.length; i2++) {
            int i3 = i2 / 8;
            bArr[i3] = (byte) (bArr[i3] | (zArr[i2] ? 1 << (7 - (i2 % 8)) : 0));
        }
        int length = zArr.length % 8;
        return length == 0 ? new DERBitString(bArr) : new DERBitString(bArr, 8 - length);
    }

    public static ExtensionsGenerator doRemoveExtension(ExtensionsGenerator extensionsGenerator, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extensions generate = extensionsGenerator.generate();
        ExtensionsGenerator extensionsGenerator2 = new ExtensionsGenerator();
        Enumeration oids = generate.oids();
        boolean z2 = false;
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) oids.nextElement();
            if (aSN1ObjectIdentifier2.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
                z2 = true;
            } else {
                extensionsGenerator2.addExtension(generate.getExtension(aSN1ObjectIdentifier2));
            }
        }
        if (z2) {
            return extensionsGenerator2;
        }
        throw new IllegalArgumentException(AbstractC0413b.m1010d("remove - extension (OID = ", aSN1ObjectIdentifier, ") not found"));
    }

    public static ExtensionsGenerator doReplaceExtension(ExtensionsGenerator extensionsGenerator, Extension extension) {
        Extensions generate = extensionsGenerator.generate();
        ExtensionsGenerator extensionsGenerator2 = new ExtensionsGenerator();
        Enumeration oids = generate.oids();
        boolean z2 = false;
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (aSN1ObjectIdentifier.equals((ASN1Primitive) extension.getExtnId())) {
                extensionsGenerator2.addExtension(extension);
                z2 = true;
            } else {
                extensionsGenerator2.addExtension(generate.getExtension(aSN1ObjectIdentifier));
            }
        }
        if (z2) {
            return extensionsGenerator2;
        }
        throw new IllegalArgumentException("replace - original extension (OID = " + extension.getExtnId() + ") not found");
    }

    private static AttributeCertificate generateAttrStructure(AttributeCertificateInfo attributeCertificateInfo, AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(attributeCertificateInfo);
        aSN1EncodableVector.add(algorithmIdentifier);
        aSN1EncodableVector.add(new DERBitString(bArr));
        return AttributeCertificate.getInstance(new DERSequence(aSN1EncodableVector));
    }

    private static CertificateList generateCRLStructure(TBSCertList tBSCertList, AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertList);
        aSN1EncodableVector.add(algorithmIdentifier);
        aSN1EncodableVector.add(new DERBitString(bArr));
        return CertificateList.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public static X509AttributeCertificateHolder generateFullAttrCert(ContentSigner contentSigner, AttributeCertificateInfo attributeCertificateInfo) {
        try {
            return new X509AttributeCertificateHolder(generateAttrStructure(attributeCertificateInfo, contentSigner.getAlgorithmIdentifier(), generateSig(contentSigner, attributeCertificateInfo)));
        } catch (IOException unused) {
            throw new IllegalStateException("cannot produce attribute certificate signature");
        }
    }

    public static X509CRLHolder generateFullCRL(ContentSigner contentSigner, TBSCertList tBSCertList) {
        try {
            return new X509CRLHolder(generateCRLStructure(tBSCertList, contentSigner.getAlgorithmIdentifier(), generateSig(contentSigner, tBSCertList)));
        } catch (IOException unused) {
            throw new IllegalStateException("cannot produce certificate signature");
        }
    }

    public static X509CertificateHolder generateFullCert(ContentSigner contentSigner, TBSCertificate tBSCertificate) {
        try {
            return new X509CertificateHolder(generateStructure(tBSCertificate, contentSigner.getAlgorithmIdentifier(), generateSig(contentSigner, tBSCertificate)));
        } catch (IOException unused) {
            throw new IllegalStateException("cannot produce certificate signature");
        }
    }

    private static byte[] generateSig(ContentSigner contentSigner, ASN1Object aSN1Object) {
        OutputStream outputStream = contentSigner.getOutputStream();
        aSN1Object.encodeTo(outputStream, ASN1Encoding.DER);
        outputStream.close();
        return contentSigner.getSignature();
    }

    private static Certificate generateStructure(TBSCertificate tBSCertificate, AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(algorithmIdentifier);
        aSN1EncodableVector.add(new DERBitString(bArr));
        return Certificate.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public static Set getCriticalExtensionOIDs(Extensions extensions) {
        return extensions == null ? EMPTY_SET : Collections.unmodifiableSet(new HashSet(Arrays.asList(extensions.getCriticalExtensionOIDs())));
    }

    public static List getExtensionOIDs(Extensions extensions) {
        return extensions == null ? EMPTY_LIST : Collections.unmodifiableList(Arrays.asList(extensions.getExtensionOIDs()));
    }

    public static Set getNonCriticalExtensionOIDs(Extensions extensions) {
        return extensions == null ? EMPTY_SET : Collections.unmodifiableSet(new HashSet(Arrays.asList(extensions.getNonCriticalExtensionOIDs())));
    }

    public static boolean isAlgIdEqual(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        if (!algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        if (Properties.isOverrideSet("org.bouncycastle.x509.allow_absent_equiv_NULL")) {
            if (algorithmIdentifier.getParameters() == null) {
                return algorithmIdentifier2.getParameters() == null || algorithmIdentifier2.getParameters().equals(DERNull.INSTANCE);
            }
            if (algorithmIdentifier2.getParameters() == null) {
                return algorithmIdentifier.getParameters() == null || algorithmIdentifier.getParameters().equals(DERNull.INSTANCE);
            }
        }
        if (algorithmIdentifier.getParameters() != null) {
            return algorithmIdentifier.getParameters().equals(algorithmIdentifier2.getParameters());
        }
        if (algorithmIdentifier2.getParameters() != null) {
            return algorithmIdentifier2.getParameters().equals(algorithmIdentifier.getParameters());
        }
        return true;
    }

    public static ASN1Primitive parseNonEmptyASN1(byte[] bArr) {
        ASN1Primitive fromByteArray = ASN1Primitive.fromByteArray(bArr);
        if (fromByteArray != null) {
            return fromByteArray;
        }
        throw new IOException("no content found");
    }

    public static Date recoverDate(ASN1GeneralizedTime aSN1GeneralizedTime) {
        try {
            return aSN1GeneralizedTime.getDate();
        } catch (ParseException e2) {
            throw new IllegalStateException("unable to recover date: " + e2.getMessage());
        }
    }
}
