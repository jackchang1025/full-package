package org.bouncycastle.dvcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.dvcs.Data;
import org.bouncycastle.asn1.dvcs.TargetEtcChain;

/* loaded from: classes.dex */
public class VPKCRequestData extends DVCSRequestData {
    private List chains;

    public VPKCRequestData(Data data) {
        super(data);
        TargetEtcChain[] certs = data.getCerts();
        if (certs == null) {
            throw new DVCSConstructionException("DVCSRequest.data.certs should be specified for VPKC service");
        }
        this.chains = new ArrayList(certs.length);
        for (int i2 = 0; i2 != certs.length; i2++) {
            this.chains.add(new TargetChain(certs[i2]));
        }
    }

    public List getCerts() {
        return Collections.unmodifiableList(this.chains);
    }
}
