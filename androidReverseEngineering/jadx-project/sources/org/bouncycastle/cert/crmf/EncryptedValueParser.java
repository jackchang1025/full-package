package org.bouncycastle.cert.crmf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.asn1.crmf.EncryptedValue;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class EncryptedValueParser {
    private EncryptedValuePadder padder;
    private EncryptedValue value;

    public EncryptedValueParser(EncryptedValue encryptedValue) {
        this.value = encryptedValue;
    }

    private byte[] decryptValue(ValueDecryptorGenerator valueDecryptorGenerator) {
        if (this.value.getValueHint() != null) {
            throw new UnsupportedOperationException();
        }
        try {
            byte[] readAll = Streams.readAll(valueDecryptorGenerator.getValueDecryptor(this.value.getKeyAlg(), this.value.getSymmAlg(), this.value.getEncSymmKey().getBytes()).getInputStream(new ByteArrayInputStream(this.value.getEncValue().getBytes())));
            EncryptedValuePadder encryptedValuePadder = this.padder;
            return encryptedValuePadder != null ? encryptedValuePadder.getUnpaddedData(readAll) : readAll;
        } catch (IOException e2) {
            throw new CRMFException(AbstractC0000a.m8d(e2, new StringBuilder("Cannot parse decrypted data: ")), e2);
        }
    }

    public AlgorithmIdentifier getIntendedAlg() {
        return this.value.getIntendedAlg();
    }

    public X509CertificateHolder readCertificateHolder(ValueDecryptorGenerator valueDecryptorGenerator) {
        return new X509CertificateHolder(Certificate.getInstance(decryptValue(valueDecryptorGenerator)));
    }

    public char[] readPassphrase(ValueDecryptorGenerator valueDecryptorGenerator) {
        return Strings.fromUTF8ByteArray(decryptValue(valueDecryptorGenerator)).toCharArray();
    }

    public PrivateKeyInfo readPrivateKeyInfo(ValueDecryptorGenerator valueDecryptorGenerator) {
        return PrivateKeyInfo.getInstance(decryptValue(valueDecryptorGenerator));
    }

    public EncryptedValueParser(EncryptedValue encryptedValue, EncryptedValuePadder encryptedValuePadder) {
        this.value = encryptedValue;
        this.padder = encryptedValuePadder;
    }
}
