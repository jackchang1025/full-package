package org.bouncycastle.openssl;

import java.io.IOException;
import org.bouncycastle.operator.OperatorCreationException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PEMEncryptedKeyPair {
    private final String dekAlgName;
    private final byte[] iv;
    private final byte[] keyBytes;
    private final PEMKeyPairParser parser;

    public PEMEncryptedKeyPair(String str, byte[] bArr, byte[] bArr2, PEMKeyPairParser pEMKeyPairParser) {
        this.dekAlgName = str;
        this.iv = bArr;
        this.keyBytes = bArr2;
        this.parser = pEMKeyPairParser;
    }

    public PEMKeyPair decryptKeyPair(PEMDecryptorProvider pEMDecryptorProvider) {
        try {
            return this.parser.parse(pEMDecryptorProvider.get(this.dekAlgName).decrypt(this.keyBytes, this.iv));
        } catch (IOException e2) {
            throw e2;
        } catch (OperatorCreationException e3) {
            throw new PEMException("cannot create extraction operator: " + e3.getMessage(), e3);
        } catch (Exception e4) {
            throw new PEMException(AbstractC0000a.m9e(e4, new StringBuilder("exception processing key pair: ")), e4);
        }
    }

    public String getDekAlgName() {
        return this.dekAlgName;
    }
}
