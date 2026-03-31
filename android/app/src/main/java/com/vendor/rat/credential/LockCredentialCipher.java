package com.vendor.rat.credential;

public interface LockCredentialCipher {

    EncryptedPayload encrypt(String plainText) throws Exception;

    String decrypt(String cipherText, String iv) throws Exception;

    final class EncryptedPayload {
        private final String cipherText;
        private final String iv;

        public EncryptedPayload(String cipherText, String iv) {
            this.cipherText = cipherText;
            this.iv = iv;
        }

        public String getCipherText() {
            return cipherText;
        }

        public String getIv() {
            return iv;
        }
    }
}
