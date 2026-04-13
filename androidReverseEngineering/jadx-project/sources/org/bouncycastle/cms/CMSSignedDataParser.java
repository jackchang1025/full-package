package org.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Generator;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetStringParser;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.BERSetParser;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfoParser;
import org.bouncycastle.asn1.cms.SignedDataParser;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSSignedDataParser extends CMSContentInfoParser {
    private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
    private static final DefaultDigestAlgorithmIdentifierFinder dgstAlgFinder = new DefaultDigestAlgorithmIdentifierFinder();
    private ASN1Set _certSet;
    private ASN1Set _crlSet;
    private boolean _isCertCrlParsed;
    private CMSTypedStream _signedContent;
    private ASN1ObjectIdentifier _signedContentType;
    private SignedDataParser _signedData;
    private SignerInformationStore _signerInfoStore;
    private Set<AlgorithmIdentifier> digestAlgorithms;
    private Map digests;

    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, InputStream inputStream) {
        this(digestCalculatorProvider, (CMSTypedStream) null, inputStream);
    }

    private static ASN1Set getASN1Set(ASN1SetParser aSN1SetParser) {
        if (aSN1SetParser == null) {
            return null;
        }
        return ASN1Set.getInstance(aSN1SetParser.toASN1Primitive());
    }

    private static void pipeEncapsulatedOctetString(ContentInfoParser contentInfoParser, OutputStream outputStream) {
        ASN1OctetStringParser aSN1OctetStringParser = (ASN1OctetStringParser) contentInfoParser.getContent(4);
        if (aSN1OctetStringParser != null) {
            pipeOctetString(aSN1OctetStringParser, outputStream);
        }
    }

    private static void pipeOctetString(ASN1OctetStringParser aSN1OctetStringParser, OutputStream outputStream) {
        OutputStream createBEROctetOutputStream = CMSUtils.createBEROctetOutputStream(outputStream, 0, true, 0);
        Streams.pipeAll(aSN1OctetStringParser.getOctetStream(), createBEROctetOutputStream);
        createBEROctetOutputStream.close();
    }

    private void populateCertCrlSets() {
        if (this._isCertCrlParsed) {
            return;
        }
        this._isCertCrlParsed = true;
        try {
            this._certSet = getASN1Set(this._signedData.getCertificates());
            this._crlSet = getASN1Set(this._signedData.getCrls());
        } catch (IOException e2) {
            throw new CMSException("problem parsing cert/crl sets", e2);
        }
    }

    public static OutputStream replaceCertificatesAndCRLs(InputStream inputStream, Store store, Store store2, Store store3, OutputStream outputStream) {
        SignedDataParser signedDataParser = SignedDataParser.getInstance(new ContentInfoParser((ASN1SequenceParser) new ASN1StreamParser(inputStream).readObject()).getContent(16));
        BERSequenceGenerator bERSequenceGenerator = new BERSequenceGenerator(outputStream);
        bERSequenceGenerator.addObject((ASN1Primitive) CMSObjectIdentifiers.signedData);
        BERSequenceGenerator bERSequenceGenerator2 = new BERSequenceGenerator(bERSequenceGenerator.getRawOutputStream(), 0, true);
        bERSequenceGenerator2.addObject((ASN1Primitive) signedDataParser.getVersion());
        bERSequenceGenerator2.getRawOutputStream().write(signedDataParser.getDigestAlgorithms().toASN1Primitive().getEncoded());
        ContentInfoParser encapContentInfo = signedDataParser.getEncapContentInfo();
        BERSequenceGenerator bERSequenceGenerator3 = new BERSequenceGenerator(bERSequenceGenerator2.getRawOutputStream());
        bERSequenceGenerator3.addObject((ASN1Primitive) encapContentInfo.getContentType());
        pipeEncapsulatedOctetString(encapContentInfo, bERSequenceGenerator3.getRawOutputStream());
        bERSequenceGenerator3.close();
        getASN1Set(signedDataParser.getCertificates());
        getASN1Set(signedDataParser.getCrls());
        if (store != null || store3 != null) {
            ArrayList arrayList = new ArrayList();
            if (store != null) {
                arrayList.addAll(CMSUtils.getCertificatesFromStore(store));
            }
            if (store3 != null) {
                arrayList.addAll(CMSUtils.getAttributeCertificatesFromStore(store3));
            }
            ASN1Set createBerSetFromList = CMSUtils.createBerSetFromList(arrayList);
            if (createBerSetFromList.size() > 0) {
                bERSequenceGenerator2.getRawOutputStream().write(new DERTaggedObject(false, 0, (ASN1Encodable) createBerSetFromList).getEncoded());
            }
        }
        if (store2 != null) {
            ASN1Set createBerSetFromList2 = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(store2));
            if (createBerSetFromList2.size() > 0) {
                bERSequenceGenerator2.getRawOutputStream().write(new DERTaggedObject(false, 1, (ASN1Encodable) createBerSetFromList2).getEncoded());
            }
        }
        bERSequenceGenerator2.getRawOutputStream().write(signedDataParser.getSignerInfos().toASN1Primitive().getEncoded());
        bERSequenceGenerator2.close();
        bERSequenceGenerator.close();
        return outputStream;
    }

    public static OutputStream replaceSigners(InputStream inputStream, SignerInformationStore signerInformationStore, OutputStream outputStream) {
        SignedDataParser signedDataParser = SignedDataParser.getInstance(new ContentInfoParser((ASN1SequenceParser) new ASN1StreamParser(inputStream).readObject()).getContent(16));
        BERSequenceGenerator bERSequenceGenerator = new BERSequenceGenerator(outputStream);
        bERSequenceGenerator.addObject((ASN1Primitive) CMSObjectIdentifiers.signedData);
        BERSequenceGenerator bERSequenceGenerator2 = new BERSequenceGenerator(bERSequenceGenerator.getRawOutputStream(), 0, true);
        bERSequenceGenerator2.addObject((ASN1Primitive) signedDataParser.getVersion());
        signedDataParser.getDigestAlgorithms().toASN1Primitive();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator<SignerInformation> it = signerInformationStore.getSigners().iterator();
        while (it.hasNext()) {
            aSN1EncodableVector.add(CMSSignedHelper.INSTANCE.fixDigestAlgID(it.next().getDigestAlgorithmID(), dgstAlgFinder));
        }
        bERSequenceGenerator2.getRawOutputStream().write(new DERSet(aSN1EncodableVector).getEncoded());
        ContentInfoParser encapContentInfo = signedDataParser.getEncapContentInfo();
        BERSequenceGenerator bERSequenceGenerator3 = new BERSequenceGenerator(bERSequenceGenerator2.getRawOutputStream());
        bERSequenceGenerator3.addObject((ASN1Primitive) encapContentInfo.getContentType());
        pipeEncapsulatedOctetString(encapContentInfo, bERSequenceGenerator3.getRawOutputStream());
        bERSequenceGenerator3.close();
        writeSetToGeneratorTagged(bERSequenceGenerator2, signedDataParser.getCertificates(), 0);
        writeSetToGeneratorTagged(bERSequenceGenerator2, signedDataParser.getCrls(), 1);
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        Iterator<SignerInformation> it2 = signerInformationStore.getSigners().iterator();
        while (it2.hasNext()) {
            aSN1EncodableVector2.add(it2.next().toASN1Structure());
        }
        bERSequenceGenerator2.getRawOutputStream().write(new DERSet(aSN1EncodableVector2).getEncoded());
        bERSequenceGenerator2.close();
        bERSequenceGenerator.close();
        return outputStream;
    }

    private static void writeSetToGeneratorTagged(ASN1Generator aSN1Generator, ASN1SetParser aSN1SetParser, int i2) {
        ASN1Set aSN1Set = getASN1Set(aSN1SetParser);
        if (aSN1Set != null) {
            boolean z2 = aSN1SetParser instanceof BERSetParser;
            OutputStream rawOutputStream = aSN1Generator.getRawOutputStream();
            if (z2) {
                rawOutputStream.write(new BERTaggedObject(false, i2, (ASN1Encodable) aSN1Set).getEncoded());
            } else {
                rawOutputStream.write(new DERTaggedObject(false, i2, (ASN1Encodable) aSN1Set).getEncoded());
            }
        }
    }

    public Store getAttributeCertificates() {
        populateCertCrlSets();
        return HELPER.getAttributeCertificates(this._certSet);
    }

    public Store getCRLs() {
        populateCertCrlSets();
        return HELPER.getCRLs(this._crlSet);
    }

    public Store getCertificates() {
        populateCertCrlSets();
        return HELPER.getCertificates(this._certSet);
    }

    public Set<AlgorithmIdentifier> getDigestAlgorithmIDs() {
        return this.digestAlgorithms;
    }

    public Store getOtherRevocationInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        populateCertCrlSets();
        return HELPER.getOtherRevocationInfo(aSN1ObjectIdentifier, this._crlSet);
    }

    public CMSTypedStream getSignedContent() {
        if (this._signedContent == null) {
            return null;
        }
        return new CMSTypedStream(this._signedContent.getContentType(), CMSUtils.attachDigestsToInputStream(this.digests.values(), this._signedContent.getContentStream()));
    }

    public String getSignedContentTypeOID() {
        return this._signedContentType.getId();
    }

    public SignerInformationStore getSignerInfos() {
        if (this._signerInfoStore == null) {
            populateCertCrlSets();
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            for (Object obj : this.digests.keySet()) {
                hashMap.put(obj, ((DigestCalculator) this.digests.get(obj)).getDigest());
            }
            try {
                ASN1SetParser signerInfos = this._signedData.getSignerInfos();
                while (true) {
                    ASN1Encodable readObject = signerInfos.readObject();
                    if (readObject == null) {
                        break;
                    }
                    SignerInfo signerInfo = SignerInfo.getInstance(readObject.toASN1Primitive());
                    arrayList.add(new SignerInformation(signerInfo, this._signedContentType, null, (byte[]) hashMap.get(signerInfo.getDigestAlgorithm().getAlgorithm())));
                }
                this._signerInfoStore = new SignerInformationStore(arrayList);
            } catch (IOException e2) {
                throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("io exception: ")), e2);
            }
        }
        return this._signerInfoStore;
    }

    public int getVersion() {
        return this._signedData.getVersion().intValueExact();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0086 A[Catch: IOException -> 0x0094, TryCatch #1 {IOException -> 0x0094, blocks: (B:3:0x0003, B:4:0x0025, B:6:0x002b, B:8:0x0032, B:13:0x0038, B:19:0x0042, B:21:0x0057, B:24:0x007e, B:26:0x0086, B:27:0x0091, B:31:0x008b, B:32:0x006b, B:34:0x0071, B:36:0x0081), top: B:2:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008b A[Catch: IOException -> 0x0094, TryCatch #1 {IOException -> 0x0094, blocks: (B:3:0x0003, B:4:0x0025, B:6:0x002b, B:8:0x0032, B:13:0x0038, B:19:0x0042, B:21:0x0057, B:24:0x007e, B:26:0x0086, B:27:0x0091, B:31:0x008b, B:32:0x006b, B:34:0x0071, B:36:0x0081), top: B:2:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, CMSTypedStream cMSTypedStream, InputStream inputStream) {
        super(inputStream);
        CMSTypedStream pKCS7TypedStream;
        try {
            this._signedContent = cMSTypedStream;
            this._signedData = SignedDataParser.getInstance(this._contentInfo.getContent(16));
            this.digests = new HashMap();
            ASN1SetParser digestAlgorithms = this._signedData.getDigestAlgorithms();
            HashSet hashSet = new HashSet();
            while (true) {
                ASN1Encodable readObject = digestAlgorithms.readObject();
                if (readObject == null) {
                    break;
                }
                AlgorithmIdentifier algorithmIdentifier = AlgorithmIdentifier.getInstance(readObject);
                hashSet.add(algorithmIdentifier);
                try {
                    DigestCalculator digestCalculator = digestCalculatorProvider.get(algorithmIdentifier);
                    if (digestCalculator != null) {
                        this.digests.put(algorithmIdentifier.getAlgorithm(), digestCalculator);
                    }
                } catch (OperatorCreationException unused) {
                }
            }
            this.digestAlgorithms = Collections.unmodifiableSet(hashSet);
            ContentInfoParser encapContentInfo = this._signedData.getEncapContentInfo();
            ASN1Encodable content = encapContentInfo.getContent(4);
            if (!(content instanceof ASN1OctetStringParser)) {
                if (content != null) {
                    pKCS7TypedStream = new PKCS7TypedStream(encapContentInfo.getContentType(), content);
                    if (this._signedContent != null) {
                        pKCS7TypedStream.drain();
                    }
                    this._signedContent = pKCS7TypedStream;
                }
                this._signedContentType = cMSTypedStream == null ? encapContentInfo.getContentType() : this._signedContent.getContentType();
                return;
            }
            pKCS7TypedStream = new CMSTypedStream(encapContentInfo.getContentType(), ((ASN1OctetStringParser) content).getOctetStream());
            if (this._signedContent == null) {
                this._signedContent = pKCS7TypedStream;
                this._signedContentType = cMSTypedStream == null ? encapContentInfo.getContentType() : this._signedContent.getContentType();
                return;
            } else {
                pKCS7TypedStream.drain();
                this._signedContentType = cMSTypedStream == null ? encapContentInfo.getContentType() : this._signedContent.getContentType();
                return;
            }
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("io exception: ")), e2);
        }
        throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("io exception: ")), e2);
    }

    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, CMSTypedStream cMSTypedStream, byte[] bArr) {
        this(digestCalculatorProvider, cMSTypedStream, new ByteArrayInputStream(bArr));
    }

    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, byte[] bArr) {
        this(digestCalculatorProvider, new ByteArrayInputStream(bArr));
    }
}
