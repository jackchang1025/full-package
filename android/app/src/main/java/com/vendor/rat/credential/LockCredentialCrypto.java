package com.vendor.rat.credential;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class LockCredentialCrypto implements LockCredentialCipher {

    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String KEY_ALIAS = "vendor_rat_lock_credential";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH_BITS = 128;

    @Override
    public EncryptedPayload encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateSecretKey());
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return new EncryptedPayload(
                Base64.encodeToString(encrypted, Base64.NO_WRAP),
                Base64.encodeToString(cipher.getIV(), Base64.NO_WRAP)
        );
    }

    @Override
    public String decrypt(String cipherText, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(
                GCM_TAG_LENGTH_BITS,
                Base64.decode(iv, Base64.DEFAULT)
        );
        cipher.init(
                Cipher.DECRYPT_MODE,
                getOrCreateSecretKey(),
                spec
        );
        byte[] decrypted = cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private SecretKey getOrCreateSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);
        SecretKey existingKey = (SecretKey) keyStore.getKey(KEY_ALIAS, null);
        if (existingKey != null) {
            return existingKey;
        }

        KeyGenerator keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEY_STORE
        );
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
        )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            builder.setUnlockedDeviceRequired(false);
        }
        keyGenerator.init(builder.build());
        return keyGenerator.generateKey();
    }
}
