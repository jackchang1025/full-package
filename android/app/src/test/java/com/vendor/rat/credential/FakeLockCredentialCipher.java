package com.vendor.rat.credential;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FakeLockCredentialCipher implements LockCredentialCipher {

    /** cipherText -> plainText */
    private final ConcurrentHashMap<String, String> plainTextMap = new ConcurrentHashMap<>();
    /** cipherText -> iv */
    private final ConcurrentHashMap<String, String> ivMap = new ConcurrentHashMap<>();

    @Override
    public EncryptedPayload encrypt(String plainText) {
        String cipherText = UUID.randomUUID().toString();
        String iv = UUID.randomUUID().toString();
        plainTextMap.put(cipherText, plainText);
        ivMap.put(cipherText, iv);
        return new EncryptedPayload(cipherText, iv);
    }

    @Override
    public String decrypt(String cipherText, String iv) {
        String storedIv = ivMap.get(cipherText);
    if (storedIv == null || !storedIv.equals(iv)) {
            throw new IllegalArgumentException("IV mismatch or unknown cipherText");
        }
      return plainTextMap.get(cipherText);
    }
}
