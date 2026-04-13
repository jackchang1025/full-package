package org.bouncycastle.dvcs;

import org.bouncycastle.asn1.dvcs.Data;

/* loaded from: classes.dex */
public abstract class DVCSRequestData {
    protected Data data;

    public DVCSRequestData(Data data) {
        this.data = data;
    }

    public Data toASN1Structure() {
        return this.data;
    }
}
