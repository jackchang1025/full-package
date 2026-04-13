package org.bouncycastle.its.operator;

import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.operator.ContentVerifier;

/* loaded from: classes.dex */
public interface ITSContentVerifierProvider {
    ContentVerifier get(int i2);

    ITSCertificate getAssociatedCertificate();

    boolean hasAssociatedCertificate();
}
