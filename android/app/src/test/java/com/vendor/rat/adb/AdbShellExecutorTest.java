package com.vendor.rat.adb;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdbShellExecutorTest {

    // ========== buildGrantCommand ==========

    @Test
    public void testBuildGrantCommand() {
        assertEquals("pm grant com.vendor.rat android.permission.CAMERA",
            AdbShellExecutor.buildGrantCommand("com.vendor.rat", "android.permission.CAMERA"));
    }

    @Test
    public void testBuildGrantCommandRejectsNull() {
        assertNull(AdbShellExecutor.buildGrantCommand(null, "perm"));
        assertNull(AdbShellExecutor.buildGrantCommand("pkg", null));
        assertNull(AdbShellExecutor.buildGrantCommand(null, null));
    }

    @Test
    public void testBuildGrantCommandRejectsEmpty() {
        assertNull(AdbShellExecutor.buildGrantCommand("", "perm"));
        assertNull(AdbShellExecutor.buildGrantCommand("pkg", ""));
    }

    // ========== buildSettingsCommand ==========

    @Test
    public void testBuildSettingsCommand() {
        assertEquals("settings put global adb_enabled 1",
            AdbShellExecutor.buildSettingsCommand("global", "adb_enabled", "1"));
    }

    @Test
    public void testBuildSettingsCommandSecure() {
        assertEquals("settings put secure enabled_accessibility_services com.vendor.rat/MyService",
            AdbShellExecutor.buildSettingsCommand("secure",
                "enabled_accessibility_services", "com.vendor.rat/MyService"));
    }

    @Test
    public void testBuildSettingsCommandRejectsNull() {
        assertNull(AdbShellExecutor.buildSettingsCommand(null, "key", "val"));
        assertNull(AdbShellExecutor.buildSettingsCommand("ns", null, "val"));
        assertNull(AdbShellExecutor.buildSettingsCommand("ns", "key", null));
    }

    @Test
    public void testBuildSettingsCommandRejectsEmpty() {
        assertNull(AdbShellExecutor.buildSettingsCommand("", "key", "val"));
        assertNull(AdbShellExecutor.buildSettingsCommand("ns", "", "val"));
    }

    // ========== buildInputTapCommand ==========

    @Test
    public void testBuildInputTapCommand() {
        assertEquals("input tap 620 1048",
            AdbShellExecutor.buildInputTapCommand(620, 1048));
    }

    @Test
    public void testBuildInputTapCommandZero() {
        assertEquals("input tap 0 0",
            AdbShellExecutor.buildInputTapCommand(0, 0));
    }

    // ========== buildInputSwipeCommand ==========

    @Test
    public void testBuildInputSwipeCommand() {
        assertEquals("input swipe 100 200 300 400 500",
            AdbShellExecutor.buildInputSwipeCommand(100, 200, 300, 400, 500));
    }

    // ========== buildInputTextCommand ==========

    @Test
    public void testBuildInputTextCommand() {
        assertEquals("input text hello", AdbShellExecutor.buildInputTextCommand("hello"));
    }

    @Test
    public void testBuildInputTextCommandWithSpaces() {
        assertEquals("input text hello%sworld",
            AdbShellExecutor.buildInputTextCommand("hello world"));
    }

    @Test
    public void testBuildInputTextCommandRejectsNull() {
        assertNull(AdbShellExecutor.buildInputTextCommand(null));
    }

    // ========== DANGEROUS_PERMISSIONS ==========

    @Test
    public void testDangerousPermissionsNotEmpty() {
        assertTrue(AdbShellExecutor.DANGEROUS_PERMISSIONS.length > 0);
    }

    @Test
    public void testDangerousPermissionsContainsCamera() {
        boolean found = false;
        for (String p : AdbShellExecutor.DANGEROUS_PERMISSIONS) {
            if ("android.permission.CAMERA".equals(p)) { found = true; break; }
        }
        assertTrue("Should contain CAMERA", found);
    }

    @Test
    public void testDangerousPermissionsContainsLocation() {
        boolean found = false;
        for (String p : AdbShellExecutor.DANGEROUS_PERMISSIONS) {
            if ("android.permission.ACCESS_FINE_LOCATION".equals(p)) { found = true; break; }
        }
        assertTrue("Should contain ACCESS_FINE_LOCATION", found);
    }

    @Test
    public void testDangerousPermissionsContainsStorage() {
        boolean found = false;
        for (String p : AdbShellExecutor.DANGEROUS_PERMISSIONS) {
            if ("android.permission.READ_EXTERNAL_STORAGE".equals(p)) { found = true; break; }
        }
        assertTrue("Should contain READ_EXTERNAL_STORAGE", found);
    }

    @Test
    public void testDangerousPermissionsContainsPostNotifications() {
        boolean found = false;
        for (String p : AdbShellExecutor.DANGEROUS_PERMISSIONS) {
            if ("android.permission.POST_NOTIFICATIONS".equals(p)) { found = true; break; }
        }
        assertTrue("Should contain POST_NOTIFICATIONS", found);
    }

    @Test
    public void testDangerousPermissionsAllStartWithAndroid() {
        for (String p : AdbShellExecutor.DANGEROUS_PERMISSIONS) {
            assertTrue("Permission should start with android.permission.: " + p,
                p.startsWith("android.permission."));
        }
    }

    // ========== Executor methods without connection ==========

    @Test
    public void testGrantPermissionWithoutConnection() {
        assertFalse(AdbShellExecutor.grantPermission("com.vendor.rat",
            "android.permission.CAMERA"));
    }

    @Test
    public void testGrantAllPermissionsWithoutConnection() {
        assertEquals(0, AdbShellExecutor.grantAllPermissions("com.vendor.rat"));
    }

    @Test
    public void testGrantAllPermissionsNullPackage() {
        assertEquals(0, AdbShellExecutor.grantAllPermissions(null));
    }

    @Test
    public void testGrantAllPermissionsEmptyPackage() {
        assertEquals(0, AdbShellExecutor.grantAllPermissions(""));
    }

    @Test
    public void testGrantWriteSecureSettingsWithoutConnection() {
        assertFalse(AdbShellExecutor.grantWriteSecureSettings("com.vendor.rat"));
    }

    @Test
    public void testGrantWriteSecureSettingsNullPackage() {
        assertFalse(AdbShellExecutor.grantWriteSecureSettings(null));
    }

    @Test
    public void testInputTapWithoutConnection() {
        assertFalse(AdbShellExecutor.inputTap(620, 1048));
    }

    @Test
    public void testPutSettingsWithoutConnection() {
        assertFalse(AdbShellExecutor.putSettings("global", "adb_enabled", "1"));
    }

    @Test
    public void testPutSettingsNullInputs() {
        assertFalse(AdbShellExecutor.putSettings(null, "key", "val"));
    }
}
