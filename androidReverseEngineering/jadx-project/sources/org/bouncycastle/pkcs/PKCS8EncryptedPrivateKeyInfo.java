package org.bouncycastle.pkcs;

import java.io.ByteArrayInputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS8EncryptedPrivateKeyInfo {
    private EncryptedPrivateKeyInfo encryptedPrivateKeyInfo;

    public PKCS8EncryptedPrivateKeyInfo(EncryptedPrivateKeyInfo encryptedPrivateKeyInfo) {
        this.encryptedPrivateKeyInfo = encryptedPrivateKeyInfo;
    }

    private static EncryptedPrivateKeyInfo parseBytes(byte[] bArr) {
        try {
            return EncryptedPrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(bArr));
        } catch (ClassCastException e2) {
            throw new PKCSIOException("malformed data: " + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new PKCSIOException("malformed data: " + e3.getMessage(), e3);
        }
    }

    public PrivateKeyInfo decryptPrivateKeyInfo(InputDecryptorProvider inputDecryptorProvider) {
        try {
            return PrivateKeyInfo.getInstance(Streams.readAll(inputDecryptorProvider.get(this.encryptedPrivateKeyInfo.getEncryptionAlgorithm()).getInputStream(new ByteArrayInputStream(this.encryptedPrivateKeyInfo.getEncryptedData()))));
        } catch (Exception e2) {
            throw new PKCSException(AbstractC0000a.m9e(e2, new StringBuilder("unable to read encrypted data: ")), e2);
        }
    }

    public byte[] getEncoded() {
        return this.encryptedPrivateKeyInfo.getEncoded();
    }

    public byte[] getEncryptedData() {
        return this.encryptedPrivateKeyInfo.getEncryptedData();
    }

    public AlgorithmIdentifier getEncryptionAlgorithm() {
        return this.encryptedPrivateKeyInfo.getEncryptionAlgorithm();
    }

    public EncryptedPrivateKeyInfo toASN1Structure() {
        return this.encryptedPrivateKeyInfo;
    }

    public PKCS8EncryptedPrivateKeyInfo(byte[] bArr) {
        this(parseBytes(bArr));
    }
}
