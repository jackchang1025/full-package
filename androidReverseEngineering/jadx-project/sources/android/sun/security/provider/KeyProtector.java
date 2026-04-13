package android.sun.security.provider;

import android.sun.security.pkcs.EncryptedPrivateKeyInfo;
import android.sun.security.pkcs.PKCS8Key;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import android.sun.security.x509.AlgorithmId;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;

/* loaded from: classes.dex */
final class KeyProtector {
    private static final String DIGEST_ALG = "SHA";
    private static final int DIGEST_LEN = 20;
    private static final String KEY_PROTECTOR_OID = "1.3.6.1.4.1.42.2.17.1.1";
    private static final int SALT_LEN = 20;
    private MessageDigest md;
    private byte[] passwdBytes;

    public KeyProtector(char[] cArr) {
        if (cArr == null) {
            throw new IllegalArgumentException("password can't be null");
        }
        this.md = MessageDigest.getInstance(DIGEST_ALG);
        this.passwdBytes = new byte[cArr.length * 2];
        int i2 = 0;
        for (char c : cArr) {
            byte[] bArr = this.passwdBytes;
            int i3 = i2 + 1;
            bArr[i2] = (byte) (c >> '\b');
            i2 = i3 + 1;
            bArr[i3] = (byte) c;
        }
    }

    public void finalize() {
        byte[] bArr = this.passwdBytes;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
            this.passwdBytes = null;
        }
    }

    public byte[] protect(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("plaintext key can't be null");
        }
        if (!"PKCS#8".equalsIgnoreCase(key.getFormat())) {
            throw new KeyStoreException("Cannot get key bytes, not PKCS#8 encoded");
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new KeyStoreException("Cannot get key bytes, encoding not supported");
        }
        int length = encoded.length / 20;
        if (encoded.length % 20 != 0) {
            length++;
        }
        byte[] bArr = new byte[20];
        new SecureRandom().nextBytes(bArr);
        int length2 = encoded.length;
        byte[] bArr2 = new byte[length2];
        byte[] bArr3 = bArr;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            this.md.update(this.passwdBytes);
            this.md.update(bArr3);
            bArr3 = this.md.digest();
            this.md.reset();
            System.arraycopy(bArr3, 0, bArr2, i3, i2 < length + (-1) ? bArr3.length : length2 - i3);
            i2++;
            i3 += 20;
        }
        int length3 = encoded.length;
        byte[] bArr4 = new byte[length3];
        for (int i4 = 0; i4 < length3; i4++) {
            bArr4[i4] = (byte) (encoded[i4] ^ bArr2[i4]);
        }
        byte[] bArr5 = new byte[20 + length3 + 20];
        System.arraycopy(bArr, 0, bArr5, 0, 20);
        System.arraycopy(bArr4, 0, bArr5, 20, length3);
        this.md.update(this.passwdBytes);
        Arrays.fill(this.passwdBytes, (byte) 0);
        this.passwdBytes = null;
        this.md.update(encoded);
        byte[] digest = this.md.digest();
        this.md.reset();
        System.arraycopy(digest, 0, bArr5, 20 + length3, digest.length);
        try {
            return new EncryptedPrivateKeyInfo(new AlgorithmId(new ObjectIdentifier(KEY_PROTECTOR_OID)), bArr5).getEncoded();
        } catch (IOException e2) {
            throw new KeyStoreException(e2.getMessage());
        }
    }

    public Key recover(EncryptedPrivateKeyInfo encryptedPrivateKeyInfo) {
        if (!encryptedPrivateKeyInfo.getAlgorithm().getOID().toString().equals(KEY_PROTECTOR_OID)) {
            throw new UnrecoverableKeyException("Unsupported key protection algorithm");
        }
        byte[] encryptedData = encryptedPrivateKeyInfo.getEncryptedData();
        byte[] bArr = new byte[20];
        System.arraycopy(encryptedData, 0, bArr, 0, 20);
        int length = (encryptedData.length - 20) - 20;
        int i2 = length / 20;
        if (length % 20 != 0) {
            i2++;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(encryptedData, 20, bArr2, 0, length);
        byte[] bArr3 = new byte[length];
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            this.md.update(this.passwdBytes);
            this.md.update(bArr);
            bArr = this.md.digest();
            this.md.reset();
            System.arraycopy(bArr, 0, bArr3, i4, i3 < i2 + (-1) ? bArr.length : length - i4);
            i3++;
            i4 += 20;
        }
        byte[] bArr4 = new byte[length];
        for (int i5 = 0; i5 < length; i5++) {
            bArr4[i5] = (byte) (bArr2[i5] ^ bArr3[i5]);
        }
        this.md.update(this.passwdBytes);
        Arrays.fill(this.passwdBytes, (byte) 0);
        this.passwdBytes = null;
        this.md.update(bArr4);
        byte[] digest = this.md.digest();
        this.md.reset();
        for (int i6 = 0; i6 < digest.length; i6++) {
            if (digest[i6] != encryptedData[length + 20 + i6]) {
                throw new UnrecoverableKeyException("Cannot recover key");
            }
        }
        try {
            return PKCS8Key.parseKey(new DerValue(bArr4));
        } catch (IOException e2) {
            throw new UnrecoverableKeyException(e2.getMessage());
        }
    }
}
