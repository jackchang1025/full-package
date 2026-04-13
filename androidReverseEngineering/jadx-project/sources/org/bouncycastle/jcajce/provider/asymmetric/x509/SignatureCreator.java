package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.security.Signature;

/* loaded from: classes.dex */
interface SignatureCreator {
    Signature createSignature(String str);
}
