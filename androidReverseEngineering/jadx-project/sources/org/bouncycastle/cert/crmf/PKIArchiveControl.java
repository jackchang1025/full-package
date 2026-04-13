package org.bouncycastle.cert.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.bouncycastle.asn1.crmf.EncryptedKey;
import org.bouncycastle.asn1.crmf.PKIArchiveOptions;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKIArchiveControl implements Control {
    public static final int archiveRemGenPrivKey = 2;
    public static final int encryptedPrivKey = 0;
    public static final int keyGenParameters = 1;
    private static final ASN1ObjectIdentifier type = CRMFObjectIdentifiers.id_regCtrl_pkiArchiveOptions;
    private final PKIArchiveOptions pkiArchiveOptions;

    public PKIArchiveControl(PKIArchiveOptions pKIArchiveOptions) {
        this.pkiArchiveOptions = pKIArchiveOptions;
    }

    public int getArchiveType() {
        return this.pkiArchiveOptions.getType();
    }

    public CMSEnvelopedData getEnvelopedData() {
        try {
            return new CMSEnvelopedData(new ContentInfo(CMSObjectIdentifiers.envelopedData, EnvelopedData.getInstance(EncryptedKey.getInstance(this.pkiArchiveOptions.getValue()).getValue())));
        } catch (CMSException e2) {
            throw new CRMFException("CMS parsing error: " + e2.getMessage(), e2.getCause());
        } catch (Exception e3) {
            throw new CRMFException(AbstractC0000a.m9e(e3, new StringBuilder("CRMF parsing error: ")), e3);
        }
    }

    @Override // org.bouncycastle.cert.crmf.Control
    public ASN1ObjectIdentifier getType() {
        return type;
    }

    @Override // org.bouncycastle.cert.crmf.Control
    public ASN1Encodable getValue() {
        return this.pkiArchiveOptions;
    }

    public boolean isEnvelopedData() {
        return !EncryptedKey.getInstance(this.pkiArchiveOptions.getValue()).isEncryptedValue();
    }
}
