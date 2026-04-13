package org.bouncycastle.cms;

/* loaded from: classes.dex */
public interface SignerInformationVerifierProvider {
    SignerInformationVerifier get(SignerId signerId);
}
