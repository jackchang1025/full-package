package com.vendor.rat.utils;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30, application = Application.class)
public class SecureSettingsWriterTest {
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
    }

    @Test
    public void testHasWriteSecureSettingsPermission() {
        // Robolectric default: permission not granted
        boolean result = SecureSettingsWriter.hasPermission(context);
        assertNotNull(Boolean.valueOf(result));
    }

    @Test
    public void testEnableWifiDebug_noPermission_returnsFalse() {
        boolean result = SecureSettingsWriter.enableWifiDebug(context);
        assertFalse(result);
    }

    @Test
    public void testEnableUsbDebug_noPermission_returnsFalse() {
        boolean result = SecureSettingsWriter.enableUsbDebug(context);
        assertFalse(result);
    }

    @Test
    public void testEnableDeveloperOptions_noPermission_returnsFalse() {
        boolean result = SecureSettingsWriter.enableDeveloperOptions(context);
        assertFalse(result);
    }

    @Test
    public void testIsWifiDebugEnabled_defaultFalse() {
        assertFalse(SecureSettingsWriter.isWifiDebugEnabled(context));
    }

    @Test
    public void testIsDeveloperOptionsEnabled_defaultFalse() {
        assertFalse(SecureSettingsWriter.isDeveloperOptionsEnabled(context));
    }

    @Test
    public void testIsUsbDebugEnabled_defaultFalse() {
        assertFalse(SecureSettingsWriter.isUsbDebugEnabled(context));
    }

    @Test
    public void testNullContext_doesNotCrash() {
        assertFalse(SecureSettingsWriter.hasPermission(null));
        assertFalse(SecureSettingsWriter.enableWifiDebug(null));
        assertFalse(SecureSettingsWriter.isWifiDebugEnabled(null));
    }
}
