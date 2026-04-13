package org.bouncycastle.tsp.cms;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.cms.Attributes;
import org.bouncycastle.asn1.cms.MetaData;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.DigestCalculator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class MetaDataUtil {
    private final MetaData metaData;

    public MetaDataUtil(MetaData metaData) {
        this.metaData = metaData;
    }

    private String convertString(ASN1String aSN1String) {
        if (aSN1String != null) {
            return aSN1String.toString();
        }
        return null;
    }

    public String getFileName() {
        MetaData metaData = this.metaData;
        if (metaData != null) {
            return convertString(metaData.getFileNameUTF8());
        }
        return null;
    }

    public String getMediaType() {
        MetaData metaData = this.metaData;
        if (metaData != null) {
            return convertString(metaData.getMediaType());
        }
        return null;
    }

    public Attributes getOtherMetaData() {
        MetaData metaData = this.metaData;
        if (metaData != null) {
            return metaData.getOtherMetaData();
        }
        return null;
    }

    public void initialiseMessageImprintDigestCalculator(DigestCalculator digestCalculator) {
        MetaData metaData = this.metaData;
        if (metaData == null || !metaData.isHashProtected()) {
            return;
        }
        try {
            digestCalculator.getOutputStream().write(this.metaData.getEncoded(ASN1Encoding.DER));
        } catch (IOException e2) {
            throw new CMSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to initialise calculator from metaData: ")), e2);
        }
    }
}
