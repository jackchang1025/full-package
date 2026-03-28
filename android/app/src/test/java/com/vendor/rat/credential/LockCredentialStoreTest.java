package com.vendor.rat.credential;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.vendor.rat.utils.SharedUtils;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class LockCredentialStoreTest {

    @Before
    public void setUp() {
        SharedUtils.init(RuntimeEnvironment.getApplication());
        LockCredentialStore.setCipherForTest(new FakeLockCredentialCipher());
      LockCredentialStore.clearAll();
    }

    @Test
    public void savePin_shouldPersistEncryptedPinAndType_only() {
  LockCredentialStore.clearAll();

        LockCredentialStore.savePin("123456");

        assertEquals("PIN", LockCredentialStore.getLockType());
        assertEquals("123456", LockCredentialStore.getPin());
        assertTrue(LockCredentialStore.hasCredential());
    assertFalse(LockCredentialStore.isCurrentRunVerified());
        assertFalse(LockCredentialStore.isPromptSuppressedForCurrentRun());
    }

    @Test
    public void getPin_shouldReturnNull_whenIvDoesNotMatchCipherText() {
        LockCredentialStore.savePin("123456");
        SharedUtils.save("wrong-iv", LockCredentialKeys.IV);

        assertNull(LockCredentialStore.getPin());
    }

    @Test
    public void savePin_shouldRollback_whenPartialSaveFails() {
  SharedUtils.init(new FailingSaveContext(RuntimeEnvironment.getApplication(), LockCredentialKeys.IV));

        LockCredentialStore.savePin("123456");

    assertFalse(LockCredentialStore.hasCredential());
        assertNull(SharedUtils.getString(LockCredentialKeys.LOCK_TYPE));
  assertNull(SharedUtils.getString(LockCredentialKeys.ENCRYPTED_PIN));
        assertNull(SharedUtils.getString(LockCredentialKeys.IV));
  assertEquals(0L, SharedUtils.getLong(LockCredentialKeys.SAVED_AT));
        assertNull(LockCredentialStore.getPin());
    }

  @Test
 public void cancelPrompt_shouldSuppressPromptUntilReset() {
        LockCredentialStore.markPromptSuppressedForCurrentRun();
        assertTrue(LockCredentialStore.isPromptSuppressedForCurrentRun());

        LockCredentialStore.resetCurrentRunFlags();

        assertFalse(LockCredentialStore.isPromptSuppressedForCurrentRun());
    }

    @Test
    public void markCurrentRunVerified_shouldNotPersistAcrossRuntimeReset() {
        LockCredentialStore.savePin("123456");
        LockCredentialStore.markCurrentRunVerified();

        assertTrue(LockCredentialStore.isCurrentRunVerified());

 LockCredentialStore.resetCurrentRunFlags();

        assertFalse(LockCredentialStore.isCurrentRunVerified());
        assertEquals("123456", LockCredentialStore.getPin());
    }

    @Test
    public void clearAll_shouldRemoveStoredPinAndRuntimeState() {
    LockCredentialStore.savePin("123456");
      LockCredentialStore.markCurrentRunVerified();
   LockCredentialStore.markPromptSuppressedForCurrentRun();

        LockCredentialStore.clearAll();

        assertFalse(LockCredentialStore.hasCredential());
        assertFalse(LockCredentialStore.isCurrentRunVerified());
        assertFalse(LockCredentialStore.isPromptSuppressedForCurrentRun());
        assertNull(LockCredentialStore.getPin());
    }

    // ===== New tests for the 3 security fixes =====

    /**
     * Fix 3: When decryption fails (wrong IV), getPin() must clear persisted state
     * so that hasCredential() returns false afterwards.
     */
    @Test
    public void getPin_withWrongIv_shouldClearPersistedStateAndHasCredentialReturnsFalse() {
        LockCredentialStore.savePin("123456");
      assertTrue("Precondition: hasCredential should be true after savePin",
                LockCredentialStore.hasCredential());

        // Corrupt the IV so decryption will throw
     SharedUtils.save("corrupted-iv-value", LockCredentialKeys.IV);

        String result = LockCredentialStore.getPin();

        assertNull("getPin should return null on decryption failure", result);
        assertFalse("hasCredential should be false after decryption failure clears state",
  LockCredentialStore.hasCredential());
        assertNull("LOCK_TYPE should be cleared", SharedUtils.getString(LockCredentialKeys.LOCK_TYPE));
        assertNull("ENCRYPTED_PIN should be cleared",
SharedUtils.getString(LockCredentialKeys.ENCRYPTED_PIN));
    assertNull("IV should be cleared", SharedUtils.getString(LockCredentialKeys.IV));
        assertFalse("currentRunVerified should be reset", LockCredentialStore.isCurrentRunVerified());
   assertFalse("promptSuppressed should be reset",
        LockCredentialStore.isPromptSuppressedForCurrentRun());
    }

    /**
  * Fix 1 (partial-save rollback): When commit() fails mid-write (IV key blocked),
     * no dirty state should remain — all 4 keys must be absent.
  */
    @Test
    public void savePin_partialCommitFailure_leavesNoDirtyState() {
      // Re-init with a context that makes IV writes fail at commit time
    SharedUtils.init(new FailingSaveContext(RuntimeEnvironment.getApplication(), LockCredentialKeys.IV));

        LockCredentialStore.savePin("secret");

     assertFalse("hasCredential must be false after failed save", LockCredentialStore.hasCredential());
        assertNull("LOCK_TYPE must not be persisted", SharedUtils.getString(LockCredentialKeys.LOCK_TYPE));
        assertNull("ENCRYPTED_PIN must not be persisted",
SharedUtils.getString(LockCredentialKeys.ENCRYPTED_PIN));
        assertNull("IV must not be persisted", SharedUtils.getString(LockCredentialKeys.IV));
        assertEquals("SAVED_AT must be 0", 0L, SharedUtils.getLong(LockCredentialKeys.SAVED_AT));
    }

    /**
     * Fix 2: FakeLockCredentialCipher must not embed plaintext in cipherText or iv.
     */
    @Test
    public void fakeCipher_cipherTextAndIv_mustNotEqualPlaintext() throws Exception {
        FakeLockCredentialCipher fakeCipher = new FakeLockCredentialCipher();
        String pin = "123456";

        LockCredentialCipher.EncryptedPayload payload = fakeCipher.encrypt(pin);

        assertNotEquals("cipherText must not equal plaintext", pin, payload.getCipherText());
        assertNotEquals("iv must not equal plaintext", pin, payload.getIv());
        // Also verify cipherText does not contain the plaintext as a substring
        assertFalse("cipherText must not contain plaintext",
     payload.getCipherText().contains(pin));
        assertFalse("iv must not contain plaintext", payload.getIv().contains(pin));
        // Verify round-trip still works
        assertEquals("decrypt must recover original plaintext",
         pin, fakeCipher.decrypt(payload.getCipherText(), payload.getIv()));
}

    private static final class FailingSaveContext extends ContextWrapper {

     private final String failingKey;

        FailingSaveContext(Context base, String failingKey) {
     super(base);
     this.failingKey = failingKey;
      }

  @Override
        public Context getApplicationContext() {
    return this;
   }

     @Override
        public SharedPreferences getSharedPreferences(String name, int mode) {
            return new FailingSaveSharedPreferences(super.getSharedPreferences(name, mode), failingKey);
        }
    }

    private static final class FailingSaveSharedPreferences implements SharedPreferences {

        private final SharedPreferences delegate;
        private final String failingKey;

        FailingSaveSharedPreferences(SharedPreferences delegate, String failingKey) {
            this.delegate = delegate;
            this.failingKey = failingKey;
     }

        @Override
      public Map<String, ?> getAll() {
            return delegate.getAll();
        }

        @Override
     public String getString(String key, String defValue) {
      return delegate.getString(key, defValue);
        }

      @Override
   public Set<String> getStringSet(String key, Set<String> defValues) {
    return delegate.getStringSet(key, defValues);
        }

        @Override
      public int getInt(String key, int defValue) {
            return delegate.getInt(key, defValue);
        }

        @Override
        public long getLong(String key, long defValue) {
 return delegate.getLong(key, defValue);
        }

        @Override
        public float getFloat(String key, float defValue) {
    return delegate.getFloat(key, defValue);
  }

        @Override
        public boolean getBoolean(String key, boolean defValue) {
            return delegate.getBoolean(key, defValue);
    }

        @Override
        public boolean contains(String key) {
  return delegate.contains(key);
        }

        @Override
        public Editor edit() {
          return new FailingSaveEditor(delegate.edit(), failingKey);
   }

        @Override
        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        delegate.registerOnSharedPreferenceChangeListener(listener);
}

        @Override
        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
  delegate.unregisterOnSharedPreferenceChangeListener(listener);
     }
 }

    private static final class FailingSaveEditor implements SharedPreferences.Editor {

        private final SharedPreferences.Editor delegate;
    private final String failingKey;
  private boolean shouldFail;

        FailingSaveEditor(SharedPreferences.Editor delegate, String failingKey) {
         this.delegate = delegate;
          this.failingKey = failingKey;
   }

     @Override
      public SharedPreferences.Editor putString(String key, String value) {
            if (failingKey.equals(key)) {
shouldFail = true;
      return this;
            }
        delegate.putString(key, value);
        return this;
}

        @Override
        public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
        delegate.putStringSet(key, values);
         return this;
      }

  @Override
        public SharedPreferences.Editor putInt(String key, int value) {
     delegate.putInt(key, value);
      return this;
        }

        @Override
        public SharedPreferences.Editor putLong(String key, long value) {
          if (failingKey.equals(key)) {
                shouldFail = true;
                return this;
            }
       delegate.putLong(key, value);
            return this;
        }

        @Override
        public SharedPreferences.Editor putFloat(String key, float value) {
   delegate.putFloat(key, value);
            return this;
  }

        @Override
        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            delegate.putBoolean(key, value);
return this;
   }

        @Override
    public SharedPreferences.Editor remove(String key) {
            delegate.remove(key);
    return this;
  }

        @Override
        public SharedPreferences.Editor clear() {
       delegate.clear();
          return this;
        }

 @Override
 public boolean commit() {
            return !shouldFail && delegate.commit();
     }

    @Override
        public void apply() {
         if (shouldFail) {
                throw new IllegalStateException("Injected SharedPreferences failure for key=" + failingKey);
}
            delegate.apply();
        }
  }
}
