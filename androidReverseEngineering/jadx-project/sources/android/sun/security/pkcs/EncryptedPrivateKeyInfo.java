package android.sun.security.pkcs;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.x509.AlgorithmId;
import java.io.IOException;

/* loaded from: classes.dex */
public class EncryptedPrivateKeyInfo {
    private AlgorithmId algid;
    private byte[] encoded;
    private byte[] encryptedData;

    public EncryptedPrivateKeyInfo(AlgorithmId algorithmId, byte[] bArr) {
        this.algid = algorithmId;
        this.encryptedData = (byte[]) bArr.clone();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EncryptedPrivateKeyInfo)) {
            return false;
        }
        try {
            byte[] encoded = getEncoded();
            byte[] encoded2 = ((EncryptedPrivateKeyInfo) obj).getEncoded();
            if (encoded.length != encoded2.length) {
                return false;
            }
            for (int i2 = 0; i2 < encoded.length; i2++) {
                if (encoded[i2] != encoded2[i2]) {
                    return false;
                }
            }
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public AlgorithmId getAlgorithm() {
        return this.algid;
    }

    public byte[] getEncoded() {
        Object clone;
        byte[] bArr = this.encoded;
        if (bArr != null) {
            clone = bArr.clone();
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            this.algid.encode(derOutputStream2);
            derOutputStream2.putOctetString(this.encryptedData);
            derOutputStream.write((byte) 48, derOutputStream2);
            byte[] byteArray = derOutputStream.toByteArray();
            this.encoded = byteArray;
            clone = byteArray.clone();
        }
        return (byte[]) clone;
    }

    public byte[] getEncryptedData() {
        return (byte[]) this.encryptedData.clone();
    }

    public int hashCode() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.encryptedData;
            if (i2 >= bArr.length) {
                return i3;
            }
            i3 += bArr[i2] * i2;
            i2++;
        }
    }

    public EncryptedPrivateKeyInfo(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("encoding must not be null");
        }
        DerValue derValue = new DerValue(bArr);
        DerValue[] derValueArr = {derValue.data.getDerValue(), derValue.data.getDerValue()};
        if (derValue.data.available() != 0) {
            throw new IOException("overrun, bytes = " + derValue.data.available());
        }
        this.algid = AlgorithmId.parse(derValueArr[0]);
        if (derValueArr[0].data.available() != 0) {
            throw new IOException("encryptionAlgorithm field overrun");
        }
        this.encryptedData = derValueArr[1].getOctetString();
        if (derValueArr[1].data.available() != 0) {
            throw new IOException("encryptedData field overrun");
        }
        this.encoded = (byte[]) bArr.clone();
    }
}
