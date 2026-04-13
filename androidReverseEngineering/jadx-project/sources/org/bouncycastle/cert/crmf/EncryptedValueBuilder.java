package org.bouncycastle.cert.crmf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.crmf.EncryptedValue;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.operator.KeyWrapper;
import org.bouncycastle.operator.OperatorException;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfoBuilder;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class EncryptedValueBuilder {
    private OutputEncryptor encryptor;
    private EncryptedValuePadder padder;
    private KeyWrapper wrapper;

    public EncryptedValueBuilder(KeyWrapper keyWrapper, OutputEncryptor outputEncryptor) {
        this(keyWrapper, outputEncryptor, null);
    }

    private EncryptedValue encryptData(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStream outputStream = this.encryptor.getOutputStream(byteArrayOutputStream);
        try {
            outputStream.write(bArr);
            outputStream.close();
            AlgorithmIdentifier algorithmIdentifier = this.encryptor.getAlgorithmIdentifier();
            try {
                this.wrapper.generateWrappedKey(this.encryptor.getKey());
                return new EncryptedValue(null, algorithmIdentifier, new DERBitString(this.wrapper.generateWrappedKey(this.encryptor.getKey())), this.wrapper.getAlgorithmIdentifier(), null, new DERBitString(byteArrayOutputStream.toByteArray()));
            } catch (OperatorException e2) {
                throw new CRMFException("cannot wrap key: " + e2.getMessage(), e2);
            }
        } catch (IOException e3) {
            throw new CRMFException(AbstractC0000a.m8d(e3, new StringBuilder("cannot process data: ")), e3);
        }
    }

    private byte[] padData(byte[] bArr) {
        EncryptedValuePadder encryptedValuePadder = this.padder;
        return encryptedValuePadder != null ? encryptedValuePadder.getPaddedData(bArr) : bArr;
    }

    public EncryptedValue build(PrivateKeyInfo privateKeyInfo) {
        try {
            return new EncryptedValue(privateKeyInfo.getPrivateKeyAlgorithm(), this.encryptor.getAlgorithmIdentifier(), new DERBitString(this.wrapper.generateWrappedKey(this.encryptor.getKey())), this.wrapper.getAlgorithmIdentifier(), null, new DERBitString(new PKCS8EncryptedPrivateKeyInfoBuilder(privateKeyInfo).build(this.encryptor).getEncryptedData()));
        } catch (IllegalStateException e2) {
            throw new CRMFException("cannot encode key: " + e2.getMessage(), e2);
        } catch (OperatorException e3) {
            throw new CRMFException("cannot wrap key: " + e3.getMessage(), e3);
        }
    }

    public EncryptedValueBuilder(KeyWrapper keyWrapper, OutputEncryptor outputEncryptor, EncryptedValuePadder encryptedValuePadder) {
        this.wrapper = keyWrapper;
        this.encryptor = outputEncryptor;
        this.padder = encryptedValuePadder;
    }

    public EncryptedValue build(X509CertificateHolder x509CertificateHolder) {
        try {
            return encryptData(padData(x509CertificateHolder.getEncoded()));
        } catch (IOException e2) {
            throw new CRMFException(AbstractC0000a.m8d(e2, new StringBuilder("cannot encode certificate: ")), e2);
        }
    }

    public EncryptedValue build(char[] cArr) {
        return encryptData(padData(Strings.toUTF8ByteArray(cArr)));
    }
}
