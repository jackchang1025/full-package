package org.bouncycastle.dvcs;

import org.bouncycastle.asn1.dvcs.Data;

/* loaded from: classes.dex */
public class CCPDRequestData extends DVCSRequestData {
    public CCPDRequestData(Data data) {
        super(data);
        initDigest();
    }

    private void initDigest() {
        if (this.data.getMessageImprint() == null) {
            throw new DVCSConstructionException("DVCSRequest.data.messageImprint should be specified for CCPD service");
        }
    }

    public MessageImprint getMessageImprint() {
        return new MessageImprint(this.data.getMessageImprint());
    }
}
