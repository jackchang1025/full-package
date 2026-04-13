package org.bouncycastle.dvcs;

import org.bouncycastle.asn1.dvcs.Data;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;

/* loaded from: classes.dex */
public class VSDRequestData extends DVCSRequestData {
    private CMSSignedData doc;

    public VSDRequestData(Data data) {
        super(data);
        initDocument();
    }

    private void initDocument() {
        if (this.doc == null) {
            if (this.data.getMessage() == null) {
                throw new DVCSConstructionException("DVCSRequest.data.message should be specified for VSD service");
            }
            try {
                this.doc = new CMSSignedData(this.data.getMessage().getOctets());
            } catch (CMSException e2) {
                throw new DVCSConstructionException("Can't read CMS SignedData from input", e2);
            }
        }
    }

    public byte[] getMessage() {
        return this.data.getMessage().getOctets();
    }

    public CMSSignedData getParsedMessage() {
        return this.doc;
    }
}
