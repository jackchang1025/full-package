package org.bouncycastle.operator;

import java.io.InputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public interface InputDecryptor {
    AlgorithmIdentifier getAlgorithmIdentifier();

    InputStream getInputStream(InputStream inputStream);
}
