package org.bouncycastle.operator;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public interface InputExpanderProvider {
    InputExpander get(AlgorithmIdentifier algorithmIdentifier);
}
