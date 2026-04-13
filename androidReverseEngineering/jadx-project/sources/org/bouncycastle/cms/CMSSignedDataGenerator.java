package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CMSSignedDataGenerator extends CMSSignedGenerator {
    private List signerInfs;

    public CMSSignedDataGenerator() {
        this.signerInfs = new ArrayList();
    }

    public CMSSignedData generate(CMSTypedData cMSTypedData) {
        return generate(cMSTypedData, false);
    }

    public SignerInformationStore generateCounterSigners(SignerInformation signerInformation) {
        return generate(new CMSProcessableByteArray(null, signerInformation.getSignature()), false).getSignerInfos();
    }

    public CMSSignedDataGenerator(DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        super(digestAlgorithmIdentifierFinder);
        this.signerInfs = new ArrayList();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CMSSignedData generate(CMSTypedData cMSTypedData, boolean z2) {
        BEROctetString bEROctetString;
        if (!this.signerInfs.isEmpty()) {
            throw new IllegalStateException("this method can only be used with SignerInfoGenerator");
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        this.digests.clear();
        for (SignerInformation signerInformation : this._signers) {
            CMSUtils.addDigestAlgs(linkedHashSet, signerInformation, this.digestAlgIdFinder);
            aSN1EncodableVector.add(signerInformation.toASN1Structure());
        }
        ASN1ObjectIdentifier contentType = cMSTypedData.getContentType();
        if (cMSTypedData.getContent() != null) {
            ByteArrayOutputStream byteArrayOutputStream = z2 ? new ByteArrayOutputStream() : null;
            OutputStream safeOutputStream = CMSUtils.getSafeOutputStream(CMSUtils.attachSignersToOutputStream(this.signerGens, byteArrayOutputStream));
            try {
                cMSTypedData.write(safeOutputStream);
                safeOutputStream.close();
                if (z2) {
                    bEROctetString = new BEROctetString(byteArrayOutputStream.toByteArray());
                    for (SignerInfoGenerator signerInfoGenerator : this.signerGens) {
                        SignerInfo generate = signerInfoGenerator.generate(contentType);
                        linkedHashSet.add(generate.getDigestAlgorithm());
                        aSN1EncodableVector.add(generate);
                        byte[] calculatedDigest = signerInfoGenerator.getCalculatedDigest();
                        if (calculatedDigest != null) {
                            this.digests.put(generate.getDigestAlgorithm().getAlgorithm().getId(), calculatedDigest);
                        }
                    }
                    return new CMSSignedData(cMSTypedData, new ContentInfo(CMSObjectIdentifiers.signedData, new SignedData(CMSUtils.convertToBERSet(linkedHashSet), new ContentInfo(contentType, bEROctetString), this.certs.size() == 0 ? CMSUtils.createBerSetFromList(this.certs) : null, this.crls.size() != 0 ? CMSUtils.createBerSetFromList(this.crls) : null, new DERSet(aSN1EncodableVector))));
                }
            } catch (IOException e2) {
                throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("data processing exception: ")), e2);
            }
        }
        bEROctetString = null;
        while (r3.hasNext()) {
        }
        if (this.certs.size() == 0) {
        }
        if (this.crls.size() != 0) {
        }
        return new CMSSignedData(cMSTypedData, new ContentInfo(CMSObjectIdentifiers.signedData, new SignedData(CMSUtils.convertToBERSet(linkedHashSet), new ContentInfo(contentType, bEROctetString), this.certs.size() == 0 ? CMSUtils.createBerSetFromList(this.certs) : null, this.crls.size() != 0 ? CMSUtils.createBerSetFromList(this.crls) : null, new DERSet(aSN1EncodableVector))));
    }
}
